<!-- 
	采购销售订单查询弹出窗口页面
 -->
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../common/_tag.jspf"%>
<style type="text/css">
@import "${pageContext.request.contextPath}/china/css/sdstyle.css";

@import "${pageContext.request.contextPath}/china/css/style.css";
</style>
<div class="searchpart" id="searchpart">
	
</div>
<div id="content2">
	<div class="datagrid_area">
		<div class="datagrid" id="soGridContainer"></div>
		<div class="pagination" id="so_pageBar"></div>
	</div>
	
	<div id="btarea">
		<div id="infobts">
			<span class="passivebt">
				<div data-dojo-type="dijit.form.Button"
					data-dojo-props="onClick:function(){Common.currentpage.selectSaleOrder();}">
				<fmt:message key="CMD_done" bundle="${ClientStrings}" />
				</div>
			</span>
		</div>
	</div>
</div>


