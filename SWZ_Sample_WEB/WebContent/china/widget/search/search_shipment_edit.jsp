<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../../common/_tag.jspf"%>
<style type="text/css">
    @import "../../../javascript/dojo16/dojox/form/resources/CheckedMultiSelect.css";
</style>
  <div id="content2">
       <form id="searchContentParm" data-dojo-type="dijit.form.Form"
		data-dojo-props='encType:"multipart/form-data", onSubmit:function(e){Common.currentpage.save("shipment"); return false;}'>
       <div id="infoarea">
         <div class="save_as">
         <table>
              <tr>
                <th><fmt:message key="SEARCH_PROMPT_save_custom_search_as" bundle="${ClientStrings}"/></th>
                <td>
                	<input type="text" id="searchName" dojoType="dijit.form.ValidationTextBox" required="true" name="searchName" trim=true value="${currentSearchValues.searchName}" />
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
           <!--   <tr>
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
            </tr>-->
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
		<th><fmt:message key="BEAN_CE_BILLLW_P_carrier"
			bundle="${ClientStrings}"></fmt:message></th>
		<td><select id="s_carrier" data-dojo-type="dijit.form.Select"
			data-dojo-args="evt"
			data-dojo-props='name:"carrier", maxHeight:"150",<c:if test="${currentSearchValues.carrier_eq!=null}"> value:"${currentSearchValues.carrier_eq}"</c:if>' >
				<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
				<c:forEach var="c" items="${carrier}" varStatus="s">
					<option value="${c.key}:${c}"
						<c:if test="${c.key eq search.carrier.key}">selected</c:if>>${c}</option>
				</c:forEach>
		    </select>
		<span class="iconbt dropiconbt">
				<div dojotype="dijit.form.DropDownButton" iconClass="detailIcon classIcon"  showLabel="false"  title="<fmt:message key="PROFILE_TITLE_org_detail" bundle="${ClientStrings}"/>">
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
		<td><input name="scac_shipment"  maxHeight="150" id="s_scac" dojotype="dijit.form.ValidationTextBox" value="${currentSearchValues.scac_subString}">	
		</td>
		</tr>
            <tr>
              <th class="topth"><fmt:message key="WEB_BASE_CE_BILLEDIT_HEADER_mode" bundle="${ClientStrings}"/> </th>
              <td>
                 <select multiple="true" name="mode" dojoType="dojox.form.CheckedMultiSelect">
				   <c:forEach var="temp" items="${mode}">
						<option value="${temp.key}"
								<c:forEach var="o" items="${currentSearchValues.status_multi}" varStatus="s">
										<c:if test="${o eq temp.key}">selected="selected"</c:if>
								</c:forEach>
						>${temp.displayObject}</option>
					</c:forEach>
				</select>
			  </td>
            </tr>
           
              
        	<tr>
              <th><fmt:message key="WEB_BASE_CE_BILLEDIT_HEADER_movementType" bundle="${ClientStrings}" /></th>
              <td>
              			 <select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="movementType" <c:if test="${currentSearchValues.movementType_eq!=null}">value="${currentSearchValues.movementType_eq}"</c:if> > 
		              		<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
		              		<c:forEach var="temp" items="${movementType}" >
								<option value="${temp.key}:${temp}">
									${temp.displayObject}
								</option>
							</c:forEach>
		              	  </select>
		     </td>
			</tr>
            <tr>
              <th><fmt:message key="WEB_BASE_CE_INVOICEPRINT_LABEL_equipment" bundle="${ClientStrings}"/></th>
                <td>
                	<select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="equipment"  <c:if test="${currentSearchValues.equipment_eq!=null}">value ="${currentSearchValues.equipment_eq}" </c:if> >
		              		<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
		              		<c:forEach var="temp" items="${equipmentTypeSearch}">
								<option value="${temp.key}:${temp}">
									${temp.displayObject}
							    </option>
							</c:forEach>
		           </select>
		      </td>
            </tr> 
      		<tr>
              <th><fmt:message key="WEB_BASE_CE_BILLEDIT_HEADER_description" bundle="${ClientStrings}"/></th>
              <td colspan="3" > <textarea class="info_textarea" dojoType="dijit.form.Textarea" name="itemDescription" value="${currentSearchValues.itemDescription_subString}"></textarea></td>
            </tr>
              
            <tr>
              <th class="topth"><fmt:message key="WEBEAN_UI_COLUMNNAMES_PROPERTYLABEL_PLAN_STATUS" bundle="${ClientStrings}"/></th>
              <td >
	              <select multiple="true" name="planStatus" dojoType="dojox.form.CheckedMultiSelect"> 
	             	 <c:forEach var="temp" items="${planStatus}" varStatus="s">
							<option value="${temp.key}" 
									<c:forEach var="o" items="${currentSearchValues.planStatus_multi}" varStatus="s">
										<c:if test="${o eq temp.key}">selected="selected"></c:if>
									</c:forEach> >${temp.displayObject}
							</option>
					</c:forEach>
	              </select>
              </td>
			  </tr>
               <tr>
              <th class="topth"><fmt:message key="WEBEAN_UI_COLUMNNAMES_PROPERTYLABEL_SHIPMENT_STATUS" bundle="${ClientStrings}"/></th>
               <td >
	              <select multiple="true" name="status" dojoType="dojox.form.CheckedMultiSelect">
	              	 <c:forEach var="temp" items="${status}" varStatus="s">
							<option value="${temp.key}"
								<c:forEach var="o" items="${currentSearchValues.status_multi}" >
										<c:if test="${o eq temp.key}">selected="selected"></c:if>
								</c:forEach>>${temp.displayObject}
							</option>
					 </c:forEach>
	              </select>
              </td>
            </tr>
            <tr>
              <th><fmt:message key="WEB_BASE_CE_BOLEDIT_HEADER_note" bundle="${ClientStrings}"/></th>
               <td colspan="3" class="radio_td"> 
	                <input  type="radio" dojoType="dijit.form.RadioButton" name="hasNote" value="1" 
	                <c:if test="${true eq currentSearchValues.hasNote_eq}">checked</c:if>/><label for="radioOne"> <fmt:message key="WEB_BASE_TOURS_SHAREDFLEETNAMEEDIT_CONSTLABEL_yes" bundle="${ClientStrings}"/> </label>
	                <input  type="radio" dojoType="dijit.form.RadioButton" name="hasNote" value="0"
	                <c:if test="${false eq currentSearchValues.hasNote_eq}">checked</c:if>/><label for="radioOne">  <fmt:message key="WEB_BASE_TOURS_SHAREDFLEETNAMEEDIT_CONSTLABEL_no" bundle="${ClientStrings}"/></label>
	               	<input  type="radio"  dojoType="dijit.form.RadioButton" name="hasNote" value="2"
	               	<c:if test="${null eq currentSearchValues.hasNote_eq}">checked</c:if>/><label for="radioOne"> <fmt:message key="SEARCH_STR_either" bundle="${ClientStrings}"/></label>
	               
               </td>
            </tr>
           <tr>
              <th><fmt:message key="WEB_BASE_AGENT_ACTIVATION_P_reference" bundle="${ClientStrings}"/></th>
             <td colspan="3"  class="radio_td"> 
                <input  type="radio" dojoType="dijit.form.RadioButton" name="alert" value="1"  
                <c:if test="${true eq currentSearchValues.alert_eq}">checked</c:if>/><label for="radioOne"> <fmt:message key="WEB_BASE_TOURS_SHAREDFLEETNAMEEDIT_CONSTLABEL_yes" bundle="${ClientStrings}"/> </label>
                <input  type="radio" dojoType="dijit.form.RadioButton" name="alert" value="0"	
                <c:if test="${false eq currentSearchValues.alert_eq}">checked</c:if>  /><label for="radioOne"> <fmt:message key="WEB_BASE_TOURS_SHAREDFLEETNAMEEDIT_CONSTLABEL_no" bundle="${ClientStrings}"/></label>
               	<input  type="radio" dojoType="dijit.form.RadioButton"  name="alert" value="2"
               	<c:if test="${null eq currentSearchValues.alert_eq}">checked</c:if> /><label for="radioOne"> <fmt:message key="SEARCH_STR_either" bundle="${ClientStrings}"/></label>
             </td>
            </tr>
            <tr>
              <th><fmt:message key="WEBEAN_UI_COLUMNNAMES_PROPERTYLABEL_CUSTOMER" bundle="${ClientStrings}"/></th>
              <td >
              	   <select dojotype="dijit.form.FilteringSelect" autoComplete="true"  name="customer" <c:if test="${currentSearchValues.customer_eq!=null}">value="${currentSearchValues.customer_eq}"</c:if> id="s_customer" onChange="Org.customerdetail('search');">
              			<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              			<c:forEach var="temp" items="${customer}" varStatus="s">
						<option value="${temp.key}:${temp}">${temp.displayObject}</option>
						</c:forEach>
              	   </select>
	              	 <span class="iconbt dropiconbt">
			    	<div dojotype="dijit.form.DropDownButton" iconClass="detailIcon classIcon"  showLabel="false"  title="<fmt:message key="PROFILE_TITLE_org_detail" bundle="${ClientStrings}"/>">
					<div id="customerdialog" data-dojo-type="dijit.TooltipDialog">
						<span><fmt:message key="nb.bill.editbill.choose_one" bundle="${NB}"></fmt:message></span>
					</div>
				</div>
			</span>
          	 </td> 
           </tr>
          <tr>		   
              <th><fmt:message key="CE_CUSTOMERSERVICESHIPMENTDETAILTABS_LABEL_suppliers" bundle="${ClientStrings}"/></th>
             <td >
             	<select dojotype="dijit.form.FilteringSelect" autoComplete="true"  name="supplier" <c:if test="${currentSearchValues.supplier_eq!=null}">value="${currentSearchValues.supplier_eq}"</c:if>  id="s_supplier" onChange="Org.supplierdetail('search');">
            		<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              		<c:forEach var="temp" items="${supplier}" varStatus="s">
					<option value="${temp.key}:${temp}">${temp.displayObject}</option>
					</c:forEach>
            	 </select> 
           		<span class="iconbt dropiconbt">
			    	<div dojotype="dijit.form.DropDownButton" iconClass="detailIcon classIcon"  showLabel="false" title="<fmt:message key="PROFILE_TITLE_org_detail" bundle="${ClientStrings}"/>">
					<div id="supplierdialog" data-dojo-type="dijit.TooltipDialog">
						<span><fmt:message key="nb.bill.editbill.choose_one" bundle="${NB}"></fmt:message></span>
					</div>
				</div>
			</span>
            </td>                    
            </tr>
           <tr>
              <th><fmt:message key="BEAN_TOURS_TOURCYCLECRITERIALW_P_referenceType" bundle="${ClientStrings}"/></th>
              <td>
              	<select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="referenceType" <c:if test="${currentSearchValues.referenceType_eq!=null}">value="${currentSearchValues.referenceType_eq}"</c:if> >
              	<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              	<c:forEach var="temp" items="${referenceType}" varStatus="s">
					<option value="${temp.key}:${temp}">
						${temp.displayObject}
					</option>
				</c:forEach>
              	</select> 
              </td>
			</tr>
            <tr>
              <th><fmt:message key="WEBEAN_UI_COLUMNNAMES_PROPERTYLABEL_REFERENCE" bundle="${ClientStrings}"/></th>
 				<td><input type="text"  dojoType="dijit.form.TextBox"  name="referenceNumber" value="${currentSearchValues.referenceNumber_startsWith}"/></td>
            </tr>
            <tr>
            <th><fmt:message key="WEBEAN_UI_COLUMNNAMES_PROPERTYLABEL_LOAD_PLAN_ID" bundle="${ClientStrings}"/></th>
            <td><input type="text"  dojoType="dijit.form.TextBox"  name="loadPlanId" value="${currentSearchValues.loadPlanId_eq}"/></td>
            </tr>
             <tr>
              <th><fmt:message key="BEAN_LM_LMITEM_P_loadPlanStatus" bundle="${ClientStrings}"/></th>
              <td>
              	<select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="loadPlanStatus"  <c:if test="${currentSearchValues.loadPlanStatus_eq!=null}">value="${currentSearchValues.loadPlanStatus_eq}"</c:if> >
              		<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
	              	<c:forEach var="temp" items="${loadPlanStatus}" varStatus="s">
					  <option value="${temp.key}:${temp}">${temp.displayObject}</option>
					</c:forEach>
              	</select> 
              </td>
            </tr>
            <tr>
               <th><fmt:message key="WEB_BASE_CE_INVOICEEDITSEND_voucherStatus" bundle="${ClientStrings}"/></th>
              <td>
              	<select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="invoiceStatus"  <c:if test="${currentSearchValues.invoiceStatus_eq!=null}">value="${currentSearchValues.invoiceStatus_eq}"</c:if> >
              	
              		<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
	              	<c:forEach var="temp" items="${invoiceStatus}" varStatus="s">
					<option value="${temp.key}:${temp}">${temp.displayObject}</option>
					</c:forEach>
              	</select>
              </td>
            </tr>
              <tr>
                <th class="topth"><fmt:message key="BEAN_CM_CONTRACT_RATE_ROUTEGUIDERULELW_P_distributionType" bundle="${ClientStrings}"/></th>
                 <td >
		              <select multiple="true" name="publishType" dojoType="dojox.form.CheckedMultiSelect">
		              	 <c:forEach var="temp" items="${publishType}" varStatus="s">
								<option value="${temp.key}"
								<c:forEach var="o" items="${currentSearchValues.publishType_multi}" >
										<c:if test="${o eq temp.key}">selected="selected"</c:if>
								</c:forEach> >${temp.displayObject}</option>
						 </c:forEach>
		              </select>
              	</td>
            </tr>
                  <tr>
                <th class="topth"><fmt:message key="BEAN_PROFILE_USERPREFERENCESLW_P_paymentMethod" bundle="${ClientStrings}"/></th>
               <td >
		              <select multiple="true" name="paymentMethod" dojoType="dojox.form.CheckedMultiSelect">
		              	 <c:forEach var="temp" items="${paymentMethod}" varStatus="s">
								<option value="${temp.key}" 
								<c:forEach var="o" items="${currentSearchValues.paymentMethod_multi}">
										<c:if test="${o eq temp.key}">selected="selected"></c:if>
								</c:forEach>>${temp.displayObject}
								</option>
						 </c:forEach>
		              </select>
              	</td>
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
        <!-- <button dojotype="dijit.form.Button">搜索</button> -->
      <span class="activebt">   <button dojotype="dijit.form.Button"><fmt:message key="CMD_disp_save" bundle="${ClientStrings}"/>
        	<script type="dojo/method" data-dojo-event="onClick" data-dojo-args="evt">
                        	Common.currentpage.save("shipment");
            </script>
        </button></span>
        <span class="activebt"> <button dojotype="dijit.form.Button"  onClick="Common.currentpage.deleteSearch('shipment')"><fmt:message key="CMD_delete_routeGuide" bundle="${ClientStrings}"/></button></span>
       <span class="passivebt"> <button dojotype="dijit.form.Button"  onClick="Nav.closePage()"><fmt:message key="CMD_delete_cancel" bundle="${ClientStrings}"/></button></span>
        </div>
     </div>
  </div>