package cn.com.leadfar.oa.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.com.leadfar.oa.model.LeaveForm;

public interface LeaveFormService {
	
	/**
	 * 保存请假单
	 */
	public void addLeaveForm(LeaveForm leaveForm);
	
	/**
	 * 启动某个请假单对应的流程实例！
	 * @param leaveFormId
	 */
	public void startProcess(int leaveFormId);
	
	/**
	 * 查询我的请假单
	 * @param userId
	 * @return
	 */
	public List findMyLeaveForms(int userId);
	
	/**
	 * 提交请假单
	 * @param userId
	 * @param leaveFormId
	 */
	public void submitLeaveForm(String taskId,String nextTransitionName);
	
	/**
	 * 查询待审请假单列表
	 * @param approverId
	 * @return
	 */
	public List findLeavingForms(int approverId);
	
	/**
	 * 查询某个Task出发，可选的那些离开的Transition的名称列表
	 * @param taskId
	 * @return
	 */
	public Set<String> findNextTransitions(String taskId);
	
	/**
	 * 查询特定的请假单对象
	 * @param leaveFormId
	 * @return
	 */
	public LeaveForm findLeaveForm(int leaveFormId);
	
	/**
	 * 保存审批信息
	 * @param comment
	 * @param approveTime
	 * @param approverId
	 * @param leaveFormId
	 */
	public void addApproveInfo(String comment,Date approveTime,int approverId,int leaveFormId);
	
	/**
	 * 查询已审的请假单列表
	 * @param approverId
	 * @return
	 */
	public List findLeavedForms(int approverId);
}
