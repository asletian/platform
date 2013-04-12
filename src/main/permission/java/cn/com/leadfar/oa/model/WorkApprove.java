package cn.com.leadfar.oa.model;

import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class WorkApprove {
	private int id;
	
	/**
	 * ����ʱ��
	 */
	private Date approveTime;
	
	/**
	 * ����ID
	 */
	private String taskId;
	
	/**
	 * ��������ʱ����Ӧ�ı�KEY
	 * 
	 * ��JBPM�У�ͨ��һ��Task���󣬿��Եõ�������ı�������һ�����
	 * Task���󱻽�������ͨ����ʷ��¼�޷���������������Ϣ��������Ҫ
	 * ��������ʱ�򣬼�¼�����KEY���Ա��ڲ鿴������ʷ��¼��ʱ���ܹ�
	 * �����������ʾ�˴���������Ϣ��
	 * 
	 * formKey��ֵ�����ڱ���WorkApprove��ʱ���裡
	 */
	private String formKey;
	
	/**
	 * ������
	 */
	private User approver;
	
	/**
	 * ��������ҵ�����
	 */
	private WorkEntity workEntity;
	
	/**
	 * ����ʱ��д�ĸ�������
	 */
	private Map<String,EntityProperty> props;
	
	public Object getProperty(String propertyName){
		try {
			//���ȴ�WorkEntity�����ȡ�˱���ֵ
			Object value = PropertyUtils.getProperty(this, propertyName);
			return value;
		} catch (Exception e) {
			if(props != null){
				//�����WorkEntity�л�ȡ�������ٵ��䶯̬�����в����������ֵ
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
