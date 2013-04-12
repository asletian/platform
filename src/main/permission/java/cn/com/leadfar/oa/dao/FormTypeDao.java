package cn.com.leadfar.oa.dao;

import java.util.List;

import cn.com.leadfar.oa.model.FormType;

public interface FormTypeDao extends BaseDao {
	public List<FormType> findAllTopFormTypes();
}
