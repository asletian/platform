package cn.com.leadfar.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.leadfar.oa.dao.RoleDao;
import cn.com.leadfar.oa.dao.UserDao;
import cn.com.leadfar.oa.model.Person;
import cn.com.leadfar.oa.model.Role;
import cn.com.leadfar.oa.model.User;
import cn.com.leadfar.oa.model.UsersRoles;
import cn.com.leadfar.oa.service.UserService;
import cn.com.leadfar.oa.vo.PagerVO;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;
	
	@Resource
	private RoleDao roleDao;
	
	@Override
	public void addRoleToUser(int userId, int roleId) {
		
		UsersRoles ur = new UsersRoles();
		User user = findUserById(userId);
		Role role = roleDao.findById(Role.class, roleId);
		ur.setUser(user);
		ur.setRole(role);
		
		userDao.save(ur);
	}

	@Override
	public void addUser(User user) {
		userDao.save(user);
	}

	@Override
	public void addUser(User user, int[] roleIds) {
		userDao.save(user);
		
		if(roleIds != null){
			//建立用户与角色之间的关联
			for(int roleId:roleIds){
				UsersRoles ur = new UsersRoles();
				ur.setUser(user);
				ur.setRole(roleDao.findById(Role.class, roleId));
				userDao.save(ur);
			}
		}
	}

	@Override
	public void delRoleFromUser(int userId, int roleId) {
		UsersRoles ur = userDao.findUsersRoles(userId, roleId);
		if(ur != null){
			userDao.del(ur);
		}
	}

	@Override
	public void delUser(int userId) {
		userDao.del(findUserById(userId));
	}

	@Override
	public User findUserByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User findUserById(int userId) {
		return userDao.findById(User.class, userId);
	}

	@Override
	public User login(String username, String password) {
		User user = userDao.findByUsername(username);
		if(user == null){
			throw new RuntimeException("用户【"+username+"】不存在");
		}
		
		if(!user.getPassword().equals(password)){
			throw new RuntimeException("密码错误！");
		}
		
		return user;
	}

	@Override
	public PagerVO findPersonUsers(String personName) {
		
		return userDao.findPersonUsers(personName);
	}

	@Override
	public List findPersonsWithUser(String personName) {
		
		return userDao.findPersonsWithUser(personName);
	}

	@Override
	public void updateUser(User user) {
		userDao.update(user);
	}

	public void updateUser(User user,int[] roleIds) {
		userDao.update(user, roleIds);
	}
	@Override
	public List<Integer> findRoleIdsOfUser(int userId) {
		return userDao.findRoleIdsOfUser(userId);
	}

	@Override
	public List findAllUsers() {
		return userDao.findAll(User.class);
	}

	@Override
	public String findUsernamesByRoleId(int roleId) {
		return userDao.findUsernamesByRoleId(roleId);
	}

	@Override
	public String findUsernamesByRoleName(String roleName) {
		return userDao.findUsernamesByRoleName(roleName);
	}

}
