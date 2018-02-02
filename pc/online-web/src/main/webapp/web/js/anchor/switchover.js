$(function(){
	
//	这是点击课程里面
	//点击课程  开始
	$("#kecheng_bottom2 .button").click(function() {
		alert(1111111);
	    $(".curriculum_two").hide();
	    $(".curriculum_one").show();
	});
	//点击课程  返回
	$(".curriculum_one .button").click(function() {
	    $(".curriculum_one").hide();
	    $(".curriculum_two").show();
	});	

	
	
	
	
	
	
	
});