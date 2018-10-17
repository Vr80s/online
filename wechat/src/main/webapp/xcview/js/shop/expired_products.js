$shopCartDiv = $("#shop_cart_div");
$choiceProduct = $("#choice_product");
var oldSkuId;
var updatedSkuId;
var updatedSku;
var skus = [];
var storeCartItems =[];
$(function () {

    $('.message').on('click', function () {
        jqtoast('客服休息中,稍后报道~');
    })
	
});
initCart();
initRecommendProduct();

function initCart() {
    requestGetService("/xczh/shop/cart", null, function (data) {
        if (data.resultObject.storeCartItems.length < 1) {
            $(".payment-bar").hide();
            $('.vacancy-main').show();
        } else {
        	
        	storeCartItems = data.resultObject.storeCartItems;
        	
        	if(data.resultObject.isChecked){
        		$("#AllCheck").attr("checked",'checked');
        	}else{
        		$("#AllCheck").removeAttr("checked");
        	}
            $('#shop_cart_div').html(template('shop_cart_tmpl', data.resultObject));
            $(".shop-names").html(data.resultObject.storeCartItems[0].name+"医师推荐");
        }
    });
}

function initRecommendProduct() {
    requestGetService("/xczh/shop/goods/list", {
        'pageNumber': 1,
        'pageSize': 4,
        'orderType': 'RECOMMEND_DESC'
    }, function (data) {
        if (data.success) {
            $('#shop_recommend_product_ul').html(template('shop_recommend_product_tmpl', data));
            $(".list li").click(function () {
                var id = $(this).attr("data-id");
                window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
            })
            
            $(".list li").each(function(){
			    var d = $(this).find("span").html();
				if(/^\d+$/.test(d)){
				d = d + ".00";
				}else if(/^(\d+\.)(\d+)$/.test(d)){
					var i = RegExp.$1;
					var t = RegExp.$2;
					if(t.length == 1)
					d = d + "0";
					else if(t.length > 2)
					d = i + t.substring(0,2);
				}
				//	alert(v);
				$(this).find("span").html(d);
			});
            
            
        }
    });
}


// 点击分享按钮
$('.share_class').click(function () {
    $('.weixin_ceng').show();
});

//点击分享提示背景色
$('.weixin_img').click(function () {
    $('.weixin_ceng').hide();
});




