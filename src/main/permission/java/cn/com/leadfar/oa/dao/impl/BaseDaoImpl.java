package cn.com.leadfar.oa.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import cn.com.leadfar.oa.SystemContext;
import cn.com.leadfar.oa.dao.BaseDao;
import cn.com.leadfar.oa.vo.PagerVO;

public class BaseDaoImpl implements BaseDao {

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public void del(Object entity) {
		getSession().delete(entity);
	}

	@Override
	public List find(Object entity) {
		return getSession().createCriteria(entity.getClass()).add(
				Example.create(entity)).list();
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass) {
		return getSession().createCriteria(entityClass).list();
	}

	@Override
	public <T> T findById(Class<T> entityClass, int id) {
		return (T) getSession().load(entityClass, id);
	}

	@Override
	public PagerVO findPaginated(String query) {
		return findPaginated(query, null);
	}

	@Override
	public PagerVO findPaginated(String query, Object param) {
		return findPaginated(query, new Object[]{param});
	}

	@Override
	public PagerVO findPaginated(String query, Object[] params) {
		return findPaginated(query, params, SystemContext.getOffset(), SystemContext.getPagesize());
	}

	@Override
	public PagerVO findPaginated(String query, int offset, int pagesize) {
		return findPaginated(query, null,offset,pagesize);
	}

	@Override
	public PagerVO findPaginated(String query, Object param, int offset,
			int pagesize) {
		return findPaginated(query, new Object[]{param}, offset, pagesize);
	}

	@Override
	public PagerVO findPaginated(String queryHql, Object[] params, int offset,
			int pagesize) {
		// 首先查询总记录数
		String countHql = getCountHql(queryHql);
		Query query = getSession().createQuery(countHql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		int total = ((Long) query.uniqueResult()).intValue();

		// 查询当前页的数据
		query = getSession().createQuery(queryHql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		query.setFirstResult(offset);
		query.setMaxResults(pagesize);
		List datas = query.list();

		PagerVO pv = new PagerVO();
		pv.setDatas(datas);
		pv.setTotal(total);

		return pv;
	}

	@Override
	public void save(Object entity) {
		getSession().save(entity);
	}

	@Override
	public void update(Object entity) {
		getSession().update(entity);
	}

	/**
	 * select a from Article a where a.title like ? --> select count(*) from
	 * Article a where a.title like ?
	 * 
	 * @param queryHql
	 * @return
	 */
	protected String getCountHql(String queryHql) {
		int index = queryHql.toLowerCase().indexOf("from");
		if (index == -1) {
			throw new RuntimeException("非法的查询语句【" + queryHql + "】");
		}
		return "select count(*) " + queryHql.substring(index);
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
