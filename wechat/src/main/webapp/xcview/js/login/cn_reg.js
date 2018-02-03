
/**
 * 倒计时
 */
var wait = 90;
function time(o) {
	if (wait == 0) {
		//o.removeAttribute("disabled");
		$(o).css("background","#00bc12")
        $(o).val("获取验证码");
		wait = 90;
	} else {
		//o.setAttribute("disabled", true);
		$(o).css("background","#ccc")
        $(o).val("" + wait + "S");
		wait--;
		setTimeout(function() {
			time(o)
		}, 1000)
	}
}
/**
 * 点击获取验证码
 */

document.getElementById("btn").addEventListener("tap", function() {
	
	var number = document.getElementById("mobile").value; // 手机号
	
	var text = $(this).val();
	if(text!="获取验证码"){
		return false;
	}
	if (!stringnull(number)) {
		webToast("手机号不能为空","middle",3000);
		return false;
	}
	if (!(/^1[34578]\d{9}$/.test(number))) {
		webToast("手机号格式不正确","middle",3000);
		return false;
	}
	var urlparm = {
		username : number,
		vtype:1   	//类型，1注册，2重置密码
	};
	//time($(this));
	requestService("/xczh/user/sendCode", urlparm, function(data) {
		if (data.success) {
			//进入倒计时
			time($(this));
		} else {
			webToast(data.errorMessage,"middle",3000);
		}
	});
})



/**
 * 点击注册
 */
document.getElementById("enter_btn").addEventListener("tap", function() {
	
	//这块是需要搞下用户协议的同意
	
//	var agreementchecked = document.getElementById("checkbox1").checked;
//	if (stringnull(agreementchecked)) {
//		reminderror.innerHTML = "";
//		
//	} else {
//		
//		webToast("您好，注册须同意《熊猫中医云课堂用户协议》","middle",1500);
//		return false;
//	}
	var number = document.getElementById("mobile").value; // 手机号
	var yanzhengma = document.getElementById("vcode").value;
	var userpassword = document.getElementById("password").value; // 密码
	
	if (!stringnull(number)) {
		webToast("手机号不能为空","middle",1500);
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
	
	if (!(/^1[34578]\d{9}$/.test(number))) {
		webToast("手机号格式不正确","middle",1500);
		return false;
	}
	
	var pwdLength = userpassword.trim().length;
    if(pwdLength < 6 || pwdLength > 18) {
          webToast("请输入6-18位密码","middle",1500);
          return false;
    }
    
    var yanLength = yanzhengma.trim().length;
    if(yanLength > 4 || yanLength < 0) {
//        webToast("请输入4位数验证码","middle",1500);
          webToast("验证码有误，请重新输入","middle",1500);
          return false;
    }
    var urlparm = {
		username : number,
		password : userpassword,
		code : yanzhengma
    };
	var access_url ="/xczh/user/phoneRegist";
	requestService(access_url, urlparm, function(data) {
		if (data.success) {
			location.href = "/xcview/html/enter.html";
		} else {
			webToast(data.errorMessage,"middle",1500);				
		}
	});
})


/*
 * 返回登录页
 */
$(".enroll,.return").click(function(){
	location.href = "/xcview/html/enter.html";
})

//$(".check02_a").click(function(){
//	
//})

mui.init({
	swipeBack : false
});
