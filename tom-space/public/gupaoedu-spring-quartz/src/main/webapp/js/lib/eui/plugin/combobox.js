window.UI.plugin = (function(){
	return {
		combobox:function(selector,options){//下拉列表框
			var opts=jQuery.extend(false,{
				url:"",
				valueField:"id",
				textField:'text',
				data:[{
				    "id":1,
				    "text":"text1"
				},{
				    "id":2,
				    "text":"text2"
				},{
				    "id":3,
				    "text":"text3",
				    "selected":true
				},{
				    "id":4,
				    "text":"text4"
				},{
				    "id":5,
				    "text":"text5"
				}]
			},options),self={};
			self.ele=$(selector);
			self.ele.each(function(){
				var _self=$(this);
				$(_self).bind("onInit",opts.onInit);
				$(_self).bind("onLoadSuccess",opts.onLoadSuccess);
				$(_self).bind("onLoadError",opts.onLoadError);
				$(_self).bind("onRenderEnd",opts.onRenderEnd);
				$(_self).bind("onSelected",opts.onSelected);
				if(opts.url!=null&&opts.url!=""){
					CORE.ajax({url:opts.url,dataType:"text",success:function(e){
						opts.data=eval('('+e+')');
						render(opts.data);
						$(_self).trigger("onLoadSuccess",[e]);
		        	},error:function(e){
		        		$(_self).trigger("onLoadError",[e]);
		        	}
					})
				}else{
					render(opts.data);
				}
				$(_self).trigger("onInit");
				function render(data){
					var selectedText=data[0][opts.textField],
					selectedValue=data[0][opts.valueField];
					html=' <div role="presentation" class="dropdown">';
					html+='<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="false"><span class="dropdownValue"  data-value="{{value}}">';
					html+="{{text}}";
			        html+='</span><span class="caret"></span>';
			        html+='</button><ul class="dropdown-menu" role="menu"  aria-labelledby="dropdownMenu1" style="max-height:250px;overflow:auto;">';
			        for(var i=0;i<data.length;i++){
			        	if(data[i]["selected"]==true){
			        		selectedText=data[i][opts.textField];
			        		selectedValue=data[i][opts.valueField];
			        	}
			        	html+='<li role="presentation" ><a class="menuitem" role="menuitem"  href="javascript:void(0)" data-value="'+data[i][opts.valueField]+'">'+data[i][opts.textField]+'</a></li>';
			        }
			        html+='</ul>';
					html+='</div>';
					html=html.replace("{{value}}",selectedValue);
					html=html.replace("{{text}}",selectedText);
			        _self.html("");
			        _self.append(html);
			        $(_self).trigger("onRenderEnd");
				}
				$(_self).on("click",".menuitem",function(){
					var ddlVal=$(".dropdownValue",_self);
					ddlVal.attr("data-value",$(this).attr("data-value"));
					ddlVal.text($(this).text());
					$(_self).trigger("onSelected",[$(this).data("value"),$(this).text()]);
				});
				self.setValue=function(value){
					for(var i=0;i<opts.data.length;i++){
						if(opts.data[i][opts.valueField]==value){
							var ddlVal=$(".dropdownValue",_self);
							ddlVal.attr("data-value",opts.data[i][opts.valueField]);
							ddlVal.text(opts.data[i][opts.textField]);
							break;
						}
					};
				}
			});
			
			return self;
		}
	};
})();
