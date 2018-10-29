define("tms/china/js/module/_Bill",[ "dojo", "tms/china/widget/_Page","tms/china/js/module/_selection"],function(dojo, Page) {
	/**
	 * This is javascript for oder page
	 */		
	dojo.declare("tms.china.js.module._Bill",
							[ tms.china.widget._Page,tms.china.js.module._selection ],
							{
								
								//grid : null,
								type : null,
								
								init : function(controllerType){
									this.type=controllerType;
									
								},
								
								showErrorDialog : function(errorString) {
									new tms.china.widget.infoMsg.InfoMsg( {
										message : errorString,
										title : Util.getI18nValue("NB_BILL_TITLE_error"),
										type : Info_constant.FAIL
									}).show();
								},
								
								search : function() {
									var type=this.type;
									var kw = {
										url : window.actionPath+"/"+type+"/search",
										handleAs : "json",
										error:Util.ajaxErrorHandler,
										preventCache : true
									};
									var deferred = dojo.xhrPost(kw);
									deferred.addCallback(dojo.hitch(this,
											this._search));

								},

								_search : function(json) {
									// set store to grid
									var store = new dojo.data.ItemFileReadStore(
											{
												data : json
											});

									this.updateGrid(store);
									dojo.publish("PageLoadComplete",
											[ "Orders Page Load Completed" ]);
								},
								
								callSearch : function(){
									
									this.searchPart.search();
								},

								showPrompt : function(title,href){
									var dialog=dijit.byId("dialog");
									dialog.set("title",title);
									dialog.set("href",href);
									dialog.show();
								},
								
								promptCancel : function(){
									if(this.isSelected()){
										var href=window.actionPath+"/"+this.type+"/promptCancel"+"?oid="+this.getSelectedID("oid");
										var title=(this.type=="order")?Common.I18N.resourceBundle.NB_BILL_TITLE_cancelOrder:Common.I18N.resourceBundle.NB_BILL_TITLE_cancelShipment;
										this.showPrompt(title, href);
									}
								},
								
								confirmCancel : function(oid){
									dijit.byId("standby").show();
									this.cancel(oid);
								},
								
								cancel : function(oid) {
									if (!oid){
										oid = this.getSelectedID("oid");
									}
									var type=this.type;
										var kw = {
											url : window.actionPath+"/"+type+"/cancel",
											handleAs : "json",
											content : {
												oid:oid
											},
											error: Util.ajaxErrorHandler
										};
									var deferred=dojo.xhrPost(kw);
									deferred.addCallback(dojo.hitch(this,this._cancel));
								},
								
								_cancel : function(data){
									data=data.json;
									dijit.byId("standby").hide();
									dijit.byId("dialog").hide();
									if (data.success == "false") {
										this.showErrorDialog(data.description);
									}
									else{
										this.callSearch();
									}
								},
								
								promptEditStatus : function(){
									if(this.isSelected()){
										var href=window.actionPath+"/bill/promptEditStatus"+"?oid="+this.getSelectedID("oid");
										this.showPrompt(Common.I18N.resourceBundle.NB_BILL_TITLE_editStatus, href);
									}
								},
								
								confirmEditStatus : function(oid){
									dijit.byId("standby").show();
									this.editStatus(oid);
								},
								
								editStatus : function(oid){
									var kw = {
											url : window.actionPath+"/bill/editStatus",
											handleAs : "json",
											content : {
												oid:oid
											},
											error: Util.ajaxErrorHandler
										};
									var deferred=dojo.xhrPost(kw);
									deferred.addCallback(dojo.hitch(this,this._editStatus));
								},
								
								_editStatus : function(data){
									data=data.json;
									dijit.byId("standby").hide();
									dijit.byId("dialog").hide();
									if (data.success == "false") {
										this.showErrorDialog(data.description);
									}
									else{
										
										this.callSearch();
									}
								},
								
								cancelDialog : function(){
									dijit.byId("dialog").hide();
								},
								
								//add by zxd
								getRestoreData: function(){
									var restoreData = this.inherited(arguments);
									//储存选择状态
									var selectedInfos = {};
									
									var selectedInfo = {};
									
									var gridId = this.type;
									var selected = dijit.byId(gridId).get("selection").getSelected();
									var compareFunc = function(org1, org2){
										var org1RefId = dojo.getObject("oid", false, org1);
										var org2RefId = dojo.getObject("oid", false, org2);
										if(org1RefId === org2RefId){
											return true;
										}else{
											return false;
										}	
									};
									
									dojo.setObject("gridId", gridId, selectedInfo);
									dojo.setObject("selected", selected, selectedInfo);
									dojo.setObject("compareFunc", compareFunc, selectedInfo);
									
									selectedInfos[gridId] = selectedInfo;
									dojo.setObject("selectedInfos", selectedInfos, restoreData);
								
									return restoreData;
								}

							});

			return tms.china.js.module._Bill;
		});

