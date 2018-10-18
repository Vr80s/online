/*setInterval(function(){
	alert(111);
	createReceiverInfo(receiver);
},1000);*/


	    
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
                window.location="/xcview/html/shop/method.html?orderSns="+orderSns+"&type=2";
            }else{
//              jqtoast(data.errorMessage);
                jqtoast("请填写收货地址");
            }
        });
    });
    getOrderList();
    getDefaultReceiver();
    
//  跳转修改地址
	$(".select_address_main").click(function(){
		window.localStorage.isAddress="details";
		location.href="/xcview/html/address.html?&type=2";
	})

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


function setReload(){
//  window.location.reload();
location.replace(document.referrer);
}

var u = navigator.userAgent, app = navigator.appVersion;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //g
var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
if (isAndroid) {

}
if (isIOS) {
    setTimeout(function(){
			
			createReceiverInfo(receiver);	
	},100);
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
    params += "&shippingMethodId=1"
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
            '<img src="'+ orders[i].doctor.avatar +'" alt="" class="head_portrait" />' +
            '<span>'+orders[i].doctor.name +'</span>'+
            '</div>' +
            '<div class="main_product_details">';
        for(var j=0; j < orders[i].orderItems.length; j++){
            str += '<div class="product_details">' +
                '<img src="'+orders[i].orderItems[j].thumbnail+'" alt="" class="surface_plot" />' +
                '<div class="product_details_center">' +
                '<div class="title">' + orders[i].orderItems[j].name + '</div>' +
                '<div class="pack">' +
                orders[i].orderItems[j].specifications.join(";") +
                '(库存'+orders[i].orderItems[j].sku.availableStock+'件)' +
                '</div>' +
                '<div class="total_prices">' +
                '<div class="price_yuan" >' +
                '<div class="yuan">￥<span>'+orders[i].orderItems[j].price+'</span></div>' +
                '<div class="number">x<span>'+orders[i].orderItems[j].quantity+'</span></div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>';
        }
        str += '</div>' +
            '<div class="zongjia"></div>' +
            '<div class="express">' +
            '<div class="freight">运费</div>' +
            '<div class="express_price">￥<span>'+orders[i].freight+'</span></div>' +
            '</div>' +
            '<div class="BBS">' +
            '<div class="message">买家留言：<input class="memo" store-id="'+orders[i].store.id+'" type="text" placeholder="给商家留言" /></div>' +
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




function returns() {

      var userId = localStorage.userId;
      if (isNotBlank(userId)) {
          /*
           * 判断这上个地址是否来自我们的浏览器啦。如果是的就返回上一页，如果不是的话，那么就返回首页吧。
           */
          var before_address = document.referrer;
          if (before_address.indexOf("page/index") != -1 ||
              before_address.indexOf("shopping_trolley.html") != -1 ||   //购物车
              before_address.indexOf("order_center.html") != -1 ||   //好货订单--订单列表
              before_address.indexOf("line_item.html") != -1) {  //订单详情

              window.history.back();
          } else {
              window.history.go(-1);
          }
      } else {
          //登录页面
          location.href = "home_page.html";
          
      }
}
/*function returns() {
	history.back();
}*/

var goHistorys = function(url){
    if(url != null){
        location.href = url;
    }else{
        history.go(-2);
    }
}