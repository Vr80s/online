var mobile = "";
//获取手机号
mobile = localStorage.getItem("username");
$("#mobileShow").html(mobile);


var type = getUrlParam("type");
if(type == 1){ //type=1 此微信号已经绑定了了啊
	$(".success_main").find(".one").html("亲,此微信号已经绑定了!");
	$(".success").show();
}
/*
 * 获取用户绑定的第三方用户信息
 */
requestService("/xczh/bind/userBindingInfo", null, function(data) {
	if (data.success) {
		var item = data.resultObject;

		$("#weixin_bind").attr("data-title",item.wxUnionId);   
		$("#weixin_bind").html(item.wxName);   
	}
})	


$(".email_one .div0_show").click(function(){
	var unionId = $("#weixin_bind").attr("data-title");
	
	if(stringnull(unionId)){ //已经绑定了，这个是解除绑定
		
		$(".success").show();
	
	}else{//还没有绑定，唤起微信授权
		
		//alert("========");
		location.href = "/xczh/wxlogin/wxGetCodeUrl?userId="+localStorage.userId;
	}
});


/**
 * 取消  --》取消绑定
 */
$(".success .three .three_btn1").click(function(){
	$(".success").hide();
});
	
/**
 * 确认 --》取消绑定
 */
function removeBind(){
	
	var unionId = $("#weixin_bind").attr("data-title");
	if(stringnull(type)){
		$(".success").hide();
	}else{
		var unionId = $("#weixin_bind").attr("data-title");
		requestService("/xczh/bind/removeBinding", {
			type:1,
			unionId:unionId
		}, function(data) {
			if (data.success) {
				//刷新下页面
				location.href="/xcview/html/lickacc_mobile.html";
			}
		})	
	}
}
