package com.olymtech.cas.util;

import java.util.HashMap;

import com.olymtech.integration.webservice.api.common.CommonSetting;
import com.olymtech.integration.webservice.api.common.ConstantLang;

public class ConstantValue {
	// 默认站点
	public static final String _defaultSiteNo = "6";
	// 保存用户的登录失败次数
	public static HashMap<String,Integer> _userLoginErrorCount = new HashMap<String,Integer>();
	//保存网上营业厅url
	public static HashMap<String,String> _siteBusinessUrl = new HashMap<String,String>();
	// 最大失败次数
	public static final int _maxErrorCount = 3;

	public static final String bizName = "biztrademarketWebService-hessian";
	public static final String memName = "membershipService-hessian-real";
	public static final String autoName = "autoWebService-hessian";
	
	public static final String _usePwd="usePwd";//使用用户密码登陆
	public static final String _useDytoken="useDytoken";//使用动态令牌登陆
	
	//匿名用户名和密码
	public static final String _defaultAnonymityName="admin@anonymity.com";
	
	public static final String _ZJEPOWER_SITENO = "1";
	
	public static final String _SESSION_VALID_IMG_CONTENT="validContent";
	
	public static final String _BIZ_HESSIAN_URL = "http://saas.800jit.com/comws/remoting/";
	
	public static final String _CAS_DOMAIN = "cas.800jit.com";
	
	//客户登录运营商的默认应用
	public static String _defaultClientAppCode="autoweb";//asp_serviceportal
	//默认ssocenter
	public static final String  _defaultSSOCode="ssocenter";
	//默认应用
	public static String  _defaultAppCode="asp_intranet";

	public static CommonSetting setting() {
		CommonSetting setting = new CommonSetting();
		setting.setLang(ConstantLang.zh_CN);
		setting.setDomain("www.biztrademarket.com");//www.800jit.com
		setting.setEncryptStr("biztrademarket");//800jit
		return setting;
	}
}
