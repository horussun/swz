dojo.provide("dojox.grid.MyEnhancedGrid");
dojo.require("dojox.grid.EnhancedGrid");
dojo.require("dijit.Menu");
dojo.require("dojox.widget.PlaceholderMenuItem");
dojo.require("dijit.Tooltip");
dojo.require("dojo.cookie");
dojo.require("dijit.Dialog");
/**
 * 自定义的enhancedGrid
 * 20111111 陆锋平：新建
 */
dojo.declare("dojox.grid.MyEnhancedGrid", dojox.grid.EnhancedGrid, {
	initLayout:null,
	cellToolTip:null,
	paymentToolTip:null,
	paymentToolTipLocation:null,
	doFilter:false,
	celleditField:["destination","origin","sidNumber","noteDisplay","alertDisplay","trailerContainerNumber","departurePlanned","departurePlannedEnd","arrivalPlanned","arrivalPlannedEnd","weight","volume","quantity","arrivalActual","departureActual","originArrival"],
	celleditField_invoice:["voucherPrice"],
	GRID_TYPE:{
	   ORDER:"order",
	   SHIPMENT:"shipment",
	   INVOICE:"invoice",
	   TRACKING:"customerserviceshipment"	   
	},
	filterFlag:false,
	needFilter:false,
	constructor : function() {    
	//	console.log("MyEnhancedGrid构造函数"); 
		this.inherited(arguments);
		this.selectable=true;
		this.spanLabel=true;
		this.rowsPerPage=30;
		//复制cell内容
		
	},
	postCreate: function(){			
	this.inherited(arguments);	
	this.connect(this,'onCellMouseOver','_showCellContent');
	this.connect(this,'onCellMouseOut','_hideCellContent');
	this.connect(this,'onCellDblClick','_cellEdit');
	this.connect(this,'onResizeColumn','_onResizeColumn');
	//this.initCellHead();
	this.setDisplayOrHiddenFilter(this.filterFlag);

	//this.setDisplayOrHiddenFilter(true);
	//console.log(this.plugin("filter"));
	/*//初始化头部菜单
	var menu = new dijit.Menu({
			    name: "columnSelectorMenu"
		 });
	var placeholderMenuItem = new dojox.widget.PlaceholderMenuItem(
		     {
			    label: "GridColumns"
			  });
	menu.addChild(placeholderMenuItem);	
	this.attr("headerMenu", menu);
	this.setupHeaderMenu();
	
	var selRegionMenu=this.createSelectedRegionMenu(this);
   // this.plugin("menus")._setRowMenuAttr(selRegionMenu);
   // this.plugin("menus")._setSelectedRegionMenuAttr(selRegionMenu);
	this.plugin("menus")._setCellMenuAttr(selRegionMenu);*/
	},
	/**
	 * 当用户鼠标移动到cell上时自动浮现tooltip
	 */
	_showCellContent:function(e){
		var msg = e.cellNode.innerHTML;
		if(msg!=null && msg!="" &&msg!=undefined &&msg && e.cellIndex!=0)
		dijit.showTooltip(msg, e.cellNode);
		this.cellToolTip=e.cellNode;
		
	},
	/**
	 * 隐藏tooltip
	 */
	_hideCellContent:function(e){
		if(this.paymentToolTip){
			this.paymentToolTip.close();
		}
		this.paymentToolTipLocation = null;
		dijit.hideTooltip(e.cellNode);
		this.cellToolTip=null;
		dijit._masterTT._onDeck=null;
	},
	/*
	copySelectedContent:function(e){
		var selected=null;
        //IE下获取选择文本
        if(document.selection){
        	document.selection.createRange().execCommand("Copy");     
        }
        //firefox下获取选择文本
        else if(window.getSelection().toString()){
        	  selected=window.getSelection().toString();
        	  console.log(selected);
        	  if(selected!=null)
        	  this.copyToClipboard(selected);
        }
    },
	createSelectedRegionMenu :function(resultsGrid)
    {     	
        var selRegionMenu = new dijit.Menu({name:"selectedRegionMenu"});
        selRegionMenu.addChild(new dijit.MenuItem({label:"Copy",  iconClass: "dijitEditorIcon dijitEditorIconCopy", onClick:dojo.hitch(resultsGrid,resultsGrid.copySelectedContent)}));
        selRegionMenu.startup();
        return selRegionMenu;
    },
    copyToClipboard :function(txt) {
        if(window.clipboardData) {
          window.clipboardData.clearData();
          window.clipboardData.setData("Text", txt);
        } else if(navigator.userAgent.indexOf("Opera") != -1) {
         window.location = txt;
        } else if (window.netscape) {
         try {
          netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
         } catch (e) {
        	 alert("被浏览器拒绝！请在浏览器地址栏输入'about:config'并回车然后将'signed.applets.codebase_principal_support'设置为'true'");
         }
         var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
         if (!clip)
          return;
         var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
         if (!trans)
          return;
         trans.addDataFlavor('text/unicode');
         var str = new Object();
         var len = new Object();
         var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
         var copytext = txt;
         str.data = copytext;
         trans.setTransferData("text/unicode",str,copytext.length*2);
         var clipid = Components.interfaces.nsIClipboard;
         if (!clip)
          return false;
         clip.setData(trans,null,clipid.kGlobalClipboard);
        }
       } ,*/
		
	/*
	 * 重写是否能够排序。如果item中无值，则不让排序
	 */
	canSort: function(inSortInfo){
		// summary:
		//		Overwritten
		if(this.store.isEmpty())
			return false;
		else
			return true;
	},
       /**
        * 重写方法用于进行服务器排序
        */
       setSortInfo: function(inSortInfo){

    	   this.initCellSortDisplay();
//    	   var sortWidget= Common.currentpage.getSortWidget(this.name);
//			if(sortWidget!=null) sortWidget.clearSortColumn();
    	   if(this.canSort(inSortInfo)){
    		   
				this.sortInfo = inSortInfo;
				this.selectedStore();
				this.newServerSort() ;
			}
		},
		/**
		 * 清楚sort数据
		 */
		clearSortInfo:function(){
			this.sortInfo=0;
			this.update();
		},
		/**
		 * 服务器排序函数
		 */
		serverSort:function(){
			//console.log(this.getSortProps());
			var self=this;
			var json={};
			json["sort"+0]=this.getSortProps()[0];	
			json["name"]=this.id;
			Util.xhrPost({
					url : window.actionPath + "/search/sort",
					content: Util.prepareAjaxData("sortParam", json),
					handleAs : "json",
					sync:true,
					preventCache : true
				}, dojo.hitch(this, function(json){//successful	
					if(json==null) return ;
					//console.log("successful");
					var store = new dojo.data.ItemFileReadStore( {
						data : json.json
					});	
					store.fetch({onComplete:function(items){
						self.setStore(store);
						try{
						self.selectedRestore(items);
						}catch(e){
							console.log(e);
						}
						if(self.filterFlag){
							var ft=dojo.fromJson(dojo.cookie(window.userId+"_"+self.id+"_ft"));
							var fr=dojo.cookie(window.userId+"_"+self.id+"_fr");
							if(ft.length>=1)
							self.setFilter(ft,fr);
						}						
					}});
					
				}),dojo.hitch(this, function(json){
					console.debug("error");//failure
					Util.hideStandBy();
				})
				);
			
		},
		newServerSort:function(){
			this.store.url=window.actionPath + "/search/sort";
			var json={};
			json["sort"+0]=this.getSortProps()[0];
			json["name"]=this.id;
			var query={
				json:dojo.toJson(json)
			};
			this.setQuery(query);
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
		 * isDisplay：true ：显示
		 * isDisplay:false :隐藏
		 */
		setDisplayOrHiddenFilter:function(isDisplay){
			var filter=this.plugin("filter");
			if(filter==null || filter==undefined) return ;
			if(isDisplay){
				if(dojo.isIE)//由于css问题这里区分IE和FireFox
					this.plugin("filter").filterBar.set("style",{display:"block"});				
				else
					this.plugin("filter").filterBar.set("style",{display:null});
			}else{
				filter.filterBar.set("style",{display:"none"});
			}
		},
		/**
		 * 相应grid的双击事件,改成双击显示详细信息
		 */
		_cellEdit:function(e){
			console.log(e.grid.name);
			//=============2011.12.01马江龙：双击sid编辑订单=============
			
			
		},
		/**
		 * 处理除了note alert之外的cell
		 */
		_doEditCell:function(type,oid,field,rowIndex){
			if(type==this.GRID_TYPE.ORDER || type==this.GRID_TYPE.SHIPMENT){
			  /* var cellEditDialog=dijit.byId("dialog");
			   cellEditDialog.set("title",this.getCellByField(field).name?this.getCellByField(field).name:"");
			   cellEditDialog.set("href",window.actionPath+"/celledit/init?type="+type+"&oid="+oid+"&field="+field);
			   cellEditDialog.show();*/
				Nav.showDialog(Nav.CELLEDIT_PAGE,"tms.china.celledit.CellEditPage",{type:type,oid:oid,field:field,rowIndex:rowIndex},this.getCellByField(field).name?this.getCellByField(field).name:"");
			 }
		},
		/**
		 * 初始化可双击的头部
		 */
		initCellHead2:function(){
			var self=this;
			var cells=this.layout.cells;
			for(var i=1;i<cells.length;i++){

				if(dojo.indexOf(this.celleditField,cells[i].field)>=0 || (cells[i].field.indexOf("billRefNumbers")>=0)){				
					var headId=self.id+"Hdr"+i;
					if(dojo.byId(headId)!=null && dojo.byId(headId)!=undefined)
					dojo.addClass(headId,"doubleclickcell");
				}

			}
		},
		/*initCellHead:function(){
			var self=this;
			dojo.forEach(this.celleditField,function(field){
				var idx=self.getIdxByField(field);
				if(idx==-1)return ;
				var headId="gridHdr"+idx;
				//console.log(dojo.byId(headId));
				dojo.addClass(headId,"doubleclickcell");
				//console.log(dojo.byId(headId));
			});
		},*/
		/**
		 * 通过field获得Idx
		 */
		getIdxByField:function(field){
			if(field==null)return -1;
			var cells=this.layout.cells;
			for(var i=0;i<cells.length;i++){
		//		console.log(cells[i]);
				if(cells[i].field==field){
					return i;
				}
			}
			return -1;
		},
		/**
		 * 重修destroy方法
		 */
		destroy:function(){
			  if(this.cellToolTip);{
			  dijit.hideTooltip(this.cellToolTip); 
			  }
			  if(this.paymentToolTip){
				  this.paymentToolTip.close();
			  }
			  this.inherited(arguments);	
		},
		_onResizeColumn:function(index,width){
			
			var field=this.layout.cells[index].field;
			//console.log(field+"++++"+width);
			if(field){
				dojo.cookie(window.userId+"_"+this.id+"_"+field, width+"px", {
		            expires: 7
		        });
			}
			if(this.id=="order"){
				this.initCellHead2();
			}
			
		},
		/**
		 * 在排序之前记录当前的选中信息
		 */
		selectedStore:function(){
			if(this.selection){
				var selected=this.selection.getSelected();
				if(selected && selected.length>0){
					this.lastSelelcted=selected;
				}else {
					this.lastSelelcted=this.lastSelelcted||[];
				}
			}else {
				this.lastSelelcted=[];
			}
			this.selection.clear();
			
		},
		
		/**
		 * 排序后重新选中之前的记录
		 * items：排序后返回的记录
		 */
		selectedRestore:function(items,start){
			
			var createComparefunctionByName=function(id){
				
				return  function(org1, org2){
					var org1RefId,org2RefId;
					/*
					if(id==="order"){
						org1RefId = dojo.getObject("oid", false, org1);
						org2RefId = dojo.getObject("oid", false, org2);
					}*/			
					
					org1RefId = dojo.getObject("OID", false, org1);
					org2RefId = dojo.getObject("OID", false, org2);
					
					if(org1RefId === org2RefId){
						return true;
					}else{
						return false;
					}
				};
				
			};
			var start=start || 0;
			var selection= this.selection;
			if(this.lastSelelcted && this.lastSelelcted.length>0 && items && items.length>0){
				var comparefun=createComparefunctionByName(this.id);
				dojo.forEach(this.lastSelelcted,function(selecteditem){
					/*console.log(selecteditem);
					console.log(items);*/
					for(var i=0;i<items.length;i++){
						if(comparefun(selecteditem.i,items[i].i)){
							selection.setSelected(start+i,true);
						}
					}
				});
			}
		},
			
		/**
		 * 重载此方法
		 */
		_fetch: function(start, isRender){
			// summary:
			//		Overwritten, see DataGrid._fetch()
			if(this.items){
				return this.inherited(arguments);
			}
			start = start || 0;
			if(this.store && this.store.url &&  !this._pending_requests[start]){
				if(!this._isLoaded && !this._isLoading){
					this._isLoading = true;
					this.showMessage(this.loadingMessage);
				}
				this._pending_requests[start] = true;
				try{
					var req = {
						start: start,
						count: this.rowsPerPage,
						query: this.query,
						sort: this.getSortProps(),
						queryOptions: this.queryOptions,
						isRender: isRender,
						onBegin: dojo.hitch(this, "_onFetchBegin"),
						onComplete: dojo.hitch(this, "_onFetchComplete"),
						onError: dojo.hitch(this, "_onFetchError")
					};
					this._storeLayerFetch(req);
				}catch(e){
					this._onFetchError(e, {start: start, count: this.rowsPerPage});
				}
			}
			return 0;
		},
		/**
		 * 重载DataGrid中的_onFetchComplete方法
		 */
		_onFetchComplete: function(items, req){
			this.inherited(arguments);
			
			if(this.id=="order" ){
				this.initCellHead2();
			}
			/*try{
				if(this.filterFlag && this.needFilter){
					var ft=dojo.fromJson(dojo.cookie(window.userId+"_"+this.name+"_ft"));
					var fr=fr=dojo.cookie(window.userId+"_"+this.name+"_fr");
					if(ft && fr){
						console.log("do filter");
						this.setFilter(ft,fr);
					}
				}
			}catch(e){
				console.error(e);
			}*/	
			this.triggerFilterEvent(items,req.start);
		},
		/**
		 * 重载DataGrid中的_refresh方法
		 */
		/*_refresh: function(isRender){
			try{
				if(this.filterFlag){ //如果需要过滤
					var ft=dojo.fromJson(dojo.cookie(window.userId+"_"+this.name+"_ft"));
					var fr=fr=dojo.cookie(window.userId+"_"+this.name+"_fr");
					if(ft && fr){
					  var exprs =this.getExprs(ft,fr);
					  var filter=this.layer("filter");
						  filter.filterDef(exprs);
						  this.plugin("filter").filterBar.toggleClearFilterBtn(false);
					}		
				}
			}catch(e){
				console.error(e);
			}
			this.inherited(arguments);
		},*/
		/**
		 * 通过从cookie中找到存储的过滤规则构造exprs
		 */
		getExprs: function(rules, ruleRelation){
			rules = rules || [];
			if(!dojo.isArray(rules)){
				rules = [rules];
			}
			var filterDefDialog=this.plugin("filter").filterDefDialog;
			var exprs = dojo.map(rules, filterDefDialog.getExprForCriteria, filterDefDialog);
			exprs = filterDefDialog.builder.buildExpression(exprs.length == 1 ? exprs[0] : {
				"op": ruleRelation,
				"data": exprs
			});
			filterDefDialog._savedCriterias=rules;
			return exprs;
		},
		/**
		 * 搜索完成后触发，根据搜索结果确定是否需要做过滤
		 */
		triggerFilterEvent:function(items,start){
			if(this.doFilter){//判断是否需要做过滤操作
				this.doFilter=false;
				var ft=dojo.fromJson(dojo.cookie(window.userId+"_"+this.id+"_ft"));
				var fr=dojo.cookie(window.userId+"_"+this.id+"_fr");
				if(ft && fr){
				  var exprs =this.getExprs(ft,fr);
				  var filter=this.layer("filter");
					  filter.filterDef(exprs);
					  this.plugin("filter").filterBar.toggleClearFilterBtn(false);
					  this.plugin("filter").filterDefDialog._criteriasChanged=true;
					  this.store.url=window.actionPath + "/search/doFilter";
					  var query={
						name:this.id
					  };
					  this.setQuery(query);
				}else {
					this.selectedRestore(items, start);
				}	
			}else{
				this.selectedRestore(items, start);
			}
		},
		onFetchError:function(err, req){
		}
});