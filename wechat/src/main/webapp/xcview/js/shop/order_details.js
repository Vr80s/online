$(function() {
    var sn = getQueryString("sn");

    orderDetails();

});


function orderDetails() {
    requestGetService("/xczh/shop/order/detail", {
        sn: '201809281011313'
    }, function (data) {
        if(data.success ){
            var obj =  data.resultObject;
                $(".orderDetails").html(template('order_details',obj));

        }
    });

}


