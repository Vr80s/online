


var loginUserId = "";
var loginStatus = true;
var smallHeadPhoto ="";
 RequestService("/online/user/isAlive", "GET", null, function (data) {
    if (data.success) {
    	loginUserId = data.resultObject.id;
    	smallHeadPhoto = data.resultObject.smallHeadPhoto;
    	loginStatus = true;
    }else{
    	loginStatus = false;
    }
},false)

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
	
	
	//	详情/评价/常见问题	选项卡
//    $(".wrap-sidebar ul li").click(function () {
//        $(".wrap-sidebar ul li").removeClass("active-footer");
//        $(this).addClass("active-footer");
//        $(".sidebar-content").addClass("hide").eq($(this).index()).removeClass("hide")
//    })
	
	
	
	
//  渲染精彩视频看是否存在啦	
	if(type == "info" && video!=null && video != undefined){
		//获取视频信息接口
		RequestService("/online/vedio/getVideoPlayCodeByVideoId", "GET", {
            videoId: video,
			width: "463",
			height: "260",
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
		var $this =  $(this);
		//判断有没有登录啦
		RequestService("/online/user/isAlive", "GET", null, function(data) {
           if(!data.success) {
               $('#login').modal('show');
           } else {
        		var type = 1;
    			if($this.hasClass("isAdd-active")){
    				type = 2;
    			}
    			var paramsObj = {
    				lecturerId:userId,
    				type:type
    			}
    			RequestService("/focus/updateFocus", "get", paramsObj, function(data) {
    				if(data.success) {
    					
    					if($this.hasClass("isAdd-active")){
    						$this.removeClass("isAdd-active").find("span").text("加关注")
    						$this.find("img").attr("src","../../web/images/icon-up.png");
    					}else{
    						$this.addClass("isAdd-active").find("span").text("已关注")
    						$this.find("img").attr("src","../../web/images/icon-down.png");
    					}
    					var jsonObj = data.resultObject;
    					$("#focusCount").html(jsonObj.focusCount);
    					$("#fansCount").html(jsonObj.fansCount);
    					//location.reload();
    				}else{
    					showTip(data.errorMessage);
    				}
    			},false)
           }
	   });
	})
})
