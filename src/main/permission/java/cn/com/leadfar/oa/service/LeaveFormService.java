package cn.com.leadfar.oa.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.com.leadfar.oa.model.LeaveForm;

public interface LeaveFormService {
	
	/**
	 * ������ٵ�
	 */
	public void addLeaveForm(LeaveForm leaveForm);
	
	/**
	 * ����ĳ����ٵ���Ӧ������ʵ����
	 * @param leaveFormId
	 */
	public void startProcess(int leaveFormId);
	
	/**
	 * ��ѯ�ҵ���ٵ�
	 * @param userId
	 * @return
	 */
	public List findMyLeaveForms(int userId);
	
	/**
	 * �ύ��ٵ�
	 * @param userId
	 * @param leaveFormId
	 */
	public void submitLeaveForm(String taskId,String nextTransitionName);
	
	/**
	 * ��ѯ������ٵ��б�
	 * @param approverId
	 * @return
	 */
	public List findLeavingForms(int approverId);
	
	/**
	 * ��ѯĳ��Task��������ѡ����Щ�뿪��Transition�������б�
	 * @param taskId
	 * @return
	 */
	public Set<String> findNextTransitions(String taskId);
	
	/**
	 * ��ѯ�ض�����ٵ�����
	 * @param leaveFormId
	 * @return
	 */
	public LeaveForm findLeaveForm(int leaveFormId);
	
	/**
	 * ����������Ϣ
	 * @param comment
	 * @param approveTime
	 * @param approverId
	 * @param leaveFormId
	 */
	public void addApproveInfo(String comment,Date approveTime,int approverId,int leaveFormId);
	
	/**
	 * ��ѯ�������ٵ��б�
	 * @param approverId
	 * @return
	 */
	public List findLeavedForms(int approverId);
}
