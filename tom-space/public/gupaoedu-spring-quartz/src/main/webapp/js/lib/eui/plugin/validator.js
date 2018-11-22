window.UI.plugin = (function(){
	return {
		validator:function(selector,options){//验证控件
			var opts=jQuery.extend(true,{
				event:"blur", //blur change
				submitType:"ajax",
				ajaxCallback:function(){
				}
			},options),self={};
			//alert(selector+":  "+document.title);
			self.ele = $(selector);
			self.ele.each(function(i,o){
				var _self=$(this);
				var _RULE = {
						requied :function(input,rule){
							return !(input.val() == "" || input.val().length == 0);
						},
						regex : function(input,rule){
							return new RegExp(rule).test(input.val());
						},
						equalto :function(input,rule){
							return $(rule).val() == input.val();
						},
						email:function(input,rule){
							rule=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
							return rule.test(input.val());
						},
						telphone:function(input,rule){
							rule=/^1[34578]\d{9}$/;
							return rule.test(input.val());
						},
						gt:function(input,rule){
							var r=parseInt(rule);
							var v=parseInt(input.val());
							return v>r;
						},
						lt:function(input,rule){
							var r=parseInt(rule);
							var v=parseInt(input.val());
							return v<r;
						}
					};
				var _target = selector+" input,textarea";
				//alert(JSON.stringify(opt));

				var _vali_func = function(target){
					var _self = $(target);
					var _vali = true;
					_self.removeClass("is-success is-error");
					_self.next("p").remove();
					$.each(_RULE,function(rule,fun){
						//console.log($(_self).data("v-" + rule));
						var v = _self.data("v-" + rule);
						if(v){
							_vali = fun.call(_RULE,_self,v);
							if(!_vali){
								_self.after("<p class='msg-error'>" + _self.data("v-" + rule + "-msg") + "</p>");
							}
							return _vali;
						}

					});
					if(_vali){
						_self.addClass("is-success");
					}else{
						_self.addClass("is-error");
					}
				};
				$(_target).on(opts.event,function(){
					_vali_func(this);
				});				
				$(_target).parents("form").find("button[type=submit]").on("click",function(){
					$(_target).each(function(){
						_vali_func(this);
					});
					var vali_result=$(_target).parents("form").find(".is-error").length==0;
					if(opts.submitType=="ajax"){
						if(vali_result){
							opts.ajaxCallback();
						}
						return false;
					}else{
						return vali_result;
					}
				});
			});
			return self;
		}
	}
})();