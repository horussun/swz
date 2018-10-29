define("tms/china/js/module/CustomForestTreeModel", [ "dojo" ], function(dojo) {
	
	dojo.require("dijit.tree.ForestStoreModel");
	dojo.declare("tms.china.js.module.CustomForestTreeModel", dijit.tree.ForestStoreModel, {
		constructor : function() {
		// console.log("CustomForestTreeModel......");
		 this.inherited(arguments);
		}
		
	});
	return tms.china.js.module.CustomForestTreeModel;
});