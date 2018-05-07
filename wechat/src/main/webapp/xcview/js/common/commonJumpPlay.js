

/**
 * 直播、视频、音频、专辑跳转
 * @param id
 * @returns
 */
function common_jump_all(courseId){
   requestService("/xczh/course/userCurrentCourseStatus?courseId="+courseId,null,function(data) {
	   
     var userPlay=data.resultObject;
     var falg =authenticationCooKie(); 
  	 
     var watchState = userPlay.watchState;
     var type = userPlay.type;
     var collection = userPlay.collection;
     var lineState = userPlay.lineState;
     
     
     if(watchState==1 || watchState==2){
		if(type == 1 || type == 2){
			//增加学习记录
			requestService("/xczh/history/add",{courseId:courseId, recordType:1},function(data) {
				 console.log("增加学习记录");
			}) 
			if(collection==1){
				location.href = "/xcview/html/live_select_album.html?course_id="+courseId;
			}else{
				location.href = "/xcview/html/live_audio.html?my_study="+courseId;
			}
		}else if(type == 3){	
			
			common_jump_play(courseId,watchState,lineState);
		}else if(type == 4){
			location.href = "/xcview/html/school_class.html?course_id="+courseId;
		}
	 }else {
		if(type == 1 || type == 2){
			location.href = "/xcview/html/school_audio.html?course_id="+courseId;
		}else if(type == 3){	
			common_jump_play(courseId,watchState,lineState);
		}else if(type == 4){
			location.href = "/xcview/html/school_class.html?course_id="+courseId;
		}
	 }
})
}
/**
 * 公众的根据直播状态判断跳转
 * @param id
 * @returns
 */
function common_jump_play(id,watchState,lineState){
      //var userPlay=data.resultObject;
          var falg =authenticationCooKie(); 
//付费的     
          if(watchState==0){
             location.href="/xcview/html/school_play.html?course_id="+id+"&type="+1
          }
//免费的    直播跳转      
          else if(watchState==1 && lineState==1){  //直播中
             if (falg==1002){
             	location.href ="/xcview/html/cn_login.html";      
             }else if (falg==1005) {
                location.href ="/xcview/html/evpi.html";
             }else{
 	            requestService("/xczh/history/add",{courseId:id,recordType:2},function(data) {
 	            	   	console.log("增加观看记录");
 	            }) 
 	            requestService("/xczh/history/add",{courseId:id,recordType:1},function(data) {
 	            	   	console.log("增加学习记录");
 	            }) 
 	            location.href="/xcview/html/details.html?courseId="+id
             }
             
          }else if(watchState==1 && lineState!=1){ //先去直播详情页  ---》在去直播间
             if (falg==1002){
                   location.href ="/xcview/html/cn_login.html";      
                }else if (falg==1005) {
                   location.href ="/xcview/html/evpi.html";
                }else{
             	   requestService("/xczh/history/add",{courseId:id,recordType:1},function(data) {
 	            	   	console.log("增加学习记录");
 	               }) 
             	   location.href="/xcview/html/live_play.html?my_study="+id;	
                }
          }
//购买后的直播和即将直播跳直播间
          else if(watchState==2 && lineState==1){
         	 //增加观看记录就行
             requestService("/xczh/history/add",
                {courseId:id,recordType:1}
                ,function(data) {
                }) 
             location.href="/xcview/html/details.html?courseId="+id      
             
          }else if(watchState==2 && lineState!=1){
         	location.href="/xcview/html/school_play.html?course_id="+id 
          }
    	  
}

/**
 * 视频/音频/专辑  --》 跳转
 * @param watchState
 * @param type
 * @param collection
 * @param courseId
 * @returns
 */
function common_jump_school(watchState,type,collection,courseId){
	if(watchState==1){
		if(type == 1 || type == 2){
			//增加学习记录
			requestService("/xczh/history/add",{courseId:courseId, recordType:1},function(data) {
				 console.log("增加学习记录");
			}) 
			if(collection==1){
				location.href = "/xcview/html/live_select_album.html?course_id="+courseId;
			}else{
				location.href = "/xcview/html/live_audio.html?my_study="+courseId;
			}
		}else if(type == 4){
			location.href = "/xcview/html/school_class.html?course_id="+courseId;
		}
	}else{
		if(type == 1 || type == 2){
			location.href = "/xcview/html/school_audio.html?course_id="+courseId+"&type="+2;
		}else if(type == 4){
			location.href = "/xcview/html/school_class.html?course_id="+courseId+"&type="+3;
		}
	}
}





