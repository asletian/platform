package com.crazy.pss.uc.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * @Description 部门
 * 
 * @author crazy/Y
 * @date 2013-4-4
 */
@Entity
@DiscriminatorValue(value="DEPARTMENT")
public class Department extends Party {
	
}
