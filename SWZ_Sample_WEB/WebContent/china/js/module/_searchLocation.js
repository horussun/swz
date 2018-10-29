define("tms/china/js/module/_searchLocation", [ "dojo" ], function(dojo) {
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.declare("tms.china.js.module._searchLocation", null, {
		/*
		 * args Object{inputId,selectId,buttonId,locType,searchType}
		 * 
		 */
		enableSearch : function(args){
			if(args.inputId&&args.selectId&&args.buttonId&&args.locType){
				dojo.connect(dijit.byId(args.inputId),"onKeyPress",this,
						function(e){
							this.searchLocation(e,args);
						}
					);
				dojo.connect(dijit.byId(args.buttonId),"onClick",this,
						function(e){
							var data={
								"text":	dijit.byId(args.inputId).value,
								"locType":args.locType,
								"searchType":args.searchType
							};
							this.saveLocations(data);
						}
					);
			}
		},

		/*
		 * data Object{text,locType,searchType}
		 */
		saveLocations : function(data) {
			var args = {
				url : window.actionPath + "/bill/saveSearch",
				content : Util.prepareAjaxData(null,data),
				handleAs : "json",
				error:Util.ajaxErrorHandler,
				preventCache : true
			};
			var deferred = dojo.xhrPost(args);

		},

		searchLocation : function(e,args) {		
			if (e.keyCode == dojo.keys.ENTER && e.target.value != "") {
				var target=dijit.byId(args.selectId);
				var text = e.target.value;
				var criteria={
					"text":text,
					"locType":args.locType,
					"searchType":args.searchType
				};
				this.startSearch(target,criteria,false);
				this.showLoading(target);
			}
		},
		
		showLoading : function(target){
			var data={
				"label":"name",
				"identifier":"value",
				"items":[{
					"name": Common.I18N.resourceBundle.WEB_BASE_WAIT_loadingMsg,
					"value": ""
				}]
			};
			this._updateLocation(data, target);
		},
		
		/*
		 * criteria Object{text,locType,SearchType}
		 */
		startSearch : function(target,criteria,sync){
			if(criteria.text!=""){
				var args = {
					url : window.actionPath + "/bill/searchLocation",
					content : Util.prepareAjaxData(null,criteria),
					handleAs : "json",
					preventCache : true,
					sync : sync
				};
				var deferred = dojo.xhrPost(args);
				deferred.addCallback(dojo.hitch(this, function(data){this._updateLocation(data,target);}));
			}
		},

		_updateLocation : function(data,widget) {
			if (data.items.length <= 0) {
				data.items[0] = {
					name : Common.I18N.resourceBundle.NB_BILL_SEARCHLOCATION_notFound,
					value : ""
				};
			}
			var store = new dojo.data.ItemFileReadStore( {
				data : data
			});
			store.fetch();
			var i;
			for(i=0;i<data.items.length;++i){
				if(data.items[i].value!="")break;
			}
			if(i==data.items.length)i=0;
			widget.set("store", store);
			widget.set("value", data.items[i].value);

		}
	});

	return tms.china.js.module._searchLocation;
});
