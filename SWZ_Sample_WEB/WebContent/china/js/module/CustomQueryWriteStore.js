define("tms/china/js/module/CustomQueryWriteStore", [ "dojo" ], function(dojo) {
	
	dojo.require("dojox.data.QueryReadStore");
	dojo.declare("tms.china.js.module.CustomQueryWriteStore", dojox.data.QueryReadStore, {
		constructor : function() {
		 //console.log("CustomQueryReadStore......");
		 this.inherited(arguments);
		},
		/* @Override isItemLoaded method */ 
		isItemLoaded: function(/* anything */ something) { 
		    // Currently we have item["children"] as a state that tells if an item is 
		    // loaded or not. 
		    // if item["children"] === true, means the item is not loaded. 
		    var isLoaded = false; 

		    if (this.isItem(something)) { 
		        var children = this.getValue(something, "children"); 
		        if (children === true) { 
		            // need to lazy loading children 
		            isLoaded = false; 
		        } else { 
		            isLoaded = true; 
		        } 
		    } 

		    return isLoaded; 
		},
		/* @Override loadItem method */ 
		loadItem: function(/* object */ args){
			if (this.isItemLoaded(args.item)) { 
		        return; 
		    } 

		    var item = args.item; 
		    var scope = args.scope || dojo.global; 
		    var sort = args.sort || null; 
		    var onItem = args.onItem; 
		    var onError = args.onError; 

		    if (dojo.isArray(item)) { 
		        item = item[0]; 
		    } 

		    // load children 
		    var children = this.getValue(item, "children"); 

		    // load children 
		    if (children === true) { 
		        var serverQuery = {}; 

		        // "parent" param 
		        var itemId = this.getValue(item, "id"); 
		        serverQuery["parent"] = itemId; 

		        // "sort" param 
		        if (sort) { 
		            var attribute = sort.attribute; 
		            var descending = sort.descending; 
		            serverQuery["sort"] = (descending ? "-" : "") + attribute; 
		        } 

		        // ajax request 
		        var _self = this; 

		        var xhrData = { 
		            url: window.actionPath+"/search/getChildren", 
		            handleAs: "json", 
		            error:Util.ajaxErrorHandler,
		            content: serverQuery 
		        }; 

		        var xhrFunc =dojo.xhrPost; 
		        var deferred = xhrFunc(xhrData); 

		        // onError callback 
		             deferred.addErrback(function(error) { 
		            if (args.onError) { 
		                args.onError.call(scope, error); 
		            } 
		        }); 

		        // onLoad callback 
		        deferred.addCallback(function(data) { 
		            if (!data) { 
		                return; 
		            } 

		            if (dojo.isArray(data)) { 
		                var children = data; 

		                var parentItemId = itemId; 
		                var childItems = []; 

		                dojo.forEach(children, function(childData) { 
		                    // build child item 
		                    //childItems.push(childData); 
		                	 var childItem = {}; 
		                     childItem.i = childData; 
		                     childItem.r = this; 
		                     childItems.push(childItem); 
		                }, _self); 

		                _self.setValue(item, "children", childItems); 
		            } 
		            if (args.onItem) { 
		                args.onItem.call(scope, item); 
		            } 
		        }); 
		    } 			
		},
		/* @Override geValues method */ 
		getValues: function(item, attribute) { 
		    //  summary: 
		    //      See dojo.data.api.Read.getValues() 

		    this._assertIsItem(item); 
		    if (this.hasAttribute(item, attribute)) { 
		        return item.i[attribute] || []; 
		    } 

		    return []; // Array 
		},

		/* @Override seValue method */ 
		setValue: function(/* item */ item, /* attribute-name-string */ attribute, 
		                   /* almost anything */ value) { 
		    // summary: See dojo.data.api.Write.set() 

		    // Check for valid arguments 
		    this._assertIsItem(item); 
		  //  this._assert(dojo.isString(attribute)); 
		  //  this._assert(typeof value !== "undefined"); 

		    var success = false; 
		    var _item = item.i; 
		    _item[attribute] = value; 
		    success = true; 
		    return success; // boolean 
		} 
		
	});
	return tms.china.js.module.CustomQueryWriteStore;
});