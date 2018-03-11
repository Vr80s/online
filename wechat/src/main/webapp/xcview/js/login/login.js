
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
	
	location.href = "/xczh/wxlogin/publicWechatAndMobile";
	//location.href = "/bxg/wxjs/h5BsGetCodeUrl";
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
				  */
				location.href = "/xczh/wxlogin/getCurrentWechatOpenId?username="+number;
			}else{
				/*
				 * 跳转到分类
				 */
				location.href = "/xcview/html/home_page.html";
				
				/**
				 * 如果是分享页面的话，就去分享页面了
				 */
//				var share =  sessionStorage.getItem("share");
//				var shareCourseId =  sessionStorage.getItem("shareCourseId");
//				
//				
//				 /**
//				  * 需要获取当前微信的openId,并且传递要跳转页面的参数
//				  */
//				if(access == "brower"){
//					if(stringnull(share) && share == "liveDetails"){  //liveDetails bunchDetails
//						sessionStorage.removeItem("share");
//						location.href = "/bxg/xcpage/courseDetails?courseId="+shareCourseId;
//						return;
//					}else if(stringnull(share) && share == "bunchDetails"){  //xcviews/html/particulars.html?courseId=299
//						sessionStorage.removeItem("share");
//						location.href = "/xcviews/html/particulars.html?courseId=" + shareCourseId;
//						return;
//					}else if(stringnull(share) && share == "foresshow"){
//						sessionStorage.removeItem("share");
//						location.href = "/xcviews/html/foreshow.html?course_id=" + shareCourseId;
//						return;
//					}
//				}else if(access == "wx"){
//					sessionStorage.removeItem("share");
//					sessionStorage.removeItem("shareCourseId");
//					location.href = "/bxg/wxjs/h5BsGetCodeUrlAndBase" +
//					"?share="+share+"&courseid="+courseid+"" +
//					"&shareCourseId="+shareCourseId;
//				}
			}
		} else {
//			tishi.innerHTML = "<div class='vanish'><div class='vanish_bg'></div><div class='vanish_cen'><div class='vanish_size'>帐号或密码错误</div></div></div>";
//			setTimeout(function(){$(".vanish").hide();},1500);
			webToast(data.errorMessage,"middle",1500);
		}
	});
}










