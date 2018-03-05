var mescroll;
var freeCourseNum;
$(function(){


    getBoughtList(1,10,'down')
},false);



//获取已购课程
function getBoughtList(pageNumber,pageSize,downOrUp) {
    requestService("/xczh/manager/freeCourseList",{
        pageNumber:pageNumber,
        pageSize:pageSize
    },function(data) {
        if(data.success==true){
            if(downOrUp=='down'){
                if(data.resultObject.records.length==0 || data.resultObject.records.length==''){
                    $(".bought").hide();
                    $(".no_class").show();
                }else{
                    $(".no_class").hide();
                    $(".bought").css({"padding-top":"0.3rem"});

                }

                $(".bought_main").html(template('bought_main',{items:data.resultObject.records}));
                /*mescroll.endSuccess();
                mescroll.lockUpScroll( false );
                mescroll.optUp.hasNext=true;*/
                mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);//是否还有更多数据；若还有更多数据，则传入false; 否则传入true
                mui('#refreshContainer').pullRefresh().refresh(true);
            }else if(data.resultObject.records.length==0 || data.resultObject.records.length==''){
                mui('#refreshContainer').pullRefresh().endPullupToRefresh(true);
            }else{
                $(".bought_main").append(template('bought_main',{items:data.resultObject.records}));
                var backData = data.resultObject.records;
                //mescroll.endSuccess(backData.length);
                mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);

            }
            
//          var Ids =  item.id;
            mui("#refreshContainer").on('tap', '.div1', function (event) {
				var itemId=$(this).attr("data-jump");
				location.href = "live_select_album.html?course_id="+itemId;
			});
			
			mui("#refreshContainer").on('tap', '.div2', function (event) {
				var itemId2=$(this).attr("data-jump");
	
				location.href = "live_audio.html?my_study="+itemId2;
			});
			
			mui("#refreshContainer").on('tap', '.div3', function (event) {
				var itemId3=$(this).attr("data-jump");
	
				location.href = "live_play.html?my_study="+itemId3;
			});
			
			mui("#refreshContainer").on('tap', '.div4', function (event) {
				var itemId4=$(this).attr("data-jump");
	
				location.href = "live_play.html?my_study="+itemId4;
			});
			
			mui("#refreshContainer").on('tap', '.div5', function (event) {
				var itemId5=$(this).attr("data-jump");
	
				location.href = "live_play.html?my_study="+itemId5;
			});
			mui("#refreshContainer").on('tap', '.div6', function (event) {
				var itemId6=$(this).attr("data-jump");
	
				location.href = "live_class.html?my_study="+itemId6;
			});
			
			
            
            

        }else{
            alert(data.errorMessage);
        }
    });
}

var num = 1;
/*下拉刷新的回调 */
function downCallback(){
    num = 1;
    //联网加载数据
    getBoughtList(num,10,'down');
}
/*上拉加载的回调  */
function upCallback(){
    num++;
    getBoughtList(num,10,'up');
}

/**
 * ************************************ 页面刷新下刷新事件
 * **************************************************
 */
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
/*mui('.mui-scroll-wrapper').scroll({
	 deceleration: 0.0005, //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
	 indicators: false //是否显示滚动条
});
*/
/**
 * 下拉刷新
 */
function pulldownRefresh() {
    num = 1;
    setTimeout(function() {
        getBoughtList(num,10,'down');
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
        getBoughtList(num,10,'up');
    }, 500);
}



