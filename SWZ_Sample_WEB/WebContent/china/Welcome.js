define("tms/china/Welcome", [ "dojo", "tms/china/widget/_Page" ], function(dojo, Page) {
	/**
	 * 欢迎页面的javascript代码
	 * This is javascript for welcome page
	 */
	dojo.declare("tms.china.Welcome", [ tms.china.widget._Page ], {
		// Place comma-separated class attributes here.
		// Note, instance attributes
		// should be initialized in the constructor.
		// Variables initialized here
		// will be treated as 'static' class variables.

		// Constructor function. Called when instance of
		// this class is created
		constructor : function(json_param) {
			// 在这里可以对传进来的json_param进行一些操作，以来初始化
			// 例如，对于编辑订单，在页面对象构造的时候需要传进来所选择的订单号。
			//

		},	

		/**
		 * Add required dojo classes
		 */
		addRequired : function() {
			// 请求所需的控件
			dojo.require("dijit.form.Button");
			dojo.require("dijit.form.TextBox");
			dojo.require("dijit.layout.ContentPane");
	},

	name: "欢迎页面",
	intervalId:null,
	
	initPage : function() {

		// 1. Call super class's same method to init
		// common tasks
		this.inherited(arguments);
		//this.setOrderCounts();//获取记录条数
		//数据在1分钟内的更新
		/*var soc = this.setOrderCounts;
		setInterval(function(){
			soc();
		},60000);
		*/
		// 2. Init components
		//虚假数据自更新
//		var salecount=4300;
//		var purchcount=11012;
//		var shipcount=24020;

		this.setOrderCounts();
		var soc = this.setOrderCounts;
		intervalId=setInterval(function(){
			if(dojo.byId("ERPcontent1")!=null){
//				dojo.byId("ERPcontent1").innerHTML = salecount;
//				dojo.byId("ERPcontent2").innerHTML = salecount+10;
//				dojo.byId("ECcontent1").innerHTML = purchcount;
//				dojo.byId("ECcontent2").innerHTML = purchcount+20;
//				dojo.byId("Logcontent1").innerHTML = shipcount;
//				dojo.byId("Logcontent2").innerHTML = shipcount+30;
//				salecount++;
//				purchcount++;
//				shipcount++;
				soc();
			}else{
				clearInterval(intervalId);
			}
			
		},30000);
		Util.hideStandBy();
	}
	
	//获取后台记录条数
	,setOrderCounts:function(){
//		var args = {
//				url :window.actionPath + "/main/getOrderCounts",
//				handleAs : "json",
//				content : null,
//				load : function(data) {
//					Util.hideStandBy();
//					
//					if(data!=null&&dojo.byId("ERPcontent1")!=null){
//						data = data.json;
//						dojo.byId("ERPcontent1").innerHTML = data.sale;
//						dojo.byId("ERPcontent2").innerHTML = data.sale;
//						dojo.byId("ECcontent1").innerHTML = data.purchaser;
//						dojo.byId("ECcontent2").innerHTML = data.purchaser;
//						dojo.byId("Logcontent1").innerHTML = data.shipment;
//						dojo.byId("Logcontent2").innerHTML = data.shipment;
//					}
//					
//				},
//				error : Util.ajaxErrorHandler
//			};
//		dojo.xhrPost(args);
	}
	});

	return tms.china.Welcome;
});