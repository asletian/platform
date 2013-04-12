package cn.com.leadfar.oa.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;

import cn.com.leadfar.oa.dao.UserDao;
import cn.com.leadfar.oa.dao.WorkApproveDao;
import cn.com.leadfar.oa.dao.WorkEntityDao;
import cn.com.leadfar.oa.model.EntityProperty;
import cn.com.leadfar.oa.model.User;
import cn.com.leadfar.oa.model.WorkApprove;
import cn.com.leadfar.oa.model.WorkEntity;
import cn.com.leadfar.oa.service.ProcessService;
import cn.com.leadfar.oa.service.WorkApproveService;
import cn.com.leadfar.oa.vo.TaskVOList;

@Service("workApproveService")
public class WorkApproveServiceImpl implements WorkApproveService {

	@Resource
	private UserDao userDao;
	
	@Resource
	private TaskService taskService;
	
	@Resource
	private ExecutionService executionService;
	
	@Resource
	private WorkEntityDao workEntityDao;
	
	@Resource
	private WorkApproveDao workApproveDao;
	
	@Resource
	private ProcessService processService;
	
	@Override
	public List findAllDealedList(int userId, String processKey) {
		return workApproveDao.findAllDealedList(userId, processKey);
	}

	@Override
	public List<TaskVOList.TaskVO> findAllDealingList(int userId, String processKey) {
		
		User user = userDao.findById(User.class, userId);
		String username = user.getUsername();
		
		//查询我的Task对象列表
		List<Task> myTasks = taskService.createTaskQuery().assignee(username).list();
		
		//查询我所属组的Task对象列表
		List<Task> groupTasks = taskService.findGroupTasks(username);
		
		myTasks.addAll(groupTasks);
		
		//构建TaskListVO对象
		TaskVOList taskVOList = new TaskVOList();
		for (Iterator iterator = myTasks.iterator(); iterator.hasNext();) {
			Task task = (Task) iterator.next();
			String executionId = task.getExecutionId();
			Execution execution = executionService.findExecutionById(executionId);
			
			//传过来了processKey的值
			if(processKey != null && !processKey.trim().equals("") && !processKey.trim().equals("undefined")){
				//则必须是对应流程定义下面的Task才需显示出来
				if(execution.getProcessDefinitionId().startsWith(processKey)){
					ProcessInstance processInstance = (ProcessInstance)execution.getProcessInstance();
					int workEntityId = Integer.parseInt(processInstance.getKey());
					WorkEntity entity = workEntityDao.findById(WorkEntity.class, workEntityId);
					taskVOList.addWorkEntityTask(entity, task);
				}				
			}else{ //无需过滤，列出所有的Task来
				ProcessInstance processInstance = (ProcessInstance)execution.getProcessInstance();
				int workEntityId = Integer.parseInt(processInstance.getKey());
				WorkEntity entity = workEntityDao.findById(WorkEntity.class, workEntityId);
				taskVOList.addWorkEntityTask(entity, task);
			}
		}
		
		return taskVOList;
	}

	@Override
	public WorkApprove findWorkApproveByTaskId(String taskId) {
		return workApproveDao.findWorkApproveByTaskId(taskId);
	}

	@Override
	public WorkEntity findWorkEntityByTaskId(String taskId) {
		
		WorkApprove approve = workApproveDao.findWorkApproveByTaskId(taskId);
		if(approve == null){ //尚未审批
			Task task = taskService.getTask(taskId);
			String executionId = task.getExecutionId();
			String processInstanceKey = executionService.findExecutionById(executionId).getProcessInstance()
				.getKey();
			int workEntityId = Integer.parseInt(processInstanceKey);
			return workEntityDao.findById(WorkEntity.class, workEntityId);
		}
		
		return approve.getWorkEntity();
	}

	@Override
	public void saveWorkApprove(WorkApprove workApprove) {
		//根据taskId获得其对应的表单KEY，将这个表单KEY存储在WorkApprove对象中
		String formKey = processService.findTaskFormKeyByTaskId(workApprove.getTaskId());
		workApprove.setFormKey(formKey);
		
		workApproveDao.save(workApprove);
	}

	@Override
	public void saveAndSubmitWorkApprove(WorkApprove workApprove, String outcome) {
		saveWorkApprove(workApprove);
		
		//提交到下一个环节
		taskService.completeTask(workApprove.getTaskId(), outcome);
		
		WorkEntity workEntity = workEntityDao.findById(WorkEntity.class, workApprove.getWorkEntity().getId());
		
		//提交之后的流程实例对象
		ProcessInstance instance = executionService.createProcessInstanceQuery()
			.processDefinitionId(workEntity.getProcessDefinitionId())
			.processInstanceKey(""+workEntity.getId())
			.uniqueResult();
		
		if(instance == null){ //已流转结束
			workEntity.setStatus(WorkEntity.STATUS_END);
		}else{
			workEntity.setStatus(instance.findActiveActivityNames().toString());
		}
		
	}

	@Override
	public Set<String> findOutcomeTransitions(String taskId) {
		
		Set<String> outcomes = taskService.getOutcomes(taskId);
		
		return outcomes;
		
	}

	@Override
	public void delEntityProperty(int entityPropertyId) {
		workApproveDao.del(workApproveDao.findById(EntityProperty.class, entityPropertyId));
	}
}
