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
	 * @returns
	 */
	$("#return").attr("href","/courses/"+course_id+"/info");

	
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
		
	},false);

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
    function NewDate(str) {
        str = str.split(" ");
        var date = new Date();
        var date1 = str[0].split("-");
        var date2 = str[1].split(":");
        date.setUTCFullYear(date1[0], date1[1] - 1, date1[2]);
        date.setUTCHours(date2[0], date2[1], date2[2], 0);
        return date;
    }
    if (startDate) {
        offset = startDate.getTime() - new Date().getTime();
    }
    if ("-".indexOf(endDate) > -1) {
        endDate = endDate.replace(/-/g, "/");
    }
    var container = setting.element;
    var currentDate = function() {
        var _date = new Date();
        var _utc = _date.getTime() + offset + _date.getTimezoneOffset() * 6e4;
        var _new_date = new Date(_utc + 36e5 * setting.offset);
        return _new_date;
    };
    function countDown() {
        var _target_date = NewDate(endDate), 
        _current_date = currentDate();
        var _diff = _target_date - _current_date;
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
