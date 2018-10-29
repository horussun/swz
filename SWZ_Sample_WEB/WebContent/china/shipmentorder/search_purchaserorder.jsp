<!-- 
	搜索销售订单页面
 -->
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../common/_tag.jspf"%>

<div class="searchpart" id="searchpart">
	<table class="searcher" dojoAttachPoint="searchtable">
		<tr>
			<th>订单编号：</th>
			<td><input type="text" id="orderNumber"
				dojoType="dijit.form.TextBox" name="findText" style="width: 140px" />
			</td>
			<td class="shortselect">
			<button type="button" dojoType="dijit.form.Button"
				onClick="Common.currentpage.search()">搜索</button>
			</td>
		</tr>
	</table>
</div>

<div id="content2">
	<div class="datagrid_area">
		<div class="datagrid" id="poGridContainer"></div>
		<div class="pagination" id="po_pageBar"></div>
	</div>
	
	<div id="btarea">
		<div id="infobts">
			<span class="passivebt">
				<div data-dojo-type="dijit.form.Button"
					data-dojo-props="onClick:function(){Common.currentpage.selectPurchaserOrder();}">
				<fmt:message key="CMD_done" bundle="${ClientStrings}" />
				</div>
			</span>
		</div>
	</div>
</div>


