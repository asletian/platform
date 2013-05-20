package com.crazy.pss.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.ArrayUtils;

import com.crazy.pss.common.model.AbstractModel;

/**
 * 
 * @Description Controller类方法资源
 * 
 * @author crazy/Y
 * @date 2013-5-15
 */
@Entity
@Table(name = "T_CONTROLLER_METHOD_OPER")
public class ControllerMethodOper extends AbstractModel{
	
	/**
	 * 方法名，同一种操作可以对应多个方法
	 * 比如：addInput,add,del,update|updateInput
	 */
	private String methodName;
	
	/**
	 * 操作名称
	 * 比如：添加、删除、更新、发布等等等等
	 */
	private String operName;
	
	/**
	 * 操作唯一标识
	 * 比如：ADD,DEL,UPDATE,READ
	 */
	private String operSn;
	
	/**
	 * 操作唯一标识助记符
	 * 比如：ADD,DEL,UPDATE,READ
	 */
	private String operKey;
	
	/**
	 * 操作存储索引，这个索引值必须大于或等于0，并且小于或等于31
	 * 同一个资源所包含的操作中，其索引值是唯一的，不允许重复。
	 */
	private int operIndex;
	
	/**
	 * 添加方法资源
	 * 
	 * @param mn 方法名
	 */
	@Transient
	public void addMethodName(String mn) {
		if(methodName == null) {
			this.methodName = mn;
		} else {
			String[] methodNames = this.methodName.split("\\|");
			if(!ArrayUtils.contains(methodNames, mn)) {
				this.methodName = this.methodName + "|" + mn;
			}
		}
	}
	
	@Column(name = "METHOD_NAME", length = 50)
	public String getMethodName() {
		return methodName;
	}
	
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	@Column(name = "OPER_NAME", length = 50)
	public String getOperName() {
		return operName;
	}
	
	public void setOperName(String operName) {
		this.operName = operName;
	}
	
	@Column(name = "OPER_SN", length = 50)
	public String getOperSn() {
		return operSn;
	}
	
	@Column(name = "OPER_KEY", length = 50)
	public String getOperKey() {
		return operKey;
	}
	
	public void setOperSn(String sn) {
		this.operSn = sn;
	}

	public void setOperKey(String operKey) {
		this.operKey = operKey;
	}

	@Column(name = "OPER_INDEX")
	public int getOperIndex() {
		return operIndex;
	}
	
	public void setOperIndex(int operIndex) {
		this.operIndex = operIndex;
	}
}
