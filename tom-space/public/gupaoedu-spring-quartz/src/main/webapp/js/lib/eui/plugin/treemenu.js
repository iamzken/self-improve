window.UI.plugin = (function(){
	return {
		treemenu:function(selector,options){
			var opts=jQuery.extend(true,{
				url:"",
				zNodes:{},
				setting:{}
			},options),self={};
			self.ele = $(selector);
			self.ele.each(function(i,o){
				_self=$(this);
				$(_self).bind("onInit",opts.onInit);
				$(_self).bind("onLoadSuccess",opts.onLoadSuccess);
				$(_self).bind("onLoadError",opts.onLoadError);
				if(opts.url!=null&&opts.url!=""){
					CORE.ajax({url:opts.url,dataType:"text",success:function(e){
						debugger;
							opts.zNodes=eval('('+e+')');
						    self = $.fn.zTree.init(_self, opts.setting, opts.zNodes);
							$(_self).trigger("onLoadSuccess",[e]);
			        	},error:function(e){
			        		$(_self).trigger("onLoadError",[e]);
			        	}
					})
				}else{
					self = $.fn.zTree.init(_self, opts.setting, opts.zNodes);
				}
			});
			return self;
		}
	};
})();