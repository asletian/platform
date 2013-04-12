package cn.com.leadfar.oa.web.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.com.leadfar.oa.model.EntityProperty;
import cn.com.leadfar.oa.model.WorkApprove;
import cn.com.leadfar.oa.model.WorkEntity;
import cn.com.leadfar.oa.service.FormService;
import cn.com.leadfar.oa.service.WorkApproveService;
import cn.com.leadfar.oa.vo.TaskVOList;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 任务处理有关的Action
 * @author Lee
 *
 */
@Controller("workApproveAction")
@Scope("prototype")
public class WorkApproveAction extends BaseAction implements ModelDriven {

	private WorkApprove approve;
	
	@Resource
	private WorkApproveService workApproveService;
	
	@Resource
	private FormService formService;	
	
	private String processKey;
	
	/**
	 * 当在界面上点击附件下载/删除附件时，传进来一个EntityProperty对象的ID
	 * 
	 */
	private int entityPropertyId;	
	
	@Override
	public Object getModel() {
		if(approve == null){
			approve = new WorkApprove();
		}
		return approve;
	}
	
	/**
	 * 查询所有待办任务列表
	 * @return
	 */
	public String dealingList(){
		
		List<TaskVOList.TaskVO> vos = workApproveService.findAllDealingList(currentUser().getId(), processKey);

		ActionContext.getContext().put("vos", vos);
		
		/**
		 * 如果没有指定流程定义KEY，即列出所有WorkEntity对象来，无法在列表上体现
		 * 各个WorkEntity之间的不同，因此列表上只会显示一些通用的信息
		 */
		if(processKey == null || processKey.trim().equals("")
				|| processKey.equals("undefined")){
			return "dealing_list";
		}		
		
		/**
		 * 如果指定了流程定义KEY，那么，这些WorkEntity都将具有相同的动态属性，在列表上
		 * 可以将这些动态属性也一起列出
		 */
		if(vos != null && !vos.isEmpty()){
			Set<String> propsName = new HashSet<String>(); //所有动态属性的名称列表
			for(int i=0; i<vos.size(); i++){
				propsName.addAll(vos.get(i).getWorkEntity().getProps().keySet());
			}
			List<String> propsNameList = new ArrayList<String>();
			propsNameList.addAll(propsName);
			Collections.sort(propsNameList);
			ActionContext.getContext().put("propsName", propsNameList);
		}		
		
		return "dealing_detail_list";
	}
	
	/**
	 * 打开任务办理界面，如果曾经办理过本任务，则打开界面时，需要把上次办理的信息
	 * 显示在页面上，便于用于修改！
	 * @return
	 */
	public String taskInput(){
		
		//首先，根据taskId查询其对应的审批信息
		String taskId = approve.getTaskId();
		approve = workApproveService.findWorkApproveByTaskId(taskId);
		if(approve == null){ //approve有可能是空的，则是新建
			approve = new WorkApprove();
			approve.setTaskId(taskId);
			
			//本次任务对应的WorkEntity对象
			WorkEntity workEntity = workApproveService.findWorkEntityByTaskId(taskId);			
			approve.setWorkEntity(workEntity);
		}
		
		//查询当前任务对应的outcome transition的名称列表
		Set<String> outcomes = workApproveService.findOutcomeTransitions(taskId);
		ActionContext.getContext().put("outcomes", outcomes);
		
		return "task_input";
	}
	
	/**
	 * 查看审批的详细信息
	 * @return
	 */
	public String viewWorkApprove(){
		//根据taskId查询其对应的审批信息
		String taskId = approve.getTaskId();
		approve = workApproveService.findWorkApproveByTaskId(taskId);
		
		return "view_work_approve";
	}
	
	//保存任务处理的信息
	public String saveTask(){
		
		approve.setApproveTime(new Date());

		//获得提交按钮的取值，可能是“保存”或“保存并[xxx]”
		String nextTransitionName = ((String[])ActionContext.getContext().getParameters().get("submit"))[0];

		//除了保存之外，还需要按照指定的transitionName来提交
		if(nextTransitionName.startsWith("保存并")){
			nextTransitionName = nextTransitionName.substring("保存并[".length(), nextTransitionName.length()-1);
			workApproveService.saveAndSubmitWorkApprove(approve, nextTransitionName);
		}else{
			workApproveService.saveWorkApprove(approve);
		}
		
		return "save_task_success";
	}	
	
	/**
	 * 查询所有已办任务列表
	 * @return
	 */
	public String dealedList(){
		List<WorkEntity> vos = workApproveService.findAllDealedList(currentUser().getId(), processKey);

		ActionContext.getContext().put("vos", vos);
		
		/**
		 * 如果没有指定流程定义KEY，即列出所有WorkEntity对象来，无法在列表上体现
		 * 各个WorkEntity之间的不同，因此列表上只会显示一些通用的信息
		 */
		if(processKey == null || processKey.trim().equals("")
				|| processKey.equals("undefined")){
			return "dealed_list";
		}
		
		/**
		 * 如果指定了流程定义KEY，那么，这些WorkEntity都将具有相同的动态属性，在列表上
		 * 可以将这些动态属性也一起列出
		 */
		if(vos != null && !vos.isEmpty()){
			Set<String> propsName = new HashSet<String>(); //所有动态属性的名称列表
			for(int i=0; i<vos.size(); i++){
				propsName.addAll(vos.get(i).getProps().keySet());
			}
			List<String> propsNameList = new ArrayList<String>();
			propsNameList.addAll(propsName);
			Collections.sort(propsNameList);
			ActionContext.getContext().put("propsName", propsNameList);
		}
		
		return "dealed_detail_list";
	}
	
	/**
	 * 将EntityProperty对象转换为字符串，用于页面呈现
	 * 对于日期，转换为格式：yyyy-MM-dd
	 * 对于文件，则取出其文件名即可
	 * @param prop
	 * @return
	 */
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public String convertEntityPropertyToString(EntityProperty prop){
		if(prop == null || prop.getValue() == null){
			return "";
		}
		Object value = prop.getValue();
		if(value instanceof Date){
			return format.format(value);
		}
		if(value instanceof byte[]){
			return prop.getFileValueFileName();
		}
		return value.toString();
	}	

	public String delEntityProperty(){
		workApproveService.delEntityProperty(entityPropertyId);
		return "del_entity_property_success";
	}
	
	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public int getEntityPropertyId() {
		return entityPropertyId;
	}

	public void setEntityPropertyId(int entityPropertyId) {
		this.entityPropertyId = entityPropertyId;
	}
	
}
