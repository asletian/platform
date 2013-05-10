package com.crazy.pss.uc.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crazy.pss.common.persistence.ICustomRepository;
import com.crazy.pss.common.service.BaseService;
import com.crazy.pss.uc.dao.DepartmentDao;
import com.crazy.pss.uc.model.Department;

@Service("uc.departmentService")
@Transactional(readOnly = true)
public class DepartmentService extends BaseService<Department> {

	@Autowired
	private DepartmentDao departmentDao;

	@Override
	protected ICustomRepository<Department, Serializable> getDao() {
		return departmentDao;
	}

	
}
