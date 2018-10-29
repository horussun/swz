<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
            <tr>
              <th><span class="star">*</span><fmt:message key="WEBEAN_SEARCH_DAYOPERAND_P_date" bundle="${ClientStrings}"/></th>
              <td class="shortselect">
              	 		<select id = "dateKeySelect" dojotype="dijit.form.FilteringSelect" autoComplete="true" required="true" name="dateContent" onChange="Common.currentpage.initDateContent()" >
		               		<option value="0" selected='selected'><fmt:message key="WEB_BASE_LM_LMCOMBINESHIPMENTS_RADIO_choose" bundle="${ClientStrings}"/></option>
		               		<c:forEach var="temp" items="${dateContent}" varStatus="s">
							   <option value="${temp.key}:${temp}">${temp.displayObject}</option>
							</c:forEach>
		               	</select>
		               	<%-- 开始的相对日期 --%>
		               	<span id="dateStrSpan">
			               	<select id = "dateStr" dojotype="dijit.form.FilteringSelect" autoComplete="true" required="true" name="relativeDate" onChange="Common.currentpage.fixDate()" >
			               		<option value="0"><fmt:message key="WEB_BASE_LM_LMCOMBINESHIPMENTS_RADIO_choose" bundle="${ClientStrings}"/></option>
			                </select> 
		                </span>
		                <%-- 固定日期 --%>
		                <span id="dateStrInputSpan" style="display:none">
		                	<input id="dateStrInput" type="text" dojotype="dijit.form.DateTextBox" required="true" name="basedate" />
		                </span>
		                <%-- 开始的相对日期的时间 --%>
		                <span id="startTimeSpan" style="display:none" class="time">
		                	<input type="text" dojoType="dijit.form.TimeTextBox" name="starttime" constraints={timePattern:"HH:mm"} value="now" />
		                </span>
		                <%-- 结束的相对日期范围（不保存时间用） --%>
		                <span id="dateEndSpan"> 
			             	<select id="dateEnd" dojotype="dijit.form.FilteringSelect" autoComplete="true" required="true" name="rangeContent" onChange="Common.currentpage.showTime()"> 
			             		<option value="0"><fmt:message key="WEB_BASE_LM_LMCOMBINESHIPMENTS_RADIO_choose" bundle="${ClientStrings}"/></option>
			             	</select>
		             	</span>
		             	<%-- 结束的相对日期范围（保存时间用） --%>
		           		<span id="toDateSpan" style="display:none">
		           			<select id="toDate" dojotype="dijit.form.FilteringSelect" autoComplete="true" required="true" name="todate"> 
		             			<option value="0"><fmt:message key="WEB_BASE_LM_LMCOMBINESHIPMENTS_RADIO_choose" bundle="${ClientStrings}"/></option>
		             		</select>
		             	</span>
		             	<%-- 结束的相对日期范围的时间 --%>
		             	<span id="toTimeSpan" style="display:none" class="time">
		             		<input type="text" dojoType="dijit.form.TimeTextBox" name="totime" constraints={timePattern:"HH:mm"} value="now" />
						</span>
              </td> 
            </tr>