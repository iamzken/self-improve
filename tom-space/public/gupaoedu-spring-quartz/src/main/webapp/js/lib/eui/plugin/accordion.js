window.UI.plugin = (function(){
	return {
		accordion:function(selector,options){//分页控件
			var opts=jQuery.extend(true,{
				url:"",
				height:"100%",
				onItemClick:function(e,obj){
					//alert(JSON.stringify(obj));
				}
			},options),self={};
			//alert(selector+":  "+document.title);
			self.ele = $(selector);
			self.ele.each(function(i,o){
				var _self=$(this);
				var render=function(){
					var html='<div class="page-sidebar sidebar-fixed" style="left:0px;top:0px;"><div class="page-sidebar"><ul class="nav sidebar-menu">'
					if(opts.data&&opts.data.length>0){
						for(var i=0;i<opts.data.length;i++){
							if(opts.data[i].subMenu==null){
								html+='<li style="cursor:pointer;" '+(opts.data[i].active?'class="active"':'')+' ><a data-indexi="'+i+'" href="'+opts.data[i].linkHref+'">';
								html+='<i class="'+opts.data[i].iconClass+'"></i>';
								html+='<span text="'+opts.data[i].menuText+'" class="menu-text">'+opts.data[i].menuText+'</span>'+(opts.data[i].subcontent==null?'':'<i class="menu-expand"></i>')+'';
								html+='</a>'+(opts.data[i].subcontent==null?'':'<div class="subcontent">'+opts.data[i].subcontent+'</div>')+'</li>';
							}else{
								html+='<li  style="cursor:pointer;" ><a data-indexi="'+i+'" class="menu-dropdown">';
								html+='<i class="'+opts.data[i].iconClass+'"></i>';
								html+='<span text="'+opts.data[i].menuText+'" class="menu-text">'+opts.data[i].menuText+'</span>';
								html+='<i class="menu-expand"></i></a>';
								html+=' <ul class="submenu">';
								for(var j=0;j<opts.data[i].subMenu.length;j++){
									html+='<li><a  data-indexi="'+i+'" data-indexj="'+j+'" href="'+opts.data[i].subMenu[j].linkHref+'">';
									html+='<span text="'+opts.data[i].menuText+'" class="menu-text">'+opts.data[i].subMenu[j].menuText+'</span>';
									html+='</a>'+(opts.data[i].subMenu[j].subcontent==null?'':'<div class="subcontent">'+opts.data[i].subMenu[j].subcontent+'</div>')+'</li>'
								}
								html+='</ul>'
							}
						}
					}	
					html+="</ul></div></div>";
					$(_self).html();
					$(_self).html(html);
					
				}
				$(_self).bind("onLoadSuccess",opts.onLoadSuccess);
				$(_self).bind("onLoadError",opts.onLoadError);
				$(_self).bind("onItemClick",opts.onItemClick);
				_self.css({position:"relative",height:opts.height,width:opts.width});
				if(opts.url!=null&&opts.url!=""){
					CORE.ajax({url:opts.url,dataType:"text",success:function(e){
						opts.data=eval('('+e+')');
						render();
						$(_self).trigger("onLoadSuccess",[e]);
		        	},error:function(e){
		        		$(_self).trigger("onLoadError",[e]);
		        	}
					})
				}else{
					render();
				}
					var menu=_self;
					$(_self).on("click","a",function(e){
						$("a",_self).parent().removeClass("active");
						$(this).parent().addClass("active");
						var indexI=$(this).attr("data-indexi");
						var indexJ=$(this).attr("data-indexj");
						var obj={};
						if(!isNaN(indexJ)){
							obj=opts.data[parseInt(indexI)].subMenu[parseInt(indexJ)];
						}else{
							obj=opts.data[parseInt(indexI)];
						}
						$(_self).trigger("onItemClick",[obj]);
					});
					$(".sidebar-menu",menu).on("click",function(e){
				        var i=$(e.target).closest("a");
				        var li=i.parent();
				        if(li.find(".submenu").length>0||li.find(".subcontent").length>0){
				        	li.toggleClass("open");
				        }
				       /* u,
				        r,
				        f;if(i&&i.length!=0){
				            if(!i.hasClass("menu-dropdown"))return menu&&i.get(0).parentNode.parentNode==this&&(u=i.find(".menu-text").get(0),
				            e.target!=u&&!$.contains(u,
				            e.target))?!1:void 0;if(r=i.next().get(0),
				            !$(r).is(":visible")){
				                if(f=$(r.parentNode).closest("ul"),
				                menu&f.hasClass("sidebar-menu"))return;f.find("> .open > .submenu").each(function(){
				                    this==r||$(this.parentNode).hasClass("active")||$(this).slideUp(200).parent().removeClass("open")
				                })
				            }
				            return menu&&$(r.parentNode.parentNode).hasClass("sidebar-menu")?($(r).slideToggle(200).parent().toggleClass("open"),
				            !1):!1;
				        }*/
				    });
			});
			return self;
		}
	}
})();