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
						<option value="${o.key}:${o}"
							<c:if test="${o.key eq order.origin.key}">selected</c:if>>${o}</option>
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
						<option value="${d.key}:${d}"
							<c:if test="${d.key eq order.destination.key}">selected</c:if>>${d}</option>
					</c:forEach>
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
					data-dojo-type="dijit.form.FilteringSelect">
					<option value=""><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>

							<c:forEach var="o" items="${origin}" varStatus="s">
								<option value="${o.key}:${o}">${o}</option>
							</c:forEach>

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
              	<select data-dojo-props='name:"shipTo",maxHeight:"150"'id="cs_destination2" data-dojo-type="dijit.form.FilteringSelect">
					<option value=""><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>

							<c:forEach var="d" items="${destination}" varStatus="s">
								<option value="${d.key}:${d}">${d}</option>
							</c:forEach>

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
                 <select multiple="true" name="mode" dojoType="dojox.form.CheckedMultiSelect">
				   <c:forEach var="temp" items="${mode}" varStatus="s">
						<option value="${temp.key}">${temp.displayObject}</option>
					</c:forEach>
				</select>
			  </td>
            </tr>
            <tr>
              <th><fmt:message key="WEB_BASE_CE_BILLEDIT_HEADER_movementType" bundle="${ClientStrings}" /></th>
              <td>
              				<select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="movementType"> 
		              		<option  value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
		              		<c:forEach var="temp" items="${movementType}" varStatus="s">
								<option value="${temp.key}:${temp}">${temp.displayObject}</option>
							</c:forEach>
		              	  </select>
		     </td>
			</tr>
            <tr>
              <th><fmt:message key="BEAN_CE_CONSOLIDATELW_P_equipmentList" bundle="${ClientStrings}" /></th>
                <td>
                	<select dojotype="dijit.form.FilteringSelect" autoComplete="true"name="equipmentTypeSearch">
		              		<option  value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
		              		<c:forEach var="temp" items="${equipmentTypeSearch}" varStatus="s">
								<option value="${temp.key}:${temp}">${temp.displayObject}</option>
							</c:forEach>
		           </select>
		      </td>
            </tr> 
            <tr>
              <th><fmt:message key="WEB_BASE_CE_BILLEDIT_HEADER_description" bundle="${ClientStrings}"/></th>
              <td colspan="3" > <textarea class="info_textarea" dojoType="dijit.form.Textarea" name="itemDescription"></textarea></td>
            </tr>
            <tr>
              <th class="topth"><fmt:message key="WEBEAN_UI_COLUMNNAMES_PROPERTYLABEL_PLAN_STATUS" bundle="${ClientStrings}"/></th>
              <td >
	              <select multiple="true" name="planStatus" dojoType="dojox.form.CheckedMultiSelect"> 
	             	 <c:forEach var="temp" items="${planStatus}" varStatus="s">
							<option value="${temp.key}">${temp.displayObject}</option>
					</c:forEach>
	              </select>
              </td>
			  </tr>
              <tr>
              <th class="topth"><fmt:message key="WEBEAN_UI_COLUMNNAMES_PROPERTYLABEL_SHIPMENT_STATUS" bundle="${ClientStrings}"/></th>
               <td >
	              <select multiple="true" name="status" dojoType="dojox.form.CheckedMultiSelect">
	              	 <c:forEach var="temp" items="${status}" varStatus="s">
							<option value="${temp.key}">${temp.displayObject}</option>
					 </c:forEach>
	              </select>
              </td>
            </tr>
            <tr>
              <th><fmt:message key="WEB_BASE_CE_BOLEDIT_HEADER_note" bundle="${ClientStrings}"/></th>
               <td colspan="3" class="radio_td"> 
	                <input  type="radio" dojoType="dijit.form.RadioButton" name="hasNote" value="1"/><label for="radioOne">  <fmt:message key="WEB_BASE_TOURS_SHAREDFLEETNAMEEDIT_CONSTLABEL_yes" bundle="${ClientStrings}"/> </label>
	                <input  type="radio" dojoType="dijit.form.RadioButton" name="hasNote" value="0"/><label for="radioOne"> <fmt:message key="WEB_BASE_TOURS_SHAREDFLEETNAMEEDIT_CONSTLABEL_no" bundle="${ClientStrings}"/></label>
	               	<input  type="radio"  dojoType="dijit.form.RadioButton" name="hasNote" value="2" checked="true"/><label for="radioOne"> <fmt:message key="SEARCH_STR_either" bundle="${ClientStrings}"/></label>
               </td>
            </tr>
            <tr>
              <th><fmt:message key="WEB_BASE_AGENT_ACTIVATION_P_reference" bundle="${ClientStrings}"/></th>
             <td colspan="3"  class="radio_td"> 
                <input  type="radio" dojoType="dijit.form.RadioButton" name="hasAlert" value="1"/><label for="radioOne"> <fmt:message key="WEB_BASE_TOURS_SHAREDFLEETNAMEEDIT_CONSTLABEL_yes" bundle="${ClientStrings}"/> </label>
                <input  type="radio" dojoType="dijit.form.RadioButton" name="hasAlert" value="0"/><label for="radioOne"> <fmt:message key="WEB_BASE_TOURS_SHAREDFLEETNAMEEDIT_CONSTLABEL_no" bundle="${ClientStrings}"/></label>
               	<input  type="radio" dojoType="dijit.form.RadioButton"  name="hasAlert" value="2" checked="true"/><label for="radioOne"><fmt:message key="SEARCH_STR_either" bundle="${ClientStrings}"/></label>
             </td>
            </tr>
            <tr>
              <th><fmt:message key="WEBEAN_UI_COLUMNNAMES_PROPERTYLABEL_CUSTOMER" bundle="${ClientStrings}"/></th>
              <td >
              	   <select dojotype="dijit.form.FilteringSelect" autoComplete="true"  name="customer" id="s_customer" onChange="Org.customerdetail('search');">
              			<option  value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
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
             	<select dojotype="dijit.form.FilteringSelect" autoComplete="true"  name="supplier" id="s_supplier" onChange="Org.supplierdetail('search');">
            		<option  value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
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
              	<select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="referenceType">
              	<option value><fmt:message key="SEARCH_STR_all" bundle="${ClientStrings}"/></option>
              	<c:forEach var="temp" items="${referenceType}" varStatus="s">
				<option value="${temp.key}:${temp}">${temp.displayObject}</option>
				</c:forEach>
              	</select> 
              </td>
			</tr>
            <tr>
              <th><fmt:message key="WEBEAN_UI_COLUMNNAMES_PROPERTYLABEL_REFERENCE" bundle="${ClientStrings}"/></th>
 				<td><input type="text"  dojoType="dijit.form.TextBox"  name="referenceNumber"/></td>
            </tr>
            <tr>
              <th><fmt:message key="nb.createSearch.weight.greater" bundle="${NB}"/></th>
              <td class="short"><input type="text" id="weightG" dojoType="dijit.form.NumberTextBox"  name="weightGreater" value="0.0"/>
             
                 <select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="weightUnitG"> 
              			<c:forEach var="temp" items="${weightType}" varStatus="s">
						<option value="${temp.key}">${temp.displayObject}</option>
						</c:forEach>
              	   </select>
              </td>
              
             
			 </tr>
             <tr>
                <th><fmt:message key="nb.createSearch.weight.less" bundle="${NB}"/></th>
              	<td class="short"><input type="text" id="weightL" dojoType="dijit.form.NumberTextBox"  name="weightLess" value="0.0"/>
            
              	   <select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="weightUnitL"> 
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
								<option value="${temp.key}">${temp.displayObject}</option>
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
       <!--  <button dojotype="dijit.form.Button">搜索</button> -->
        <span class="activebt"><button dojotype="dijit.form.Button" onClick="Common.currentpage.save('order')" ><fmt:message key="CMD_disp_save" bundle="${ClientStrings}"/></button></span>
        <!--<button dojotype="dijit.form.Button">复制</button>
        -->
        <span class="passivebt"><button dojotype="dijit.form.Button" onClick="Nav.closePage()"><fmt:message key="CMD_delete_cancel" bundle="${ClientStrings}"/></button></span>
        </div>
     </div>
  </div>
