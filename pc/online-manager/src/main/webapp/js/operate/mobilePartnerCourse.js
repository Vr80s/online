var courseDetailForm;
$(function() {
	courseDetailForm = $("#courseDetailForm").validate({
		messages : {
			img_url:{
				required:"课程展示图不能为空！"
			},share_desc:{
				required:"合伙人介绍内容不能为空！",
				minlength:"合伙人介绍内容不能为空！",
			},work_flow:{
				required:"推广流程内容不能为空！",
				minlength:"推广流程内容不能为空！"
			}
		}
	});
	$.ajax({
		url:basePath+'/mobile/partner/courseInfo',
		dataType:'json',
		type:'post',
		data:{},
		success:function(data){
			if (data.success) {
				if(data.resultObject.id && data.resultObject.id != ''){
					$('#id').val(data.resultObject.id);
				}
				if(data.resultObject.course_id && data.resultObject.course_id != ''){
					$('#course_id').val(data.resultObject.course_id);
				}
				if(data.resultObject.img_url && data.resultObject.img_url != ''){
					$('#edit_img_url').val(data.resultObject.img_url);
					reviewImage("edit_img_url", data.resultObject.img_url);
				}
				if(data.resultObject.share_desc && data.resultObject.share_desc != ''){
					$('#shareDesc_content').html(data.resultObject.share_desc);
					$('#edit_share_desc').html(data.resultObject.share_desc);
				}
				if(data.resultObject.work_flow && data.resultObject.work_flow != ''){
					$('#workFlow_content').html(data.resultObject.work_flow);
					$('#edit_work_flow').html(data.resultObject.work_flow);
				}
				$(".remove").hide();
			}
		}
	});

	createImageUpload($('.uploadImg'));//生成图片编辑器

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
		uploadType:{type:'url',action:basePath+'/mobile/partner/uploadImg'}//图片上传方式，url/base64
	}).prev().addClass('wysiwyg-style2');
});

$('#okbt').on('click',function(){
	if($('#shareDesc_content').html().indexOf('<img')!=-1){
		$('#edit_share_desc').val("img");
	}else{
		$('#edit_share_desc').val($('#shareDesc_content').text());
	}

	if($('#workFlow_content').html().indexOf('<img')!=-1){
		$('#edit_work_flow').val("img");
	}else{
		$('#edit_work_flow').val($('#workFlow_content').text());
	}

	if($("#courseDetailForm").valid()){
		var shareDesc_val = $('#shareDesc_content').next().val();
		if (shareDesc_val) {
			$('#edit_share_desc').val(shareDesc_val);
		} else {
			$('#edit_share_desc').val($('#shareDesc_content').html());
		}

		var workFlow_val = $('#workFlow_content').next().val();
		if(workFlow_val){
			$('#edit_work_flow').val(workFlow_val);
		} else {
			$('#edit_work_flow').val($('#workFlow_content').html());
		}

		$("#courseDetailForm").attr("action", basePath+"/mobile/partner/saveOrUpdate");
		$("#courseDetailForm").ajaxSubmit(function(data) {
			unmask();
			data = getJsonData(data);
			if (data.success) {
				alertInfo("保存成功！");
				$("html").eq(0).css("overflow", "scroll");
			} else {
				layer.msg(data.errorMessage);
				$("html").eq(0).css("overflow", "scroll");
			}
		});
	}
});

$('#cancelbt').on('click',function(){
	window.location.href=basePath+'/home#welcome';
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

			$("#edit_img_url").val(data.url);
			document.getElementById("smallImgPath_file").focus();
			document.getElementById("smallImgPath_file").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});
