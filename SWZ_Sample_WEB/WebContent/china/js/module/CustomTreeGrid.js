define("tms/china/js/module/CustomTreeGrid", [ "dojo" ], function(dojo) {
	
	dojo.require("dojox.grid.TreeGrid");
	dojo.extend(dojox.grid.cells._Base, {
		defaultValue: ''
	});
	dojo.declare("tms.china.js.module.CustomTreeGrid", dojox.grid.TreeGrid, {
		constructor : function() {
		// console.log("CustomTreeGrid......");
		
		 this.inherited(arguments);
		 this.selectable=true;
		},
		postCreate: function(){			
			this.inherited(arguments);	
			this.connect(this,'onResizeColumn','_onResizeColumn');
		},
		/* @Override */
	    sort: function() {
	        this.closeExpando();
	       // console.log(this.getSortProps());
	        this.serverSort();
	      //  this._refresh();
	        //this.inherited(arguments);
	    },

	    closeExpando: function(identities) {
	        if (identities) {
	            if (dojo.isArray(identities)) {
	                // close multiple expando
	                dojo.forEach(identities, function(identity) {
	                    this._closeExpando(identity);
	                }, this);
	            } else {
	                // close single expando
	                var identity = identities;
	                this._closeExpando(identity);
	            }
	        } else {
	            // close all expando
	            var expandoCell = this.getCell(this.expandoCell);
	            for (var identity in expandoCell.openStates) {
	                this._closeExpando(identity);
	            }
	        }
	    },

	    _closeExpando: function(identity) {
	        var expandoCell = this.getCell(this.expandoCell);

	        if (expandoCell.openStates.hasOwnProperty(identity) === true) {
	            var open = expandoCell.openStates[identity] || false;
	           // if (open === true) {
	                // clean up expando cache
	                this._cleanupExpandoCache(null, identity, null);
	            //}
	        }
	    }
	    ,
	    /**
	     * 服务器排序函数
	     */
	    serverSort:function(){
	    	var model=this.treeModel;
	    	this.clearSortIcon();
	    	//console.log(this.rowCount);
	    	if(this.rowCount==0)return ;
	    	var group=dijit.byId("lfp_option").get("value");
	    	if(model){
	    		var json={};
				json["sort"+0]=this.getSortProps()[0];	
				json["name"]=this.name;		
	    		model.store.url=window.actionPath + "/search/sort";
	    		//model.store.error=Util.ajaxErrorHandler;//cjp
	    		model.query={
		    				json:dojo.toJson(json),
		    				groupBy:group==0?null:group
	    					};
	    		//console.log(model.query);
	    		this.setModel(model);
	    	}
	    },
		/**
		 * 通过field返回Cell对象，如果找不到则返回null
		 */
		getCellByField:function(fieldname){
			if(fieldname==null)return null;
			var cells=this.layout.cells;
			for(var i=0;i<cells.length;i++){
		//		console.log(cells[i]);
				if(cells[i].field==fieldname){
					return cells[i] ;
				}
			}
			return null;
		},
		/**
		 * 设置Cell对象的排序显示
		 */
		setCellSortDisplay:function(cell,index,asc){
			if(cell==null)return ;
			if(asc){
				if(index==1)
					cell.name=cell.name+"<img SRC='/china/images/down1.png' HEIGHT='11' WIDTH='11'>";
				else if(index==2)
					cell.name=cell.name+"<img SRC='/china/images/down2.png' HEIGHT='11' WIDTH='11'>";
				else if(index==3)
					cell.name=cell.name+"<img SRC='/china/images/down3.png' HEIGHT='11' WIDTH='11'>";
				
			}
			else{
				if(index==1)
					cell.name=cell.name+"<img SRC='/china/images/up1.png' HEIGHT='11' WIDTH='11'>";
				else if(index==2)
					cell.name=cell.name+"<img SRC='/china/images/up2.png' HEIGHT='11' WIDTH='11'>";
				else if(index==3)
					cell.name=cell.name+"<img SRC='/china/images/up3.png' HEIGHT='11' WIDTH='11'>";
			}
			//this.update();
		},
		/**
		 * 初始化cell
		 */
		initCellSortDisplay:function(){
			this.sortInfo=0;
				 
			var cells=this.layout.cells;
			for(var i=0;i<cells.length;i++){
				if(cells[i].name.indexOf("<img")!=-1){
					cells[i].name=cells[i].name.substring(0,cells[i].name.indexOf("<img"));
				}
		    }
		},
		/**
		 * 清楚头部的排序的图标
		 */
		clearSortIcon:function(){
			var cells=this.layout.cells;
			for(var i=0;i<cells.length;i++){
				if(cells[i].name.indexOf("<img")!=-1){
					cells[i].name=cells[i].name.substring(0,cells[i].name.indexOf("<img"));
				}
		    }
		},
		_onResizeColumn:function(index,width){
			
			var field=this.layout.cells[index].field;
			//console.log(field+"++++"+width);
			if(field){
				dojo.cookie(window.userId+"_"+this.name+"_"+field, width+"px", {
		            expires: 7
		        });
			}
		}
		

		
	});
	return tms.china.js.module.CustomTreeGrid;
});