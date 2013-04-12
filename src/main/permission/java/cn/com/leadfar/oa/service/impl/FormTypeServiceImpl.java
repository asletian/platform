package cn.com.leadfar.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.leadfar.oa.dao.FormTypeDao;
import cn.com.leadfar.oa.model.FormType;
import cn.com.leadfar.oa.service.FormTypeService;

@Service("formTypeService")
public class FormTypeServiceImpl implements FormTypeService {

	@Resource
	private FormTypeDao formTypeDao;
	
	@Override
	public void addFormType(FormType type) {
		formTypeDao.save(type);
	}

	@Override
	public void delFormType(int formTypeId) {
		formTypeDao.del(formTypeDao.findById(FormType.class, formTypeId));
	}

	@Override
	public List<FormType> findAllTopFormTypes() {
		return formTypeDao.findAllTopFormTypes();
	}

	@Override
	public FormType findFormTypeById(int formTypeId) {
		return formTypeDao.findById(FormType.class, formTypeId);
	}

	@Override
	public void updateFormType(FormType type) {
		formTypeDao.update(type);
	}

}
