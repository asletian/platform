package cn.com.leadfar.oa.web.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.leadfar.oa.model.Form;
import cn.com.leadfar.oa.service.FormService;
import cn.com.leadfar.oa.service.ProcessService;
import cn.com.leadfar.oa.web.JSONUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("formAction")
@Scope("prototype")
public class FormAction extends BaseAction implements ModelDriven{
	
	private Form form;
	
	@Resource
	private FormService formService;
	
	@Resource
	private ProcessService processService;	
	
	/**
	 * 在工作表单上呈现添加、更新界面时，需要呈现动态的表单的内容
	 */
	private String processDefinitionId;
	
	/**
	 * 在审批界面上，需要显示动态表单的内容
	 */
	private String taskId;	
	
	@Override
	public Object getModel() {
		if(form == null){
			form = new Form();
		}
		return form;
	}
	
	/**
	 * 打开添加表单的界面
	 * @return
	 */
	public String addInput(){
		return "form_add_input";
	}
	
	public String add(){
		formService.addForm(form);
		return "add_success";
	}
	
	public String updateInput(){
		form = formService.findFormById(form.getId());
		return "form_update_input";
	}
	
	public String update(){
		formService.updateForm(form);
		return "update_success";
	}
	
	public String del(){
		formService.delForm(form.getId());
		return "del_success";
	}
	
	/**
	 * 本方法将查询所有的表单，并构造一个JSON串，其格式为：
	 * [{"id","f_xx","title":"表单KEY","value":"表单的KEY"},{},{}]
	 * 这个方法将被流程设计器调用
	 */
	public void searchAllForm(){
		List<Form> allForms = formService.findAllForms();
		List vos = new ArrayList();
		for (Iterator iterator = allForms.iterator(); iterator.hasNext();) {
			Form f = (Form) iterator.next();
			Map vo = new HashMap();
			vo.put("id", "f_"+f.getId()); //id
			vo.put("title", f.getKey()); //formKey
			vo.put("value", f.getKey()); //formKey
			vos.add(vo);
		}
		JSONUtils.toJSON(vos);
	}	
	
	/**
	 * 如果processDefinitionId有值，将根据processDefinitionId查询出其起点对应的表单，并呈现
	 * 如果taskId有值，将根据taskId查询出其对应的表单，并呈现
	 * @return
	 */
	public String display(){
		
		String formKey = null;
		
		if(processDefinitionId != null){
			//根据流程定义ID查询起点的表单
			formKey = processService.findStartFormKeyByProcessDefinitionId(processDefinitionId);
		}else if(taskId != null){
			//根据taskId查询其对应的任务表单
			formKey = processService.findTaskFormKeyByTaskId(taskId);
		}
		
		if(formKey == null){
			throw new RuntimeException("无法呈现表单！[formKey="+formKey+"]");
		}
		
		ActionContext.getContext().put("formKey", formKey);
		
		return "display";
	}
	
	public Object service(String serviceId){
		return WebApplicationContextUtils
			.getRequiredWebApplicationContext(ServletActionContext.getServletContext())
			.getBean(serviceId);
	}	

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
