define("tms/china/js/module/_order",[ "dojo", "tms/china/widget/_Page","tms/china/js/module/_selection"],function(dojo, Page) {
	/**
	 * This is javascript for oder page
	 */		
	dojo.declare("tms.china.js.module._order",
							[ tms.china.widget._Page,tms.china.js.module._selection ],
							{
								
								//grid : null,
								name:null,
								pageBar:null,
								grid:null,
								gridId:null,
								searchPart:null,
								
								pageContainer:null,
								gridContainer:null,
								type:null,
								
								addRequired : function() {
									//请求所需的控件
									
									dojo.require("dijit.form.Button");
									dojo.require("dijit.form.FilteringSelect");
									
									dojo.require("dojox.grid.MyEnhancedGrid");
									dojo.require("dojox.grid.enhanced.plugins.Menu");
									dojo.require("dojox.grid.enhanced.plugins.Selector");
									dojo.require("dojox.grid.enhanced.plugins.IndirectSelection");
									dojo.require("dojox.grid.enhanced.plugins.MyNestedSorting");
									dojo.require("dojox.grid.enhanced.plugins.Filter");
									
									dojo.require("tms.china.widget.pageBar.pageBar");
									dojo.require("tms.china.js.module.CustomQueryReadStore");
									dojo.require("tms.china.widget.infoMsg.InfoMsg");
									dojo.require("tms.china.widget.searcher.Search");
								},
								initPage : function() {
									
									// 1. Call super class's same method to init
									// common tasks
									this.inherited(arguments);
							
									this.pageBar=new tms.china.widget.pageBar.pageBar({name:"pageBarName",grid:this.gridId}).placeAt(this.pageContainer);
									var layout=this.getLayout(this.type);
									var store = new tms.china.js.module.CustomQueryReadStore({
										name:this.gridId,
										requestMethod:"post"
									});
												
									dojo.byId(this.gridContainer).style.height=window.screen.height*0.45+"px";
									this.setDefGrid(this.showGrid(store, layout, TestPageContext.listPlugins,this.gridContainer,this.gridId));//grid显示
									this.grid = this.getDefGrid();
									
									//是否要重搜索
									var flag = false;
									if(Nav.getRestoreData()){
										flag = true;
									}
									//搜索控件
									this.searchPart = new tms.china.widget.searcher.Search(
											{type:this.type,gridId:this.gridId,flag:flag}).placeAt("searchpart");
									//进入页面，默认搜索一下
									this.searchPart.search();
									
									dojo.connect(this.grid,"onSelectionChanged",this,this.grayButton);
									this.grid._cellEdit=this._cellEdit;
									dojo.publish("PageLoadComplete",[ "test Page Load Completed" ]);
								},
								//存储搜索信息
								getRestoreData: function(){
									var restoreData = Nav.getRestoreData();
									//为了保存排序的信息
									if(restoreData==null)
										restoreData = {};
									var selectedInfo = {};
									
									var selected = this.grid.get("selection").getSelected();
									
									dojo.setObject("gridId", this.gridId, selectedInfo);
									//保存选中的那条记录
									dojo.setObject("selected", selected, selectedInfo);
									dojo.setObject("selectedInfo", selectedInfo, restoreData);
									//保存页码
									if(this.gridId == "gridID2"){
										//这里的getPageBar是transferlist页面的
										dojo.setObject("currentPage", this.getPageBar(this.gridId).currentPage, restoreData);
									}else{
										dojo.setObject("currentPage", this.getPageBar().currentPage, restoreData);
									}
									
									//保存搜索栏中的信息
									dojo.setObject("searchValues", this.searchPart.searchValues,restoreData);
									//保存排序的信息
									if(this.grid.getSortProps()!=null){
										dojo.setObject("sortProp", this.grid.getSortProps()[0], restoreData);
									}
									return restoreData;
								},
								//automatic called by customQueryReadStore.js removed now 
								getPageBar : function(type) {
									return this.pageBar;
								}
							});

			return tms.china.js.module._order;
		});

