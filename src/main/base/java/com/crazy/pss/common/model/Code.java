package com.crazy.pss.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @Description 
 * 
 * @author crazy/Y
 * @date 2013-7-19 上午11:24:42
 */
@Entity
@Table(name="T_SYS_CODE")
public class Code extends AbstractModel{

	private String name;
	private String value;
	private Integer index = 0;
	private Boolean enable;

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "VALUE")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "INDEX")
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	@Column(name = "ENABLE")
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	
}
