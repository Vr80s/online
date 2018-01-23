$(function(){
	//	回复弹窗
$(".wrap_returned_btn .btn_littleReturn").click(function(){
	$(".bg_userModal").show();
	$(".wrapLittle_comment").show();
	$("#littlt_return").focus()
});
$(".bg_userModal").click(function(){
	$(".bg_userModal").hide();
	$(".wrapLittle_comment").hide();
});
//	评论弹窗
	$(".wrap_input").on('click',function(){
		$(".bg_modal").show();
		$(".wrapAll_comment").show();
	})
	$(".bg_modal").on('click',function(){
		$(".bg_modal").hide();
		$(".wrapAll_comment").hide();
	})
//	标签选中变色
	 $(".select_lable li").click(function(){
  		 $(this).toggleClass("active_color"); 
 	 });
//选集弹窗
 	 $(".select_icon").click(function(){
  		$(".bg_modal02").show();
		$(".wrap_select_list").show();
 	 });
  	$(".bg_modal02").on('click',function(){
		$(".bg_modal02").hide();
		$(".wrap_select_list").hide();
	})
	$(".list_box span").on('click',function(){
		$(".bg_modal02").hide();
		$(".wrap_select_list").hide();
	})
})
