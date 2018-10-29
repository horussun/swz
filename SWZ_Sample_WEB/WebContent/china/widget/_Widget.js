define("tms/china/widget/_Widget", [ "dojo", "dijit", "dijit/_Widget" ], function(dojo) {
	dojo.declare("tms.china.widget._Widget", [ dijit._Widget], {
		// Place comma-separated class attributes here. Note, instance attributes 
		// should be initialized in the constructor. Variables initialized here
		// will be treated as 'static' class variables.

		//the root node of the _Widget /**Dom Node*/
		rootNode : null,
		
		// Constructor function. Called when instance of this class is created
		constructor : function(/**Dom Node*/ rootNode) {
//			this.inherited(arguments);
			this.rootNode = rootNode;
		}
	, 
	/**
	 * Return a DeferredList during init period for caller to check if the component is ready.
	 */
		getInitDefList: function() {
		//this is a interface
		}
	
	//Uncomment above comma and add comma-separated functions here. Do not leave a 
	// trailing comma after last element.
	
	, uninitialize: function(){
			// destroy all inner nodes
			dojo.forEach(dijit.findWidgets(this.rootNode), function(widget) {
				if (!widget._destroyed) {
					if (widget.destroyRecursive) {
						widget.destroyRecursive();
					} else {
						if (widget.destroy) {
							widget.destroy();
						}
					}
				}
			});
		}
		
	});
	
	return tms.china.widget._Widget;
});