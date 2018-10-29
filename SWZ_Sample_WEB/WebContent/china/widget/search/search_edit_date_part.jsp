<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<tr>
              <th><span class="star">*</span><fmt:message key="WEBEAN_SEARCH_DAYOPERAND_P_date" bundle="${ClientStrings}"/></th>
              <td  class="shortselect">
              	 		<select id = "dateKeySelect" dojotype="dijit.form.FilteringSelect" autoComplete="true" required="true" name="dateContent" value="${currentSearchValues.dateContent}" onChange="Common.currentpage.initDateContent()" >
		               		<option value="0"><fmt:message key="WEB_BASE_LM_LMCOMBINESHIPMENTS_RADIO_choose" bundle="${ClientStrings}"/></option>
		               		<c:forEach var="temp" items="${dateContent}" varStatus="s">
							   <option value="${temp.key}:${temp}">${temp.displayObject}</option>
							</c:forEach>
		               	</select> 
		               	<%-- 开始的相对日期 --%>
		               	<span id="dateStrSpan">
			               	<select id = "dateStr" dojotype="dijit.form.FilteringSelect" autoComplete="true" required="true" name="relativeDate" onChange="Common.currentpage.fixDate()" value="${currentSearchValues.relativeContent}">
			               		<option value="0"><fmt:message key="WEB_BASE_LM_LMCOMBINESHIPMENTS_RADIO_choose" bundle="${ClientStrings}"/></option>
			               		<c:forEach var="temp" items="${currentSearchValues.relativeList}">
			               		   <c:if test="${5!=temp.key}"> <option value="${temp.key}:${temp}">${temp.displayObject}</option></c:if>
								</c:forEach>
			                </select> 
		                </span>
						<c:choose>
							<%-- 保存过时间 --%>
		                	<c:when test="${isTimeSaved}">
		                		<%-- 固定日期 --%>
		                		<span id="dateStrInputSpan" style="display:none">
		                			<input id="dateStrInput" type="text" dojotype="dijit.form.DateTextBox" required="true" name="basedate" value="${currentSearchValues.baseDate}" />
		                		</span>
		                		<%-- 开始的相对日期的时间 --%>
		                		<span id="startTimeSpan" class="time">
		                		 	<input type="text" dojoType="dijit.form.TimeTextBox" name="starttime" constraints={timePattern:"HH:mm"} value="${dayTime}" />
		                		</span>
		                		<%-- 显示为“date” --%>
		                		<span id="dateEndSpan">
			                		<select id="dateEnd" dojotype="dijit.form.FilteringSelect" autoComplete="true" required="true" name="rangeContent" value="date" onChange="Common.currentpage.showTime()"> 
					             		<option value="0"><fmt:message key="WEB_BASE_LM_LMCOMBINESHIPMENTS_RADIO_choose" bundle="${ClientStrings}"/></option>
					             		<option value="date"><fmt:message key="COMP_datetime_small_time" bundle="${ClientStrings}"/></option>
					             		<c:forEach var="temp" items="${currentSearchValues.rangeList}" varStatus="s">
										   <option value="${temp.key}:${temp}">${temp.displayObject}</option>
										</c:forEach>
			             			</select>
		             			</span>
		             			<%-- 结束的相对日期范围 --%>
		             			<span id="toDateSpan">
		             				<select id="toDate" dojotype="dijit.form.FilteringSelect" autoComplete="true" required="true" name="todate" value="${currentSearchValues.rangeContent}"> 
			             				<option value="0"><fmt:message key="WEB_BASE_LM_LMCOMBINESHIPMENTS_RADIO_choose" bundle="${ClientStrings}"/></option>
			             				<c:forEach var="temp" items="${currentSearchValues.rangeList}" varStatus="s">
										   <option value="${temp.key}:${temp}">${temp.displayObject}</option>
										</c:forEach>
		             				</select>
		             			</span>
		             			<%-- 结束的相对日期范围的时间 --%>
		             			<span id="toTimeSpan" class="time">
		             				<input type="text" dojoType="dijit.form.TimeTextBox" name="totime" constraints={timePattern:"HH:mm"} value="${dayRangeTime}"/>
				                </span>
		                	</c:when>
		                	<%-- 没有保存过时间 --%>
		                	<c:otherwise>
		                		<%-- 固定日期 --%>
		                		<span id="dateStrInputSpan" style="display:none">
		                			<input id="dateStrInput" type="text" dojotype="dijit.form.DateTextBox" required="true" name="basedate" value="${currentSearchValues.baseDate}" />
		                		</span>
		                		<%-- 开始的相对日期的时间 --%>
		                		<span id="startTimeSpan" style="display:none" class="time">
		                		 	<input type="text" dojoType="dijit.form.TimeTextBox" name="starttime" constraints={timePattern:"HH:mm"} value="now" />
		                		</span>
		                		<%-- 结束的相对日期范围 --%>
		                		<span id="dateEndSpan">
			             			<select id="dateEnd" dojotype="dijit.form.FilteringSelect" autoComplete="true" required="true" name="rangeContent" value="${currentSearchValues.rangeContent}" onChange="Common.currentpage.showTime()"> 
					             		<option value="0"><fmt:message key="WEB_BASE_LM_LMCOMBINESHIPMENTS_RADIO_choose" bundle="${ClientStrings}"/></option>
					             		<option value="date"><fmt:message key="COMP_datetime_small_time" bundle="${ClientStrings}"/></option>
					             		<c:forEach var="temp" items="${currentSearchValues.rangeList}" varStatus="s">
										   <option value="${temp.key}:${temp}">${temp.displayObject}</option>
										</c:forEach>
					             	</select>
				             	</span>
				             	<%-- 结束的相对日期范围（保存时间用） --%>
				             	<span id="toDateSpan" style="display:none"><select id="toDate" dojotype="dijit.form.FilteringSelect" autoComplete="true" required="true" name="todate" value="${currentSearchValues.rangeContent}"> 
				             		<option value="0"><fmt:message key="WEB_BASE_LM_LMCOMBINESHIPMENTS_RADIO_choose" bundle="${ClientStrings}"/></option>
				             		<c:forEach var="temp" items="${currentSearchValues.rangeList}" varStatus="s">
									   <option value="${temp.key}:${temp}">${temp.displayObject}</option>
									</c:forEach>
				             	</select></span>
				             	<%-- 结束的相对日期范围的时间 --%>
				             	<span id="toTimeSpan" style="display:none" class="time"><input type="text" dojoType="dijit.form.TimeTextBox" name="totime" 
									constraints={timePattern:"HH:mm"} value="now" />
				                </span>
		                	</c:otherwise>
		                </c:choose>
              </td> 
            </tr>