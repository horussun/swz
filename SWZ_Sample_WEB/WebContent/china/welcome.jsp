<!-- 
	欢迎页面
 -->
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Include Tag common jspf --%>
<%@ include file="common/_tag.jspf"%>

<!--<div class="infoarea">
	<div id="welcome">
		<h1>欢迎使用</h1>
		<p><label>数据交换平台/label></p>
	</div>   
	  -->
<jsp:directive.page  import="com.ibm.dataexchange.web.profile.UserContext"/>		
<table  align="center">
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="224px"  valign="top" >
			<table>
				<tr>
					<td>
						<div class="content1" style="width:223px;">
							<div class="heading"><label>我的服务</label></div>
							<hr/>
							<table cellspacing="20" cellpadding="20">
								<tr>
									<td><a href="http://10.98.0.160:8000/" target="_blank"><img src="${pageContext.request.contextPath}/china/images/s1.jpg" width="70px"/></a></td>
									<td><a href="http://10.98.1.160:7000/servlet/Login" target="_blank"><img src="${pageContext.request.contextPath}/china/images/s2.jpg" width="70px"/></a></td>
								</tr>
								<tr>
									<td><img src="${pageContext.request.contextPath}/china/images/s3.jpg" width="70px"/></td>
									<td><img src="${pageContext.request.contextPath}/china/images/s4.jpg" width="70px"/></td>
								</tr>
							</table>
							
						</div>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td>
						<div class="content1" style="width:223px;">
							<div class="heading"><label>我关注的企业的动态</label></div>
							<hr/>
							<div class="bkg">
								<p><a>宁波彩虹制衣面向全国承招供应商</a></p><hr>
								<p><a>荣成承运车队面向江浙沪地区提供中短途运输服务</a></p><hr>
								<p><a>荣成承运车队今年首次降低运输价格</a></p><hr>
								<p><a>宁波彩虹制衣面向全国承招服装销售代理</a></p><hr>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="content1" style="width:223px;" align="right">
							<div><textarea style="width:223px;height:110px;">输入信息内容</textarea></div>
							<hr/>
							<div data-dojo-type="dijit.form.Button">发布信息</div>
						</div>
					</td>
				</tr>
			</table>
		</td>
		<td width="800px" rowspan="3" valign="top">

			<iframe id="ibody" src="http://10.98.0.107/ibmcognos/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=view&ui.format=HTML&ui.object=storeID(%22iE070A579268140B5BD6169129D60418D%22)&ui.name=main_page_1&cv.header=false&ui.backURL=%2fibmcognos%2fcps4%2fportlets%2fcommon%2fclose.html&cv.header=false&cv.toolbar=false" style="width: 800px;height:600px; border:none;scrolling:no" >
			</iframe>


		</td>
	</tr>

</table>

 
 