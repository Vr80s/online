
/**
 * 如果来自微信浏览器，隐藏微信分享
 */
if(!is_weixn()){
    $(".weixin_li").remove();
}

var course_id = getQueryString("courseId");
/**
  * 视频id
  */
var videoId = "";var teacherId;var teacherName;
var courseHead ="";var roomNumber="";var lineState =1;
var result="";
//统一提交的方法
	requestService("/bxg/live/liveDetails", {course_id : course_id}, function(data) {
		if (data.success) {
            result = data.resultObject;
            //视频id
            videoId = result.directId;
            watchState = result.watchState;
            
            teacherId=result.userId;
            teacherName=name;
            /**
             * 这里判断是否需要密码确认和是否付费
             */
            $("#userId").val(result.userId);

            $("#teacherId").val(result.userId);
            courseHead = result.smallImgPath;
            
            $(".details_size span:eq(1)").html(result.giftCount);
            $(".details_size span:eq(0)").html(result.learndCount);
            /**
             * 关注
             */
            if(result.isfocus == 1){
                $(".guanzhu2").show();
            }else if(result.isfocus == 0){
                $(".guanzhu1").show();
            }
            lineState =result.lineState;
            /**
             * 判断是直播呢，还是回放呢
             *  显示正在直播和直播回放
             */
            if(result.lineState == 3){  //隐藏送礼
            	//直播回放
            	$("title").text("直播回放");mywords
            	$(".history_span").text("直播回放");
            	$("#mywords").css("width","11.4rem");
            	$("#face").css("top","1.45rem");
            	$("#face").css('left','0.06rem');
            	
            	$(".give_a1").hide();
            	$(".give_a1_img").hide();
            	$(".give_a1_span02").hide();
            	$(".poson").css('right','-1.4rem');
            	
                $(".send_img").css('background-size','100% 100%');
               
            	$("#mywords").click(function() {
            		$(".give_a1").hide();
            		$(".give_a1_img").hide();
                });
            	$(".details_size").hide();
            	$(".poson").css('right','-2.1rem');
            	$(".div_input").css('width','13.2rem');
            	$(".coze_bottom_input").css('margin-left','0rem');
            	$(".give_a01").css('margin-left','0.5rem');
            	$("#face").click(function() {
            		$(".coze_bottom").css('bottom','7.1rem');
            	});
            	
            	
            	$(".li1").click(function() {
            		$(".give_a1_span02").css("display","none");
            		$(".give_a1_span02 img").hide();
            		
            		$(".give_a1").hide();
            		$(".give_a1 img").hide();
            	});
            	
            	
            	$(".li2").click(function() {
            		$(".give_a1_span02 img").hide();
            		$(".div_input").css('margin-left','2.3rem');
            	});
            	    
            	/*点击发送*/
            	$("#sendChat").click(function() {
        		    $(".face_img01").css('background','url(/xcviews/images/face.png) no-repeat');
                    $(".face_img01").css('background-size','100% 100%');
                    $(".send_img").css('background-size','100% 100%');
                    $(".give_a1").hide();
                    $(".give_a1").css("display","none");
                    $(".give_a1_span02").hide();
                    
                    $(".give_a1_span02 img").hide();
                    $(".coze_bottom").css("bottom","0");
                });	
            }else{
            	//正在直播
            	$("title").text("直播中");
            	$(".history_span").text("直播中");
                $("#mywords").click(function() {

                });
                // 点击送礼开始  --显示送礼列表
                $(".give_a1").click(function() {

                    requestService("/bxg/bs/getBalanceTotal", {test:1}, function(data) {
                        if (data.success) {
                            var result = data.resultObject;
                            $("#xmbShowSpan").html(result.balanceTotal);
                        }else{
//                          alert("熊猫币余额获取失败！请稍后再试");
							$(".vanish2").show();
							setTimeout(function(){$(".vanish2").hide();},1500);
                        }

                    });
                    $(".send_gifts").show();
                });
            }
            
            //点击直播回放时的input mywords
           	$("#mywords").click(function() {
           			/*$(".send_img").css('background','url(/xcviews/images/jiantou01.jpg) no-repeat');*/
                    $("#sendChat").css("background-size","100% 100%");
            });
            //点击直播回放时的发送按钮  
            $("#sendChat").click(function() {
           			/*$(".send_img").css('background','url(/xcviews/images/jiantou02.jpg) no-repeat');*/
                    $(".send_img").css("background-size","100% 100%");
            });
          
            /**
             * 为详情页面添加数据
             */
            $("#headImg").attr("src",result.headImg);
             $(".details_chat1").attr("src",result.headImg);
            var children = $("#zhiboxiangqing [class='p1']").text(result.gradeName);
            var children = $("#zhiboxiangqing [class='p2'] span").text(result.name);
            var children = $("#zhiboxiangqing [class='p3'] span").text(result.roomNumber);
            var children = $("#zhibopinglun [class='p1']").text(result.gradeName);
            var children = $("#zhibopinglun [class='p2']").text(result.name);
            
            $(".anchor_center").text(result.description);
            
          //视频id不等于null的时候
          if(stringnull(videoId)){
             chZJ(videoId,watchState);
          }
		}
	},false)

    /**
     * 调转到用户主页啦
     */
    function userIndex(){
    	
    	location.href = "/xcviews/html/personage.html?lecturerId="+teacherId;
    }
            
function  goPay() {

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
//          alert("提交订单错误！请稍后再试！");
			$(".vanish4").show();
			setTimeout(function(){$(".vanish4").hide();},1500);
        }

    });
}


function subscribe(){

    var regPhone = /^1[3-578]\d{9}$/;
    var flag=true;
    if ($("#mobile").val().trim().length === 0) {
       alert("请输入手机号！");
        flag= false;
    } else if (!(regPhone.test($("#mobile").val().trim()))) {
        alert("手机号格式错误！");
        flag= false;
    }
    if(!flag){
    	return false;
	}
    requestService("/bxg/common/subscribe", {course_id : course_id,mobile:$("#mobile").val()}, function(data) {
        if (data.success) {
            var result = data.resultObject;
            $(".buy_center1").show();
            $("#subscribeA").html("您已预约");

        }else{
            alert("预约失败！"+data.msg);
        }

    });
}


function ckProPrice() {
    //判断商品价格
    var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
    if ($("#actualPay").val() == ""&&$("#actualPayHide").val() == "") {
        alert("请输入赠送金额！");
        return false;
    } else {
    	
    	
    	if ($("#actualPay").val() != ""){
            if (!reg.test($("#actualPay").val())) {
                alert("价格不合法！");
                return false;
            } else {
                return true;
            }
        }

        if ($("#actualPayHide").val() != ""){
            if (!reg.test($("#actualPayHide").val())) {
                alert("价格不合法！");
                return false;
            } else {
                return true;
            }
        }
    }
}
/*
 * 得到焦点失去焦点
 */
$('#searchKey').focus(function() { 
	$('#lbSearch').text(''); 
}); 

$('#searchKey').blur(function() { 
	var str = $(this).val(); 
	str = $.trim(str); 
	if(str == '') 
	$('#lbSearch').text('搜神马？'); 
}); 

/*
 * 当这个值发生变化了，进行。
 */
$('#searchKey').change(function() { 
	var str = $(this).val(); 
	str = $.trim(str); 
	if(str == '') 
	$('#lbSearch').text('搜神马？'); 
}); 



/*function ds(payType){*/
function ds(){
    if(!ckProPrice()){
        return;
    }
    var actualPay;
    if ($("#actualPay").val() != ""){
        actualPay=$("#actualPay").val();
    }else{
        actualPay=$("#actualPayHide").val();
    }
    var giftId=$("#sid").val();
    location.href="/xcviews/html/exceptionalpay.html?"+"receiver="+$("#userId").val()+"&rewardId="+giftId+"&liveId="+course_id+"&actualPay="+actualPay+"&returnUrl="+document.URL;
}

function cdPrice(price,sid){
    $("#actualPay").val("")
    $("#actualPayHide").val(price);
    $("#sid").val(sid);
}

//初始化打赏价格列表

requestService("/bxg/reward/list", {pageNumber:1,pageSize:100}, function(data) {
	  if (data.success) {
	         var result = data.resultObject;
	         var html = "";
	         for (var i = 0; i < result.length; i++) {
	             if(result[i].price>0){
	                 html += "<div class='give_lable1' onclick='cdPrice(" + result[i].price + "," + result[i].id + ")'> <label for=''  class='lable1_put'> <input name='give' type='radio' /> </label> <span class='give_money1'>￥<span>" + result[i].price + "</span></span> </div>";
	             }else{
	                 $("#sid").val(result[i].id);
	                 sid=result[i].id;
	             }

	         }
	         $("#cdPrices").html(html);
	         $(".give_lable1:eq(2)").click();
	     }
},false)


/**
 * 初始化礼物列表1
 */

requestService("/bxg/gift/list",{pageNumber:1,pageSize:100},function(data) {
	if (data.success) {
        var result = data.resultObject;
        var html = "";
        for (var i = 0; i < result.length; i++) {
            if(result[i].price>0){
                html += "<li><a href='javascript: ;'><div class='gifts_div'><img src='"+result[i].smallimgPath +"' alt='' /></div><div class='gift_p'><p giftId='"+result[i].id+"' class='liwu' style='font-size:0.6rem;color:#666;'>"+result[i].name+"</p><p class='jiage' style='font-size:0.6rem;color:#666;'>"+"<img src='/xcviews/images/top_up01.png' style='width: 0.425rem;margin-top: -0.1rem;display: inline;margin-right: 0.2rem;vertical-align: middle;' />"+result[i].price+"</p></div></a></li>";
            }else{
                html += "<li><a href='javascript: ;'><div class='gifts_div'><img src='"+result[i].smallimgPath +"' alt='' /></div><div class='gift_p'><p giftId='"+result[i].id+"' class='liwu' style='font-size:0.6rem;color:#666;'>"+result[i].name+"</p><p class='jiage' style='font-size:0.6rem;color:#666;'><img src='/xcviews/images/top_up01.png' style='width: 0.45rem;margin-top: -0.1rem;display: inline;margin-right: 0.2rem;vertical-align: middle;' />0</p></div></a></li>";
            }
        }
        $(".gift_ul_li").html(html);
    }
},false);

/**
 * 刷新礼物排行榜
 */
function refreshGiftRanking(){
	
	requestService("/bxg/gift/rankingList",{pageNumber:1,pageSize:10,liveId:course_id,type:0},function(data) {
		if (data.success) {
			   var list=data.resultObject;
	            var html="";
	            for (var i=0;i<list.length;i++){

	                var pName="";
	                if(i==0){
	                    pName="状元";
	                }else if(i==1){
	                    pName="榜眼";
	                }else if(i==2){
	                    pName="探花";
	                }else{
	                	pName="第"+(i+1)+"名";
	                }

	                var pLogo="";
	                if(i==0){
	                    pLogo="/xcviews/images/01_03.png";
	                }else if(i==1){
	                    pLogo="/xcviews/images/02_03.png";
	                }else if(i==2){
	                    pLogo="/xcviews/images/03_03.png";
	                }

	                html+="<div class='leaderboard_list'>\n";
	                
	                    if(i==0 || i==1 || i==2){
	                    	html+="<div class='leaderboard_left'>\n";
	                    	html+="<img src='"+pLogo+"' alt='' style='width: 1.6rem;height:0.9rem' /><br />";
	                    	html+="<span>"+pName+"</span>";
	                    }else{
	                    	html+="<div class='leaderboard_left' style='line-height: 1.8rem;'>\n";
	                    	html+="<span>"+pName+"</span>";
	                    } 
	               html+="</div>\n" +
	                    "<div class='leaderboard_center' title="+list[i].userId+" >\n" +
	                    "<img src='"+list[i].smallHeadPhoto+"' alt='' />\n" +
	                    "<div class='leaderboard_center_size'>\n" +
	                    "<p class='p1'>"+list[i].name+"</p>\n" +
	                    "<p class='p2'>\n" +
	                    "贡献&nbsp;&nbsp;<span>"+list[i].giftCount+"</span>\n" +
	                    "</p>\n" +
	                    "</div>\n" +
	                    "</div>\n" +
	                    "<img src='' alt=''\n" +
	                    "class='leaderboard_list_right' />\n" +
	                    "<div class='both'></div>\n" +
	                    "</div>";

	            }
	            /*html+="<div style="height:5rem;"></div>";*/
	            $("#phbList").html(html);
	            
	            /**
	             * 点击查看
	             */
	            $(".leaderboard_center").click(function(){
	            	var teacherId = $(this).attr("title");
	            	location.href = "/xcviews/html/personage.html?lecturerId="+teacherId;
	            })
	    }
	},false);
	
}

requestService("/bxg/live/getMoneySum", {id: course_id}, function(data) {
	 var data = data.resultObject;
     $("#moneySum").html(data);
},false)


//微博分享 
document.getElementById('weiboShare').onclick = function(e){
	    var  p = {
	        url: getServerHost()+"/wx_share.html?courseId="+course_id,/*获取URL，可加上来自分享到QQ标识，方便统计*/
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
	        url: getServerHost()+"/wx_share.html?courseId="+course_id,/*获取URL，可加上来自分享到QQ标识，方便统计*/
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

//qq空间分享 
document.getElementById('qqShare0').onclick = function(e){
	    var  p = {
	        url: getServerHost()+"/wx_share.html?courseId="+course_id,/*获取URL，可加上来自分享到QQ标识，方便统计*/
	        desc: '中医传承', /*分享理由(风格应模拟用户对话),支持多分享语随机展现（使用|分隔）*/
	        title : result.gradeName,/*分享标题(可选)*/
	        summary : result.description.stripHTML(),/*分享描述(可选)*/
	        pics : result.smallImgPath  /*分享图片(可选)*/
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
if(isWeiXin()){
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
			'showMenuItems'
	    ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	var domain = window.location.protocol+"//"+document.domain;
	
	
	wx.ready(function () {
		//发送到朋友
		wx.onMenuShareAppMessage({
		    title: result.gradeName, // 分享标题
		    desc: result.description.stripHTML(), // 分享描述
		    link:domain+"/wx_share.html?courseId="+course_id, // 分享链接
		    imgUrl: result.smallImgPath, // 分享图标
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
		    title: result.gradeName, // 分享标题
		    link:domain+"/wx_share.html?courseId="+course_id, // 分享链接
		    imgUrl: result.smallImgPath, // 分享图标
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
		    title: result.gradeName, // 分享标题
		    desc: result.description.stripHTML(), // 分享描述
		    link:domain+"/wx_share.html?courseId="+course_id, // 分享链接
		    imgUrl: result.smallImgPath, // 分享图标
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