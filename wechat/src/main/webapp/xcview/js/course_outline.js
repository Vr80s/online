$(function(){


//搜索历史开始
var courseId = getQueryString('courseId');
requestService("/xczh/course/details",{
	courseId:courseId
},
    function(data) {
		
		if(data.success==true){
		
//		标题
		$(".outline_main").html(template('outline_main',data.resultObject)) 	    	
			
//			大纲
		$(".outline_list_ul").html(template('outline_list_ul',data.resultObject)) 	    	
		}
		
},false) 



//搜索历史结束








})

