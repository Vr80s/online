var scoreTypeTable;
var searchCase;
var seed=1;
var searchCase = new Array();
$(function(){
	document.onkeydown=function(event){
		if(event.keyCode==13){
			return false
		}
	}
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
	var objData = [{ "title": checkbox,"class":"center","width":"60px","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
				        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
				    }},
	               { "title": "序号", "class": "center","width":"80px","sortable": false,"data":"sort" },
	               { "title": "科室名称", "class":"center","sortable":false,"data": 'name' },
	               { "title": "创建人", "class":"center","sortable":false,"data": 'createPerson' },
					{ "title": "创建日期", "class":"center","sortable":false,"data": 'createTime','mRender':function(data, display, row){
						return getLocalTime(data);
					}
                    },
					{ "title": "状态", "class":"center","sortable":false,"data": 'status',"mRender":function (data, display, row) {
						return row.status?"已启用":"已禁用";
						}
					},
					
/*					 {"sortable": false,"class": "center","width":"10%","title":"排序","data": 'sort',"mRender":function (data, display, row) {
					    	return '<div class="hidden-sm hidden-xs action-buttons">'+
							'<a class="blue" href="javascript:void(-1);" title="上移" onclick="upMove(this)" name="upaa"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
					    	'<a class="blue" href="javascript:void(-1);" title="下移" onclick="downMove(this)" name="downaa"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
						}},*/
					
					
					 /*{"sortable": false,"class": "center","width":"10%","title":"排序","data": 'sort',"mRender":function (data, display, row) {
							if(row.status ==1){//如果是禁用
					    		return '<div class="hidden-sm hidden-xs action-buttons">'+
					    		'<a class="blue" name="upa" href="javascript:void(-1);" title="上移"  onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
					        	'<a class="blue" name="downa" href="javascript:void(-1);" title="下移"  onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
					    	}else{
					    		return '<div class="hidden-sm hidden-xs action-buttons">'+
					    		'<a class="gray" href="javascript:void(-1);" title="上移"  ><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
					        	'<a class="gray" href="javascript:void(-1);" title="下移"  ><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
					    	}
					 }},*/

        			{ "title": "推荐值", "class":"center","width":"6%", "sortable":false,"data": 'sort' },
	               { "sortable": false,"data":"id","class": "center","width":"14%","title":"操作","mRender":function (data, display, row) {
		            		   var buttons= '<div class="hidden-sm hidden-xs action-buttons">'+
								'<a class="blue" href="javascript:void(-1);" title="查看" onclick="openShow(this)"><i class="ace-icon fa fa-search  bigger-130"></i></a>'+
								'<a class="blue" href="javascript:void(-1);" title="修改" onclick="editDialog(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
					   			if(row.status==1) {
									buttons+='<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> ';
								}else{
									buttons+='<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> ';
								};
                                buttons+='<a class="blue" href="javascript:void(-1);" title="设置推荐值" onclick="updateRecommendSort(this)">设置推荐值</a>';
		      				return buttons;

				   		}
		      		},
		      		{ "title": "被引用的数量", "class": "center","width":"80px","sortable": false,"data":"menuCount","visible":false }
		      		];
	scoreTypeTable = initTables("scoreTypeTable",basePath+"/medical/department/list",objData,true,true,2,null,searchCase,function(data){
		debugger
			 var iDisplayStart = data._iDisplayStart;
			 var countNum = data._iRecordsTotal;//总条数
			 pageSize = data._iDisplayLength;//每页显示条数
			 currentPage = iDisplayStart / pageSize +1;//页码
			 if(currentPage == 1){//第一页的第一行隐藏向上箭头
			 	$("#scoreTypeTable tbody tr:first td").eq(6).find('a').eq(0).css("pointer-events","none").removeClass("blue").addClass("gray");
			 }
			 if(countNum/pageSize < 1 || countNum/pageSize == 1){//数据不足一页隐藏下移箭头
			 	$("#scoreTypeTable tbody tr:last td").eq(6).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
			 }
			 var countPage;
			 if(countNum%pageSize == 0){
			 	countPage = parseInt(countNum/pageSize);
			 }else{
			 	countPage = parseInt(countNum/pageSize) + 1;
			 }
			if(countPage == currentPage){//隐藏最后一条数据下移
//				$("#cloudClassTable tbody tr:last td:last a:eq(5)").hide();
				$("#scoreTypeTable tbody tr:last td").eq(6).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
			}
 			$("[name='upa']").each(function(index){
 				if(index == 0){
 					$(this).css("pointer-events","none").removeClass("blue").addClass("gray");
 				}
 			});
 			$("[name='downa']").each(function(index){
 				if(index == $("[name='downa']").size()-1){
 					$(this).css("pointer-events","none").removeClass("blue").addClass("gray");
 				}
 			});
	});
	addForm = $("#add-form").validate({
		messages:{
			name: {
				required:"请输入科室名称！",
				minlength:"授课方式名称过短，应大于2个字符！"
			}
		}
	});

	updateForm= $("#update-form").validate({
		messages:{
			name: {
				required:"请输入科室名称！",
				minlength:"授课方式名称过短，应大于2个字符！"
			}
		}
	});


	//下线时间 时间控件
	createDatetimePicker($('.datetime-picker'));
	
	

});

$(function(){


	 /* 添加 */
      $("#add_button").click(function(){
      	 $("textarea").keyup(function(){
			   var len = $(this).val().length;
			   if(len > 100){
			    $(this).val($(this).val().substring(0,100));
			   }
		   });
//		 ajaxRequest(basePath+"/medical/department/maxSort",null,function(result){
			  $("input[name='code']").removeAttr("disabled");//增加角色把代码框放开
			  addForm.resetForm();
			 // $("#remark").val(result.resultObject+1); 去掉之前sort的默认值
			  openDialog("roleDialog","dialogRoleDiv","新增科室",500,330,true,"提交",function(){
				  if($("#add-form").valid()){
					  mask();
					  $("#add-form").attr("action", "medical/department/add");
					  $("#add-form").ajaxSubmit(function(data){
						  unmask();
						  if(data.success){
							  $("#roleDialog").dialog("close");
							  layer.msg(data.errorMessage);
							  freshTable(scoreTypeTable);
						  }else{
							  layer.msg(data.errorMessage);
						  }
					  });
				  }
			  });
//		  });
      });


      /*  删除*/
      $(".dele_bx").click(function(){
      	  deleteAll('user/role/deletes',scoreTypeTable);
      });

      /* 修改 */
      $(".rolexg").click(function(){

      });
});



/**
 * 科室排序列表上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTable.fnGetData(oo);
	ajaxRequest(basePath+'/medical/department/upMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(scoreTypeTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

/**
 * 科室排序列表下移
 * @param obj
 */
function downMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTable.fnGetData(oo);
	ajaxRequest(basePath+'/medical/department/downMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(scoreTypeTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};
/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTable.fnGetData(oo);
	ajaxRequest(basePath+"/medical/department/updateStatus",{"id":aData.id,"status":aData.status},function(){
		freshTable(scoreTypeTable);
	});
}

/**
 * 状态修改
 * @param obj
 */
function childrenMenusUpdateStatus(id,status){
	ajaxRequest(basePath+"/cloudClass/menu/updateStatus",{"id":id,"status":status},function(){
	});
}



function delDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTable.fnGetData(oo);
	showDelDialog(function(){
		mask();
		var url = "user/role/delete";
		ajaxRequest(url,{'id':aData.id},function(data){
			unmask();
			if(!data.success){
				alertInfo(data.errorMessage);
			}else{
				freshTable(scoreTypeTable);
			}
		});
	});
}

function editDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTable.fnGetData(oo); // get datarow
	$("#update_id").val(aData.id);
	$("#update_name").val(aData.name);
	$("#update_remark").val(aData.remark);
	openDialog("updateDialog","updateDialogDiv","修改科室",500,330,true,"提交",function(){
		if($("#update-form").valid()){
			mask();
			$("#update-form").attr("action", "medical/department/update");
			$("#update-form").ajaxSubmit(function(data){
				unmask();
				if(data.success){
					$("#updateDialog").dialog("close");
					console.log(data);
					layer.msg(data.errorMessage);
					freshTable(scoreTypeTable);
					
				}else{
					layer.msg(data.errorMessage);
				}
			});
		}
	});
}

function openShow(obj){
	var oo = $(obj).parent().parent().parent();
	var row = scoreTypeTable.fnGetData(oo); // get datarow
	$("#show_menuName").text(row.name);
	$("#show_createPerson").text(row.createPerson);
	$("#show_createTime").text(getLocalTime(row.createTime));
	$("#show_status").text(row.status=="1"?"已启用":"已禁用");
	$("#show_remark").text(row.remark);
	var dialog = openDialogNoBtnName("previewCloudClasMenuDialog","previewCloudClasMenuDialogDiv","查看科室",555,350,false,"确定",null);
}

function cancelRow(trId){
	$(trId).hide();
}
function appendRow(pid){
	$("#childMenus").append("<tr id='tr_"+seed+"'><td><input type='text' id='menus_name_"+seed+"' />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='button' value='保存' onclick='saveMenusRow(\"menus_name_"+seed+"\",\""+pid+"\")'/>&nbsp;&nbsp;&nbsp;<input type='button' value='取消' onclick='cancelRow(tr_"+seed+")'/></td></tr>");
	seed++;
}

function deleteRow(rowId,pid){
	ajaxRequest(basePath+"/cloudClass/menu/deleteChildren",{'id':rowId},function(data) {
		if(data.success){
			ajaxRequest(basePath+"/cloudClass/menu/childMenu",{'pid':pid},function(data) {
				drawMenusPage(data,pid);
			})
		}
	});
}

function drawMenusPage(data,pid){
	$("#childMenus").html("");
	if(data.success){
		for(var i=0;i<data.resultObject.length;i++){
			var rowData="<tr id='childMenus_tr_"+data.resultObject[i].id+"'><td> ";
			if(data.resultObject[i].status==1){
				rowData+="<input type='checkbox' name='childMenuNames' onclick='childrenMenusUpdateStatus(\""+data.resultObject[i].id+"\",\""+data.resultObject[i].status+"\")' checked='checked' value='"+data.resultObject[i].name+"' />"+data.resultObject[i].name;
			}else{
				rowData+="<input type='checkbox' name='childMenuNames'  onclick='childrenMenusUpdateStatus(\""+data.resultObject[i].id+"\",\""+data.resultObject[i].status+"\")' value='"+data.resultObject[i].name+"' />"+data.resultObject[i].name;
			}
			rowData+="</td>";
			rowData+="<td>";
			rowData+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			if(i!=0){
				rowData+="<a class='blue' href='javascript:void(-1);' title='上移' onclick='childrenMenusUpMove("+data.resultObject[i].id+","+pid+")'><i class='glyphicon glyphicon-arrow-up bigger-130'></i></a>";
			}else{
				rowData+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			}
			if(i+1!=data.resultObject.length){
				rowData+="<a class='blue' href='javascript:void(-1);' title='下移' onclick='childrenMenusDownMove("+data.resultObject[i].id+","+pid+")'><i class='glyphicon glyphicon-arrow-down bigger-130'></i></a>";
			}else{
				rowData+="&nbsp;&nbsp;&nbsp;&nbsp;";
			}
			rowData+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class='blue' href='javascript:void(-1);' title='增加' onclick='appendRow("+pid+")'><i class='glyphicon glyphicon-plus bigger-130'></i></A>&nbsp;&nbsp;<a class='blue' href='javascript:void(-1);' title='删除' onclick='deleteRow("+data.resultObject[i].id+","+pid+")'><i class='glyphicon glyphicon-minus bigger-130'></i></A>"
			rowData+="</td>";
			rowData+="</tr>";
			$("#childMenus").append(rowData);

		}
	}
}

function search(){
	var name = $("#name").val();
	var timeStart = $("#time_start").val();
	var timeEnd = $("#time_end").val();

	if(timeStart != "" || timeEnd != ""){
		if(timeEnd != "" && timeStart == ""){
			alertInfo("开始时间不能为空");
			return;
		}
		if(timeStart != "" && timeEnd == ""){
			alertInfo("结束时间不能为空");
			return;
		}
		if(timeStart > timeEnd){
			alertInfo("开始时间不能大于结束时间");
			return;
		}
		searchCase.push('{"tempMatchType":"7","propertyName":"time_start","propertyValue1":"'+timeStart+'","tempType":"String"}');
		searchCase.push('{"tempMatchType":"6","propertyName":"time_end","propertyValue1":"'+timeEnd+'","tempType":"String"}');
		searchCase.push('{"tempMatchType":"5","propertyName":"name","propertyValue1":"'+name+'","tempType":"String"}');
	}
	searchButton(scoreTypeTable,searchCase);
	searchCase.pop();
	searchCase.pop();
}

function deleteBatch(){
	deleteAll(basePath+"/medical/department/deletes",scoreTypeTable);
}

/**
 * Description：设置推荐值
 * @Date: 2018/3/9 14:11
 **/
function updateRecommendSort(obj){
    var oo = $(obj).parent().parent().parent();
    var row = scoreTypeTable.fnGetData(oo);
    $("#UpdateRecommendSort_id").val(row.id);
    var dialog = openDialog("UpdateRecommendSortDialog","dialogUpdateRecommendSortDiv","修改推荐值",350,200,true,"确定",function(){
        if($("#UpdateRecommendSortFrom").valid()){
            mask();
            $("#UpdateRecommendSortFrom").attr("action", basePath+"/medical/department/updateSort");
            $("#UpdateRecommendSortFrom").ajaxSubmit(function(data){
                try{
                    data = jQuery.parseJSON(jQuery(data).text());
                }catch(e) {
                    data = data;
                }
                unmask();
                if(data.success){
                    $("#sort").val("");
                    $("#UpdateRecommendSortDialog").dialog("close");
                    layer.msg(data.resultObject);
                    freshTable(scoreTypeTable);
                }else{
                    alertInfo(data.errorMessage);
                }
            });
        }
    });
};


//配置角色权限
function configResource(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTable.fnGetData(oo); // get datarow
	//alertInfo("配置" + aData.name + "权限");
	//debugger;
	var roleId = aData.id;
	console.log("roleId:" + roleId);
	initCommonZtrees("resource","resource2","user/resource/role/tree",{"roleId":roleId},"right",function(){//左右两边都加载
			initCommonZtrees("resource","resource2","user/resource/tree",{},"left");
	});
	openDialog("configDialog","dialogConfigDiv","分配权限",560,480,true,"提交",function(){
		var resourceIds = new Array();
		if(!isnull(Tree2)){
			var nodes = getAllNodes(Tree2);
			if(nodes.length>0){
				for(var i=0;i<nodes.length;i++){
					var id = nodes[i].id;
					var name = nodes[i].name;
					resourceIds.push(id);
				}
			}
		}
		var url = "user/role/update/resources";
		var data = {"roleId":roleId,"resourceIds":resourceIds.join(",")};
		ajaxRequest(url,data,function(result){
			if(result.success){
					$("#configDialog").dialog("close");
			}else{
				alertInfo(result.errorMessage);
			}
		});
	});
}

function getLocalTime(nS) {
    return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');
}