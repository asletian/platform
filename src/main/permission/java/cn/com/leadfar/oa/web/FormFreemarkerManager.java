package cn.com.leadfar.oa.web;

import javax.servlet.ServletContext;

import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.leadfar.oa.service.FormService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;

/**
 * ��дFreemarkerManager����ҪĿ���ǣ�
 * ����һ���µ�TemplateLoader -> FormContentLoader
 * ���FormContentLoader�������ݿ��м���ģ������ݣ�
 * @author Lee
 *
 */
public class FormFreemarkerManager extends FreemarkerManager {

	@Override
	protected TemplateLoader createTemplateLoader(
			ServletContext servletContext, String templatePath) {

		FormService formService = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext)
			.getBean(FormService.class);
		
		//Struts2����������TemplateLoader
		MultiTemplateLoader loaders = (MultiTemplateLoader)super.createTemplateLoader(servletContext, templatePath);
		
		MultiTemplateLoader myloaders = new MultiTemplateLoader(
			new TemplateLoader[]{
					loaders,
					new FormContentLoader(formService) 
			}
		);
		
		return myloaders;
	}

}
