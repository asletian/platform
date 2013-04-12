package cn.com.leadfar.oa.service;

import java.util.List;
import java.util.Set;

import cn.com.leadfar.oa.model.WorkApprove;
import cn.com.leadfar.oa.model.WorkEntity;
import cn.com.leadfar.oa.vo.TaskVOList;

public interface WorkApproveService {
	
	/**
	 * ��ѯ����ת��ĳ���û����ϵ������б�
	 * @param userId �û�ID
	 * @param processKey ����KEY�������ֵΪ�գ����ѯ���е������б�
	 * @return ���ص���Ϣ������WorkEntity�������WorkEntity������ص�����Task����
	 */
	public List<TaskVOList.TaskVO> findAllDealingList(int userId,String processKey);
	
	/**
	 * ��ѯ��ĳ���û�����������������б�
	 * @param userId �û�ID
	 * @param processKey ����KEY�������ֵΪ�գ����ѯ���е������б�
	 * @return 
	 */
	public List findAllDealedList(int userId,String processKey);
	
	/**
	 * ����taskId��ѯ���Ӧ��WorkApprove����
	 * WorkApprove���󱣴��˱�������������ҵ��������Ϣ
	 * @param taskId
	 * @return
	 */
	public WorkApprove findWorkApproveByTaskId(String taskId);
	
	/**
	 * ����taskId��ѯ���Ӧ��WorkEntity����
	 * �����task�Ѿ���������Ӧ���Եõ����ӦWorkApprove��Ȼ��ɵõ���Ӧ��WorkEntity
	 * ���򣬴�taskId�ɵõ�Task��ͨ��Task�ɵõ�execution��ͨ��execution�ɵõ�
	 * ProcessInstance��ͨ��ProcessInstance�ɵõ���processInstanceKey��Ȼ���
	 * �õ���Ӧ��WorkEntity����
	 * @param taskId
	 * @return
	 */
	public WorkEntity findWorkEntityByTaskId(String taskId);
	
	/**
	 * ����WorkApprove
	 * @param workApprove
	 */
	public void saveWorkApprove(WorkApprove workApprove);
	
	/**
	 * ����WorkApprove��������ָ����outcome�뿪��
	 * @param workApprove
	 * @param outcome
	 */
	public void saveAndSubmitWorkApprove(WorkApprove workApprove,String outcome);
	
	/**
	 * ��ѯĳ���������һ����transition�������б�
	 * @param taskId
	 * @return
	 */
	public Set<String> findOutcomeTransitions(String taskId);
	
	/**
	 * ɾ��ĳ����̬����
	 * ��Ҫ��Ϊ��ɾ���ļ�
	 * @param entityPropertyId
	 */
	public void delEntityProperty(int entityPropertyId);	
}
