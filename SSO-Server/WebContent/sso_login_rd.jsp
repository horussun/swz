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
            
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>

<%

final String queryString = request.getQueryString() != null ? "?" + request.getQueryString() : "";
try {
	String display = "style='display:none'";
	Object ob = session.getAttribute("errorMsg");

	String identity = request.getParameter("identity")==null?"":request.getParameter("identity");
	String cookieUserName = request.getParameter("cookieUserName")==null?"":request.getParameter("cookieUserName");
	String userName=cookieUserName;
	boolean flag = false;
	if (ob != null) {
		session.removeAttribute("errorMsg");
		int maxErrorCount=com.olymtech.cas.util.ConstantValue._maxErrorCount;
		userName = session.getAttribute("userName")==null?"":(String) session.getAttribute("userName");
		int errorCount = 0;
		if(com.olymtech.cas.util.ConstantValue._userLoginErrorCount.get(userName)!=null){
			errorCount=(Integer)com.olymtech.cas.util.ConstantValue._userLoginErrorCount.get(userName);
		}
		if(errorCount++>maxErrorCount){
			display = "";
		}
		flag = true;
	}

	LoginStatus ls = new LoginStatus();
	String TGT = ls.getTGT(request);
	if (TGT == null) {
 %>

<script type="text/javascript"> 
 
$(function()
		{
			var cookie_key="username_cookie_key";
			
			var username_cookie=$.cookie(cookie_key);
		
			if(username_cookie!=null)
			{
				$("#username").val(username_cookie);
				$("#remeberme").attr("checked",true);
			}
			 
			$("#fm1").submit(function()
			{
				var username=$("#username");
				var password=$("#password");
				if($.trim(username.val())=="")
				{
					alert('请输入用户名');
					username.focus();
					return false;
				}
				
				if($.trim(password.val())=="")
				{
					alert('请输入密码');
					password.focus();
					return false;
				}
				 
				if($("#remeberme").attr("checked"))
				{
					$.cookie(cookie_key,username.val(),{'secure':true,'expires':30});
				}
				else
				{
					$.cookie(cookie_key,null,{'secure':true});
				}
 
				username.val($.trim(username.val()));
				
			});
	});
 
</script>
<form id="fm1" name="form1" class="fm-v clearfix" action="http://cas.800jit.com/ssocenter/directLogin<%=queryString %>" method="post" target="_top">

                <div class="box fl-panel" id="login">
                
                <table style="width:100%;">
                <tr><td>  
                <label for="username" class="fl-label"><spring:message code="screen.welcome.label.netid" /></label>
						
				</td>
				<td id="p-t"><input id="username" name="username" class="required input-bg" tabindex="1" accesskey="n" type="text" value="" size="20" autocomplete="false" value="<%=userName %>"/></td>
				</tr>
                 <tr><td> <label for="password" class="fl-label"><spring:message code="screen.welcome.label.password" /></label>
						</td>
						<td>
						<input id="password" name="password" class="required input-bg" tabindex="2" accesskey="p" type="password" value="" size="20" autocomplete="off"/></td></tr> 
                 <tr <%=display%>>
                 <td> 
                 <label for="validContent" class="fl-label">
                 <spring:message code="screen.welcome.label.validContent" /></label>
	             </td>
                 <td>
                 <input id="validContent" name="validContent" class="required input-bg0" tabindex="3" type="text" value="" size="7" autocomplete="false"/>
                 <img src="/ssocenter/validImgServlet" style="width:80px"></td>
                 </tr>
                  <tr>
                 <td colspan="2"><input type="checkbox" name="chk" id="remeberme"><spring:message code="screen.rememberme" />
						<a href="http://saas.800jit.com/intranet/frame_themes/default/others/pwd/forgot_pwd1.jsp" target="_blank" class="find-password"><spring:message code="screen.welcome.label.getpassword" /></a>
						<%if(identity.equals("huozhu")) {%>
						<a href="http://<%=request.getParameter("serverName")%><spring:message code="portal.jit.register" />" target="_blank" class="find-password"><spring:message code="screen.welcome.label.freeRegister" /></a>
						<%}else {%>
						<a href="http://www.800jit.com/free/register" target="_blank" class="find-password"><spring:message code="screen.welcome.label.freeRegister" /></a>
						<%}%></td>
                 
                 </tr>
                 <tr>
                 <td></td>
                 <td>
       
                        <input id="btn-submit" name="submit1" accesskey="l" value="" tabindex="4" type="submit" />
                        </td></tr>
                </table>
 
                </div>

			<%if (flag) {%>
				<script>
				alert('<spring:message code="screen.error.label.warn" />');
				</script>
			<%}%>
            </form>
 <% 
	} else { 
%>
					<div id="msg" class="success">
			<spring:message code="screen.success.header" />
		</div>
		
		<div>
			<a href="<%=session.getAttribute("wheretogo") %>" target="_top"><h2><spring:message code="screen.relogin" /></h2></a>
		</div>
<%
	} 
} catch (Exception e) {
%>
					<div align="center"><a href="http://cas.800jit.com/ssocenter/sso_login_rd.jsp<%=queryString %>"><spring:message code="screen.welcome.label.click" /></a></div>
<% 
}
%>
                </div>
            </div>
        </div>
    </body>
</html>
