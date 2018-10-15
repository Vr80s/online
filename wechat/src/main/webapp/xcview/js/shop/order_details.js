var data_sn="";
var data_id="";
var sn = getQueryString("sn");
var order_Id;
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
		location.href="/xcview/html/shop/shop-commentary.html?sn="+order_Id;
	})
});

//订单详情
function orderDetails() {
    requestGetService("/xczh/shop/order/detail", {
        sn: sn
    }, function (data) {
        if(data.success ){
            var obj =  data.resultObject;
            order_Id=data.resultObject.sn;
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
            
//          商品价格
            var v = $(".price_yuan .yuan span").html();
			if(/^\d+$/.test(v)){
			v = v + ".00";
			}else if(/^(\d+\.)(\d+)$/.test(v)){
				var i = RegExp.$1;
				var t = RegExp.$2;
				if(t.length == 1)
				v = v + "0";
				else if(t.length > 2)
				v = i + t.substring(0,2);
			}
		//	alert(v);
			$(".price_yuan .yuan span").html(v);
			
//			总价
			var b = $(".total_price .yuan span").html();
			if(/^\d+$/.test(b)){
			b = b + ".00";
			}else if(/^(\d+\.)(\d+)$/.test(b)){
				var i = RegExp.$1;
				var t = RegExp.$2;
				if(t.length == 1)
				b = b + "0";
				else if(t.length > 2)
				b = i + t.substring(0,2);
			}
		//	alert(v);
			$(".total_price .yuan span").html(b);
			
//		运费
			var f = $(".freight .yuan span").html();
			if(/^\d+$/.test(f)){
			f = f + ".00";
			}else if(/^(\d+\.)(\d+)$/.test(f)){
				var i = RegExp.$1;
				var t = RegExp.$2;
				if(t.length == 1)
				f = f + "0";
				else if(t.length > 2)
				f = i + t.substring(0,2);
			}
		//	alert(v);
			$(".freight .yuan span").html(f);
			
//		需付款
			var p = $(".PAYG .yuans span").html();
			if(/^\d+$/.test(p)){
			p = p + ".00";
			}else if(/^(\d+\.)(\d+)$/.test(p)){
				var i = RegExp.$1;
				var t = RegExp.$2;
				if(t.length == 1)
				p = p + "0";
				else if(t.length > 2)
				p = i + t.substring(0,2);
			}
		//	alert(v);
			$(".PAYG .yuans span").html(p);
			
			
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
        sn: sn
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
            orderDetails();
        }else{
            jqtoast(data.errorMessage);
        }
    });
}
//跳转到物流信息页面
function getTransitSteps(orderSn) {
    location.href = "/xcview/html/shop/shop-logistics.html?orderSn=" + orderSn;
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
            for(var i=0;i<orderItems.length;i++){
                var skuId = orderItems[i].sku.id;
                var quantity = orderItems[i].quantity;
                addCart(skuId,quantity);
            }
            location.href="/xcview/html/shop/shopping_trolley.html";
        }
    });
}

//再次购买
function addCart(skuId,quantity) {
    requestPostService("/xczh/shop/cart", {
        skuId: skuId,
        quantity:quantity
    }, function (data) {
        if(data.success ){

        }
    },false);
}