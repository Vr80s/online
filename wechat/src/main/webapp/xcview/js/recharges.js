

function is_weixn(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
    	localStorage.setItem("access","wx");
        return true;
    } else {
    	localStorage.setItem("access","brower");
        return false;
    }
}

var orderNo = "";
var type =""; //判断课程类别，支付使用


/**
 * 点击去支付
 */
function  goPay() {
    /**
     * 判断支付类型
     */
//	var payClassFalg =  $(".ul_li0").find("img").attr("class");
//	
//	var payType = ""; //支付类型   1 熊猫币支付   2支付宝支付  3 微信支付
//	if(payClassFalg == "BTCpanda"){ 
//		payType = 1;
//	}else if(payClassFalg == "alipay_img"){
//		payType = 2;
//	}else if(payClassFalg == "weixin_img"){
//		payType = 3;
//	}
	
	/**
	 * 这里暂时搞成 这种的  2支付宝 3微信 
	 */
	
	var payType = ""; //支付类型   1 熊猫币支付   2支付宝支付  3 微信支付
	
	var actualPay = 1; //充值金额人民币
	
    if(payType == 2){ //支付宝支付  rechargePay
    	/**
    	 * 支付宝在字符前判断此订单中的课程用户是否已经购买过了
    	 */
     	 if(is_weixn()){ //是否来自微信浏览器
     		  //去另一个页面引导用户去外部浏览器打开
     		  //0:支付宝 1:微信 2:网银	
              location.href = "/xcview/html/wechat_alipay.html?actualPay="+actualPay+
              "&redirectUrl="+getRedirectUrl()+"&type=2";
              return;
          }
          jmpPayPage("/xczh/alipay/rechargePay",payType,"actualPay="+actualPay,null);
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
        if(btype=='wx'){   	//公众号
            orderForm=3;
        }else if(btype=='brower'){ //h5
            orderForm=4
        }
        //clientType= 2 表示微信支付
        var strparam = "clientType=2&actualPay="+actualPay;
        if(stringnull(openId)){
        	strparam+="&openId="+openId;
        }
        jmpPayPage("/xczh/pay/rechargePay",payType,strparam,getRedirectUrl());
    }
}

/**
 * 支付成功后调转的地址
 * @param allCourse
 * @returns {String}
 */
function getRedirectUrl(){
   return "/xcviews/html/foreshow.html";
}




