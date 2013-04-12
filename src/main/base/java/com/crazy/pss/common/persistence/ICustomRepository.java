package com.crazy.pss.common.persistence;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 
 * @Description 持久化接口标准
 * 
 * @author crazy/Y
 * @date 2013-4-6
 * @param <T>	实体类
 * @param <ID>	实体类ID
 */
public interface ICustomRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>{

	public EntityManager getEntityManager();
	
	public Session getSession();
	
	public DetachedCriteria createDetachedCriteria(Criterion... criterions);
	
	public Query getQuery(String jpql);
}
