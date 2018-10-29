package com.olymtech.cas.sso;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.olymtech.cas.util.StringUtil;


/** 某一个站点的系统配置参数详细 实体类*/
public class OneSiteParmDetail implements Serializable {

	private static final long serialVersionUID = -5476824090042321594L;
    private  String     siteNo             	;   //站点编号
    private  String     siteName            ;   //站点名称
    private  String     siteIP              ;   //站点IP
    private  String     cookieDomain       	;   //Cookie域名
    private  String     wwwDomain       	;   //WWW域名
    private  String     siteDomain       	;   //站点域名
    private  String     cookieDomainPort   	;   //域名端口
    private	 String		loginPageUrl		;	//登录URL 
    private  String     logoutUrl			;	//退出URL
    private  String     org_id       		;   //ORG_ID
    private  String     isPublic       		;   //是否是公开（是否是运营商站点）
    private  String     site_error_page     ;   //站点提示页面
    
    private  String     status       		;   //状态 (1：可用  0：未使用 -1：不可用)
    
    private  String     default_app_code    ;   //站点默认进入的应用
    private  String     site_portal_app_code;	//站点对应的门户应用代码
    private  String     site_service_phone  ;	//站点对应的服务电话
    private  String     site_logo			;	//站点Logo
    
    private  String     site_org_id			;	//站点对应的公司ID
    private  String     site_org_name		;	//站点对应的公司名称
    private  String     site_org_name_en	;	//站点对应的公司名称(英文)
    private  String     site_org_address	;	//站点对应的公司地址
    private  String     site_org_fax		;	//站点对应的公司传真
    private  String     site_org_email		;	//站点对应的公司Email
    private  String     site_org_postcode   ;   //站点对应的公司邮编
    private  String     site_org_tel   ;   //站点对应的公司电话

    private  String     site_service_im_user;   //站点对应的即时通用户(目前支持一个)
    
    
    
    
	public String getSite_org_postcode() {
		return site_org_postcode;
	}
	public void setSite_org_postcode(String site_org_postcode) {
		this.site_org_postcode = site_org_postcode;
	}
	public String getCookieDomain() {
		return cookieDomain;
	}
	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}
	public String getCookieDomainPort() {
		return cookieDomainPort;
	}
	public void setCookieDomainPort(String cookieDomainPort) {
		this.cookieDomainPort = cookieDomainPort;
	}
	public String getLoginPageUrl() {
		return loginPageUrl;
	}
	public void setLoginPageUrl(String loginPageUrl) {
		this.loginPageUrl = loginPageUrl;
	}
	public String getLogoutUrl() {
		return logoutUrl;
	}
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}
	public String getSiteNo() {
		return siteNo;
	}
	public void setSiteNo(String siteNo) {
		this.siteNo = siteNo;
	}

	public String getSiteIP() {
		return siteIP;
	}
	public void setSiteIP(String siteIP) {
		this.siteIP = siteIP;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWwwDomain() {
		return wwwDomain;
	}
	public void setWwwDomain(String wwwDomain) {
		this.wwwDomain = wwwDomain;
	}
	public String getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}
	public String getSite_error_page() {
		return site_error_page;
	}
	public void setSite_error_page(String site_error_page) {
		this.site_error_page = site_error_page;
	}
	public String getDefault_app_code() {
		return default_app_code;
	}
	public void setDefault_app_code(String default_app_code) {
		this.default_app_code = default_app_code;
	}
	public String getSite_logo() {
		return site_logo;
	}
	public void setSite_logo(String site_logo) {
		this.site_logo = site_logo;
	}
	public String getSite_org_address() {
		return site_org_address;
	}
	public void setSite_org_address(String site_org_address) {
		this.site_org_address = site_org_address;
	}
	public String getSite_org_email() {
		return site_org_email;
	}
	public void setSite_org_email(String site_org_email) {
		this.site_org_email = site_org_email;
	}
	public String getSite_org_fax() {
		return site_org_fax;
	}
	public void setSite_org_fax(String site_org_fax) {
		this.site_org_fax = site_org_fax;
	}
	public String getSite_org_id() {
		return site_org_id;
	}
	public void setSite_org_id(String site_org_id) {
		this.site_org_id = site_org_id;
	}
	public String getSite_org_name() {
		return site_org_name;
	}
	public void setSite_org_name(String site_org_name) {
		this.site_org_name = site_org_name;
	}
	public String getSite_org_name_en() {
		return site_org_name_en;
	}
	public void setSite_org_name_en(String site_org_name_en) {
		this.site_org_name_en = site_org_name_en;
	}
	public String getSite_portal_app_code() {
		return site_portal_app_code;
	}
	public void setSite_portal_app_code(String site_portal_app_code) {
		this.site_portal_app_code = site_portal_app_code;
	}
	public String getSite_service_phone() {
		return site_service_phone;
	}
	public void setSite_service_phone(String site_service_phone) {
		this.site_service_phone = site_service_phone;
	}
	public String getSiteDomain() {
		return siteDomain;
	}
	public void setSiteDomain(String siteDomain) {
		this.siteDomain = siteDomain;
	}
	public String getSite_service_im_user() {
		return site_service_im_user;
	}
	public void setSite_service_im_user(String site_service_im_user) {
		this.site_service_im_user = site_service_im_user;
	}
	public String getSite_org_tel() {
		return site_org_tel;
	}
	public void setSite_org_tel(String site_org_tel) {
		this.site_org_tel = site_org_tel;
	}
	
	/**
	 * 把结果集合设置到实体中去
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public OneSiteParmDetail setRsValueToEntity(ResultSet rs) throws SQLException{
		OneSiteParmDetail entry=new OneSiteParmDetail();
			entry.setSiteNo(StringUtil.transpose_blank(rs.getString("siteNo")));
			entry.setSiteName(StringUtil.transpose_blank(rs.getString("siteName")));
			entry.setSiteIP(StringUtil.transpose_blank(rs.getString("siteIP")));
			entry.setCookieDomain(StringUtil.transpose_blank(rs.getString("cookieDomain")));
			entry.setWwwDomain(StringUtil.transpose_blank(rs.getString("wwwDomain")));
			entry.setSiteDomain(StringUtil.transpose_blank(rs.getString("siteDomain")));
			entry.setCookieDomainPort(StringUtil.transpose_blank(rs.getString("cookieDomainPort")));
			entry.setLoginPageUrl(StringUtil.transpose_blank(rs.getString("loginPageUrl")));
			entry.setLogoutUrl(StringUtil.transpose_blank(rs.getString("logoutUrl")));
			entry.setOrg_id(StringUtil.transpose_blank(rs.getString("org_id")));
			entry.setIsPublic(StringUtil.transpose_blank(rs.getString("isPublic")));	
			entry.setSite_error_page(StringUtil.transpose_blank(rs.getString("site_error_page")));
			entry.setStatus(StringUtil.transpose_blank(rs.getString("status")));
			entry.setDefault_app_code(StringUtil.transpose_blank(rs.getString("default_app_code")));
			entry.setSite_portal_app_code(StringUtil.transpose_blank(rs.getString("site_portal_app_code")));
			entry.setSite_service_phone(StringUtil.transpose_blank(rs.getString("site_service_phone")));
			entry.setSite_logo(StringUtil.transpose_blank(rs.getString("site_logo_content")));
			entry.setSite_service_im_user(StringUtil.transpose_blank(rs.getString("site_service_im_user")));
			
			entry.setSite_org_id(StringUtil.transpose_blank(rs.getString("org_id")));
			entry.setSite_org_name(StringUtil.transpose_blank(rs.getString("org_name")));
			entry.setSite_org_name_en(StringUtil.transpose_blank(rs.getString("org_name_en")));
			entry.setSite_org_address(StringUtil.transpose_blank(rs.getString("address")));
			entry.setSite_org_fax(StringUtil.transpose_blank(rs.getString("fax")));
			entry.setSite_org_tel(StringUtil.transpose_blank(rs.getString("tel")));
			entry.setSite_org_postcode(StringUtil.transpose_blank(rs.getString("postcode")));
			entry.setSite_org_email(StringUtil.transpose_blank(rs.getString("email")));
		return entry;
	}
    
}

