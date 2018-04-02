var lecturerStatus = $("#lecturerStatus").val();
$(function(){
	
	$(".wysiwyg-editor").ace_wysiwyg({
		toolbar:
				[
					'font',
					null,
					'fontSize',
					null,
					{name:'bold', className:'btn-info'},
					{name:'italic', className:'btn-info'},
					{name:'strikethrough', className:'btn-info'},
					{name:'underline', className:'btn-info'},
					null,
					{name:'insertunorderedlist', className:'btn-success'},
					{name:'insertorderedlist', className:'btn-success'},
					{name:'outdent', className:'btn-purple'},
					{name:'indent', className:'btn-purple'},
					null,
					{name:'justifyleft', className:'btn-primary'},
					{name:'justifycenter', className:'btn-primary'},
					{name:'justifyright', className:'btn-primary'},
					{name:'justifyfull', className:'btn-inverse'},
					null,
					{name:'createLink', className:'btn-pink'},
					{name:'unlink', className:'btn-pink'},
					null,
					{name:'insertImage', className:'btn-success'},
					null,
					'foreColor',
					null,
					{name:'undo', className:'btn-grey'},
					{name:'redo', className:'btn-grey'}
				],
		'wysiwyg': {
			fileUploadError: showErrorAlert
		},
		uploadType:{type:'url',action:basePath+'/cloudclass/course/uploadImg'}//图片上传方式，url/base64
	}).prev().addClass('wysiwyg-style2');
//	reviewImage("add_imgPath",$("#add_imgPath").val())
	if(lecturerStatus == 1){
		$("#isTeacher").prop("checked",true);
	}
	debugger;
});


//保存修改
$("#saveBtn").click(function(){
	
	var content=$("#article_content").html();
	if($("#article_content").html()=="<br>"){
		content="";
	}
	$("#description").val(content);
	debugger;
	
	if($("#addArticle-form").valid()){
		mask();
		 $("#addArticle-form").attr("action", basePath+"/onlineuser/updateUserLecturer");
		 $("#addArticle-form").ajaxSubmit(function(data){
			 debugger;
//			 try{
//         		data = jQuery.parseJSON(jQuery(data).text());
//         	}catch(e) {
//         		data = data;
//         	  }
			 if(data.success){
				 alertInfo(data.resultObject);
				 turnPage(basePath+'/home#onlineuser/index');
			 }else{
				 alertInfo(data.errorMessage);
			 }
			 unmask();
		 })
	}
});

//返回
$("#returnbutton").click(function(){
	turnPage(basePath+'/home#onlineuser/index');
})

$("#isTeacher").on("click",function(){//点击添加UL
	var  lecturerStatus = 0;
	if($("#isTeacher").is(':checked')){//如果选中赋值1 否则0
		lecturerStatus = 1;
	}else{
		lecturerStatus = 0;
	}
	$("#lecturerStatus").val(lecturerStatus);
});
/**
 * 图片回显
 * @param inputId  input[type=file]的id
 * @param imgSrc   图片路径
 */
//function reviewImage(inputId,imgSrc){
//	var fileName = imgSrc;
//	if(imgSrc.indexOf("/")>-1){
//		fileName = imgSrc.substring(imgSrc.lastIndexOf("/")+1);
//	}
//	$("#"+inputId).parent().find('.ace-file-name').remove();
//	$("#"+inputId).parent().find(".ace-file-container").addClass('hide-placeholder').attr('data-title', null)
//	.addClass('selected').html('<span class="ace-file-name" data-title="'+fileName+'">'
//			 +('<img class="middle" style="width: 250px; height: 140px;" src="'+imgSrc+'"><i class="ace-icon fa fa-picture-o file-image"></i>')
//					 +'</span>');
//}
//
///**
// * 设置用户为讲师
// */
//function setUserLecturer(obj,op){
//	
//	var oo = $(obj).parent().parent().parent();
//	var aData = onlineuserTable.fnGetData(oo); // get datarow
//	
//	var loginName  = aData.loginName;//用户id
//	ajaxRequest('onlineuser/updateUserLecturer', {
//		'loginName' : loginName,
//		'lecturerStatus' : op
//	}, function(data) {
//		console.log(data);
//		console.log("====="+data.resultObject.resultObject);
//		if (data.success) {
//			freshTable(onlineuserTable);
//			if(op==1){
//				var str ="设置该用户的讲师职位成功";
//			/*	$("#room_number").html("房间号:"+data.resultObject.resultObject);
//				var dialog = openDialogNoBtnName("userRoomNumberDialog","viewRoomNumber",str,300,200,false,"确定",null);*/
//				alertInfo("设置该用户的讲师职位成功<br>房间号："+data.resultObject.resultObject);
//			}else{
//				alertInfo("取消该用户的讲师职位成功");
//			}
//		} else {
//			alertInfo(data.errorMessage);
//		}
//	});
//}
//
