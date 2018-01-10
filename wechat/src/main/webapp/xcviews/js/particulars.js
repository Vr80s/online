
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
var multimediaType = getQueryString("multimedia_type");

/**
 * 判断是否需要跳转到pc网页
 */
h5PcConversions(true,course_id);

/*if(localStorage.getItem("userId")==null){
    location.href ="/xcviews/html/share.html?course_id="+course_id;
}*/


/**
 * 视频id   videoId
 */
var videoId = "";var teacherId;var teacherName;var courseHead ="";
var roomNumber="";var multimedia_type ="";var result = "";
var chapterId ="";var watchState="";
var vId ="";
/*
 * 加载评论列表
 */
var pageNumber = 1;
/**
 * 
 * 获取评论列表
 * 
 */
var loginName = localStorage.loginName;
var userId =  localStorage.userId;
var smallHeadPhoto =  localStorage.smallHeadPhoto;
function  getVideoCriticize(pageNumber,vId){
	//视频id :2c9aec355eb943f5015ecb4221f30005
	//用户名：15936216273
	//分页参数：pageSize
	//生产
//	var dataParams ={
//		pageSize:pageSize,
//		videoId:videoId,	
//		name:name,
//		pageNumber:20
//	}
	//测试
	var dataParams ={
		pageNumber:pageNumber,
		videoId:vId,	
		loginName:loginName,
		pageSize:10
	}
	requestService("/bxg/criticize/getVideoCriticize", 
			dataParams, function(data) {
		var items = data.resultObject.items;
		if(items.length>0){
			var str = '';
			for (var i = items.length - 1; i >= 0; i--) {
				  var obj = items[i]; 
				  str += "<div class='discuss_main_one'>"+
					"<div class='discuss'>"+
						"<img src='"+obj.smallPhoto+"' alt='' class='discuss_img' />"+
						"<p>"+obj.userName+"</p>"+
					"</div>"+
					"<div class='discuss_size'>"+obj.content+"</div>";
				  
					//<!-- 回复模块啦 -->
					if(stringnull(obj.response)){
						str += "<div class='discuss_size_reply'>熊猫中医回复："+obj.response+"</div>";
					}
					str +="<div class='discuss_time'>"+
						"<div class='discuss_time_size'>"+obj.createTime.replace(/-/g,".")+"</div>"+
						"<div class='praise'>"+
							"<span id='praise' class='praise0' title="+obj.id+">";
							 if(obj.isPraise){
								 str += "<img src='../images/yizan.png' class='praise-img' />"; 
							 }else{
								 str += "<img src='../images/zan01.png' class='praise-img' />";
							 }
							 str +="</span>"+
							"<span id='praise-txt'>"+obj.praiseSum+"</span>"+
							"<span id='add-num'><em>+1</em></span>"+
						"</div>"+
					    "</div>"+
					  "<div class='both'></div>"+
				    "</div>";
	        }		
			if (pageNumber == 1){
	         	$("#new_chatmsg").html(str);
	         	setTimeout(function(){
	     			$(".discuss_main").mCustomScrollbar('update').mCustomScrollbar("scrollTo","bottom");
	             },50);
	        }else{
	        	$("#new_chatmsg").prepend(str);
         		setTimeout(function(){
         			$(".discuss_main").mCustomScrollbar('update').mCustomScrollbar("scrollTo","-=500");
                },50);
	        }
			
			/**
			 * 点赞和取消赞
			 */
			$(".praise0").click(function(){
				var praise_img = $(this).find("img");
				
				var criticizeId =  $(this).attr("title");
				
				var praise_txt = $(this).next();
				var text_box = $(this).next().next();
//				var text_box = $("#add-num");
//				var praise_txt = $("#praise-txt");
				var num=parseInt(praise_txt.text());
				var falg = true;
				if(praise_img.attr("src") == ("../images/yizan.png")){  //取消赞
					$(this).html("<img src='../images/zan01.png' id='praise-img' class='animation' />");
					praise_txt.removeClass("hover");
					text_box.show().html("<em class='add-animation'>-1</em>");
					$(".add-animation").removeClass("hover");
					num -=1;
					praise_txt.text(num)
					falg = false;
				}else{												  //点赞
					$(this).html("<img src='../images/yizan.png' id='praise-img' class='animation' />");
					praise_txt.addClass("hover");
					text_box.show().html("<em class='add-animation'>+1</em>");
					$(".add-animation").addClass("hover");
					num +=1;
					praise_txt.text(num);
					falg = true;
				}
				
				//Boolean isPraise, String criticizeId
			     var dataParams ={
			    		 isPraise: falg,
			    		 criticizeId:criticizeId
			     }
				 requestService("/bxg/criticize/updatePraise",dataParams, function(data) {
			    	  if(data.success){
			    		  console.log("点赞成功");
			    	  }	  
				 },false)
			});
		}
	},false)
}
/**
 * 点击初始化视频的方法：
 * @param videoId
 */
function chZJ(videoId,chapterId,vid){
	/**
	 * 清空评论区的列表
	 */
	$("#new_chatmsg").html("");
	/*videoId = videoId;
	chapterId = chapterId;*/
	sessionStorage.setItem("videoId",videoId);
	sessionStorage.setItem("chapterId",chapterId);
	sessionStorage.setItem("vid",vid);
	/**
	 * 请求代码啦
	 */
//	屏幕分辨率的高：window.screen.height 
//	屏幕分辨率的宽：window.screen.width 
	var playerwidth = window.screen.width;
	var playerheight = 8.95*21.8;
//	var playerwidth = 300;
//	var playerheight = 300;
	console.log(playerwidth);
	var dataParams = {
		playerwidth:playerwidth,	
		playerheight:playerheight,
		videoId:videoId,
		multimedia_type:multimediaType
	}
	requestService("/bxg/ccvideo/commonCourseStatus", 
			dataParams, function(data) {
		if(data.success){
			var playCodeStr = data.resultObject;
			var playCodeObj = JSON.parse(playCodeStr);
			console.log(playCodeObj.video.playcode);
			
			$("#ccvideo").html(playCodeObj.video.playcode);
			//"<script src=\"http://p.bokecc.com/player?vid=C728945447E95B7F9C33DC5901307461&siteid=B5E673E55C702C42&autoStart=true&width=360&height=195&playerid=E92940E0788E2DAE&playertype=1\" type=\"text/javascript\"><\/script>"
		 	/**
	    	 * 初始化评论区
	    	 */
	    	//getVideoCriticize(1,vid);
			
		}else{
    		$(".history_bg").show();
		}
	},false);
}

/**
 * 请求这个课程的详情
 */
requestService("/bxg/bunch/detail", {course_id : course_id}, function(data) {

    result = data.resultObject;
    //视频id
    videoId = result.directId;
    //章节id
    chapterId = result.chapterId;
    //视频的主键id
    vId = result.vId;
    
    watchState = result.watchState;
    //假装免费
    watchState = 0;
    result.watchState =0;
    
    /*
     * 如果videoId不存在的时候，需要显示视频正在来的路上
     */
    if(!stringnull(videoId)){
    	$("#no_video").text("视频正在赶来的路上");
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
    	 * 初始化CC视频
    	 */
    	chZJ(videoId,chapterId,vId);
    	/**
    	 * 存在视频id，隐藏视频正在赶来的路上
    	 */
    	$(".no_video").hide();
    	
    	$(".li1").click(function(){
    		$(".opc").hide();
    	});
    	if(result.watchState == 1){  //goto 付费页面
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
    
    /**
     * 关注
     */
    if(result.isfocus == 1){
        $(".guanzhu2").show();
        $("#guanzhuimg").attr("src","/xcviews/images/guanzhu_03.png");
    }else if(result.isfocus == 0){
        $(".guanzhu1").show();
        $("#guanzhuimg").attr("src","/xcviews/images/guanzhu_03.png");
    }

    /**
     * 为详情页面添加数据
     */
    $("#headImg").attr("src",result.headImg); //主播头像
    var children = $("#zhibopinglun [class='p1']").text(result.gradeName);
    var children = $("#zhibopinglun [class='p2']").text(result.name);
    
    //课程详情
    $(".anchor_center").html(result.description);

     /*
      * 粉丝数和礼物数
      */
     $(".yx_details_size span:eq(0)").html(result.countFans);
     $(".yx_details_size span:eq(1)").html(result.countGift);
//      var children = $("#zhiboxiangqing [class='p1']").text(result.gradeName);
//      var children = $("#zhiboxiangqing [class='p2'] span").text(result.name);
//		var children = $("#zhiboxiangqing [class='p3'] span").text(result.roomNumber); 
},false)	

/**
 * 
 * 发送评论
 * 
 */
$("#sendChat").click(function() {
    //var userInfo  = VHALL_SDK.getUserinfo();
    var text = $("#mywords").html();
    if(!stringnull(text) || text == "聊聊您的想法吧"){
    	return;
    }
    $("#mywords").html("")
    var msg = null;
    /*
     * 保存评论
     */
//    保存评论需要的字段：
//    评论内容，创建时间、用户id、章节id、视频id
    var chapterId = sessionStorage.getItem("chapterId");
    var vid = sessionStorage.getItem("vid");
    
    var dataParams = {
        content:text,	
        chapterId:chapterId,
        videoId:vid,
        courseId:course_id
    }
    if(text.length>3000){
  	  alert("评论长度太长了");
  	  return;
    }
//    var dataParams = {
//        content:text,	
//        chapterId:"2c9aec345eb431d8015eb6a297790055",
//        videoId:"2c9aec355eb943f5015ecb4221f30005"
//    }
    var str ="";
    requestService("/bxg/criticize/saveCriticize", 
			dataParams, function(data) {
  	  if(data.success){
			  $("#mywords").val('');
			  
			  //getVideoCriticize(1,vid);
			  
	 	      $(".discuss_main").mCustomScrollbar("scrollTo","bottom","0");
  	  }else{
  		  alert(data.errorMessage);
  	  }
    })
});

    
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
            chZJ(videoId,chapterId);
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
		                    "<li class='chapter_main_cen_ul_li'  onclick=chZJ('"+si[f].videoId+"','"+si[f].chapterId+"','"+si[f].vid+"')>\n" +
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
	    desc: result.courseDescription, // 分享描述
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
	    	//alert("取消分享");
	    	$(".weixin_ceng").hide();
	    	$(".share").hide();
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
	    desc: result.courseDescription, // 分享描述
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
