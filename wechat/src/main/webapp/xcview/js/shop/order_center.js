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

function payment(orderSns) {
    location.href = "/xcview/html/shop/method.html?orderSns=" + orderSns;
}

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
                $(".cancelOrder").show();
            });
            // 点击取消隐藏奇效订单提示
            $(".countermandCancel").off("click");
            $(".countermandCancel").click(function(){
                $(".cancelOrder").hide();
            });
            // 点击删除订单提示
            $(".delete_order").off("click");
            $(".delete_order").click(function(){
                data_sn = $(this).attr('data-sn');
                $(".deleteOrder").show();
            });
            // 点击取消隐藏奇效订单提示
            $(".countermandDelete").off("click");
            $(".countermandDelete").click(function(){
                $(".deleteOrder").hide();
            });

            // 点击订单跳转
            $(".main_product_details").off("click");
            $(".main_product_details").click(function(){
                var sn  = $(this).attr('data-sn');
                location.href="/xcview/html/shop/line_item.html?sn="+sn;
                $(".deleteOrder").hide();
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

//删除订单
function deleteOrder() {
    requestPostService("/xczh/shop/order/delete", {
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