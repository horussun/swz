package com.olymtech.cas.authentication.handler;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.client.HessianProxyFactory;
import com.olymtech.cas.authentication.principal.OlymtechCredentials;
import com.olymtech.cas.sso.AppAccessLog;
import com.olymtech.cas.sso.AppAccessLogManager;
import com.olymtech.cas.sso.BaseDBOperate;
import com.olymtech.cas.sso.DyTokenManager;
import com.olymtech.cas.sso.JsonToObject;
import com.olymtech.cas.sso.LoginResult;
import com.olymtech.cas.sso.OneSiteParmDetail;
import com.olymtech.cas.sso.OneWebAppConfigDetail;
import com.olymtech.cas.sso.User;
import com.olymtech.cas.util.ConstantValue;
import com.olymtech.cas.util.SecurityValidate;
import com.olymtech.cas.util.StringUtil;
import com.olymtech.integration.webservice.api.biztrademarket.IBiztrademarketWebService;
import com.olymtech.integration.webservice.api.common.CommonSetting;
import com.olymtech.integration.webservice.api.membership.IMembershipService;
import com.olymtech.integration.webservice.api.membership.entity.JitB2bUserRelaEntity;
import com.olymtech.integration.webservice.api.membership.entity.OrganizationInfoEntity;
import com.olymtech.integration.webservice.api.membership.entity.UserInfoEntity;

/**
 * login logic check
 * @author Administrator
 *
 */
public class SSOUserCheckHandler extends BaseDBOperate {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public boolean ssoUserCheck(Connection conn, OlymtechCredentials credentials) throws Exception {
	boolean returnValue = false;
	String localUserName = credentials.getUsername();// username or jit_user_no
	String password = credentials.getPassword();
	String siteNo = credentials.getSiteNo();
	String serverName = credentials.getServerName();
	String key = credentials.getKey();
	String userInfo = credentials.getUserInfo();// b2b login
	String loginType = credentials.getLoginType();
	String dyToken = credentials.getDyToken();
	@SuppressWarnings("unused")
	String ipAddress = credentials.getIpAddress();
	String validContent = credentials.getValidContent();
	@SuppressWarnings("unused")
	String contextPath = credentials.getContextPath();
	@SuppressWarnings("unused")
	String appType = credentials.getAppType();
	String option1 = credentials.getOption1();
	String validContentSession = credentials.getValidContentSession();
	String backURL = credentials.getBackURL();

	String username = localUserName, userId, orgId, orgSiteNo = null, toURL = "";
	try {
	    // check
	    User user = null;
	    // 根据访问域名得到站点
	    siteNo = getSiteNo(conn, siteNo, serverName);
	    credentials.setSiteNo(siteNo);
	    // 某一用户名错误登陆次数
	    int count = ConstantValue._userLoginErrorCount.get(username) == null ? 0 : (Integer) ConstantValue._userLoginErrorCount.get(username);
	    // 判断有没有填写用户名和密码；验证码；动态令牌
	    if (!StringUtil.isEmpty(localUserName) && !StringUtil.isEmpty(password)) {
		// check username and password
		if ((user = checkUserByBase(conn, localUserName, password, loginType, dyToken)) == null) {
		    count = countUserLogintimes(localUserName, username, count, loginType);
		    return false;
		}

		// 验证码
		validContent = StringUtil.transpose_blank(validContent).toLowerCase();
		if (count > ConstantValue._maxErrorCount && validContent.equals("")) {// 如果最后一次是失败的且没有输入验证码提示输入验证码
		    return false;
		}
		ConstantValue._userLoginErrorCount.put(localUserName, 0);

		// 判断是否使用动态验证码登陆并验证

		if (validImgContentCheck(validContent, option1, validContentSession) == false) {
		    ConstantValue._userLoginErrorCount.put(localUserName, ++count);
		    return false;
		}

		// 判断是否为key登陆
	    } else if ((StringUtil.isEmpty(localUserName) || StringUtil.isEmpty(password)) && !StringUtil.isEmpty(key)) {
		if ((user = checkUserByLoginKey(conn, key)) == null) {
		    return false;
		}
		// 如果b2b登陆信息为空 return false;
	    } else if (!StringUtil.isEmpty(userInfo)) {
		if ((user = checkUserByB2B(conn, userInfo, siteNo, serverName)) == null) {
		    return false;
		}
		// get org id for b2b info has no orgid
		user.setOrgSiteNo(getCurrentDirectSiteNo(conn, user.getUserId()));
	    } else {
		count = countUserLogintimes(localUserName, username, count, loginType);
		return false;
	    }

	    username = user.getUsername();
	    credentials.setUsername(username);// for jit_no translate user name
	    userId = user.getUserId();
	    orgId = user.getOrgid();
	    orgSiteNo = user.getOrgSiteNo();

	    // remember user login trace

	    accessLogs(conn, credentials.getIpAddress(), userId, orgId, orgSiteNo);

	    // check end

	    // redirect
	    // 如果是超级管理员，那么哪里都能登陆
	    boolean isSupperAdmin = isSupperAdmin(conn, username);
	    // 判断是否是匿名用户
	    boolean isAnonymityUser = isAnonymityUser(conn, username);
	    // 判断是不是货主公司
	    String hostOrgID = getOrgIdBySiteNo(conn, orgSiteNo);
	    boolean isClient = isClientOrg(conn, orgId, hostOrgID);
	    // 判断登陆：如果不是同一个站点就不允许登陆，除非从浙江epower登陆的客户，暂时还不限制。
	    // 现在允许范围是"登陆的站点是公司所属的站点"、货主等货代公司的站点
	    if (isSupperAdmin == false && isAnonymityUser == false && !StringUtil.isEmpty(siteNo)) {
		if (!orgSiteNo.equals(siteNo)) {
		    // 登陆的站点不是自己公司的站点列表中的一个那么不允许登陆
		    ArrayList<String> myOrgSiteList = isClient ? getMyOrgSiteList(conn, hostOrgID) : getMyOrgSiteList(conn, orgId);
		    // if (isClient)
		    // myOrgSiteList.add("6");
		    if (!isAutoWebManager(conn, userId) && !myOrgSiteList.contains(siteNo)) {
			return false;
		    }
		}
	    }

	    // site info where to go and where to login redirect
	    OneSiteParmDetail ospd = getSiteConfigInfoList(conn, siteNo);
	    if (ospd == null)
		return false;
	    String siteDomain = StringUtil.isEmpty(serverName) ? ospd.getSiteDomain() : serverName + (":" + credentials.getServerPort());

	    // check is or not goto ffx

	    String logciapp = isGoFFxbyUSER(conn, userId) == null ? isGoFFxbyORG(conn, userId) : isGoFFxbyUSER(conn, userId);
	    if (logciapp != null) {
		toURL = "http://" + siteDomain + "/ffx?app=" + logciapp;
		credentials.setToUrl(toURL);
		return true;
	    }

	    if (isAnonymityUser == false) {
		String client_org_id = orgId;
		String loginSiteNo = siteNo;
		String toAppCode = getConfigAppCodeByUserId(conn, loginSiteNo, client_org_id, orgId, userId);
		List<String> list = getChildOrgListByHostOrg(conn, hostOrgID);
		list.add(hostOrgID);
		List<String> clientList = getOrgListByClientOrg(conn, client_org_id);
		String toSite = getConfigToSite(conn, orgSiteNo, client_org_id);
		String operator_site = siteDomain;
		// 是否需要验证mac
		boolean need_check = user.getIs_mac_confirm().equals("1");

		// 如果是货主公司，toURL置为货代门户(自助建站上的网上营业厅)
		if (isClient) {
		    // 如果是货主判断货主状态是否有效,如果总公司下所有了公司对应的
		    if (clientList.size() < 1)
			return false;
		    else {
			boolean flag = false;
			for (Object object : clientList) {
			    if (list.contains(object)) {
				flag = true;
				break;
			    }
			}
			if (!flag)
			    return false;
		    }
		    // 如果登录用户是货主且config.operator_site_org_rela无设置，toAppCode默认为asp_serviceportal
		    if (StringUtil.isEmpty(toAppCode)) {
			toAppCode = ConstantValue._defaultClientAppCode;
		    }

		    if (StringUtil.isEmpty(toURL)
			    || toURL.equalsIgnoreCase("http://" + siteDomain + "/" + getWebAppConfigInfo(conn, "autoweb").getWebAppContext() + "/")) {
			toURL = "http://" + siteDomain + "/serviceportal/applogin";
		    }
		}

		if (!StringUtil.isEmpty(backURL)) {// 自主建站特殊处理，直接进入设置界面
		    toURL = backURL;
		    credentials.setToUrl(toURL);
		    return true;
		}

		// 默认站点应用是自助建站,取站点应用,go to modelhome for news publish
		if (!StringUtil.isEmpty(toAppCode) && toAppCode.equals("autoweb") && !isClient) {
		    toURL = "http://" + siteDomain + "/modelhome/applogin";
		    credentials.setToUrl(toURL);
		    return true;

		}

		// 如果登录用户没有设置过站点默认应用登录并该用户不属于货主，则重定向到应用选择界面
		if (StringUtil.isEmpty(toAppCode) && !isClient) {
		    toURL = "http://" + siteDomain + "/membership/frame_themes/default/selectedAppList.jsp";
		    credentials.setToUrl(toURL);
		    return true;
		}
		// 得到当前站点对应的默认登陆应用代码
		if (StringUtil.isEmpty(toAppCode)) {
		    toAppCode = ospd.getDefault_app_code();
		}
		// 如果站点没有配置对应的默认应用，那么使用当前web.xml中配置的default_app_code
		// 作为登陆的默认应用
		if (StringUtil.isEmpty(toAppCode)) {
		    toAppCode = ConstantValue._defaultAppCode;
		}

		OneWebAppConfigDetail owcd = getWebAppConfigInfo(conn, toAppCode);
		// toSite存在且和含orgSiteNo同域
		if (!StringUtil.isEmpty(toSite) && toSite.contains(operator_site)) {
		    toURL = "http://" + toSite + "/" + owcd.getWebAppContext() + owcd.getWebAppLoginURLMapping();
		}

		if (StringUtil.isEmpty(toURL)) {
		    toURL = "http://" + siteDomain + "/" + owcd.getWebAppContext() + owcd.getWebAppLoginURLMapping();
		}

		if (!need_check) {// 不需要验证mac，正常登录
		    // 是否要进行密码安全验证
		    /*
		     * if (!StringUtil.isEmpty(password)) { if
		     * (isSecurity(credentials.getOriginalPassword())) { } else
		     * { toURL = "http://" + siteDomain +
		     * "/membership/frame_themes/default/passwordhint.jsp?backurl="
		     * + "&toURL=" + toURL; credentials.setToUrl(toURL); return
		     * true; } } else { }
		     */

		} else if (need_check) {// 需要验证mac地址
		    // 公司管理员为用户设置的mac地址串（可能是几个）
		    boolean arg_redirect = StringUtil.isEmpty(backURL);
		    if (arg_redirect) {
			if (!toAppCode.equals("autoweb")) {
			    if (toURL.indexOf("?") < 0) {
				toURL += "?isSSODirect=true";
			    } else {
				toURL += "&isSSODirect=true";
			    }
			}
		    }
		    String mac_adds = user.getMac_address();
		    String get_mac_url = "http://" + siteDomain + "/membership/get_mac_address.jsp?" + "tourl=" + toURL + "&backurl=" + backURL
			    + "&macaddress=" + mac_adds + "&toAppCode=" + toAppCode + "&arg_redirect=" + arg_redirect;

		    credentials.setToUrl(get_mac_url);
		    return true;
		}

	    } else {
		// 匿名登录
		if (StringUtil.isEmpty(backURL)) {
		    toURL = "http://" + siteDomain + "/serviceportal/applogin?isSSODirect=true";
		} else {
		    toURL = StringUtil.replaceString(backURL, "$$", "&");
		}
	    }
	    credentials.setToUrl(toURL);
	    // redirect end

	    returnValue = true;
	} catch (Exception e) {
	    returnValue = false;
	    throw e;
	} finally {
	}
	return returnValue;
    }

    private void accessLogs(Connection conn, String ipAddress, String userId, String orgId, String orgSiteNo) {
	try {
	    AppAccessLogManager appAccessLogManager = new AppAccessLogManager();
	    AppAccessLog actionLog = new AppAccessLog();
	    actionLog.setUserId(userId);
	    actionLog.setOrgId(orgId);
	    actionLog.setIpAddress(ipAddress);
	    actionLog.setLoginSiteno(orgSiteNo);
	    appAccessLogManager.doInsert(conn, actionLog);
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	}
    }

    /**
     * @param conn
     * @param userId
     * @return
     * @throws SQLException
     */
    private String isGoFFxbyORG(Connection conn, String userId) throws SQLException {
	String sqlStr = "select c.logicapp_code from mem_org_logicapp_rela a,mem_user b,mem_logic_application c where a.org_id = b.org_id and a.logicapp_id = c.logicapp_id and b.user_id = ? ";
	PreparedStatement stmt = null;
	ResultSet rs = null;
	String isFFX = null;
	try {
	    stmt = conn.prepareStatement(sqlStr);
	    stmt.setString(1, userId);
	    rs = stmt.executeQuery();
	    if (rs.next()) {
		isFFX = rs.getString("logicapp_code");
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(stmt, rs);
	}
	return isFFX;
    }

    /**
     * 
     * @param conn
     * @param userId
     * @return
     * @throws SQLException
     * @throws SQLException
     */
    private String isGoFFxbyUSER(Connection conn, String userId) throws SQLException {
	String sqlStr = "select b.logicapp_code from mem_logicapp_user_rela a,mem_logic_application b where a.logicapp_id = b.logicapp_id and user_id = ? ";
	PreparedStatement stmt = null;
	ResultSet rs = null;
	String isFFX = null;
	try {
	    stmt = conn.prepareStatement(sqlStr);
	    stmt.setString(1, userId);
	    rs = stmt.executeQuery();
	    if (rs.next()) {
		isFFX = rs.getString("logicapp_code");
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(stmt, rs);
	}
	return isFFX;
    }

    /*
     * private boolean isSecurity(String str) { return
     * str.matches("(\\d{1,6})|([a-zA-Z]{1,6})") ? false : true; }
     */
    /**
     * 通过站点编号得到站点的网上营业厅url
     * 
     * @param siteNo
     * @return
     */
    /*
    private String getSiteBusinessUrlBySiteNo(String siteNo) {
	String businessUrl = "";
	try {
	    if (ConstantValue._siteBusinessUrl.get(siteNo) != null) {// 如果内存中有当前站点的url则从内存中取
		businessUrl = (String) ConstantValue._siteBusinessUrl.get(siteNo);
	    } else {// 如果内存中没有url就调用服务
		String hessianUrl = ConstantValue._BIZ_HESSIAN_URL + ConstantValue.autoName;
		HessianProxyFactory factory = new HessianProxyFactory();
		IAutoWebService autoWebService = (IAutoWebService) factory.create(IAutoWebService.class, hessianUrl);
		businessUrl = autoWebService.getSpecifiedPageUrlBySiteno(siteNo);
		// System.out.println("businessUrl:" + businessUrl);
	    }
	} catch (Exception e) {
	    // e.printStackTrace();

	}
	return businessUrl;
    }
    */

    /**
     * 得到所有可用的web应用的信息
     * 
     * @return
     * @throws SQLException
     */
    private OneWebAppConfigDetail getWebAppConfigInfo(Connection conn, String appCode) throws SQLException {
	OneWebAppConfigDetail entry = new OneWebAppConfigDetail();
	String sqlStr = " select webAppContext,webAppCode,webAppId,webAppSchema,webAppTheme,webAppLoginURL,"
		+ " webAppLogoutURL,webAppLoginURLMapping,webAppWSURL,webAppWSPort,webAppProvider_en,webAppProvider_zh,webAppLogoImgURL,webAppHelpURL "
		+ "	from asp_config.webAppConfig" + "	where status='1' and webAppCode = ?";
	PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
	    stmt = conn.prepareStatement(sqlStr);
	    stmt.setString(1, appCode);
	    rs = stmt.executeQuery();
	    if (rs.next()) {
		entry.setWebAppContext(StringUtil.transpose_blank(rs.getString("webAppContext")));
		entry.setWebAppCode(StringUtil.transpose_blank(rs.getString("webAppCode")));
		entry.setWebAppId(StringUtil.transpose_blank(rs.getString("webAppId")));
		entry.setWebAppSchema(StringUtil.transpose_blank(rs.getString("webAppSchema")));
		entry.setWebAppTheme(StringUtil.transpose_blank(rs.getString("webAppTheme")));
		entry.setWebAppLoginURL(StringUtil.transpose_blank(rs.getString("webAppLoginURL")));
		entry.setWebAppLogoutURL(StringUtil.transpose_blank(rs.getString("webAppLogoutURL")));
		entry.setWebAppLoginURLMapping(StringUtil.transpose_blank(rs.getString("webAppLoginURLMapping")));
		entry.setWebAppWSURL(StringUtil.transpose_blank(rs.getString("webAppWSURL")));
		entry.setWebAppWSPort(StringUtil.transpose_blank(rs.getString("webAppWSPort")));
		entry.setWebAppProvider_en(StringUtil.transpose_blank(rs.getString("webAppProvider_en")));
		entry.setWebAppProvider_zh(StringUtil.transpose_blank(rs.getString("webAppProvider_zh")));
		entry.setWebAppLogoImgURL(StringUtil.transpose_blank(rs.getString("webAppLogoImgURL")));
		entry.setWebAppHelpURL(StringUtil.transpose_blank(rs.getString("webAppHelpURL")));
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(stmt, rs);
	}
	return entry;
    }

    /**
     * 从表asp_config.to_site_definition中得到默认的目的域名
     * 
     * @param operator_siteNo
     * @param client_org_id
     * @return
     * @throws SQLException
     */
    private String getConfigToSite(Connection conn, String operator_siteNo, String client_org_id) throws SQLException {
	String sqlStr = "select b.sitedomain from asp_config.to_site_definition a,asp_config.site b where a.to_siteno = b.siteno and a.operator_siteno = ? and a.client_org_id = ? ";
	PreparedStatement stmt = null;
	ResultSet rs = null;
	String to_site = null;
	try {
	    stmt = conn.prepareStatement(sqlStr);
	    stmt.setString(1, operator_siteNo);
	    stmt.setString(2, client_org_id);
	    rs = stmt.executeQuery();
	    if (rs.next()) {
		to_site = rs.getString("sitedomain");
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(stmt, rs);
	}
	return to_site;
    }

    /**
     * 所有货主公司所对应的货代公司
     * 
     * @param clientOrgId
     * @return
     * @throws SQLException
     */
    private List<String> getOrgListByClientOrg(Connection conn, String clientOrgId) throws SQLException {
	PreparedStatement stmt = null;
	ResultSet rs = null;
	List<String> list = new ArrayList<String>();
	try {
	    String strQuery = "SELECT org_id from membership.mem_org_client_rela where client_org_id = ? and status='1'";
	    stmt = conn.prepareStatement(strQuery);
	    stmt.setString(1, clientOrgId);
	    rs = stmt.executeQuery();
	    while (rs.next()) {
		String orgId = rs.getString("org_id");
		list.add(orgId);
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(stmt, rs);
	}
	return list;
    }

    /**
     * 得到总公司的所有子公司
     * 
     * @param hostOrgId
     * @return
     * @throws SQLException
     */

    private List<String> getChildOrgListByHostOrg(Connection conn, String hostOrgId) throws SQLException {
	PreparedStatement stmt = null;
	ResultSet rs = null;
	List<String> list = new ArrayList<String>();
	try {
	    String strQuery = "SELECT org_id from membership.mem_organization where parent_org_id = ?";
	    stmt = conn.prepareStatement(strQuery);
	    stmt.setString(1, hostOrgId);
	    rs = stmt.executeQuery();
	    while (rs.next()) {
		String orgId = rs.getString("org_id");
		list.add(orgId);
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(stmt, rs);
	}
	return list;
    }

    private String getConfigAppCodeByUserId(Connection conn, String operator_siteNo, String client_org_id, String org_id, String userId) throws SQLException {
	String sqlStr = "select app_code from asp_config.operator_site_org_user_rela where operator_siteNo =? and client_org_id = ? and org_id=? and user_id=?";
	PreparedStatement ps = null;
	ResultSet rs = null;
	String app_code = null;
	try {
	    ps = conn.prepareStatement(sqlStr);
	    int i = 1;
	    ps.setString(i++, operator_siteNo);
	    ps.setString(i++, client_org_id);
	    ps.setString(i++, org_id);
	    ps.setString(i++, userId);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		app_code = rs.getString("app_code");
	    }

	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(ps, rs);
	}
	return app_code;
    }

    private String getCurrentDirectSiteNo(Connection conn, String userId) throws SQLException {
	String sqlStr = "select siteno " + "	from mem_organization o,mem_user u  " + "	where user_id=? and u.org_id = o.org_id ";
	PreparedStatement ps = null;
	ResultSet rs = null;
	String siteNo = null;
	try {
	    ps = conn.prepareStatement(sqlStr);
	    ps.setString(1, userId);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		siteNo = rs.getString("siteno");
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(ps, rs);
	}
	return siteNo;
    }

    private int countUserLogintimes(String localUserName, String username, int count, String loginType) {
	if (username != null && !username.equals(ConstantValue._defaultAnonymityName) && !loginType.equals(ConstantValue._useDytoken))
	    ConstantValue._userLoginErrorCount.put(localUserName, ++count);
	return count;
    }

    /**
     * 
     * @param conn
     * @param userInfo
     * @param siteNo
     * @param serverName
     * @return
     * @throws JSONException
     * @throws MalformedURLException
     * @throws SQLException
     */
    private User checkUserByB2B(Connection conn, String userInfo, String siteNo, String serverName) throws JSONException, MalformedURLException, SQLException {
	User user = null;
	// 解密
	SecurityValidate securityvalidate = new SecurityValidate();
	String DcryptStr = "";
	DcryptStr = securityvalidate.decrypt(userInfo);
	LoginResult lr = new LoginResult();
	JSONObject js2obj = (JSONObject) JsonToObject.getJSONObject(DcryptStr);
	try {
	    if (js2obj.getString("UserId") == null || js2obj.getString("UserId").equals("0")) {
		user = new User();
		user.setUserId(js2obj.getString("JitUserId"));
		user.setUsername(js2obj.getString("UserName"));
		user.setOrgid(js2obj.getString("JitOrgId"));
	    } else {
		lr.setUserId(js2obj.getInt("UserId"));
		lr.setUserName(js2obj.getString("UserName"));
		Zhimiao.biztrademarket.WebService.Entities.LoginResult loginresult = new Zhimiao.biztrademarket.WebService.Entities.LoginResult();
		try {
		    BeanUtils.copyProperties(loginresult, lr);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		// 二次安全验证
		if (validateSecurity(loginresult)) {
		    user = b2bUserInfoValid(conn, siteNo, lr, serverName);
		}
	    }

	} catch (JSONException e) {
	    throw e;
	    // e1.printStackTrace();
	}

	return user;
    }

    /*
	 * 
	 */
    private User checkUser(Connection conn, String username, String password) throws SQLException {
	User user = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	// 一定要是活动状态的公司才能是有效用户
	StringBuffer sql = new StringBuffer(" select a.user_id,a.user_name,c.org_id,c.siteno,a.is_mac_confirm,a.mac_address ").append(
		" from mem_user a,mem_user_status_list b, mem_organization c,mem_org_status_list d ").append(" where a.user_status_id = b.user_status_id ")
		.append(" and b.user_status_code = 'active' ").append(" and a.org_id = c.org_id ").append(" and c.org_status_id = d.org_status_id ").append(
			" and d. org_status_code = 'active' ").append(" and (upper(a.user_name) = ? or a.jit_user_no = ?) ").append(" and a.password  = ? ");
	try {
	    ps = conn.prepareStatement(sql.toString());
	    ps.setString(1, username.toUpperCase());
	    ps.setString(2, username);
	    ps.setString(3, password);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		user = new User();
		user.setUserId(rs.getString("user_id"));
		user.setUsername(rs.getString("user_name"));
		user.setOrgid(rs.getString("org_id"));
		user.setOrgSiteNo(rs.getString("siteno"));
		user.setIs_mac_confirm(rs.getString("is_mac_confirm"));
		user.setMac_address(rs.getString("mac_address"));
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(ps, rs);
	}
	return user;
    }

    /**
     * 验证码检查
     * 
     * @param request
     * @return 没有通过返回 false
     */
    private boolean validImgContentCheck(String validContent, String option1, String validContentSession) {
	// 如果输入的验证码和Session中的不同那么就认为不对，进入重新登陆页面
	validContent = StringUtil.transChina(validContent).toLowerCase();
	option1 = StringUtil.transpose_blank(option1);
	if (option1.equals("1") && StringUtil.isEmpty(validContent) || (!StringUtil.isEmpty(validContent) && !validContent.equals(validContentSession))) {
	    return false;
	}
	return true;

    }

    /**
     * add by kxj 20080527 得到已知站点配置的信息
     * 
     * @return
     * @throws Exception
     */
    public OneSiteParmDetail getSiteConfigInfoList(Connection conn, String siteNo) throws SQLException {
	OneSiteParmDetail entry = new OneSiteParmDetail();
	String sqlStr = " select a.siteNo,a.siteName,a.siteIP,a.cookieDomain,a.wwwDomain,a.siteDomain,a.cookieDomainPort,a.loginPageUrl,"
		+ " a.logoutUrl,a.org_id,a.site_error_page,a.isPublic,a.status,a.default_app_code,a.site_portal_app_code,a.site_service_phone,"
		+ " a.site_logo_content,a.site_background_img,a.site_service_im_user,"
		+ " b.org_id,b.org_name,b.org_name_en,b.address,b.fax,b.tel,b.postcode,b.email " + "	from asp_config.site a,membership.mem_organization b"
		+ "	where a.status='1' and a.org_id=b.org_id and a.siteno=?";
	PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
	    stmt = conn.prepareStatement(sqlStr);
	    stmt.setString(1, siteNo);
	    rs = stmt.executeQuery();
	    while (rs.next()) {
		entry = entry.setRsValueToEntity(rs);
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(stmt, rs);
	}
	return entry;
    }

    private boolean isAutoWebManager(Connection conn, String userId) throws SQLException {

	boolean isManager = false;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
	    String sqlStr = " select count(1) as cnt " + "	from mem_user_role_rela b,mem_role c " + "	where b.user_id=? " + "	and b.ORG_ID=c.ORG_ID"
		    + "	and b.APP_ID=c.APP_ID" + "	and (c.ROLE_CODE='autoweb_service' or c.ROLE_CODE='autoweb_develop')";
	    stmt = conn.prepareStatement(sqlStr);
	    stmt.setString(1, userId);
	    rs = stmt.executeQuery();
	    if (rs.next()) {
		int count = rs.getInt("cnt");
		if (count > 0)
		    isManager = true;
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(stmt, rs);
	}
	return isManager;
    }

    /**
     * 通过org_id 到站点表中得到对应的公司站点列表
     * 
     * @param userName
     * @return
     * @throws Exception
     */
    private ArrayList<String> getMyOrgSiteList(Connection conn, String orgId) throws SQLException {

	String sqlStr = "SELECT siteno " + " FROM asp_config.site s " + " WHERE (org_id = ? OR EXISTS (SELECT 1 " + " FROM membership.mem_organization m "
		+ " WHERE parent_org_id = s.org_id AND m.org_id = ?) " + " )";
	PreparedStatement stmt = null;
	ResultSet rs = null;
	ArrayList<String> retList = new ArrayList<String>();
	try {
	    stmt = conn.prepareStatement(sqlStr);
	    stmt.setString(1, orgId);
	    stmt.setString(2, orgId);
	    rs = stmt.executeQuery();
	    while (rs.next()) {
		retList.add(StringUtil.transpose_blank(rs.getString("siteno")));
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(stmt, rs);
	}
	return retList;
    }

    private boolean isClientOrg(Connection conn, String client_org_id, String hostOrgID) throws SQLException {
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
	    String strQuery = "SELECT org_id from membership.MEM_ORG_CLIENT_RELA r where  " + " CLIENT_ORG_ID = ? and status='1' and exists "
		    + "(select 1 from membership.mem_organization a where r.org_id = a.org_id and a.org_status_id='1' and "
		    + "(a.org_id=? or a.parent_org_id=?))";
	    ps = conn.prepareStatement(strQuery);
	    ps.setString(1, client_org_id);
	    ps.setString(2, hostOrgID);
	    ps.setString(3, hostOrgID);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		return true;
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(ps, rs);
	}
	return false;
    }

    /**
     * 通过站点编号得到公司id
     * 
     * @param siteNo
     * @return
     * @throws Exception
     */
    private String getOrgIdBySiteNo(Connection conn, String siteNo) throws SQLException {
	String orgId = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
	    String sqlStr = "select org_id from asp_config.site where siteno = ?";
	    ps = conn.prepareStatement(sqlStr);
	    ps.setString(1, siteNo);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		orgId = rs.getString("org_id");
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(ps, rs);
	}

	return orgId;
    }

    /**
     * 
     * @param conn
     * @param userName
     * @param password
     * @param loginType
     * @param dyToken
     * @return
     * @throws Exception
     */
    private User checkUserByBase(Connection conn, String userName, String password, String loginType, String dyToken) throws Exception {
	User user = null;
	// 得到用户登录方式（使用用户密码登录usePwd；使用动态令牌登录useDytoken）
	loginType = StringUtil.transpose_blank(loginType);
	if (StringUtil.isEmpty(userName))
	    return null;
	// 得到与用户绑定的动态令牌序列号
	String tokenSN = getUserDytokenSN(conn, userName);
	tokenSN = StringUtil.transpose_blank(tokenSN);
	if (loginType.equals(ConstantValue._usePwd) && tokenSN.equals("")) {// 如果用用户密码登录且没有与令牌绑定
	    // 不做任何事情
	} else if (loginType.equals(ConstantValue._usePwd) && !tokenSN.equals("")) {// 如果用用户密码登录且有与令牌绑定
	    // 提示必须用动态令牌登录
	    return null;
	} else if (loginType.equals(ConstantValue._useDytoken) && tokenSN.equals("")) {// 如果用动态令牌登录且没有与令牌绑定
	    // 提示不能用动态令牌登录
	    return null;
	} else if (loginType.equals(ConstantValue._useDytoken) && !tokenSN.equals("")) {// 如果用动态令牌登录且有与令牌绑定
	    if (isValidDyTokenPwd(tokenSN, dyToken)) {
		// 提示用户名动态令牌密码错误
		return null;
	    }
	}
	user = checkUser(conn, userName, password);
	return user;
    }

    /**
     * 判断动态令牌密码是否有效
     * 
     * @param request
     * @param userName
     * @return
     */
    private boolean isValidDyTokenPwd(String dytokenSn, String dyToken) {
	// 动态令牌密码
	String dyTokenPwd = StringUtil.transpose_blank(dyToken).trim();
	boolean isValid = false;
	String retCode = StringUtil.transpose_blank(DyTokenManager.getInstance().checkDynamicPwdByToken("1", dytokenSn, dyTokenPwd));
	if (retCode.equals("0"))
	    isValid = true;
	return isValid;
    }

    private String getUserDytokenSN(Connection conn, String userName) throws SQLException {

	String tokenSN = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
	    String sql = "select dytoken_series from mem_dytoken where user_name=? ";
	    ps = conn.prepareStatement(sql);
	    ps.setString(1, userName);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		tokenSN = rs.getString("dytoken_series");
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(ps, rs);
	}

	return tokenSN;
    }

    /**
     * 
     * 判断关系表中是否存在， 若不存在，则创建jit 用户并添加关联关系，返回jit用户信息 user
     * 
     * @param request
     * @param response
     * @param userInfo
     *            要判断的信息
     * @return
     * @throws MalformedURLException
     * @throws SQLException
     */
    private User b2bUserInfoValid(Connection conn, String siteNo, LoginResult userInfo, String serverName) throws MalformedURLException, SQLException {
	User user = null;
	UserInfoEntity userEntity = new UserInfoEntity();// 存储用户信息
	if (userInfo != null) {
	    JitB2bUserRelaEntity jbEntity = new JitB2bUserRelaEntity();
	    jbEntity.setB2b_user_id(String.valueOf(userInfo.getUserId()));
	    List<JitB2bUserRelaEntity> jitb2bList = new ArrayList<JitB2bUserRelaEntity>();
	    jitb2bList = this.getJitB2bUserInfo(jbEntity);
	    if (jitb2bList != null && jitb2bList.size() > 0) {// 关系表中存在，返回user
		JitB2bUserRelaEntity getjbEntity = (JitB2bUserRelaEntity) jitb2bList.get(0);
		userEntity = appContextBymem(ConstantValue.memName).getUserInfo(ConstantValue.setting(), getjbEntity.getUser_id(),
			getSiteNo(conn, siteNo, serverName));// 通过user_id
		// 得到用户信息
		if (userEntity != null) {
		    userEntity.setUserId(getjbEntity.getUser_id());
		}
	    } else {// 当关系表中不存在，则创建jit 账户,并与当前b2b账户关联起来,存储到关系表中
		userEntity = addjitUserInfo(ConstantValue.setting(), userInfo);// 开通
		// jit
		if (userEntity != null && !userEntity.isIsecho())// 开通成功
		{
		    userInfo.setJitUserId(Integer.parseInt(userEntity.getUserId()));
		    userInfo.setJitOrgId(Integer.parseInt(userEntity.getOrgId()));
		    userInfo.setUserName(userEntity.getUserName());
		    if (!addJitB2bUserRelaInfo(userInfo))
			return null;// 添加b2bjituserrela 关联关系表
		}
	    }
	    if (userEntity != null) {
		user = new User();
		user.setUserId(userEntity.getUserId());
		user.setOrgid(userEntity.getOrgId());
		user.setUsername(userEntity.getUserName());
	    }
	}
	return user;
    }

    /**
     * 添加jitb2b关联信息
     * 
     * @param txtUserInfo
     *            return JitB2bUserRelaEntity
     * @throws MalformedURLException
     */
    private boolean addJitB2bUserRelaInfo(LoginResult txtUserInfo) throws MalformedURLException {
	// 将txtUserInfo信息赋给注册页面 得到 一个新的对象 插入关系表中
	if (txtUserInfo != null) {
	    JitB2bUserRelaEntity jitb2bEntity = new JitB2bUserRelaEntity();
	    // jitb2bEntity.setB2b_user_id(String.valueOf(txtUserInfo.getUserId()));
	    jitb2bEntity.setJit_no(String.valueOf(txtUserInfo.getJitNo()));
	    jitb2bEntity.setOrg_id(String.valueOf(txtUserInfo.getJitOrgId()));
	    jitb2bEntity.setUser_id(String.valueOf(txtUserInfo.getJitUserId()));
	    jitb2bEntity.setStatus("1");
	    jitb2bEntity.setB2b("1");
	    jitb2bEntity.setB2b_user_id(String.valueOf(txtUserInfo.getUserId()));
	    // 注册时填写的信息
	    return appContextBymem(ConstantValue.memName).addJitB2bInfo(ConstantValue.setting(), jitb2bEntity);
	}
	return false;

    }

    /**
     * 添加jit用户
     * 
     * @param setting
     * @param orgEntity
     * @param userEntity
     * @return
     * @throws MalformedURLException
     */
    private UserInfoEntity addjitUserInfo(CommonSetting setting, LoginResult userEntity) throws MalformedURLException {
	// 用户基础信息
	UserInfoEntity userinfoEntity = new UserInfoEntity();
	// 为公司初始化信息
	OrganizationInfoEntity organizationInfo = new OrganizationInfoEntity();
	organizationInfo.setOrg_name(userEntity.getUserName());
	organizationInfo.setLinkman(userEntity.getUserName());

	userinfoEntity.setUserName(userEntity.getUserName());
	userinfoEntity.setOldPasswd("111111");
	userinfoEntity.setNewPasswd("111111");
	userinfoEntity.setEmail(userEntity.getUserMail());
	return appContextBymem(ConstantValue.memName).newRegistFrombyB2B(ConstantValue.setting(), organizationInfo, userinfoEntity);
    }

    /**
     * 获取jieb2b 关联关系
     * 
     * @param jbusereneity
     *            查询调教
     * @return 返回的集合
     * @throws MalformedURLException
     */
    private List<JitB2bUserRelaEntity> getJitB2bUserInfo(JitB2bUserRelaEntity jbusereneity) throws MalformedURLException {
	return appContextBymem(ConstantValue.memName).getJitB2bInfo(ConstantValue.setting(), jbusereneity);// 查询关系表
    }

    /**
     * 调用服务验证
     * 
     * @param request
     * @return
     * @throws MalformedURLException
     */
    private static IMembershipService appContextBymem(String beanName) throws MalformedURLException {
	String hessianUrl = ConstantValue._BIZ_HESSIAN_URL + beanName == null ? ConstantValue.bizName : beanName;
	HessianProxyFactory factory = new HessianProxyFactory();
	IMembershipService memWebService = (IMembershipService) factory.create(IMembershipService.class, hessianUrl);
	return memWebService;
    }

    private boolean validateSecurity(Zhimiao.biztrademarket.WebService.Entities.LoginResult lr) throws MalformedURLException {
	return appContextBybiz(ConstantValue.bizName).ValidateUser(lr);
    }

    private static IBiztrademarketWebService appContextBybiz(String beanName) throws MalformedURLException {
	String hessianUrl = ConstantValue._BIZ_HESSIAN_URL + beanName == null ? ConstantValue.bizName : beanName;
	HessianProxyFactory factory = new HessianProxyFactory();
	IBiztrademarketWebService bizWebService = (IBiztrademarketWebService) factory.create(IBiztrademarketWebService.class, hessianUrl);
	return bizWebService;
    }

    private User checkUserByLoginKey(Connection conn, String key) throws SQLException {
	User user = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
	    String sql = "select u.user_name,u.user_id,u.org_id,o.siteno,u.is_mac_confirm,u.mac_address "
		    + " from membership.mem_user u,asp_common.com_mem_login_key k,mem_organization o "
		    + " where u.org_id = k.org_id and u.user_id = k.user_id and k.mem_login_key_end_time>=sysdate " + " and u.org_id=o.org_id "
		    + " and mem_login_key = ? ";
	    ps = conn.prepareStatement(sql);
	    ps.setString(1, key);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		user = new User();
		user.setUsername(rs.getString("user_name"));
		user.setUserId(rs.getString("user_id"));
		user.setOrgid(rs.getString("org_id"));
		user.setOrgSiteNo(rs.getString("siteno"));
		user.setIs_mac_confirm(rs.getString("is_mac_confirm"));
		user.setMac_address(rs.getString("mac_address"));
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(ps, rs);
	}
	return user;
    }

    /**
     * 判断是否是匿名用户
     * 
     * @param userId
     * @return
     * @throws SQLException
     */
    private boolean isAnonymityUser(Connection conn, String userName) throws SQLException {
	String sqlStr = " select 1 from mem_user a  where upper(a.user_name)=?  and a.is_demo_user='1'";
	PreparedStatement ps = null;
	ResultSet rs = null;
	// boolean isAnonymityUser = false;
	try {
	    ps = conn.prepareStatement(sqlStr);
	    ps.setString(1, userName.toUpperCase());
	    rs = ps.executeQuery();
	    if (rs.next()) {
		return true;
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(ps, rs);
	}
	return false;
    }

    /**
     * 判断是否是超级管理员
     * 
     * @param userId
     * @return
     * @throws SQLException
     */
    private boolean isSupperAdmin(Connection conn, String userName) throws SQLException {
	String sql = " select 1 from mem_user a,mem_user_role_rela b,mem_role c  where upper(a.user_name)=? "
		+ "	and a.USER_ID=b.USER_ID and a.ORG_ID=b.ORG_ID and b.APP_ID='1' and b.ORG_ID=c.ORG_ID" + "	and b.APP_ID=c.APP_ID and c.ROLE_CODE='admin'";

	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
	    ps = conn.prepareStatement(sql);
	    ps.setString(1, userName.toUpperCase());
	    rs = ps.executeQuery();
	    if (rs.next()) {
		return true;
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(ps, rs);
	}
	return false;

    }

    private String getSiteNo(Connection conn, String siteNo, String serverName) throws SQLException {
	siteNo = StringUtil.transChina(siteNo).trim();
	if (StringUtil.isEmpty(siteNo))
	    siteNo = getSiteNoBySiteDomain(conn, serverName);
	if (StringUtil.isEmpty(siteNo))
	    siteNo = ConstantValue._defaultSiteNo;
	return siteNo;
    }

    private String getSiteNoBySiteDomain(Connection conn, String domain) throws SQLException {
	String siteNo = null;
	StringBuffer sql = new StringBuffer("select siteno from asp_config.site where status='1' and (siteDomain=? or wwwDomain=?)");
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
	    ps = conn.prepareStatement(sql.toString());
	    ps.setString(1, domain);
	    ps.setString(2, domain);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		siteNo = rs.getString("siteno");
	    }
	} catch (SQLException e) {
	    throw e;
	} finally {
	    closeConnection(ps, rs);
	}
	return siteNo;
    }
}
