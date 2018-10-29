<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="com.olymtech.cas.web.LoginStatus"%>
<%
String casNoFilter = "/box/,/index.jsp,/test.jsp,/js";
String contextPath = request.getContextPath();
String uri = request.getRequestURI();
String checkPath = uri.substring(contextPath.length(),uri.length());
String[] noFilterArray = casNoFilter.split(",");
System.out.println("contextPath:"+contextPath);
System.out.println("uri:"+uri);
System.out.println("checkPath:"+checkPath);
for (String string : noFilterArray) {
	if (checkPath.indexOf(string) != -1) {
		
		System.out.println("..");
	}
}
%> 