package com.crazy.pss.security.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.crazy.pss.common.persistence.ICustomRepository;
import com.crazy.pss.security.model.ControllerResource;

/**
 * 
 * @Description 控制器资源dao
 * 
 * @author crazy/Y
 * @date 2013-5-17
 */
public interface ControllerResourceDao extends ICustomRepository<ControllerResource, Serializable>{

}

/**
 * 
 * @Description 控制器资源dao接口
 * 
 * @author crazy/Y
 * @date 2013-5-17
 */
interface ControllerResourceDaoCustom {
	
}

/**
 * 
 * @Description 控制器资源dao实现
 * 
 * @author crazy/Y
 * @date 2013-5-17
 */
@Repository("security.controllerResourceDao")
class ControllerResourceDaoImpl implements ControllerResourceDaoCustom {
	
}