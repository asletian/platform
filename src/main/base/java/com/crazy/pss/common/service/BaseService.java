package com.crazy.pss.common.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.crazy.pss.common.persistence.ICustomRepository;
import com.crazy.pss.common.utils.Reflections;

/**
 * 
 * @Description service基类, 可添加一般service中需要的共用的方法
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
public abstract class BaseService<T> {

	protected abstract ICustomRepository<T, Serializable> getDao();
	
	@Transactional(readOnly = false)
	public void save(T t){
		getDao().save(t);
	}
	
	public T get(String id){
		return getDao().findOne(id);
	}
	
	public List<T> searchAll(){
		return getDao().findAll();
	}
	
	public void delete(T t){
		getDao().delete(t);
	}
	
	public T searchUnique(final String name, final String value) { 
		T t = getDao().findOne(new Specification<T> (){
			
			@Override
			public Predicate toPredicate(Root<T> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				root = (Root<T>) query.from(getEntityClass());
				Path<String> pname = root.get(name);
				return cb.equal(pname, value);
			}
		});
		return t;
	}
	
	public List<T> searchBy(final String name, final String value) {
		List<T> ts = getDao().findAll(new Specification<T> (){

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				root = (Root<T>) query.from(getEntityClass());
				Path<String> pname = root.get(name);
				return cb.equal(pname, value);
			}
			
		});
		return ts;
	}
	
	private Class<T> getEntityClass(){
		return Reflections.getClassGenricType(getClass());
	}
}
