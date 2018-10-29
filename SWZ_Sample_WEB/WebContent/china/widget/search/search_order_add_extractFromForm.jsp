<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../../common/_tag.jspf"%>
<%@ page import="com.nistevo.webean.util.*,
com.nistevo.webean.*,
com.nistevo.webean.bean.*,
com.nistevo.webean.search.*,
com.nistevo.bean.common.*,
com.nistevo.bean.profile.*"
%>

<style type="text/css">
    @import "../../../javascript/dojo16/dojox/form/resources/CheckedMultiSelect.css";
</style>




<div id="content">
       <form id="searchContentParm">
       <div id="infoarea">
         <div class="save_as">
          <!-- 第一部分 -->
         <table>
              <tr>
                <th>定制搜索另存为（可选）</th>
                <td>
                	<input type="text"  dojoType="dijit.form.TextBox"  name="${nameMapping.searchName}"/>
                	<input dojoType="dijit.form.CheckBox"  name="${nameMapping.isSearchDefault}"/><label for="mycheck">使其成为我的缺省搜索</label>
                </td>
              </tr>
         </table>
         </div>
            <hr />
            <!-- 第二部分 -->
		  <div class="constraints">
		          <label class="title">搜索限制</label>
		          <table>
		            <tr>
		              <th>日期</th>
		              <td colspan="3">
		              	<select id = "dateSelect" dojotype="dijit.form.FilteringSelect" autoComplete="true" name="${nameMapping.dateKey}" onChange="Common.currentpage.getCreateDateContent()">
		               		<option value="">选择</option>
		               		<c:forEach var="temp" items="${dateContent}" varStatus="s">
							   <option value="${temp.key}:${temp}">${temp.displayObject}</option>
							</c:forEach>
		               	</select> 
		                <input type="text" dojotype="dijit.form.DateTextBox" required="true" name="${nameMapping.dateStr}" />
		             	<select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="${nameMapping.dateEnd}"> 
		             		<c:forEach var="temp" items="${day_ranges_30}" varStatus="s">
							<option value="${temp.key}:${temp}">${temp.displayObject}</option>
							</c:forEach>
		             	</select> 
		            </tr>
		            <tr>
		              <th>始发地</th>
		              <td ><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="${nameMapping.origin_eq}"> 
		              			<option value="0">所有</option>
		              			<c:forEach var="temp" items="${origin}" varStatus="s">
								    <option value="${temp.key}:${temp}">${temp.displayObject}</option>
								</c:forEach>
		              	   </select>
		              <button class="iconbt" type="button"><img src="/images/search.png"  width="20px" height="20px"  /></button>
		              </td>
		              
		              <th >目的地</th>
		              <td ><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="${nameMapping.destination_eq}"> 
		              			<option value="0">所有</option>
		              			<c:forEach var="temp" items="${destination}" varStatus="s">
									<option value="${temp.key}:${temp}">${temp.displayObject}</option>
								</c:forEach>
		              		</select>
		              <button class="iconbt" type="button"><img src="/images/search.png"  width="20px" height="20px"  /></button>
		              </td>
		            </tr>
		            <tr>
		              <th>Ship from</th>
		              <td ><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="${nameMapping.shipFrom_eq}"> 
		              			<option value="0">所有</option>
		              			<c:forEach var="temp" items="${shipFrom}" varStatus="s">
									<option value="${temp.key}:${temp}">${temp.displayObject}</option>
								</c:forEach>
		              	   </select>
		              <button class="iconbt" type="button"><img src="/images/search.png"  width="20px" height="20px"  /></button></td>
		              
		              <th >Ship to</th>
		              <td ><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="${nameMapping.shipTo_eq}"> 
		              			<option value="0">所有</option>
		              			<c:forEach var="temp" items="${shipTo}" varStatus="s">
									<option value="${temp.key}:${temp}">${temp.displayObject}</option>
								</c:forEach>
		              		</select>
		              <button class="iconbt" type="button"><img src="/images/search.png"  width="20px" height="20px"  /></button></td>
		            </tr>
		            
		            <tr>
		              <th class="topth">模式</th>
		              <td>
		                 <select multiple="true" name="${nameMapping.mode_multi}" dojoType="dojox.form.CheckedMultiSelect">
						   <c:forEach var="temp" items="${mode}" varStatus="s">
								<option value="${temp.key}">${temp.displayObject}</option>
							</c:forEach>
						</select>
					  </td>
		            </tr>
		           
		              
		            <tr>
		              <th>移动类型</th>
		              <td><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="${nameMapping.movementType_eq}"> 
		              		<option value="0">所有</option>
		              		<c:forEach var="temp" items="${movementType}" varStatus="s">
								<option value="${temp.key}:${temp}">${temp.displayObject}</option>
							</c:forEach>
		              	  </select>
		              </td>
		              <th>设备列表</th>
		              <td><select dojotype="dijit.form.FilteringSelect" autoComplete="true"name="${nameMapping.equipmentTypeSearch_eq}">
		              		<option value="0">所有</option>
		              		<c:forEach var="temp" items="${equipmentTypeSearch}" varStatus="s">
								<option value="${temp.key}:${temp}">${temp.displayObject}</option>
							</c:forEach>
		              	  </select>
		              </td>
		            </tr> 
		            <tr>
		              <th>描述</th>
		              <td colspan="3" > <textarea class="info_textarea" dojoType="dijit.form.Textarea" name="${nameMapping.itemDescription_subString}"></textarea></td>
		            </tr>
		              
		            <tr>
		              <th class="topth">计划状态</th>
		               <td >
		              <select multiple="true" name="${nameMapping.planStatus_multi}" dojoType="dojox.form.CheckedMultiSelect"> 
		             	 <c:forEach var="temp" items="${planStatus}" varStatus="s">
								<option value="${temp.key}">${temp.displayObject}</option>
						</c:forEach>
		              </select>
		              </td>
		            </tr>
		            <tr>
		              <th class="topth">装运状态</th>
		              <td >
		              <select multiple="true" name="${nameMapping.status_multi}" dojoType="dojox.form.CheckedMultiSelect">
		              	 <c:forEach var="temp" items="${status}" varStatus="s">
								<option value="${temp.key}">${temp.displayObject}</option>
						 </c:forEach>
		              </select>
		              </td>
		            </tr>
		            <tr>
		              <th>备注</th>
		              <td colspan="3" class="radio_td"> 
		                <input  type="radio" dojoType="dijit.form.RadioButton" name="${nameMapping.hasNote_eq}" value="true"/><label for="radioOne"> 是 </label>
		                <input  type="radio" dojoType="dijit.form.RadioButton" name="${nameMapping.hasNote_eq}" value="false"/><label for="radioOne"> 否</label>
		                <input  type="radio"  dojoType="dijit.form.RadioButton" name="${nameMapping.hasNote_eq}" value="null" checked="true"/><label for="radioOne"> 从不 </label></td>
		            </tr>
		            <tr>
		              <th>警报</th>
		              <td colspan="3"  class="radio_td"> 
		                <input  type="radio" dojoType="dijit.form.RadioButton" name="${nameMapping.hasAlert_eq}" value="true"/><label for="radioOne"> 是 </label>
		                <input  type="radio" dojoType="dijit.form.RadioButton" name="${nameMapping.hasAlert_eq}" value="false"/><label for="radioOne"> 否</label>
		               <input  type="radio" dojoType="dijit.form.RadioButton"  name="${nameMapping.hasAlert_eq}" value="null" checked="true"/><label for="radioOne"> 从不 </label></td>
		            </tr>
		            <tr>
		              <th>客户</th>
		              <td ><select dojotype="dijit.form.FilteringSelect" autoComplete="true"  name="${nameMapping.customer_eq}" id="s_customer" onChange="Org.customerdetail('search');">
		              			<option value="0">所有</option>
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
		              <th>供应商</th>
		            <td ><select dojotype="dijit.form.FilteringSelect" autoComplete="true"  name=${nameMapping.supplier_eq} id="s_supplier" onChange="Org.supplierdetail('search');">
		            		<option value="0">所有</option>
		              		<c:forEach var="temp" items="${supplier}" varStatus="s">
							<option value="${temp.key}:${temp}">${temp.displayObject}</option>
							</c:forEach>
		            	 </select> 
		          <span class="iconbt dropiconbt">
			    	<div dojotype="dijit.form.DropDownButton" iconClass="detailIcon classIcon"  showLabel="false"  title="组织详细信息">
					<div id="supplierdialog" data-dojo-type="dijit.TooltipDialog">
						<span><fmt:message key="nb.bill.editbill.choose_one" bundle="${NB}"></fmt:message></span>
					</div>
				</div>
			</span>
		            </td>                    
		            </tr>
		            <tr>
		              <th>引用类型</th>
		              <td>
		              	<select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="${nameMapping.referenceType_eq}">
		              	<option value="0">所有</option>
		              	<c:forEach var="temp" items="${referenceType}" varStatus="s">
						<option value="${temp.key}:${temp}">${temp.displayObject}</option>
						</c:forEach>
		              	</select> 
		              </td>
		              <th>引用编号</th>
		              <td><input type="text"  dojoType="dijit.form.TextBox"  name="${nameMapping.referenceNumber_startsWith}"/></td>
		            </tr>
		            <tr>
		              <th>重量大于/等于</th>
		              <td><input type="text"  dojoType="dijit.form.TextBox"  name="${nameMapping.referenceNumber_weight_ge}" value="0.0"/>
		              <td><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="${nameMapping.referenceNumber_weight_ge}__dim"> 
		              			<c:forEach var="temp" items="${weightType}" varStatus="s">
								<option value="${temp.key}">${temp.displayObject}</option>
								</c:forEach>
		              	   </select>
		              </td>
		              
		              <th>重量小于/等于</th>
		              <td><input type="text"  dojoType="dijit.form.TextBox"  name="${nameMapping.referenceNumber_weight_le}" value="0.0"/>
		              <td><select dojotype="dijit.form.FilteringSelect" autoComplete="true" name="${nameMapping.referenceNumber_weight_ge}__dim"> 
		              			<c:forEach var="temp" items="${weightType}" varStatus="s">
								<option value="${temp.key}">${temp.displayObject}</option>
								</c:forEach>
		              		</select>
		             </td>
		            </tr> 
		              
		              <tr>
		                <th class="topth">付款方法</th>
		                 <td >
				              <select multiple="true" name="${nameMapping.paymentMethod_multi}" dojoType="dojox.form.CheckedMultiSelect">
				              	 <c:forEach var="temp" items="${paymentMethod}" varStatus="s">
										<option value="${temp.key}">${temp.displayObject}</option>
								 </c:forEach>
				              </select>
		              	</td>
		            </tr>
		          </table>
		          </div>
          
            <hr />
            <label class="title">排序订单</label>
            <div class="sort">
             <table>
              <tr>
                <th>排序方式1</th>
                <td><select  name="${nameMapping.sortBy1}" dojoType="dijit.form.FilteringSelect">
		              	 <c:forEach var="temp" items="${sortBy1}" varStatus="s">
								<option value="${temp.key}">${temp.displayObject}</option>
						 </c:forEach>
		              </select>
		            </td>
                 <th>顺序</th>
                <td> <input  type="radio" name="${nameMapping.ascending1}" value="true"/><label for="radioOne">升序</label>
                     <input  type="radio" name="${nameMapping.ascending1}" value="fals"/><label for="radioOne"> 降序</label>
                 </td>
              </tr>
              <tr>
                <th>排序方式2</th>
                <td><select  name="${nameMapping.sortBy2}" dojoType="dijit.form.FilteringSelect">
		              	 <c:forEach var="temp" items="${sortBy2}" varStatus="s">
								<option value="${temp.key}">${temp.displayObject}</option>
						 </c:forEach>
		              </select>
		            </td>
                 <th>顺序</th>
                <td> <input  type="radio" name="${nameMapping.ascending2}" value="true"/><label for="radioOne">升序</label>
                     <input  type="radio" name="${nameMapping.ascending2}" value="fals"/><label for="radioOne"> 降序</label>
                 </td>
              </tr>
              <tr>
                <th>排序方式3</th>
                <td><select name="${nameMapping.sortBy3}" dojoType="dijit.form.FilteringSelect">
		              	 <c:forEach var="temp" items="${sortBy3}" varStatus="s">
								<option value="${temp.key}">${temp.displayObject}</option>
						 </c:forEach>
		              </select>
		            </td>
                 <th>顺序</th>
               <td> <input  type="radio" name="${nameMapping.ascending3}" value="true"/><label for="radioOne">升序</label>
                     <input  type="radio" name="${nameMapping.ascending3}" value="fals"/><label for="radioOne"> 降序</label>
                 </td>
              </tr>
            </table>
            </div> 
      </div> 
     </form>
     <div id="btarea">
        <div id="infobts">
        <!--<button dojotype="dijit.form.Button">搜索</button>  -->
        <button dojotype="dijit.form.Button">保存
        	<script type="dojo/method" data-dojo-event="onClick" data-dojo-args="evt">
                        	Common.currentpage.save("order");
            </script>
        </button>
        <button dojotype="dijit.form.Button">复制</button>
        <button dojotype="dijit.form.Button">取消</button>
        </div>
     </div>
  </div>