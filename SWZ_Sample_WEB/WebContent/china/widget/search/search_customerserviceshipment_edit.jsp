<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../../common/_tag.jspf"%>
<style type="text/css">
    @import "../../../javascript/dojo16/dojox/form/resources/CheckedMultiSelect.css";
</style>
  <div id="content2">
       <form id="searchContentParm" data-dojo-type="dijit.form.Form"
		data-dojo-props='encType:"multipart/form-data", onSubmit:function(e){Common.currentpage.save("customerserviceshipment"); return false;}'>
       <div id="infoarea">
         <div class="save_as">
         <table>
              <tr>
                <th><fmt:message key="SEARCH_PROMPT_save_custom_search_as" bundle="${ClientStrings}"/></th>
                <td>
                	<input type="text" id="searchName" dojoType="dijit.form.ValidationTextBox" required="true"  trim=true name="searchName" value="${currentSearchValues.searchName}" />
               		<input dojoType="dijit.form.CheckBox"  name="isDefault"<c:if test="${true eq currentSearchValues.isDefault}">checked</c:if>/><label for="mycheck"><fmt:message key="SEARCH_CHECK_default_search" bundle="${ClientStrings}"/></label>
                </td>
              </tr>
         </table>
         </div>
            <hr />
 	 <div class="constraints">
          <label class="title"><fmt:message key="SEARCH_STR_search_constraints" bundle="${ClientStrings}"/></label>
          <table>
              <%@ include file="search_edit_date_part.jsp" %>
           <!-- <tr>
            	<th>采购订单号</th>
            	<td>
            		<input type="text" name="purchaseOrderNumber" dojoType="dijit.form.ValidationTextBox" value="${currentSearchValues.purchaseOrderNumber_eq}"/>
            	</td>
            </tr>
            <tr>
            	<th>销售订单号</th>
            	<td>
            		<input type="text" name="salesOrderNumber" dojoType="dijit.form.ValidationTextBox" value="${currentSearchValues.salesOrderNumber_eq}"/>
            	</td>
            </tr>  --> 
          <tr>
          <th><fmt:message key="BEAN_CE_ORDERITEM_P_SEARCH_equipmentTypeSearch" bundle="${ClientStrings}"/></th>
              <td><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="equipmentTypeSearch" <c:if test="${currentSearchValues.equipmentTypeSearch_eq!=null}">value="${currentSearchValues.equipmentTypeSearch_eq}"</c:if> >
              		<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              		<c:forEach var="temp" items="${equipmentTypeSearch}" varStatus="s">
						<option value="${temp.key}:${temp}">${temp.displayObject}</option>
					</c:forEach>
              	  </select>
              </td>
            </tr> 
           <tr>
              <th><fmt:message key="BEAN_LM_LMITEM_P_origin" bundle="${ClientStrings}"/></th>
              <td >
			  	<input type="text" id="cs_origin1_text" data-dojo-type="dijit.form.ValidationTextBox" /> <%-- 设置初始化的值 --%> 
				<select
					data-dojo-props='name:"origin",maxHeight:"150"' id="cs_origin1"
					data-dojo-type="dijit.form.FilteringSelect" >
					
					<option value=""><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
					<c:if test="${not empty currentSearchValues.origin_eq}">
					<option value="${currentSearchValues.origin_eq}" selected="selected">${currentSearchValues.origin_eq_value}</option>
					</c:if>
				</select> 
				<span class="iconbt">
						<button id="cs_origin1_search" dojoType="dijit.form.Button"
							iconClass="searchIcon classIcon" showLabel="false" type="button"><fmt:message key="CMD_disp_save" bundle="${ClientStrings}"/></button>
				</span>
             </td>              
            </tr>
            <tr>			
              <th ><fmt:message key="BEAN_LM_LMITEM_P_destination" bundle="${ClientStrings}"/></th>
              <td >
              	<input type="text" id="cs_destination1_text" data-dojo-type="dijit.form.ValidationTextBox"  /> <%-- 设置初始化的值 --%> 
              	<select data-dojo-props='name:"destination",maxHeight:"150"'id="cs_destination1" data-dojo-type="dijit.form.FilteringSelect">
					
					<option value=""><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
					<c:if test="${not empty currentSearchValues.destination_eq}">
					<option value="${currentSearchValues.destination_eq}" selected="selected">${currentSearchValues.destination_eq_value}</option>
					</c:if>
				</select> 
				<span class="iconbt">
					<button id="cs_destination1_search" dojoType="dijit.form.Button"
						iconClass="searchIcon classIcon" showLabel="false" type="button"><fmt:message key="CMD_disp_save" bundle="${ClientStrings}"/></button>
				</span>
			   </td>              
            </tr>
            
            <tr>
              <th><fmt:message key="WEBEAN_UI_COLUMNNAMES_PROPERTYLABEL_CUSTOMER" bundle="${ClientStrings}"/></th>
              <td ><select dojotype="dijit.form.FilteringSelect" autoComplete="true"  name="customer" <c:if test="${currentSearchValues.customer_eq!=null}">value="${currentSearchValues.customer_eq}"</c:if> id="s_customer" onChange="Org.customerdetail('search');">
              			<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              			<c:forEach var="temp" items="${customer}" varStatus="s">
						<option value="${temp.key}:${temp}">${temp.displayObject}</option>
						</c:forEach>
              	   </select>
              	   <span class="iconbt dropiconbt">
			    	<div dojotype="dijit.form.DropDownButton" iconClass="detailIcon classIcon"  showLabel="false"  title="组织详细信息">
					<div id="customerdialog" data-dojo-type="dijit.TooltipDialog">
						<span><fmt:message key="nb.bill.editbill.choose_one" bundle="${NB}"></fmt:message></span>
					</div>
				</div>
			</span>
           </td>
           </tr>
           <tr>     
              <th><fmt:message key="CE_CUSTOMERSERVICESHIPMENTDETAILTABS_LABEL_suppliers" bundle="${ClientStrings}"/></th>
            <td ><select dojotype="dijit.form.FilteringSelect" autoComplete="true"  name="supplier"  <c:if test="${currentSearchValues.supplier_eq!=null}">value="${currentSearchValues.supplier_eq}"</c:if> id="s_supplier" onChange="Org.supplierdetail('search');">
            		<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              		<c:forEach var="temp" items="${supplier}">
					   <option value="${temp.key}:${temp}">${temp.displayObject}</option>
					</c:forEach>
            	 </select> 
            	 <span class="iconbt dropiconbt">
			    	<div dojotype="dijit.form.DropDownButton" iconClass="detailIcon classIcon"  showLabel="false"  title="<fmt:message key="PROFILE_TITLE_org_detail" bundle="${ClientStrings}"/>">
					<div id="supplierdialog" data-dojo-type="dijit.TooltipDialog">
						<span><fmt:message key="nb.bill.editbill.choose_one" bundle="${NB}"></fmt:message></span>
					</div>
				</div>
			</span>
            </td>                    
            </tr>
    
        <tr>
              <th><fmt:message key="BEAN_CE_BILLLW_P_carrier"	bundle="${ClientStrings}"/></th>
              <td><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="carrier" <c:if test="${currentSearchValues.carrier_eq!=null}">value="${currentSearchValues.carrier_eq}"</c:if> id="s_carrier" onChange="Org.carrierdetail('search');"> 
              		<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              		<c:forEach var="temp" items="${carrier}" varStatus="s">
						<option value="${temp.key}:${temp}">${temp}</option>
					</c:forEach>
              	  </select>
	               <span class="iconbt dropiconbt">
			    	<div dojotype="dijit.form.DropDownButton" iconClass="detailIcon classIcon"  showLabel="false"   title="<fmt:message key="PROFILE_TITLE_org_detail" bundle="${ClientStrings}"/>">
					<div id="carrierdialog" data-dojo-type="dijit.TooltipDialog">
						<span><fmt:message key="nb.bill.editbill.choose_one" bundle="${NB}"></fmt:message></span>
					</div>
				</div>
			</span>
              </td>
             </tr>
		<tr style="display:none">
		<th><fmt:message key="BEAN_CE_BILLLW_P_scacCode"
			bundle="${ClientStrings}"></fmt:message></th>
		<td>
		<input name="carrierScac"  maxHeight="150" id="s_scac" dojotype="dijit.form.ValidationTextBox" value="${currentSearchValues.carrierScac_subString}"/></td>
		</tr>
            <tr>
              <th><fmt:message key="WEB_BASE_CE_BILLEDIT_HEADER_description" bundle="${ClientStrings}"/></th>
              <td colspan="3" > <textarea class="info_textarea" dojoType="dijit.form.Textarea" name="itemDescription" value="${currentSearchValues.itemDescription_subString}" ></textarea></td>
            </tr>

             <tr>
              <th><fmt:message key="BEAN_TOURS_TOURCYCLECRITERIALW_P_referenceType" bundle="${ClientStrings}"/></th>
              <td>
              	<select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="referenceType" <c:if test="${currentSearchValues.referenceType_eq!=null}"> value="${currentSearchValues.referenceType_eq}"</c:if> >
              	<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              	<c:forEach var="temp" items="${referenceType}" varStatus="s">
				<option value="${temp.key}:${temp}">${temp.displayObject}</option>
				</c:forEach>
              	</select> 
              </td>
              </tr>
             <tr>
              <th><fmt:message key="BEAN_CE_INVOICE_GENERICINVOICEITEM_P_SEARCH_referenceNumber" bundle="${ClientStrings}"/></th>
              <td><input type="text"  dojoType="dijit.form.TextBox"  name="referenceNumber"  value="${currentSearchValues.referenceNumber_startsWith}" /></td>
            </tr>           
          </table>
          
          </div>
            <hr />
             <%@ include file="sort.jsp"%>
      </div> 
      <input type="submit" class="hideSubmitInput">
     </form>
     <div id="btarea">
        <div id="infobts">
      <!--   <button dojotype="dijit.form.Button">搜索</button>-->
      <span class="activebt">  <button dojotype="dijit.form.Button"><fmt:message key="CMD_disp_save" bundle="${ClientStrings}"/>
        	<script type="dojo/method" data-dojo-event="onClick" data-dojo-args="evt">
                        	Common.currentpage.save("customerserviceshipment");
            </script>
        </button></span>
      <span class="activebt"><button dojotype="dijit.form.Button"  onClick="Common.currentpage.deleteSearch('customerserviceshipment')"><fmt:message key="CMD_delete_routeGuide" bundle="${ClientStrings}"/></button></span>
       <span class="passivebt"> <button dojotype="dijit.form.Button" onClick="Nav.closePage()"><fmt:message key="CMD_delete_cancel" bundle="${ClientStrings}"/></button></span>
        </div>
     </div>
  </div>