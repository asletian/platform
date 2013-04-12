package cn.com.leadfar.oa.jbpm;

import java.util.HashSet;

import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.id.DbidGenerator;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.jbpm.pvm.internal.util.Clock;

/**
 * JBPMȱʡ��TaskImplʵ������ȱ�ݣ��޷�֧�ֻ�ǩ���ߵ�����
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
	 * ��һ�����񱻽�����ʱ�������������һ�������񣬲���¼����ʷ��Ϣ
	 * (ֻ��¼���������ʷ��Ϣ)
	 */
	@Override
	public void historyTaskComplete(String outcome) {
		if(this.getSuperTask() == null){
			super.historyTaskComplete(outcome); //��¼��ʷ��Ϣ
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
