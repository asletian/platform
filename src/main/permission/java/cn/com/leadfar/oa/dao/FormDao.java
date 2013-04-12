package cn.com.leadfar.oa.dao;

import java.util.List;

import cn.com.leadfar.oa.model.Form;

public interface FormDao extends BaseDao {
	public List<Form> findAllForms(int formTypeId);
	public Form findFormByKey(String formKey);
}
