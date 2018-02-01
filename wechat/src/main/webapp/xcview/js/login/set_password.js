	
/**
 * 验证码时间倒计时
 */
var countdown=90;
function settime(obj) { //发送验证码倒计时
    if (countdown == 0) { 
    	$(obj).attr('disabled',false); 
        //obj.removeattr("disabled"); 
    	$(obj).val("获取验证码");
        $("#btn").css("background","#00bc12")
        countdown = 90; 
        return;
    } else { 
    	$(obj).attr('disabled',true);
    	$(obj).val("" + countdown + "S");
        countdown--; 
        $("#btn").css("background","#ccc")
    } 
	setTimeout(function() { 
   		 settime(obj) }
    ,1000) 
}


var mobile = getQueryString("mobile");


$(".header_return").click(function(){
	 location.href = "/xcview/html/forget_password.html?mobile="+mobile;
});

//点击发送验证码
document.getElementById("btn").addEventListener("tap", function() {
	var _this = this;
	
	var text = $(this).val();
	if(text!="获取验证码"){
		return false;
	}
	
	//获取上一个电话号
	if (!(/^1[34578]\d{9}$/.test(mobile))) {
		webToast("未获取到正确的手机号","middle",1500);
		return false;
	}
	//获取上一个电话号
	var urlparm = {
		username : mobile,
		vtype:2   	//类型，1注册，2重置密码
	};
	requestService("/xczh/user/sendCode", urlparm, function(data) {
		if (data.success) {
			settime(_this);
		} else {
			webToast(data.errorMessage,"middle",3000);
		}
	});
})

/**
 * 点击注册
 */
document.getElementById("forget_btn").addEventListener("tap", function() {
	
	var yanzhengma = document.getElementById("code").value;
	var userpassword = document.getElementById("newPassword").value; // 密码
	
	if (!stringnull(mobile)) {
		webToast("手机号不能为","middle",1500);
		return false;
	}
	if (!stringnull(yanzhengma)) {
		webToast("验证码不能为空","middle",1500);
		return false;
	}
	if (!stringnull(userpassword)) {
		webToast("密码不能为空","middle",1500);
		return false;
	}
	if (!(/^1[34578]\d{9}$/.test(mobile))) {
		webToast("手机号格式不正确","middle",1500);
		return false;
	}
	var yanLength = yanzhengma.trim().length;
	    if(yanLength > 4 || yanLength < 0) {
	          webToast("请输入4位数验证码","middle",1500);
	          return false;
	}
	var pwdLength = userpassword.trim().length;
    if(pwdLength < 6 || pwdLength > 18) {
          webToast("请输入6-18位密码","middle",1500);
          return false;
    }
   
    var urlparm = {
		username : mobile,
		password : userpassword,
		code : yanzhengma
    };
    
	var access_url ="/xczh/user/forgotPassword";
	requestService(access_url, urlparm, function(data) {
		if (data.success) {
			location.href = "/xcview/html/enter.html";
		} else {
			webToast(data.errorMessage,"middle",1500);				
		}
	});
})



	