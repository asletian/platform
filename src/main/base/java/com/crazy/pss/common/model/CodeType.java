package com.crazy.pss.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

/**
 * 
 * @Description 
 * 
 * @author crazy/Y
 * @date 2013-7-19 上午11:24:42
 */
@Entity
@Table(name = "T_SYS_CODE_TYPE")
public class CodeType extends AbstractModel{

	private String name;
	private String desc;
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "DESC")
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
