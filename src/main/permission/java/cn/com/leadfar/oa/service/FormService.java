package cn.com.leadfar.oa.service;

import java.util.List;

import cn.com.leadfar.oa.model.Form;

public interface FormService {
	
	/**
	 * 添加表单
	 * @param form
	 */
	public void addForm(Form form);
	
	/**
	 * 如果表单已经被应用于流程定义中，则不允许删除！
	 * @param formId
	 */
	public void delForm(int formId);
	
	/**
	 * 更新表单的内容
	 * @param form
	 */
	public void updateForm(Form form);
	
	/**
	 * 根据ID查询表单对象
	 * @param formId
	 * @return
	 */
	public Form findFormById(int formId);
	
	/**
	 * 查询某个表单类型下面的所有表单
	 * @param formTypeId
	 * @return
	 */
	public List<Form> findAllForms(int formTypeId);
	
	public List<Form> findAllForms();
	
	public Form findFormByKey(String formKey);
}
