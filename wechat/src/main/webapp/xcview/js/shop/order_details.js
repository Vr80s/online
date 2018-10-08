$(function() {
    var sn = getQueryString("sn");
    var order_Id;
    orderDetails();
	function orderDetails() {
	    requestGetService("/xczh/shop/order/detail", {
	        sn: sn
	    }, function (data) {
	        if(data.success ){
	            var obj =  data.resultObject;
	            	order_Id=data.resultObject.sn;
	                $(".orderDetails").html(template('order_details',obj));
	        }
	    });
	
	}
    
//  点击评价
	$(".orderDetails").on('click','.win_evaluate',function(){
		location.href="/xcview/html/shop/shop-commentary.html?orderId="+order_Id;
	})

});




