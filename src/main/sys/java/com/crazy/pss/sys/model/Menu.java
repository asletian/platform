package com.crazy.pss.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import com.crazy.pss.common.utils.Constants;

/**
 * 
 * @Description 菜单
 * 
 * @author crazy/Y
 * @date 2013-4-8
 */
@Entity
@Table(name = "T_SYS_MENU")
public class Menu {

	private String id;
	private Menu parent;	// 父级菜单
	private String parentIds; // 所有父级编号
	private String name; 	// 名称
	private String href; 	// 链接
	private String target; 	// 目标（ mainFrame、_blank、_self、_parent、_top）
	private String icon; 	// 图标
	private Integer sort; 	// 排序
	private String isShow; 	// 是否在菜单中显示（1：显示；0：不显示）
	private String delFlag; // 删除标记（0：正常；1：删除）
	
	public Menu(){
		delFlag = Constants.DEL_FLAG_NORMAL;
		isShow = Constants.SHOW;
	}
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_ID")
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	@Length(min=1, max=255)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Column(name="NAME")
	@Length(min=1, max=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="HREF")
	@Length(min=0, max=255)
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Column(name="TARGET")
	@Length(min=0, max=20)
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	@Column(name="ICON")
	@Length(min=0, max=100)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Column(name="SORT")
	@NotNull
	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Column(name="IS_SHOW")
	@Length(min=1, max=1)
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	@Column(name="DEL_FLAG")
	@Length(min=1, max=1)
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}