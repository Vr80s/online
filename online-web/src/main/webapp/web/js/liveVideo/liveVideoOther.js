var teacherId;
var teacherName;
var collectionId = "";
var courseName = "中医传承平台";
var	smallImgPath = "https://file.ipandatcm.com/data/picture/online/2017/12/18/15/12db98e4fc674f1d9b1e5995d2c533d3.jpg";
var	description = "零基础也能学中医！许多学员推荐“古籍经典”系列以及【小宝中医带你快速入门学针灸】课程作为他们的入门必备。";
var courseId = course_id;
$(function() {
	var roomid = room_id;
	var planId = plan_id;
	if(courseId == undefined) {
		courseId = 1;
	}
	/**
	 * 设置返回按钮
	 * @returns
	 */
	$("#return").attr("href","/courses/"+course_id+"/info");
	/**
	 * 增加学习记录
	 */
	RequestService("/learnWatch/add", "POST", {
		courseId:courseId,recordType:1
	}, function(data) {
		console.log("添加观看记录");
	},false);
	
	//直播间访问量增加
	RequestService("/online/live/updateBrowseSum", "get", {
		courseId: courseId
	}, function(data) {
	});
	
	
	//获取直播间课程信息
	RequestService("/online/live/getOpenCourseById", "GET", {
		courseId: courseId,
		planId: planId
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
	});
	

})

