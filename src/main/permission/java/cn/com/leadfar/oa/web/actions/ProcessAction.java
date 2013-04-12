package cn.com.leadfar.oa.web.actions;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.com.leadfar.oa.service.ProcessService;
import cn.com.leadfar.oa.vo.ProcessFileVO;

import com.opensymphony.xwork2.ActionContext;

@Controller("processAction")
@Scope("prototype")
public class ProcessAction extends BaseAction{
	
	/**
	 * �����������̶����ļ���·����Ϣ
	 */
	private String processKey;
	
	@Resource
	private ProcessService processService;
	
	/**
	 * �г��������̶�����Ϣ
	 * @return
	 */
	public String list(){
		
		List<ProcessFileVO> vos = processService.findAllProcess();
		
		ActionContext.getContext().put("vos", vos);
		
		return "list";
	}
	
	/**
	 * ����ĳ�����̶���
	 * @return
	 */
	public String deploy(){
		
		processService.addDeployProcess(processKey);
		
		return "deploy_success";
	}
	
	public String del(){
		processService.delProcess(processKey);
		return "del_success";
	}

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

}
