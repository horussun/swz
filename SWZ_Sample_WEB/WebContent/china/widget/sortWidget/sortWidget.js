define("tms/china/widget/sortWidget/sortWidget",[ "dojo", "dijit", "tms/china/widget/_Widget", "dijit/_Templated","text!tms/china/widget/sortWidget/templates/sortWidget.html" ], function(dojo, dijit) {
	/**
	 * 主要用于实现每个grid数据的后台排序自定义组件
	 * 20111110 陆锋平：新建
	 */
	//加入该widget所需要的dojo widget
	dojo.require("dijit.form.Button");
	dojo.require("dijit.Dialog");
	dojo.require("dijit.form.RadioButton");
	dojo.require("dijit.form.Select");
	dojo.require("dijit.form.FilteringSelect");
	
	dojo.require("dojo.data.ItemFileWriteStore");
		
	dojo.declare("tms.china.widget.sortWidget.sortWidget", [ tms.china.widget._Widget,dijit._Templated ], {
		
		// Path to the template
		templateString : dojo.cache("tms.china.widget.sortWidget","templates/sortWidget.html"),
		
		// Set this to true if your widget contains other widgets
		widgetsInTemplate : true,
		
		//构造参数
		name:null,
		//对应的表
		grid:null,
		gridId:null,
		
		//列的store
		columnStore:null,
		sortColumn:null,
		//构造函数
		constructor : function(rootNode) {
		    this.name=rootNode.name;
		    this.grid=dijit.byId(rootNode.gridId);
		    this.gridId=rootNode.gridId;
		    this.columnStore=new dojo.data.ItemFileWriteStore({url:window.actionPath + "/tableConfig/searchSortColumns?name="+this.name});
		    this.columnStore.fetch({onComplete: dojo.hitch(this,this._delete)});
			this.inherited(arguments);
		},
		/**
		 * 删除matchIdIcon
		 * items :需要操作的items数组
		 * 
		 */
		_delete:function(items){
			for (var i = 0; i < items.length; i++){
				if(items[i].value=="matchIdIcon"){
					 this.columnStore.deleteItem(items[i]);
					 break;
				}
			}
		},
		postMixInProperties : function() {
			this.inherited(arguments);			
		},
		/**
		 * 清空组件的数据
		 */
		emptyVlaue:function(){
			var self=this;
			self.column_sortl_name.set("value","");
			self.column_sortl_order_up.set("checked",true);
			self.column_sort2_name.set("value","");
			self.column_sort2_order_up.set("checked",true);
			self.column_sort3_name.set("value","");
			self.column_sort3_order_up.set("checked",true);
		},
		/**
		 * 生成这个组件的初始化函数
		 */
		init:function(){

			if(this.columnStore!=null){			
				this.column_sortl_name.set("store",this.columnStore);
				this.column_sort2_name.set("store",this.columnStore);
				this.column_sort3_name.set("store",this.columnStore);
			}else alert("colunmStore is null");
		//	this.sortColumn=this.getSortColumn();
			this.sortColumn=this.getSortColumn();
			if(this.sortColumn==null){
				this.emptyVlaue();
				return ;
			}
			
			var self=this;
			for(var count=0;count<3;count++){
				var a=self.sortColumn["sort"+count];
				
				if(count==0){		
					if(a==null || a==undefined) {
						self.column_sortl_name.set("value","");
						self.column_sortl_order_up.set("checked",true);
					} else{
					self.column_sortl_name.set("value",a.attribute);
					if(a.descending==false)
					self.column_sortl_order_up.set("checked",true);
					else
					self.column_sortl_order_down.set("checked",true);
					}
				}
				if(count==1){
					if(a==null || a==undefined) {
						self.column_sort2_name.set("value","");
						self.column_sort2_order_up.set("checked",true);
					}else{
						self.column_sort2_name.set("value",a.attribute);
						if(a.descending==false)
						self.column_sort2_order_up.set("checked",true);
						else
						self.column_sort2_order_down.set("checked",true);
					}
				}
				if(count==2){
					if(a==null || a==undefined) {
						self.column_sort3_name.set("value","");
						self.column_sort3_order_up.set("checked",true);
					}else{
						self.column_sort3_name.set("value",a.attribute);
						if(a.descending==false)
						self.column_sort3_order_up.set("checked",true);
						else
						self.column_sort3_order_down.set("checked",true);
					}	
				}
			}
		},
		
		postCreate : function() {
		},
		/**
		 * 显示组件的函数
		 */
		show:function(){
			this.init();
			this.dialog.show();
		},
		/**
		 * 和后台交互的函数
		 */
		_postPrams:function(json){
		//	Util.showStandBy();
			/*return Util.xhrPost({
				url : window.actionPath + "/search/sort",
				content: Util.prepareAjaxData("sortParam", json),
				handleAs : "json",
				error:function(){
					console.debug("post error");
				},
				preventCache : true
			});*/
			var grid=dijit.byId(this.gridId);
			grid.selectedStore();
			grid.store.url=window.actionPath + "/search/sort";
			var query={
					customSort:true,
					json:dojo.toJson(json)
			};
			grid.setQuery(query);
			this.cancel();
		},
		/**
		 * 处理返回值
		 */
		_Callback:function(json){
		//	Util.hideStandBy();
			this.cancel();
			var self=this;
			self.grid=dijit.byId(this.gridId);
			if(json==null) return ;
			var store = new dojo.data.ItemFileReadStore( {
				data : json.json
			});	
			//console.log(store);
			store.fetch({onComplete:function(){
				self.grid.setStore(store);
				if(self.grid.filterFlag){
					var ft=dojo.fromJson(dojo.cookie(window.userId+"_"+self.grid.name+"_ft"));
					var fr=dojo.cookie(window.userId+"_"+self.grid.name+"_fr");
					if(ft.length>=1)
					self.grid.setFilter(ft,fr);
				}
			}});
			
		},
		/**
		 * 隐藏组件
		 */
		cancel:function(){
			this.dialog.hide();
		},
		/**
		 * 上传数据的函数
		 */
		submit:function(){
			var json={};
			this.grid=dijit.byId(this.gridId);
			var count=0;
			if(this.column_sortl_name.get("value")!="" && this.column_sortl_name.get("value")!=undefined ){
				json["sort"+count]={};
				json["sort"+count].attribute=this.column_sortl_name.get("value");
				json["sort"+count].descending=(this.column_sortl_order_up.get("checked")==true)?false:true;
				count++;
			}
			if(this.column_sort2_name.get("value")!="" && this.column_sort2_name.get("value")!=undefined ){
				json["sort"+count]={};
				json["sort"+count].attribute=this.column_sort2_name.get("value");
				json["sort"+count].descending=(this.column_sort2_order_up.get("checked")==true)?false:true;
				count++;
			}
			if(this.column_sort3_name.get("value")!="" && this.column_sort3_name.get("value")!=undefined ){
				json["sort"+count]={};
				json["sort"+count].attribute=this.column_sort3_name.get("value");
				json["sort"+count].descending=(this.column_sort3_order_up.get("checked")==true)?false:true;
			}
			console.log(json);
			json["name"]=this.name;
			if(json.sort0==null ||json.sort0 == undefined) return ;
			this.sortColumn=json;
			//每个排序都要不同
		    if(json.sort0!=null){
		    	if(json.sort1!=null){
		    		if(json.sort0.attribute==json.sort1.attribute){
		    			alert("the sort columns must be different ");
		    			return ;
		    		}
		    		if(json.sort2!=null){
		    			if(json.sort0.attribute==json.sort2.attribute ||json.sort1.attribute==json.sort2.attribute){
		    				alert("the sort columns must be different ");
			    			return ;
		    			}
		    		}
		    	}
		    }
		    this.grid.initCellSortDisplay();
			for(var i=0;i<3;i++){
				if(json["sort"+i]==null)break ;
				var field=this.grid.getCellByField(json["sort"+i].attribute);
				//console.log(field);
				this.grid.setCellSortDisplay(field,i+1,json["sort"+i].descending);
			}
		    if(this.name=="dedicatedfleet"){
		    	var groupBy=dijit.byId("lfp_option");
				if(groupBy){
					if(groupBy.get("value")!=0){
						var grid=this.grid;
						if(grid){
							if(grid.rowCount==0){ 
								this.cancel();
								return ;
							}
							var model =grid.treeModel;
							if(model){
								model.store.url=window.actionPath + "/search/sort";
					    		model.query={
						    				json:dojo.toJson(json),
						    				groupBy:groupBy.get("value")==0?null:groupBy.get("value")
					    					};
								grid.closeExpando();
								grid.setModel(model);
								this.cancel();
								return ;
							}
						}
					}
				}
		    }
		    /*this.grid.update();
			var deferred=this._postPrams(json);
			deferred.addCallback(dojo.hitch(this,this._Callback));*/
		    //add new
		    this._postPrams(json);
		},
		/**
		 * 清除排序数据
		 */
		clearSortColumn:function(){
			this.sortColumn=null;
		},
		/**
		 * 从后台获取当前grid的排序列信息
		 */
		getSortColumn:function(){
			var name=this.name;
			var currentSortColumn=null;
			Util.xhrPost({
				url : window.actionPath + "/search/currentSortColumns",
				content: {searchType:name},				
				handleAs : "json",
				sync:true,
				preventCache : true,
				error:Util.ajaxErrorHandler
			},  dojo.hitch(this, function(json){
			//	console.log(json);
				if(json.json==null)
					currentSortColumn=null;
				else
				currentSortColumn=json.json;
			}));
			return currentSortColumn;
		}
			
	});
	return tms.china.widget.sortWidget.sortWidget;
});
