define("tms/china/widget/customizeGridWidget/customizeGridWidget",[ "dojo", "dijit", "tms/china/widget/_Widget", "dijit/_Templated","text!tms/china/widget/customizeGridWidget/templates/customizeGridWidget.html" ], function(dojo, dijit) {
	/**
	 * 这是针对定制相应的grid的客户化组件，主要实现对grid的结构的调整功能，具体包括显示，隐藏列等
	 * 20111110 陆锋平：新建
	 */
	//加入该widget所需要的dojo widget
	dojo.require("dijit.form.Button");
	dojo.require("dijit.Dialog");
	dojo.require("dojo.data.ItemFileWriteStore");
	dojo.require("dojox.grid.EnhancedGrid");
	dojo.require("dijit.form.RadioButton");
	dojo.require("dojox.form.CheckedMultiSelect");
	dojo.require("dojox.grid.enhanced.plugins.IndirectSelection");	
	dojo.declare("tms.china.widget.customizeGridWidget.customizeGridWidget", [ tms.china.widget._Widget,dijit._Templated ], {
		
		// Path to the template
		templateString : dojo.cache("tms.china.widget.customizeGridWidget","templates/customizeGridWidget.html"),
		
		// Set this to true if your widget contains other widgets
		widgetsInTemplate : true,
		//构造参数
		name:null,
		//对应的表
		grid:null,
		store:null,
		itemCount:null,
		selectedItem:null,
		selectedItemIndex:null,
		groupField:null,
		auth:false,
		//国际化
		_ui_string_column:null,
		_ui_string_displayorhide:null,
		_ui_string_preferredpagesize:null,
		_ui_string_moveup:null,
		_ui_string_movedown:null,
		_ui_string_defaults:null,
		_ui_string_copy:null,
		_ui_string_cancel:null,
		_ui_string_save:null,
		_ui_string_customizetable:null,
		_ui_string_to:null,
		_ui_string_copytabletitle:null,
		_ui_string_allusers:null,
		_ui_string_users:null,
		_ui_string_all:null,
		constructor : function(rootNode) {
		 	this.name=rootNode.name;
		 	this.grid=dijit.byId(rootNode.gridId);	 
			this.inherited(arguments);
		},
		
		postMixInProperties : function() {
			this.inherited(arguments);		
			this._ui_string_column=Common.I18N.resourceBundle.WEBEAN_UI_COLUMN_P_displayName;
			this._ui_string_displayorhide=Common.I18N.resourceBundle.WEBEAN_UI_COLUMN_P_visibleDisplay;
			this._ui_string_preferredpagesize=Common.I18N.resourceBundle.WEBEAN_UI_ABSTRACTTABLE_P_preferredPageSize;
			this._ui_string_moveup=Common.I18N.resourceBundle.CMD_moveUp;
			this._ui_string_movedown=Common.I18N.resourceBundle.CMD_moveDown;
			this._ui_string_defaults=Common.I18N.resourceBundle.CMD_O_use_defaults ;
			this._ui_string_copy=Common.I18N.resourceBundle.CMD_copy;
			this._ui_string_cancel=Common.I18N.resourceBundle.CMD_cancel;
			this._ui_string_save=Common.I18N.resourceBundle.CMD_save;
			this._ui_string_customizetable=Common.I18N.resourceBundle.TABLE_customizeTable;
			this._ui_string_to=Common.I18N.resourceBundle.WEB_BASE_WEBEAN_TABLECOPY_to;
			this._ui_string_copytabletitle=Common.I18N.resourceBundle.WEB_BASE_WEBEAN_TABLECOPY_title;
			this._ui_string_allusers=Common.I18N.resourceBundle.WEB_BASE_WEBEAN_TABLECOPY_allUsers;
			this._ui_string_users=Common.I18N.resourceBundle.WEB_BASE_WEBEAN_TABLECOPY_users;
			this._ui_string_all=Common.I18N.resourceBundle.NB_TABLE_ALL;
		},
		/**
		 * 初始化函数,主要用于获取当前表layout信息
		 */
		init:function(){
			//获取当前的layout信息
			this._getStore(this.name,null,false);
			if(this.name=="dedicatedfleet"){
				this.groupField=this.getGroupField();
				//console.log(this.groupField);
			}
		},
		
		postCreate : function() {
			this.inherited(arguments);
			var self=this;
			if(this.name=="itineraryOrder") this._pagesize.style.display="none";
			
			dojo.connect(this.configGrid,"onSelected",function(index){
				//console.log(index);
			    if(index==0) self.up.set("disabled",true);
			    else  self.up.set("disabled",false);
			    
			    if(index==self.itemCount-1) self.down.set("disabled",true);
			    else  self.down.set("disabled",false);
			    
				self.displayOrHide.set("disabled",false);
				self.selectedItem=self.configGrid.selection.getSelected()[0];
				self.selectedItemIndex=index;
			});
			dojo.connect(this.configGrid,"onCellDblClick",function(e){
				if(e.cell.field=="ally"){
					self._hideOrDisplay();
				}
			});
			
		},
		/**
		 * 显示组件
		 */
		show:function(){
			this.init();
			if(this.auth){
				this.copytouser.set("style",{display:"inline"});
			}else this.copytouser.set("style",{display:"none"});
			
			this.dialog.show();
		},
		/**
		 * 和后台交互的函数，上传参数
		 * name:grid的类型
		 * cmd:具体的操作
		 * json:附件的需要上传的参数
		 */
		_postPrams:function(name,cmd,json){
			//Util.showStandBy();
			var self=this;
			if(cmd==null ||cmd==undefined)//获取layout
			return dojo.xhrPost({
				url : window.actionPath + "/tableConfig/searchConfigColumns",
				content:{name:name,useDefaultFlag:json},
				handleAs : "json",
				sync:true,
				/*error:function(){
					console.debug("post error");
				},*/
				error:Util.ajaxErrorHandler,
				preventCache : true
			});
			//保存
			else if(cmd=="SAVE"){
				return dojo.xhrPost({
					url : window.actionPath + "/tableConfig/saveConfigColumns",
					content:{name:name,configedColumns:dojo.toJson(json)},
					handleAs : "json",
					sync:true,
					/*error:function(){
						console.debug("post error");
					},*/
					error:Util.ajaxErrorHandler,
					preventCache : true
				});
			}//获得user
			else if(cmd=="USER"){
				var userStore=null;
				dojo.xhrPost({
					url : window.actionPath + "/tableConfig/searchUsers",
					handleAs : "json",
					sync:true,
					load:function(json){
					   userStore=json ;
					},
					/*error:function(){
						console.debug("post error");
					},*/
					error:Util.ajaxErrorHandler,
					preventCache : true
				});
				return userStore;
			}
			//复制给组织内的所有用户
			else if(cmd=="COPYTOALL"){
				dojo.xhrPost({
					url : window.actionPath + "/tableConfig/copyAllUsers",
					content:{name:name,configedColumns:dojo.toJson(json)},
					handleAs : "json",
					sync:true,
				
					load:function(json){
						if(json.json=="success"){
							self._cancelCopyDialog();
						}else alert("error");
					},
					/*error:function(){
					console.debug("post error");
				},*/
				error:Util.ajaxErrorHandler,
					preventCache : true
				});
			}
		},
		/**
		 * 获取layout处理返回值
		 */
		_layoutCallback:function(json){
			//Util.hideStandBy();
		//	console.log(json);
			if(json==null) return ;
			
			var store = new dojo.data.ItemFileWriteStore( {
				data : json
			});	
			this.store=store;
			var self=this;
			this.configGrid.store=this.store;
			
			this.store.fetch({onBegin:function(total){
				   self.itemCount=total; 
			   }});
			this.pageSize.set("value",json.preferredPageSize);	
			this.auth=json.auth;
		},
		/**
		 *save处理返回值
		 */
		_saveCallback:function(json){
			//Util.hideStandBy();
			//console.log(json);
			if(json.json=="success"){
				this.cancel();
				Common.currentpage.updateUI(this.name);
			}
		},
		/**
		 * 取消
		 */
		cancel:function(){
			this.dialog.hide();
		},
		/**
		 * 上传参数
		 */
		_submit:function(){
		
			
			var deferred=this._postPrams(this.name,"SAVE",this.getJson());
			deferred.addCallback(dojo.hitch(this,this._saveCallback));
		},
		/**
		 * 获取表格的列信息
		 */
		_getStore:function(name,cmd,json){
			var deferred=this._postPrams(name,cmd,json);
			deferred.addCallback(dojo.hitch(this,this._layoutCallback));
		},
		/**
		 * 上移
		 */
		_up:function(){
			if(this.selectedItem==null) {
				alert("请选择列");
				return;
			}
			var pre=this.selectedItemIndex-1;
			if(pre<0)return ;
			var preItem=this.configGrid.getItem(pre);
			this._swapItems(preItem,this.selectedItem);
			this.configGrid.selection.deselectAll();
			this.configGrid.selection.addToSelection(pre);
			this.configGrid.scrollToRow(pre);
			this.configGrid.update();
			console.log(this.configGrid.selection.getSelected());	
		},
		/**
		 * 下移
		 */
		_down:function(){
			if(this.selectedItem==null) {
				alert("请选择列");
				return;
			}
			var next=this.selectedItemIndex+1;
			if(next>=this.itemCount)return ;
			var nextItem=this.configGrid.getItem(next);
			this._swapItems(nextItem,this.selectedItem);
			this.configGrid.selection.deselectAll();
			this.configGrid.selection.addToSelection(next);
			this.configGrid.scrollToRow(next);
			this.configGrid.update();	
			//console.log(this.store);
		},
		/**
		 * 更改显示或者隐藏
		 */
		_hideOrDisplay:function(){
			if(this.selectedItem==null) {
				alert("请选择列");
				return;
			}
			if(this.name=="dedicatedfleet"){
				if(this.isGroupColumn(this.selectedItem.field[0])){
				//	console.log(this.selectedItem.field[0]);
				//	alert("hhh");
					new tms.china.widget.infoMsg.InfoMsg(
							{	message:"分组字段不能隐藏",
							    title:Common.I18N.resourceBundle.WEBEAN_PAGE_errorOccurred,
							    type:Info_constant.FAIL   //可选参数type有三个值，Info_constant.SUCCESS，Info_constant.FAIL，Info_constant.CONFIRM ,对应的图片不一样
							}).show();
					return ;
				}
			}
			if (this.selectedItem.display[0]){
				this.store.setValue(this.selectedItem,"display",false);
				this.store.setValue(this.selectedItem,"ally",Common.I18N.resourceBundle.WEBEAN_UI_ABSTRACTTABLE_hide);
			}else {
				this.store.setValue(this.selectedItem,"display",true);
				this.store.setValue(this.selectedItem,"ally",Common.I18N.resourceBundle.WEBEAN_UI_ABSTRACTTABLE_show);
			}
			//console.log(this.store);
			this.configGrid.update();
		},
		/**
		 * 交换item
		 */
		_swapItems:function(item1,item2){
			var temp;
			var store=this.store;
			if(store.isItem(item1)&& store.isItem(item2)){
				temp=store.getValue(item1,"column");
				store.setValue(item1,"column",store.getValue(item2,"column"));
				store.setValue(item2,"column",temp);
				temp=store.getValue(item1,"display");
				store.setValue(item1,"display",store.getValue(item2,"display"));
				store.setValue(item2,"display",temp);	
				temp=store.getValue(item1,"field");
				store.setValue(item1,"field",store.getValue(item2,"field"));
				store.setValue(item2,"field",temp);
				temp=store.getValue(item1,"ally");
				store.setValue(item1,"ally",store.getValue(item2,"ally"));
				store.setValue(item2,"ally",temp);
			}else{
				alert("error");
			}
		},
		/**
		 * 获得默认layout
		 */
		_getDefaultLayout:function(){
			this._getStore(this.name, null, true);
		//	console.log(this.store);
			this.configGrid.setStore(this.store);
		},
		/**
		 * 复制当前layout给其他用户
		 */
		_showCopyDialog:function(){
			
			if(this.users.store==null ||this.users.store==undefined){
			    var data=this.getUserStore();
				var store = new dojo.data.ItemFileWriteStore({
					data : data
				});	
				console.log(store);
				this.users.store=store;
			}
			this.copyDialog.show();
		},
		/**
		 * 隐藏copyDialog
		 */
		_cancelCopyDialog:function(){
			this.copyDialog.hide();
		},
		/**
		 * 获得users
		 */
		getUserStore:function(){
			return this._postPrams(null, "USER", null);
		},
		/**
		 * 选择所有用户
		 */
		_selectAll:function(value){
			//console.log(value);
			if(value){
				this.users.setDisabled(true);
			}else{
				this.users.setDisabled(false);
			}
		},
		/**
		 * 确认copy给用户
		 */
		_confirm:function(){
			var flag=this.all.get("checked");
			if(flag){//全部用户
				this._postPrams(this.name,"COPYTOALL",this.getJson());
			}else{//指定用户
				var selectedItem=this.users.get("value");
				console.log(selectedItem);
				if(selectedItem.length==0) {//未指定用户
					this._cancelCopyDialog();
					return ;
				}else{
					var json={
						items:[]				
					};
					for(var i=0;i<selectedItem.length;i++){
						json.items[i]={};
						json.items[i]["userKey"]=selectedItem[i].split(":")[0];
						json.items[i]["userDisplayName"]=selectedItem[i].split(":")[1];
						json.items[i]["selectedFlag"]=true;
					}
					var self=this;
					dojo.xhrPost({
						url : window.actionPath + "/tableConfig/copyUsers",
						content:{name:this.name,selectedUsers:dojo.toJson(json),configedColumns:dojo.toJson(self.getJson())},
						handleAs : "json",
						sync:true,
						load:function(json){
							if(json.json=="success"){
								self._cancelCopyDialog();
							}else alert("error");
							
						},
						error:Util.ajaxErrorHandler,
						/*error:function(){
							console.debug("post error");
						},*/
						preventCache : true
					});
					
				}
			
			}
		},
		/**
		 * 获得上传的参数
		 */
		getJson:function(){
			var json={
					preferredPageSize:"10",
					items:[]				
				};
				json.preferredPageSize=this.pageSize.get("value");
				this.store.fetch({onComplete:function(items){
					for(var i=0;i<items.length;i++){
						json.items[i]={};
						json.items[i]["field"]=items[i].field[0];
						json.items[i]["display"]=items[i].display[0];
					}
				   }});
			return json;
		},
		/**
		 * 获得分组列表
		 */
		getGroupField:function(){
			var groupColumn=null;
			dojo.xhrGet({
				url : window.actionPath + "/search/getDedicatedFleetGroupFields",
				handleAs : "json",
				sync:true,
				error:Util.ajaxErrorHandler,
				load:function(json){
				console.log(json);
					groupColumn=json.items;
				},
				/*error:function(){
					console.debug("post error");
				},*/
				preventCache : true
			});
			return groupColumn;
		},
		/**
		 * 判断当前的选中列是否在分组列表中
		 */
		isGroupColumn:function(column){
			if(!this.groupField) return false;
			var flag=false;
			for(var i=0;i<this.groupField.length;i++){
				if(column==this.groupField[i].value) {
					flag=true;
					break;
				}
			}
			return flag;
		}
	});
	return tms.china.widget.customizeGridWidget.customizeGridWidget;
});
