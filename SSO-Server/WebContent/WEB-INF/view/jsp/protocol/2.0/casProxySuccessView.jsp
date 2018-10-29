<%@page pageEncoding="UTF-8"%>
<%@ page session="false" contentType="text/plain" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<cas:serviceResponse xmlns:cas='http://cas.800jit.com/cas'>
	<cas:proxySuccess>
		<cas:proxyTicket>${fn:escapeXml(ticket)}</cas:proxyTicket>
	</cas:proxySuccess>
</cas:serviceResponse>