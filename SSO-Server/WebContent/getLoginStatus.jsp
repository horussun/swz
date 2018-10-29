<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="com.olymtech.cas.web.LoginStatus"%>
<%
try {
	LoginStatus ls = new LoginStatus();
	String TGT = ls.getTGT(request);
	if (TGT == null) {
		out.write(TGT);
	} else { 
		out.write(TGT);
	} 
} catch (Exception e) {
	out.write(e.getMessage());
}
%>

 