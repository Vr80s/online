// 师承内容出现--1、动态-发布动态课程。2、直播间（4）-师承--跟师直播课程列表。3、直播间--最近直播------4个地方出现判断是否是弟子

var doctorId = getQueryString("doctor");
var loginUserId="";
var loginUserName="";
var getPostsIdByComment="";
var postsCommentId="";
var postsCommentUserName="";
var doctorPostsType ="";
var isShow = false;
var option_id="";
$(function(){
    loginUserId = localStorage.getItem("userId");
    loginUserName = localStorage.getItem("name");
    sowingMap();
    //点击全部动态
    $(".li_color").click(function () {
        if(isShow){
            $(".baseImagenumbers").show();
        }
    })
    //点击选项
    $('#mainMenu').click(function(e){
        console.log(e.target.id);
        option_id = e.target.id;
        if (option_id == "") {
            option_id = "li-1";
        }
        if(option_id == "li-1"){
            miniRefresh.endDownLoading(true);
        }
    });

});
//轮播图
function sowingMap() {
    requestGetService("/xczh/host/doctor/v2",{
        doctorId:doctorId
    },function(data) {
        if(data.success==true){
            var obj = data.resultObject;
            $(".top_details").html(template('top_details',{items:obj}));
            var mySwiper = new Swiper('.swiper-container', {
                autoplay: true, //可选选项，自动滑动
                loop: true,
                pagination: {
                    el: '.swiper-pagination'
                }
            })
            
            //是否显示粉丝数
            /*if(obj.fansCount > 0){
                $(".fans").show();
            } else {
                $(".fans").hide();
            }*/

            

            //关注
            $(".attention").click(function(){
                $(".fans").show();
                var lecturerId = $(this).attr("data-userId");
                var src = $(this).find('img').attr('src');
                var p = $(".fans").find('#fansCount').html();
                if(src.indexOf("weigz")>-1){
                    my_follow(lecturerId,1);
                    $(".attention").find('img').attr('src','/xcview/images/yigz.png');
                    $(".attention").find('.pay_attention').html("已关注");
                    $(".attention").css("background","#bbb");
                    $(".fans").find('#fansCount').html(parseInt(p)+1);
                    $(".fans").show();
                } else {
                    my_follow(lecturerId,2);
                    $(".attention").find('img').attr('src','/xcview/images/weigz.png');
                    $(".attention").find('.pay_attention').html("加关注");
                    $(".attention").css("background","#00bc12");
                    $(".fans").find('#fansCount').html(parseInt(p)-1);
                    /*if(parseInt(p)-1 <= 0){
                        $(".fans").hide();
                    }*/
                }
            });

        }
    });
    
}
//点击轮播
function sowingDetails(url) {
    if(url != null && url != ""){
        location.href = url;
    }
}

function idotototo(){
    var $dot5 = $('.show-text');
    $dot5.each(function () {
        if($(this).attr("data-flag")==1)return;
        if ($(this).height() > 100) {
            $(this).attr("data-flag", 1);
            $(this).height(100);
            $(this).append('<span class="qq"> <a class="toggle" href="###" style="color:#2cb82c"><span class="opens">展开<span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span></span><span class="closes">收起<span class="glyphicon glyphicon-menu-up" aria-hidden="true"></span></span></a></span>');
        }
        var $dot4 = $(this);

        function createDots() {
            $dot4.dotdotdot({
                after: 'span.qq'
            });
        }
        function destroyDots() {
            $dot4.trigger('destroy');
        }

        createDots();
        $dot4.on(
            'click',
            'a.toggle',
            function () {
                $dot4.toggleClass('opened');

                if ($dot4.hasClass('opened')) {
                    destroyDots();
                } else {
                    createDots();
                }
                return false;
            }
        );
 });
    
}
//动态列表
function doctorPostsList(pageNumber,downOrUp,doctorPostsType) {
    requestGetService("/doctor/posts", {
        pageNumber: pageNumber,
        pageSize:10,
        type:doctorPostsType,
        doctorId:doctorId
    }, function (data) {
        var obj = data.resultObject.records;
        for(var i=0;i<obj.length;i++){
            if(obj[i].type==5){
                if(obj[i].teaching){
                    obj[i].teaching=1;
                }else{
                    obj[i].teaching=0;
                }
            }
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
            //是否上下架
            if(obj[i].articleStatus){
                obj[i].articleStatus = 1;
            }else {
                obj[i].articleStatus = 0;
            }
            if(obj[i].courseStatus){
                obj[i].courseStatus = 1;
            }else {
                obj[i].courseStatus = 0;
            }

        }
//  判断是下拉刷新还是上拉加载
        if(downOrUp=='down'){
            //判断全部动态默认图
            if(data.resultObject.records.length==0){

                var minirefreshs=$(window).height()-$(".top_show_heights").height();
                $(".rests_nav").height(minirefreshs);
                
                isShow = true;
                $(".baseImagenumbers").show();
                //$(".upwrap-tips").hide();
            }else{
                isShow = false;
                $(".baseImagenumbers").hide();
            }

            $(".rests_nav").html(template('wrap_doctor_dynamics',{items:obj}));
            miniRefresh.endDownLoading(true);// 结束下拉刷新
            idotototo();
        } else if(obj.length==0){
            miniRefresh.endUpLoading(true);// 结束上拉加载
        } else {
            $(".rests_nav").append(template('wrap_doctor_dynamics',{items:obj}));

            miniRefresh.endUpLoading(false);
            idotototo();
        }

        //图片放大
        webpackUniversalModuleDefinition(imgWindow,imgfn);

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

        //点赞
        $(".zan_img").off("click");
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
        $(".evaluate_img").off("click");
        $(".evaluate_img").click(function(){
            $(".evaluate_img").removeClass("comment_hide");
            $(".face").attr("src","/xcview/images/face.png");
            $("#page_emotion").css("bottom","-2.8rem");
            $(".comment").show();
            $(".article_main").show();
            $(".article_main").html("说点什么...");
            getPostsIdByComment = $(this).attr('data-id');
            postsCommentId = "";
        });

        // 回复/删除
        $(".evaluateDiv").off("click");
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
        $(".article_hide").off("click");
        $(".article_hide").click(function(){
            var articleId = $(this).attr("data-id");
            var articleStatus = $(this).attr("data-status");
            var writingsId = $(this).attr("data-writingsId");
            if(articleStatus == 1){
                if(writingsId != null && writingsId != ""){
                    location.href = "/xcview/html/physician/consilia.html?consiliaId=" + articleId;
                } else {
                    location.href = "/xcview/html/physician/consilia.html?consiliaId=" + articleId;
                }
            } else {
               return false;
            }


        });
        //课程跳转
        $(".course_hide").off("click");
        $(".course_hide").click(function(){
            var itemId = $(this).attr("data-id");
            var teaching = $(this).attr("data-teaching");
            var courseStatus = $(this).attr("data-status");
            jumpTeachingCourse(itemId,teaching);
        });







        //医案跳转
        $(".consilia_nav_cen").off("click");
        $(".consilia_nav_cen").click(function(){
            var itemId = $(this).attr("data-id");
            var articleStatus = $(this).attr("data-status");
            if(articleStatus == 1){
                location.href = "/xcview/html/physician/consilia.html?consiliaId=" + itemId;
            } else {
                return false;
            }

        });
        //点击视频播放/暂停
        $(".ccvideo").off("click");
        $(".ccvideo").click(function(){
            var data_id = $(this).attr("data-id");
            $(".ccvideo"+data_id).hide();
            /*$(".ccH5FullsBtn").css("display","none");    
            $(".ccH5ExitFullsBtn").css("display","none");*/
        });

    });
}
/**
 * 点击类型
 */
function postsType(obj) {
    doctorPostsType = $(obj).attr("value");
    if (option_id == "li-1"){
        page = 1;
        doctorPostsList(1,"down",doctorPostsType);
    }

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
            postsCommentUserName = data.resultObject[0].userName;
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
                $("."+evaluatePostsId+"").prepend("<div class=\"both\"></div><div class='response evaluateDiv 'data-id="+getNewPostsCommentId+" data-userName="+postsCommentUserName+"" +
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
                postsCommentUserName = $(this).attr('data-userName');
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
                    $(".article_main").show();
                    $(".article_main").html("回复"+postsCommentUserName+":");
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

//增加/取消关注
function my_follow(followed, type) {
    requestService("/xczh/myinfo/updateFocus", {
        lecturerId: followed,
        type: type
    }, function (data) {
    })
}



//刷新
// 初始化页码
var page = 1;

// miniRefresh 对象
var miniRefresh = new MiniRefresh({
    container: '#minirefresh',
    down: {
        //isLock: true,//是否禁用下拉刷新
        callback: function () {
            if (option_id == "li-1"){
                page = 1;
                sowingMap();
                doctorPostsList(page,'down',doctorPostsType);
            } else if (option_id == "li-2") {
                sowingMap();
                doctorStatus();
                miniRefresh.endDownLoading(true);// 结束下拉刷新
            } else if (option_id == "li-3") {
                sowingMap();
                apprenticeInfo();
                miniRefresh.endDownLoading(true);// 结束下拉刷新
            } else if (option_id == "li-4") {
                sowingMap();
                doctorIntroduction();
                miniRefresh.endDownLoading(true);// 结束下拉刷新
            }

        }
    },
    up: {
        isAuto: false,
        callback: function () {
            if (option_id == "li-1"){
                page++;
                doctorPostsList(page,'up',doctorPostsType);
            } else {
                miniRefresh.endUpLoading(true);// 结束上拉加载
            }
        }
    }
});



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
function recentlyLive(userId){
    requestService("/xczh/doctors/recentlyLive", {userId:userId},function (data) { 
        if (data.success == true) {
            createRecentlyLive(data.resultObject);                            
        }
    });
}

function createRecentlyLive(recentlyLive){
    if(recentlyLive.teaching){
        recentlyLive.teaching=1;
    }else{
        recentlyLive.teaching=0;
    }
    // 直播状态
    //直播课程状态：lineState  1直播中， 2预告，3直播结束 ， 4 即将直播 ，5 准备直播 ，6 异常直播
    $('#living_broadcastroom').html(template('living_broadcastroom_id', {items: recentlyLive}));
        
    var obj =  recentlyLive;
    var startStr =  recentlyLive.startTime;
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
            $(".count_down_title_span").hide();
            $("#box1").css("margin-top","-1.9rem");
            $(".count_down_title").css("margin-top","1rem");
        }else if(obj!=null && (obj.lineState ==2 || obj.lineState == 4  || obj.lineState ==5)){
            var str ="开播时间   " + startStr.replace(/\-/g, ".").slice(0,16);
            $("#box1").html(str);
        }
        
    }
}

// 直播课程列表
function createDoctorCourse(userId){
    requestService("/xczh/doctors/doctorCourse", {userId:userId},function (data) {  
        if (data.success == true) {
            // 直播课程
            $('#live_streaming').html(template('live_streaming_id', {items: data.resultObject[1].courseList}));
        }
    });
}
function doctorCourses(data){
    userId = data.resultObject.userId;
    var type = 3;
    requestService("/xczh/course/courseTypeNumber", {  //二、获取完权限，获取课程数量。
        userId : userId,
        type : type
        },function (data) {
            if (data.success) {
                var number = data.resultObject;
                if (number > 0) {  //三、获取完课程判断类型。
                    //最近的直播
                    recentlyLive(userId);                        
                    // 直播课程列表
                    createDoctorCourse(userId);                        
                }else{
                    $(".no_live").css("display","block");     /*默认背景图*/
                    $("#recommended").css("display","block"); /*显示为您推荐*/
                    $(".living_broadcastroom").css("display","none");  /*封面图隐藏*/
                    $("#live_lesson").css("display","none");  /*直播课程*/
                    defaultId();
                };

            }
        });
}
//判断医师是否具有主播权限

function doctorStatus() {
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
                //有权限，获取课程列表
                doctorCourses(data);
            }
        }
    });
}
doctorStatus();
/*直播间结束*/
//获取医师个人介绍
doctorIntroduction();

function doctorIntroduction(){
    requestService("/xczh/doctors/introduction", {doctorId:getQueryString('doctor')},function (data) {
        if (data.success) {
            createDoctorIntroduction(data.resultObject);
        }else{
            $(".message_referral_main_time").hide();
            $(".self_introduction").hide();
            $(".baseImagenumber").show();
        }
    });
}

function createDoctorIntroduction(introduction){
    // 介绍
    if (isNotBlank(introduction)) {
        $('.self_introduction_cen_html').html(template('self_introduction_cen_id', {items: introduction}));
        // 个人介绍
        if(isNotBlank(introduction.description)){
            $(".self_introduction_cen").html(introduction.description); 
        }else{
            $(".self_introduction").hide();
            $(".baseImagenumber").show();            
        };
        
        var hospitalData=introduction.hospital;
        if (isNotBlank(hospitalData)) {
            $('.message_referral_main_time').html(template('message_referral_id', {items: hospitalData}));
            if (isBlank(hospitalData.name)) {
                $(".clinic").addClass("hide");
            }
            if (isBlank(hospitalData.tel)) {
                $(".tel").addClass("hide");
            }
            if (isBlank(hospitalData.detailedAddress)) {
                $(".house_address").addClass("hide");
            }   
            if(isBlank(introduction.workTime)){
                $(".table").hide();
            }else{
                var workTime = introduction.workTime; //这是一字符串 
                var apms = workTime.split(",");   //先分离,获取X--Y
                for(var i in apms){
                    var apm = apms[i].split(".");   
                    $(".apm"+apm[0]+"_"+apm[1]+" img").show();
                }
            };
        }else{
            $(".message_referral_main_time").hide();
        }
    }else{
        $(".message_referral_main_time").hide();
        $(".self_introduction").hide();
        $(".baseImagenumber").show();
    };
}

// 师承开始
function apprenticeInfo() {
    // 在线弟子申请的状态 1->未报名 2->没有审核 3->审核未通过 4->审核已通过 . 值是1与3 去提交信息页面 2与4 去申请信息查看页
    requestGetService("/xczh/host/doctor/apprentice",{doctorId:doctorId},function (data) {
        if (data.success == true) {
            // 招生简章详情
            $('.prose_origin_main').html(template('prose_origin_main_id', {items: data.resultObject.regulations}));

            $(".prose_origin_details").click(function(){
                var id=$(this).attr("data-ids");
                var tokenStr=$(this).attr("data-tokenStr");
                location.href ='/xcview/html/apprentice/inherited_introduction.html?merId='+id;
            });
            // 获取判断是否提交申请弟子信息  师承底部判断是否提交
            $('.disciple_application_state').html(template('disciple_application_state_id', {items: data.resultObject}));
            // 判断--老师解惑--时隐藏
            if (isBlank(data.resultObject.questions)) {
                $(".QA_main").hide();
            } else{
                $(".QA_main").show();
                // 医师问答列表
                $('.QA_main').html(template('QA_main_id', {items: data.resultObject.questions}));
            }
            // 远程诊疗
            if (isBlank(data.resultObject.treatments.indexDateText)) {
                $(".therapy").show();
                // 预约
                $('.subscribe_id').html(template('subscribe_id', {items: data.resultObject.treatments}));
                // ceshi();
            } else{
                $(".therapy").hide();
            }
            // $('.subscribe_id').html(template('subscribe_id', {items: data.resultObject.treatments}));
            // 跟师直播--师承
            if (isBlank(data.resultObject.apprenticeCourses)) {
                $(".wrap_vedio_main").hide();
            } else{
                $(".wrap_vedio_main").show();
                // 跟师直播开始
                // 跟师直播开始
                var apprenticeCourses = data.resultObject.apprenticeCourses;
                for(var i=0;i<apprenticeCourses.length;i++){
                    if(apprenticeCourses[i].teaching){
                        apprenticeCourses[i].teaching=1;
                    }else{
                        apprenticeCourses[i].teaching=0;
                    }
                }
                $('#teacher_hide').html(template('teacher_hide_id', {items: apprenticeCourses}));
                // $(".more_people_time").html(data.resultObject.apprenticeCourses.startTime);
            }
            // 跟师直播--直播间
            if (isBlank(data.resultObject.apprenticeCourses)) {
                $(".wrap_vedio_main").hide();
            } else{
                $(".wrap_vedio_main").show();
                // 跟师直播开始
                var apprenticeCourses = data.resultObject.apprenticeCourses;
                for(var i=0;i<apprenticeCourses.length;i++){
                    if(apprenticeCourses[i].teaching){
                        apprenticeCourses[i].teaching=1;
                    }else{
                        apprenticeCourses[i].teaching=0;
                    }
                }
                $('#teacher_hides').html(template('teacher_hide_ids', {items: apprenticeCourses}));
                // $(".more_people_time").html(data.resultObject.apprenticeCourses.startTime);
            }
            // 弟子头像--显示
            if (isBlank(data.resultObject.apprentices)) {
                // alert(11);
                $(".disciple_main").hide();
            } else{
                $(".disciple_main").show();
                // 医师问答列表
                $('.disciple_main_id').html(template('disciple_main_id', {items: data.resultObject.apprentices}));
                // $(".disciple_number").html(data.resultObject.apprenticeCount);
                apprenticeCount();
            }
            // 获取头像长度做弟子总数--现在用 的字段
            /*var length = $(".disciple_main_apprentices").size();
            $(".disciple_number").html(length);*/
            if (isNotBlank(data.resultObject.settings)) {
                // 如何成为弟子
                $('.become_disciple_cen_id').html(template('become_disciple_cen_id', {items: data.resultObject.settings}));
            } else{
                $(".become_disciple").hide();
            }
            // webToast("提交成功","middle",1500);
        }/*else{
        webToast(data.errorMessage,"middle",1500);
    }*/
    });
}
apprenticeInfo();


// 获取师承弟子数
function apprenticeCount(){
    requestGetService("/xczh/host/doctor/v2",{
        doctorId:doctorId
    },function(data) {
        if(data.success==true){
            $('.disciple_number').html(template('disciple_number_id', {items: data.resultObject}));
        }
    });
};


// 0 -> 没有申请过弟子 1-> 弟子申请在审核中 2->已经是弟子但没有参与观看跟师直播权限
// 点击跟师直播
function jumpTeachingCourse(courseId,teaching) {
    // 是否为师承课程
    if (teaching==1) {
        requestGetService("/xczh/enrol/checkAuth",{
            doctorId:doctorId,
            courseId:courseId
        },function (data) {
            if (data.success) {
                // 是否是徒弟
                if (!data.resultObject.auth) {
                    if (data.resultObject.type == 0) {
                        $(".learn_tips").show();  //在线弟子 申请加入
                    }else if(data.resultObject.type == 1) {
                        $(".learn_tips_audit").show();  //弟子审核中
                    }else if(data.resultObject.type == 2) {
                        $(".learn_tips_part").show();  //部分弟子有权限
                    };
                }else{                                
                    liveJump(courseId);
                }
            }
        });
    }else{
        liveJump(courseId);
    }
}



// 直播跳转
function liveJump(courseId){
    location.href = "/page/course/"+courseId;
}


// 点击预约判断
function order(id){
    
    requestGetService("/xczh/enrol/checkAuth",{doctorId:doctorId},function (data) {
        if (data.success == true) {
            // 是否是徒弟
            if (data.resultObject.auth == false) {
                if (data.resultObject.type == 0) {
                    $(".order_tips").show();
                }else if(data.resultObject.type == 1) {
                    $(".order_tips_no").show();
                };

            }else{
                location.href ='/xcview/html/physician/reserve_information.html?doctor='+doctorId+'&dataId='+id+''
            }

        }
    });
};

