var lecturerId = getQueryString('lecturerId');
$(function(){

    courseList(1,10,'down')
		requestService("/xczh/host/hostPageCourse",{
			lecturerId: lecturerId,
			pageNumber:1,
			pageSize:1000
		},function(data){
		$("#course_list_main").html(template('data_course_list',{items:data.resultObject.records}))
});

})

//获取已购课程
function courseList(pageNumber,pageSize,downOrUp) {
    requestService("/xczh/host/hostPageCourse",{
        pageNumber:pageNumber,
        pageSize:pageSize,
        lecturerId: lecturerId
    },function(data) {
        if(data.success==true){
            if(downOrUp=='down'){
                if(data.resultObject.length==0 || data.resultObject.length==''){

                }
                $("#course_list_main").html(template('data_course_list',{items:data.resultObject.records}))
                mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);//是否还有更多数据；若还有更多数据，则传入false; 否则传入true
                mui('#refreshContainer').pullRefresh().refresh(true);
                mui("#refreshContainer").off();
            }else if(data.resultObject.records.length==0 || data.resultObject.records.length==''){
                mui('#refreshContainer').pullRefresh().endPullupToRefresh(true);
                mui("#refreshContainer").off();
            }else{
                $(".course_list_main").append(template('data_course_list',{items:data.resultObject.records}));
                mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);
            }
        }else{
            alert(data.errorMessage);
        }

        mui("#refreshContainer").on('tap', '.common_jump_school', function (event) {
            var watchState=$(this).attr("data-watchState");
            var type=$(this).attr("data-type");
            var collection=$(this).attr("data-collection");
            var course_id=$(this).attr("data-id");
            common_jump_school(watchState,type,collection,course_id);
        });
        mui("#refreshContainer").on('tap', '.common_jump_all', function (event) {
            var course_id=$(this).attr("data-id");
            common_jump_all(course_id);
        });

    })
}



var num = 1;
/*下拉刷新的回调 */
function downCallback(){
    num = 1;
    //联网加载数据
    courseList(num,10,'down');
}
/*上拉加载的回调  */
function upCallback(){
    num++;
    courseList(num,10,'up');
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
        courseList(num,10,'down');
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
        courseList(num,10,'up');
    }, 500);
}


/**
 * 视频/音频/专辑  --》 跳转
 * @param watchState
 * @param type
 * @param collection
 * @param courseId
 * @returns
 */
function common_jump_school(watchState,type,collection,courseId){

    var falg =authenticationCooKie();
    if(watchState==1){
        //增加学习记录
        requestService("/xczh/history/add",{courseId:courseId, recordType:1},function(data) {
            console.log("增加学习记录");
        })

        if(type == 1 || type == 2){
            if(collection==1){
                if (falg==1002){
                    location.href ="/xcview/html/cn_login.html";
                }else if (falg==1005) {
                    location.href ="/xcview/html/evpi.html";
                }else{
                    location.href = "/xcview/html/live_select_album.html?course_id="+courseId;
                }
            }else{
                location.href = "/xcview/html/live_audio.html?my_study="+courseId;
            }
        }else if(type == 4){
            location.href = "/xcview/html/school_class.html?course_id="+courseId;
        }
    }else{
        if(type == 1 || type == 2){
            location.href = "/xcview/html/school_audio.html?course_id="+courseId+"&type="+2;
        }else if(type == 4){
            location.href = "/xcview/html/school_class.html?course_id="+courseId+"&type="+3;
        }
    }
}

/**
 * 直播、视频、音频、专辑跳转
 * @param id
 * @returns
 */
function common_jump_all(courseId){
    requestService("/xczh/course/userCurrentCourseStatus?courseId="+courseId,null,function(data) {

        var userPlay=data.resultObject;
        var falg =authenticationCooKie();

        var watchState = userPlay.watchState;
        var type = userPlay.type;
        var collection = userPlay.collection;
        var lineState = userPlay.lineState;

        if(watchState==1 || watchState==2){
            if(type == 1 || type == 2){
                //增加学习记录
                requestService("/xczh/history/add",{courseId:courseId, recordType:1},function(data) {
                    console.log("增加学习记录");
                })
                if(collection==1){
                    location.href = "/xcview/html/live_select_album.html?course_id="+courseId;
                }else{
                    location.href = "/xcview/html/live_audio.html?my_study="+courseId;
                }
            }else if(type == 3){

                common_jump_play(courseId,watchState,lineState);
            }else if(type == 4){
                location.href = "/xcview/html/school_class.html?course_id="+courseId;
            }
        }else {
            if(type == 1 || type == 2){
                location.href = "/xcview/html/school_audio.html?course_id="+courseId;
            }else if(type == 3){
                common_jump_play(courseId,watchState,lineState);
            }else if(type == 4){
                location.href = "/xcview/html/school_class.html?course_id="+courseId;
            }
        }
    })
}