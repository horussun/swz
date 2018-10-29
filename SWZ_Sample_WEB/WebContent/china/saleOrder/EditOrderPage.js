define("tms/china/saleOrder/EditOrderPage", [ "dojo", "tms/china/widget/_Page", "tms/china/js/module/_hasDialog", "tms/china/js/module/_selection" ], function(dojo, Page) {
	/**
	 * 编辑销售订单页面所使用的功能
	 */
	dojo.declare("tms.china.saleOrder.EditOrderPage", [ tms.china.widget._Page, tms.china.js.module._hasDialog, tms.china.js.module._selection ], {
		constructor : function(json_param) {
			// 在这里可以对传进来的json_param进行一些操作，以来初始化
		if (json_param) {
			if (json_param.cmd == "edit") {
				this.name = "编辑销售订单";
			} else if (json_param.cmd == "add") {
				this.name = "新建销售订单";
			}
		}
		this.f = false;
	},
	// 用来判断编辑时，下拉列表框注值时第一次不要清空买家地址列表框的value
		f : null,
		/**
		 * Add required dojo classes
		 */
	addRequired : function() {
			// 请求所需的控件
		dojo.require("dijit.form.Form");
		dojo.require("dijit.form.ValidationTextBox");
		dojo.require("dijit.form.Textarea");
		dojo.require("dijit.form.Select");
		dojo.require("dijit.form.Button");
		dojo.require("dijit.form.ComboButton");
		dojo.require("dijit.form.FilteringSelect");
		dojo.require("tms.china.widget.infoMsg.InfoMsg");
		dojo.require("dijit.form.DateTextBox");
		dojo.require("dijit.form.TimeTextBox");
		dojo.require("dijit.form.DropDownButton");
		dojo.require("dijit.form.SimpleTextarea");
		dojo.require("dijit.TooltipDialog");
		dojo.require("dojox.grid.EnhancedGrid");

	},

	initPage : function() {

		// 1. Call super class's same method to init
		// common tasks
		this.inherited(arguments);
		// 2. Init components
		var flag = false;
		if (Nav.getRestoreData()) {
			flag = true;
		}
		this.form = dijit.byId("order_form");
		// 初始化下拉store
		this.setStore("/dropdownstore/getPurchaserOrgStore", purchaserStore, dijit.byId("purchaser"), dojo.byId("Hidden_buyerId").value); // 买家
		this.setStore("/dropdownstore/getAddressStore", pAddStore, dijit.byId("pAdd"), dojo.byId("Hidden_buyerAddressId").value);// 买家地址
		// 只显示买家org
//		dijit.byId('purchaser').query.type = "0";// 过滤为客户类型"0"的组织
		// 截取下单时间（数据库中）的日期部分，显示在日期控件中
		var dt = dojo.byId("Hidden_saleOrderPlacedTime").value;
		if (dt) {
			var d = dt.substring(0, 10);
			var t = "T"+dt.substring(11,19);
			dijit.byId("SalesOrder_orderPlacedTime:date").set('value', d);
			dijit.byId("SalesOrder_orderPlacedTime:time").set('value', t);
		}

		dojo.publish("PageLoadComplete", [ "test Page Load Completed" ]);
	},
	// 初始化store的data
	setStore : function(url, store, gd, val) {
		var args = {
			url : window.actionPath + url,
			handleAs : "json",
			content : null,
			load : function(data) {
				Util.hideStandBy();
				data = data.json;
				store.data = data;
				if (val) {
					gd.set('value', val);
				}
			},
			error : Util.ajaxErrorHandler
		};
		dojo.xhrPost(args);
	},
	/**
	* 保存销售订单
	*/
	save : function() {
		Util.showStandBy();
		var args = {
			url : window.actionPath + "/saleOrder/save",
			form : dojo.byId("order_form"),
			handleAs : "json",
			content : null,
			load : function(data) {
				Util.hideStandBy();
				data = data.json;
				if (data.success == "false") {
					Common.currentpage.showErrorDialog(data.description);
				} else {
					Nav.closePage();
			    }
		    },
		    error : Util.ajaxErrorHandler
		};
		dojo.xhrPost(args);

	},
	
	// 采购订单级联
	changePurchaser : function() {
		console.log("purchaser change!");
		dijit.byId('pAdd').query.oid = dijit.byId('purchaser').get('value') || "*";
		// flag:0,表示第一次，所以，编辑时第一次changePurchaser时不会重置pAdd中的值
		if (this.f) {
			dijit.byId('pAdd').set('value', '');
			dijit.byId("SalesOrder_contactinfo").setValue("");
		}

	this.f = true;

	},

	//添加联系方式
	changePAdd : function() {
		console.log("pADD change!");
		console.log(dijit.byId('pAdd').get('value'));
		// 注入联系方式
		dijit.byId("SalesOrder_contactinfo").setValue((dijit.byId('pAdd').item || {id : ''}).cinfo);
		if (!dijit.byId('purchaser').get('value')) {
			dijit.byId('purchaser').set('value', (dijit.byId('pAdd').item || {oid : ''}).oid);
		}

	},
	//取消按钮
	cancel : function() {
		// 关闭当前页面
		Nav.closePage();

	},
	/**
	 * 弹出产品查询界面
	 */
	searchProduct : function() {
		var title = "产品查询";
		Nav.showDialog("/saleOrder/searchProduct", "tms.china.saleOrder.Search_product", null, title);

	},
	/**
	 * 在产品搜索弹出框中的选中确认事件
	 */
	selected : function() {
		Util.showStandBy();
		var grid = dijit.byId("gridID");
		if (this.isSelected(grid)) {

			dojo.byId("Product_productNumber").value = this.getSelectedID("P_NUMBER", grid);
			dojo.byId("Product_productTopic").value = this.getSelectedID("P_TOPIC", grid);
			dojo.byId("Product_productName").value = this.getSelectedID("P_NAME", grid);
			dojo.byId("Product_productDescription").value = this.getSelectedID("P_DESCRIPTION", grid);
			dojo.byId("Product_productStyle").value = this.getSelectedID("P_STYLE", grid);
			dojo.byId("Product_productType").value = this.getSelectedID("P_TYPE", grid);
			dojo.byId("Product_productColor").value = this.getSelectedID("P_COLOR", grid);
			dojo.byId("Product_productSexType").value = this.getSelectedID("P_SEXTYPE", grid);
			dojo.byId("Product_productMaterial").value = this.getSelectedID("P_PRODUCTMATERIAL", grid);
			dojo.byId("Product_productCode").value = this.getSelectedID("P_CODE", grid);
			//dojo.byId("Product_productUomName").value = this.getSelectedID("U_NAME", grid);
			//dojo.byId("Product_dateOnSale").value = this.getSelectedID("P_DATEONSALE", grid);
			dojo.byId("Product_periodOfSale").value = this.getSelectedID("P_PERIODOFSALE", grid);
			dojo.byId("SalesOrder_productId").value = this.getSelectedID("OID", grid);
			dojo.byId("SalesOrder_uomId").value = this.getSelectedID("P_UOMID", grid);
		}
		Nav.closeDialog();
		Util.hideStandBy();
	}

	});

	return tms.china.saleOrder.EditOrderPage;
});