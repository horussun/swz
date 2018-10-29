package com.olymtech.app.ssocenter.old;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.olymtech.cas.util.StringUtil;

public class UserIDCookie {

	HttpServletRequest req;

	HttpServletResponse res;

	String strKeyPath = null;

	Cookie _cookie = null;

	String userCookieName = "ssoCookie";

	public String _ssoUserAppCookieName = "ssoUserAppCookie";
	public String _ssoLogoutURLCookieName = "ssoLogoutURLCookie";
	public String _ssoSiteNoCookieName = "ssoSiteNoCookie";
	public String _ssoLoginSiteNoCookieName = "ssoLoginSiteNoCookie";
	public String _ssoOrgIdCookieName = "ssoOrgIdCookie";
	public String _ssoUserIdCookieName = "ssoUserIdCookie";
	public String _ssORequestHostDomain = "ssORequestHostDomain";
	public String _ssoRememberMe = "ssoRemberMe";
	
	public String _ssoLocalCookieUserName = "ssoLocalCookieUserName";
	
	//int iTimeOutSpan = 3600 * 24 * 30;
	int iTimeOutSpan = -1;
	int rememberTimeOut = 3600 * 24 * 30;

	//int iTimeOutSpan = 30*24*60*60;
	
	
	String strPath = "/";

	/**
	 * 生成一个Cookie
	 * 
	 * @param req
	 * @param res
	 */
	public UserIDCookie(HttpServletRequest req, HttpServletResponse res) {
		this.req = req;
		this.res = res;
	}

	/**
	 * 把用户ID写入Cookie并且写到客户端 save the user ID to Cookie,and write it to desk
	 * 
	 * @param userID
	 */
	public void setUserInfo(String userID, String keyFilePath, String domainName) {
		//userCookieName==ssoCookie
		setCookieUserName(userID, keyFilePath, domainName,userCookieName,iTimeOutSpan);
	}
	
	/**
	 * 把OrgID写入Cookie并且写到客户端 save the user ID to Cookie,and write it to desk
	 * 
	 * @param userID
	 */
	public void setOrgInfo(String orgId, String domainName) {
		Cookie org_cookie = new javax.servlet.http.Cookie(_ssoOrgIdCookieName, orgId);
		org_cookie.setMaxAge(iTimeOutSpan);
		if (domainName != null) {
			org_cookie.setDomain(domainName);
		}
		org_cookie.setPath(strPath);
		res.addCookie(org_cookie);
	}
	

	/**
	 * 把UserID写入Cookie并且写到客户端 save the user ID to Cookie,and write it to desk
	 * 
	 * @param userID
	 */
	public void setUserId(String userId, String domainName) {
		Cookie user_cookie = new javax.servlet.http.Cookie(_ssoUserIdCookieName, userId);
		user_cookie.setMaxAge(iTimeOutSpan);
		if (domainName != null) {
			user_cookie.setDomain(domainName);
		}
		user_cookie.setPath(strPath);
		res.addCookie(user_cookie);
	}


	
	/**
	 * 把用户ID Cookie并且写到客户端 save the user ID to Cookie,and write it to desk
	 * 保存一个月
	 * @param userID
	 * @param keyFilePath
	 * @param domainName
	 */
	public void setLocalCookieUserName(String userID, String keyFilePath, String domainName) {
		setCookieUserName(userID, keyFilePath, domainName,_ssoLocalCookieUserName,rememberTimeOut);
	}
	
	/**
	 * 把用户写入Cookie并且写到客户端 save the user ID to Cookie,and write it to desk
	 * @param userName
	 * @param keyFilePath
	 * @param domainName
	 */
	public void setCookieUserName(String userName, String keyFilePath, String domainName,String cookieName,int iTimeOutSpan) {
		//添加时间戳
		//Date date = new Date();
		//long fix=date.getTime();
		//统一使用数据库时间来标记用户登录时刻的时间，因为应用服务器时间经常会出现不一致的情况
		//Timestamp now =StringUtil.getDataBaseNowTime();
		Timestamp now=new Timestamp(Calendar.getInstance().getTimeInMillis());
		
		userName=userName+"$"+now.getTime();
		
		Encryption e = Encryption.getInstance(Encryption.DES);
		try {
			e.importSecretKey(keyFilePath);
			byte[] cipherText = e.encrypt(userName.getBytes());
			String s = "";
			for (int i = 0; i < cipherText.length; i++) {
				s += String.valueOf((int) cipherText[i]) + "*";
			}
			s = s.substring(0, s.length() - 1);
			_cookie = new javax.servlet.http.Cookie(cookieName, s);
			_cookie.setMaxAge(iTimeOutSpan);
			if (domainName != null) {
				_cookie.setDomain(domainName);
			}
			_cookie.setPath(strPath);
			// }

			res.addCookie(_cookie);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}	
	
	

	/**
	 * 把Login Site No写入Cookie并且写到客户端 save the user ID to Cookie,and write it to desk
	 * 
	 * @param userID
	 */
	public void setLoginSiteNo(String LoginSiteNo ,String domainName) {
		Cookie loginSite_cookie = new javax.servlet.http.Cookie(_ssoLoginSiteNoCookieName, LoginSiteNo);
		loginSite_cookie.setMaxAge(iTimeOutSpan);
		if (domainName != null) {
			loginSite_cookie.setDomain(domainName);
		}
		loginSite_cookie.setPath(strPath);
		res.addCookie(loginSite_cookie);

	}
	
	/**
	 * 从Cookie中得到登陆的站点编号
	 * 
	 * @return 
	 */
	public String getLoginSiteNo() {
			_cookie = getCookie(_ssoLoginSiteNoCookieName);
			if (_cookie != null) {
				return _cookie.getValue();
			} else {
				return "";
			}
	}
	
	
	
	/**
	 * 把公司所属Site No写入Cookie并且写到客户端 save the user ID to Cookie,and write it to desk
	 * 
	 * @param userID
	 */
	public void setSiteNo(String siteNo ,String domainName) {
		Cookie site_cookie = new javax.servlet.http.Cookie(_ssoSiteNoCookieName, siteNo);
		site_cookie.setMaxAge(iTimeOutSpan);
		if (domainName != null) {
			site_cookie.setDomain(domainName);
		}
		site_cookie.setPath(strPath);
		res.addCookie(site_cookie);

	}
	
	/**
	 * 从Cookie中得到所属的站点
	 * 
	 * @return 
	 */
	public String getSiteNo() {
			_cookie = getCookie(_ssoSiteNoCookieName);
			if (_cookie != null) {
				return _cookie.getValue();
			} else {
				return "";
			}
		
	}
	public void setRemberMe(String domainName,String value){
		Cookie site_cookie = new javax.servlet.http.Cookie(_ssoRememberMe, value);
		site_cookie.setMaxAge(rememberTimeOut);
		if (domainName != null) {
			site_cookie.setDomain(domainName);
		}
		site_cookie.setPath(strPath);
		res.addCookie(site_cookie);
	}
	public String getRememberMe() {
		_cookie = getCookie(_ssoRememberMe);
		if (_cookie != null) {
			return _cookie.getValue();
		} else {
			return "";
		}
	
}
	


	/**
	 * 把请求的SSO的主机IP放入Cookie
	 * @param hostDomain
	 * @param domainName
	 */
	public void setSSORequestHostDomain(String hostDomain ,String domainName) {
		_cookie = new javax.servlet.http.Cookie(_ssORequestHostDomain, hostDomain);
		_cookie.setMaxAge(iTimeOutSpan);
		if (domainName != null) {
			_cookie.setDomain(domainName);
		}
		_cookie.setPath(strPath);
		res.addCookie(_cookie);
	}
	
	/**
	 * 得到请求的SSO的主机IP
	 * @return
	 */
	public String getSSORequestHostDomain() {
			_cookie = getCookie(_ssORequestHostDomain);
			if (_cookie != null) {
				return _cookie.getValue();
			} else {
				return "";
			}
	}	
	
	/**
	 * 把退出的URL写入Cookie并且写到客户端 save the user ID to Cookie,and write it to desk
	 * 
	 * @param userID
	 */
	public void setLogoutURL(String logoutURL ,String domainName) {
		_cookie = new javax.servlet.http.Cookie(_ssoLogoutURLCookieName, logoutURL);
		_cookie.setMaxAge(iTimeOutSpan);
		if (domainName != null) {
			_cookie.setDomain(domainName);
		}
		_cookie.setPath(strPath);
		res.addCookie(_cookie);

	}

	/**
	 * 从Cookie中得到退出的URL
	 * 
	 * @return userID
	 */
	public String getLogoutURL() {
			_cookie = getCookie(_ssoLogoutURLCookieName);
			if (_cookie != null) {
				return _cookie.getValue();
			} else {
				return "";
			}
		
	}
	
	/**
	 * 根据key文件从Cookie中得到用户信息
	 * 
	 * @return userID
	 */
	public String getUserInfo(String keyFilePath) {
		return getUserCookieInfo(keyFilePath,userCookieName);
	}
	
	/**
	 * 根据key文件从Cookie中得到用户信息,保存30天的Cookie
	 * @param keyFilePath
	 * @return
	 */
	public String getLocalCookieUserName(String keyFilePath) {
		return getUserCookieInfo(keyFilePath,_ssoLocalCookieUserName);
	}	
	

	/**
	 * 从Cookie名称中根据 加密文件取得用户信息
	 * @param keyFilePath
	 * @param cookieName
	 * @return
	 */
	private String getUserCookieInfo(String keyFilePath,String cookieName) {
		
		String userID="";		
		try {
			Encryption e = Encryption.getInstance(Encryption.DES);
			e.importSecretKey(keyFilePath);
			_cookie = getCookie(cookieName);
			if (_cookie != null && !_cookie.getValue().equals("")) {
				String[] s = split(_cookie.getValue(), '*');
				byte[] cipherText = new byte[s.length];
				for (int i = 0; i < s.length; i++) {
					cipherText[i] = (byte) Integer.parseInt(s[i]);
				}
				byte[] b = e.decrypt(cipherText);
				userID=new String(b).trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//去掉添加的时间戳
		if(userID.indexOf("$")!=-1){
			userID=userID.substring(0,userID.indexOf("$"));
		}
		return userID;
	}
	
	
	/**
	 * 根据key文件从Cookie的密文中取得用户信息  为了WebService调用的。
	 * 
	 * @return userID
	 */
	public static String getUserInfo(String keyFilePath,String cookieValue) {
		
		String userID="";		
		try {
			Encryption e = Encryption.getInstance(Encryption.DES);
			e.importSecretKey(keyFilePath);

			if (cookieValue != null) {
				String[] s = split(cookieValue, '*');
				byte[] cipherText = new byte[s.length];
				for (int i = 0; i < s.length; i++) {
					cipherText[i] = (byte) Integer.parseInt(s[i]);
				}
				byte[] b = e.decrypt(cipherText);
				userID=new String(b).trim();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		//去掉添加的时间戳
//		if(userID.indexOf("$")!=-1){
//			userID=userID.substring(0,userID.indexOf("$"));
//		}
		return userID;
	}
	
	/**
	 * 得到Cookie中的用户名的密文
	 * @return
	 */
	public String getCookieUserName(){
		_cookie = getCookie(userCookieName);
		if (_cookie != null) {
			return _cookie.getValue();
		} else {
			return "";
		}
	}
	
	/**
	 * 根据key文件从Cookie中得到用户信息（全部信息包括时间戳）
	 * 
	 * @return userID
	 */
	public String getAllUserInfo(String keyFilePath) {
		
		String userID="";
		
		try {
			Encryption e = Encryption.getInstance(Encryption.DES);
			e.importSecretKey(keyFilePath);
			_cookie = getCookie(userCookieName);
			if (_cookie != null && _cookie.getValue().length()>0) {
				String[] s = split(_cookie.getValue(), '*');
				byte[] cipherText = new byte[s.length];
				for (int i = 0; i < s.length; i++) {
					cipherText[i] = (byte) Integer.parseInt(s[i]);
					//System.out.println(cipherText[i]);
				}
				byte[] b = e.decrypt(cipherText);
				// byte[] b = plainText;

				userID=new String(b).trim();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userID;
	}	
	
	

	/**
	 * 使Cookie失效
	 */
	public void invalidate(String realDomain) {
		//修改时间:20081203 修改人:xj
		killCookie(userCookieName,realDomain);
		killCookie(_ssoUserAppCookieName,realDomain);
		killCookie(_ssoSiteNoCookieName,realDomain);
		killCookie(_ssoOrgIdCookieName,realDomain);
		killCookie(_ssoLogoutURLCookieName,realDomain);

	}
	
	private void killCookie(String name,String realDomain) {
		 Cookie   killMyCookie = new Cookie(name, null);   
        killMyCookie.setMaxAge(0);   
        killMyCookie.setPath("/");   
        if(realDomain!=null)killMyCookie.setDomain(realDomain);
        res.addCookie(killMyCookie);
	}
	
	public void killAllCookie() {
		try{
			String remember = URLDecoder.decode(getRememberMe(),"utf-8").split(":")[0];
			Cookie[] cookies = req.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if(StringUtil.transpose_blank(cookies[i].getName()).equals("userLanguage")
							||StringUtil.transpose_blank(cookies[i].getName()).equals(_ssoRememberMe))continue;
					if(remember.equals("true")&&StringUtil.transpose_blank(cookies[i].getName()).equals(_ssoLocalCookieUserName))continue;
					 Cookie   killMyCookie = new Cookie(cookies[i].getName(), null);   
			         killMyCookie.setMaxAge(0);   
			         killMyCookie.setPath("/");   
			         res.addCookie(killMyCookie);
				}
			}
		}catch(Exception e){}
	}
	

	public static void main(String[] args) {

	}

	/**
	 * @return Returns the iTimeOutSpan.
	 */
	public int getMaxAge() {
		return iTimeOutSpan;
	}

	/**
	 * 设置Cookie的最长生命周期
	 * 
	 * @param timeOutSpan
	 */
	public void setMaxAge(int timeOutSpan) {
		iTimeOutSpan = timeOutSpan;
	}

	
	/**
	 * 判断Cookie中用户名能不能取得，也就是判断Cookie是否存在
	 * @param keyFilePath
	 * false :不存在  
	 * @return
	 */
	public boolean isCookieExisted(String keyFilePath) {
		String userName = this.getUserInfo(keyFilePath);
		if (userName == null || userName.trim().length()==0)
			return false;
		else 
			return true;
	}

	/**
	 * 返回路径
	 * 
	 * @return Returns the strPath.
	 */
	public String getStrPath() {
		return strPath;
	}

	/**
	 * 根据cookie名取得Cookie
	 * 
	 * @param strCookieName
	 * @return
	 */
	public Cookie getCookie(String strCookieName) {
		Cookie result = null;
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(strCookieName)){
					result = cookies[i];
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 设置路径
	 * 
	 * @param strPath
	 *            The strPath to set.
	 */
	public void setStrPath(String strPath) {
		this.strPath = strPath;
		_cookie = getCookie(this.userCookieName);
		_cookie.setPath(strPath);
	}

	/**
	 * 计算字符串ptr中有多少个char c
	 * @param ptr
	 * @param c
	 * @return
	 */
	public static int count(String ptr, char c) {
		int coun = 0, pos = 0;
		while ((pos = ptr.indexOf(c, pos)) != -1) {
			coun++;
			pos++;
		}
		return coun;
	}

	/**
	 * 分割
	 * @param src
	 * @param separator
	 * @return
	 */
	public static String[] split(String src, char separator) {

		if (src == null)
			return null;
		else
			src = src.trim();
		int sprtCount = count(src, separator);
		if (sprtCount == 0) {
			String[] det = new String[1];
			det[0] = src;
			return det;
		}
		String[] det = new String[sprtCount + 1];
		int indexs = 0, indexe = 0;
		for (int i = 0; i <= sprtCount; i++) {
			indexe = src.indexOf(separator, indexs);
			if (indexe == -1)
				det[i] = src.substring(indexs);
			else {
				det[i] = src.substring(indexs, indexe);
				indexs = indexe + 1;
			}
		}
		return det;
	}
	
	

}