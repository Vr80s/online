$(function(){
    completeCourseList(1,8,"down");  /*1页数，8几个*/
})

//获取已结束课程
function completeCourseList(pageNumber,pageSize,downOrUp) {
    requestGetService("/xczh/enrol/enrollmentRegulations",{
        page:pageNumber,
        size:pageSize
    },function(data) {
        if(data.success==true){
            //  判断是下拉刷新还是上拉加载
            if(downOrUp=='down'){
                    //也可以判断有无评论显示默认图片
                if(data.resultObject.records.length==0){
                     // 没有数据默认点击后下载app
                    var tokenStr = "";
                    if(data.token!=null&&data.appUniqueId!=null){
                        tokenStr = "&token="+data.token+"&appUniqueId="+data.appUniqueId;
                    }
                }
                var enrollmentRegulationsList = data.resultObject.records;
            for(var i = 0;i < enrollmentRegulationsList.length;i++){
                enrollmentRegulationsList[i].tokenStr = tokenStr;
            }
            $(".apprentice_list").html(template('apprentice_list_tmp',{items:enrollmentRegulationsList}))
                mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);
                mui('#refreshContainer').pullRefresh().refresh(true);
                mui("#refreshContainer").off();
            }else if(data.resultObject.records.length==0){
                mui('#refreshContainer').pullRefresh().endPullupToRefresh(true);
                mui("#refreshContainer").off();
            }else{
                var enrollmentRegulationsList = data.resultObject.records;
            for(var i = 0;i < enrollmentRegulationsList.length;i++){
                enrollmentRegulationsList[i].tokenStr = tokenStr;
            }
            $(".apprentice_list").append(template('apprentice_list_tmp',{items:enrollmentRegulationsList}));
                mui("#refreshContainer").off();
                mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);
            }
        }else{
            alert(data.errorMessage);
        }

        mui("#refreshContainer").on('tap','.onclick_li',function(){
                location.href ='/xcview/html/down_load.html'
        });

        mui("#refreshContainer").on('tap','.onclick_lis',function(){
            alert(4);
            var id=$(this).attr("data-ids");
            var tokenStr=$(this).attr("data-tokenStr");
            location.href ='inherited_introduction.html?merId='+id+tokenStr;
        });
        
        mui("#refreshContainer").on('tap', '.live_select_album', function (event) {
            var course_id=$(this).attr("data-id");
            location.href="live_select_album.html?course_id="+course_id;
        });   
    })
}


var num = 1;
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
        completeCourseList(num,8,'down');
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
        completeCourseList(num,8,'up');
    }, 500);
}