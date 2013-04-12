package cn.com.leadfar.oa.service;

import java.util.List;

import cn.com.leadfar.oa.vo.ProcessFileVO;

public interface ProcessService {
	
	/**
	 * �������̶����ļ�
	 * @param filePath ��������̶����ļ����Ŀ¼��һ�����·��
	 */
	public void addDeployProcess(String filePath);
	
	/**
	 * ɾ�����̶����ļ���������Ҫ�����̶����ļ�ɾ��������Ҫ
	 * �����̶�����Ϣ������JBPM�е����̶������ȫ��ɾ��
	 * @param processKey
	 */
	public void delProcess(String processKey);
	
	/**
	 * �г����̶�����й���Ϣ
	 * 
	 * @return
	 */
	public List<ProcessFileVO> findAllProcess();
	
	/**
	 * �г������Ѳ�������̶��壨��������Щ��δ��������̶����ļ���
	 * @return
	 */
	public List<ProcessFileVO> findAllDeployedProcess();
	
	/**
	 * ��ѯ���ı�
	 * @param processDefinitionId
	 * @return
	 */
	public String findStartFormKeyByProcessDefinitionId(String processDefinitionId);
	
	/**
	 * ��������ID����ѯ�������Ӧ�Ķ�̬��
	 * @param taskId
	 * @return
	 */
	public String findTaskFormKeyByTaskId(String taskId);
}
