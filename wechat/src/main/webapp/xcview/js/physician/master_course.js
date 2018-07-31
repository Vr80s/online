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


// 定义获取当前页面id
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
    
    // 跟师直播列表===============================================================================课程
    requestGetService("/xczh/host/doctor/apprentice",{doctorId:doctorId},function (data) {
        if (data.success == true) {

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
            }
        }
    });
}
var doctorCourseUserId="";

function doctorCourses(data){
    userId = data.resultObject.userId;
    var type = 6;
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
                    // createDoctorCourse(userId);                        
                }else{
                    $(".no_live").css("display","block");     /*默认背景图*/
                    $("#recommended").css("display","block"); /*显示为您推荐*/
                    $(".living_broadcastroom").css("display","none");  /*封面图隐藏*/
                    $("#live_lesson").css("display","none");  /*直播课程*/
                    // defaultId();
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
                // defaultId();
            }else{

                //有权限，获取课程列表
                doctorCourses(data);
            }
        }
    });
}
doctorStatus();
/*直播间结束*/

// 师承开始
function apprenticeInfo() {
    // 在线弟子申请的状态 1->未报名 2->没有审核 3->审核未通过 4->审核已通过 . 值是1与3 去提交信息页面 2与4 去申请信息查看页
    requestGetService("/xczh/host/doctor/apprentice",{doctorId:doctorId},function (data) {
        if (data.success == true) {
            // 招生简章详情
            // $('.prose_origin_main').html(template('prose_origin_main_id', {items: data.resultObject.regulations}));

            $(".prose_origin_details").click(function(){
                var id=$(this).attr("data-ids");
                var tokenStr=$(this).attr("data-tokenStr");
                location.href ='/xcview/html/apprentice/inherited_introduction.html?merId='+id;
            });  //    '/xcview/html/physician/physician_list.html?queryKey='+search_val+'&curriculum_blck=2';
            // 获取判断是否提交申请弟子信息  
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
            }

        }

    });
}

apprenticeInfo();  /*师承方法*/

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


function checkAuth(doctorId) {
    var USER_UN_BIND = 1005;//用户用微信登录的但是没有绑定注册信息
    var USER_UN_LOGIN = 1002;//未登录
    var flag = getFlagStatus();
  
    if (flag === USER_UN_BIND) {
        var rd = getCurrentRelativeUrl();
        localStorage.setItem("rd", rd); 
        location.href = "/xcview/html/evpi.html";
    }else if(flag === USER_UN_LOGIN){
          var rd = getCurrentRelativeUrl();
         localStorage.setItem("rd", rd); 
        
        location.href = "/xcview/html/enter.html";
    }else{
        window.location.href="/xcview/html/physician/quiz.html?doctor="+doctorId; 
    }
}

// 判断是否在线弟子
    $(".learn_tips_close").click(function(){
        $(".learn_tips").hide();
    });

    $(".learn_tips_submit").click(function(){
        $(".learn_tips").hide();
    });

    $(".order_tips_close").click(function(){
        $(".order_tips").hide();
    });

    $(".order_tips_no_close").click(function(){
        $(".order_tips_no").hide();
    });

    // 审核中关闭叉号
    $(".learn_tips_audit_close").click(function(){
        $(".learn_tips").hide();
    });

    // 我知道了--课程
    $(".learn_tips_part_submit").click(function(){
        $(".learn_tips_part").hide();
    });

    // 弟子审核中
    $(".learn_tips_audit_submit").click(function(){
        $(".learn_tips_audit").hide();
    });
    $(".learn_tips_audit_bg").click(function(){
        $(".learn_tips_audit").hide();
    });

