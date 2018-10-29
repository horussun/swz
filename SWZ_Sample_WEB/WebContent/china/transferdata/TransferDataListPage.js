define("tms/china/transferdata/TransferDataListPage", [ "dojo", "tms/china/js/module/_order" ], function(dojo, Page) {
	/**
	 * 平台传输数据列表页面对应的javascript代码
	 * This is javascript for TransferDataListPage page
	 */
	dojo.declare("tms.china.transferdata.TransferDataListPage", [ tms.china.js.module._order], {

		constructor : function(json_param) {
			// 在这里可以对传进来的json_param进行一些操作，以来初始化
			this.name = "订单";//Util.getI18nValue("TAB_test");
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
			this.gridId="gridID2";
			this.pageContainer = "transferdata_pageBar",
			this.gridContainer = "transferdataGridContainer",
			this.type = "TRANSFERMESSAGE",
			
			this.inherited(arguments);
		},
		//得到pageBar控件
		//automatic called by customQueryReadStore.js removed now 
		getPageBar : function(type) {
			//has to use String here because gridId varible is been changed by someother place
			if(type==this.gridId)
				return this.pageBar;
			else
				return null;
		},
		
		//双击grid，弹出详细信息
		_cellEdit:function(e){
			var oid =e.grid.getItem(e.rowIndex).i.OID;
			var otype = e.grid.getItem(e.rowIndex).i.T_TYPE;
			console.debug(oid);
			console.debug(otype);
			var title="订单信息";
			if(otype=="销售订单"){
				Nav.showDialog(Nav.SALEORDER_VIEWDTAIL, 'tms.china.saleOrder.ViewDetailPage',{sid:oid,type:'0'},title);
			}else if(otype =="采购订单"){
				Nav.showDialog(Nav.PURCHASER_ORDER_VIEWDTAIL, 'tms.china.purchaserorder.ViewDetailPage',{pid:oid,type:'0'},title);
			}else{
				Nav.showDialog(Nav.SHIPMENT_ORDER_VIEW, 'tms.china.shipmentorder.ViewDetailPage',{sid:oid,type:'0'},title);
			}
				
		},
		//根据条件让按钮失效（disabled）
		grayButton : function(){
			var selState=this.getSelectionState();
			switch(selState){
				case this.SelectionState.MULTIPLE :
					dijit.byId("OrderView").set("disabled",true);
					break;
				case this.SelectionState.NONE :
					dijit.byId("OrderView").set("disabled",true);
					break;
				case this.SelectionState.SINGLE :
					dijit.byId("OrderView").set("disabled",false);
					break;
			}
		},
		//弹出显示各种订单信息的消息框
		popUpDialog : function(){	
			if(this.isSelected()){
				var id = this.getSelectedID("OID");
				console.debug("id:"+id);
				var t_type = this.getSelectedID("T_TYPE");
				console.debug("t_type:"+t_type);
				
				var title="订单信息";
				if(t_type=="销售订单"){
					Nav.showDialog(Nav.SALEORDER_VIEWDTAIL, 'tms.china.saleOrder.ViewDetailPage',{sid:id,type:'0'},title);
				}
				if(t_type=="采购订单"){
					
					Nav.showDialog(Nav.PURCHASER_ORDER_VIEWDTAIL, 'tms.china.purchaserorder.ViewDetailPage',{pid:id,type:'0'},title);
				}
				if(t_type=="运输订单"){
					Nav.showDialog(Nav.SHIPMENT_ORDER_VIEW, 'tms.china.shipmentorder.ViewDetailPage',{sid:id,type:'0'},title);
				}	
			}
		}
	});
	return tms.china.transferdata.TransferDataListPage;
});