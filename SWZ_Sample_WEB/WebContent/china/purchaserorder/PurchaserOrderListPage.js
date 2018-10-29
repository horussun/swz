define("tms/china/purchaserorder/PurchaserOrderListPage", [ "dojo", "tms/china/js/module/_order" ], function(dojo, Page) {
	/**
	 * 采购订单搜索页面使用到的方法
	 */
	dojo.declare("tms.china.purchaserorder.PurchaserOrderListPage", [ tms.china.js.module._order], {

		constructor : function(json_param) {
			// 在这里可以对传进来的json_param进行一些操作，以来初始化
			this.name = "采购订单";//Util.getI18nValue("TAB_test");
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
			this.pageContainer = "purchaser_pageBar",
			this.gridContainer = "purchaserGridContainer",
			this.type = "PURCHASEORDER",
			
			this.inherited(arguments);
		},
		
		//列表双击打开订单详细事件
		_cellEdit:function(e){
			var oid =e.grid.getItem(e.rowIndex).i.OID;
			console.debug(oid);	
			Nav.go(Nav.PURCHASER_ORDER_VIEWDTAIL+"?tracker=true", 'tms.china.purchaserorder.ViewDetailPage',{pid:oid});
		},
		
		//根据列表选择，灰化按钮
		grayButton : function(){
			var selState=this.getSelectionState();
			switch(selState){
				case this.SelectionState.MULTIPLE :
					dijit.byId("purchaserOrderView").set("disabled",true);
					dijit.byId("purchaserOrderEdit").set("disabled",true);
					break;
				case this.SelectionState.NONE :
					dijit.byId("purchaserOrderView").set("disabled",true);
					dijit.byId("purchaserOrderEdit").set("disabled",true);
					break;
				case this.SelectionState.SINGLE :
					dijit.byId("purchaserOrderView").set("disabled",false);
					dijit.byId("purchaserOrderEdit").set("disabled",false);
					break;
			}
		},
		//点击详细按钮查看订单详细
		viewDetail : function(){
			if(this.isSelected()){
				var pid = this.getSelectedID("OID");
				console.debug("pid:" + pid);
				Nav.go(Nav.PURCHASER_ORDER_VIEWDTAIL, 'tms.china.purchaserorder.ViewDetailPage',{pid:pid});	
			}
			
		},
		//点击编辑按钮编辑订单
		showEditView:function(){
			if(this.isSelected()){
				var pid = this.getSelectedID("OID");
				console.debug("pid:" + pid);
				Nav.go(Nav.PURCHASER_ORDER_EDIT, 'tms.china.purchaserorder.orderEdit',{cmd:'edit',pid:pid});	
			}
		},
		//点击excel导入按钮弹出excel导入窗口
		popUpImport : function(){
			var title="上传excel销售订单|采购订单|运输订单";
			Nav.showDialog(Nav.IMPORT_EXCEL+"?type=purchase", "tms.china.saleOrder.ImportExcel",null,title);
		}
	});

	return tms.china.purchaserorder.PurchaserOrderListPage;
});