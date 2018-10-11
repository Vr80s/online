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
        var skuId = getParam("skuId");
        var quantity = getParam("quantity");

        var cartItemIds = getParam("cartItemIds");

        var params;
        if(isNotBlank(cartItemIds)){
            params = "cartItemIds="+cartItemIds;
        }else{
            params = "skuId="+skuId + "&quantity="+quantity;
        }

        var receiverId = $(".receiverId").val();
        var shippingMethodId = 1;
        var memo = "拒收到付";
        var memoJson=[];
        $(".memo").each(function(){
            var mj = {id:$(this).attr("store-id"),memo:$(this).val()};
            memoJson.push(mj);
        });

        requestService("/xczh/shop/order/create",params+"&shippingMethodId="+shippingMethodId+"&receiverId="+receiverId+"&memo="+memo+"&memoJson="+JSON.stringify(memoJson),function(data){
            if(data.success){
                var orderSns = data.resultObject.orderSns.join(',');
                window.location="/xcview/html/shop/method.html?orderSns="+orderSns;
            }else{
//              jqtoast(data.errorMessage);
                jqtoast("请填写收货地址");
            }
        });
    });
    getOrderList();
    getDefaultReceiver();

});

var receiver;
function getDefaultReceiver(){
    requestGetService("/xczh/shop/receiver/list",null,function(data){
        if(data.success){
            var receiverList = data.resultObject;
            if(receiverList.length > 0){
                $(".yes_address").show();
                $(".no_address").hide();
                receiver = receiverList[0];
                for (var i = 0;i < receiverList.length;i++){
                    if(receiverList[i].isDefault){
                        receiver = receiverList[i];
                    }
                }
                createReceiverInfo(receiver);
            }else{
                $(".yes_address").hide();
                $(".no_address").show();
            }
        }else{
            jqtoast(data.errorMessage);
        }
    },false);
}

function createReceiverInfo(receiver){
    $(".receiverId").val(receiver.id);
    $(".consignee").html(receiver.consignee);
    $(".phone").html(receiver.phone);
    $(".areaName").html(receiver.areaName);
    $(".address").html(receiver.address);
}

function getOrderList(){
    var skuId = getParam("skuId");
    var quantity = getParam("quantity");

    var cartItemIds = getParam("cartItemIds");

    var params;
    if(isNotBlank(cartItemIds)){
        params = "cartItemIds="+cartItemIds;
    }else{
        params = "skuId="+skuId + "&quantity="+quantity;
    }
    requestGetService("/xczh/shop/checkout",params,function(data){
        if(data.success){
            creatOrderList(data.resultObject.orders,data.resultObject.price,data.resultObject.freight);
            $(".amountPayable").html(data.resultObject.amountPayable);
        }else{
            jqtoast(data.errorMessage);
        }
    });
}

function creatOrderList(orders,price,freight){
    var str = '';
    for(var i=0;i<orders.length;i++){
        str += '<div class="indent_list">' +
            '<div class="tilte">' +
            '<img src="'+ orders[i].store.logo +'" alt="" class="head_portrait" />' +
            orders[i].store.name +
            '</div>' +
            '<div class="main_product_details">';
        for(var j=0; j < orders[i].orderItems.length; j++){
            str += '<div class="product_details">' +
                '<img src="'+orders[i].orderItems[j].thumbnail+'" alt="" class="surface_plot" />' +
                '<div class="product_details_center">' +
                '<div class="title"><h4>' + orders[i].orderItems[j].name + '</h4></div>' +
                '<div class="pack">' +
                orders[i].orderItems[j].specifications.join(";") +
                '(库存'+orders[i].orderItems[j].sku.stock+'件)' +
                '</div>' +
                '<div class="total_prices">' +
                '<div class="price_yuan" >' +
                '<div class="yuan">￥<span>'+orders[i].orderItems[j].price+'</span></div>' +
                '<div class="number"><strong>x<span>'+orders[i].orderItems[j].quantity+'</span></strong></div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>';
        }
        str += '</div>' +
            '<div class="zongjia"></div>' +
            '<div class="express">' +
            '<div class="freight"><strong>运费</strong></div>' +
            '<div class="express_price"><strong>￥<span>'+orders[i].freight+'</span></strong></div>' +
            '</div>' +
            '<div class="BBS">' +
            '<div class="message"><strong>买家留言：</strong><input class="memo" store-id="'+orders[i].store.id+'" type="text" placeholder="给商家留言" /></div>' +
            '</div>' +
            '</div>';
    }
    str += '<div class="total_price">' +
        '<div class="total_price_main">' +
        '<div class="total last">' +
        '<div class="totals">商品总价</div>' +
        '<div class="totals_yuan">￥<span class="totals_yuan_rmb">'+price+'</span></div>' +
        '</div>' +
        '<div class="carriage last">' +
        '<div class="carriages">运费</div>' +
        '<div class="carriage_yuan">￥<span class="carriage_yuan_rmb">'+freight+'</span></div>' +
        '</div>' +
        '</div>' +
        '</div>';
    $(".indent").html(str);
}


