define("tms/china/js/module/CustomDialog", [ "dojo" ], function(dojo) {
	
	dojo.require("dijit.Dialog");
	
	dojo.declare("tms.china.js.module.CustomDialog", dijit.Dialog, {
		_onKey:function(evt){
		// console.log("jjjjj");
		 //console.log(evt.charOrCode);
		 if(evt.charOrCode===dojo.keys.ENTER){
			 var savebutton=dojo.byId("billcelledit_save");
			 if(savebutton){
				 //console.log("ddd");
				 savebutton.click();
			 }
		 }
		 this.inherited(arguments);
	}		
	});
	return tms.china.js.module.CustomDialog;
});