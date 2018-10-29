<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../../common/_tag.jspf"%>
<style type="text/css">
    @import "../../../javascript/dojo16/dojox/form/resources/CheckedMultiSelect.css";
</style>
  <div id="content2">
       <form id="searchContentParm" data-dojo-type="dijit.form.Form"
		data-dojo-props='encType:"multipart/form-data", onSubmit:function(e){Common.currentpage.save("invoice"); return false;}'>
       <div id="infoarea">
         <div class="save_as">
         <table>
              <tr>
                <th><fmt:message key="SEARCH_PROMPT_save_custom_search_as" bundle="${ClientStrings}"/></th>
                <td>
                	<input type="text" id="searchName" dojoType="dijit.form.ValidationTextBox" required="true" name="searchName"  trim=true value="${currentSearchValues.searchName}" />
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
							iconClass="searchIcon classIcon" showLabel="false" type="button">保存</button>
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
           <tr style="display:none">
              <th><fmt:message key="BEAN_CE_BILLLW_P_carrier"
			bundle="${ClientStrings}"></fmt:message> </th>
              <td><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="carrier" <c:if test="${currentSearchValues.carrier_eq!=null}"> value="${currentSearchValues.carrier_eq}"</c:if> id="s_carrier" onChange="Org.carrierdetail('search');"> 
              		<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              		<c:forEach var="temp" items="${payorOrganization}" varStatus="s">
						<option value="${temp.key}:${temp}">${temp}</option>
					</c:forEach>
              	  </select>
	               <span class="iconbt dropiconbt">
			    	<div dojotype="dijit.form.DropDownButton" iconClass="detailIcon classIcon"  showLabel="false"  title="组织详细信息">
					<div id="carrierdialog" data-dojo-type="dijit.TooltipDialog">
						<span><fmt:message key="nb.bill.editbill.choose_one" bundle="${NB}"></fmt:message></span>
					</div>
				</div>
			    </span>
              </td>
             </tr>
            
            <tr >
              <th><fmt:message key="BEAN_CM_CONTRACT_CONTRACTLW_P_shipper" bundle="${ClientStrings}"/></th>
              <td><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="payorOrganization" <c:if test="${currentSearchValues.payorOrganization_eq!=null}">value="${currentSearchValues.payorOrganization_eq}"</c:if> > 
              		<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              		<c:forEach var="temp" items="${payorOrganization}" varStatus="s">
						<option value="${temp.key}:${temp}">${temp}</option>
					</c:forEach>
              	  </select>
            </tr>
            <tr>
             <th><fmt:message key="WEB_BASE_CE_INVOICEEDITSEND_voucherStatus" bundle="${ClientStrings}"/></th>
              <td>
              	<select multiple="true" dojotype="dojox.form.CheckedMultiSelect" autoComplete="true" name="invoiceStatus" >
	              	<c:forEach var="temp" items="${invoiceStatus}" >
						<option value="${temp.key}"
						       <c:forEach var="o" items="${currentSearchValues.invoiceStatus_multi}">
										<c:if test="${o eq temp.key}">selected="selected"</c:if>
								</c:forEach> >${temp.displayObject}
						</option>
					</c:forEach>
              	</select>
              </td>
            </tr>
            
            <tr>
              <th><fmt:message key="WEB_BASE_FP_RECONCILECHARGES_UnapprovedChgItems" bundle="${ClientStrings}"/></th>
              <td colspan="3" class="radio_td"> 
               
                <input  type="radio" dojoType="dijit.form.RadioButton" name="unapprovedCharges" value="1"  
                <c:if test="${true eq currentSearchValues.unapprovedCharges_eq}">checked</c:if>/><label for="radioOne"> <fmt:message key="WEB_BASE_TOURS_SHAREDFLEETNAMEEDIT_CONSTLABEL_yes" bundle="${ClientStrings}"/> </label>
                <input  type="radio" dojoType="dijit.form.RadioButton" name="unapprovedCharges" value="0"	
                <c:if test="${false eq currentSearchValues.unapprovedCharges_eq}">checked</c:if>  /><label for="radioOne"> <fmt:message key="WEB_BASE_TOURS_SHAREDFLEETNAMEEDIT_CONSTLABEL_no" bundle="${ClientStrings}"/></label>
               	<input  type="radio" dojoType="dijit.form.RadioButton"  name="unapprovedCharges" value="2"	
               	<c:if test="${null eq currentSearchValues.unapprovedCharges_eq}">checked</c:if> /><label for="radioOne"><fmt:message key="SEARCH_STR_either" bundle="${ClientStrings}"/> </label>
             </td>
            </tr>
            <tr>
            	<th><fmt:message key="BEAN_CE_INVOICESUMMARYLW_P_carrierInvoiceNumber" bundle="${ClientStrings}"/>  </th>
            	<td>
            		<input type="text" dojoType="dijit.form.ValidationTextBox" name="carrierInvoiceNumber" value="${currentSearchValues.carrierInvoiceNumber_eq}" />
            	</td>
            </tr>
            <tr>
              <th><fmt:message key="BEAN_TOURS_TOURCYCLECRITERIALW_P_referenceType" bundle="${ClientStrings}"/></th>
              <td>
              	<select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="referenceType" <c:if test="${currentSearchValues.referenceType_eq!=null}"> value="${currentSearchValues.referenceType_eq}"> </c:if> >
              	<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              	<c:forEach var="temp" items="${referenceType}" varStatus="s">
				<option value="${temp.key}:${temp}">${temp.displayObject}</option>
				</c:forEach>
              	</select> 
              </td>
            </tr>
            
            <tr>
              <th><fmt:message key="BEAN_CE_INVOICE_GENERICINVOICEITEM_P_SEARCH_referenceNumber" bundle="${ClientStrings}"/></th>
              <td><input type="text"  dojoType="dijit.form.TextBox"  name="referenceNumber" value="${currentSearchValues.referenceNumber_startsWith}" /></td>
            </tr>
            <tr>
              <th class="topth"><fmt:message key="BEAN_CE_INVOICE_GENERICINVOICEITEM_P_transportationMode" bundle="${ClientStrings}"/></th>
              <td>
                 <select multiple="true" name="transportationMode" dojoType="dojox.form.CheckedMultiSelect"  >
				   <c:forEach var="temp" items="${transportationMode}" varStatus="s">
						<option value="${temp.key}"
								<c:forEach var="o" items="${currentSearchValues.transportationMode_multi}" >
										<c:if test="${o eq temp.key}">selected="selected"</c:if>
								</c:forEach> >${temp.displayObject}
						</option>
					</c:forEach>
				</select>
			  </td>
            </tr>
            <tr>
              <th><fmt:message key="WEB_BASE_CE_BILLEDIT_HEADER_movementType" bundle="${ClientStrings}" /> </th>
              <td><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="movementType" <c:if test="${currentSearchValues.movementType_eq!=null}"> value="${currentSearchValues.movementType_eq}" </c:if> > 
              		<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              		<c:forEach var="temp" items="${movementType}" varStatus="s">
						<option value="${temp.key}:${temp}">${temp.displayObject}</option>
					</c:forEach>
              	  </select>
              </td>
            </tr> 
            <tr>
              <th><fmt:message key="WEB_BASE_CE_INVOICEPRINT_LABEL_chargeAndAllowanceCode" bundle="${ClientStrings}"/></th>
              <td><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="chargeAllowanceCode" value="${currentSearchValues.chargeAllowanceCode_eq}"> 
              		<option value="0"><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              		<c:forEach var="temp" items="${chargeAllowanceCode}" varStatus="s">
						<option value="${temp.key}:${temp}">${temp.displayObject}</option>
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
								 <c:forEach var="o" items="${currentSearchValues.paymentMethod_multi}" >
										<c:if test="${o eq temp.key}">selected="selected"</c:if>
								</c:forEach>>${temp.displayObject}
						    </option>
						 </c:forEach>
		              </select>
              	</td>
            </tr>
            <tr>
            	<th><fmt:message key="BEAN_CE_INVOICE_INVOICEITEM_P_carrierPurchaseOrderNumber" bundle="${ClientStrings}"/></th>
            	<td>
            		<input type="text"  dojoType="dijit.form.ValidationTextBox"  name="carrierPurchaseOrderNumber" value="${currentSearchValues.carrierPurchaseOrderNumber_eq}"/>
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
      <!--    <button dojotype="dijit.form.Button">搜索</button>-->
        <span class="activebt"><button dojotype="dijit.form.Button"><fmt:message key="CMD_disp_save" bundle="${ClientStrings}"/>
        	<script type="dojo/method" data-dojo-event="onClick" data-dojo-args="evt">
                        	Common.currentpage.save("invoice");
            </script>
        </button></span>
        <span class="activebt"><button dojotype="dijit.form.Button"  onClick="Common.currentpage.deleteSearch('invoice')"><fmt:message key="CMD_delete_routeGuide" bundle="${ClientStrings}"/></button></span>
        <span class="passivebt"><button dojotype="dijit.form.Button"  onClick="Nav.closePage()"><fmt:message key="CMD_delete_cancel" bundle="${ClientStrings}"/></button></span>
        </div>
     </div>
  </div>