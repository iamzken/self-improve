/*!
 * ftp pro 1.0
 *
 * @2015.10.20
 */
var enc = $.query.get('enc'), c = $.query.get('c'), id =$.query.get('id');
var loginHost = "/index.html";

/**
 * 检查ajax的返回结果
 */
var checkAjaxResult = function (data){
	if(data.status == 99){
		if(!window.alerted){
			alert("您尚未登录或者登录已超时！");
			window.alerted = true;
		}
		window.top.location.href = loginHost;
		return false;
	}else if(data.status == 98){
		alert(data.msg);
		return false;
	}
	return true;
}

/**
 * 发送ajax请求
 */
var ajax = function(options){
	var opts = $.extend(true,{}, options);
	var d = $.extend(true,{id:id, enc: enc, c: c},opts.data);
	opts.data = d;
	opts.success = function(data){
		if(checkAjaxResult(data)){
			if(options.success){
				options.success(data);
			}
		}
	};
	$.ajax(opts);
};

!function ($) {
    var options = {
        filesBody: $(".fileContiner"),
        loadFilesUrl: "/",
        loading: $("#loading"),
        path: [],
        selectFiles : []
    };

    function createFileBody(data, self){
        var menuStyle = (data.type == "dir" ? "menufolder" : "menufile"),
            filetype = (data.type == "dir" ? "folder" : data.ext),
            downurl = (data.type == "dir" ? "" : 'data-url="' + data.url +'"'),
            fbody = $([
                '<div class="file folderBox ' + menuStyle + '">',
                '<div ' + downurl + ' data-filetype="' + filetype + '" data-name="' + data.name + '" class="' + filetype + ' ico"></div>',
                '<div class="titleBox">',
                '<span class="title">' + data.name + '</span>',
                '</div></div>'].join("")).attr("title", ["名称:", data.name, "\r\n大小:", data.size, "\r\n修改时间:", data.last].join(""));
        var ico = $(".ico", fbody).dblclick(function () {
            var data = $(this).data();
            if(data.filetype == "folder") {
                self.changeDir(data.name);
            }  else {
                self.download(data.url);
            }
        });
        if ({"jpg": 0, "png": 0, "gif": 0}[filetype.toLowerCase()] == 0) {
        	ico.addClass("picture");
            ico.css({"background":"url(\"" + "/web/preview/" + "" + data.url + ".file" + "\") no-repeat scroll center center","backgroundSize":"100%","backgroundRepeat":"no-repeat","margin":"2px auto"});
        }
        var title = $(".title", fbody).dblclick(function () {
            if ($("textarea", this).length > 0) return;
            var titlebox = $(this), oldname = titlebox.html(), changebox = $("<input size=8 style='color: #000' />").val(oldname);
            var renamethis = function () {
                self.rename(oldname, changebox.val(), function (name) {
                    try { $("div[data-name=" + oldname + "]").attr("data-name", name); } catch (err) { }
                    titlebox.html(name);
                });
            };
            titlebox.html(changebox);
            changebox.keydown(function (e) {
                if (e.keyCode == 13) $(this).blur();
            }).blur(renamethis).focus().select();
        });
        fbody.mousedown(function(){
            $(".select", fbody.parent()).removeClass("select");
            $(this).addClass("select");
            options.selectFiles = [ico.data()];

            $("*[fd=rname]").removeClass("disabled").unbind("click").bind("click", function(){
                title.dblclick();
            });
            $("*[fd=remove]").removeClass("disabled").unbind("click").bind("click", function(){
                self.remove(title.html())
            });
            if(ico.data().filetype == "folder"){
                $("*[fd=download]").addClass("disabled");
            } else {
                $("*[fd=download]").removeClass("disabled").unbind("click").bind("click", function(){
                    self.download(ico.data().url);
                });
            }
        });
        return fbody;
    };

    var navbar = (function(){
    	var init = function(){
			addressSet(),
            bindAddressEvent();
		},
    	addressSet = function() {
            var e = options.path.join("/").replace("\/\/","/");
            if(options.path[0] == "public:"){
            	e = (e.replace(/public:/,"/公共目录/"));
            }else if(options.path[0] == "recycle:"){
            	e = (e.replace(/recycle:/,"/回收站/"));
            }else if(options.path[0] == "my:"){
            	e = (e.replace(/my:/,"/我的文件/"));
            }
            //alert("--====" + e +　"===---");
            $("input.path").val((e + "/").replace("\/\/","/")),
            $("#yarnball_input").css("display", "none"),
            $("#yarnball").css("display", "block");
            var t = function(e) {
                var t = {"path_type":"","id":""},
                a = t.path_type,
                i = '<li class="yarnlet first"><a title="@1@" style="z-index:{$2};"><span class="left-yarn"></span>{$3}</a></li>\n',
                n = '<li class="yarnlet "><a title="@1@" style="z-index:{$2};">{$3}</a></li>\n';
                e = e.replace(/\/+/g, "/");
                var s = e.split("/");
                "" == s[s.length - 1] && s.pop();
                var o = s[0] + "/",
                r = i.replace(/@1@/g, o),
                l = s[0],
                c = "";
                r = r.replace("{$2}", s.length),
                r = r.replace("{$3}", c + '<span class="title_name">' + l + "</span>");
                for (var d = r,
                p = 1,
                u = s.length - 1; s.length > p; p++, u--) o += s[p] + "/",
                r = n.replace(/@1@/g, o),
                r = r.replace("{$2}", u),
                r = r.replace("{$3}", '<span class="title_name">' + s[p] + "</span>"),
                d += r;
                return '<ul class="yarnball">' + d + "</ul>"
            };
            $("#yarnball").html(t(e)),
            setAddressWidth()
        },
        setAddressWidth = function() {
            $(".yarnball").stop(!0, !0);
            var e = $("#yarnball").innerWidth(),
            t = 0;
            $("#yarnball li a").each(function() {
                t += $(this).outerWidth() + parseInt($(this).css("margin-left")) + 5
            });
            var a = e - t;
            0 >= a ? $(".yarnball").css("width", t + "px").css("left", a + "px") : $(".yarnball").css({
                left: "3px",
                width: e + "px"
            })
        },
        bindAddressEvent = function(){
			$("#yarnball li a").die("click").live("click",
            function(e) {
                var t = $(this).attr("title");
                $("input.path").val(t),
                gotoPath(),
                stopPP(e)
            }),
            $("#yarnball").die("click").live("click",
            function() {
                return $("#yarnball").css("display", "none"),
                $("#yarnball_input").css("display", "block"),
                $("#yarnball_input input").focus(),
                !0
            }),
            $("#yarnball_input input").die("blur").live("blur",
            function() {
            	gotoPath()
            }),
            $("#yarnball_input input").keyEnter(function() {
            	gotoPath()
            })
		},
		pathClear = function(e) {
            return e = e.replace(/\\/g, "/"),
            e = e.replace(/\/+/g, "/")
        },
		gotoPath = function() {
            var e = pathClear($("input.path").val()) + "/";
            $("input.path").val(e),
            explorer.loadFiles(),
            addressSet();
        },
        currPath = function(){
        	var path = (options.path.join("/") || "").replace(/\/{2,}/img, "/");
        	path = path.replace(/(my:)|(public:)|(recycle:)/,"/");
            return path;
        };
        return {
        	init : init,
        	currPath : currPath,
        	addressSet : addressSet
        }
    })();
    
    var slider = (function(){
    	this.navbar = navbar;
    	this.initTree = function(pbox){
    		var myRoot = "my",
    			publicRoot = "public",
    			recycleRoot = "recycle",
    		pid = function(o){
        		if(o.fgroup == "USERS" && o.pid == 0){
        			return myRoot;
        		}else if(o.fgroup == "PUBLIC" && o.pid == 0){
        			return publicRoot;
        		}
        		return o.pid;
        	},
        	setValue = function(e,id,node){
        		var root = (node.root == "public:" ? "public/" : "/");
        		var treeObj = $.fn.zTree.getZTreeObj(id);
        		if(node.children && node.children.length > 0){return;}
    			ajax({url:root + "list.json?t=" + new Date().getTime(),type:"post",data:{"path":node.level >= 1 ? node.xpath + node.name : node.xpath,"name":node.name},success:function(d){
    				var data = d.data,chilren = new Array();
    				
    				if(data.length == 0){ return; }
    	        	for(var i = 0 ;i < data.length; i ++) {
    	        		if(data[i].type != "dir"){continue;} //只显示文件夹
    	        		var d = {
    	        			id : data[i].id,
    	        			pid : pid(data[i]),
    	        			name : data[i].name,
    	        			xpath : data[i].xpath,
    	        			open : true
    	        		};
    	        		chilren.push(d);
    	        	}
    	        	treeObj.removeChildNodes(node);
    	        	treeObj.addNodes(node, chilren);
    			}});
    		},
    		success = function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
    			$.fn.zTree.getZTreeObj(treeId).expandAll(true);
    		},
    		setting = {
    				data: {
    					simpleData: {
    						enable: true,
    						pIdKey:"pid",
    						rootPid:0
    					}
    				},
    				callback: {//事件处理回调函数
    					onClick: setValue,
    					onAsyncSuccess:success
    				},
    				view: {
    					dblClickExpand: false,
    					showLine: !1,
    					selectedMulti: !1,
    					dblClickExpand: !1,
    					addDiyDom: function(e, t) {
    						var a = 10,
    							i = $("#" + t.tId + "_switch",pbox),
    							n = $("#" + t.tId + "_ico",pbox);
    						if (i.remove(), n.before(i),t.level >= 1) {
    							var s = "<span class='space' style='display: inline-block;width:" + a * t.level + "px'></span>";
    							i.before(s);
    						}
    					}
    				}
    			};
    		var rdata = [{id:myRoot,pid:myRoot,root:"my:",name:"我的文件",xpath:"/",iconSkin:"my",open:true},
    		             {id:publicRoot,pid:publicRoot,root:"public:",name:"公共资源",xpath:"/",iconSkin:"lib",open:true}]
        	
    		$.fn.zTree.init(pbox, setting,rdata);
    	};
    	return {
    		initTree : initTree
    	}
    })();
    
    // 文件管理
    var explorer = (function(id, enc, c) {
    	var self = this;
    	this.navbar = navbar;
    	this.slider = slider;
        this.logout = function(){
			ajax({url: "/system/logout.json",success:function(data){
				if(data.data.loginHost){
					window.location.href = loginHost;
				}
			}});
		};
        this.changeDir = function (path, ishash) {
            if (path == '回到上一级') {
                if (options.path.length > 1) options.path.pop();
            } else if (path[0] == "/") {
                if(options.path.join("/") != path) {
                    options.path = ["my:"];
                    $(path.split("/")).each(function () {
                        if (this != "") options.path.push(this);
                    });
                } else return;
            } else if (path != "") options.path.push(path);
            //$("#yarnball").append('<li class="yarnlet"><a>' + path + '</a></li>')
            //if(!ishash) $.history.load(options.path.join("/").replace(/\/{2,}/img, "/"));
            self.navbar.init();
            self.loadFiles();
        },
        this.rename = function(oldname, newname, recall){
            if(newname == "" || newname == oldname) return recall(oldname);
            ajax({
                type: "post",
                url:  "/web/rename.json?t=" + new Date().getTime(),
                data: {path:(options.path.join("/") || "").replace(/\/{2,}/img, "/"),oldname:oldname,newname:newname},
                dataType: "json",
                success: function (result) {
                	
                    if(result.status == 1){
                        return recall(newname);
                    } else {
                        alert({"-1": "原文件不存在", "-2": "文件已存在", "-3": "修改失败."}[result.code])
                    }
                },
                error:function () {
                    return recall(oldname);
                }
            });
        },
        this.remove = function(name){
            if (name == "") return;
            var form = $('#delConfirm').modal('show'), button = $("#okbutton").one("click", function () {
            	ajax({
                    type: "post",
                    url:  "/web/remove.json?t=" + new Date().getTime(),
                    data: {path:(options.path.join("/") || "").replace(/\/{2,}/img, "/"),oldname:oldname,newname:newname},
                    dataType: "json",
                    success: function (result) {
                        if (result.ststus == 1) {
                            self.loadFiles();
                        } else alert("删除失败.");
                        form.modal('hide');
                    },
                    error: function () {
                        return alert("删除失败.");
                    }
                });
                return;
            });
        },
        this.download = function(url){
            if(url == "") return;
            $("#downForm").attr("action", "/web/download/" + url + ".file").submit();
        },
        this.createFolder = function(name){
            if(name == "" ) return;
            ajax({
                type: "post",
                url:  "/web/createFolder.json?t=" + new Date().getTime(),
                data: {path:(options.path.join("/") || "").replace(/\/{2,}/img, "/"),name:name},
                dataType: "json",
                success: function (result) {
                    if(result.status == 1){
                        self.loadFiles();
                    } else {
                        alert(result.msg)
                    }
                },
                error:function () {}
            });
        },
        this.uploadCallback = function(){
        	
             self.loadFiles();
                       
        },
        this.loadFiles = function () {
        	
        	 ajax({
                 type: "post",
                 url:  "/web/list.json?t=" + new Date().getTime(),
                 data: {path:navbar.currPath(),name:name},
                 dataType: "json",
                 success: function (f) {
                     options.loading.hide();
                     options.filesBody.empty();
                     if(self.navbar.currPath() != "/"){
                         options.filesBody.append(createFileBody({type:"dir", name:"回到上一级"}, self));
                     }
                     var files = f.data;
                     if(files.length > 0){
                         $(files).each(function () {
                             if (this.type == "dir") options.filesBody.append(createFileBody(this, self));
                         }).each(function () {
                             if (this.type != "dir") options.filesBody.append(createFileBody(this, self));
                         });
                     } else {
                         options.filesBody.append('<div class="empty"><i class="fa fa-cloud"></i>该文件夹为空<p style="font-size:24px;margin-top:30px;text-align: center;">咕泡学院,只为更好的你</p></div>');
                     }
                     options.filesBody.append('<div style="clear:both"/>');
                 },
                 error:function (res) {
                     options.loading.hide();
                     options.filesBody.append('<div style="text-align:center;color:#aaa;">加载失败，请稍候再试.</div>');
                 }
             });
        	
            options.loading.show();
        },
        this.currPrarms = function(){
            return ["enc=" + enc, "c=" + c, "id=" + id].join("&");
        };
        return {
        	logout : logout,
        	loadFiles : loadFiles,
        	changeDir : changeDir,
        	createFolder : createFolder,
        	uploadCallback : uploadCallback
        }
    })(id, enc, c);
    
   	window.explorer = explorer;
    window.slider = slider;
   	
   	$.ajaxSetup ({cache: false});//关闭AJAX相应的缓存
}(jQuery);

$(document).ready(function(){
	$.history.init(function (hash) { 
		if(window.hashInit){return;}
		window.hashInit = true;
		explorer.changeDir(hash || "/", true);
		slider.initTree($("#folderList"));
	}, {});

	var wbt = $("*[fd=upload], *[fd=create], *[fd=rname]").hide(), rbt = $("*[fd=remove]").hide();
	if (c.indexOf("w") > -1) {
	    wbt.show();
	} else if (c.indexOf("d") > -1) {
	    rbt.show();
	} else if (c.indexOf("r") > -1) {
	    wbt.hide();
	}

	$("#showUploadBtn").click(function(){
		
		$("#uploadModal iframe").attr("src",
			"/upload.html?"
			+ "path=" + navbar.currPath()
            + "&id=" + id
            + "&enc=" + enc
            + "&c=" + c
            + "&callback=explorer.uploadCallback");
		
		$("#uploadModal").modal();
	});
	$("#showCreateFolderBtn").click(function(){
		$("#createFolderModal").modal();
	});

	$("#logoutBtn").click(function(){
		if(confirm("您确定要退出吗?")){
			explorer.logout();
		}
	});

	//reset upload form
	$('#uploadModal').on('hidden.bs.modal', function (e) {
	    $(".progress-bar", this).width(0);
	    $(".badge", this).empty()
	});
});