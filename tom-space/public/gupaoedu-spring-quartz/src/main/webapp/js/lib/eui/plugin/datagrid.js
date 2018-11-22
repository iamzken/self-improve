window.UI.plugin = (function(){
	return {
		datagrid:function(selector,options){//表格
			var opts = jQuery.extend(true,{
                url:"",//数据路径
                data:"",
                isBanma:true,
                colums:{},
                box:"",//表格容器
                checkedTrs:[],
                datagridClass:"datagrid",
                onInit:function(event){},
                onLoadSuccess:function(event){},
                onLoadError:function(event){},
                onAfterRender:function(){}
            },options),self = {},pagination=null;
			self.ele=$(selector);
			//初始化分页属性
			if(opts.pagination!=""&&opts.pagination!=null&&opts.pagination!=false){
				opts.total=opts.pagination.total;
				opts.pageSize=opts.pagination.pageSize;
				opts.pageNo=opts.pagination.pageNo;
				if(typeof opts.pagination=="string"||opts.pagination==true){
					opts.total=11;
					opts.pageSize=5;
					opts.pageNo=1;
				}
			}
			//加入多选框	            
            var item={field:"checkbox",title:'<div style="margin: 0px;width: 20px;" class="checkbox">'+
        	        '<label style="padding: 0px">'+
                    '<input id="ckb-all" type="checkbox" class="colored-blue">'+
                    '<span class="text"></span>'+
        		    '</label>'+
        		    '</div>',type:"checkbox",width:20,formatter:function(r,row,index){
        			return '<div style="margin: 0px;width: 20px;" class="checkbox">'+
        	        '<label style="padding: 0px">'+
                    '<input type="checkbox" class="colored-blue dg-ckb">'+
                    '<span class="text"></span>'+
        		    '</label>'+
        		    '</div>';
        	}};
            for(var i=0;i< opts.colums[0].length;i++){
            	if(opts.colums[0][i].checkbox==true){
            		opts.colums[0][i]=item;
            	}
            }
			self.ele.each(function(i,o){
				var _self=this;
				//添加分页容器
				$(_self).append("<div class='dg-warpper'></div>");
				if(opts.pagination!=""&&opts.pagination!=null&&opts.pagination!=false){
					$(_self).append("<div class='pagers'></div>")
					opts.pagination.selector=selector+' .pagers';
				}
				$(_self).bind("onInit",opts.onInit);
				$(_self).bind("onLoadSuccess",opts.onLoadSuccess);
				$(_self).bind("onLoadError",opts.onLoadError);
				$(_self).bind("onAfterRender",opts.onAfterRender);
				$(_self).trigger("onInit");
				if(opts.data==""||opts.data==null){
					if("" == opts.url || null == opts.url){
	            		opts.data = {data:[]};
	            		render(opts.data);
	            	}else{
						CORE.ajax({url:opts.url,dataType:"text",success:function(e){
							opts.data=eval('('+e+')');
							render(opts.data);
							$(_self).trigger("onLoadSuccess",[e]);
			        	},error:function(e){
			        		$(_self).trigger("onLoadError",[e]);
			        	}
						});
	            	}
				}else{
					 render(opts.data);
				}
	            
				
				//跳到指定页
				function gotoPage(total,pageNo){
					opts.total=total;
					if(pageNo){						
						opts.pageNo=pageNo;
					}
					render(opts.data);
				}
				//呈现
				function render(data,delRowIndex,isRe){
					opts.data=data;
					//不分页时的情况
					if(opts.pagination==""||opts.pagination==null||opts.pagination==false){
						opts.pageSize=opts.data.rows.length;
						opts.pageNo=1;
					}
					if(delRowIndex!=-1){
						getSelectedRow();
					}
					if(!isNaN(delRowIndex)&&delRowIndex>-1){
						for(var i=0;i<opts.checkedTrs.length;i++){
							if(opts.checkedTrs[i]==delRowIndex){
								var index=opts.checkedTrs.indexOf(delRowIndex);
								opts.checkedTrs.splice(index,1);
								i=-1;//删除元素后 重新遍历
								continue;
							}else if(opts.checkedTrs[i]>delRowIndex){
								opts.checkedTrs[i]-=1;
							}
						}
					}
					//如果当前页数超过总页数设置为最后一页
					opts.total=opts.data.rows.length;
					var pageCount=parseInt(opts.total%opts.pageSize==0?opts.total/opts.pageSize:opts.total/opts.pageSize+1);
					if(opts.pageNo>pageCount||opts.pageNo<1){opts.pageNo=pageCount;}
					dataGrid=$('<div class="'+opts.datagridClass+'"><table class="table-datagrid table table-striped table-hover table-bordered"><thead class="bordered-blueberry"></thead><tbody></tbody></table></div>');
					renderDataGridHerder(opts.colums);
					renderDataGridBody(data.rows,opts.colums);
					//renderDataGridFooter(data);
					$(_self).find(".dg-warpper").html("");
					$(_self).find(".dg-warpper").append(dataGrid);
					if(delRowIndex!=-1){					
						setSelectedRow();
					}
					$(_self).trigger("onAfterRender");
					
					//分页
					if(opts.pagination!=""&&opts.pagination!=null&&opts.pagination!=false){
						var sele=opts.pagination.selector;
						var total=opts.data.rows.length;
						
						if(typeof opts.pagination=="string"){
							sele=opts.pagination;
						}
						if(opts.pagination==true){
							sele=selector+' .pagers';
						}
						if(pagination){
							pagination.gotoPage(total,opts.pageNo);
						}
						if(!pagination||isRe==true){
							pagination=UI.pager(sele,{pageNo:opts.pageNo,pageSize:opts.pageSize,total:total,onPaging:function(evt,index){
								pagination.gotoPage(total,index);
								gotoPage(total,index);
							}});
						}
					}
					//多选框全选
					$(selector).on("click","#ckb-all",function(){
						var ckbs=$(selector).find("input[type=checkbox].dg-ckb");
						if($(this).is(":checked")){
							ckbs.prop("checked","checked");
						}else{
							ckbs.removeAttr("checked");
						}
					});
				}
				
				
				//呈现表头
				function renderDataGridHerder(cols){
					var dataHeader='<tr class="theader-row">';
					for(var i = 0; i < cols[0].length; i ++){
						dataHeader+='<th  style="background-color:#EAE8E8; width:'+cols[0][i].width+'">'+cols[0][i].title+'</th>';
					}
					dataHeader+="</tr>";
					dataGrid.find("table.table-datagrid thead").append(dataHeader);
				}
				//呈现表身
				function renderDataGridBody(data,cols){
					var dataBody="";var index=0;
					for(var j=0;j<data.length;j++){
						if(j>opts.pageNo*opts.pageSize-opts.pageSize-1&&j<opts.pageNo*opts.pageSize){
							//设置自定义属性
							var attrHtml="";
							if(opts.customAttr){
								for(var key in opts.customAttr){
									attrHtml+=" data-"+key+"='"+data[j][opts.customAttr[key]]+"' ";
								}
							}
							var classHtml="";
							classHtml=data[j]["customClass"];
							dataBody+='<tr '+attrHtml+' class="datagrid-row '+classHtml+'"  data-rowIndex="'+j+'" data-curPageRowIndex="'+index+'">';
							for(var i = 0; i < cols[0].length; i ++){
								dataBody+='<td data-name="'+cols[0][i].field+'" class="datagrid-col" '+(j%2==0&opts.isBanma?'style="background-color:#EEEEEE;"':'')+' >';
								var field=data[j][cols[0][i].field];
								if(typeof cols[0][i].formatter=="function"){
									field=cols[0][i].formatter(data[j][cols[0][i].field],data[j],j);
								}
								//编辑时改成输入框 
								//(j==editIndex)?'<input id="ipt_'+editIndex+'_'+cols[0][i].field+'"  type="text" value="'+field+'" />':
								dataBody+=field;
								dataBody+='</td>';
							}
							dataBody+="</tr>";
							index++;
						}
					}
					dataGrid.find("table.table-datagrid tbody").append(dataBody);
				}
				//获取选中行
				function getSelectedRow(){
					//opts.checkedTrs=[];
					var table=$("table",selector);
					var trs=table.find("tr");
					trs.each(function(){
						if($(this).data("rowindex")!=null){
							if($(this).find("input[type=checkbox]").is(":checked")){
								if(opts.checkedTrs.indexOf($(this).data("rowindex"))==-1){									
									opts.checkedTrs.push($(this).data("rowindex"));
								}
							}else{
								var i=opts.checkedTrs.indexOf($(this).data("rowindex"));
								if(i!=-1){
									opts.checkedTrs.splice(i,1);
								}
							}
						}
					});
				} 
				//设置选中行
				function setSelectedRow(){
					var table=$("table",selector);
					var trs=table.find("tr");
					trs.each(function(){
						if($(this).data("rowindex")!=null){
							var rowIndex=parseInt($(this).data("rowindex"));
							if(opts.checkedTrs.indexOf(rowIndex)!=-1){
								$(this).find("input[type=checkbox]").attr("checked","checked");
							}
						}
					});
				}
				//呈现表脚
				function renderDataGridFooter(data){
					//'<ul class="pagination"><li class="prev"><a href="javascript:void(0)">上一页</a></li><li data-index="1"><a href="javascript:void(0)">1</a></li><li data-index="2" class="active"><a href="javascript:void(0)">2</a></li><li data-index="3"><a href="javascript:void(0)">3</a></li><li class="next"><a href="javascript:void(0)">下一页</a></li></ul>';
					var dataFooter='<div class="row">';
					dataFooter+='<div class="col-sm-6"><span>当前'+curIndex*pageSize+'到'+(curIndex*pageSize+pageSize>=total?total:(curIndex*pageSize+pageSize))+'条记录，共'+total+'条</span></div>';
					//dataFooter+='<ul class="pagination"><li class="prev"><a href="javascript:void(0)">上一页</a></li><li data-index="1"><a href="javascript:void(0)">1</a></li><li data-index="2" class="active"><a href="javascript:void(0)">2</a></li><li data-index="3"><a href="javascript:void(0)">3</a></li><li class="next"><a href="javascript:void(0)">下一页</a></li></ul>';
					dataFooter+='<div class="col-sm-6"><ul style="float:right;" class="pagination">'+
		                  '<li class="prev"><a href="javascript:void(0)">上一页</a></li>'+
		                  '<li data-index="1"><a href="javascript:void(0)">1</a></li>'+
		                  '<li class="next disabled"><a href="javascript:void(0);">下一页</a></li>'+
			              '</ul></div>'
					dataFooter+="</div>";
					dataGrid.append(dataFooter);
				}
				//编辑行
				function editDataRow(index,rowData,insertIndex){//index=-1为增加一条
					if(index!=-1){
						for(var i=0;i<opts.data.rows.length;i++){
							if(i==index){
								var newRowData=jQuery.extend(true,opts.data.rows[i],rowData);
								opts.data.rows[i]=newRowData;
								break;
							}
						}
					}
					if(index==-1){
						if(insertIndex>-1){
							opts.data.rows.splice(insertIndex, 0,rowData);
						}else{
							opts.data.rows.unshift(rowData);	
						}
						
						render(opts.data);
						
						
					}else{
						render(opts.data)
					}
				}
				//删除行
				function deleteRow(rowIndex){
					opts.data.rows.splice(rowIndex,1);
					render(opts.data,rowIndex);
				}
				//批量删除
				function batchDelRow(){
					getSelectedRow();
					indexs=opts.checkedTrs;
					indexs.sort(function(n1,n2){
						return n2-n1;
					});
					for(var i=0;i<indexs.length;i++){
						opts.data.rows.splice(indexs[i],1);
					}
					render(opts.data,-1);
					opts.checkedTrs=[];
				}
				//刷新行的rowindex
				function refreshRowIndex(){
					var trs=dataGrid.find("table.table-datagrid").find("tr.datagrid-row");
					trs.each(function(i){
						$(this).data("rowindex",i);
					});
				}
				self={
					getRowByIndex:function(rowIndex){
						return opts.data.rows[rowIndex];
					},
					render:function(data){
						render(data,-1,true);
					},
					editRow:function(editIdnex,rowData){//编辑行
						editDataRow(editIdnex,rowData);
					},
					addRow:function(rowData){//新增一行
						editDataRow(-1,rowData);
					},
					//插入一行到指定位置
					insertRow:function(rowData,insertIndex){
						editDataRow(-1,rowData,insertIndex);
					},
					deleteRow:function(rowIndex){//删除行
						deleteRow(rowIndex);
					},
					batchDelRow:function(indexs){
						batchDelRow(indexs);
					},
					getCurTotal:function(){
						return opts.data.rows.length;
					},
					gotoPage:function(total,pageNo){
						gotoPage(total,pageNo);
					}
				};
			});
			
			return self;
		}
	};
})();