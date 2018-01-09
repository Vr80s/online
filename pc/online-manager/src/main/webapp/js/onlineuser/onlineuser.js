var onlineuserTable;
var onlineuserForm;
$(function() {

	createDatePicker($('#createTimeStart'));
	createDatePicker($('#createTimeEnd'));
	createDatePicker($('#lastLoginTimeStart'));
	createDatePicker($('#lastLoginTimeEnd'));

	var objData = [{
		"title" : "序号",
		"width" : "6%",
		"class" : "center",
		"sortable" : false,
		"data" : "id"
	},{
		"title" : "用户名",
		"width" : "12%",
		"class" : "center",
		"sortable" : false,
		"data" : 'name'
	},{
		"title" : "账号",
		"width" : "12%",
		"class" : "center",
		"sortable" : false,
		"data" : 'loginName'
	},{
		"width" : "6%",
		"title" : "性别",
		"class" : "center",
		"sortable" : false,
		"data" : 'sex',
		"visible":false,
		"mRender":function (data, display, row){
        	return getSex(data);
        }
	}
	
	,{
		"title" : "手机号",
		"width" : "10%",
		"class" : "center",
		"sortable" : false,
		"data" : 'mobile',
		"visible":false
	},{
		"title" : "QQ号",
		"width" : "9%",
		"class" : "center",
		"sortable" : false,
		"data" : 'qq',
		"visible":false
	},{
		"title" : "邮箱",
		"class" : "center",
		"width" : "12%",
		"sortable" : false,
		"data" : 'email',
		"visible":false
	}
	,{
		"title" : "登陆IP",
		"class" : "center",
		"width" : "8%",
		"sortable" : false,
		"data" : 'lastLoginIp'
	},{
		"title" : "访问次数",
		"class" : "center",
		"width" : "8%",
		"sortable" : false,
		"data" : 'visitSum'
	},{
		"title" : "注册时间",
		"width" : "8%",
		"class" : "center",
		"sortable" : false,
		"data" : 'createTime',
		"mRender":function (data, display, row){
			
			return data;
			/*if(data && data != ''){
				var d = new Date(data);
				return d.format('yyyy-M-d');
			}*/
        }
	},{
		"title" : "停留时长",
		"class" : "center",
		"width" : "8%",
		"sortable" : false,
		"data" : 'stayTime',
		"mRender" : function(data, display, row) {
			if(data || data == 0){
				return data +'s';
			}
		}
	},{ 
		"title" : "最后登录时间",
		"class" : "center",
		"sortable" : false,
		"width" : "10%",
		"data" : 'lastLoginDate',
		"mRender":function (data, display, row){
//			if(data && data != ''){
//				var d = new Date(data);
//				return d.format('yyyy-M-d hh:mm:ss');
//			}
			return data;
        }
	},{
		"title" : "充值余额",
		"class" : "center",
		"width" : "8%",
		"sortable" : false,
		"data" : 'balance'
	},{
            "title" : "赠送余额",
            "class" : "center",
            "width" : "8%",
            "sortable" : false,
            "data" : 'balanceGive'
        },{
            "title" : "微吼账号",
            "class" : "center",
            "width" : "8%",
            "sortable" : false,
            "data" : 'vhallId'
        },{
		"title" : "启/禁用状态",
		"class" : "center",
		"sortable" : false,
		"width" : "10%",
		"data" : 'status',
		"mRender" : function(data, display, row) {
			if (row.status == 0) {
				return "已启用";
			} /*else if (row.status == -1) {
				return "已禁用";
			} */else {
				return "已禁用";
			}
		}
	},{
		"title" : "讲师权限",
		"class" : "center",
		"sortable" : false,
		"width" : "10%",
		"data" : 'status',
		"mRender" : function(data, display, row) {
			if (row.isLecturer == 1) {
				return "有权限";
			} else {
				return "无权限";
			}
		}
//	},{
//		"title" : "学科权限状态",//menuId
//		"class" : "center",
//		"sortable" : false,
//		"width" : "10%",
//		"data" : 'menuName',
//		"mRender" : function(data, display, row) {
//			return  row.menuName==null?"无权限":row.menuName; ;
//		}
	},{
		"title" : "学科权限状态",//menuId
		"class" : "center",
		"sortable" : false,
		"width" : "7%",
		"data" : 'menuId',
		"visible":false 
		
	},{
		"sortable" : false,
		"data" : "id",
		"class" : "center",
		"width" : "12%",
		"title" : "操作",
		"mRender" : function(data, display, row) {
			var str = '<div class="hidden-sm hidden-xs action-buttons">'
				+ '<a class="blue" href="javascript:void(-1);" title="查看" onclick="viewUserDialog(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>';
			
			/*if(row.isLecturer == 0){
				str +=  '<a class="blue" href="javascript:void(-1);" title="设置为讲师" onclick="setUserLecturer(this,1)"><i class="ace-icon fa fa-cog bigger-130"></i></a>';
			}else{
				str += '<a class="blue" href="javascript:void(-1);" title="取消讲师资格" onclick="setUserLecturer(this,0)"><i class="ace-icon fa fa-cog bigger-130"></i></a>';
			}*/
		 
			if(row.status == 0){
				str+='<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this,-1);"><i class="ace-icon fa fa-ban bigger-130"></i></a>';
			}else {
				str+='<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this,0);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a>';
			}
			debugger;
			str += '<a class="blue" href="javascript:void(-1);" title="编辑个人简介" onclick="showDetailDialog(\''+row.id+'\');"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>';
//			str+='<a class="blue" href="javascript:void(-1);" title="设置学科权限" onclick="OpenMenuDailg(this);"><i class="glyphicon glyphicon-wrench"></i></a></div>';
			return str;
		}
	} ];
	onlineuserTable = initTables("onlineuserTable", basePath + "/onlineuser/list", objData, true, true, 1);
	onlineuserForm = $("#onlineuser-form").validate({});
});

function getSex(sex){
	if (sex == 2) {
		return '未知';
	} else if (sex == 1){
		return '男';
	} else {
		return '女';
	}
}

function viewUserDialog(obj) {
	console.log(obj);
	$('#userInfoDialog').find('.paddingtop7px').html('');
	var oo = $(obj).parent().parent().parent();
	var aData = onlineuserTable.fnGetData(oo); // get datarow
	$("#name_look").html(aData.name);
	$("#sex_look").html(getSex(aData.sex));
	$("#mobile_look").html(aData.mobile);
	$("#qq_look").html(aData.qq);
	$("#email_look").html(aData.email);
	$("#ip_look").html(aData.lastLoginIp);
	$("#visitSum_look").html(aData.visitSum);
	$("#gradeName_look").html(aData.gradeName);
	$("#view_room_number").html(aData.roomNumber);
	
	$("#account_look").html(aData.loginName);
	$("#balance_look").html(aData.balance);
	$("#balanceGive_look").html(aData.balanceGive);
	$("#vhallId_look").html(aData.vhallId);
	$("#last_time_look").html(aData.lastLoginDate);
	$("#regis__time_look").html(aData.createTime);
	//view_room_number
	//$("#isPay_look").html(aData.isPay==1?'已付费':'未付费');
	var dialog = openDialogNoBtnName("userInfoDialog","dialogUserInfoDiv","查看用户",600,400,false,"确定",null);
	//var dialog = openDialog("userInfoDialog", "dialogUserInfoDiv", "查看用户", 543,400, false);
	/*dialog.find("#close").click(function() {
		dialog.dialog("close");
	});*/
}



/**
 * 设置用户为讲师
 */
function setUserLecturer(obj,op){
	
	var oo = $(obj).parent().parent().parent();
	var aData = onlineuserTable.fnGetData(oo); // get datarow
	
	var loginName  = aData.loginName;//用户id
	ajaxRequest('onlineuser/updateUserLecturer', {
		'loginName' : loginName,
		'lecturerStatus' : op
	}, function(data) {
		console.log(data);
		console.log("====="+data.resultObject.resultObject);
		if (data.success) {
			freshTable(onlineuserTable);
			if(op==1){
				var str ="设置该用户的讲师职位成功";
			/*	$("#room_number").html("房间号:"+data.resultObject.resultObject);
				var dialog = openDialogNoBtnName("userRoomNumberDialog","viewRoomNumber",str,300,200,false,"确定",null);*/
				alertInfo("设置该用户的讲师职位成功<br>房间号："+data.resultObject.resultObject);
			}else{
				alertInfo("取消该用户的讲师职位成功");
			}
		} else {
			alertInfo(data.errorMessage);
		}
	});
}


function updateStatus(obj,op) {
	var oo = $(obj).parent().parent().parent();
	var aData = onlineuserTable.fnGetData(oo);
	ajaxRequest('onlineuser/updateUserStatus', {
		'loginName' : aData.loginName,
		'status' : op
	}, function(data) {
		if (data.success) {
			freshTable(onlineuserTable);
		} else {
			alertInfo(data.errorMessage);
		}
	});
}

function search() {
	searchButton(onlineuserTable);
}
function reset(){
	$('#searchName').val('');
	$('#lastLoginIp').val('');
	$('#createTimeStart').val('');
	$('#createTimeEnd').val('');
	$('#lastLoginTimeStart').val('');
	$('#lastLoginTimeEnd').val('');
	$('#status').val('');
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

function OpenMenuDailg(obj){
	  $("#menu-form").resetForm();
	  var oo = $(obj).parent().parent().parent();
	  var aData = onlineuserTable.fnGetData(oo);
	  $("#id").val(aData.id);
		//所属学科
  	for(i=0;i<$("#menuId option").length;i++){
  		if($("#menuId option").eq(i).text()==aData.menuName){
  			$("#menuId option").eq(i).attr("select","selected"); 
  			$("#menuId").val($("#menuId option").eq(i).val());
  		}
  	}
	  openDialog("menuDialog","dialogMenuDiv","设置学科权限",500,230,true,"提交",function(){
		  if($("#menu-form").valid()){
			  mask();
			  $("#menu-form").attr("action",basePath+"/onlineuser/setMenu");
			  $("#menu-form").ajaxSubmit(function(data){
				  unmask();
				  if(data.success){
					  $("#menuDialog").dialog("close");
					  layer.msg(data.resultObject);
					  freshTable(onlineuserTable);
				  }else{
					  layer.msg(data.errorMessage);
				  }
			  });
		  }
	  });
}

function showDetailDialog(userId){
//	var oo = $(obj).parent().parent().parent();
//	var aData,page;
//	if(status==1) {
//		aData = P_courseTable.fnGetData(oo); // get datarow
//        page = getCurrentPageNo(P_courseTable);
//	}else{
//		aData = M_courseTable.fnGetData(oo); // get datarow
//        page = getCurrentPageNo(M_courseTable);
//	}
	window.location.href=basePath+'/home#onlineuser/editUserDescription?userId='+userId;
}