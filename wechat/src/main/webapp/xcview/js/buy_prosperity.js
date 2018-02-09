


/**
 * 进入购买成功后的页面
 */
var courseId = getQueryString("courseId");

/**
 * 根据课程id得到课程简单信息
 */

var type 
requestService("/xczh/course/details",{courseId:courseId}, function(data) {
	 if (data.success) {
         var result = data.resultObject;
         $(".purchase_details_title").find(".span0").html(result.name);
         $(".purchase_details_title").find(".span1").html(result.gradeName);
         $(".purchase_details_money").find("span").html(result.currentPrice);
         $("#smallImgPath").attr("src",result.smallImgPath);
         
         requestService("/xczh/course/guessYouLike",{courseId:courseId}, function(data) {
        	 if (data.success) {
                 var result = data.resultObject.records;
                 $(".lectures").html(template('lectures',{items:result}))
             }
        	 
        },false)
     }
},false)

/**
 * 去学习中心
 */
$(".check_one span").click(function(){
	
	location.href="/xcview/html/my_study.html";
})
/**
 * 学堂
 */
$(".check_two span,.header_return").click(function(){
	
	location.href="/xcview/html/home_page.html";
})
/**
 * 加载跳转页面
 * @param id
 */
function jump(){
	
	var id = courseId;
	
	requestService("/xczh/course/details?courseId="+courseId,null,function(data) {

		var course = data.resultObject;
		if(course.watchState == 0||course.watchState == 1){
			if(course.type==1||course.type==2){
//				视频音频购买
				location.href="school_audio.html?course_id="+id
			}else if(course.type==3){
//				直播购买
				location.href="school_play.html?course_id="+id
			}else{
//				线下课购买
				location.href="school_class.html?course_id="+id
			}			
		}else if(course.watchState == 2||course.watchState == 3){
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
				location.href="live_audio.html?my_study="+id									
			}else{
//					线下课页面
				location.href="live_class.html?my_study="+id									
			}		
		}
	})
}





