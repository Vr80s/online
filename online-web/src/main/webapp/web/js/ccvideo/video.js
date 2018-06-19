/**
 * Created by fanwenqiang on 2016/11/2.
 */
var courseId = $.getUrlParam("courseId");
var collectionId = "";
var courseName = "中医传承平台";
var	smallImgPath = "https://file.ipandatcm.com/data/picture/online/2017/12/18/15/12db98e4fc674f1d9b1e5995d2c533d3.jpg";
var	description = "零基础也能学中医！许多学员推荐“古籍经典”系列以及【小宝中医带你快速入门学针灸】课程作为他们的入门必备。";

var multimediaType = 1;
var userId= "";
var directId = "";
/**
 * 设置返回课程详情a标签
 */
$("#return").attr("href","/courses/"+courseId+"/info");



var menuid = "";
var pageNumber = 1;
var pageSize = 15;
//视频播放窗口加载
//获取当前屏幕高度
var allwidth = parseInt($(".videoBody-top").width());
//当前屏幕左半部分宽度
var awidth = parseInt($(".videoBody-top").width() - 290);
var aheight = parseInt($(window).height() - $(".header").height());
$(".videoBody-top").height(aheight);
$(".videoBody-menuList").height(aheight);
$(".videoBody-video").height(aheight);
$(".loadImg").css("display", "block");
//时间格式处理
function timeChange(num) {
	return '' + num + "分钟";
};


//获取课程名字和讲师姓名
RequestService("/online/live/getOpenCourseById", "get", {
	courseId: courseId
}, function(data) {
	if(!data.success){
		location.href="/courses/"+courseId+"/info";
	}
	
	var obj =  data.resultObject;''
	
	//分享使用
	courseName = obj.courseName;
	smallImgPath = obj.smallImgPath;
	description = obj.description;

	//数据渲染
	$(".headerBody .rightT p").html(obj.courseName).attr("title", obj.courseName);
	document.title = data.resultObject.courseName ;
	$(".headerBody .rightT i").html(obj.lecturer);
	
	$(".headerBody-title").html(obj.courseName);
	
	//如果是音频的话，需要自己去设置哪里播放
    multimediaType = obj.multimediaType;
    directId = obj.direct_id;
}, false);


//获取视频信息接口
RequestService("/online/user/isAlive", "GET", null, function(data) { ///online/user/isAlive
	if(data.success === true) {
		
		userId = data.resultObject.id;
		
		RequestService("/online/vedio/getPlayCodeByCourseId", "GET", {
            courseId: courseId,
			width: awidth,
			height: aheight,
			directId:directId,
			multimediaType:multimediaType,
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
				
				
				var playCodeStr = data.resultObject;
	            var playCodeObj = JSON.parse(playCodeStr);
	            console.log(playCodeObj.video.playcode);
	            
				$(".videoBody-video").html(playCodeObj.video.playcode);
			
				
			} else if(data.success == false) {
				
				//弹出错误信息
				alert(data.errorMessage);
			}
		});
	} else {
		/**
		 * 如果用户没有登录直接就搞走呗
		 */
		location.href="/courses/"+courseId+"/info";
	}
},false);



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

var key = userId + courseId;
//b6df19ee5ff64d96bddcf0b0f1fd83252154
var time = localStorage.getItem(key);

/*
 * 开始播放了
 */
function custom_player_start(){
	//alert("开始播放了");
    if(multimediaType == 2){
    	if(time!=null && time != undefined && time != ""){
    		player.seek(time);
    	}
    }
}

/**
 * 播放暂定了
 * @returns
 */
function custom_player_pause(){
	//alert("开始暂停了");
}
/**
 * 播放器准备就绪
 * @returns
 */
function custom_player_ready(){
	
	player.start();
	
}


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
   
//    config.player_plugins = {// 插件名称 : 插件参数
//        Subtitle : {
//            url : "http://dev.bokecc.com/static/font/example.utf8.srt"
//            , size : 24
//            , color : 0xFFFFFF
//            , surroundColor : 0x3c3c3c
//            , bottom : 0.15
//            , font : "Helvetica"
//            , code : "utf-8"
//        }
//    };
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
		videoPositionRecording(player.getPosition());
	}
}

var key ="";
function videoPositionRecording(time){
	if(userId!="" && courseId!=""){
		key == ""? key = userId+courseId : key;
		
		/**
		 * 获取的是秒
		 * player.getPosition()
		 */
		localStorage.setItem(key,player.getPosition());
	}
}







