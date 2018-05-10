
///**
// * 如果是来自分享的话。需要判断这个id啦
// */
var opendId = getQueryString("openId");
if(stringnull(opendId)){
	localStorage.setItem("openid", openId);
}
//if(stringnull(param_page)){
//	localStorage.setItem("code", param_page);
//}
//
//
//var error = getQueryString("error");
//if(stringnull(error)){
//	alert("帐号信息有误,重试或联系客户!");
//}

/**
 * 是否显示第三方登录啦
 */
var wxOrbrower = "";
if (is_weixin()) {
	console.log(" 是来自微信内置浏览器")
	wxOrbrower = "wx";
	
	
} else {
	wxOrbrower = "brower";
	
	$(".other_means").hide();
	$(".enter_bottom").hide();
}
//localStorage.setItem("access", wxOrbrower);
//var access = localStorage.access;
//if(access == "brower"){
//	$(".footer").hide();
//}else if(access == "wx"){
//	$(".footer").show();
//}
/**
 * 第三方登录
 */
function sanfangLogin(){
	
	/**
	 * 清理下存在的信息
	 */
	commonLocalStorageRemoveItem();
	
	location.href = "/xczh/wxlogin/publicWechatAndMobile";
}

/*******  新的接口  *************/

/**
 * 跳转注册页面
 */
$(".enroll").click(function(){
	location.href = "/xcview/html/cn_login.html";
})

//获取用户信息
//传值
//掉接口
//成功返回
//var page = param_page;
var openid = localStorage.openid;
var access = localStorage.access;
var tishi = document.getElementById("tishi");

/**
 * 点击登录
 * @returns {Boolean}
 */
function login(){
	
	var number = document.getElementById("account").value;
	var password = document.getElementById("password").value;
	
	if (!stringnull(number) || !stringnull(password)) {
//		webToast("手机号或密码不能为空","middle",1500);
		return false;
	}
	if (!(/^1[346578]\d{9}$/.test(number))) {
		
		webToast("请输入正确的手机号","middle",1500);
		return false;
	}
	var pwdLength = password.trim().length;
	if (pwdLength < 6 || pwdLength > 18) {
		webToast("请输入6~18位的密码","middle",1500);
		return false;
	}
	/**
	 * 请求登录
	 * 
	 */
	var urlparm = {
		username : number,
		password : password
    };
	requestService("/xczh/user/login", urlparm, function(data) {
		if (data.success) {
			var configresult = data.resultObject;
			/**
			 * 添加 所有关于用户的缓存
			 */
			commonLocalStorageSetItem(data);
			/**
			 * 	  判断用户是否存在unindid，
			 * 		如果存在说明绑定了微信号。
			 * 		   虽然绑定了微信号了，但是绑定的如果不是当前微信号的话，是不是也比较尴尬啊，支付不了。
			 *      所以需要获取获取当前微信号的呢。
			 * 		如果不存在，说明没有绑定微信号。
			 */
			if(wxOrbrower == "wx"){
				 /* 
				  * 需要获取下用户信息啦，并且需要传递用户名和密码过去
				  *  entryType 1 表示从登录页面进入首页   2 表示从注册页面进入完善头像页面
				  */
				location.href = "/xczh/wxlogin/getCurrentWechatOpenId?entryType=1";
			}else{
				var orderPageHtml = sessionStorage.getItem("order_page_html");
//				if(stringnull(orderPageHtml)){
//					alert(orderPageHtml);
//					sessionStorage.setItem("order_page_html","");
//					location.replace(orderPageHtml);
//				}else{
//					//到登录页面的入口   被拦截后，回到注册页面，然后用户可以注册，
//					location.href = "/xcview/html/home_page.html";
//				}
				
				var type = getQueryString('type');
				getQueryString(type);

				if(type=='1'){
					location.href="/xcview/html/school_play.html?course_id="+courseId+"&type="+1;
				}else if(type=='2'){
					location.href ="/xcview/html/school_audio.html?course_id="+courseId+"&type="+2;
				}else if(type=='3'){
					ocation.href ="/xcview/html/school_class.html?course_id="+courseId+"&type="+3;
				}else{
					location.href = "/xcview/html/home_page.html";
				}
				
				// location.href = "/xcview/html/home_page.html";

			}
		} else {
			webToast(data.errorMessage,"middle",1500);
		}
	});
}










