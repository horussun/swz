define("tms/china/purchaserorder/orderEdit",[ "dojo", "tms/china/widget/_Page","tms/china/js/module/_searchLocation",
                                              "tms/china/js/module/_hasDialog","tms/china/js/module/_selection" ],function(dojo, Page) {
	/**
	 * 编辑采购订单页面用到的方法
	 */		
	dojo.declare(
			"tms.china.purchaserorder.orderEdit",
			[ tms.china.widget._Page,
					tms.china.js.module._searchLocation,tms.china.js.module._hasDialog,tms.china.js.module._selection ],
			{
				// Place comma-separated class attributes here.
				// Note, instance
				// attributes
				// should be initialized in the constructor.
				// Variables
				// initialized here
				// will be treated as 'static' class variables.

				// Constructor function. Called when instance of
				// this class is
				// created
				constructor : function(json_param) {
					if (json_param) {
						if (json_param.cmd == "edit") {
							this.name= "编辑采购订单";
						} else if (json_param.cmd == "copy") {
							this.name = Common.I18N.resourceBundle.NB_ORDER_TITLE_copyOrder;
						} else if (json_param.cmd == "add") {
							this.name = "新建采购订单";
						} 
					}
					this.f = false;
				},
				//用来判断编辑时，下拉列表框注值时第一次不要清空买家地址列表框的value
				f:null,
				/**
				 * Add required dojo classes
				 */
				addRequired : function() {

					// 请求所需的控件
					dojo.require("dijit.form.Button");
					dojo.require("dijit.form.Textarea");
					dojo.require("dijit.form.Form");
					dojo.require("dijit.form.ValidationTextBox");
					dojo.require("dijit.form.DateTextBox");
					dojo.require("dijit.form.TimeTextBox");
					dojo.require("dijit.form.FilteringSelect");
					dojo.require("dijit.form.Select");
					dojo.require("dijit.form.DropDownButton");
					dojo.require("dijit.form.SimpleTextarea");
					dojo.require("dijit.TooltipDialog");
					dojo.require("dojox.grid.EnhancedGrid");
					dojo.require("tms.china.widget.infoMsg.InfoMsg");
					dojo.require("dijit.form.CheckBox");
					
					dojo.require("tms.china.purchaserorder.searchSaleOrder");
					dojo.require("dojo.data.ItemFileReadStore");
					
					dojo.require("dojox.form.Uploader");
					if(document.all){
//						dojo.require("dojox.form.uploader.plugins.Flash");
						dojo.require("dojox.form.uploader.plugins.IFrame");
					}else{
						dojo.require("dojox.form.uploader.plugins.HTML5");
					}
				},

				initPage : function() {
					console.debug("AddPage.initPage");

					// 1. Call super class's same method to init
					// common tasks
					this.inherited(arguments);

					// 2. Init components
					var flag=false;
					if(Nav.getRestoreData()){
						flag=true;
					}
					
					this.form = dijit.byId("order_form");
					
					//初始化供应商下拉store
					this.setStore("/dropdownstore/getPurchaserOrgStore", vendorStore,dijit.byId("vendor"),
							dojo.byId("Hidden_vendorOrganizationId").value);
					//只显示供应商org
					dijit.byId('vendor').query.type = "1";
					//初始化预计送货地址下拉store
					this.setStore("/dropdownstore/getAddressStore", deliveryStore,dijit.byId("delivery"),
							dojo.byId("Hidden_deliveryDestinationAddressId").value);
					//初始化买家送货地址下拉store
					this.setStore("/dropdownstore/getAddressStore", pAddStore,dijit.byId("pAdd"),
							dojo.byId("Hidden_buyerOrganizationAddressId").value);
					
					//初始化编辑时对应的时间
					var dt = dojo.byId("Hidden_orderPlacedTime").value;
					if(dt){
						var d = dt.substring(0,10);
						var t = "T"+dt.substring(11,19);
						dijit.byId("PurchaserOrder_orderPlacedTime:date").set('value',d);
						dijit.byId("PurchaserOrder_orderPlacedTime:time").set('value',t);
					}
					
					
					fileUploader=dijit.byId("uploader2");
					dojo.connect(fileUploader, "onChange", function(dataArray){
//							            dojo.forEach(dataArray, function(file) {
			            	Util.showStandBy();
			    			fileUploader.url= window.actionPath+"/purchaserorder/uploadFile";
			    			fileUploader.upload();
//							            });
			        });
					
					dojo.connect(fileUploader, "onComplete", function(dataArray){
						 if(document.all){
				            	dojo.forEach(dataArray, function(json) {			                    
				                    if(json.success=="false"){
				                    	new tms.china.widget.infoMsg.InfoMsg({
					                        title : "消息",
					                        type : json.success,
					                        message : json.message
					                    }).show();
				            		}
				                    dojo.byId("image").src=window.contextRoot+json.message;
				                	dojo.byId("PurchaserOrder_productDesignDiagramPath").value=json.message;
					            });
				            }else{
				            	dojo.forEach(dataArray, function(json) {
				            		if(json.success==false){
				            			new tms.china.widget.infoMsg.InfoMsg({
					                        title : "消息",
					                        type : json.success,
					                        message : json.message
					                    }).show();
				            		}
				                    
				                    dojo.byId("image").src=window.contextRoot+json.message;
				                	dojo.byId("PurchaserOrder_productDesignDiagramPath").value=json.message;
					            });
				            }
				            
				            Util.hideStandBy();
				        });
					//按当前组织id获取组织地址（下拉）
					 dijit.byId('pAdd').query.oid = dojo.byId("Hidden_buyerOrganizationId").value;
					// 发布页面载入完成的事件
					dojo.publish(
						"PageLoadComplete",
						[ "Add Order Page Load Completed" ]);
				}
				//初始化store的data,第三个参数是被注入的select dijit，第四个参数是被注入的value
				,setStore:function(url,store,gd,val){
					var args = {
						url :window.actionPath + url,
						handleAs : "json",
						content : null,
						load : function(data) {
							Util.hideStandBy();
							data = data.json;
							
							store.data=data;
							if(val){
								gd.set('value',val);
							}
						},
						error : Util.ajaxErrorHandler
					};
					dojo.xhrPost(args);
				}
				// 创建订单
				,saveOrder : function() {

					Util.showStandBy();

					var args = {
						url : window.actionPath
								+ "/purchaserorder/save",
						form : dojo.byId("order_form"),
						handleAs : "json",
						content : null,
						load : function(data) {
							Util.hideStandBy();
							data = data.json;
							console.debug("zhou");
							if (data.success == "false") {
								Common.currentpage
										.showErrorDialog(data.description);
							} else {
								Nav.closePage();
								
							}
						},
						error : Util.ajaxErrorHandler
					};
					dojo.xhrPost(args);
				}

				,
				//订单取消按钮
				cancelOrder : function() {
					// 关闭当前页面
					Nav.closePage();
				},
				//弹出产品搜索窗口
				searchProduct : function(){
					var title="产品查询";
					Nav.showDialog("/saleOrder/searchProduct", "tms.china.saleOrder.Search_product",null,title);
								
				},
				//弹出销售订单搜索窗口
				searchSaleOrder : function(){
					var title="销售订单查询";
					Nav.showDialog("/purchaserorder/promotSearchSO", "tms.china.purchaserorder.searchSaleOrder",null,title);
								
				},
				
				
				/*//下拉列表框的采购商级联
				changePurchaser : function () {
	        		console.log("purchaser change!");
	             //	dijit.byId('pAdd').query.oid = dijit.byId('purchaser').get('value') || "*";
	             	
	             	//flag:0,表示第一次，所以，编辑时第一次changePurchaser时不会重置pAdd中的值
	             	if(this.f){
	             		dijit.byId('pAdd').set('value','');
	             	}
	             	this.f = true;
	             	dijit.byId("Contact_info").setValue("");
	          	},
	          	*/
				
				//下拉列表框的采购商地址和联系方式级联
	          	changePAdd : function() {
	          		console.log("pADD change!");
	          		console.log(dijit.byId('pAdd').get('value'));
	          		
	          		//注入联系方式
	          		dijit.byId("Contact_info").setValue((dijit.byId('pAdd').item || {
          				id: ''
    				}).cinfo);
	          	/*	if(!dijit.byId('purchaser').get('value')){
	          			dijit.byId('purchaser').set('value', (dijit.byId('pAdd').item || {
	          				oid: ''
	    				}).oid);
	          		}*/
					
	          	}
	          //可删除
				,testSearch:function(){
					console.debug("zhou");
					var grid=dijit.byId("productGridID");
					grid.store.url=window.actionPath + "/purchaserorder/testSearch";
					var query=null;
					grid.setQuery(query);
				}
				//产品选中后，注入到相应的textbox中
				,selected:function(){
					var grid = dijit.byId("gridID");
					if(this.isSelected(grid)){
						dojo.byId("PurchaserOrder_productId").value = this.getSelectedID("OID",grid);
						dijit.byId("Product_productNumber").setValue(this.getSelectedID("P_NUMBER",grid));
						dijit.byId("Product_productTopic").setValue(this.getSelectedID("P_TOPIC",grid));
						dijit.byId("Product_productDescription").setValue(this.getSelectedID("P_DESCRIPTION",grid));
						dijit.byId("Product_productStyle").setValue(this.getSelectedID("P_STYLE",grid));
						dijit.byId("Product_productType").setValue(this.getSelectedID("P_TYPE",grid));
						dijit.byId("Product_productColor").setValue(this.getSelectedID("P_COLOR",grid));
						dijit.byId("Product_productSexType").setValue(this.getSelectedID("P_SEXTYPE",grid));
						dijit.byId("Product_productMaterial").setValue(this.getSelectedID("P_PRODUCTMATERIAL",grid));
						dijit.byId("Product_productCode").setValue(this.getSelectedID("P_CODE",grid));
						dijit.byId("Product_productName").setValue(this.getSelectedID("P_NAME",grid));
					}
					Nav.closeDialog();	
				}
				//销售订单选中后，注入到相应的textbox中
				,selectSaleOrder:function(){
					var grid = dijit.byId("gridID");
					if(this.isSelected(grid)){
						var id = this.getSelectedID("OID",grid);
						console.debug(id);
						dojo.byId("PurchaserOrder_saleOrderId").value = this.getSelectedID("OID",grid);
						dijit.byId("SaleOrder_orderNumber").setValue(this.getSelectedID("O_NUMBER",grid));
					}
					Nav.closeDialog();	
				}

			});

			return tms.china.purchaserorder.orderEdit;
		});