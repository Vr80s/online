$(function(){


//搜索历史开始
var courseId = getQueryString('courseId');
requestService("/xczh/course/details",{
	courseId:courseId
},
    function(data) {
		
		if(data.success==true){
		
//课程大纲
		$(".outline_list_ul").html(data.resultObject.courseOutline);
	
//			var index_my=$(".outline_list_ul p").index()+1;
//			if(0<index_my<=9){
//				var number_s='0'+index_my;
//				$(".outline_list_ul p").prepend('<span>'+number_s+'&nbsp'+'</span>')		
//			}else{
//				var number_m=index_my;
//				$(".outline_list_ul p").prepend('<span>'+number_m+'&nbsp'+'</span>')		
//			}
	



//		标题		
		$(".outline_main").html(template('outline_main',data.resultObject)) 	    	
  	
		}
		
},false) 



//搜索历史结束








})

