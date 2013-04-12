package cn.com.leadfar.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.leadfar.oa.dao.LeaveFormDao;

@Repository("leaveFormDao")
public class LeaveFormDaoImpl extends BaseDaoImpl implements LeaveFormDao {

	@Override
	public List findLeavedForms(int approverId) {
		
		String hql = "select lf from ApproveInfo ai join ai.leaveForm lf join ai.approver ar" +
				" where ar.id = ?";
		
		return getSession().createQuery(hql).setParameter(0, approverId).list();
	}

	@Override
	public List findLeavingForms(int approverId) {

		String hql = "select lf from LeaveForm lf where lf.approver.id = ?";
		
		return getSession().createQuery(hql).setParameter(0, approverId).list();
	}

	@Override
	public List findMyLeaves(int userId) {

		String hql = "select lf from LeaveForm lf where lf.leaver.id = ?";
		
		return getSession().createQuery(hql).setParameter(0, userId).list();
	}

}
