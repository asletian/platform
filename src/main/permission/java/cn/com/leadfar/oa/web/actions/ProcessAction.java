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
	 * 用来接收流程定义文件的路径信息
	 */
	private String processKey;
	
	@Resource
	private ProcessService processService;
	
	/**
	 * 列出所有流程定义信息
	 * @return
	 */
	public String list(){
		
		List<ProcessFileVO> vos = processService.findAllProcess();
		
		ActionContext.getContext().put("vos", vos);
		
		return "list";
	}
	
	/**
	 * 部署某个流程定义
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
