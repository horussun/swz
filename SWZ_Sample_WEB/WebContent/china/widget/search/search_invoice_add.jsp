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
                	<input type="text" id="searchName" dojoType="dijit.form.ValidationTextBox" required="true" trim=true name="searchName"/>
               		<input dojoType="dijit.form.CheckBox"  name="isDefault"/><label for="mycheck"><fmt:message key="SEARCH_CHECK_default_search" bundle="${ClientStrings}"/></label>
                </td>
              </tr>
         </table>
         </div>
            <hr />
 	 <div class="constraints">
          <label class="title"><fmt:message key="SEARCH_STR_search_constraints" bundle="${ClientStrings}"/></label>
          <table>
			<%@ include file="search_add_date_part.jsp" %>
            <tr>
              <th><fmt:message key="BEAN_LM_LMITEM_P_origin" bundle="${ClientStrings}"/></th>
              <td >
			  	<input type="text" id="cs_origin1_text" data-dojo-type="dijit.form.ValidationTextBox" /> <%-- 设置初始化的值 --%> 
				<select
					data-dojo-props='name:"origin",maxHeight:"150"' id="cs_origin1"
					data-dojo-type="dijit.form.FilteringSelect">
					<option value=""><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>

							<c:forEach var="o" items="${origin}" varStatus="s">
								<option value="${o.key}:${o}">${o}</option>
							</c:forEach>

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
              	<input type="text" id="cs_destination1_text"data-dojo-type="dijit.form.ValidationTextBox" /> <%-- 设置初始化的值 --%> 
              	<select data-dojo-props='name:"destination",maxHeight:"150"'id="cs_destination1" data-dojo-type="dijit.form.FilteringSelect">
					<option value=""><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>

							<c:forEach var="d" items="${destination}" varStatus="s">
								<option value="${d.key}:${d}">${d}</option>
							</c:forEach>

				</select> 
				<span class="iconbt">
					<button id="cs_destination1_search" dojoType="dijit.form.Button"
						iconClass="searchIcon classIcon" showLabel="false" type="button"><fmt:message key="CMD_disp_save" bundle="${ClientStrings}"/></button>
				</span>
			   </td>              
            </tr>
           <tr style="display:none">
              <th><fmt:message key="BEAN_CE_BILLLW_P_carrier"
			bundle="${ClientStrings}"></fmt:message></th>
              <td><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="carrier" id="s_carrier" onChange="Org.carrierdetail('search');"> 
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
              <td><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="payorOrganization"> 
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
	              	<c:forEach var="temp" items="${invoiceStatus}" varStatus="s">
						<option value="${temp.key}">${temp.displayObject}</option>
					</c:forEach>
              	</select>
              </td>
            </tr>
            
            <tr>
              <th><fmt:message key="WEB_BASE_FP_RECONCILECHARGES_UnapprovedChgItems" bundle="${ClientStrings}"/></th>
              <td colspan="3" class="radio_td"> 
                <input  type="radio" dojoType="dijit.form.RadioButton" name="unapprovedCharges" value="1"/><label for="radioOne">  <fmt:message key="WEB_BASE_TOURS_SHAREDFLEETNAMEEDIT_CONSTLABEL_yes" bundle="${ClientStrings}"/> </label>
                <input  type="radio" dojoType="dijit.form.RadioButton" name="unapprovedCharges" value="0"/><label for="radioOne"> <fmt:message key="WEB_BASE_TOURS_SHAREDFLEETNAMEEDIT_CONSTLABEL_no" bundle="${ClientStrings}"/></label>
                <input  type="radio"  dojoType="dijit.form.RadioButton" name="unapprovedCharges" value="2" checked="true"/><label for="radioOne"><fmt:message key="SEARCH_STR_either" bundle="${ClientStrings}"/></label></td>
            </tr>
            <tr>
            	<th><fmt:message key="BEAN_CE_INVOICESUMMARYLW_P_carrierInvoiceNumber" bundle="${ClientStrings}"/> </th>
            	<td>
            		<input type="text" dojoType="dijit.form.ValidationTextBox" name="carrierInvoiceNumber"/>
            	</td>
            </tr>
            <tr>
              <th><fmt:message key="BEAN_TOURS_TOURCYCLECRITERIALW_P_referenceType" bundle="${ClientStrings}"/></th>
              <td>
              	<select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="referenceType">
              	<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              	<c:forEach var="temp" items="${referenceType}" varStatus="s">
				<option value="${temp.key}:${temp}">${temp.displayObject}</option>
				</c:forEach>
              	</select> 
              </td>
            </tr>
            
            <tr>
              <th><fmt:message key="BEAN_CE_INVOICE_GENERICINVOICEITEM_P_SEARCH_referenceNumber" bundle="${ClientStrings}"/></th>
              <td><input type="text"  dojoType="dijit.form.TextBox"  name="referenceNumber"/></td>
            </tr>
            <tr>
              <th class="topth"><fmt:message key="BEAN_CE_INVOICE_GENERICINVOICEITEM_P_transportationMode" bundle="${ClientStrings}"/></th>
              <td>
                 <select multiple="true" name="transportationMode" dojoType="dojox.form.CheckedMultiSelect">
				   <c:forEach var="temp" items="${transportationMode}" varStatus="s">
						<option value="${temp.key}">${temp.displayObject}</option>
					</c:forEach>
				</select>
			  </td>
            </tr>
            <tr>
              <th><fmt:message key="WEB_BASE_CE_BILLEDIT_HEADER_movementType" bundle="${ClientStrings}" /></th>
              <td><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="movementType"> 
              		<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              		<c:forEach var="temp" items="${movementType}" varStatus="s">
						<option value="${temp.key}:${temp}">${temp.displayObject}</option>
					</c:forEach>
              	  </select>
              </td>
            </tr> 
            <tr>
              <th><fmt:message key="WEB_BASE_CE_INVOICEPRINT_LABEL_chargeAndAllowanceCode" bundle="${ClientStrings}"/></th>
              <td><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="chargeAllowanceCode"> 
              		<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
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
								<option value="${temp.key}">${temp.displayObject}</option>
						 </c:forEach>
		              </select>
              	</td>
            </tr>
            <tr>
            	<th><fmt:message key="BEAN_CE_INVOICE_INVOICEITEM_P_carrierPurchaseOrderNumber" bundle="${ClientStrings}"/></th>
            	<td>
            		<input type="text"  dojoType="dijit.form.ValidationTextBox"  name="carrierPurchaseOrderNumber"/>
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
      <span class="activebt">  <button dojotype="dijit.form.Button"><fmt:message key="CMD_disp_save" bundle="${ClientStrings}"/>
        	<script type="dojo/method" data-dojo-event="onClick" data-dojo-args="evt">
                        	Common.currentpage.save("invoice");
            </script>
        </button>
        </span>
        <!--
        <button dojotype="dijit.form.Button">复制</button>
        -->
        <span class="passivebt"><button dojotype="dijit.form.Button"  onClick="Nav.closePage()"><fmt:message key="CMD_delete_cancel" bundle="${ClientStrings}"/></button></span>
        </div>
     </div>
  </div>