/**
 * Created by fanwenqiang on 2016/11/2.
 */


//应该搞第一个课程了
var collectionId = $.getUrlParam("collectionId");
var courseId = $.getUrlParam("courseId");

var ljxx = $.getUrlParam("ljxx");
var falgCollectionId = "_"+collectionId;
var falgId = "_"+collectionId+"_"+courseId;


$("#return").attr("href","/courses/"+collectionId+"/info");


var courseName = "中医传承平台";
var	smallImgPath = "https://file.ipandatcm.com/data/picture/online/2017/12/18/15/12db98e4fc674f1d9b1e5995d2c533d3.jpg";
var	description = "零基础也能学中医！许多学员推荐“古籍经典”系列以及【小宝中医带你快速入门学针灸】课程作为他们的入门必备。";

var userId= "";
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
	var aheight = parseInt($(window).height() - $(".header").height() - 65);
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


$(function() {
	
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
			userId = data.resultObject.id;
			
			//请求集合查看列表
			
			//获取课程名字和讲师姓名
			RequestService("/online/live/getOpenCourseById", "get", {
				courseId: collectionId
			}, function(data) {
				if(!data.success){
					location.href="/courses/"+collectionId+"/info";
				}
				$(".headerBody .rightT p").html(data.resultObject.courseName).attr("title", data.resultObject.courseName);
				document.title = data.resultObject.courseName ;
				$(".headerBody .rightT i").html(data.resultObject.lecturer);
				menuid = data.resultObject.menu_id;
				
				//分享使用
				courseName = data.resultObject.courseName;
				smallImgPath = data.resultObject.smallImgPath;
				description = data.resultObject.description;
				
			}, false);
		} else {
			/**
			 * 如果用户没有登录直接就搞走呗
			 */
			location.href="/courses/"+collectionId+"/info";
		}
	});
	
	
	
});



/*********************** 帅气的cc视频自定义事件  **************************/

function getSWF(objectId) {
    if (window.document[ objectId ]) {
        return window.document[ objectId ];
    } else if (navigator.appName.indexOf("Microsoft") == -1) {
        if (document.embeds && document.embeds[ objectId ]) {
            return document.embeds[ objectId ];
        }
    } else {
        return document.getElementById( objectId );
    }
}

var player ="";
/**
 * 播放器配置示例
 *
 * */
function on_cc_player_init(vid, objectId ){
    var config = {};
    
    console.log("sdada")
    //关闭右侧菜单
    config.rightmenu_enable = 0;
   
    config.on_player_seek = "custom_seek";
    config.on_player_ready = "custom_player_ready";
    config.on_player_start = "custom_player_start";
    config.on_player_pause = "custom_player_pause";
    config.on_player_resume = "custom_player_resume";
    config.on_player_stop = "custom_player_stop";
   
    config.player_plugins = {// 插件名称 : 插件参数
        Subtitle : {
            url : "http://dev.bokecc.com/static/font/example.utf8.srt"
            , size : 24
            , color : 0xFFFFFF
            , surroundColor : 0x3c3c3c
            , bottom : 0.15
            , font : "Helvetica"
            , code : "utf-8"
        }
    };
    player= getSWF(objectId);
    player.setConfig(config);
    
}



/**
 * 记录播放到哪里了。我的天
 * 获取当前播放时间（单位：秒）
 * @returns
 */
var int=self.setInterval("clock()",2000)
function clock(){
	if(player!=""){//存放到缓存里面，怎么存。
		//console.log(""+player.getPosition());
		videoAlbumPositionRecording(player.getPosition());
	}
}

var key ="";
function videoAlbumPositionRecording(time){
	if(userId!="" && collectionId!=""){
		key ==""? key=userId+collectionId : key;

		var recordingList = localStorage.getItem(key);
		var value = "%"+courseId+"="+time+"%";
		var courseKey = "%"+courseId+"=";
		
		if(recordingList==null || recordingList ==undefined || recordingList ==""){
			recordingList=value;
		}else if(recordingList.indexOf(courseKey)==-1){
			recordingList+=value;
		}else{
			var re = new RegExp("%"+courseId+"(\\S*)%","i"); 
			recordingList = recordingList.replace(re,value);
		}
		localStorage.setItem(key,recordingList);
	}
}




