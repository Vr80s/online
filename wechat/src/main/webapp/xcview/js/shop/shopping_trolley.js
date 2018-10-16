$shopCartDiv = $("#shop_cart_div");
$choiceProduct = $("#choice_product");
var oldSkuId;
var updatedSkuId;
var updatedSku;
var skus = [];
var storeCartItems =[];
$(function () {
    // 数量减
    $shopCartDiv.on('click', '.minus', function () {
        var t = $(this).parent().find('.num');
        t.text(parseInt(t.text()) - 1);
        if (t.text() <= 1) {
            t.text(1);
        }
        if ($(this).parent().find('.num').html() == "1") {
            t.siblings(".minus").addClass("minus-class");
        }
        modifyCartQuantity(t.data('sid'), t.text());
        TotalPrice();
    });
    // 数量加
    $shopCartDiv.on('click', '.plus', function () {
        var t = $(this).parent().find('.num');
        var curQty = parseInt(t.text());
        var sid = t.data('sid');
        var inventory = parseInt($shopCartDiv.find('.product-sku-' + sid).find('.number_packages').text());
        if (inventory <= curQty) {
            jqtoast('商品库存不足');
            return false;
        }
        t.text(curQty + 1);
        if (t.text() <= 1) {
            t.text(1);
        }
        $(this).siblings(".minus").removeClass("minus-class");
        modifyCartQuantity(t.data('sid'), t.text());
        TotalPrice();
    });
    /******------------分割线-----------------******/
    // 点击商品按钮
    $shopCartDiv.on('click', '.goodsCheck', function () {
        var goods = $(this).closest(".shop-group-item").find(".goodsCheck"); //获取本店铺的所有商品
        var goodsC = $(this).closest(".shop-group-item").find(".goodsCheck:checked"); //获取本店铺所有被选中的商品
        var Shops = $(this).closest(".shop-group-item").find(".shopCheck"); //获取本店铺的全选按钮
        if (goods.length == goodsC.length) { //如果选中的商品等于所有商品
            Shops.prop('checked', true); //店铺全选按钮被选中
            if ($(".shopCheck").length == $(".shopCheck:checked").length) { //如果店铺被选中的数量等于所有店铺的数量
                $("#AllCheck").prop('checked', true); //全选按钮被选中
                TotalPrice();
            } else {
                $("#AllCheck").prop('checked', false); //全选按钮不被选中 
                TotalPrice();
            }

            $(".exclude-freight").hide();  //不含运费
        } else { //如果选中的商品不等于所有商品
            $(".exclude-freight").show();  //不含运费

            Shops.prop('checked', false); //店铺全选按钮不被选中
            $("#AllCheck").prop('checked', false); //全选按钮也不被选中
            // 计算
            TotalPrice();
            // 计算
        }
        
       var cid =   $(this).attr("data-cid");
       var arrayCartItems=[];
       arrayCartItems.push(cid);
       updateChecked(arrayCartItems,$(this).prop("checked"));
        
    });
    // 点击店铺按钮
    $shopCartDiv.on('click', '.shopCheck', function () {
        if ($(this).prop("checked") == true) { //如果店铺按钮被选中
            $(".exclude-freight").show();  //不含运费
            $(this).parents(".shop-group-item").find(".goods-check").prop('checked', true); //店铺内的所有商品按钮也被选中
            if ($(".shopCheck").length == $(".shopCheck:checked").length) { //如果店铺被选中的数量等于所有店铺的数量
                $("#AllCheck").prop('checked', true); //全选按钮被选中
                TotalPrice();
            } else {
                $("#AllCheck").prop('checked', false); //else全选按钮不被选中
                TotalPrice();
            }
        } else { //如果店铺按钮不被选中
            $(".exclude-freight").hide();  //不含运费
            $(this).parents(".shop-group-item").find(".goods-check").prop('checked', false); //店铺内的所有商品也不被全选
            $("#AllCheck").prop('checked', false); //全选按钮也不被选中
            TotalPrice();
        }
        
       var storeId =   $(this).attr("data-storeId");
       var arrayCartItems = getCartItemsByStroreId(storeId);
        
       updateChecked(arrayCartItems,$(this).prop("checked"));
    });
    
    function updateChecked(ids,checked){
    	 requestGetService("/xczh/shop/cart/updateCartItemChecked", 
    	 	{'cartItemIds': ids.join(','),"isChecked":checked}, function (data) {
    	 	console.log("设置默认成功");
    	 })
    }
    
    function getCartItemsByStroreId(storeId){
		var arrayCartItems = [];	
    	if(storeId!=null){
        	for (var i = 0; i < storeCartItems.length; i++) {
        		var storeCartItem = storeCartItems[i];
        		if(storeId == storeCartItem.id){
        			var cartItems = storeCartItem.cartItems;
        			for (var j = 0; j < cartItems.length; j++) {
        				arrayCartItems.push(cartItems[j].id);
        			}
        			break;
        		}
        	}
        }
    	return arrayCartItems;
    }
    
   function getCartItemsAll(){
		var arrayCartItems = [];	
    	if(storeId!=null){
        	for (var i = 0; i < storeCartItems.length; i++) {
        			var cartItems = storeCartItems[i].cartItems;
        			for (var j = 0; j < cartItems.length; j++) {
        				arrayCartItems.push(cartItems[j].id);
        			}
            }
        }
        return arrayCartItems;
    }
    	
    
    
    // 点击全选按钮
    $("#AllCheck").click(function () {
        if ($(this).prop("checked") == true) { //如果全选按钮被选中  
            $(".exclude-freight").show();   //不含运费
            // $(".select").show();  //选择规格下拉显示
            // $(".selectbg").show();    //选择规格下拉添加的 背景层
            $(".goods-check").prop('checked', true); //所有按钮都被选中
            TotalPrice();
        } else {
            $(".exclude-freight").hide(); //不含运费
            // $(".select").hide();      //选择规格下拉隐藏
            // $(".selectbg").hide();    //选择规格下拉添加的 背景层
            $(".goods-check").prop('checked', false); //else所有按钮不全选
            TotalPrice();
        }
        $(".shopCheck").change(); //执行店铺全选的操作
    });

    //计算
    function TotalPrice() {
        var allprice = 0; //总价
        $(".shop-group-item").each(function () { //循环每个店铺
            $(".check").click(function () {
                if ($('.check').is(':checked')) {
                    $(".exclude-freight").show();  //不含运费
                    // $(".select").show();       //选择规格下拉隐藏
                    // $(".selectbg").show();     //选择规格下拉添加的 背景层
                } else {
                    $(".exclude-freight").hide();  //不含运费
                    // $(".select").hide();       //选择规格下拉隐藏
                    // $(".selectbg").hide();     //选择规格下拉添加的 背景层
                }
            });

            var oprice = 0; //店铺总价
            $(this).find(".goodsCheck").each(function () { //循环店铺里面的商品
                if ($(this).is(":checked")) { //如果该商品被选中
                    var num = parseInt($(this).parents(".shop-info").find(".num").text()); //得到商品的数量
                    var price = parseFloat($(this).parents(".shop-info").find(".price").text()); //得到商品的单价
                    var total = price * num; //计算单个商品的总价
                    oprice += total; //计算该店铺的总价
                }
                $(this).closest(".shop-group-item").find(".ShopTotal").text(oprice.toFixed(2)); //显示被选中商品的店铺总价
            });
            var oneprice = parseFloat($(this).find(".ShopTotal").text()); //得到每个店铺的总价
            allprice += oneprice; //计算所有店铺的总价
        });
        $("#AllTotal").text(allprice.toFixed(2)); //输出全部总价
    }

    // 点击加入购物车
    $('.add_cart').click(function () {
        $('.shopping_trolley').show();
    });
    // 点击详情背景色
    $('.shopping_trolley_bg').click(function () {
        // $('.shopping_trolley').hide();
    });

    // 点击加号
    $choiceProduct.on('click', '.increase', function (e) {
        var count = parseInt($('.spinnerExample').val());
        if (count > updatedSku.availableStock) {
            jqtoast('商品库存不足');
            if (count > 1) {
                $('.spinnerExample').val(count - 1);
            }
            return false;
        }
    });
    $choiceProduct.on('click', '.decrease', function (e) {
    });

    // 点击遮盖在select上的div
    $shopCartDiv.on('click', '.inventory', function () {
        var pid = $(this).data('pid');
        var sid = $(this).data('sid');
        requestGetService("/xczh/shop/cart/product", {"id": pid}, function (data) {
            $('.shopping_trolley_main').html(template('shop_product_choice', data.resultObject));
            skus = data.resultObject.skuVOs;
            choiceSku(sid);
            $('.shopping_trolley').show();
            // 点击数量加减
            $choiceProduct.find('.spinnerExample').spinner({});
            oldSkuId = sid;
        });
    });
//	点击遮盖的div显示后的选择框的背景图
    $(".shopping_trolley_bg").click(function () {
        $(".shopping_trolley").hide();
    });

    $choiceProduct.on('click', '.include', function () {
        var index = $(this).data('index');
        $choiceProduct.find('.spec-index-' + index).html($(this).data('name'));
        $(this).parents('.specifications_ul').find('.include').removeClass('public');
        $(this).addClass('public');
        changeProductProp();
    });

    // 点击头部编辑
    $('.compile').click(function () {
        $(this).hide();
        $('.finish').show();
        $('.shop-total').hide();
        $('.itemdelete').show();
    });

    // 点击头部完成
    $('.finish').click(function () {
        $(this).hide();
        $('.compile').show();
        $('.shop-total').show();
        $('.itemdelete').hide();
    });

    /**
     * 确认修改规格
     */
    $choiceProduct.on('click', '.determine', function (e) {
        e.stopPropagation();
        if (oldSkuId && updatedSkuId) {
            var quantity = $choiceProduct.find('.spinnerExample').val();
            requestPostService('/xczh/shop/cart/update', {
                'oldSkuId': oldSkuId,
                'updatedSkuId': updatedSkuId,
                "quantity": quantity
            }, function (data) {
                if (data.success === true) {
                    //没有修改规格，更新数量
                    var $oldSkuId = $shopCartDiv.find('.product-sku-' + oldSkuId);
                    if (oldSkuId === updatedSkuId) {
                        $oldSkuId.find('.num').text(quantity);
                        // var spinnerExample = $(".spinnerExample").val();
                        // $(".shop-arithmetic .num").html(spinnerExample);
                    } else {
                        //判断新增的sku是否已经存在在购物车中
                        var $newSkuId = $shopCartDiv.find('.product-sku-' + updatedSkuId);
                        if ($newSkuId.length > 0) {
                            $newSkuId.find('.num').html(quantity);
                            $oldSkuId.removeClass('product-sku-' + oldSkuId);
                        } else {
                            $oldSkuId.removeClass('product-sku-' + oldSkuId);
                            // var spinnerExample = $(".spinnerExample").val();
                            // $(".shop-arithmetic .num").html(spinnerExample);
                            $oldSkuId.addClass('product-sku-' + updatedSkuId);
                            $oldSkuId = $shopCartDiv.find('.product-sku-' + updatedSkuId);
                            $oldSkuId.find('.goodsCheck').data('sid', updatedSkuId);
                            $oldSkuId.find('.goodsCheck').data('cid', data.resultObject);
                            $oldSkuId.find('.packaging').html(updatedSku.specifications);
                            $oldSkuId.find('.number_packages').html(updatedSku.availableStock);
                            $oldSkuId.find('.inventory').data('sid', updatedSkuId);
                            $oldSkuId.find('.num').html(quantity);
                            $oldSkuId.find('.price').html(updatedSku.price);
                        }

                    }
                    $('.shopping_trolley').hide();
                }
                TotalPrice();
            });
        }
    });

    /**
     * 结算
     */
    $('.settlement').on('click', function () {
        var ids = [];
        var skuIds = [];
        $shopCartDiv.find('.goodsCheck').each(function () {
            if ($(this).is(":checked")) {
                ids.push($(this).data('cid'));
                skuIds.push($(this).data('sid'));
            }
        });
        if (ids.length < 1) {
            jqtoast('请勾选商品');
            return false;
        }
        requestPostService('/xczh/shop/checkSkus', {'cartItemIds': ids.join(',')}, function (resp) {
            if (!resp.resultObject) {
                window.location.href = '/xcview/html/shop/confirm_order.html?cartItemIds=' + ids.join(',')+"&type=2";
            } else {
                jqtoast(resp.resultObject);
            }
        });
    });

    $('.message').on('click', function () {
        jqtoast('客服休息中,稍后报道~');
    })
});
initCart();
initRecommendProduct();

function changeProductProp() {
    var specIds = [];
    $choiceProduct.find('.public').each(function () {
        specIds.push($(this).data("eid"));
    });
    var matchIndex;
    var match;
    for (var i = 0; i < skus.length; i++) {
        match = true;
        for (var j = 0; j < specIds.length; j++) {
            if (skus[i].specificationValueIds.indexOf(specIds[j]) === -1) {
                match = false;
                break;
            }
        }
        if (match) {
            matchIndex = i;
            break;
        }
    }
    $choiceProduct.find('.price').html('￥' + skus[matchIndex].price);
    $choiceProduct.find('.repertory').html('库存' + skus[matchIndex].availableStock + '件');
    updatedSkuId = skus[matchIndex].id;
    updatedSku = skus[matchIndex];
}

function choiceSku(skuId) {
    if (skus.length > 0) {
        for (var i = 0; i < skus.length; i++) {
            var specIds = skus[i].specificationValueIds;
            if (skuId === skus[i].id) {
                for (var j = 0; j < specIds.length; j++) {
                    $('.spec-' + specIds[j]).trigger('click');
                }
                break;
            }
        }
        changeProductProp();
    }
}

function initCart() {
    requestGetService("/xczh/shop/cart", null, function (data) {
        if (data.resultObject.storeCartItems.length < 1) {
            $(".compile").hide();  //编辑隐藏
            $(".finish").hide();   //完成
            $(".payment-bar").hide();
            $('.vacancy-main').show();
        } else {
        	
        	storeCartItems = data.resultObject.storeCartItems;
        	
            //$(".finish").show();   //完成
            $(".compile").show();  //编辑显示
            $(".payment-bar").show();
            var obj = data.resultObject.storeCartItems;
            for(i=0;i<obj.length;i++){
                var price = obj[i].cartItems[0].sku.price;
                if(/^\d+$/.test(price)){
                    price = price + ".00";
                }else if(/^(\d+\.)(\d+)$/.test(price)){
                    var j = RegExp.$1;
                    var t = RegExp.$2;
                    if(t.length == 1)
                        price = price + "0";
                    else if(t.length > 2)
                        price = j + t.substring(0,2);
                }
                obj[i].cartItems[0].sku.price=price;
            }
            $('#shop_cart_div').html(template('shop_cart_tmpl', data.resultObject));
        }
    });
}

function modifyCartQuantity(skuId, quantity) {
    requestPostService('/xczh/shop/cart/modify', {'skuId': skuId, 'quantity': quantity}, function (data) {
    });
}

function deleteCartProduct(skuIds) {
    requestPostService('/xczh/shop/cart/sku/delete', {'skuIds': skuIds.join(",")}, function (data) {
        location.reload();
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
        }
    });
}

// 最大医师推荐div循环下li
function recommendLi() {
    // 循环li
    $('.shop-group-item ul li').each(function () {
        if ($(this).find(".check").is(':checked')) {
            // $(this).parent().remove();
            $(this).remove();
            // 如果li为0，隐藏最大div
            if ($(".shop-group-item ul li").length == 0) {
                $(".shop-group-item").hide();   //ul 父级
                $(".shopping").hide();    //最大div
                $(".payment-bar").hide(); //底部---结算
            }
        }
    });
}

function recommend() {
    // 循环li最大父级别
    $('.shop-group-item').each(function () {
        if ($(this).find("li").length == 0) {
            $(this).remove();
        }
    });
}

// 点击确认删除选中商品
$('.affirm').click(function () {
    var skuIds = [];
    $shopCartDiv.find('.product-li').each(function () {
        $(this).find('input[type="checkbox"]:checked').each(function () {
            skuIds.push($(this).data('sid'));
        });
    });
    if (skuIds.length < 1) {
        jqtoast("请选择要删除的商品");
    }
    recommendLi();  //最大医师推荐div循环下li
    recommend();    //循环li最大父级别
    // $('.finish').hide();      //完成
    // $('.compile').show();     //编辑
    // $('.shop-total').show();  //结算
    // $('.itemdelete').hide();  //删除商品按钮
    $('.removeitem').hide();  //删除商品提示框
    jqtoast("删除成功");

    // 判断有没有商品列表
    var a = $(".shopping").css("display");
    if (a == "block") {
        $(".hidden_field").css("display", "block");  //显示底部高度
        $(".vacancy-main").hide();   //隐藏猜你喜欢
    } else {
        $(".hidden_field").css("display", "none");
        $(".compile").hide();  //编辑隐藏
        $(".vacancy-main").show();   //显示猜你喜欢
    }
    deleteCartProduct(skuIds);
});

$(".foot_del").click(function () {
    $(".Each").each(function () {
        if ($(this).prop('checked')) {
            $(this).parents(".imfor").remove();
            totl();
            if ($(".imfor").length == 0) {
                $("#susum").text(0);
            }
        }
    })
});


// 点击删除商品出现提示框  
$('.itemdelete').click(function () {

    if ($(".shop-group-item ul li").find(".check").is(':checked')) {
        $('.removeitem').show();
    } else {
        jqtoast("请先选择要删除的商品");
    }
});
/*$('.itemdelete').click(function(){
    $('.removeitem').show();
});*/
// 点击取消隐藏删除提示框 
$('.countermand').click(function () {
    $('.removeitem').hide();
});




