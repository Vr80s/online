
/**
 * 
 *  获取用户中心列表页
 *     获取视频数、礼物数、是否关注
 */
var lecturerId = getQueryString("lecturerId");

var isFours = "";

//lecturerId = "341d9c5b313a40a0a8084e1564a129b1";
/*
 * 我应该怎样判断这个用户是状态呢？从后台查一下吗？会不会很慢啊？
 */

//是否是讲师：0,用户，1既是用户也是讲师  is_lecturer  /bxg/common/judgeUserIsTeacher
requestService("/bxg/common/judgeUserIsTeacher", {
	userId:lecturerId}, function(data) {
	
		userIndexStatisticsInfo();
		if(!data.success){//普通用户
		//隐藏tab页
		$(".personage_bto_ul").hide();
		
		//隐藏正在直播
		$(".personage_top_cen_img").hide();
		
		//隐藏 视频数、礼物数
		$(".personage_pay").hide(); 
		
	}else{
		//userIndexStatisticsInfo();
	}
},false)
/**
 * 用户主页 -- 主播课程列表
 */
var pageNumber = 1;
var pageSize = 6;

function userIndexCourseList(type,falg){
	if(falg){
		pageNumber = pageNumber+1;
	}

	requestService("/bxg/common/userHomePageCourseList", {
		lecturerId:lecturerId,
		pageNumber:pageNumber,
		pageSize:pageSize,
		type:type
	}, function(data) {
		if (data.success) {
			var objList = data.resultObject;
			if(!falg){
				$("#personage_bto_cen1").html("");
			}
			if(objList.length<6){
				$(".more_btm").hide();
			}else{
				$(".more_btm").show();
			}
			var html ="";
			if (objList.length>0) {
				for (var int = 0; int < objList.length; int++) {
					var obj = objList[int];
					var watchStr ="";
					if(obj.watchState == 0){  // watchState ： 0 免费   1 收费  2 密码 
						watchStr ="免费";
					}else if(obj.watchState == 1){
						watchStr ="￥"+obj.currentPrice;
					}else if(obj.watchState == 2){
						watchStr ="加密";
					}
					html +="<div class='public1_list'>"+
						"<div class='public1_list_img' onclick='userIndex("+JSON.stringify(obj)+")'>"+
							"<img src='../images/line_03.jpg' alt=''>"+
						"</div>"+
						"<div class='public1_title'>"+obj.gradeName+"</div>"+
						"<div class='public1_cen'>"+
							"<div class='public1_cen_left'>主讲:"+obj.name+"&nbsp;"+obj.courseLength+"小时</div>"+
							"<div class='public1_cen_right'>"+watchStr+"</div>"+
						"</div>"+
					   "</div>";
				}
			    $("#personage_bto_cen1").append(html);
			}else{
				//暂无数据
			}
		}else{
		}
	})
}
/**
 * 点击的时候需要判断是直播呢，还是直播预告呢，还是视频的呢
 * @param obj
 */
function userIndex(obj){
	
	//live_status  直播状态 1.直播中，2预告，3直播结束
	if(obj.type == 1){  //liveDetails bunchDetails
		if(obj.lineState == 2){ 
			location.href = "/xcviews/html/foreshow.html?course_id=" + obj.courseId;
		}else{
			location.href = "/bxg/xcpage/courseDetails?courseId="+obj.courseId;
		}
		return;		
	}else{
		location.href = "/xcviews/html/particulars.html?courseId=" + obj.courseId;
		return;
	}
}


/**
 * 用户主页 -- 主播统计信息
 */
function userIndexStatisticsInfo(){
	requestService("/bxg/common/userHomePage", {
		lecturerId:lecturerId
	}, function(data) {
		if (data.success) {
			
		 var bigObj = data.resultObject;	
		 
		/*讲师基本信息
		 *  sql.append(" select id,name,room_number as roomNumber,sex,province_name as provinceName,");
			sql.append(" small_head_photo as smallHeadPhoto,city_name as cityName,individuality_signature as info ");
			sql.append(" from oe_user where id = ?  ");
		 */
		 var lecturerInfo = bigObj.lecturerInfo;
		 $("#teacherHeadImg").attr("src",lecturerInfo.smallHeadPhoto);	
		 $("#teacherName").text(lecturerInfo.name);
		 
		 if(stringnull(lecturerInfo.provinceName) || stringnull(lecturerInfo.cityName)){
			 $("#index_address").val(lecturerInfo.provinceName+"  "+lecturerInfo.cityName);
		 }
		 
		 if(lecturerInfo.sex == 1){
			 $("#index_sex").val("男");
		 }else if(lecturerInfo.sex == 0){
			 $("#index_sex").val("女");
		 }
		 
		 if(stringnull(lecturerInfo.info) || stringnull(lecturerInfo.info)){
			 $("#index_individualitySignature").val(lecturerInfo.info);
		 }
		 
		 /*
		  * 是否有证咋直播的课程
		  */
		 var mapLiveState = bigObj.mapLiveState;  //id  status
		 if(stringnull(mapLiveState.status) && mapLiveState.status == 1){
			 $(".personage_top_cen_in").attr("id",mapLiveState.id);
		 }else{
			 $(".personage_top_cen_img").hide(); 
		 }
		 /**
		  * 是否关注
		  */
		 if(bigObj.isFours == 0){
			 $("#is_fours").text("关注");
		 }else if(bigObj.isFours == 1){
			 $("#is_fours").text("已关注");
		 }
		 isFours = bigObj.isFours;
		 /**
		  * 目前是课程数
		  */
		 $("#courseAll").text(bigObj.courseAll);
		 /**
		  * 礼物数
		  */
		 $("#giftAll").text(bigObj.giftAll);
		 /**
		  * 总共的粉丝数
		  */
		 $("#fansCount").text(bigObj.fansCount);
		 /*
		  * 是否正在直播
		  */
		 $("#teacherHeadImg").attr("src",lecturerInfo.smallHeadPhoto);
		 
		 var listFans = bigObj.listFans;
		 var fansHtml ="";
		 for (var int = 0; int < listFans.length; int++) {
			var fans = listFans[int];
			fansHtml+="<div class='personage_image_right_img' >" +
					"<img  onclick='jumpSelf(this)' title="+fans.lecturerId+" src="+fans.lecturerHeadImg+" alt=''></div>";
		 }
		 
		 
		 $("#listFans").append(fansHtml);
		}else{
			alert("请求失败");
		}
	})
}

/**
 * 自己跳转自己
 */
function jumpSelf(obj){
	//alert(lecturerId);
     var lecturerId =  $(obj).attr("title");
	window.location.href ="/xcviews/html/personage.html?lecturerId="+lecturerId;
}

/**
 * 点击用户页面跳转
 */
$(".personage_image_right_img").click(function(){
	var lecturerId = $(this).attr("title");
	window.location.href = getServerHost()+"/xcviews/html/personage.html?lecturerId="+lecturerId;
})

/**
 * 点击关注或者取消关注
 */
$("#is_fours").click(function(){
	 /**
	  * 是否关注
	  */
	if(isFours == 0){
		requestService("/bxg/focus/add", {
			lecturerId : lecturerId
		}, function(data) {
			if(data.success){
				$("#is_fours").text("已关注");
				isFours =1;
			};
		},false)
	}else if(isFours == 1){
		requestService("/bxg/focus/cancel", {
			lecturerId : lecturerId
		}, function(data) {
			if(data.success){
				$("#is_fours").text("关注");
				isFours =0;
			};
		},false)
	} 
})
/**
 *  点击正在直播按钮 直接去详情页面
 */
$(".personage_top_cen_in").click(function(){
	var courseid = $(".personage_top_cen_in").attr("id");
	/**
	 * zhi
	 */
	//http://localhost:10089/bxg/xcpage/courseDetails?courseId=450
	location.href = "/bxg/xcpage/courseDetails?courseId="+courseid;
	return;
	//alert("去正在直播的课程页面");
})




/**
 *  点击更多，加载直播视频获取音频
 */
$(".more_btm").click(function(){
	//alert("去正在直播的课程页面");
	var aBtn=$('.personage_bto_ul ul li');
    for(i=0;i<aBtn.length;i++){
    	var className = $(aBtn[i]).attr("class");
    	if(className.indexOf("personage_bto_li1")!=-1){
    		var type = $(aBtn[i]).attr("title");
            if(type!=0){
            	userIndexCourseList(type,true);
            	break;
            }
    	}
    }
})









 