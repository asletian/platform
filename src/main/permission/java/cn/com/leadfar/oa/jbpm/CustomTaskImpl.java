package cn.com.leadfar.oa.jbpm;

import java.util.HashSet;

import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.id.DbidGenerator;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.jbpm.pvm.internal.util.Clock;

/**
 * JBPM缺省的TaskImpl实现类有缺陷，无法支持会签决策的需求
 * @author Lee
 *
 */
public class CustomTaskImpl extends TaskImpl {

	@Override
	public TaskImpl createSubTask() {
	    DbSession dbSession = EnvironmentImpl.getFromCurrent(DbSession.class);
	    TaskImpl subTask = createTask(); //dbSession.createTask();
	    if (subTasks == null) {
	      subTasks = new HashSet<TaskImpl>();
	    }
	    addSubTask(subTask);
	    return subTask;
	}

	/**
	 * 当一个任务被结束的时候，如果此任务是一个子任务，不记录其历史信息
	 * (只记录主任务的历史信息)
	 */
	@Override
	public void historyTaskComplete(String outcome) {
		if(this.getSuperTask() == null){
			super.historyTaskComplete(outcome); //记录历史信息
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
