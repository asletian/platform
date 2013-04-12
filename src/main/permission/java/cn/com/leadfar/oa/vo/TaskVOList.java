package cn.com.leadfar.oa.vo;

import java.util.ArrayList;
import java.util.List;

import org.jbpm.api.task.Task;

import cn.com.leadfar.oa.model.WorkEntity;

public class TaskVOList extends ArrayList{
	
	public void addWorkEntityTask(WorkEntity entity,Task task){
		//首先判断当前列表中是否已有这个WorkEntity对象
		TaskVO existVO = null;
		for(int i=0; i<size(); i++){
			TaskVO vo = (TaskVO)get(i);
			if(vo.getWorkEntity().getId() == entity.getId()){
				existVO = vo;
				break;
			}
		}
		if(existVO != null){ //对当前entity对象来说，列表中已经存在一个对应的TaskVO对象
			//则在这个TaskVO中增加一个Task对象即可
			existVO.addTask(task);
		}else{ //如果列表中尚未有对应的TaskVO对象，则创建一个，并加入列表
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
