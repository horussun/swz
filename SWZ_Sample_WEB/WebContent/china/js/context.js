//初始化国际化类
dojo.registerModulePath("tms.china", "../../../china");
//InfoMsa.js
var Info_constant = {
		SUCCESS:"success",
		FAIL:"error",
		CONFIRM:"confirm"
			
	};
//Test.js
var TestPageContext  = {
		
		 listPlugins :{
					/*menus: {
								
								rowMenu: dijit.byId("df_fleetMenu"),
								cellMenu: dijit.byId("df_fleetMenu")
				
								},
					pagination: {
								    pageSizes: ["3", "5", "6", "All"], // Array, custom the items
																		// per page button
								    // itemTitle: "entrys", // String, custom the item' title of
									// description
								    description: "30%", // boolean, custom whether or not the
														// description will be displayed
								    // description: false,
								    sizeSwitch: "160px", // boolean, custom whether or not the
															// page size switch will be displayed
								    // sizeSwitch: false,
								    pageStepper: "30em", // boolean, custom whether or not the
															// page step will be displayed
								    // pageStepper: false,
								    gotoButton: true, // boolean, custom whether or not the goto
														// page button will be displayed
								    maxPageStep: 7, // Integer, custom how many page step will be
													// displayed
								    position: "bottom" // String, custom the position of the
														// pagination bar
								    // there're three options: top, bottom, both
								    // ,descTemplate: "${1} ${0}" // A template of the current
									// position description.
					},
					*/
	menus :{}
	,
	filter: {
		//itemsName: 'test',
		//closeFilterbarButton: true,
		ruleCount: 8
	},
	indirectSelection : {
		name : "",
		width : "20px",
		styles : "text-align: center;"
	}
	}

};
