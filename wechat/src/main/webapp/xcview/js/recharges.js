

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

var type = getQueryString("type"); //若果type 不等于null 时提示充值成功。

if(stringnull(type) || type == 1){
	alert("充值成功");
}


var orderNo = "";
var type =""; //判断课程类别，支付使用

$(".header_return").click(function(){
	window.history.go(-1);
})

/**
 * 点击去充值
 */
function  goPay() {
    /**
     * 判断支付类型
     */
	var zhifubao_payFalg =  $("#zhifubao_pay").attr("class");
	var weixin_payFalg =  $("#weixin_pay").attr("class");
	
	var payType = ""; //支付类型   1 熊猫币支付   2支付宝支付  3 微信支付
	
	
	if(zhifubao_payFalg.indexOf("pay_border")!=-1){ //就是支付宝支付
		payType = 2;
	}else if(weixin_payFalg.indexOf("pay_border")!=-1){
		payType = 3;
	}else{
		alert("获取支付类型有误");
		return;
	}
	/**
	 * 这里需要获取熊猫币的余额
	 */
	var actualPay  = $("#ul").find(".li0").find(".p1").find("span").text();

	alert(actualPay);
	console.log("实际充值的人民币"+actualPay);
	
//	actualPay = 1; //充值金额人民币
//
//	//测试  
	actualPay = 0.01;
	
	if(payType == 2){ //支付宝支付  rechargePay
    	/**
    	 * 支付宝在字符前判断此订单中的课程用户是否已经购买过了
    	 */
     	 if(is_weixn()){ //是否来自微信浏览器
     		  //去另一个页面引导用户去外部浏览器打开
     		  //0:支付宝 1:微信 2:网银	
              location.href = "/xcview/html/wechat_alipay.html?userId="+localStorage.userId+"&actualPay="+actualPay+
              "&redirectUrl="+getRedirectUrl()+"&type=2";
              return;
          }
          jmpPayPage("/xczh/alipay/rechargePay",payType,"actualPay="+actualPay,null);
    }else if(payType==3){ //微信支付
        var btype=   localStorage.getItem("access");
        var openId=   localStorage.getItem("openid");
        var orderForm = 3;
        if(btype=='wx'){   	//公众号
            orderForm=3;
        }else if(btype=='brower'){ //h5
            orderForm=4
        }
        //clientType= 2 表示微信支付
        var strparam = "clientType="+orderForm+"&actualPay="+actualPay;
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
   /**
    * 去充值页面的几个途径
    */	
   return "/xcviews/html/recharges.html?type=1";
}




