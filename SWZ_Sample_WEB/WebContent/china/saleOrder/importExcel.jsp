<!-- 
	导入excel弹出窗口页面
 -->
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="../common/_tag.jspf" %>

<input type="hidden" name="type" id="type" value="${type}"/>
<div id="content2" style="width:600px;">
<form method="post" id="myForm" action="/b2bhub/importexcel/importExcel" enctype="multipart/form-data" >
<div id="uploaddiv" style="border:3px solid #ddd;-moz-border-radius:10px;-webkit-border-radius:10px;border-radius:10px;">
	<table >
		<tr>
			<td width="60px"></td>
			<td align="right" width="160px"></td>
			<td align="right" width="160px"></td>
			<td align="left" width="160px">
				<a href="${pageContext.request.contextPath}/template/template-${type}order.xls">Excel模板下载</a>
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="right">选择上传的文件</td>
			<td align="right"> 
				<input id="uploadFileName" dojoType="dijit.form.TextBox" type="text" style="width: 150px;" readOnly="true" />
			</td>
			<td align="left">
				<input class="browseButton" name="uploadedfile" multiple="true" type="file" dojoType="dojox.form.Uploader" label="浏览..." id="uploader" url="/b2bhub/importexcel/importExcel" />
			</td>
        </tr>
	</table>
</div>
</form>
	<div id="btarea" style="width:600px">
		<div id="infobts">
			<span class="activebt">
				<div data-dojo-type="dijit.form.Button">
					<fmt:message key="CMD_done" bundle="${ClientStrings}"></fmt:message>
					<script type="dojo/method" event="onClick" args="e">
	        		Common.currentDialogPage.excelImport();
	        	   </script>
				</div> 
			</span> 
			<span class="passivebt">
				<div data-dojo-type="dijit.form.Button">
					 <fmt:message key="CMD_cancel" bundle="${ClientStrings}"></fmt:message>
					<script type="dojo/method" event="onClick" args="e">
	        		Common.currentDialogPage.cancel();
	        	    </script>
				</div> 
			</span>
		</div>
	</div>
</div>