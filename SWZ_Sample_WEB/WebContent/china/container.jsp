<!-- 
	主容器页面
 -->
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/_body.jspf" %>
<jsp:directive.page  import="com.ibm.dataexchange.web.webbean.RoleBean"/>				
<!-- Style Sheet -->

<STYLE type="text/css">
@import "${pageContext.request.contextPath}/javascript/dojo16/dojox/form/resources/CheckedMultiSelect.css";
@import "${pageContext.request.contextPath}/javascript/dojo16/dojox/grid/enhanced/resources/sl/EnhancedGrid.css";
</STYLE>

<%
RoleBean roleBean =(RoleBean)request.getAttribute("role");
//String name=roleBean.getName();
//String option=roleBean.getOption();
int type=roleBean.getType();

//tab控制
boolean isPurchaser=(type==1)||(type==2);//采购功能
boolean isSales=(type==1)||(type==2) ; 	//销售功能
boolean isShipment=(type==1)||(type==2)||(type==3);//运单功能
boolean isManager=(type==4);//平台传输功能

%>

<SCRIPT type="text/javascript">

	dojo.require("dojo.parser");
	dojo.require("dijit.dijit");

	dojo.require("tms.china.widget._Page");
	dojo.require("tms.china.widget._Widget");
	dojo.require("tms.china.MainContainer");
	dojo.require("dojo.date.locale");
	dojo.require("tms.china.widget.menu.MenuBar");
	dojo.require("tms.china.widget.menu.MenuBarItem");
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dijit.Dialog");
	dojo.require("tms.china.tms_locale.I18n");

	dojo.require("dojox.widget.Toaster");

	//on load
	dojo.ready(
		function(){
			
			var page = new tms.china.MainContainer();
			page.addRequired();

			//save into common reference
			Common.MainContainer = page;
			
			// 定义一个事件的接受，当mainContainer中的子页面加载完成后，隐藏掉可能存在的body standby
			dojo.subscribe("PageLoadComplete", function(text){
				Util.hideStandBy();
			});
			
			//初始化界面
			Common.MainContainer.initPage();

			//load welcome page. 
			Nav.goWelcomePage();
			
			//显示时间
			RunClock();

			//启动脉搏
			Util.startPulse();
		}	
	);

	//时间显示
	function RunClock() {
	    var clock = dojo.byId( 'nowtime' );
	  if ( clock ) {
		 var current = new Date();
		 
		 var display = dojo.date.locale.format(current, {formatLength: "long", selector: "date"})
		 				+ '&nbsp;&nbsp;'
		 				+
		 				dojo.date.locale.format(current, {timePattern: "HH:mm:ss", selector: "time"});
		
	     clock.innerHTML =  display;
	     window.setTimeout("RunClock()",1000);
	  }
	}


	
</SCRIPT>


<div id="menubar" data-dojo-type="tms.china.widget.menu.MenuBar">
<div  data-dojo-type="tms.china.widget.menu.MenuBarItem" data-dojo-props="onClick:function(){Nav.go(Nav.WELCOME_PAGE, 'tms.china.Welcome', null,true);}">首页</div>
   <%if(isSales){ %> 
  
     <div  data-dojo-type="tms.china.widget.menu.MenuBarItem" data-dojo-props="onClick:function(){Nav.go(Nav.SALE_ORDER_LIST, 'tms.china.saleOrder.SaleOrderListPage', null,true);}"><fmt:message key="TAB_SALEORDER" bundle="${ClientStrings}"></fmt:message></div>
  <%} 
  if(isPurchaser){%>
   <div  data-dojo-type="tms.china.widget.menu.MenuBarItem" data-dojo-props="onClick:function(){Nav.go(Nav.PURCHASER_ORDER_LIST, 'tms.china.purchaserorder.PurchaserOrderListPage', null,true);}"><fmt:message key="TAB_PURCHASEORDER" bundle="${ClientStrings}"></fmt:message></div>
   <%}if(isShipment){ %>
    <div  data-dojo-type="tms.china.widget.menu.MenuBarItem" data-dojo-props="onClick:function(){Nav.go(Nav.SHIPMENT_ORDER_LIST, 'tms.china.shipmentorder.ShipmentOrderListPage', null,true);}"><fmt:message key="TAB_TRANSPORTORDER" bundle="${ClientStrings}"></fmt:message></div>
  <%}if(isManager){ %> 
   <div  data-dojo-type="tms.china.widget.menu.MenuBarItem" data-dojo-props="onClick:function(){Nav.go(Nav.TRANSFERDATA_LIST, 'tms.china.transferdata.TransferDataListPage', null,true);}"><fmt:message key="TAB_TRANSMESSAGE" bundle="${ClientStrings}"></fmt:message></div>
  <%}%>
    <div  data-dojo-type="tms.china.widget.menu.MenuBarItem" data-dojo-props="onClick:function(){Nav.go(Nav.REPORT_INDEX, 'tms.china.report.ReportPage', null,true);}"><fmt:message key="TAB_REPORT" bundle="${ClientStrings}"></fmt:message></div>
  
	<div id="option"><span id="nowtime" ></span></div>
	
</div>
<div id="breadcumb" class="hideBreadCrumb">
</div>
<div id="content" style="height:84%">
	<!-- Start of main contenner -->
	<div id="mainContainer" data-dojo-type="dijit.layout.ContentPane" data-dojo-props='region:"center",renderStyles:true,executeScripts:true,cleanContent: true' style="height:100%">
		<div class="infoarea">
		</div>
	</div>
	<div id="mainDialog" data-dojo-type="dijit.Dialog" data-dojo-props='region:"center",renderStyles:true,executeScripts:true,cleanContent: true'></div>
	<div data-dojo-type="dojox.widget.Toaster"  data-dojo-props="positionDirection:'bl-right'" id="first_toaster"></div>
</div>

<%@ include file="./common/_bottom.jspf" %>
