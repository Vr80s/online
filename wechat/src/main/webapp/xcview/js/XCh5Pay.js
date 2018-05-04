/**
 * h5支付功能
 * 主要功能方法<code>jmpPayPage<code>
 * author:liutao
 */


function is_weixn(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
        return false;
    }
}

/**
 * 跳转到支付页面。
 * 依附于后端com.xczh.distributed.common.bean.ResponseObject对象
 * orderUrl 统一下单的url
 * payMode 支付方式 2:支付宝 3:微信支付
 * params 下订单需要的参数,格式：paramName=1&paramName=2&paramName=3...
 * returnUrl 微信支付需要传
 *
 */
function jmpPayPage(orderUrl,payMode,params,returnUrl){
	
    if(payMode==2){
        location.href = orderUrl+"?"+params;
    }else if(payMode==3){
        weixin(orderUrl,params,returnUrl);
    }
}

function weixin(url,params,returnUrl) {

	if(is_weixn()){
        gongzhonghao(url,params,returnUrl);
	}else{
        wechatH5(url,params,returnUrl);
	}
}
function wechatH5(url,params,returnUrl){
	var protocol = document.location.protocol;
	var domain = window.location.host;
	if(returnUrl.indexOf(domain) ==-1){
		returnUrl =protocol+"//"+domain +returnUrl;
	}
    $.ajax({
        url : url+"?"+params,
        type : 'get',
        success : function(datas) {
            var params = datas.resultObject;
            if(datas.success){
            	var paramsIsOk = params.ok;
            	if(paramsIsOk != "false"){
            		if(datas.code == 1002){  //过期
        				location.href = "/xcview/html/cn_login.html";
        			}else if(datas.code == 1003){ //被同一用户顶掉了
        				location.href = "/xcview/html/common.html";
        			}else if(datas.code == 1005){ //token过期  -->去完善信息页面
        				var openId =  msg.resultObject.openId;
        				var unionId =  msg.resultObject.unionId;
        				location.href = "/xcview/html/evpi.html?openId="+openId+"&unionId="+unionId;
        			}else{
        				location.replace(params.mweb_url+"&redirect_url="+encodeURIComponent(returnUrl));
        			}
            	}else{
            		alert("抱歉充值失败!");
            	}
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
            var package1 = resultpay.package;
            var signType = resultpay.signType;
            var paySign = resultpay.paySign;
            var appId=resultpay.appId;
//            alert("timestamp"+timestamp+";"
//                +"nonceStr"+nonceStr+";"
//                +"package1"+package1+";"
//                +"signType"+signType+";"
//                +"paySign"+paySign+";"
//                +"appId"+appId+";"
//                )
            // 支付成功后的回调函数
            WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId":""+appId+"",     //公众号名称，由商户传入
                    "timeStamp":""+timestamp+"",         //时间戳，自1970年以来的秒数
                    "nonceStr":""+nonceStr+"", //随机串
                    "package":""+package1+"",
                    "signType":"MD5",         //微信签名方式：
                    "paySign":""+paySign+"" //微信签名
                },
                function(res){
                    if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                    	// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                        location.replace(returnUrl);
                    }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                    	console.log("取消支付");
                    }else if(res.err_msg == "get_brand_wcpay_request:fail"){
                    	//console.log("支付失败");
                    	alert("尝试下重新登录,或者关注公众号!");
                    }  
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
        	alert(data.errorMessage);
        }
    });
}