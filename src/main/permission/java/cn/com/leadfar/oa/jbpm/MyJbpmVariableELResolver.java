package cn.com.leadfar.oa.jbpm;

import java.util.Set;

import javax.el.ELContext;

import org.jbpm.api.ProcessInstance;
import org.jbpm.pvm.internal.el.JbpmVariableElResolver;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ScopeInstanceImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

import cn.com.leadfar.oa.model.WorkApprove;
import cn.com.leadfar.oa.model.WorkEntity;
import cn.com.leadfar.oa.service.WorkEntityService;

public class MyJbpmVariableELResolver extends JbpmVariableElResolver {

	private ProcessInstance processInstance;
	
	public MyJbpmVariableELResolver(ScopeInstanceImpl scopeInstance) {
		super(scopeInstance);
		
		if(scopeInstance == null){
			return;
		}
		
		if(scopeInstance instanceof ExecutionImpl){
			ExecutionImpl ei = (ExecutionImpl)scopeInstance;
			processInstance = ei.getProcessInstance();
		}else{
			TaskImpl ti = (TaskImpl)scopeInstance;
			ExecutionImpl ei = ti.getExecution();
			processInstance = ei.getProcessInstance();
		}
		
	}

	@Override
	public Object getValue(ELContext context, Object base, Object property) {
		
		if(base == null){
			
			String propertyName = (String)property;
			
			int workEntityId = Integer.parseInt(processInstance.getKey());
			
			//【重点代码】
			WorkEntity entity = EnvironmentImpl.getFromCurrent(WorkEntityService.class)
				.findWorkEntityById(workEntityId);
			
			try {
				Object value = entity.getProperty(propertyName);
				if(value != null){
					context.setPropertyResolved(true); //告诉JBPM，本变量已经解释
					return value;
				}
			} catch (Exception e) {
			}
			
			//如果从WorkEntity及其动态属性中都无法找到这个变量值，则可以继续从WorkApprove中查找
			Set<WorkApprove> workApproves = entity.getWorkApproves();
			if(workApproves != null){
				for(WorkApprove wa:workApproves){
					try {
						Object value = wa.getProperty(propertyName);
						if(value != null){
							context.setPropertyResolved(true); //告诉JBPM，本变量已经解释
							return value;
						}
					} catch (Exception e) {
					}
				}
			}
			
		}
		
		return super.getValue(context,base, property);
	}

}
