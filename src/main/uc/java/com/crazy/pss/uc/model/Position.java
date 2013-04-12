package com.crazy.pss.uc.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * @Description 职位
 * 
 * @author crazy/Y
 * @date 2013-4-4
 */
@Entity
@DiscriminatorValue(value="POSITION")
public class Position extends Party {

}
