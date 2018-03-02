var mescroll;
var freeCourseNum;
$(function(){

    requestService("/xczh/manager/freeCourseList",{
        pageNumber:1,
        pageSize:10000000
    },function(data) {
        if(data.success==true){
            freeCourseNum = data.resultObject.records.length;
            freeCourseNum = parseInt((freeCourseNum + 10 - 1) / 10);
        }
    })
    /*mescroll = new MeScroll("mescroll", {
        down: {
            auto: false, //是否在初始化完毕之后自动执行下拉回调callback; 默认true
            callback: downCallback //下拉刷新的回调
        },
        up: {
            auto: false, //是否在初始化时以上拉加载的方式自动加载第一页数据; 默认false
            isBounce: false, //此处禁止ios回弹,解析(务必认真阅读,特别是最后一点): http://www.mescroll.com/qa.html#q10
            callback: upCallback, //上拉回调,此处可简写; 相当于 callback: function (page) { upCallback(page); }
            toTop:{ //配置回到顶部按钮
                src : "../images/mescroll-totop.png", //默认滚动到1000px显示,可配置offset修改
                offset : 1000
            }
        }
    });*/
    getBoughtList(1,10,'down')
},false);

mui("#refreshContainer").on('tap', '.bought_main_list', function (event) {
	
	alert("==========");
	//pageNumber
//	sessionStorage.setItem("live_pageNumber", pageNumber); 
//	
//	var ev = this;
//	var lecturerId = ev.alt;
//	location.href = "/xcviews/html/personage.html?lecturerId="+lecturerId;
});

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
                mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);
                mui('#refreshContainer').pullRefresh().refresh(true);
            }else {
                $(".bought_main").append(template('bought_main',{items:data.resultObject.records}));
                var backData = data.resultObject.records;
                //mescroll.endSuccess(backData.length);
                var hasNext=true;
                if(pageNumber>=freeCourseNum){
                    mui('#refreshContainer').pullRefresh().endPullupToRefresh(true);
                }

               // mescroll.endByPage(backData.length, freeCourseNum);
            }

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



