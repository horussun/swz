define("tms/china/purchaserorder/ViewDetailPage", ["dojo", "tms/china/widget/_Page","tms/china/js/module/_selection"], function(dojo, Page) {
    /**
     * 采购订单详细页面使用的方法
     */
	dojo.declare("tms.china.purchaserorder.ViewDetailPage", [tms.china.widget._Page,tms.china.js.module._selection], {
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
            this.name = "查看采购订单详细";
        },
        gridId:null,
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
            
            var flag=false;
			if(Nav.getRestoreData()){
				flag=true;
			}
			
			
			function showDetail() {
				dijit.showTooltip(dojo.byId("description").value, dojo.byId("detaildiv"));
			}
			function hiddenDetail() {
				dijit.hideTooltip(dojo.byId("detaildiv"));
			}
			var cnt = dojo.byId("detaildiv");
			//第三个参数为何可以使任意值
			dojo.connect(cnt,"mouseover",this,this.showDetail);
			dojo.connect(cnt,"mouseout",this,this.hiddenDetail);
			
            dojo.publish("PageLoadComplete", ["Orders Page Load Completed"]);
        },
        //查看产品详细描述
        showDetail : function() {
    		dijit.showTooltip(dojo.byId("description").value, dojo.byId("detaildiv"));
    	},
    	//隐藏产品描述
    	hiddenDetail : function () {
    		dijit.hideTooltip(dojo.byId("detaildiv"));
    	}
      
    });
    return tms.china.purchaserorder.ViewDetailPage;
});
