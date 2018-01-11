$(function(){
//	左右两边tab切换
		$(".select_list li").click(function(){
				$(".select_list li").removeClass("active");
				$(this).addClass("active");
				$(".wrap_box .little_box").hide()
				$(".select_box").hide().eq($(this).index()).show();
			})
			$(".select_list .select-ud").bind('click',function(event){
            	event.stopPropagation();			
				$(".select_list .littleBox").stop().slideToggle()
			})
			$(".setTop").click(function(){
				$(".select_list .littleBox").slideUp()
			})
//			对课程目录下小的下拉进行操作		
			$(".select_list .littleBox p").bind('click',function(event){
            	event.stopPropagation();
				$(".select_list .littleBox p").removeClass("activeP");
				$(this).addClass("activeP");
				$(".wrap_box .little_box").hide().eq($(this).index()).show();
				$(".select_box").hide()
			})
})
