package com.crazy.pss.uc.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.crazy.pss.common.persistence.ICustomRepository;
import com.crazy.pss.uc.model.Position;

/**
 * 
 * @Description 岗位dao接口
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
public interface PositionDao extends ICustomRepository<Position, Serializable>{

	
}

/**
 * 
 * @Description 自定义岗位dao接口
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
interface PositionDaoCustom{
	
}

/**
 * 
 * @Description 自定义岗位dao接口实现类
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
@Repository("uc.positionDao")
class PositionDaoImpl implements PositionDaoCustom{
	
}
