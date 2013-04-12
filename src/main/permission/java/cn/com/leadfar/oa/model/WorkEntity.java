package cn.com.leadfar.oa.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

public class WorkEntity {

	public static final String STATUS_NEW = "新建";
	public static final String STATUS_END = "完成";
	
	private int id;
	
	/**
	 * 创建者
	 */
	private User creator;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 状态，即当前业务对象流转到哪里了
	 */
	private String status;
	
	/**
	 * 业务对象对应的流程定义的ID
	 */
	private String processDefinitionId;
	
	/**
	 * 业务对象的动态属性
	 */
	private Map<String,EntityProperty> props;

	private Set<WorkApprove> workApproves;
	
	public Object getProperty(String propertyName){
		try {
			//首先从WorkEntity对象获取此变量值
			Object value = PropertyUtils.getProperty(this, propertyName);
			return value;
		} catch (Exception e) {
			if(props != null){
				//如果从WorkEntity中获取不到，再到其动态属性中查找这个变量值
				EntityProperty ep = props.get(propertyName);
				if(ep != null){
					return ep.getValue();
				}
			}
		}
		return null;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public Map<String, EntityProperty> getProps() {
		return props;
	}

	public void setProps(Map<String, EntityProperty> props) {
		this.props = props;
	}

	public Set<WorkApprove> getWorkApproves() {
		return workApproves;
	}

	public void setWorkApproves(Set<WorkApprove> workApproves) {
		this.workApproves = workApproves;
	}
}
