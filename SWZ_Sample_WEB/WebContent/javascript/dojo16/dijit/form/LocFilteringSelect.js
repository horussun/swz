define("dijit/form/LocFilteringSelect", ["dojo", "dijit", "dijit/form/FilteringSelect"], function(dojo, dijit) {

dojo.declare(
	"dijit.form.LocFilteringSelect",
	[dijit.form.FilteringSelect],
	{

		_setValueAttr: function(/*String*/ value, /*Boolean?*/ priorityChange){
			// summary:
			//		Hook so set('value', value) works.
			// description:
			//		Sets the value of the select.
			//		Also sets the label to the corresponding value by reverse lookup.
			if(!this._onChangeActive){ priorityChange = null; }
			this._lastQuery = value;
	
			if(value === null || value === ''){
				this._setDisplayedValueAttr('', priorityChange);
				return;
			}
			//2011.12.10 马江龙
			dojo.publish("LocFilteringSelect.loadItemBegin");
			//#3347: fetchItemByIdentity if no keyAttr specified
			var self = this;
			this.store.fetchItemByIdentity({
				identity: value,
				onItem: function(item){
					self._callbackSetLabel(item? [item] : [], undefined, priorityChange);
					//2011.12.10 马江龙
					dojo.publish("LocFilteringSelect.loadItemComplete");
				}
			});
		},
		
		_openResultList: function(/*Object*/ results, /*Object*/ dataObject){
			// Callback when a data store query completes.
			// Overrides ComboBox._openResultList()
	
			// #3285: tap into search callback to see if user's query resembles a match
			if(dataObject.query[this.searchAttr] != this._lastQuery){
				return;
			}
			dijit.form.ComboBoxMixin.prototype._openResultList.apply(this, arguments);
	
			if(this.item === undefined){ // item == undefined for keyboard search
				// If the search returned no items that means that the user typed
				// in something invalid (and they can't make it valid by typing more characters),
				// so flag the FilteringSelect as being in an invalid state
				this.validate(true);
				//默认高亮第一条数据
				this.dropDown._highlightNextOption();
			}
		},
		
		_onKey: function(/*Event*/ evt){
			// summary:
			//		Handles keyboard events
			var key = evt.charOrCode;

			var dk = dojo.keys;
			var highlighted = null;

			if(this._opened){
				highlighted = this.dropDown.getHighlightedOption();
			}
			
			switch(key){
				case dk.ENTER:
				case dk.TAB:
					if(highlighted){
						this._announceOption(highlighted);
					}
					break;
			}

			//调用ComboBox的_onKey方法
			this.inherited(arguments);
		},
		//////////// INITIALIZATION METHODS ///////////////////////////////////////

		constructor: function(){
			dojo.require("dojox.data.QueryReadStore");		
			this.store=new dojox.data.QueryReadStore({
				url:window.actionPath+"/bill/queryLocation"+arguments[0].queryString,
				requestMethod:"post"
			});
			
			//初始化参数
			//默认不显示箭头
			if(arguments[0].hasDownArrow===undefined){
				arguments[0].hasDownArrow=false;
			}
			
			if(arguments[0].queryExpr===undefined){
				arguments[0].queryExpr="${0}";
			}
			//默认关闭自动补全
			arguments[0].autoComplete=false;
			
			this.inherited(arguments);
		}
	}
);


return dijit.form.LocFilteringSelect;
});
