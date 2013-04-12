package com.crazy.pss.uc.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.crazy.pss.common.persistence.ICustomRepository;
import com.crazy.pss.uc.model.Department;

/**
 * 
 * @Description 部门dao接口
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
public interface DepartmentDao extends ICustomRepository<Department, Serializable>{

	
}

/**
 * 
 * @Description 自定义部门dao接口
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
interface DepartmentDaoCustom{
	
}

/**
 * 
 * @Description 自定义部门dao接口实现类
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
@Repository("uc.departmentDao")
class DepartmentDaoImpl implements DepartmentDaoCustom{
	
}
