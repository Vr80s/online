
/**
 * 如果是来自分享的话。需要判断这个id啦
 */
var opendId = getQueryString("openId");
if(stringnull(opendId)){
	localStorage.setItem("openid", openId);
}
if(stringnull(param_page)){
	localStorage.setItem("code", param_page);
}


var error = getQueryString("error");
if(stringnull(error)){
	alert("账号信息有误,重试或联系客户!");
}
/**
 * 是否显示第三方登录啦
 */
if (isWeiXin()) {
	console.log(" 是来自微信内置浏览器")
	wxOrbrower = "wx";
} else {
	wxOrbrower = "brower";
}
localStorage.setItem("access", wxOrbrower);
var access = localStorage.access;
if(access == "brower"){
	$(".footer").hide();
}else if(access == "wx"){
	$(".footer").show();
}
/**
 * 第三方登录
 */
function sanfangLogin(){
	location.href = "/bxg/wxjs/h5BsGetCodeUrl";
}


//获取用户信息
//传值
//掉接口
//成功返回
var page = param_page;
var openid = localStorage.openid;
var access = localStorage.access;
var tishi = document.getElementById("tishi");
function login(){
	var number = document.getElementById("account").value;
	var password = document.getElementById("password").value;
	if (number == "") {
		tishi.innerHTML = "用户名不能为空";
		return false;
	}
	if (!(/^1[34578]\d{9}$/.test(number))) {
		tishi.innerHTML = "请输入正确格式的账号";
		return false;
	}
	if (password == "") {
		tishi.innerHTML = "密码不能为空";
		return false;
	}
	var pwdLength = password.trim().length;
	if (pwdLength < 6 || pwdLength > 18) {
		tishi.innerHTML = "请输入6~18位的密码";
		return false;
	}
	var urlparm = {
		username : number,
		password : password
	};
	
	//是浏览器登录呢，还是微信登录呢
	var ccontrollerAddress = "/bxg/bs/login";
	/*
	 * 如果是普通的一个连接进入的话，是可以的啊，也需要
	 */
	requestService(ccontrollerAddress, urlparm, function(data) {
		if (data.success) {
			var configresult = data.resultObject;
			/**
			 * 	  判断用户是否存在unindid，
			 * 		如果存在说明绑定了微信号。
			 * 		   虽然绑定了微信号了，但是绑定的如果不是当前微信号的话，是不是也比较尴尬啊，支付不了。
			 *      所以需要获取获取当前微信号的呢。
			 *      
			 * 		如果不存在，说明没有绑定微信号。
			 */
			if(!stringnull(configresult.unionId) && access == "wx"){
				 /* 
				  * 需要获取下用户信息啦，并且需要传递用户名和密码过去
				  */
				location.href = "/bxg/wxjs/h5BsGetCodeUrlReqParams?username="+number;
			}else{
				/**
				 * 添加缓存
				 */
				commonLocalStorageSetItem(data)
				// 返回的信息 下一个页面
				tishi.innerHTML = "";
				var courseid = localStorage.courseId;
				var coursename = localStorage.coursename;
				var sharecode = localStorage.sharecode;
				/**
				 * 如果是分享页面的话，就去分享页面了
				 */
				var share =  sessionStorage.getItem("share");
				var shareCourseId =  sessionStorage.getItem("shareCourseId");
				 /**
				  * 需要获取当前微信的openId,并且传递要跳转页面的参数
				  */
				if(access == "brower"){
					if(stringnull(share) && share == "liveDetails"){  //liveDetails bunchDetails
						sessionStorage.removeItem("share");
						location.href = "/bxg/xcpage/courseDetails?courseId="+courseid;
						return;
					}else if(stringnull(share) && share == "bunchDetails"){  //xcviews/html/particulars.html?courseId=299
						sessionStorage.removeItem("share");
						location.href = "/xcviews/html/particulars.html?courseId=" + shareCourseId;
						return;
					}else if(stringnull(share) && share == "foresshow"){
						sessionStorage.removeItem("share");
						location.href = "/xcviews/html/foreshow.html?course_id=" + shareCourseId;
						return;
					}
					if (page == "1") {
						location.href = "/bxg/page/index/" + openid + "/null";
					}
				}else if(access == "wx"){
					sessionStorage.removeItem("share");
					sessionStorage.removeItem("shareCourseId");
					location.href = "/bxg/wxjs/h5BsGetCodeUrlAndBase" +
					"?share="+share+"&courseid="+courseid+"" +
					"&shareCourseId="+shareCourseId;
				}
			}
		} else {
			tishi.innerHTML = "账号或密码错误";
		}
	});
}

/**
 * input获取焦点隐藏错误提示
 */
$("input").focus(function(){
	tishi.innerHTML = "";
});