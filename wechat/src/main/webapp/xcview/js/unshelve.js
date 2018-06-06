

requestService("/xczh/course/unshelveCouserRecommen",null,function(data) {
	$("#course_list_main").html(template("data_course_list",{items: data.resultObject}));
})	



