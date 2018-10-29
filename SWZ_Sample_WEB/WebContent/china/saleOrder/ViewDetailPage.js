define("tms/china/saleOrder/ViewDetailPage", [ "dojo", "tms/china/widget/_Page", "tms/china/js/module/_selection" ], function(dojo, Page) {

	/**
	 * 查看销售订单详细页面所用的功能
	 */
	dojo.declare("tms.china.saleOrder.ViewDetailPage", [ tms.china.widget._Page, tms.china.js.module._selection ], {

		constructor : function(json_param) {
			this.name = "查看销售订单详细";
		},

		purchaser_pageBar : null,
		gridId : null,
		/**
		 * Add required dojo classes
		 */
		addRequired : function() {
			// 请求所需的控件
		dojo.require("dijit.form.Button");
		dojo.require("dijit.form.SimpleTextarea");
	},
	initPage : function() {

		// 1. Call super class's same method to init
		// common tasks
		this.inherited(arguments);
		var flag = false;
		if (Nav.getRestoreData()) {
			flag = true;
		}

		gridId = "gridID";
		this.purchaser_pageBar = new tms.china.widget.pageBar.pageBar( {
			name : "pageBarName",
			grid : gridId
		});
		var layout = this.getLayout("PURCHASEORDER");
		var store = new tms.china.js.module.CustomQueryReadStore( {
			name : gridId,
			requestMethod : "post"
		});
		dojo.byId("purchaserGridContainer").style.height = "120px";
		this.showGrid(store, layout, TestPageContext.listPlugins, "purchaserGridContainer", gridId);

		var grid = dijit.byId(gridId);
		grid.store.url = window.actionPath + "/search/search";
		var json = {};
		json["searchType"] = "PURCHASEORDER";
		json["soId"] = dojo.byId("soId").value;
		json["name"] = gridId;
		// 平台传输模块也用到了这个，所以得判断下到底要不要进行数据过滤
		if (dojo.byId("userId") == null) {
			console.debug(window.userId);
			json["buyId"] = window.userId;
		}
		var query = {
			json : dojo.toJson(json)
		};
		grid.setQuery(query);

		// double click cell event
		grid._cellEdit = this._cellEdit;
		
		var cnt = dojo.byId("detaildiv");
		//第三个参数为何可以使任意值
		dojo.connect(cnt,"mouseover",this,this.showDetail);
		dojo.connect(cnt,"mouseout",this,this.hiddenDetail);
		//以下事件绑定是ff的，对IE不兼容
		//cnt.addEventListener('mouseover',showDetail, false);
		//cnt.addEventListener('mouseout',hiddenDetail, false);
		dojo.publish("PageLoadComplete", [ "Orders Page Load Completed" ]);
	},
	//显示产品描述
	showDetail : function() {
		dijit.showTooltip(dojo.byId("description").value, dojo.byId("detaildiv"));
	},
	//隐藏产品描述
	hiddenDetail : function () {
		dijit.hideTooltip(dojo.byId("detaildiv"));
	},
	// automatic called by customQueryReadStore.js
	//取得分页栏
		getPageBar : function(type) {
			if (type == gridId)
				return this.purchaser_pageBar;
			else
				return null;
		},
//双击显示订单详细页面
		_cellEdit : function(e) {
			var oid = e.grid.getItem(e.rowIndex).i.OID;
			// 平台传输数据模块，关闭弹出的显示销售订单信息的dialog
		Nav.closeDialog();
		Nav.go(Nav.PURCHASER_ORDER_VIEWDTAIL, 'tms.china.purchaserorder.ViewDetailPage', {
			pid : oid
		});
	}
	});

	return tms.china.saleOrder.ViewDetailPage;
});
