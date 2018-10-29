package com.olymtech.cas.sso;

import java.io.Serializable;
import java.util.Date;

public class LoginResult implements Serializable {
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = -3056823523026159265L;

	/// <summary>
	  /// biz用户ID
	  /// </summary>
	  private int userId;

      /// <summary>
      /// 用户名称
      /// </summary>
      private String userName;
      
      /// <summary>
      /// 800JIT数据库里的掌柜号
      /// </summary>
      private int jitNo;

      /// <summary>
      /// 800JIT数据库里的公司ID
      /// </summary>
      private int jitOrgId;

      /// <summary>
      /// 800JIT数据库里的用户ID
      /// </summary>
      private int jitUserId;

      /// <summary>
      /// SessionId
      /// </summary>
      private String sessionId;

      /// <summary>
      /// 登录时间
      /// </summary>
      private Date loginTime;
  
      /// <summary>
      /// 是否为货代  1 货代  2货主
      /// </summary>
      private int isClientorHostOrg;
      /// <summary>
      /// b2b用户邮箱
      /// </summary>      
      private String userMail;
       

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int bizUserId) {
		this.userId = bizUserId;
	}

	public int getJitNo() {
		return jitNo;
	}

	public void setJitNo(int jitNo) {
		this.jitNo = jitNo;
	}

	public int getJitOrgId() {
		return jitOrgId;
	}

	public void setJitOrgId(int jitOrgId) {
		this.jitOrgId = jitOrgId;
	}

	public int getJitUserId() {
		return jitUserId;
	}

	public void setJitUserId(int jitUserId) {
		this.jitUserId = jitUserId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getIsClientorHostOrg() {
		return isClientorHostOrg;
	}

	public void setIsClientorHostOrg(int isClientorHostOrg) {
		this.isClientorHostOrg = isClientorHostOrg;
	}
      
      
      


	
	
	

}
