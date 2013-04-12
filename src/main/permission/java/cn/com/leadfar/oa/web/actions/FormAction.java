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
	 * �ڹ������ϳ�����ӡ����½���ʱ����Ҫ���ֶ�̬�ı�������
	 */
	private String processDefinitionId;
	
	/**
	 * �����������ϣ���Ҫ��ʾ��̬��������
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
	 * ����ӱ��Ľ���
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
	 * ����������ѯ���еı���������һ��JSON�������ʽΪ��
	 * [{"id","f_xx","title":"��KEY","value":"����KEY"},{},{}]
	 * ������������������������
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
	 * ���processDefinitionId��ֵ��������processDefinitionId��ѯ��������Ӧ�ı���������
	 * ���taskId��ֵ��������taskId��ѯ�����Ӧ�ı���������
	 * @return
	 */
	public String display(){
		
		String formKey = null;
		
		if(processDefinitionId != null){
			//�������̶���ID��ѯ���ı�
			formKey = processService.findStartFormKeyByProcessDefinitionId(processDefinitionId);
		}else if(taskId != null){
			//����taskId��ѯ���Ӧ�������
			formKey = processService.findTaskFormKeyByTaskId(taskId);
		}
		
		if(formKey == null){
			throw new RuntimeException("�޷����ֱ���[formKey="+formKey+"]");
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
