<!-- 
	搜索产品弹出窗口页面
 -->
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../common/_tag.jspf"%>
<style type="text/css">
	@import "${pageContext.request.contextPath}/china/css/sdstyle.css";
	@import "${pageContext.request.contextPath}/china/css/style.css";
</style>
	<div class="searchpart" id="searchpart">
		
	</div>
    <div id="content2">
        <div id="searchPane" dojoType="dijit.layout.ContentPane" title="<fmt:message key="TAB_searchProduct" bundle="${ClientStrings}" />" >
		</div>
		<div class="datagrid_area">
			<div class="datagrid" id="searchProductGridContainer"></div>
			<div class="pagination" id="searchProduct_pageBar"></div>
		</div>
  	<div id="btarea">
        <div id="infobts">
        <span class="passivebt">
             <div data-dojo-type="dijit.form.Button" data-dojo-props="onClick:function(){Common.currentpage.selected();}">
        		<fmt:message key="CMD_done" bundle="${ClientStrings}"/>
        	</div>
       </span>
       </div>
    </div>
   </div>    
     

