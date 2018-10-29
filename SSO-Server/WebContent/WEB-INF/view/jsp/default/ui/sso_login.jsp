<%@ page contentType="text/html; charset=UTF-8" %>
<%response.setHeader("P3P","CP=CAO PSA OUR"); %>
<%String dis = request.getParameter("serverName")==null?"":request.getParameter("serverName");%>
<jsp:directive.include file="includes/sso_top.jsp" />
<%
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
%>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
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
			else
			{
				$("#username").val('<%=userName%>');
			}
			 
			$("#fm1").submit(function()
			{
				//alert('here');
				var username=$("#username");
				var password=$("#password");
				if($.trim(username.val())=="")
				{
					alert('<spring:message code="screen.username.isnull" />');
					username.focus();
					return false;
				}
				
				if(password.val()=="")
				{
					alert('<spring:message code="screen.password.isnull" />');
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

				$("#btn-submit").attr("disabled","disabled");
				//return false;
				
			});
	});

</script>
<form:form method="post" name="form1" id="fm1" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true" target="_top">
			    <form:errors path="*" cssClass="errors" id="status" element="div" />
                <div class="box fl-panel" id="login">
                
                <table style="width:100%;">
                <tr><td>  
                <label for="username" class="fl-label"><spring:message code="screen.welcome.label.netid" /></label>
						<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
				</td>
				<td id="p-t"><form:input cssClass="required input-bg" cssErrorClass="error" id="username" size="20" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" ></form:input></td>
				</tr>
                 <tr><td> <label for="password" class="fl-label"><spring:message code="screen.welcome.label.password" /></label>
						<spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" /></td>
						<td>
						<form:password cssClass="required input-bg" cssErrorClass="error" id="password" size="20" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" /></td></tr> 
                 <tr <%=display%>>
                 <td> 
                 <label for="validContent" class="fl-label">
                 <spring:message code="screen.welcome.label.validContent" /></label>
	                   
	                    	
	              
	             </td>
                 <td>
                 <form:input cssClass="required input-bg0" cssErrorClass="error" id="validContent" size="7" tabindex="3" path="validContent" autocomplete="false" htmlEscape="true" />
                 <img src="/ssocenter/validImgServlet" style="width:80px"></td>
                 </tr>
                  <tr>
                 <td colspan="2"><input type="checkbox" name="chk" id="remeberme"><spring:message code="screen.rememberme" />
						<a href="http://saas.800jit.com/intranet/frame_themes/default/others/pwd/forgot_pwd1.jsp" target="_blank" class="find-password"><spring:message code="screen.welcome.label.getpassword" /></a>
						<%if(identity.equals("huozhu")) {%>
						<a href="http://<%=dis%><spring:message code="portal.jit.register" />" target="_blank" class="find-password"><spring:message code="screen.welcome.label.freeRegister" /></a>
						<%}else {%>
						<a href="<spring:message code="saas.register" />" target="_blank" class="find-password"><spring:message code="screen.welcome.label.freeRegister" /></a>
						<%}%></td>
                 
                 </tr>
                 <tr>
                 <td></td>
                 <td>
                 <input type="hidden" name="lt" value="${flowExecutionKey}" />
						<input type="hidden" name="_eventId" value="submit" />
						<input type="hidden" name="dyToken" value="" />
                        <input id="btn-submit" name="submit1" accesskey="l" value="" tabindex="4" type="submit" />
                        </td></tr>
                </table>

                </div>
<%if (flag) {%>
	<script>
	alert('<spring:message code="screen.error.label.warn" />');
	</script>
<%}%>
            </form:form>

<jsp:directive.include file="includes/sso_bottom.jsp" />
