$(function(){

	var lecturerId = getQueryString('lecturerId');
		requestService("/xczh/host/hostPageCourse",{
			lecturerId: lecturerId,
			pageNumber:1,
			pageSize:8
		},function(data){
		$("#course_list_main").html(template('data_course_list',{items:data.resultObject.records}))
});
  
  
 
  
  
  
  
  
  
})

//直播和即将直播跳转
function jump_play(id){
   requestService("/xczh/course/details?courseId="+id,null,function(data) {
      var userPlay=data.resultObject;
      var falg =authenticationCooKie();       	       
//付费的直播和即将直播未购买跳购买页    
         if(userPlay.watchState==0 && userPlay.lineState==1){
            location.href="school_play.html?course_id="+id 
         }else if(userPlay.watchState==0 && userPlay.lineState==4){
            location.href="school_play.html?course_id="+id          
         }
//免费的直播和即将直播跳直播间      
         else if(userPlay.watchState==1 && userPlay.lineState==1){
            if (falg==1002){
            location.href ="/xcview/html/cn_login.html";      
            }else if (falg==1005) {
               location.href ="/xcview/html/evpi.html";
            }else{
            requestService("/xczh/history/add",
               {courseId:id,recordType:2}
               ,function(data) {
      
               }) 
            location.href="details.html?courseId="+id
            }
         }else if(userPlay.watchState==1 && userPlay.lineState==4){
            if (falg==1002){
                  location.href ="/xcview/html/cn_login.html";      
               }else if (falg==1005) {
                  location.href ="/xcview/html/evpi.html";
               }else{
                  requestService("/xczh/history/add",
                     {courseId:id,recordType:2}
                     ,function(data) {
            
                     }) 
                  location.href="details.html?courseId="+id  
               }
         }
//购买后的直播和即将直播跳直播间
         else if(userPlay.watchState==2 && userPlay.lineState==1){
            requestService("/xczh/history/add",
               {courseId:id,recordType:2}
               ,function(data) {
      
               }) 
            location.href="details.html?courseId="+id           
         }else if(userPlay.watchState==2 && userPlay.lineState==4){
            requestService("/xczh/history/add",
               {courseId:id,recordType:2}
               ,function(data) {
      
               }) 
            location.href="details.html?courseId="+id  
           }
//主播本人自己的直播和即将直播跳直播间			
			else if(userPlay.watchState==3 && userPlay.lineState==1){
				requestService("/xczh/history/add",
					{courseId:id,recordType:2}
					,function(data) {
		
					})	
				location.href="details.html?courseId="+id				
			}else if(userPlay.watchState==3 && userPlay.lineState==4){
				requestService("/xczh/history/add",
					{courseId:id,recordType:2}
					,function(data) {
		
					})	
				location.href="details.html?courseId="+id				
			}
			else{
				location.href="school_play.html?course_id="+id				
			}

 }
 )}
