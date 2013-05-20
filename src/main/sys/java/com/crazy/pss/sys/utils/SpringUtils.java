package com.crazy.pss.sys.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;

public class SpringUtils {

	/**
	 * 绑定用户提交的数据到指定的 java 类中(日期格式)
	 * 
	 * @param request
	 * @param command
	 * @return
	 */
	public static BindingResult bind_date(HttpServletRequest request, Object command, String format) {
		ServletRequestDataBinder binder = new ServletRequestDataBinder(command);
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
		binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, true));
		binder.registerCustomEditor(Float.class, null, new CustomNumberEditor(Float.class, true));
		binder.registerCustomEditor(BigDecimal.class, null, new CustomNumberEditor(BigDecimal.class, true));
		binder.registerCustomEditor(Byte.class, null, new CustomNumberEditor(Byte.class, true));

		binder.bind(request);
		BindingResult bindingResult = binder.getBindingResult();
		List list = bindingResult.getAllErrors();
		for (Iterator localIterator = list.iterator(); localIterator.hasNext();) {
			Object error = localIterator.next();
			System.out.println(error);
		}
		if (bindingResult.hasErrors())
			throw new RuntimeException("您输入的数据有误!");

		return bindingResult;
	}
}
