define("tms/china/shipmentorder/orderEdit", [ "dojo", "tms/china/widget/_Page", "tms/china/js/module/_searchLocation", "tms/china/js/module/_hasDialog", "tms/china/js/module/_selection" ], 
	function(dojo, Page) {
	/**
	 * 运输订单编辑页面对应的javascript代码
	 * This is the javascript for 1. create order pages
	 */
	dojo.declare("tms.china.shipmentorder.orderEdit", [ tms.china.widget._Page, tms.china.js.module._searchLocation, 
	                                                    tms.china.js.module._hasDialog, tms.china.js.module._selection ], {
		constructor : function(json_param) {
			if (json_param) {
				if (json_param.cmd == "edit") {
					this.name = "编辑运输订单";
				} else if (json_param.cmd == "copy") {
					this.name = Common.I18N.resourceBundle.NB_ORDER_TITLE_copyOrder;
				} else if (json_param.cmd == "add") {
					this.name = "新建运输订单";
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
		dojo.require("dijit.form.Button");
		dojo.require("dijit.form.Textarea");
		dojo.require("dijit.form.Form");
		dojo.require("dijit.form.ValidationTextBox");
		dojo.require("dijit.form.DateTextBox");
		dojo.require("dijit.form.TimeTextBox");
		dojo.require("dijit.form.FilteringSelect");
		dojo.require("dijit.form.Select");
		dojo.require("dijit.form.DropDownButton");
		dojo.require("dijit.TooltipDialog");
		dojo.require("dojox.grid.EnhancedGrid");
		dojo.require("tms.china.widget.infoMsg.InfoMsg");
		dojo.require("dijit.form.CheckBox");
		dojo.require("tms.china.shipmentorder.searchPurchaserOrder");
		dojo.require("dojo.data.ItemFileReadStore");
	},
	
	initPage : function() {
		console.debug("AddPage.initPage");

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
		// 第三个参数是被注入的select，第四个参数是被注入的value
		this.setStore("/dropdownstore/getPurchaserOrgStore", carrierStore, dijit.byId("carrier"), dojo.byId("Hidden_carrierOrganizationId").value);
		// 只显示承运商org
		dijit.byId('carrier').query.type = "3";

		this.setStore("/dropdownstore/getAddressStore", receiveAddStore, dijit.byId("receiveAdd"), dojo.byId("Hidden_receiveAddressId").value);
		this.setStore("/dropdownstore/getAddressStore", startAddStore, dijit.byId("startAdd"), dojo.byId("Hidden_startAddressId").value);
		this.setStore("/dropdownstore/getAddressStore", destinationAdd, dijit.byId("destinationAdd"), dojo.byId("Hidden_destinationAddressId").value);
		
		// 初始化编辑时对应的时间
		var delivery = dojo.byId("Hidden_deliveryDate").value;
		var arrival = dojo.byId("Hidden_arrivalDate").value;
		if (delivery) {
			delivery = delivery.substring(0, 10);
			dijit.byId("ShipmentOrder_deliveryDate").set('value', delivery);
		}
		if (arrival) {
			arrival = arrival.substring(0, 10);
			dijit.byId("ShipmentOrder_arrivalDate").set('value', arrival);
		}
		//按当前组织id获取组织地址（下拉）
		dijit.byId('receiveAdd').query.oid = dojo.byId("Hidden_customerId").value;
		dijit.byId('destinationAdd').query.oid = dojo.byId("Hidden_customerId").value;
		dijit.byId('startAdd').query.oid = window.userId;
		// 发布页面载入完成的事件
		dojo.publish("PageLoadComplete", [ "Add Order Page Load Completed" ]);
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
	
	// 保存订单
	saveOrder : function() {
		Util.showStandBy();

		var args = {
			url : window.actionPath + "/shipmentorder/save",
			form : dojo.byId("order_form"),
			handleAs : "json",
			content : null,
			load : function(data) {
				Util.hideStandBy();
				data = data.json;
				console.debug("zhou");
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
	
	//关闭编辑页面
	cancelOrder : function() {
		Nav.closePage();
	},
	//弹出"搜索销售订单"对话框
	searchSaleOrder : function() {
		var title = "销售订单查询";
		Nav.showDialog("/purchaserorder/promotSearchSO", "tms.china.purchaserorder.searchSaleOrder", null, title);

	},
	//选中相应的销售订单后，注入相关值
	selectSaleOrder : function() {
		var grid = dijit.byId("gridID");
		if (this.isSelected(grid)) {
			dojo.byId("ShipmentOrder_purchaserOrderId").value = this.getSelectedID("OID", grid);
			dijit.byId("salesOrder_orderNumber").setValue(this.getSelectedID("O_NUMBER", grid));
			dijit.byId("Product_productNumber").setValue(this.getSelectedID("P_NUMBER", grid));
			dijit.byId("Product_productName").setValue(this.getSelectedID("P_NAME", grid));
			dijit.byId("salesOrder_quantity").setValue(this.getSelectedID("O_QUANTITY", grid));
//			dijit.byId("Uom_uomName").setValue(this.getSelectedID("UOM_NAME", grid));
			dijit.byId("SalesBuyer").setValue(this.getSelectedID("ORGANIZATIONNAME",grid));
			//按当前买家组织id获取组织地址（下拉）
			dijit.byId('receiveAdd').query.oid = this.getSelectedID("ORGANIZATIONID",grid);
			dijit.byId('destinationAdd').query.oid = this.getSelectedID("ORGANIZATIONID",grid);

		}
		Nav.closeDialog();
	}
	
    });

	return tms.china.shipmentorder.orderEdit;
});