
function is_weixn(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
        return false;
    }
}
/*if(is_weixn()){
    $("#zfbDiv").remove();
}*/


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
    }})

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
    var radio = document.getElementsByName("fruit");
    var payType=null;
    for (i=0; i<radio.length; i++) {
        if (radio[i].checked) {
            payType=radio[i].value;
        }
    }
    if(payType==0){
    	/**
    	 * 支付宝在字符前判断此订单中的课程用户是否已经购买过了
    	 */
    	requestService("/bxg/order/orderIsExitCourseIsBuy", {orderId:getQueryString("orderId")}, function(data) {
    		 var params = data.resultObject;
             if(data.success){
             	 if(is_weixn()){
                      location.href = "wechat_alipay_xianxia.html?orderId="+getQueryString("orderId")+"&orderNo="+getQueryString("orderNo")+"&redirectUrl="+getgetRedirectUrl(allCourse);
                      return;
                  }
                  jmpPayPage("/bxg/alipay/payXianXia",payType,"orderId="+getQueryString("orderId")+"&orderNo="+getQueryString("orderNo"),null);
             	
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
        jmpPayPage("/bxg/pay/wxPay",payType,strparam,getgetRedirectUrl(allCourse));
    }
}
function getgetRedirectUrl(allCourse){
var redirectUrl="";
if(allCourse.length>1){
    redirectUrl=getServerHost()+"/xcviews/html/indent.html";
    return redirectUrl;
}else{
    //var c=allCourse[0];
        redirectUrl=getServerHost()+"/xcviews/html/personalfor.html?userId="+localStorage.userId+"&id="+getQueryString("id"); //此处跳转到待定
    return redirectUrl;
}
}