
function is_weixn(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
        return false;
    }
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    //获取?号后边的值
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

/**
 * 返回当前页面的名字：
 * 比如/asdasf/asfaewf/agaegr/trer/rhh.html?y=1
 *    /asdasf/asfaewf/agaegr/trer/rhh.html   
 * 
 * 返回 rhh.html
 * 
 */
function getCurrentViewHtml(){
	var url = window.location.href;
	var indexLast = url.lastIndexOf("\/");
	var index = url.indexOf("?");
	if(index!=-1){
		url  = url .substring(indexLast + 1, index);
	}else{
		url  = url .substring(indexLast + 1, str.length);
	}
	return url;
}
/**
 * 根据分享所处的当前页面 截取得到分享id： 
 * @param viewHtml   当前页面
 * @returns {Number}  
 */
function getShareId(){
	
	var viewHtml = getCurrentViewHtml();
	if(viewHtml == "live_personal.html"){
		
		return getQueryString("userLecturerId");
		
	}else if(viewHtml == "live_audio.html" || 
			viewHtml == "live_play.html"){
		return getQueryString("my_study");
		
	}else if(viewHtml == "school_audio.html"|| 
			viewHtml == "school_play.html"|| 
			viewHtml == "school_class.html"|| 
			viewHtml == "live_select_album.html"){
		
		return getQueryString("course_id");
	}
}


/**
 * 根据分享所处的当前页面 得到分享类型： 
 * @returns {Number}  1：课程  2：主播
 */
function getShareType(){
	var viewHtml = getCurrentViewHtml();
	if(viewHtml == "live_personal.html"){
		return 2;
	}else{
		return 1;
	}
}

//目前是写死的，应该是固定着的
//var gradeName = "你好,心承智慧";
//var smallImgPath ="http://attachment-center.ixincheng.com:38080/data/attachment/online/2017/12/13/17/4b4eb1300a90449d8d1cb975cce35def.png";
//var description ="你好,心承智慧";


var shareType = getShareType();
var shareId = getShareId();

var domain = window.location.protocol+"//"+document.domain;
var link = domain+"/wx_share.html?shareType="+shareType+"&shareId="+shareId;

//点击分享share
$(".header_news").click(function(){

	var shareType = getShareType();
	var shareId = getShareId();
	
//	requestService("/xczh/share/courseShare",{
//		shareId : shareId,
//		shareType:shareType
//	},function(data) {
//		
//		if(data.success){
//			
//			description = data.description.stripHTML();
//			link = data.link;
//			
//			if(shareType==1){
//				gradeName = data.gradeName;
//				smallImgPath = data.smallImgPath;
//			}else if(shareType==2){
//				gradeName = data.name;
//				smallImgPath = data.headImg;
//			}
//		}
//	})
	
	$(".share").show();
});

$(".share_cancel").click(function(){
	$(".share").hide();
});

$(".share_cancel").click(function(){
	$(".weixin_ceng").hide();
});


//点击微信出现提示框
$(".share_to_one").click(function(){
	$(".weixin_ceng").show();
});

$(".weixin_ceng").click(function(){
	$(".weixin_ceng").hide();
});



//微博分享 
document.getElementById('weiboShare').onclick = function(e){
	
	    var  p = {
	        url: link,/*获取URL，可加上来自分享到QQ标识，方便统计*/
	        title :'中医好课程' + '《' + gradeName + '》',/*分享标题(可选)*/
	        pic : smallImgPath /*分享图片(可选)*/
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
	        url:link,/*获取URL，可加上来自分享到QQ标识，方便统计*/
	        desc: '熊猫中医', /*分享理由(风格应模拟用户对话),支持多分享语随机展现（使用|分隔）*/
	        title :'中医好课程' + '《' + gradeName + '》',/*分享标题(可选)*/
	        summary : description,/*分享描述(可选)*/
	        pics : smallImgPath  /*分享图片(可选)*/
	    };
	    var s = [];
	    for (var i in p) {
	        s.push(i + '=' + encodeURIComponent(p[i] || ''));
	    }
	    var _src = "http://connect.qq.com/widget/shareqq/index.html?" + s.join('&') ;
	    window.open(_src);
};

//qq空间分享 
document.getElementById('qqShare0').onclick = function(e){
	    var  p = {
		        url:link,/*获取URL，可加上来自分享到QQ标识，方便统计*/
		        desc: '熊猫中医', /*分享理由(风格应模拟用户对话),支持多分享语随机展现（使用|分隔）*/
		        title : '中医好课程' + '《' + gradeName + '》',/*分享标题(可选)*/
		        summary : description,/*分享描述(可选)*/
		        pics : smallImgPath  /*分享图片(可选)*/
		 };
	    var s = [];
	    for (var i in p) {
	        s.push(i + '=' + encodeURIComponent(p[i] || ''));
	    }
	    var _src = "https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?" + s.join('&') ;
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
/*
 * 如果是微信浏览器的话在进行加载这部分函数
 */
if(is_weixn()){
	
	
	var shareBack = getQueryString("shareBack");
	
	function pushHistory() {
	    var state = {
	        title: "title",
	        url: "#"
	    };
	    window.history.pushState(state, "title", "#");
	}
	
	if(shareBack == 1){
		pushHistory();
	}
	
	
	var ccontrollerAddress = "/bxg/wxjs/h5JSSDK";
	var urlparm = {
		url: window.location.href
	}
	var signObj = "";
	requestService(ccontrollerAddress, urlparm, function(data) {
		if (data.success) {
			signObj = data.resultObject;
		    console.log(JSON.stringify(signObj));
		}	
	},false)	

	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: signObj.appId, // 必填，企业号的唯一标识，此处填写企业号corpid
	    timestamp: signObj.timestamp, // 必填，生成签名的时间戳
	    nonceStr:signObj.noncestr, // 必填，生成签名的随机串
	    signature: signObj.sign,// 必填，签名，见附录1
	    jsApiList: [
			'checkJsApi',
			'onMenuShareTimeline',
			'onMenuShareAppMessage',
			'onMenuShareQQ',
			'onMenuShareWeibo',
			'onMenuShareQZone',
			'hideMenuItems',
			'showMenuItems',
			'closeWindow'
	    ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
//    title : gradeName,/*分享标题(可选)*/
//    summary : description,/*分享描述(可选)*/
//    pics : smallImgPath  /*分享图片(可选)*/
	
	wx.ready(function () {
		 var d1 = description.replace(/&nbsp;/g,"");
		
		 if(shareBack == 1){
			 
			 if (typeof window.addEventListener != "undefined") {
				  window.addEventListener("popstate", function(e) {
				  	     wx.closeWindow();
				  }, false);
			 } else {
				  window.attachEvent("popstate", function(e) {
				         wx.closeWindow();
				  });
			 }
		 }
		//发送到朋友
		wx.onMenuShareAppMessage({
		    title: '中医好课程' + '《' + gradeName + '》', // 分享标题
		    desc: d1, // 分享描述
		    link:link, // 分享链接
		    imgUrl: smallImgPath, // 分享图标
		    type: '', // 分享类型,music、video或link，不填默认为link
		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		    success: function () {
		        // 用户确认分享后执行的回调函数
		    	$(".weixin_ceng").hide();
		    	$(".share").hide();
		    },
		    cancel: function () {
		        // 用户取消分享后执行的回调函数
		    	$(".weixin_ceng").hide();
		    	$(".share").hide();
		    }
		});
		//发送到朋友圈
		wx.onMenuShareTimeline({
		    title:'中医好课程' + '《' + gradeName + '》', // 分享标题
		    link:link, // 分享链接
		    imgUrl: smallImgPath, // 分享图标
		    success: function () {
		        // 用户确认分享后执行的回调函数
		    	$(".weixin_ceng").hide();
		    	$(".share").hide();
		    	
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
		    title: '中医好课程' + '《' + gradeName + '》', // 分享标题
		    desc: d1, // 分享描述
		    link:link, // 分享链接
		    imgUrl: smallImgPath, // 分享图标
		    success: function () {
		       // 用户确认分享后执行的回调函数
		    	$(".weixin_ceng").hide();
		    	$(".share").hide();
		    	//alert("分享成功");
		    },
		    cancel: function () {
		       // 用户取消分享后执行的回调函数
		    	///alert("取消分享");
		    	$(".weixin_ceng").hide();
		    	$(".share").hide();
		    }
		});
	})    
}





