package com.crazy.pss.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.crazy.pss.common.model.AbstractModel;
import com.crazy.pss.common.security.SysResource;

/**
 * 
 * @Description Controller类资源
 * 
 * @author crazy/Y
 * @date 2013-5-16
 */
@Entity
@Table(name = "T_CONTROLLER_RESOURCE")
public class ControllerResource extends AbstractModel implements SysResource{
	
	/**
	 * 资源所对应的Action类名（可能有多个，用"|"隔开）
	 */
	private String className;
	
	/**
	 * 资源的命名，比如：
	 * 组织机构管理、公文管理
	 */
	private String name;
	
	/**
	 * 资源的唯一标识
	 * 比如：
	 * org,document等等
	 */
	private String sn;
	
	/**
	 * 父资源的唯一标识
	 * 比如：
	 * party
	 */
	private String parentSn;
	
	/**
	 * 排序号
	 */
	private int orderNumber;
	
	/**
	 * 资源所包含的操作
	 * key是操作的唯一标识（助记符），比如：UPDATE,DEL,READ,CREATE
	 */
	private Map<String,ControllerMethodOper> opers;

	private ControllerResource parent;
	
	private Set<ControllerResource> children;
	
	@Column(name = "CLASS_NAME", length = 100)
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name = "NAME", length = 100, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SN", length = 50, unique = true)
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@OneToMany
	@MapKey(name = "operKey")
	public Map<String, ControllerMethodOper> getOpers() {
		return opers;
	}

	public void setOpers(Map<String, ControllerMethodOper> opers) {
		this.opers = opers;
	}

	@Column(name = "ORDER_NUMBER")
	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	public ControllerResource getParent() {
		return parent;
	}

	public void setParent(ControllerResource parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy = "parent")
	public Set<ControllerResource> getChildren() {
		return children;
	}

	public void setChildren(Set<ControllerResource> children) {
		this.children = children;
	}

	@Column(name = "PARENT_SN", length = 50)
	public String getParentSn() {
		return parentSn;
	}

	public void setParentSn(String parentSn) {
		this.parentSn = parentSn;
	}
	
	@Override
	@Transient
	public String getResourceId() {
		return getId();
	}

	@Override
	@Transient
	public int[] getOpersIndex() {
		if(opers != null){
			Collection<ControllerMethodOper> amo = opers.values();
			int[] opersIndex = new int[amo.size()];
			int i = 0;
			for(ControllerMethodOper o:amo){
				opersIndex[i++] = o.getOperIndex();
			}
			return opersIndex;
		}
		return null;
	}

	@Override
	@Transient
	public String getResourceType() {
		return "ControllerResource";
	}

	@Override
	@Transient
	public List<SysResource> getChildrenResource() {
		if(children != null){
			List<SysResource> cs = new ArrayList<SysResource>();
			cs.addAll(children);
			return cs;
		}
		return null;
	}

	@Override
	@Transient
	public int getOperIndexBySn(String operSn) {
		return opers.get(operSn).getOperIndex();
	}
	
	/**
	 * 根据方法名来得到对应的操作的唯一标识
	 * @param methodName
	 * @return
	 */
	@Transient
	public String getOperSnByMethodName(String methodName){
		if(opers == null){
			return null;
		}
		for( ControllerMethodOper amo : opers.values() ){
			if(contains(amo.getMethodName(),methodName)){
				return amo.getOperSn();
			}
		}
		return null;
	}
	
	/**
	 * amoMethodName字符串是否包含有methodName字符串
	 * amoMethodName字符串的格式是：
	 * add|addInput|...
	 * @param amoMethodName
	 * @param methodName
	 * @return
	 */
	@Transient
	private boolean contains(String amoMethodName,String methodName){
		//按照"|"隔开方法名
		String[] allMethods = amoMethodName.split("\\|");
		for(String m:allMethods){
			if(m.equals(methodName)){
				return true;
			}
		}
		return false;
	}

	@Transient
	public void addClassName(String clsName){
		if(this.className == null){
			this.className = clsName;
		}else{
			this.className = this.className + "|" + clsName;
		}
	}
	
	@Transient
	public void addControllerMethodOper(String methodName,String operName,String operSn,int operIndex){
		if(opers == null){
			opers = new HashMap<String, ControllerMethodOper>();
		}
		ControllerMethodOper amo = opers.get(operSn);
		if(amo != null){
			amo.addMethodName(methodName);
		}else{
			
			//首先，判断索引值是否已存在，如果已经存在，则抛出异常，不允许重复
			for(ControllerMethodOper o:opers.values()){
				if(o.getOperIndex() == operIndex){
					throw new RuntimeException("针对资源【"+name+"】的操作" +
							"【"+o.getOperName()+"】已经和索引【"+o.getOperIndex()+"】绑定，" +
									"无法再次把一个新的操作【"+operName+"】绑定到该索引值");
				}
			}
			
			amo = new ControllerMethodOper();
			amo.setMethodName(methodName);
			amo.setOperName(operName);
			amo.setOperIndex(operIndex);
			amo.setOperSn(operSn);
			opers.put(operSn, amo);
		}
	}
}
