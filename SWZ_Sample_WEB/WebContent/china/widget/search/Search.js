define("tms/china/widget/search/Search",[ "dojo", "dijit", "tms/china/widget/_Widget", "dijit/_Templated","text!tms/china/widget/search/templates/Search.html" ], function(dojo, dijit) {
			/**
			 * 这是每个页面的search组件，主要实现对各种数据（订单，运单等）的搜索功能
			 * 20111110 陆锋平：新建
			 */
	
			//加入该widget所需要的dojo widget
			dojo.require("dijit.form.FilteringSelect");
			dojo.require("dijit.form.Select");
			dojo.require("dijit.form.Button");
			dojo.require("dijit.form.TextBox");
			dojo.require("dijit.form.ValidationTextBox");
			dojo.require("dojo.data.ItemFileReadStore");
			dojo.require("dojo.data.ItemFileWriteStore");
			dojo.require("dijit.form.DateTextBox");
			dojo.require("dijit.form.TimeTextBox");
			dojo.require("dijit.form.Form");
			dojo.require("dijit.form.RadioButton");			
			dojo.require("tms.china.widget.infoMsg.InfoMsg");
			/*dojo.require("dojox.data.QueryReadStore");
			dojo.provide("ServerPagingReadStore");
			dojo.declare("ServerPagingReadStore", dojox.data.QueryReadStore, {
				fetch:function(request) {
					request.serverQuery = {
											searchType:request.searchType,
										    searchContent:request.searchContent,
										    findContent:request.findContent,
										    findText:request.findText,
										    dateContent:request.dateContent,
										    basedate:request.basedate,
										    rangeContent:request.rangeContent
											};
					return this.inherited("fetch", arguments);
				}
			});*/
			dojo.declare("tms.china.widget.search.Search", [ tms.china.widget._Widget,
					dijit._Templated ], {
				// Path to the template
				templateString : dojo.cache("tms.china.widget.search",
						"templates/Search.html"),

				// Set this to true if your widget contains other widgets
				widgetsInTemplate : true,

				//UI strings 定义template中用到的界面的String
				_messages: null,
				_ui_string_search: null,
				_ui_string_create_search: null,
				_ui_string_find: null,
				_ui_string_button_find: null,
				_ui_string_date: null,
				_ui_string_date_from: null,
				_ui_string_date_to: null,
				
				//wedgets的类型
				searchType:null,
				//搜索对应的grid的id
				gridId:null,
				//提交的form
				form:null,
				//store
				store:null,
				//搜索结果总数
				totolNumber:0 ,
				customerSearchConnection:null,
				findConnection:null,
				dateConnection:null,
				flag:false,
				ft:[],
				fr:null,
				mostCurrentCycle:null,
				// Override this method to perform custom behavior during dijit construction.
				// Common operations for constructor:
				// 1) Initialize non-primitive types (i.e. objects and arrays)
				// 2) Add additional properties needed by succeeding lifecycle methods
				// Constructor function. Called when instance of this class is created
				constructor : function(/**Dom Node*/ rootNode) {
					this.inherited(arguments);
					console.log(rootNode.type) ;
					if(rootNode!=null){
						this.searchType=rootNode.type;
						this.gridId=rootNode.gridId;
						this.flag=rootNode.flag;
					}
					
				},
				/**
				 * 初始化搜索条件
				 * 主要用于获取搜索的条件
				 */
		        initDefList:function(){
					
					var self=this;
					this.form=this.searchForm;
					this.initBaseDate();
					
					
					//得到searchContent
					//this.clearConnection();
					Util.xhrPost({
						url : window.actionPath + "/searchData/getSearchCriteria",
						content:{searchType:self.searchType,cmd:"customize"},
						handleAs : "json",
						sync:true,
						error: Util.ajaxErrorHandler,
						preventCache : true
					},  dojo.hitch(this, function(json){
						var searchContent=new dojo.data.ItemFileReadStore({data:json});
						if(!(json.searchDefault==null || json.searchDefault==undefined)){
							if(self.flag){
							self.clearConnection();
							self.searchButton.set("disabled",false);
							}
							searchContent.fetch({onComplete:function(items){
								self.customized_search_select.set("store",searchContent);
								if(!self.flag)
								for(var i=0;i<items.length;i++){
									if(items[i].value[0]!=null){
										if(items[i].value[0].split(":")[0]==json.searchDefault){
											self.customized_search_select.set("value",items[i].value[0]);
											//
											
											break;
										}
									}
								}
									var rangeContent=new dojo.data.ItemFileReadStore({sync:true,url:"/chinaui/searchData/getDayRange"});
									rangeContent.fetch({onComplete:function(){					
										self.range_select.set("store",rangeContent);
										var findContent=new dojo.data.ItemFileReadStore({sync:true,url:"/chinaui/searchData/getSearchCriteria?searchType="+self.searchType+"&cmd=find"});
										findContent.fetch({onComplete:function(){
											self.find_select.set("store",findContent);
											var dateContent=new dojo.data.ItemFileReadStore({sync:true,url:"/chinaui/searchData/getSearchCriteria?searchType="+self.searchType+"&cmd=date"});
											if(self.searchType=="dedicatedfleet"){
												dateContent.fetch({onComplete:function(items){
													
														for(var i=0;i<items.length;i++){
															if(Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_mostCurrentCycle==items[i].name[0]){
																self.mostCurrentCycle=items[i].value[0];
																break;
															};
														}
													
													 self.date_select.set("store",dateContent);
													 var groupBy=dijit.byId("lfp_option");
													 if(groupBy){
														 var groupByStore=new dojo.data.ItemFileReadStore({
															url:window.actionPath+"/search/getDedicatedFleetGroupFields"
															//error:Util.ajaxErrorHandler
														});
														 groupByStore.fetch({onComplete:function(items){
															 
															 groupBy.set("store",groupByStore);
															// groupBy.set("value",0);																	
															 
															// dojo.connect(groupBy,"onChange",Common.currentpage,"GroupBy");//绑定切换分组的函数
															 dojo.publish("SearchLoad_Complete1", ["Search_DateContent_Load_Completed"]);
														 }});
													}else{							 
													 dojo.publish("SearchLoad_Complete1", ["Search_DateContent_Load_Completed"]);
													 }
												}});  
										    }else if(self.searchType=="availableshipment"){
										    	dateContent.fetch({onComplete:function(){	
										    		 self.date_select.set("store",dateContent);
													 dojo.publish("SearchLoad_Complete2", ["Search_DateContent_Load_Completed"]);
												}});
										    }else {
										    	dateContent.fetch({onComplete:function(){
										    		self.date_select.set("store",dateContent);
													 dojo.publish("SearchLoad_Complete", ["Search_DateContent_Load_Completed"]);
												}}); 
										    }
											
											
										}});
									}});	
								
								//self.setConnection();
							}});
						}else{
							searchContent.fetch({onComplete:function(items){
								self.customized_search_select.set("store",searchContent);								
								var rangeContent=new dojo.data.ItemFileReadStore({sync:true,url:"/chinaui/searchData/getDayRange"});
									rangeContent.fetch({onComplete:function(){					
										self.range_select.set("store",rangeContent);
										var findContent=new dojo.data.ItemFileReadStore({sync:true,url:"/chinaui/searchData/getSearchCriteria?searchType="+self.searchType+"&cmd=find"});
										findContent.fetch({onComplete:function(){
											self.find_select.set("store",findContent);
											var dateContent=new dojo.data.ItemFileReadStore({sync:true,url:"/chinaui/searchData/getSearchCriteria?searchType="+self.searchType+"&cmd=date"});
											if(self.searchType=="dedicatedfleet"){
												dateContent.fetch({onComplete:function(items){
													for(var i=0;i<items.length;i++){
														if(Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_mostCurrentCycle==items[i].name[0]){
															self.mostCurrentCycle=items[i].value[0];
															
															break;
														};
													}
													 self.date_select.set("store",dateContent);
													 var groupBy=dijit.byId("lfp_option");
													 if(groupBy){
														 var groupByStore=new dojo.data.ItemFileReadStore({
															url:window.actionPath+"/search/getDedicatedFleetGroupFields"
														});
														 groupByStore.fetch({onComplete:function(items){
															 
															 groupBy.set("store",groupByStore);
															// groupBy.set("value",0);																	
															 
															// dojo.connect(groupBy,"onChange",Common.currentpage,"GroupBy");//绑定切换分组的函数
															 dojo.publish("SearchLoad_Complete1", ["Search_DateContent_Load_Completed"]);
														 }});
													 }else{							 
													 dojo.publish("SearchLoad_Complete1", ["Search_DateContent_Load_Completed"]);
													 }
												}});  
										    }else if(self.searchType=="availableshipment"){
										    	dateContent.fetch({onComplete:function(){	
										    		 self.date_select.set("store",dateContent);
													 dojo.publish("SearchLoad_Complete2", ["Search_DateContent_Load_Completed"]);
												}});
										    }else {
										    	dateContent.fetch({onComplete:function(){
										    		self.date_select.set("store",dateContent);
													 dojo.publish("SearchLoad_Complete", ["Search_DateContent_Load_Completed"]);
												}}); 
										    }
											
											
										}});
									}});	
								
								//self.setConnection();
							}});
						}
					}));
					/*var searchContent=new dojo.data.ItemFileReadStore({sync:true,url:"/chinaui/searchData/getSearchCriteria?searchType="+this.searchType+"&cmd=customize"});
					searchContent.fetch();
					this.customized_search_select.set("store",searchContent);
					console.log(searchContent);*/
					
					//var findContent=new dojo.data.ItemFileReadStore({sync:true,url:"/servlet/NBAjaxSrv?param=shipment_findcontent&json={}&action=shipment.StaticData"});
					/*var findContent=new dojo.data.ItemFileReadStore({sync:true,url:"/chinaui/searchData/getSearchCriteria?searchType="+this.searchType+"&cmd=find"});
					findContent.fetch();
					this.find_select.set("store",findContent);*/
					
					//var dateContent=new dojo.data.ItemFileReadStore({sync:true,url:"/servlet/NBAjaxSrv?param=shipment_datecontent&json={}&action=shipment.StaticData"});
					/*var dateContent=new dojo.data.ItemFileReadStore({sync:true,url:"/chinaui/searchData/getSearchCriteria?searchType="+this.searchType+"&cmd=date"});
				
					if(this.searchType=="dedicatedfleet"){
						dateContent.fetch({onComplete:function(){					
							 dojo.publish("SearchLoad_Complete1", ["Search_DateContent_Load_Completed"]);
						}});  
				    }else if(this.searchType=="availableshipment"){
				    	dateContent.fetch({onComplete:function(){					
							 dojo.publish("SearchLoad_Complete2", ["Search_DateContent_Load_Completed"]);
						}});
				    }else {
				    	dateContent.fetch({onComplete:function(){					
							 dojo.publish("SearchLoad_Complete", ["Search_DateContent_Load_Completed"]);
						}}); 
				    }
					
					this.date_select.set("store",dateContent);*/
					if(this.searchType=="alert"){
						//显示警报的选择
						this.alertView.style.display="inline";
					}
				
					
				},
				/**
				 * 初始化sort
				 */
				initSort:function(sort){
					    var grid=dijit.byId(this.gridId);
					    if(grid.initCellSortDisplay)
					    grid.initCellSortDisplay();
					    var sortWidget= Common.currentpage.getSortWidget(this.searchType);
					   // console.log(sortWidget);
					    if(sortWidget!=null) sortWidget.clearSortColumn();
					    if(sort==null)return ;
					    for(var i=0;i<3;i++){
							if(sort["sort"+i]==null)break ;
							var field=grid.getCellByField(sort["sort"+i].attribute);
							//console.log(field);
							if(grid.setCellSortDisplay)
							grid.setCellSortDisplay(field,i+1,sort["sort"+i].descending);
						}
					   
				},
				/**
				 * 执行搜索
				 */
				search:function(){
					var grid=dijit.byId(this.searchType);
					if(grid.plugin){
						if(grid.plugin("filter")){
							grid.plugin("filter").filterDefDialog.clearFilter();
							/*if(grid.filterFlag && grid.needFilter){
								var ft=dojo.fromJson(dojo.cookie(window.userId+"_"+grid.name+"_ft"));
								var fr=fr=dojo.cookie(window.userId+"_"+grid.name+"_fr");
								if(ft && fr){
									console.log("do filter");
									grid.setFilter(ft,fr);
								}
							}*/
							
						}
					}
					if(grid.selectedStore){
						grid.selectedStore();
					}
					
					if(this.searchType==="shipment" || this.searchType==="order" ||this.searchType==="invoice" ||this.searchType==="customerserviceshipment" ||this.searchType==="availableshipment" ||this.searchType=="ltlorder"){
						/*var deferred=this._postSearchDate();
						deferred.addCallback(dojo.hitch(this,this._searchCallback));*/
						
						grid.store.url=window.actionPath + "/search/search";
						var query=this.getSearchContent();
						grid.setQuery(query);
						
					}/*else if(this.searchType=="order"){
						var deferred=this._postSearchDate();
						deferred.addCallback(dojo.hitch(this,this._searchCallback));
					}*//*else if(this.searchType=="ltlorder"){
						var deferred=this._postSearchDate();
						deferred.addCallback(dojo.hitch(this,this._searchCallback));
					}*//*else if(this.searchType=="availableshipment"){
						var deferred=this._postSearchDate();
						deferred.addCallback(dojo.hitch(this,this._searchCallback));
					}*//*else if(this.searchType=="invoice"){
						var deferred=this._postSearchDate();
						deferred.addCallback(dojo.hitch(this,this._searchCallback));
					}*/else if(this.searchType=="dedicatedfleet"){
						var groupBy=dijit.byId("lfp_option");
						var groupValue=groupBy.get("value");
						var deferred=null;
						var findValue = this.find_select.get("displayedValue");
						if(this.customized_search_select.get("value")){
							if(groupValue!=0){
								this.treegridSearch(groupValue);
								return ;
							}
							//deferred=this._postSearchDate();
							grid.store.url=window.actionPath + "/search/search";
							var query=grid.lastSearchContent=this.getSearchContent();
							grid.setQuery(query);
						}else if(/*findValue==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_fleetRef 
								|| findValue==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_assetRef
								|| findValue==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_tractorNumber
								|| findValue==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDFLEETASSETITEM_P_driverName*/true){
							if( this.judgeFunction.isEmpty(this.date_select.get("displayedValue"))/*!this.date_select.get("displayedValue")||this.date_select.get("displayedValue").trim()==""*/){
								
								new tms.china.widget.infoMsg.InfoMsg(
										{	message:Common.I18N.resourceBundle.SEARCH_MSG_need_date,
										    title:Common.I18N.resourceBundle.WEBEAN_PAGE_errorOccurred,
										    type:Info_constant.FAIL   //可选参数type有三个值，Info_constant.SUCCESS，Info_constant.FAIL，Info_constant.CONFIRM ,对应的图片不一样
										}).show();
								return;
							}else {
								if(groupValue!=0){
									this.treegridSearch(groupValue);
									return ;
								}
								//deferred=this._postSearchDate();
								grid.store.url=window.actionPath + "/search/search";
								var query=grid.lastSearchContent=this.getSearchContent();
								grid.setQuery(query);
							}
							
						}else if(findValue){
							if(groupValue!=0){
								this.treegridSearch(groupValue);
								return ;
							}
							//deferred=this._postSearchDate();
							grid.store.url=window.actionPath + "/search/search";
							var query=grid.lastSearchContent=this.getSearchContent();
							grid.setQuery(query);
						}else if(this.date_select.get('displayedValue')){
							if(groupValue!=0){
								this.treegridSearch(groupValue);
								return ;
							}
							//deferred=this._postSearchDate();
							grid.store.url=window.actionPath + "/search/search";
							var query=grid.lastSearchContent=this.getSearchContent();
							grid.setQuery(query);
						}else {
							return;
						}
						//deferred.addCallback(dojo.hitch(this,this._searchCallback));
					}else if(this.searchType==="alert"){
						var findValue = this.find_select.get("displayedValue");
						if(findValue===Common.I18N.resourceBundle.BEAN_BRUNSWICK_SHIPMENTIDHOLDER_P_SEARCH_scac && this.judgeFunction.isEmpty(this.date_select.get("displayedValue"))/*!this.date_select.get("displayedValue")||this.date_select.get("displayedValue").trim()=="")*/){
							new tms.china.widget.infoMsg.InfoMsg(
									{	message:Common.I18N.resourceBundle.SEARCH_MSG_need_date,
									    title:Common.I18N.resourceBundle.WEBEAN_PAGE_errorOccurred,
									    type:Info_constant.FAIL   //可选参数type有三个值，Info_constant.SUCCESS，Info_constant.FAIL，Info_constant.CONFIRM ,对应的图片不一样
									}).show();
							return;
						}
						/*var deferred=this._postSearchDate();
						deferred.addCallback(dojo.hitch(this,this._searchCallback));*/
						grid.store.url=window.actionPath + "/search/search";
						var query=grid.lastSearchContent=this.getSearchContent();
						grid.setQuery(query);
					}/*else if(this.searchType=="customerserviceshipment"){
						var deferred=this._postSearchDate();
						deferred.addCallback(dojo.hitch(this,this._searchCallback));
					}*/
					this.setConnection();
				},
				/**
				 * 执行搜索之后的回调程序
				 */
				_searchCallback:function(json){
					this.setConnection();
					if(json.json==null){
						store=null;
						dijit.byId(this.gridId).setStore(store);
						Util.hideStandBy();
						return ;
					}
					this.initSort(json.json.sort);					
					var result=json.json.result ;
					//初始化分页条
					
					Common.currentpage.getPageBar(this.searchType).initPageBar(json.json.totalNumber,json.json.pageSize,json.json.searchDescription);
					
					if(json.json.totalNumber<=json.json.pageSize && json.json.totalNumber>=2 ){
						//当搜索结果为一页时显示过滤器
						if(dijit.byId(this.gridId).setDisplayOrHiddenFilter){
							dijit.byId(this.gridId).setDisplayOrHiddenFilter(true);
							dijit.byId(this.gridId).filterFlag=true;
						}
					}else{
						if(dijit.byId(this.gridId).setDisplayOrHiddenFilter){
							dijit.byId(this.gridId).setDisplayOrHiddenFilter(false);
						}
					}
					
					if(result!=null){
						var store = new dojo.data.ItemFileReadStore( {
							data : result
						});
					//	console.log(store);
						//add by mjl
						var currentGridId = this.gridId;
						dijit.byId(currentGridId).selection.clear();
						dijit.byId(currentGridId).setStore(store);
						//恢复选中状态
						dijit.byId(currentGridId).store.fetch(
								{
									onComplete: function(items){								
										if(Nav.getRestoreData()){
											var selectedInfos = Nav.getRestoreData().selectedInfos;
											if(selectedInfos){
												for(var selectedInfosIndex in selectedInfos){
													var selectedInfo = selectedInfos[selectedInfosIndex];
													var gridId = dojo.getObject("gridId", false, selectedInfo);
													if(gridId == currentGridId){
														var grid = dijit.byId(gridId);
														if(grid){
															var selection = grid.get("selection");
															var selected = dojo.getObject("selected", false, selectedInfo);
															var compareFunc = dojo.getObject("compareFunc", false, selectedInfo);
															for(var selectedIndex in selected){
																//找到对应的item
																for(var itemsIndex in items){	
																		if(compareFunc(selected[selectedIndex], items[itemsIndex])){
																			grid.selection.setSelected(itemsIndex, true);
																			break;
																		}
																}
															}
														}
													}
												}
											}
										}
										dojo.publish("PageLoadComplete", ["Load Completed"]);
									}
								
								}
						);
						var grid=dijit.byId(this.gridId);
						this.ft=dojo.fromJson(dojo.cookie(window.userId+"_"+grid.name+"_ft"));
						if(!this.ft) return ;
						if(this.ft.length>=1 && json.json.totalNumber<=json.json.pageSize && json.json.totalNumber>=2){	
							this.fr=dojo.cookie(window.userId+"_"+grid.name+"_fr");
							grid.setFilter(this.ft,this.fr);
						}else{
							grid.filterFlag=false;
						}				
					}
					else{
						//console.log(json);
						Util.hideStandBy();
					}							
				},
				/**
				 * 提交搜索条件到SearchController控制类
				 * 
				 */
				_postSearchDate:function(){																    
					    var datawrapper = {};
						datawrapper["searchType"]=this.searchType;	
						 var grid=dijit.byId(this.gridId);
						 if(grid){
							 grid.setStore(null);
						 }
						Util.showStandBy();				
						return Util.xhrPost({
							url : window.actionPath + "/search/search",
							content: Util.prepareAjaxData("jsonparm", datawrapper),
							form:this.form.domNode ,
							//sync:true,
							handleAs : "json",
							error:Util.ajaxErrorHandler,
							preventCache : true
						});
						
				 },
			

				// When this method is called, all variables inherited from superclasses are 'mixed in'.
				// Common operations for postMixInProperties
				// 1) Modify or assign values for widget property variables defined in the template HTML file
				postMixInProperties : function() {
					this.inherited(arguments);
					
					//init display string
					//找到widget下面的Search_i18n.js。初始化
					 dojo.requireLocalization("tms.china.widget", "Search_i18n");
					//得到resource bundle
					this._messages = dojo.i18n.getLocalization("tms.china.widget", "Search_i18n", this.lang);
					//设置界面上的String
					this._ui_string_search = this._messages['_ui_string_search'];
					this._ui_string_create_search = this._messages['_ui_string_create_search'];
					this._ui_string_find = this._messages['_ui_string_find'];
					this._ui_string_button_find = this._messages['_ui_string_button_find'];
					this._ui_string_date = this._messages['_ui_string_date'];
					this._ui_string_date_from = this._messages['_ui_string_date_from'];
					this._ui_string_date_to = this._messages['_ui_string_date_to'];
				},

				// postCreate() is called after buildRendering().  This is useful to override when 
				// you need to access and/or manipulate DOM nodes included with your widget.
				// DOM nodes and widgets with the dojoAttachPoint attribute specified can now be directly
				// accessed as fields on "this". 
				// Common operations for postCreate
				// 1) Access and manipulate DOM nodes created in buildRendering()
				// 2) Add new DOM nodes or widgets 
				postCreate : function() {	
					/**
					 * 禁用ltlorder的自定义搜索
					 */
					if(this.searchType ==="ltlorder"){
						this.customized_search_tr.style.display="none";
						dojo.addClass(this.searchtable,"search_margin");
					}
		
					this.setConnection();
					this.initDefList();	
					dojo.connect(this.domNode, "onkeypress", this, "_onKey");
				},
				
				/**
				 * 清空findText中的值
				 */
				_emptyFindText:function(){
					//console.log("event2 starting ...........");
					//console.log(this.find_select.get("value"));
					if(!this.find_select.get("value")){
						 this.find_text.set("value","");
						 this.star.style.display="none";
						 if(!this.customized_search_select.get("value") && !this.date_select.get("value"))  {
								this.searchButton.set("disabled",true);	 
						}
						 dijit.hideTooltip(this.find_text.domNode);
						 dojo.removeClass(this.find_text.domNode,"dijitTextBoxError"); 
					}else {
						this.customized_search_select.set("value","");
						this.searchButton.set("disabled",false);
						var findValue = this.find_select.get("displayedValue");
						//添加 周华
						this.date_select.set("displayedValue","创建日期");
						//this.dayrange.set("displayedValue","及之前的 30 天");
						this._initDateContent();
						
						//
						
						if(this.searchType=="alert"){
							if(findValue==Common.I18N.resourceBundle.BEAN_BRUNSWICK_SHIPMENTIDHOLDER_P_SEARCH_scac){
								this.star.style.display="inline";
							}else{
								this.star.style.display="none";
							}
						}else if(this.searchType=="dedicatedfleet"){
							if(/*findValue==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_fleetRef 
									|| findValue==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_assetRef
									|| findValue==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_tractorNumber
									|| findValue==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDFLEETASSETITEM_P_driverName*/true){
								this.star.style.display="inline";
								if(this.mostCurrentCycle){
									this.date_select.set("value",this.mostCurrentCycle);
								}
							}/*else{
								this.date_select.set("value","");
								this.star.style.display="none";
							}*/
						}
					}
						
					
					
				// this.find_text.set("value","");
				},
				/**
				 * 初始化date的搜索条件
				 */
				_initDateContent:function(){
					//console.log("event3 starting ...........");
					if(this.searchType=="dedicatedfleet"){//专用车队专门判断	
						
						var datacontent=this.date_select.get("displayedValue");
						//console.log(datacontent);
						if(datacontent==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_mostCurrentCycle ||
								datacontent==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_currentTruckPosition||
								 datacontent==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_previousCycle){
							this.searchButton.set("disabled",false);
							this.customized_search_select.set("value","");
							this.basedate.style.display="none";
							this.dayrange.style.display="none";
							return ;
						}else if(datacontent==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_historicalCycleArrivalDate || datacontent==Common.I18N.resourceBundle.BEAN_FLEET_DEDICATED_DEDICATEDASSETSUMMARYITEM_P_historicalCycleDepartureDate){
							this.searchButton.set("disabled",false);
							this.customized_search_select.set("value","");
							this.basedate.style.display="inline";
						//	this.initBaseDate();
							this.dayrange.style.display="none";
							return ;		
						}
						
					}
					if(this.date_select.get("value")){
						//alert("dd");
						this.searchButton.set("disabled",false);
						this.customized_search_select.set("value","");
						this.basedate.style.display="inline";
						this.dayrange.style.display="inline";
					}else{
						if(!this.find_select.get("value") && !this.customized_search_select.get("value"))this.searchButton.set("disabled",true);
						this.basedate.style.display="none";
						this.dayrange.style.display="none";
					}
					
					//this.range_select.set("value","0:---");
				},
				/**
				 * 自定义search内容的搜索
				 */
				_search:function(){
					//console.log("event1 starting ...........");
					if(this.customized_search_select.get("value")==null || this.customized_search_select.get("value")==undefined || this.customized_search_select.get("value")==""){
						if(!this.find_select.get("value") && !this.date_select.get("value")) this.searchButton.set("disabled",true);
						return  ;
					}
					else this.searchButton.set("disabled",false);
					this.find_select.set("value","");
					this.date_select.set("value","");
					
					/*if(this.searchType=="dedicatedfleet"){
						var group=dijit.byId("lfp_option");
						if(group){
							if(group.get("value")==0){
								this.search();
							}else{
								group.set("value",0);
							}
						}
					}else{
						this.search();
					}*/
					this.search();
				},
				/**
				 * 添加自定义搜索
				 */
				_addSearch:function(){
					if(this.searchType=="shipment"){
					Nav.go(Nav.SEARCH_ADD, 'tms.china.widget.search.SearchAddPage',{searchType:"shipment"});
					return ;
					}else if(this.searchType=="order"){
						Nav.go(Nav.SEARCH_ADD, 'tms.china.widget.search.SearchAddPage',{searchType:"order"});
						return ;
					}else if(this.searchType=="invoice"){
						Nav.go(Nav.SEARCH_ADD, 'tms.china.widget.search.SearchAddPage',{searchType:"invoice"});
						return ;
					}else if(this.searchType=="alert"){
						Nav.go(Nav.SEARCH_ADD, 'tms.china.widget.search.SearchAddPage',{searchType:"alert"});
						return ;
					}else if(this.searchType=="dedicatedfleet"){
						Nav.go(Nav.SEARCH_ADD, 'tms.china.widget.search.SearchAddPage',{searchType:"dedicatedfleet"});
						return ;
					}else if(this.searchType=="availableshipment"){
						Nav.go(Nav.SEARCH_ADD, 'tms.china.widget.search.SearchAddPage',{searchType:"availableshipment"});
						return ;
					}else if(this.searchType=="customerserviceshipment"){
						Nav.go(Nav.SEARCH_ADD, 'tms.china.widget.search.SearchAddPage',{searchType:"customerserviceshipment"});
						return ;
					}
				}
					/**
					 * 编辑自定义搜索
					 */
					,_editSearch:function(){
						var searchID = this.customized_search_select.get('value');
						console.log(searchID);
						if(!searchID){
							alert("请选择一个搜索");
							return;
						}
						if(this.searchType=="shipment"){
							Nav.go(Nav.SEARCH_EDIT, 'tms.china.widget.search.SearchAddPage',{searchType:"shipment",searchID:searchID});
							return ;
						}else if(this.searchType=="order"){
							Nav.go(Nav.SEARCH_EDIT, 'tms.china.widget.search.SearchAddPage',{searchType:"order",searchID:searchID});
							return ;
						}else if(this.searchType=="invoice"){
							Nav.go(Nav.SEARCH_EDIT, 'tms.china.widget.search.SearchAddPage',{searchType:"invoice",searchID:searchID});
							return ;
						}else if(this.searchType=="alert"){
							Nav.go(Nav.SEARCH_EDIT, 'tms.china.widget.search.SearchAddPage',{searchType:"alert",searchID:searchID});
							return ;
						}else if(this.searchType=="dedicatedfleet"){
							Nav.go(Nav.SEARCH_EDIT, 'tms.china.widget.search.SearchAddPage',{searchType:"dedicatedfleet",searchID:searchID});
							return ;
						}else if(this.searchType=="availableshipment"){
							Nav.go(Nav.SEARCH_EDIT, 'tms.china.widget.search.SearchAddPage',{searchType:"availableshipment",searchID:searchID});
							return ;
						}else if(this.searchType=="customerserviceshipment"){
							Nav.go(Nav.SEARCH_EDIT, 'tms.china.widget.search.SearchAddPage',{searchType:"customerserviceshipment",searchID:searchID});
							return ;
						}
				},
				/**
				 * 得到提交的form
				 */
				getForm:function(){
					return this.form;
				},
				/**
				 * 得到搜索的结果总数
				 */
				getTotalNumber:function(){
					return this.totolNumber;
				},
				/**
				 * 获取search组件中的各种参数
				 */
				getSearchContent:function(){
					return this.customized_search_select;
				},
				getFindContent:function(){
					return this.find_select;
				},
				getDataContent:function(){
					return this.date_select;
				},
				getBaseDate:function(){
					return this.fixed_date;
				},
				getRangeContent:function(){
					return this.range_select;
				},
				getAlertView_history:function(){
					return this.alertView_history;
				},
				getAlertView_last:function(){
					return this.alertView_last;
				},
				getFindText:function(){
					return this.find_text;
				},
				/**
				 * 验证输入的内容是否合法
				 */
				searchWithValidation:function(){
					////>>>>>>>>>>>>>>>>>>>>>>>>>>>
					//console.log("===>" + this.customized_search_select.isValid());
					//console.log("===>" + this.find_select.isValid());
					//console.log("===>" + this.date_select.isValid());
					//console.log("===>" + this.fixed_date.isValid());
					//console.log("===>" + this.range_select.isValid());
					
					//if(this.customized_search_select instanceof dijit.form.FilteringSelect)
					//	alert("yes");
					if(!this.findtextValidator()){
						return ;
					}
					//周华 添加
					if(!this.validatorDate()){
						return;
					}
					
					if(!this.validateDate2()){
						return;
					}
					
					if(this.customized_search_select.isValid() &&this.find_select.isValid() 
							&& this.date_select.isValid() && this.fixed_date.isValid() 					
							 && this.range_select.isValid() && this.to_date.isValid()&&!this.searchButton.get("disabled")){
						//专用车队分组的情况下搜索
						/*if(this.searchType=="dedicatedfleet"){
							var group=dijit.byId("lfp_option");
							if(group){
								if(group.get("value")==0){
									this.search();
								}else{
									group.set("value",0);
								}
							}
						}else{
							this.search();
						}*/
						this.search();
					}else{
						if(this.searchButton.get("disabled")&&this.customized_search_select.get("value")){
							this.searchButton.set("disabled",false);
							this.search();
							return ;
						}else if(this.searchButton.get("disabled")){
							return ;
						}
						new tms.china.widget.infoMsg.InfoMsg(
								{	message:Common.I18N.resourceBundle.SEARCH_MSG_parameter_error,
								    title:Common.I18N.resourceBundle.WEBEAN_PAGE_errorOccurred,
								    type:Info_constant.FAIL   //可选参数type有三个值，Info_constant.SUCCESS，Info_constant.FAIL，Info_constant.CONFIRM ,对应的图片不一样
								}).show();
						return;
					}
				},
				/**
				 * 初始化日期,默认为今天
				 */
				initBaseDate:function(){
					var date=new Date() ;
					var day;
					var month ;
					if(date.getDate()<10)
						day="0"+date.getDate();
					else day=date.getDate();
					
					if(date.getMonth()+1<10) month="0"+(date.getMonth()+1);
					else month=date.getMonth()+1;
					
					var format=date.getFullYear()+"-"+month+"-"+day;
					console.log(format);
					this.fixed_date.set("value",format);
					this.to_date.set("value",format);
					
				},
				
				/**
				 * 绑定组件中的各个事件
				 */
				setConnection:function(){
						if(!this.customerSearchConnection)
						this.customerSearchConnection=dojo.connect(this.customized_search_select,'onChange',dojo.hitch(this,this._search));
						if(!this.findConnection)
						this.findConnection=dojo.connect(this.find_select,'onChange',dojo.hitch(this,this._emptyFindText));
						if(!this.dateConnection)
						this.dateConnection=dojo.connect(this.date_select,'onChange',dojo.hitch(this,this._initDateContent));
						
						//add
						if(!this.rangeConnection)
							this.rangeConnection=dojo.connect(this.range_select,'onChange',dojo.hitch(this,this._showTodate));
						if(this.searchButton.get("disabled")&&this.customized_search_select.get("value")){
							this.searchButton.set("disabled",false);
						}
				},
				/**
				 * 解除绑定事件
				 */
				clearConnection:function(){
					
					if(this.customerSearchConnection){
						dojo.disconnect(this.customerSearchConnection);
						this.customerSearchConnection=null;
					}
				/*	if(this.findConnection)
					{
						dojo.disconnect(this.findConnection);
						this.findConnection=null;
					}*/
					/*if(this.dateConnection){	
						dojo.disconnect(this.dateConnection);
						this.dateConnection=null;//console.log(this.dateConnection+"+++++++++++++++111");
					}*/
					//console.log(this.dateConnection+"+++++++++++++++2222");
				},
				/**
				 * 当选择时间时,显示下一时间组件
				 */
				_showTodate:function(){
					if(this.range_select.get("value")=="date"){
						this.todate.style.display="inline";
						//2011.12.14马江龙
						this.totime.style.display="inline";
						this.basetime.style.display="inline";
					}else{
						this.todate.style.display="none";
						//2011.12.14马江龙
						this.totime.style.display="none";
						this.basetime.style.display="none";
					}
				},
				/**
				 * 得到当前搜索条件对象
				 */
				getSearchContent:function(){
					var searchContent={};
					if(this.customized_search_select.get("value")){
						searchContent["searchContent"]=this.customized_search_select.get("value");
					}
					if(this.find_select.get("value")){
						searchContent["findContent"]=this.find_select.get("value");
						if(this.find_text.get("value")){
							searchContent["findText"]=this.find_text.get("value");
						}
					}
					if(this.date_select.get("value")){
						searchContent["dateContent"]=this.date_select.get("value");
						searchContent["basedate"]=this.fixed_date.valueNode.value;
						searchContent["rangeContent"]=this.range_select.get("value");
						searchContent["todate"]=this.to_date.valueNode.value;	
						searchContent["basetime"]=this.fixed_time.valueNode.value;
						searchContent["totime"]=this.to_time.valueNode.value;
					}
					searchContent["json"]=dojo.toJson({searchType:this.searchType});
					if(this.searchType==="alert" && this.alertView_history.get("checked")){
						searchContent["alerts"]="history";
					}
					
					return searchContent;
				},
				
				/**
				 * 从后台获取当前grid的排序列信息
				 */
				getSortColumn:function(){
					var name=this.searchType;
					var currentSortColumn=null;
					Util.xhrPost({
						url : window.actionPath + "/search/currentSortColumns",
						content: {searchType:name},				
						handleAs : "json",
						sync:true,
						error:Util.ajaxErrorHandler,
						preventCache : true
					},  dojo.hitch(this, function(json){
						//console.log(json);
						if(json.json==null)
							currentSortColumn=null;
						else
						currentSortColumn=json.json;
					}));
					return currentSortColumn;
				}
				/**
				 * treegrid搜索
				 */
				,treegridSearch:function(groupValue){
					Util.showStandBy();	
					var grid=dijit.byId(this.gridId);
					var model =grid.treeModel;
					var parm=this.getSearchContent();
					parm["groupBy"]=groupValue;
					model.store.url=window.actionPath + "/search/search";
					model.query={};
					model.query=dojo.mixin(model.query,parm);
					//model.store.fetch({serverQuery:parm});
					grid.closeExpando();
					grid.selection.clear();
					grid.setModel(model);
					//console.log(this.getSortColumn());
					this.initSort(this.getSortColumn());
					this.setConnection();
					Util.hideStandBy();	
				}
				,_onKey:function(evt){
					if(evt.charOrCode===dojo.keys.ENTER){
						this.searchWithValidation();
					 }
				},
				//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
				//周华 2012.4.1
				validatorDate:function(){
					var arr = ["运输计划号", "收货人名称","客户数"];
					
					var key = this.find_select.get("displayedValue");
					if(dojo.indexOf(arr,key)!= -1 && this.judgeFunction.isEmpty(this.date_select.get("displayedValue"))){
						
						dijit.showTooltip("扩展字段搜索, 日期不能为空",this.date_select.domNode);
						return false ;
					}
					else {
						dijit.hideTooltip(this.date_select.domNode);
						return true;
					}
				},
				
				validateDate2:function(){
					if(this.todate.style.display=="none")
						return true;
					
					var start = new Date(this.fixed_date.get("displayedValue"));
					var end = new Date(this.to_date.get("displayedValue"));
					if(Math.abs(dojo.date.difference(start,end,"month")) < 6){
						return true;
					}else {
						//dijit.showTooltip("搜索时间跨度不能超过6个月",this.fixed_date.domNode);
						new tms.china.widget.infoMsg.InfoMsg( 
								{
									message:Common.I18N.resourceBundle.SEARCH_MSG_date_error,
								    title:Common.I18N.resourceBundle.nb_public_error,
									type : Info_constant.FAIL
								}
						).show();
						return false;
					}
					
					
					
				},
				//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
				findtextValidator:function(){
					
					if(!this.judgeFunction.isEmpty(this.find_select.get("displayedValue")) 
							&& this.judgeFunction.isEmpty(this.find_text.get("value"))
							  && this.judgeFunction.isEmpty(this.date_select.get("displayedValue"))
							/*(this.find_select.get("displayedValue") && this.find_select.get("displayedValue").trim()!="") 
							&& (!this.find_text.get("value")|| this.find_text.get("value").trim()=="" )
							  && (!this.date_select.get("displayedValue")||this.date_select.get("displayedValue").trim()=="")*/
							  
					){
						dijit.showTooltip("字段不能为空",this.find_text.domNode);
					//	console.log(this.find_text);
						dojo.addClass(this.find_text.domNode,"dijitTextBoxError");
						return false ;
					}
					else {
						dijit.hideTooltip(this.find_text.domNode);
						dojo.removeClass(this.find_text.domNode,"dijitTextBoxError");
						return true;
					}
				},
				
				judgeFunction:{
					isEmpty:function(value){
						//console.log(value);
						if(value==null) return true ;
						else if(typeof(value) ==undefined) return true ;
						else if(value.replace(/^\s*|\s*$/g,'').length === 0) return true ;
						//console.log("hhhhhhhh");
						return false ;
					}
				}
				
			});
			
			return tms.china.widget.search.Search;
		});
