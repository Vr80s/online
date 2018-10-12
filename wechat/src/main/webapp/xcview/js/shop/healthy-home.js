$(function(){
	$(".sex-ul li").click(function(){
		$(".sex-ul li img").removeClass("active");
		$(this).find("img").addClass("active");
	})
})
