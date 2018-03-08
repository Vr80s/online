var mescroll;
$(function(){
    //滑动刷新
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
                offset : 1000,
                warpClass : "mescroll-totop" ,
                showClass : "mescroll-fade-in" ,
                hideClass : "mescroll-fade-out",
                htmlLoading : '<p class="upwarp-progress mescroll-rotate"></p><p class="upwarp-tip">加载中..</p>'
            }
        }
    });*/

//		点击人民币提现   出现添加银行卡提示             withdraws.html--提现页   点击提现之前已经添加过银行卡直接到提现页
    $(".balance .div02").click(function(){
        requestService("/xczh/medical/userBankList",{
        },function(data) {
            if(data.success==true){
                if(data.resultObject.length>0){
                    window.location.href="withdraws.html";
                }else {
                    $(".bank_card").show();
                }
            }else{
                alert(data.errorMessage);
            }
        });

    });

    $(".bank_card_main_fixed .div0").click(function(){
        $(".bank_card").hide();
    });
    $(".bank_card_main_fixed .div1").click(function(){
        $(".bank_card").hide();
    });

    balance();
    transactionRecord(1,10,'down');

});

//人民币/熊猫币余额
function balance() {
    requestService("/xczh/manager/getWalletEnchashmentBalance",{
    },function(data) {
        if(data.success==true){
            $("#xmbNumber").text(parseInt(data.resultObject));
        }else{
            alert(data.errorMessage);
        }
    });
}
//交易记录
function transactionRecord(pageNumber,pageSize,downOrUp) {
    requestService("/xczh/manager/wallet",{
        pageNumber:pageNumber,
        pageSize:pageSize
    },function(data) {
        if(data.success==true){

            //	判断是刷新还是加载
            if(downOrUp=='down'){
                if(data.resultObject.length=='' || data.resultObject.length==0){
                    $(".no_deal").show()
                }
              
                $(".record_main_div").html(template('record_main_div',{items:data.resultObject}));
               /* mescroll.endSuccess();
                mescroll.lockUpScroll( false );
                mescroll.optUp.hasNext=true;*/
                mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);
                mui('#refreshContainer').pullRefresh().refresh(true);
            }else if(data.resultObject.length==0){
                mui('#refreshContainer').pullRefresh().endPullupToRefresh(true);

                /*var backData = data.resultObject;
                mescroll.endSuccess(backData.length);*/
                //mescroll.endBySize(backData.length, criticizeNum);
            }else {
                $(".record_main_div").append(template('record_main_div',{items:data.resultObject}));
                mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);
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
    transactionRecord(num,10,'down');
}
/*上拉加载的回调  */
function upCallback(){
    num++;
    transactionRecord(num,10,'up');
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
        transactionRecord(num,10,'down');
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
        transactionRecord(num,10,'up');
    }, 500);
}