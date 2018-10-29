define("tms/china/MainContainer", [ "dojo", "tms/china/widget/_Page" ], function(dojo, _Page) {
	/**
	 * 主容器页面对应的javascript代码
	 *  This is javascript for oder page
	 */
	dojo.declare("tms.china.MainContainer", [ tms.china.widget._Page ], {
		// Place comma-separated class attributes here. Note, instance attributes 
		// should be initialized in the constructor. Variables initialized here
		// will be treated as 'static' class variables.

		// Constructor function. Called when instance of this class is created
		constructor : function() {
			
		}
	
	/**
	 * Add required dojo classes，应该在dojo.ready之前被执行。
	 */
	, addRequired : function(){

	}
	
	/**
	 * 初始化页面
	 */
	, initPage: function(){
		//1. Call super class's same method to init common tasks
		this.inherited(arguments);

		//3. Init display
			// search
		
		
	}
	
//	,
	//Uncomment above comma and add comma-separated functions here. Do not leave a 
	// trailing comma after last element.			
	});
	
	return tms.china.MainContainer;
});

