var userLecturerId = getQueryString('userLecturerId');
var my_impression1="";
var my_impression2="";
var my_impression3="";



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
//	回复弹窗
$(".wrap_returned_btn .btn_littleReturn").click(function(){
	$(".bg_userModal").show();
	$(".wrapLittle_comment").show();
	$("#littlt_return").focus()
});
$(".bg_userModal").click(function(){
	$(".bg_userModal").hide();
	$(".wrapLittle_comment").hide();
});
//	评论弹窗
	$(".wrap_input").on('click',function(){
		$(".bg_modal").show();
		$(".wrapAll_comment").show();
	})
	$(".bg_modal").on('click',function(){
		$(".bg_modal").hide();
		$(".wrapAll_comment").hide();
	})
//	标签选中变色
	
	 /*$(".select_lable li").click(function(){
  	 $(this).toggleClass("active_color"); 
  });*/
    //星星五星好评
    /*$('.my_impression1 img').each(function(index){
        var star='../images/xing1.png';    //普通灰色星星图片的存储路径  
        var starRed='../images/xing.png';     //红色星星图片存储路径  
        var prompt=['1分','2分','3分','4分','5分'];   //评价提示语  
        this.id=index;      //遍历img元素，设置单独的id  
        $(this).on("mouseover click",function(){    //设置鼠标滑动和点击都会触发事件  
            $('.my_impression1 img').attr('src',star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色  
            $(this).attr('src',starRed);        //设置鼠标当前所在图片为打星颜色图  
            $(this).prevAll().attr('src',starRed);  //设置鼠标当前的前面星星图片为打星颜色图  
            $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值

            my_impression1=this.id;
        });
    });*/
//主播演绎好评
   /* $('.my_impression2 img').each(function(index){
        var star='../images/face0.png';    //普通灰色星星图片的存储路径
        var starRed='../images/face1.png';     //红色星星图片存储路径
        var prompt=['一般','一般','好','好','很好'];   //评价提示语
        this.id=index;      //遍历img元素，设置单独的id
        $(this).on("mouseover click",function(){    //设置鼠标滑动和点击都会触发事件
            $('.my_impression2 img').attr('src',star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色
            $(this).attr('src',starRed);        //设置鼠标当前所在图片为打星颜色图
            $(this).prevAll().attr('src',starRed);  //设置鼠标当前的前面星星图片为打星颜色图
            $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值
            my_impression2=this.id;

        });
    });*/
//节目内容好评
    /*$('.my_impression3 img').each(function(index){
        var star='../images/face0.png';    //普通灰色星星图片的存储路径
        var starRed='../images/face1.png';     //红色星星图片存储路径
        var prompt=['一般','一般','好','好','很好'];   //评价提示语
        this.id=index;      //遍历img元素，设置单独的id
        $(this).on("mouseover click",function(){    //设置鼠标滑动和点击都会触发事件
            $('.my_impression3 img').attr('src',star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色
            $(this).attr('src',starRed);        //设置鼠标当前所在图片为打星颜色图
            $(this).prevAll().attr('src',starRed);  //设置鼠标当前的前面星星图片为打星颜色图
            $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值
            my_impression3=this.id;
        });
    });*/
    //获取ID跳转相应页面页面
//引入comment.j后调用方法获取ID，course_id为html里的a链接后面的ID
	var userLecturerId = getQueryString('userLecturerId');
//传ID courseId为接口ID
requestService("/xczh/host/hostPageInfo",{
	lecturerId : userLecturerId
},function(data) {
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
		data.resultObject.recentCourse.startTime= data.resultObject.recentCourse.startTime.substring(0,10); //截取日期
		$("#personal_status").html(template('data_status',data.resultObject.recentCourse));
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
		$("#sure_address").html(template('data_address',data.resultObject));

		
		
		
		

});
		
//主播课程
//var userLecturerId = getQueryString('userLecturerId');
requestService("/xczh/host/hostPageCourse",{
	lecturerId: userLecturerId,
	pageNumber:1,
	pageSize:6
},function(data){
	$("#wrap_vedio_btn").html(template('wrap_class',{items:data.resultObject.records}));
});

    refresh();

});
	
//	点击关注判断

	function my_follow(followed,type){
					requestService("/xczh/myinfo/updateFocus",{
						lecturerId : followed,
						type:type
					},function(data){
                        alert(data.resultObject);
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
            pageSize:3
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
                $(".bg_userModal").show();
                $(".wrapLittle_comment").show();
                $("#littlt_return").focus()
            });
            $(".bg_userModal").click(function(){
                $(".bg_userModal").hide();
                $(".wrapLittle_comment").hide();
            });
            //点赞
            $(".btn_click_zan").click(function(){
                //评论id
                criticize_id=$(this).attr("data-id");
                var p = $(this).find('span').html();

                var src = $(this).find('img').attr('src');
                if(src.indexOf("zan001")>-1){
                    $(this).find('img').attr('src','../images/zan01.png');

                    $(this).find('span').html(parseInt(p)-1);
                    updatePraise(criticize_id,false);
                }else{
                    $(this).find('img').attr('src','../images/zan001.png');
                    $(this).find('span').html(parseInt(p)+1);
                    updatePraise(criticize_id,true);
                }
            });

        });
    }

    //评论
    function reportComment() {


        var comment_detailed = $('#comment_detailed').val();

        requestService("/xczh/criticize/saveCriticize",{

            content:comment_detailed,
            userId : userLecturerId
        },function(data) {
            //	课程名称/等级/评论

            alert(data.resultObject);
            //	直播时间/主播名字
            //$("#wrap_playTime").html(template('data_name',data.resultObject));
            $(".wrapAll_comment").hide();
            $(".bg_modal").hide();
            requestService("/xczh/criticize/getCriticizeList",{
                userId : userLecturerId
            },function(data) {
                //	课程名称/等级/评论
                $(".wrap_all_returned").html(template('wrap_people_comment',{items:data.resultObject.items}));

            });
        });
    }

    //回复评论
    function replyComment() {
        var comment_detailed = $('#littlt_return').val();

        requestService("/xczh/criticize/saveReply",{

            content:comment_detailed,
            criticizeId : criticize_id
        },function(data) {
            //	课程名称/等级/评论

            alert(data.resultObject);
            //	直播时间/主播名字
            $(".bg_userModal").hide();
            $(".wrapLittle_comment").hide();
            document.getElementById("littlt_return").value="";
            refresh();
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