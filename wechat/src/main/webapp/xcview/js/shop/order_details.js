$(function() {
    var sn = getQueryString("sn");
    var order_Id;
    orderDetails();

	function orderDetails() {
	    requestGetService("/xczh/shop/order/detail", {
	        sn: '201809281011313'
	    }, function (data) {
	        if(data.success ){
	            var obj =  data.resultObject;
	            	order_Id=data.resultObject.sn;
	                $(".orderDetails").html(template('order_details',obj));
                getShipping();

	        }
	    });
	
	}
    
//  点击评价
	$(".orderDetails").on('click','.win_evaluate',function(){
		location.href="/xcview/html/shop/shop-commentary.html?sn="+order_Id;
	})
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
            sn: '201809201010304'
        }, function (data) {
            if(data.success ){
                var shippingId = data.resultObject.id;
                if(shippingId != null){
                    transitStep(shippingId);
				}

            }
        });
    }

});




