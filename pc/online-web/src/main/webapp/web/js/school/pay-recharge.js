$(function(){
//	选择充值数量
	$(".select-pay-money ul li").click(function(){
		$(".select-pay-money ul li").removeClass("select-money");
		$(this).addClass("select-money");
	})
//选择支付方式
	$(".main-bottom ul li").click(function(){
		$(".main-bottom ul li").removeClass("select-confirm").find("span").addClass("hide");
		$(this).addClass("select-confirm").find("span").removeClass("hide");
	})
})
