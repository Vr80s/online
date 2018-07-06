    
var doctorId = getQueryString("doctor");
var loginUserId="";
var loginUserName="";
var getPostsIdByComment="";
var postsCommentId="";
var postsCommentUserName="";
var doctorPostsType ="";
$(function(){
    loginUserId = localStorage.getItem("userId");
    loginUserName = localStorage.getItem("name");
    sowingMap();

});
//轮播图
function sowingMap() {
    requestGetService("/xczh/host/doctor/v2",{
        doctorId:doctorId
    },function(data) {
        if(data.success==true){
            var obj = data.resultObject;
            $(".top_details").html(template('top_details',{items:obj}));
            //关注
            $(".attention").click(function(){
                $(".message_face").show();
                var lecturerId = $(this).attr("data-userId");
                var src = $(this).find('img').attr('src');
                var p = $(".fans").find('#fansCount').html();
                if(src.indexOf("weigz")>-1){
                    my_follow(lecturerId,1);
                    $(".attention").find('img').attr('src','/xcview/images/yigz.png');
                    $(".attention").find('.pay_attention').html("已关注");
                    $(".attention").css("background","#bbb");
                    $(".fans").find('#fansCount').html(parseInt(p)+1);
                } else {
                    my_follow(lecturerId,2);
                    $(".attention").find('img').attr('src','/xcview/images/weigz.png');
                    $(".attention").find('.pay_attention').html("加关注");
                    $(".attention").css("background","#00bc12");
                    $(".fans").find('#fansCount').html(parseInt(p)-1);
                }
            });

        }
    });
    
}
function sowingDetails(url) {
    if(url != null && url != ""){
        location.href = url;
    }
}
//动态列表
function doctorPostsList(doctorPostsType) {
    requestGetService("/doctor/posts", {
        pageNumber: 1,
        pageSize:10,
        type:doctorPostsType,
        doctorId:doctorId
    }, function (data) {
        var obj = data.resultObject.records;
        for(var i=0;i<obj.length;i++){
            var likes ="";
            //判断是否点赞
            obj[i].isPraise=false;
            //封装点赞列表
            var likeList = obj[i].doctorPostsLikeList;
            if(likeList!=null&&likeList.length>0){
                for(var j=0;j<likeList.length;j++){
                    if(loginUserId!=undefined&&loginUserId==likeList[j].userId){
                        obj[i].isPraise=true;
                    }
                    likes += likeList[j].userName+",";
                }
                obj[i].likes = likes.substr(0,likes.length-1);
            }
            //过滤文章内容标签
            if(obj[i].articleId!==null && obj[i].articleId!==""){
                obj[i].articleContent = obj[i].articleContent.replace(/<.*?>/ig,"");
            }

        }


            //判断全部动态默认图
            if(data.resultObject.records.length==0){
                $(".baseImagenumbers").show();
            }else{
                $(".baseImagenumbers").hide();
            }

        $(".rests_nav").html(template('wrap_doctor_dynamics',{items:obj}));


        /*$(".consilia_nav_span .title").each(function(){
            var title = $(this);
            // 判断可编辑医案显示隐藏
            if(data.resultObject.records.content == null || data.resultObject.records.content == ""){
                    title.parent(".consilia_nav_span").hide();
                    // alert(1111);
                }else{
                    title.parent(".consilia_nav_span").show();
                    // alert(2222);
            }
        });*/


        for(var i=0;i<obj.length;i++){
            //没有点赞时隐藏小手
            if(obj[i].doctorPostsCommentList.length==0 && obj[i].doctorPostsLikeList.length==0){
                $("#"+obj[i].id).hide();
                $("#"+obj[i].id).find(".number_people_fize").hide();
                $("#"+obj[i].id).find(".evaluate_main").hide();
            } else if(obj[i].doctorPostsCommentList.length>0 && obj[i].doctorPostsLikeList.length==0){
                $("#"+obj[i].id).find(".number_people_fize").hide();
            } else if(obj[i].doctorPostsCommentList.length==0 && obj[i].doctorPostsLikeList.length>0){
                $("#"+obj[i].id).find(".evaluate_main").hide();
            }
            //cc视频
            if(obj[i].video!=null&&obj[i].video!=""){
                ccVideo(obj[i].video,1,obj[i].id);
            }
            //判断关键字   动态类别：1.普通动态2.图片动态3.视频动态4.文章动态5.课程动态
            if(obj[i].type == 2 || obj[i].type == 1 || obj[i].type == 4){
                var content = obj[i].content;
            if (content != null && content.indexOf('#')>=0) {// 判断#是否存在
                var arr = content.split("#");
                var str1="";
                for (var j = 0; j < arr.length; j++) {
                  if (j%2==0) { // 除2余数等0，就是整除的意思
                  str1=str1+arr[j];// 字符串链接
                  } else{
                  str1 = str1+"<span style='color:#333;'  class='span_span'>#"+arr[j]+"#</span>";// 字符串链接
                  };
                };
                obj[i].content = str1;
                // alert(obj[i].content=str1;);
                $('.span_span'+obj[i].id+'').html(str1);
            }
            }
        }


        //判断普通动态
        /*var h = $(".essay_main").height();
        if (h > 200) {
            $(".essay_pack_up_btn").show();
            $(".line_xian").hide();
        } else {
            $(".essay_pack_up_btn").hide()
        }*/

        $(".essay_main").each(function(){
            var hh = $(this).height();
            var essay_pack_up_btn = $(this).next();
            if (hh > 70) {
                essay_pack_up_btn.show();
                $(this).addClass("consilia_nav_span_title");
            } else {
                essay_pack_up_btn.hide();
            }
        });
        $(".essay_pack_up_btn").click(function(){
            var dynamic = $(this);
            if(dynamic.find('span').html()=="展开"){
                dynamic.parent().find('.essay_main').removeClass("consilia_nav_span_title");
                // $(".consilia_nav_span .title").removeClass("consilia_nav_span_title");
                dynamic.find('span').html("收起");
            }else{
                // $(".consilia_nav_span .title").addClass("consilia_nav_span_title");
                dynamic.parent().find('.essay_main').addClass("consilia_nav_span_title");
                dynamic.find('span').html("展开");
            }
        });

        //点击其他--收起
        $(".essay_pack_up_btn").click(function(){
            if($(".essay_pack_up_btn_span").html()=="收起"){
                $(".consilia_nav_span .title").css("height","100%");
                $(".essay_pack_up_btn_span span").html("展开");
                $(".consilia_nav_span .title").addClass("consilia_nav_span_title");
            }else{
                $(".essay_pack_up_btn_span span").html("收起");
                $(".consilia_nav_span .title").css("height","2.1rem");
                $(".consilia_nav_span .title").removeClass("consilia_nav_span_title");
            }
        });



        $(".consilia_nav_span .title").each(function(){
            var h = $(this).height();

            var consilia_nav_btn = $(this).parent().next().next();

            if (h > 70) {
                consilia_nav_btn.show();
                $(this).addClass("consilia_nav_span_title");
            } else {
                consilia_nav_btn.hide();

            }

        })

        // 点击文章收起
        $(".consilia_nav_btn").click(function(){
            var post = $(this);
            if(post.find('span').html()=="展开"){
                post.parent().find('.consilia_nav_span').find('.title').removeClass("consilia_nav_span_title");
                // $(".consilia_nav_span .title").removeClass("consilia_nav_span_title");
                post.find('span').html("收起");
            }else{
                // $(".consilia_nav_span .title").addClass("consilia_nav_span_title");
                post.parent().find('.consilia_nav_span').find('.title').addClass("consilia_nav_span_title");
                post.find('span').html("展开");
            }
        });


        //点击评论表情
        /*mui("#refreshContainer").on('tap', '.face', function (event) {
            alert(111);
            
        });*/


        //点赞
        $(".zan_img").click(function(){
            var src = $(this).attr('src');
            var postsId = $(this).attr('data-id');
            if(src.indexOf("zan001")>-1){
                delPostsLike(this,postsId);
            }else{
                postsLike(this,postsId);
            }
        });
        //评论
        $(".rests_nav .evaluate_img").click(function(){
            $(".face").attr("src","/xcview/images/face.png");
            $("#page_emotion").css("bottom","-2.8rem");
            $(".comment").show();
            $(".article_main").show();
            $(".article_main").html("说点什么...");
            getPostsIdByComment = $(this).attr('data-id');
            postsCommentId = "";
        });
        // 点击其他内容区域隐藏评论区域
        $(".comment_hide").click(function(){
            $(".face").attr("src","/xcview/images/face.png");
            $("#page_emotion").css("bottom","-2.8rem");
            $(".comment").hide();
        });

        // 回复/删除
        $(".evaluateDiv").click(function(){
            postsCommentId = $(this).attr('data-id');
            postsCommentUserName = $(this).attr('data-userName');
            getPostsIdByComment = $(this).attr('data-postsId');
            var userId = $(this).attr('data-userId');
            var replyUserId = $(this).attr('data-replyUserId');
            var data_postsId = $(this).attr('data-postsId');
            if(replyUserId!=undefined&&replyUserId!=""){
                userId=replyUserId;
            }
            if(userId!=loginUserId){
                $(".face").attr("src","/xcview/images/face.png");
                $("#page_emotion").css("bottom","-2.8rem");
                $(".comment").show();
                $(".article_main").show();
                $(".article_main").html("回复"+postsCommentUserName+":");
            }else {
                // alert("删除");
                $(".remove_copy").show();
                $(".remove").unbind( "click" );
                $(".remove").click(function () {
                    ajaxRequest("/doctor/posts/"+data_postsId+"/comment/"+postsCommentId,null,"DELETE",function(data) {
                        if(data.success==true){
                            $(".remove_copy").hide();
                            $(".evaluate"+data_postsId).find("."+postsCommentId).remove();

                            if($("#"+data_postsId).children(".number_people_fize").children("span").is(":empty") &&data.resultObject.length==0){
                                $("#"+data_postsId).hide();
                            }else if(!$("#"+data_postsId).children(".number_people_fize").children("span").is(":empty") && data.resultObject.length==0){
                                $("#"+data_postsId).find(".evaluate_main").hide();
                            }
                            webToast("删除成功","middle",1500);
                        }else{
                            webToast("删除失败","middle",1500);
                        }
                    });
                })
            }
        });

        //文章跳转
        $(".article_hide").click(function(){
            var articleId = $(this).attr("data-id");
            location.href = "/xcview/html/physician/article.html?articleId=" + articleId;
        });
        //课程跳转
        $(".course_hide").click(function(){
            var itemId = $(this).attr("data-id");
            common_jump_all(itemId)
        });
        //医案跳转
            $(".consilia_nav_cen").click(function(){
            var itemId = $(this).attr("data-id");
            location.href = "/xcview/html/physician/consilia.html?articleId=" + itemId;
        });
        //点击视频播放/暂停
        $(".ccvideo").click(function(){
            var ccId = $(this).find("video").attr("id");
            var oReplay = document.getElementById(ccId);
            $(".ccvideo_img").hide();
            if (oReplay.paused){
                oReplay.play();
            }
            else{
                oReplay.pause();
            }
        });


    });
}

function postsType(obj) {
    doctorPostsType = $(obj).attr("value");
    doctorPostsList(1,'down',doctorPostsType);
    //alert(type)
}
/**
 * 评论
 */
function sendComment(){
    var article = $("#form_article").val();
    if($("#form_article").val()==""){
        webToast("内容不能为空","middle",1500);
        return false;
    }
    requestService("/doctor/posts/"+getPostsIdByComment+"/comment",{
        postsId:getPostsIdByComment,
        commentId:postsCommentId,
        content:article
    },function(data) {
        if(data.success==true){
            $("#form_article").val("");
            var evaluatePostsId = "evaluate"+getPostsIdByComment;
            var getNewPostsCommentId = data.resultObject[0].id;
            $("#"+getPostsIdByComment).show();
            if(!$("#"+getPostsIdByComment).children(".number_people_fize").children("span").is(":empty")){
                $("#"+getPostsIdByComment).find(".number_people_fize").show();
            } else {
                $("#"+getPostsIdByComment).find(".number_people_fize").hide();
            }
            $("#"+getPostsIdByComment).find(".evaluate_main").show();
            if(postsCommentId==""){
                $("."+evaluatePostsId+"").prepend("<div class='evaluateDiv' data-id="+getNewPostsCommentId+" data-postsId="+getPostsIdByComment+" data-userId="+loginUserId+" >" +
                    "<span class='name'>"+loginUserName+"：</span><span class=\"evaluate_cen\">"+article+"</span></div><div class=\"both\"></div>");
                $("."+evaluatePostsId+"").find("").attr("class");
                $("div[data-id="+getNewPostsCommentId+"]").addClass(""+getNewPostsCommentId+"");
            }else {
                $("."+evaluatePostsId+"").prepend("<div class=\"both\"></div><div class='response evaluateDiv 'data-id="+getNewPostsCommentId+"" +
                    " data-postsId="+getPostsIdByComment+" data-replyUserId="+loginUserId+"> " +
                    "<span class=\"my\">"+loginUserName+"</span> <span class=\"response_cen\">回复</span> " +
                    "<span class=\"she\">"+postsCommentUserName+"：</span> <span class=\"response_center\">"+article+"</span>" +
                    "</div>");
                $("div[data-id="+getNewPostsCommentId+"]").addClass(""+getNewPostsCommentId+"");
            }

            // 回复/删除
            $(".evaluateDiv").unbind( "click" );
            $(".evaluateDiv").click(function(){
                postsCommentId = $(this).attr('data-id');
                getPostsIdByComment = $(this).attr('data-postsId');
                var userId = $(this).attr('data-userId');
                var replyUserId = $(this).attr('data-replyUserId');
                if(replyUserId!=undefined&&replyUserId!=""){
                    userId=replyUserId;
                }
                if(userId!=loginUserId){
                    $(".face").attr("src","/xcview/images/face.png");
                    $("#page_emotion").css("bottom","-2.8rem");
                    $(".comment").show();
                }else {
                    $(".remove_copy").show();
                    $(".remove").unbind( "click" );
                    $(".remove").click(function () {
                        ajaxRequest("/doctor/posts/"+getPostsIdByComment+"/comment/"+postsCommentId,null,"DELETE",function(data) {
                            if(data.success==true){
                                $(".remove_copy").hide();
                                $(".evaluate"+getPostsIdByComment).find("."+postsCommentId).remove();
                                if($("#"+getPostsIdByComment).children(".number_people_fize").children("span").is(":empty") &&data.resultObject.length==0){
                                    $("#"+getPostsIdByComment).hide();
                                }else if(!$("#"+getPostsIdByComment).children(".number_people_fize").children("span").is(":empty") && data.resultObject.length==0){
                                    $("#"+getPostsIdByComment).find(".evaluate_main").hide();
                                }
                                webToast("删除成功","middle",1500);
                            }else{
                                webToast("删除失败","middle",1500);
                            }
                        });
                    })
                }

            });
            webToast("评论成功","middle",1500);
        }else{
            webToast(data.resultObject,"middle",1500);
        }
    });
}

/**
 * 点赞
 */
function postsLike(obj,postsId) {
    requestService("/doctor/posts/"+postsId+"/like/"+1,{
        postsId:postsId
    },function(data) {
        if(data.success==true){
            $(obj).attr('src','/xcview/images/zan001.png');
            $("#"+postsId+"").children("div").find("img").attr('src','/xcview/images/zan001.png');
            //重新获取点赞列表
            $("#"+postsId).show();
            if($("#"+postsId).children(".evaluate_main").children(".evaluateDiv").length==0 ){
                $("#"+postsId).find(".evaluate_main").hide();
            }
            $("#"+postsId).find(".number_people_fize").show();

            getPostsLikeList(postsId,data.resultObject.list);
        }else{
            webToast(data.resultObject,"middle",1500);
        }
    });
}
/**
 * 取消点赞
 */
function delPostsLike(obj,postsId) {
    requestService("/doctor/posts/"+postsId+"/like/"+0,{
        postsId:postsId
    },function(data) {
        if(data.success==true){
            $(obj).attr('src','/xcview/images/zan01.png');
            $("#"+postsId+"").children("div").find("img").attr('src','/xcview/images/zan01.png');
            //重新获取点赞列表
            getPostsLikeList(postsId,data.resultObject.list);
            if($("#"+postsId).children(".evaluate_main").children(".evaluateDiv").length==0 && data.resultObject.list.length==0){
                $("#"+postsId).hide();
            }else if(data.resultObject.list.length==0){
                $("#"+postsId).find(".number_people_fize").hide();
            }
        }else{
            webToast(data.resultObject,"middle",1500);
        }
    });
}
//获取点赞列表
function getPostsLikeList(postsId,list) {

    var likes = list;
    var like="";
    if(likes!=null&&likes.length>0){
        for(var j=0;j<likes.length;j++){
            like += likes[j].userName+",";
        }
        $("#"+postsId+"").children("div").find(".likes").html(like.substr(0,like.length-1));
    }else {
        $("#"+postsId+"").children("div").find(".likes").html("");
    }
}

/**
 * videoId : 视频播放id
 * multimediaType:媒体类型  视频 1 音频 2
 */
function ccVideo(videoId, multimediaType,id) {
    var playerwidth = window.screen.width; //   屏幕分辨率的宽：window.screen.width
    var playerheight = 8.95 * 21.8; //  屏幕分辨率的高：window.screen.height
    console.log(playerwidth);
    var dataParams = {
        playerwidth: playerwidth,
        playerheight: playerheight,
        videoId: videoId,
        multimedia_type: multimediaType
    }
    requestService("/xczh/ccvideo/palyCode",
        dataParams, function (data) {
            if (data.success) {
                var playCodeStr = data.resultObject;
                var playCodeObj = JSON.parse(playCodeStr);
                console.log(playCodeObj.video.playcode);
                //$("#ccvideo").html(playCodeObj.video.playcode)
                $("#ccvideo"+id).html(playCodeObj.video.playcode);
            } else {
            }
        }, false);
}

//文章跳转
function articleDetails(id) {
    location.href = "/xcview/html/physician/article.html?articleId=" + id;
}
//医案跳转
function consiliaDetails(id) {
    location.href = "/xcview/html/physician/consilia.html?articleId=" + id;
}

//增加/取消关注
function my_follow(followed, type) {
    requestService("/xczh/myinfo/updateFocus", {
        lecturerId: followed,
        type: type
    }, function (data) {
    })
}




/* --------------直播间------------- */
/*直播间开始*/
// 一、获取是否医师权限。二、获取完权限，获取课程。三、获取完课程判断类型。
// 点击直播间回放和直播中状态跳转发礼物直播间
function detailsId(id){
    // var id = data.resultObject.courseList.id;
    location.href = "/xcview/html/details.html?courseId=" + id;
};

// 定义无直播状态方法--为您推荐，默认图
function defaultId(){
    requestService("/xczh/course/recommendSortAndRand", null,function (data) {
        if (data.success == true) {
            // 为您推荐
            $('#broadcastroom_course').html(template('broadcastroom_id', {items: data.resultObject}));
        }
    });
}
// 定义获取当前页面id
// var doctorId = getQueryString('doctor');

requestService("/xczh/doctors/doctorStatus", {doctorId:doctorId},function (data) {  //一、获取是否医师权限。
    if (data.success == true) {
        // 0 无权限 1 医师认证通过 2 医馆认证通过 3 医师认证被禁用
        var status = data.resultObject.status;
        if (status == 0 || status == 3) {
                $(".no_live").css("display","block");
                $("#recommended").css("display","block");
                $(".living_broadcastroom").css("display","none");  /*封面图隐藏*/
                $("#live_lesson").css("display","none");  /*直播课程*/
                defaultId();
                
        }else{
            userId = data.resultObject.userId;
            var type = 3;
            requestService("/xczh/course/courseTypeNumber", {  //二、获取完权限，获取课程。
                userId : userId,
                type : type
                },function (data) {
                    if (data.success) {
                        var number = data.resultObject;
                        if (number > 0) {   //三、获取完课程判断类型。
                            requestService("/xczh/doctors/recentlyLive", {
                                userId:userId,
                                pageSize: 1000
                            },function (data) {  
                                if (data.success == true) {
                                    // 直播状态
                                    //直播课程状态：lineState  1直播中， 2预告，3直播结束 ， 4 即将直播 ，5 准备直播 ，6 异常直播
                                    $('#living_broadcastroom').html(template('living_broadcastroom_id', {items: data.resultObject}));
                                        
                                    var obj =  data.resultObject;
                                    var startStr =  data.resultObject.startTime;
                                    if(obj!=null && startStr!=null){
                                        
                                         //兼容ios和安卓了
                                         var startTime = startStr.replace(/\-/g, "/");
                                         
                                         function timer() {
                                                //设置结束的时间
                                                var endtime = new Date(startTime);
                                                //设置当前时间
                                                var now = new Date();
                                                //得到结束与当前时间差 ： 毫秒
                                                var t = endtime.getTime() - now.getTime();
                                                
                                                if (t > 0) {
                                                    //得到剩余天数
                                                    // var tian = Math.floor(t / 1000 / 60 / 60 / 24);
                                                    //得到还剩余的小时数（不满一天的小时）
                                                    var h = Math.floor(t / 1000 / 60 / 60 % 24);
                                                    if(h<10){
                                                        h="0"+h;
                                                    }
                                                    //得到分钟数
                                                    var m = Math.floor(t / 1000 / 60 % 60);
                                                    if(m<10){
                                                        m="0"+m;
                                                    }
                                                    //得到的秒数
                                                    var s = Math.floor(t / 1000 % 60);
                                                    if(s<10){
                                                        s="0"+s;
                                                    }
                                                    var str = "直播倒计时 " + h + "：" + m + "：" + s;
                                                    $("#box1").html(str);
                                                }
                                            }
                                            
                                            if(obj!=null && obj.isLive == 1){
                                                setInterval(timer, 1000);
                                            }else if(obj!=null && (obj.lineState ==2 || obj.lineState == 4  || obj.lineState ==5)){
                                                var str = startStr.replace(/\-/g, ".").slice(0,16)+ "   即将直播";
                                                $("#box1").html(str);
                                            }
                                        
                                    }
                                    
                                    }
                                });
                                requestService("/xczh/course/liveDetails", {userId:userId},function (data) {  
                                    if (data.success == true) {
                                        // 直播状态
                                        //直播课程状态：lineState  1直播中， 2预告，3直播结束 ， 4 即将直播 ，5 准备直播 ，6 异常直播
                                        $('#living_broadcastroom').html(template('living_broadcastroom_id', {items: data.resultObject}));
                                    }
                                });

                                // 直播课程
                                requestService("/xczh/doctors/doctorCourse", {userId:userId},function (data) {  
                                    if (data.success == true) {
                                        // 直播课程
                                        $('#live_streaming').html(template('live_streaming_id', {items: data.resultObject[1].courseList}));
                                    }
                                });
                            }else{
                                $(".no_live").css("display","block");     /*默认背景图*/
                                $("#recommended").css("display","block"); /*显示为您推荐*/
                                $(".living_broadcastroom").css("display","none");  /*封面图隐藏*/
                                $("#live_lesson").css("display","none");  /*直播课程*/
                                defaultId();
                            };

                        }

                            /*介绍开始*/
                            // doctorIds = data.resultObject.doctorId;
                            requestService("/xczh/doctors/introduction", {doctorId:getQueryString('doctor')},function (data) {
                                if (data.success == true) {
                                    // 介绍
                                    var hospitalData=data.resultObject.hospital;
                                    if (hospitalData != null && hospitalData != "") {
                                        $('#message_referral').html(template('message_referral_id', {items: hospitalData}));

                                        if (hospitalData.name == "") {
                                        $(".clinic").addClass("hide");
                                        }
                                        if (hospitalData.tel == null) {
                                            $(".tel").addClass("hide");
                                        }
                                        if (hospitalData.detailedAddress == null) {
                                            $(".house_address").addClass("hide");
                                        }
                                        /*if (data.resultObject.lecturerInfo = null && data.resultObject.lecturerInfo.workTime = null) {
                                            $(".hid_wtime").addClass("hide");
                                        }*/
                                        // 个人介绍
                                        if(data.resultObject.description == null || data.resultObject.description == ''){
                                            
                                            $(".self_introduction").hide();
                                        }else{

                                            $(".self_introduction_cen").html(data.resultObject.description);
                                        };

                                        if(data.resultObject.workTime == null || data.resultObject.workTime == ''){
                                            
                                            $(".table").hide();
                                        }else{

                                            var workTime = data.resultObject.workTime; //这是一字符串 
                                            // workTime = "1.1,3.2";
                                            var apms = workTime.split(",");   //先分离,获取X--Y
                                            for(var i in apms){
                                                var apm = apms[i].split(".");   
                                                $(".apm"+apm[0]+"_"+apm[1]+" img").show();
                                            }

                                        };

                                    }else{
                                        $(".baseImagenumber").show();
                                    }

                                    // $('#message_referral').html(template('self_introduction_id', {items: data.resultObject}));
                                    

                                    

                                    // if (data.resultObject.hospital.name == null && data.resultObject.hospital.tel == null && data.resultObject.hospital.detailedAddress == null && data.resultObject.description == null && data.resultObject.description == '') {
                                    //     alert(123);
                                    //     $(".baseImagenumber").show();

                                    // };
                                                                    

                                }
                            });
                            /*介绍结束*/

                    });
                    
            }

    }




});
/*直播间结束*/


