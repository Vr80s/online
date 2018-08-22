var my_impression1="";
var my_impression2="";
var my_impression3="";
var course_id ="";
var criticize_id = "";
var LecturerId="";
function stripHTML(str){
			var reTag = /<(?:.|\s)*?>/g;
			return str.replace(reTag,"");
		}
$(function(){

	//获取课程ID跳转相应页面页面
	//引入comment.j后调用方法获取ID，course_id为html里的a链接后面的ID
	var courseId = getQueryString('course_id');
    course_id = courseId;
	//传ID courseId为接口的课程ID
	requestService("/xczh/course/details",{
		courseId : courseId	
	},function(data) {
		
	//	简介/内容
		if(data.resultObject.description == null || data.resultObject.description == ''){
			$(".no_data").show();
			$(".btn").hide()
			$(".zhezhao").hide()
		}else{
			$(".wrap p").html(data.resultObject.description)
		}
	//	主讲人
		if(data.resultObject.lecturerDescription == null || data.resultObject.lecturerDescription == ''){
			$(".no_data1").show();
			$(".btn1").hide();
			$(".zhezhao1").hide();
		}else{

			$(".wrap1 p").html(data.resultObject.lecturerDescription)
		}
		//判断简介的字长度
		var h=$(".wrap1").height();
		if(h>200){
			$(".zhezhao1").hide()
			$(".btn1").show()
			$(".line_xian").hide()			
//			$(".wrap1").css({"height":"2rem","overflow":"hidden"})
		}else{
			$(".zhezhao1").hide()
			$(".btn1").hide()

			
		}
		
		var h2=$(".wrap").height();
		if(h2>200){
			$(".zhezhao").hide()
			$(".btn").show()			
//			$(".wrap").css({"height":"2rem","overflow":"hidden"})
		}else{
			$(".zhezhao").hide()
			$(".btn").hide()
		}
	});

    //传ID courseId为接口的课程ID，评论列表
    refresh();

});



