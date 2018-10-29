define("tms/china/tms_locale/I18n", [ "dojo", "tms/china/widget/_Page" ], function(dojo, Page) {
			
			dojo.declare("tms.china.tms_locale.I18n", [ tms.china.widget._Widget], {
				resourceBundle:null,

				// Override this method to perform custom behavior during dijit construction.
				// Common operations for constructor:
				// 1) Initialize non-primitive types (i.e. objects and arrays)
				// 2) Add additional properties needed by succeeding lifecycle methods
				// Constructor function. Called when instance of this class is created
				constructor : function() {
					console.log("tms.china.tms_locale.I18n constructor");
					dojo.requireLocalization("tms.china.tms_locale", "i18n");
					//得到resource bundle
					this.resourceBundle  = dojo.i18n.getLocalization("tms.china.tms_locale", "i18n");
					
				},
				
				// When this method is called, all variables inherited from superclasses are 'mixed in'.
				// Common operations for postMixInProperties
				// 1) Modify or assign values for widget property variables defined in the template HTML file
				postMixInProperties : function() {
					this.inherited(arguments);
				
				},

				// postCreate() is called after buildRendering().  This is useful to override when 
				// you need to access and/or manipulate DOM nodes included with your widget.
				// DOM nodes and widgets with the dojoAttachPoint attribute specified can now be directly
				// accessed as fields on "this". 
				// Common operations for postCreate
				// 1) Access and manipulate DOM nodes created in buildRendering()
				// 2) Add new DOM nodes or widgets 
				postCreate : function() {
					
				}
				/**
				 * Get i18n String by key
				 */
				,s:function(key){
					if(this.resourceBundle == null){
						this.resourceBundle  = dojo.i18n.getLocalization("tms.china.tms_locale", "i18n");
					}
					
					return this.resourceBundle[key];
				}
				
				
			});
			
			return tms.china.tms_locale.i18n;
		});
