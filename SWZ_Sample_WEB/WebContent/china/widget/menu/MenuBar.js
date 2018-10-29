define("tms/china/widget/menu/MenuBar", ["dojo", "dijit", "dijit/MenuBar"], function(dojo, dijit) {
dojo.declare("tms.china.widget.menu.MenuBar", [dijit.MenuBar], {
	
	lastItem:null,
	
	_onChildBlur: function(item){
		// summary:
		//		Called when a child MenuItem becomes inactive because focus
		//		has been removed from the MenuItem *and* it's descendant menus.
		// tags:
		//		private
		this._stopPopupTimer();
		
		//by mjl
		//item._setSelected(false);
		
		// Close all popups that are open and descendants of this menu
		var itemPopup = item.popup;
		if(itemPopup){
			this._stopPendingCloseTimer(itemPopup);
			itemPopup._pendingClose_timer = setTimeout(function(){
				itemPopup._pendingClose_timer = null;
				if(itemPopup.parentMenu){
					itemPopup.parentMenu.currentPopup = null;
				}
				dijit.popup.close(itemPopup); // this calls onClose
			}, this.popupDelay);
		}
	},

	onItemClick: function(/*dijit._Widget*/ item, /*Event*/ evt){
		console.debug("onItemClick");

		if(this.lastItem)this.lastItem._setSelected(false);
		item._setSelected(true);
		// before calling user defined handler, close hierarchy of menus
		// and restore focus to place it was when menu was opened
		this.onExecute();
		// user defined handler for click
		item.onClick(evt);
		
		this.lastItem=item;
	},
	
	_onItemFocus : function(item){
		console.debug("_onItemFocus");
		if(item!=this.lastItem)item._setSelected(false);
	},
	
	setItemFocus : function(item){
		if(this.lastItem)this.lastItem._setSelected(false);
		item._setSelected(true);
		this.lastItem=item;
	}
});


return tms.china.widget.menu.MenuBar;
});