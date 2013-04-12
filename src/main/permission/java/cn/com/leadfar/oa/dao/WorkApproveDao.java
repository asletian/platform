package cn.com.leadfar.oa.dao;

import java.util.List;

import cn.com.leadfar.oa.model.WorkApprove;
import cn.com.leadfar.oa.model.WorkEntity;

public interface WorkApproveDao extends BaseDao {
	
	/**
	 * ��������WorkApprove����
	 * @param workApprove
	 */
	public void save(WorkApprove workApprove);
	
	/**
	 * ��������ID����ѯ��������Ӧ��WorkApprove��Ϣ
	 * @param taskId
	 * @return
	 */
	public WorkApprove findWorkApproveByTaskId(String taskId);
	
	/**
	 * ��ѯ�������Ѱ������WorkEntity����
	 * @param userId
	 * @param processKey
	 * @return
	 */
	public List<WorkEntity> findAllDealedList(int userId, String processKey);
}
