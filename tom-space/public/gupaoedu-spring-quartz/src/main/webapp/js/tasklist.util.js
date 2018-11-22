var pageNo=1,pageSize=10;
var dg;
var pagination;
var error_prev_array = new Array([0]);
var info_prev_array = new Array([0]);
var bomWidth;
var bomHeight;

$(document).ready(function(){		
	bomWidth = $(document).width();
	bomHeight = $(document).height();	
	$("#logContent").css("width",(bomWidth*3)/4).css("height",((bomHeight*3)/4)+65);
	$("#logContent").css("height",((bomHeight*3)/4)+65);
	$("#logError").css("height",((bomHeight*3)/4)-105);
	$("#logInfo").css("height",((bomHeight*3)/4)-105);
	$("#logDialog").css("margin-left",bomWidth/8);
	$("#addTaskContent").css("width",(bomWidth*3)/4).css("height",((bomHeight*3)/4)+65);
	$("#addTaskContent").css("height",((bomHeight*3)/4)+65);
	$("#addTaskDialog").css("margin-left",(bomWidth)/8);	
	$("#includeFrame").css("width",(bomWidth*3)/4).css("height",((bomHeight*3)/4));
	$.ajax({ 	
		url: "/web/task/getPage.json",
		dataType:"text",
		type:"POST",
		data:{pageNo:pageNo,pageSize:pageSize},
		success: function(e){
		if(eval("("+e+")").status==0){
			UI.alert({icon:'danger',title:"",msg:eval("("+e+")").msg});	return;
		}
		var data=eval("("+e+")").data;		
			dg=UI.datagrid("#dataGrid-box",{
				data:data,
				striped:true,
				customAttr:{id:"id"},
				colums:[[
					{field:"i",title:"",width:"30px",formatter:function(val,row,index){
						return index+1;
					}},
					/*
					{field:"id",hidden:true,width:"1px",title:"",formatter:function(val,row,index){	//	任务ID
						if(val==null||val==""){
							return "";
						}else{
							return val;
						}
					}},
					*/
					{field:"planExe",title:"任务类型",width:233,formatter:function(val,row,index){
						if(parseInt(val)>0){
							return "临时任务";
						}else{
							return "普通任务";
						}
					}},
					/*
					{field:"trigger",title:"触发器",width:233,formatter:function(val,row,index){
						if(val==null||val==""){
							return "";
						}else{
							return val;
						}
					}},
					*/
					{field:"desc",title:"任务名称",width:233,formatter:function(val,row,index){
						if(val==null||val==""){
							return "";
						}else{
							return val;
						}
					}},
					{field:"cronDesc",title:"执行计划",width:233,formatter:function(val,row,index){
						if(val==null||val==""){
							return "";
						}else{
							return val;
						}
					}},
					/*{field:"triggerDesc",title:"触发器描述",width:233,formatter:function(val,row,index){
						if(val==null||val==""){
							return "";
						}else{
							return val;
						}
					}},*/
					
				
					/*{field:"group",title:"任务组",width:233,formatter:function(val,row,index){
						if(val==null||val==""){
							return "";
						}else{
							return val;
						}
					}},
					{field:"groupDesc",title:"任务组描述",width:233,formatter:function(val,row,index){
						if(val==null||val==""){
							return "";
						}else{
							return val;
						}
					}},*/
					{field:"execute",title:"已执行(次)",width:233,formatter:function(val,row,index){
						return val;
					}},
					{field:"lastExeTime",title:"最后一次执行",width:233,formatter:function(val,row,index){
						return val==0?"--":new Date(val).format("MM/dd HH:mm:ss");
					}},
					{field:"lastFinishTime",title:"最后一次耗时",width:233,formatter:function(val,row,index){
						if((row.lastExeTime>0)&&((val-row.lastExeTime)>0)){	
							var miao=(val-row.lastExeTime)/1000;	
							miao = new Number(new Number(miao+1).toFixed(2)-1).toFixed(2);
							if(miao<60){
								return miao+"秒";
							}else{
								return parseInt(miao/60)+"分"+new Number(new Number((miao%60)+1).toFixed(2)-1).toFixed(2)+"秒";
							}
//							if(val-row.lastExeTime>0){
//								 var miao=(val-row.lastExeTime)/1000;
//								 miao=miao.toFixed(2);
//								 if(miao<60){
//									 return miao+"秒";
//								 }else{
//									 return (miao/60)+"分"+(miao%60)+"秒";
//								 }
								//return ((val-row.lastExeTime)/1000) + "," + val;
//							}else{
//								return "正在执行";
//							}
						}else{
							return "--";
						}
					}},
					{field:"state",title:"状态",width:233,formatter:function(val,row,index){
		    				if(val != null){
								return ["<span style='color:#EFAD4D;'>已禁用</span>","<span style='color:#3071A9;'>已启动</span>","已删除","<span style='color:#d9544f;'>已暂停</span>","<span style='color:#5bb85d;'>已完成</span>"][val];
							}else{
								return ""
							}
					}},
					{field:"ope",title:"操作",width:"200px",formatter:function(val,row,index){
		    				var zanting='<div onclick="taskPlayOrPause(this);" class="btn btn-info btn-xs tooltip-info" title="'+(row.state==1?"暂停":"启动")+'\"><i class="'+(row.state==1?"fa fa-pause":"fa fa-play")+'"></i></div>&nbsp;';
		    				var shanchu='<div onclick="taskRemove(this);" class="btn btn-danger btn-xs tooltip-danger" title="删除"><i class="fa fa-trash-o"></i></div>&nbsp;';
//		    				var qidong='<a href="javascript:void(0);" class="btn btn-success btn-xs tooltip-danger" title="启动"><i class="fa fa-play"></i></a>&nbsp;';
		    				var jinyong='<div onclick="taskDisable(this);" class="btn btn-danger btn-xs tooltip-danger" title="禁用"><i class="glyphicon glyphicon-remove-circle"></i></div>&nbsp;';
		    				var chongqi='<div onclick="taskRestart(this);" class="btn btn-success btn-xs tooltip-danger" title="重启"><i class="fa fa-undo"></i></div>&nbsp;';
		    				var log='<div onclick="taskLog(this);" title="查看日志" data-toggle="modal" data-target="#logModal" class="btn btn-primary btn-xs tooltip-danger" title="日志">查看日志</div>';
		    				if(row.state==2){	//	删除
		    					shanchu="";
		    				}else if(row.state==0){	//	禁用	
		    					zanting="";
		    					jinyong="";
		    				}else if(row.state==4){	//	完成	
		    					zanting="";
		    					jinyong="";
		    					chongqi="";
		    				}
//		    				else if(row.state==1){
//		    					qidong="";
//		    				}
		    				return zanting+shanchu+jinyong+chongqi+log;
					}}
				]],
				onAfterRender:function(){
					$(".datasbox").unbind("click").on("click",".add",function(){
						
					});
				}
			});		
			pagination=UI.pager("#pagition",{pageNo:pageNo,pageSize:pageSize,total:data.total,inline:true,onPaging:function(evt,index){
				dataChange(index,pageSize);
			}});	
			taskDetailTriggerInitial(data);		
			$("#taskBody").show();
		},
		error:function(e){	console.log( e ); }	
	});
//	var xOffset = 10; 
//	var yOffset = 30; 
//	$("#dataGrid-box").on("mouseover","tr",function(e){
//		var i=$(e.target).closest("td");
//		if($(this).attr("data-trigger")!=""&&$(this).attr("data-trigger")!=null&&i.attr("data-name")!="ope"){
//			$(".hoverlayer").html("触发器:"+$(this).attr("data-trigger"));
//			$(".hoverlayer").show();
//		}
//	});
//	$("#dataGrid-box").on("mouseout","tr",function(){
//		$(".hoverlayer").html("");
//		$(".hoverlayer").hide();
//	});
//	$("#dataGrid-box").on("mousemove","tr",function(e){
//		$(".hoverlayer").css("top",(e.pageY - xOffset) + "px")
//		$(".hoverlayer").css("left",(e.pageX + yOffset) + "px") 
//	});
	
	$.ajax({ 
		url: "/web/task/getRecordMap.json",
		dataType:"json",
		type:"POST",
		success: function(e){
			$("#allTasks").attr("value",JSON.stringify(e));
		}
	});
	
});

function taskRestart(thisObj){
	if(window.confirm('真的重启？')){
		$.ajax({ 
			url:"/web/task/restart.json",
			dataType:"json",	
			type:"POST",
			data:{taskId:$(thisObj).parent().parent().attr("data-id")},
			success: function(datas){
				var tempValue = eval("("+datas.data+")");	// alert(tempValue.taskId);
				if($(thisObj).parent().parent().attr("data-id")==tempValue.taskId){
					if(tempValue.flag){	
						dataChange(1,pageSize);
					}else{
						UI.alert({icon:'danger',title:"",msg:tempValue.msg});
					}
				}else{
					UI.alert({icon:'danger',title:"",msg:"taskId不一致,请检查!"});
				}
			},
			error:function(e){
				UI.alert({icon:'danger',title:"",msg:"移除错误-"+console.log(e)});
			}
		});
	}else{}
}

function taskRemove(thisObj){
	if(window.confirm('真的删除？')){
		$.ajax({ 
			url:"/web/task/remove.json",
			dataType:"json",	
			type:"POST",
			data:{taskId:$(thisObj).parent().parent().attr("data-id")},
			success: function(datas){
				var tempValue = eval("("+datas.data+")");	// alert(tempValue.taskId);
				if($(thisObj).parent().parent().attr("data-id")==tempValue.taskId){
					if(tempValue.flag){	
						dataChange(1,pageSize);
					}else{
						UI.alert({icon:'danger',title:"",msg:tempValue.msg});
					}
				}else{
					UI.alert({icon:'danger',title:"",msg:"taskId不一致,请检查!"});
				}
			},
			error:function(e){
				UI.alert({icon:'danger',title:"",msg:"移除错误-"+console.log(e)});
			}
		});
	}else{}
}

function taskDisable(thisObj){
	if(window.confirm('真的禁用？')){
		$.ajax({ 
			url:"/web/task/disable.json",
			dataType:"json",	
			type:"POST",
			data:{taskId:$(thisObj).parent().parent().attr("data-id")},
			success: function(datas){
				var tempValue = eval("("+datas.data+")");	// alert(tempValue.taskId);
				if($(thisObj).parent().parent().attr("data-id")==tempValue.taskId){
					if(tempValue.flag){	
						dataChange(1,pageSize);
					}else{
						UI.alert({icon:'danger',title:"",msg:tempValue.msg});
					}
				}else{
					UI.alert({icon:'danger',title:"",msg:"taskId不一致,请检查!"});
				}
			},
			error:function(e){
				UI.alert({icon:'danger',title:"",msg:"移除错误-"+console.log(e)});
			}
		});
	}else{}
}

function taskPlayOrPause(thisObj){	//	启动或暂停
	if($(thisObj).find("i").attr("class")=="fa fa-pause"){	//	暂停
		if(window.confirm('真的暂停？')){
			$.ajax({ 
				url:"/web/task/pause.json",
				dataType:"json",	
				type:"POST",
				data:{taskId:$(thisObj).parent().parent().attr("data-id")},
				success: function(datas){
					var tempValue = eval("("+datas.data+")");
					if($(thisObj).parent().parent().attr("data-id")==tempValue.taskId){
						if(tempValue.flag){
							$(thisObj).attr("title","启动");
							$(thisObj).find("i").attr("class","fa fa-play");
							$(thisObj).parent().parent().find("td[data-name=state]").html("<span style='color:#d9544f;'>已暂停</span>");
						}else{
							UI.alert({icon:'danger',title:"",msg:tempValue.msg});
						}
					}else{
						UI.alert({icon:'danger',title:"",msg:"taskId不一致,请检查!"});
					}
				},
				error:function(e){
					UI.alert({icon:'danger',title:"",msg:"暂停错误-"+console.log(e)});
				}
			});
		}else{}
	}else{	//	启动
		if(window.confirm('真的启动？')){
			$.ajax({ 
				url:"/web/task/restart.json",
				dataType:"json",
				type:"POST",
				data:{taskId:$(thisObj).parent().parent().attr("data-id")},
				success: function(datas){
					var tempValue = eval("("+datas.data+")");
					if($(thisObj).parent().parent().attr("data-id")==tempValue.taskId){
						if(tempValue.flag){
							$(thisObj).attr("title","暂停");
							$(thisObj).find("i").attr("class","fa fa-pause");
							$(thisObj).parent().parent().find("td[data-name=state]").html("<span style='color:#3071A9;'>已启动</span>");
						}else{
							UI.alert({icon:'danger',title:"",msg:tempValue.msg});
						}
					}else{
						UI.alert({icon:'danger',title:"",msg:"taskId不一致,请检查!"});
					}	
				},
				error:function(e){
					UI.alert({icon:'danger',title:"",msg:"启动错误-"+console.log(e)});
				}
			});
		}else{}
	}
}

function taskDetailTriggerInitial(data){
	$("#dataGrid-box").find("table td[data-name^=desc]").on("mouseover",function(e){	
		if($(this).parent().attr("data-rowindex")!=""&&$(this).parent().attr("data-rowindex")!=null){
			var index = parseInt($(this).parent().attr("data-rowindex"));
			var info = "<p><b>任务详情</b></p><br/>";
			$.each(data.rows[index],function(key,val){
				if(val.length>=70){
					info += ("<b>" + key + "</b>" + ":" + val.substring(0,70)) + "<br/>";
					info += val.substring(70,val.length) + "<br/>";
				}else{
					info += ("<b>" + key + "</b>" + ":" + val) + "<br/>";
				}
			});
			$(".hoverlayer").css("background-color",$(this).css("background-color"));
			$(".hoverlayer").html(info);
			$(".hoverlayer").show();
		}
	});		
	$("#dataGrid-box").on("mouseout","tr",function(){	
		$(".hoverlayer").html("");
		$(".hoverlayer").hide();
	});
	$("#dataGrid-box").on("mousemove","tr",function(e){		
//		console.log($(document).height()+" - "+bomHeight);
//		if($(document).height()>bomHeight){
//			$(".hoverlayer").css("top",(e.pageY - 150) + "px");
//		}else{
//			$(".hoverlayer").css("top",(e.pageY - 10) + "px") ; 
//		}
//		alert( $("#pagition").offset().top+" - "+bomHeight );
		$(".hoverlayer").css("top",(e.pageY - 10) + "px");
		$(".hoverlayer").css("left",(e.pageX + 30) + "px");
	});
}

function dataChange(pageNo,pageSize){	
	$.ajax({ 
		url: "/web/task/getPage.json",
		dataType:"text",
		type:"POST",
		data:{pageNo:pageNo,pageSize:pageSize},
		success: function(e){
			var data=eval("("+e+")");
			if(data.status==1){						
				dg.render(data.data);
				pagination.gotoPage(data.data.total,pageNo);	
				var pageCount=parseInt(data.data.total%data.data.pageSize==0?data.data.total/data.data.pageSize:data.data.total/data.data.pageSize+1);
				taskDetailTriggerInitial(data.data);
			}else{
				$("#dataGrid-box").find("table tr").each(function(i){
					if(i>0){	//	一直删除时,如果到留到最后一列,需手动删除
						$(this).remove();
					}
				});
				$("#pagition").html("");
			}
		}
	});
};

function restartAllTask(){	
	if(window.confirm('确定是否重启所有任务？(回到最初)')){
		$.ajax({ 
			url: "/web/task/restartAll.json",
			dataType:"json",
			type:"POST",
			success: function(datas){
				var tempValue = eval("("+datas.data+")");	
				if(tempValue.flag){						
					dataChange(1,10);
					location.reload(); 
				}else{
					UI.alert({icon:'danger',title:"",msg:tempValue.msg});
				}
			}
		});
	}else{}
};	

function pageErrorLog(thisObj,objVal){	
	var seekVal ;
	if("errorPrev"==objVal){
		if(($("#errorNext").attr("value")==0)||($("#errorPrev").attr("value")==0)){
			$("#logError").html("没有找到error日志记录!");	
			return;
		}else{
			if($("#logError").text()=="没有找到error日志记录!"){		
				seekVal = error_prev_array[parseInt($("#errorNext").attr("value")-1)]+"" ;
				$("#errorPrev").attr("value",parseInt($("#errorPrev").attr("value"))+1);
				$("#errorNext").attr("value",parseInt($("#errorNext").attr("value"))+1);
			}else{
				seekVal = error_prev_array[parseInt($("#errorPrev").attr("value")-1)]+"" ;	
			}
			if(parseInt($("#errorPrev").attr("value"))!=0){
				$("#errorPrev").attr("value",parseInt($("#errorPrev").attr("value"))-1);
			}
			$("#errorNext").attr("value",parseInt($("#errorNext").attr("value"))-1);
		}
	}else if("errorNext"==objVal){
		if($("#logError").text()=="没有找到error日志记录!"){
			seekVal = error_prev_array[parseInt($("#errorPrev").attr("value"))]+"" ;	
			if($("#errorPrev").attr("value")!=0){
				$("#errorPrev").attr("value",parseInt($("#errorPrev").attr("value"))-1);
			}
			$("#errorNext").attr("value",parseInt($("#errorNext").attr("value"))-1);
		}else{
			seekVal = error_prev_array[parseInt($("#errorNext").attr("value"))]+"" ;	
		}
		if(parseInt($("#errorNext").attr("value"))!=0){
			$("#errorPrev").attr("value",parseInt($("#errorPrev").attr("value"))+1);
		}
		$("#errorNext").attr("value",parseInt($("#errorNext").attr("value"))+1);
	}		
	$.ajax({ 
		url: "/web/logs/getError.json",
		dataType:"json",	
		type:"post",
		data:{taskId:$("#currTaskId").attr("value"),seek:seekVal},
		success: function(datas){
		//	console.log( datas.data.currSeek + " - " + datas.data.data.length );	
			if(datas.data.data.length!=0){
				$("#logError").html("");
				$("#errorPrev").attr("disabled","disabled") ;
				$("#errorNext").attr("disabled","disabled") ;
				setTimeout(function(){
					for( var temp=0;temp<datas.data.data.length;temp++ ){
						$("#logError").append( datas.data.data[temp]+"\n" );
					}
					$("#errorPrev").removeAttr("disabled") ;
					$("#errorNext").removeAttr("disabled") ;
					if(("errorNext"==objVal)&&(error_prev_array.indexOf(datas.data.currSeek)==-1)){
						error_prev_array[error_prev_array.length] = datas.data.currSeek ;
					}
				},888);
			}else{
				$("#errorPrev").attr("value",parseInt($("#errorPrev").attr("value"))-1);
				$("#errorNext").attr("value",parseInt($("#errorNext").attr("value"))-1);
				$("#logError").html( "没有找到error日志记录!" );
			}
		},
		error: function(datas){
			UI.alert({icon:'danger',title:"",msg:console.log(datas)});
		}
	});
};

function pageInfoLog(thisObj,objVal){		
	var seekVal ;
	if("infoPrev"==objVal){
		if(($("#infoNext").attr("value")==0)||($("#infoPrev").attr("value")==0)){
			$("#logInfo").html("没有找到info日志记录!");
			return;
		}else{
			if($("#logInfo").text()=="没有找到info日志记录!"){		
				seekVal = info_prev_array[parseInt($("#infoNext").attr("value")-1)]+"" ;
				$("#infoPrev").attr("value",parseInt($("#infoPrev").attr("value"))+1);
				$("#infoNext").attr("value",parseInt($("#infoNext").attr("value"))+1);
			}else{
				seekVal = info_prev_array[parseInt($("#infoPrev").attr("value")-1)]+"" ;	
			}
			if(parseInt($("#infoPrev").attr("value"))!=0){
				$("#infoPrev").attr("value",parseInt($("#infoPrev").attr("value"))-1);
			}
			$("#infoNext").attr("value",parseInt($("#infoNext").attr("value"))-1);
		}
	}else if("infoNext"==objVal){
		if($("#logInfo").text()=="没有找到info日志记录!"){
			seekVal = info_prev_array[parseInt($("#infoPrev").attr("value"))]+"" ;	
			if($("#infoPrev").attr("value")!=0){
				$("#infoPrev").attr("value",parseInt($("#infoPrev").attr("value"))-1);
			}
			$("#infoNext").attr("value",parseInt($("#infoNext").attr("value"))-1);		
		}else{
			seekVal = info_prev_array[parseInt($("#infoNext").attr("value"))]+"" ;	
		}
		if(parseInt($("#infoNext").attr("value"))!=0){
			$("#infoPrev").attr("value",parseInt($("#infoPrev").attr("value"))+1);
		}
		$("#infoNext").attr("value",parseInt($("#infoNext").attr("value"))+1);
	}		
	$.ajax({ 
		url: "/web/logs/getInfo.json",
		dataType:"json",	
		type:"post",
		data:{taskId:$("#currTaskId").attr("value"),seek:seekVal},
		success: function(datas){
		//	console.log( datas.data.currSeek + " - " + datas.data.data.length );	
			if(datas.data.data.length!=0){
				$("#logInfo").html("");
				$("#infoPrev").attr("disabled","disabled") ;
				$("#infoNext").attr("disabled","disabled") ;
				setTimeout(function(){
					for( var temp=0;temp<datas.data.data.length;temp++ ){
						$("#logInfo").append( datas.data.data[temp]+"\n" );
					}
					$("#infoPrev").removeAttr("disabled") ;
					$("#infoNext").removeAttr("disabled") ;
					if(("infoNext"==objVal)&&(info_prev_array.indexOf(datas.data.currSeek)==-1)){
						info_prev_array[info_prev_array.length] = datas.data.currSeek ;
					}
				},888);
			}else{
				$("#infoPrev").attr("value",parseInt($("#infoPrev").attr("value"))-1);
				$("#infoNext").attr("value",parseInt($("#infoNext").attr("value"))-1);
				$("#logInfo").html( "没有找到info日志记录!" );
			}
		},
		error: function(datas){
			UI.alert({icon:'danger',title:"",msg:console.log(datas)});
		}
	});
};

function taskLog(thisObj){	
	$("#logError").html("");
	$("#logInfo").html("");
	$("#currTaskId").attr("value",$(thisObj).parent().parent().attr("data-id"));
	$.ajax({ 
		url: "/web/logs/getAllLog.json",
		dataType:"json",
		type:"post",
		data:{taskId:$(thisObj).parent().parent().attr("data-id")},
		success: function(datas){
//			var tempValue = eval("("+datas.data+")");	
//			console.log( datas.error.data );
//			console.log( datas.info.data );
			if(datas.info.data.data.length!=0){		
				for( var temp=0;temp<datas.info.data.data.length;temp++ ){
					$("#logInfo").append( datas.info.data.data[temp]+"\n" );
				}
				info_prev_array[info_prev_array.length] = datas.info.data.currSeek ;
				$("#errorPrev").attr("value",0);
				$("#errorNext").attr("value",1);
			}else{
				$("#logInfo").append( "没有找到info日志记录!" );
			}
			if(datas.error.data.data.length!=0){
				for( var temp=0;temp<datas.error.data.data.length;temp++ ){
					$("#logError").append( datas.error.data.data[temp]+"\n" );
				}	
				error_prev_array[error_prev_array.length] = datas.error.data.currSeek ;
				$("#infoPrev").attr("value",0);
				$("#infoNext").attr("value",1);
			}else{
				$("#logError").append( "没有找到error日志记录!" );
			}
			$("#logError").show();
			$("#logInfo").hide();
		},
		error: function(datas){
			UI.alert({icon:'danger',title:"",msg:console.log(datas)});
		}
	});
};

function logShow(value){
	if(value=="error"){
		$("#logError").show();
		$("#logInfo").hide();
		$("#errorSpan").show("slow");
		$("#infoSpan").hide("slow");
	}else if(value=="info"){
		$("#logInfo").show();
		$("#logError").hide();
		$("#errorSpan").hide("slow");
		$("#infoSpan").show("slow");
	}
}

setTimeout(function(){
	
},2000);
