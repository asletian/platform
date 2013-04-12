package cn.com.leadfar.oa.vo;


/**
 * �����̲���Ľ������г��������̶����ļ�ʱ��ʹ�õ�VO
 * @author Lee
 *
 */
public class ProcessFileVO implements Comparable<ProcessFileVO>{

	/**
	 * �ļ�·��
	 */
	private String filePath;
	
	/**
	 * ��������
	 */
	private String processName;
	
	/**
	 * ����KEY
	 */
	private String processKey;
	
	/**
	 * ���İ汾��
	 */
	private int processVersion;	
	
	/**
	 * ״̬��������
			- �������̶����ļ������ݿ�����ؼ�¼��δ�����貿�� - UNDEPLOY
			- �����̶����ļ������ݿ�����ؼ�¼����ɾ�������Ƴ����� - DELETED
			- �������̶����ļ������ݿ�����ؼ�¼�����ļ���ʱ���һ�£��Ѳ�������仯�� - DEPLOYED
			- �������̶����ļ������ݿ�����ؼ�¼�����ļ���ʱ�����һ�£��Ѹ��£������²��� - UPDATED
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
