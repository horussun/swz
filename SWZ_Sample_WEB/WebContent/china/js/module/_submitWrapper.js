define("tms/china/js/module/_submitWrapper", [ "dojo"], function(dojo) {
	dojo.declare("tms.china.js.module._submitWrapper",null, {
		constructor : function() {},
		
		/**
		 * 提交ajax请求
		 * url：请求的地址
		 * action：请求的action
		 * callback：回调函数
		 * withRefId: 是否要递交refId
		 * isMultiple: refId是否为多个
		 * gridId： grid的Id
		 */
		ajaxRequestMayWithRefIds : function(url, action, callback, content, withRefId, isMultiple, gridId){
			var refIds = null;
			if(withRefId){
				var refIds = this.getSelectedRefIds(gridId, isMultiple);
				if(refIds == null){
					var msg = null;
					if(isMultiple){
						msg = Util.getI18nValue("nb_freightpayment_chooseitem_multiple_alert");
					}else{
						msg = Util.getI18nValue("nb_freightpayment_chooseitem_single_alert");
					}
					
					new tms.china.widget.infoMsg.InfoMsg( 
							{
								message : msg,
								title : Util.getI18nValue("nb_public_alert"),
								type : Info_constant.CONFIRM
							}
					).show();
					return;
				}
			}
			if(content){
				if(refIds){						
					refIds = this.merge(refIds, content, false);
				}else{
					refIds = content;
				}
			}
			
			Util.showStandBy();
			Util.xhrPost(
				{
					url : window.actionPath + url,
					handleAs : "json",
					content : Util.prepareAjaxData(action, refIds),
					preventCache : true,
					error : function(error, ioargs){
						Util.ajaxErrorHandler(error, ioargs);
					},
					
					load : function(response, ioargs){
						Util.hideStandBy();
						if(callback){
							callback(response, ioargs);
						}
					}
					
				}
			);
			
		},
		
		/**
		 * 从grid中取得选中条目的refIds
		 * gridId： grid的Id
		 * isMultiple: refId是否为多选
		 */
		getSelectedRefIds : function(gridId, isMultiple){
			var refIdsJson = null;
			var grid = dijit.byId(gridId);
			var gridstore = grid.store;
			var selectedItems = grid.selection.getSelected();
			if(isMultiple && selectedItems.length >= 1){
				var jsonstr = "{identifier: 'refId', items:[";
				var selectedRefIds = {identifier: 'refId', items:[]};
				for(var index = 0; index < selectedItems.length; index++){
					var item = "{refId: '";
					item += gridstore.getValue(selectedItems[index], 'refId');
					item += "'},";
					jsonstr += item;
				}
				jsonstr = jsonstr.substring(0, jsonstr.lastIndexOf(','));
				jsonstr += "]}";
				refIdsJson = dojo.fromJson(jsonstr);
			}else if(selectedItems.length == 1){
				var jsonstr = "{refId: '";
				jsonstr += gridstore.getValue(selectedItems[0], 'refId');
				jsonstr += "'}";
				refIdsJson = dojo.fromJson(jsonstr);
			}
			return refIdsJson;	
		},
		
		/**
		 * 合并对象
		 * des：目的对象
		 * src：来源对象
		 * override： 是否覆盖
		 */
		merge : function(des, src, override){
		    if(src instanceof Array){
		        for(var i = 0, len = src.length; i < len; i++)
		        	merge(des, src[i], override);
		    }
		    for( var i in src){
		        if(override || !(i in des)){
		            des[i] = src[i];
		        }
		    } 
		    return des;
		}
	});
	return tms.china.js.module._submitWrapper;
});