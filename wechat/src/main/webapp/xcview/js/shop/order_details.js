$(function() {


    orderDetails();

});


function orderDetails() {
    requestGetService("/xczh/shop/order/detail", {
        sn: '201809281011313'
    }, function (data) {
        if(data.success ){
            var obj =  data.resultObject;
                //$(".indent").html(template('order_list',{items:obj}));

        }
    });

}


