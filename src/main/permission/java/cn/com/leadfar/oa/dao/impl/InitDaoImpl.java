package cn.com.leadfar.oa.dao.impl;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import cn.com.leadfar.oa.dao.InitDao;
import cn.com.leadfar.oa.model.ACL;
import cn.com.leadfar.oa.model.ActionResource;
import cn.com.leadfar.oa.model.Menu;
import cn.com.leadfar.oa.model.Person;
import cn.com.leadfar.oa.model.Principal;
import cn.com.leadfar.oa.model.Role;
import cn.com.leadfar.oa.model.SysResource;
import cn.com.leadfar.oa.model.User;
import cn.com.leadfar.oa.model.UsersRoles;

@Repository("initDao")
public class InitDaoImpl extends BaseDaoImpl implements InitDao {

	@Override
	public void addInitAdmin() {

		getSession().flush();
		getSession().clear();
		
		//添加超级管理员（人员及登录帐号）
		Person adminPerson = new Person();
		adminPerson.setName("超级管理员");
		getSession().save(adminPerson);
		User adminUser = new User();
		adminUser.setUsername("admin");
		adminUser.setPassword("admin");
		adminUser.setPerson(adminPerson);
		getSession().save(adminUser);
		
		//创建系统管理员角色
		Role adminRole = new Role();
		adminRole.setName("系统管理员");
		getSession().save(adminRole);
		
		//创建普通员工角色
		Role commonRole = new Role();
		commonRole.setName("普通员工");
		getSession().save(commonRole);
		
		//让管理员用户拥有系统管理员角色
		UsersRoles ur1 = new UsersRoles();
		ur1.setRole(adminRole);
		ur1.setUser(adminUser);
		getSession().save(ur1);
		
		//让管理员用户拥有普通员工角色
		UsersRoles ur2 = new UsersRoles();
		ur2.setRole(commonRole);
		ur2.setUser(adminUser);
		getSession().save(ur2);
		
		//查询出：系统管理相关菜单、安全相关操作、组织机构相关操作
		String hql = "select r from cn.com.leadfar.oa.model.SysResource r " +
				"where r.sn = 'system' or r.sn='security' or r.sn = 'party'";
		List<SysResource> res = getSession().createQuery(hql).list();
		for(SysResource r:res){
			saveAllPermitAcl(adminRole,r); //将这些权限全部赋予系统管理员角色
			saveAllPermitAcl(adminUser,r); //将这些权限全部赋予管理员用户
		}
		
		//个人办公与工作流相关的菜单项资源
		hql = "select r from cn.com.leadfar.oa.model.SysResource r " +
			"where r.sn = 'personal' or r.sn='workflow'";
		res = getSession().createQuery(hql).list();
		for(SysResource r:res){
			saveAllPermitAcl(commonRole,r); //将这些权限全部赋予普通员工角色
		}
//		hql = "select ar from ActionResource ar where ar.sn = 'security' or ar.sn = 'party'";
//		List<ActionResource> ars = getSession().createQuery(hql).list();
//		for(SysResource ar:ars){
//			saveAllPermitAcl(u,ar);
//		}
	}
	
	/**
	 * 将某个资源对象（包括其所有子对象）的所有操作的允许权限授予principal对象
	 * @param principal
	 * @param resource
	 */
	private void saveAllPermitAcl(Principal principal,SysResource resource){
		ACL acl = new ACL();
		acl.setPrincipalType(principal.getPrincipalType());
		acl.setPrincipalId(principal.getPrincipalId());
		acl.setResourceType(resource.getResourceType());
		acl.setResourceId(resource.getResourceId());
		acl.setAclState(-1); //全部操作都允许
		acl.setAclTriState(0); //全部操作都不继承
		getSession().save(acl);
		
		List<SysResource> children = resource.getChildrenResource();
		if(children != null){
			for(SysResource c:children){
				saveAllPermitAcl(principal,c);
			}
		}
	}

}
