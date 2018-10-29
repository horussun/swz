package com.olymtech.cas.sso;

import java.io.Serializable;

public class AppAccessLog implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5101072012530225048L;
    private String logID; // sequence
    private String userId; // 登录序列
    private String orgId; // 所属组织id
    private String loginTime; // 登陆时间
    private String logoutTime; // 登出时间
    private String ipAddress; // ip地址
    private String loginStatus; // 登陆标志
    private String loginSiteno; // 登陆站点

    public String getLogID() {
	return logID;
    }

    public void setLogID(String logID) {
	this.logID = logID;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getOrgId() {
	return orgId;
    }

    public void setOrgId(String orgId) {
	this.orgId = orgId;
    }

    public String getLoginTime() {
	return loginTime;
    }

    public void setLoginTime(String loginTime) {
	this.loginTime = loginTime;
    }

    public String getLogoutTime() {
	return logoutTime;
    }

    public void setLogoutTime(String logoutTime) {
	this.logoutTime = logoutTime;
    }

    public String getIpAddress() {
	return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
	this.ipAddress = ipAddress;
    }

    public String getLoginStatus() {
	return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
	this.loginStatus = loginStatus;
    }

    public String getLoginSiteno() {
	return loginSiteno;
    }

    public void setLoginSiteno(String loginSiteno) {
	this.loginSiteno = loginSiteno;
    }
}
