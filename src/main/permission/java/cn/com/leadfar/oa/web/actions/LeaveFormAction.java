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
	
	//审批意见
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
	 * 打开请假单的申请界面
	 * @return
	 */
	public String addInput(){
		return "add_input";
	}
	
	//添加请假单
	public String add(){
		
		leaveFormService.addLeaveForm(model);
		
		return "add_success";
	}
	
	//启动流程实例
	public String start(){
		
		leaveFormService.startProcess(model.getId());
		
		return "start_success";
	}
	
	//我的请假单列表
	public String myLeaves(){
		
		List lfs = leaveFormService.findMyLeaveForms(currentUser().getId());
		
		ActionContext.getContext().put("leaves", lfs);
		
		return "my_leaves";
	}
	
	//提交请假单
	public String submit(){
		
		//可以拿到transitionName
		
		leaveFormService.submitLeaveForm(taskId,transitionName);
		
		return "submit_success";
	}
	
	//待审请假单列表
	public String leaving(){
		
		List lfs = leaveFormService.findLeavingForms(currentUser().getId());
		ActionContext.getContext().put("leaves", lfs);
		return "leaving";
	}
	
	//打开审批的录入界面
	public String approveInput(){
		return "approve_input";
	}
	
	//执行审批操作
	public String approve(){
		
		leaveFormService.addApproveInfo(comment, new Date(), currentUser().getId(), model.getId());
		
		//通过taskId查询nextTransitions
		Set<String> nextTransitions = leaveFormService.findNextTransitions(taskId);
		
		ActionContext.getContext().put("nextTransitions", nextTransitions);
		
		return "approve_success";
	}
	
	//查询已审请假单列表
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
