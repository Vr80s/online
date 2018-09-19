$(function(){
	$('.save-pic ul').on('click',"p",function(){
		$(this).parent().remove();
		isPiclength();
	});
//	开启弹窗   货物状态
	$(".goods-select").click(function(){
		$(".gray-bgcolor").removeClass("hide");
		$(".goos-status-modal").animate({"bottom":"0"},300)
	});
//	退货原因
	$(".goods-select-reason").click(function(){
		$(".gray-bgcolor").removeClass("hide");
		$(".return-goods-modal").animate({"bottom":"0"},300)
	});
//	关闭弹窗
	$(".gray-bgcolor, .goos-status-top img, .return-goods-top img").click(function(){
		$(".gray-bgcolor").addClass("hide");
		$(".comment-modal").stop().animate({"bottom":"-8rem"},200)
	});
//	选中货物状态
	$(".goos-status-select ul li span").click(function(){
		$(".goos-status-select ul li span").html("")
		$(this).html('<img src="/xcview/images/select-status.png" alt="选中" />')
	});
//	选中退货原因
	$(".return-goods-select ul li span").click(function(){
		$(".return-goods-select ul li span").html("")
		$(this).html('<img src="/xcview/images/select-status.png" alt="选中" />')
	});	
	
	
})
