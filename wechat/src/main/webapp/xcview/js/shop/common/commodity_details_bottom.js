/**
 * 
 */
 
//1.选择商品放入购物车，2.选择商品立即购买.默认立即购买
var shoppingFlag = 2;
// 点击加入购物车
$('.add_cart').click(function(){
    shoppingFlag = 1;
    emptySpecification();
    
    tokenCheck();
    
    
    $('.shopping_trolley').show();
});
// 点击立即购买
$('.buy_now').click(function(){
    shoppingFlag = 2;
    emptySpecification();
    
    tokenCheck();
    
    $('.shopping_trolley').show();
});

function tokenCheck(){
	
	 if(localStorage.getItem("wv") == null){
	    var USER_UN_BIND = 1005;//用户用微信登录的但是没有绑定注册信息
	    var USER_TOKEN_NULL = 1001;//token is null
		var USER_UN_LOGIN = 1002;//未登录
		var USER_TOP = 1003;//被顶掉
		var USER_NORMAL = 1000;
		var USER_WEIXIN_AUTH = 1006;//需要去微信授权
		    // alert(2112111);
		
		    var flag = getFlagStatus();
		
		    if (flag === USER_UN_BIND) {
		        location.href = "/xcview/html/evpi.html";  /*完善信息页面*/
		    }else if (flag === USER_UN_LOGIN){
//			        location.href ='/xcview/html/shop/shopping_trolley.html'
		        location.href = "/xcview/html/enter.html";  /*登录页面*/ 
		    }else if (flag === USER_TOP){
//			        location.href ='/xcview/html/shop/shopping_trolley.html'
		        location.href = "/xcview/html/enter.html";  /*登录页面*/  
		    }else{
//			        location.href ='/xcview/html/shop/shopping_trolley.html'
		        $('.shopping_trolley').show();  
		    }
	}else{
		location.href ='/xcview/html/cn_login.html';/*注册页面*/
	}

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
				var cartQuantity = $('.shopping_quantity').val();
				if (!cartQuantity) {
				    cartQuantity = quantity;
				} else {
				    cartQuantity = parseInt(cartQuantity) + quantity;
				}
				
				var preNumber = $(".shopping_quantity").html();
				totalQuantity = Number(cartQuantity) + Number(preNumber);
				if(totalQuantity>0){
					$(".shopping_quantity").show();
				}
                $('.shopping_quantity').html(totalQuantity);
                jqtoast("已加入购物车");
			}
		});
	}else{
		//确认下单逻辑
		
		window.location = "/xcview/html/shop/confirm_order.html?skuId="+skuId+"&quantity="+quantity+"&productId="+productId;
	}
});


function specificationChoose(obj){

    var skus = obj.skuVOs;
    var specificationIds = [];
    var defaultSkus = {};
    
    for (var i = 0; i < skus.length; i++) {
    	if(skus[i].isDefault){
    		specificationIds = skus[i].specificationValueIds;
    		defaultSkus = skus[i];
    	}
    	if(skus[i].specificationValueIds!=null &&
    		skus[i].specificationValueIds.length>0){
    			
    	   skus[i].specificationIdsStr = skus[i].specificationValueIds.sort().join(',');
    	}
    }
    
      /**
     * 显示库存是否充足
     * 	当前价格 
     */
    if(defaultSkus!=null){
    	
    	if(defaultSkus.isOutOfStock){
    		 $(".information .repertory").html("该商品库存不足");
    		 $(".shopping_trolley_center .determine").css("background","#aaaaaa");
    	}else{
    		 $(".information .repertory").html("库存"+defaultSkus.availableStock+"件");
    		 $(".shopping_trolley_center .determine").css("background","#F97215");
    	}
     	$(".information .price").html(defaultSkus.price);
     	
     	var j = $(".information .price").html();
		if(/^\d+$/.test(j)){
		j = j + ".00";
		}else if(/^(\d+\.)(\d+)$/.test(j)){
			var i = RegExp.$1;
			var t = RegExp.$2;
			if(t.length == 1)
			j = j + "0";
			else if(t.length > 2)
			j = i + t.substring(0,2);
		}
		//alert(v);
		$(".information .price").html(j);
     	
     	
   	    currentSku = defaultSkus;
    }
    
//     	 点击规格li
     $('.specifications_ul .casing').click(function(){
     	
     	currentSku =null;
     	
     	// 判断显示已选择
        if ($(".include").hasClass("public")) {
            $(".kind").show();
            $(".specification").show();
        };
        $(this).addClass('public');
        $(this).siblings().removeClass('public');
        
        $(".specification").addClass("hide");
        $(".specification").removeClass("showfalg");
        
        var lalala = [];
        $('.specifications_ul .casing[class*="public"]').each(function(index,obj){
        	 
        	 var dataId = $(obj).attr("data-id");
        	 $(".specification").each(function(index,objs){
        	 	 var dataIds = $(objs).attr("data-id");
        	 	 var dataName = $(objs).attr("title");
	        	 if(dataId == dataIds){
	        	 	$(objs).addClass("showfalg");
	        	 	lalala.push(dataIds);
	        	 	 $(".specification_name").each(function(index,obj){
	        	 		var title = $(obj).attr("title");
	        	 		if(dataName == title){
	        	 			$(obj).addClass("hide");
	        	 		}
	        	 	})
	        	 }
	        })
        })
        
	    var specificationNames = $(".specification_name[class*='hide']");
	 	if(specificationNames!=null
	 		    && specificationNames.length == specificationsResutl.length){
	 			
	 		 $(".shopping_trolley_center .determine").css("background","#F97215");   	
	 		 $(".showfalg").removeClass("hide");
	 		 $(".choice").html("已选择");
	 		 
	 		 
 		    if(lalala!=null){
             	lalala.sort();
            }
            
            for (var i = 0; i < skus.length; i++) {
            	if(skus[i].specificationIdsStr == lalala.join(",")){
            		currentSku = skus[i];
            	}
            }
            
            if(currentSku!=null){
            	if(currentSku.isOutOfStock){
	        		 $(".information .repertory").html("该商品库存不足");
	        		 $(".shopping_trolley_center .determine").css("background","#aaaaaa");
	        	}else{
	        		 $(".information .repertory").html("库存"+currentSku.availableStock+"件");
	        		 $(".shopping_trolley_center .determine").css("background","#F97215");
	        	}
	         	$(".information .price").html(currentSku.price);
        	}else{
        	 	$(".information .repertory").html("该商品库存不足");
	            $(".shopping_trolley_center .determine").css("background","#aaaaaa");
        	}
	 	}else{
	 		$(".shopping_trolley_center .determine").css("background","#aaaaaa");
	 	}
        
    });
}


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








