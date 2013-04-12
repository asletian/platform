package cn.com.leadfar.oa.web;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.xwork.StringUtils;

import cn.com.leadfar.oa.model.Form;
import cn.com.leadfar.oa.service.FormService;
import freemarker.cache.TemplateLoader;

/**
 * 所谓templateSource实际上就是模板源代码
 * @author Lee
 *
 */
public class FormContentLoader implements TemplateLoader {

	private FormService formService;
	
	public FormContentLoader(FormService formService){
		this.formService = formService;
	}
	
	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
		//忽略！！！
	}

	/**
	 * 传进来的参数值，其格式如下：
	 * form/dynamic/default_start_form
	 */
	@Override
	public Object findTemplateSource(String formPath) throws IOException {
		
		String prefix = "form/dynamic/";
		
		//不是动态表单，忽略不做解释
		if(!formPath.startsWith(prefix)){
			return null;
		}
		
		//如果是动态表单，则从路径中提取出KEY值来
		String formKey = formPath.substring(prefix.length());
		
		//查询出动态表单对象
		Form form = formService.findFormByKey(formKey);
		
		if(form == null){
			return null;
		}
		
		//返回动态表单的内容
		String content = form.getContent();
		
		/**
		 * 替换其中的所有的#xxx#为<#include "xxx">
		 */
		Pattern pattern = Pattern.compile("#(.*?)#");
		Matcher m = pattern.matcher(content);
		while(m.find()){
			content = StringUtils.replace(content , 
					m.group(0),
					"<#include \""+m.group(1)+"\">"
				);
		}		
		
		return content;
	}

	@Override
	public long getLastModified(Object templateSource) {
		return 0;
	}

	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		return new StringReader((String)templateSource);
	}

}
