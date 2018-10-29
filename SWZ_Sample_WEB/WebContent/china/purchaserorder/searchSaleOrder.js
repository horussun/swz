define("tms/china/purchaserorder/searchSaleOrder", [ "dojo", "tms/china/widget/_Page","tms/china/js/module/_selection" ], function(dojo, Page) {
	/**
	 * 销售订单查询弹出窗口所使用到的方法
	 */
	dojo.declare("tms.china.purchaserorder.searchSaleOrder", [ tms.china.widget._Page,tms.china.js.module._selection], {

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
		so_pageBar:null,
		sort:null,
		gridId:null,
		searchPart:null,
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
			dojo.require("tms.china.widget.searcher.Search");
		},
	
		initPage : function() {
	
			// 1. Call super class's same method to init
			// common tasks
			this.inherited(arguments);
	
			//搜索控件
			this.searchPart = new tms.china.widget.searcher.Search(
					{type:"SALEORDER2",gridId:"gridID",flag:false}).placeAt("searchpart");
			gridId="gridID";
			this.so_pageBar=new tms.china.widget.pageBar.pageBar({name:"soPageBarName",grid:gridId}).placeAt("so_pageBar");
			
			var layout=this.getLayout("SALEORDER");
			var store=new tms.china.js.module.CustomQueryReadStore({
				name:gridId,
				requestMethod:"post"
			});

			dojo.byId("soGridContainer").style.height=window.screen.height*0.5+"px";
			this.showGrid(store, layout, TestPageContext.listPlugins,"soGridContainer",gridId);
	
			dojo.publish("PageLoadComplete",[ "test Page Load Completed" ]);
		}
		
		//automatic called by customQueryReadStore.js removed now 
		,getPageBar : function(type) {
			return this.so_pageBar;
		}
		
			
	});

	return tms.china.purchaserorder.searchSaleOrder;
});