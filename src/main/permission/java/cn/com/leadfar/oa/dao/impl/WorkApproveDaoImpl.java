package cn.com.leadfar.oa.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.com.leadfar.oa.dao.WorkApproveDao;
import cn.com.leadfar.oa.model.EntityProperty;
import cn.com.leadfar.oa.model.WorkApprove;
import cn.com.leadfar.oa.model.WorkEntity;

@Repository("workApproveDao")
public class WorkApproveDaoImpl extends BaseDaoImpl implements WorkApproveDao {
	
	@Override
	public void save(WorkApprove workApprove) {
		if(workApprove.getId() == 0){ //添加记录
			getSession().save(workApprove);
		}else{ //更新记录
			WorkApprove oldApprove = (WorkApprove)getSession().load(WorkApprove.class, workApprove.getId());
			
			//文件附件类型，需要特殊对待
			//对于文件附件类型来说，如果从界面上传过来ID值，意思就是无需更新
			Map<String,EntityProperty> newProps = workApprove.getProps();
			if(newProps != null){
				for(String propertyName:newProps.keySet()){
					EntityProperty ep = newProps.get(propertyName);
					String propertyType = ep.getPropertyType();
					int propertyId = ep.getId();
					if(propertyId != 0){ //如果属性有ID，表示在数据库中应该有相应的记录
						propertyType = oldApprove.getProps().get(propertyName).getPropertyType();
						if(propertyType.equals("fileValue")){//文件类型，特殊对待
							EntityProperty oldEP = oldApprove.getProps().get(propertyName);
							newProps.put(propertyName, oldEP);
						}
					}
				}
			}
			
			getSession().merge(workApprove);
		}
	}
	
	@Override
	public WorkApprove findWorkApproveByTaskId(String taskId) {
		String hql = "select a from WorkApprove a where a.taskId = ?";
		return (WorkApprove)getSession().createQuery(hql)
					.setParameter(0, taskId)
					.uniqueResult();
	}

	@Override
	public List<WorkEntity> findAllDealedList(int userId, String processKey) {
		String hql = "select distinct we from WorkApprove wa join wa.workEntity we " +
				"where wa.approver.id = ? and we.processDefinitionId like ?";
		//没有processKey的值，则查询所有的对象
		if(processKey == null || processKey.trim().equals("") || processKey.trim().equals("undefined")){
			processKey = "%";
		}
		return getSession().createQuery(hql)
			.setParameter(0, userId)
			.setParameter(1, processKey+"-%")
			.list();
	}
}
