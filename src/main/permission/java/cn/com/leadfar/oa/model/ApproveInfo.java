package cn.com.leadfar.oa.model;

import java.util.Date;

/**
 * 请假单的审批信息
 * @author Lee
 *
 */
public class ApproveInfo {
	private int id;
	private User approver;
	private LeaveForm leaveForm;
	private Date approveTime;
	private String comment;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getApprover() {
		return approver;
	}
	public void setApprover(User approver) {
		this.approver = approver;
	}
	public LeaveForm getLeaveForm() {
		return leaveForm;
	}
	public void setLeaveForm(LeaveForm leaveForm) {
		this.leaveForm = leaveForm;
	}
	public Date getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
