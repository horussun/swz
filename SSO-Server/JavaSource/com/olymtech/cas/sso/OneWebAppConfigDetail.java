package com.olymtech.cas.sso;

/** 某一个Web应用的配置 实体类*/
public class OneWebAppConfigDetail implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4548192374282310492L;
    private  String     webAppContext;
    private  String     webAppCode;
    private  String     webAppId;
    private  String     webAppSchema;
    private  String     webAppTheme;
    private  String     webAppLoginURL;
    private  String     webAppLogoutURL;
    private  String     webAppLoginURLMapping;
    private  String     webAppWSURL;
    private  String     webAppWSPort;
    private  String     webAppProvider_en;
    private  String     webAppProvider_zh;
    private  String     webAppLogoImgURL;
    private  String     webAppHelpURL;
    
	public String getWebAppHelpURL() {
		return webAppHelpURL;
	}
	public void setWebAppHelpURL(String webAppHelpURL) {
		this.webAppHelpURL = webAppHelpURL;
	}
	public String getWebAppLogoImgURL() {
		return webAppLogoImgURL;
	}
	public void setWebAppLogoImgURL(String webAppLogoImgURL) {
		this.webAppLogoImgURL = webAppLogoImgURL;
	}
	public String getWebAppProvider_en() {
		return webAppProvider_en;
	}
	public void setWebAppProvider_en(String webAppProvider_en) {
		this.webAppProvider_en = webAppProvider_en;
	}
	public String getWebAppProvider_zh() {
		return webAppProvider_zh;
	}
	public void setWebAppProvider_zh(String webAppProvider_zh) {
		this.webAppProvider_zh = webAppProvider_zh;
	}
	public String getWebAppCode() {
		return webAppCode;
	}
	public void setWebAppCode(String webAppCode) {
		this.webAppCode = webAppCode;
	}
	public String getWebAppContext() {
		return webAppContext;
	}
	public void setWebAppContext(String webAppContext) {
		this.webAppContext = webAppContext;
	}
	public String getWebAppLoginURL() {
		return webAppLoginURL;
	}
	public void setWebAppLoginURL(String webAppLoginURL) {
		this.webAppLoginURL = webAppLoginURL;
	}
	public String getWebAppLoginURLMapping() {
		return webAppLoginURLMapping;
	}
	public void setWebAppLoginURLMapping(String webAppLoginURLMapping) {
		this.webAppLoginURLMapping = webAppLoginURLMapping;
	}
	public String getWebAppLogoutURL() {
		return webAppLogoutURL;
	}
	public void setWebAppLogoutURL(String webAppLogoutURL) {
		this.webAppLogoutURL = webAppLogoutURL;
	}
	public String getWebAppSchema() {
		return webAppSchema;
	}
	public void setWebAppSchema(String webAppSchema) {
		this.webAppSchema = webAppSchema;
	}
	public String getWebAppWSPort() {
		return webAppWSPort;
	}
	public void setWebAppWSPort(String webAppWSPort) {
		this.webAppWSPort = webAppWSPort;
	}
	public String getWebAppWSURL() {
		return webAppWSURL;
	}
	public void setWebAppWSURL(String webAppWSURL) {
		this.webAppWSURL = webAppWSURL;
	}
	public String getWebAppTheme() {
		return webAppTheme;
	}
	public void setWebAppTheme(String webAppTheme) {
		this.webAppTheme = webAppTheme;
	}
	public String getWebAppId() {
		return webAppId;
	}
	public void setWebAppId(String webAppId) {
		this.webAppId = webAppId;
	}
}

