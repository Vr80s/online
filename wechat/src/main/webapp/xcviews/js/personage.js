
/**
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
		if(!data.success){//普通用户
			//personage_bto_ul
			//隐藏tab页     
			$(".personage_bto_ul").hide();
			$(".personage_bto").css("margin-top","0");
			//隐藏熊猫币贡献榜
			$(".contribution").hide();
			//隐藏灰色进度条
			$(".grey_scroll").hide();
			//隐藏正在直播
			$(".personage_top_cen_img").hide();
			//隐藏 视频数、礼物数
			$(".personage_pay").hide(); 
		}else{
			viewContributionList();
		}
},false)

userIndexStatisticsInfo();
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
			if(objList == null || objList.length == 0){
				$(".null15").show();
			}else{
				$(".null15").hide();
			}
			
			var html ="";
			if (objList.length>0) {
				for (var int = 0; int < objList.length; int++) {
					var obj = objList[int];
					var watchStr ="";
					var lineState = obj.lineState;
					if(obj.watchState == 0){  // watchState ： 0 免费   1 收费  2 密码 
						watchStr ="免费";
					}else if(obj.watchState == 1){
						watchStr ="￥"+obj.currentPrice;
					}else if(obj.watchState == 2){
						watchStr ="加密";
					}
					
					var courseTime = "";
					if(type ==1){
						courseTime = obj.courseTimeConver;
					}
					
					html +="<div class='public1_list'>"+
						"<div class='public1_list_bg'>"+
						"</div>"+
						"<div class='public1_list_img' onclick='userIndex("+JSON.stringify(obj)+")'>"+
							"<img src='"+obj.smallImgPath+"' alt=''>"+
						"</div>"+
						"<div class='public1_title'>"+obj.gradeName+"</div>"+
						"<div class='public1_cen'>"+
							/*"<div class='public1_cen_left'>主讲:"+obj.name+"&nbsp;"+obj.courseLength+"小时</div>"+*/
							"<div class='public1_cen_right'>"+watchStr+"</div>"+
						"</div>"+
						"<div class='public1_list_bottom'>"+  //这是观看人数
							"<img src='../images/yjing.png' alt=''>"+obj.learndCount+
						"</div>"+								//这是播放时间
						"<div class='public1_list_bottom0'>"+courseTime+
						"</div>";
						if(type==1){//直播
							
							var liveTypeOrState  = ""; 
							var liveTypeImg = "";
							// lineState：    1 直播中，2 预告， 3 回放	
							if(lineState==1){
                				liveTypeOrState ="直播中";
                				liveTypeImg ="/xcviews/images/zhibo001.png";
                			}else if(lineState==2){
                				liveTypeOrState ="预告";
                				liveTypeImg ="/xcviews/images/yugao001.png"	
                			}else{
                				liveTypeOrState ="回放";
                				liveTypeImg ="/xcviews/images/huifang001.png"
                			}
							html += "<div class='public1_list_bottom01'>"+   //这是头部播放类型
							"<div class='play_types'><img src='"+liveTypeImg+"' /></div>"+
							"<div class='play_types_size'>"+liveTypeOrState+"</div>"+
							"</div>";
						}	
						html +="</div>";
				}
			    $("#personage_bto_cen1").append(html);
			    
			    if(type!=1){//视频
			    	$(".public1_list_bg").css("background","url(../images/tv.png) no-repeat");
			    	$(".public1_list_bg").css("background-size","100% 100%");
			    	$(".public1_list_bg").css("height","1.775rem");
			    	$(".public1_list_bg").css("bottom","1.58rem");
			    }
			    
			}else{
				//暂无数据
			}
		}else{
		}
	})
}
/**
 * 进页面的时候就需要请求这个数据啦
 */
window.onload=function(){
	var aBtn=$('.personage_bto_ul ul li');
    for(i=0;i<aBtn.length;i++){
      $(aBtn[i]).click(function(){
        for(i=0;i<aBtn.length;i++){
          $(aBtn[i]).removeClass('personage_bto_li1');
          $(aBtn[i]).addClass('personage_bto_li');
        }
        $(this).removeClass();
        $(this).addClass('personage_bto_li1');
        var type = $(this).attr("title");
        //点击请求列表	
        if(type!=0){
          pageNumber = 1;
      	  userIndexCourseList(type,false);
        }else{
        	$(".null15").hide();
        }
      })
    }

    $(".personage_bto_li01").click(function() {
        $(".personage_bto_cen").show();
  	  $(".personage_bto_cen1").hide();
    });
    $(".personage_bto_li02").click(function() {
  	    $(".personage_bto_cen").hide()
      	$(".personage_bto_cen1").show();
    });
    $(".personage_bto_li03").click(function() {
  	  $(".personage_bto_cen").hide()
  	  $(".personage_bto_cen1").show();
    });
    $(".personage_bto_li04").click(function() {
  	  $(".personage_bto_cen").hide()
  	  $(".personage_bto_cen1").show();
    });

	  $(aBtn[0]).click();

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
		  * 是否有正在直播的课程
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
			 $("#is_fours").css("background","url(/xcviews/images/attention.png) no-repeat");
			 $("#is_fours").css("background-size","100% 100%");
		 }else if(bigObj.isFours == 1){
			 $("#is_fours").css("background","url(/xcviews/images/attention_bg.png) no-repeat");
			 $("#is_fours").css("background-size","100% 100%");
		 }
		 isFours = bigObj.isFours;
		/* *//**
		  * 目前是课程数
		  */
		 $("#courseAll").val(bigObj.courseAll);
		 /**
		  * 礼物数
		  */
		 //$("#giftAll").text(bigObj.giftAll);
		 /**
		  * 总共的粉丝数
		  */
		 $("#fansCount").text(bigObj.fansCount);
		 /**
		  * 总共的关注数
		  */
		 $("#focusCount").text(bigObj.focusCount);
		 /*
		  * 是否正在直播
		  */
		 $("#teacherHeadImg").attr("src",lecturerInfo.smallHeadPhoto);
		 
		/* var listFans = bigObj.listFans;
		 var fansHtml ="";
		 for (var int = 0; int < listFans.length; int++) {
			var fans = listFans[int];
			fansHtml+="<div class='personage_image_right_img' >" +
					"<img  onclick='jumpSelf(this)' title="+fans.lecturerId+" src="+fans.lecturerHeadImg+" alt=''></div>";
		 }
		 $("#listFans").append(fansHtml);*/
		}else{
			alert("请求失败");
		}
	})
}


/**
 * 点击贡献榜进入到贡献榜页面啦
 */
$(".contribution").click(function(){
	var personageHistory = sessionStorage.personageHistory;
	if(stringnull(personageHistory)){
		personageHistory ++;
	}else{
		personageHistory = 1;
	}
	sessionStorage.setItem("personageHistory",personageHistory);
	location.href = "/xcviews/html/ranking_list.html?lecturerId="+lecturerId;
})
var current = location.href;
/**
 * 点击返回。如果超过两次返回的话那么就需要这样搞了默认返回首页
 */
$(".personage_return").click(function(){
	var personageHistory = sessionStorage.personageHistory;
	if(!stringnull(personageHistory) && personageHistory !=1){
		sessionStorage.removeItem("personageHistory");
		history.go(-1);
	}
	if(personageHistory ==1 ){
		sessionStorage.removeItem("personageHistory");
		history.go(-2);
	}
	if(stringnull(personageHistory) || personageHistory >5){
		sessionStorage.removeItem("personageHistory");
		location.href="/xcviews/html/my.html";//默认返回到首页吧
		//history.go(-1);

	}
	//history.go(-1);
})

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
				//$("#is_fours").text("已关注");
				isFours =1;
				$("#is_fours").css("background","url(/xcviews/images/attention_bg.png) no-repeat");
				$("#is_fours").css("background-size","100% 100%");
			
			};
		},false)
	}else if(isFours == 1){
		requestService("/bxg/focus/cancel", {
			lecturerId : lecturerId
		}, function(data) {
			if(data.success){
				//$("#is_fours").text("关注");
				isFours =0;
				 $("#is_fours").css("background","url(/xcviews/images/attention.png) no-repeat");
				 $("#is_fours").css("background-size","100% 100%");
			};
		},false)
	} 
})
/**
 *  点击正在直播按钮 直接去详情页面
 */
$(".personage_top_cen_img").click(function(){
	var courseid = $(".personage_top_cen_in").attr("id");
	if(stringnull(courseid)){
		location.href = "/bxg/xcpage/courseDetails?courseId="+courseid;
		return;
	}else{
		console.info("此课程有误");
		return;
	}	
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
/**
 * 显示贡献榜
 */
function viewContributionList(){
	requestService("/bxg/gift/userRankingList", {
		userId : lecturerId
	}, function(data) {
		if(data.success){

			var str = "";
			for (var int = 0; int < data.resultObject.length; int++) {
				var obj = data.resultObject[int];
				var topIcon = "";
				if(int == 0){
					topIcon +="/xcviews/images/Scholar.png";
				}else if(int == 1){
					topIcon +="/xcviews/images/smirnoff.png";
				}else if(int == 2){
					topIcon +="/xcviews/images/tertius.png";
				}else{
					break;
				}
				str +="<div class='contribution_right_one'>"+
				"<div class='contribution_img_one'><img src="+topIcon+" /></div>"+
				"<div class='contribution_img_two' ><img  src="+obj.smallHeadPhoto+" /></div>"+
				"</div>";
				if(int == 2){
					/*<div class="contribution_right_two"><img src="/xcviews/images/rightj.png" alt="" /></div>
					<div class="both"></div>*/
					str +="<div class='contribution_right_two'><img src='/xcviews/images/rightj.png' alt='' />" +
							"</div><div class='both'></div>";
				}
			}
			$(".contribution_right").html(str);
		};
	},false)
	
}





 