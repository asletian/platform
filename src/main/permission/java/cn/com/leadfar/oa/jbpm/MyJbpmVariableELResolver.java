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
			
			//���ص���롿
			WorkEntity entity = EnvironmentImpl.getFromCurrent(WorkEntityService.class)
				.findWorkEntityById(workEntityId);
			
			try {
				Object value = entity.getProperty(propertyName);
				if(value != null){
					context.setPropertyResolved(true); //����JBPM���������Ѿ�����
					return value;
				}
			} catch (Exception e) {
			}
			
			//�����WorkEntity���䶯̬�����ж��޷��ҵ��������ֵ������Լ�����WorkApprove�в���
			Set<WorkApprove> workApproves = entity.getWorkApproves();
			if(workApproves != null){
				for(WorkApprove wa:workApproves){
					try {
						Object value = wa.getProperty(propertyName);
						if(value != null){
							context.setPropertyResolved(true); //����JBPM���������Ѿ�����
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
