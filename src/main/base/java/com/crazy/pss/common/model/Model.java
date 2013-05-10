package com.crazy.pss.common.model;

import java.io.Serializable;

/**
 * 
 * @Description 数据层顶级接口，提供了主键生成方法，创建时间，及version记录等
 * 
 * @author crazy/Y
 * @date 2013-4-30
 * @param <K>
 */
public interface Model<K extends Serializable> {

	/**
	 * 获取主键
	 * 
	 * @return 主键
	 */
	K getId();

	/**
	 * 设置主键
	 * 
	 * @param id
	 *            主键
	 */
	void setId(K id);

	/**
	 * 获取version
	 * 
	 * @return
	 */
	Integer getVersion();

	/**
	 * 设置version
	 * 
	 * @param version
	 */
	void setVersion(Integer version);
}
