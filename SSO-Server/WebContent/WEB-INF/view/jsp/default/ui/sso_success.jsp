<jsp:directive.include file="includes/sso_top.jsp" />
		<div id="msg" class="success">
			<spring:message code="screen.success.header" />
		</div>
		<%
		Object username = com.olymtech.common.cache.MemCachedFactory.getCache().get("cas-loginUserName");
		Object wheretogo = com.olymtech.common.cache.MemCachedFactory.getCache().get("wheretogo"+username);
		Object firstpage = com.olymtech.common.cache.MemCachedFactory.getCache().get("firstpage"+username);
		%>
		<div>
			<a href="<%=wheretogo %>" target="_top"><h2><spring:message code="screen.relogin" /></h2></a>
			<a href=https://cas.800jit.com/ssocenter/logout?url=<%=firstpage %> target="_top"><h2><spring:message code="management.services.link.logout" /></h2></a>
		</div>
		
<jsp:directive.include file="includes/sso_bottom.jsp" />

