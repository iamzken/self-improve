window.UI.plugin = (function(){
	return {
		treegrid:function(selector,options){
			var callback=options.onAfterRender;
			options.onAfterRender=function(){
				if(callback){
					callback();
				}
				$(selector+" table.table-datagrid").treegrid(opts.treeOption);
			}
			var opts=jQuery.extend(true,{
				url:"",
				treeOption:{}
				
			},options),self={};
			var dg={};
			self.ele = $(selector);
			self.ele.each(function(i,o){
				_self=$(this);
				dg=UI.datagrid(selector,options);
			});
			return dg;
		}
	};
})();