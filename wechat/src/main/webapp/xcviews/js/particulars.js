
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

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

var course_id=getQueryString("courseId");

/**
 * 判断是否需要跳转到pc网页
 */
h5PcConversions(true,course_id);

/*if(localStorage.getItem("userId")==null){
    location.href ="/xcviews/html/share.html?course_id="+course_id;
}*/
/**
  * 视频id
  */
var videoId = "";var teacherId;var teacherName;var courseHead ="";
var roomNumber="";var multimedia_type ="";var result = "";
var watchState="";

	requestService("/bxg/bunch/detail", {course_id : course_id}, function(data) {

        result = data.resultObject;
        //视频id
        videoId = result.directId;
        watchState = result.watchState;
        /*
         * 如果videoId不存在的时候，需要显示视频正在来的路上
         */
        if(!stringnull(videoId)){
        	
        	$(".no_video").show();
        	$(".li1").click(function(){
        		$(".opc").show();
        	});
        	var b_smallImgPath ="url("+result.smallImgPath+") no-repeat";
         	$(".details").css("background",b_smallImgPath);
        	
        	/*
             * 默认给个3，让其不加载视频
             */
        	watchState = 3;
        }else{
        	/**
        	 * 存在视频id，隐藏视频正在赶来的路上
        	 */
        	$(".no_video").hide();
        	$(".li1").click(function(){
        		$(".opc").hide();
        	});
        	if(result.watchState == 1){  //goto 付费页面
             	//background: url(http://test-www.ixincheng.com/web/images/defaultHead/16.png) no-repeat;
             	var b_smallImgPath ="url("+result.smallImgPath+") no-repeat";
             	$("#bug_bg").css("background",b_smallImgPath);
                $("#buyDiv").show();
                $("#buyPirce").html(result.currentPrice);
             }else if(result.watchState == 2){  //goto 需要密码页面
                $("#passwordDiv").show();
             }
        }
        
        //用户id
        teacherId=result.userId;
        teacherName=name;
        /*
         * 显示title
         */
        multimedia_type = result.multimediaType;
        if(multimedia_type == 2){//音频
        	$(".history_span").text("音频详情");
        	$("title").text("音频详情");
        }else{
        	$("title").text("视频详情");
        }
     
        /**
         * 这里判断是否需要密码确认和是否付费
         */
        $("#userId").val(result.userId);
        $("#teacherId").val(result.userId);
        
        roomNumber=result.roomNumber;  
        courseHead = result.smallImgPath;
        
        /*
         * 礼物数和学习人数
         */
        $(".details_size span:eq(0)").html(result.giftCount);
        $(".details_size span:eq(1)").html(result.learndCount);
        
        /**
         * 关注
         */
        if(result.isfocus == 1){
            $(".guanzhu2").show();
            //grabble.png  /xcviews/images/guanzhu_03.png
            $("#guanzhuimg").attr("src","/xcviews/images/guanzhu_03.png");
        }else if(result.isfocus == 0){
            //grabble.png  /xcviews/images/grabble.png
            $(".guanzhu1").show();
            $("#guanzhuimg").attr("src","/xcviews/images/guanzhu_03.png");
        }

        /**
         * 为详情页面添加数据
         */
        $("#headImg").attr("src",result.headImg);
        var children = $("#zhiboxiangqing [class='p1']").text(result.gradeName);
        var children = $("#zhiboxiangqing [class='p2'] span").text(result.name);
        var children = $("#zhiboxiangqing [class='p3'] span").text(result.roomNumber);
        var children = $("#zhibopinglun [class='p1']").text(result.gradeName);
        var children = $("#zhibopinglun [class='p2']").text(result.name);
        
        $(".anchor_center").html(result.description);
    
	},false)	


    
    /**
     * 调转到用户主页啦
     */
    function userIndex(){
    	location.href = "/xcviews/html/personage.html?lecturerId="+teacherId;
    }
    
/**
 * input获取焦点隐藏错误提示
 */
$("#password").focus(function(){
	$('#password').css('border','1px solid #d2d2d2')
	$("#password").removeClass("input02");
	$("#password").addClass("input01");
	$('#password').attr('placeholder','请在此处输入密码');
});
    
    
function enterPassword(){
    requestService("/bxg/common/coursePwdConfirm", {course_id : course_id,course_pwd:$("#password").val()}, function(data) {
        if (data.success) {
        	/**
        	 * 如果密码输入正确的话，需要加载下视频啦。并且显示正确的课程缩略图
        	 */
            $("#passwordDiv").hide();
            watchState=0;
            chZJ(videoId);
		}else{
			$('#password').val('');
			$('#password').css('border','1px solid red')
			$("#password").removeClass("input01");
			$("#password").addClass("input02");
	        $('#password').attr('placeholder','密码有误，请重新输入');
		}
        });
}

function  goPay() {
	//alert("视频正在赶来的路上,请稍后购买");
    var btype=   localStorage.getItem("access")
    var orderFrom;
    if(btype=='wx'){
        orderFrom=3;
    }else if(btype=='brower'){
        orderFrom=4;
    }
    requestService("/bxg/order/save", {courseId : course_id,orderFrom:orderFrom}, function(data) {
        if (data.success) {
            var result = data.resultObject;
            location.href = "/xcviews/html/pay.html?courseId="+course_id+"&orderNo="+result.orderNo+"&orderId="+result.orderId+"&page=1";
        }else{
            alert("提交订单错误！请稍后再试！");
        }

    });
}

function ckProPrice() {
    //判断商品价格
    var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
    if ($("#actualPay").val() == "") {
        alert("请输入赠送金额！");
        return false;
    } else {
        if (!reg.test($("#actualPay").val())) {
            alert("价格不合法！");
            return false;
        } else {
            return true;
        }
    }
}

function cdPrice(price){
    $("#actualPay").val(price);
}
initZJ();
//初始化章节
function initZJ(){

    var html="";
    
    requestService("/bxg/video/getVideos",{courseId: course_id}, function(data) {

        if (data.success) {
            var result = data.resultObject;
            for (var i = 0; i < result.length; i++) {
                var yi=result[i];
                html += "<!--1 start -->\n" +
                    "<div class='chapter_title'>\n" +
                    "<div class='chapter_title_left'><span class='chapter_title_left_img'><img src='../images/dian_03.png' alt='' /></span><span class='chapter_title_left_size'>&nbsp;&nbsp;"+yi.name+"</span></div>\n" +
                    "<div class='chapter_title_right'>共 <span>7</span> 节课</div>\n" +
                    "</div>\n" +
                    "<div class='chapter_main'>\n" ;
                    var er=yi.chapterSons;
                    for(var j=0;j<er.length;j++){
                        html+="<!--2 start-->\n" +
                        "<div class='chapter_main_title'>\n" +
                        "<span>"+er[j].name+"</span>\n" +
                        "<div class='chapter_main_title_bg'></div>\n" +
                        "</div>\n" +
                        "<div class='chapter_main_cen'>\n";
                        var san=er[j].chapterSons;

                    for(var k=0;k<san.length;k++){

                    html+="<!--3 start -->\n" +
                    "<div class='chapter_main_cen_title'>"+san[k].name+"</div>\n" +
                    "<ul class='chapter_main_cen_ul'>\n";


                    var si=san[k].videos;

	                    for(var f=0;f<si.length;f++){
		                    html+="<!--4 start-->\n" +
		                    "<li class='chapter_main_cen_ul_li'  onclick=chZJ('"+si[f].videoId+"')>\n" +
		                    "<div class='chapter_cen_ul_bg'></div>\n" +
		                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+si[f].videoName+"\n" +
		                    /*"<span class='chapter_ul_span'>16:24</span>\n" +*/
		                    "</li>\n" +
		                    "<!--4 end-->\n";
	                    }



                    html+="<div class='both'></div>\n" +
                    "</ul>\n" +
                    "<!--3 end -->\n";
                    }
                    html+="</div>\n" +
                    "<!--2 end-->\n" ;
                    }
                   html+="</div>\n" +
                    "<!--1 end-->";

            }
            $("#zjList").html(html);
        } else {
            alert("章节初始化错误！请稍后再试！");
        }
    
	},false)
    
}

function chZJ(videoId){
    var map;
    
    requestService("/bxg/common/getWeihouSign",{video:videoId}, function(data) {
    	 map  = data.resultObject;
	},false)
    

    $("#video").html("");
    
    var weihouSignInfo ={
    		facedom: "#face",
            textdom: "#mywords",
            app_key: map.app_key,// 第三方app_key
            signedat: map.signedat,// 签名时间戳
            sign: map.sign,// 签名
            email: map.email,
            roomid: map.roomid,// 活动id
            account: map.account,// 第三方用户id
            username: map.username,// 用户昵称
            docContent: "#doc"
    }
    if(watchState == 0){
    	weihouSignInfo.videoContent="#video";
    }
    //weihouSignInfo.videoContent="#video";
    VHALL_SDK.init(weihouSignInfo);
}

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

var domain = window.location.protocol+"//"+document.domain;

wx.ready(function () {
	//发送到朋友
	wx.onMenuShareAppMessage({
	    title: result.gradeName, // 分享标题
	    desc: result.courseDescription, // 分享描述
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
	    	//alert("取消分享");
	    	$(".weixin_ceng").hide();
	    	$(".share").hide();
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
	    desc: result.courseDescription, // 分享描述
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
