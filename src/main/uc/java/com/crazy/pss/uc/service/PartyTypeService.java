package com.crazy.pss.uc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crazy.pss.common.persistence.ICustomRepository;
import com.crazy.pss.common.service.BaseService;
import com.crazy.pss.uc.dao.PartyTypeDao;
import com.crazy.pss.uc.model.PartyType;

@Service("uc.partyTypeService")
@Transactional(readOnly = true)
public class PartyTypeService extends BaseService<PartyType> {

	@Autowired
	private PartyTypeDao partyTypeDao;

	@Override
	protected ICustomRepository getDao() {
		return partyTypeDao;
	}
	
}
