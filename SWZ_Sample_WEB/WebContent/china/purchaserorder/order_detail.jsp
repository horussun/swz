<!-- 
	采购订单详细页面
 -->
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../common/_tag.jspf"%>
<%-- Page to show/edit/add single order --%>
<div id="content2">
	<form id="purchase"><input type="hidden" id="poId" name="poId"
		value="${order.id}">
		<div id="infoarea" style="height:580px;"><br />
		<label class="title">查看详细信息</label>
		<table class="detail_info">
		
			<tr>
				<th><fmt:message key="BEAN_CE_ORDER_ID" bundle="${ClientStrings}"></fmt:message>：</th>
				<td>${order.orderNumber }</td>
		
				<th><fmt:message key="BEAN_PO_PRODUCTREQUIREMENT"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--生产工艺要求  -->
				<td>${order.productRequirement }</td>
		
			</tr>
			<tr>
				<th><fmt:message key="BEAN_PO_QUANTITY" bundle="${ClientStrings}"></fmt:message>：</th>
				<!--订货数量  -->
				<td>${order.quantity }</td>
		
				<th><fmt:message key="BEAN_CE_ORDER_PRICE"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--订单价格  -->
				<td>${order.price }</td>
			</tr>
			<tr>
				<th><fmt:message key="BEAN_PO_PRODUCTMATERIALREQUIREMENT"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--辅料要求  -->
				<td>${order.productMaterialRequirement }</td>
		
				<th><fmt:message key="BEAN_PO_SALEORDER"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--对应销售订单  -->
				<td>${saleOrder.orderNumber }</td>
			</tr>
			<tr>
				<th><fmt:message key="BEAN_PO_BUYER" bundle="${ClientStrings}"></fmt:message>：</th>
				<!--订货单位  -->
				<td>宁波郁金香服装有限公司</td>
		
				<th><fmt:message key="BEAN_PO_ORDERCREATEWAY"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--订单生成方式  -->
				<td>${creationType }</td>
			</tr>
		
			<tr>
				<th><fmt:message key="BEAN_PO_ORDERPLACEDTIME"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--下单日期  -->
				<td>${order.orderPlacedTime }</td>
		
				<th><fmt:message key="BEAN_CE_ORDER_STATE"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--订单状态 -->
				<td>${order.orderStatus }</td>
			</tr>
			<tr>
				<th><fmt:message key="BEAN_PO_PRODUCTDESIGNDIAGRAM"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--产品设计图  -->
				<td><img id="image"
					src="${pageContext.request.contextPath}${order.productDesignDiagramPath}" 
					onerror="this.src='${pageContext.request.contextPath}/upload/default.jpg'"
					width="160" height="90"></td>
			</tr>
		</table>
		
		<hr />
		<label class="title">产品信息</label>
		<table class="detail_info">
			<tr>
				<th><fmt:message key="BEAN_CE_PRODUCT_TOPIC"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--产品主题  -->
				<td>${product.productTopic }</td>
				
				<th><fmt:message key="BEAN_CE_PRODUCT_NAME"
					bundle="${ClientStrings}"></fmt:message>：<!--产品名称  -->
				<td>${product.productName }</td>
				
			</tr>
			<tr>
				<th><fmt:message key="BEAN_CE_PRODUCT_STYLE"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--产品款式  -->
				<td>${product.productStyle }</td>
		
				<th><fmt:message key="BEAN_CE_PRODUCT_TYPE"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--产品类型  -->
				<td>${product.productType }</td>
			</tr>
		
			<tr>
				<th><fmt:message key="BEAN_CE_PRODUCT_COLOR"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--产品颜色  -->
				<td>${product.productColor }</td>
		
				<th><fmt:message key="BEAN_CE_SEX_TYPE" bundle="${ClientStrings}"></fmt:message>：</th>
				<!--性别类型  -->
				<td>${product.productSexType }</td>
			</tr>
		
			<tr>
				<th><fmt:message key="BEAN_CE_PRODUCT_MATERIAL"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--产品材质  -->
				<td>${product.productMaterial }</td>
		
				<th><fmt:message key="BEAN_CE_PRODUCT_NUMBER"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--产品号码  -->
				<td>${product.productCode }</td>
			</tr>
		
			<tr>
				<th><fmt:message key="BEAN_CE_PRODUCT_DESCRIPTION"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--产品描述  -->
				<td>
					<div id="detaildiv" style="width:100px">查看产品描述</div>
	             	<input type="hidden" id="description" value='${product.productDescription}'>
        		</td>
		
				<th><fmt:message key="BEAN_CE_PRODUCT_ID"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--产品编码  -->
				<td>${product.productNumber }</td>
			</tr>
		</table>
		
		<hr />
		<label class="title">组织信息</label>
		<table class="detail_info">
			<tr>
				<th><fmt:message key="BEAN_PO_VENDOR" bundle="${ClientStrings}"></fmt:message>：</th>
				<!--供应商  -->
				<td>${vendor.organizationName }</td>
		
				<th><fmt:message key="BEAN_PO_DELIVERYDESTINATIONADDRESS"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--预计送货地址  -->
				<td>${deliverAdd.addressName}</td>
			</tr>
		
			<tr>
				<th><fmt:message key="BEAN_PO_BUYERORGANIZATION"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--采购单位  -->
				<td>${buyer.organizationName }</td>
				<th><fmt:message key="BEAN_CE_PURCHASER_DELIVERY_ADDRESS"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--买家发货地址  -->
				<td>${buyerAdd.addressName }</td>
			</tr>
			<tr>
				<th><fmt:message key="BEAN_CE_PURCHASER_CONTACT"
					bundle="${ClientStrings}"></fmt:message>：</th>
				<!--买家联系方式  -->
				<td>${buyerAdd.contactInfo }</td>
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
		<input type="hidden" id="type" value="${type }"></div>
	</form>
	<div id="btarea">
		<div id="infobts">
			<span class="passivebt">
				<div dojotype="dijit.form.Button"><fmt:message key="CMD_done"
					bundle="${ClientStrings}"></fmt:message> 
					<script type="dojo/method" event="onClick" args="e">
        				if(document.forms["purchase"].type.value){
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