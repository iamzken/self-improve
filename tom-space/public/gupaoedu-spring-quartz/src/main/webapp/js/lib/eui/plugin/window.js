window.UI.plugin = (function(){
	var private_template = {
			messager : function(options){
				var messageId = "modal-message";
				var messager = $('<div id="' + messageId + '" style="display: none;" aria-hidden="true">' +
				        '<div class="modal-dialog">' +
				        '<div class="modal-content">' +
				            '<div class="modal-header">' +
				               options.header +
				            '</div>' +
				            '<div class="modal-title">' + options.title + '</div>' +
				            '<div class="modal-body">' + options.msg + '</div>' +
				            '<div class="modal-footer">' +
				                options.footer +
				            '</div>' +
				        '</div>' +
				    '</div>' +
				'</div>');
				if($("#" + messageId).length == 0){
					messager.appendTo($("body"))
				}else{
					$("#" + messageId).html($(messager).html());
				}
				$("#" + messageId).attr("class","").addClass('modal ' + options.className + ' fade');
				return $("#" + messageId);
			}
	},
	private_frame_call = (function(){
		var idName 		= "FrameCall";
		var idNameAll	= "#" + idName;
		var ie = !-[1,];//是否ie
		return{
			apiOpen:function(){
				var html = '<input id="' + idName + '" type="hidden" action="1" value="1" onclick="UI.frameCall.api()" result=""/>';
				$(html).prependTo('body');
			},
			//其他窗口调用该窗口函数，调用另一个frame的方法
			api:function(){
				var action = $(idNameAll).attr('action');
				var value=$(idNameAll).attr('value');
				
				if (action == 'get') {//获取变量
					share.data('create_app_path',eval(value));
					return;
				}	
				var fun=action+'('+value+');';//拼装执行语句，字符串转换到代码
				try{
					$(idNameAll).attr("result",eval(fun));
				} catch(e) {};
			},
			//该窗口调用顶层窗口的子窗口api,调用iframe框架的js函数.封装控制器。
			top:function(iframe,action,value){
				if (!window.parent.frames[iframe]) return;
				var obj = window.top.frames[iframe].document;
	            if(!obj) return;
				obj=obj.getElementById(idName);		
				$(obj).attr("action",action);
				var val = (value ? JSON.stringify(value.split(",")).replace(/\[|\]/g,'') : "");
				$(obj).attr("value",val);
				$(obj).click();
				return eval("(" + $(obj).attr("result") + ")");
			},
			//该窗口调用父窗口的api
			child:function(iframe,action,value){
				if (!window.frames[iframe]) return;
				var obj = window.frames[iframe].document;
	            if(!obj) return;
				obj=obj.getElementById(idName);
				$(obj).attr("action",action);
				var val = (value ? JSON.stringify(value.split(",")).replace(/\[|\]/g,'') : "");
				$(obj).attr("value",val);
				$(obj).click();
				return eval("(" + $(obj).attr("result") + ")");
			},
			//该窗口调用父窗口的api
			father:function(action,value){
				var obj=window.parent.document;
				obj=obj.getElementById(idName);
				$(obj).attr("action",action);
				var val = (value ? JSON.stringify(value.split(",")).replace(/\[|\]/g,'') : "");
				$(obj).attr("value",val);
				$(obj).click();
				return eval("(" + $(obj).attr("result") + ")");
			},
			//___自定义通用方法，可在页面定义更多提供给接口使用的api。
			goUrl:function(url){
				window.location.href=url;
			},
			goRefresh:function(){
				window.location.reload(); 
			}
		}
	})();
	private_frame_call.apiOpen();//初始化开放API调用
	
	return {
		template : (function(){
			return Handlebars;
		})(),
		alert : function(options,callback){ //警告框
			var opts = jQuery.extend(true,{
                mask:false,		//是否加遮罩
                icon:"info",	//默认图标
                msg:"",			//提示信息
                title:"",		//标题
                ok:"关闭",		//按钮名称
                onClose:callback
            },options);
			var icons = {
					"success":"glyphicon glyphicon-check",
					"warning":"fa fa-warning",
					"danger":"glyphicon glyphicon-fire",
					"info":"fa fa-envelope"
					};
			opts.className = ('modal-message modal-' + options.icon);
			opts.header = ('<i class="' + icons[opts.icon] + '"></i>');
			opts.body = opts.msg;
			opts.footer = ('<button type="button" class="btn btn-' + opts.icon + '" data-dismiss="modal">' + opts.ok + '</button>');
			return private_template.messager(opts).modal();
		},
		confirm : function(options,callback){ //确认框
			var opts = jQuery.extend(true,{
                mask:false,		//是否加遮罩
                icon:"info",	//默认图标
                title:"重要操作",		//标题
                msg:'',
                width:300,
                height:100,
                confirm:"确认",		//确认按钮名称
                cancel:"取消",
                onClose:function(){},
                onCancel:function(){},
                onConfirm:function(){}
            },options);
			opts.message=opts.msg;
			opts.buttons={  
                "确认": {  
                    label: "确认",  
                    className: "btn-primary",  
                    callback: function () {  
                       opts.onConfirm();  
                    }  
                }  
                ,"取消": {  
                    label: "取消",  
                    className: "btn-default",  
                    callback: function () {  
                        opts.onCancel();
                    }  
                }  
            }  
			var w = bootbox.dialog(opts,function(){opts.callback();});
			$(".modal-dialog",w).css({width:opts.width,left:"50%",marginLeft:"-"+parseInt(opts.width)/2+"px"});
			$(".bootbox-body",w).css({height:opts.height});
			return w;
		},
		dialog : function(options){ //会话框
			var opts = jQuery.extend(true,{},options);
			var w=bootbox.dialog(opts);
			//w.find(".modal-dialog").css("marginLeft","-"+(parseInt(w.find(".modal-dialog").width())/2)+"px")
			return w;
		},
		window : function(options){//窗体
			var opts = jQuery.extend(true,{url:"",width:400,height:300},options);
			opts.message = '<IFRAME src="' + opts.url + '" style="width:100%;height:100%;border:0;overflow:hidden;"></IFRAME>';
			var w = bootbox.dialog(opts);
			$(".modal-dialog",w).css({width:opts.width,left:"50%",marginLeft:"-"+opts.width/2+"px"});
			$(".bootbox-body",w).css({height:opts.height});
			w = jQuery.extend(true,{
				getWindow:function(){
					//alert($("iframe",this)[0].contentWindow);
					return $("iframe",this)[0].contentWindow;
				}
			},w);
			return w;
		},
		prompt : function(options,callback){//输入框
			var opts = jQuery.extend(true,{
                mask:false,		//是否加遮罩
                icon:"info",	//默认图标
                title:"",		//标题
                msg:'',
                confirm:"确认",		//确认按钮名称
                cancel:"取消",
                onClose:function(){},
                onCancel:function(){},
                onConfirm:function(){},
                callback:function(){}
            },options);
			return bootbox.prompt(opts.msg,callback);
		},
		toast : function(options){//渐隐提示框
			var opts = jQuery.extend(true,{
            },options);
			var html="<div class='jianyin' >"+opts.msg+"</div>";
			if($("body").find(".jianyin").prop("class")!="jianyin"){
				$("body").append(html);
			}else{
				$(".jianyin").html(opts.msg);
				$(".jianyin").css("display","block");
			}
			var width=$("body").find(".jianyin").width(),
			height=$("body").find(".jianyin").height();
			$("body").find(".jianyin").css({marginTop:"-"+(height/2),marginLeft:"-"+(width/2)});
			setTimeout(function(){
				$(".jianyin").fadeOut(300,function(){
					if(typeof opts.callback=="function"){						
						opts.callback();
					}
				});
			},1000);
		},
		customalt : function(options){//自定义按钮提示框
			alert(options);
		},
		mask : function(){ //添加遮罩
			alert("添加遮罩");
		},
		loading : function(){ //添加带加载提示的遮罩
			alert("添加带加载提示的遮罩");
		},
		close : function(){ //关闭任意弹窗
			alert("关闭任意弹窗");
		},
		frameCall : private_frame_call, //窗体之间相互调用API接口定义
	}
})();