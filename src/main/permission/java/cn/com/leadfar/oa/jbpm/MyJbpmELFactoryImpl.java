package cn.com.leadfar.oa.jbpm;

import javax.el.CompositeELResolver;
import javax.el.ELContext;

import org.jbpm.pvm.internal.el.JbpmElFactoryImpl;
import org.jbpm.pvm.internal.model.ScopeInstanceImpl;

public class MyJbpmELFactoryImpl extends JbpmElFactoryImpl {

	@Override
	protected ELContext createCompositeResolver(ScopeInstanceImpl scopeInstance) {

		ELContext context = super.createCompositeResolver(scopeInstance);
		
		CompositeELResolver ce = (CompositeELResolver)context.getELResolver();
		
		//加入我们自定义的变量解释器
		ce.add(new MyJbpmVariableELResolver(scopeInstance));
		
		return context;
	}

}
