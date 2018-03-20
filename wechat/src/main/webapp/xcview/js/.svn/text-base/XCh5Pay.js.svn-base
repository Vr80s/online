/**
 * h5支付功能
 * 主要功能方法<code>jmpPayPage<code>
 * author:liutao
 */

/**
 * 跳转到支付页面。
 * 依附于后端com.xczh.distributed.common.bean.ResponseObject对象
 * orderUrl 统一下单的url
 * payMode 支付方式 0:支付宝 1:微信支付
 * params 下订单需要的参数,格式：paramName=1&paramName=2&paramName=3...
 * returnUrl 微信支付需要传
 *
 */
function jmpPayPage(orderUrl,payMode,params,returnUrl){
    if(payMode==0){
        location.href = orderUrl+"?"+params;
    }else if(payMode==1){
        weixin(orderUrl,params,returnUrl);
    }
}

function weixin(url,params,returnUrl) {
 var btype = localStorage.getItem("access")
	if(btype=='wx'){
        gongzhonghao(url,params,returnUrl);
	}else if(btype=='brower'){
        wechatH5(url,params,returnUrl);
	}
}
function wechatH5(url,params,returnUrl){
    $.ajax({
        url : url+"?"+params,
        type : 'get',
        success : function(datas) {
            var params = datas.resultObject;
            if(datas.success){
                window.location.href = params.mweb_url+"&redirect_url="+returnUrl;
            }else{
            	alert(data.errorMessage);
            }
        },
        error : function(response) {
            console.log("error:" + response);
        }
    });
}

function gongzhonghao(url,params,returnUrl){
    requestService(url+"?"+params, null, function(data) {
        if (data.success) {
            var resultpay = data.resultObject;
            var timestamp = resultpay.timeStamp;
            var nonceStr = resultpay.nonceStr;
            var package = resultpay.package;
            var signType = resultpay.signType;
            var paySign = resultpay.paySign;
            var appId=resultpay.appId;
            // 支付成功后的回调函数
            WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId":""+appId+"",     //公众号名称，由商户传入
                    "timeStamp":""+timestamp+"",         //时间戳，自1970年以来的秒数
                    "nonceStr":""+nonceStr+"", //随机串
                    "package":""+package+"",
                    "signType":"MD5",         //微信签名方式：
                    "paySign":""+paySign+"" //微信签名
                },
                function(res){
                    if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                        location.href = returnUrl;//"/bxg/page/wait_money";
                    }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                }
            );
            if (typeof WeixinJSBridge == "undefined"){
                if( document.addEventListener ){
                    document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
                }else if (document.attachEvent){
                    document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                    document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
                }
            }else{
                onBridgeReady();
            }
        }else{
        	//请求重新登录
        	alert("重新登录下试试");
        }
    });
}