

/**
 * 进入购买成功后的页面
 */
var courseId = getQueryString("courseId");

/**
 * 根据课程id得到课程简单信息
 */

var type ="";
var lineState="";
var collection="";
requestService("/xczh/course/details",{courseId:courseId}, function(data) {
	 if (data.success) {
         var result = data.resultObject;

         $(".purchase_details_title").find(".span1").html(result.gradeName);
         $(".purchase_details_title").find(".span0").html(result.name);
         
         $(".purchase_details_money").find("span").html(result.currentPrice);
         $("#smallImgPath").attr("src",result.smallImgPath + '?imageView2/2/w/212' );
         
         type= result.type;
         lineState = result.lineState;
         collection=result.collection;
         
         requestService("/xczh/course/guessYouLike",{courseId:courseId}, function(data) {
        	 if (data.success) {
                 var result = data.resultObject.records;
                 $(".lectures").html(template('lectures',{items:result}))
             }
        	 
        },false)
     }
},false)

/**
 * 去直播
 * @param id
 */
function jump(){	
	var id = courseId;
	if(type ==3 && (lineState ==1 || lineState ==3 || lineState ==4)){ //直播间  
		 //增加一条学习记录
		 requestService("/xczh/history/add",
	               {courseId:id,recordType:2} ,function(data) {
	     }) 
		 location.href="details.html?courseId="+id
	}else if(type ==3 && (lineState ==2 || lineState ==5)){ //预告的、回放的
		location.href="live_play.html?my_study="+id
	}else if((type ==1 || type ==2) && !collection){ //课程页面
		location.href="live_audio.html?my_study="+id
	}else if(type ==4){								 //线下培训班
		location.href="live_class.html?my_study="+id
	}else if((type ==1 || type ==2) && collection){
		location.href="live_select_album.html?course_id="+id
	}
}

/**
 * 猜你喜欢
 */



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
	  sessionStorage.setItem("school_index",1);	
	location.href="/xcview/html/home_page.html";
})






