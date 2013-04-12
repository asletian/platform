package cn.com.leadfar.oa.web.actions;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.com.leadfar.oa.annotations.Oper;
import cn.com.leadfar.oa.annotations.Res;
import cn.com.leadfar.oa.model.Role;
import cn.com.leadfar.oa.service.RoleService;
import cn.com.leadfar.oa.vo.PagerVO;
import cn.com.leadfar.oa.web.JSONUtils;

import com.opensymphony.xwork2.ModelDriven;

@Controller("roleAction")
@Scope("prototype")
@Res(name="½ÇÉ«²Ù×÷",sn="role",orderNumber=70,parentSn="security")
public class RoleAction extends BaseAction implements ModelDriven{
	
	@Resource
	private RoleService roleService;
	
	private Role role;
	
	private String sSearch;
	
	@Override
	public Object getModel() {
		if(role == null){
			role = new Role();
		}
		return role;
	}
	
	@Oper
	public String execute(){
		return "index";
	}
	public void list(){
		PagerVO pv = roleService.findAllRoles(sSearch);
		Map map = new HashMap();
		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);
	}
	@Oper
	public String addInput(){
		return "add_input";
	}
	@Oper
	public String updateInput(){
		role = roleService.findRoleById(role.getId());
		return "update_input";
	}
	@Oper
	public String add(){
		roleService.addRole(role);
		return "add_success";
	}
	@Oper
	public String update(){
		roleService.updateRole(role);
		return "update_success";
	}
	@Oper
	public void del(){
		roleService.delRole(role.getId());
	}
	public String getSSearch() {
		return sSearch;
	}
	public void setSSearch(String search) {
		sSearch = search;
	}
}
