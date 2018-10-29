<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.olymtech.app.ssocenter.old.UserIDCookie"%>
<%@page import="com.olymtech.app.ssocenter.old.Config"%>
<%@page import="java.net.URL"%>
<%@page import="java.net.URLDecoder"%>

<%
response.setHeader("P3P","CP=CAO PSA OUR");
UserIDCookie cookie = new UserIDCookie(request,response);
URL clsURL = cookie.getClass().getResource(Config.DesKeyPath);
String keyFilePath=clsURL.getPath();
String rememberMe = "";
String cookieLoginType = "";
if(cookie.getRememberMe()!=null && URLDecoder.decode(cookie.getRememberMe(),"utf-8").indexOf(":")>0) {
	String[] remember = URLDecoder.decode(cookie.getRememberMe(),"utf-8").split(":");
	rememberMe = remember[0];
	if(remember.length>1)cookieLoginType = remember[1];
}
String cookieUserName = "";
if(rememberMe.equals("true"))
	cookieUserName = cookie.getLocalCookieUserName(keyFilePath);
/*货主*/
String serverName = request.getServerName();
String serverPort = request.getServerPort()+"";
final String queryString = request.getQueryString();
String url = "https://cas.800jit.com/ssocenter/login?locale=en&serverName="+serverName+"&cookieUserName="+cookieUserName+"&serverPort="+serverPort+"&identity=huozhu"+(queryString != null ? "&" + queryString : "")+"&rd="+System.currentTimeMillis();
response.sendRedirect(url);
 %>