var my_impression1="";
var my_impression2="";
var my_impression3="";
var course_id ="";
var criticize_id = "";
var LecturerId="";
var commentCode ="";
$(function(){
function stripHTML(str){
	var reTag = /<(?:.|\s)*?>/g;
	return str.replace(reTag,"");
}
//选集弹窗

 	 $(".select_icon").click(function(){
  		$(".bg_modal02").show();
		$(".wrap_select_list").show();
 	 });
  	$(".bg_modal02").on('click',function(){
		$(".bg_modal02").hide();
		$(".wrap_select_list").hide();
	})
	$(".list_box span").on('click',function(){
		$(".bg_modal02").hide();
		$(".wrap_select_list").hide();
	})

        	//获取课程ID跳转相应页面页面
	//引入comment.j后调用方法获取ID，course_id为html里的a链接后面的ID
	var courseId = getQueryString('course_id');
    course_id = courseId;
//	获取默认第一个视频ID
	var directId = getQueryString('directId');
	//传ID courseId为接口的课程ID
	requestService("/xczh/course/details",{
		courseId : courseId	
	},function(data) {
		//分享的信息展示
		gradeName = data.resultObject.gradeName;
		smallImgPath = data.resultObject.smallImgPath;
		if(data.resultObject.description==null || data.resultObject.description==''){
			
		}else{
			description = data.resultObject.description.stripHTML();
		}
		//详情页的banner
		var school_img = document.createElement("img");
		school_img.src = data.resultObject.smallImgPath;
		$(".play_video").append(school_img)

		//获取讲师id
        LecturerId=data.resultObject.userLecturerId;
		
		//	CC视频ID
//	    var	videoId = data.resultObject.directId;
	    var	type = data.resultObject.type;
		
		//初始化视频资源
		chZJ(directId,type);
		
	//	课程名称/等级/评论
		$("#speak_people").html(template('data_people',data.resultObject));
		$(".all_returned_num p").html("评论"+data.resultObject.criticizeCount+"")
	//	直播时间/主播名字
		$("#wrap_playTime").html(template('data_name',data.resultObject));

		
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
	});
    //传ID courseId为接口的课程ID，评论列表
    refresh();
	
	requestService("/xczh/course/getCoursesByCollectionId",{collectionId:823},function(data) {
	if(data.success==true){
    	$(".all_list_ul").html(template('all_list_ul',{items:data.resultObject}))
    //		点击选集获取视频ID		
	$(".all_list_ul li").click(function(){
		var directId=$(this).attr("data-myvideo");
		var courseId=$(this).attr("data-courseId");
		//初始化视频资源
//		chZJ(directId,1);
		window.location="/xcview/html/live_album.html?course_id="+courseId+"&directId="+directId;
	})
	}
})
	
	
	
	
//	请求视频代码
	function chZJ(videoId,multimediaType){
	/**
	 * 请求代码啦
	 */
	var playerwidth = window.screen.width; //	屏幕分辨率的宽：window.screen.width 
	var playerheight = 8.95*21.8; //	屏幕分辨率的高：window.screen.height 
	console.log(playerwidth);
	var dataParams = {
		playerwidth:playerwidth,	
		playerheight:playerheight,
		videoId:videoId,
		multimedia_type:multimediaType
//		multimedia_type:1
	}
	requestService("/bxg/ccvideo/commonCourseStatus", 
			dataParams, function(data) {
		if(data.success){
			var playCodeStr = data.resultObject;
			var playCodeObj = JSON.parse(playCodeStr);
			console.log(playCodeObj.video.playcode);
//			$("#video_v").html(playCodeObj.video.playcode)
			$("#video_box").html(playCodeObj.video.playcode)
			//"<script src=\"http://p.bokecc.com/player?vid=C728945447E95B7F9C33DC5901307461&siteid=B5E673E55C702C42&autoStart=true&width=360&height=195&playerid=E92940E0788E2DAE&playertype=1\" type=\"text/javascript\"><\/script>"
			/**
	    	 * 初始化评论区
	    	 */
	    	//getVideoCriticize(1,vid);
		}else{
    		$(".video_prompt").show();
		}
	},false);
}
	
})






//刷新评论列表
function refresh(){
    requestService("/xczh/criticize/getCriticizeList",{
        courseId : course_id,
        pageNumber:1,
        pageSize:6
    },function(data) {
    	//  	判断有无评论显示默认图片
		if(data.resultObject.items.length==0){
			$(".quie_pic").show()
		}else{
			$(".quie_pic").hide()
//			$(".wrap_all_returned").css({"margin-bottom":"1.2rem"})
		}
        //	课程名称/等级/评论
        $(".wrap_all_returned").html(template('wrap_people_comment',{items:data.resultObject.items}));
        //判断是否是第一次评论
        $(".wrapAll_comment").html(template('id_show_xingxing',{items:data.resultObject.commentCode}));
        commentCode = data.resultObject.commentCode;
        //	回复弹窗
        $(".wrap_returned_btn .btn_littleReturn").click(function(){
            //评论id
            criticize_id=this.id;
            //跳转到评论列表页
            btn_allComment();
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

        $(".select_lable li").click(function(){
            $(this).toggleClass("active_color");
        });
        //星星五星好评
        $('.my_impression1 img').each(function(index){
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
        });
        //主播演绎好评
        $('.my_impression2 img').each(function(index){
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
        });
        //节目内容好评
        $('.my_impression3 img').each(function(index){
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
        });
        //点赞
        $(".btn_click_zan").click(function(){
            //评论id
            criticize_id=$(this).attr("data-id");
            //跳转到评论列表页
            btn_allComment();
        });
        //判断浮层是否已选
        if(commentCode==1){
            var list=document.getElementsByClassName("active_color");
            if(my_impression1==""||my_impression2==""||my_impression3==""||list.length<=0){
                $(".report_btn").css("opacity","0.3");
            }else{
                $(".report_btn").css("opacity","1");
            }
        }
        $('.my_impression1').click(function(){
            var list=document.getElementsByClassName("active_color");
            if(my_impression1==""||my_impression2==""||my_impression3==""||list.length<=0){
                $(".report_btn").css("opacity","0.3");
            }else{
                $(".report_btn").css("opacity","1");
            }
        })
        $('.my_impression2').click(function(){
            var list=document.getElementsByClassName("active_color");
            if(my_impression1==""||my_impression2==""||my_impression3==""||list.length<=0){
                $(".report_btn").css("opacity","0.3");
            }else{
                $(".report_btn").css("opacity","1");
            }
        })
        $('.my_impression3').click(function(){
            var list=document.getElementsByClassName("active_color");
            if(my_impression1==""||my_impression2==""||my_impression3==""||list.length<=0){
                $(".report_btn").css("opacity","0.3");
            }else{
                $(".report_btn").css("opacity","1");
            }
        })
        $('.select_lable').click(function(){
            var list=document.getElementsByClassName("active_color");
            if(my_impression1==""||my_impression2==""||my_impression3==""||list.length<=0){
                $(".report_btn").css("opacity","0.3");
            }else{
                $(".report_btn").css("opacity","1");
            }
        })

    });
}
//评论
function reportComment() {
    //判断浮层是否已选
    if(commentCode==1){
        var opacity = $(".report_btn").css("opacity");
        if(opacity!=1){
            return false;
        }
    }
    var arr=new Array();

    var list=document.getElementsByClassName("active_color");
    for (var i = 0; i < list.length; i++) {
        arr.push(list[i].value);
    }
    var str=arr.join(",");

    var comment_detailed = $('#comment_detailed').val();
    if(comment_detailed==""){
        webToast("请输入评论内容","middle",3000);
        return
    }
    var overallLevel=0;
    if(my_impression1!=""){
        overallLevel = parseInt(my_impression1)+1
    }
    var deductiveLevel=0;
    if(my_impression2!=""){
        deductiveLevel = parseInt(my_impression2)+1
    }
    var contentLevel=0;
    if(my_impression3!=""){
        contentLevel = parseInt(my_impression3)+1
    }
    requestService("/xczh/criticize/saveCriticize",{
        overallLevel:overallLevel,
        deductiveLevel:deductiveLevel,
        contentLevel:contentLevel,
        criticizeLable:str,
        content:comment_detailed,
        courseId : course_id,
        userId:LecturerId
    },function(data) {
        //	课程名称/等级/评论
        if(data.success==true){
            webToast("评论成功","middle",3000);
            //	直播时间/主播名字
            //$("#wrap_playTime").html(template('data_name',data.resultObject));
            $(".wrapAll_comment").hide();
            $(".bg_modal").hide();
            document.getElementById("comment_detailed").value="";
            del();
            refresh(1,10);
        }else{
            webToast("评论失败","middle",3000);
        }
    });
}

//回复评论
function replyComment() {
    var comment_detailed = $('#littlt_return').val();
    if(comment_detailed==""){
        webToast("内容不能为空","middle",3000);
        return
    }
    requestService("/xczh/criticize/saveReply",{

        content:comment_detailed,
        criticizeId : criticize_id
    },function(data) {
        //	课程名称/等级/评论
        if(data.success==true){
            webToast("回复成功","middle",3000);
            //	直播时间/主播名字
            $(".bg_userModal").hide();
            $(".wrapLittle_comment").hide();
            document.getElementById("littlt_return").value="";
            del();
            refresh(1,10);
        }else {
            webToast("回复失败","middle",3000);
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
//点击所有评论跳转
function btn_allComment(){
    window.location.href="all_comment.html?courseId="+course_id+"&LecturerId="+LecturerId+"";
}


var courseId = getQueryString('course_id');
//点击购买后的接口
function btn_buy(){
    requestService("/xczh/order/save",{
        courseId:courseId,
        orderFrom:2
    },function(data){

        window.location.href="purchase.html?courseId="+data.resultObject.orderId+"";
    });


}
//点击免费报名后
function btn_mianfei(){


    //	评论弹窗
//  $(".wrap_input").on('click',function(){
//      del();
//      $(".bg_modal").show();
//      $(".wrapAll_comment").show();
//  })
//  $(".bg_modal").on('click',function(){
//      $(".bg_modal").hide();
//      $(".wrapAll_comment").hide();
//  })
    window.location.href="live_class.html?my_study="+course_id+"";
}

//删除评论状态
function del(){
    //星星
    var star='../images/xing1.png';
    $('.my_impression1 img').attr('src',star);
    //主播演绎
    var star1='../images/face0.png';
    $('.my_impression2 img').attr('src',star1);
    //节目内容
    var star2='../images/face0.png';
    $('.my_impression3 img').attr('src',star2);
    //很赞
    $(".select_lable li").removeClass("active_color");
    my_impression1="";
    my_impression2="";
    my_impression3=""

}