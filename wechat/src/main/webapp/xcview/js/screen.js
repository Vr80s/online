$(function(){
//引入comment.j后调用方法获取ID，course_id为html里的a链接后面的ID
var courseId = getQueryString('myselectBtn');
//传ID courseId为接口的课程ID
//课程分类
requestService("/xczh/recommend/queryAllCourse",{
	menuType : courseId	
},function(data) {
	
});

//课程类型
var courseId = getQueryString('classStyle');
requestService("/xczh/recommend/queryAllCourse",{
	courseType:courseId
},function(data) {
	
});




})
