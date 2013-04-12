package cn.com.leadfar.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.leadfar.oa.dao.UserDao;
import cn.com.leadfar.oa.model.Person;
import cn.com.leadfar.oa.model.Role;
import cn.com.leadfar.oa.model.User;
import cn.com.leadfar.oa.model.UsersRoles;
import cn.com.leadfar.oa.vo.PagerVO;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public User findByUsername(String username) {
		return (User)getSession().createQuery("select u from User u where u.username = ?")
			.setParameter(0, username)
			.uniqueResult();
	}

	@Override
	public List<Role> findRoles(int userId) {
		
		String hql = "select r from UsersRoles ur join ur.role r join ur.user u " +
				"where u.id = ?";
		return getSession().createQuery(hql)
			.setParameter(0, userId).list();
	}

	@Override
	public List<User> findUsers(int roleId) {
		String hql = "select u from UsersRoles ur join ur.role r join ur.user u " +
			"where r.id = ?";
		return getSession().createQuery(hql)
			.setParameter(0, roleId).list();
	}

	@Override
	public UsersRoles findUsersRoles(int userId, int roleId) {
		String hql = "select ur from UsersRoles ur join ur.role r join ur.user u " +
			"where u.id = ? and r.id = ? ";
		return (UsersRoles)getSession().createQuery(hql)
			.setParameter(0, userId)
			.setParameter(1, roleId)
			.uniqueResult();
	}

	@Override
	public PagerVO findPersonUsers(String personName) {
		String hql = "select p.id,p.name,pt.name,u.username from Person p left join p.parent pt left join p.user u where p.name like ?";
		return findPaginated(hql,"%"+personName+"%");
	}

	@Override
	public List findPersonsWithUser(String personName) {
		String hql = "select p.id,p.name from Person p join p.user u where p.name like ?";
		return getSession().createQuery(hql).setParameter(0, "%"+personName+"%").list();
	}

	@Override
	public List<Integer> findRoleIdsOfUser(int userId) {
		String hql = "select r.id from UsersRoles ur join ur.role r join ur.user u " +
				"where u.id = ?";
		return getSession().createQuery(hql)
			.setParameter(0, userId)
			.list();
	}

	@Override
	public void update(User user, int[] roleIds) {
		getSession().update(user);
		
		String hql = "select ur from UsersRoles ur join ur.user u where u.id = ?";
		List<UsersRoles> urs = getSession().createQuery(hql).setParameter(0, user.getId())
			.list();
		
		for(UsersRoles ur:urs){
			getSession().delete(ur);
		}
		
		//建立新的关联
		if(roleIds != null){
			for(int roleId:roleIds){
				UsersRoles ur = new UsersRoles();
				ur.setUser(user);
				ur.setRole((Role)getSession().load(Role.class, roleId));
				getSession().save(ur);
			}
		}
		
	}

	@Override
	public String findUsernamesByRoleId(int roleId) {
		
		String hql = "select u.username from UsersRoles ur join ur.user u join ur.role r " +
				"where r.id = ?";
		
		List<String> usernamesList = getSession().createQuery(hql).setParameter(0, roleId).list();
		
		//[zy,qe,ss]
		String usernamesString = usernamesList.toString();
		usernamesString = usernamesString.substring(1, usernamesString.length()-1);
		return usernamesString;
	}

	@Override
	public String findUsernamesByRoleName(String roleName) {
		String hql = "select u.username from UsersRoles ur join ur.user u join ur.role r " +
		"where r.name = ?";

		List<String> usernamesList = getSession().createQuery(hql).setParameter(0, roleName).list();
		
		//[zy,qe,ss]
		String usernamesString = usernamesList.toString();
		usernamesString = usernamesString.substring(1, usernamesString.length()-1);
		return usernamesString;
	}

}
