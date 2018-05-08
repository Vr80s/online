var objData; 
var feedbackForm;
var feedbackTable;
var searchCase = new Array();
var titleArray= new Array();
$(function(){
	objData = [
       //{ "title": createAllCheckBox(),"class":"center","data":"id","sortable":false,"width":"5%","mRender":function(data,display,row){
       // 		return createCheckBox(data);
       //}	},

       { "title": "序号", "class":"center","width":"80px","sortable":false,"data": 'rowId' },
       { "title": "意见编号", "class":"center","bVisible": false,"sortable":false,"data": 'id',"width":"80px"  },
       { "title": "用户编号", "class":"center","bVisible": false,"sortable":false,"data": 'userId',"width":"80px"  },
       { "title": "意见内容", "class":"center","sortable":false,"data": 'title' ,"mRender":function (data, display, row) {
       	// debugger
    		// titleArray[row.id]=data.replace(/[\r\n]/g,"");
            // return "<span class='titleSpan' id='"+row.id+"'></span>";//data.replace(/[\r\n]/g,"");
        	return "<span>"+row.context+"</span>";//data.replace(/[\r\n]/g,"");
       }},
       { "title": "创建时间", "class":"center","sortable":false,"data": 'createTimeStr' },
       { "title": "回复时间", "class":"center","sortable":false,"data": 'lastTimeStr' },
       { "title": "创建人", "class":"center","sortable":false,"data": 'userName' },
       // { "title": "回答状态", "class":"center","sortable":false,"data": 'answerStatus',"mRender":function (data, display, row) {
    	//    if(row.answerStatus != "1"){
    	// 	   return "未回答";
    	//    }else{
    	// 	   return "已回答";
    	//    }
       // }},
       { "sortable": false,"class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {
		   var replyBtn="<a style='visibility:hidden'><i class='ace-icon fa fa-comment-o bigger-130'></i></a>";
		   // if(row.answerStatus != '1' && row.userId !=null ){
			//    replyBtn="<a class='blue' href='javascript:void(-1);' title='回复' onclick='addContext(this)'><i class='ace-icon fa fa-comment-o bigger-130'></i></a>";
		   // }

    	   return '<div class="hidden-sm hidden-xs action-buttons">'+
		   '<a class="blue" href="javascript:void(-1);" title="查看" onclick="feedbackDialog(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
		   //'<a class="blue" href="javascript:void(-1);" title="'+(row.lastTime == '' ?"回复":"修改")+'" onclick="'+(row.lastTime == '' ?"addContext(this)":"updateContext(this)")+'"><i class="ace-icon fa '+(row.lastTime == '' ?"fa-comment-o":"fa-edit")+' bigger-130"></i></a>'+
		   // 去掉修改功能
		   replyBtn+
		   //'<a class="'+(row.isDelete == 'true' ?"green":"red")+'" href="javascript:void(-1);" title="'+(row.isDelete == 'true' ?"撤销删除":"删除")+'" onclick="updateStatus(this)"><i class="ace-icon fa '+(row.isDelete == 'true' ?"fa-check-square-o":"fa-trash-o")+' bigger-130"></i></a></div>';
		   '<a class="blue" href="javascript:void(-1);" title="'+(row.isDelete == 'true' ?"":"删除")+'" onclick="updateStatus(this)"><i class="ace-icon fa '+(row.isDelete == 'true' ?"":"fa-trash-o")+' bigger-130"></i></a></div>';
		}}];
	feedbackTable = initTables("feedbackTable","feedback/initFeedback",objData,true,true,1,null,searchCase,function(data){
    	$(".titleSpan").each(function(index){
    		$(this).text(titleArray[$(this).attr("id")]);
    	});
    	//赋值完成后清空
    	titleArray.length = 0;
    });//第2列作为序号列
	
	createDatePicker2($(".datetime-picker"),"yy-mm-dd");
});

/**
 * 查询
 */
function search(){
	var timeStart = $("#time_start").val();
	var timeEnd = $("#time_end").val();
	
	if(timeStart != "" || timeEnd != ""){
		if(timeEnd != "" && timeStart == ""){
			layer.msg("开始时间不能为空");
			return;
		}
		if(timeStart != "" && timeEnd == ""){
			layer.msg("结束时间不能为空");
			return;
		}
		if(timeStart > timeEnd){
			layer.msg("开始时间不能大于结束时间");
			return;
		}
		searchCase.push('{"tempMatchType":"7","propertyName":"time_start","propertyValue1":"'+timeStart+'","tempType":"String"}');
		searchCase.push('{"tempMatchType":"6","propertyName":"time_end","propertyValue1":"'+timeEnd+'","tempType":"String"}');
	}
	searchButton(feedbackTable,searchCase);
	searchCase.pop();
	searchCase.pop();
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
 * 删除or恢复
 * @param obj
 */
function updateStatus(obj){

	showDelDialog(function() {
		var oo = $(obj).parent().parent().parent();
		var aData = feedbackTable.fnGetData(oo);
		showDelDialog(function(){
			ajaxRequest("feedback/updateDelete",{"id":aData.id},function(res){
				layer.msg(res.errorMessage);
				mask();
				freshTable(feedbackTable);
				unmask();
			});
		});
	});
}

/**
 * 回复消息
 */
function addContext(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = feedbackTable.fnGetData(oo);
	$("#add_replytext").val("");
	$("#add_context").val(aData.context);
	$("#add_title").val(aData.title);
	$("#add_createtimeStr").val(aData.createTimeStr);
	$("#feedId").val(aData.id);
	$("#feedUserId").val(aData.userId);
	var dialog = openDialog("feedBackDialog","dialogfeedBackDiv","回复",450, 510,true,"确定",function(){
		if($("#addfeedBack-form").valid()){
			mask();
			var param = $("#addfeedBack-form").serialize();
			ajaxRequest(basePath+"/feedback/updateoradd",param,function(res){
				unmask();
				if(res.success){
					dialog.dialog("close");
				}
				ajaxCallback(res);
				//关闭后将框里的数据清除
				$("#add_replytext").html("");
				$("#add_context").html("");
			});
		}
		
	});
}

/**
 * 修改回复内容 
 */
function updateContext(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = feedbackTable.fnGetData(oo);
	$("#add_replytext").val(aData.replytext);
	$("#add_context").val(aData.context);
	$("#feedId").val(aData.id);
	var dialog = openDialog("feedBackDialog","dialogfeedBackDiv","修改",555, 510,true,"确定",function(){
		if($("#addfeedBack-form").valid()){
			mask();
			var param = $("#addfeedBack-form").serialize();
			ajaxRequest(basePath+"/feedback/updateoradd",param,function(res){
				unmask();
				if(res.success){
					dialog.dialog("close");
				}
				ajaxCallback(res);
				//关闭后将框里的数据清除
				$("#add_replytext").val("");
				$("#add_context").val("");
			});
		}
	
	});
}
/**
 * 查看
 * @param obj
 */
function feedbackDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = feedbackTable.fnGetData(oo);
	$("#show_title").val(aData.title);
	$("#show_createtimeStr").val(aData.createTimeStr);
	// aData.context = aData.context.replace('<font color="#2cb82c">意见反馈：</font>','');
	$("#show_context").val(aData.context);
	$("#show_statusStr").val(aData.answerStatus);
	ajaxRequest(basePath+"/feedback/findFeekBackByFeedId",{"feedId":aData.id},function(res) {
		var replytext="";
		var replytitle="";
		var replycreateTimeStr="";
		if(res.resultObject!=null){
			replytext=res.resultObject.context;
			replytitle=res.resultObject.title;
			replycreateTimeStr=res.resultObject.createTimeStr;
		}
		debugger;
        replytext = replytext.replace('<font color=\"#2cb82c\">意见反馈：</font>',"");
		$("#show_replytext").val(replytext);
		$("#show_replytitle").val(replytitle);
		$("#show_replycreateTimeStr").val(replycreateTimeStr);

		var dialog = openDialogNoBtnName("showfeedBackDialog","dialogfeedBackDiv","查看",450, 510,false,"确定",null);
	});
}

function ajaxCallback(res){
	if(res.success){
		freshTable(feedbackTable);
		layer.msg(res.errorMessage);
	}else{
		layer.msg(res.errorMessage);
	}
}

function anwserType(obj){
	var name = obj.options[obj.selectedIndex].text;
	if(name == "未回答"){
		$("#searchType").val("13");
	}else if(name == "已回答"){
		$("#searchType").val("14");
	}else{
		$("#searchType").val("4");
	}
}








