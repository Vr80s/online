//搜索历史结果开始
$(function(){
requestService("/xczh/course/details",{courseId:748}, 
    function(data) {
		
		if(data.success==true){

//			检索列表
 	    	$(".course_video_bottom").html(template('course_video_bottom',{items:data.resultObject}))
 	    	$(".course_video_main_time").html(template('course_video_main_time',{items:data.resultObject}))

 	    	
		}
		
},false) 	


});




//搜索历史结果结束










