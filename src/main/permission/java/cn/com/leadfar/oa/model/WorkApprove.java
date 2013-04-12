package cn.com.leadfar.oa.model;

import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class WorkApprove {
	private int id;
	
	/**
	 * 审批时间
	 */
	private Date approveTime;
	
	/**
	 * 任务ID
	 */
	private String taskId;
	
	/**
	 * 本任务处理时，对应的表单KEY
	 * 
	 * 在JBPM中，通过一个Task对象，可以得到其关联的表单，但是一旦这个
	 * Task对象被结束，则通过历史记录无法获得其关联表单的信息，所以需要
	 * 在审批的时候，记录这个表单KEY，以便在查看审批历史记录的时候，能够
	 * 根据这个表单显示此次审批的信息！
	 * 
	 * formKey的值，是在保存WorkApprove的时候赋予！
	 */
	private String formKey;
	
	/**
	 * 审批者
	 */
	private User approver;
	
	/**
	 * 被审批的业务对象
	 */
	private WorkEntity workEntity;
	
	/**
	 * 审批时填写的各种属性
	 */
	private Map<String,EntityProperty> props;
	
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
	public Date getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public User getApprover() {
		return approver;
	}
	public void setApprover(User approver) {
		this.approver = approver;
	}
	public WorkEntity getWorkEntity() {
		return workEntity;
	}
	public void setWorkEntity(WorkEntity workEntity) {
		this.workEntity = workEntity;
	}
	public Map<String, EntityProperty> getProps() {
		return props;
	}
	public void setProps(Map<String, EntityProperty> props) {
		this.props = props;
	}
	public String getFormKey() {
		return formKey;
	}
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
}
