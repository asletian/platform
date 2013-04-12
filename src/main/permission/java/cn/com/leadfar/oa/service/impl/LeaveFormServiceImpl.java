package cn.com.leadfar.oa.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;

import cn.com.leadfar.oa.dao.LeaveFormDao;
import cn.com.leadfar.oa.dao.UserDao;
import cn.com.leadfar.oa.model.ApproveInfo;
import cn.com.leadfar.oa.model.LeaveForm;
import cn.com.leadfar.oa.model.User;
import cn.com.leadfar.oa.service.LeaveFormService;

@Service("leaveFormService")
public class LeaveFormServiceImpl implements LeaveFormService {

	@Resource
	private LeaveFormDao leaveFormDao;
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private ExecutionService executionService;
	
	@Resource
	private TaskService taskService;
	
	@Override
	public void addApproveInfo(String comment, Date approveTime,
			int approverId, int leaveFormId) {
		ApproveInfo ai = new ApproveInfo();
		ai.setComment(comment);
		ai.setApproveTime(approveTime);
		User user = new User();
		user.setId(approverId);
		ai.setApprover(user);
		LeaveForm lf = new LeaveForm();
		lf.setId(leaveFormId);
		ai.setLeaveForm(lf);
		leaveFormDao.save(ai);
	}

	@Override
	public void addLeaveForm(LeaveForm leaveForm) {
		leaveForm.setStatus(LeaveForm.STATUS_NEW);
		leaveForm.setCreateTime(new Date());
		leaveFormDao.save(leaveForm);
	}

	@Override
	public void startProcess(int leaveFormId) {
		
		LeaveForm lf = leaveFormDao.findById(LeaveForm.class, leaveFormId);
		
		Map parameters = new HashMap();
		parameters.put("days", lf.getDays());
		
		ProcessInstance instance = executionService.startProcessInstanceByKey("LeaveForm", parameters, leaveFormId+"");
		
		//得到流程实例当前指向的环节的名称
		String status = instance.findActiveActivityNames().toString();
		
		lf.setStatus(status);
	}

	@Override
	public LeaveForm findLeaveForm(int leaveFormId) {
		return leaveFormDao.findById(LeaveForm.class, leaveFormId);
	}

	@Override
	public List findLeavedForms(int approverId) {
		return leaveFormDao.findLeavedForms(approverId);
	}

	@Override
	public List findLeavingForms(int approverId) {
		
		User user = userDao.findById(User.class, approverId);
		String assignee = user.getUsername();
		
		List<Task> tasks = taskService.findPersonalTasks(assignee);
		
		List returnList = new ArrayList();
		
		for(Task t:tasks){
			Execution execution = executionService.findExecutionById(t.getExecutionId());
			ProcessInstance instance = (ProcessInstance)execution.getProcessInstance();
			String instanceKey = instance.getKey();
			int leaveFormId = Integer.parseInt(instanceKey);
			LeaveForm lf = leaveFormDao.findById(LeaveForm.class, leaveFormId);
			returnList.add(new Object[]{lf,t.getId()});
		}
		
		return returnList;
		
		//return leaveFormDao.findLeavingForms(approverId);
	}

	@Override
	public List findMyLeaveForms(int userId) {
		return leaveFormDao.findMyLeaves(userId);
	}

	@Override
	public Set<String> findNextTransitions(String taskId) {
		
		return taskService.getOutcomes(taskId);
		
	}

	@Override
	public void submitLeaveForm(String taskId,String nextTransitionName) {
		
		Task task = taskService.getTask(taskId);
		Execution execution = executionService.findExecutionById(task.getExecutionId());
		ProcessInstance instance = (ProcessInstance)execution.getProcessInstance();
		String instanceKey = instance.getKey();
		String instanceId = instance.getId();
		int taskLeaveFormId = Integer.parseInt(instanceKey);		
		LeaveForm lf = leaveFormDao.findById(LeaveForm.class, taskLeaveFormId);
		
		taskService.completeTask(taskId,nextTransitionName);
		
		//流转完成之后，重新查询流程实例对象
		ProcessInstance newInstance = executionService.findProcessInstanceById(instanceId);
		if(newInstance != null){ //尚未完成流转
			lf.setStatus(newInstance.findActiveActivityNames().toString());
		}else{
			lf.setStatus(LeaveForm.STATUS_END); //已经完成流转
		}		
		
	}
}
