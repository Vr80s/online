

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
         $(".purchase_details_title").find(".span0").html(result.name);
         $(".purchase_details_title").find(".span1").html(result.gradeName);
         $(".purchase_details_money").find("span").html(result.currentPrice);
         $("#smallImgPath").attr("src",result.smallImgPath);
         
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

/**
 * 猜你喜欢
 */
function guesYouLike(type,courseId){
//	if(type == 1){ //直播间   直播中、即将直播、直播回放
//		location.href="details.html?courseId="+courseId
//	}else if(type ==2){ // 预告
//		location.href="live_play.html?my_study="+courseId
//	}else if(type ==3){ //课程页面
//		location.href="live_audio.html?my_study="+courseId
//	}else if(type ==4){ //线下培训班
//		location.href="live_class.html?my_study="+courseId
//	}else if(type ==5){ //专辑
//		location.href="live_select_album.html?my_study="+courseId
//	}
	
	
//	<!--<a style="position: absolute; width: 100%; height: 100%; top: 0; left: 0; z-index: 999;" href="javascript:;" onclick="jump({{item.id}})"></a>-->
//	{{if item.type == 1 || item.type == 2}}
//	<a style="position: absolute; width: 100%; height: 100%; top: 0; left: 0; z-index: 999;" href="school_audio.html?course_id={{item.id}}"></a>
//	{{else if item.type == 3}}
//	<a style="position: absolute; width: 100%; height: 100%; top: 0; left: 0; z-index: 999;" href="school_play.html?course_id={{item.id}}"></a>
//	{{else if item.type == 4}}
//	<a style="position: absolute; width: 100%; height: 100%; top: 0; left: 0; z-index: 999;" href="school_class.html?course_id={{item.id}}"></a>
//	
//	{{/if}}
	
	if(type == 1 ||type ==2){ //课程
		location.href="school_audio.html?course_id="+courseId
	}else if(type ==3){ // 直播
		location.href="school_play.html?course_id="+courseId
	}else if(type ==4){ // 线下班
		location.href="school_class.html?course_id="+courseId
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






