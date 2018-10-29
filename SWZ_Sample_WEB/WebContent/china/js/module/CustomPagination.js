define("tms/china/js/module/CustomPagination", [ "dojo" ], function(dojo) {
	
	dojo.require("dojox.grid.enhanced.plugins.Pagination");
	dojo.declare("tms.china.js.module.CustomPagination", dojox.grid.enhanced.plugins.Pagination, {
		name: "custompagination",
		constructor : function() {
		 //console.log("CustomQueryReadStore......");
		 this.inherited(arguments);
		},
		
		//重新分页函数
		gotoPage: function(page){
			// summary:
			//		Function to handle shifting to an arbirtary page in the list.
			//	page:
			//		The page to go to, starting at 1.
			var totalPages = Math.ceil(this._maxSize / this.pageSize);
			page--;
			if(page < totalPages && page >= 0 && this._currentPage !== page){
				this._currentPage = page;
				var grid=this.grid;
				// this._updateSelected();
				this.grid.store.url=window.actionPath + "/search/turnPage";
				this.query={
						name:grid.name,
						cmd:"goToPage",
						page:page+1,
						pagesize:this.pageSize
				};
				this.grid.setQuery(this.query);
				this.grid.resize();
			}
		},
		changePageSize: function(size){
			if(typeof size == "string"){
				size = parseInt(size, 10);
			}
			var startIndex = this.pageSize * this._currentPage;
			dojo.forEach(this.paginators, function(f){
				f.currentPageSize = this.grid.rowsPerPage = this.pageSize = size;
				if(size >= this._maxSize){
					this.grid.rowsPerPage = this.defaultRows;
					this.grid.usingPagination = false;
				}else{
					this.grid.usingPagination = true;
				}
			}, this);
			var endIndex = startIndex + Math.min(this.pageSize, this._maxSize);
			if(endIndex > this._maxSize){
				this.gotoLastPage();
			}else{
				var cp = Math.ceil(startIndex / this.pageSize);
				if(cp !== this._currentPage){
					this.gotoPage(cp + 1);
				}else{
					var grid=this.grid;
					grid.store.url=window.actionPath + "/search/turnPage";
					grid.query=this.query={
							name:grid.name,
							cmd:"goToPage",
							page:cp + 1,
							pagesize:this.pageSize
					};
					this.grid._refresh(true);
				}
			}
			this.grid.resize();
		}
		
	});
	return tms.china.js.module.CustomPagination;
});
dojox.grid.EnhancedGrid.registerPlugin(tms.china.js.module.CustomPagination);