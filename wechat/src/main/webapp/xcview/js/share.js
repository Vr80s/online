function is_weixn(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
        return false;
    }
}
if(!is_weixn()){
    $(".weixin_li").remove();
}
/**
 * 进入这个详情页的时候需要记录下一个标记。如果用户登录后，直接就进入这个详情页，就ok了。
 *   登录之后删除掉。
 */
/**
 * 这个地方搞错了，这直接先去登录注册页面，让后呢，
 * 分享页面 - 返回
 */
document.getElementById("grabble_img").addEventListener("tap",function() {
	getOpenid();
})

var course_id =getQueryString("course_id");
/**
 * 判断是否需要跳转到pc网页。响应式的一种吧
 */
h5PcConversions(true,course_id);
/**
 * 分享页面关注
 */
$(".fx_guanzhu1").click(function(){
	getOpenid();
})
var pwdAndBuy = "";
var isSubscribe ="";
var teacherId="";
var type ="";
var lineState ="";

var result="";
var descriptionType  = "";


requestService("/bxg/common/h5ShareAfter",{
	course_id : course_id
}, function(data) {
	if (data.success) {
		
	    result = data.resultObject;
		type = result.type;
		lineState = result.lineState;
		
		sessionStorage.setItem("shareCourseId",course_id);
		
		if(result.type != 1){ //视频和音频   
			
			$(".details_size").html("观看人数：<span>"+result.learndCount+"</span>");
		
			$(".buy_bottom_p1").hide();
			descriptionType =result.courseDescription
		
			sessionStorage.setItem("share", "bunchDetails");
		}
		if(result.lineState != 2){   // 正在直播或者回放
			
			if(result.lineState == 1){    // 正在直播
				$(".buy_bottom_p1").show();
			}
			
			$(".details_size").html("观看人数：<span>"+result.learndCount+"</span>&nbsp;&nbsp;&nbsp;&nbsp;" +
			 		" 礼物：<span>"+result.giftCount+"</span>");
			
			descriptionType =result.description;
			
			$(".details_bottom").show();
			$(".order_center").hide();
			$(".order").css("background","url("+result.smallImgPath+")");
			$('.order').css('backgroundSize','100% 100%')
			
	        $(".buy_right a").html("立即观看");
			
			
			sessionStorage.setItem("share", "liveDetails"); 
		}else{  	//直播预告
			
			
			$(".details_bottom").hide();
			$(".order_center").show();
			var y = result.startTime.substring(0,4);
			var m = result.startTime.substring(5,7);
			var d = result.startTime.substring(8,10);
			var h = result.startTime.substring(11,13);
			var minute = result.startTime.substring(14,16);
			$(".order_center p:eq(0)").html(h+":"+minute+"开播");
			$(".order_center p:eq(1)").html(y+"."+m+"."+d);
			$(".order_center p:eq(2)").html("已预约人数："+result.countSubscribe);
			$(".buy_right a").html("立即预约");
			
			sessionStorage.setItem("share", "foresshow"); 
		}
		$(".buy_bottom_p2").html(result.learndCount+"人学习");
		$("#teacherId").val(result.userId);
		$("#content").html(result.description);
		$(".details_chat_attention p:eq(0)").html(result.gradeName);
		$(".details_chat_attention p:eq(1) span").html(result.name);
		$("#head_img").attr("src",result.headImg);
		$(".guanzhu1").show();
	} else {
	}
}, false)

/**
 * 这个东西不能暴露在前端的。
 */
function getOpenid(){
	if(isWeiXin()){ //来自微信浏览器
		/**
		 * 这里到分享页面后。点击让绑定手机号
		 */
		//location.href ="/bxg/wxjs/h5ShareGetWxOpenId?courseId="+course_id+"&type="+type+"&lineState="+lineState;
		location.href ="/xcviews/html/my.html?openId="+getQueryString("openid");
	}else{
		location.href ="/bxg/page/login/1";
	}
}


/**************** 微信分享 *************************/

	/*
	 * 注意：
	 * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
	 * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
	 * 3. 完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
	 * 如有问题请通过以下渠道反馈：
	 * 邮箱地址：weixin-open@qq.com
	 * 邮件主题：【微信JS-SDK反馈】具体问题
	 * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
	 */
	var domain = window.location.protocol+"//"+document.domain;

	wx.ready(function () {
		//发送到朋友
		wx.onMenuShareAppMessage({
		    title: result.gradeName, // 分享标题
		    desc: descriptionType, // 分享描述
		    link:domain+"/wx_share.html?courseId="+course_id, // 分享链接  这个连接一定要和微信中配置的jssdk 权限域名一致
		    imgUrl: result.smallImgPath, // 分享图标
		    type: '', // 分享类型,music、video或link，不填默认为link
		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		    success: function () {
		        // 用户确认分享后执行的回调函数
		    	$(".weixin_ceng").hide();
		    	$(".share").hide();
		    	//alert("分享成功");
		    },
		    cancel: function () {
		        // 用户取消分享后执行的回调函数
		    	$(".weixin_ceng").hide();
		    	$(".share").hide();
		    	//alert("取消分享");
		    }
		});
		
		//发送到朋友圈
		wx.onMenuShareTimeline({
		    title: result.gradeName, // 分享标题
		    link:domain+"/wx_share.html?courseId="+course_id, // 分享链接  这个连接一定要和微信中配置的jssdk 权限域名一致
		    imgUrl: result.smallImgPath, // 分享图标
		    success: function () {
		        // 用户确认分享后执行的回调函数
		    	$(".weixin_ceng").hide();
		    	$(".share").hide();
		    	//alert("分享成功");
		    },
		    cancel: function () {
		        // 用户取消分享后执行的回调函数
		    	//alert("取消分享");
		    	$(".weixin_ceng").hide();
		    	$(".share").hide();
		    }
		});
	    //发送到qq  
		wx.onMenuShareQQ({
		    title: result.gradeName, // 分享标题
		    desc: descriptionType, // 分享描述
		    link:domain+"/wx_share.html?courseId="+course_id, // 分享链接  这个连接一定要和微信中配置的jssdk 权限域名一致
		    imgUrl: result.smallImgPath, // 分享图标
		    success: function () {
		       // 用户确认分享后执行的回调函数
		    	$(".weixin_ceng").hide();
		    	$(".share").hide();
		    	//alert("分享成功");
		    },
		    cancel: function () {
		       // 用户取消分享后执行的回调函数
		    	//alert("取消分享");
		    	$(".weixin_ceng").hide();
		    	$(".share").hide();
		    }
		});
	})   
	
	
	 
