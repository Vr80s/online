

/*直播间开始*/
// 一、获取是否医师权限。二、获取完权限，获取课程。三、获取完课程判断类型。
// 点击直播间回放和直播中状态跳转发礼物直播间
function detailsId(){
	location.href = "/xcview/html/details.html?courseId=" + id
};

// 定义无直播状态方法--为您推荐，默认图
function defaultId(){
	requestService("/xczh/course/recommendSortAndRand", null,function (data) {
	    if (data.success == true) {
		    // 为您推荐
	        $('#broadcastroom_course').html(template('broadcastroom_id', {items: data.resultObject}));
	    }
	});
}
// 定义获取当前页面id
var doctorId = getQueryString('doctor');

requestService("/xczh/doctors/doctorStatus", {doctorId:doctorId},function (data) {  //一、获取是否医师权限。
    if (data.success == true) {
    	// 0 无权限 1 医师认证通过 2 医馆认证通过 3 医师认证被禁用
	    var status = data.resultObject.status;
    	if (status == 0 || status == 3) {
	   		 	$(".no_live").css("display","block");
	   		 	$("#recommended").css("display","block");
	   		 	$(".living_broadcastroom").css("display","none");  /*封面图隐藏*/
	   		 	$("#live_lesson").css("display","none");  /*直播课程*/
    			defaultId();
        }else{
    		userId = data.resultObject.userId;
    		var type = 3;
			requestService("/xczh/course/courseTypeNumber", {  //二、获取完权限，获取课程。
				userId : userId,
				type : type
				},function (data) {
				    if (data.success) {
				    	var number = data.resultObject;
					    if (number > 0) {   //三、获取完课程判断类型。
					    	requestService("/xczh/doctors/recentlyLive", {userId:userId},function (data) {  
							    if (data.success == true) {
								    // 直播状态
								    //直播课程状态：lineState  1直播中， 2预告，3直播结束 ， 4 即将直播 ，5 准备直播 ，6 异常直播
							        $('#living_broadcastroom').html(template('living_broadcastroom_id', {items: data.resultObject}));
							    
								    function timer() {
								        //设置结束的时间
								        var startTime = data.resultObject.startTime;
								        // var endtime = new Date("2020/04/22 00:00:00");
								        var endtime = new Date(startTime);
								        //设置当前时间
								        var now = new Date();
								        //得到结束与当前时间差 ： 毫秒
								        var t = endtime.getTime() - now.getTime();
								        if (t > 0) {
								            //得到剩余天数
								            // var tian = Math.floor(t / 1000 / 60 / 60 / 24);
								            //得到还剩余的小时数（不满一天的小时）
								            var h = Math.floor(t / 1000 / 60 / 60 % 24);
								            if(h<10){
												h="0"+h;
								            }
								            //得到分钟数
								            var m = Math.floor(t / 1000 / 60 % 60);
								            if(m<10){
												m="0"+m;
								            }
								            //得到的秒数
								            var s = Math.floor(t / 1000 % 60);
								            if(s<10){
												s="0"+s;
								            }
								            var str = "直播倒计时 " + h + "：" + m + "：" + s;
								            $("#box1").html(str);
								        } /*else {
								            //clearInterval(timer1); //这里可以添加倒计时结束后需要执行的事件 
								            //alert("倒计时结束后执行");
								        }*/
								    }
									setInterval(timer, 1000);

								    }
								});

								requestService("/xczh/course/liveDetails", {userId:userId},function (data) {  
								    if (data.success == true) {
									    // 直播状态
									    //直播课程状态：lineState  1直播中， 2预告，3直播结束 ， 4 即将直播 ，5 准备直播 ，6 异常直播
								        $('#living_broadcastroom').html(template('living_broadcastroom_id', {items: data.resultObject}));
								    }
								});

						    	// 直播课程
								requestService("/xczh/doctors/doctorCourse", {userId:userId},function (data) {  
								    if (data.success == true) {
									    // 直播课程
								        $('#live_streaming').html(template('live_streaming_id', {items: data.resultObject[1].courseList}));
								    }
								});
						    }else{
						    	$(".no_live").css("display","block");     /*默认背景图*/
					   		 	$("#recommended").css("display","block"); /*显示为您推荐*/
					   		 	$(".living_broadcastroom").css("display","none");  /*封面图隐藏*/
					   		 	$("#live_lesson").css("display","none");  /*直播课程*/
						    	defaultId();
						    };

					    }

					    	/*介绍开始*/
							// doctorIds = data.resultObject.doctorId;
							requestService("/xczh/doctors/introduction", {doctorId:getQueryString('doctor')},function (data) {
							    if (data.success == true) {
								    // 介绍
							        $('#message_referral').html(template('message_referral_id', {items: data.resultObject.hospital}));
							        // $('#message_referral').html(template('self_introduction_id', {items: data.resultObject}));
							    	
							    	// 个人介绍
									if(data.resultObject.description == null || data.resultObject.description == ''){
										
										$(".self_introduction").hide();
									}else{

										$(".self_introduction_cen").html(data.resultObject.description);
										
									};


									var str = data.resultObject.workTime; //这是一字符串 
									var ss = str.split(",");
									// 先把所有的隐藏
									$(".aaa img").hide();
									for (var i = 0; i < ss.length; i++) {
										$(".aaa_"+ss[i]+" img").show();
									};

									

									

							    }
							});
							/*介绍结束*/

					});
					
        	}

    }




});
/*直播间结束*/












