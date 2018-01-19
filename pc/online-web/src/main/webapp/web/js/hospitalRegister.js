$(function(){
	
	
	$('.register').click(function(){
	//此处进行验证
	var phoneNum = $.trim($('.phoneNum').val());
	var phonePass =  /^1[3,4,5,7,8]\d{9}$/gi;
	
	//手机号码验证
	if($.trim(phoneNum) == ""){
				$(".phone_warn").text('手机号不能为空');
				$(".phone_warn").css('display','block');
				return false;
			}
	if(!phonePass.test(phoneNum)){
		$(".phone_warn").text('手机号格式不正确')
		$(".phone_warn").css('display','block');
		return false;
			}

	if($.trim($('.my_password').val()) != $.trim($('.sure_password').val())){
		$(".my_different").css('display','block');
	}
	
	window.location.href = '/web/html/ResidentHospital.html';
	
	})
	
	
	
	
})