package cn.com.leadfar.oa.web.actions;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.com.leadfar.oa.annotations.Res;
import cn.com.leadfar.oa.model.Department;

@Controller("departmentAction")
@Scope("prototype")
@Res(name="²¿ÃÅ²Ù×÷",sn="department",orderNumber=30,parentSn="party")
public class DepartmentAction extends PartyAction {
	public Object getModel() {
		
		if(model == null){
			model = new Department();
		}
		
		return model;
	}
}
