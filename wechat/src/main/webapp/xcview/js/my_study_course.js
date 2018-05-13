
/**
 * 保存openId
 */
var openId = getQueryString("openId");
if(stringnull(openId)){
    localStorage.setItem("openid",openId);
}

$(function(){
		
//已购课程/结束课程	

//	var no_class='<p style="color: #a5a5a5;">暂无课程...</p>'
	// requestService("/xczh/myinfo/list",{pageSize:5},function(data) {  备份之前接口
	// requestService("/xczh/myinfo/myCourseType",{pageNumber:1,pageSize:500,type:1},function(data) {    备份新接口
	requestService("/xczh/myinfo/list",null,function(data) {
			$(template("data_my_class",{items:data.resultObject[0]})).appendTo("#my_class_box");
			// $(template("data_my_class",{items:data.resultObject[1]})).appendTo("#my_class_box");
			if(data.resultObject[0].courseList.length==0){				
				$("#my_class_box").hide()
				$(".wrap_noClass").show();
			}
			
//点击播放视频后才开始记录播放历史	
//直播中
	$(".paly_ing_all").click(function(){
			var courseId=$(this).attr("data-ppd");
			
			//更新下观看记录
			requestService("/xczh/history/add",{courseId:courseId,recordType:2},function(data) {
				console.log();
			})	
			
			location.href="details.html?courseId="+courseId
		})
//即将直播
	$(".paly_ing_all_now").click(function(){
		  var courseId_now=$(this).attr("data-ppdnow");

		 location.href="/xcview/html/live_play.html?my_study="+courseId_now;	
	})
	})	

})
