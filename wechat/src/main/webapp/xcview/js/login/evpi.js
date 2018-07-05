
/**
 * 倒计时
 */
var wait = 60;
function time(o) {
	if (wait == 0) {
		//o.removeAttribute("disabled");
		$(o).css("background","#00bc12");
        $(o).text("获取验证码");
		wait = 60;
	} else {
		//o.setAttribute("disabled", true);
		$(o).css("background","#ccc");
        $(o).text("" + wait + "S");
		wait--;
		setTimeout(function() {
			time(o)
		}, 1000)
	}
}

/**
 * 微信用户openId和unionid 为了防止获取不到,双重获取
 */
var openId ="";var unionId ="";

var third_party_uc_t_ = cookie.get("_third_ipandatcm_user_");
if(isNotBlank(third_party_uc_t_)){
	
	third_party_uc_t_ = decodeURIComponent(third_party_uc_t_);	
	openId = third_party_uc_t_.split(";")[0];
	unionId = third_party_uc_t_.split(";")[1];
}else{
	openId = getQueryString("openId");
	if(!isNotBlank(openId)){
		openId = localStorage.getItem("openid")
	}
	unionId = getQueryString("unionId");
	if(!isNotBlank(unionId)){
		unionId = localStorage.getItem("unionId")
	}
}






/**
 * jump_type=1	跳到首页
 * jump_type=2	跳到我的页面
 */
var jump_type = getQueryString("jump_type");

/**
 * 点击获取验证码   --  验证手机号是否是否
 */

var vtype =0;

document.getElementById("btn").addEventListener("tap", function() {
	var number = document.getElementById("mobile").value; // 手机号
	
	var o = $(this);
	var text = $(this).text();
	if(text!="获取验证码"){
		return false;
	}
	if (!isNotBlank(number)) {
		webToast("手机号不能为空","middle",1500);
		return false;
	}
	if (!(/^1[345678]\d{9}$/.test(number))) {
		webToast("手机号格式不正确","middle",1500);
		return false;
	}
	var urlparm = {
		userName : number,
		unionId : unionId,
		type:1   	// 1 微信  2 微博   3 qq
	};
	/**
	 * 
	 */
	requestService("/xczh/third/thirdCertificationMobile", urlparm, function(data) {
		
		if (data.code == 400) { //显示密码框
			vtype =1;
			$("#password_div").show();
		}else if(data.code == 401){ //隐藏密码框
			vtype =6;//
			$("#password_div").hide();
		}else if(data.code == 402){
			webToast("此手机号绑定其他微信号","middle",1500);
		}
		
		if(data.code != 402){
			requestService("/xczh/user/sendCode", {username:number,vtype:vtype}, function(data) {
				if (data.success) {
					//进入倒计时
					time(o);
				} else {
					webToast(data.errorMessage,"middle",1500);
				}
			});
		}
	});
})




/**
 * 点击注册
 */
$(".enter_btn").click(function(){
	
	var number = document.getElementById("mobile").value; // 手机号
	var yanzhengma = document.getElementById("vcode").value;
	var userpassword = document.getElementById("password").value; // 密码
	
	if (!isNotBlank(number)) {
		webToast("手机号不能为空","middle",1500);
		return false;
	}
	
	if (!isNotBlank(yanzhengma)) {
		webToast("验证码不能为空","middle",1500);
		return false;
	}
	if (!(/^1[345678]\d{9}$/.test(number))) {
		webToast("手机号格式不正确","middle",1500);
		return false;
	}
	
	var params ={
			userName:number,
			code:yanzhengma,
			unionId:unionId,
			type:1
	};
	var url = "/xczh/third/thirdPartyBindIsNoMobile";
	if(vtype==1){
		if (!isNotBlank(userpassword)) {
//			webToast("密码不能为空","middle",1500);
			return false;
		}
		url = "/xczh/third/thirdPartyBindMobile";
		params.passWord = userpassword;
	}
	requestService(url,params, function(data) {
		if (data.success) {
			/**
			 * 添加 所有关于用户的缓存
			 */
			commonLocalStorageSetItem(data);
            locationToOriginPage();
		} else {
			webToast(data.errorMessage,"middle",1500);
		}
	});
})	

/*
 * 返回登录页
 */
$(".header_return").click(function(){
	location.href = "/xcview/html/home_page.html";
	//window.history.back();
})


mui.init({
	swipeBack : false
});
