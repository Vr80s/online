$(function(){

	

	
	function getWhiteHeight(){
		var videoHeight = $(document.body).height()-219;
		var studentHeight=$(document.body).height()-262;
		var chatHeight=$(document.body).height()-152;
	//	文档高度
		$(".video-main").css({"height":videoHeight});
	//	文档左侧文件列表高度
		$(".select-document-wrap").css({"height":videoHeight});
	//	学员列表高度
		$(".student-list").css({"height":studentHeight});
	//	聊天区域
		$(".chat-personal").css({"height":chatHeight});	
	}
	getWhiteHeight();
	$(window).resize(function (){  
		getWhiteHeight();
	})
//------------------------------------------工具栏----------------------------------------------------------------	


//	顶部工具hover效果
	$(".select-tool .show-img").hover(function(){
		$(this).siblings(".tip-tool").removeClass("hide");	
		$(".select-huabi").addClass("hide");
		$(this).siblings(".select-huabi").removeClass("hide");
	},function(){
		$(this).siblings(".tip-tool").addClass("hide");
	})
//	点击添加背景
	$(".select-tool li").click(function(){	
//		判断是否点击的颜色
		if($(this).hasClass("remove-color")){	
		}else{
			$(".select-tool li").removeClass("active");
			$(this).addClass("active");
		}
		
	})
//	点击下拉选项添加背景
	$(".select-tool .select-huabi .comment-bg").click(function(){		
		$(".select-tool .select-huabi .comment-bg").removeClass("active");
		$(this).addClass("active");
//		判断是否点击颜色下拉选项
		if($(this).hasClass("comment-color")){
			var selectClass=$(this).find("img").attr("src");
			$(this).parent().siblings(".show-img").find(".sure-color").attr("src",selectClass)
		}
	})
//	收起下拉工具
	$(window).click(function(){
		$(".select-tool .select-huabi").addClass("hide");
	})
	
	
//------------------------------------------文档弹出框----------------------------------------------------------------	


//	点击文档弹出框
	$(".select-document .document-modal").click(function(){
		$(".background-ask").removeClass("hide");
		$(".modal-document").removeClass("hide");
	})
//	关闭文档弹出框
	$(".modal-top img").click(function(){
		$(".background-ask").addClass("hide");
		$(".modal-document").addClass("hide");
	})
//  hover删除按钮显现
	$(".hover-delect").hover(function(){
		$(".hover-delect .delect-img").addClass("hide");
		$(this).find(".delect-img").removeClass("hide")
	},function(){
		$(".hover-delect .delect-img").addClass("hide");
	})
//  点击删除
	$(".hover-delect .delect-img").click(function(){
		$(this).parent().remove();
	})
//点击上传文件的URL
 	var fileUrl;
	$('#file-input').change(function(){ 
	     $('#submitFile').ajaxSubmit({ 
	       type:'post', 
	       dataType:'json', 
	       success:function(result){ 
	         //请求成功后的操作 
	  			fileUrl=result.url;
	  			console.log(fileUrl)
	         //并且清空原文件，不然选择相同文件不能再次传 
	//       $('#file-input').val(''); 
	       }, 
	       error:function(){ 
	         //并且清空原文件，不然选择相同文件不能再次传 
	//       $('#file-input').val(''); 
	       } 
	     }); 
	})
})

//------------------------------------------文档左侧列表点击效果----------------------------------------------------------------	
$(".icon-right").click(function(){
	$(this).parent(".select-document-wrap").addClass("select-left");
	$(".video-main .icon-left").removeClass("hide");
})
$(".icon-left").click(function(){
	$(this).siblings(".select-document-wrap").removeClass("select-left");
	$(".video-main .icon-left").addClass("hide");
})
$(".modal-list li").each(function(){
	var index=$(this).index();
	var num=index+1;
	$(this).find("span").html(num)
})
$(".modal-list li").click(function(){
	$(".modal-list li").removeClass("active");
	$(this).addClass("active")
})
