package cn.com.leadfar.oa.service;

import java.util.List;

import cn.com.leadfar.oa.model.FormType;

public interface FormTypeService {
	public void addFormType(FormType type);
	public void updateFormType(FormType type);
	public void delFormType(int formTypeId);
	public FormType findFormTypeById(int formTypeId);
	
	/**
	 * 查询所有的顶级表单类型
	 * @return
	 */
	public List<FormType> findAllTopFormTypes();
}
