<!-- 
	运输订单编辑页面
 -->
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../common/_tag.jspf"%>
<%-- Page to show/edit/add single order --%>

<div id="content2">
	<div dojoType="dojo.data.ItemFileReadStore" jsId="carrierStore"></div>
	<div dojoType="dojo.data.ItemFileReadStore" jsId="receiveAddStore"></div>
	<div dojoType="dojo.data.ItemFileReadStore" jsId="startAddStore"></div>
	<div dojoType="dojo.data.ItemFileReadStore" jsId="destinationAdd"></div>

<form id="order_form" data-dojo-type="dijit.form.Form"
	data-dojo-props='encType:"multipart/form-data"'>
	<script type="dojo/method" event="onSubmit">
                if (this.validate()) {
					Common.currentpage.saveOrder();
					return false;
                } else {
					return false;	
				}
     </script>
	<div id="infoarea"><br>
		<label class="title"> <!--<fmt:message
							key="WEB_CHINA_ORDERDETAIL_NEW" bundle="${ClientStrings}" />-->
		运输订单基本信息</label>
		<table class="info">
			<tr>
				<th>运单编码：</th>
				<td><input type="text" data-dojo-type="dijit.form.TextBox"
					data-dojo-props='value:"${order.orderNumber}",maxLength:30 ,disabled:true'
					id="ShipmentOrder_orderNumber" />
				</td>
		
				<th>价格：</th>
		
				<td><input type="text" name="ShipmentOrder_price"
					value="${order.price}" dojotype="dijit.form.ValidationTextBox"
					regExp="^\d+(\.\d+)?" maxLength="30" required="true"
					id="ShipmentOrder_price" />
				</td>
			</tr>
		
			<tr>
				<th>对应销售订单：</th>
				<td><input type="text" data-dojo-type="dijit.form.TextBox"
					data-dojo-props='name:"salesOrder_orderNumber",value:"${salesOrder.orderNumber}",maxLength:30,disabled:true'
					id="salesOrder_orderNumber" /> 
					<span class="iconbt">
					<div data-dojo-type="dijit.form.Button"
						data-dojo-props='iconClass:"plusIcon classIcon",showLabel:false,
						onClick:function(){Common.currentpage.searchSaleOrder();}'>
					</div>
				  </span>
				</td>
		
				<th>运单生成方式：</th>
				<td><input type="text" data-dojo-type="dijit.form.TextBox"
					data-dojo-props='value:"${creationType }",maxLength:30,disabled:true '
					id="aaa" />
				</td>
			</tr>
		
			<tr>
		
				<th>货号：</th>
				<td><input type="text" data-dojo-type="dijit.form.TextBox"
					data-dojo-props='name:"ShipmentOrder_containerNumbers",value:"${order.containerNumbers }",maxLength:30 '
					id="ShipmentOrder_containerNumbers" />
				</td>
				<th>生产批次：</th>
				<td><input type="text" data-dojo-type="dijit.form.TextBox"
					data-dojo-props='name:"ShipmentOrder_batchNumber",value:"${order.batchNumber }",maxLength:30 '
					id="ShipmentOrder_batchNumber" />
				</td>
		
			</tr>
			<tr>
				<th>预计发运日期：</th>
				<td><input name="ShipmentOrder_deliveryDate:date"
					id="ShipmentOrder_deliveryDate" type="text"
					dojoType="dijit.form.DateTextBox" required="true" value="now" />
				</td>
				<th>预计到达日期：</th>
				<td><input name="ShipmentOrder_arrivalDate:date"
					id="ShipmentOrder_arrivalDate" type="text"
					dojoType="dijit.form.DateTextBox" required="true" value="now" /><!--value:"${order.orderPlacedTime}"  -->
				</td>
			</tr>
		
		</table>
	
		<hr />
		<label class="title">产品信息</label>
		<table class="info">
			<tr>
				<th><fmt:message key="BEAN_CE_PRODUCT_NAME"	bundle="${ClientStrings}"></fmt:message>：<!--产品名称  --></th>
				<td><input type="text" data-dojo-type="dijit.form.TextBox"
					data-dojo-props='name:"Product_productName",value:"${product.productName}",maxLength:30,disabled:true '
					id="Product_productName" />
				</td>
				<th><fmt:message key="BEAN_CE_PRODUCT_ID" bundle="${ClientStrings}"></fmt:message>：</th>
				<!--产品编码  -->
				<td><input type="text" data-dojo-type="dijit.form.TextBox"
					data-dojo-props='name:"Product_productNumber",value:"${product.productNumber}",maxLength:30,disabled:true'
					id="Product_productNumber" />
				</td>
			</tr>
			<tr>
				<th>订货数量：</th>
				<td><input type="text" data-dojo-type="dijit.form.TextBox"
					data-dojo-props='name:"salesOrder_quantity",value:"${salesOrder.quantity}",maxLength:30,disabled:true'
					id="salesOrder_quantity" />
				</td>
		
			 <!-- <th>销售单位：</th>
				<td><input type="text" data-dojo-type="dijit.form.TextBox"
					data-dojo-props='name:"Uom_uomName",value:"${uom.uomName}",maxLength:30,disabled:true'
					id="Uom_uomName" />	 --> 
				</td>
			</tr>
		</table>
	
		<hr />
		<label class="title">组织信息</label>
		<table class="info">
			<tr>
				<th>承运商：</th>
				<td>
				<div dojoType="dijit.form.FilteringSelect" id="carrier"
					name="ShipmentOrder_carrierOrganizationId" store="carrierStore"
					searchAttr="id" labelAttr="name">
				</div>
				</td>
		
				<th>预收货地址：</th>
				<td>
				<div dojoType="dijit.form.FilteringSelect" id="receiveAdd"
					name="ShipmentOrder_receiveAddressId" store="receiveAddStore"
					searchAttr="id" labelAttr="name">
				</div>
				</td>
			</tr>
			<tr>
				<th>出发地地址：</th>
				<td>
				<div dojoType="dijit.form.FilteringSelect" id="startAdd"
					name="ShipmentOrder_startAddressId" store="startAddStore"
					searchAttr="id" labelAttr="name">
				</div>
				</td>
		
				<th>目的地地址：</th>
				<td>
				<div dojoType="dijit.form.FilteringSelect" id="destinationAdd"
					name="ShipmentOrder_destinationAddressId" store="destinationAdd"
					searchAttr="id" labelAttr="name">
				</div>
				</td>
			</tr>
			<tr>
				<th>发运客户：</th>
				<!--发运客户  -->
				<td><input type="text" data-dojo-type="dijit.form.TextBox"
					data-dojo-props='value:"${start.organizationName }",maxLength:30,disabled:true ' />
				</td>
				<th>买家：</th>
				<!--买家  -->
				<td><input type="text" data-dojo-type="dijit.form.TextBox"
					data-dojo-props='value:"${customer.organizationName}",maxLength:30,disabled:true' id="SalesBuyer" />
				</td>
			</tr>
		
		
		</table>
	
	</div>
	<input type="hidden" name="ShipmentOrder_id" id="ShipmentOrder_id" value="${order.id }">
	<input type="hidden" name="ShipmentOrder_purchaseOrderId" id="ShipmentOrder_purchaserOrderId" value="${order.purchaseOrderId }">
	<input type="hidden" id="Hidden_carrierOrganizationId"	value="${order.carrierOrganizationId }">
	<input type="hidden"id="Hidden_receiveAddressId" value="${order.receiveAddressId }">
	<input type="hidden" id="Hidden_startAddressId"	value="${order.startAddressId }"> 
	<input type="hidden"id="Hidden_destinationAddressId" value="${order.destinationAddressId }">
	<input type="hidden" id="Hidden_deliveryDate"value="${order.deliveryDate }"> 
	<input type="hidden"id="Hidden_arrivalDate" value="${order.arrivalDate }"> 
	<input type="hidden" id="ShipmentOrder_orderStatus"	value="${order.orderStatus }"> 
	<input type="hidden"name="ShipmentOrder_orderNumber" value="${order.orderNumber}">
	<input type="hidden" id="Hidden_customerId" value="${salesOrder.customerId}">
	<div id="btarea">
	<div id="infobts">
		<span class="activebt">
		<button dojotype="dijit.form.Button" type="submit">
		<fmt:message key="CMD_save" bundle="${ClientStrings}"></fmt:message>
		</button>
		</span> 
	    <span class="passivebt">
	    <button dojotype="dijit.form.Button"
		onclick="Common.currentpage.cancelOrder();">
		<fmt:message key="CMD_cancel" bundle="${ClientStrings}"></fmt:message>
		</button>
	   </span>
	</div>
	</div>
</form>
</div>