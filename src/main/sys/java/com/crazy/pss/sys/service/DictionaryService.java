package com.crazy.pss.sys.service;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crazy.pss.common.persistence.ICustomRepository;
import com.crazy.pss.common.service.BaseService;
import com.crazy.pss.sys.dao.DictionaryDao;
import com.crazy.pss.sys.model.Dictionary;

/**
 * 
 * @Description 数据字典服务类
 * 
 * @author crazy/Y
 * @date 2013-4-8
 */
@Service("sys.dictionaryService")
@Transactional(readOnly = true)
public class DictionaryService extends BaseService<Dictionary> {

	@Autowired
	private DictionaryDao dictionaryDao;

	@Override
	protected ICustomRepository getDao() {
		return dictionaryDao;
	}

	public List<Dictionary> search(final Dictionary dict, Integer pageNo, Integer size) {
		Page<Dictionary> dicts = dictionaryDao.findAll(new Specification<Dictionary> (){
			@Override
			public Predicate toPredicate(Root<Dictionary> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				root = query.from(Dictionary.class);
				Path<String> label = root.get("label");
				Path<String> type = root.get("type");
				Predicate plabel = cb.like(label, dict.getLabel());
				Predicate ptype = cb.like(type, dict.getType());
				cb.and(plabel, ptype);
				return cb.and(plabel, ptype);
			}
		}, new PageRequest(pageNo, size));
		return dicts.getContent();
	}
	
}
