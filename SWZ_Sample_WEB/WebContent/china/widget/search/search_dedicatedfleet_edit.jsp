<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../../common/_tag.jspf"%>
<style type="text/css">
    @import "../../../javascript/dojo16/dojox/form/resources/CheckedMultiSelect.css";
</style>
  <div id="content2">
       <form id="searchContentParm" data-dojo-type="dijit.form.Form"
		data-dojo-props='encType:"multipart/form-data", onSubmit:function(e){Common.currentpage.save("dedicatedfleet"); return false;}'>
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

   <%--车辆状态 --%>
              <tr>
              <th class="topth"><fmt:message key="BEAN_FLEET_DEDICATED_FLEETMANAGEMENTTRIPVIEWITEM_P_SEARCH_tractorStatus" bundle="${ClientStrings}" /></th>
              <td>
              
                 <select multiple="true" name="tractorStatus" dojoType="dojox.form.CheckedMultiSelect">
				   <c:forEach var="temp" items="${tractorStatus}">
						<option value="${temp.key}"
								<c:forEach var="o" items="${currentSearchValues.tractorStatus_multi}" varStatus="s">
										<c:if test="${o eq temp.key}">selected="selected"</c:if>
								</c:forEach> >${temp.displayObject}</option>
					</c:forEach>
				</select>				
               
			  </td>
            </tr>            
 
   <%--长短途 --%>
              <tr>
              <th class="topth"><fmt:message key="BEAN_FLEET_DEDICATED_FLEETMANAGEMENTTRIPVIEWITEM_P_SEARCH_preferDistance" bundle="${ClientStrings}" /></th>
              <td>
              
                 <select multiple="true" name="preferDistance" dojoType="dojox.form.CheckedMultiSelect">
				   <c:forEach var="temp" items="${preferDistance}">
						<option value="${temp.key}"
								<c:forEach var="o" items="${currentSearchValues.preferDistance_multi}" varStatus="s">
										<c:if test="${o eq temp.key}">selected="selected"</c:if>
								</c:forEach> >${temp.displayObject}</option>
					</c:forEach>
				</select>				
               
			  </td>
            </tr>      
    
 <%--车型 --%>
              <tr>
              <th class="topth"><fmt:message key="BEAN_FLEET_DEDICATED_FLEETMANAGEMENTTRIPVIEWITEM_P_SEARCH_equipment" bundle="${ClientStrings}" /></th>
              <td>
              
                 <select multiple="true" name=orgEquipment dojoType="dojox.form.CheckedMultiSelect">
				   <c:forEach var="temp" items="${orgEquipment}">
						<option value="${temp.key}"
								<c:forEach var="o" items="${currentSearchValues.equipment_multi}" varStatus="s">
										<c:if test="${o eq temp.key}">selected="selected"</c:if>
								</c:forEach> >${temp.displayObject}</option>
					</c:forEach>
				</select>				
               
			  </td>
            </tr>              
         <%--过期 日期--%>     
          <tr> 
          	 <th><fmt:message key="TRUCK_MANAGER_EXPIRATION_DATE" bundle="${ClientStrings}"/></th>
                  <td><input type="text" name="expirationDate" id="expirationDate" dojoType="dijit.form.DateTextBox"  value="${currentSearchValues.expirationDate_eq}" constraints="{timePattern:'yyyy-MM-dd'}" />
	               </td>
           </tr>  
           <tr>
           
              <th><fmt:message key="BEAN_CAPCENTER_CAPACITYCENTERITEM_P_radiusLocation" bundle="${ClientStrings}"/></th>              
            
            <td >
			  	<input type="text" id="cs_origin2_text"data-dojo-type="dijit.form.ValidationTextBox" /> <%-- 设置初始化的值 --%> 
				<select
					data-dojo-props='name:"radiusLocation",maxHeight:"150"' id="cs_origin2"
					data-dojo-type="dijit.form.FilteringSelect">
					
					<c:choose>
						<c:when test="${originFlag}">
							<c:forEach var="o" items="${origin}" varStatus="s">
								<option value="${o.key}:${o}"
									<c:if test="${o.key eq order.origin.key}">selected</c:if>>${o}</option>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<option value="${order.origin.key}:${order.origin}" selected>${order.origin}</option>
						</c:otherwise>
					</c:choose>
					<option value="${currentSearchValues.radiusLocation_eq}" selected>${currentSearchValues.radiusLocation_eq}</option>
				</select> 
				<span class="iconbt">
						<button id="cs_origin2_search" dojoType="dijit.form.Button"
							iconClass="searchIcon classIcon" showLabel="false" type="button"><fmt:message key="CMD_disp_save" bundle="${ClientStrings}"/></button>
				</span>
             </td>  
            </tr>
            
           <tr> 
           <th><fmt:message key="BEAN_FLEET_DEDICATED_FLEETMANAGEMENTTRIPVIEWITEM_P_SEARCH_radiusArea" bundle="${ClientStrings}"/></th>
              <td><input type="text"  dojoType="dijit.form.TextBox"  name="radiusArea" value="${currentSearchValues.radiusArea_eq}"/></td>
           </tr> 
        <tr> 
              <th><fmt:message key="BEAN_FLEET_DEDICATED_FLEETMANAGEMENTTRIPVIEWITEM_P_SEARCH_radiusDistance" bundle="${ClientStrings}"/></th>
              <td  class="short">
              		<input type="text" id="radiuDistance"  dojoType="dijit.form.NumberTextBox"  name="radiusDistance" value="${currentSearchValues.radiusDistance_eq_value}"/>
             		<select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="distanceUnit" value="${currentSearchValues.radiusDistance_eq_dim}"> 
              			<c:forEach var="temp" items="${distanceType}">
							<option value="${temp.key}">${temp.displayObject}</option>
						</c:forEach>
              	   </select>
              </td>
          </tr>
            
          </table>
          </div>
           <hr/>
          <%@ include file="sort.jsp"%>
      </div> 
      <input type="submit" class="hideSubmitInput">
     </form>
     <div id="btarea">
        <div id="infobts">
      <!--<button dojotype="dijit.form.Button">搜索</button>  -->  
        <span class="activebt"><button dojotype="dijit.form.Button"><fmt:message key="CMD_disp_save" bundle="${ClientStrings}"/>
        	<script type="dojo/method" data-dojo-event="onClick" data-dojo-args="evt">
                        	Common.currentpage.save("dedicatedfleet");
            </script>
        </button></span>
        <span class="activebt"><button dojotype="dijit.form.Button" onClick="Common.currentpage.deleteSearch('dedicatedfleet')"><fmt:message key="CMD_delete_routeGuide" bundle="${ClientStrings}"/></button></span>
       <span class="passivebt"> <button dojotype="dijit.form.Button" onClick="Nav.closePage()"><fmt:message key="CMD_delete_cancel" bundle="${ClientStrings}"/></button></span>
        </div>
     </div>
  </div>