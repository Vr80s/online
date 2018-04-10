var onlineuserTable;
var onlineuserForm;
$(function() {

/*	createDatePicker($('#createTimeStart'));
	createDatePicker($('#createTimeEnd'));*/
	
	createDatePicker($('#subscribeTimeStart'));
	createDatePicker($('#subscribeTimeEnd'));

	var objData = [{
		"title" : "序号",
		"width" : "6%",
		"class" : "center",
		"sortable" : false,
		"data" : "id"
	},{
		"title" : "微信昵称",
		"width" : "12%",
		"class" : "center",
		"sortable" : false,
		"data" : 'nickname'
	},{
		"title" : "用户名",
		"width" : "12%",
		"class" : "center",
		"sortable" : false,
		"data" : 'loginName'
	}
	,{
		"title" : "渠道名称",
		"width" : "12%",
		"class" : "center",
		"sortable" : false,
		"data" : 'channelName'
	},{
		"width" : "6%",
		"title" : "性别",
		"class" : "center",
		"sortable" : false,
		"data" : 'sex',
		"mRender":function (data, display, row){
        	return getSex(data);
        }
	},{
		"title" : "所在城市",
		"width" : "10%",
		"class" : "center",
		"sortable" : false,
		"data" : 'city'
	},{
		"title" : "是否取消关注",
		"width" : "10%",
		"class" : "center",
		"sortable" : false,
		"data" : 'subscribe',
		"mRender":function (data, display, row){
			if(null==data || data == "1"){
				return "已关注";
			}else{
				return "已取消关注";
			}
        }
	},{
		"title" : "关注时间",
		"width" : "9%",
		"class" : "center",
		"sortable" : false,
		"data" : 'subscribeTime',
		"mRender":function (data, display, row){
			
			return getDateTimeFormat(data);
        }
	},{
		"title" : "最后更新时间",
		"class" : "center",
		"width" : "12%",
		"sortable" : false,
		"data" : 'lastUpdateTime'
	},{
		"sortable" : false,
		"data" : "id",
		"class" : "center",
		"width" : "12%",
		"title" : "操作",
		"mRender" : function(data, display, row) {
			var str = '<div class="hidden-sm hidden-xs action-buttons">'
				+ '<a class="blue" href="javascript:void(-1);" title="查看" onclick="viewUserDialog(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>';
			
			
/*			if(row.isLecturer == 0){
				str +=  '<a class="blue" href="javascript:void(-1);" title="设置为讲师" onclick="setUserLecturer(this,1)"><i class="ace-icon fa fa-cog bigger-130"></i></a>';
			}else{
				str += '<a class="blue" href="javascript:void(-1);" title="取消讲师资格" onclick="setUserLecturer(this,0)"><i class="ace-icon fa fa-cog bigger-130"></i></a>';
			}*/
		 
//			if(row.status == 0){
//				str+='<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this,-1);"><i class="ace-icon fa fa-ban bigger-130"></i></a>';
//			}else {
//				str+='<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this,0);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a>';
//			}
			debugger;
			// str += '<a class="blue" href="javascript:void(-1);" title="编辑个人简介" onclick="showDetailDialog(\''+row.id+'\');"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>';
//			str+='<a class="blue" href="javascript:void(-1);" title="设置学科权限" onclick="OpenMenuDailg(this);"><i class="glyphicon glyphicon-wrench"></i></a></div>';
			return str;
		}
	} ];
	onlineuserTable = initTables("onlineuserTable", basePath + "/wechatuser/list", objData, true, true, 1);
	onlineuserForm = $("#onlineuser-form").validate({});
});

function getDateTimeFormat(data){
	//微信性别  --》 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	if(data && data != ''){
		var d = new Date(data);
		return d.format('yyyy-M-d hh:mm:ss');
	}
}


function getSex(sex){
	//微信性别  --》 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	if (sex == 0) {
		return '未知';
	} else if (sex == 1){
		return '男';
	} else if (sex == 2){
		return '女';
	}
}

function viewUserDialog(obj) {
	console.log(obj);
	$('#userInfoDialog').find('.paddingtop7px').html('');
	var oo = $(obj).parent().parent().parent();
	var aData = onlineuserTable.fnGetData(oo); // get datarow
	$("#name_look").html(aData.nickname);
	$("#sex_look").html(getSex(aData.sex));
	
	$("#mobile_look").html(aData.loginName);
	$("#vhallId_look").html(aData.channelName);
	
	$("#balanceGive_look").html(aData.province + "__"+aData.city+"__"+aData.country);
	$("#balance_look").html(getDateTimeFormat(aData.subscribeTime));
	$("#last_time_look").html(getDateTimeFormat(aData.lastUpdateTime));
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