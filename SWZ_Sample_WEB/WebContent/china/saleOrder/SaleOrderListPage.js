define("tms/china/saleOrder/SaleOrderListPage", [ "dojo", "tms/china/js/module/_order" ], 
	function(dojo, Page) {
	/**
	 * 销售订单列表页面所使用的功能
	 */
	dojo.declare("tms.china.saleOrder.SaleOrderListPage", [ tms.china.js.module._order], {

		constructor : function(json_param) {
			// 在这里可以对传进来的json_param进行一些操作，以来初始化
			this.name = "销售订单";//Util.getI18nValue("TAB_test");
			if(json_param){
				this.createSearchNameAndKey = json_param.searchNameAndKey;
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
			this.pageContainer = "sale_pageBar",
			this.gridContainer = "saleGridContainer",
			this.type = "SALEORDER",
			
			this.inherited(arguments);
	
		},
		
		//双击列表显示详细信息事件
		_cellEdit:function(e){
			var oid =e.grid.getItem(e.rowIndex).i.OID;
			console.debug(oid);
			Nav.go(Nav.SALEORDER_VIEWDTAIL+"?tracker=true", 'tms.china.saleOrder.ViewDetailPage',{sid:oid});
		},
		
		//根据列表的选择灰化按钮
		grayButton : function(){
			var selState=this.getSelectionState();
			switch(selState){
				case this.SelectionState.MULTIPLE :
					dijit.byId("saleOrderView").set("disabled",true);
					dijit.byId("saleOrderEdit").set("disabled",true);
					dijit.byId("createShipment").set("disabled",true);
					break;
				case this.SelectionState.NONE :
					dijit.byId("saleOrderView").set("disabled",true);
					dijit.byId("saleOrderEdit").set("disabled",true);
					dijit.byId("createShipment").set("disabled",true);
					break;
				case this.SelectionState.SINGLE :
					dijit.byId("saleOrderView").set("disabled",false);
					dijit.byId("saleOrderEdit").set("disabled",false);
					
					var selectedItem=this.getSelectedItem(this.grid);
					var id= this.grid.store.getValue(selectedItem,"SHIPMENT_PID");
					if(id!=""){
						dijit.byId("createShipment").set("disabled",true);
					}else{
						dijit.byId("createShipment").set("disabled",false);
					}
					break;
			}
		},
		
		//编辑销售订单
		editOrder : function(cmd){
			if(cmd=="add"){
			   Nav.go(Nav.SALE_ORDER_EDIT, 'tms.china.saleOrder.EditOrderPage',{cmd:'add'});
			}else
			
			if(this.isSelected()){
			   var oid = this.getSelectedID("OID");
			   console.debug("oid:" + oid);
		           Nav.go(Nav.SALE_ORDER_EDIT, 'tms.china.saleOrder.EditOrderPage',{oid:oid,cmd:"edit"});	
			}
			
	       },
	       
	       //查看销售订单详细
		viewDetail : function(){
		
			if(this.isSelected()){
				var sid = this.getSelectedID("OID");
				console.debug("sid:" + sid);
				
				Nav.go(Nav.SALEORDER_VIEWDTAIL, 'tms.china.saleOrder.ViewDetailPage',{sid:sid});	
			}
			
		},
		
		//根据销售订单创建运单 按钮
		createShipment:function(){
			if(this.isSelected()){
				var oid = this.getSelectedID("OID");
				Nav.go(Nav.SHIPMENT_ORDER_EDIT, 'tms.china.shipmentorder.orderEdit',{cmd:'edit',oid:oid});	
			}
		},
		
		//弹出excel导入窗口
		popUpImport : function(){
			var title="上传excel销售订单|采购订单|运输订单";
			Nav.showDialog(Nav.IMPORT_EXCEL+"?type=sale", "tms.china.saleOrder.ImportExcel",null,title);
		}

	});

	return tms.china.saleOrder.SaleOrderListPage;
});