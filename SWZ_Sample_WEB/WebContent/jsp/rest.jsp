<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="<c:url value='/js/jquery-1.3.2.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/application.js'/>"></script>
<script>
function restClient()
{
	/**
	 * 使用get方式调用服务
	 * @param url 资源地址
	 * @param data 参数
	 * @param dataType 返回值类型
	 * @param contentType 访问类型或参数类型
	 * @param cache 是否缓存
	 * @param async 是否异步加载
	 * @param success 成功时回调函数
	 * @param error 失败时回调函数
	 */
	this.get=function get(url,data,dataType,contentType,cache,async,success,error)
	{  
        ajax("GET",url,data,dataType,contentType,cache,async,success,error);
	};
	/**
	 * 使用post方式调用服务
	 * @param url 资源地址
	 * @param data 参数
	 * @param dataType 返回值类型
	 * @param contentType 访问类型或参数类型
	 * @param cache 是否缓存
	 * @param async 是否异步加载
	 * @param success 成功时回调函数
	 * @param error 失败时回调函数
	 */
	this.post=function post(url,data,dataType,contentType,cache,async,success,error)
	{
		 ajax("POST",url,data,dataType,contentType,cache,async,success,error);
	};
	/**
	 * 使用put方式调用服务
	 * @param url 资源地址
	 * @param data 参数
	 * @param dataType 返回值类型
	 * @param contentType 访问类型或参数类型
	 * @param cache 是否缓存
	 * @param async 是否异步加载
	 * @param success 成功时回调函数
	 * @param error 失败时回调函数
	 */
	this.put=function put(url,data,dataType,contentType,cache,async,success,error)
	{
		 ajax("PUT",url,data,dataType,contentType,cache,async,success,error);
	};
	/**
	 * 使用delete方式调用服务
	 * @param url 资源地址
	 * @param data 参数
	 * @param dataType 返回值类型
	 * @param contentType 访问类型或参数类型
	 * @param cache 是否缓存
	 * @param async 是否异步加载
	 * @param success 成功时回调函数
	 * @param error 失败时回调函数
	 */
	this.del=function del(url,data,dataType,contentType,cache,async,success,error)
	{
		ajax("DELETE",url,data,dataType,contentType,cache,async,success,error);
	};
	/**
	 * 将form表单转化为json数组
	 * @param formid form表单id
	 * @returns json数组
	 */
	this.form2Json=function form2Json(formid) {   
	    var formObj = $("#"+formid);   
	    var JsonObj = "'{";   
	    var a = formObj.serializeArray();   
	    var index = 0;   
	    $.each(a, function() {   
	        index++;   
	        JsonObj += "\"" + this.name + "\":\"" + this.value + "\"";   
	        if(a.length != 1 && a.length != index) {   
	            JsonObj += ",";   
	        }   
	    });   
	    JsonObj += "}'";   
	    return eval(JsonObj);   
	};   
	/**
	 * jquery ajax方法
	 * @param type 调用类型
	 * @param url 资源地址
	 * @param data 参数
	 * @param dataType 返回值类型
	 * @param contentType 访问类型或参数类型
	 * @param cache 是否缓存
	 * @param async 是否异步加载
	 * @param success 成功时回调函数
	 * @param error 失败时回调函数
	 */
	function ajax(type,url,data,dataType,contentType,cache,async,success,error)
	{
	      $.ajax(
	    	    	{
	    			  type: type,
	    			  data: data,
	    			  dataType:dataType,
	    			  contentType:contentType,
	    			  url:url,
	    			  success: success,
	    			  error: error,
	    			  async:async,
	    			  cache:cache
	    			});
	}
}

function sayHello()
{
	var rc=new restClient(); 
	Var parameter={};
	Parameter.name="fw";
	rc.post("http://127.0.0.1:8080/fw/rest/test/hello",parameter,"json",'application/json',false,false,success,error);
}
function success(data, textStatus)
{
   Alert(data);
}
function error()
{
  alert("调用失败");	
}
</script>
