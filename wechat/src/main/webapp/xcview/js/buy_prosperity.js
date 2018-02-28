


/**
 * 进入购买成功后的页面
 */
var courseId = getQueryString("courseId");

/**
 * 根据课程id得到课程简单信息
 */

var type ="";
var collection="";
requestService("/xczh/course/details",{courseId:courseId}, function(data) {
	 if (data.success) {
         var result = data.resultObject;
         $(".purchase_details_title").find(".span0").html(result.name);
         $(".purchase_details_title").find(".span1").html(result.gradeName);
         $(".purchase_details_money").find("span").html(result.currentPrice);
         $("#smallImgPath").attr("src",result.smallImgPath);
         
         type= result.type;
         collection=result.collection
         requestService("/xczh/course/guessYouLike",{courseId:courseId}, function(data) {
        	 if (data.success) {
                 var result = data.resultObject.records;
                 $(".lectures").html(template('lectures',{items:result}))
             }
        	 
        },false)
     }
},false)

/**
 * 加载跳转页面
 * @param id
 */
function jump(){	
	var id = courseId;
	if(type==1||type==2){
		if(collection==true){
//					专辑视频音频播放页
		location.href="live_select_album.html?course_id="+id					
		}else{
//					单个视频音频播放
		location.href="live_audio.html?my_study="+id					
		}
	}else if(type==3){
//					播放页面
		location.href="live_play.html?my_study="+id									
	}else{
//					线下课页面
		location.href="live_class.html?my_study="+id									
	}		

}

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






