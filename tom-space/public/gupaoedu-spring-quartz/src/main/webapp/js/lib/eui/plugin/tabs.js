window.UI.plugin = (function(){
	return {
		tabs:function(selector,options){//分页控件
			var opts=jQuery.extend(true,{
				url:"",
				data:[{
					title:"Home",
					id:"home",
					active:true
				},{
					title:"Help",
					id:"help"
				}],
				onTabChange:function(e,id){
					console.log(e.target);
				}
			},options),self={};
			self.ele = $(selector);
			self.ele.each(function(i,o){
				var _self=$(this);
				$(_self).bind("onLoadSuccess",opts.onLoadSuccess);
				$(_self).bind("onLoadError",opts.onLoadError);
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
				function render(){
					var html='<ul class="nav nav-tabs">';
					for(var i=0;i<opts.data.length;i++){
						html+='<li '+(opts.data[i].active?' class="active" ':'')+' data-id="'+opts.data[i].id+'" ><a href="javascript:void(0);">'+opts.data[i].title+'</a></li>';
					}
					html+='</ul>';
					$(_self).html("");
					$(_self).html(html);
				}
				$("ul.nav.nav-tabs>li",_self).bind("onTabChange",opts.onTabChange);
				$(_self).on("click","ul.nav.nav-tabs>li",function(e){
					$("ul.nav.nav-tabs>li",_self).removeClass("active");
					$(this).addClass("active");
					var id=$(this).data("id");
					$(this).trigger("onTabChange",[id]);
				});
			});
			return self;
		}
	};
})();