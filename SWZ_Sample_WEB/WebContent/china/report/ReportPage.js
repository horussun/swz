define("tms/china/report/ReportPage",[ "dojo", "tms/china/widget/_Page" ],function(dojo, Page) {
	/**
	 *  报表页面所使用的方法
	 */
	dojo.declare("tms.china.report.ReportPage",[ tms.china.widget._Page ],{
		constructor : function(json_param) {
			this.name = "report";
		},
		open:null,

		/**
		 * Add required dojo classes
		 */
		addRequired : function() {
			//need widegt
			dojo.require("dojo.data.ItemFileReadStore");
			dojo.require("dijit.form.Button");
			dojo.require("dijit.layout.BorderContainer");
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.Tree");
		},
		
		/**
		 * init page
		 */
		initPage : function() {
			//console.log("start");
			this.open = false;
			/*
			var exbutton = new dijit.form.Button({
				iconClass: 'dijitEditorIcon dijitEditorIconRightToLeft',
				showLabel: false,
	            onClick: function() {
					var n = dijit.byId("left").domNode;
					dojo.animateProperty({
						node:n,
						duration:100,
						properties: { width: { end: (this.open ? "30" : "180"), units:"px" } },
						onEnd: function(){
							this.open = !this.open;
							dijit.byId("borderContainer").layout();
						}
					}).play(1);
	            }
			}).placeAt("expandButton");
			*/
			
			var store = new dojo.data.ItemFileReadStore({
				data: this.getCognosTree()
            });
			
			var treeModel = new dijit.tree.TreeStoreModel({
                store: store,
                query: {
					title: '*'
                },
                childrenAttrs: ["items"],
                mayHaveChildren: function(item){
                	var items = store.getValues(item, "items");
                	return items.length > 0;
                }
            });
			
			var cgTree = new dijit.Tree({
                model: treeModel,
                showRoot: false,
                persist: false,
                autoExpand: false,
                openOnDblClick: true,
                getTooltip: function(item){
					return item.title;
				},
				onClick: function(item) {
					dojo.byId("ibody").src = item.url;
				}
            }).placeAt("cognosTree");
	        
	        dojo.query("span.dijitTreeLabel").forEach(function(node, i){
				dojo.attr(node.parentNode.parentNode,"title",node.firstChild.nodeValue);
	        });
			
			dojo.publish("PageLoadComplete", ["ReportPage Load Completed"]);
		}
		
		//显示左侧报表树形结构
		,getCognosTree: function() {
			var returnString = {identifier: "id",label: "title",items: [{id: 0,title: "报表",url: "",items: [{id: 1,title: "报表目录读取异常",url: "",items: []}]}]};
			Util.xhrPost({
				url : window.actionPath + "/report/getCognosTree",
				content: null,				
				handleAs : "json",
				sync:true,
				preventCache : true,
				error: Util.ajaxErrorHandler //cjp
			},dojo.hitch(this, function(json){
			    //console.debug(json);
				if(json.json == null) {
				} else {
					returnString = json.json;
				}
			}));
			return returnString;
		}

	});

	return tms.china.report.ReportPage;
});