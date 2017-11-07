var teachMethodTable;
var roleForm;
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
	               { "title": "序号", "class": "center","width":"80px","sortable": false,"data":"id" },
	               { "title": "授课方式名称", "class":"center","sortable":false,"data": 'name' },
	               { "title": "创建人", "class":"center","sortable":false,"data": 'createPerson' },
	               { "title": "创建日期", "class":"center","sortable":false,"data": 'createTime'},
					{ "title": "状态", "class":"center","sortable":false,"data": 'status',"mRender":function (data, display, row) {
						return row.status=="1"?"已启用":"已禁用";
						}
					},
	               { "sortable": false,"data":"id","class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {
		            	   if(row.courseCount==0){
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
		      		{ "title": "被引用的数量", "class": "center","width":"80px","sortable": false,"data":"courseCount","visible":false }
		      		];
	teachMethodTable = initTables("teachMethodTable",basePath+"/cloudClass/teachMethod/list",objData,true,true,2,null,searchCase,function(data){
			var iDisplayStart = data._iDisplayStart;
			var countNum = data._iRecordsTotal;//总条数
			pageSize = data._iDisplayLength;//每页显示条数
			currentPage = iDisplayStart / pageSize +1;//页码
			if(currentPage == 1){//第一页的第一行隐藏向上箭头
				$("#teachMethodTable tbody tr:first td:last a:eq(4)").css("pointer-events","none").removeClass("blue").addClass("gray");
			}
			if(countNum/pageSize < 1 || countNum/pageSize == 1){//数据不足一页隐藏下移箭头
				$("#teachMethodTable tbody tr:last td:last a:eq(5)").css("pointer-events","none").removeClass("blue").addClass("gray");
			}
			var countPage;
			if(countNum%pageSize == 0){
				countPage = parseInt(countNum/pageSize);
			}else{
				countPage = parseInt(countNum/pageSize) + 1;
			}
			if(countPage == currentPage){//隐藏最后一条数据下移
				$("#teachMethodTable tbody tr:last td:last a:eq(5)").css("pointer-events","none").removeClass("blue").addClass("gray");
			}
	});
	addForm = $("#add-form").validate({
		messages:{
			name:{
				required:"请输入授课方式名称！",
				minlength:"授课方式名称过短，应等于2个字符！"
			}
		}
	});

	updateForm = $("#update-form").validate({
		messages:{
			name:{
				required:"请输入授课方式名称！",
				minlength:"授课方式名称过短，应等于2个字符！"
			}
		}
	});

	//下线时间 时间控件
	createDatetimePicker3($(".datetime-picker"),"yy-mm-dd");
});

$(function(){

	 /* 添加 */
      $("#add_button").click(function(){
//		 ajaxRequest(basePath+"/cloudClass/scoreType/maxSort",null,function(result){
			  $("input[name='code']").removeAttr("disabled");//增加角色把代码框放开
			  addForm.resetForm();
			 // $("#remark").val(result.resultObject+1); 去掉之前sort的默认值
			  openDialog("roleDialog","dialogRoleDiv","新增授课方式",500,330,true,"提交",function(){
				  if($("#add-form").valid()){
					  mask();
					  $("#add-form").attr("action", "cloudClass/teachMethod/add");
					  $("#add-form").ajaxSubmit(function(data){
						  unmask();
						  if(data.success){
							  $("#roleDialog").dialog("close");
							  layer.msg(data.errorMessage);
							  freshTable(teachMethodTable);
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
      	  deleteAll('user/role/deletes',teachMethodTable);
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
	var aData = teachMethodTable.fnGetData(oo);
	ajaxRequest(basePath+"/cloudClass/teachMethod/updateStatus",{"id":aData.id,"status":aData.status},function(){
		freshTable(teachMethodTable);
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
	var aData = teachMethodTable.fnGetData(oo);
	showDelDialog(function(){
		mask();
		var url = "user/role/delete";
		ajaxRequest(url,{'id':aData.id},function(data){
			unmask();
			if(!data.success){
				layer.msg(data.errorMessage);
			}else{
				freshTable(teachMethodTable);
			}
		});
	});
}

function editDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = teachMethodTable.fnGetData(oo); // get datarow
	$("#update_id").val(aData.id);
	$("#update_name").val(aData.name);
	$("#update_remark").val(aData.remark);
	openDialog("updateDialog","updateDialogDiv","修改授课方式",500,350,true,"提交",function(){
		if($("#update-form").valid()){
			mask();
			$("#update-form").attr("action", "cloudClass/teachMethod/update");
			$("#update-form").ajaxSubmit(function(data){
				unmask();
				if(data.success){
					$("#updateDialog").dialog("close");
					layer.msg(data.errorMessage);
					freshTable(teachMethodTable);
				}else{
					layer.msg(data.errorMessage);
				}
			});
		}
	});
}

function openShow(obj){
	var oo = $(obj).parent().parent().parent();
	var row = teachMethodTable.fnGetData(oo); // get datarow
	$("#show_name").text(row.name);
	$("#show_createPerson").text(row.createPerson);
	
	$("#show_createTime").text(row.createTime.substring(0,10));
	$("#show_status").text(row.status=="1"?"已启用":"已禁用");
	$("#show_remark").text(row.remark);
	var dialog = openDialogNoBtnName("previewCloudClasMenuDialog","previewCloudClasMenuDialogDiv","查看授课方式",555,350,false,"确定",null);
}


//function openMenuManage(obj){
//	var oo = $(obj).parent().parent().parent();
//	var row = teachMethodTable.fnGetData(oo); // get datarow
//	$("#parentId").val(row.id);
//	ajaxRequest(basePath+"/cloudClass/menu/childMenu",{'pid':row.id},function(data) {
//		drawMenusPage(data,row.id);
//		var dialog = openDialogNoBtnName("childMenuDialog", "childMenuDialogDiv", "设置", 555, 230, true, "确定", null);
//	});
//}



function cancelRow(trId){
	$(trId).hide();
}
//function saveMenusRow(menus_name_id,pid){
//	var menuName=$("#"+menus_name_id).val();
//	if(menuName!=null&&menuName.length>0){
//		ajaxRequest(basePath+"/cloudClass/menu/addChildren",{'menuName':menuName,'parentId':pid},function(data) {
//			if(data.success){
//				ajaxRequest(basePath+"/cloudClass/menu/childMenu",{'pid':pid},function(data) {
//					drawMenusPage(data,pid);
//				})
//			}
//		});
//	}
//}
function appendRow(pid){
	$("#childMenus").append("<tr id='tr_"+seed+"'><td><input type='text' id='menus_name_"+seed+"' />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='button' value='保存' onclick='saveMenusRow(\"menus_name_"+seed+"\",\""+pid+"\")'/>&nbsp;&nbsp;&nbsp;<input type='button' value='取消' onclick='cancelRow(tr_"+seed+")'/></td></tr>");
	seed++;
}


function deleteBatch(){
	deleteAll(basePath+"/cloudClass/teachMethod/deletes",teachMethodTable);
}


//配置角色权限
function configResource(obj){ 
	var oo = $(obj).parent().parent().parent();
	var aData = teachMethodTable.fnGetData(oo); // get datarow
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

/**
* 日期格式化yyyy-MM-dd HH:mm:ss
*/
function FormatDate (strTime) {
	var year = strTime.getFullYear();
	var month = getFormat(strTime.getMonth()+1);
	var day = getFormat(strTime.getDate());
	var hours = getFormatHMS(strTime.getHours());
	var minutes = getFormatHMS(strTime.getMinutes());
	var seconds = getFormatHMS(strTime.getSeconds());
    return year+"-"+month+"-"+day ; 
}
function getFormat(time){
	if(time >= 1 && time < 9){
		time = "0"+time;
	}
	return time;
}
function getFormatHMS(time){
	if(time >= 0 && time < 9){
		time = "0"+time;
	}
	return time;
}