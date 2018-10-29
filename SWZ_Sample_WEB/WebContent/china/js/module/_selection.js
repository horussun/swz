define("tms/china/js/module/_selection", [ "dojo" ,"tms/china/js/module/_grid"], function(dojo,grid) {
	dojo.declare("tms.china.js.module._selection", [tms.china.js.module._grid], {
		constructor : function() {

		},
		
		SelectionState : {
			NONE:-1,
			SINGLE:0,
			MULTIPLE:1
		},

		// 判断grid的行是否有被选中
		// 如果设置了默认的grid，则grid参数可以忽略
		isSelected : function(grid) {
			var isSel = false;
			grid = this._getGrid(grid);
			if (grid) {
				if (grid.selection.getSelected().length > 0) {
					isSel = true;
				}
			}
			return isSel;
		},
		
		getSelectionState : function(grid) {
			var state = this.SelectionState.NONE;
			grid = this._getGrid(grid);
			if (grid) {
				var t= grid.selection.getSelected().length-1;
				if (t==0) {
					state = this.SelectionState.SINGLE;
				}
				else if(t>0){
					state = this.SelectionState.MULTIPLE;
				}
				else if(t<0){
					state = this.SelectionState.NONE;
				}
			}
			return state;
		},
		
		getSelectionIDs : function(name,grid){
			grid = this._getGrid(grid);
			var items=grid.selection.getSelected();
			var ids=new Array();
			for(var i=0;i<items.length;++i){
				ids[i]=grid.store.getValue(items[i],name);
			}
			return ids;
		},

		// 根据grid和属性名，得到属性的value
		// 如果设置了默认的grid，则grid参数可以忽略
		getSelectedID : function(name, grid) {
			var selectedItem=this.getSelectedItem(grid);
			var id;
			if (selectedItem) {
				id = this._getGrid(grid).store.getValue(selectedItem,name);
			}
			return id;
		},
		
		getSelectedItem : function(grid){
			grid = this._getGrid(grid);
			var item;
			if(grid){
				item = grid.selection.getSelected()[0];
			}
			return item;
		},
		
		//只对write store有效
		deleteSelectedItem : function(grid){
			grid = this._getGrid(grid);
			grid.store.deleteItem(this.getSelectedItem(grid));
			grid.render();
		}
	});
	return tms.china.js.module._selection;
});