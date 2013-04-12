package cn.com.leadfar.oa.service;

import java.util.List;

import cn.com.leadfar.oa.model.EntityProperty;
import cn.com.leadfar.oa.model.WorkEntity;

public interface WorkEntityService {
	
	/**
	 * ���湤���������
	 * @param entity 
	 */
	public void addWorkEntity(WorkEntity entity);
	
	/**
	 * ��ѯĳ���û���һ���ǵ�ǰ��¼�û�����������WorkEntity����
	 * @return
	 */
	public List<WorkEntity> findAllMyCreatedWorkEntities(int userId);
	
	/**
	 * ��ѯĳ���û���һ���ǵ�ǰ��¼�û�����������ĳ�����͵�WorkEntity����
	 * @param userId
	 * @param processKey
	 * @return
	 */
	public List<WorkEntity> findAllMyCreatedWorkEntities(int userId,String processKey);
	
	/**
	 * ����WorkEntity����
	 * @param entity
	 */
	public void updateWorkEntity(WorkEntity entity);
	
	/**
	 * ɾ��WorkEntity����
	 * @param entityId
	 */
	public void delWorkEntity(int entityId);
	
	/**
	 * ����ID��ѯWorkEntity����
	 * @param entityId
	 * @return
	 */
	public WorkEntity findWorkEntityById(int entityId);
	
	/**
	 * �����µ�����ʵ��
	 * @param entityId
	 */
	public void startProcess(int entityId);
	
	/**
	 * ����ĳ����̬����
	 * ��Ҫ��Ϊ���ļ�����
	 * @param entityPropertyId
	 * @return
	 */
	public EntityProperty findEntityPropertyById(int entityPropertyId);
	
	/**
	 * ɾ��ĳ����̬����
	 * ��Ҫ��Ϊ��ɾ���ļ�
	 * @param entityPropertyId
	 */
	public void delEntityProperty(int entityPropertyId);
	
}
