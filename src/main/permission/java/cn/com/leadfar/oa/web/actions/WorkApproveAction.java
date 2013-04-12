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
 * �������йص�Action
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
	 * ���ڽ����ϵ����������/ɾ������ʱ��������һ��EntityProperty�����ID
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
	 * ��ѯ���д��������б�
	 * @return
	 */
	public String dealingList(){
		
		List<TaskVOList.TaskVO> vos = workApproveService.findAllDealingList(currentUser().getId(), processKey);

		ActionContext.getContext().put("vos", vos);
		
		/**
		 * ���û��ָ�����̶���KEY�����г�����WorkEntity���������޷����б�������
		 * ����WorkEntity֮��Ĳ�ͬ������б���ֻ����ʾһЩͨ�õ���Ϣ
		 */
		if(processKey == null || processKey.trim().equals("")
				|| processKey.equals("undefined")){
			return "dealing_list";
		}		
		
		/**
		 * ���ָ�������̶���KEY����ô����ЩWorkEntity����������ͬ�Ķ�̬���ԣ����б���
		 * ���Խ���Щ��̬����Ҳһ���г�
		 */
		if(vos != null && !vos.isEmpty()){
			Set<String> propsName = new HashSet<String>(); //���ж�̬���Ե������б�
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
	 * �����������棬��������������������򿪽���ʱ����Ҫ���ϴΰ������Ϣ
	 * ��ʾ��ҳ���ϣ����������޸ģ�
	 * @return
	 */
	public String taskInput(){
		
		//���ȣ�����taskId��ѯ���Ӧ��������Ϣ
		String taskId = approve.getTaskId();
		approve = workApproveService.findWorkApproveByTaskId(taskId);
		if(approve == null){ //approve�п����ǿյģ������½�
			approve = new WorkApprove();
			approve.setTaskId(taskId);
			
			//���������Ӧ��WorkEntity����
			WorkEntity workEntity = workApproveService.findWorkEntityByTaskId(taskId);			
			approve.setWorkEntity(workEntity);
		}
		
		//��ѯ��ǰ�����Ӧ��outcome transition�������б�
		Set<String> outcomes = workApproveService.findOutcomeTransitions(taskId);
		ActionContext.getContext().put("outcomes", outcomes);
		
		return "task_input";
	}
	
	/**
	 * �鿴��������ϸ��Ϣ
	 * @return
	 */
	public String viewWorkApprove(){
		//����taskId��ѯ���Ӧ��������Ϣ
		String taskId = approve.getTaskId();
		approve = workApproveService.findWorkApproveByTaskId(taskId);
		
		return "view_work_approve";
	}
	
	//�������������Ϣ
	public String saveTask(){
		
		approve.setApproveTime(new Date());

		//����ύ��ť��ȡֵ�������ǡ����桱�򡰱��沢[xxx]��
		String nextTransitionName = ((String[])ActionContext.getContext().getParameters().get("submit"))[0];

		//���˱���֮�⣬����Ҫ����ָ����transitionName���ύ
		if(nextTransitionName.startsWith("���沢")){
			nextTransitionName = nextTransitionName.substring("���沢[".length(), nextTransitionName.length()-1);
			workApproveService.saveAndSubmitWorkApprove(approve, nextTransitionName);
		}else{
			workApproveService.saveWorkApprove(approve);
		}
		
		return "save_task_success";
	}	
	
	/**
	 * ��ѯ�����Ѱ������б�
	 * @return
	 */
	public String dealedList(){
		List<WorkEntity> vos = workApproveService.findAllDealedList(currentUser().getId(), processKey);

		ActionContext.getContext().put("vos", vos);
		
		/**
		 * ���û��ָ�����̶���KEY�����г�����WorkEntity���������޷����б�������
		 * ����WorkEntity֮��Ĳ�ͬ������б���ֻ����ʾһЩͨ�õ���Ϣ
		 */
		if(processKey == null || processKey.trim().equals("")
				|| processKey.equals("undefined")){
			return "dealed_list";
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
		
		return "dealed_detail_list";
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
