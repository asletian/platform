package cn.com.leadfar.oa.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jbpm.api.Deployment;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;

import cn.com.leadfar.oa.dao.WorkApproveDao;
import cn.com.leadfar.oa.model.WorkApprove;
import cn.com.leadfar.oa.service.ProcessService;
import cn.com.leadfar.oa.vo.ProcessFileVO;

public class ProcessServiceImpl implements ProcessService {

	private RepositoryService repositoryService;
	
	private String processFileRootDir;
	
	private TaskService taskService;
	
	private WorkApproveDao workApproveDao;
	
	@Override
	public void addDeployProcess(String filePath) {
		InputStream is = null;
		try {
			File processFile = getProcessFile(filePath);
			
			//�õ����β������̶����ļ���ʱ���
			long timestamp = processFile.lastModified();
			
			//�����̶����ļ��������ݿ�
			is = FileUtils.openInputStream(processFile);
			repositoryService.createDeployment()
				.addResourceFromInputStream(processFile.getName(),is)
				.setName(filePath) //���β�������ƣ����̶����ļ���·��
				.setTimestamp(timestamp) //���β����ʱ��������̶����ļ���������ʱ��
				.deploy();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delProcess(String processKey) {
		
		try {
			//�������°汾�����̶������
			List<ProcessDefinition> defs = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(processKey) //�������̶����KEY���в�ѯ
				.orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION) //���հ汾�ŵ�������
				.list();

			//�ܹ������˶��ٴ�
			int totalVersion = defs.size();
			
			//������̶������Ϊ�գ�����������δ����ֻ��Ҫɾ�����̶����ļ�����
			if(defs.isEmpty()){
				String filePath = processKey;
				File processFile = getProcessFile(filePath);
				FileUtils.forceDelete(processFile);
			}else{
				//���汾�����̶�����󣬱���ɾ����Ҫɾ����
				ProcessDefinition def = defs.get(0);
				
				//���ȣ���ѯ�����β����Deployment����
				Deployment currentDeployment = repositoryService.createDeploymentQuery()
					.deploymentId(def.getDeploymentId()).uniqueResult();
				
				//���β����Ӧ�����̶����ļ�����
				String filePath = currentDeployment.getName();
				
				//ɾ���������̶������ز�����Ϣ
				//����Deployment��ProcessDefinition������Щ���̶�����󴴽�����������ʵ������ȵ�
				repositoryService.deleteDeploymentCascade(def.getDeploymentId());
				
				//�����������1�Σ��򲢲������Ӵ�����ɾ�����̶����ļ���
				//���ǽ������ϵ����̶����ļ��ָ�Ϊ��һ���汾�����̶����ļ�
				if(totalVersion > 1){
					
					//�������ϵ����̶����ļ����ָ�Ϊ��һ���汾�����̶����ļ�
					ProcessDefinition preDef = defs.get(1);
					
					Deployment preDeployment = repositoryService.createDeploymentQuery()
						.deploymentId(preDef.getDeploymentId()).uniqueResult();
					
					//����deploymentId�����̶����ļ������ƣ���ѯ�����Ӧ�����̶����ļ�
					InputStream is = repositoryService.getResourceAsStream(preDef.getDeploymentId(), 
							preDeployment.getName()); //�ϴβ�������ƾ������̶����ļ�������
					
					try{
						//��InputStreamд������ϵ��ļ�
						writeProcessFile(is, preDeployment.getName(),preDeployment.getTimestamp());
					}catch(Exception ignore){
						System.out.println("�ָ����̶����ļ������쳣��"+ignore.getMessage());
					}finally{
						if(is != null)
							is.close();
					}
				}else{ //ֻ������һ�Σ�����ɾ������ͬʱ�Ѵ����ϵ����̶����ļ�һ��ɾ��
					
					//ɾ�������ϵ����̶����ļ�
					String absoluteFilePath = processFileRootDir + filePath;
					File f = new File(absoluteFilePath);
					if(f.exists()){
						FileUtils.forceDelete(f);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ɾ�����̶��塾"+processKey+"����ʱ������쳣",e);
		}
	}

	@Override
	public List<ProcessFileVO> findAllProcess() {
		
		//�г����������е����̶����ļ�
		Collection<File> allDefFiles = FileUtils.listFiles(
				new File(this.processFileRootDir),  //�ĸ�Ŀ¼�µ��ļ�
				new String[]{"jpdl.xml"},  //�ļ�����չ��
				true); //�Ƿ�������Ŀ¼

		Map<String,File> allDefFilesMap = new HashMap<String,File>();
		for(File defFile:allDefFiles){
			allDefFilesMap.put(defFile.getName(), defFile);
		}
		
		Map<String,ProcessFileVO> vos = new HashMap<String,ProcessFileVO>();
		
		//��ѯ�����е����̶�����󣨶�����ͬ��KEY��ֻ����߰汾�ļ�¼���ɣ�
		List<ProcessDefinition> allDefs = repositoryService.createProcessDefinitionQuery()
			.orderAsc(ProcessDefinitionQuery.PROPERTY_KEY) //��KEY����
			.orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION) //���汾�ŵ�������
			.list();
		for(ProcessDefinition def:allDefs){
			String key = def.getKey();
			String name = def.getName();
			String deploymentId = def.getDeploymentId();
			if(vos.containsKey(key)){
				continue; //�����̶����Ӧ��KEY�Ѵ�����������ٴ�������
			}
			
			//��δ�����򴴽�һ��ProcessFileVO����
			ProcessFileVO vo = new ProcessFileVO();
			
			Deployment deployment = repositoryService.createDeploymentQuery()
				.deploymentId(deploymentId).uniqueResult();
			String filePath = deployment.getName();
			vo.setFilePath(filePath);
			vo.setProcessKey(key);
			vo.setProcessName(def.getName());
			vo.setProcessVersion(def.getVersion());
			
			//�ж�״̬
			//�������Ƿ��д��ļ���
			if(!allDefFilesMap.containsKey(filePath)){ //������û������ļ�
				//������û������ļ�����ʾ���ļ��Ѿ����Ӵ�����ɾ��
				vo.setStatus(ProcessFileVO.DELETED);
			}else{//������������ļ����ж�ʱ����Ƿ�һ��
				File defFile = allDefFilesMap.get(filePath);
				long diskFileLastModified = defFile.lastModified();
				long databaseDeploymentTimeStamp = deployment.getTimestamp();
				if(diskFileLastModified != databaseDeploymentTimeStamp){
					//ʱ�����һ�£��������ļ��Ѹ���
					vo.setStatus(ProcessFileVO.UPDATED);
				}else{
					//ʱ���һ�£������������κθ���
					vo.setStatus(ProcessFileVO.DEPLOYED);
				}
				
				//���ļ��Ѵ������
				allDefFilesMap.remove(filePath);
			}
			
			//�����̶���KEY�Ѵ�����ɣ������ٴ�����ͬKEY���������̶������
			vos.put(key, vo);
		}
		
		//�Ƿ���δ������Ĵ����ϵ����̶����ļ���
		//���������Щ�ļ�����ʾ����δ�������ݿ�
		if(!allDefFilesMap.isEmpty()){
			
			Iterator<Map.Entry<String,File>> files = allDefFilesMap.entrySet().iterator();
			while(files.hasNext()){
				Map.Entry<String,File> fileEntry = files.next();
				ProcessFileVO vo = new ProcessFileVO();
				vo.setFilePath(fileEntry.getKey());
				vo.setProcessKey(null);
				vo.setProcessName(null);
				vo.setProcessVersion(0);
				vo.setStatus(ProcessFileVO.UNDEPLOY); //δ����
				vos.put(fileEntry.getKey(), vo);
			}
		}
		
		//���ڣ��õ������е����̶�����Ϣ�б�
		Collection<ProcessFileVO> allVos = vos.values();
		
		//����һ��List����
		List<ProcessFileVO> returnVos = new ArrayList<ProcessFileVO>();
		returnVos.addAll(allVos);
		
		//Ϊ�˸��õ��û����飬������б�ִ�����򣨰����ļ����ƽ�������
		//ע��ProcessFileVO��ʵ��Comparable�ӿڣ�
		Collections.sort(returnVos);
		
		return returnVos;
	}

	@Override
	public List<ProcessFileVO> findAllDeployedProcess() {
		List<ProcessFileVO> all = findAllProcess();
		
		for (Iterator iterator = all.iterator(); iterator.hasNext();) {
			ProcessFileVO vo = (ProcessFileVO) iterator.next();
			
			/**
			 * ������Щ��
			 * 	���̶����ļ��ѱ�ɾ��������δ�ָ��ϸ��汾�����̣�DELETED��
			 *  ���̶����ļ��ѱ����£�����δ���²�������̣�UPDATED��
			 *  ���̶����ļ��ѱ�����������δ��������̣�UNDEPLOYED��
			 * ȫ����������WorkEntity
			 */
			if(!vo.getStatus().equals(ProcessFileVO.DEPLOYED)){
				iterator.remove();
			}
		}
		
		return all;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public void setProcessFileRootDir(String processFileRootDir) {
		this.processFileRootDir = processFileRootDir;
	}
	
	
	
	@Override
	public String findStartFormKeyByProcessDefinitionId(String processDefinitionId) {

		/**
		 * �õ������̶�����������
		 */
		String startActivityName = repositoryService.getStartActivityNames(processDefinitionId).get(0);
		
		/**
		 * �������̶����ID���������ƣ����Եõ����󶨵ı�
		 */
		String formKey = repositoryService.getStartFormResourceName(processDefinitionId, startActivityName);
		
		if(formKey == null){
			return "default_start_form";
		}
		
		return formKey;
	}

	@Override
	public String findTaskFormKeyByTaskId(String taskId) {
		Task task = taskService.getTask(taskId);
		String formKey = null;
		if(task != null){ //����Task�ҵ���Ӧ�ı�KEY
			formKey = task.getFormResourceName();
		}
		
		if(formKey == null){ //��Task���Ҳ�����KEY������˵Task���󲻴��ڣ�����Task�Ѿ�������
			//������Ӧ��WorkApprove�����л��
			WorkApprove wa = workApproveDao.findWorkApproveByTaskId(taskId);
			if(wa != null){
				formKey = wa.getFormKey();
			}
		}
		
		if(formKey == null){
			return "default_approve_form";
		}
		return formKey;
	}

	private File getProcessFile(String filePath){
		String absoluteFilePath = processFileRootDir + filePath;
		File processFile = new File(absoluteFilePath);
		if(!processFile.exists()){
			throw new RuntimeException("���̶����ļ���"+absoluteFilePath+"��������");
		}
		return processFile;
	}
	
	private void writeProcessFile(InputStream is,String filePath,long lastModified){
		String absoluteFilePath = processFileRootDir + filePath;
		File f = new File(absoluteFilePath);
		OutputStream os = null;
		try {
			if(is != null){
				os = new FileOutputStream(f);
				IOUtils.copy(is, os);
				os.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("�ָ����̶����ļ�["+filePath+"]��ʱ������쳣",e);
		} finally{
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(f.exists())
			f.setLastModified(lastModified);
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setWorkApproveDao(WorkApproveDao workApproveDao) {
		this.workApproveDao = workApproveDao;
	}

}
