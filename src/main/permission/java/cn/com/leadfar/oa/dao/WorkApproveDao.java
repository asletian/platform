package cn.com.leadfar.oa.dao;

import java.util.List;

import cn.com.leadfar.oa.model.WorkApprove;
import cn.com.leadfar.oa.model.WorkEntity;

public interface WorkApproveDao extends BaseDao {
	
	/**
	 * 保存或更新WorkApprove对象
	 * @param workApprove
	 */
	public void save(WorkApprove workApprove);
	
	/**
	 * 根据任务ID，查询这个任务对应的WorkApprove信息
	 * @param taskId
	 * @return
	 */
	public WorkApprove findWorkApproveByTaskId(String taskId);
	
	/**
	 * 查询出所有已办理过的WorkEntity对象
	 * @param userId
	 * @param processKey
	 * @return
	 */
	public List<WorkEntity> findAllDealedList(int userId, String processKey);
}
