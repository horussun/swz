define("tms/china/saleOrder/Search_product", [ "dojo", "tms/china/widget/_Page","tms/china/js/module/_grid","tms/china/js/module/_selection" ], 
	function(dojo, Page) {
	/**
	 * 搜索产品弹出窗口所使用的功能
	 */
	dojo.declare("tms.china.saleOrder.Search_product", [ tms.china.widget._Page,tms.china.js.module._grid,tms.china.js.module._selection], {

		constructor : function(json_param) {
			// 在这里可以对传进来的json_param进行一些操作，以来初始化
			this.name = "产品查询";
			if(json_param){
				this.createSearchNameAndKey = json_param.searchNameAndKey;
				this.createSearchType = json_param.searchType;
			}
		},

		gridId: null,
		name:null,
		searchPart:null,
		searchProduct_pageBar:null,

		/**
		 * Add required dojo classes
		 */
		addRequired : function() {
			//请求所需的控件

			dojo.require("tms.china.widget.pageBar.pageBar");
			dojo.require("dojo.data.ItemFileReadStore");
			dojo.require("dijit.form.Select");
			dojo.require("dijit.form.Button");
			dojo.require("dijit.form.ComboButton");
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
					{type:"PRODUCT",gridId:"gridID",flag:false}).placeAt("searchpart");
			gridId="gridID";
            this.searchProduct_pageBar=new tms.china.widget.pageBar.pageBar({name:"pageBarName",grid:gridId}).placeAt("searchProduct_pageBar");
			var layout=this.getLayout("PRODUCT");
			var store=new tms.china.js.module.CustomQueryReadStore({
				name:gridId,
				requestMethod:"post"
			});
			
			dojo.byId("searchProductGridContainer").style.height=window.screen.height*0.5+"px";
			this.showGrid(store, layout, TestPageContext.listPlugins,"searchProductGridContainer",gridId);
			dojo.publish("PageLoadComplete",[ "test Page Load Completed" ]);
		},
		//取得分页条
		getPageBar:function(type){
			return this.searchProduct_pageBar;
		}
	});

	return tms.china.saleOrder.Search_product;
});