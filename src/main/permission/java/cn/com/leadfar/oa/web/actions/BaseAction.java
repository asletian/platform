package cn.com.leadfar.oa.web.actions;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import cn.com.leadfar.oa.service.AclService;
import cn.com.leadfar.oa.vo.LoginInfoVO;

public class BaseAction {
	
	@Resource
	private AclService aclService;
	
	/**
	 * 从HTTP SESSION中取出已登录的User对象信息
	 * @return
	 */
	public LoginInfoVO currentUser(){
		return (LoginInfoVO)ServletActionContext.getRequest().getSession().getAttribute("login");
	}
	
	public boolean permit(String resourceSn,String operSn){
		try{
			return aclService.hasPermission(currentUser().getId(), resourceSn, operSn);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
