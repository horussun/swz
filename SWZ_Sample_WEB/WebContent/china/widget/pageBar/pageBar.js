define("tms/china/widget/pageBar/pageBar",[ "dojo", "dijit", "tms/china/widget/_Widget", "dijit/_Templated","text!tms/china/widget/pageBar/templates/pageBar.html" ], function(dojo, dijit) {
	/**
	 * 主要用于实现对grid的分页操作的组件
	 * 20111110 陆锋平：新建
	 */
	dojo.require("dijit.Tooltip");
	
	dojo.declare("tms.china.widget.pageBar.pageBar", [ tms.china.widget._Widget,dijit._Templated ], {
		
		// Path to the template
		templateString : dojo.cache("tms.china.widget.pageBar","templates/pageBar.html"),
		
		// Set this to true if your widget contains other widgets
		widgetsInTemplate : true,
		
		//构造参数
	
		currentPage:1,
		pageNumber:1,
		name:null,
		grid:null,
		total:null,
		pageSize:null,
		//构造函数
		constructor : function(rootNode) {
		    this.name=rootNode.name;
		    this.grid=rootNode.grid;
			this.inherited(arguments);
		},
		
		
		postMixInProperties : function() {
			this.inherited(arguments);			
		},
		/**
		 * 每次搜索完成之后初始化pageBar
		 * total：总共多少页
		 * pageSize：每页多少行
		 * searchDescription：当前搜索的描述
		 */
		initPageBar:function(total,pageSize,searchDescription,currentPage){
			//console.log(this.pageNumber);
			//alert("ddd0");
			this.searchDescription.innerHTML=searchDescription;
			this.currentPage=currentPage;
			this.goToPage.set("value",this.currentPage);
			
			if(pageSize<=0) return ;
			if(total==0) {
				//alert("ddd1");
				dojo.removeClass(this.first,"firstbt");
				dojo.removeClass(this.last,"lastbt");
				dojo.removeClass(this.next,"nextbt");
				dojo.removeClass(this.pre,"prebt");
				dojo.addClass(this.first,"firstDisablebt");
				dojo.addClass(this.last,"lastDisablebt");
				dojo.addClass(this.next,"nextDisablebt");
				dojo.addClass(this.pre,"prevDisablebt");
				//alert(this._pageDescription.innerHTML);
				this._pageDescription.innerHTML="";
				this._totalPage.innerHTML="1";
				this._dw.style.display="none";
				return ;
			}
			
			this.total=total;
			this.pageSize=pageSize;
			this.pageNumber=Math.ceil(total/pageSize) ;
			this._totalPage.innerHTML=this.pageNumber;
		//	alert("ddd3");
			if(this.pageNumber>=2){
				dojo.removeClass(this.next,"nextDisablebt");
				dojo.addClass(this.next,"nextbt");
				dojo.removeClass(this.last,"lastDisablebt");
				dojo.addClass(this.last,"lastbt");
				dojo.removeClass(this.first,"firstbt");
				dojo.addClass(this.first,"firstDisablebt");
				dojo.removeClass(this.pre,"prevbt");
				dojo.addClass(this.pre,"prevDisablebt");
				
			}else{
				dojo.removeClass(this.first,"firstbt");
				dojo.removeClass(this.last,"lastbt");
				dojo.removeClass(this.next,"nextbt");
				dojo.removeClass(this.pre,"prebt");
				dojo.addClass(this.first,"firstDisablebt");
				dojo.addClass(this.last,"lastDisablebt");
				dojo.addClass(this.next,"nextDisablebt");
				dojo.addClass(this.pre,"prevDisablebt");
			}
			if(pageSize>total)
			this._pageDescription.innerHTML="(1-"+total+")/"+total;
			else
			this._pageDescription.innerHTML="(1-"+pageSize+")/"+total;	
			
			this._dw.style.display="inline";
		},
	
		postCreate : function() {
			this.connect(this.searchDescription,'onmouseover','_showSearchContent');
			this.connect(this.searchDescription,'onmouseout','_hideSearchContent');
		},
		/**
		 * 显示当前搜索内容的tooltip
		 */
		_showSearchContent:function(e){	
			var msg = this.searchDescription.innerHTML;
			dijit.showTooltip(msg, this.searchDescription);
		},
		/**
		 * 隐藏当前搜索内容的tooltip
		 */
		_hideSearchContent:function(e){
			dijit.hideTooltip(this.searchDescription);
			dijit._masterTT._onDeck=null;
		},
		/**
		 * 去第一页
		 */
		_goFirst :function(){
			if(this.currentPage==1)return ;
			this._postPrams(this.grid, "first");
			this._pageDescription.innerHTML="(1-"+this.pageSize+")/"+this.total;	
			this.currentPage=1;
			dojo.removeClass(this.next,"nextDisablebt");
			dojo.addClass(this.next,"nextbt");
			dojo.removeClass(this.last,"lastDisablebt");
			dojo.addClass(this.last,"lastbt");
			dojo.removeClass(this.first,"firstbt");
			dojo.addClass(this.first,"firstDisablebt");
			dojo.removeClass(this.pre,"prevbt");
			dojo.addClass(this.pre,"prevDisablebt");
			this.goToPage.set("value",this.currentPage);
		},
		/**
		 * 去前一页
		 */
		_goPre:function(){
			if(this.currentPage<=1) return ;
			this._postPrams(this.grid, "previous");
			var start=parseInt(this.pageSize*(this.currentPage-2))+1;
			var end=this.pageSize*(this.currentPage-1);
			this._pageDescription.innerHTML="("+start+"-"+end+")/"+this.total;
			this.currentPage-- ;	
			if(this.currentPage==1){
				dojo.removeClass(this.next,"nextDisablebt");
				dojo.addClass(this.next,"nextbt");
				dojo.removeClass(this.last,"lastDisablebt");
				dojo.addClass(this.last,"lastbt");
				dojo.removeClass(this.first,"firstbt");
				dojo.addClass(this.first,"firstDisablebt");
				dojo.removeClass(this.pre,"prevbt");
				dojo.addClass(this.pre,"prevDisablebt");
			}else
			{
				dojo.addClass(this.first,"firstbt");
				dojo.addClass(this.last,"lastbt");
				dojo.addClass(this.next,"nextbt");
				dojo.addClass(this.pre,"prevbt");
				dojo.removeClass(this.first,"firstDisablebt");
				dojo.removeClass(this.last,"lastDisablebt");
				dojo.removeClass(this.next,"nextDisablebt");
				dojo.removeClass(this.pre,"prevDisablebt");
			}
			this.goToPage.set("value",this.currentPage);
			
		},
		/**
		 * 去下一页
		 */
		_goNext:function(){
			if(this.currentPage>=this.pageNumber)return ;
			this._postPrams(this.grid, "next");
			if(this.currentPage+1==this.pageNumber){
				var start=this.pageSize*this.currentPage+1;			
				this._pageDescription.innerHTML="("+start+"-"+this.total+")/"+this.total;
			}
			else 
			{
				var start=this.pageSize*this.currentPage+1;
				var end=this.pageSize*(this.currentPage+1);
				this._pageDescription.innerHTML="("+start+"-"+end+")/"+this.total;
			}
			this.currentPage++;
			if(this.currentPage==this.pageNumber){
				dojo.addClass(this.first,"firstbt");
				dojo.removeClass(this.last,"lastbt");
				dojo.removeClass(this.next,"nextbt");
				dojo.addClass(this.pre,"prevbt");
				dojo.removeClass(this.first,"firstDisablebt");
				dojo.addClass(this.last,"lastDisablebt");
				dojo.addClass(this.next,"nextDisablebt");
				dojo.removeClass(this.pre,"prevDisablebt");			
			}else
			{
				dojo.addClass(this.first,"firstbt");
				dojo.addClass(this.last,"lastbt");
				dojo.addClass(this.next,"nextbt");
				dojo.addClass(this.pre,"prevbt");
				dojo.removeClass(this.first,"firstDisablebt");
				dojo.removeClass(this.last,"lastDisablebt");
				dojo.removeClass(this.next,"nextDisablebt");
				dojo.removeClass(this.pre,"prevDisablebt");
			}
			this.goToPage.set("value",this.currentPage);
		},
		/**
		 * 去末页
		 */
		_goLast:function(){
			if(this.currentPage>=this.pageNumber)return ;
			this._postPrams(this.grid, "last");
			var start=this.pageSize*(this.pageNumber-1)+1;
			this._pageDescription.innerHTML="("+start+"-"+this.total+")/"+this.total;	
			this.currentPage=this.pageNumber;
			dojo.addClass(this.first,"firstbt");
			dojo.removeClass(this.last,"lastbt");
			dojo.removeClass(this.next,"nextbt");
			dojo.addClass(this.pre,"prevbt");
			dojo.removeClass(this.first,"firstDisablebt");
			dojo.addClass(this.last,"lastDisablebt");
			dojo.addClass(this.next,"nextDisablebt");
			dojo.removeClass(this.pre,"prevDisablebt");	
			this.goToPage.set("value",this.currentPage);
		},
		/**
		 * 去指定页
		 */
		_goToPage:function(){
			var pagenumber=parseInt(this.goToPage.get("value"));
			if(pagenumber<=this.pageNumber && pagenumber>=1 && pagenumber!=this.currentPage){
			this._postPrams(this.grid,"goToPage",pagenumber)  ;
			var start=this.pageSize*(pagenumber-1)+1;
			var end=null;
			if (pagenumber==this.pageNumber) end=this.total;
			else end=this.pageSize*pagenumber;
			this._pageDescription.innerHTML="("+start+"-"+end+")/"+this.total;	
			this.currentPage=pagenumber;	
			if(pagenumber==1){
				dojo.removeClass(this.next,"nextDisablebt");
				dojo.addClass(this.next,"nextbt");
				dojo.removeClass(this.last,"lastDisablebt");
				dojo.addClass(this.last,"lastbt");
				dojo.removeClass(this.first,"firstbt");
				dojo.addClass(this.first,"firstDisablebt");
				dojo.removeClass(this.pre,"prevbt");
				dojo.addClass(this.pre,"prevDisablebt");
			}else if(pagenumber==this.pageNumber){
				dojo.addClass(this.first,"firstbt");
				dojo.removeClass(this.last,"lastbt");
				dojo.removeClass(this.next,"nextbt");
				dojo.addClass(this.pre,"prevbt");
				dojo.removeClass(this.first,"firstDisablebt");
				dojo.addClass(this.last,"lastDisablebt");
				dojo.addClass(this.next,"nextDisablebt");
				dojo.removeClass(this.pre,"prevDisablebt");
			}
			else{
				dojo.addClass(this.first,"firstbt");
				dojo.addClass(this.last,"lastbt");
				dojo.addClass(this.next,"nextbt");
				dojo.addClass(this.pre,"prevbt");
				dojo.removeClass(this.first,"firstDisablebt");
				dojo.removeClass(this.last,"lastDisablebt");
				dojo.removeClass(this.next,"nextDisablebt");
				dojo.removeClass(this.pre,"prevDisablebt");
			}
			}else{
				console.log("error number");
			}
			
		},
		/**
		 * 和后台交互的函数
		 * name:对应分页的类型
		 * cmd:用户操作命令
		 * page：用户去指定页的参数
		 */
		_postPrams:function(name,cmd,page){
			var grid=dijit.byId(name);
			if(grid.selection){
				grid.selection.clear();
			}
			grid.store.url=window.actionPath + "/search/turnPage";
			var query={
					name:name,
					cmd:cmd,
					page:page
			};
			grid.setQuery(query);
			/*Util.showStandBy();
			var deferred=Util.xhrPost({
				url : window.actionPath + "/search/turnPage",
				content:{name:name,cmd:cmd,page:page},
				handleAs : "json",
				error:null,
				preventCache : true
				});
			deferred.addCallback(dojo.hitch(this,this._Callback));*/
		},
		/**
		 * 处理返回值
		 * json:返回值
		 */
		_Callback:function(json){
			console.log(json);
			if(json==null){
				store=null;
				if(this.grid==null)return ;
				dijit.byId(this.grid).setStore(store);
				Util.hideStandBy();
				return ;
			}
			var result=json.json ;
			if(result!=null){
				var store = new dojo.data.ItemFileReadStore( {
					data : result
				});
				if(this.grid==null)return ;
				dijit.byId(this.grid).setStore(store);
				console.log(result);
				Util.hideStandBy();
			}
			else{
				alert(json);
				Util.hideStandBy();
			}	
		}
			
	});
	return tms.china.widget.pageBar.pageBar;
});
