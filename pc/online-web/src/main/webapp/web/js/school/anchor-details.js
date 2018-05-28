$(function(){
	
	//tab 的显示
	var index =0;
	$(".wrap-sidebar ul li").removeClass("active-footer");
	if(type == "info"){
		$(".wrap-sidebar ul li").eq(1).addClass("active-footer");
		index =1;
	}else if(type == "courses"){
		$(".wrap-sidebar ul li").eq(0).addClass("active-footer");
	}else if(type == "comment"){
		$(".wrap-sidebar ul li").eq(2).addClass("active-footer");
		index =2;
	}
	
	$(".sidebar-content").addClass("hide").eq(index).removeClass("hide")
	
//  渲染精彩视频看是否存在啦	
	if(type == "info" && video!=null && video != undefined){
		//获取视频信息接口
		RequestService("/online/vedio/getVideoPlayCodeByVideoId", "GET", {
            videoId: video,
			width: "300",
			height: "150",
			autoPlay: false
		}, function(data) {
			if(data.success == true) {
				var scr = data.resultObject;
				$(".save-video").html(scr);
			} else if(data.success == false) {
				alert("播放发生错误，请清除缓存重试")
			}
		});
	}else{
		$(".anchor-video").hide();
	}
//	关注效果
	$(".isAdd-follow").click(function(){
		var type = 1;
		if($(this).hasClass("isAdd-active")){
			type = 2;
		}
		var paramsObj = {
			lecturerId:userId,
			type:type
		}
		RequestService("/focus/updateFocus", "get", paramsObj, function(data) {
			if(data.success) {
				if($(this).hasClass("isAdd-active")){
					$(this).removeClass("isAdd-active").find("span").text("加关注")
					$(this).find("img").attr("src","../../web/images/icon-up.png");
				}else{
					$(this).addClass("isAdd-active").find("span").text("已关注")
					$(this).find("img").attr("src","../../web/images/icon-down.png");
				}
			}else{
				alert(data.errorMessage);
			}
		},false)	
	})
	
//	
////	课程/介绍/评价
//	$(".wrap-sidebar ul li").click(function(){
//		$(".wrap-sidebar ul li").removeClass("active-footer");
//		$(this).addClass("active-footer");
//		$(".sidebar-content").addClass("hide").eq($(this).index()).removeClass("hide");
//	})
////	点击我要评价
//	$(".want-evaluate").click(function(){
//		$(".bg-modal").removeClass("hide");
//		$(".wrap-modal").removeClass("hide");
//	});
//	$(".close-impression").click(function(){
//		$(".bg-modal").addClass("hide");
//		$(".wrap-modal").addClass("hide");
//	});
////	回复评论和点赞按钮加颜色
//	$(".operation-reply li").click(function(){		
//		if($(this).hasClass("selected")){
//			$(this).removeClass("selected");
//		}else{
//			$(this).addClass("selected");
//		}
//	})
////	点击回复图标出现输入框
//	$(".reply-icon").click(function(){
//		if($(this).hasClass("selected")){
//			$(this).parents().siblings(".wrap-input").removeClass("hide");
//		}else{
//			$(this).parents().siblings(".wrap-input").addClass("hide");
//		}
//	})
////星星五星好评
//	$('.impression-star img').each(function(index){
//        var star='../../web/images/star-dim.png';    //普通灰色星星图片的存储路径  
//        var starRed='../../images/star-light.png';     //红色星星图片存储路径  
//        var prompt=['1分','2分','3分','4分','5分'];   //评价提示语  
//        this.id=index;      //遍历img元素，设置单独的id  
//        $(this).on("mouseover click",function(){    //设置鼠标滑动和点击都会触发事件  
//            $('.impression-star img').attr('src',star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色  
//            $(this).attr('src',starRed);        //设置鼠标当前所在图片为打星颜色图  
//            $(this).prevAll().attr('src',starRed);  //设置鼠标当前的前面星星图片为打星颜色图  
//            $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值
//        });
//    });
////节目内容
//	$('.impression-face img').each(function(index){
//        var star='../../images/gs.png';    //普通灰色星星图片的存储路径  
//        var starRed='../../images/rs.png';     //红色星星图片存储路径  
//        var prompt=['一般','一般','好','好','很好'];   //评价提示语  
//        this.id=index;      //遍历img元素，设置单独的id  
//        $(this).on("mouseover click",function(){    //设置鼠标滑动和点击都会触发事件  
//            $('.impression-face img').attr('src',star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色  
//            $(this).attr('src',starRed);        //设置鼠标当前所在图片为打星颜色图  
//            $(this).prevAll().attr('src',starRed);  //设置鼠标当前的前面星星图片为打星颜色图  
//            $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值
//        });
//    });
////主播演绎
//	$('.impression-show img').each(function(index){
//	    var star='../../images/gs.png';    //普通灰色星星图片的存储路径  
//	    var starRed='../../images/rs.png';     //红色星星图片存储路径  
//	    var prompt=['一般','一般','好','好','很好'];   //评价提示语  
//	    this.id=index;      //遍历img元素，设置单独的id  
//	    $(this).on("mouseover click",function(){    //设置鼠标滑动和点击都会触发事件  
//	        $('.impression-show img').attr('src',star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色  
//	        $(this).attr('src',starRed);        //设置鼠标当前所在图片为打星颜色图  
//	        $(this).prevAll().attr('src',starRed);  //设置鼠标当前的前面星星图片为打星颜色图  
//	        $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值
//	    });
//	});
////  很赞,干货很多
//	$(".impression-setlist li").click(function(){
//		if($(this).hasClass("impression-active")){
//			$(this).removeClass("impression-active");		
//		}else{
//			$(this).addClass("impression-active");					
//		}
//	})
////	点击发表评论
//	$(".submission").click(function(){
//		$(".bg-modal").addClass("hide");
//		$(".wrap-modal").addClass("hide");
//	})

})
