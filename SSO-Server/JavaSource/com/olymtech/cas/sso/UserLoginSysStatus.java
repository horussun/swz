package com.olymtech.cas.sso;

import java.io.Serializable;

public class UserLoginSysStatus implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 2858350530920884147L;

    private String userId; // 登录名序列

    private String subSysName; // ip地址

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getSubSysName() {
	return subSysName;
    }

    public void setSubSysName(String subSysName) {
	this.subSysName = subSysName;
    }
}
