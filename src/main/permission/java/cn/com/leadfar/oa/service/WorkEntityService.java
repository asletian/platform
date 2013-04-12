package cn.com.leadfar.oa.service;

import java.util.List;

import cn.com.leadfar.oa.model.EntityProperty;
import cn.com.leadfar.oa.model.WorkEntity;

public interface WorkEntityService {
	
	/**
	 * 保存工作任务对象
	 * @param entity 
	 */
	public void addWorkEntity(WorkEntity entity);
	
	/**
	 * 查询某个用户（一般是当前登录用户）所创建的WorkEntity对象
	 * @return
	 */
	public List<WorkEntity> findAllMyCreatedWorkEntities(int userId);
	
	/**
	 * 查询某个用户（一般是当前登录用户）所创建的某种类型的WorkEntity对象
	 * @param userId
	 * @param processKey
	 * @return
	 */
	public List<WorkEntity> findAllMyCreatedWorkEntities(int userId,String processKey);
	
	/**
	 * 更新WorkEntity对象
	 * @param entity
	 */
	public void updateWorkEntity(WorkEntity entity);
	
	/**
	 * 删除WorkEntity对象
	 * @param entityId
	 */
	public void delWorkEntity(int entityId);
	
	/**
	 * 根据ID查询WorkEntity对象
	 * @param entityId
	 * @return
	 */
	public WorkEntity findWorkEntityById(int entityId);
	
	/**
	 * 启动新的流程实例
	 * @param entityId
	 */
	public void startProcess(int entityId);
	
	/**
	 * 查找某个动态属性
	 * 主要是为了文件下载
	 * @param entityPropertyId
	 * @return
	 */
	public EntityProperty findEntityPropertyById(int entityPropertyId);
	
	/**
	 * 删除某个动态属性
	 * 主要是为了删除文件
	 * @param entityPropertyId
	 */
	public void delEntityProperty(int entityPropertyId);
	
}
