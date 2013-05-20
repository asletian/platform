package com.crazy.pss.uc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crazy.pss.common.persistence.FilterRules;
import com.crazy.pss.common.utils.Constants;
import com.crazy.pss.common.web.BaseController;
import com.crazy.pss.sys.utils.SpringUtils;
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
	
	/**
	 * 查询人员
	 * @return
	 */
	@RequestMapping({"list",""})
	public String list(Model model,FilterRules filterRules,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize){
		Page<Employee> page = employeeService.searchPage(filterRules.getRules(), pageNo, pageSize, new Sort("id"));
		model.addAttribute("page",page);
		return "modules/uc/employeeList";
	}
	
	/**
	 * 跳转人员添加、修改
	 * @param model
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(Model model,Employee employee){
		if(employee.getId() == null){
			employee = new Employee();
		}else{
			employee = employeeService.get(employee.getId().toString());
		}
		model.addAttribute("employee", employee);
		return "modules/uc/employeeForm";
	}
	
	/**
	 * 人员保存、修改
	 * @param model
	 * @param employee
	 * @param filterRules
	 * @return
	 */
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request,Model model){
		Employee employee = new Employee();
		SpringUtils.bind_date(request, employee, "yyyy-MM-dd");
		if(!beanValidator(model, employee)) {
			return form(model, employee);
		}
		employeeService.save(employee);
		addMessage(model, "保存人员" + employee.getName() + "成功");
		return "redirect:"+Constants.ADMIN_PATH+"/uc/employee/list";
	}
	
	/**
	 * 人员删除
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete")
	public String delete(Model model,String id){
		Employee employee = employeeService.get(id);
		if(null == employee){
			addMessage(model,"数据不存在");
		}else{
			employeeService.delete(id);
			addMessage(model, "删除人员" + employee.getName() + "成功");
		}
		return "redirect:"+Constants.ADMIN_PATH+"/uc/employee/list";
		
	}
	
}
