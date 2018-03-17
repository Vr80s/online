
/**
 * 保存openId
 */
var openId = getQueryString("openId");
if(stringnull(openId)){
    localStorage.setItem("openid",openId);
}


$(function(){
var all_history="";	
//	播放历史
	requestService("/xczh/history/list",null,function(data) {
		all_history=data.resultObject.records.length;

		if(data.resultObject.records.length==0){
			$(".wrap-his-play").hide()
		}else{
			$(".wrap-his-play").show()
			//$("#paly_history").html(template("data_history",{items:data.resultObject.records}))
		
			var str ="";
			for (var int = 0; int < data.resultObject.records.length; int++) {
				
				var item = data.resultObject.records[int];
				str+="<li onclick='go_play_hos("+item.type+","+item.lineState+","+item.collection+","+item.courseId+")'>";
				str+="<p>"+item.lecturerName+"： "+item.gradeName+"</p>";
				str+="<span>"+item.timeDifference+"</span>";
				str+="</li>";
			}
			$("#paly_history").html(str);
		}

	})	
//		alert(all_history)
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

//	var no_class='<p style="color: #a5a5a5;">暂无课程...</p>'
	requestService("/xczh/myinfo/list",null,function(data) {

//			if(data.resultObject[0].courseList.startTime!="" || data.resultObject[0].courseList.startTime!=null){
//				data.resultObject[0].courseList.startTime=data.resultObject[0].courseList.startTime.replace(/-/g,".")
//			}else if(data.resultObject[1].courseList.startTime!="" || data.resultObject[1].courseList.startTime!=null){
//				data.resultObject[1].courseList.startTime=data.resultObject[1].courseList.startTime.replace(/-/g,".")
//			}
			$(template("data_my_class",{items:data.resultObject[0]})).appendTo("#my_class_box");
			$(template("data_my_class",{items:data.resultObject[1]})).appendTo("#my_class_box");
			if(data.resultObject[0].courseList.length==0){				
				$("#my_class_box").hide()
				$(".wrap_noClass").show();
			}
			
//点击播放视频后才开始记录播放历史	
//直播中
	$(".paly_ing_all").click(function(){
			var courseId=$(this).attr("data-ppd");
			requestService("/xczh/history/add",
			{courseId:courseId}
			,function(data) {

			})	
			location.href="details.html?courseId="+courseId
		})
//即将直播
	$(".paly_ing_all_now").click(function(){
			var courseId_now=$(this).attr("data-ppdnow");
			requestService("/xczh/history/add",
			{courseId:courseId_now}
			,function(data) {

			})	
			location.href="details.html?courseId="+courseId_now
		})
	})	
	
//我关注的主播
		requestService("/xczh/myinfo/myFocus",null,function(data) {
			if(data.resultObject.length=='' || data.resultObject.length==0 ){
//				$(".no_follow_uesr").show();您还没有关注的主播
				$(".wrap-follow").hide();   /*没有主播的时候--隐藏关注主播*/
			}
			$("#all_follow_people").html(template("data_follow",data))		
		})	
	




})


/*
 * 搜索历史播放   点击事件
 */
function go_play_hos(type,lineState,collection,id){
	if(type ==3 && (lineState ==1 || lineState ==3 || lineState ==4)){ //直播间
		location.href="details.html?courseId="+id
	}else if(type ==3 && (lineState ==2 || lineState ==5)){ //预告的、回放的
		location.href="live_play.html?my_study="+id
	}else if((type ==1 || type ==2) && !collection){ //课程页面
		location.href="live_audio.html?my_study="+id
	}else if(type ==4){								 //线下培训班
		location.href="live_class.html?my_study="+id
	}else if((type ==1 || type ==2) && collection){
		location.href="live_select_album.html?my_study="+id
	}
}
//搜索历史播放结束





