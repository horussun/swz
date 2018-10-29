<%--
报表页面
--%>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../common/_tag.jspf"%>

<%-- tree of report --%>

<div id="borderContainer" dojoType="dijit.layout.BorderContainer">
	<div id="left" dojoType="dijit.layout.BorderContainer" region="left" splitter="true" style="width: 180px;height:100%">
        
        <div dojoType="dijit.layout.ContentPane" region="center" style="height: 100%;">
        	<%-- report tree --%>
           <div id="cognosTree" style="height: 100%;"></div>
        </div>
	</div>
      <%-- report content --%>
	<div id="content_body" jsId="content_body" dojoType="dijit.layout.ContentPane" region="center">
        <iframe id="ibody" src="" style="width: 100%; height: 100%; border: none"/>
	</div>
</div>