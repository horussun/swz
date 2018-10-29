<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

  <label class="title"><fmt:message key="TABLE_sort" bundle="${ClientStrings}"/></label>
         <div class="sort">
           <table>
              <tr>
                <th><fmt:message key="WEBEAN_BEAN_SEARCHBEAN_P_sortBy1" bundle="${ClientStrings}"/></th>
                <td><select id="sort1" name="sortBy1" dojoType="dijit.form.FilteringSelect" maxHeight="200">
                     <option value=""><fmt:message key="WEB_BASE_LM_LMCOMBINESHIPMENTS_RADIO_choose" bundle="${ClientStrings}"/></option>
                     <c:choose> 
                      <c:when test="${editFlag}">
                      <c:forEach var="temp" items="${sortBy}" varStatus="s">
								<option value="${temp.key}:${temp.column}"
								<c:if test="${temp.key eq sortBy1.key}"> selected</c:if>>${temp.displayObject}</option>								
						 	</c:forEach>		              	 
					  </c:when>
						 <c:otherwise>
							<c:forEach var="temp" items="${sortBy}" varStatus="s">
								<option value="${temp.key}:${temp.column}">
								${temp.displayObject}</option>
						 	</c:forEach>
						</c:otherwise>
						</c:choose>
		              </select>
		            </td>
                 <th><fmt:message key="WEBEAN_BEAN_SEARCHBEAN_P_ascending1" bundle="${ClientStrings}"/></th>
                <td> <input  dojoType="dijit.form.RadioButton" type="radio" name="ascending1" value="true" <c:if test="${sortBy1Order}">checked</c:if>/><label for="radioOne"><label for="radioOne"><fmt:message key="SEARCH_STR_ascending" bundle="${ClientStrings}"/></label>
                     <input dojoType="dijit.form.RadioButton"  type="radio" name="ascending1" value="false" <c:if test="${!sortBy1Order}">checked</c:if>/><label for="radioOne"> <fmt:message key="SEARCH_STR_descending" bundle="${ClientStrings}"/></label>
                 </td>
              </tr>
              <tr>
                <th><fmt:message key="WEBEAN_BEAN_SEARCHBEAN_P_sortBy2" bundle="${ClientStrings}"/></th>
                <td><select id="sort2"  name="sortBy2" dojoType="dijit.form.FilteringSelect" maxHeight="200">
                 <option value=""><fmt:message key="WEB_BASE_LM_LMCOMBINESHIPMENTS_RADIO_choose" bundle="${ClientStrings}"/></option>
                 <c:choose> 
                      <c:when test="${editFlag}">
                      <c:forEach var="temp" items="${sortBy}" varStatus="s">
								<option value="${temp.key}:${temp.column}"
								<c:if test="${temp.key eq sortBy2.key}"> selected</c:if>>${temp.displayObject}</option>								
						 	</c:forEach>		              	 
					  </c:when>
						 <c:otherwise>
							<c:forEach var="temp" items="${sortBy}" varStatus="s">
								<option value="${temp.key}:${temp.column}">
								${temp.displayObject}</option>
						 	</c:forEach>
						</c:otherwise>
						</c:choose>
		              </select>
		            </td>
                 <th><fmt:message key="WEBEAN_BEAN_SEARCHBEAN_P_ascending1" bundle="${ClientStrings}"/></th>
                <td> <input   dojoType="dijit.form.RadioButton"type="radio" name="ascending2" value="true" <c:if test="${sortBy2Order}">checked</c:if>/><label for="radioOne"><fmt:message key="SEARCH_STR_ascending" bundle="${ClientStrings}"/></label>
                     <input   dojoType="dijit.form.RadioButton"type="radio" name="ascending2" value="false" <c:if test="${!sortBy2Order}">checked</c:if>/><label for="radioOne"> <fmt:message key="SEARCH_STR_descending" bundle="${ClientStrings}"/></label>
                 </td>
              </tr>
              <tr>
                <th><fmt:message key="WEBEAN_BEAN_SEARCHBEAN_P_sortBy3" bundle="${ClientStrings}"/></th>
                <td><select  id="sort3" name="sortBy3" dojoType="dijit.form.FilteringSelect" maxHeight="200">
                 <option value=""><fmt:message key="WEB_BASE_LM_LMCOMBINESHIPMENTS_RADIO_choose" bundle="${ClientStrings}"/></option>
                 <c:choose> 
                      <c:when test="${editFlag}">
                      <c:forEach var="temp" items="${sortBy}" varStatus="s">
								<option value="${temp.key}:${temp.column}"
								<c:if test="${temp.key eq sortBy3.key}"> selected</c:if>>${temp.displayObject}</option>								
						 	</c:forEach>		              	 
					  </c:when>
						 <c:otherwise>
							<c:forEach var="temp" items="${sortBy}" varStatus="s">
								<option value="${temp.key}:${temp.column}">
								${temp.displayObject}</option>
						 	</c:forEach>
						</c:otherwise>
						</c:choose>
		              </select>
		            </td>
                 <th><fmt:message key="WEBEAN_BEAN_SEARCHBEAN_P_ascending1" bundle="${ClientStrings}"/></th>
               <td> <input  dojoType="dijit.form.RadioButton" type="radio" name="ascending3" value="true" <c:if test="${sortBy3Order != null && sortBy3Order}">checked</c:if> /><label for="radioOne"><fmt:message key="SEARCH_STR_ascending" bundle="${ClientStrings}"/></label>
                    <input dojoType="dijit.form.RadioButton" type="radio" name="ascending3" value="false" <c:if test="${sortBy3Order != null && !sortBy3Order}">checked</c:if>/><label for="radioOne"><fmt:message key="SEARCH_STR_descending" bundle="${ClientStrings}"/></label>
                 </td>
              </tr>
            </table>
            <div style="display:none">
              <input  name="isEdit" value="${editFlag}"></input>
             </div>
            </div> 
      