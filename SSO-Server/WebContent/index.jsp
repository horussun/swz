<%@ page language="java"  session="false" %>
<%
final String queryString = request.getQueryString();
final String url = "https://cas.800jit.com/ssocenter/login" + (queryString != null ? "?" + queryString : "");
response.sendRedirect(response.encodeURL(url));
%>
