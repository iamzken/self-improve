window.UI.plugin = (function(){
	return {
		layout:function(selector,options){//下拉列表框
			var opts=jQuery.extend(true,{
				onHeaderEnd:function(){
				}
			},options),self={};
			self.ele=$(selector);
			self.ele.each(function(){
				var _self=$(this);
				function setTitle(ly){
					var lyHtml='';
					if(ly.attr("title")!=""){
						lyHtml=ly.html();
						ly.html('<div class="panel-heading">'+ly.attr("title")+'</div><div class="panel-body">'+lyHtml+'</div>');
					}
				}
				$(_self).bind("onHeaderEnd",opts.onHeaderEnd);
				function calCenterWidth(){
					var boxWidth=_self.find(".middle").width();
					var eastWidth=_self.find(".east").width();
					var westWidth=_self.find(".west").width();
					var cenWidth=boxWidth-eastWidth-westWidth-28;
					_self.find(".center").width(cenWidth).css({marginLeft:"10px",marginRight:"10px"});
				}
				setTitle(_self.find(".north"));
				setTitle(_self.find(".west"));
				setTitle(_self.find(".center"));
				setTitle(_self.find(".east"));         
				setTitle(_self.find(".south"));
				if(_self.find(".west").attr("data-tel")=="true"){
					_self.find(".west").append('<div class="shensuo" data-open="true" ><span class="glyphicon glyphicon-resize-horizontal"></span></div>');
					_self.find(".west").on("click",".shensuo",function(){
						if($(this).attr("data-open")=="true"){
							_self.find(".west").width("35px");
							calCenterWidth();
							_self.find(".west .panel-heading").hide();
							_self.find(".west .panel-body").hide();
							$(this).attr("data-open","false");
						}else{
							_self.find(".west").width("15%");
							calCenterWidth();
							_self.find(".west .panel-heading").show();
							_self.find(".west .panel-body").show();
							$(this).attr("data-open","true");
						}
					});
				}
				if(_self.find(".east").attr("data-tel")=="true"){
					_self.find(".east").append('<div class="shensuo"  data-open="true" ><span class="glyphicon glyphicon-resize-horizontal"></span></div>');
					_self.find(".east").on("click",".shensuo",function(){
						if($(this).attr("data-open")=="true"){
							_self.find(".east").width("35px");
							calCenterWidth();
							_self.find(".east .panel-heading").hide();
							_self.find(".east .panel-body").hide();
							$(this).attr("data-open","false");
						}else{
							_self.find(".east").width("15%");
							calCenterWidth();
							_self.find(".east .panel-heading").show();
							_self.find(".east .panel-body").show();
							$(this).attr("data-open","true");
						}
					});
				}
				$(_self).trigger("onHeaderEnd");
			});
		}
	}
})();