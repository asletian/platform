package cn.com.leadfar.oa.dao;

import java.util.List;

import cn.com.leadfar.oa.model.WorkEntity;

public interface WorkEntityDao extends BaseDao {
	public List<WorkEntity> findAllMyCreatedWorkEntities(int userId);
	public List<WorkEntity> findAllMyCreatedWorkEntities(int userId,String processKey);
	public void update(WorkEntity entity);
}
