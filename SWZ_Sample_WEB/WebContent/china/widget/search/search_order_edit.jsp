<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../../common/_tag.jspf"%>
<style type="text/css">
    @import "../../../javascript/dojo16/dojox/form/resources/CheckedMultiSelect.css";
</style>
  <div id="content2">
       <form id="searchContentParm" data-dojo-type="dijit.form.Form"
		data-dojo-props='encType:"multipart/form-data", onSubmit:function(e){Common.currentpage.save("order"); return false;}'>
       <div id="infoarea">
         <div class="save_as">
         <table>
              <tr>
                <th><fmt:message key="SEARCH_PROMPT_save_custom_search_as" bundle="${ClientStrings}"/></th>
                <td>
                	<input type="text" id="searchName" dojoType="dijit.form.ValidationTextBox" required="true" trim=true name="searchName" value="${currentSearchValues.searchName}" />
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
              <th><fmt:message key="BEAN_CE_BOLITEM_P_shipFrom" bundle="${ClientStrings}"/></th>
              <td >
			  	<input type="text" id="cs_origin2_text"data-dojo-type="dijit.form.ValidationTextBox" /> <%-- 设置初始化的值 --%> 
				<select
					data-dojo-props='name:"shipFrom",maxHeight:"150"' id="cs_origin2"
					data-dojo-type="dijit.form.FilteringSelect"  >
					
					<option value=""><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
					<c:if test="${not empty currentSearchValues.shipTo_eq}">
					<option value="${currentSearchValues.shipTo_eq}" selected="selected">${currentSearchValues.shipTo_eq_value}</option>
					</c:if>
					
				</select> 
				<span class="iconbt">
						<button id="cs_origin2_search" dojoType="dijit.form.Button"
							iconClass="searchIcon classIcon" showLabel="false" type="button"><fmt:message key="CMD_disp_save" bundle="${ClientStrings}"/></button>
				</span>
             </td>              
            </tr>
            <tr>			
              <th ><fmt:message key="BEAN_CE_BOLITEM_P_shipTo" bundle="${ClientStrings}"/></th>
              <td >
              	<input type="text" id="cs_destination2_text"data-dojo-type="dijit.form.ValidationTextBox" /> <%-- 设置初始化的值 --%> 
              	<select data-dojo-props='name:"shipTo",maxHeight:"150"'id="cs_destination2" data-dojo-type="dijit.form.FilteringSelect" >
					
					<option value=""><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
					<c:if test="${not empty currentSearchValues.destination_eq}">
					<option value="${currentSearchValues.shipFrom_eq}" selected="selected">${currentSearchValues.shipFrom_eq_value}</option>
					</c:if>
					
				</select> 
				<span class="iconbt">
					<button id="cs_destination2_search" dojoType="dijit.form.Button"
						iconClass="searchIcon classIcon" showLabel="false" type="button"><fmt:message key="CMD_disp_save" bundle="${ClientStrings}"/></button>
				</span>
			   </td>              
            </tr>
            <tr>
              <th class="topth"><fmt:message key="WEB_BASE_CE_BILLEDIT_HEADER_mode" bundle="${ClientStrings}" /></th>
               <td>
                 <select multiple="true" id = "mode" name="mode" dojoType="dojox.form.CheckedMultiSelect" >
				   <c:forEach var="temp" items="${mode}" varStatus="s">
				   		
						<option value="${temp.key}" 
							<c:forEach var="o" items="${currentSearchValues.mode_multi}" varStatus="s">
								<c:if test="${o eq temp.key}">selected="selected"></c:if>
							</c:forEach>>
							${temp.displayObject}
					  </option>
					</c:forEach>
				</select>
			  </td>
            </tr>
            <tr>
              <th><fmt:message key="WEB_BASE_CE_BILLEDIT_HEADER_movementType" bundle="${ClientStrings}" /></th>
              <td>
              			 <select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="movementType"  <c:if test="${currentSearchValues.movementType_eq!=null}">value="${currentSearchValues.movementType_eq}"</c:if>> 
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
              <th><fmt:message key="BEAN_CE_CONSOLIDATELW_P_equipmentList" bundle="${ClientStrings}" /></th>
                <td>
                	<select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="equipmentTypeSearch"  <c:if test="${currentSearchValues.equipmentTypeSearch_eq!=null}"> value="${currentSearchValues.equipmentTypeSearch_eq}" </c:if>>
		              		<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
		              		<c:forEach var="temp" items="${equipmentTypeSearch}" varStatus="s">
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
									</c:forEach>>${temp.displayObject}
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
	                <c:if test="${true eq currentSearchValues.hasNote_eq}">checked</c:if>/><label for="radioOne"> <fmt:message key="WEB_BASE_TOURS_SHAREDFLEETNAMEEDIT_CONSTLABEL_yes" bundle="${ClientStrings}"/></label>
	                <input  type="radio" dojoType="dijit.form.RadioButton" name="hasNote" value="0"
	                <c:if test="${false eq currentSearchValues.hasNote_eq}">checked</c:if>/><label for="radioOne"> <fmt:message key="WEB_BASE_TOURS_SHAREDFLEETNAMEEDIT_CONSTLABEL_no" bundle="${ClientStrings}"/></label>
	               	<input  type="radio"  dojoType="dijit.form.RadioButton" name="hasNote" value="2"
	               	 <c:if test="${null eq currentSearchValues.hasNote_eq}">checked</c:if>/><label for="radioOne"><fmt:message key="SEARCH_STR_either" bundle="${ClientStrings}"/> </label>
	               
               </td>
            </tr>
            <tr>
              <th><fmt:message key="WEB_BASE_AGENT_ACTIVATION_P_reference" bundle="${ClientStrings}"/></th>
             <td colspan="3"  class="radio_td"> 
                <input  type="radio" dojoType="dijit.form.RadioButton" name="hasAlert" value="1"  
                <c:if test="${true eq currentSearchValues.hasAlert_eq}">checked</c:if>/><label for="radioOne"><fmt:message key="WEB_BASE_TOURS_SHAREDFLEETNAMEEDIT_CONSTLABEL_yes" bundle="${ClientStrings}"/></label>
                <input  type="radio" dojoType="dijit.form.RadioButton" name="hasAlert" value="0"	
                <c:if test="${false eq currentSearchValues.hasAlert_eq}">checked</c:if>  
                <c:if test="${false eq currentSearchValues.hasAlert_eq}">checked</c:if>/><label for="radioOne"> <fmt:message key="WEB_BASE_TOURS_SHAREDFLEETNAMEEDIT_CONSTLABEL_no" bundle="${ClientStrings}"/></label>
               	<input  type="radio" dojoType="dijit.form.RadioButton"  name="hasAlert" value="2" 
               	<c:if test="${null eq currentSearchValues.hasAlert_eq}">checked</c:if> /><label for="radioOne"> <fmt:message key="SEARCH_STR_either" bundle="${ClientStrings}"/> </label>
             </td>
            </tr>
            <tr>
              <th><fmt:message key="WEBEAN_UI_COLUMNNAMES_PROPERTYLABEL_CUSTOMER" bundle="${ClientStrings}"/></th>
              <td >
              	   <select dojotype="dijit.form.FilteringSelect" autoComplete="true"  name="customer"  <c:if test="${currentSearchValues.customer_eq!=null}"> value="${currentSearchValues.customer_eq}"</c:if> id="s_customer" onChange="Org.customerdetail('search');">
              			<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              			<c:forEach var="temp" items="${customer}" varStatus="s">
						<option value="${temp.key}:${temp}">${temp.displayObject}</option>
						</c:forEach>
              	   </select>
	              	 <span class="iconbt dropiconbt">
			    	<div dojotype="dijit.form.DropDownButton" iconClass="detailIcon classIcon"  showLabel="false"  title='<fmt:message key="PROFILE_TITLE_org_detail" bundle="${ClientStrings}"/>'>
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
             	<select dojotype="dijit.form.FilteringSelect" autoComplete="true"  name="supplier" <c:if test="${currentSearchValues.supplier_eq!=null}"> value="${currentSearchValues.supplier_eq}"</c:if> id="s_supplier" onChange="Org.supplierdetail('search');">
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
              	<select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="referenceType" <c:if test="${currentSearchValues.referenceType_eq!=null}"> value="${currentSearchValues.referenceType_eq}"></c:if> >
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
              <th><fmt:message key="nb.createSearch.weight.greater" bundle="${NB}"/></th>
              <td  class="short">
              <input type="text" id="weightG" dojoType="dijit.form.NumberTextBox"  name="weightGreater" 
              value= "<c:choose> 
              			<c:when test="${currentSearchValues.weight_ge_value == null}">0.0</c:when>
              			<c:otherwise> ${currentSearchValues.weight_ge_value}</c:otherwise>
              		 </c:choose>"
              />
          
                 <select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="weightUnitG" 
                 <c:choose> 
              			<c:when test="${currentSearchValues.weight_ge_dim != null}">value="${currentSearchValues.weight_ge_dim}"</c:when>
              	  </c:choose>       
                 > 
              			<c:forEach var="temp" items="${weightType}" varStatus="s">
						<option value="${temp.key}">${temp.displayObject}</option>
						</c:forEach>
              	   </select>
              </td>
              
             
			 </tr>
             <tr>
                <th><fmt:message key="nb.createSearch.weight.less" bundle="${NB}"/></th>
              	<td  class="short"> <input type="text" id="weightL" dojoType="dijit.form.NumberTextBox"  name="weightLess" value="<c:choose> 
              			<c:when test="${currentSearchValues.weight_le_value == null}">0.0</c:when>
              			<c:otherwise> ${currentSearchValues.weight_le_value}</c:otherwise>
              		 </c:choose>"/>
           
              	   <select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="weightUnitL" 
              	   <c:choose> 
              			<c:when test="${currentSearchValues.weight_le_dim != null}">value="${currentSearchValues.weight_le_dim}"</c:when>
              	  </c:choose>    
              	   > 
              			<c:forEach var="temp" items="${weightType}" varStatus="s">
						<option value="${temp.key}">${temp.displayObject}</option>
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
								<c:forEach var="o" items="${currentSearchValues.paymentMethod_multi}" varStatus="s">
										<c:if test="${o eq temp.key}">selected="selected"></c:if>
								</c:forEach> >${temp.displayObject}
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
        <!--<button dojotype="dijit.form.Button">搜索</button>
        -->
        <span class="activebt"><button dojotype="dijit.form.Button" onClick="Common.currentpage.save('order')" ><fmt:message key="CMD_disp_save" bundle="${ClientStrings}"/></button></span>
       <span class="activebt"> <button dojotype="dijit.form.Button" onClick="Common.currentpage.deleteSearch('order')" ><fmt:message key="CMD_delete_routeGuide" bundle="${ClientStrings}"/></button></span>
       <span class="passivebt"> <button dojotype="dijit.form.Button" onClick="Nav.closePage()"><fmt:message key="CMD_delete_cancel" bundle="${ClientStrings}"/></button></span>
        </div>
     </div>
  </div>
