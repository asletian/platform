package com.crazy.pss.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crazy.pss.common.config.GlobalConfiguration;
import com.crazy.pss.common.utils.Constants;
import com.crazy.pss.common.web.BaseController;

@Controller("login")
@RequestMapping(value = Constants.ADMIN_PATH)
public class LoginController extends BaseController{

//	@ModelAttribute
//	public Employee get(@RequestParam(required=false) String id) {
//		if (!StringUtils.isEmpty(id)){
//			return employeeService.get(id);
//		}else{
//			return new Employee();
//		}
//	}
	
	@RequestMapping("login")
	public String login(){
		return "common/default";
	}
}
