var teacherId;
var teacherName;

var collectionId = "";
var courseName = "中医传承平台";
var	smallImgPath = "https://file.ipandatcm.com/data/picture/online/2017/12/18/15/12db98e4fc674f1d9b1e5995d2c533d3.jpg";
var	description = "零基础也能学中医！许多学员推荐“古籍经典”系列以及【小宝中医带你快速入门学针灸】课程作为他们的入门必备。";
var courseId = course_id;

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
	 * 增加/更新  观看记录
	 */
	RequestService("/learnWatch/add", "POST", {
		courseId:courseId,
		recordType:2
	}, function(data) {
		console.log("添加观看记录");
	});
	
	
	//获取直播间课程信息
	RequestService("/online/live/getOpenCourseById", "GET", {
		courseId: courseId,
		planId: planId
	}, function(data) {

		var obj = data.resultObject;
		
		//分享使用
		courseName = obj.courseName;
		smallImgPath = obj.smallImgPath;
		description = obj.description;
		
		teacherId = obj.teacherId;
		teacherName = obj.teacherName;
		//讲师名字
		$(".headMess .name").html(obj.teacherName);
		teacherName = obj.teacherName;
		//鲜花数
//		$(".headMess .num").html(obj.flowers_number);
		$(".headMess .learnd").html(obj.learn_count);
		//课程名称
		$("#courseName").html(obj.courseName);
        $("#title-share").html(obj.courseName); 
		$(".liveMess .lb span").html(obj.courseName);
		//教师头像
		$(".headImg img").attr("src", obj.head_img);
		//开始结束时间
		$(".liveMess .liveTime span").html('' + obj.start_time);
		$(".liwu").html(obj.giftCount);
		$(".dashang").html(obj.rewardTotal);
		if(liveStatus==1){
			$("#liveStatus").html("【正在直播】");
		}else if(liveStatus==3){
			$("#liveStatus").html("【直播回放】");
		}
		
		vhallObj.roomId = obj.direct_id;
        vhallObj.channelId = obj.channel_id;
        vhallObj.recordId = obj.record_id;
	},false);
	
	//直播间访问量增加
	RequestService("/online/live/updateBrowseSum", "get", {
		courseId: courseId
	}, function(data) {
	});
	

	var n = "";
	var s = "";

	$("#chat-content").bind("input", function() {
		var num = 140 - $("#chat-content").val().length;
		if($("#chat-content").val() == "") {
			$(".right-text .sub").attr("data-send", "all");
		}
		if(num >= 0) {
			$(".right-list span").html(num);
		} else {
			$(".right-list span").html(0);
		}
	});


function refreshBalance(){
	//获取个人熊猫币余额
	RequestService("/userCoin/balance", "GET", {
	}, function(data) {
		var balance = data.resultObject;
		$(".balanceTotal").html(balance);
		//熊猫币余额
		$("#xmb_ye").html("熊猫币"+balance);
		$("#account").html(balance);
	});
}
refreshBalance();

