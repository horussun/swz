<!-- 
	采购订单编辑页面
 -->
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../common/_tag.jspf"%>
<%-- Page to show/edit/add single order --%>

<div id="content2">
	<div dojoType="dojo.data.ItemFileReadStore" jsId="pAddStore"></div>
	<div dojoType="dojo.data.ItemFileReadStore" jsId="vendorStore"></div>
	<div dojoType="dojo.data.ItemFileReadStore" jsId="deliveryStore"></div>

	<form id="order_form" data-dojo-type="dijit.form.Form" action=""
		method="POST" encType="multipart/form-data"
		data-dojo-props='encType:"multipart/form-data",method:"POST"'>
		<script type="dojo/method" event="onSubmit">
      		if (this.validate()) {
				Common.currentpage.saveOrder();
				return false;
			} else {
				return false;	
			}
        </script>
        
		<div id="infoarea"><br>
			<label class="title">采购订单基本信息</label>
			<table class="info">
				<tr>
					<th><fmt:message key="BEAN_CE_ORDER_ID" bundle="${ClientStrings}"></fmt:message>：</th>
					<!--订单编号  -->
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='name:"PurchaserOrder_orderNumber",value:"${order.orderNumber}",maxLength:30,disabled:true '
						id="PurchaserOrder_orderNum" /></td>
			
					<th><fmt:message key="BEAN_PO_PRODUCTREQUIREMENT"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--生产工艺要求  -->
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='name:"PurchaserOrder_productRequirement",value:"${order.productRequirement}",maxLength:30 '
						id="PurchaserOrder_productRequirement" />
				</tr>
				
				<tr>
					<th><fmt:message key="BEAN_PO_QUANTITY" bundle="${ClientStrings}"></fmt:message>：</th>
					<!--订货数量  -->
					<td><input name="PurchaserOrder_quantity"
						value="${order.quantity}" regExp="^\d+(\.\d+)?"
						id="PurchaserOrder_quantity" type="text"
						dojotype="dijit.form.ValidationTextBox" /></td>
			
					<th><fmt:message key="BEAN_CE_ORDER_PRICE"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--订单价格  -->
					<td><input name="PurchaserOrder_price" value="${order.price}"
						regExp="^\d+(\.\d+)?" maxLength="30" required="true"
						id="PurchaserOrder_price" type="text"
						dojotype="dijit.form.ValidationTextBox" /></td>
				</tr>
			
				<tr>
					<th><fmt:message key="BEAN_PO_PRODUCTMATERIALREQUIREMENT"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--辅料要求  -->
			
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='name:"PurchaserOrder_productMaterialRequirement",value:"${order.productMaterialRequirement}",maxLength:30 '
						id="PurchaserOrder_productMaterialRequirement" /></td>
					<th><fmt:message key="BEAN_PO_SALEORDER"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--对应销售订单  -->
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='name:"SaleOrder_orderNumber",value:"${saleOrder.orderNumber}",maxLength:30,disabled:true'
						id="SaleOrder_orderNumber" /> <span class="iconbt">
					<div data-dojo-type="dijit.form.Button"
						data-dojo-props='iconClass:"plusIcon classIcon",showLabel:false,
										onClick:function(){Common.currentpage.searchSaleOrder();}'>
			
					</div>
					</span></td>
			
				</tr>
				<tr>
					<th><fmt:message key="BEAN_PO_BUYER" bundle="${ClientStrings}"></fmt:message>：</th>
					<!--订货单位  -->
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='value:"宁波郁金香服装有限公司",maxLength:30,disabled:true ' />
			
					</td>
			
					<th><fmt:message key="BEAN_PO_ORDERCREATEWAY"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--订单生成方式  -->
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='value:"${creationType }",maxLength:30,disabled:true '
						id="aaa" /></td>
					</td>
				</tr>
				<tr>
					<th><fmt:message key="BEAN_PO_ORDERPLACEDTIME"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--下单日期  -->
					<td><input name="PurchaserOrder_orderPlacedTime:date"
						id="PurchaserOrder_orderPlacedTime:date" type="text"
						dojoType="dijit.form.DateTextBox" required="true" value="now" /><!--value:"${order.orderPlacedTime}"  -->
						<span class="short"> 
						<input type="text" data-dojo-type="dijit.form.TimeTextBox" 
							data-dojo-props='constraints:{timePattern:"HH:mm:ss"},value:new Date(),required:true,name:"PurchaserOrder_orderPlacedTime:time"'
							id="PurchaserOrder_orderPlacedTime:time" />
						</span>
			
					</td>
				</tr>
				<tr>
					<th><fmt:message key="BEAN_PO_PRODUCTDESIGNDIAGRAM"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--产品设计图  -->
					<td><img id="image"
						src="${pageContext.request.contextPath}${order.productDesignDiagramPath}"
						width="160" height="90"> <input class="browseButton"
						name="uploadedfile" multiple="false" type="file"
						dojoType="dojox.form.Uploader" label="上传..." id="uploader2" /></td>
			
				</tr>
			</table>
			
			<hr />
			<label class="title">产品信息</label>
			<table class="info">
				<tr>
					<th><fmt:message key="BEAN_CE_PRODUCT_NAME"
						bundle="${ClientStrings}"></fmt:message>：<!--产品名称  --></th>
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='name:"Product_productName",value:"${product.productName}",maxLength:30,disabled:true '
						id="Product_productName" /> <span class="iconbt">
					<div data-dojo-type="dijit.form.Button"
						data-dojo-props='iconClass:"plusIcon classIcon",showLabel:false,
										onClick:function(){Common.currentpage.searchProduct();}'>
			
					</div>
					</span></td>
					<th><fmt:message key="BEAN_CE_PRODUCT_TOPIC"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--产品主题  -->
					<td><input type="text" dojotype="dijit.form.ValidationTextBox"
					   value='${product.productTopic}' disabled=true
						id="Product_productTopic" />
					</td>
					
				</tr>
				<tr>
					<th><fmt:message key="BEAN_CE_PRODUCT_STYLE"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--产品款式  -->
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='name:"Product_productStyle",value:"${product.productStyle}",maxLength:30,disabled:true '
						id="Product_productStyle" /></td>
			
					<th><fmt:message key="BEAN_CE_PRODUCT_TYPE"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--产品类型  -->
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='name:"Product_productType",value:"${product.productType}",maxLength:30,disabled:true '
						id="Product_productType" /></td>
				</tr>
			
				<tr>
					<th><fmt:message key="BEAN_CE_PRODUCT_COLOR"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--产品颜色  -->
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='name:"Product_productColor",value:"${product.productColor}",maxLength:30,disabled:true '
						id="Product_productColor" /></td>
			
					<th><fmt:message key="BEAN_CE_SEX_TYPE" bundle="${ClientStrings}"></fmt:message>：</th>
					<!--性别类型  -->
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='name:"Product_productSexType",value:"${product.productSexType}",maxLength:30,disabled:true '
						id="Product_productSexType" /></td>
				</tr>
			
				<tr>
					<th><fmt:message key="BEAN_CE_PRODUCT_MATERIAL"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--产品材质  -->
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='name:"Product_productMaterial",value:"${product.productMaterial}",maxLength:30,disabled:true '
						id="Product_productMaterial" /></td>
			
					<th><fmt:message key="BEAN_CE_PRODUCT_NUMBER"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--产品号码  -->
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='name:"Product_productCode",value:"${product.productCode}",maxLength:30,disabled:true '
						id="Product_productCode" /></td>
				</tr>
			
				<tr>
					<th><fmt:message key="BEAN_CE_PRODUCT_DESCRIPTION"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--产品描述  -->
					<td>
					<textarea data-dojo-type="dijit.form.SimpleTextarea"
						 	data-dojo-props='name:"Product_productDescription"
						 	,value:"",rows:4,cols:22,readOnly:true'
        					id="Product_productDescription">${product.productDescription}
        			</textarea> 
					</td>
						
					<th><fmt:message key="BEAN_CE_PRODUCT_ID"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--产品编码  -->
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='name:"Product_productNumber",value:"${product.productNumber}",maxLength:30,disabled:true'
						id="Product_productNumber" /></td>
				</tr>
			
			</table>
			
			<hr />
			<label class="title">组织信息</label>
			<table class="info">
			
				<tr>
					<th><fmt:message key="BEAN_PO_VENDOR" bundle="${ClientStrings}"></fmt:message>：</th>
					<!--供应商  -->
					<td>
					<div dojoType="dijit.form.FilteringSelect" id="vendor"
						name="PurchaserOrder_vendorOrganizationId" store="vendorStore"
						searchAttr="id" labelAttr="name">
					</td>
			
					<th><fmt:message key="BEAN_PO_DELIVERYDESTINATIONADDRESS"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--预计送货地址  -->
					<td>
					<div dojoType="dijit.form.FilteringSelect" id="delivery"
						name="PurchaserOrder_deliveryDestinationAddressId"
						store="deliveryStore" searchAttr="id" labelAttr="name">
					</td>
				</tr>
				<tr>
					<th><fmt:message key="BEAN_PO_BUYERORGANIZATION"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--采购单位  -->
					<td><!--	<div dojoType="dijit.form.FilteringSelect" id="purchaser"  store="purchaserStore" onChange="Common.currentpage.changePurchaser()" searchAttr="id"
										 labelAttr="name" 
										 dojoProps='disabled:true' value="${order.buyerOrganizationId }">-->
					<input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='value:"${buyer.organizationName}",maxLength:30,disabled:true ' />
					<!--  <select
											data-dojo-props='name:"f_weightUnit", maxHeight:"150"'
											id="f_weightUnit" data-dojo-type="dijit.form.Select"
											style="width: 100px;">
												<c:forEach var="wu" items="${weightUnit}" varStatus="s">
													<option value="${wu.key}:${wu}"
														<c:if test="${wu.key eq order.weightUnit.key}">selected="selected"</c:if>>${wu}</option>
												</c:forEach>
									</select>--></td>
					<th><fmt:message key="BEAN_CE_PURCHASER_DELIVERY_ADDRESS"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--买家发货地址  -->
					<td>
					<div dojoType="dijit.form.FilteringSelect" id="pAdd"
						name="PurchaserOrder_buyerOrganizationAddressId" store="pAddStore"
						onChange="Common.currentpage.changePAdd()" searchAttr="id"
						labelAttr="name"></div>
					</td>
			
			
				</tr>
				<tr>
					<th><fmt:message key="BEAN_CE_PURCHASER_CONTACT"
						bundle="${ClientStrings}"></fmt:message>：</th>
					<!--买家联系方式  -->
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='value:"",maxLength:30,disabled:true '
						id="Contact_info" /></td>
				</tr>
			
			</table>
		
		</div>
		<!--隐藏域  -->
		<input type="hidden" name="PurchaserOrder_productDesignDiagramPath"
			id="PurchaserOrder_productDesignDiagramPath"
			value="${order.productDesignDiagramPath}"> <input type="hidden"
			name="PurchaserOrder_id" id="PurchaserOrder_id" value="${order.id }">
		<input type="hidden" name="PurchaserOrder_productId"
			id="PurchaserOrder_productId" value="${order.productId }"> <input
			type="hidden" name="PurchaserOrder_saleOrderId"
			id="PurchaserOrder_saleOrderId" value="${order.saleOrderId }">
		<input type="hidden" name="PurchaserOrder_orderNumber"
			id="PurchaserOrder_orderNum" value="${order.orderNumber }"> <input
			type="hidden" id="Hidden_buyerOrganizationId" value="${buyer.id}">
		<input type="hidden" id="Hidden_vendorOrganizationId"
			value="${order.vendorOrganizationId }"> <input type="hidden"
			id="Hidden_deliveryDestinationAddressId"
			value="${order.deliveryDestinationAddressId }"> <input
			type="hidden" id="Hidden_buyerOrganizationAddressId"
			value="${order.buyerOrganizationAddressId }"> <input
			type="hidden" id="Hidden_orderPlacedTime"
			value="${order.orderPlacedTime }">
			
		<div id="btarea">
			<div id="infobts">
				<span class="activebt">
					<button dojotype="dijit.form.Button" type="submit"><fmt:message
						key="CMD_save" bundle="${ClientStrings}"></fmt:message>
					</button>
				</span>
				<span class="passivebt">
					<button dojotype="dijit.form.Button"
						onclick="Common.currentpage.cancelOrder();"><fmt:message
						key="CMD_cancel" bundle="${ClientStrings}"></fmt:message>
					</button>
				</span>
			</div>
		</div>
	</form>
</div>