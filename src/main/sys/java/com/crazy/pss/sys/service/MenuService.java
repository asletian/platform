package com.crazy.pss.sys.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crazy.pss.common.persistence.ICustomRepository;
import com.crazy.pss.common.service.BaseService;
import com.crazy.pss.sys.dao.MenuDao;
import com.crazy.pss.sys.model.Menu;

/**
 * 
 * @Description 菜单服务类
 * 
 * @author crazy/Y
 * @date 2013-4-8
 */
@Service("sys.menuService")
@Transactional(readOnly = true)
public class MenuService extends BaseService<Menu> {

	@Autowired
	private MenuDao menuDao;

	@Override
	protected ICustomRepository<Menu, Serializable> getDao() {
		return menuDao;
	}

	public List<Menu> searchByParent(String parentId){
		return menuDao.findByParent(parentId);
	}
	
	public List<Menu> searchByTarget(String target){
		return menuDao.findByTarget(target);
	}
	
	public List<Menu> searchTop(){
		Menu parent = menuDao.findRoot();
		return searchByParent(parent.getId());
	}
	
	public List<Menu> searchAllExcludeRoot(){
		return menuDao.findAllExcludeRoot();
	}
}
