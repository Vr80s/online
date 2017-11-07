var noticeTable;
var noticeForm;
var noticeFormEdit;
var searchJson = new Array();
var nowTime;
$(function() {
	document.onkeydown=function(event){

	}
	nowTime=show();
    loadNoticeList();
});

//列表展示
function loadNoticeList(){
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /><span class="lbl"></span>';
    var dataFields = [
		{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
		    return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
		}}, 
        {title: '序号', "class": "center", "width": "7%","data": 'id',datafield: 'xuhao', "sortable": false},
        {title: '公告内容', "class": "center", "width": "45%","data": 'noticeContent', "sortable": false,"mRender":function(data,display,row){
        	return data.replace(/<[^>]+>/g,"");
        }},
        {title: '展示时间段', "class": "center", "width": "20%","data": 'showStartTime', "sortable": false,"mRender":function(data,display,row){
    		var dayCount;
        	if(row.showStartTime==null&&row.showEndTime==null){//新添加
        		return null
        	}else if(row.showStartTime!=null&&row.showEndTime==null){
        		dayCount=dateDiff(row.showStartTime,nowTime);
        		return dayCount+"天</br>("+row.showStartTime+"至"+nowTime+")"
        	}else if(row.showStartTime!=null&&row.showEndTime!=null){
        		dayCount=dateDiff(row.showStartTime,row.showEndTime);
        		return dayCount+"天</br>("+row.showStartTime+"至"+row.showEndTime+")"
        	}else{
        		return "数据异常";
        	}
    		
        }},
        {title: '创建人', "class": "center", "width": "10%","data": 'createPersonName', "sortable": false},
        {title: '状态', "class": "center", "width": "10%","data": 'status', "sortable": false,"mRender":function(data,display,row){
        	var status ;
        	if(data == 0){
        		status = "未发布";
            }else if(data == 1){
            	status = "已发布";
            }else if(data == 2){
            	status = "已下线";
            }
        	return status;
        }},
        {title:"操作","class": "center","width":"13%","height":"34px","data":"id","sortable": false,"mRender":function (data, display, row) {

                var buttons= '<div class="hidden-sm hidden-xs action-buttons">';
	   			if(row.status==0 || row.status==2) {
					buttons+=' <a class="blue" href="javascript:void(-1);" title="发布" onclick="updateStatus(this,1);">发布</a>'+
					         ' <a class="blue" href="javascript:void(-1);" title="修改" onclick="updateNotice(this)">修改</a></div>';
				}else{
					buttons+=' <a class="blue" href="javascript:void(-1);" title="下线" onclick="updateStatus(this,2);">下线</a>'+
							 ' <a class="gray" href="javascript:void(-1);" title="修改" style="cursor:not-allowed" >修改</a></div>';
				}
				buttons += '';
                return buttons;
            }
        }
    ];

    noticeTable = initTables("noticeTable", basePath + "/operate/notice/findNoticeList", dataFields, true, true, 2,null,searchJson,function(data){
    });

    noticeForm = $("#addNotice-form").validate({
        messages: {
        	noticeContent: {
				required:"公告内容不能为空！",
				maxlength:"最大长度不能超过50！"
            }
        }
    });

    noticeFormEdit = $("#updateNotice-form").validate({
        messages: {
        	noticeContent: {
				required:"公告内容不能为空！",
				maxlength:"最大长度不能超过50！"
            }
        }
    });
    $(".wysiwyg-editor").ace_wysiwyg({
		toolbar : [ null,
				     {name : 'createLink', className : 'btn-info'}, 
				     {name : 'unlink', className : 'btn-info'}, null,'foreColor',null],
		'wysiwyg' : {
			fileUploadError : showErrorAlert
		},
		uploadType:{type:'url',action:basePath+'/cloudclass/course/uploadImg'}//图片上传方式，url/base64
	}).prev().addClass('wysiwyg-style2').addClass('wysiwyg-style3');
}

 //条件搜索
 function search(){
     searchButton(noticeTable,searchJson);
}

 //新增框
 $(".add_bx").click(function(){
 	noticeForm.resetForm();
 	$('#notice_content').text("");
 	$('#noticeContent').val("");
 	$("#wordCnt").text(0);
 	var dialog = openDialog("addNoticeDialog","dialogAddNoticeDiv","新增公告",720,480,true,"确定",function(){
 		$('#noticeContent').val($('#notice_content').text());//先验证清除格式长度
 		if($("#addNotice-form").valid()){
 			//通过以后重新赋完整的值
 			 $('#noticeContent').val($('#notice_content').html());//先验证清除格式长度
 			 mask();
 			 $("#addNotice-form").attr("action", basePath+"/operate/notice/addNotice");
 	            $("#addNotice-form").ajaxSubmit(function(data){
 	            	try{
                 		data = jQuery.parseJSON(jQuery(data).text());
                 	}catch(e) {
                 		data = data;
                 	}
 	                unmask();
 	                if(data.success){
 	                    $("#addNoticeDialog").dialog("close");
 	                    layer.msg(data.resultObject);
 	                    freshTable(noticeTable);
 	                }else{
 	                	layer.msg(data.errorMessage);
 	               }
 	         });
 		}
 	});
 });

function updateNotice(obj){
	var oo = $(obj).parent().parent().parent();
	var row = noticeTable.fnGetData(oo);
	noticeFormEdit.resetForm();
	
	$("#update_id").val(row.id);
	$('#update_noticeContent').val(row.noticeContent);
	$("#update_notice_content").html(row.noticeContent);
	
	var arr = $('#update_notice_content').text().split("");
	$("#wordCnt2").text(arr.length);
	
 	var dialog = openDialog("updateNoticeDialog","dialogUpdateNoticeDiv","修改公告",720,480,true,"确定",function(){
 		$('#update_noticeContent').val($('#update_notice_content').text());//先验证清除格式长度
 		if($("#updateNotice-form").valid()){
 			$('#update_noticeContent').val($('#update_notice_content').html());
 			 mask();
 			 $("#updateNotice-form").attr("action", basePath+"/operate/notice/updateNoticeById");
 	            $("#updateNotice-form").ajaxSubmit(function(data){
 	            	try{
                 		data = jQuery.parseJSON(jQuery(data).text());
                 	}catch(e) {
                 		data = data;
                 	}
 	                unmask();
 	                if(data.success){
 	                    $("#updateNoticeDialog").dialog("close");
 	                    layer.msg(data.resultObject);
 	                    freshTable(noticeTable);
 	                }else{
 	                	layer.msg(data.errorMessage);
 	               }
 	         });
 		}
 	});
}

/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj,status){
	var oo = $(obj).parent().parent().parent();
	var row = noticeTable.fnGetData(oo); // get datarow
	ajaxRequest(basePath+"/operate/notice/updateStatus",{"id":row.id,"status":status},function(data){
		if(data.success){
			layer.msg(data.resultObject);
			freshTable(noticeTable);
		}else{
			layer.msg(data.errorMessage);
		}
		
	});
};

 /**
  * 批量逻辑删除
  * 
  */
$(".dele_bx").click(function(){
 	deleteAll(basePath+"/operate/notice/deletes",noticeTable);
});

$("#notice_content").on("keyup change",function(){
	var arr = $('#notice_content').text().split("");
	$("#wordCnt").text(arr.length);
	if(arr.length <= 50){
		//如果有红色提示去掉
		$("#notice_content").parent().find("[class='error']").remove();		
	}
});

$("#update_notice_content").on("keyup change",function(){
	var arr = $('#update_notice_content').text().split("");
	$("#wordCnt2").text(arr.length);
	if(arr.length <= 50){
		//如果有红色提示去掉
		$("#update_notice_content").parent().find("[class='error']").remove();
	}
});

//获取当前时间
function show(){
	   var mydate = new Date();
	   var str = "" + mydate.getFullYear() + "-";
	   str += (mydate.getMonth()+1) + "-";
	   str += mydate.getDate() ;
	   return str;
}

//字符串转成Time(dateDiff)所需方法
function stringToTime(string) {
   var f = string.split(' ', 2);
   var d = (f[0] ? f[0] : '').split('-', 3);
   var t = (f[1] ? f[1] : '').split(':', 3);
   return (new Date(
  parseInt(d[0], 10) || null,
  (parseInt(d[1], 10) || 1) - 1,
parseInt(d[2], 10) || null,
parseInt(t[0], 10) || null,
parseInt(t[1], 10) || null,
parseInt(t[2], 10) || null
)).getTime();
}

function dateDiff(date1, date2) {
    var type1 = typeof date1, type2 = typeof date2;
    if (type1 == 'string')
        date1 = stringToTime(date1);
    else if (date1.getTime)
        date1 = date1.getTime();
    if (type2 == 'string')
        date2 = stringToTime(date2);
    else if (date2.getTime)
        date2 = date2.getTime();

    return (date2 - date1) / (1000 * 60 * 60 * 24); //结果是秒
}
