package cn.com.leadfar.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.leadfar.oa.dao.FormDao;
import cn.com.leadfar.oa.model.Form;
import cn.com.leadfar.oa.service.FormService;

@Service("formService")
public class FormServiceImpl implements FormService {

	@Resource
	private FormDao formDao;
	
	@Override
	public void addForm(Form form) {
		formDao.save(form);
	}

	@Override
	public List<Form> findAllForms(int formTypeId) {
		return formDao.findAllForms(formTypeId);
	}

	@Override
	public List<Form> findAllForms() {
		return formDao.findAll(Form.class);
	}

	@Override
	public void delForm(int formId) {
		formDao.del(findFormById(formId));
	}

	@Override
	public void updateForm(Form form) {
		formDao.update(form);
	}

	@Override
	public Form findFormById(int formId) {
		return formDao.findById(Form.class,formId);
	}

	@Override
	public Form findFormByKey(String formKey) {
		return formDao.findFormByKey(formKey);
	}
}
