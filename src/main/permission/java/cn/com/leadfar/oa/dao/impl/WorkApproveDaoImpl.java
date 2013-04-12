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
		if(workApprove.getId() == 0){ //��Ӽ�¼
			getSession().save(workApprove);
		}else{ //���¼�¼
			WorkApprove oldApprove = (WorkApprove)getSession().load(WorkApprove.class, workApprove.getId());
			
			//�ļ��������ͣ���Ҫ����Դ�
			//�����ļ�����������˵������ӽ����ϴ�����IDֵ����˼�����������
			Map<String,EntityProperty> newProps = workApprove.getProps();
			if(newProps != null){
				for(String propertyName:newProps.keySet()){
					EntityProperty ep = newProps.get(propertyName);
					String propertyType = ep.getPropertyType();
					int propertyId = ep.getId();
					if(propertyId != 0){ //���������ID����ʾ�����ݿ���Ӧ������Ӧ�ļ�¼
						propertyType = oldApprove.getProps().get(propertyName).getPropertyType();
						if(propertyType.equals("fileValue")){//�ļ����ͣ�����Դ�
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
		//û��processKey��ֵ�����ѯ���еĶ���
		if(processKey == null || processKey.trim().equals("") || processKey.trim().equals("undefined")){
			processKey = "%";
		}
		return getSession().createQuery(hql)
			.setParameter(0, userId)
			.setParameter(1, processKey+"-%")
			.list();
	}
}
