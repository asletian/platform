package com.crazy.pss.uc.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crazy.pss.common.persistence.ICustomRepository;
import com.crazy.pss.uc.model.Employee;

/**
 * 
 * @Description 人员dao接口
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
public interface EmployeeDao extends ICustomRepository<Employee, Serializable>{

	@Query("from Employee m ")
	public List<Employee> findAllEmployee();
	
}

/**
 * 
 * @Description 自定义人员dao接口
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
interface EmployeeDaoCustom{
	
}

/**
 * 
 * @Description 自定义人员dao接口实现类
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
@Repository("uc.employeeDao")
class EmployeeDaoImpl implements EmployeeDaoCustom{
	
}
