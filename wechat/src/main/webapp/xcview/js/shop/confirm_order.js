$(function() {
    //计算
    var oprice = 0; //总价
    var opricePostage = 0 //合计

    $(".price_yuan").each(function(){
        var num = parseFloat($(this).find(".number").find("span").text()); //得到商品的数量
        // console.log(num);
        var price = parseFloat($(this).find(".yuan").find("span").text()); //得到商品的单价
        // console.log(price);
        
        var total = price * num; //计算单个商品的总价
        // console.log(total);
        oprice += total; //计算该店铺的总价
        // console.log(oprice);
    });

    $(".indent_list").each(function(){
        // 合计
        var postage = parseFloat($(".express_price").find("span").text()); //获取邮费
        opricePostage += postage; //计算邮费总价
    })
    //合计 = 获取邮费 + 计算该店铺的总价
    var summation = opricePostage + oprice;

    $(".summation .yuan span").html(summation.toFixed(2));  //底部合计
    $(".totals_yuan span").html(oprice.toFixed(2));         //商品总价格

    $(".submit_order").click(function(){
        var skuId = 10001;
        var quantity = 1;
        var receiverId = 10210;
        var shippingMethodId = 1;
        var memo = "拒收到付";
        requestService("/xczh/shop/order/create","skuId="+skuId+"&quantity="+quantity+"&shippingMethodId="+shippingMethodId+"&receiverId="+receiverId+"&memo="+memo,function(data){
            if(data.success){
                var orderSns = data.resultObject.orderSns;
                window.location="http://localhost:8282/order/payment?orderSns="+orderSns;
            }else{
                alert(data.errorMessage);
            }
        },false);
    });
});





