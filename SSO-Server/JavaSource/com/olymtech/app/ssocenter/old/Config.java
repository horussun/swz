package com.olymtech.app.ssocenter.old;

public class Config {
    // 常量定义
    final public static int ORACLE = 0;
    final public static int INFORMIX = 1;
    final public static int SQLSERVER = 2;
    final public static int SYBASE = 3;

    final public static int WEBLOGIC = 0;
    final public static int TOMCAT = 1;
    final public static int JBOSS = 2;

    // 程序配置
    final public static String ConfigPoolName = "config";
    final public static String CommonPoolName = "common";
    final public static String BossPoolName = "bops";
    final public static String MembershipInPoolName = "membership";
    final public static String MembershipOutPoolName = "membership_out";
    final public static String IntranetPoolName = "intranet";
    final public static String InternetPoolName = "Internet";
    final public static String ModelHomePoolName = "modelhome";
    // ---中企新干线数据库PORTAL
    final public static String PortalPoolName = "portal";

    final public static String Session_orgId_Key = "session_orgId";
    final public static String Session_userId_Key = "session_userId";
    final public static String Session_userName_Key = "session_userName";
    final public static String Session_SiteNo_Key = "session_siteNo";
    final public static String Session_cookieDomain_Key = "session_cookieDomain";
    final public static String Session_themeRootPath_Key = "session_themeRootPath";
    final public static String Session_themeStyle_Key = "session_themeStyle";

    final public static String DesKeyPath = "/conf/deskey.tex";
    final public static String DBIniPath = "/conf/dbinfo.properties";
    final public static String Log4JPath = "/conf/log4j.properties";
    final public static String IBosTreeViewPath = "/conf/ibostreeview.xml";

    final public static int DATABASE = Config.ORACLE;

    public static String WEB_ROOT_REAL_PATH = "";

    // 系统参数配置设置时候的类型
    // 系统配置
    final public static String SYS_TYPE = "sys";
    // 显示配置
    final public static String SHOW_TYPE = "show";

    // 系统初始化是否成功，true：成功，先设计为成功，如果某个环节初始化失败，
    // 此标志将被设计为失败，系统可根据此标志是否拒绝用户访问
    private static boolean isInitSucceed = true;

    public Config() {
    }

    // 设计初始化标志为失败
    public static void setInitFailed() {
	isInitSucceed = false;
    }

    // 获取系统初始化是否成功
    public static boolean getInitState() {
	return isInitSucceed;
    }

    public static void init(String configFile) {
    }
}
