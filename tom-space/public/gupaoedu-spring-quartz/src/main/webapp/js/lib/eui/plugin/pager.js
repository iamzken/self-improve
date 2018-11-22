window.UI.plugin = (function(){
	return {
		pager:function(selector,options){//分页控件
			var opts=jQuery.extend(true,{
				total:11,
				pageSize:5,
				pageNo:1,
				inline:false,
				onPaging:function(index){}
			},options),self={};
			//alert(selector+":  "+document.title);
			self.ele = $(selector);
			self.ele.each(function(i,o){
				//调到指定页
				var _self=this;
				function render(){
					var pageCount=parseInt(opts.total%opts.pageSize==0?opts.total/opts.pageSize:opts.total/opts.pageSize+1);
					if(opts.pageNo>pageCount||opts.pageNo<1){opts.pageNo=pageCount;}
					var isOne=false;
					var isPrevDis=opts.pageNo==1;
					var isNextDis=opts.pageNo==pageCount;
					var prevhtml='<a href="javascript:void(0)">上一页</a>';
					var nexthtml='<a href="javascript:void(0);">下一页</a>';
					var inlineHtml=' class="col-sm-6" ';
					if(opts.inline==false){
						inlineHtml=' class="col-sm-12" ';
					}
					if(opts.total<=opts.pageSize){
						isOne=true;
					}
					
					var html='<div class="row" data-fromPageNo="'+opts.pageNo+'">';
					//html+='<div '+inlineHtml+' ><span>当前'+(opts.pageNo==0?0:opts.pageNo*opts.pageSize-opts.pageSize+1)+'到'+(opts.pageNo*opts.pageSize>=opts.total?opts.total:(opts.pageNo*opts.pageSize))+'条记录，共'+opts.total+'条</span> <span>共'+pageCount+'页</span></div>';
					html+='<div '+inlineHtml+' ><ul style="float:right;" class="pagination">'+
						  '<li  class="'+(isOne||isPrevDis?'disabled':'')+'"  data-index="1"><a href="javascript:void(0)">首页</a></li>'+
		                  '<li class="prev'+(isOne||isPrevDis?' disabled':'')+'" data-index="'+(opts.pageNo-1<=1?1:opts.pageNo-1)+'" >'+prevhtml+'</li>';
		                  var len=opts.pageNo+3>=pageCount?pageCount:opts.pageNo+3;
		                  if(opts.pageNo-3>1){
		                	  html+='<li ><a href="javascript:void(0)">...</a></li>';
		                  }
	                	  for(var i=opts.pageNo-3>0?opts.pageNo-3:1;i<=len;i++){
	                		  html+='<li '+(i==opts.pageNo?'class="active" ':'')+' data-index="'+i+'"><a href="javascript:void(0)">'+i+'</a></li>';
	                	  }
	                	  if(len<pageCount){
	                		  html+='<li ><a href="javascript:void(0)">...</a></li>';
	                	  }
		            html+='<li class="next'+(isOne||isNextDis?' disabled':'')+'"  data-index="'+(opts.pageNo+1>=pageCount?pageCount:opts.pageNo+1)+'" >'+nexthtml+'</li>'+
		                  '<li  class="'+(isOne||isNextDis?'disabled':'')+'"  data-index="'+pageCount+'"><a href="javascript:void(0);">末页</a></li>'+
			              '</ul></div>'
                    html+="</div>";
		            $(_self).html("");
		            $(_self).html(html);
				}
				render();
				$(_self).bind("onPaging",opts.onPaging);
				$(_self).on("click",".pagination li",function(evt){
					if($(this).hasClass("disabled")){
						return;
					}
					var index=$(this).data("index");
					if(!isNaN(index)){
						index=parseInt(index);
						$(_self).trigger("onPaging",[index]);
					}
				});
				self.gotoPage=function(total,pageNo){
					opts.total=total;
					if(pageNo){						
						opts.pageNo=pageNo;
					}
					render();
				}
			});
			//alert("----" + self);
			return self;
		}
	};
})();