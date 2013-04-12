package com.crazy.pss.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

/**
 * 
 * @Description 数据字典
 * 
 * @author crazy/Y
 * @date 2013-4-8
 */
@Entity
@Table(name = "T_SYS_DICTIONARY")
public class Dictionary{

	private String id;		// 编号
	private String label;	// 值
	private String value;	// 键
	private String type;	// 类型
	private String desciption;// 描述
	private Integer sort;	// 排序

	@Id
	@Column(length=32, name="ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="LABEL")
	@Length(min=1, max=100)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Column(name="VALUE")
	@Length(min=1, max=100)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name="TYPE")
	@Length(min=1, max=100)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="DESCIPTION")
	@Length(min=0, max=100)
	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

	@Column(name="SORT")
	@NotNull
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}