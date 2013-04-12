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
	 * 当在界面上点击附件下载/删除附件时，传进来一个EntityProperty对象的ID
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
	 * 打开新建任务的主界面
	 * 在这个主界面上，需要列出所有已经部署的流程定义来
	 * @return
	 */
	public String createIndex(){

		findAllDeployedProcess();
		
		return "create_index";
	}
	
	private void findAllDeployedProcess(){
		//查询出所有已部署的流程定义对象
		List<ProcessFileVO> vos = processService.findAllDeployedProcess();
		ActionContext.getContext().put("vos", vos);
	}
	
	//根据流程定义的ID，打开添加界面
	public String addInput(){
//		ValueStack vs = ActionContext.getContext().getValueStack();
//		System.out.println("addInput:"+vs.getRoot());
//		//查询表单
//		String formKey = processService.findStartFormKeyByProcessDefinitionId(workEntity.getProcessDefinitionId());
//
//		Form form = formService.findFormByKey(formKey);
//		
//		ActionContext.getContext().put("form", form);
		
		return "add_input";
	}
	
	//添加WorkEntity对象
	public String add(){
		
		workEntityService.addWorkEntity(workEntity);
		
		return "add_success";
	}
	
	/**
	 * 打开我的任务主界面
	 * @return
	 */
	public String myIndex(){
		findAllDeployedProcess();
		return "my_index";
	}
	
	/**
	 * 打开我的任务列表
	 * @return
	 */
	public String myList(){
		
		//根据流程定义KEY，查询出通过这个流程定义（不管哪个版本）所创建的所有WorkEntity对象列表
		List<WorkEntity> vos = workEntityService.findAllMyCreatedWorkEntities(currentUser().getId(), processKey);

		ActionContext.getContext().put("vos", vos);
		
		/**
		 * 如果没有指定流程定义KEY，即列出所有WorkEntity对象来，无法在列表上体现
		 * 各个WorkEntity之间的不同，因此列表上只会显示一些通用的信息
		 */
		if(processKey == null || processKey.trim().equals("")
				|| processKey.equals("undefined")){
			return "my_list";
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
		
		return "my_detail_list";
	}
	
	//查看WorkEntity的详细信息
	public String viewWorkEntity(){
		
		workEntity = workEntityService.findWorkEntityById(workEntity.getId());
		
		//查询表单
//		String formKey = processService.findStartFormKeyByProcessDefinitionId(workEntity.getProcessDefinitionId());
//
//		Form form = formService.findFormByKey(formKey);
//		
//		ActionContext.getContext().put("form", form);			
		
		return "view_work_entity";
	}
	
	//打开更新WorkEntity的界面
	public String updateInput(){
		
		workEntity = workEntityService.findWorkEntityById(workEntity.getId());
		
//		//查询表单
//		String formKey = processService.findStartFormKeyByProcessDefinitionId(workEntity.getProcessDefinitionId());
//
//		Form form = formService.findFormByKey(formKey);
//		
//		ActionContext.getContext().put("form", form);		
		
		return "update_input";
	}
	
	//更新WorkEntity对象
	public String update(){
		
		workEntityService.updateWorkEntity(workEntity);
		
		return "update_success";
	}
	
	//删除WorkEntity对象
	public String del(){
		workEntityService.delWorkEntity(workEntity.getId());
		return "del_success";
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
	
	//点击附件的名称时，可以下载对应的附件
	public void download(){
		EntityProperty fileProp = workEntityService.findEntityPropertyById(entityPropertyId);
		if(fileProp.getFileValue() != null){
			try {
				HttpServletResponse response = ServletActionContext.getResponse();
				
				//设置MIME类型
				response.setContentType("application/x-msdownload;charset=UTF-8");
				
				//提示文件保存的文件名
				String fileName = fileProp.getFileValueFileName();//URLEncoder.encode(fileProp.getFileValueFileName(), "UTF-8");
				//为了在浏览器中下载时，提示中文的下载文件名，需要转换为ISO-8859-1编码的字符串
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment;filename="+fileName);
				
				//将文件内容写入response
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
	
	//根据WorkEntity对象的ID，提交新建的WorkEntity
	//即启动新的流程实例
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
