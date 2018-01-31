$(function(){
	//获取课程ID跳转相应页面页面
	var courseId = getQueryString('courseId');
	//传ID courseId为接口的课程ID
	requestService("/xczh/order/getByOrderId",{
		orderId : courseId	
	},function(data) {
	$("#purchase_details").html(template('data_details',data.resultObject[0].allCourse));
		
		
	})
})
