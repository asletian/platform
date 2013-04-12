package com.crazy.pss.uc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crazy.pss.common.persistence.ICustomRepository;
import com.crazy.pss.common.service.BaseService;
import com.crazy.pss.uc.dao.PositionDao;
import com.crazy.pss.uc.model.Position;

@Service("uc.positionService")
@Transactional(readOnly = true)
public class PositionService extends BaseService<Position> {

	@Autowired
	private PositionDao PositionDao;

	@Override
	protected ICustomRepository getDao() {
		return PositionDao;
	}
	
}
