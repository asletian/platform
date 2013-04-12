package cn.com.leadfar.oa.dao;

import java.util.List;

public interface LeaveFormDao extends BaseDao {
	public List findMyLeaves(int userId);
	public List findLeavingForms(int approverId);
	public List findLeavedForms(int approverId);
}
