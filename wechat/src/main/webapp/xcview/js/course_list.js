$(function(){

	var lecturerId = getQueryString('lecturerId');
		requestService("/xczh/host/hostPageCourse",{
			lecturerId: lecturerId,
			pageNumber:1,
			pageSize:1000
		},function(data){
		$("#course_list_main").html(template('data_course_list',{items:data.resultObject.records}))
});

})
