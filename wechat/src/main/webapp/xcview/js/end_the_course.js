
/**
 * 保存openId
 */
var openId = getQueryString("openId");
if(isNotBlank(openId)){
    localStorage.setItem("openid",openId);
}

$(function(){
//已结束课程
    completeCourseList(1,10,'down')
})

//获取已结束课程
function completeCourseList(pageNumber,pageSize,downOrUp) {
    requestService("/xczh/myinfo/myCourseType",{
        pageNumber:pageNumber,
        pageSize:pageSize,
        type:2
    },function(data) {
        if(data.success==true){
            if(downOrUp=='down'){
                if(data.resultObject.length==0 || data.resultObject.length==''){
                    $(".my_class_box").hide();
                    $(".wrap_noClass").show();
                }
                $(".data_my_class").html(template('data_my_class',{items:data.resultObject}));
                mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);//是否还有更多数据；若还有更多数据，则传入false; 否则传入true
                mui('#refreshContainer').pullRefresh().refresh(true);
                mui("#refreshContainer").off();
            }else if(data.resultObject.length==0 || data.resultObject.length==''){
                mui('#refreshContainer').pullRefresh().endPullupToRefresh(true);
                mui("#refreshContainer").off();
            }else{
                $(".data_my_class").append(template('data_my_class',{items:data.resultObject}));
                mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);
            }
        }else{
            alert(data.errorMessage);
        }

        mui("#refreshContainer").on('tap', '.live_select_album', function (event) {
            var course_id=$(this).attr("data-id");
            location.href="live_select_album.html?course_id="+course_id;
        });
        mui("#refreshContainer").on('tap', '.live_audio', function (event) {
            var course_id=$(this).attr("data-id");
            location.href="live_audio.html?my_study="+course_id;
        });
        //点击播放视频后才开始记录播放历史
        //直播中
        mui("#refreshContainer").on('tap', '.paly_ing_all', function (event) {
            var courseId=$(this).attr("data-ppd");
            //更新下观看记录
            requestService("/xczh/history/add",{courseId:courseId,recordType:2},function(data) {
                console.log();
            })
            location.href="details.html?courseId="+courseId
        });
        mui("#refreshContainer").on('tap', '.live_play', function (event) {
            var course_id=$(this).attr("data-id");
            location.href="live_play.html?my_study="+course_id;
        });
        mui("#refreshContainer").on('tap', '.live_class', function (event) {
            var course_id=$(this).attr("data-id");
            location.href="live_class.html?my_study="+course_id;
        });
        //即将直播
        mui("#refreshContainer").on('tap', '.paly_ing_all_now', function (event) {
            var courseId_now=$(this).attr("data-ppdnow");
            location.href="/xcview/html/live_play.html?my_study="+courseId_now;
        });

    })
}


var num = 1;
/*下拉刷新的回调 */
function downCallback(){
    num = 1;
    //联网加载数据
    completeCourseList(num,10,'down');
}
/*上拉加载的回调  */
function upCallback(){
    num++;
    completeCourseList(num,10,'up');
}

mui.init();
mui.init({
    pullRefresh: {
        container: '#refreshContainer',
        down: {
            callback: pulldownRefresh
        },
        up: {
            contentrefresh: '正在加载...',
            callback: pullupRefresh
        }
    }
});

/**
 * 下拉刷新
 */
function pulldownRefresh() {
    num = 1;
    setTimeout(function() {
        completeCourseList(num,10,'down');
        mui('#refreshContainer').pullRefresh().endPulldownToRefresh(); //refresh completed
    }, 500);

};
var count = 0;
/**
 * 上拉加载具体业务实现
 */
function pullupRefresh() {
    num++;
    setTimeout(function() {
        completeCourseList(num,10,'up');
    }, 500);
}