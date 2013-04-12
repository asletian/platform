package com.crazy.pss.core.uuid;

import java.io.Serializable;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * 
 * Hibernate映射文件的id生成器,生成UUID
 * 
 */
public class IDGenerator implements IdentifierGenerator {
	
	public Serializable generate(SessionImplementor session, Object obj) {
		return com.crazy.pss.core.db.IDGenerator.createId();
	}
	
	
}