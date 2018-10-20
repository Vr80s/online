/**
 * 
 */
 
 
//1.选择商品放入购物车，2.选择商品立即购买.默认立即购买
var shoppingFlag = 2;
// 点击加入购物车
$('.add_cart').click(function(){
    shoppingFlag = 1;
    // emptySpecification();
    defaultSpecification();
    tokenCheck();
    
//  $('.shopping_trolley').show();
});
// 点击立即购买
$('.buy_now').click(function(){
	debugger
    shoppingFlag = 2;
    // emptySpecification();
    defaultSpecification();
    tokenCheck();
    
//  $('.shopping_trolley').show();
});

function tokenCheck(){
	requestPostService("/xczh/myinfo/myFocus",null,function (data) {
	    if (data.success == true) {
	       	$('.shopping_trolley').show(); 
	    }
	});
}


// 点击数量加减
$('.spinnerExample').spinner({});

 //清空选中的项
function emptySpecification(){
   
	$(".choice").html("选择");
	
	if(specificationsResutl!=null && specificationsResutl.length>0){
		currentSku = null;
    	$(".shopping_trolley_center .determine").css("background","#aaaaaa");
	}
	$('.specifications_ul .casing').removeClass('public');
    $(".specification").removeClass("showfalg");
    $(".specification").addClass("hide");
    $(".specification_name").removeClass("hide");
}

function defaultSpecification(){
	if($(".casing").hasClass("public"))return;
	var skuList = productDetailsObj.skuVOs;
    for(var i=0;i < skuList.length;i++){
    	if(skuList[i].stock > 0){
    		for(var j=0;j<skuList[i].specificationValueIds.length;j++){
                $(".casing-"+skuList[i].specificationValueIds[j]).click();
            }
            break;
		}
	}
}

//选择商品后，点击确定
$('.determine').click(function () {
    var skuId = 10001;
    var quantity = 1;
    var flag = false;
    
    //当前库存不足
    if(currentSku==null || currentSku.isOutOfStock){
    	return;
    }
    //库存id
    skuId =  currentSku.id;
    //数量
    quantity = $(".spinnerExample").val();
    
    requestGetService("/xczh/shop/checkSku?skuId="+skuId+"&quantity="+quantity,null,function(data){
		if(data.success){
			flag = true;
		}else{
			jqtoast(data.errorMessage);
		}
	},false);
    if(!flag){
        return;
	}
	if(shoppingFlag == 1){
	    requestPostService("/xczh/shop/cart", {'skuId': skuId, 'quantity' : quantity}, function(data){
			if (data.success) {
				$('.shopping_trolley').hide();

				var cartItemNumber  = data.resultObject;
//				var cartQuantity = $('.shopping_quantity').val();
//				if (!cartQuantity) {
//				    cartQuantity = quantity;
//				} else {
//				    cartQuantity = parseInt(cartQuantity) + quantity;
//				}
//				var preNumber = $(".shopping_quantity").html();
//				totalQuantity = Number(cartQuantity) + Number(preNumber);
				if(parseInt(cartItemNumber)>0){
					$(".shopping_quantity").show();
				}
                $('.shopping_quantity').html(cartItemNumber);
                jqtoast("已加入购物车");
			}else{
				jqtoast(data.errorMessage);
			}
		});
	}else{
		//确认下单逻辑
		
		window.location = "/xcview/html/shop/confirm_order.html?skuId="+skuId+"&quantity="+quantity+"&productId="+productId;
	}
});

// 点击详情背景色
$('.shopping_trolley_bg').click(function(){
	$('.shopping_trolley').hide();
});
// 点击关闭按钮
$('.close').click(function(){
	$('.shopping_trolley').hide();
});

// 点击客服
$(".service").click(function(){
	jqtoast("熊猫客服休息中，稍后报道！");
});
// 点击头部区域客服消息  
$(".advices").click(function(){
	jqtoast("熊猫客服休息中，稍后报道！");
});

// 点击分享提示显示
$(".share_class").click(function(){
	$(".weixin_ceng").show();
});
// 点击分享提示隐藏
$(".weixin_ceng").click(function(){
	$(".weixin_ceng").hide();
});


// 点击加号
$('.increase').on('click',function (e) {
    var count = parseInt($('.spinnerExample').val());
    if (count > currentSku.availableStock) {
        jqtoast('商品库存不足');
        if (count > 1) {
            $('.spinnerExample').val(count - 1);
        }
        return false;
    }
});

setTimeout(function(){
    	$(".it_over").show();
}, 2000);








