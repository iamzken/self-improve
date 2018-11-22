function include(urls,callback){
	
	//分析外部引入的文件路径
	var private_parseurl = function(file){
	    var result = new Array();
	    if(null == file || undefined == file){
	        return result;
	    }
	    var files = typeof file == "string" ? [file] : file;
	    for (var i = 0; i < files.length; i++)
	    {
	        var obj = {};
	        var _path = files[i].replace(/^\s|\s$/g, "");
	        obj.att = _path.split('.');
	        obj.ext = obj.att[obj.att.length - 1].toLowerCase();
	        obj.isCSS = obj.ext == "css";
	        obj.path = _path;
	        obj.id = _path.split("/");
	        obj.id = obj.id[obj.id.length - 1].replace("." + obj.ext,"").replace(/\./g,"-");
	        result.push(obj);
	    }
	    return result;
	};
	//获取一个HTTP请求对象
	var privateGetHttpRequest = function(){ 
	    if(window.XMLHttpRequest){// Gecko 
	        return new XMLHttpRequest();
	    }else if( window.ActiveXObject ){ // IE 
	        return new ActiveXObject("MsXml2.XmlHttp");
	    }
	 }; 
	
	 //将脚本导入到head区
	 var privateIncludeJS = function(sid,obj,source){
		 // && (!document.getElementById(sid))
	     if ((source != null)){
	    	 var ohead = document.getElementsByTagName('HEAD').item(0); 
	         var oscript = document.createElement("script"); 
	         oscript.language = "javascript"; 
	         oscript.type = "text/javascript"; 
	         //oScript.id = sid;
	         //oscript.defer = true; 
	         oscript.text = source;
	         ohead.appendChild(oscript);
	     }
	 };
	
	 var privateIncludeCSS = function(sid,obj){
		 if (obj.path.length != 0){
			var ohead = document.getElementsByTagName('HEAD').item(0); 
            var tag = document.createElement("link");
            tag.setAttribute('type', 'text/css');
            tag.setAttribute('rel', 'stylesheet');
            tag.setAttribute('href', obj.path);
            ohead.appendChild(tag);
        }
	 };
	 
	 var privateLoadContent = function(sid, obj){ 
		    var oXmlHttp = privateGetHttpRequest();
		    oXmlHttp.onreadystatechange = function(){ 
		        if (oXmlHttp.readyState == 4){ 
		            if (oXmlHttp.status == 200 || oXmlHttp.status == 304){ 
		            	privateIncludeJS(sid,obj,oXmlHttp.responseText ); 
		            }else{ 
		                console.log( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ; 
		            } 
		        } 
		    } 
		    oXmlHttp.open('GET', obj.path, false); 
		    oXmlHttp.send(null); 
	 }; 
	 
	 
	 var arrs = private_parseurl(urls);
    for (var i = 0 ; i < arrs.length; i ++){
        var obj = arrs[i];
        if (obj.path != null && obj.path != ""){
        	if(obj.ext == "js"){
        		privateLoadContent(obj.id,obj);
        	}else{
        		privateIncludeCSS(obj.id,obj);
        	}
        }
    }
    if(!(undefined == callback || null == callback)){
	    callback();
	}
	
}
//加载框架所需资源
include(["css/bootstrap.min.css","css/font-awesome.min.css","css/dataTables.bootstrap.css","js/lib/jquery.query-2.1.7.js","js/common.tools.js","js/lib/bootstrap/bootstrap.min.js","js/lib/handlebars.js","js/handlebars.helper.js","js/handlebars.helper.js","js/lib/bootstrap/bootbox/bootbox.js"]);
//核心API
window.CORE = (function(){
	return {
		//获取keys
		objectKeys : function(obj){
			var keys = [];
		    for(var p in obj){
		        if(obj.hasOwnProperty(p)){
		            keys.push(p);
		        }
		    }
		    return keys;
		},
		//获取values
		objectValues : function(obj){
			var values = [];
		    for(var p in obj){
		        keys.push(obj[p]);
		    }
		    return values;
		},
		getParam : function(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
			var r = window.location.search.substr(1).match(reg);
			if (r != null) return unescape(r[2]); return null;
		},
		checkAjaxResult : function (data){
			if(data.status == 99){
				if(!window.alerted){
					alert("您尚未登录或者登录已超时！");
					window.alerted = true;
				}
				window.top.location.href = data.data.loginHost;
				return false;
			}else if(data.status == 98){
				alert(data.msg);
				return false;
			}
			return true;
		},
		ajaxCheck : function(method){
			if(AUTH && AUTH.options){
				var auth_aid = CORE.getParam("auth_aid"),
					auth_mid = CORE.getParam("auth_mid");
				return AUTH.options["" + auth_aid]["" + auth_mid] == 1;
			}
			return true;
		},
		ajax : function(options){
			if(options == undefined){ options = {}; }
			var opts = $.extend(true,{}, options);
			var d = $.extend(true,{
				auth_aid:CORE.getParam("auth_aid"),
				auth_mid:CORE.getParam("auth_mid"),
				auth_cid:CORE.getParam("auth_cid")
			},opts.data);
			opts.data = d;
			opts.success = function(data){
				if(CORE.checkAjaxResult(data)){
					if(options.success){
						options.success(data);
					}
				}
			};
			return $.ajax(opts);
		}
	};
})();


window.UI = (function(){
	var basePath = 'js/eui/';
    var es = document.getElementsByTagName('script');
    for(var i = 0;i < es.length; i++){
    	var s = es[i]['src'].indexOf("eui.js");
    	if(s !=-1 ){
    		basePath = es[i].src.replace("eui.js","");
    	}
    }
	return {
		template : (function(){
			return Handlebars;
		})(),
//		toast：
		alert:function(options,callback){
			var r={};
			include([basePath + "plugin/window.js"],function(){
				r=UI.plugin.alert(options,callback);
			});
			return r;
		},
		confirm:function(options,callback){
			var r={}
			include([basePath + "plugin/window.js"],function(){
				r=UI.plugin.confirm(options,callback);
			});
			return r;
		},
		dialog:function(options,callback){
			var r={};
			include([basePath + "plugin/window.js"],function(){
				r=UI.plugin.dialog(options,callback);
			});
			return r;
		},
		prompt:function(options,callback){
			var r={};
			include([basePath + "plugin/window.js"],function(){
				r=UI.plugin.prompt(options,callback);
			});
			return r;
		},
		window:function(options){
			var r={};
			include([basePath + "plugin/window.js"],function(){
				r=UI.plugin.window(options);
			});
			return r;
		},
		toast:function(options){
			var r={};
			include([basePath + "plugin/window.js",basePath + "plugin/toast.css"],function(){
				r=UI.plugin.toast(options);
			});
			return r;
		},
		pager:function(selector,options){
			var r = {};
			include([basePath + "plugin/pager.js"],function(){
				r = UI.plugin.pager(selector,options);
			});
			return r;
		},
		datagrid:function(selector,options){
			var r = {};
			include([basePath + "plugin/datagrid.js"],function(){
				r = UI.plugin.datagrid(selector,options);
			});
			return r;
		},
		combobox:function(selector,options){
			var r={};
			include([basePath + "plugin/combobox.js"],function(){
				r=UI.plugin.combobox(selector,options);
			});
			return r;
		},
		treemenu:function(selector,options){
			var r={};
			include([basePath + "plugin/zTree/css/zTreeStyle/metro.css",basePath + "plugin/zTree/js/jquery.ztree.all-3.5.min.js",basePath + "plugin/treemenu.js"],function(){
				r=UI.plugin.treemenu(selector,options);
			});
			return r;
		},
		datetime:function(selector,options){
			var r={};
			include([basePath + "plugin/bootstrap-datetime/bootstrap-datetimepicker.css",basePath + "plugin/bootstrap-datetime/bootstrap-datetimepicker.js",basePath + "plugin/datetime.js"],function(){
				r=UI.plugin.datetime(selector,options);
			});
			return r;
			
		},
		accordion:function(selector,options){
			var r={};
				include([basePath + "plugin/accordion.js",basePath + "plugin/accordion.css"],function(){
				r=UI.plugin.accordion(selector,options);
			});
			return r;
		},
		tabs:function(selector,options){
			var r={};
			include([basePath + "plugin/tabs.js"],function(){
				r=UI.plugin.tabs(selector,options);
			});
			return r;
		},
		searchbox:function(selector,options){
			var r={};
			include([basePath + "plugin/searchbox.js"],function(){
				r=UI.plugin.searchbox(selector,options);
			});
			return r;
		},
		layout:function(selector,options){
			var r={};
			include([basePath + "plugin/layout.css",basePath + "plugin/layout.js"],function(){
				r=UI.plugin.layout(selector,options);
			});
			return r;
		},
		validator:function(selector,options){
			var r={};
			include([basePath + "plugin/validator.css",basePath + "plugin/validator.js"],function(){
				r=UI.plugin.validator(selector,options);
			});
			return r;
		},
		treegrid:function(selector,options){
			var r={};
			include([basePath + "plugin/datagrid.js",basePath + "plugin/treegrid/jquery.treegrid.css",basePath + "plugin/treegrid/jquery.treegrid.js",basePath + "plugin/treegrid.js"],function(){
				r=UI.plugin.treegrid(selector,options);
			});
			return r;
		},
		setInput:function(selector,options){
			var r={};
			include([basePath + "plugin/setInput.js"],function(){
				r=UI.plugin.setInput(selector,options);
			});
			return r;
		}
	};
})();

if(window.self != window.top){
	var boxs = "alert|confirm|window|dialog|prompt|toast|customalt".split("|");
	
	for(var i = 0; i < boxs.length; i ++){
		try{
			window.UI[boxs[i]] = window.top.UI[boxs[i]];
			
		}catch(e){
			
		}
	}
	//window.CORE = window.top.CORE;
}
