define("tms/china/widget/_Page", ["dojo", "dijit", "tms/china/widget/_Widget"], function(dojo) {
    /**
     * 这是一个基类, 每个页面都会继承这个类. 在其中提供一些通用的方法和属性
     * 20111110 胡捷: 新建
     */
    dojo.declare("tms.china.widget._Page", [tms.china.widget._Widget], {
       
        eventHandle : null,
        showErrMsg : function(errMsg) {
            dojo.require("tms.china.widget.infoMsg.InfoMsg");
            new tms.china.widget.infoMsg.InfoMsg({
                title : Common.I18N.resourceBundle.NB_BILL_TITLE_error,
                type : Info_constant.FAIL,
                message : errMsg
            }).show();
        },
        showSuccessMsg : function(successMsg) {
            dojo.require("tms.china.widget.infoMsg.InfoMsg");
            new tms.china.widget.infoMsg.InfoMsg({
                title : Common.I18N.resourceBundle.nb_public_result,
                type : Info_constant.SUCCESS,
                message : successMsg
            }).show();
        },
        showConfirmMsg : function(confirmMsg) {
            dojo.require("tms.china.widget.infoMsg.InfoMsg");
            new tms.china.widget.infoMsg.InfoMsg({
                title : Common.I18N.resourceBundle.nb_public_confirm,
                type : Info_constant.CONFIRM,
                message : confirmMsg
            }).show();
        },
        /**
         * Method for submit form
         */
        _submitForm : function(url/* url */, formId/* form to submit */, load_func, error_func, addContent /*
         * additional
         * content to submit
         */

        ) {
            console.debug("_submitForm");
            Util.xhrPost({
                // The URL of the request
                url : url,
                content : addContent,
                form : dojo.byId(formId),
                // The success handler
                load : load_func,
                // The error handler
                error : error_func
            });
        },
        initPage : function() {

        }
        // Uncomment above comma and add comma-separated
        // functions here. Do not leave a
        // trailing comma after last element.
        ,
        restoreData : function() {
            if(this.form) {
                var restoreData = Nav.getRestoreData();
                if(restoreData) {
                    var formValues = dojo.getObject("values", false, restoreData);
                    // restore form values
                    this.form.setValues(formValues);
                }
            }
        },
        getRestoreData : function() {
            var restoreData = {};
            // 保存search的form的数据
            if(this.form) {
                var values = this.form.getValues();
                dojo.setObject("values", values, restoreData);
            }
            return restoreData;
        },

        getPageGrid : function(type) {
        	
        },
        getSortWidget : function(name) {
        	
        },
       
        
        
        showCustomizeGridDialoag : function(name) {
            var customizeGrid = new tms.china.widget.customizeGridWidget.customizeGridWidget({
                name : name,
                gridId : name
            });
            customizeGrid.show();

        },
        
        updateUI : function(name) {
            var layout = this.getLayout(name);
            dijit.byId(name).store.url = null;
            dijit.byId(name).setStructure(layout);
            if(this.searchPart) {
                this.searchPart.searchWithValidation();
            }
        },

        name : null,
        form : null,
        _errMsgDialog : null
    });

    return tms.china.widget._Page;
});
