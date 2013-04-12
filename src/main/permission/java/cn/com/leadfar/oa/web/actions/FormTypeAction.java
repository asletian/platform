package cn.com.leadfar.oa.web.actions;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.com.leadfar.oa.model.Form;
import cn.com.leadfar.oa.model.FormType;
import cn.com.leadfar.oa.service.FormService;
import cn.com.leadfar.oa.service.FormTypeService;
import cn.com.leadfar.oa.vo.FormTypeTreeVO;
import cn.com.leadfar.oa.web.JSONUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("formTypeAction")
@Scope("prototype")
public class FormTypeAction extends BaseAction implements ModelDriven{
	
	private FormType formType;
	
	@Resource
	private FormTypeService formTypeService;
	
	@Resource
	private FormService formService;
	
	@Override
	public Object getModel() {
		if(formType == null){
			formType = new FormType();
		}
		return formType;
	}

	public String execute(){
		return "index";
	}
	
	/**
	 * ���ɱ�������
	 */
	public void tree(){
		List<FormType> types = formTypeService.findAllTopFormTypes();
		List typesVo = new ArrayList();
		for(FormType ft:types){
			FormTypeTreeVO ftv = new FormTypeTreeVO(ft);
			typesVo.add(ftv);
		}
		JSONUtils.toJSON(typesVo);
	}
	
	/**
	 * ����ӱ����͵Ľ���
	 * @return
	 */
	public String addInput(){
		return "formtype_add_input";
	}
	
	public String add(){
		formTypeService.addFormType(formType);
		return "add_success";
	}
	
	public String updateInput(){
		
		formType = formTypeService.findFormTypeById(formType.getId());
		
		//��ѯ������������������Щ��
		List<Form> forms = formService.findAllForms(formType.getId());
		
		ActionContext.getContext().put("forms", forms);
		
		return "formtype_update_input";
	}
	
	public String update(){
		
		formTypeService.updateFormType(formType);
		
		return "update_success";
	}
	
	public String del(){
		formTypeService.delFormType(formType.getId());
		return "del_success";
	}
}
