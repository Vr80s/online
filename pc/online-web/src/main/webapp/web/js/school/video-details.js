$(function(){
//详情/评价/常见问题	选项卡
	$(".wrap-sidebar ul li").click(function(){
		$(".wrap-sidebar ul li").removeClass("active-footer");
		$(this).addClass("active-footer");
		$(".sidebar-content").addClass("hide").eq($(this).index()).removeClass("hide")
	})
})
