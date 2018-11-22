
/**
 * 动态载入页面内容
 * @param selector 选择器
 * @param url 请求url
 * @param data 参数
 * @param includes 渲染文件数组
 * @param success 载入成功时的回调函数
 */
if(jQuery)(function($) {
    //普通load
    $.fn.loadPage = function(options) {
        var opts = $.extend(true,this.loadpageDftVal, options);
        return $(this).each(function() {
            if(!(null == opts.url || opts.url == undefined)){
                $(this).load(opts.url,opts.data,function(response,status,xhr){
                    private_reloadIncludes($(this),opts.includes,function(){
                        if(opts.success){opts.success(response,status,xhr);}
                    });
                });
            }
        });
    };


    //以get方式发送请求
    $.fn.getPage = function(options) {
        var opts = $.extend(true,this.loadpageDftVal, options);
        return $(this).each(function(i,o) {
            if(!(null == opts.url || opts.url == undefined)){
                $.ajax({
                    type: "GET",
                    url:opts.url,
                    async:false,
                    data:opts.data,
                    success:function(response){
                        private_reloadIncludes($(this),opts.includes,function(){
                            $(this).append(response);
                            if(opts.success){opts.success(response);}
                        });
                    },
                    error:function(){
                    }
                });
            }
        });
    };

    
    //跨域在iframe中跨域
    $.fn.loadCrossDomainPage = function(options){
        var opts = $.extend(true,this.loadpageDftVal, options);
        return $(this).each(function() {
            if(!(null == opts.url || opts.url == undefined)){
                var iframe = $('<IFRAME/>')
                    .attr("frameborder",0)
                    .attr("allowTransparency",true)
                    .attr("src",opts.url)
                    .width($(this).width())
                    .height($(this).height())
                    .css({overflow:'hidden',backgroundColor:'transparent'});
                if(opts.css == undefined){
                    iframe.css(opts.css);
                }
                $(this).empty().append(iframe);
            }
        });
    };
    //设置默认值
    this.loadpageDftVal = {
        url:"",
        css:{},
        data:{},
        includes:[],
        success:function(response,status,xhr){}
    };

    //将数据填充至模板中
    $.fn.template = function(options){
        var opts = $.extend(true,{
            datas:[],
            fields:[],
            onComplete:function(item,o,i){}}, options);
        var allItems = new Array();
        $(this).each(function() {
            var tmpl = $(this).html();
            var fieldMap = new Array();
            $.each(opts.fields,function(i,o){
                fieldMap[o.name] = o;
            });
            $.each(opts.datas,function(i,o){
                var item = $(tmpl);
                $.each(o,function(key,val){
                    var m = fieldMap[key];
                    var className = key;
                    var fitVal = val;
                    if(!(null == m || undefined == m)){
                        if(!(null == m.className || undefined == m.className)){
                            className = m.className;
                        }
                        if(!(null == m.formatter || undefined == m.formatter)){
                            var s = m.formatter(val,o);
                            if(!(s == undefined || s == null)){
                                o[m] = s;
                            }
                        }
                    }
                    var attr = item.attr(key,val).find("." + className);
                    attr.html(fitVal);
                });
                allItems.push(item);
                opts.onComplete(item,o,i);
            });
        });
        return allItems;
    };

    /**
     * 给对象添加遮罩
     */
    $.fn.addMask = function(msg, maskDivClass){
        this.removeMask();
        // 参数
        var op = {
            opacity : 0.4,
            z : 10000,
            bgcolor : '#CCC'
        };
        var original = $(document.body);
        var position = {
            top : 0,
            left : 0
        };
        if (this[0] && this[0] !== window.document) {
            original = this;
            position = original.position();
        }
        // 创建一个 Mask 层，追加到对象中
        var maskDiv = $('<div class="mask_div"> </div>');
        maskDiv.appendTo(original);
        var maskWidth = original.outerWidth();
        if (!maskWidth) {
            maskWidth = original.width();
        }
        var maskHeight = original.outerHeight();
        if (!maskHeight) {
            maskHeight = original.height();
        }
        maskDiv.css({
            position : 'absolute',
            top : position.top,
            left : position.left,
            'z-index' : op.z,
            width : maskWidth,
            height : maskHeight,
            'background-color' : op.bgcolor,
            opacity : 0,
            cursor : 'wait'
        });
        if (maskDivClass) {
            maskDiv.addClass(maskDivClass);
        }
        if (msg) {
            var msgDiv = $('<div style="position:absolute;background:#fff;"><div style="line-height:24px;border:#CCC 1px solid;background:#fff;padding:2px 10px 2px 10px;">' + msg + '</div></div>');
            msgDiv.appendTo(maskDiv);
            var widthspace = (maskDiv.width() - msgDiv
                .width());
            var heightspace = (maskDiv.height() - msgDiv
                .height());
            msgDiv.css({
                top : (heightspace / 2 - 2),
                left : (widthspace / 2 - 2)
            });
        }
        maskDiv.fadeIn('fast', function() {
            // 淡入淡出效果
            $(this).fadeTo('slow', op.opacity);
        });
        return maskDiv;
    };

    /**
     * 删除遮罩
     */
    $.fn.removeMask = function(){
        var original = $(this);
        if (this[0] && this[0] !== window.document) {
            original = $(this[0]);
        }
        original.find("> div.mask_div").fadeOut('slow',
            0, function() {
                $(this).remove();
            });
    };

})(jQuery);


/**
 * 单例插件
 */
if(jQuery)(function(jQuery){
    $.extend({
        includePath: '',
        //载入
        include: function(files,_callback){
            var arrs = private_parsefile(files);
            var css = arrs[0];
            var js = arrs[1];
            for (var i = 0 ; i < css.length; i ++){
                var obj = css[i];
                if ($((obj.tag + "[" + obj.link + "]").replace(/=/,"^=")).length == 0){
                    var styleTag = document.createElement(obj.tag);
                    styleTag.setAttribute('type', 'text/css');
                    styleTag.setAttribute('rel', 'stylesheet');
                    styleTag.setAttribute('href', obj.path);
                    $("head")[0].appendChild(styleTag);
                    //$("head")[0].append($("<" + obj.tag + obj.attr + obj.link + " />"));
                }
            }
            for (var i = 0 ; i < js.length; i ++){
                var obj = js[i];
                if ($((obj.tag + "[" + obj.link + "]").replace(/=/,"^=")).length == 0){
                    $("head").append("<" + obj.tag + obj.attr + obj.link + "></" + obj.tag + ">");
                }
            }
            if(!(undefined == _callback || null == _callback)){
                _callback();
            }
        },
        //卸载
        uninclude: function(files){
            var arrs = private_parsefile(files);
            for (var i = 0 ; i < arrs.length; i ++){
                var arr = arrs[i];
                for (var j = 0 ; j < arr.length; j ++){
                    var obj = arr[j];
                    if ($(obj.tag + "[" + obj.link + "]").length > 0) {
                        $(obj.tag + "[" + obj.link + "]").remove();
                    }
                }
            }
        },
        //跨域POST请求
        syncPost : function(options){
        	var opts = $.extend(true,{formId:"syncForm",resultIframeId:"syncResult",data:{},url:"",callback:"",error:function(){}}, options);
        	if($("#" + opts.resultIframeId).length == 0){
        		$("body").append('<iframe style="display: none !important" id="' + opts.resultIframeId + '" name="' + opts.resultIframeId + '"></iframe>');
        	}else{
        		$("#" + opts.resultIframeId).attr("name",opts.resultIframeId);
        	}
        	if($("#" + opts.formId).length == 0){
        		$("body").append('<form style="display: none;" id="' + opts.formId + '" target="' +  opts.resultIframeId + '" name="' + opts.formId + '" method="POST"></form>');
        	}
        	$("#" + opts.formId).empty();$("#" + opts.resultIframeId).attr("src","javascript:void(0);");
        	
        	
        	for(var attr in opts.data){
        		$("#" + opts.formId).append('<input type="hidden" name="' + attr + '" value=\'' + opts.data[attr] + '\'/>');
            }
        	
        	if(opts.callback && opts.callback.length > 0){
        		$("#" + opts.formId).append('<input type="hidden" name="callback" value="' + opts.callback + '"/>');
        		$("#" + opts.formId).append('<input type="hidden" name="script" value="1"/>');
        	}
        	
        	$("#" + opts.formId).attr("action",opts.url).submit();
        	$("#" + opts.resultIframeId).load(function(){
        		$("#" + opts.formId).attr("action","javascript:void(0);").empty();
        	});
        },
        //获得浏览器版本
        browserVersion:function(){
            var bro=$.browser;
            var binfo = "";
            if(bro.msie) {binfo = "IE " + bro.version;}
            if(bro.mozilla) {binfo = "MF " + bro.version;}
            if(bro.safari) {binfo = "Safari " + bro.version;}
            if(bro.opera) {binfo = "Opera "+ bro.version;}
            return binfo;
        },
        //创建遮罩
        mask:function(){
            var mask = null;
            if($("#mask").length > 0){
                mask = $("#mask");
            }else{
                mask = $('<div id="mask"></div>');
                mask.appendTo("body");
            }
            mask.addClass('mask');
            mask.height($(document).height()).show();
            return mask;
        },
        //弹出模式窗口,含遮罩
        dialog:function(options) {
            var opts = jQuery.extend(true,{
                mask:true,
                width:0,
                height:0,
                url:"",
                title:"",
                includes:[],
                success:function(w){}
            },options);
            var box = null;
            var id = "dialog";
            var content = $('<div></div>');
            if(opts.url){
                content.loadPage({
                    url:opts.url,
                    includes:opts.includes,
                    success:function(txt){
                        var w = createWin();
                        var ii = 0 ;
                        var interval_disappear = setInterval(function() {
                            if(ii == 0) {
                                ii ++;
                                w.center();
                                w.show();
                                opts.success(w);
                            }else{
                                clearInterval(interval_disappear);
                            }
                        }, 20);
                    }});
            }
            //关闭窗体
            var closeWin = function(){
                private_closeWin(id,opts.mask,function(){
                    $("#" + id).find(".panel_cc").empty();
                });
            };
            //创建窗体
            var createWin = function(){
                //创建遮罩
                $.mask();
                //创建弹窗

                if($("#" + id).length > 0){
                    box = $("#" + id);
                    box.close = closeWin;
                }else{
                    box = $('<div id="' + id + '"><div class="shadow"><div class="panel_title"><span class="title"></span><div class="dialog_panel"><a class="icons close_btn" href="javascript:void(0);" title="关闭"></a></div></div><div class="panel_cc"></div><span class="dialog_bottom"></span></div></div>');
                    box.find(".close_btn").unbind("click").bind("click",closeWin);
                    box.appendTo("body");
                    box.close = closeWin;
                }
                if(!(0 == opts.width || undefined == opts.width)){
                    box.width(opts.width);
                }
                if(!(0 == opts.height || undefined == opts.height)){
                    box.height(opts.height);
                }
                if(!(null == opts.title || undefined == opts.title)){
                }
                box.addClass("win_box");
                box.find(".title").empty().html(opts.title);
                box.find(".panel_cc").empty().append(content);
                return box;
            };
            return box;
        },
        //警告窗口
        alert:function(options){
            var opts = jQuery.extend(true,{
                mask:false,
                msg:"",
                title:null,
                onClose:function(){}
            },options);
            var id = "alert";
            //创建遮罩
            if(opts.mask){
                $.mask();
            }
            //创建窗体
            var box = null;
            if($("#" + id).length > 0){
                box = $("#" + id);
            }else{
                box = $('<div id="' + id + '"><div class="shadow"><div class="panel_title"><span class="title"></span></div><div class="panel_cc"><span class="msg"></span></div><div class="alert_bottom"><a class="close_btn panel_btn" href="javascript:void(0);">确定</a></div></div></div>');
                box.find(".close_btn").unbind("click").bind("click",function(){
                    if(false != opts.onClose()){
                        private_closeWin(id,opts.mask);
                    }
                });
                box.appendTo("body");
            }
            if(null == opts.title || undefined == opts.title){
                opts.title = "提示";
            }
            box.addClass("win_box").addClass("msg_box");
            box.find(".title").empty().html(opts.title);
            box.find(".msg").html(opts.msg);
            box.show();
            box.center();
            return box;
        },
        customalt:function(options){
            var opts = jQuery.extend(true,{
                mask:false,
                msg:"",
                title:null,
                buttons:[{id:"",name:"确定",className:"",css:{},onClick:function(){}}],
                onClose:function(){}
            },options);
            var id = "customalt_" + $("[id^='customalt_']").length;
            //创建遮罩
            if(opts.mask){
                $.mask();
            }
            //创建窗体
            var box = null;
            if($("#" + id).length > 0){
                box = $("#" + id);
            }else{
                box = $('<div id="' + id + '" class="customalt"><div class="shadow"><div class="panel_title"><span class="title"></span><span class="close_btn"><a href="javascript:void(0);">×</a></span></div><div class="panel_cc"><span class="msg"></span></div><div class="customalt_bottom"></div></div></div>');
                var btnbox = box.find(".customalt_bottom");
                for(var i in opts.buttons){
                    if(isNaN(i)){continue;}
                    var o = opts.buttons[i];
                    var btn = $('<a class="panel_btn" href="javascript:void(0);">' + o.name + '</a>').unbind("click").click(function(){
                        private_closeWin(id,opts.mask);
                    });
                    if(o.onClick){btn.click(o.onClick);}
                    if(o.css){btn.css(o.css)}
                    if(o.className){btn.addClass(o.className);}
                    if(o.id){btn.attr("id",o.id)}
                    btnbox.append(btn);
                    if(i <= opts.buttons.length-2){
                        btnbox.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                    }
                }
                box.find(".close_btn").unbind("click").bind("click",function(){
                    if(false  != opts.onClose()){
                        private_closeWin(id,opts.mask);
                    }
                });
                box.appendTo("body");
            }
            if(null == opts.title || undefined == opts.title){
                opts.title = "提示";
            }
            box.addClass("win_box").addClass("msg_box");
            box.find(".title").html(opts.title);
            box.find(".msg").html(opts.msg);
            box.show();
            box.center();
            //alert(box.html());
            return box;
        },
        //确认窗体
        confirm:function(options){
            var opts = jQuery.extend(true,{
                mask:false,
                msg:"",
                title:null,
                onConfirm:function(){},
                onCancel:function(){},
                onClose:function(){}
            },options);
            var id = "confirm";
            //创建遮罩
            if(opts.mask){
                $.mask();
            }
            //创建窗体
            var box = null;
            if($("#" + id).length > 0){
                box = $("#" + id);
            }else{
                box = $('<div id="' + id + '"><div class="shadow"><div class="panel_title"><span class="title"></span></div><div class="panel_cc"><span class="msg"></span></div><div class="confirm_bottom">' +
                    '<a class="close_btn panel_btn" href="javascript:void(0);">确定</a>' +
                    '&nbsp;&nbsp;&nbsp;&nbsp;' +
                    '<a class="cancel_btn panel_btn" href="javascript:void(0);">取消</a>' +
                    '</div></div></div>');
                box.find(".close_btn").unbind("click").bind("click",function(){
                    if(false  != opts.onConfirm()){
                        private_closeWin(id,opts.mask,opts.onClose);
                    }
                });
                box.find(".cancel_btn").unbind("click").bind("click",function(){
                    if(false  != opts.onCancel()){
                        private_closeWin(id,opts.mask,opts.onClose);
                    }
                });
                box.appendTo("body");
            }
            if(null == opts.title || undefined == opts.title){
                opts.title = "请确认";
            }
            box.addClass("win_box").addClass("msg_box");
            box.find(".title").html(opts.title);
            box.find(".msg").html(opts.msg);
            box.show();
            box.center();
            return box;
        },
        //弹出含输入框的窗体
        prompt:function(options){
            var opts = jQuery.extend(true,{
                mask:false,
                msg:"",
                title:null,
                onConfirm:function(val){},
                onCancel:function(val){}
            },options);
            var id = "prompt";
            //创建遮罩
            if(opts.mask){
                $.mask();
            }
            //创建窗体
            var box = null;
            if($("#" + id).length > 0){
                box = $("#" + id);
            }else{
                box = $('<div id="' + id + '"><div class="shadow"><div class="panel_title"><span class="title"></span></div><div class="panel_cc"><span class="msg"></span>:<input class="prompt_input"/></div><div class="prompt_bottom">' +
                    '<a class="cancel_btn panel_btn" href="javascript:void(0);">取消</a>' +
                    '&nbsp;&nbsp;&nbsp;&nbsp;' +
                    '<a class="close_btn panel_btn" href="javascript:void(0);">确定</a>' +
                    '</div></div></div>');
                box.find(".close_btn").unbind("click").bind("click",function(){
                    if(false  != opts.onConfirm(box.find(".prompt_input").val())){
                        private_closeWin(id,opts.mask);
                    }
                });
                box.find(".cancel_btn").unbind("click").bind("click",function(){
                    if(false  != opts.onCancel(box.find(".prompt_input").val())){
                        private_closeWin(id,opts.mask);
                    }
                });
                box.appendTo("body");
            }
            if(null == opts.title || undefined == opts.title){
                opts.title = "请输入";
            }
            box.addClass("win_box").addClass("msg_box");
            box.find(".prompt_input").val("");
            box.find(".title").html(opts.title);
            box.find(".msg").html(opts.msg);
            box.show();
            box.center();
            return box;
        },
        toast:function(options){
            var opts = jQuery.extend(true,{
                mask:false,
                delay:1200,
                speed:2000,
                msg:""
            },options);
            var id = "toast";
            //创建遮罩
            if(opts.mask){
                $.mask();
            }
            //创建窗体
            var box = null;
            if($("#" + id).length > 0){
                box = $("#" + id);
            }else{
                box = $('<div id="' + id + '"><div class="shadow"><div class="panel_cc"><span class="msg"></span></div></div></div>');
                box.appendTo("body");
            }
            box.addClass("win_box").addClass("msg_box");
            box.find(".msg").html(opts.msg);
            box.center();
            box.show();
            box.delay(opts.delay).fadeOut(opts.speed,function(){
                if(opts.mask){
                    $("#mask").hide();
                }
            });
            return box;
        }
    });
})(jQuery);


/**
 * 功能性组件
 */
if(jQuery)(function($) {
    //编辑器
    $.fn.editor = function(options) {

    };
    //进度条
    $.fn.progress = function(options){

    };

    $.fn.centers = function(options) {
        return this.each(function() {
            var top = ($(window).height() - $(this).outerHeight()) / 2;
            var left = ($(window).width() - $(this).outerWidth()) / 2;
            $(this).css({position:'absolute', margin:0, top: (top > 0 ? top : 0)+'px', left: (left > 0 ? left : 0)+'px'});
        });
    };
})(jQuery);


/**
 * 窗体定位插件
 * @author Tanyongde
 */
jQuery.fn.center = function(loaded) {
    var obj = this;
    body_width = parseInt($(window).width());
    body_height = parseInt($(window).height());
    block_width = parseInt(obj.width());
    block_height = parseInt(obj.height());

    left_position = parseInt((body_width/2) - (block_width/2)  + $(window).scrollLeft());
    if (body_width<block_width) { left_position = 0 + $(window).scrollLeft(); };

    top_position = parseInt((body_height/2) - (block_height/2) + $(window).scrollTop());
    if (body_height<block_height) { top_position = 0 + $(window).scrollTop(); };

    obj.css({ 'top': top_position, 'left': left_position });
    if(!loaded) {
        $(window).bind('resize', function() {
            obj.center(!loaded);
        });
        $(window).bind('scroll', function() {
            obj.center(!loaded);
        });
    } else {
        //obj.stop();
    }
};

(function($){
	$.getUrlParam = function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r!=null) return unescape(r[2]); return null;
	}
})(jQuery);

//分析外部引入的文件路径
var private_parsefile = function(file){
    var result = new Array();
    var css = new Array();
    var js = new Array();
    if(null == file || undefined == file){
        return result;
    }
    var files = typeof file == "string" ? [file] : file;
    for (var i = 0; i < files.length; i++)
    {
        var obj = {};
        obj.name = files[i].replace(/^\s|\s$/g, "");
        obj.att = obj.name.split('.');
        obj.ext = obj.att[obj.att.length - 1].toLowerCase();
        obj.isCSS = obj.ext == "css";
        obj.tag = obj.isCSS ? "link" : "script";
        obj.attr = obj.isCSS ? " type='text/css' rel='stylesheet' " : " language='javascript' type='text/javascript' ";
        obj.link = (obj.isCSS ? "href" : "src") + "='" + $.includePath + obj.name + "'";
        obj.path = $.includePath + obj.name;
        if(obj.isCSS){
            css.push(obj);
        }else{
            js.push(obj);
        }
    }
    result.push(css);
    result.push(js);
    return result;
};


//重载样式和脚本
function private_reloadIncludes(obj,includes,_callback){
    var oldincludes = $(obj).attr("includes");
    if(!(null == oldincludes ||
        undefined == oldincludes ||
        null == includes ||
        undefined == includes ||
        0 == includes.length)){
        $.uninclude(eval("(" + oldincludes + ")"));
    }

    $(obj).attr("includes",JSON.stringify(includes));
    if(!(undefined == includes || null == includes || includes.length == 0)){
        $.include(includes,_callback);
    }else if(!(null == _callback || undefined == _callback)){
        _callback();
    }
};

//关闭窗体
function private_closeWin(id,mask,callback){
    $("#" + id).fadeOut("fast",function(){
        if(callback){callback();}
        $(this.remove());
    });
    if(mask){
        $("#mask").fadeOut("fast",function(){
            $(this.remove());
        });
    }
}


$.ajaxSetup ({cache: false});//关闭AJAX相应的缓存