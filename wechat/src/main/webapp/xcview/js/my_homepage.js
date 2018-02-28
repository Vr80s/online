var status;

//yx_新增
//var openId = getQueryString("openId");
var opendId = getQueryString("openId");
if(stringnull(opendId)){
	localStorage.setItem("openid", openId);
}
//   
//   var userId = localStorage.getItem("userId");
//   if(!stringnull(userId)){
//		/* 如果是微信公众号进入页面时，没有给他返回token。所以这里他在请求下呢  */
//		var ccontrollerAddress = "/bxg/user/isLogined";
//		requestService(ccontrollerAddress, null, function(data) {
//			if (data.success) {
//				commonLocalStorageSetItem(data);
//			}else{
//				alert("网络异常");
//			}
//		},false)	
//	}
//}	
	

function balance() {
    requestService("/xczh/manager/home",{
    },function(data) {
        if(data.success==true){
            $("#xmbNumber").text(data.resultObject.xmbCount);
            $("#courseNumber").text(data.resultObject.courseCount);
            //用户头像
            $(".header_img").html(template('userInfo',data.resultObject.user));
			
        }else{        	           
            webToast(data.errorMessage,"middle",3000);
        }
    });
}
//判断是否为游客并跳转登陆界面
var isNouser='<div class="header_img_right y">主播工作台 &nbsp;></div>'+
				'<div class="both"></div>'+
				'<img src="../images/default_pic.png" alt="" class="img0" id="smallHeadPhoto" />'+
				'<p class="p" onclick="go_enter()">登录 / 注册</p>';

	var user_cookie = cookie.get("_uc_t_");
	if(user_cookie == null || user_cookie == ''){
   		$(".header_img").append(isNouser)
	}else{
//人民币/熊猫币余额
		balance();
		//获取认证状态
	    requestService("/xczh/medical/applyStatus",{
	    },function(data) {
	        if(data.success==true){
	            status = data.resultObject;

	        }else{
	            webToast(data.errorMessage,"middle",3000);
	        }
	    });
	}
	function go_enter(){
        window.location.href="enter.html";         
	}
	

//点击我要当主播
//function myAnchor() {
//  localStorage.setItem("judgeSkip", "my");
//  if(status==1||status==3||status==5){
//      window.location.href="phy_examine.html";
//  }else if(user_cookie == null || user_cookie == ''){
//      window.location.href="cn_login.html.html";         
//	}else if(status==2||status==4||status==6){
//      window.location.href="hos_examine.html";
//  }else{
//      window.location.href="my_anchor.html";
//  }
//}

//去下载页面
$(".my_anchor").click(function(){
	location.href="down_load.html"
})


/**
 * cookie
 * 返回 1000 有效   1002 过期，去登录页面  1005 过期且去完善信息
 */
function authenticationCooKie(){
	var falg = 1000;
	var user_cookie = cookie.get("_uc_t_");
	var third_party_cookie = cookie.get("third_party_uc_t_");
	if(!stringnull(user_cookie)){ //未登录
		falg = 1002
		if(stringnull(third_party_cookie)){   //用户用微信登录的但是没有绑定注册信息
			falg = 1005;
		}
	}
	return falg;
}














