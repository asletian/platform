package cn.com.leadfar.oa.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.StrutsTypeConverter;

public class DateConverter extends StrutsTypeConverter {

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	private static Log logger = LogFactory.getLog(DateConverter.class);
	
	@Override
	public Object convertFromString(Map context, String[] params, Class toType) {
		
		try {
			if(params != null && params.length > 0){
				return format.parse(params[0]);
			}
		} catch (ParseException e) {
			logger.warn("日期格式有误，无法转换，正确格式应该是：yyyy-MM-dd");
		}
		
		return null;
	}

	@Override
	public String convertToString(Map context, Object value) {

		return format.format(value);
	}

}
