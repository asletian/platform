package com.crazy.pss.sys.dao;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crazy.pss.common.persistence.ICustomRepository;
import com.crazy.pss.sys.model.Dictionary;

/**
 * 
 * @Description 菜单dao接口
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
public interface DictionaryDao extends ICustomRepository<Dictionary, Serializable>{

//	@Query(value="from Dictionary d where d.delFlag='0' order by d.name" )
//	public Page<Dictionary> findAll(Pageable pageable);
}

/**
 * 
 * @Description 自定义菜单dao接口
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
interface DictionaryDaoCustom{
	
}

/**
 * 
 * @Description 自定义菜单dao接口实现类
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
@Repository("sys.dictionaryDao")
class DictionaryDaoImpl implements DictionaryDaoCustom{
	
}
