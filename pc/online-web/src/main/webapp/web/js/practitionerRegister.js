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
		return false;
	}
	
	var data = {
                nikeName: $('.nikeName').val().trim(),
                username: $('.phoneNum').val().trim(),
                password: $('.my_password').val().trim(),
                code: $('.code').val().trim()
            };
	//注册部分的ajax
	  RequestService("/online/user/phoneRegist", "POST", data, function (data) {
	  	console.log(data);
	  	if(data.success == false){
	  		if(data.errorMessage == '动态码不正确!'){
					
					$('.my_code .my_code_warn').text(data.errorMessage);
					$('.phone_warn').css('display','none');
					$('.my_code .my_code_warn').css('display','block');
						return false;
				}else{
					$('.phone_warn').text(data.errorMessage);
					$('.my_code .my_code_warn').css('display','none');
					$('.phone_warn').css('display','block');
						return false;
				}
	  	}else if(data.success == true){
	  		alert('注册成功!');
	  		
	  	}
	  })
	
//	window.location.href = '/web/html/ResidentDoctor.html';
	
	})
	
	
	
	
	//点击动态图片进行切换
	$(".imgVertifyCode img").on("click",function(){
		var a=Math.random();
        $(this).attr("src","/online/verificationCode/vcode?t="+a+"");
	});
	
	
	//点击获取动态码的时候
//	function btn_cade(){
//						var myTime=60;
//						var timer=null;
//						timer=setInterval(auto,1000);						
//						function auto(){
//							myTime--;
//							$(".practitioner_input .my_code button").html(myTime+ 's');
//							$(".practitioner_input .my_code button").removeAttr('onclick');
//							$(".practitioner_input .my_code button").css({"background":"#dedede","color":"#999999"})
//							if(myTime==0){
//								setTimeout(function(){
//									clearInterval(timer)
//									$(".practitioner_input .my_code button").html('获取验证码');
//									$(".practitioner_input .my_code button").attr('onclick','btn_cade()');
//									$(".practitioner_input .my_code button").css({"background":"#00bd12","color":"white"})
//								},1000)								
//							}
//						}
//					//验证动态码
//					var tel = $.trim($('.phoneNum').val());
//					var imgVertify=$(".imgVertify").val();
//					 var data = {phone: tel, vtype: 1,vcode:imgVertify};
//					 RequestService("/online/verificationCode/sendmessage", "POST", data,function(result){
//					 	console.log(data);
//					 })
//
//
//						
//	}
	
	
})
