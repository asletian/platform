package cn.com.leadfar.oa.model;

import java.util.Date;

public class LeaveForm {
	
	public static final String STATUS_NEW = "�½�";
	public static final String STATUS_END = "���";
	
	private int id;
	private String content;
	private Date createTime;
	private Date beginTime;
	private Date endTime;
	private int days;
	private String status;
	
	/**
	 * �����
	 */
	private User leaver;
	
	/**
	 * ������
	 */
//	private User approver; //��������Ѿ�����JBPM����������ȥ����

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public User getLeaver() {
		return leaver;
	}

	public void setLeaver(User leaver) {
		this.leaver = leaver;
	}

//	public User getApprover() {
//		return approver;
//	}
//
//	public void setApprover(User approver) {
//		this.approver = approver;
//	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
