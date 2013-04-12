package cn.com.leadfar.oa.service;

import java.util.List;

import cn.com.leadfar.oa.model.Form;

public interface FormService {
	
	/**
	 * ��ӱ�
	 * @param form
	 */
	public void addForm(Form form);
	
	/**
	 * ������Ѿ���Ӧ�������̶����У�������ɾ����
	 * @param formId
	 */
	public void delForm(int formId);
	
	/**
	 * ���±�������
	 * @param form
	 */
	public void updateForm(Form form);
	
	/**
	 * ����ID��ѯ������
	 * @param formId
	 * @return
	 */
	public Form findFormById(int formId);
	
	/**
	 * ��ѯĳ����������������б�
	 * @param formTypeId
	 * @return
	 */
	public List<Form> findAllForms(int formTypeId);
	
	public List<Form> findAllForms();
	
	public Form findFormByKey(String formKey);
}
