package cn.com.leadfar.oa.dao;

import java.util.List;

import cn.com.leadfar.oa.model.Menu;

public interface MenuDao extends BaseDao {
	public List<Menu> findAllTopMenus();
	public List<Integer> findAllTopMenuIds();
}
