<%@ page session="true" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" import="com.olymtech.cas.web.LoginStatus"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
	    <title>SSO</title>
        <link type="text/css" rel="stylesheet" href="css/cas_default.css" />
        <script type="text/javascript" src="js/common_rosters.js"></script>
        <link rel="icon" href="/ssocenter/favicon.ico" type="image/x-icon" />
	</head>
	<body id="cas" onload="init();" class="fl-theme-iphone" >
    <div class="flc-screenNavigator-view-container">
        <div class="fl-screenNavigator-view">	
            <div id="content" class="fl-screenNavigator-scroll-container"> 
<%

final String queryString = request.getQueryString() != null ? "?" + request.getQueryString() : "";
try {
	//String url = request.getRequestURL().toString();
	//String urltop = url.substring(0,url.indexOf(":"));
	//if(urltop.equalsIgnoreCase("http")) {
	//	response.sendRedirect(url.replaceFirst("http","https")+(queryString != null ? "?" + queryString : ""));
	//}
	String serverName = request.getServerName();
	String serverPort = request.getServerPort()+"";
	LoginStatus ls = new LoginStatus();
	String TGT = ls.getTGT(request);
	if (TGT == null) {
 %>
 			<form id="fm1" class="fm-v clearfix" action="https://cas.800jit.com/ssocenter/directLogin<%=queryString %>" method="post" target="_top">
 <%
Object ob = session.getAttribute("errorMsg");
if (ob != null) {
	session.removeAttribute("errorMsg");
%>
<div class='error' style="color:red"><spring:message code="screen.error.label.warn" /></div>
<%
}
%>
				<div class="box fl-panel" id="login">
                    <div class="row fl-controls-left">
                        <label for="username" class="fl-label"><spring:message code="screen.welcome.label.netid" /></label>
						<div class="input-bg"><input id="username" name="username" class="required" tabindex="1" accesskey="n" type="text" value="" size="20" autocomplete="false"/></div>
                    	<div class="clear"></div>
                    </div>
                    <div class="row fl-controls-left">
                        <label for="password" class="fl-label"><spring:message code="screen.welcome.label.password" /></label>
						<div class="input-bg"><input id="password" name="password" class="required" tabindex="2" accesskey="p" type="password" value="" size="20" autocomplete="off"/></div>
						<div class="clear"></div>
                    </div>
                    <div class="row fl-controls-left">
	                    <label for="validContent" class="fl-label"><spring:message code="screen.welcome.label.validContent" /></label>
	                    <div class="input-bg0"><input id="validContent" name="validContent" class="required" tabindex="3" type="text" value="" size="7" autocomplete="false"/></div><img src="https://cas.800jit.com/ssocenter/validImgServlet">
						<div class="clear"></div>
                    </div>
                    
                    <div class="row btn-row"> 
						<input type="hidden" name="dyToken" value="" />
						<input type="hidden" name="option1" value="0" />
						
                        <input class="btn-submit" name="submit" accesskey="l" value="<spring:message code="screen.welcome.button.login" />" tabindex="4" type="submit" />
                        <a href="http://saas.800jit.com/intranet/frame_themes/default/others/pwd/forgot_pwd1.jsp" target="_top" class="find-password"><spring:message code="screen.welcome.label.getpassword" /></a>
                    </div>
                </div>
            </form>
<% 
	} else { 
%>
					<div align="center"><spring:message code="screen.welcome.label.success" /><a href="https://cas.800jit.com/ssocenter/logout?url=https://cas.800jit.com/ssocenter/index.jsp<%=queryString %>"><spring:message code="screen.welcome.label.off" /></a></div>
<%
	} 
} catch (Exception e) {
%>
					<div align="center"><a href="https://cas.800jit.com/ssocenter/index.jsp<%=queryString %>"><spring:message code="screen.welcome.label.click" /></a></div>
<% 
}
%>
                </div>
            </div>
        </div>
    </body>
</html> 