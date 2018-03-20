//搜索历史结果开始
$(function(){
requestService("/xczh/course/details",{courseId:748}, 
    function(data) {
		
		if(data.success==true){
		
		
   	    	$(".course_video_bottom").html(template('course_video_bottom',data.resultObject))
   	    	
   	    	$(".course_video_main_time").html(template('course_video_main_time',data.resultObject))
   	    	
// 	    	详情
   	    	$(".wrap").html(template('wrap',data.resultObject))
   	    	
// 	    	简介
   	    	$(".wrap1").html(template('wrap1',data.resultObject))
   	    	
// 	    	熊猫币
   	    	$(".footer_ul").html(template('footer_one',data.resultObject))
// 	    	$(".course_video_main_time").html(template('course_video_main_time',{items:data.resultObject}))

 	    	
		}
		
		
	
		
		
},false) 	


});




//搜索历史结果结束










