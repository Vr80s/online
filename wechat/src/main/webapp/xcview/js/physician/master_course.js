// 师承内容出现--1、动态-发布动态课程。2、直播间（4）-师承--跟师直播课程列表。3、直播间--最近直播------4个地方出现判断是否是弟子

var doctorId = getQueryString("doctor");
var isShow = false;


//跟师直播列表
function apprenticeList(userId,pageNumber,downOrUp) {
    requestGetService("/xczh/doctors/doctorCourseType",{userId:userId,type:5,pageNumber:pageNumber,pageSize:6},function (data) {
        if (data.success == true) {
            //  判断是下拉刷新还是上拉加载
            if(downOrUp=='down'){
                //判断全部动态默认图
                if(data.resultObject.length==0){
                    $(".wrap_vedio_main").hide();
                }else{
                    $(".wrap_vedio_main").show();
                    // 跟师直播开始
                    var apprenticeCourses = data.resultObject;
                    for(var i=0;i<apprenticeCourses.length;i++){
                        if(apprenticeCourses[i].teaching){
                            apprenticeCourses[i].teaching=1;
                        }else{
                            apprenticeCourses[i].teaching=0;
                        }
                    }
                }

                // 循环 apprenticeCourses 判断日期
                // 也就是说先把这个数据处理了，然后再给模板
                $.each(apprenticeCourses,function(key,value){
                    // console.log(startDateStr);
                    var myDate = new Date();  /*定义日期  31号(日期)*/
                    var y = myDate.getFullYear(); /*获取当天日期-年*/
                    var m = myDate.getMonth()+1; /*获取当天日期-月*/
                    if (m<10) {
                        m = "0"+m;
                    }
                    var d = myDate.getDate(); /*获取当天日期-日*/
                    if (d<10) {
                        d = "0"+d;
                    }
                    // 这里要判断年月日 2018-07-10 
                    var a = y+"."+m+"."+d;
                    var ymd = value.startTime;
                    var ymd = ymd.replace(/-/g,".");  
                    ymd = ymd.substring(0,10);// substring
                    var isday = "y";   /*今天*/
                    if (a!=ymd) {
                        // 不相等
                        // 不等startDateStr把这个换成年月日格式就行了
                        apprenticeCourses[key]['startDateStr'] = ymd;
                        apprenticeCourses[key]['isday'] = "n";
                    }else{
                        apprenticeCourses[key]['isday'] = "y";
                    }

                });

                $('#teacher_hides').html(template('teacher_hide_ids', {items: data.resultObject}));

                
                miniRefresh.endDownLoading(true);// 结束下拉刷新
            } else if(data.resultObject.length==0){
                miniRefresh.endUpLoading(true);// 结束上拉加载
            } else {
                $('#teacher_hides').append(template('teacher_hide_ids', {items: data.resultObject}));
                miniRefresh.endUpLoading(false);
            }
        }
    });
}

//判断医师是否具有主播权限
function doctorStatus(pageNumber,downOrUp) {
    requestService("/xczh/doctors/doctorStatus", {doctorId:doctorId},function (data) {  //一、获取是否医师权限。
        if (data.success == true) {
            //有权限，获取课程列表
            apprenticeList(data.resultObject.userId,pageNumber,downOrUp);
        }
    });
}
doctorStatus(1,'down');
/*直播间结束*/


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

//刷新
// 初始化页码
var page = 1;

// miniRefresh 对象
var miniRefresh = new MiniRefresh({
    container: '#minirefresh',
    down: {
        //isLock: true,//是否禁用下拉刷新
        callback: function () {
                page = 1;
            doctorStatus(page,'down');
        }
    },
    up: {
        isAuto: false,
        callback: function () {
                page++;
            doctorStatus(page,'up');
        }
    }
});