package com.crazy.pss.common.web;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import com.crazy.pss.common.utils.DateUtils;

/**
 * 
 * @Description 
 * 初始化数据绑定
 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
 * 2. 将字段中Date类型转换为String类型
 * 
 * @author crazy/Y
 * @date 2013-4-6
 */
@Component
public class CommonBindingInitializer implements WebBindingInitializer{

	public void initBinder(WebDataBinder binder, WebRequest request) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});		
	}

}
