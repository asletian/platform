package com.crazy.pss.common.persistence;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.crazy.pss.common.utils.Reflections;

/**
 * 
 * @Description 对SimpleJpaRepository的实现进行扩展提供全局持久化方法
 * 
 * @see http://static.springsource.org/spring-data/data-jpa/docs/current/reference/html/repositories.html#repositories.definition
 * @author crazy/Y
 * @date 2013-4-6
 * @param <T>	实体对象
 * @param <ID>	实体对象ID
 */
public class CustomRepository<T, ID extends Serializable>
			extends SimpleJpaRepository<T, ID> implements ICustomRepository<T, ID>{

	private final EntityManager entityManager;

	// There are two constructors to choose from, either can be used.
	public CustomRepository(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		// This is the recommended method for accessing inherited class dependencies.
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Session getSession() {
		return (Session) entityManager.getDelegate();
	}

	public DetachedCriteria createDetachedCriteria(Criterion... criterions) {
		DetachedCriteria dc = DetachedCriteria.forClass(Reflections.getClassGenricType(getClass()));
		for (Criterion c : criterions) {
			dc.add(c);
		}
		return dc;
	}
	
	public Query getQuery(String jpql){
		return entityManager.createQuery(jpql);
	}
	
}
