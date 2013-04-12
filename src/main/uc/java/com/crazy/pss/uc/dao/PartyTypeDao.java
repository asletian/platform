package com.crazy.pss.uc.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.crazy.pss.common.persistence.ICustomRepository;
import com.crazy.pss.uc.model.PartyType;

/**
 * 
 * @Description Party类型dao接口
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
public interface PartyTypeDao extends ICustomRepository<PartyType, Serializable>{

	
}

/**
 * 
 * @Description 自定义Party类型dao接口
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
interface PartyTypeDaoCustom{
	
}

/**
 * 
 * @Description 自定义Party类型dao接口实现类
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
@Repository("uc.partyTypeDao")
class PartyTypeDaoImpl implements PartyTypeDaoCustom{
	
}
