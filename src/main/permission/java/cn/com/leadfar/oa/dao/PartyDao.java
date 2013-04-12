package cn.com.leadfar.oa.dao;

import cn.com.leadfar.oa.model.Company;
import cn.com.leadfar.oa.model.Party;

public interface PartyDao extends BaseDao{
	public Company findCompany();
	public Party findById(int id);
	public void saveOrUpdate(Party party);
}
