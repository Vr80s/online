

function is_weixn(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
        return false;
    }
}

/**
 * 去充值页面
 */
$(".footer_div_btn").click(function(){
	
	location.href = "/xcview/html/recharges.html";
})


var orderNo = "";
var type =""; //判断课程类别，支付使用

//获取课程ID跳转相应页面页面
var courseId = getQueryString('courseId');
//传ID courseId为接口的课程ID

var allCourse;
requestService("/xczh/order/getByOrderId",{
	orderId : courseId	
},function(data) {
	$("#purchase_details").html(template('data_details',data.resultObject.allCourse[0]));
	$(".purchase_list_one_right .give_price").html(data.resultObject.allCourse[0].actualPay)

	var currentPriceXMB = data.resultObject.actualPay;//这时人民币
	
	allCourse = data.resultObject.allCourse;
	
	orderNo =data.resultObject.orderNo;
	//要支付的毛笔
	$(".pay_xmb").html(currentPriceXMB);
	/**
	 * 获取熊猫币余额,判断是否显示去充值
	 */
	requestService("/xczh/manager/getWalletEnchashmentBalance",null,function(data) {
		if(data.success){
			var xmbye = data.resultObject;
			$("#xmb_ye").html(xmbye);
			//判断当前购买的要消耗的熊猫币  是否大于 自己的熊猫币余额
			if(xmbye<currentPriceXMB){
				$(".footer_div").hide();
				$(".footer_div_btn").show();
			}else{
				$(".footer_div").show();
				$(".footer_div_btn").hide();
			}
		}
	})
})

/**
 * 点击去支付
 */
function  goPay() {
    /**
     * 判断支付类型
     */
	var payClassFalg =  $(".ul_li0").find("img").attr("class");
	
	var payType = ""; //支付类型   1 熊猫币支付   2支付宝支付  3 微信支付
	if(payClassFalg == "BTCpanda"){ 
		payType = 1;
	}else if(payClassFalg == "alipay_img"){
		payType = 2;
	}else if(payClassFalg == "weixin_img"){
		payType = 3;
	}
	
    if(payType==1){ //熊猫币支付
    	
    	requestService("/xczh/iap/appleIapPayOrder", {order_no:orderNo}, function(data) {
   		    var params = data.resultObject;
            if(data.success){
            	
            	alert("熊猫币购买成功！请这是跳转页面");
            }else{
              alert(data.errorMessage);
            }
		},false)
    	
    }else if(payType==2){ //支付宝支付
    	/**
    	 * 支付宝在字符前判断此订单中的课程用户是否已经购买过了
    	 */
    	requestService("/xczh/order/orderIsExitCourseIsBuy", {orderId:getQueryString("courseId")}, function(data) {
    		 var params = data.resultObject;
    		 if(data.success){
             	 if(is_weixn()){
             		  //去另一个页面引导用户去外部浏览器打开
                      location.href = "/xcview/html/wechat_alipay.html?type=1&orderId="+getQueryString("courseId")+"&redirectUrl="+getgetRedirectUrl(allCourse,true);
                      return;
                  }
                  jmpPayPage("/xczh/alipay/pay",payType,"orderId="+getQueryString("courseId"),null);
             }else{
               alert(data.errorMessage);
             }
 		},false)
// 	    PC(1, "pc"),
// 	    H5(2, "h5"),
// 	    ANDROID(3, "android"),
// 	    IOS(4, "ios"),
// 	    OFFLINE(5, "线下"),
// 	    WORKER(6, "工作人员");		
 		
    }else if(payType==3){ //微信支付
        var btype=   localStorage.getItem("access");
        var openId=   localStorage.getItem("openid");
        var orderForm = 2;
        if(btype=='wx'){
            orderForm=3;
        }else if(btype=='brower'){ //h5
            orderForm=4
        }
        var strparam = "orderFrom="+orderForm+"&orderId="+getQueryString("courseId");
        if(stringnull(openId)){
        	strparam+="&openId="+openId;
        }
        alert("----------------"+strparam);
        jmpPayPage("/xczh/pay/wxPay",payType,strparam,getgetRedirectUrl(allCourse,false));
    }
}
/**
 * 回调页面啦
 * @param allCourse
 * @returns {String}
 */
function getgetRedirectUrl(allCourse,falg){
	//alert(window.location.host);
	
	
	
//	var redirectUrl="";
//	if(allCourse.length>1){  //如果此订单中有多个订单，返回到我的订单页面
//	    redirectUrl="/xcview/html/my_study.html";
//	    return redirectUrl;
//	}else{
//	    var c=allCourse[0];  //判断此课程是预约呢、直播、点播的课程
//	    
//	    alert(c.collection);
//	    if(c.collection){  //live_select_album.html?course_id=123
//	    	 redirectUrl="/xcview/html/live_select_album.html?course_id="+c.id;
//	    }else{
//	    	if(c.type == 1 || c.type == 2){ //直播状态1 视频 2 音频 3 直播 4下线班 
//	   	 	    redirectUrl="/xcview/html/live_audio.html?my_study="+c.id;
//	   	    }else if(c.type == 3){
//	   	    	redirectUrl="/xcview/html/live_play.html?my_study="+c.id;
//	   	    }else if(c.type == 4){
//	   	    	redirectUrl="/xcview/html/live_class.html?my_study="+c.id;
//	   	    }
//	    }
//	    return redirectUrl;
//	}
	var c=allCourse[0];
	return "xcview/html/buy_prosperity.html"+c.id;
}





