



function  goPay() {
    var xiradio = document.getElementsByName("xi");
    var xic=null;
    for (i=0; i<xiradio.length; i++) {
        if (xiradio[i].checked) {
            xic=xiradio[i].value;
        }
    }
    if(xic==null){
        alert("请同意用户消费协议！");
        return;
    }

    /*var radio = document.getElementsByName("fruit");
    var payType=null;
    for (i=0; i<radio.length; i++) {
        if (radio[i].checked) {
            payType=radio[i].value;
        }
    }*/

    //var radio = document.getElementsClassName("buy_pay");
    var payType=null;
   /* for (i=0; i<radio.length; i++) {
        if ("") {
            payType=radio[i].value;
        }
    }*/
    /*$(".label02").addClass('label1');*/
    var classname =  $(".label02").attr("class");
    if(classname.indexOf("label1")!=-1){
    	payType = 1;
    }else{
    	payType = 0;
    }
    if(payType==0){
    	/**
    	 * 支付宝在字符前判断此订单中的课程用户是否已经购买过了
    	 */
    	requestService("/bxg/order/orderIsExitCourseIsBuy", {orderId:getQueryString("orderId")}, function(data) {
    		 var params = data.resultObject;
             if(data.success){
             	 if(is_weixn()){
                      location.href = "wechat_alipay.html?orderId="+getQueryString("orderId")+"&orderNo="+getQueryString("orderNo")+"&redirectUrl="+getgetRedirectUrl(allCourse,true);
                      return;
                  }
                  jmpPayPage("/bxg/alipay/pay",payType,"orderId="+getQueryString("orderId")+"&orderNo="+getQueryString("orderNo"),null);
             	
             }else{
               alert(data.errorMessage);
             }
 		},false)
    	
    }else if(payType==1){
        var btype=   localStorage.getItem("access");
        var openId=   localStorage.getItem("openid");
       
        var orderForm;
        if(btype=='wx'){
            orderForm=3;
        }else if(btype=='brower'){ //h5
            orderForm=4
        }
        var strparam = "orderFrom="+orderForm+"&orderId="+getQueryString("orderId")+"&orderNo="+getQueryString("orderNo");
       
        if(stringnull(openId)){
        	strparam+="&openId="+openId;
        }
        jmpPayPage("/bxg/pay/wxPay",payType,strparam,getgetRedirectUrl(allCourse,false));
    }
}
/**
 * 回调页面啦
 * @param allCourse
 * @returns {String}
 */
function getgetRedirectUrl(allCourse,falg){
	//alert(window.location.host);
	var domain = "";
	if(falg){
	    domain = window.location.host;
	}
	var redirectUrl="";
	if(allCourse.length>1){  //如果此订单中有多个订单，返回到我的订单页面
	    redirectUrl="/xcviews/html/indent.html";
	    return redirectUrl;
	}else{
	    var c=allCourse[0];  //判断此课程是预约呢、直播、点播的课程
	    if(c.lineState != 2){ //直播状态1.直播中，2预告，3直播结束
	    	if(c.type==1){     
	 	    	//bxg/xcpage/courseDetails?courseId=724
	 	        redirectUrl="/bxg/xcpage/courseDetails?courseId="+c.id;
	 	    }else{
	 	        redirectUrl="/xcviews/html/particulars.html?courseId="+c.id;
	 	    }
	    }else{
	    	redirectUrl="/xcviews/html/foreshow.html?course_id="+c.id;
	    }
	    return redirectUrl;
	}
}