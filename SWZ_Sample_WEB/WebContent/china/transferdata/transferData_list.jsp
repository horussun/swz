<!-- 
	平台传输数据列表页面
 -->
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../common/_tag.jspf" %>

<div class="searchpart" id="searchpart">
	
</div>
<div id="content2">
	<span class="buttons">
    	<div data-dojo-type="dijit.form.Button" data-dojo-props="onClick:function(){Common.currentpage.popUpDialog();},disabled:true" id="OrderView">
      		查看订单
     	</div>
	</span>
	<div class="datagrid_area">
		<div class="datagrid" id="transferdataGridContainer"></div>
		<div class="pagination" id="transferdata_pageBar"></div>
	</div>
</div>
<input type="hidden" id="userId" name="userId" value="${role.userId }"> 
<div id="dialog" dojoType="dijit.Dialog"></div>