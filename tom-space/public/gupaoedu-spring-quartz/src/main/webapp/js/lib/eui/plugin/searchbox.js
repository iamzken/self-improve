window.UI.plugin = (function(){
	return {
		searchbox:function(selector,options){
			var opts=jQuery.extend(true,{
				placeholder:"请输入检索内容！",
				name:"search",
				tWidth:320,
				onSearch:function(e,value){alert(value);}
			},options),self={};
			self.ele=$(selector);
			self.ele.each(function(){
				var _self=$(this);
				var render=function(){
					html='<div class="input-group col-md-4" >';
	                html+='<input style="transition:width 0.3s; width:'+opts.tWidth+'px; "  class="form-control" name="'+opts.name+'" placeholder="'+opts.placeholder+'" size="16" type="text"><span style="cursor:pointer;*float:left;width:40px; height:34px;" class="input-group-addon siconbox	">'
                    html+='<span class="glyphicon glyphicon-search"></span></span></div>';
					_self.append(html);
				}
				render();
				$(_self).bind("onSearch",opts.onSearch);
				$(_self).on("click",".input-group-addon.siconbox",function(){
					var value=$(this).parent().find("input[type=text]").val();
					$(_self).trigger("onSearch",[value]);
				});
			});
			return self;
		}
	}
})();