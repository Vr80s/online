$(function(){
//	播放历史
	requestService("/xczh/history/list",null,function(data) {
		if(data.resultObject.records.length==0){
			$(".wrap-his-play").hide()
		}else{
			$(".wrap-his-play").show()
			$("#paly_history").html(template("data_history",{items:data.resultObject.records}))
			
		}

	})	

//	点击清除播放历史弹出确认取消框
	$(".play-title p").click(function(){
		$(".history_bg").show();
		$(".history_modal").show();
	})
	$(".history_modal ul li:nth-child(1)").click(function(){
		$(".history_bg").hide();
		$(".history_modal").hide();
	})
	$(".history_modal ul li:nth-child(2)").click(function(){
		requestService("/xczh/history/empty",null,function(data) {			
		$("#paly_history").hide()
		$(".history_bg").hide();
		$(".history_modal").hide();
		location.href="my_study.html"
		})
		
	})	
		
//已购课程/结束课程	
					
	var no_class='<p style="color: #a5a5a5;">暂无课程...</p>'
	requestService("/xczh/myinfo/list",null,function(data) {
		
		
			$(template("data_my_class",{items:data.resultObject[0]})).appendTo("#my_class_box");
			$(template("data_my_class",{items:data.resultObject[1]})).appendTo("#my_class_box");
		
		
		$(".paly_ing").click(function(){
			var courseId=$(this).attr("data-id");
			requestService("/xczh/history/add",
			{courseId:courseId}
			,function(data) {

			})	
		})

	})	
	
//我关注的主播
		requestService("/xczh/myinfo/myFocus",null,function(data) {
			if(data.resultObject.length=='' || data.resultObject.length==0 ){
				$(".no_follow_uesr").show()
			}
			$("#all_follow_people").html(template("data_follow",data))		
		})	
	
//点击播放视频后才开始记录播放历史

$(".paly_ing").click(function(){
	requestService("/xczh/history/add",
	courseId=courseId
	,function(data) {
		alert(1)
	})	
})

})


//搜索历史播放
function go_play_hos(id){
	requestService("/xczh/course/details?courseId="+id,null,function(data) {

		var course = data.resultObject;
		if(course.watchState == 2||course.watchState == 3 || course.watchState == 1){
			if(course.type==1||course.type==2){
				if(course.collection){
//					专辑视频音频播放页
				location.href="live_select_album.html?course_id="+id					
				}else{
//					单个视频音频播放
				location.href="live_audio.html?my_study="+id					
				}
			}else if(course.type==3){
//					播放页面
				location.href="live_play.html?my_study="+id									
			}else{
//					线下课页面
				location.href="live_class.html?my_study="+id									
			}		
		}
	})
}
//搜索历史播放结束





