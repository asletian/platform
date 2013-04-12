package cn.com.leadfar.oa.jbpm;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jbpm.api.JbpmException;
import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.jpdl.internal.activity.JpdlExternalActivity;
import org.jbpm.pvm.internal.el.Expression;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.history.HistoryEvent;
import org.jbpm.pvm.internal.history.events.TaskActivityStart;
import org.jbpm.pvm.internal.id.DbidGenerator;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.task.TaskConstants;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.jbpm.pvm.internal.util.Clock;

/**
 * 自定义的任务节点，用于会签的决策
 * @author Lee
 *
 */
public class CustomTaskActivity extends JpdlExternalActivity {

	private String name;
	private String signers; // 一个字符串，可以是qe,zy这样逗号分隔的字符串，或者是一个#{xxx}表达式
	private int passNumber; // 这个值表示有多少个用户审批通过就可以继续往下
	private String form;    // 任务表单
	// 如果signers是一个表达式，则计算表达式的结果！
	private Expression signersExpression;

	/**
	 * 当流程实例进入会签节点的时候，execute方法将被执行
	 */
	@Override
	public void execute(ActivityExecution execution) throws Exception {
		signersExpression = Expression.create(signers);
		execute((ExecutionImpl) execution);
	}

	/**
	 * 在这个方法中，根据signers定义的会签参与人员的计算结果，创建一个主任务，
	 * 同时给每个参与者创建一个子任务，这些任务共享同一个execution对象
	 * @param execution
	 */
	public void execute(ExecutionImpl execution) {
		  
		// 创建一个主TaskImpl
		DbSession dbSession = EnvironmentImpl.getFromCurrent(DbSession.class);
		TaskImpl task =  createTask(); //创建自定义的CustomTaskImpl对象 //dbSession.createTask();
		task.setExecution(execution); // 设置task对应的execution对象
		task.setProcessInstance(execution.getProcessInstance()); // task对应的processInstance对象
		task.setSignalling(true); // 表示在调用completeTask的时候，将自动signal（即流转到下一个环节）
		task.setName(execution.getActivityName()); // task的名称，取为节点的名称
		task.setFormResourceName(form);
		task.setVariable("passedNumber", 0); //设置一个任务变量，用来记录有多少个subTask已经完成
		dbSession.save(task); // 保存到数据库
		HistoryEvent.fire(new TaskActivityStart(task), execution); // 记录历史信息
		execution.waitForSignal(); // 等待，直到外界的调用

		// 根据signers定义的参与者，来创建多个SubTask
		// 这个字符串必须是逗号分隔的字符串
		String signerUsernames = (String) signersExpression.evaluate(execution);
		String[] ss = signerUsernames.split(",");
		for (String username : ss) {
			
			username = username.trim();
			
			TaskImpl subTask = task.createSubTask(execution.getActivityName()
					+ "." + username);
			subTask.setExecution(execution);
			// 
			subTask.setProcessInstance(execution.getProcessInstance());
			subTask.setSignalling(true); //完成任务的时候，尝试往下流转 // 表示在某个参与者完成（completeTask）的时候，不要触发流程往下流转
			subTask.setFormResourceName(form);
			subTask.setDescription("会签"); // task中的description属性，在OA中将作为本任务的操作名称

			// 将subTask分配给特定的assignee
			subTask.setAssignee(username);

			dbSession.save(subTask);
			HistoryEvent.fire(new TaskActivityStart(subTask), execution); // 记录历史信息
		}

	}

	/**
	 * 当一个任务被结束的时候，会调用signal()方法，以便往下流转
	 */
	@Override
	public void signal(ActivityExecution execution, String signalName,
			Map<String, ?> parameters) throws Exception {
		signal((ExecutionImpl) execution, signalName, parameters);
	}

	public void signal(ExecutionImpl execution, String signalName,
			Map<String, ?> parameters) throws Exception {

		//得到execution对应的节点
		ActivityImpl activity = execution.getActivity();

		//一些变量
		if (parameters != null) {
			execution.setVariables(parameters);
		}

		//抛出事件
		execution.fire(signalName, activity);

		DbSession taskDbSession = EnvironmentImpl
				.getFromCurrent(DbSession.class);
		//taskDbSession.findTaskByExecution(execution);
		List<Task> tasks = taskDbSession.createTaskQuery()
			.executionId(execution.getId()).list();
		
		TaskImpl task = null;
		
		for (Iterator iterator = tasks.iterator(); iterator.hasNext();) {
			TaskImpl ti = (TaskImpl) iterator.next();
			if(ti.getSuperTask() == null){ //找出根Task对象
				task = ti;
			}
		}
		
		if (task != null) {
			//因为已经signal，所以设置signal状态从true变为false
			//signal=true表示正在等待执行
			task.setSignalling(false); 
		}
		
		////////////////  找出下一步执行的Transition ////////////////////
		Transition transition = null;
		List<? extends Transition> outgoingTransitions = activity
				.getOutgoingTransitions();

		if (outgoingTransitions != null && !outgoingTransitions.isEmpty()) {
			// Lookup the outgoing transition
			boolean noOutcomeSpecified = TaskConstants.NO_TASK_OUTCOME_SPECIFIED
					.equals(signalName);
			if (noOutcomeSpecified
					&& activity.findOutgoingTransition(signalName) == null) {
				// When no specific outcome was specified, the unnamed
				// transition
				// is looked up (name is null). If a null outcome was
				// specifically
				// used, then the else clause will be used (but the result is
				// the same)
				// Note: the second part of the if clause is to avoid the
				// situation
				// where the user would have chosen the same name as the
				// constant
				transition = activity.findOutgoingTransition(null);
			} else {
				transition = activity.findOutgoingTransition(signalName);
			}

			// If no transition has been found, we check if we have a special
			// case
			// in which we can still deduce the outgoing transition
			if (transition == null) {
				// no unnamed transition found
				if (signalName == null) {
					// null was explicitly given as outcome
					throw new JbpmException(
							"No unnamed transitions were found for the task '"
									+ name + "'");
				} else if (noOutcomeSpecified) {
					// Special case: complete(id)
					if (outgoingTransitions.size() == 1) { // If only 1
															// transition, take
															// that one
						transition = outgoingTransitions.get(0);
					} else {
						throw new JbpmException(
								"No unnamed transitions were found for the task '"
										+ name + "'");
					}
				} else {
					// Likely a programmatic error.
					throw new JbpmException("No transition named '"
							+ signalName + "' was found.");
				}
			}
			
			//////////////////  进行会签决策   //////////////////////////
			//在往下流转之前，先做判断，判断任务完成的数量是否已经到达设定值
			Integer passedNumber = (Integer)task.getVariable("passedNumber");

			passedNumber = passedNumber + 1;
			
			task.setVariable("passedNumber", passedNumber);
			
			//如果子任务完成的数量已经到达设定值，则往下流转
			if(passedNumber >= passNumber){
				if (transition != null) {
					execution.take(transition);
				}
			}else{
				//尚未达到设定数量，主任务继续等待！
				execution.waitForSignal();
			}
		}

	}
	
	private TaskImpl createTask(){
	    TaskImpl task = new CustomTaskImpl();
	    long dbid = EnvironmentImpl.getFromCurrent(DbidGenerator.class).getNextId();
	    task.setDbid(dbid);
	    task.setNew(true);
	    task.setCreateTime(Clock.getTime());
	    return task;
	}

}
