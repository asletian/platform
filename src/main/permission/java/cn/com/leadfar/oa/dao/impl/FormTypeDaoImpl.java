package cn.com.leadfar.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.leadfar.oa.dao.FormTypeDao;
import cn.com.leadfar.oa.model.FormType;

@Repository("formTypeDao")
public class FormTypeDaoImpl extends BaseDaoImpl implements FormTypeDao {

	@Override
	public List<FormType> findAllTopFormTypes() {
		String hql = "select t from FormType t where t.parent is null";
		return getSession().createQuery(hql).list();
	}

}
