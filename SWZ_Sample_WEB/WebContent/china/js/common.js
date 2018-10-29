var Constant = {

    //Post parameter keys
	POST_URL: window.AJAX_URL,
	ACTION: "action",
	JSON_DATA: "json",
	DATASTORE_IDENTIFIER: "identifier",
	DATASTORE_ITEMS: "items",
	DATASTORE_NAME: "name",
	SearchLocation:{
		FULL_SEARCH:"0",
		PART_SEARCH:"1"
	}
};

var Common = {
	MainContainer: null
	, currentpage: null
	, currentDialogPage:null
	, I18N : null
	, SearchLoad_CompleteHandle:null
	, InitFunction_CompleteHandle:null
};

var Util = {
		startPulse : function(){
			console.debug("send pulse...");
			dojo.xhrGet({
				url:window.contextRoot+"/DEalive.html",
				error:Util.ajaxErrorHandler,
				preventCache:true
			});
			setTimeout("Util.startPulse()",3*60*1000);
		},
		
	    prepareAjaxData: function(action, data/*json obj*/){
		    
	        var postData = data || {};
	        
	        //add action type
	        var postDataWrapper = {};
	        
	        postDataWrapper[Constant.JSON_DATA] = dojo.toJson(postData);
	        postDataWrapper[Constant.ACTION] = action;
	        
	        return postDataWrapper;
	    }	    
	    
	    /**
	     * show loader on body
	     */
	    , showStandBy: function(){
	    	dojo.require("dojox.widget.Standby");
	    	
	    	var bodyStandBy = dijit.byId("bodyStandBy");
//            document.body.appendChild(bodyStandBy.domNode);
	    	bodyStandBy.show();
           
	    }
	    , hideStandBy: function(){
	    	var bodyStandBy = dijit.byId("bodyStandBy");
	    	bodyStandBy.hide();
	    	//also hide dialog standby
	    	Util.hideDialogStandBy();
	    }

	    ,showDialogStandBy:function(){
	    	console.log("showDialogStandBy");
	    	var dialogStandBy = dijit.byId("dialogStandBy");
	    	dialogStandBy.show();
	    }
	    , hideDialogStandBy: function(){
	    	console.log("hideDialogStandBy");
	    	var dialogStandBy = dijit.byId("dialogStandBy");
	    	dialogStandBy.hide();
	    	
	    }
	    /*
	     * show the toaster's message 
	     * message:a single string with the message text
	     * messageType coulde be "fatal","error","warning" or "message"
	     * 
	     */
	    ,showToaster:function(message,messageType){
	    	dijit.byId('first_toaster').setContent(message, messageType);
	    	dijit.byId('first_toaster').show();
	    }
	    
	    /**
	     * display error dialog
	     */
	    ,								
		showErrorDialog : function(errorString) {
			new tms.china.widget.infoMsg.InfoMsg( {
				message : errorString,
				title : Util.getI18nValue("ERROR_DIALOG_TITLE"),
				type : Info_constant.FAIL
			}).show();
		}
	    ,
	    ajaxErrorHandler: function(error,ioargs){
			//process error
			Util.hideStandBy();
			if(ioargs && ioargs.xhr && ioargs.xhr.response){
				var response = dojo.fromJson(ioargs.xhr.response);
				if(response.message && response.message.text){
					Util.showErrorDialog(response.message.text);
				} else {
					Util.showErrorDialog(Util.getI18nValue("AJAX_ERROR_COMMON_MSG"));
				}
			} else {
				Util.showErrorDialog(Util.getI18nValue("AJAX_ERROR_COMMON_MSG"));
			}
		}
	    /**
	     * Get i18n String
	     */
	    ,getI18nValue:function(key){
	    	
	    	if(!Common.I18N){
	    		//init Common.I18N
		    	dojo.require("tms.china.tms_locale.I18n");
		    	Common.I18N =  new tms.china.tms_locale.I18n();
		    	
		    	console.debug("Common.I18N is initialized...");
	    	}
	    	return Common.I18N.s(key);
	    }
	    /**
	     * node can be an dom node obj or a node id
	     */
	    , display: function(node){
	    	//if null, return
	    	if(node){
	    		if(node.style){
	    			//is dom node obj
	    			node.style.display="";
	    		} else {
	    			//see if is a dom node id
	    			var domNode = dojo.byId(node);
	    			if(domNode && domNode.style){
	    				domNode.style.display="";
	    			}
	    		}
	    	}
	    }
	    /**
	     * node can be an dom node obj or a node id
	     */
	    , hide: function(node){
	    	//if null, return
	    	if(node){
	    		if(node.style){
	    			//is dom node obj
	    			node.style.display="none";
	    		} else {
	    			//see if is a dom node id
	    			var domNode = dojo.byId(node);
	    			if(domNode && domNode.style){
	    				domNode.style.display="none";
	    			}
	    		}
	    	}
	    
	    }
	    /**
	     * 当页面返回时重新再搜索。待搜索功能完成之后在完善！！
	     * searcher:页面的搜索控件的引用
	     * upOrDown:当页面有两个控件时使用（主要针对dedicatedFleet模块），true表示上面的搜索控件，false表示下面的搜索控件
	     */
		,_reSearch:function(searcher,upOrDown){
			var searchFormValue = Nav.getRestoreData();
			
			//dedicatedFleet页面有两个搜索控件的情况
			if(searchFormValue.fleetSearchFormValues&&searchFormValue.aShipmentsearchFormValues){
				var searchFormValues = null;
				if(upOrDown==true){
					searchFormValues = searchFormValue.fleetSearchFormValues;
				}else if(upOrDown==false){
					searchFormValues = searchFormValue.aShipmentsearchFormValues
				}
				console.log(searcher.form.getValues());
				searcher.form.setValues(searchFormValues);
				console.log(searcher.form.getValues());
				//searcher.search();
				var deferred ;
				if(searchFormValues.rangeContent){
					console.log(1+"rangeContent");
					deferred = dojo.connect(searcher.range_select,"onChange",function(){
						searcher.search();
						console.log(2+"rangeContent");
						dojo.disconnect(deferred);
					});
				}else if(searchFormValues.dateContent){
					console.log(1+"dateContent");
					deferred = dojo.connect(searcher.date_select,"onChange",function(){
						searcher.search();
						console.log(2+"dateContent");
						dojo.disconnect(deferred);
					});
				}else if(searchFormValues.findContent){
					console.log(1+"findContent");
					deferred = dojo.connect(searcher.find_select,"onChange",function(){
						searcher.search();
						console.log(2+"findContent");
						dojo.disconnect(deferred);
					});
				}else if(searchFormValues.searchContent){
					console.log(1+"searchContent");
					deferred = dojo.connect(searcher.customized_search_select,"onChange",function(){
						searcher.search();
						console.log(2+"searchContent");
						dojo.disconnect(deferred);
					});
				}
			//页面只有一个搜索控件的情况
			}else if(searchFormValue.values){
				var searchFormValues = searchFormValue.values;
	
				console.log(searcher.form.getValues());
				searcher.form.setValues(searchFormValues);
				console.log(searcher.form.getValues());
				//searcher.search();
				
				 var deferred ;
				  if(searchFormValues.rangeContent){
					console.log(1+"rangeContent");
					deferred = dojo.connect(searcher.range_select,"onChange",function(){
						searcher.search();
						console.log(2+"rangeContent");
						dojo.disconnect(deferred);
					});
				}else if(searchFormValues.dateContent){
					console.log(1+"dateContent");
					deferred = dojo.connect(searcher.date_select,"onChange",function(){
						searcher.search();
						console.log(2+"dateContent");
						dojo.disconnect(deferred);
					});
				}else if(searchFormValues.findContent){
					console.log(1+"findContent");
					deferred = dojo.connect(searcher.find_select,"onChange",function(){
						searcher.search();
						console.log(2+"findContent");
						dojo.disconnect(deferred);
					});
				}else if(searchFormValues.searchContent){
					console.log(1+"searchContent");
					deferred = dojo.connect(searcher.customized_search_select,"onChange",function(){
						searcher.search();
						console.log(2+"searchContent");
						dojo.disconnect(deferred);
					});
				}	
			}
		}
	    /*show information message dialog*/
	    , showInfoMsg: function(msg, caller, callback_func) {
	    	dojo.byId("infoMsg").innerHTML = msg;
	    	dijit.byId("infoDlg").show();
	    	
	    	if (callback_func) {
	    		if (caller) {
	    			if (this.connections["infoDlg_OK_btn"]) {
	    				dojo.disconnect(this.connections["infoDlg_OK_btn"]);
	    			}
	    			this.connections["infoDlg_OK_btn"] = dojo.connect(dojo.byId("infoDlg_OK_btn"), "onclick", caller, callback_func);
	    			
	    		} else if (caller == null) {
	    			if (this.connections["infoDlg_OK_btn"]) {
	    				dojo.disconnect(this.connections["infoDlg_OK_btn"]);
	    			}
	    			this.connections["infoDlg_OK_btn"] = dojo.connect(dojo.byId("infoDlg_OK_btn"), "onclick", callback_func);
	    			
	    		} else {
	    			console.error("caller: " + caller);
	    		}
	    	}
	    },
	    
	    
	    /*pool for maintaining event connections on common dialogs*/ 
	    connections: {},
	    
	    unbind: function(name) {
	        if (this.connections[name]) {
	            dojo.disconnect(this.connections[name]);
	        }
	    },
	    bind: function(name, target, eventName, caller, callback_func) {
	        if (callback_func) {
	            this.unbind(name);
	            caller = caller || null;
	            this.connections[name] = dojo.connect(target, eventName, caller, callback_func);
	        }
	    },
	    
	    xhrPost : function(args, func, errFunc){
	    	if(func){
	    		dojo.setObject("handle", 
	    						function(json, ioargs){
	    							Nav.processReturnStatus(ioargs);
								}, 
								args);

	    		if(errFunc){
	    			return dojo.xhrPost(args).then(func, errFunc);
	    		}else{
	    			return dojo.xhrPost(args).then(func);
	    		}
	    	}else{
	    		var loadFunc = dojo.getObject("load", false, args);
	    		dojo.setObject("load", 
	    						function(json, ioargs){
	    							Nav.processReturnStatus(ioargs);
	    							if (loadFunc) {
											loadFunc(json, ioargs);
									}					    	
	    						}, 
	    						args);
	    		return dojo.xhrPost(args);
	    	}
	    }

	    
	    
//	    ,
	    
//	    /*show error message dialog*/
//	    showErrorMsg: function(msg, caller, callback_func) {
//	        dojo.byId("errorMsg").innerHTML = msg;
//	        dijit.byId("errorDlg").show();
//
//	        if (callback_func) {
//	            if (caller) {
//	                if (this.connections["errorDlg_OK_btn"]) {
//	                    dojo.disconnect(this.connections["errorDlg_OK_btn"]);
//	                }
//	                this.connections["errorDlg_OK_btn"] = dojo.connect(dojo.byId("errorDlg_OK_btn"), "onclick", caller, callback_func);
//
//	            } else if (caller === null) {
//	                if (this.connections["errorDlg_OK_btn"]) {
//	                    dojo.disconnect(this.connections["errorDlg_OK_btn"]);
//	                }
//	                this.connections["errorDlg_OK_btn"] = dojo.connect(dojo.byId("errorDlg_OK_btn"), "onclick", callback_func);
//
//	            } else {
//	                console.error("caller:", caller);
//	            }
//	        }
//	    },
//	    
	    
//	    /*show confirmation message dialog*/
//	    showConfirmDlg: function(msg, caller, callback_func_ok, callback_func_cancel) {
//	    	var dlg = dijit.byId("confirmDlg");
//	    	if(!dlg){
//	    		//return if not existing dlg
//	    		return;
//	    	}
//	    	
//	        dojo.byId("confirmMsg").innerHTML = msg;
//	        dlg.show();
//
//	        var defaultCallback = function() {
//	        	dlg.hide();
//	        };
//
//	        var callbackHandler_ok = function() {
//	            defaultCallback.apply();
//	            if (callback_func_ok) {
//	                callback_func_ok.apply(caller);
//	            }
//	        };
//
//	        var callbackHandler_cancel = function() {
//	            defaultCallback.apply();
//	            if (callback_func_cancel) {
//	                callback_func_cancel.apply(caller);
//	            }
//	        };
//
//	        this.bind("confirmDlg_ok_btn", dojo.byId("confirmDlg_ok_btn"), "onclick", caller, callbackHandler_ok);
//	        this.bind("confirmDlg_cancel_btn", dojo.byId("confirmDlg_cancel_btn"), "onclick", caller, callbackHandler_cancel);
//	    },
	    
	    , compareDate: function (dateTextBoxObj1,dateTextBoxObj2){
	    	var date1 = dateTextBoxObj1 == null ? null : dateTextBoxObj1.value;
	    	var date2 = dateTextBoxObj2 == null ? null : dateTextBoxObj2.value;
	    	if(date1&&date2){
	    		return date1.getTime()-date2.getTime();
	    	}
	    }
	    
	    /**
	     * dateTextBoxObj
	     * fieldPrefix: 'f_ArrivalClose'
	     * 
	     * f_arrivalClose:date	2011-08-22
	     * 
	     * output:
	     * f_arrivalClose:Year	2011
	     * f_arrivalClose:Month	7
	     * f_arrivalClose:Day	22
	     * 
	     * return  content of fieldname:value object
	     * 
	     */
	    , dateObjOnSubmit : function (dateTextBoxObj, fieldPrefix){
	    	
	    	var date = dateTextBoxObj == null ? null : dateTextBoxObj.value;
	    	
	    	var obj = {};
	    	
	    	if(date){
	    		//year
	    		var yearName = fieldPrefix + ":Year";
	    		var yearValue = date.getFullYear();
	    		dojo.setObject(yearName, yearValue, obj);
	    		
	    		//month
	    		var monthName = fieldPrefix + ":Month";
	    		var monthValue = date.getMonth();
	    		dojo.setObject(monthName, monthValue, obj);
	    		
	    		//date
	    		var dateName = fieldPrefix + ":Day";
	    		var dateValue = date.getDate();
	    		dojo.setObject(dateName, dateValue, obj);
	    		
	    	}
	    	
	    	return obj;
	    	
	    }
	    
	    
	    /**
	     * timeTextBoxObj
	     * fieldPrefix: 'f_ArrivalClose'
	     * 
	     * f_arrivalClose:time	05:00:00
	     * 
	     * output:
	     * f_arrivalClose:AmPm	1
	     * f_arrivalClose:Hour	5
	     * f_arrivalClose:Min	0
	     * 
	     * return  content of fieldname:value object
	     * 
	     */
	    , timeObjOnSubmit : function (timeTextBoxObj, fieldPrefix,viewMilitaryTime/*24小时制*/){
	    	if(viewMilitaryTime==null||viewMilitaryTime==undefined){
	    		viewMilitaryTime=true;
	    	}
	    	
	    	var time = timeTextBoxObj == null ? null : timeTextBoxObj.value;
	    	
	    	var obj = {};
	    	
	    	if(time){
	    		//am/pm
	    		if(!viewMilitaryTime){
		    		var amPmName = fieldPrefix + ":AmPm";
		    		var amPmValue = 
		    			dojo.date.locale.format(
		    				time
		    				, {
			    				timePattern: "a"
			    				, selector: "time"
			    				, am: "0"
			    				, pm: "1"
			    				}
		    				);  // am: 0, pm: 1
	
		    		dojo.setObject(amPmName, amPmValue, obj);
	    		}
	    		//Hour
	    		var hourName = fieldPrefix + ":Hour";
	    		var hourPattern = viewMilitaryTime?"H":"h";
	    		
	    		var hourValue = 
	    			dojo.date.locale.format(
		    				time
		    				, {
			    				timePattern: "H"
			    				, selector: "time"
			    				}
		    				);  // 0-11
	    		dojo.setObject(hourName, hourValue, obj);
	    		
	    		//Min
	    		var minName = fieldPrefix + ":Min";
	    		var minValue = 
	    			dojo.date.locale.format(
		    				time
		    				, {
			    				timePattern: "m"
			    				, selector: "time"
			    				}
		    				);  // 0-59
	    		dojo.setObject(minName, minValue, obj);
	    		
	    	}
	    	
	    	return obj;
	    	
	    }
	    

		    ,
	getStringCharLength : function(str) {
		var byteLen = 0, len = str.length;
		if (str) {
			for ( var i = 0; i < len; i++) {
				if (str.charCodeAt(i) > 255) {
					byteLen += 2;
				} else {
					byteLen++;
				}
			}
			return byteLen;
		} else {
			return 0;
		}
	}
	
	,mergeJsonObject : function(jsonbject1, jsonbject2){  
        var resultJsonObject={};  
        for(var attr in jsonbject1){  
            resultJsonObject[attr]=jsonbject1[attr];  
        }  
        for(var attr in jsonbject2){  
            resultJsonObject[attr]=jsonbject2[attr];  
        }  

        return resultJsonObject;  
    }
		    
		    
};// end of Util



/**
 * This is a common navigator to navigator content pane "mainContainer" to a 
 */
var Nav = {
	
		/**
		 * 一个维持面包屑的列表/数组
		 */
	_breadCrumbChain : null,
		
	/**
	 * Core method for redirect main container
	 * 
	 * href: 				子页面的URL
	 * dojo_class_name：  	子页面的页面对象名
	 * json_param: 			同时传递给Server的view方法,以及子页面对象构造函数的参数
	 * isNewRoot: 是否一个新的根节点页面(不需要原有的面包屑链条)
	 * 
	 * TODO: 考虑添加 点击navigation 链中的链接所触发的js
	 */
	go: function (href, dojo_class_name, json_param, isNewRoot){
		try{
			console.debug("go");
			Util.showStandBy();

			//在毁掉当前页面之前, 保存恢复数据,新建节点
			Nav.onNewPage(href, dojo_class_name, json_param, isNewRoot);
			
			//重新生成pane的内容,异步
			Nav._destroyAndCreatePaneContent(href, dojo_class_name, json_param);
			
			//重绘面包屑,需要把当前页面添加为最新的节点
			Nav._addCrumb(href, dojo_class_name, json_param, Common.currentpage);

			//20111009 victor:绘制面包屑加入在_destroyAndCreatePaneContent的回调中.
			//Nav._renderBreadCrumbChain();
			
			
			
		} catch (err) {
			console.error(err);
		}
	}

	/**
	 * 重新生成pane的内容 异步
	 * 
	 */
	,_destroyAndCreatePaneContent: function(href, dojo_class_name, json_param){
		
		
		//show standby
		
		//更新pane内容
		dojo.xhrPost({
			url : window.actionPath + href,
			handleAs : "text",
			content : Util.prepareAjaxData(null, json_param),
			preventCache : true,
			error : Util.ajaxErrorHandler,
			load : function(response, ioargs){
			
						//Destroy all the widgets inside the ContentPane and empty containerNode 
						dijit.byId("mainContainer").destroyDescendants();
			
						Nav.processReturnStatus(ioargs);

						//20111009 victor:绘制面包屑加入在_destroyAndCreatePaneContent的回调中.
						Nav._renderBreadCrumbChain();
						dijit.byId("mainContainer").set('content', response);
					}
		});

		//请求需要的class
		dojo.require(dojo_class_name);
		
		//生成页面对象
		var pageObj = new (dojo.getObject(dojo_class_name))(json_param);
		
		//设置当前页面
		Common.currentpage = pageObj;
		
		
		//加载页面所需要的require
		pageObj.addRequired();
		
		//设置Onload事件对应的初始化方法
		Util.bind(
				"mainContainer_onLoad"
				,dijit.byId("mainContainer")
				, 'onLoad'
				, Common.currentpage
				, function(){ 
					//添加调整浏览器
					/*if (document.getElementById("container").offsetHeight <= window.innerHeight) {
						document.getElementById("footer").style.position = "absolute";
					} else {
						document.getElementById("footer").style.position = "relative";
					}*/
					Common.currentpage.initPage(); Util.hideStandBy();}
		);
		

	}


	/**
	 * 新开页面时候的处理
	 * 
	 * 新开页面的参数: href, dojo_class_name, json_param
	 * isNewRoot: 是否一个新的根节点
	 * 
	 */

	, onNewPage: function(href, dojo_class_name, json_param, isNewRoot){
		if(isNewRoot || !Nav._breadCrumbChain){
			//清空或初始化
			Nav._breadCrumbChain = new Array();
		}
		if(Nav._breadCrumbChain.length > 0){
			//mjl:fix defect 807,1233
			for(var i=Nav._breadCrumbChain.length-1;i>=0;--i){
				var currentCrumb=Nav._breadCrumbChain[i];
				if(currentCrumb.href == href && currentCrumb.dojo_class_name == dojo_class_name){
					break;
				}
			}
			if(i>=0){
				var popNum=Nav._breadCrumbChain.length-i;
				while(popNum>0){
					Nav._breadCrumbChain.pop();
					popNum--;
				}
				return;
			}
			
			//Chain非空, 取得转移前页面恢复用数据,并且存储
			if(Common.currentpage && Common.currentpage.getRestoreData){
				var restoreData = Common.currentpage.getRestoreData();
				dojo.setObject("restoreData", restoreData, Nav._breadCrumbChain[Nav._breadCrumbChain.length-1]);
			}
			
			
		}
		//TODO dojo.back
	}
	
	/**
	 * 完成当前页面操作之前的处理, 回到上一层(cancel,save完成时候使用)
	 */
	, closePage: function (json_param){
		console.log("closePage");
		//是否chain浏览历史,包括当前节点,至少要有两个,否则,回到欢迎页面
		if(Nav._breadCrumbChain && Nav._breadCrumbChain.length > 1){

			Util.showStandBy();
			
			//弹出最上层面包屑
			Nav._breadCrumbChain.pop();
			
			//显示和恢复最后一个节点
			Nav._displayAndRestoreLast(json_param);
			
		} else {
			//回到欢迎页面
			Nav.goWelcomePage();
		}
		
	}

	
	/**
	 * 显示和恢复最后一个节点
	 */

	,_displayAndRestoreLast:function(json_param){
		//取得最后面包屑的对象
		var lastCrumb = Nav._breadCrumbChain[Nav._breadCrumbChain.length-1];
		if(json_param){
			Nav._destroyAndCreatePaneContent(lastCrumb.href, lastCrumb.dojo_class_name, Util.mergeJsonObject(json_param,lastCrumb.json_param));
		}else {
			Nav._destroyAndCreatePaneContent(lastCrumb.href, lastCrumb.dojo_class_name, lastCrumb.json_param);
		}
		
		
		//重绘面包屑  不需要把当前页面节点加入chain,已经在上面了
		//20111009 victor:绘制面包屑加入在_destroyAndCreatePaneContent的回调中.
//		Nav._renderBreadCrumbChain();

		//设法恢复页面的values
		
		//交由每个页面自行判断,如何,以及是否需要恢复.  调用getRestoreData在最新的面包屑的restoreData中可以获取恢复数据

		
	}

	/**
	 * 从最新的面包屑的restoreData获得可能存在的恢复数据,不存在则返回null
	 */
	, getRestoreData: function(){
		
		//得到当前最新面包屑的restoreValue
		if(Nav._breadCrumbChain && Nav._breadCrumbChain.length > 0){
			//取得最后面包屑的对象
			var lastCrumb = Nav._breadCrumbChain[Nav._breadCrumbChain.length-1];

			//获得恢复用的数据
			var restoreData = dojo.getObject("restoreData", false, lastCrumb);
			return restoreData ? restoreData : null;
		} else {
			return null;
		}
	
	}
	
	/**
	 * 回到欢迎页面
	 */
	, goWelcomePage: function(){
		//初始化Chain
		Nav._breadCrumbChain = new Array();
		//load welcome page. 
		Nav.go(Nav.WELCOME_PAGE, 'tms.china.Welcome', null, true);
	}
	
	/**
	 * 重新展现第index个crumb
	 * 	
	 * 
	 */
	, goBack: function(index){
		//1. 把index之后的节点都扔掉
		if(Nav._breadCrumbChain){
			Nav._breadCrumbChain.splice(index + 1, Nav._breadCrumbChain.length - 1 - index);
		}
		Util.showStandBy();
		
		//显示和恢复最后一个节点
		Nav._displayAndRestoreLast();
	}
	
	/**
	 * 新建面包屑对象, 推入chain
	 */
	, _addCrumb:function(href, dojo_class_name, json_param, pageObj){
		console.log('crumb:'+pageObj.name);
		var crumb = {};
		dojo.setObject("href", href, crumb);
		dojo.setObject("dojo_class_name", dojo_class_name, crumb);
		dojo.setObject("json_param", json_param, crumb);
		
		if(pageObj){
//			console.debug("pageObj.name", pageObj.name);
			dojo.setObject("name", pageObj.name, crumb);
		}
		
//		console.debug("crumb", crumb);
		
		Nav._breadCrumbChain.push(crumb);
	}
	
	/**
	 * 绘制面包屑链
	 */
	, _renderBreadCrumbChain: function(){
		//清空
		dojo.empty('breadcumb');
		
		if(Nav._breadCrumbChain && Nav._breadCrumbChain.length > 0){
			//显示或隐藏面包屑
			//add by mjl
			if(Nav._breadCrumbChain.length==1){
				dojo.byId("breadcumb").className="hideBreadCrumb";
			}
			else{
				dojo.byId("breadcumb").className="showBreadCrumb";
			}
			
			var isfirst = true;

			for(var i = 0; i < Nav._breadCrumbChain.length; i++){
				var crumb = Nav._breadCrumbChain[i];
				if(!isfirst){
					//绘制连接符号 
					//箭头
					dojo.create("img"
							,{ src: window.contextRoot + "/china/images/breadcumb_n.png", width:"10px", height:"10px" }
							,dojo.byId('breadcumb'));
				} else {
					isfirst = false;
				}
				//绘制链接
				var isLast = ((i == Nav._breadCrumbChain.length-1)?true:false);
				
				var pageName = (crumb.name ? crumb.name : "没有名字的页面");
				console.log(isLast);
				if(isLast){
					//绘制label   <a href="#">添加物料</a>
					dojo.create("span"
							,{ title: pageName, innerHTML: pageName }
							,dojo.byId('breadcumb'));
				} else {
					//绘制链接Link  TODO 加入onclick
					dojo.create("a"
							,{ onclick: "Nav.goBack(" + i + ");return false;", href: "javascript:void(0);", title: pageName, innerHTML: pageName, "class": "visited" }
							,dojo.byId('breadcumb'));
				}
			}
		}
	},
	
	/**
	 * 根据返回的code决定下一步流程
	 */
	processReturnStatus: function(ioargs){
		var status = ioargs.xhr.status;
		var response = ioargs.xhr.responseText;
		switch (status) {
			case 302:
				Nav.redirect(ioargs.xhr.responseText);
				break;
			case 200:
				if (Nav.needRedirect(response)) {// server端设置了302, xhr得到的code依然为200.
					Nav.redirect(response);
				}
				break;
			case 0:
				Util.hideStandBy();
				new tms.china.widget.infoMsg.InfoMsg( {
					message : Util.getI18nValue("AJAX_ERROR_COMMON_MSG"),
					title : Util.getI18nValue("ERROR_DIALOG_TITLE"),
					type : Info_constant.FAIL
				}).show();
				window.location.reload();
				break;
			default:
				break;
		}
	},
	
	/**
	 * 是否需要跳转页面
	 */
	needRedirect: function(response){
		var redirectIndex = response.indexOf("document.location.href");
		if(redirectIndex == -1){
			return false;
		}else{
			return true;			
		}	
	},
	/**
	 * 跳转页面
	 */
	redirect: function(response){
		var redirectIndex = response.indexOf("document.location.href");
		//TODO use RegExp
		var str = response.substring(redirectIndex);
		var firstQ = str.indexOf("'");
		str = str.substring(firstQ + 1);
		var secondQ = str.indexOf("'");
		str = str.substring(0, secondQ);
		window.location.href = str;	
	}
	
	/**
	 * Core method for redirect main Dialog
	 * 
	 * href: 				子页面的URL
	 * dojo_class_name：  	子页面的页面对象名
	 * json_param: 			同时传递给Server的view方法,以及子页面对象构造函数的参数
	 * 
	 */
	,showDialog: function (href, dojo_class_name, json_param,title){
		try{
			console.debug("showDialog");
			
			Util.showStandBy();
			/*
			if(Nav._breadCrumbChain.length > 0){
				//Chain非空, 取得转移前页面恢复用数据,并且存储
				if(Common.currentpage && Common.currentpage.getRestoreData){
					var restoreData = Common.currentpage.getRestoreData();
					dojo.setObject("restoreData", restoreData, Nav._breadCrumbChain[Nav._breadCrumbChain.length-1]);
				}	
			}*/
			//dijit.byId("mainDialog").destroyDescendants();
			//更新Dialog内容
			dojo.xhrPost({
				url : window.actionPath + href,
				handleAs : "text",
				content : Util.prepareAjaxData(null, json_param),
				preventCache : true,
				error : null,
				load : function(response, ioargs){
							Nav.processReturnStatus(ioargs);
							//Destroy all the widgets inside the ContentPane and empty containerNode 
							dijit.byId("mainDialog").set("title",title);
							dijit.byId("mainDialog").set('content', response);
							dijit.byId("mainDialog").startup();
							dijit.byId("mainDialog").show();
						}
			});
			
			//请求需要的class
			dojo.require(dojo_class_name);
			
			//生成页面对象
			var pageObj = new (dojo.getObject(dojo_class_name))(json_param);
			
			//设置当前页面
			Common.currentDialogPage = pageObj;
			
			//加载页面所需要的require
			pageObj.addRequired();
			
			//设置Onload事件对应的初始化方法
			Util.bind(
					"mainDialog_onLoad"
					,dijit.byId("mainDialog")
					, 'onLoad'
					, Common.currentDialogPage
					, function(){ Common.currentDialogPage.initPage(); Util.hideStandBy();}
			);
		} catch (err) {
			console.error(err);
		}
	}
	
	/**
	 * Core method for redirect main Dialog
	 * 
	 * href: 				子页面的URL
	 * dojo_class_name：  	子页面的页面对象名
	 * json_param: 			同时传递给Server的view方法,以及子页面对象构造函数的参数
	 * 
	 */
	, closeDialog:function(){
		if(dijit.byId("mainDialog")){
			dijit.byId("mainDialog").hide();
			dijit.byId("mainDialog").destroyDescendants();
		}
	}
	
	/**********************页面URL定义***********************/
	//登录
	//test
	,TEST_TEST:'/test/test'
	//欢迎
	,WELCOME_PAGE:'/main/welcome'
	,IMPORT_EXCEL:'/importexcel/show'
    ,SALE_ORDER_LIST:'/saleOrder/list'
	,SALE_ORDER_EDIT:'/saleOrder/editOrder'
	,SALEORDER_VIEWDTAIL:'/saleOrder/viewDetail'

	,TEST_ADD:'/test/add'
	,TEST_VIEWDTAIL:'/test/viewDetail'
	,PURCHASER_ORDER_LIST:'/purchaserorder/list'
	,PURCHASER_ORDER_VIEWDTAIL:'/purchaserorder/viewDetail'
    ,PURCHASER_ORDER_EDIT:'/purchaserorder/promotEdit'
	,REPORT_INDEX:'/report/index'
		
	,SHIPMENT_ORDER_LIST:'/shipmentorder/list'
	,SHIPMENT_ORDER_VIEW:'/shipmentorder/viewDetail'
	,SHIPMENT_ORDER_EDIT:'/shipmentorder/promotEdit'	
	,TRANSFERDATA_LIST:'/transferdata/list'

};