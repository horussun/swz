<%@page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="com.olymtech.cas.client.filter.CASFilter"%>
<%@ page language="java" import="com.olymtech.cas.client.filter.CASFilterRequestWrapper"%>
<%
//HttpSession session=request.getSession();
CASFilterRequestWrapper  reqWrapper=new CASFilterRequestWrapper(request);
%>
<%=session.getAttribute(CASFilter.CAS_FILTER_USER)%>
<br><%=session.getAttribute("com.olymtech.cas.client.filter.user")%>
<br><%=reqWrapper.getRemoteUser()%>
<br>welcome to casclient!!! you  get it.
<br><a href="https://cas.800jit.com/ssocenter/logout">logout</a>
<div><%=java.net.InetAddress.getLocalHost().getHostName()%></div>

<%


%>