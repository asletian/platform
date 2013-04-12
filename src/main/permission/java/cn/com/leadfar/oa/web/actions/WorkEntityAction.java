package cn.com.leadfar.oa.web.actions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.com.leadfar.oa.model.EntityProperty;
import cn.com.leadfar.oa.model.WorkEntity;
import cn.com.leadfar.oa.service.ProcessService;
import cn.com.leadfar.oa.service.WorkEntityService;
import cn.com.leadfar.oa.vo.ProcessFileVO;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("workEntityAction")
@Scope("prototype")
public class WorkEntityAction extends BaseAction implements ModelDriven{
	
	private WorkEntity workEntity;
	
	
	@Resource
	private ProcessService processService;
	
	@Resource
	private WorkEntityService workEntityService;
	
	private String processKey;
	
	/**
	 * ���ڽ����ϵ����������/ɾ������ʱ��������һ��EntityProperty�����ID
	 * 
	 */
	private int entityPropertyId;
	
	@Override
	public Object getModel() {
		
		if(workEntity == null){
			workEntity = new WorkEntity();
		}
		
		return workEntity;
	}
	
	/**
	 * ���½������������
	 * ������������ϣ���Ҫ�г������Ѿ���������̶�����
	 * @return
	 */
	public String createIndex(){

		findAllDeployedProcess();
		
		return "create_index";
	}
	
	private void findAllDeployedProcess(){
		//��ѯ�������Ѳ�������̶������
		List<ProcessFileVO> vos = processService.findAllDeployedProcess();
		ActionContext.getContext().put("vos", vos);
	}
	
	//�������̶����ID������ӽ���
	public String addInput(){
//		ValueStack vs = ActionContext.getContext().getValueStack();
//		System.out.println("addInput:"+vs.getRoot());
//		//��ѯ��
//		String formKey = processService.findStartFormKeyByProcessDefinitionId(workEntity.getProcessDefinitionId());
//
//		Form form = formService.findFormByKey(formKey);
//		
//		ActionContext.getContext().put("form", form);
		
		return "add_input";
	}
	
	//���WorkEntity����
	public String add(){
		
		workEntityService.addWorkEntity(workEntity);
		
		return "add_success";
	}
	
	/**
	 * ���ҵ�����������
	 * @return
	 */
	public String myIndex(){
		findAllDeployedProcess();
		return "my_index";
	}
	
	/**
	 * ���ҵ������б�
	 * @return
	 */
	public String myList(){
		
		//�������̶���KEY����ѯ��ͨ��������̶��壨�����ĸ��汾��������������WorkEntity�����б�
		List<WorkEntity> vos = workEntityService.findAllMyCreatedWorkEntities(currentUser().getId(), processKey);

		ActionContext.getContext().put("vos", vos);
		
		/**
		 * ���û��ָ�����̶���KEY�����г�����WorkEntity���������޷����б�������
		 * ����WorkEntity֮��Ĳ�ͬ������б���ֻ����ʾһЩͨ�õ���Ϣ
		 */
		if(processKey == null || processKey.trim().equals("")
				|| processKey.equals("undefined")){
			return "my_list";
		}
		
		/**
		 * ���ָ�������̶���KEY����ô����ЩWorkEntity����������ͬ�Ķ�̬���ԣ����б���
		 * ���Խ���Щ��̬����Ҳһ���г�
		 */
		if(vos != null && !vos.isEmpty()){
			Set<String> propsName = new HashSet<String>(); //���ж�̬���Ե������б�
			for(int i=0; i<vos.size(); i++){
				propsName.addAll(vos.get(i).getProps().keySet());
			}
			List<String> propsNameList = new ArrayList<String>();
			propsNameList.addAll(propsName);
			Collections.sort(propsNameList);
			ActionContext.getContext().put("propsName", propsNameList);
		}
		
		return "my_detail_list";
	}
	
	//�鿴WorkEntity����ϸ��Ϣ
	public String viewWorkEntity(){
		
		workEntity = workEntityService.findWorkEntityById(workEntity.getId());
		
		//��ѯ��
//		String formKey = processService.findStartFormKeyByProcessDefinitionId(workEntity.getProcessDefinitionId());
//
//		Form form = formService.findFormByKey(formKey);
//		
//		ActionContext.getContext().put("form", form);			
		
		return "view_work_entity";
	}
	
	//�򿪸���WorkEntity�Ľ���
	public String updateInput(){
		
		workEntity = workEntityService.findWorkEntityById(workEntity.getId());
		
//		//��ѯ��
//		String formKey = processService.findStartFormKeyByProcessDefinitionId(workEntity.getProcessDefinitionId());
//
//		Form form = formService.findFormByKey(formKey);
//		
//		ActionContext.getContext().put("form", form);		
		
		return "update_input";
	}
	
	//����WorkEntity����
	public String update(){
		
		workEntityService.updateWorkEntity(workEntity);
		
		return "update_success";
	}
	
	//ɾ��WorkEntity����
	public String del(){
		workEntityService.delWorkEntity(workEntity.getId());
		return "del_success";
	}
	
	/**
	 * ��EntityProperty����ת��Ϊ�ַ���������ҳ�����
	 * �������ڣ�ת��Ϊ��ʽ��yyyy-MM-dd
	 * �����ļ�����ȡ�����ļ�������
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
	
	//�������������ʱ���������ض�Ӧ�ĸ���
	public void download(){
		EntityProperty fileProp = workEntityService.findEntityPropertyById(entityPropertyId);
		if(fileProp.getFileValue() != null){
			try {
				HttpServletResponse response = ServletActionContext.getResponse();
				
				//����MIME����
				response.setContentType("application/x-msdownload;charset=UTF-8");
				
				//��ʾ�ļ�������ļ���
				String fileName = fileProp.getFileValueFileName();//URLEncoder.encode(fileProp.getFileValueFileName(), "UTF-8");
				//Ϊ���������������ʱ����ʾ���ĵ������ļ�������Ҫת��ΪISO-8859-1������ַ���
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment;filename="+fileName);
				
				//���ļ�����д��response
				response.getOutputStream().write(fileProp.getFileValue());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
	public String delEntityProperty(){
		workEntityService.delEntityProperty(entityPropertyId);
		return "del_entity_property_success";
	}
	
	//����WorkEntity�����ID���ύ�½���WorkEntity
	//�������µ�����ʵ��
	public String start(){
		
		workEntityService.startProcess(workEntity.getId());
		
		return "start_success";
	}
	
	public String dealingIndex(){
		findAllDeployedProcess();
		return "dealing_index";
	}
	public String dealedIndex(){
		findAllDeployedProcess();
		return "dealed_index";
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
