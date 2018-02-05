$(function(){

	var userLecturerId = getQueryString('userLecturerId');
		requestService("/xczh/host/hostPageCourse",{
			lecturerId: '23908ae85dad4541ba7ecf53fc52aab2',
			pageNumber:1,
			pageSize:8
		},function(data){
		$("#course_list_main").html(template('data_course_list',{items:data.resultObject.records}))
});
  
  
 
  
  
  
  
  
  
})
