<!-- 
	登陆页面
 -->
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>

<%@ include file="common/_head.jspf" %>

<STYLE type="text/css">
@import "${pageContext.request.contextPath}/china/css/login2.css";
@import "${pageContext.request.contextPath}/china/css/style.css";
</STYLE>

 <script>
 	dojo.require("dojo.parser");
	dojo.require("dijit.dijit");
	dojo.require("dijit.form.Button");
	dojo.require("dijit.form.TextBox");
	dojo.require("dijit.form.Form");
 	function changeselect(){
		var roleId=dojo.byId("CType").value;
		//window.alert(roleId);
		var userName=dojo.byId("userName");
		if(roleId==1){
			userName[0]=new Option("A001","A001");
			userName[1]=new Option("A002","A002");
			userName[2]=new Option("A003","A003");
		}else if(roleId==2){
			userName[0]=new Option("B001","B001");
			userName[1]=new Option("B002","B002");
			userName[2]=new Option("B003","B003");
		}else if(roleId==3){
			userName[0]=new Option("C001","C001");
			userName[1]=new Option("C002","C002");
			userName[2]=new Option("C003","C003");
		}else if(roleId==4){
			for(var i = 0;i<userName.options.length;i++){
				userName.options[i] = null; 
			} 
			userName[0]=new Option("D001","D001");
		}
 	}

 	var number=20124;
 	var intervalId=setInterval(function(){
		if(dojo.byId("numberlable")!=null){
			dojo.byId("numberlable").innerHTML = number+"户商家正在交易" ;
	 		number+=Math.floor(Math.random()*5+1);
		}else{
			clearInterval(intervalId);
		}
		
	},2000);
/*
 	function showDetail() {
		dijit.showTooltip("详细信息", dojo.byId("detaildiv"));
	}
	function hiddenDetail() {
		dijit.hideTooltip(dojo.byId("detaildiv"));
	}
	var cnt = dojo.byId("test");
	cnt.addEventListener('mouseover',showDetail, false);
	cnt.addEventListener('mouseout',hiddenDetail, false);
	*/

 </script>
<BODY class="sl" id="bodyId">
 <div id="header">
	<div id="sllogo">
		<img src="${pageContext.request.contextPath}/china/images/sllogo1.png" width="680px" height="49px" />
	</div>
	<div id="ibmlogo">
		<img src="${pageContext.request.contextPath}/china/images/ibmlogo.png" width="69px" height="28px" />
	</div>
</div>
<table width="1024px" align="center" style="font-family:SimHei,Microsoft YaHei,Arial;font-size:14px;background:url(${pageContext.request.contextPath}/china/images/platform_bg.jpg) repeat-x ;">
	<tr>
		<td valign="top"><img src="${pageContext.request.contextPath}/china/images/shiming.jpg" height="65px"/></td>
		<td>作为智慧物流平台商业模式的核心和关键支撑，解决不同主体（企业）之间的数据交换和路由问题，协助各参与主体之间能够进行高可靠、高效率的数据交换，以实现供应链各方信息流的协同工作。在此基础之上，帮助客户应对海量数据信息管理的挑战，提供以信息为中心、以洞察力为导向、以分析驱动决策的优化分析解决方案。</td>
		<td width="200px">
			<div class="outer" style="width:390px;height:65px">
			<form id="form" action="container" method="post">
			<table cellspacing="0" cellpadding="0" align="left" style=" color:#00649D;font-family:SimHei,Microsoft YaHei,Arial;font-size:14px;padding-left:5px;">
				<tr>
					<td align="right"> 角色：</td><td><select id="CType"  autoComplete="true"  name="CType" onchange="javascript:changeselect();">
						<option value="1">供应商</option>
						<option value="2">品牌商</option>
						<option value="3">承运商</option>
						<option value="4">平台管理人员</option></select></td>
					<td align="right">用户：</td><td><select id="userName"  autoComplete="true"  name="userName">
						<option value="A001">A001</option>
						<option value="A002">A002</option>
						<option value="A003">A003</option></select></td>
					<td><span class="activebt"><div  data-dojo-type="dijit.form.Button">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;登录&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<script type="dojo/method" event="onClick" args="e">
	        						dojo.byId("form").submit();
	        	   				</script>
			        	</div> 
			        	</span>
			        </td>
				</tr>
				<tr>
					<td align="right">密码：</td><td colspan="3"><input type="password" value="123456123456" size="29"/></td>
					<td><span class="activebt">
						<div data-dojo-type="dijit.form.Button">邀请您的合作伙伴</div> 
						</span>
					</td>
				</tr>
			</table>
			</form>
			</div>
		</td>
	</tr>
</table>

<table  align="center" >
	<tr>
		<td width="1024px" valign="top">
			<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="1024" height="308" id="banner" align="middle">
			<param name="allowScriptAccess" value="sameDomain" />
			<param name="allowFullScreen" value="false" />
			<param name="movie" value="${pageContext.request.contextPath}/china/images/banner.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#ffffff" />
			<embed src="${pageContext.request.contextPath}/china/images/banner.swf" quality="high" bgcolor="#ffffff" width="1024" height="308" name="banner" align="middle" allowScriptAccess="sameDomain" allowFullScreen="false" type="application/x-shockwave-flash" pluginspage="http://www.adobe.com/go/getflashplayer_cn" />
			</object>
		</td>
	</tr>
	<tr>
		<td>
			<iframe id="ibody" src="http://10.98.0.107/ibmcognos/cgi-bin/cognos.cgi?SM=query&search=H4sIAAAAAAAAAH3KwQqDMAwA0M-x5BpjGSgM9g1j7DCRUlw2CjMpMe77LYLXnd9zk7ARm3vL90U6XDnOdKmMFqtGl4ppnCz96EZZ1A6fY*KQ44cClqa7PUiXJHwUhAZrONcN3AF7Dz36E7b4LF9Wy6v9i23XlbgB5VJIwJ0AAAA_&webcompress=true&SA=propEnum,properties&ITEM=data&EA=&SS=queryOptions,options&dataEncoding=MIMECompressed&ES=&EM=&cv.id=_THIS_&viewer=true" style="width: 1024px;height: 300px; border: none">
			</iframe>
		</td>
	</tr>
</table>

<%@ include file="./common/_bottom.jspf" %>
 
 