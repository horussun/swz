define(
	"tms/china/saleOrder/ImportExcel",
	[ "dojo", "tms/china/widget/_Page" ],
	function(dojo, Page) {
		/**
		 * excel导入弹出窗口所使用的功能
		 */
	    dojo .declare(
			    "tms.china.saleOrder.ImportExcel",
			    [ tms.china.widget._Page ],
			    {
				constructor : function(json_param) {
				    this.name = "上传excel销售订单|采购订单|运输订单";
				},
				name : null,
				fileUploader : null,

				/**
				 * Add required dojo classes
				 */
				addRequired : function() {
				    // 请求所需的控件
				    dojo.require("tms.china.widget.infoMsg.InfoMsg");
				    dojo.require("dojox.grid.enhanced.plugins.Menu");
				    dojo.require("dojox.grid.enhanced.plugins.Selector");
				    dojo.require("dojox.grid.enhanced.plugins.IndirectSelection");
				    dojo.require("dijit.form.Form");
				    dojo.require("dojox.form.Uploader");

				    if (document.all) {
				    	dojo.require("dojox.form.uploader.plugins.IFrame");
				    } else {
				    	dojo.require("dojox.form.uploader.plugins.HTML5");
				    }
				},

				initPage : function() {

				    // 1. Call super class's same method to init
				    // common tasks
				    this.inherited(arguments);

				    // 2. Init components
				    var flag = false;
				    if (Nav.getRestoreData()) {
				    	flag = true;
				    }

				    fileUploader = dijit.byId("uploader");
				    dojo.connect( fileUploader, "onChange", function(dataArray) {
						dojo.forEach(dataArray,function(file) {
							var fileName = file.name;
							// in IE,the uploadFileName will include file's path
							if (dojo.isIE) {
								// get fileName
								fileName = fileName.substring(fileName.lastIndexOf('\\') + 1);
							 }
							 dijit.byId( "uploadFileName").attr("value",fileName);
						   });
						 });
                     //when complete importing, re search
				     dojo.connect(fileUploader, "onComplete", function(dataArray) {
							dijit.byId("uploadFileName").reset();
							dijit.byId("mainDialog").hide();
							// re search
							Common.currentpage.searchPart.search();
							
							if (document.all) {
							    dojo.forEach(dataArray,function(json) {
								new tms.china.widget.infoMsg.InfoMsg(
									{
									 title : "消息",
									 type : json.success,
									 message : json.message
									}).show();
								});
							} else {
							    dojo.forEach(dataArray,function(json) {
								new tms.china.widget.infoMsg.InfoMsg(
									{
									 title : "消息",
									 type : json.success,
									 message : json.message
									}).show();
								});
							}

							Util.hideStandBy();
					 });
                    //导入数据请求后台importExcel方法处理，传入type值（具体对应销售订单、采购订单、运单）
				    fileUploader.url = window.actionPath
					    + "/importexcel/importExcel?type="
					    + dojo.byId('type').value;
				    var cnt = dojo.byId("uploaddiv");
				    
				    //html5 file api
				    if (window.FileReader) {
						// 处理拖放文件列表
						function handleFileSelect(evt) {
						    Util.showStandBy();
						    evt.stopPropagation();
						    evt.preventDefault();
						    var files = evt.dataTransfer.files;
						    fileUploader.upload(files);
						}
	
						// 处理插入拖出效果
						function handleDragEnter(evt) {
						    this.setAttribute('style',
							    'border-style:dashed;');
						}
						function handleDragLeave(evt) {
						    this.setAttribute('style', '');
						}
	
						//处理文件拖入事件，防止浏览器默认事件带来的重定向
						function handleDragOver(evt) {
						    evt.stopPropagation();
						    evt.preventDefault();
						}
	
						cnt.addEventListener('dragenter',handleDragEnter, false);
						cnt.addEventListener('dragover',handleDragOver, false);
						cnt.addEventListener('drop',handleFileSelect, false);
						cnt.addEventListener('dragleave',handleDragLeave, false);
				    }

				    dojo.publish("PageLoadComplete",
					    [ "test Page Load Completed" ]);
				},

				//上传excel到后台，用于导入数据库
				excelImport : function() {
				    Util.showStandBy();
				    fileUploader.url = window.actionPath
					    + "/importexcel/importExcel?type="
					    + dojo.byId('type').value;
				    fileUploader.upload();
				},

				//取消按钮
				cancel : function() {
				    dijit.byId("mainDialog").hide();
				}
			    });

	    return tms.china.saleOrder.ImportExcel;
	});