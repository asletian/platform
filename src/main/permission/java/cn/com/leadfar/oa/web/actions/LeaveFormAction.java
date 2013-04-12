package cn.com.leadfar.oa.web.actions;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.com.leadfar.oa.model.LeaveForm;
import cn.com.leadfar.oa.service.LeaveFormService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("leaveFormAction")
@Scope("prototype")
public class LeaveFormAction extends BaseAction implements ModelDriven{
	
	@Resource
	private LeaveFormService leaveFormService;
	
	private LeaveForm model;
	
	//�������
	private String comment;
	
	private String taskId;
	
	private String transitionName;
	
	@Override
	public Object getModel() {
		if(model == null){
			model = new LeaveForm();
		}
		return model;
	}

	/**
	 * ����ٵ����������
	 * @return
	 */
	public String addInput(){
		return "add_input";
	}
	
	//�����ٵ�
	public String add(){
		
		leaveFormService.addLeaveForm(model);
		
		return "add_success";
	}
	
	//��������ʵ��
	public String start(){
		
		leaveFormService.startProcess(model.getId());
		
		return "start_success";
	}
	
	//�ҵ���ٵ��б�
	public String myLeaves(){
		
		List lfs = leaveFormService.findMyLeaveForms(currentUser().getId());
		
		ActionContext.getContext().put("leaves", lfs);
		
		return "my_leaves";
	}
	
	//�ύ��ٵ�
	public String submit(){
		
		//�����õ�transitionName
		
		leaveFormService.submitLeaveForm(taskId,transitionName);
		
		return "submit_success";
	}
	
	//������ٵ��б�
	public String leaving(){
		
		List lfs = leaveFormService.findLeavingForms(currentUser().getId());
		ActionContext.getContext().put("leaves", lfs);
		return "leaving";
	}
	
	//��������¼�����
	public String approveInput(){
		return "approve_input";
	}
	
	//ִ����������
	public String approve(){
		
		leaveFormService.addApproveInfo(comment, new Date(), currentUser().getId(), model.getId());
		
		//ͨ��taskId��ѯnextTransitions
		Set<String> nextTransitions = leaveFormService.findNextTransitions(taskId);
		
		ActionContext.getContext().put("nextTransitions", nextTransitions);
		
		return "approve_success";
	}
	
	//��ѯ������ٵ��б�
	public String leaved(){
		return "leaved";
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTransitionName() {
		return transitionName;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}
}
