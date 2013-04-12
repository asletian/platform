package cn.com.leadfar.oa.service;

import java.util.List;

import cn.com.leadfar.oa.vo.ProcessFileVO;

public interface ProcessService {
	
	/**
	 * 部署流程定义文件
	 * @param filePath 相对于流程定义文件存放目录的一个相对路径
	 */
	public void addDeployProcess(String filePath);
	
	/**
	 * 删除流程定义文件，除了需要把流程定义文件删除，还需要
	 * 把流程定义信息（包括JBPM中的流程定义对象）全部删除
	 * @param processKey
	 */
	public void delProcess(String processKey);
	
	/**
	 * 列出流程定义的有关信息
	 * 
	 * @return
	 */
	public List<ProcessFileVO> findAllProcess();
	
	/**
	 * 列出所有已部署的流程定义（不包括那些尚未部署的流程定义文件）
	 * @return
	 */
	public List<ProcessFileVO> findAllDeployedProcess();
	
	/**
	 * 查询起点的表单
	 * @param processDefinitionId
	 * @return
	 */
	public String findStartFormKeyByProcessDefinitionId(String processDefinitionId);
	
	/**
	 * 根据任务ID，查询本任务对应的动态表单
	 * @param taskId
	 * @return
	 */
	public String findTaskFormKeyByTaskId(String taskId);
}
