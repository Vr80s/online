

function is_weixn(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
        return false;
    }
}

var orderNo = "";
var type =""; //判断课程类别，支付使用

//获取课程ID跳转相应页面页面
var courseId = getQueryString('courseId');
//传ID courseId为接口的课程ID


/**
 * 去充值页面
 */
$(".footer_div_btn").click(function(){
	
	location.href = "/xcview/html/recharges.html?recharges_blck=1&orderId="+courseId;
})

var currentPriceXMB =""; //当前课程价格
var xmbye =""; //用户剩余的熊猫币数量

var allCourse;
requestService("/xczh/order/getByOrderId",{
	orderId : courseId	
},function(data) {
	$("#purchase_details").html(template('data_details',data.resultObject.allCourse[0]));
	$(".purchase_list_one_right .give_price").html(data.resultObject.allCourse[0].actualPay)

	currentPriceXMB = data.resultObject.actualPay;//直接返回的熊猫币
	
	allCourse = data.resultObject.allCourse;
	
	orderNo =data.resultObject.orderNo;
	//要支付的毛笔
	$(".pay_xmb").html(currentPriceXMB);
	/**
	 * 获取熊猫币余额,判断是否显示去充值
	 */
	requestService("/xczh/manager/getWalletEnchashmentBalance",null,function(data) {
		if(data.success){
			
			xmbye = data.resultObject;
			
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
 * 点击支付方式--》进行切换
 */
var aBtn=$('#ul li');
for(i=0;i<aBtn.length;i++){
    $(aBtn[i]).click(function(){
        for(i=0;i<aBtn.length;i++){
	        $(aBtn[i]).removeClass('ul_li0');
	        $(aBtn[i]).addClass('ul_li');
	        $(aBtn[i]).find(".ul_li_bg").show();
        }
        var imgClass = $(this).find("img").attr("class");
        if(imgClass == "BTCpanda"){
        
        	if(xmbye<currentPriceXMB){
				$(".footer_div").hide();
				$(".footer_div_btn").show();
			}else{
				$(".footer_div").show();
				$(".footer_div_btn").hide();
			}
        	
			$("#xmb_rmb").html(currentPriceXMB);
        	$("#currency").html("熊猫币");
        }else{
        	$(".footer_div").show();
			$(".footer_div_btn").hide();
			$("#xmb_rmb").html(currentPriceXMB / 10);
			$("#currency").html("元");
        }
      	$(this).removeClass();
      	$(this).addClass('ul_li0');
       	$(aBtn[i]).find(".ul_li_bg").hide();
    })
}
$(aBtn[0]).click();

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
            	
            	location.href="/xcview/html/buy_prosperity.html?courseId="+getQueryString("courseId");
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
        var openId=   localStorage.getItem("openid");
        var orderForm = 2;
        if(is_weixn()){
            orderForm=3;
        }else if(btype=='brower'){ //h5
            orderForm=4
        }
        var strparam = "orderFrom="+orderForm+"&orderId="+getQueryString("courseId");
        if(stringnull(openId)){
        	strparam+="&openId="+openId;
        }
        jmpPayPage("/xczh/pay/wxPay",payType,strparam,getgetRedirectUrl(allCourse,false));
    }
}
/**
 * 回调页面啦
 * @param allCourse
 * @returns {String}
 */
function getgetRedirectUrl(allCourse,falg){
	var c=allCourse[0];
	return "/xcview/html/buy_prosperity.html?courseId="+c.id;
	//return "/xcview/html/buy_prosperity.html?recharges_blck=2&orderId="+c.id;
}





