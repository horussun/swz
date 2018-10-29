<!-- 
	销售订单列表页面
 -->
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../common/_tag.jspf" %>

<div class="searchpart" id="searchpart">
	
</div>
<div id="content2">
	<span class="buttons">
		<div data-dojo-type="dijit.form.Button" data-dojo-props="onClick:function(){Common.currentpage.editOrder('add');}" id="saleOrderAdd">
      		<fmt:message key="CMD_new" bundle="${ClientStrings}"></fmt:message>
      	</div>
      	<div data-dojo-type="dijit.form.Button" data-dojo-props="onClick:function(){Common.currentpage.viewDetail();},disabled:true" id="saleOrderView">
      		<fmt:message key="CMD_viewdetail" bundle="${ClientStrings}"></fmt:message>
      	</div>
      	<div data-dojo-type="dijit.form.Button" data-dojo-props="onClick:function(){Common.currentpage.editOrder('edit');},disabled:true" id="saleOrderEdit">
      	           编辑
      	</div>
      	<div data-dojo-type="dijit.form.Button" data-dojo-props="onClick:function(){Common.currentpage.createShipment();},disabled:true" id="createShipment">
      		创建运输订单
      	</div>
      	<div data-dojo-type="dijit.form.Button" data-dojo-props="onClick:function(){Common.currentpage.popUpImport();}" id="import">
      		<fmt:message key="CMD_EXCLE_IMPORT" bundle="${ClientStrings}"></fmt:message>
      	</div>
	</span>

	<div class="datagrid_area">
		<div class="datagrid" id="saleGridContainer"></div>
		<div class="pagination" id="sale_pageBar"></div>
	</div>
</div>
<div id="dialog" dojoType="dijit.Dialog"></div>