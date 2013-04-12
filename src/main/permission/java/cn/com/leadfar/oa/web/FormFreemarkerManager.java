package cn.com.leadfar.oa.web;

import javax.servlet.ServletContext;

import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.leadfar.oa.service.FormService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;

/**
 * 重写FreemarkerManager的主要目的是：
 * 插入一个新的TemplateLoader -> FormContentLoader
 * 这个FormContentLoader将从数据库中加载模板的内容！
 * @author Lee
 *
 */
public class FormFreemarkerManager extends FreemarkerManager {

	@Override
	protected TemplateLoader createTemplateLoader(
			ServletContext servletContext, String templatePath) {

		FormService formService = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext)
			.getBean(FormService.class);
		
		//Struts2创建的所有TemplateLoader
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
