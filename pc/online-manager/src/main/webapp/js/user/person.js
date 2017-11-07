var personInfoForm;
var personPasswordForm;
$(function(){
	personInfoForm = $("#person-info-form").validate({
		messages:{
            name:{
                required:"姓名不能为空"
            },
            mobile:{
                required:"手机不能为空"
            }
		}
	});
	personPasswordForm = $("#person-password-form").validate({
		messages:{
			oldpassword:{
                required:"请输入原密码",
                maxlength:"请输入原密码",
                minlength:"请输入原密码"
            },
            newpassword:{
                required:"请输入6-16位以字母、数字或下划线组成的密码",
                maxlength:"请输入6-16位以字母、数字或下划线组成的密码",
                minlength:"请输入6-16位以字母、数字或下划线组成的密码"
            },
            repassword:{
                required:"请输入6-16位以字母、数字或下划线组成的密码",
                maxlength:"请输入6-16位以字母、数字或下划线组成的密码",
                minlength:"请输入6-16位以字母、数字或下划线组成的密码"
            }
		}
	});
	clearPassword();
});

function removePhoto(){
	if($('.imageBox').css("background-image")=="none"){
		alertInfo("请选择头像");
		return;
	}
	$('.imageBox').val("");
	$('.imageBox').css("background-image","")
	$(".avatarlarge img").attr("src",'/images/defaultavatar.png');
	$(".avatarsmall img").attr("src",'/images/defaultavatar.png');
}

function updatePersonInfo(){
	//debugger;
	//alertInfo("保存");
	if($("#person-info-form").valid()){
		mask();
		$("#person-info-form").ajaxSubmit(function(data){
			unmask();
			if(data.success){
				alertInfo("修改成功");
			}else{
				alertInfo(data.errorMessage);
			}
		});
	}
}

function updateHeadPhoto(){
	if($('.imageBox').css("background-image")!="none"){
		$('#btnCrop').trigger("click");
	}
	var image = $('#imageId').val();
	//没选择文件时会产生有334个字符
	if(image.length < 400){
		alertInfo("请选择头像");
		return;
	}
	mask();
	$("#person-head-form").ajaxSubmit(function(data){
		unmask();
		if(data.success){
			$(".nav-user-photo").prop("src", basePath+"/attachmentCenter/download?aid="+data.resultObject.smallHeadPhoto);
			alertInfo("修改成功");
		}else{
			alertInfo(data.errorMessage);
		}
	});
}

function updatePassword(){
	if($("#person-password-form").valid()){
		var op = $("#oldpassword").val();
		var np = $("#newpassword").val();
		var rp = $("#repassword").val();
		if(np != rp){
			alertInfo("两次密码不一样");
			clearPassword();
			return;
		}
		mask();
		$("#person-password-form").ajaxSubmit(function(data){
			if(data.success){
				unmask();
				alertInfo("修改成功");
				clearPassword();
			}else{
				unmask();
				alertInfo(data.errorMessage);
				clearPassword();
			}
		});
	}
}

function clearPassword(){
	$("#oldpassword").val("");
	$("#newpassword").val("");
	$("#repassword").val("");
}