package cn.com.leadfar.oa.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

public class WorkEntity {

	public static final String STATUS_NEW = "�½�";
	public static final String STATUS_END = "���";
	
	private int id;
	
	/**
	 * ������
	 */
	private User creator;
	
	/**
	 * ����ʱ��
	 */
	private Date createTime;
	
	/**
	 * ״̬������ǰҵ�������ת��������
	 */
	private String status;
	
	/**
	 * ҵ������Ӧ�����̶����ID
	 */
	private String processDefinitionId;
	
	/**
	 * ҵ�����Ķ�̬����
	 */
	private Map<String,EntityProperty> props;

	private Set<WorkApprove> workApproves;
	
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
