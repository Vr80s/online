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
 * 这个东西不能暴露在前端的。
 */
function getOpenid(){
	
	if(isWeiXin()){ //来自微信浏览器
		/**
		 * 这个直接获取用户的  h5BsGetCodeUr  然后去绑定手机号啦！
		 */
		location.href ="/bxg/wxjs/h5BsGetCodeUrl";
	}else{
		location.href ="/bxg/page/login/1";
	}
}

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

var result="";

var descriptionType  = "";

requestService("/bxg/common/h5ShareAfter",{
	course_id : course_id
}, function(data) {
	if (data.success) {
		
	    result = data.resultObject;
		type = result.type;
		
		sessionStorage.setItem("shareCourseId",course_id);
		
		$("#teacherId").val(result.userId);
		
		//判断分享的时间课程类型，得到这个课程的学习人数，这个课程的讲师，这个课程的额缩率图，这个课程的描述
		//观众人数
		if(result.type == 1){ //直播详情
			if(result.lineState == 2){
				$(".buy_bottom_p1").show();
			}
			
			if(result.lineState != 1){
				 $(".details_size").html("观看人数：<span>"+result.learndCount+"</span>&nbsp;&nbsp;&nbsp;&nbsp;" +
					 		" 礼物：<span>"+result.giftCount+"</span>");
				 sessionStorage.setItem("share", "liveDetails"); 
			}else{
				sessionStorage.setItem("share", "foresshow");
			}
			descriptionType =result.description;
		}else{
			
			 $(".details_size").html("观看人数：<span>"+result.learndCount+"</span>");
			 sessionStorage.setItem("share", "bunchDetails");
			
			$(".buy_bottom_p1").hide();
			descriptionType =result.courseDescription
		}
		$(".buy_bottom_p2").html(result.learndCount+"人学习");
	
		
		alert(sessionStorage.getItem("share"));
		
		if(result.lineState != 1){  //直播已结束和正在直播
			$(".details_bottom").show();
			$(".order_center").hide();
			/**
			 * 设置课程缩率图
			 */
			$(".order").css("background","url("+result.smallImgPath+")");
			$('.order').css('backgroundSize','100% 100%')
			/**
			 * 观众数、礼物数
			 */
			//观看人数：<span></span>&nbsp;&nbsp;&nbsp;&nbsp; 礼物：<span></span>
			
	        $(".buy_right a").html("立即观看");
		}else{
			
			$(".details_bottom").hide();
			$(".order_center").show();
			/*
			 * 判断课程类别。
			 */
			
			var y = result.startTime.substring(0,4);
			var m = result.startTime.substring(5,7);
			var d = result.startTime.substring(8,10);
			var h = result.startTime.substring(11,13);
			var minute = result.startTime.substring(14,16);
			/*
			 * 判断课程类别。
			 */
			$(".order_center p:eq(0)").html(h+":"+minute+"开播");
			$(".order_center p:eq(1)").html(y+"."+m+"."+d);
			$(".order_center p:eq(2)").html("已预约人数："+result.countSubscribe);
			$(".buy_right a").html("立即预约");
		}
		/*
		 * 判断课程类别。
		 */
		$("#content").html(result.description);
		$(".details_chat_attention p:eq(0)").html(result.gradeName);
		$(".details_chat_attention p:eq(1) span").html(result.name);
		$("#head_img").attr("src",result.headImg);
		/**
		 * 肯定是未关注的吧
		 */
		$(".guanzhu1").show();
	} else {
	}
}, false)

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
	wx.ready(function () {
		//发送到朋友
		wx.onMenuShareAppMessage({
		    title: result.gradeName, // 分享标题
		    desc: descriptionType, // 分享描述
		    link:getServerHost()+"/bxg/common/pcShareLink?courseId="+course_id, // 分享链接
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
		    link:getServerHost()+"/bxg/common/pcShareLink?courseId="+course_id, // 分享链接
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
		    link:getServerHost()+"/bxg/common/pcShareLink?courseId="+course_id, // 分享链接
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
	
	
	 