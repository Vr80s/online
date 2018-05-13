$(function(){
	$(".select-pay li").click(function(){
		$(".select-pay li").removeClass("select-confirm").find("span").addClass("hide");
		$(this).addClass("select-confirm").find("span").removeClass("hide");
	})
})
