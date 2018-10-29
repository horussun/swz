define("tms/china/shipmentorder/ViewDetailPage", [ "dojo", "tms/china/widget/_Page" ], function(dojo, Page) {
	/**
	 * 运输订单详细页面对应的javascript代码
	 */
	dojo.declare("tms.china.shipmentorder.ViewDetailPage", [ tms.china.widget._Page ], {
		
		constructor : function(json_param) {
			this.name = "查看运单详细";
		},
		/**
		 * Add required dojo classes
		 */
		addRequired : function() {
		// 请求所需的控件
		dojo.require("dijit.form.Button");

	},
	initPage : function() {

		// 1. Call super class's same method to init
		// common tasks
		this.inherited(arguments);

	}
	});

	return tms.china.shipmentorder.ViewDetailPage;
});
