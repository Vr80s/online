var userLecturerId = getQueryString('userLecturerId');
var my_impression1="";
var my_impression2="";
var my_impression3="";

var gradeName = "";
var smallImgPath = "";
var description= "";

var is_watchState="";
/**
 * videoId : 视频播放id
 * multimediaType:媒体类型  视频 1 音频 2  
 */
function chZJ(videoId,multimediaType){
	var playerwidth = window.screen.width; //	屏幕分辨率的宽：window.screen.width 
	var playerheight = 8.95*21.8; //	屏幕分辨率的高：window.screen.height 
	console.log(playerwidth);
	var dataParams = {
		playerwidth:playerwidth,	
		playerheight:playerheight,
		videoId:videoId,
		multimedia_type:multimediaType
	}
	requestService("/bxg/ccvideo/commonCourseStatus", 
			dataParams, function(data) {
		if(data.success){
			var playCodeStr = data.resultObject;
			var playCodeObj = JSON.parse(playCodeStr);
			console.log(playCodeObj.video.playcode);
			$("#ccvideo").html(playCodeObj.video.playcode)
			
		}else{
		}
	},false);
}    


$(function(){

//	评论弹窗
	$(".wrap_input").on('click',function(){
		$(".bg_modal").show();
		$(".wrapAll_comment").show();
	})
	$(".bg_modal").on('click',function(){
		$(".bg_modal").hide();
		$(".wrapAll_comment").hide();
	})
    if($('#comment_detailed').val()==""){
        $(".report_btn").css("opacity","0.3");
    }else{
        $(".report_btn").css("opacity","1");
    }
    if($('#littlt_return').val()==""){
        $(".return_btn").css("opacity","0.3");
    }else{
        $(".return_btn").css("opacity","1");
    }
	//评论按钮颜色
    $('#comment_detailed').keyup(function(){
            if($('#comment_detailed').val()==""){
                $(".report_btn").css("opacity","0.3");
            }else{
                $(".report_btn").css("opacity","1");
            }
    })
    //控制回复按钮颜色
    $('#littlt_return').keyup(function(){
        if($('#littlt_return').val()==""){
            $(".return_btn").css("opacity","0.3");
        }else{
            $(".return_btn").css("opacity","1");
        }
    })
var userLecturerId = getQueryString('userLecturerId');
requestService("/xczh/host/hostPageInfo",{
	lecturerId : userLecturerId
},function(data) {
	/**
	 * 分享使用到的参数
	 */
	if(data.success && stringnull(data.resultObject.lecturerInfo)){
		var lecturerInfo = data.resultObject.lecturerInfo;
		gradeName = lecturerInfo.name;
		smallImgPath = lecturerInfo.small_head_photo;
		description= lecturerInfo.detail;
	}
	$(".all_returned_num p").html("评论"+data.resultObject.criticizeCount+"")
//	直播头像/主播名字
	$(".personal_bg").html(template('personal_header',data.resultObject));
//<!--主播名字/粉丝数量-->
	$("#wrap_wrapPersonal").html(template('data_number',data.resultObject));
// 打开页面判断是否已关注
    $(".add_follow").click(function(){
        //评论id
        lecturerId = $(this).attr("data-lecturerId");
        var p = $(".right_personal").find('span').html();

        var src = $(this).find('img').attr('src');
        if(src.indexOf("append1_icon")>-1){
            $(".add_follow").find('img').attr('src','../images/append2_icon.png');
            $(".add_follow").find('p').html("已关注");

            $(".right_personal").find('span').html(parseInt(p)+1);
            my_follow(lecturerId,1);
        }else{
            $(".add_follow").find('img').attr('src','../images/append1_icon.png');
            $(".add_follow").find('p').html("加关注");
            $(".right_personal").find('span').html(parseInt(p)-1);
            my_follow(lecturerId,2);
        }
    });		
//直播时间截取
//		data.resultObject.recentCourse.startTime= data.resultObject.recentCourse.startTime.substring(0,10); //截取日期
			$("#personal_status").html(template('data_status',data.resultObject.recentCourse));
	
	if(data.resultObject.recentCourse="" || data.resultObject.recentCourse== null){
			$("#personal_status").hide();
		}else{
			is_watchState=data.resultObject.recentCourse.watchState;

			
		}


//医师精彩致辞
	if(data.resultObject.lecturerInfo.video==''||data.resultObject.lecturerInfo.video==null){
		$("#wrap_vedio").hide()
	}else{
		//$("#wrap_vedio").html(template('data_vedio',data.resultObject.lecturerInfo));
		
		chZJ(data.resultObject.lecturerInfo.video,1);
	}

//介绍
		if(data.resultObject.lecturerInfo.detail==''||data.resultObject.lecturerInfo.detail==null){
			$("#jieshao").hide()			
		}else{
			$(".user_mywrite").html(data.resultObject.lecturerInfo.detail);
		}
//坐诊医馆及时间
		if(data.resultObject.hospital==null){
			data.resultObject.hospital={};
		}
		if(data.resultObject.hospital.name==null){
			data.resultObject.hospital.name="暂无";
		}
		if(data.resultObject.hospital.tel==null){
			data.resultObject.hospital.tel="暂无";
		}
		if(data.resultObject.hospital.detailedAddress==null){
			data.resultObject.hospital.detailedAddress="暂无";
		}
		if(data.resultObject.lecturerInfo==null){
			data.resultObject.lecturerInfo={};
		}
		if(data.resultObject.lecturerInfo.workTime==null){
			data.resultObject.lecturerInfo.workTime="暂无";
		}
		$("#sure_address").html(template('data_address',data.resultObject));
});

//主播课程
//var userLecturerId = getQueryString('userLecturerId');
requestService("/xczh/host/hostPageCourse",{
	lecturerId: userLecturerId,
	pageNumber:1,
	pageSize:6
},function(data){
	if(data.resultObject.records.length==0 ||data.resultObject.records == ""){
		$("#my_class").hide();
	}else{
		$("#wrap_vedio_btn").html(template('wrap_class',{items:data.resultObject.records}));		
	}

});
    refresh();
});
	
//	点击关注判断
	function my_follow(followed,type){
					requestService("/xczh/myinfo/updateFocus",{
						lecturerId : followed,
						type:type
					},function(data){
//                      alert(data.resultObject);
					})
			}
	function btn_class(){
	
	location.href="course_list.html?lecturerId="+userLecturerId;
}
    //刷新评论列表
    function refresh(){
        requestService("/xczh/criticize/getCriticizeList",{
            userId : userLecturerId,
            pageNumber:1,
            pageSize:6
        },function(data) {
        	//  	判断有无评论显示默认图片
		if(data.resultObject.items.length==0){
			$(".quie_pic").show()
		}else{
			$(".quie_pic").hide()

		}
            //	课程名称/等级/评论
            $(".wrap_all_returned").html(template('wrap_people_comment',{items:data.resultObject.items}));
            //	回复弹窗
            $(".wrap_returned_btn .btn_littleReturn").click(function(){
                //评论id
                criticize_id=this.id;
                btn_user_allComment();
            });
            $(".bg_userModal").click(function(){
                $(".bg_userModal").hide();
                $(".wrapLittle_comment").hide();
            });
            //点赞
            $(".btn_click_zan").click(function(){
                //评论id
                criticize_id=$(this).attr("data-id");
                btn_user_allComment();
            });

        });
    }

    //评论
    function reportComment() {
        //内容是否不为空
        var opacity = $(".report_btn").css("opacity");
        if(opacity!=1){
            return false;
        }
        var comment_detailed = $('#comment_detailed').val();

        requestService("/xczh/criticize/saveCriticize",{

            content:comment_detailed,
            userId : userLecturerId
        },function(data) {
            //	课程名称/等级/评论
            if(data.success==true){
                webToast("评论成功","middle",1500);
                //	直播时间/主播名字
                $(".wrapAll_comment").hide();
                $(".bg_modal").hide();
                document.getElementById("comment_detailed").value="";
                refresh();

            }else{
                webToast("评论失败","middle",1500);
            }
        });
    }

    //回复评论
    function replyComment() {
        var comment_detailed = $('#littlt_return').val();
        if(comment_detailed==""){
            //webToast("内容不能为空","middle",1500);
            return
        }
        requestService("/xczh/criticize/saveReply",{

            content:comment_detailed,
            criticizeId : criticize_id
        },function(data) {
            //	课程名称/等级/评论
            if(data.success==true){
                webToast("回复成功","middle",1500);
                //	直播时间/主播名字
                $(".bg_userModal").hide();
                $(".wrapLittle_comment").hide();
                document.getElementById("littlt_return").value="";
                refresh();
            }else {
                webToast("回复失败","middle",1500);
            }
        });
    }

    //点赞、取消点赞
    function updatePraise(id,isPraise) {
        requestService("/xczh/criticize/updatePraise", {
            praise: isPraise,
            criticizeId: id
        }, function (data) {
            //	课程名称/等级/评论
        });
    }

    function btn_user_allComment(){
        window.location.href="all_user_comment.html?userLecturerId="+userLecturerId+"";
    }
    


	//判断主播是否在开直播及最近一次直播
	var falg =authenticationCooKie();	
function go_play(t){
	var data_id=$(t).attr("data-play");
	console.log(data_id)
	if (falg==1002){
			location.href ="/xcview/html/enter.html";		
		}else if (falg==1005) {
			location.href ="/xcview/html/evpi.html";
		}else{
			if(is_watchState==2 || is_watchState==3){
			location.href ="details.html?courseId="+data_id;			
			}
			else{
				location.href ="school_play.html?course_id="+data_id;		
			}
		}		
	}