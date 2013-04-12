package cn.com.leadfar.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.leadfar.oa.dao.FormDao;
import cn.com.leadfar.oa.model.Form;

@Repository("formDao")
public class FormDaoImpl extends BaseDaoImpl implements FormDao {

	@Override
	public List<Form> findAllForms(int formTypeId) {
		String hql = "select f from Form f where f.type.id = ?";
		return getSession().createQuery(hql).setParameter(0, formTypeId).list();
	}

	@Override
	public Form findFormByKey(String formKey) {
		String hql = "select f from Form f where f.key = ?";
		return (Form)getSession().createQuery(hql)
			.setParameter(0, formKey)
			.uniqueResult();
	}

}
