package cn.com.leadfar.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.leadfar.oa.dao.MenuDao;
import cn.com.leadfar.oa.model.Menu;

@Repository("menuDao")
public class MenuDaoImpl extends BaseDaoImpl implements MenuDao {

	@Override
	public List<Menu> findAllTopMenus() {
		String hql = "select m from Menu m where m.parent is null";
		
		return getSession().createQuery(hql).list();
	}

	@Override
	public List<Integer> findAllTopMenuIds() {
		String hql = "select m.id from Menu m where m.parent is null";
		return getSession().createQuery(hql).list();
	}

}
