define("tms/china/js/module/_grid", [ "dojo" ], function(dojo) {
	dojo.declare("tms.china.js.module._grid", null, {
		constructor : function() {
			dojo.require("dojo.data.ItemFileWriteStore");
			dojo.require("dojo.data.ItemFileReadStore");
		},
		//获取layout
		getLayout : function(type){
			var layout=null;
			dojo.xhrPost({
				url : window.actionPath + "/tableConfig/searchGridColumns",
				content: {name:type},
				handleAs : "json",
				sync : true,
				/*error:function(){
					console.debug("post error");
				},*/
				error:Util.ajaxErrorHandler,
				load : function(json){
					if(json){
						layout=json.items;
					}
				},
				preventCache : true
			});
			;
			return this.updateLayout(layout,type);
		},
		updateLayout:function(layout,type){
			if(layout){
				var width;
			/*	console.log(layout);
				console.log("_______________");*/
				dojo.forEach(layout,function(item){
					width=dojo.cookie(window.userId+"_"+type+"_"+item.field);
					if(width){
						item.width=width;
					}
				});
				/*console.log("++++++++++++");
				console.log(layout);*/
			}
			return layout;
		},
		
		// 清空grid
		/**
		 * args={
		 * 	grid: //要清空的grid，如果设置了默认的grid，则可不填
		 *  useItemFileWriteStore: //选择使用useItemFileWriteStore还是useItemFileReadStore，默认为false
		 * }
		 */
		clearGrid : function(args) {
			var eptStore;
			var grid;
			var useItemFileWriteStore;
			if(args){
				grid=args.grid;
				useItemFileWriteStore=args.useItemFileWriteStore;
			}
			if (useItemFileWriteStore) {
				eptStore = new dojo.data.ItemFileWriteStore( {
					data : {
						items : []
					}
				});
			}
			else{
				eptStore = new dojo.data.ItemFileReadStore( {
					data : {
						items : []
					}
				});
			}
			this.updateGrid(eptStore, grid);
		},

		// 显示grid
//		showGrid : function(args, target) {
//			var grid = new dojox.grid.MyEnhancedGrid(args).placeAt(target);
//			grid.startup();
//			return grid;
//		},
		showGrid : function(store, layout, plugins,gridContainerId,gridId) {
			console.debug("showGrid"+gridId);
			try {
				if(dijit.byId(gridId)){
					var grid = dijit.byId(gridId);
					grid.setStore(store);
					grid.startup();
					return grid;
				}
				var grid = new dojox.grid.MyEnhancedGrid( {
					id : gridId,
					store : store,
					//name field are not used any more, we should all use id as identify
					name: "gridName",
					structure : layout,
					rowSelector : '0px',
					plugins : plugins,
					escapeHTMLInData : false,
					selectionMode : "single",
					columnReordering : true
				}).placeAt(gridContainerId);

				grid.startup();
				return grid;
			} catch (err) {
				console.error(err);
			}
		},

		// 更新指定的grid
		updateGrid : function(store, grid) {
			grid = this._getGrid(grid);
			if (grid) {
				grid.setStore(store);
			}
		},

		// 设置默认的grid
		setDefGrid : function(grid) {
			this._fields._grid = grid;
			this._fields._hasDefGrid = true;
		},

		// 是否设置了默认的grid
		hasDefGrid : function() {
			return this._fields._hasDefGrid;
		},

		// 获取默认的grid
		getDefGrid : function() {
			return this._fields._grid;
		},

		// 决定作用的grid
		_getGrid : function(grid) {
			if (this.hasDefGrid() && !grid) {
				grid = this.getDefGrid();
			}
			return grid;
		},

		// 字段
		_fields : {
			_grid : null,
			_hasDefGrid : false
		}
	});
	return tms.china.js.module._grid;
});