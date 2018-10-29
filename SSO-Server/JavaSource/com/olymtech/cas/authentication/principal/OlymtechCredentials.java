package com.olymtech.cas.authentication.principal;

import org.jasig.cas.authentication.principal.Credentials;

public class OlymtechCredentials implements Credentials {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1537306541953446903L;
	
	private String username;//用户名
	private String password;//密码，可能被加密
	private String originalPassword;//原始密码
	
	private String validContent;//动态验证码
	private String dyToken;//动态令牌
	
	private String siteNo;
	private String domain;
	private String backURL;//自主建站用
	private String appType;
	private String errorURL;//出错后跳转地址
	
	private String serverName;//跳转域名
	private String serverPort = "80";//跳转端口
	private String webContent;//存放跳转到的应用名称
	private String toUrl;//最终跳转地址
	
	private String orgId;
	private String userId;
	
	private String key;//key 方式登录
	private String userInfo;//b2b
	private String fromOutSite = "false";//匿名登录标记 true 表示匿名登录
	
	private String loginType = "usePwd"; //useDytoken
	
	private String ipAddress;
	
	private String contextPath;
	
	private String option1;
	private String validContentSession;
	private String identity;
	
	private String JSID;  //remember what application client machine be called for logout 
	
	public String getJSID() {
		return JSID;
	}
	public void setJSID(String jSID) {
		JSID = jSID;
	}
	public String getFromOutSite() {
		return fromOutSite;
	}
	public void setFromOutSite(String fromOutSite) {
		this.fromOutSite = fromOutSite;
	}
	public String getServerPort() {
		return serverPort;
	}
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	public String getToUrl() {
		return toUrl;
	}
	public void setToUrl(String toUrl) {
		this.toUrl = toUrl;
	}
	public String getOriginalPassword() {
		return originalPassword;
	}
	public void setOriginalPassword(String originalPassword) {
		this.originalPassword = originalPassword;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getValidContentSession() {
		return validContentSession;
	}
	public void setValidContentSession(String validContentSession) {
		this.validContentSession = validContentSession;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getValidContent() {
		return validContent;
	}
	public void setValidContent(String validContent) {
		this.validContent = validContent;
	}
	public String getDyToken() {
		return dyToken;
	}
	public void setDyToken(String dyToken) {
		this.dyToken = dyToken;
	}
	public String getWebContent() {
		return webContent;
	}
	public void setWebContent(String webContent) {
		this.webContent = webContent;
	}
	public String getSiteNo() {
		return siteNo;
	}
	public void setSiteNo(String siteNo) {
		this.siteNo = siteNo;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getBackURL() {
		return backURL;
	}
	public void setBackURL(String backURL) {
		this.backURL = backURL;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getErrorURL() {
		return errorURL;
	}
	public void setErrorURL(String errorURL) {
		this.errorURL = errorURL;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
}
