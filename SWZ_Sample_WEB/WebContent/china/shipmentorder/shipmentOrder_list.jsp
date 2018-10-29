<!-- 
	运输订单列表页面
 -->
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../common/_tag.jspf" %>

	<div class="searchpart" id="searchpart">
		
	</div>
	<div id="content2">
		<span class="buttons">
			<div data-dojo-type="dijit.form.Button" data-dojo-props="onClick:function(){Nav.go(Nav.SHIPMENT_ORDER_EDIT, 'tms.china.shipmentorder.orderEdit',{cmd:'add'});}">
	      		<fmt:message key="CMD_new" bundle="${ClientStrings}"></fmt:message>
	      	</div>
	      	<div data-dojo-type="dijit.form.Button" id="shipmentOrderEdit"
	      		data-dojo-props="onClick:function(){Common.currentpage.showEditView();},disabled:true">
	      			编辑
	      	</div>
	      	<div data-dojo-type="dijit.form.Button" id="shipmentViewDetail"
	      		data-dojo-props="onClick:function(){Common.currentpage.viewDetail();},disabled:true">
	      		<fmt:message key="CMD_viewdetail" bundle="${ClientStrings}"></fmt:message>
	      	</div>
	      	<div data-dojo-type="dijit.form.Button" data-dojo-props="onClick:function(){Common.currentpage.popUpImport();}" id="import">
	      		<fmt:message key="CMD_EXCLE_IMPORT" bundle="${ClientStrings}"></fmt:message>
	      	</div>
		</span>
	
		<div class="datagrid_area">
			<div class="datagrid" id="shipmentGridContainer"></div>
			<div class="pagination" id="shipment_pageBar"></div>
		</div>
	</div>
	<div id="dialog" dojoType="dijit.Dialog"></div>