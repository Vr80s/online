$(function(){
	
//	这是点击课程里面开始
	//点击左侧课程
	$(".courseP").click(function() {
	    $(".curriculum_two").show();
	    $(".curriculum_one").hide();
	});

	//点击课程--新课程
	$("#kecheng_bottom2 .button").click(function() {
	    $(".curriculum_two").hide();
	    $(".curriculum_one").show();
	});
	//点击课程  返回
	$(".curriculum_one .button").click(function() {
	    $(".curriculum_one").hide();
	    $(".curriculum_two").show();
	});	
	
	//点击学堂隐藏  
	$(".school").click(function() {
	    $(".account_main_alter").hide();
	    $(".account_main_message").hide();
	    $(".personal_details").hide();
	    $(".message_return").hide();
	    
//	    alert(135214);
	    
	    $("#curriculum").show();
	    $(".wrap_box").show();
	    
	});
	
	$(".littleBox p").click(function() {
	    $(".account_main_alter").hide();
	    $(".account_main_message").hide();
	    $(".personal_details").hide();
	    $(".message_return").hide();
	});
		
//	这是点击课程里面结束


//这是点击专辑里面开始
	//点击左侧专辑
	$(".specialP").click(function() {
	    $("#zhuanji_bottom2").show();
	    $("#zhuanji_bottom").hide();
	    $("#zhuanjis_bottom").hide();
	});
	
	//点击第一页,新专辑
	$("#zhuanji_bottom2 .returns").click(function() {
	    $("#zhuanji_bottom2").hide();
	    $("#zhuanji_bottom").show();
	});
	
	//点击第二页返回--到第一页
	$("#zhuanji_bottom .returns").click(function() {
	    $("#zhuanji_bottom").hide();
	    $("#zhuanji_bottom2").show();
	});
	
	
	//点击新专辑添加课程开始
	//点击添加课程
	$(".add_course").click(function() {
	    $(".new_box").show();
	});
	
	//关闭添加课程弹框
	$(".new_box_main p").click(function() {
	    $(".new_box").hide();
	});
	//点击新专辑添加课程结束
	
	//添加课程开始
	$(".new_box .new_box_main .size").click(function() {
	    $("#zhuanjis_bottom").show();
	    $("#zhuanji_bottom").hide();
	    $(".new_box").hide();
	});
	
	
//	课程添加返回  
	$("#zhuanjis_bottom .returns").click(function() {
	    $("#zhuanji_bottom2").show();
	    $("#zhuanjis_bottom").hide();
	});
	
	//添加课程结束  
	
//这是点击专辑里面结束
	

//这是点击资源开始   
	$(".resourceP").click(function() {
	    $(".resource_two").show();
	    $(".resource_one").hide();
	});
	
	$(".resource_two .zhuanlan_top button").click(function() {
	    $(".resource_one").show();
	    $(".resource_two").hide();
	});
	$(".resource_one .zhuanlan_top button").click(function() {
	    $(".resource_one").hide();
	    $(".resource_two").show();
	});

//这是点击资源结束
	


//点击认证消息   name_news
$(".name_news").click(function() {
//	隐藏认证信息第二页  
	$(".account_main_alter").hide();
	
//	隐藏个人信息内容
    $(".message_return").hide();
    $(".personal_details").hide();
});

//点击个人信息  
$(".name_personage").click(function() {
    $(".account_main_alter").hide();
//  $(".personal_details").show();
});
	

//账户重新认证点击开始
$(".reauthentication .right_btn").click(function() {
    $(".account_main_message").hide();
    $(".account_main_alter").show();
});



$(".account_main_alter_title .two").click(function() {
    $(".account_main_alter").hide();
    $(".account_main_message").show();
});


//账户个人信息  
$(".right_modification").click(function() {
    $(".personal_details").hide();
    $(".message_return").show();
});
$(".message_return .message_title .two").click(function() {
    $(".message_return").hide();
    $(".personal_details").show();
});


	
	
	
	
});

