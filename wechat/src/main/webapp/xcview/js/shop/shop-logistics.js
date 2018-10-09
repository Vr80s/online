
requestGetService("/xczh/shop/order/transitStep", {
    shippingId: 10102
}, function (data) {
    if(data.success ){
    	$(".logistics_address_ul").html(template('logistics_address_ul', {items: data.resultObject.transitSteps}));
    }
});