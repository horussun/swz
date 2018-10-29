define("tms/china/widget/search/SearchAddPage", [ "dojo", "tms/china/widget/_Page" ,"tms/china/js/module/_searchLocation" ],function(dojo, _Page) {
	/**
	 * This is javascript for new shipment search page
	 */		
	dojo.declare("tms.china.widget.search.SearchAddPage",
					[ tms.china.widget._Page,tms.china.js.module._searchLocation], {
						// Place comma-separated class attributes here. Note,
						// instance attributes
						// should be initialized in the constructor. Variables
						// initialized here
						// will be treated as 'static' class variables.

						// Constructor function. Called when instance of this
						// class is created
						searchType:"",
						searchID:"",
						
						constructor : function(json_param) {
						
							this.searchType = json_param.searchType;
							this.searchID = json_param.searchID;
							console.log(this.searchType);
							console.log(this);
							
							if(this.searchID){
								this.name=Util.getI18nValue('SEARCH_STR_edit_custom_search');
							}else {
								this.name=Util.getI18nValue('SEARCH_CMD_create_search');
							}
						}

						/**
						 * Add required dojo classes，应该在dojo.ready之前被执行。
						 */
						,
						addRequired : function() {
													
							dojo.require('dijit.form.TextBox');
							dojo.require('dijit.form.Button');
							dojo.require('dijit.form.FilteringSelect');
							dojo.require('dijit.form.ComboButton');	
							dojo.require("dijit.form.DateTextBox");
							dojo.require("dijit.form.CheckBox");
							dojo.require("dijit.form.RadioButton");
							dojo.require("dijit.form.Textarea");
							dojo.require("dijit.form.RadioButton");
							dojo.require("dojox.form.CheckedMultiSelect");	
							dojo.require("tms.china.widget.infoMsg.InfoMsg");
							dojo.require("dojo.data.ItemFileReadStore");
						},

						/**
						 * 初始化页面
						 */
						name : null
						,initPage : function() {
							
							// tasks
							this.inherited(arguments);
							//add require class87
							this.addRequired();
							
							console.debug("Search_ADD_PAGE init started!");
							console.debug(1);
							
							if(this.searchType=="dedicatedfleet"){
								this.enableSearch({inputId:'cs_origin1_text',selectId:'cs_origin1',buttonId : "cs_origin1_search",locType:'origin',searchType:Constant.SearchLocation.PART_SEARCH});
								this.enableSearch({inputId:'cs_destination1_text',selectId:'cs_destination1',buttonId : "cs_destination1_search",locType:'destination',searchType:Constant.SearchLocation.PART_SEARCH});
								this.enableSearch({inputId:'cs_origin2_text',selectId:'cs_origin2',buttonId : "cs_origin2_search",locType:'origin',searchType:Constant.SearchLocation.FULL_SEARCH});
							}else if(this.searchType=="availableshipment"){
								this.enableSearch({inputId:'cs_origin1_text',selectId:'cs_origin1',buttonId : "cs_origin1_search",locType:'origin',searchType:Constant.SearchLocation.PART_SEARCH});
								this.enableSearch({inputId:'cs_destination1_text',selectId:'cs_destination1',buttonId : "cs_destination1_search",locType:'destination',searchType:Constant.SearchLocation.PART_SEARCH});
								this.enableSearch({inputId:'cs_radius_text',selectId:'cs_radius',buttonId : "cs_radius_search",locType:'origin',searchType:Constant.SearchLocation.FULL_SEARCH});
							}else {
								this.enableSearch({inputId:'cs_origin1_text',selectId:'cs_origin1',buttonId : "cs_origin1_search",locType:'origin',searchType:Constant.SearchLocation.PART_SEARCH});
								this.enableSearch({inputId:'cs_destination1_text',selectId:'cs_destination1',buttonId : "cs_destination1_search",locType:'destination',searchType:Constant.SearchLocation.PART_SEARCH});
								this.enableSearch({inputId:'cs_origin2_text',selectId:'cs_origin2',buttonId : "cs_origin2_search",locType:'origin',searchType:Constant.SearchLocation.PART_SEARCH});
								this.enableSearch({inputId:'cs_destination2_text',selectId:'cs_destination2',buttonId : "cs_destination2_search",locType:'destination',searchType:Constant.SearchLocation.PART_SEARCH});
								
							}
							
							//alert memberlistReference 初始化
							this._initAlert_memberListSelect();
							
							var carrierSelect = dijit.byId("s_carrier");
							if (carrierSelect!=null && carrierSelect.get('value') !=  Common.I18N.resourceBundle.CMD_choose) {
								Org.carrierdetail("search");
							}
							var supplierSelect = dijit
									.byId("s_supplier");
							if (supplierSelect!=null && supplierSelect.get('value') !=  Common.I18N.resourceBundle.CMD_choose) {
								Org.supplierdetail("search");
							}
							var customerSelect = dijit
									.byId("s_customer");
							if (customerSelect!=null && customerSelect.get('value') !=  Common.I18N.resourceBundle.CMD_choose) {
								Org.customerdetail("search");
							}
							
							console.log(2);
							
							//编辑时初始化日期
							//dedicatedfleet模块特殊处理
							console.log(this.searchType);
							if(this.searchType=="dedicatedfleet"&&this.searchID){
								console.log(3);
								console.log(dijit.byId('dateKeySelect').get('displayedValue'));
								if(dijit.byId('dateKeySelect').get('displayedValue')==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_mostCurrentCycle
										||dijit.byId('dateKeySelect').get('displayedValue')==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_currentTruckPosition
										||dijit.byId('dateKeySelect').get('displayedValue')==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_previousCycle){
									console.log(4);
									dojo.byId('dateStrSpan').style.display="none";
									dojo.byId('dateEndSpan').style.display="none";
								}else if(this.searchID&&(dijit.byId('dateKeySelect').get('displayedValue')==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_historicalCycleDepartureDate
										||dijit.byId('dateKeySelect').get('value')==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_historicalCycleArrivalDate)){
									dojo.byId('dateStrSpan').style.display="";
									dojo.byId('dateEndSpan').style.display="none";
								}else {
									dojo.byId('dateStrSpan').style.display="";
									dojo.byId('dateEndSpan').style.display="";
								}
							}else {
								console.log("else");
								if(this.searchID&&dijit.byId('dateStr').get('value')=='4:'+Common.I18N.resourceBundle.BEAN_PROFILE_INVOICEPROFILELW_TRUEKEY_invoiceTimeIsFixed){
									dojo.byId("dateStrInputSpan").style.display="";
								}
							}
							
							//publish
							Util.hideStandBy();
							//dojo.publish("PageLoadComplete", ["Add order Page Load Completed"]);
						},
						_initAlert_memberListSelect:function(){
							if(!this.searchID&&this.searchType=="alert"){
								this.getMemberLis();
							}
						},
						showTime: function(){
							var dateEndSelect = dijit.byId("dateEnd");
							var startTimeSpan = dojo.byId("startTimeSpan");
							var toTimeSpan = dojo.byId("toTimeSpan");
							var toDateSpan = dojo.byId("toDateSpan");
							
							if(dateEndSelect.get("value")=="date"){
								Util.display(startTimeSpan);
								Util.display(toTimeSpan);
								Util.display(toDateSpan);
							}else{
								var toDateSelect = dijit.byId("toDate");
								toDateSelect.set("value",dateEndSelect.get("value"));
								Util.hide(startTimeSpan);
								Util.hide(toTimeSpan);
								Util.hide(toDateSpan);
							}
						},
						
						fixDate:function(){
							
							console.log(dijit.byId('dateStr').get('value'));
							if(dijit.byId('dateStr').get('value')!='4:'+Common.I18N.resourceBundle.BEAN_PROFILE_INVOICEPROFILELW_TRUEKEY_invoiceTimeIsFixed){
								dojo.byId("dateStrInputSpan").style.display="none";
								return;
							}else if(!this.searchID){
								dojo.byId("dateStrInputSpan").style.display="";
								var date=new Date() ;
								var day;
								var month ;
								if(date.getDate()<10)
									day="0"+date.getDate();
								else day=date.getDate();
								
								if(date.getMonth()+1<10) month="0"+(date.getMonth()+1);
								else month=date.month+1;
								
								format=date.getFullYear()+"-"+month+"-"+day;
								//console.log(format);
								dijit.byId('dateStrInput').set("value",format);
							}else if(this.searchID){
								dojo.byId("dateStrInputSpan").style.display="";
							}
							console.log('fixDate');
							
						},
						initDateContent:function(){
							console.log(this);
							console.log('initDateContent');
							
							var datawrapper = {};
							console.log(this.params.searchType);
							datawrapper["searchType"]=this.params.searchType;
							
							datawrapper["dateContent"]=dijit.byId('dateKeySelect').get('value');
							
							//为“选择"选项时
							if(datawrapper["dateContent"]==0){
								return;
								console.log(dijit.byId("dateKeySelect").get('displayedValue'));
							//dedicatedFleet模块有一些特殊处理
							}else if(datawrapper["searchType"]=="dedicatedfleet"){
				
								if(dijit.byId("dateKeySelect").get('displayedValue')== Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_mostCurrentCycle
										||dijit.byId("dateKeySelect").get('displayedValue') == Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_currentTruckPosition
										||dijit.byId("dateKeySelect").get('displayedValue') == Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_previousCycle){
									dojo.byId('dateStrSpan').style.display="none";
									dojo.byId('dateEndSpan').style.display="none";
									return;
								}else if(dijit.byId("dateKeySelect").get('displayedValue') == Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_historicalCycleArrivalDate
										||dijit.byId("dateKeySelect").get('displayedValue') == Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_historicalCycleDepartureDate){
									dojo.byId('dateStrSpan').style.display="";
									dojo.byId('dateEndSpan').style.display="none";
								}else {
									dojo.byId('dateStrSpan').style.display="";
									dojo.byId('dateEndSpan').style.display="";
								}
							}/*else{
								dojo.byId('dateStrSpan').style.display="";
								dojo.byId('dateEndSpan').style.display="";
							}*/
						
							var post_param={
									url : window.actionPath + "/search/getDateValueList",
									content: Util.prepareAjaxData("json_param", datawrapper),
									//form:dojo.byId("searchContentParm"),
									handleAs : "json",
									/*error: function(error){
												console.log(error);
												var errorObject = dojo.fromJson(error.responseText);
												console.log(errorObject);
												Util.hideStandBy();
												new tms.china.widget.infoMsg.InfoMsg(
														{	message:errorObject.message.text,
														    title:Common.I18N.resourceBundle.WEBEAN_PAGE_errorOccurred,
														    type:Info_constant.FAIL   //可选参数type有三个值，Info_constant.SUCCESS，Info_constant.FAIL，Info_constant.CONFIRM ,对应的图片不一样
														}).show();
											},*/
									error:Util.ajaxErrorHandler,
									preventCache : true,
									load:function(result){
									 
									 	dojo.require("dojo.data.ItemFileReadStore");
									 	
									 	var storeStr = new dojo.data.ItemFileReadStore( {
											data : result.json.str
										});
										var storeEnd = new dojo.data.ItemFileReadStore( {
											data : result.json.end
										});
										var storeEndWithOutDateOp = new dojo.data.ItemFileReadStore( {
											data : result.json.endWithOutDateOp
										});
										
										//（dedicatedfleet模块）
										console.log(Common.currentpage.params.searchType);
										if(Common.currentpage.params.searchType=='dedicatedfleet'&&(dijit.byId("dateKeySelect").get('displayedValue') == Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_historicalCycleArrivalDate
												||dijit.byId("dateKeySelect").get('displayedValue') == Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_historicalCycleDepartureDate)
											){
											dojo.byId('dateStrSpan').style.display="";
											dojo.byId('dateEndSpan').style.display="none";
										}else if(Common.currentpage.params.searchType=='dedicatedfleet') {
											dojo.byId('dateStrSpan').style.display="";
											dojo.byId('dateEndSpan').style.display="";
										}
										console.log(storeStr);
										console.log(storeEnd);
										
									 	dijit.byId("dateStr").set("store", storeStr);
									 	dijit.byId('dateStr').set('value','2:'+Common.I18N.resourceBundle.BEAN_DASHBOARD_DASHBOARDTIMEITEM_TITLE_toDay);
									    dijit.byId("dateEnd").set("store", storeEnd);
									    dijit.byId('dateEnd').set('value','0:'+'---');
									    dijit.byId("toDate").set("store", storeEndWithOutDateOp);
									    dijit.byId("toDate").set('value','0:'+'---');
									}
								};
							Util.xhrPost(post_param);
						},
						
						save:function(searchType){
							
							var originSelect = dijit.byId("cs_origin1");
							if(originSelect){
								if(originSelect.displayedValue == Util.getI18nValue("WEB_BASE_WAIT_loadingMsg")){
									return;
								}
							}
							
							var destinationSelect = dijit.byId("cs_destination1");
							if(destinationSelect){
								if(destinationSelect.displayedValue == Util.getI18nValue("WEB_BASE_WAIT_loadingMsg")){
									return;
								}
							}
							
							var originSelect2 = dijit.byId("cs_origin2");
							if(originSelect2){
								if(originSelect2.displayedValue == Util.getI18nValue("WEB_BASE_WAIT_loadingMsg")){
									return;
								}
							}
							
							var radiusSelect = dijit.byId("cs_radius");
							if(radiusSelect){
								if(radiusSelect.displayedValue == Util.getI18nValue("WEB_BASE_WAIT_loadingMsg")){
									return;
								}
							}
							
							if(!dijit.byId("searchName").isValid()){
								new tms.china.widget.infoMsg.InfoMsg(
										{	message:"搜索名字未定义",
										    title:Common.I18N.resourceBundle.WEBEAN_PAGE_errorOccurred,
										    type:Info_constant.FAIL   //可选参数type有三个值，Info_constant.SUCCESS，Info_constant.FAIL，Info_constant.CONFIRM ,对应的图片不一样
										}).show();
								
								return;
							}else if(searchType!="dedicatedfleet"&&(dijit.byId("dateKeySelect").get('value')==0||dijit.byId("dateStr").get('value')==0||!dijit.byId("dateKeySelect").isValid()||!dijit.byId("dateStr").isValid()||!dijit.byId("searchName").isValid()||!dijit.byId("dateEnd").isValid())){
								new tms.china.widget.infoMsg.InfoMsg(
										{	message:"日期输入不合法",
										    title:Common.I18N.resourceBundle.WEBEAN_PAGE_errorOccurred,
										    type:Info_constant.FAIL   //可选参数type有三个值，Info_constant.SUCCESS，Info_constant.FAIL，Info_constant.CONFIRM ,对应的图片不一样
										}).show();
								
								return;
							}else if(dijit.byId("radiuDistance")&&!dijit.byId("radiuDistance").isValid()){
								
								new tms.china.widget.infoMsg.InfoMsg(
										{	message:"半径距离输入不合法",
										    title:Common.I18N.resourceBundle.WEBEAN_PAGE_errorOccurred,
										    type:Info_constant.FAIL   //可选参数type有三个值，Info_constant.SUCCESS，Info_constant.FAIL，Info_constant.CONFIRM ,对应的图片不一样
										}).show();
								
								return;
							}else if(dijit.byId('dateStr').get('value')=='4:'+Common.I18N.resourceBundle.BEAN_PROFILE_INVOICEPROFILELW_TRUEKEY_invoiceTimeIsFixed&&!dijit.byId('dateStrInput').isValid()){
								
								new tms.china.widget.infoMsg.InfoMsg(
										{	message:"日期输入不合法",
										    title:Common.I18N.resourceBundle.WEBEAN_PAGE_errorOccurred,
										    type:Info_constant.FAIL   //可选参数type有三个值，Info_constant.SUCCESS，Info_constant.FAIL，Info_constant.CONFIRM ,对应的图片不一样
										}).show();
								return;
							}
																
							if(!this.checkInSorts() ){
								
								  new tms.china.widget.infoMsg.InfoMsg(
											{	message:"sort字段不合法",
											    title:Common.I18N.resourceBundle.WEBEAN_PAGE_errorOccurred,
											    type:Info_constant.FAIL   //可选参数type有三个值，Info_constant.SUCCESS，Info_constant.FAIL，Info_constant.CONFIRM ,对应的图片不一样
											}).show();
								  return;
							}
								
							Util.showStandBy();
							var datawrapper = {};
							datawrapper["searchType"]=searchType;
							if(this.searchID){
								datawrapper["searchID"]=this.searchID;
							}
							
							var post_param={
									url : window.actionPath + "/search/saveSearch",
									content: Util.prepareAjaxData("json_param", datawrapper),
									form:dojo.byId("searchContentParm"),
									handleAs : "json",
									error: Util.ajaxErrorHandler,
									preventCache : true,
									load:function(result){
									 	
									 	 var createSearchValues = {searchNameAndKey:result.json,searchType:Common.currentpage.searchType};
										 Nav.closePage(createSearchValues);
									}
								};
							Util.xhrPost(post_param);
							
						}
						,deleteSearch:function(searchType){
							new tms.china.widget.infoMsg.InfoMsg(
									{
										message:Common.I18N.resourceBundle.SEARCH_PROMPT_custom_search_name + this.searchID.replace(/\d+(:[\w\d]+)/ , "$1"),
									    title:Common.I18N.resourceBundle.WEB_BASE_WEBEAN_SEARCH_SEARCHVIEW_removeCustomSearch,
									    type:Info_constant.CONFIRM
									},
									{
										caller:this, //caller是callback函数所在的scope
										callback:"_deleteSearchCallback" //回调函数名 
									}).show();
						}
						,_deleteSearchCallback:function(searchType){
							var datawrapper = {};
							datawrapper["searchType"]=this.searchType;
							datawrapper["searchID"]=this.searchID;
							
							var post_param={
									url : window.actionPath + "/search/deleteSearch",
									content: Util.prepareAjaxData("json_param", datawrapper),
									handleAs : "json",
									error: Util.ajaxErrorHandler,
									/*error: function(error){
												console.log(error);
												var errorObject = dojo.fromJson(error.responseText);
												console.log(errorObject);
												Util.hideStandBy();
												new tms.china.widget.infoMsg.InfoMsg(
														{	message:errorObject.message.text,
														    title:Common.I18N.resourceBundle.WEBEAN_PAGE_errorOccurred,
														    type:Info_constant.FAIL   //可选参数type有三个值，Info_constant.SUCCESS，Info_constant.FAIL，Info_constant.CONFIRM ,对应的图片不一样
														}).show();
											},*/
									preventCache : true,
									load:function(result){
									 	 console.log(result);
									 	 Util.hideStandBy();
									 	/* new tms.china.widget.infoMsg.InfoMsg(
												{	message:Common.I18N.resourceBundle.WEBEAN_SERVLET_SEARCHSRV_searchRemoved+":"+result.json,
												    title:Common.I18N.resourceBundle.SEARCH_TITLE_edit_remove_search,
												    type:Info_constant.FAIL   //可选参数type有三个值，Info_constant.SUCCESS，Info_constant.FAIL，Info_constant.CONFIRM ,对应的图片不一样
												}).show();*/
									 	var message = Common.I18N.resourceBundle.WEBEAN_SERVLET_SEARCHSRV_searchRemoved+":"+result.json;
									 	Util.showToaster(message,"message");
									 	// alert( Common.I18N.resourceBundle.WEBEAN_SERVLET_SEARCHSRV_searchRemoved+result.json);
										Nav.closePage();
									}
								};
							
							Util.xhrPost(post_param);
							
						}
						//get memberlist for alert Search page!
						,getMemberLis:function(){
							var datawrapper = {};
							var memberListType = dijit.byId("memberListType").get('value');
							datawrapper["memberListType"] = memberListType;
							var post_param={
									sync:true,
									url : window.actionPath + "/search/getMemberList",
									content: Util.prepareAjaxData("json_param", datawrapper),
									handleAs : "json",
									error: Util.ajaxErrorHandler,
									/*error: function(error){
												console.log(error);
												var errorObject = dojo.fromJson(error.responseText);
												console.log(errorObject);
												Util.hideDialogStandBy();
												new tms.china.widget.infoMsg.InfoMsg(
														{	message:errorObject.message.text,
														    title:Common.I18N.resourceBundle.WEBEAN_PAGE_errorOccurred,
														    type:Info_constant.FAIL   //可选参数type有三个值，Info_constant.SUCCESS，Info_constant.FAIL，Info_constant.CONFIRM ,对应的图片不一样
														}).show();
											},*/
									preventCache : true,
									load:function(result){
									 	console.log(result.json);	
									 	
										if (result.json.items.length == 0) {
											result.json.items[0] = {
												name : "没有匹配项",
												value : ""
											};
										}
										var store = new dojo.data.ItemFileReadStore( {
											data : result.json
										});
										
										dijit.byId("memberlistId").set("store", store);
									    dijit.byId("memberlistId").set("value", result.json.items[0].value);
						
									}
								};
							
							Util.xhrPost(post_param);
							
						}
						,getCreateDateContent:function(){
	
							var post_param={
									url : window.actionPath + "/search/getCreateDateContent",
									content: Util.prepareAjaxData("json_param", dijit.byId("dateKeySelect").get("value")),
									handleAs : "json",
									form:dojo.byId("searchContentParm"),
									//error:null,
									error: Util.ajaxErrorHandler,
									preventCache : true,
									load:function(result){
											console.debug(result);
											//alert(dojo.string.trim(result.json));
											//显示alert信息
											new tms.china.widget.infoMsg.InfoMsg(
													{
														message:dojo.string.trim(result.json),
													    title:"alert"
													}).show();
											
											Util.hideStandBy();
										}
							} ;
							
							Util.xhrPost(post_param);
						}
						,
						getSCAC : function(type, code, value) {
							var scacStore = new dojo.data.ItemFileReadStore(
									{
										url : window.actionPath
												+ "/search/getSCAC?"
												+ "json={'type':'"
												+ type + "','code':'"
												+ code + "','value':'"
												+ value + "'}",
										urlPreventCache : true
									});
							// Fetch the data.
							scacStore.fetch();

							return scacStore;
						},
						checkInSorts:function(){
							
							var sort1=dijit.byId("sort1");							
							var sort2=dijit.byId("sort2");
							var sort3=dijit.byId("sort3");
							if(!(sort1.isValid() && sort2.isValid() && sort3.isValid()))
								return false ;
							if(sort1.get("value") && sort2.get("value") && sort1.get("value")==sort2.get("value"))
								return false ;
							if(sort1.get("value") && sort3.get("value") && sort1.get("value")==sort3.get("value"))
								return false ;
							if(sort2.get("value") && sort3.get("value") && sort2.get("value")==sort3.get("value"))
								return false ;
							return true ;
						}

					// ,
					// Uncomment above comma and add comma-separated functions
					// here. Do not leave a
					// trailing comma after last element.
					});

			return tms.china.widget.search.SearchOrderAddPage;
		});