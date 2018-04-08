

/**
 *  1、主讲人介绍
 *  2、主播介绍
 */
var type = getQueryString('type');
var typeId = getQueryString('typeId');

requestService("/xczh/common/richTextDetails",{
	type:type,
	typeId:typeId
},function(data) {
	if(type ==1 ){//主讲人  --》 直播间主讲人
		if(data.resultObject.detail==''||data.resultObject.detail==null){
			$("#jieshao").hide()			
		}else{
			$(".user_mywrite").html(data.resultObject.lecturerInfo.detail);
		}
	}else if(type == 2){ //主播详情页面 --》 介绍 
		if(data.resultObject.detail==''||data.resultObject.detail==null){
			$("#jieshao").hide()			
		}else{
			$(".user_mywrite").html(data.resultObject.lecturerInfo.detail);
		}
	}else if(type == 3){ // 展示页面  课程详情
		if(data.resultObject.detail==''||data.resultObject.detail==null){
			$("#jieshao").hide()			
		}else{
			$(".user_mywrite").html(data.resultObject.lecturerInfo.detail);
		}
	}else if(type == 4){ //展示页面  主讲人
		if(data.resultObject.detail==''||data.resultObject.detail==null){
			$("#jieshao").hide()			
		}else{
			$(".user_mywrite").html(data.resultObject.lecturerInfo.detail);
		}
		
	}
})

