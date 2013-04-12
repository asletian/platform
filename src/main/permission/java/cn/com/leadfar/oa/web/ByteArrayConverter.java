package cn.com.leadfar.oa.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class ByteArrayConverter extends DefaultTypeConverter {

	//½«java.io.File[]×ª»»Îªbyte[]
	@Override
	public Object convertValue(Map<String, Object> context, Object value,
			Class toType) {

		File[] files = (File[])value;
		
		try {
			if(files != null && files.length > 0){
				File f = files[0];
				FileInputStream fis = new FileInputStream(f);
				return IOUtils.toByteArray(fis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
