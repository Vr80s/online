var data_sn="";
$(function() {

    $(".affirm").click(function(){
        $(".cancel_order").each(function(){
            // var postage = parseFloat($(".express_price").find("span").text()); //获取邮费
        })
        $(".removeitem").show();
        $(this).parent().parent().parent().parent(".main").remove();
    });

    orderList(1,"down");
});

function orderList(pageNumber,downOrUp) {
    requestGetService("/xczh/shop/order/list", {
        pageNumber: pageNumber,
        pageSize:10
    }, function (data) {
        if(data.success ){
            var obj =  data.resultObject;
            if(downOrUp=='down'){
                $(".indent").html(template('order_list',{items:obj}));
                miniRefresh.endDownLoading(true);// 结束下拉刷新

            } else if(obj.length==0){
                miniRefresh.endUpLoading(true);// 结束上拉加载
            } else {
                $(".indent").append(template('order_list',{items:obj}));
                miniRefresh.endUpLoading(false);
            }

            // 点击取消订单提示
            $(".cancel_order").off("click");
            $(".cancel_order").click(function(){
                data_sn = $(this).attr('data-sn');
                $(".removeitem").show();
            });
            // 点击取消隐藏奇效订单提示
            $(".countermand").off("click");
            $(".countermand").click(function(){
                $(".removeitem").hide();
            });
        }
    });
}

//取消订单
function cancelOrder() {
    requestPostService("/xczh/shop/order/cancel", {
        sn: data_sn
    }, function (data) {
        if(data.success ){
            orderList(1,"down");
        }
    });
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
            page = 1;
            orderList(page,'down');
        }
    },
    up: {
        isAuto: false,
        callback: function () {
            page++;
            orderList(page,'up');
        }
    }
});