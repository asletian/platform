package cn.com.leadfar.oa.service;

import java.util.List;
import java.util.Set;

import cn.com.leadfar.oa.model.WorkApprove;
import cn.com.leadfar.oa.model.WorkEntity;
import cn.com.leadfar.oa.vo.TaskVOList;

public interface WorkApproveService {
	
	/**
	 * 查询出流转到某个用户手上的任务列表
	 * @param userId 用户ID
	 * @param processKey 流程KEY，如果此值为空，则查询所有的任务列表
	 * @return 返回的信息包括：WorkEntity对象，与此WorkEntity对象相关的所有Task对象
	 */
	public List<TaskVOList.TaskVO> findAllDealingList(int userId,String processKey);
	
	/**
	 * 查询出某个用户曾经办理过的任务列表
	 * @param userId 用户ID
	 * @param processKey 流程KEY，如果此值为空，则查询所有的任务列表
	 * @return 
	 */
	public List findAllDealedList(int userId,String processKey);
	
	/**
	 * 根据taskId查询其对应的WorkApprove对象
	 * WorkApprove对象保存了本次审批的所有业务数据信息
	 * @param taskId
	 * @return
	 */
	public WorkApprove findWorkApproveByTaskId(String taskId);
	
	/**
	 * 根据taskId查询其对应的WorkEntity对象
	 * 如果本task已经被处理，则应可以得到其对应WorkApprove，然后可得到对应的WorkEntity
	 * 否则，从taskId可得到Task，通过Task可得到execution，通过execution可得到
	 * ProcessInstance，通过ProcessInstance可得到其processInstanceKey，然后可
	 * 得到对应的WorkEntity对象
	 * @param taskId
	 * @return
	 */
	public WorkEntity findWorkEntityByTaskId(String taskId);
	
	/**
	 * 保存WorkApprove
	 * @param workApprove
	 */
	public void saveWorkApprove(WorkApprove workApprove);
	
	/**
	 * 保存WorkApprove，并按照指定的outcome离开！
	 * @param workApprove
	 * @param outcome
	 */
	public void saveAndSubmitWorkApprove(WorkApprove workApprove,String outcome);
	
	/**
	 * 查询某个任务的下一步的transition的名称列表
	 * @param taskId
	 * @return
	 */
	public Set<String> findOutcomeTransitions(String taskId);
	
	/**
	 * 删除某个动态属性
	 * 主要是为了删除文件
	 * @param entityPropertyId
	 */
	public void delEntityProperty(int entityPropertyId);	
}
