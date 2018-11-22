window.UI.plugin = (function(){
	return {
		setInput:function(selector,options){//下拉列表框
			var opts=jQuery.extend(false,{
				model:{name:"abc",age:23}
			},options),self={};
			self.ele=$(selector);
			self.ele.each(function(){
				for(var key in opts.model){
					if($(selector+" [id="+key+"]").find(".dropdownValue").length>0){
						$(selector+" [id="+key+"]").attr("value",opts.model[key]);
					}else{						
						$(selector+" [id="+key+"]").val(opts.model[key]);
					}
				}
			});
			return self;
		}
	};
})();
