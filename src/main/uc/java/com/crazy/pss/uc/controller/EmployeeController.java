package com.crazy.pss.uc.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crazy.pss.common.config.GlobalConfiguration;
import com.crazy.pss.common.utils.Constants;
import com.crazy.pss.common.web.BaseController;
import com.crazy.pss.uc.model.Employee;
import com.crazy.pss.uc.service.EmployeeService;

@Controller("uc.employeeController")
@RequestMapping(value = Constants.ADMIN_PATH+"/uc/employee")
public class EmployeeController extends BaseController{

	@Autowired
	private EmployeeService employeeService;
	
	@ModelAttribute
	public Employee get(@RequestParam(required=false) String id) {
		if (!StringUtils.isEmpty(id)){
			return employeeService.get(id);
		}else{
			return new Employee();
		}
	}
}
