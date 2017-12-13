var courseDetailForm;
var _courseRecTable;
var _courseTable;
var tempDesId = "";//用于判断切换保存的变量

$(function() {

	courseDetailForm = $("#courseDetailForm").validate({
		messages : {
			smallImgPath:{
				required:"列表页图不能为空！"
			},detailImgPath:{
				required:"详情页图不能为空！"
			},courseDetail:{
				required:"课程详情内容不能为空！",
				minlength:"课程详情内容不能为空！",
			},courseOutline:{
				required:"课程大纲内容不能为空！",
				minlength:"课程大纲内容不能为空！"
			},commonProblem:{
				required:"常见问题不能为空！",
				minlength:"常见问题不能为空！"
			}
		}
	});

	$.ajax({
		url:basePath+'/medical/hospital/getHospitalDetail',
		dataType:'json',
		type:'post',
		data:{medicalHospitalId:$('#courseId').val()},
		success:function(data){
			if (data.success) {
				debugger;
				if(data.resultObject.picture.length>0){
					$('#edit_smallImgPath').val(data.resultObject.picture[0].picture);
					reviewImage("edit_smallImgPath", data.resultObject.picture[0].picture);
				}
				/*2017-08-14---yuruixin*/
				if(data.resultObject.smallImgPath1 && data.resultObject.smallImgPath1 != ''){
					$('#edit_smallImgPath1').val(data.resultObject.smallImgPath1);
					reviewImage("edit_smallImgPath1", data.resultObject.smallImgPath1);
				}
				if(data.resultObject.smallImgPath2 && data.resultObject.smallImgPath2 != ''){
					$('#edit_smallImgPath2').val(data.resultObject.smallImgPath2);
					reviewImage("edit_smallImgPath2", data.resultObject.smallImgPath2);
				}
				/*2017-08-14---yuruixin*/
				if(data.resultObject.detailImgPath && data.resultObject.detailImgPath != ''){
					$('#edit_detailImgPath').val(data.resultObject.detailImgPath);
					reviewImage("edit_detailImgPath", data.resultObject.detailImgPath);
				}
				if(data.resultObject.courseDetail && data.resultObject.courseDetail != ''){
//					$('#courseDetail_content').html(data.resultObject.courseDetail);
					ueEditccpe_content.ready(function(){
						ueEditccpe_content.setContent(data.resultObject.courseDetail);
					});
					$('#courseDetail').html(data.resultObject.courseDetail);
				}
				if(data.resultObject.commonProblem && data.resultObject.commonProblem != ''){
					ueEditccpe_commonProblem_content.ready(function(){
						ueEditccpe_commonProblem_content.setContent(data.resultObject.commonProblem);
					});
//					$('#commonProblem_content').html(data.resultObject.commonProblem);
					$('#commonProblem').html(data.resultObject.commonProblem);
				} else {
					initTemplate('problem','commonProblem_content','commonProblem');
				}
				if(data.resultObject.descriptionShow==1){//1是展示
					$("#descriptionShow").prop("checked",true);
				}else{
					$("#descriptionShow").prop("checked",false);
				}
				$("#titleXQ").text(data.resultObject.gradeName);
				$(".remove").hide();
			}
		}
	});

	createImageUpload($('.uploadImg'));//生成图片编辑器

});

$('#cancelbt,#returnbutton,#returnbutton2').on('click',function(){
	window.location.href=basePath+'/home#cloudclass/course/index?page='+$('#page').val();
});

//图片上传统一上传到附件中心---- 修改  列表页
$("#courseDetailForm").on("change","#smallImgPath_file",function(){
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");

			$("#edit_smallImgPath").val(data.url);
			document.getElementById("smallImgPath_file").focus();
			document.getElementById("smallImgPath_file").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});

//图片上传统一上传到附件中心---- 修改 详情页
$("#courseDetailForm").on("change","#detailImgPath_file",function(){
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
		  	$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");

		  	$("#edit_detailImgPath").val(data.url);
		  	document.getElementById("detailImgPath_file").focus();
		  	document.getElementById("detailImgPath_file").blur();
		  	$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});

//图片上传统一上传到附件中心---- 修改  列表页
$("#courseDetailForm").on("change","#smallImgPath_file",function(){
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
			
			$("#edit_smallImgPath").val(data.url);
			document.getElementById("smallImgPath_file").focus();
			document.getElementById("smallImgPath_file").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});
//图片上传统一上传到附件中心---- 修改  列表页
$("#courseDetailForm").on("change","#smallImgPath_file1",function(){
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
			
			$("#edit_smallImgPath1").val(data.url);
			document.getElementById("smallImgPath_file1").focus();
			document.getElementById("smallImgPath_file1").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});
//图片上传统一上传到附件中心---- 修改  列表页
$("#courseDetailForm").on("change","#smallImgPath_file2",function(){
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
			
			$("#edit_smallImgPath2").val(data.url);
			document.getElementById("smallImgPath_file2").focus();
			document.getElementById("smallImgPath_file2").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});

//图片上传统一上传到附件中心---- 修改 详情页
$("#courseDetailForm").on("change","#detailImgPath_file",function(){
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
			
			$("#edit_detailImgPath").val(data.url);
			document.getElementById("detailImgPath_file").focus();
			document.getElementById("detailImgPath_file").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});
