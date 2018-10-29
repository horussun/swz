<!-- 
	运输订单详细页面
 -->
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../common/_tag.jspf"%>
<%-- Page to show/edit/add single order --%>
<div id="content2">
	<form id="shipment">
		<div id="infoarea"><br />
			<label class="title">查看详细信息</label>
			<table class="detail_info">
			
				<tr>
					<th>运单编码：</th>
					<td>${order.orderNumber }</td>
			
					<th>价格：</th>
					<td>${order.price }</td>
				</tr>
				<tr>
					<th>对应销售订单：</th>
					<td>${salesOrder.orderNumber }</td>
			
					<th>运单生产方式：</th>
					<td>${creationType }</td>
				</tr>
				<tr>
					<th>运单状态：</th>
					<td>${order.orderStatus }</td>
			
					<th>生产批次：</th>
					<td>${order.batchNumber }</td>
				</tr>
			
				<tr>
					<th>预计发运日期：</th>
					<td>${order.deliveryDate }</td>
			
					<th>预计到达日期：</th>
					<td>${order.arrivalDate }</td>
				</tr>
				<tr>
					<th>下单日期 ：</th>
					<td>${order.orderPlacedTime }</td>
					<th>货号：</th>
					<td>${order.containerNumbers }</td>
				</tr>
			
			</table>
		
			<hr />
			<label class="title">产品信息</label>
			<table class="detail_info">
				<tr>
					<th><fmt:message key="BEAN_CE_PRODUCT_NAME"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--产品名称  -->
					<td>${product.productName }</td>
			
					<th><fmt:message key="BEAN_CE_PRODUCT_ID"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--产品编码  -->
					<td>${product.productNumber }</td>
			
				</tr>
				<tr>
			
					<th>订货数量：</th>
					<td>${salesOrder.quantity }</td>
			
				<!-- <th>销售单位：</th><td>${uom.uomName }</td> -->
					
				</tr>
			</table>
		
			<hr />
			<label class="title">组织信息</label>
			<table class="detail_info">
				<tr>
					<th>承运商：</th>
					<td>${carrier.organizationName }</td>
			
					<th>预收货地址：</th>
					<td>${receiveAdd.addressName}</td>
				</tr>
				<tr>
					<th>出发地地址：</th>
					<td>${startAdd.addressName}</td>
					<th>目的地地址：</th>
					<td>${destinationAdd.addressName}</td>
				</tr>
				<tr>
					<th>发运客户：</th>
					<td>${start.organizationName }</td>
					<th>买家：</th>
					<td>${customer.organizationName}</td>
					
				</tr>
			</table>
			<hr />
			<label class="title">订单来源信息</label>
			<table class="detail_info">
				<tr>
					<th><fmt:message key="BEAN_CE_ORDER_ORIGIN"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--订单来源  -->
					<td>${transfer.orderSource }</td>
			
					<th><fmt:message key="BEAN_CE_ORDER_PURPOSE"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--订单目的  -->
					<td>${transfer.orderDestination }</td>
				</tr>
				<tr>
					<th><fmt:message key="BEAN_CE_ORDER_INPUT_TIME"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--订单传输/录入时间  -->
					<td>${transfer.orderTransferTime }</td>
				</tr>
			</table>
			<%if (request.getParameter("tracker")!=null){ %>
			<hr />
			<label class="title">运单追踪</label>
			<table class="detail_info" width="100%">
				<tr>
					<th>2012-05-25 9:16:43：</th>
					<td>上海 已接受发货</td>
				</tr>
				<tr>
					<th>2012-05-25 10:11:55：</th>
					<td>上海 已发往 宁波</td>
				</tr>
				<tr>
					<th>2012-05-25 15:09:14：</th>
					<td>宁波 到达</td>
				</tr>
				<tr>
					<th>2012-05-25 16:23:22：</th>
					<td>宁波 派件员 正在派件中</td>
				</tr>
			</table>
			
			<% }%>	
			<input type="hidden" id="type" value="${type}">
			</div>

	</form>
	<div id="btarea">
		<div id="infobts">
			<span class="passivebt">
				<div dojotype="dijit.form.Button"><fmt:message key="CMD_done"
					bundle="${ClientStrings}"></fmt:message>
				<script type="dojo/method"
					event="onClick" args="e">
        		if(document.forms["shipment"].type.value){
					Nav.closeDialog();
				}else{
					Nav.closePage();
				}
        	   </script>
	        	</div>
			</span>
		</div>
	</div>
</div>