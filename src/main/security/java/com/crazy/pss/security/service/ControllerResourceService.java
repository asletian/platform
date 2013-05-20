package com.crazy.pss.security.service;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crazy.pss.common.persistence.ICustomRepository;
import com.crazy.pss.common.service.BaseService;
import com.crazy.pss.security.dao.ControllerResourceDao;
import com.crazy.pss.security.model.ControllerResource;

@Service("security.controllerResourceService")
@Transactional(readOnly = false)
public class ControllerResourceService extends BaseService<ControllerResource>{

	private ControllerResourceDao controllerResource;
	
	@Override
	protected ICustomRepository<ControllerResource, Serializable> getDao() {
		return controllerResource;
	}

}
