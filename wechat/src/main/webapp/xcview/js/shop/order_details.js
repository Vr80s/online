var data_sn="";
var data_id="";
var sn = getQueryString("sn");
var order_Id;
var isTrue =true;
$(function() {
	// 点击头部区域客服消息  
	$(".advices").click(function(){
		jqtoast("熊猫客服休息中，稍后报道！");
	});
	// 点击客服
	/*$("#notescontact").click(function(){
		jqtoast("熊猫客服休息中，稍后报道！");
	});*/
	$(".orderDetails").on('click','#notescontact',function(){
		jqtoast("熊猫客服休息中，稍后报道！");
	})
	
    orderDetails();

//  点击评价
	$(".orderDetails").on('click','.win_evaluate',function(){
		location.href="/xcview/html/shop/shop-commentary.html?sn="+sn;
	})
});





/*$(".").click(function(){
	alert(111);
	location.href = "/xcview/html/shop/method.html?orderSns=" + sn;
});*/


function priceConvert(price) {
    if(/^\d+$/.test(price)){
        return price + ".00";
    }else if(/^(\d+\.)(\d+)$/.test(price)){
        var j = RegExp.$1;
        var t = RegExp.$2;
        if(t.length == 1)
            return price + "0";
        else if(t.length > 2){
            return j + t.substring(0,2);
        }
    }
        return price
}

//订单详情
function orderDetails() {
    requestGetService("/xczh/shop/order/detail", {
        sn: sn
    }, function (data) {
        if(data.success ){
            var obj =  data.resultObject;
            order_Id=data.resultObject.id;
            //		总价
            obj.price = priceConvert(obj.price);
            //		运费
            obj.freight = priceConvert(obj.freight);
            //		需付款
            obj.amount = priceConvert(obj.amount);
            for(var i=0;i<obj.orderItems.length;i++){
                obj.orderItems[i].price = priceConvert(obj.orderItems[i].price);
            }
            $(".orderDetails").html(template('order_details',obj));
            // 点击取消订单提示
            $(".cancel_order").click(function(){
                data_sn = $(this).attr('data-sn');
                $(".cancelOrder").show();
            });
            // 点击取消隐藏奇效订单提示
            $(".countermandCancel").click(function(){
                $(".cancelOrder").hide();
            });
            $(".delete_order").click(function(){
                data_id = $(this).attr('data-id');
                $(".deleteOrder").show();
            });
            // 点击取消隐藏奇效订单提示
            $(".countermandDelete").click(function(){
                $(".deleteOrder").hide();
            });
            getShipping();
            
            $(".waiting_payment").on('click','.immediate_payment',function(){
				location.href = "/xcview/html/shop/method.html?orderSns=" + sn;
			})
        }
    });

}
//获取物流信息
function transitStep(shipping) {
    requestGetService("/xczh/shop/order/transitStep", {
        shippingId: shipping
    }, function (data) {
        if(data.success ){
            var transitSteps = data.resultObject.transitSteps[0];
            $(".logistics_sites").html(transitSteps.context);
            $(".data").html(transitSteps.ftime);
        }
    });
}
//获取shipping信息
function getShipping() {
    requestGetService("/xczh/shop/order/shipping", {
        orderId: order_Id
    }, function (data) {
        if(data.success ){
            var shippingId = data.resultObject.id;
            if(shippingId != null){
                transitStep(shippingId);
            }
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
            orderDetails();
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
            location.href = "/xcview/html/shop/order_center.html" ;
        }else{
            jqtoast(data.errorMessage);
        }
    });
}
//跳转到物流信息页面
function getTransitSteps(orderSn,orderId) {
    location.href = "/xcview/html/shop/shop-logistics.html?orderSn=" + orderSn+"&orderId="+orderId;
}

//确认收货
function confirmReceipt(orderSn) {
    requestPostService("/xczh/shop/order/receive", {
        sn: orderSn
    }, function (data) {
        if(data.success ){
            orderDetails();
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