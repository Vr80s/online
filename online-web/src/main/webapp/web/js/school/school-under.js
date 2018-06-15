$(function(){
	//	导航字体颜色
	$(".header_left .path .under").css({"color":"#00BC12"})
	//	轮播图速度控制
	$('.carousel').carousel({
	  interval: 3000
	})
	$(".hover-select").hover(function(){
		$(".banner-prev").fadeIn(200)
	},function(){
		$(".banner-prev").fadeOut(200)
		
	})
})
