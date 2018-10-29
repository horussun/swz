define("tms/china/widget/searcher/Search",[ "dojo", "dijit", "tms/china/widget/_Widget", "dijit/_Templated","text!tms/china/widget/searcher/templates/Search.html" ], function(dojo, dijit) {
			/**
			 * 这是每个页面的search组件，主要实现对各种数据（订单，运单等）的搜索功能
			 * 
			 */
	
			//加入该widget所需要的dojo widget
			
			dojo.require("dijit.form.Button");
			dojo.require("dijit.form.TextBox");
			
			dojo.declare("tms.china.widget.searcher.Search", [ tms.china.widget._Widget,
					dijit._Templated ], {
				// Path to the template
				templateString : dojo.cache("tms.china.widget.searcher",
						"templates/Search.html"),

				// Set this to true if your widget contains other widgets
				widgetsInTemplate : true,

				//UI strings 定义template中用到的界面的String
				_org:null,
				
				//wedgets的类型
				searchType:null,
				//搜索对应的grid的id
				gridId:null,
				
				flag:false,
				
				searchValues:null,
				
				// Override this method to perform custom behavior during dijit construction.
				// Common operations for constructor:
				// 1) Initialize non-primitive types (i.e. objects and arrays)
				// 2) Add additional properties needed by succeeding lifecycle methods
				// Constructor function. Called when instance of this class is created
				constructor : function(/**Dom Node*/ rootNode) {
					this.inherited(arguments);
					if(rootNode!=null){
						this.searchType=rootNode.type;
						this.gridId=rootNode.gridId;
						this.flag=rootNode.flag;
					}
					if(this.searchType == "SALEORDER"||this.searchType == "SALEORDER2"){
						this._org = "买家";
					}else if(this.searchType == "PURCHASEORDER"){
						this._org = "供应商";
					}else if(this.searchType == "SHIPMENTORDER"){
						this._org = "承运商";
					}
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
					 * 根据searchType不同，显示不同的搜索控件
					 */
					if(this.searchType != "TRANSFERMESSAGE"){
						this.transfer.style.display="none";
					}
					if(this.searchType != "PRODUCT"){
						this.product.style.display="none";
					}
					if(this.searchType == "TRANSFERMESSAGE"||this.searchType == "PRODUCT"){
						this.normal.style.display="none";
					}
					//重搜索
					if(this.flag){
						var restoreData = Nav.getRestoreData();
						var searchValues = dojo.getObject("searchValues", false, restoreData);
						
						if(searchValues){
							if(this.searchType != "TRANSFERMESSAGE"){
								//获得搜索用的值
								this.orderNumber.set("value",dojo.getObject("orderNumber", false, searchValues));
								this.pName.set("value",dojo.getObject("pName", false, searchValues));
								this.oName.set("value",dojo.getObject("oName", false, searchValues));
							}else{
								//获得搜索用的值
								if(searchValues)
								this.oType.set("value",dojo.getObject("oType", false, searchValues));
								this.oSource.set("value",dojo.getObject("oSource", false, searchValues));
								this.oDest.set("value",dojo.getObject("oDest", false, searchValues));
								//set日期控件的值格式必须为：'2011-01-01'
								var ost = dojo.getObject("oStartTime", false, searchValues);
								if(ost!=null&&ost!=""){
									var start = this.dealDateString(ost);
									this.oStartTime.set('value',start);
								}
								var oet = dojo.getObject("oEndTime", false, searchValues);
								if(oet!=null&&oet!=""){
									var end = this.dealDateString(oet);
									this.oEndTime.set('value',end);
								}
							}
						}
						var currentPage = dojo.getObject("currentPage", false, restoreData);
						var sortProp = dojo.getObject("sortProp", false, restoreData);
						if(sortProp){
							this.search(null,currentPage,sortProp);
						}else{
							this.search(null,currentPage);
						}
					}

				},
				//date 传入的格式为'2011-1-1',为何+=符号不让用
				dealDateString: function(dt){
					if(dt==null||dt=="")
						return null;
					var num = dt.split('-');
					var d = num[0]+'-';
					if(num[1].length<2)
						d=d+'0';
					d=d+num[1];
					d=d+'-';
					
					if(num[2].length<2)
						d=d+'0';
					d=d+num[2];
					return d;
				},
				//第一个为click事件，第二个个参数指定第几页，第三个个参数指定排序内容
				search : function(e,currentPage,sortProp){
					var grid  = dijit.byId(this.gridId);
					grid.store.url=window.actionPath + "/search/search";
					this.searchValues = {};
					var json={};
					json["name"]=this.gridId;
					json["searchType"]=this.searchType;
					if(this.searchType == "TRANSFERMESSAGE"){
						json["oType"]=this.oType.value;
						if(json["oType"]=='2'){
							json["searchType"]="TRANSFERMESSAGE2";
						}
						json["oSource"]=this.oSource.value;
						json["oDest"]=this.oDest.value;
						json["oStartTime"]=this.oStartTime.valueNode.value;
						json["oEndTime"]=this.oEndTime.valueNode.value;
						//记录搜索的value
						dojo.setObject("oType", json["oType"], this.searchValues);
						dojo.setObject("oSource", json["oSource"], this.searchValues);
						dojo.setObject("oDest", json["oDest"], this.searchValues);
						dojo.setObject("oStartTime", json["oStartTime"], this.searchValues);
						dojo.setObject("oEndTime", json["oEndTime"], this.searchValues);
						
					}else if(this.searchType == "PRODUCT"){
						json["pNumber"]=this.productID.value;
						json["pName"]=this.productName.value;
					}else{
						json["orderNumber"]=this.orderNumber.value;
						json["pName"]=this.pName.value;
						json["oName"]=this.oName.value;
						if(this.searchType == "SALEORDER2"){
							json["searchType"]="SALEORDER";
							json["isnull"]="0";
						}
						//数据过滤
						//console.debug("zh:"+window.userId);
						if(this.searchType!="SHIPMENTORDER"){
							json["userId"]=window.userId;
						}else{
							if(window.userId==7||window.userId==8||window.userId==9){
				                  json["carriedId"]=window.userId;
							}else{
							      json["userId"]=window.userId;
							}
						}
						//记录搜索的value
						dojo.setObject("orderNumber", json["orderNumber"], this.searchValues);
						dojo.setObject("pName", json["pName"], this.searchValues);
						dojo.setObject("oName", json["oName"], this.searchValues);
					}
					if(currentPage){
						json["currentPage"]=currentPage;
					}
					if(sortProp){
						json["sortProp"]=sortProp;
					}
					
					//console.debug(json);
					var query={
						json:dojo.toJson(json)
					};
					grid.setQuery(query);
				}
				
				
			});
			
			return tms.china.widget.searcher.Search;
		});
