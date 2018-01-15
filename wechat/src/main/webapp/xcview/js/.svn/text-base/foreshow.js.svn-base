

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
 * 
 *//*
 document.getElementById("grabble_img").addEventListener("tap",function() {
	 var page = sessionStorage.getItem("livePage");
	 if(page == 1){
		 location.href = "/bxg/page/index/null/null";
	 }else if(page == 2){
		 location.href = "/bxg/xcpage/history";
	 }else if(page == 3){
		 location.href = "/bxg/xcpage/queryResult";
	 }
 })
*/

var course_id = getQueryString("course_id");

/**
 * 判断是否需要跳转到pc网页
 */
h5PcConversions(true,course_id);

var pwdAndBuy = "";
var isSubscribe ="";
var teacherId="";

function convertDateFromString(dateString) { 
	if (dateString) { 
		var arr1 = dateString.split(" "); 
		var sdate = arr1[0].split('-'); 
		var date = new Date(sdate[0], sdate[1]-1, sdate[2],sdate[3],sdate[4]); 
		return date;
	}
}	
	
var result ="";
requestService("/bxg/common/userIsSubscribe",{  //判断是否购买或者是否输入过密码
	course_id : course_id
}, function(data) {
	if (data.success) {
		
		result = data.resultObject;
		//teacherId =data.
		$("#teacherId").val(result.userId);
		
		//(new Date()).pattern("yyyy-MM-dd  HH:mm:ss")
		//var date = convertDateFromString(result.startTime);
		
	/*	var date = new Date(Date.parse(result.startTime));// convertDateFromString(result.startTime);
		
		var y = date.getFullYear();  
        var m = date.getMonth() + 1;  
        var d = date.getDate();  
        d = d < 10 ? ('0' + d) : d;  
        var h = date.getHours();  
        h = h < 10 ? ('0' + h) : h;  
        var minute = date.getMinutes();  
        minute = minute < 10 ? ('0' + minute) : minute;  */
		
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

		$("#content").html(result.description);
		
		//isfours 0 未关注  1已关注
		//gradeName  name  headImg  
		$(".details_chat_attention p:eq(0)").html(result.gradeName);
		$(".details_chat_attention p:eq(1) span").html(result.name);
		$("#head_img").attr("src",result.headImg);
		if(result.isfocus == 1){
			$(".guanzhu2").show();
			$(".guanzhu1").hide();
			//grabble.png  /xcviews/images/guanzhu_03.png
			/*$("#guanzhuimg").attr("src","/xcviews/images/guanzhu_03.png");*/
		}else if(result.isfocus == 0){
			//grabble.png  /xcviews/images/grabble.png
			$(".guanzhu1").show();
			$(".guanzhu2").hide();
		/*	$("#guanzhuimg").attr("src","/xcviews/images/guanzhu_03.png");*/
		}
		if(result.isSubscribe == 0){ //未预约 
			isSubscribe = 0;
			if(result.watchState == 1){   //需要购买
				$(".buy_bottom_p1").html("￥<span>"+result.currentPrice+"</span>");
				$(".buy_bottom_p2").html(result.learndCount+"人已购买");
				pwdAndBuy = 1;
			}else if(result.watchState == 2){ //需要密码
				/*$(".buy_bottom_p1").html("<span>需要密码</span>");*/
				$(".buy_bottom_p1").html("<span style='font-size:16px;'>密码预约</span>")
				$(".buy_bottom_p2").html(result.learndCount+"人确认密码");
				pwdAndBuy = 2;
			}else{
				$(".buy_bottom_p1").html("<span style='font-size:16px;'>免费视频</span>")
				$(".buy_bottom_p2").html(result.learndCount+"人预约");
				$(".buy_bottom_p2").hide();
				pwdAndBuy = 0;
			}
		}else if(result.isSubscribe == 1){//已经预约
			isSubscribe = 1;
			$(".buy_bottom_p1").hide();
			if(result.watchState == 1){
				pwdAndBuy = 1;
				$(".buy_bottom_p2").html(result.learndCount+"人已购买");
			}else if(result.watchState == 2){
				pwdAndBuy = 2;
				$(".buy_bottom_p2").html(result.learndCount+"人确认密码");
			}else if(result.watchState == 0){
				pwdAndBuy = 0;
				$(".buy_bottom_p2").html(result.learndCount+"人已预约");
			}
			$("#buy_right a").html("已预约");
		}
	} else {

	}
}, false)

	//微博分享 
	document.getElementById('weiboShare').onclick = function(e){
		    var  p = {
		        url: getServerHost()+"/bxg/common/pcShareLink?courseId="+course_id,/*获取URL，可加上来自分享到QQ标识，方便统计*/
		        title :result.gradeName,/*分享标题(可选)*/
		        pic : result.smallImgPath /*分享图片(可选)*/
		    };
		    var s = [];
		    for (var i in p) {
		        s.push(i + '=' + encodeURIComponent(p[i] || ''));
		    }
		    var _src = "http://service.weibo.com/share/share.php?" + s.join('&') ;
		    window.open(_src);
	};

    //qq分享 
    document.getElementById('qqShare').onclick = function(e){
		    var  p = {
		        url: getServerHost()+"/bxg/common/pcShareLink?courseId="+course_id,/*获取URL，可加上来自分享到QQ标识，方便统计*/
		        desc: '中医传承', /*分享理由(风格应模拟用户对话),支持多分享语随机展现（使用|分隔）*/
		        title : result.gradeName,/*分享标题(可选)*/
		        summary : result.description.stripHTML(),/*分享描述(可选)*/
		        pics : result.smallImgPath  /*分享图片(可选)*/
		    };
		    var s = [];
		    for (var i in p) {
		        s.push(i + '=' + encodeURIComponent(p[i] || ''));
		    }
		    var _src = "http://connect.qq.com/widget/shareqq/index.html?" + s.join('&') ;
		    window.open(_src);
	};



/**************** 微信分享 *************************/
	/*
	 * 注意：
	 * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
	 * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
	 * 3. 完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
	 *
	 * 如有问题请通过以下渠道反馈：
	 * 邮箱地址：weixin-open@qq.com
	 * 邮件主题：【微信JS-SDK反馈】具体问题
	 * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
	 */
	wx.ready(function () {
		//发送到朋友
		wx.onMenuShareAppMessage({
		    title: result.gradeName, // 分享标题
		    desc: result.description.stripHTML(), // 分享描述
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
		    desc: result.description.stripHTML(), // 分享描述
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



/**
 * 点击立即预约
 */
function goPay() {
	if(isSubscribe == 0){
		if(pwdAndBuy ==0){
			
			$(".buy_bg01").show();
			$(".buy_center").show();
			$(".order_center").hide();
			
		}else if(pwdAndBuy == 1){
			requestService("/bxg/order/save", {
				courseId : course_id,
				orderFrom : 4
			}, function(data) {
				if (data.success) {
					var result = data.resultObject;
					location.href = "/xcviews/html/pay.html?orderId="+result.orderId+"&courseId=" + course_id
							+ "&orderNo=" + result.orderNo+ "&page=2";
				} else {
					alert("提交订单错误！请稍后再试！");
				}
			});
		}else if(pwdAndBuy == 2){
			//$("#mobile_subscribe").show();
			//显示预约
			$(".buy_bg01").show();
			$(".buy_center").show();
		}
	}else if(isSubscribe == 1){//已经预约，还需要在判断是否确认密码
		if(pwdAndBuy == 2){
			$("#passwordDiv").show();
		}else if(pwdAndBuy == 0){
			alert("开始直播时,您可以直接观看");
			return;
		}
	}else{
		alert("状态异常");
	}
}


function subscribe() {

	var regPhone = /^1[3-578]\d{9}$/;
	var flag = true;
	if ($("#mobile").val().trim().length === 0) {
		alert("请输入手机号！");
		flag = false;
	} else if (!(regPhone.test($("#mobile").val().trim()))) {
		alert("手机号格式错误！");
		flag = false;
	}
	if (!flag) {
		return false;
	}
	requestService("/bxg/common/subscribe", {
		course_id : course_id,
		mobile : $("#mobile").val()
	}, function(data) {
		if (data.success) {
			//已经预约
			isSubscribe =1;
			
			var result = data.resultObject;
			$("#mobile_subscribe").hide()
			$(".buy_center1").show();
			
			$("#buy_right a").html("已预约");
			$(".order_center").show();
			
			if(pwdAndBuy == 2){
				$("#passwordDiv").show();
			}else if(pwdAndBuy == 0){
				alert("开始直播时,您可以直接观看");
				return;
			}
		} else {
			$("#mobile_subscribe").hide()
			alert("预约失败！" + data.msg);
		}
	});
}



/*
 * 显示密码的弹框
 */
function viewPwd(){
	$("#passwordDiv").show();
}

function enterPassword() {
	requestService("/bxg/common/coursePwdConfirm", {
		course_id : course_id,
		course_pwd : $("#password").val()
	}, function(data) {
		if (data.success) {
			$("#passwordDiv").hide();
			pwdAndBuy = 0;
            alert("开始直播时,您可以直接观看");
		} else {
			alert("密码错误！");
		}
	});
}
/*// 返回到直播列表中
document.getElementById("grabble_img").addEventListener("tap", function() {
	//跳转要调整的地方
	
	location.href = "/bxg/page/index/null/null";
})*/



