dojo.provide("dojox.grid.enhanced.plugins.MyNestedSorting");

dojo.require("dojox.grid.enhanced.plugins.NestedSorting");

dojo.declare("dojox.grid.enhanced.plugins.MyNestedSorting", dojox.grid.enhanced.plugins.NestedSorting, {
	
	name: "myNestedSorting",
	
	constructor: function(){
		this.inherited(arguments);
		this.grid.getSortProps =dojo.hitch(this, 'getSortProps');//覆盖，不让它进行客户端排序	
	},
	onStartUp: function(){
		//overwrite base Grid functions
		this.inherited(arguments);
	//	this.connect(this.grid, 'onHeaderCellClick', '_onHeaderCellClick');
	},
	_onHeaderCellClick:function(e){
		var cellIdx = e.cell.index;
		this._focusRegion(e.target);
		if(dojo.hasClass(e.target, 'dojoxGridSortBtn')){
			this._onSortBtnClick(e);;
			dojo.stopEvent(e);
			this._focusRegion(this._getCurrentRegion());
		}
		
	},
	/**
	 * 重写_doSort方法，实现服务器端排序
	 */
	_doSort: function(cellIdx){
		if(!this._sortData[cellIdx] || !this._sortData[cellIdx].order){
			this.setSortData(cellIdx, 'order', 'asc');	//no sorting data
		}else if(this.isAsc(cellIdx)){
			this.setSortData(cellIdx, 'order', 'desc');	//change to 'desc'
		}else if(this.isDesc(cellIdx)){
			this.removeSortData(cellIdx); //remove from sorting sequence
			this._updateSortDef();
			this._initSort(false);	
			return;
		}
		this._updateSortDef();
		this._initSort(false);
		this._initFocus();
		console.log(this._sortDef);
		var json={};
		for(i=0;i<this._sortDef.length;i++){
			if(i>2) break;
			json["sort"+i]=this._sortDef[i];			
		}
		if(this._sortDef.length>=1 && this._sortDef.length<=3){
			json["name"]=this.grid.name;
			//this.grid.set("store",null);
			var deferred=this._postSortParam(json);
			deferred.addCallback(dojo.hitch(this,this._sortCallback));
		}
		
		//	this.grid.sort();
	/*	console.log(this.grid.store);
		this.grid.set("store",null);
		console.log(this.grid.store);*/
		
	},
	_postSortParam:function(json){
		Util.showStandBy();
		return dojo.xhrPost({
			url : window.actionPath + "/search/sort",
			content: Util.prepareAjaxData("sortParam", json),
			handleAs : "json",
			error:function(){
				console.debug("post error");
			},
			preventCache : true
		});
	},
	_sortCallback:function(json){
		Util.hideStandBy();
		
		if(json==null) return ;
		var store = new dojo.data.ItemFileReadStore( {
			data : json.json
		});	
		console.log(store);
		this.grid.setStore(store);
		//this.grid.store=store;
		//this.grid.render();
		
	},
	getSortProps: function(){
		// summary:
		//		重写。。不让grid再进行客户端排序
		return null;
	},
	/**
	 * 重写清除之前搜索
	 */
	clearSort: function(){
		this.inherited(arguments);
		this._initSort(true);	
	},
	/**
	 * 设置排序列
	 */
	setSortDef:function(sortDef){
		this._sortDef=sortDef;
		this._initSort(true);
		console.log(this._sortDef);
	},
	getSortDef:function(){
		return this._sortDef;
	},
	destroy: function(){
		this.inherited(arguments);
	}
	
});
dojox.grid.EnhancedGrid.registerPlugin(dojox.grid.enhanced.plugins.MyNestedSorting);
