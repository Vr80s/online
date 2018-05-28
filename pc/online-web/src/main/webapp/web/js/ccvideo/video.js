/**
 * Created by fanwenqiang on 2016/11/2.
 */
$(function() {

	var courseId = $.getUrlParam("courseId");
	
	/**
	 * 增加学习记录
	 */
	RequestService("/learnWatch/add", "POST", {
		courseId:courseId,recordType:1
	}, function(data) {
		console.log("添加观看记录");
	},false);
	
	
	
	
	
	
	
	var menuid = "";
	var pageNumber = 1;
	var pageSize = 15;
	//视频播放窗口加载
	//获取当前屏幕高度
	var allwidth = parseInt($(".videoBody-top").width());
	//当前屏幕左半部分宽度
	var awidth = parseInt($(".videoBody-top").width() - 290);
	var aheight = parseInt($(window).height() - $(".header").height() - 110);
	$(".videoBody-top").height(aheight);
	$(".videoBody-menuList").height(aheight);
	$(".videoBody-video").height(aheight);
	$(".loadImg").css("display", "block");
	//时间格式处理
	function timeChange(num) {
		return '' + num + "分钟";
	};
	//获取视频信息接口
	RequestService("/online/user/isAlive", "GET", null, function(data) { ///online/user/isAlive
		if(data.success === true) {
			RequestService("/online/vedio/getPlayCodeByCourseId", "GET", {
                courseId: courseId,
				width: awidth,
				height: aheight,
				autoPlay: false
			}, function(data) {
				if(data.success == true) {
					
					/**
					 * 增加观看记录
					 */
					RequestService("/learnWatch/add", "POST", {
						courseId:courseId,
						recordType:2
					}, function(data) {
						console.log("添加观看记录");
					},false);
					
					var scr = data.resultObject.playCode;
					$(".videoBody-video").append(scr);
					$(".headerBody-title").html(data.resultObject.title);
					
				} else if(data.success == false) {
					
					//弹出错误信息
					alert(data.errorMessage);
				}
			});
		} else {
			/**
			 * 如果用户没有登录直接就搞走呗
			 */
			location.href="/course/courses/"+courseId;
		}
	});
	//获取课程名字和讲师姓名
	RequestService("/online/live/getOpenCourseById", "get", {
		courseId: courseId
	}, function(data) {
		if(!data.success){
			location.href="/course/courses/"+courseId;
		}
		$(".headerBody .rightT p").html(data.resultObject.courseName).attr("title", data.resultObject.courseName);
		document.title = data.resultObject.courseName ;
		$(".headerBody .rightT i").html(data.resultObject.lecturer);
		menuid = data.resultObject.menu_id;

		var host = window.location.host;

		var weboshare_url="http://"+host+"/course/courses/"+courseId;
		
		/**
		 * 微博分享
		 */
		$("#weibo_share").click(function(){
			var  p = {
		        url: weboshare_url,/*获取URL，可加上来自分享到QQ标识，方便统计*/
		        title : data.resultObject.courseName,/*分享标题(可选)*/
		        pic : data.resultObject.smallImgPath /*分享图片(可选)*/
		    };
		    var s = [];
		    for (var i in p) {
		        s.push(i + '=' + encodeURIComponent(p[i] || ''));
		    }
		    var _src = "http://service.weibo.com/share/share.php?" + s.join('&') ;
		    window.open(_src);
		})
		/**
		 * qq分享
		 */
	    $("#qq_share").click(function(){
	    	 var  p = {
	 		        url: weboshare_url,/*获取URL，可加上来自分享到QQ标识，方便统计*/
	 		        desc: '中医传承', /*分享理由(风格应模拟用户对话),支持多分享语随机展现（使用|分隔）*/
	 		        title : data.resultObject.courseName,/*分享标题(可选)*/
	 		        summary : data.resultObject.description,/*分享描述(可选)*/
	 		        pics : data.resultObject.smallImgPath  /*分享图片(可选)*/
	 		    };
	 		    var s = [];
	 		    for (var i in p) {
	 		        s.push(i + '=' + encodeURIComponent(p[i] || ''));
	 		    }
	 		    var _src = "http://connect.qq.com/widget/shareqq/index.html?" + s.join('&') ;
	 		    window.open(_src);
		})
		/*$("#weibo_share").attr("href","http://service.weibo.com/share/share.php?url="+weboshare_url+"&title="+data.resultObject.description)
		$("#qq_share").attr("href","http://connect.qq.com/widget/shareqq/index.html?url="+weboshare_url+"&title="+data.resultObject.description)*/
	}, false);
});