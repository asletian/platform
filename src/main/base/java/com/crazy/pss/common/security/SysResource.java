package com.crazy.pss.common.security;

import java.util.List;

/**
 * 
 * @Description 系统资源接口
 * 
 * @author crazy/Y
 * @date 2013-5-15
 */
public interface SysResource {
	
	/**
	 * 获取资源id
	 * 
	 * @return 资源id
	 */
	public String getResourceId();
	
	/**
	 * 获取资源类型
	 * 
	 * @return 资源类型
	 */
	public String getResourceType();
	
	/**
	 * 获取资源操作的所有索引
	 * 
	 * @return 资源操作索引
	 */
	public int[] getOpersIndex();
	
	/**
	 * 通过资源操作唯一标识获取资源操作索引
	 * 
	 * @param operSn 资源操作唯一标识
	 * @return 资源操作索引
	 */
	public int getOperIndexBySn(String operSn);
	
	/**
	 * 获取资源列表
	 * 
	 * @return 子资源列表
	 */
	public List<SysResource> getChildrenResource();
}
