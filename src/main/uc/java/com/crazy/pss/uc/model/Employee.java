package com.crazy.pss.uc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 
 * @Description 人员
 * 
 * @author crazy/Y
 * @date 2013-4-4
 */
@Entity
@DiscriminatorValue(value="EMPLOYEE")
public class Employee extends Party {
	
	/**
	 * 性别
	 */
	public enum Sex{
		/** 男 */
		male,
		/** 女 */
		female;
		
		public String tString(){
			return this.name().toString();
		}
	}
	
	private Sex sex;
	private String email;
	private String phoneURL;
	private String officePhone;
	private String mobile;
	/** 转正时间*/
	private Date positiveTime;
	/** 合同时长   xxx年 */
	private Integer contractDuration;
	/** 员工编号  */
	private String staffNum;

	@Column(name="SEX", length=50)
	@Enumerated(EnumType.STRING)
	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	@Column(name="EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="PHONE_URL")
	public String getPhoneURL() {
		return phoneURL;
	}

	public void setPhoneURL(String phoneURL) {
		this.phoneURL = phoneURL;
	}

	@Column(name="OFFICE_PHONE")
	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	@Column(name="MOBILE")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取转正日期
	 * 
	 * @return	转正日期
	 */
	@Column(name="POSITIVE_TIME")
	public Date getPositiveTime() {
		return positiveTime;
	}

	/**
	 * 设置转正日期
	 * 
	 * @param positiveTime	转正日期
	 */
	public void setPositiveTime(Date positiveTime) {
		this.positiveTime = positiveTime;
	}

	/**
	 * 获取合同时长,单位年.
	 * 
	 * @return	合同时长
	 */
	@Column(name="CONTRACT_DURATION")
	public Integer getContractDuration() {
		return contractDuration;
	}

	/**
	 * 设置合同时长,单位年.
	 * 
	 * @param contractDuration	合同时长
	 */
	public void setContractDuration(Integer contractDuration) {
		this.contractDuration = contractDuration;
	}

	/**
	 * 获取员工编号
	 * 
	 * @return	员工编号
	 */
	@Column(name="STAFF_NUM")
	public String getStaffNum() {
		return staffNum;
	}

	/**
	 * 设置员工编号
	 * 
	 * @param staffNum	员工编号
	 */
	public void setStaffNum(String staffNum) {
		this.staffNum = staffNum;
	}

}
