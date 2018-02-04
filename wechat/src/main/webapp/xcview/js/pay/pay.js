
function is_weixn(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
        return false;
    }
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

var allCourse;
requestService("/bxg/order/getByOrderId", {orderId : getQueryString("orderId")}, function(data) {
    if (data.success) { //去详情页面
        var result = data.resultObject;
        allCourse=data.resultObject.allCourse;
        var html="";
        for(var i=0;i<allCourse.length;i++) {
            html+="<div class='zhibo_list1'>\n" +
                "<img src='"+allCourse[i].smallImgPath+"' alt='' id='cimg' />\n" +
                "<div class='zhibo_right1'>\n" +
                "<div class='zhibo_list_title1'><a href='javascript: ;'><span id='guanzhu2'>"+allCourse[i].gradeName+"</span></a></div>\n" +
                "<div class='zhibo_list_size1 buy_size'>￥<span  id='buyPirce2'>"+allCourse[i].currentPrice+"</span></div>\n" +
                "</div>\n" +
                "</div>";
        }
        $("#clist").html(html);
        $("#buyPirce").html(result.actualPay);
    }
});
/**
 * 去支付
 * 
 */
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

    var payType=null;
    var classname =  $(".label02").attr("class");
    if(classname.indexOf("label1")!=-1){
    	payType = 1;
    }else{
    	payType = 0;
    }
    if(payType==0){  //支付宝
    	/**
    	 * 因为支付宝支付时直接返回给我们的是一个页面，所以我们要在之前判断是否存在有购买的课程
    	 * 
    	 */
    	requestService("/xczh/order/orderIsExitCourseIsBuy", {orderId:getQueryString("orderId")}, function(data) {
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
    }else if(payType==3){ //熊猫币充值
    	
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