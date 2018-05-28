var scoreTypeTableSxxy;
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
	               { "title": "课程类别名称", "class":"center","sortable":false,"data": 'name' },
	               { "title": "创建人", "class":"center","sortable":false,"data": 'createPerson' },
					{ "title": "创建日期", "class":"center","sortable":false,"data": 'createTime' },
					{ "title": "状态", "class":"center","sortable":false,"data": 'status',"mRender":function (data, display, row) {
						return row.status=="1"?"已启用":"已禁用";
						}
					},
					{ "sortable": false,"data":"id","class": "center","width":"10%","title":"排序","mRender":function (data, display, row) {
				   		var buttons= '<div class="hidden-sm hidden-xs action-buttons">';
							buttons+='<a class="blue" id="moveUp" href="javascript:void(-1);" title="上移" onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
							'<a class="blue" id="moveDown" href="javascript:void(-1);" title="下移" onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a>';
							return buttons;
			   			}
					},
	               { "sortable": false,"data":"id","class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {
		            	   if(row.menuCount==0){
		            		   var buttons= '<div class="hidden-sm hidden-xs action-buttons">'+
								'<a class="blue" href="javascript:void(-1);" title="查看" onclick="openShow(this)"><i class="ace-icon fa fa-search  bigger-130"></i></a>'+
								'<a class="blue" href="javascript:void(-1);" title="修改" onclick="editDialog(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
					   			if(row.status==1) {
									buttons+='<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> ';
								}else{
									buttons+='<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> ';
								};
		      				return buttons;
		            	   }else{
		            		   var buttons= '<div class="hidden-sm hidden-xs action-buttons">'+
								'<a class="blue" href="javascript:void(-1);" title="查看" onclick="openShow(this)"><i class="ace-icon fa fa-search  bigger-130"></i></a>'+
								'<a class="gray" href="javascript:void(-1);" title="修改" ><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
					   			if(row.status==1) {
									buttons+='<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> ';
								}else{
									buttons+='<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> ';
								};
		      				return buttons;
		            	   }
					   		
				   		}
		      		},
		      		{ "title": "被引用的数量", "class": "center","width":"80px","sortable": false,"data":"menuCount","visible":false }
		      		];
	scoreTypeTableSxxy = initTables("scoreTypeTableSxxy",basePath+"/cloudClass/scoreType/list",objData,true,true,2,null,searchCase,function(data){
			var iDisplayStart = data._iDisplayStart;
			var countNum = data._iRecordsTotal;//总条数
			pageSize = data._iDisplayLength;//每页显示条数
			currentPage = iDisplayStart / pageSize +1;//页码
			if(currentPage == 1){//第一页的第一行隐藏向上箭头
//				$("#cloudClassTable tbody tr:first td:last a:eq(4)").hide();
				$("#scoreTypeTableSxxy tbody tr:first td").eq(6).find('a').eq(0).css("pointer-events","none").removeClass("blue").addClass("gray");
			}
			if(countNum/pageSize < 1 || countNum/pageSize == 1){//数据不足一页隐藏下移箭头
//				$("#cloudClassTable tbody tr:last td:last a:eq(5)").hide();
				$("#scoreTypeTableSxxy tbody tr:last td").eq(6).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
			}
			var countPage;
			if(countNum%pageSize == 0){
				countPage = parseInt(countNum/pageSize);
			}else{
				countPage = parseInt(countNum/pageSize) + 1;
			}
			if(countPage == currentPage){//隐藏最后一条数据下移
//				$("#cloudClassTable tbody tr:last td:last a:eq(5)").hide();
				$("#scoreTypeTableSxxy tbody tr:last td").eq(6).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
			}
	});
	
	addForm = $("#add-form").validate({
		messages:{
			name: {
				required:"请输入课程类别名称！",
				minlength:"授课方式名称过短，应大于2个字符！"
			}
		}
	});

	updateForm= $("#update-form").validate({
		messages:{
			name: {
				required:"请输入课程类别名称！",
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
//		 ajaxRequest(basePath+"/cloudClass/scoreType/maxSort",null,function(result){
			  $("input[name='code']").removeAttr("disabled");//增加角色把代码框放开
			  addForm.resetForm();
			 // $("#remark").val(result.resultObject+1); 去掉之前sort的默认值
			  openDialog("roleDialog","dialogRoleDiv","新增课程类别",500,330,true,"提交",function(){
				  if($("#add-form").valid()){
					  mask();
					  $("#add-form").attr("action", "cloudClass/scoreType/add");
					  $("#add-form").ajaxSubmit(function(data){
						  unmask();
						  if(data.success){
							  $("#roleDialog").dialog("close");
							  layer.msg(data.errorMessage);
							  freshTable(scoreTypeTableSxxy);
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
      	  deleteAll('user/role/deletes',scoreTypeTableSxxy);
      });

      /* 修改 */
      $(".rolexg").click(function(){

      });
});


/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTableSxxy.fnGetData(oo);
	ajaxRequest(basePath+"/cloudClass/scoreType/updateStatus",{"id":aData.id,"status":aData.status},function(){
		freshTable(scoreTypeTableSxxy);
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


/**
 * 上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTableSxxy.fnGetData(oo);
	ajaxRequest(basePath+"/cloudClass/menu/scoreTypeUp",{"id":aData.id},function(res){
		if(res.success){
			freshTable(scoreTypeTableSxxy);
		}
	});
}

/**
 * 下移
 * @param obj
 */
function downMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTableSxxy.fnGetData(oo);
	ajaxRequest(basePath+"/cloudClass/menu/scoreTypeDown",{"id":aData.id},function(res){
		if(res.success){
			freshTable(scoreTypeTableSxxy);
		}
	});
}


function delDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTableSxxy.fnGetData(oo);
	showDelDialog(function(){
		mask();
		var url = "user/role/delete";
		ajaxRequest(url,{'id':aData.id},function(data){
			unmask();
			if(!data.success){
				alertInfo(data.errorMessage);
			}else{
				freshTable(scoreTypeTableSxxy);
			}
		});
	});
}

function editDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTableSxxy.fnGetData(oo); // get datarow
	$("#update_id").val(aData.id);
	$("#update_name").val(aData.name);
	$("#update_remark").val(aData.remark);
	openDialog("updateDialog","updateDialogDiv","修改课程类别",500,330,true,"提交",function(){
		if($("#update-form").valid()){
			mask();
			$("#update-form").attr("action", "cloudClass/scoreType/update");
			$("#update-form").ajaxSubmit(function(data){
				unmask();
				if(data.success){
					$("#updateDialog").dialog("close");
					console.log(data);
					layer.msg(data.errorMessage);
					freshTable(scoreTypeTableSxxy);
					
				}else{
					layer.msg(data.errorMessage);
				}
			});
		}
	});
}

function openShow(obj){
	var oo = $(obj).parent().parent().parent();
	var row = scoreTypeTableSxxy.fnGetData(oo); // get datarow
	$("#show_menuName").text(row.name);
	$("#show_createPerson").text(row.createPerson);
	$("#show_createTime").text(row.createTime);
	$("#show_status").text(row.status=="1"?"已启用":"已禁用");
	$("#show_remark").text(row.remark);
	var dialog = openDialogNoBtnName("previewCloudClasMenuDialog","previewCloudClasMenuDialogDiv","查看课程类别",555,350,false,"确定",null);
}


function openMenuManage(obj){
	var oo = $(obj).parent().parent().parent();
	var row = scoreTypeTableSxxy.fnGetData(oo); // get datarow
	$("#parentId").val(row.id);
	ajaxRequest(basePath+"/cloudClass/menu/childMenu",{'pid':row.id},function(data) {
		drawMenusPage(data,row.id);
		var dialog = openDialogNoBtnName("childMenuDialog", "childMenuDialogDiv", "设置", 555, 230, true, "确定", null);
	});
}



function cancelRow(trId){
	$(trId).hide();
}
function saveMenusRow(menus_name_id,pid){
	var menuName=$("#"+menus_name_id).val();
	if(menuName!=null&&menuName.length>0){
		ajaxRequest(basePath+"/cloudClass/menu/addChildren",{'menuName':menuName,'parentId':pid},function(data) {
			if(data.success){
				ajaxRequest(basePath+"/cloudClass/menu/childMenu",{'pid':pid},function(data) {
					drawMenusPage(data,pid);
				})
			}
		});
	}
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

function deleteBatch(){
	deleteAll(basePath+"/cloudClass/scoreType/deletes",scoreTypeTableSxxy);
}
