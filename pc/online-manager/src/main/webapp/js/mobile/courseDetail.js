var courseDetailForm;
$(function() {
	$('#myTab a').click(function (e) {
	  e.preventDefault()
	  $(this).tab('show');
	  $("html").eq(0).css("overflow","scroll");
	})

	//$('#myTab a[href="#profile"]').tab('show') // Select tab by name
	$('#myTab a:first').tab('show') // Select first tab
	$('#myTab a:last').tab('show') // Select last tab
	$('#myTab li:eq(2) a').tab('show') // Select third tab (0-indexed)

	document.onkeydown=function(event){

//		if(event.keyCode==13){
//			return false
//		}
	}
$(function() {
	createImageUpload($('.uploadImg'));//生成图片编辑器
	document.onkeydown=function(event){
		
	}
	courseDetailForm = $("#courseDetailForm").validate({
		messages : {
			smallImgPath:{
				required:"列表页图不能为空！"
			},detailImgPath:{
				required:"详情页图不能为空！"
			},courseDetail:{
				required:"课程详情内容不能为空！",
				minlength:"课程详情内容不能为空！",
			}
		}
	});
	$.ajax({
		url:basePath+'/mobile/course/getCourseDetail',
		dataType:'json',
		type:'post',
		data:{courseId:$('#courseId').val()},
		success:function(data){
			if (data.success) {
				if(data.resultObject.smallImgPath && data.resultObject.smallImgPath != ''){
					$('#edit_smallImgPath').val(data.resultObject.smallImgPath);
					reviewImage("edit_smallImgPath", data.resultObject.smallImgPath);
				}
				if(data.resultObject.detailImgPath && data.resultObject.detailImgPath != ''){
					$('#edit_detailImgPath').val(data.resultObject.detailImgPath);
					reviewImage("edit_detailImgPath", data.resultObject.detailImgPath);
				}
				if(data.resultObject.courseDetail && data.resultObject.courseDetail != ''){
					$('#courseDetail_content').html(data.resultObject.courseDetail);
					$('#courseDetail').html(data.resultObject.courseDetail);
				}
				if(data.resultObject.courseOutline && data.resultObject.courseOutline != ''){
					$('#courseOutline_content').html(data.resultObject.courseOutline);
					$('#courseOutline').html(data.resultObject.courseOutline);
				} else {
					initTemplate('outline','courseOutline_content','courseOutline');
				}
				if(data.resultObject.commonProblem && data.resultObject.commonProblem != ''){
					$('#commonProblem_content').html(data.resultObject.commonProblem);
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
	function initTemplate(type,contentid,id){
		$.ajax({
			url:basePath+'/mobile/course/getTemplate',
			dataType:'json',
			type:'get',
			data:{type:type},
			success:function(data){
				if(data.success){
					$('#'+contentid).html(data.resultObject);
					$('#'+id).html(data.resultObject);
				}
			}
		});
	}

	$(".wysiwyg-editor").ace_wysiwyg({
		toolbar : [ {
			name : 'viewSource',
			className : 'btn-info'
		},null,'font', null, 'fontSize', null, {
			name : 'bold',
			className : 'btn-info'
		}, {
			name : 'italic',
			className : 'btn-info'
		}, {
			name : 'strikethrough',
			className : 'btn-info'
		}, {
			name : 'underline',
			className : 'btn-info'
		}, null, {
			name : 'insertunorderedlist',
			className : 'btn-success'
		}, {
			name : 'insertorderedlist',
			className : 'btn-success'
		}, {
			name : 'outdent',
			className : 'btn-purple'
		}, {
			name : 'indent',
			className : 'btn-purple'
		}, null, {
			name : 'justifyleft',
			className : 'btn-primary'
		}, {
			name : 'justifycenter',
			className : 'btn-primary'
		}, {
			name : 'justifyright',
			className : 'btn-primary'
		}, {
			name : 'justifyfull',
			className : 'btn-inverse'
		}, null, {
			name : 'createLink',
			className : 'btn-pink'
		}, {
			name : 'unlink',
			className : 'btn-pink'
		}, null, {
			name : 'insertImage',
			className : 'btn-success'
		}, null, 'foreColor', null, {
			name : 'undo',
			className : 'btn-grey'
		}, {
			name : 'redo',
			className : 'btn-grey'
		} ],
		'wysiwyg' : {
			fileUploadError : showErrorAlert
		},
		uploadType:{type:'url',action:basePath+'/cloudclass/course/uploadImg'}//图片上传方式，url/base64
	}).prev().addClass('wysiwyg-style2');

	
 });

$('#okbt,#previewbt').on('click',function(e){
	var id = $(e.currentTarget).attr('id');
	var methodName = id=='previewbt' ? 'addPreview' : 'updateCourseDetail';
	if($('#courseDetail_content').html().indexOf('<img')!=-1){
		$('#courseDetail').val("img");
	}else{
		$('#courseDetail').val($('#courseDetail_content').text());
	}

	/*if($('#courseOutline_content').html().indexOf('<img')!=-1){
		$('#courseOutline').val("img");
	}else{
		$('#courseOutline').val($('#courseOutline_content').text());
	}*/

	/*if($('#commonProblem_content').html().indexOf('<img')!=-1){
		$('#commonProblem').val("img");
	}else{
		$('#commonProblem').val($('#commonProblem_content').text());
	}*/

	var validate = id=='previewbt' ? true : $("#courseDetailForm").valid();

	if(validate){

		var courseDetail_content_val = $('#courseDetail_content').next().val();
		if(courseDetail_content_val){
			$('#courseDetail').val(courseDetail_content_val);
		} else {
			$('#courseDetail').val($('#courseDetail_content').html());
		}

		var courseOutline_content_val = $('#courseOutline_content').next().val();
		if (courseOutline_content_val) {
			$('#courseOutline').val(courseOutline_content_val);
		} else {
			$('#courseOutline').val($('#courseOutline_content').html());
		}

		var commonProblem_content_val = $('#commonProblem_content').next().val();
		if(commonProblem_content_val){
			$('#commonProblem').val(commonProblem_content_val);
		} else {
			$('#commonProblem').val($('#commonProblem_content').html());
		}

		mask();
		$("#courseDetailForm").attr("action", basePath+"/mobile/course/"+methodName);
		$("#courseDetailForm").ajaxSubmit(function(data){
			unmask();
			data = getJsonData(data);
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
$('#cancelbt,#returnbutton,#returnbutton2').on('click',function(){
	window.location.href=basePath+'/home#mobile/course/index?page='+$('#page').val();
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

