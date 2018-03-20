

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
var orderId = getQueryString('orderId');

/*
 * 保存上一个页面的地址
 *  如果上一个页面来自   重新购买或者是充值页面，那么就不返回了。
 *  要返回到，课程展示页面
 */
var  before_address= document.referrer;
//alert(before_address);
if(!(before_address.indexOf("recharges.html") !=-1 || 
		   before_address.indexOf("buy_prosperity.html")!=-1 ||
		   before_address.indexOf("purchase.html")!=-1 )		   
){
	sessionStorage.setItem("purchase_back",before_address);
}
/*
 * 点击进行返回
 */
function purchaseBack(){
	var before_address = sessionStorage.getItem("purchase_back");
    if(stringnull(before_address)){
    	location.href =before_address;
    }else{
    	 history.back(-1);
    }
}

/**
 * 监听浏览器---》回退事件
 */
//var counter = 0;
//if (window.history && window.history.pushState) {
//   $(window).on('popstate', function () {
//  	 
//      window.history.pushState('forward', null, '#');
//      window.history.forward(1);
//                  
//      /*
//       * 返回事件
//       */
//      purchaseBack();
// });
//}
//window.history.pushState('forward', null, '#'); //在IE中必须得有这两行
//window.history.forward(1);
//





/**
 * 去充值页面
 */
$(".footer_div_btn").click(function(){
	
	location.href = "/xcview/html/recharges.html?recharges_blck=1&orderId="+orderId;
})

var currentPriceXMB =""; //当前课程价格
var xmbye =""; //用户剩余的熊猫币数量

var allCourse;
requestService("/xczh/order/getByOrderId",{
	orderId : orderId	
},function(data) {
	
	$("#purchase_details").html(template('data_details',data.resultObject.allCourse[0]));
	$(".purchase_list_one_right .give_price").html(data.resultObject.allCourse[0].actualPay)
	$(".purchase_details_money span").html(parseInt(data.resultObject.actualPay));
 
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
			
//			$("#xmb_ye").html(xmbye);
			$("#xmb_ye").html(parseInt(xmbye));
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
            	location.href="/xcview/html/buy_prosperity.html?courseId="+allCourse[0].id;
            }else{
              alert(data.errorMessage);
            }
		},false)
    }else if(payType==2){ //支付宝支付
    	/**
    	 * 支付宝在字符前判断此订单中的课程用户是否已经购买过了
    	 */
    	requestService("/xczh/order/orderIsExitCourseIsBuy", {orderId:getQueryString("orderId")}, function(data) {
    		 var params = data.resultObject;
    		 if(data.success){
             	 if(is_weixn()){
             		  //去另一个页面引导用户去外部浏览器打开
                      location.href = "/xcview/html/wechat_alipay.html?type=1&orderId="+getQueryString("orderId")+"&redirectUrl="+getgetRedirectUrl(allCourse,true);
                      return;
                  }
                  jmpPayPage("/xczh/alipay/pay",payType,"orderId="+getQueryString("orderId"),null);
             }else{
               alert(data.errorMessage);
             }
 		},false)
 		
    }else if(payType==3){ //微信支付
        var openId=   localStorage.getItem("openid");
        var orderForm = 3;
        if(is_weixn()){
            orderForm=3;
        }else{ //h5
            orderForm=4
        }
        var strparam = "orderFrom="+orderForm+"&orderId="+getQueryString("orderId");
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
	return "http://m.ipandatcm.com/xcview/html/buy_prosperity.html?courseId="+c.id;
	//return "/xcview/html/buy_prosperity.html?recharges_blck=2&orderId="+c.id;
}

