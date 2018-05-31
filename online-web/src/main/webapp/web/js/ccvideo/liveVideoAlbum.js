/**
 * Created by fanwenqiang on 2016/11/2.
 */


//应该搞第一个课程了
var collectionId = $.getUrlParam("collectionId");

$("#return").attr("href","/courses/"+collectionId+"/info");

var courseId = $.getUrlParam("courseId");

var ljxx = $.getUrlParam("ljxx");
var falgCollectionId = "_"+collectionId;
var falgId = "_"+collectionId+"_"+courseId;

/**
 * 增加学习记录
 */
RequestService("/learnWatch/add", "POST", {
	courseId:collectionId,recordType:1
}, function(data) {
	console.log("添加观看记录");
},false);


//获取播放代码
/**
 * 
 * @param collectionId
 * @param courseId
 * @returns
 */
function getPlayCode(collectionId,courseId){
	
	RequestService("/learnWatch/add", "POST", {
		collectionId:collectionId,
		courseId:courseId,
		recordType:2
	}, function(data) {
		console.log("添加观看记录");
	},false);
	
	
	var vId = $.getUrlParam("vId");
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
	
	RequestService("/online/vedio/getPlayCodeByCourseId", "GET", {
		collectionId:collectionId,
        courseId: courseId,
		width: awidth,
		height: aheight,
		autoPlay: false
	}, function(data) {
		if(data.success == true) {
			localStorage.setItem("playRecording"+falgCollectionId,"playRecording"+falgId);
			var scr = data.resultObject.playCode;
			$(".videoBody-video").append(scr);
			$(".headerBody-title").html(data.resultObject.title);
		} else if(data.success == false) {
			//弹出错误信息
			alert(data.errorMessage);
		}
	});
}

$(function() {
	
	var falgId =  localStorage.getItem("playRecording"+falgCollectionId);
	//如果点击立即学习的话，就从第一个开始播放
	if(ljxx != null && ljxx != undefined && "" != ljxx && falgId!=null &&
			falgId != undefined && "" != falgId){
		//从记录里面去下
		var falgArray = falgId.split("_");
		if(falgArray.length == 3){
			courseId = falgArray[2];
		}
	}
	
	/**
	 * 获取播放代码
	 * @returns
	 */
	getPlayCode(collectionId,courseId);
	
	/**
	 * 请求专辑列表
	 */
	RequestService("/course/newGetCoursesByCollectionId", 
			"GET", {collectionId:collectionId}, function(data) { ///online/user/isAlive
		if(data.success === true) {
			var list =  data.resultObject;
			var lalal ="";
			for (var i = 0; i < list.length; i++) {
				var obj = list[i];
				var li = "<li>";
				if(courseId == obj.id){
					li = "<li class='choosedAlbum' data-id = "+obj.id+">";
				}else{
					li = "<li data-id = "+obj.id+">"
				}
				lalal += (li+"<div>" +
					"<span class='playbtn'></span>" +
					"<span class='teacher_name'>"+obj.name+"</span>" +
					"<span class='album_title'>"+obj.gradeName+"</span> " +
					"<span class='album_time'>"+obj.courseLength+"分钟</span>" +
					"</div></li>");
			}
			$(".album_list ul").html(lalal);
		}
	},false);
	
	
	/**
	 * 点击选集
	 */
	$(".album_list li").click(function(){
	   var courseId = $(this).attr("data-id");
	   //$(this).attr("calss","choosedAlbum");
	   //getPlayCode(collectionId,courseId);
	   location.href = "/web/html/ccvideo/liveVideoAlbum.html?collectionId="+collectionId+"&courseId="+courseId;
	})
	
	
	
	
	//时间格式处理
	function timeChange(num) {
		return '' + num + "分钟";
	};
	//获取视频信息接口
	RequestService("/online/user/isAlive", "GET", null, function(data) { ///online/user/isAlive
		if(data.success === true) {
			//getPlayCode(collectionId,courseId);
		} else {
			/**
			 * 如果用户没有登录直接就搞走呗
			 */
			//location.href="/course/courses/"+courseId;
		}
	});
	
	
	
	//-- shareType 分享类型 1 课程  2 主播
	var qrcodeurl = "/wx_share.html?shareType=1&shareId=1";
	$(document).ready(function() {
		$("#qrcodeCanvas1").qrcode({
			render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
			text : qrcodeurl,    //扫描了二维码后的内容显示,在这里也可以直接填一个网址，扫描二维码后
			width : "90",               //二维码的宽度
			height : "90",              //二维码的高度
			background : "#ffffff",       //二维码的后景色
			foreground : "#000000",        //二维码的前景色
			src: '/web/images/yrx.png'             //二维码中间的图片
		});
		$("#qrcodeCanvas2").qrcode({
			render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
			text : qrcodeurl,    //扫描了二维码后的内容显示,在这里也可以直接填一个网址，扫描二维码后
			width : "90",               //二维码的宽度
			height : "90",              //二维码的高度
			background : "#ffffff",       //二维码的后景色
			foreground : "#000000",        //二维码的前景色
			src: '/web/images/yrx.png'             //二维码中间的图片
		});
	});
	
	
	
	//请求集合查看列表
	
	//获取课程名字和讲师姓名
	RequestService("/online/live/getOpenCourseById", "get", {
		courseId: courseId
	}, function(data) {
		if(!data.success){
			//location.href="/course/courses/"+courseId;
		}
		$(".headerBody .rightT p").html(data.resultObject.courseName).attr("title", data.resultObject.courseName);
		document.title = data.resultObject.courseName ;
		$(".headerBody .rightT i").html(data.resultObject.lecturer);
		menuid = data.resultObject.menu_id;
		var host = window.location.host;
		//var weboshare_url="http://"+host+"/course/courses/"+courseId; courses/704/info
		var weboshare_url="http://"+host+"/courses/"+courseId+"/info";
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




