package com.crazy.pss.common.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.crazy.pss.common.config.GlobalConfiguration;
import com.crazy.pss.common.persistence.DynamicSpecifications;
import com.crazy.pss.common.persistence.FilterGroup;
import com.crazy.pss.common.persistence.FilterRule;
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
	
	public T get(String id) {
		return getDao().findOne(id);
	}
	
	public List<T> searchAll() {
		return getDao().findAll();
	}
	
	public List<T> searchAll(Sort sort) {
		return getDao().findAll(sort);
	}
	
	@Transactional(readOnly = false)
	public void delete(T t){
		getDao().delete(t);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		getDao().delete(id);
	}
	
	public T searchUnique(final String name, final String value) { 
		T t = getDao().findOne(new Specification<T> (){
			
			public Predicate toPredicate(Root<T> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> pname = root.get(name);
				return cb.equal(pname, value);
			}
		});
		return t;
	}
	
	public List<T> searchBy(final String name, final String value) {
		List<T> ts = getDao().findAll(new Specification<T> (){

			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Path<String> pname = root.get(name);
				return cb.equal(pname, value);
			}
			
		});
		return ts;
	}
	
	public List<T> searchBy(Collection<FilterRule> rules, Sort sort) {
		if(sort == null) {
			if(hasAttribute("createTime")) {
				sort = new Sort("createTime");
			}
		}
		return getDao().findAll(DynamicSpecifications.bySearchRule(rules, getEntityClass()), sort);
	}
	
	public List<T> searchBy(Collection<FilterRule> rules) {
		return getDao().findAll(DynamicSpecifications.bySearchRule(rules, getEntityClass()));
	}
	
	public Page<T> searchPage(Collection<FilterRule> rules, Integer pageNo, Integer pageSize, Sort sort) {
		if(pageNo == null) pageNo = GlobalConfiguration.getInteger("pageNo", 0);
		if(pageSize == null) pageSize = GlobalConfiguration.getInteger("pageSize", 10);
		if(sort == null) {
			if(hasAttribute("createTime")) {
				sort = new Sort("createTime");
			}
		}
		
		return getDao().findAll(
				DynamicSpecifications.bySearchRule(rules, getEntityClass()),
				new PageRequest(pageNo, pageSize, sort));
	}
	
	public Page<T> searchPage(FilterGroup group, Integer pageNo, Integer pageSize, Sort sort) {
		if(pageNo == null) pageNo = GlobalConfiguration.getInteger("pageNo", 0);
		if(pageSize == null) pageSize = GlobalConfiguration.getInteger("pageSize", 10);
		if(sort == null) {
			if(hasAttribute("createTime")) {
				sort = new Sort("createTime");
			}
		}
		
		return getDao().findAll(
				DynamicSpecifications.bySearchGroup(group, getEntityClass()),
				new PageRequest(pageNo, pageSize, sort));
	}
	
	private Class<T> getEntityClass(){
		return Reflections.getClassGenricType(getClass());
	}
	
	private boolean hasAttribute(String attrName) {
		return Reflections.hasAttribute(attrName, getEntityClass());
	}
}
