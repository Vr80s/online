$(function(){
	
	$('.forum').css('color','#000');
	$('.path .hospital').addClass('select');
	
		//已有账号登陆之后进行的页面跳转
//	  RequestService("/medical/common/isDoctorOrHospital","GET",null,function(data){
//	       if(data.success == true ){
//	       	if(data.resultObject.indexOf(2) != -1){
//	       		//医师认证成功 
//	       		window.location.href = "/web/html/ResidentHospital.html";
//	       	}else if(data.resultObject.indexOf(1) != -1){
//	       		//医馆认证成功 提示不能进行医师认证
////	       		alert('您已完成了医馆注册，不能进行医师注册!');
//	       		$('#tip').text('您已完成了医师注册，不能进行医馆注册！');
//	       		$('#tip').toggle();
//	       		setTimeout(function(){
//	       			$('#tip').toggle();
//	       			window.location.href = "/web/html/clinics.html";
//	       		},1500)
//	       		
//	       	}else if(data.resultObject.indexOf(7) != -1){
//	       		//都没有注册过 进入注册页面
//	       		window.location.href = "/web/html/ResidentHospital.html";
//	       	}else{
//	       		//医师认证中 医师认证拒绝 跳转到认证状态页面
//	       		window.location.href = "/web/html/ResidentHospital.html";
//	       	}
//	       }
//	    });
	    
	    
	    //已有账号登陆之后进行的页面跳转
	  RequestService("/medical/common/isDoctorOrHospital","GET",null,function(data){
	       if(data.success == true && $('.login').css('display') == 'block'){
	       	if(data.resultObject.indexOf(2) != -1){
	       		//医馆认证成功
	       		window.location.href = "/web/html/ResidentHospital.html";
	       	}else if(data.resultObject.indexOf(1) != -1){
	       		//医师认证成功 提示不能进行医馆认证
//	       		alert('您已完成了医馆注册，不能进行医师注册!');
	       		$('#tip').text('您已完成了医师注册，不能进行医馆注册！');
	       		$('#tip').toggle();
	       		setTimeout(function(){
	       			$('#tip').toggle();
	       			window.location.href = "/web/html/clinics.html";
	       		},1500)
	       		
	       	}else if(data.resultObject.indexOf(7) != -1){
	       		//都没有注册过 进入注册页面
	       		window.location.href = "/web/html/ResidentHospital.html";
	       	}else{
	       		//医师认证中 医师认证拒绝 跳转到认证状态页面
	       		window.location.href = "/web/html/ResidentHospital.html";
	       	}
	       }
	    });
	
	

	
	
	
	//点击注册按钮
	$('.practitioner_settled').on('click','.register',function(){
	//此处进行验证
	var phoneNum = $.trim($('.phoneNum').val());
	var phonePass =  /^1[3,4,5,7,8]\d{9}$/gi;
	var nikename = $.trim($('.nikeName').val());
	var imgCode = $.trim($('.imgCode').val());
	var msgCode = $.trim($('.code').val());
	//手机号码验证
	if($.trim(phoneNum) == ""){
				$(".phone_warn").text('手机号不能为空');
				$(".phone_warn").css('display','block');
				return false;
		}else if(!phonePass.test(phoneNum)){
		$(".phone_warn").text('手机号格式不正确')
		$(".phone_warn").css('display','block');
		return false;
			}else{
				$(".phone_warn").css('display','none');
			}

	//昵称验证
	if(nikename == ''){
		$(".nikeName_warn").css('display','block');
		return false;
	}else{
		$(".nikeName_warn").css('display','none');
	}
	
	//图形验证码
	if(imgCode == ''){
		$(".imgVertifyCode_warn").text('请输入图形动态码')
		$(".imgVertifyCode_warn").css('display','block');
		return false;
	}else{
		$(".imgVertifyCode_warn").css('display','none');
	}
	
	
	
	//短信验证码
	if(msgCode == ''){
		$(".my_code_warn").text('请输入短信验证码')
		$(".my_code_warn").css('display','block');
		return false;
	}else{
		$(".my_code_warn").css('display','none');
	}
	
	
	
	//密码验证
	if($.trim($('.my_password').val()).length < 6){
		$(".my_different").text('请输入6-18位数密码')
		$(".my_different").css('display','block');
		return false;
	}else if($.trim($('.my_password').val()) != $.trim($('.sure_password').val())){
		$(".my_different").text('您两次输入的密码不一致')
		$(".my_different").css('display','block');
		return false;
	}else{
		$(".my_different").css('display','none');
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
				//错误的提示
				if(data.errorMessage == '动态码错误'){
					$('#tip').text('短信验证码错误');
	       			$('#tip').toggle();
	       			setTimeout(function(){
	       				$('#tip').toggle();
	       			},1500)
				}else{
					$('#tip').text(data.errorMessage);
	       			$('#tip').toggle();
	       			setTimeout(function(){
	       				$('#tip').toggle();
	       			},1500)
				}
	  	}else if(data.success == true){
	  		$('#tip').text('注册成功');
	       		$('#tip').toggle();
	       		setTimeout(function(){
	       			$('#tip').toggle();
				window.location.href = '/web/html/ResidentHospital.html';
	       		},1500)
	  	}
	  })
	
//	window.location.href = '/web/html/ResidentDoctor.html';
	
	})
	
	
	
	
	//点击动态图片进行切换
	$(".imgVertifyCode img").on("click",function(){
		var a=Math.random();
        $(this).attr("src","/online/verificationCode/vcode?t="+a+"");
	});
	
	
	//同意协议可以注册
	var agreeNum = 1;
	$('#agreeAgreement>input').click(function(){
		agreeNum *= -1;
		if(agreeNum < 0){
			$('#zhuceBtn').removeClass('register');
			$('#zhuceBtn').addClass('notregister');
		}else if(agreeNum > 0){
			$('#zhuceBtn').removeClass('notregister');
			$('#zhuceBtn').addClass('register');
		}
	})

	
	
	
})
