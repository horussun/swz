<!-- 
	销售订单编辑页面
 -->
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../common/_tag.jspf"%>

<div id="content2">
	<div dojoType="dojo.data.ItemFileReadStore" jsId="purchaserStore"></div>
	<div dojoType="dojo.data.ItemFileReadStore" jsId="pAddStore"></div>

	<form id="order_form" data-dojo-type="dijit.form.Form" action=""
		method="POST" encType="multipart/form-data"
		data-dojo-props='encType:"multipart/form-data",method:"POST"'>
		<script
		type="dojo/method" event="onSubmit">
		 if (this.validate()) {
					Common.currentpage.save();
					return false;
					
                } else {
					return false;	
       
				}
		
		
	</script>
		<div id="infoarea"><br>
			<label class="title"> <!--<fmt:message key="WEB_CHINA_ORDERDETAIL_NEW" bundle="${ClientStrings}" />-->
			销售订单基本信息 </label>
			<table class="info">
				<tr>
					<th><fmt:message key="BEAN_CE_ORDER_ID" bundle="${ClientStrings}" />
					</th>
					<td><input type="text"
						data-dojo-type="dijit.form.ValidationTextBox"
						data-dojo-props='disabled:true,value:"${order.orderNumber}"'
						id="SalesOrder_orderNumber" />
					</td>
			
					<th><fmt:message key="BEAN_CE_ORDERFORM_WAY"
						bundle="${ClientStrings}" />
					</th>
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='name:"SalesOrder_orderCreationType",value:"${creationType }",maxLength:30,disabled:true '
						id="SalesOrder_orderCreationType" />
					</td>
				</tr>
			
				<tr>
					<th><fmt:message key="BEAN_CE_ORDER_DATE"
						bundle="${ClientStrings}" />
					</th>
					<td><input type="text" dojotype="dijit.form.DateTextBox"
						name="SalesOrder_orderPlacedTime:date" required="true" value="now"
						id="SalesOrder_orderPlacedTime:date" />
						<span class="short"> 
						<input type="text" data-dojo-type="dijit.form.TimeTextBox" 
							data-dojo-props='constraints:{timePattern:"HH:mm:ss"},value:new Date(),required:true,name:"SalesOrder_orderPlacedTime:time"'
							id="SalesOrder_orderPlacedTime:time" />
						</span>
					</td>
					
					<th><fmt:message key="BEAN_CE_ORDER_PRICE"
						bundle="${ClientStrings}" />
					</th>
					<td><input type="text" name="SalesOrder_price"
						value="${order.price}" dojotype="dijit.form.ValidationTextBox"
						regExp="^\d+(\.\d+)?" maxLength="30" required="true"
						id="SalesOrder_price" />
					</td>
			
				</tr>
				<tr>
					<th><fmt:message key="BEAN_CE_PCOUNT" bundle="${ClientStrings}" />
					</th>
					<td><input type="text" name="SalesOrder_quantity"
						value="${order.quantity}" regExp="^\d+(\.\d+)?"
						dojotype="dijit.form.ValidationTextBox" id="SalesOrder_quantity" />
					</td>
				</tr>
			</table>
		
			<hr /><label class="title">买家信息</label>
			<table class="detail_info">
				<tr>
					<th><fmt:message key="BEAN_CE_PURCHASER"
						bundle="${ClientStrings}" /></th>
					<!-- 买家 -->
					<td>
					<div dojoType="dijit.form.FilteringSelect" id="purchaser"
						name="SalesOrder_customerId" store="purchaserStore"
						onChange="Common.currentpage.changePurchaser()" searchAttr="id"
						labelAttr="name">
				   </div>
					</td>
			
					<th><fmt:message key="BEAN_CE_PURCHASER_DELIVERY_ADDRESS"
						bundle="${ClientStrings}" /><!--买家发货地址  -->
					</th>
					<td>
					<div dojoType="dijit.form.FilteringSelect" id="pAdd"
						name="SalesOrder_customerAddressId" store="pAddStore"
						onChange="Common.currentpage.changePAdd()" searchAttr="id"
						labelAttr="name">
					</div>
					</td>
			
				</tr>
			
				<tr>
					<th><fmt:message key="BEAN_CE_PURCHASER_CONTACT"
						bundle="${ClientStrings}" /><!--买家联系方式  -->
					</th>
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='maxLength:30 ,disabled:true,value:"${buyerAdd.contactInfo}" '
						id="SalesOrder_contactinfo" />
					</td>
				</tr>
			</table>
		
			<hr /><label class="title">产品信息</label>
			<table class="detail_info">
				<tr>
					<th><fmt:message key="BEAN_CE_PRODUCT_NAME"
						bundle="${ClientStrings}" />
					</th>
					<td><input type="text"
						data-dojo-type="dijit.form.ValidationTextBox"
						data-dojo-props='name:"Product_productName",value:"",maxLength:100 ,disabled:true,value:"${product.productName}"'
						id="Product_productName" />
						<span class="iconbt">
					    <div data-dojo-type="dijit.form.Button" data-dojo-props='iconClass:"plusIcon classIcon", showLabel:false,
					    onClick:function(){Common.currentpage.searchProduct();}'>
					    </div>
					   </span>
				    </td>
					<th><fmt:message key="BEAN_CE_PRODUCT_ID"
						bundle="${ClientStrings}" />
					</th>
					<td><input type="text"
						data-dojo-type="dijit.form.ValidationTextBox"
						data-dojo-props='name:"Product_productNumber",maxLength:128,disabled:true,value:"${product.productNumber}"'
						id="Product_productNumber" />
					</td>
				</tr>
			
				<tr>
					<th><fmt:message key="BEAN_CE_PRODUCT_MATERIAL"
						bundle="${ClientStrings}" />
					</th>
					<td><input type="text"
						data-dojo-type="dijit.form.ValidationTextBox"
						data-dojo-props='name:"Product_productMaterial",value:"${product.productMaterial}",maxLength:200,disabled:true'
						id="Product_productMaterial" />
					</td>
					<th><fmt:message key="BEAN_CE_LISTED_ACCORDING"
						bundle="${ClientStrings}" />
					</th>
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='name:"Product_periodOfSale",value:"${product.periodOfSale}",maxLength:30 ,disabled:true'
						id="Product_periodOfSale" />
					</td>
			
				</tr>
				<!--  
				<tr>
					
					<th><fmt:message key="BEAN_CE_ON_SALEDATE"
						bundle="${ClientStrings}" />
					</th>
					<td><input type="text" data-dojo-type="dijit.form.TextBox"
						data-dojo-props='name:"Product_dateOnSale",value:"${product.dateOnSale}",maxLength:30 ,disabled:true'
						id="Product_dateOnSale" />
					</td>
					<th><fmt:message key="BEAN_CE_MARKETING_UNIT"
						bundle="${ClientStrings}" />
					</th>
					<td><input type="text"
						data-dojo-type="dijit.form.ValidationTextBox"
						data-dojo-props='name:"Product_productUomName",value:"${saleUmoid.uomName}",maxLength:30,disabled:true'
						id="Product_productUomName" />
					</td>
				</tr>
				-->
				
				<tr>
					<th><fmt:message key="BEAN_CE_PRODUCT_TYPE"
						bundle="${ClientStrings}" />
					</th>
					<td><input type="text"
						data-dojo-type="dijit.form.ValidationTextBox"
						data-dojo-props='name:"Product_productType",value:"${product.productType}",maxLength:128 ,disabled:true'
						id="Product_productType" />
					</td>
			
					<th><fmt:message key="BEAN_CE_SEX_TYPE" bundle="${ClientStrings}" />
					</th>
					<td><input type="text"
						data-dojo-type="dijit.form.ValidationTextBox"
						data-dojo-props='name:"Product_productSexType",value:"${product.productSexType}",maxLength:30 ,disabled:true'
						id="Product_productSexType" />
					</td>
				</tr>
				<tr>
					<th><fmt:message key="BEAN_CE_PRODUCT_NUMBER"
						bundle="${ClientStrings}" />
					</th>
					<td><input type="text"
						data-dojo-type="dijit.form.ValidationTextBox"
						data-dojo-props='value:"${product.productCode}",maxLength:30 ,disabled:true'
						id="Product_productCode" />
					</td>
					<th><fmt:message key="BEAN_CE_PRODUCT_TOPIC"
						bundle="${ClientStrings}" />
					</th>
					<td>
					<input type="text"
						dojoType="dijit.form.ValidationTextBox" disabled=true
						value='${product.productTopic}'
						id="Product_productTopic" /> 

					</td>
					
				</tr>
				<tr>
					<th><fmt:message key="BEAN_CE_PRODUCT_STYLE"
						bundle="${ClientStrings}" />
					</th>
					<td><input type="text"
						data-dojo-type="dijit.form.ValidationTextBox"
						data-dojo-props='name:"Product_productStyle",value:"${product.productStyle}",maxLength:100,disabled:true'
						id="Product_productStyle" />
					</td>
			
					<th><fmt:message key="BEAN_CE_PRODUCT_COLOR"
						bundle="${ClientStrings}" /></th>
					<td><input type="text"
						data-dojo-type="dijit.form.ValidationTextBox"
						data-dojo-props='name:"Product_productColor",value:"${product.productColor}",maxLength:32,disabled:true '
						id="Product_productColor" /></td>
			
				</tr>
				
				<tr>
					<th><fmt:message key="BEAN_CE_PRODUCT_DESCRIPTION"
						bundle="${ClientStrings}" />
					</th>
					<td colspan="3">
						<textarea data-dojo-type="dijit.form.SimpleTextarea"
						 	data-dojo-props='name:"Product_productDescription"
						 	,value:"",rows:4,cols:84,readOnly:true'
        					id="Product_productDescription">${product.productDescription}
        				</textarea> 
					</td>
				</tr>
			
			</table>
		
		
		</div>
		    <input type="hidden" id="SalesOrder_id" name="SalesOrder_id" value="${order.id }"> 
			<input type="hidden" name="SalesOrder_productId" id="SalesOrder_productId" value="${order.productId }"> 
		    <input type="hidden" name="SalesOrder_uomId" id="SalesOrder_uomId" value="${order.uomId }"> 
		    <input type="hidden" id="Hidden_buyerId" value="${order.customerId }">
			<input type="hidden" id="Hidden_buyerAddressId" value="${order.customerAddressId }"> 
			<input type="hidden" id="Hidden_saleOrderPlacedTime" value="${order.orderPlacedTime}">
		    <input type="hidden" name="SalesOrder_orderNumber" value="${order.orderNumber}"> <!-- end of div id="form"  -->
			<div id="btarea">
			  <div id="infobts">
				<span class="activebt">
				<button dojotype="dijit.form.Button" type="submit" id="save">
				<fmt:message
					key="CMD_save" bundle="${ClientStrings}"></fmt:message>
				</button>
				</span> 
				<span class="passivebt">
				<button dojotype="dijit.form.Button"
					onclick=Common.currentpage.cancel();>
					<fmt:message key="CMD_cancel" bundle="${ClientStrings}"></fmt:message>
				</button>
				</span>
			  </div>
			</div>
	</form>
</div>