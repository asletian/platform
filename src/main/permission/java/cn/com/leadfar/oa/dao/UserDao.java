package cn.com.leadfar.oa.dao;

import java.util.List;

import cn.com.leadfar.oa.model.Person;
import cn.com.leadfar.oa.model.Role;
import cn.com.leadfar.oa.model.User;
import cn.com.leadfar.oa.model.UsersRoles;
import cn.com.leadfar.oa.vo.PagerVO;

public interface UserDao extends BaseDao {
	public User findByUsername(String username);
	public List<Role> findRoles(int userId);
	public List<User> findUsers(int roleId);
	public UsersRoles findUsersRoles(int userId,int roleId);
	public PagerVO findPersonUsers(String personName);
	public List findPersonsWithUser(String personName);
	public List<Integer> findRoleIdsOfUser(int userId);
	public void update(User user,int[] roleIds);
	public String findUsernamesByRoleId(int roleId);
	public String findUsernamesByRoleName(String roleName);
}
