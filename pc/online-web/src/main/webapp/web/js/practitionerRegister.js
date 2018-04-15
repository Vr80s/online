$(function(){
	
	
		
	$('.forum').css('color','#000');
	$('.path .doctor').addClass('select');
	
		//已有帐号登录之后进行的页面跳转
	  RequestService("/medical/common/isDoctorOrHospital","GET",null,function(data){
	       if(data.success == true && $('.login').css('display') == 'block'){
	       	if(data.resultObject == 1 ){
	       		//医师认证成功 医师认证中 医师认证拒绝 跳转到认证状态页面
	       		window.location.href = "/web/html/anchors_resources.html";
	       	}else if(data.resultObject == 2 ){
	       		//医馆认证成功 提示不能进行医师认证
//	       		alert('您已完成了医馆注册，不能进行医师注册!');
	       		$('#tip').text('您已完成了医馆注册，不能进行医师注册！');
	       		$('#tip').toggle();
	       		setTimeout(function(){
	       			$('#tip').toggle();
	       			window.location.href = "/doctors";
	       		},2000)
	       		
	       	}else if(data.resultObject == 7 ){
	       		//都没有注册过 进入注册页面
	       		window.location.href = "/web/html/ResidentDoctor.html";
	       	}else{
	       		//医师认证中 医师认证拒绝 跳转到认证状态页面
	       		window.location.href = "/web/html/ResidentDoctor.html";
	       	}
	       }
	    });
	
	

	
	
	
	//点击注册按钮
	$('.practitioner_settled').on('click','.register',function(){
	//此处进行验证
	var phoneNum = $.trim($('.phoneNum').val());
	var phonePass =  /^1[3,4,5,6,7,8]\d{9}$/gi;
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
	if(nikename == '' || nikename.length > 20){
		$(".nikeName_warn").css('display','block');
		return false;
	}else {
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
		$(".my_different").css('display','none');
		$('.my_pwd').text('请输入6-18位数密码');
		$(".my_pwd").css('display','block');
		return false;
	}else if($.trim($('.my_password').val()) != $.trim($('.sure_password').val())){
		$(".my_pwd").css('display','none');
		$(".my_different").text('您两次输入的密码不一致')
		$(".my_different").css('display','block');
		return false;
	}else{
		$(".my_pwd").css('display','none');
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
//	  		if(data.errorMessage == '动态码不正确!'){
//					
//					$('.my_code .my_code_warn').text(data.errorMessage);
//					$('.phone_warn').css('display','none');
//					$('.my_code .my_code_warn').css('display','block');
//						return false;
//				}else{
//					$('.phone_warn').text(data.errorMessage);
//					$('.my_code .my_code_warn').css('display','none');
//					$('.phone_warn').css('display','block');
//						return false;
//				}

				//错误的提示
				if(data.errorMessage == "动态码不正确！"){
//					$('#tip').text('短信验证码错误');
//	       			$('#tip').toggle();
//	       			setTimeout(function(){
//	       				$('#tip').toggle();
//	       			},2000)
					$(".my_code_warn").text('短信验证码错误')
					$(".my_code_warn").css('display','block');

				}else{
					$('#tip').text(data.errorMessage);
	       			$('#tip').toggle();
	       			setTimeout(function(){
	       				$('#tip').toggle();
	       			},2000)
				}
				
	  	}else if(data.success == true){
	  		
	  		$('#tip').text('注册成功');
	       		$('#tip').toggle();
	       		setTimeout(function(){
	       			$('#tip').toggle();
				window.location.href = '/web/html/ResidentDoctor.html';
	       		},2000)
	  			
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
//			alert(1)
		}else if(agreeNum > 0){
			$('#zhuceBtn').removeClass('notregister');
			$('#zhuceBtn').addClass('register');
//			alert(2)
		}
	})

	
	
	
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
