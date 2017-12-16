var courseDetailForm;

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


	
	if(mdaiId!=null &&  mdaiId!="" && mdaiId!=undefined){
		
		$.ajax({
			url:basePath+'/medical/doctor/mdaiDetail',
			dataType:'json',
			type:'get',
			data:{authenticationInformationId:mdaiId},
			success:function(data){
				if (data.success) {
					debugger;
					var obj = data.resultObject;
					
					$('#edit_smallImgPath').val(obj.headPortrait);
					reviewImage("edit_smallImgPath",obj.headPortrait);
					
					$('#edit_smallImgPath1').val(obj.titleProve);
					reviewImage("edit_smallImgPath1", obj.titleProve);
					
					$('#edit_smallImgPath2').val(obj.cardPositive);
					reviewImage("edit_smallImgPath2", obj.cardPositive);
					
					$('#edit_smallImgPath3').val(obj.cardNegative);
					reviewImage("edit_smallImgPath3",obj.cardNegative);
					
					$('#edit_smallImgPath4').val(obj.qualificationCertificate);
					reviewImage("edit_smallImgPath4", obj.qualificationCertificate);
					
					$('#edit_smallImgPath5').val(obj.professionalCertificate);
					reviewImage("edit_smallImgPath5", obj.professionalCertificate);
				}
			}
		});
	}

	createImageUpload($('.uploadImg'));//生成图片编辑器

});

$('#cancelbt,#returnbutton,#returnbutton2').on('click',function(){
	window.location.href=basePath+'/home#medical/doctor/index?page='+$('#page').val();
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
//图片上传统一上传到附件中心---- 修改  列表页
$("#courseDetailForm").on("change","#smallImgPath_file3",function(){
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

			$("#edit_smallImgPath3").val(data.url);
			document.getElementById("smallImgPath_file3").focus();
			document.getElementById("smallImgPath_file3").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});
//图片上传统一上传到附件中心---- 修改  列表页
$("#courseDetailForm").on("change","#smallImgPath_file4",function(){
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

			$("#edit_smallImgPath4").val(data.url);
			document.getElementById("smallImgPath_file4").focus();
			document.getElementById("smallImgPath_file4").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});

//图片上传统一上传到附件中心---- 修改  列表页
$("#courseDetailForm").on("change","#smallImgPath_file5",function(){
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

			$("#edit_smallImgPath5").val(data.url);
			document.getElementById("smallImgPath_file5").focus();
			document.getElementById("smallImgPath_file5").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});


$('#okbt,#previewbt').on('click',function(e){
    var id = $(e.currentTarget).attr('id');
    // var methodName = 'updateCourseDetail';
	debugger
    var validate = $("#courseDetailForm").valid();
    if(validate){
        mask();
        $("#courseDetailForm").attr("action", basePath+"/medical/doctor/updateMedicalDoctorDetail");
        $("#courseDetailForm").ajaxSubmit(function(data){
            unmask();
            try{
                data = jQuery.parseJSON(jQuery(data).text());
            }catch(e) {
                data = data;
            }
            if(data.success){
                    alertInfo("保存成功！");
                $("html").eq(0).css("overflow","scroll");
            }else{
                layer.msg(data.errorMessage);
                $("html").eq(0).css("overflow","scroll");
            }
        });
    }

});
