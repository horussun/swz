<%@ page contentType="text/html; charset=UTF-8" %>
<%
String serverName = request.getServerName();
String serverPort = request.getServerPort()+"";
 %>
<form id="fm1" action="https://cas.800jit.com/ssocenter/directLogin" method="post" >
				 <div class="box fl-panel" id="login">
                    <h2>用户登陆</h2>
                    <div class="row fl-controls-left">
                        <label for="username" class="fl-label">用户名:</label>
						<input id="username" name="username" class="required" tabindex="1" accesskey="n" type="text" value="" size="20" autocomplete="false"/>
						
                    </div>
                    <div class="row fl-controls-left">
                        <label for="password" class="fl-label">密　码:</label>
						
						<input id="password" name="password" class="required" tabindex="2" accesskey="p" type="password" value="" size="20" autocomplete="off"/>
                    </div>
                    <div class="row fl-controls-left">
	                    <label for="validContent" class="fl-label">验证码:</label>
	                    <input id="validContent" name="validContent" class="required" tabindex="3" type="text" value="" size="7" autocomplete="false"/><img src="/ssocenter/validImgServlet">
                    </div>
                    
                    <div class="row btn-row">
						
                        <input class="btn-submit" name="submit" accesskey="l" value="登录" tabindex="4" type="submit" />
                        <input class="btn-reset" name="reset" accesskey="c" value="重置" tabindex="5" type="reset" />
                        <a href="http://saas.800jit.com/intranet/frame_themes/default/others/pwd/forgot_pwd1.jsp" target="_top">找回密码</a>
                    </div>
                </div>


</form>
 