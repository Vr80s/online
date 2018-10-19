var data_sn="";
var data_id="";
var isTrue =true;
$(function() {

    /*$(".affirm").click(function(){
        $(".cancel_order").each(function(){
            // var postage = parseFloat($(".express_price").find("span").text()); //获取邮费
        })
        $(".removeitem").show();
        $(this).parent().parent().parent().parent(".main").remove();
    });*/

    orderList(1,"down");
});

function payment(orderSns) {
    location.href = "/xcview/html/shop/method.html?orderSns=" + orderSns;
}
function getTransitSteps(orderSn,orderId) {
    location.href = "/xcview/html/shop/shop-logistics.html?orderSn=" + orderSn+"&orderId="+orderId;
}



function orderList(pageNumber,downOrUp) {
    requestGetService("/xczh/shop/order/list", {
        pageNumber: pageNumber,
        pageSize:10
    }, function (data) {
        if(data.success ){
            var obj =  data.resultObject;
            var recommendsHide = $(".indent").html();
			if (recommendsHide==null || recommendsHide=="") {
				$("#minirefresh").hide();
			    $(".quie_pic").show();
			    $("body").css("background","#fff");
			};
			
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
                data_id = $(this).attr('data-id');
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
            
            //		点击确认收货
            $(".confirmOrder").off("click");
			$(".confirmOrder").click(function(){
				data_sn = $(this).attr("data-sn");
				$(".confirm_receiptt").show();
			});
            $(".countermandDelete1").off("click");
			$(".countermandDelete1").click(function(){
				$(".confirm_receiptt").hide();
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
            $(".cancelOrder").hide();
            orderList(1,"down");
        }else{
            jqtoast(data.errorMessage);
        }
    });
}

//删除订单
function deleteOrder() {
    requestPostService("/xczh/shop/order/delete", {
        orderId: data_id
    }, function (data) {
        if(data.success ){
            $(".deleteOrder").hide();
            orderList(1,"down");
        }else{
            jqtoast(data.errorMessage);
        }
    });
}

//确认收货
function confirmReceipt() {
    requestPostService("/xczh/shop/order/receive", {
        sn: data_sn
    }, function (data) {
        if(data.success ){
            $(".confirm_receiptt").hide();
            orderList(1,"down");
        }else{
        	jqtoast(data.errorMessage);
        }
    });
}

//再次购买
function againBuy(orderSn) {
    requestGetService("/xczh/shop/order/detail", {
        sn: orderSn
    }, function (data) {
        if(data.success ){
            var orderItems=data.resultObject.orderItems;
            var skuIds =[];
            for(var i=0;i<orderItems.length;i++){
                var skuId = orderItems[i].sku.id;
                var product = orderItems[i].sku.product;
                if(!product.isMarketable){
                    isTrue =false;
                    jqtoast("商品已下架");
                    break;
                } else if(!product.isActive){
                    isTrue =false;
                    jqtoast("商品已失效");
                    break;
                }
                skuIds.push(skuId);
                var quantity = orderItems[i].quantity;
                if(isTrue){
                    addCart(skuId,quantity)
                } else {
                    break;
                }
            }
            if(isTrue){
                requestGetService("/xczh/shop/cart", null, function (data) {
                    if (data.success) {
                        var ids = [];
                        for(var j=0;j<data.resultObject.storeCartItems.length;j++){
                            var obj = data.resultObject.storeCartItems[j];
                            for(var i=0;i<obj.cartItems.length;i++){
                                var sku_id = obj.cartItems[i].sku.id;
                                if(isInArray(skuIds,sku_id)){
                                    ids.push(obj.cartItems[i].id);
                                }
                            }
                        }
                        requestPostService('/xczh/shop/checkSkus', {'cartItemIds': ids.join(',')}, function (resp) {
                            if (!resp.resultObject) {
                                window.location.href = '/xcview/html/shop/confirm_order.html?cartItemIds=' + ids.join(',');
                            } else {
                                jqtoast(resp.resultObject);
                            }
                        });
                    } else {
                        jqtoast(data.errorMessage);
                    }
                });
            }

        }
    });
}

function isInArray(arr,value){
    for(var i = 0; i < arr.length; i++){
        if(value === arr[i]){
            return true;
        }
    }
    return false;
}
//再次购买
function addCart(skuId,quantity) {
    requestPostService("/xczh/shop/cart", {
        skuId: skuId,
        quantity:quantity
    }, function (data) {
        if(data.success ){

        } else {
            jqtoast(data.errorMessage);
            isTrue = false;
        }
    },false);
}

//  点击评价
$(".orderDetails").on('click','.win_evaluate',function(){
	
})
function review(sn){
	location.href="/xcview/html/shop/shop-commentary.html?sn="+sn;
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

/*$(".indent_list").each(function(){
	var list = $(this).find(".express_price").find("span");
});
*/