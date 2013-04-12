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
			
			//得到本次部署流程定义文件的时间戳
			long timestamp = processFile.lastModified();
			
			//将流程定义文件部署到数据库
			is = FileUtils.openInputStream(processFile);
			repositoryService.createDeployment()
				.addResourceFromInputStream(processFile.getName(),is)
				.setName(filePath) //本次部署的名称：流程定义文件的路径
				.setTimestamp(timestamp) //本次部署的时间戳：流程定义文件的最后更新时间
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
			//查找最新版本的流程定义对象
			List<ProcessDefinition> defs = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(processKey) //按照流程定义的KEY进行查询
				.orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION) //按照版本号倒序排列
				.list();

			//总共部署了多少次
			int totalVersion = defs.size();
			
			//如果流程定义对象为空，即本流程尚未部署，只需要删除流程定义文件即可
			if(defs.isEmpty()){
				String filePath = processKey;
				File processFile = getProcessFile(filePath);
				FileUtils.forceDelete(processFile);
			}else{
				//最大版本的流程定义对象，本次删除将要删除它
				ProcessDefinition def = defs.get(0);
				
				//首先，查询出本次部署的Deployment对象
				Deployment currentDeployment = repositoryService.createDeploymentQuery()
					.deploymentId(def.getDeploymentId()).uniqueResult();
				
				//本次部署对应的流程定义文件名称
				String filePath = currentDeployment.getName();
				
				//删除本次流程定义的相关部署信息
				//包括Deployment、ProcessDefinition及用这些流程定义对象创建的所有流程实例对象等等
				repositoryService.deleteDeploymentCascade(def.getDeploymentId());
				
				//部署次数多于1次，则并不真正从磁盘上删除流程定义文件，
				//而是将磁盘上的流程定义文件恢复为上一个版本的流程定义文件
				if(totalVersion > 1){
					
					//将磁盘上的流程定义文件，恢复为上一个版本的流程定义文件
					ProcessDefinition preDef = defs.get(1);
					
					Deployment preDeployment = repositoryService.createDeploymentQuery()
						.deploymentId(preDef.getDeploymentId()).uniqueResult();
					
					//根据deploymentId和流程定义文件的名称，查询出其对应的流程定义文件
					InputStream is = repositoryService.getResourceAsStream(preDef.getDeploymentId(), 
							preDeployment.getName()); //上次部署的名称就是流程定义文件的名称
					
					try{
						//将InputStream写入磁盘上的文件
						writeProcessFile(is, preDeployment.getName(),preDeployment.getTimestamp());
					}catch(Exception ignore){
						System.out.println("恢复流程定义文件出现异常！"+ignore.getMessage());
					}finally{
						if(is != null)
							is.close();
					}
				}else{ //只部署了一次，本次删除，将同时把磁盘上的流程定义文件一并删除
					
					//删除磁盘上的流程定义文件
					String absoluteFilePath = processFileRootDir + filePath;
					File f = new File(absoluteFilePath);
					if(f.exists()){
						FileUtils.forceDelete(f);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除流程定义【"+processKey+"】的时候出现异常",e);
		}
	}

	@Override
	public List<ProcessFileVO> findAllProcess() {
		
		//列出磁盘上所有的流程定义文件
		Collection<File> allDefFiles = FileUtils.listFiles(
				new File(this.processFileRootDir),  //哪个目录下的文件
				new String[]{"jpdl.xml"},  //文件的扩展名
				true); //是否搜索子目录

		Map<String,File> allDefFilesMap = new HashMap<String,File>();
		for(File defFile:allDefFiles){
			allDefFilesMap.put(defFile.getName(), defFile);
		}
		
		Map<String,ProcessFileVO> vos = new HashMap<String,ProcessFileVO>();
		
		//查询出所有的流程定义对象（对于相同的KEY，只需最高版本的记录即可）
		List<ProcessDefinition> allDefs = repositoryService.createProcessDefinitionQuery()
			.orderAsc(ProcessDefinitionQuery.PROPERTY_KEY) //按KEY排序
			.orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION) //按版本号倒序排列
			.list();
		for(ProcessDefinition def:allDefs){
			String key = def.getKey();
			String name = def.getName();
			String deploymentId = def.getDeploymentId();
			if(vos.containsKey(key)){
				continue; //本流程定义对应的KEY已处理过，无需再处理，忽略
			}
			
			//尚未处理，则创建一个ProcessFileVO对象
			ProcessFileVO vo = new ProcessFileVO();
			
			Deployment deployment = repositoryService.createDeploymentQuery()
				.deploymentId(deploymentId).uniqueResult();
			String filePath = deployment.getName();
			vo.setFilePath(filePath);
			vo.setProcessKey(key);
			vo.setProcessName(def.getName());
			vo.setProcessVersion(def.getVersion());
			
			//判断状态
			//磁盘上是否有此文件？
			if(!allDefFilesMap.containsKey(filePath)){ //磁盘上没有这个文件
				//磁盘上没有这个文件，表示本文件已经被从磁盘中删除
				vo.setStatus(ProcessFileVO.DELETED);
			}else{//磁盘上有这个文件，判断时间戳是否一致
				File defFile = allDefFilesMap.get(filePath);
				long diskFileLastModified = defFile.lastModified();
				long databaseDeploymentTimeStamp = deployment.getTimestamp();
				if(diskFileLastModified != databaseDeploymentTimeStamp){
					//时间戳不一致，表明本文件已更新
					vo.setStatus(ProcessFileVO.UPDATED);
				}else{
					//时间戳一致，表明无需做任何更新
					vo.setStatus(ProcessFileVO.DEPLOYED);
				}
				
				//本文件已处理完毕
				allDefFilesMap.remove(filePath);
			}
			
			//本流程定义KEY已处理完成，无需再处理相同KEY的其它流程定义对象
			vos.put(key, vo);
		}
		
		//是否还有未处理过的磁盘上的流程定义文件？
		//如果还有这些文件，表示它尚未部署到数据库
		if(!allDefFilesMap.isEmpty()){
			
			Iterator<Map.Entry<String,File>> files = allDefFilesMap.entrySet().iterator();
			while(files.hasNext()){
				Map.Entry<String,File> fileEntry = files.next();
				ProcessFileVO vo = new ProcessFileVO();
				vo.setFilePath(fileEntry.getKey());
				vo.setProcessKey(null);
				vo.setProcessName(null);
				vo.setProcessVersion(0);
				vo.setStatus(ProcessFileVO.UNDEPLOY); //未部署
				vos.put(fileEntry.getKey(), vo);
			}
		}
		
		//现在，得到了所有的流程定义信息列表
		Collection<ProcessFileVO> allVos = vos.values();
		
		//创建一个List返回
		List<ProcessFileVO> returnVos = new ArrayList<ProcessFileVO>();
		returnVos.addAll(allVos);
		
		//为了更好的用户体验，对这个列表执行排序（按照文件名称进行排序，
		//注意ProcessFileVO需实现Comparable接口）
		Collections.sort(returnVos);
		
		return returnVos;
	}

	@Override
	public List<ProcessFileVO> findAllDeployedProcess() {
		List<ProcessFileVO> all = findAllProcess();
		
		for (Iterator iterator = all.iterator(); iterator.hasNext();) {
			ProcessFileVO vo = (ProcessFileVO) iterator.next();
			
			/**
			 * 对于那些：
			 * 	流程定义文件已被删除，而尚未恢复上个版本的流程（DELETED）
			 *  流程定义文件已被更新，而尚未重新部署的流程（UPDATED）
			 *  流程定义文件已被创建，而尚未部署的流程（UNDEPLOYED）
			 * 全部不允许创建WorkEntity
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
		 * 得到此流程定义起点的名称
		 */
		String startActivityName = repositoryService.getStartActivityNames(processDefinitionId).get(0);
		
		/**
		 * 根据流程定义的ID和起点的名称，可以得到起点绑定的表单
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
		if(task != null){ //根据Task找到对应的表单KEY
			formKey = task.getFormResourceName();
		}
		
		if(formKey == null){ //从Task中找不到表单KEY，或者说Task对象不存在，比如Task已经被结束
			//则从其对应的WorkApprove对象中获得
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
			throw new RuntimeException("流程定义文件【"+absoluteFilePath+"】不存在");
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
			throw new RuntimeException("恢复流程定义文件["+filePath+"]的时候出现异常",e);
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
