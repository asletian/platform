package cn.com.leadfar.oa.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskService;
import org.springframework.stereotype.Service;

import cn.com.leadfar.oa.dao.UserDao;
import cn.com.leadfar.oa.dao.WorkEntityDao;
import cn.com.leadfar.oa.model.EntityProperty;
import cn.com.leadfar.oa.model.WorkEntity;
import cn.com.leadfar.oa.service.WorkEntityService;

@Service("workEntityService")
public class WorkEntityServiceImpl implements WorkEntityService {

	@Resource
	private WorkEntityDao workEntityDao;
	
	@Resource
	private ExecutionService executionService;
	
	@Resource
	private TaskService taskService;
	
	@Resource
	private UserDao userDao;
	
	@Override
	public void addWorkEntity(WorkEntity entity) {
		
		entity.setCreateTime(new Date());
		entity.setStatus(WorkEntity.STATUS_NEW);
		
		workEntityDao.save(entity);
	}

	@Override
	public void delWorkEntity(int entityId) {
		workEntityDao.del(workEntityDao.findById(WorkEntity.class, entityId));
	}

	@Override
	public List<WorkEntity> findAllMyCreatedWorkEntities(int userId) {
		return workEntityDao.findAllMyCreatedWorkEntities(userId);
	}

	@Override
	public List<WorkEntity> findAllMyCreatedWorkEntities(int userId, String processKey) {
		return workEntityDao.findAllMyCreatedWorkEntities(userId, processKey);
	}

	@Override
	public void updateWorkEntity(WorkEntity entity) {
		workEntityDao.update(entity);
	}

	@Override
	public WorkEntity findWorkEntityById(int entityId) {
		
		return workEntityDao.findById(WorkEntity.class, entityId);
	}

	@Override
	public void startProcess(int entityId) {
		//根据entityId找到WorkEntity对象
		WorkEntity entity = workEntityDao.findById(WorkEntity.class, entityId);
		
		//根据WorkEntity对象得到processDefinitionId
		String processDefinitionId = entity.getProcessDefinitionId();
		
		//根据processDefinitionId和entityId，创建ProcessInstance
		ProcessInstance processInstance = executionService.startProcessInstanceById(
				processDefinitionId, entityId+"");
		
		//启动ProcessInstance之后，将自动流转到下一个环节，因此取出ProcessInstance现在
		//指向的ActivityName作为WorkEntity的status
		entity.setStatus(processInstance.findActiveActivityNames().toString());
		
	}

	@Override
	public void delEntityProperty(int entityPropertyId) {
		workEntityDao.del(workEntityDao.findById(EntityProperty.class, entityPropertyId));
	}

	@Override
	public EntityProperty findEntityPropertyById(int entityPropertyId) {
		return workEntityDao.findById(EntityProperty.class, entityPropertyId);
	}


}
