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
 * ��νtemplateSourceʵ���Ͼ���ģ��Դ����
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
		//���ԣ�����
	}

	/**
	 * �������Ĳ���ֵ�����ʽ���£�
	 * form/dynamic/default_start_form
	 */
	@Override
	public Object findTemplateSource(String formPath) throws IOException {
		
		String prefix = "form/dynamic/";
		
		//���Ƕ�̬�������Բ�������
		if(!formPath.startsWith(prefix)){
			return null;
		}
		
		//����Ƕ�̬�������·������ȡ��KEYֵ��
		String formKey = formPath.substring(prefix.length());
		
		//��ѯ����̬������
		Form form = formService.findFormByKey(formKey);
		
		if(form == null){
			return null;
		}
		
		//���ض�̬��������
		String content = form.getContent();
		
		/**
		 * �滻���е����е�#xxx#Ϊ<#include "xxx">
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
