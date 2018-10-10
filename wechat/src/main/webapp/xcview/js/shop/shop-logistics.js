var sn = getQueryString("orderSn");

$(function() {
    orderDetails();

    requestGetService("/xczh/shop/order/shipping", {
        sn: '201809201010304'
    }, function (data) {
        if(data.success ){
            var shippingId = data.resultObject.id;
            if(shippingId != null){
                $(".kuaidiName").html("本商品由【"+data.resultObject.deliveryCorp+"快递】承运");
                transitStep(shippingId);
            }
        }
    });

});

function transitStep(shippingId) {
    requestGetService("/xczh/shop/order/transitStep", {
        shippingId: shippingId
    }, function (data) {
        if(data.success ){
            $(".logistics_address_ul").html(template('logistics_address_ul', {items: data.resultObject.transitSteps}));
        }
    });
}

//订单详情
function orderDetails() {
    requestGetService("/xczh/shop/order/detail", {
        sn: '201809281011313'
    }, function (data) {
        if(data.success ){
            var obj =  data.resultObject;
            order_Id=data.resultObject.sn;
            $(".orderSn").html("运单号  "+order_Id);
            $(".orderInfo").html(template('orderInfo',obj));
            $(".orderImg").attr('src',data.resultObject.orderItems[0].thumbnail);

        }
    });

}