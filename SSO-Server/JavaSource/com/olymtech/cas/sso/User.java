package com.olymtech.cas.sso;

import java.io.Serializable;

/** 用户 实体类 */

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1860337207074414054L;
	private String userId; // 会员登陆ID
	private String username; // 会员登陆名
	private String password; // 会员密码
	private String orgid; // 所属组织ID
	private String orgCode; // 所属组织Code
	private String orgName; // 所属组织名称
	private String firstName; // firstName
	private String lastName; // lastName
	private String sex; // sex.....
	// private String user_status_id; //user_status_id
	private String user_status_code;// user_status_code

	private String minicrm_user_id;// minicrm_user_id
	private String minicrm_company_id;// minicrm_company_id

	// 添加的属性
	private String surName; // 姓
	private String mobile; // 手机
	private String companyPhone; // 公司电话
	private String email; // Email
	private String orgAddr; // 公司地址
	private String provinceId; // 省
	private String cityId; // 市
	
	private String orgSiteNo;//所属站点
	private String is_mac_confirm;
	private String mac_address;
	
	public String getIs_mac_confirm() {
		return is_mac_confirm;
	}

	public void setIs_mac_confirm(String is_mac_confirm) {
		this.is_mac_confirm = is_mac_confirm;
	}

	public String getMac_address() {
		return mac_address;
	}

	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
	}

	public String getOrgSiteNo() {
		return orgSiteNo;
	}

	public void setOrgSiteNo(String orgSiteNo) {
		this.orgSiteNo = orgSiteNo;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrgAddr() {
		return orgAddr;
	}

	public void setOrgAddr(String orgAddr) {
		this.orgAddr = orgAddr;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getOrgName() {
		// this.orgName=StringUtil.temp(this.orgName);
		// System.out.println("return orgName======="+this.orgName);
		return orgName;
	}

	public void setOrgName(String orgName) {
		// System.out.println("set orgName"+orgName);
		this.orgName = orgName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getUser_status_code() {
		return user_status_code;
	}

	public void setUser_status_code(String user_status_code) {
		this.user_status_code = user_status_code;
	}

	public String getMinicrm_company_id() {
		return minicrm_company_id;
	}

	public void setMinicrm_company_id(String minicrm_company_id) {
		this.minicrm_company_id = minicrm_company_id;
	}

	public String getMinicrm_user_id() {
		return minicrm_user_id;
	}

	public void setMinicrm_user_id(String minicrm_user_id) {
		this.minicrm_user_id = minicrm_user_id;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}
