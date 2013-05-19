package com.crazy.pss.common.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @Description 数据层顶级抽象类，提供了主键生成方法，创建时间，及version记录 等
 * 
 * @author crazy/Y
 * @date 2013-4-30
 */
@MappedSuperclass
public abstract class AbstractModel implements Model<String>{
	
	private String id;

	private Date createTime = new Date();

	private Integer version;

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

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Version
	@Column(name = "VERSION", nullable = false)
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof AbstractModel)) {
			return false;
		}
		AbstractModel model = (AbstractModel) o;
		return ObjectUtils.equals(id, model.getId());
	}

	public int hashCode() {
		return ObjectUtils.hashCode(id);
	}

	public String toString() {
		return this.getClass().getName() + "#" + id;
	}
}
