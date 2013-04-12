package cn.com.leadfar.oa.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts2.views.jsp.ui.OgnlTool;

import com.opensymphony.xwork2.ognl.OgnlUtil;

public class EntityProperty {
	private int id;
	private String stringValue;
	private Integer intValue;
	private Date dateValue;
	private Double doubleValue;
	private Long longValue;
	private byte[] fileValue;
	
	//动态属性的类型
	private String propertyType;
	
	/**
	 * 上传文件的时候，记录文件的名称和文件的内容类型
	 */
	private String fileValueFileName;
	private String fileValueContentType;	
	
	public Object getValue(){
		try {
			return PropertyUtils.getProperty(this, propertyType);
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
		this.propertyType = "stringValue";
	}
	public Integer getIntValue() {
		return intValue;
	}
	public void setIntValue(Integer intValue) {
		this.intValue = intValue;
		this.propertyType = "intValue";
	}
	public Date getDateValue() {
		return dateValue;
	}
	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
		this.propertyType = "dateValue";
	}

	public byte[] getFileValue() {
		return fileValue;
	}

	public void setFileValue(byte[] fileValue) {
		this.fileValue = fileValue;
		this.propertyType = "fileValue";
	}

	public String getFileValueFileName() {
		return fileValueFileName;
	}

	public void setFileValueFileName(String fileValueFileName) {
		this.fileValueFileName = fileValueFileName;
	}

	public String getFileValueContentType() {
		return fileValueContentType;
	}

	public void setFileValueContentType(String fileValueContentType) {
		this.fileValueContentType = fileValueContentType;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getDoubleValue() {
		return doubleValue;
	}
	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
		this.propertyType = "doubleValue";
	}
	public Long getLongValue() {
		return longValue;
	}
	public void setLongValue(Long longValue) {
		this.longValue = longValue;
		this.propertyType = "longValue";
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	
}
