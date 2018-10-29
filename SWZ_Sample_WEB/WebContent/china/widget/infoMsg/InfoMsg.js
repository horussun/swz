define("tms/china/widget/infoMsg/InfoMsg",[ "dojo", "dijit", "tms/china/widget/_Widget", "dijit/_Templated","text!tms/china/widget/infoMsg/templates/InfoMsg.html" ], function(dojo, dijit) {
	/***
	 * 需要引入	dojo.require("tms.china.widget.infoMsg.InfoMsg");
	 
	   用法1：alert框，只有一个确认按钮
	   new tms.china.widget.infoMsg.InfoMsg(
						{
							message:"message",
						    title:"title",
						    type:Info_constant.SUCCESS   //可选参数type有三个值，Info_constant.SUCCESS，Info_constant.FAIL，Info_constant.CONFIRM ,对应的图片不一样
						}).show();
						
		用法2：confirm框，有两个按钮，可以绑定回调函数
		new tms.china.widget.infoMsg.InfoMsg(
						{
							message:"message",
						    title:"title",
						    type:Info_constant.SUCCESS
						},
						{
							caller:this, //caller是callback函数所在的scope
							callback:"callback" //回调函数名 
						}).show();

	 * 
	 */
	//加入该widget所需要的dojo widget
	dojo.require("dijit.form.Button");
	dojo.require("dijit.Dialog");
		
	dojo.declare("tms.china.widget.infoMsg.InfoMsg", [ tms.china.widget._Widget,dijit._Templated ], {
		
		// Path to the template
		templateString : dojo.cache("tms.china.widget.infoMsg","templates/InfoMsg.html"),
		
		// Set this to true if your widget contains other widgets
		widgetsInTemplate : true,
		
		//构造参数
		_message:null,
		_title:null,
		_button_ok:null,
		_button_cancel:null,
		_callback:null,
		_image_path:null,
		
		//构造函数
		constructor : function(para,callback) {
		//	console.debug(para);
		if(para.message)
				this._message = para.message;
			if(para.title)
				this._title = para.title;
			if(para.type)
				this._image_path = "../../china/images/"+para.type+".png";
			if(callback)
				this._callback = callback;
			
			
		},
		
		//显示dialog
		show:function(){
			//console.debug("show!");
			//console.debug(this.infoMsgDialog);

			this.infoMsgDialog.show();
		},

		//隐藏dialog
		cancel:function(){
			//隐藏dialog
			this.infoMsgDialog.destroyRecursive();
			//销毁子部件
			this.destroyRecursive();
		},
		
		button_ok_click:function(){
			this.cancel();
		},
		
		button_cancel_click:function(){
			this.cancel();
		},	
		
		postMixInProperties : function() {
			//找到widget下面的Search_i18n.js。初始化
			dojo.requireLocalization("tms.china.widget", "Search_i18n");
			//得到resource bundle
			this._strings = dojo.i18n.getLocalization("tms.china.widget", "Search_i18n", this.lang);
			this._button_cancel = this._strings['_ui_callback_cancel'];
			this._button_ok = this._strings['_ui_callback_ok'];
					
		},
		
		postCreate : function() {
		//	console.debug("postCreate!");
			this.infoMsgDialog.set("title",this._title);
			//有回调函数，则显示第二个按钮并且绑定事件
			if(this._callback){
				this.button_cancel.set("style","display:inline;");
				Util.bind("infoMsg_ok",this.button_ok,"onClick",this._callback.caller,this._callback.callback);
			}
		}
			
	});
	return tms.china.widget.infoMsg.InfoMsg;
});
