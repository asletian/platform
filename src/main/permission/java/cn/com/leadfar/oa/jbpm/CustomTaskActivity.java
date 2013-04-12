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
 * �Զ��������ڵ㣬���ڻ�ǩ�ľ���
 * @author Lee
 *
 */
public class CustomTaskActivity extends JpdlExternalActivity {

	private String name;
	private String signers; // һ���ַ�����������qe,zy�������ŷָ����ַ�����������һ��#{xxx}���ʽ
	private int passNumber; // ���ֵ��ʾ�ж��ٸ��û�����ͨ���Ϳ��Լ�������
	private String form;    // �����
	// ���signers��һ�����ʽ���������ʽ�Ľ����
	private Expression signersExpression;

	/**
	 * ������ʵ�������ǩ�ڵ��ʱ��execute��������ִ��
	 */
	@Override
	public void execute(ActivityExecution execution) throws Exception {
		signersExpression = Expression.create(signers);
		execute((ExecutionImpl) execution);
	}

	/**
	 * ����������У�����signers����Ļ�ǩ������Ա�ļ�����������һ��������
	 * ͬʱ��ÿ�������ߴ���һ����������Щ������ͬһ��execution����
	 * @param execution
	 */
	public void execute(ExecutionImpl execution) {
		  
		// ����һ����TaskImpl
		DbSession dbSession = EnvironmentImpl.getFromCurrent(DbSession.class);
		TaskImpl task =  createTask(); //�����Զ����CustomTaskImpl���� //dbSession.createTask();
		task.setExecution(execution); // ����task��Ӧ��execution����
		task.setProcessInstance(execution.getProcessInstance()); // task��Ӧ��processInstance����
		task.setSignalling(true); // ��ʾ�ڵ���completeTask��ʱ�򣬽��Զ�signal������ת����һ�����ڣ�
		task.setName(execution.getActivityName()); // task�����ƣ�ȡΪ�ڵ������
		task.setFormResourceName(form);
		task.setVariable("passedNumber", 0); //����һ�����������������¼�ж��ٸ�subTask�Ѿ����
		dbSession.save(task); // ���浽���ݿ�
		HistoryEvent.fire(new TaskActivityStart(task), execution); // ��¼��ʷ��Ϣ
		execution.waitForSignal(); // �ȴ���ֱ�����ĵ���

		// ����signers����Ĳ����ߣ����������SubTask
		// ����ַ��������Ƕ��ŷָ����ַ���
		String signerUsernames = (String) signersExpression.evaluate(execution);
		String[] ss = signerUsernames.split(",");
		for (String username : ss) {
			
			username = username.trim();
			
			TaskImpl subTask = task.createSubTask(execution.getActivityName()
					+ "." + username);
			subTask.setExecution(execution);
			// 
			subTask.setProcessInstance(execution.getProcessInstance());
			subTask.setSignalling(true); //��������ʱ�򣬳���������ת // ��ʾ��ĳ����������ɣ�completeTask����ʱ�򣬲�Ҫ��������������ת
			subTask.setFormResourceName(form);
			subTask.setDescription("��ǩ"); // task�е�description���ԣ���OA�н���Ϊ������Ĳ�������

			// ��subTask������ض���assignee
			subTask.setAssignee(username);

			dbSession.save(subTask);
			HistoryEvent.fire(new TaskActivityStart(subTask), execution); // ��¼��ʷ��Ϣ
		}

	}

	/**
	 * ��һ�����񱻽�����ʱ�򣬻����signal()�������Ա�������ת
	 */
	@Override
	public void signal(ActivityExecution execution, String signalName,
			Map<String, ?> parameters) throws Exception {
		signal((ExecutionImpl) execution, signalName, parameters);
	}

	public void signal(ExecutionImpl execution, String signalName,
			Map<String, ?> parameters) throws Exception {

		//�õ�execution��Ӧ�Ľڵ�
		ActivityImpl activity = execution.getActivity();

		//һЩ����
		if (parameters != null) {
			execution.setVariables(parameters);
		}

		//�׳��¼�
		execution.fire(signalName, activity);

		DbSession taskDbSession = EnvironmentImpl
				.getFromCurrent(DbSession.class);
		//taskDbSession.findTaskByExecution(execution);
		List<Task> tasks = taskDbSession.createTaskQuery()
			.executionId(execution.getId()).list();
		
		TaskImpl task = null;
		
		for (Iterator iterator = tasks.iterator(); iterator.hasNext();) {
			TaskImpl ti = (TaskImpl) iterator.next();
			if(ti.getSuperTask() == null){ //�ҳ���Task����
				task = ti;
			}
		}
		
		if (task != null) {
			//��Ϊ�Ѿ�signal����������signal״̬��true��Ϊfalse
			//signal=true��ʾ���ڵȴ�ִ��
			task.setSignalling(false); 
		}
		
		////////////////  �ҳ���һ��ִ�е�Transition ////////////////////
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
			
			//////////////////  ���л�ǩ����   //////////////////////////
			//��������ת֮ǰ�������жϣ��ж�������ɵ������Ƿ��Ѿ������趨ֵ
			Integer passedNumber = (Integer)task.getVariable("passedNumber");

			passedNumber = passedNumber + 1;
			
			task.setVariable("passedNumber", passedNumber);
			
			//�����������ɵ������Ѿ������趨ֵ����������ת
			if(passedNumber >= passNumber){
				if (transition != null) {
					execution.take(transition);
				}
			}else{
				//��δ�ﵽ�趨����������������ȴ���
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
