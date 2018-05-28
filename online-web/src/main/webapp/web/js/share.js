/**
 * Created by admin on 2016/12/9.
 */
$(function(){
    //����url��ַ
    var ourl = document.location.search;
    var w=ourl.indexOf("=");
    ourl=ourl.substring(w+1);
    var encodeurl = encodeURIComponent(ourl);
    ourl=decodeURIComponent(ourl);
    $(".pay-result1-close").click(function(){
        $(".pay-result1").css("display","none");
    })
    $(".immJoinUs").click(function(){
        window.open("/web/weixin_pay_share_unifiedorder/1.00?preurl="+encodeurl);
        $(".pay-result1").css("display","block");
    })
    $(".pay-success-btn").click(function(){
        RequestService("/online/user/isAlive", "GET", "", function (data) {
            if(data.resultObject.shareCode=="" ||data.resultObject.shareCode==null){
                window.location.href="/web/html/myStudyCenter.html?share=0";
            }else{
                window.location.href="http://"+location.hostname+ourl;
            }
        }, false);
    })
})