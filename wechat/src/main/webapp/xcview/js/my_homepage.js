var status;
var falg =authenticationCooKie();
//yx_新增
//var openId = getQueryString("openId");
var opendId = getQueryString("openId");
if(stringnull(opendId)){
	localStorage.setItem("openid", openId);
}

function balance() {
    requestService("/xczh/manager/home",{
    },function(data) {
        if(data.success==true){
            $("#xmbNumber").text(data.resultObject.xmbCount);
            $("#courseNumber").text(data.resultObject.courseCount);
            //用户头像
            $(".header_img").html(template('userInfo',data.resultObject.user));
			
        }else{        	           
            webToast(data.errorMessage,"middle",1500);
        }
    });
}
//判断是否为游客并跳转登陆界面
var isNouser='<div class="header_img_right y">主播工作台 &nbsp;></div>'+
				'<div class="both"></div>'+
				'<img src="../images/default_pic.png" alt="" class="img0" id="smallHeadPhoto" />'+
				'<p class="p"><span onclick="go_enter_dl()">登录</span> / <span onclick="go_cnlogin_zc()">注册</span></p>';

	if (falg==1002){
   			$(".header_img").append(isNouser)
	}
//	else if (falg==1005) {
//			location.href ="/xcview/html/evpi.html";
//	}
	else{
		//人民币/熊猫币余额
		balance();
		//获取认证状态
	    requestService("/xczh/medical/applyStatus",{
	    },function(data) {
	        if(data.success==true){
	            status = data.resultObject;

	        }else{
	            webToast(data.errorMessage,"middle",1500);
	        }
	    });
	}
	function go_enter_dl(){
        window.location.href="enter.html";         
	}
	function go_cnlogin_zc(){
        window.location.href="cn_login.html";         
	}
////判断是否为游客并跳转登陆界面
//var falg =authenticationCooKie();
//已购
function go_enter(){
	if (falg==1002){
			location.href ="/xcview/html/enter.html";		
	}else if (falg==1005) {
			location.href ="/xcview/html/evpi.html";
	}else{
		window.location='/xcview/html/bought.html'		
	}
}
//钱包
function go_cnlogin(){
	if (falg==1002){
			location.href ="/xcview/html/enter.html";		
	}else if (falg==1005) {
			location.href ="/xcview/html/evpi.html";
	}else{
	window.location.href="/xcview/html/my_wallet.html";		
	}
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

//点击学习判断游客
function go_study(){
		if (falg==1002){
			location.href ="/xcview/html/enter.html";		
		}else if (falg==1005) {
			location.href ="/xcview/html/evpi.html";
		}else{
			location.href ="/xcview/html/my_study.html";			
		}
}








