

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


//var recharges_blck = getQueryString("recharges_blck"); 
//var orderId = getQueryString("orderId");
//if(stringnull(recharges_blck) && recharges_blck ==1){
//	sessionStorage.setItem("recharges_blck","1");
//	sessionStorage.setItem("recharges_blck_param",orderId);
//}else if(stringnull(recharges_blck) && recharges_blck ==2){
//	sessionStorage.setItem("recharges_blck","2");
//}else if(stringnull(recharges_blck) && recharges_blck ==3){
//	var courseId = getQueryString("courseId");
//	sessionStorage.setItem("recharges_blck_param",courseId);
//	sessionStorage.setItem("recharges_blck","3");
//}
/**
 *  充值单击返回
 */
/*$(".header_return").click(function(){
	var rechargesBlck = sessionStorage.getItem("recharges_blck");
	if(rechargesBlck == 1){
		var recharges_blck_param = sessionStorage.getItem("recharges_blck_param");
		location.href = "/xcview/html/purchase.html?orderId="+recharges_blck_param;
	}else if(rechargesBlck == 2){
		location.href = "/xcview/html/my_wallet.html";
	}else if(rechargesBlck == 3){
		var recharges_blck_param = sessionStorage.getItem("recharges_blck_param");
		location.href = "/xcview/html/details.html?courseId="+recharges_blck_param;
	}
})*/

/**
 * 监听浏览器---》回退事件
 */
/*var counter = 0;
if (window.history && window.history.pushState) {
   $(window).on('popstate', function () {
  	 
      window.history.pushState('forward', null, '#');
      window.history.forward(1);
                  
      var rechargesBlck = sessionStorage.getItem("recharges_blck");
  	if(rechargesBlck == 1){
  		var recharges_blck_param = sessionStorage.getItem("recharges_blck_param");
  		location.href = "/xcview/html/purchase.html?orderId="+recharges_blck_param;
  	}else if(rechargesBlck == 2){
  		location.href = "/xcview/html/my_wallet.html";
  	}else if(rechargesBlck == 3){
  		var recharges_blck_param = sessionStorage.getItem("recharges_blck_param");
  		location.href = "/xcview/html/details.html?courseId="+recharges_blck_param;
  	}
 });
}
window.history.pushState('forward', null, '#'); //在IE中必须得有这两行
window.history.forward(1);*/


function lastPage(){
	var last_page = document.referrer;
	if(stringnull(last_page) && last_page.indexOf("wechat_h5_recharge.html")!=-1){
		window.history.go(-2);
	}else{
		window.history.go(-1);
	}
}



/**
 * 还有可能是从
 * 点击确认刷新下页面啦。哈哈哈
 */
$("#determine").click(function(){
	//点击返回 --》我的天去哪里
//	location.href = "/xcview/html/recharges.html";
//$(".success").hide();
	if(is_weixn()){
		window.history.go(-1);
	}else{
		window.history.go(-2);
	}
})



var type = getQueryString("type"); //若果type 不等于null 时提示充值成功。
var xmbCount = getQueryString("xmbCount");
if(stringnull(type) || type == 1 || type == 2){
	$(".success").show();
	$("#xmb_success").html(xmbCount*10);
	//alert("充值成功");
}

var orderNo = "";
var type =""; //判断课程类别，支付使用

/**
 * 点击去充值
 */
function  goPay() {
    /**
     * 判断支付类型
     */
	var zhifubao_payFalg =  $("#zhifubao_pay").attr("class");
	var weixin_payFalg =  $("#weixin_pay").attr("class");
	
	var payType = 3; //支付类型   1 熊猫币支付   2支付宝支付  3 微信支付
	
/*	if(zhifubao_payFalg.indexOf("pay_border")!=-1){ //就是支付宝支付
		payType = 2;
	}else if(weixin_payFalg.indexOf("pay_border")!=-1){
		payType = 3;
	}else{
		alert("获取支付类型有误");
		return;
	}*/
	//payType = 3;
	
	/**
	 * 这里需要获取熊猫币的余额
	 */
	var actualPay  = $("#ul").find(".li0").find(".p1").find("span").text();
	console.log("实际充值的人民币"+actualPay);
	
	if(payType == 2){ //支付宝支付  rechargePay
    	/**
    	 * 支付宝在字符前判断此订单中的课程用户是否已经购买过了
    	 */
     	 if(is_weixn()){ //是否来自微信浏览器
     		  //去另一个页面引导用户去外部浏览器打开
     		  //0:支付宝 1:微信 2:网银	
    		  var outTradeNo = (new Date()).pattern("yyyyMMddHHmm")+randomWord(true,12,12);
              
    		  location.href = "/xcview/html/wechat_alipay.html?userId="+localStorage.userId+"&actualPay="+actualPay+
              "&redirectUrl="+getRedirectUrl(actualPay)+"&outTradeNo="+outTradeNo;
              
    		  //alert("");
              /*location.replace("/xcview/html/wechat_alipay.html?userId="+localStorage.userId+"&actualPay="+actualPay+
                      "&redirectUrl="+getRedirectUrl(actualPay)+"&outTradeNo="+outTradeNo);*/
              return;
          }
          jmpPayPage("/xczh/alipay/rechargePay",payType,"actualPay="+actualPay,null);
    }else if(payType==3){ //微信支付
        var openId= localStorage.getItem("openid");
        var orderForm = 3;
        if(is_weixn()){
            if(!stringnull(openId)){  // 再去重cookie中获取
            	var third_party_uc_t_ = cookie.get("_third_ipandatcm_user_");
            	if(stringnull(third_party_uc_t_)){
            		third_party_uc_t_ = decodeURIComponent(third_party_uc_t_);	
            		openId = third_party_uc_t_.split(";")[0];
            	}
            }
        }else{ //h5
            orderForm=4
        }
        // 自定义一个订单号
        var outTradeNo = (new Date()).pattern("yyMMddHH")+randomWord(true,12,12);
        
        //clientType= 2 表示微信支付
        var strparam = "clientType="+orderForm+"&actualPay="+actualPay+"&outTradeNo="+outTradeNo;
        if(stringnull(openId)){
        	strparam+="&openId="+openId;
        }
        var redirectUrl = getRedirectUrl(actualPay,orderForm,outTradeNo);
       
        jmpPayPage("/xczh/pay/rechargePay",payType,strparam,redirectUrl);
    }
}

/**
 * 支付成功后调转的地址
 * @param allCourse
 * @returns {String}
 */
function getRedirectUrl(actualPay,orderForm,outTradeNo){
	if(orderForm==4){
		return "/xcview/html/wechat_h5_recharge.html?type=2&xmbCount="+actualPay+"&outTradeNo="+outTradeNo;;
	}else{
		return "/xcview/html/recharges.html?type=2&xmbCount="+actualPay;
	}
	
}



