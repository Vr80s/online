var teacherId;
var teacherName;
var collectionId = "";
var courseName = "中医传承平台";
var	smallImgPath = "https://file.ipandatcm.com/data/picture/online/2017/12/18/15/12db98e4fc674f1d9b1e5995d2c533d3.jpg";
var	description = "零基础也能学中医！许多学员推荐“古籍经典”系列以及【小宝中医带你快速入门学针灸】课程作为他们的入门必备。";
var courseId = course_id;

var startTime = "";
if(courseId == undefined) {
	courseId = 1;
}

/**
 * 设置返回按钮
 */
$("#return").attr("href","/courses/"+course_id+"/info");


var loginUserId = "";
var loginStatus = true;
var smallHeadPhoto = "";
var nickname = "";
//判断有没有登录
RequestService("/online/user/isAlive", "GET", null, function (data) {
    if (data.success) {
        loginUserId = data.resultObject.id;
        smallHeadPhoto = data.resultObject.smallHeadPhoto;
        nickname = data.resultObject.name;
    }
}, false)

var vhallObj = {
    appId: appid,
    accountId:loginUserId
};

RequestService("/vhallyun/vhallYunToken","get",{channelId:vhallObj.channelId,roomId:vhallObj.roomId},
    function(data) {
    if (data.success) {
        vhallObj.token=data.resultObject;
    }   
},false); 

//直播间访问量增加
RequestService("/online/live/updateBrowseSum", "get", {
	courseId: courseId
}, function(data) {
});

//获取直播间课程信息
RequestService("/online/live/getOpenCourseById", "GET", {
	courseId: courseId,
	planId: ""
}, function(data) {
	//分享使用
	courseName = data.resultObject.courseName;
	smallImgPath = data.resultObject.smallImgPath;
	description = data.resultObject.description;
	teacherId = data.resultObject.teacherId;
	teacherName = data.resultObject.teacherName;
	//讲师名字
	$(".headMess .name").html(data.resultObject.teacherName);
	teacherName = data.resultObject.teacherName;
	//鲜花数
//		$(".headMess .num").html(data.resultObject.flowers_number);
	$(".headMess .learnd").html(data.resultObject.learn_count);
	//课程名称
	$("#courseName").html(data.resultObject.courseName);
    $("#title-share").html(data.resultObject.courseName); 
	$(".liveMess .lb span").html(data.resultObject.courseName);
	//教师头像
	$(".headImg img").attr("src", data.resultObject.head_img);
	//开始结束时间
	$(".liveMess .liveTime span").html('' + data.resultObject.start_time);
	$(".liwu").html(data.resultObject.giftCount);
	$(".dashang").html(data.resultObject.rewardTotal);
	if(data.resultObject.broadcastState==1){
		$("#liveStatus").html("【正在直播】");
	}else if(data.resultObject.broadcastState==3){
		$("#liveStatus").html("【直播回放】");
	}
	startTime = data.resultObject.startTime;
	
	$(".cover-plan-img").attr("src",data.resultObject.smallImgPath);
	
	vhallObj.roomId = data.resultObject.direct_id;
    vhallObj.channelId = data.resultObject.channel_id;
    vhallObj.recordId = data.resultObject.record_id;
	
},false);

	
initChat();

/**
 * 初始化消息
 */
function initChat(){
    /**
     * 加载消息
     */
    setTimeout(function(){
       window.Vhall.ready(function(){
        window.chat = new VhallChat({
           channelId:vhallObj.channelId //频道Id
        });
        /**
         * 监听自定义消息
         */
        window.chat.onCustomMsg(function(msg){
             msg = JSON.parse(msg);
             try{
                if(msg.type == 12){ // 开始直播啦
                	// 刷新页面 --》在观看
                	setTimeout(function () {
                		location.reload();
                	},3000)
                }
             }catch(error){
               console.error(error);
             }
        })
     });	
     window.Vhall.config({
          appId :vhallObj.appId,//应用 ID ,必填
          accountId :vhallObj.accountId,//第三方用户唯一标识,必填
          token:vhallObj.token//token必填
     });
    },1000);	
      
}	
	
	
var fn = function(options, callback) {
    var setting = $.extend({
        element: null,
        offset: +8
    }, options);
    if (!setting.element) {
        console.error("element is null!");
        return;
    }
    var endDate = startTime, 
    startDate = new Date(), 
    offset = 0;
    if (!endDate) {
        console.error("Incorrect endDate format, it should look like this, 2016-09-18 12:00:00.");
        return;
    }
    if (startDate) {
        offset = startDate.getTime() - new Date().getTime();
    }
    if ("-".indexOf(endDate) > -1) {
        endDate = endDate.replace(/-/g, "/");
    }
    var container = setting.element;
    function countDown() {
        var endtime = new Date(endDate);
        //设置当前时间
        var now = new Date();
        //得到结束与当前时间差 ： 毫秒
        var _diff = endtime.getTime() - now.getTime();
        
        if (_diff < 0) {
            clearInterval(interval);
            if (callback && typeof callback === "function") callback();
            return;
        }
        var _second = 1e3, _minute = _second * 60, _hour = _minute * 60, _day = _hour * 24;
        var _days = Math.floor(_diff / _day), _hours = Math.floor(_diff % _day / _hour), _minutes = Math.floor(_diff % _hour / _minute), _seconds = Math.floor(_diff % _minute / _second);
        _days = String(_days).length >= 2 ? _days : "0" + _days;
        _hours = String(_hours).length >= 2 ? _hours : "0" + _hours;
        _minutes = String(_minutes).length >= 2 ? _minutes : "0" + _minutes;
        _seconds = String(_seconds).length >= 2 ? _seconds : "0" + _seconds;
        
 			container.find(".days").text(_days);
            container.find(".hours").text(_hours);
            container.find(".minutes").text(_minutes);
            container.find(".seconds").text(_seconds);
    }
    var interval = setInterval(countDown, 1e3);
};

var timeRunner = new fn({
	element: $(".timeRunner"),
	offset: +8
}, function() {
	console.log("倒计时结束");
});
