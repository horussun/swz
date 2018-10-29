define("tms/china/js/module/CustomQueryReadStore", [ "dojo" ], function(dojo) {
	
	/**
	 * 用于重载dojo1.6源文件中的QueryReadStore，完成自定义功能----对返回的数据进行处理、初始化页面控件等
	 * 20111210 陆锋平：新建
	 */
	dojo.require("dojox.data.QueryReadStore");
	dojo.declare("tms.china.js.module.CustomQueryReadStore", dojox.data.QueryReadStore, {
		name:"shipment"
		,constructor : function() {
		 //console.log("CustomQueryReadStore......");
		 this.inherited(arguments);
		} 
		/**
		 * 重载QueryReadStore中的_filterResponse方法
		 */
	,_filterResponse:function(data,req){
		var result=data.json.result;
		result["numRows"]=data.json.currentRowNum;
		this.trigerInitEvent(data,req);
		return result;
	}
	/**
	 * 得到返回的结果只会触发的初始化函数
	 */
	,trigerInitEvent:function(json,req){
		//console.log(Common.currentpage);
		try{
			if(json.json.searchDescription){	//搜索操作的返回结果	
				//pagebar
				var pagebar=null;
				try{
					pagebar=Common.currentpage.getPageBar(this.name);
					if(pagebar==null) 
						pagebar=Common.currentDialogPage.getPageBar(this.name);
				}catch(e){
					pagebar=Common.currentDialogPage.getPageBar(this.name);
				}
				pagebar.initPageBar(json.json.totalNumber,json.json.pageSize,json.json.searchDescription,json.json.currentPage);
				//sort
//				if(json.json.sort){
//					this.initSort(json.json.sort);
//				}
				//filter
//				this.initFilter(json);
				//select
				if(json.json.result.items){
					this.initSelect(json.json.result.items,req.start);
				}
			}/*else {
				if(json.json.result.items){
					this.initCurrentSelect(json.json.result.items,req.start);
				}
			}*/
		}catch(e){
			console.error("初始化搜索结果错误"+e);
			
		}
	}
	/**
	 * 初始化排序
	 */
//	,initSort:function(sort){
//		    var grid=dijit.byId(this.name);
//		    if(grid.initCellSortDisplay){
//		    	grid.initCellSortDisplay();
//		    }
//		    var sortWidget= Common.currentpage.getSortWidget(this.searchType);
//		   // console.log(sortWidget);
//		    if(sortWidget!=null) sortWidget.clearSortColumn();
//		    if(sort==null)return ;
//		    for(var i=0;i<3;i++){
//				if(sort["sort"+i]==null)break ;
//				var field=grid.getCellByField(sort["sort"+i].attribute);
//				//console.log(field);
//				grid.setCellSortDisplay(field,i+1,sort["sort"+i].descending);
//			}
//		   
//	}
	/**
	 * 初始化过滤器
	 */
	,initFilter:function(json){
		var grid=dijit.byId(this.name);
		if(json.json.totalNumber<=json.json.pageSize && json.json.totalNumber>=2 ){
			//当搜索结果为一页，且总条数大于2个时显示过滤器
			grid.setDisplayOrHiddenFilter(true);
			grid.filterFlag=true;
			grid.doFilter=true;
		}else{
			grid.setDisplayOrHiddenFilter(false);
			grid.filterFlag=false;
			grid.doFilter=false;
		}
	},
	/**
	 * 初始化之前页面的选择条目
	 */
	initSelect:function(items,start){
		var selection=dijit.byId(this.name).selection;	
		var grid=dijit.byId(this.name);
		if(!selection){
			return ;
		}
		selection.clear();
		var start=start || 0;
		//console.log(start);
		if(items && items.length > 0){
			if(Nav.getRestoreData()&&Nav.getRestoreData().selectedInfo){
				//获取备份数据
				var selectedInfo = dojo.getObject("selectedInfo", false, Nav.getRestoreData());
				if(selectedInfo){
					var selected = dojo.getObject("selected", false, selectedInfo);
					if(selected && selected.length>0){
						grid.lastSelelcted=selected;
						//console.debug(selected);
					}
					/*var compareFunc = dojo.getObject("compareFunc", false, selectedInfo);
					if(selected && selected.length>0){										
						dojo.forEach(selected,function(selectedItem){
							//console.log(selectedItem);
							dojo.forEach(items,function(item,index){
								//console.log(item);
								if(compareFunc(selectedItem.i, item)){
									selection.setSelected(start+index, true);
								}
							});
						});
					}*/	
				}
				//完成恢复后，清空备份数据
				Nav.getRestoreData().selectedInfo = null;
			}
		}
	},
	initCurrentSelect:function(items,start){
		//console.log(start+"ddddddd");
		var grid=dijit.byId(this.name);
		var selection=grid.selection;	
		if(!selection){
			console.log("error!!!!no selection");
			return ;
		}
		if(items && items.length > 0){
			grid.selectedRestore(items,start);
		}
		
	},

	/* @Override seValue method */ 
	setValue: function(/* item */ item, /* attribute-name-string */ attribute, 
	                   /* almost anything */ value) { 
	    // summary: See dojo.data.api.Write.set() 

	    // Check for valid arguments 
	    this._assertIsItem(item); 
	  //  this._assert(dojo.isString(attribute)); 
	  //  this._assert(typeof value !== "undefined"); 

	    var success = false; 
	    var _item = item.i; 
	    _item[attribute] = value; 
	    success = true; 

	    return success; // boolean 
	},
	//判断store中是否有值
	isEmpty:function(){
		if(this._items==null||this._items.length<=0)
			return true;
		else
			return false;
	}
	});
	return tms.china.js.module.CustomQueryReadStore;
});