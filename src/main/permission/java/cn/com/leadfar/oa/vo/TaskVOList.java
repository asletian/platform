package cn.com.leadfar.oa.vo;

import java.util.ArrayList;
import java.util.List;

import org.jbpm.api.task.Task;

import cn.com.leadfar.oa.model.WorkEntity;

public class TaskVOList extends ArrayList{
	
	public void addWorkEntityTask(WorkEntity entity,Task task){
		//�����жϵ�ǰ�б����Ƿ��������WorkEntity����
		TaskVO existVO = null;
		for(int i=0; i<size(); i++){
			TaskVO vo = (TaskVO)get(i);
			if(vo.getWorkEntity().getId() == entity.getId()){
				existVO = vo;
				break;
			}
		}
		if(existVO != null){ //�Ե�ǰentity������˵���б����Ѿ�����һ����Ӧ��TaskVO����
			//�������TaskVO������һ��Task���󼴿�
			existVO.addTask(task);
		}else{ //����б�����δ�ж�Ӧ��TaskVO�����򴴽�һ�����������б�
			TaskVO vo = new TaskVO();
			vo.setWorkEntity(entity);
			vo.addTask(task);
			add(vo);
		}
	}
	
	public class TaskVO {
		private WorkEntity workEntity;
		private List<Task> tasks;
		
		public void addTask(Task task){
			if(tasks == null){
				tasks = new ArrayList<Task>();
			}
			tasks.add(task);
		}
		
		public WorkEntity getWorkEntity() {
			return workEntity;
		}
		public void setWorkEntity(WorkEntity workEntity) {
			this.workEntity = workEntity;
		}
		public List<Task> getTasks() {
			return tasks;
		}
		public void setTasks(List<Task> tasks) {
			this.tasks = tasks;
		}
		
	}
}
