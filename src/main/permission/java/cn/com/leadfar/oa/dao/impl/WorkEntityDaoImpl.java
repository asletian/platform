package cn.com.leadfar.oa.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.com.leadfar.oa.dao.WorkEntityDao;
import cn.com.leadfar.oa.model.EntityProperty;
import cn.com.leadfar.oa.model.WorkEntity;

@Repository("workEntityDao")
public class WorkEntityDaoImpl extends BaseDaoImpl implements WorkEntityDao {

	public void update(WorkEntity entity) {
		//getSession().update(entity);
		WorkEntity oldEntity = (WorkEntity)getSession().load(WorkEntity.class, entity.getId());
		
		//文件附件类型，需要特殊对待
		//对于文件附件类型来说，如果从界面上传过来ID值，意思就是无需更新
		Map<String,EntityProperty> newProps = entity.getProps();
		if(newProps != null){
			for(String propertyName:newProps.keySet()){
				EntityProperty ep = newProps.get(propertyName);
				String propertyType = ep.getPropertyType();
				int propertyId = ep.getId();
				if(propertyId != 0){ //如果属性有ID，表示在数据库中应该有相应的记录
					propertyType = oldEntity.getProps().get(propertyName).getPropertyType();
					if(propertyType.equals("fileValue")){//文件类型，特殊对待
						EntityProperty oldEP = oldEntity.getProps().get(propertyName);
						newProps.put(propertyName, oldEP);
					}
				}
			}
		}
		
		getSession().merge(entity);
	}

	@Override
	public List<WorkEntity> findAllMyCreatedWorkEntities(int userId) {
		
		String hql = "select we from WorkEntity we where we.creator.id = ? order by we.createTime desc";
		
		return getSession().createQuery(hql)
			.setParameter(0, userId)
			.list();
	}

	@Override
	public List<WorkEntity> findAllMyCreatedWorkEntities(int userId, String processKey) {
		
		if(processKey == null || processKey.trim().equals("") || processKey.trim().equals("undefined")){
			return findAllMyCreatedWorkEntities(userId);
		}
		
		String hql = "select we from WorkEntity we where we.creator.id = ? " +
				"and we.processDefinitionId like ? order by we.createTime desc";
		return getSession().createQuery(hql)
			.setParameter(0, userId)
			.setParameter(1, processKey+"-%")
			.list();
	}
}
