define("tms/china/js/module/_hasDialog", [ "dojo"], function(dojo) {
	dojo.declare("tms.china.js.module._hasDialog",null, {
		constructor : function() {
			this._dialog=dijit.byId("mainDialog");
			this._standby=dijit.byId("dialogStandBy");
		},
		
		_dialog : null,
		_standby : null,
		
		showDialog : function(arg){
			if(arg.title)this._dialog.set("title",arg.title);
			if(arg.href)this._dialog.set("href",arg.href);
			if(arg.content)this._dialog.set("content",arg.content);
			this._dialog.show();
		},
		
		closeDialog : function(){
			this._dialog.hide();
		},
		
		showWaiting : function(){
			this._standby.show();
		},
		
		hideWaiting : function(){
			this._standby.hide();
		}
	});
	return tms.china.js.module._hasDialog;
});