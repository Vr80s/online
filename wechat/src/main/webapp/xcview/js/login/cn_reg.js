
/**
 * 倒计时
 */
var wait = 60;
function time(o) {
	//alert($(o).val());
	if (wait == 0) {
		//o.removeAttribute("disabled");
		$(o).css("background","#00bc12");
        $(o).text("获取验证码");
		wait = 60;
	} else {
		//o.setAttribute("disabled", true);
		$(o).css("background","#00bc12");
		$(o).css("opacity","0.5");
        $(o).text("" + wait + "S");
		wait--;
		setTimeout(function() {
			time(o)
		}, 1000)
	}
}


var opendId = getQueryString("openId");
if(stringnull(opendId)){
	localStorage.setItem("openid", openId);
}
/**
 * 点击获取验证码
 */

document.getElementById("btn").addEventListener("tap", function() {
	
	var number = document.getElementById("mobile").value; // 手机号
	
	var o = $(this);
	
	//var text = $(this).val();

	var text = $(this).text();
	if(text!="获取验证码"){
		return false;
	}
	if (!stringnull(number)) {
//		webToast("手机号不能为空","middle",1500);
		return false;
	}
	if (!(/^1[345678]\d{9}$/.test(number))) {
		webToast("手机号格式不正确","middle",1500);
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
			time(o);
		} else {
			webToast(data.errorMessage,"middle",1500);
		}
	});
})



/**
 * 点击注册
 */

mui(".last_cn").on('tap', '#enter_btn', function (event) {
	

	
	
	var number = document.getElementById("mobile").value; // 手机号
	var yanzhengma = document.getElementById("vcode").value;
	var userpassword = document.getElementById("password").value; // 密码
	
//	if(number != '' && yanzhengma != '' && userpassword != ''){
////				$(".enter_btn").css("opacity","1");
//				$(".enter_btn").addClass("enter_btns");
//		}else{
////				$(".enter_btn").css("opacity","0.3");
//				$(".enter_btn").removeClass("enter_btns");
//	}
	
	
	
	if (!stringnull(number)) {
		//webToast("手机号不能为空","middle",1500);
		return false;
	}
	
	if (!stringnull(yanzhengma)) {
//		webToast("验证码不能为空","middle",1500);
		return false;
	}
	if (!stringnull(userpassword)) {
//		webToast("密码不能为空","middle",1500);
		return false;
	}
	
	if (!(/^1[345678]\d{9}$/.test(number))) {
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
    
    //	$("#enter_btn").click(function(){
	//这块是需要搞下用户协议的同意
	
	var agreementchecked = document.getElementById("checkbox1").checked;
	if (!agreementchecked) {
//		reminderror.innerHTML = "";
//		$(".web_toast").addClass("hide");
		webToast("请同意协议内容","middle",1500);
		
		return false;
	} 
    
    var urlparm = {
		username : number,
		password : userpassword,
		code : yanzhengma
    };
    if(is_weixin()){
    	urlparm.openId = localStorage.getItem("openid");
    }
	var access_url ="/xczh/user/phoneRegist";
	/*
	 * 这里目前还需要更改下呢，需要搞下如果是微信注册的话，绑定微信号了
	 */
	requestService(access_url, urlparm, function(data) {
		if (data.success) {
			/**
			 * 添加信息
			 * @returns
			 */
			commonLocalStorageSetItem(data);
			//是否是微信浏览器
			if(is_weixin()){
				 /* 
				  * 需要获取下用户信息啦，并且需要传递用户名和密码过去
				  * 	entryType 1 表示从登录页面进入首页   2 表示从注册页面进入完善头像页面
				  */
				location.href = "/xczh/wxlogin/getCurrentWechatOpenId?entryType=2";
			}else{
				/*
				 * 跳转到分类
				 */
				location.href = "/xcview/html/heads_nicknames.html";
			}	
		} else {
			webToast(data.errorMessage,"middle",1500);				
		}
	});
	
});

/*document.getElementById("enter_btn").addEventListener("tap", function() {
	
})*/

/*
 * 返回登录页
 */
$(".enroll,.return").click(function(){
	
	var type = getQueryString('type');
	getQueryString(type);

	if(type=='1'){
		location.href="/xcview/html/enter.html?course_id="+courseId+"&type="+1;
	}else if(type=='2'){
		location.href ="/xcview/html/enter.html?course_id="+courseId+"&type="+2;
	}else if(type=='3'){
		location.href ="/xcview/html/enter.html?course_id="+courseId+"&type="+3;
	}else{
		location.href = "/xcview/html/enter.html";
	}

	// location.href = "/xcview/html/enter.html";

	// location.href ="/xcview/html/enter.html?course_id="+courseId+"&type="+2;
// location.href="/xcview/html/school_play.html?course_id="+courseId+"&type="+1;
})

//$(".check02_a").click(function(){
//	
//})

mui.init({
	swipeBack : false
});
