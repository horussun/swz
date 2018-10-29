define("tms/china/shipmentorder/ShipmentOrderListPage", [ "dojo", "tms/china/js/module/_order" ], function(dojo, Page) {
	/**
	 * 运输订单列表页面对应的javascript代码
	 * This is javascript for ShipmentOrderList page
	 */
	dojo.declare("tms.china.shipmentorder.ShipmentOrderListPage", [ tms.china.js.module._order], {

		constructor : function(json_param) {
			// 在这里可以对传进来的json_param进行一些操作，以来初始化
			this.name = "运输订单";//Util.getI18nValue("TAB_test");
			if(json_param){
				this.searchType = json_param.searchType;
				this.createSearchType = json_param.searchType;
			}
		},
		
		/**
		 * Add required dojo classes
		 */
		addRequired : function() {
			//请求所需的控件
			this.inherited(arguments);
		},
	
		initPage : function() {
	
			// 1. Call super class's same method to init
			// common tasks
			this.gridId="gridID";
			this.pageContainer = "shipment_pageBar",
			this.gridContainer = "shipmentGridContainer",
			this.type = "SHIPMENTORDER",
			
			this.inherited(arguments);
		},
		//双击grid，弹出详细信息
		_cellEdit:function(e){
			var oid =e.grid.getItem(e.rowIndex).i.OID;
			console.debug(oid);
			Nav.go(Nav.SHIPMENT_ORDER_VIEW+"?tracker=true", 'tms.china.shipmentorder.ViewDetailPage',{sid:oid});
		},
		//根据条件让按钮失效（disabled）
		grayButton : function(){
			var selState=this.getSelectionState();
			switch(selState){
				case this.SelectionState.MULTIPLE :
					dijit.byId("shipmentViewDetail").set("disabled",true);
					dijit.byId("shipmentOrderEdit").set("disabled",true);
					break;
				case this.SelectionState.NONE :
					dijit.byId("shipmentViewDetail").set("disabled",true);
					dijit.byId("shipmentOrderEdit").set("disabled",true);
					break;
				case this.SelectionState.SINGLE :
					dijit.byId("shipmentViewDetail").set("disabled",false);
					dijit.byId("shipmentOrderEdit").set("disabled",false);
					break;
			}
		},
		//显示详细页面
		viewDetail: function(){
			if(this.isSelected()){
				var sid = this.getSelectedID("OID");
				console.debug("sid:" + sid);
				Nav.go(Nav.SHIPMENT_ORDER_VIEW, 'tms.china.shipmentorder.ViewDetailPage',{sid:sid});	
			}		
		},
		//显示编辑页面
		showEditView:function(){
			if(this.isSelected()){
				var sid = this.getSelectedID("OID");
				console.debug("sid:" + sid);
				Nav.go(Nav.SHIPMENT_ORDER_EDIT, 'tms.china.shipmentorder.orderEdit',{cmd:'edit',sid:sid});	
			}
		},
		//弹出excle导入窗口
		popUpImport : function(){
			var title="上传excel销售订单|采购订单|运输订单";
			Nav.showDialog(Nav.IMPORT_EXCEL+"?type=shipment", "tms.china.saleOrder.ImportExcel",null,title);
		}
	});

	return tms.china.shipmentorder.ShipmentOrderListPage;
});