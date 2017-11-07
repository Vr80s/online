var questionTable;
var searchCase = new Array();
var dialog;
$(function(){
	var objData = [
	            
	            { "title": "序号", "class":"center","width":"80px","sortable":false,"data": 'id' },
	            { "title": "消息内容", "class":"center","width":"350px","sortable":false,"data": 'context',"mRender":function(data,display,row){
	            	try{
	            		return data.replace(/<[^>]+>/g,"");
	            	}catch (e){
	            		return data;
	            	}
	            
				} },//return data.replace(/<[^>]+>/g,"");
				{ "title": "发送状态", "class":"center","sortable":false,"data": 'statusStr',"mRender":function(data,display,row){
						return "已发送";
					}
				},
				{ "title": "接收用户", "class":"center","sortable":false,"data": 'grade' ,"mRender":function(data,display,row){
					if(data!=-1){
						return data;
					}else{
						if(row.course!=-1){
							return row.course;
						}else if(row.subject!=-1){
							return row.subject;
						}else{
							return "全体用户";
						}
					}
				}},
//		        { "title": "创建人", "class":"center","sortable":false,"data": 'createPerson' },
			{ "title": "发送时间", "class":"center","sortable":false,"data": 'createTimeStr' },
			{ "title": "发送人数", "class":"center","sortable":false,"data": 'userCount'},
		        { "sortable": false,"class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {
				    return '<div class="hidden-sm hidden-xs action-buttons">'+
				    '<a class="blue" href="javascript:void(-1);" title="查看" onclick="openPreviewMessageDialog(this)"><i class="ace-icon fa fa-search  bigger-130"></i></a>'
				    /*+'<a class="blue"  href="javascript:void(-1);" title="删除" onclick="deleteRow(this)">    <i class="ace-icon fa fa-trash-o bigger-130"></i></a></div>';*/
				}}
		    ];
	//questionTable = initTables("questionTable",basePath+"/h_manage/load/video",objData,true,true,2);//第2列作为序号列
	//默认打开页面时,不会显示对应的视频信息,只有在点击对应菜单之后才会显示
	questionTable = initTables("message_questionTable",basePath+"/message/load/messages",objData,true,true,1);//第2列作为序号列
	

	//增加按钮事件
	$("#add_subject").click(function(){
		addDialog();
	});
	
	//增加字数统计
	$("#description").on("keyup",function(){
		textarealength($(this));
	});

	//初始化时间控件
	createDatePicker2($(".datetime-picker"),"yy-mm-dd");

	//新增框
	$(".add_bx").click(function(){
		message_user_select_id = -1;
	    $('#message_user_select').val("-1");
		$("#message_title").val("");
		$("#message_content").html("");
		$("#message_content_msg").html("");
		$("#addmessage-form :input").not(":button, :submit, :radio").val("").removeAttr("checked").remove("selected");//核心
		$('#grade').selectpicker('deselectAll');
		//$('#grade').multiselect();
		dialog = openDialog("addMessageDialog","addMessageDialogDiv","推送消息",720,580,true,"发送",function(){
			if($("#addmessage-form").valid()) {
				if(saveMessage()){
					
				}
			}
		});
		$(".error").html("");
	});

});

/**
 * 打开消息推送窗口
 */
function openAddMessageDialog(){

	$("#message_content_msg").html("");
	$("#sendToUser").val(message_user_select_text);
}
/**
 * 打开消息推送窗口
 */
function openPreviewMessageDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var row = questionTable.fnGetData(oo); // get datarow
	if(row.grade!=-1){
		$("#sendToUser_preview").val(row.grade);
	}else{
		if(row.course!=-1){
			$("#sendToUser_preview").val(row.course);
		}else if(row.subject!=-1){
			$("#sendToUser_preview").val(row.subject);
		}else{
			$("#sendToUser_preview").val("全体用户");
		}
	}
	//$("#sendToUser_preview").val(row.loginName);
	//$("#message_title_preview").val(row.title);
	$("#message_createPerson_preview").val(row.createPerson);
	$("#message_statusStr_preview").val(1);
	$("#message_content_preview").html(row.context);
	$("#message_userCount_preview").val(row.userCount);
	$("#message_content_preview").attr("contenteditable","false");
	var dialog = openDialogNoBtnName("previewMessageDialog","previewMessageDialogDiv","消息查看",555,600,false,"确定",null);
}

function deleteBatch(){
	deleteAll("message/deleteBatch",questionTable);
}

//单条删除
function deleteRow(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = questionTable.fnGetData(oo);
	showDelDialog(function(){
		ajaxRequest(basePath+"/message/delete/"+aData.id,null,function(res){
			if(res.success){
				freshTable(questionTable);
			}
			//layerAlterInfo(res.errorMessage);
		});
	});
}
function search(){
	var timeStart = $("#time_start").val();
	var timeEnd = $("#time_end").val();
	
	if(timeStart > timeEnd && timeEnd != ""){
		layer.msg("开始时间不能大于结束时间");
		return;
	}else{
		searchCase.push('{"tempMatchType":"5","propertyName":"q_name","propertyValue1":"'+$("#q_name").val()+'","tempType":"String"}');
		searchCase.push('{"tempMatchType":"5","propertyName":"q_title","propertyValue1":"'+$("#q_title").val()+'","tempType":"String"}');
		searchCase.push('{"tempMatchType":"7","propertyName":"time_start","propertyValue1":"'+$("#time_start").val()+'","tempType":"String"}');
		searchCase.push('{"tempMatchType":"6","propertyName":"time_end","propertyValue1":"'+ $("#time_end").val()+'","tempType":"String"}');
		searchButton(questionTable,searchCase);
		searchCase.pop();
		searchCase.pop();
		searchCase.pop();
		searchCase.pop();
	}
}
//回车查询事件
document.onkeydown=keyListener;
function keyListener(e){
	e = e ? e : event;
	if(e.keyCode == 13){
		document.getElementById("searchBtn").click();
	}
}
/**
 * 保存推送消息
 */
var result = true;
function saveMessage(){
//	debugger
	var content=$("#message_content").html();
	
	var subject=$("#subject").val();
	var course=$("#course").val();
	if(course==null||course==""){
		course=-1
	}
	if(subject==null||subject==""){
		subject=-1
	}
	if($("#grade").val()!=null){
		var grade=$("#grade").val().join(",");
	}else{
		var grade=-1;
	}
	
	var subjectName='-1';
	var courseName='-1';
	var gradeName='';
	if(subject!=-1){
		subjectName=$("#subject").find("option:selected").text();
	}
	if(course!=-1){
		courseName=$("#course").find("option:selected").text();
	}
	
	if($("#grade").val()!=null){
		var grades=$("#grade").find("option:selected");
		
	    for(i=0;i<grades.length;i++){
	      //gradeName.push(grades[i].text);
	      gradeName = grades[i].text+","+gradeName;
	    }
	    gradeName= gradeName.substring(0,gradeName.length-1)
	}else{
		gradeName='-1'
	}
	
	
	if(content==null||content.length==0||content==""){
		$("#message_content_msg").html("消息内容不能为空");
		return false;
	}
	if($("#message_content").text().length>50){
		$("#message_content_msg").html("消息内容最长为50字");
		return false;
	}
	syncRequest(basePath+"/message/save", {"subject":subject,"course":course,"grade":grade,"subjectName":subjectName,"courseName":courseName,"gradeName":gradeName,"context":content}, function (data) {
		if(data.success==true){
			$('#message_questionTable').DataTable().ajax.reload();
			dialog.dialog("close");
		}else{
			result = false;
			alert(data.errorMessage);
		}
	});
	return result;
}
