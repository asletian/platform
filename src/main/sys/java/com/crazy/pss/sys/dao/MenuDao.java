package com.crazy.pss.sys.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crazy.pss.common.persistence.ICustomRepository;
import com.crazy.pss.common.utils.Constants;
import com.crazy.pss.sys.model.Menu;

/**
 * 
 * @Description 菜单dao接口
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
public interface MenuDao extends ICustomRepository<Menu, Serializable>{

	@Query("from Menu m where m.parent.id = ?1")
	public List<Menu> findByParent(String parentId);
	
	@Query("from Menu m where m.isShow = '" + Constants.SHOW + "' and m.target = ?1")
	public List<Menu> findByTarget(String target);
	
	@Override
	@Query("from Menu m where m.isShow = '" + Constants.SHOW + "'")
	public List<Menu> findAll();
	
	@Query("from Menu m where m.parent != null")
	public List<Menu> findAllExcludeRoot();
	
	@Query("from Menu m where m.parent = null")
	public Menu findRoot();
	
}

/**
 * 
 * @Description 自定义菜单dao接口
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
interface MenuDaoCustom{
	
}

/**
 * 
 * @Description 自定义菜单dao接口实现类
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
@Repository("sys.menuDao")
class MenuDaoImpl implements MenuDaoCustom{
	
}
