define("tms/china/shipmentorder/searchPurchaserOrder", [ "dojo", "tms/china/widget/_Page","tms/china/js/module/_selection" ], function(dojo, Page) {
	/**
	 * 搜索销售订单页面对应的javascript代码
	 * This is javascript for searchPurchaserOrder page
	 */
	dojo.declare("tms.china.shipmentorder.searchPurchaserOrder", [ tms.china.widget._Page,tms.china.js.module._selection], {

		constructor : function(json_param) {
			// 在这里可以对传进来的json_param进行一些操作，以来初始化
			this.name = "采购订单";//Util.getI18nValue("TAB_test");
			if(json_param){
				this.createSearchNameAndKey = json_param.searchNameAndKey;
				this.createSearchType = json_param.searchType;
			}
		},
		
		//searchPart: null,
		//grid: null,
		name:null,
		po_pageBar:null,
		sort:null,
		gridId:null,
		/**
		 * Add required dojo classes
		 */
		addRequired : function() {
			//请求所需的控件
			dojo.require("tms.china.widget.pageBar.pageBar");
			
			dojo.require("dojo.data.ItemFileReadStore");
			dojo.require("dijit.form.Select");
			dojo.require("dijit.form.Button");
			dojo.require("dijit.form.FilteringSelect");
			
			dojo.require("dojox.grid.MyEnhancedGrid");
			dojo.require("dojox.grid.enhanced.plugins.Menu");
			dojo.require("dojox.grid.enhanced.plugins.Selector");
			dojo.require("dojox.grid.enhanced.plugins.IndirectSelection");
			dojo.require("dojox.grid.enhanced.plugins.MyNestedSorting");
			dojo.require("tms.china.widget.sortWidget.sortWidget");
			dojo.require("tms.china.widget.customizeGridWidget.customizeGridWidget");
			dojo.require("dojox.grid.enhanced.plugins.Filter");
			dojo.require("tms.china.js.module.CustomQueryReadStore");
		},
	
		initPage : function() {
	
			// 1. Call super class's same method to init
			// common tasks
			this.inherited(arguments);
	
			// 2. Init components
			var flag=false;
			if(Nav.getRestoreData()){
				flag=true;
			}
			gridId="gridID";
				
			this.po_pageBar=new tms.china.widget.pageBar.pageBar({name:"poPageBarName",grid:gridId}).placeAt("po_pageBar");
			
			var layout=this.getLayout("PURCHASEORDER");
			var store=new tms.china.js.module.CustomQueryReadStore({
				name:gridId,
				requestMethod:"post"
			});

			dojo.byId("poGridContainer").style.height=window.screen.height*0.5+"px";
			this.showGrid(store, layout, TestPageContext.listPlugins,"poGridContainer",gridId);

			dojo.publish("PageLoadComplete",[ "test Page Load Completed" ]);
		}
		//得到pageBar控件
		//automatic called by customQueryReadStore.js removed now 
		,getPageBar : function(type) {
			return this.po_pageBar;
		}
	});

	return tms.china.shipmentorder.searchPurchaserOrder;
});