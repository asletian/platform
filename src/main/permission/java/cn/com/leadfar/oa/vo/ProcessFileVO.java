package cn.com.leadfar.oa.vo;


/**
 * 在流程部署的界面上列出所有流程定义文件时，使用的VO
 * @author Lee
 *
 */
public class ProcessFileVO implements Comparable<ProcessFileVO>{

	/**
	 * 文件路径
	 */
	private String filePath;
	
	/**
	 * 流程名称
	 */
	private String processName;
	
	/**
	 * 流程KEY
	 */
	private String processKey;
	
	/**
	 * 最大的版本号
	 */
	private int processVersion;	
	
	/**
	 * 状态，包括：
			- 已有流程定义文件，数据库无相关记录：未部署（需部署） - UNDEPLOY
			- 无流程定义文件，数据库有相关记录：已删除（需移除部署） - DELETED
			- 已有流程定义文件，数据库有相关记录，且文件的时间戳一致：已部署（无需变化） - DEPLOYED
			- 已有流程定义文件，数据库有相关记录，且文件的时间戳不一致：已更新（需重新部署） - UPDATED
	 */
	private String status;

	public static final String UNDEPLOY = "UNDEPLOY";
	public static final String DEPLOYED = "DEPLOYED";
	public static final String UPDATED = "UPDATED";
	public static final String DELETED = "DELETED";
	
	@Override
	public int compareTo(ProcessFileVO o) {
		return filePath.compareToIgnoreCase(o.getFilePath());
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessKey() {
		return processKey;
	}
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	public int getProcessVersion() {
		return processVersion;
	}
	public void setProcessVersion(int processVersion) {
		this.processVersion = processVersion;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
