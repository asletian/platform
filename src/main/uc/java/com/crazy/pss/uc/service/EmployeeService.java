package com.crazy.pss.uc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crazy.pss.common.persistence.ICustomRepository;
import com.crazy.pss.common.service.BaseService;
import com.crazy.pss.uc.dao.EmployeeDao;
import com.crazy.pss.uc.model.Employee;

@Service("uc.employeeService")
@Transactional(readOnly = true)
public class EmployeeService extends BaseService<Employee> {

	@Autowired
	private EmployeeDao employeeDao;

	@Override
	protected ICustomRepository getDao() {
		return employeeDao;
	}
	
}
