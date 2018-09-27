$(function() {
    // 数量减
    $(".minus").click(function() {
        
        var t = $(this).parent().find('.num');
        t.text(parseInt(t.text()) - 1);
        if (t.text() <= 1) {
            t.text(1);
        }
        if ($(this).parent().find('.num').html() == "1") {
            t.siblings(".minus").addClass("minus-class");
        };
        TotalPrice();
    });
    // 数量加
    $(".plus").click(function() {
        var t = $(this).parent().find('.num');
        t.text(parseInt(t.text()) + 1);
        if (t.text() <= 1) {
            t.text(1);
        }
        $(this).siblings(".minus").removeClass("minus-class");
        TotalPrice();
    });
    /******------------分割线-----------------******/
    // 点击商品按钮
    $(".goodsCheck").click(function() {
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
            $(".select").hide();  //选择规格下拉隐藏
            $(".selectbg").hide();  //选择规格下拉添加的 背景层

        } else { //如果选中的商品不等于所有商品

            $(".exclude-freight").show();  //不含运费
            $(".select").show();  //选择规格下拉显示
            $(".selectbg").show(); //选择规格下拉添加的 背景层

            Shops.prop('checked', false); //店铺全选按钮不被选中
            $("#AllCheck").prop('checked', false); //全选按钮也不被选中
            // 计算
            TotalPrice();
            // 计算
        }
    });
    // 点击店铺按钮
    $(".shopCheck").click(function() {
        if ($(this).prop("checked") == true) { //如果店铺按钮被选中
            $(".exclude-freight").show();  //不含运费
            $(".select").show();  //选择规格下拉显示
            $(".selectbg").show();   //选择规格下拉添加的 背景层
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
            $(".select").hide();  //选择规格下拉隐藏
            $(".selectbg").hide();   //选择规格下拉添加的 背景层
            $(this).parents(".shop-group-item").find(".goods-check").prop('checked', false); //店铺内的所有商品也不被全选
            $("#AllCheck").prop('checked', false); //全选按钮也不被选中
            TotalPrice();
        }
    });
    // 点击全选按钮
    $("#AllCheck").click(function() {
        if ($(this).prop("checked") == true) { //如果全选按钮被选中  
            $(".exclude-freight").show();   //不含运费
            $(".select").show();  //选择规格下拉显示
            $(".selectbg").show();    //选择规格下拉添加的 背景层
            $(".goods-check").prop('checked', true); //所有按钮都被选中
            TotalPrice();
        } else {
            $(".exclude-freight").hide(); //不含运费
            $(".select").hide();      //选择规格下拉隐藏
            $(".selectbg").hide();    //选择规格下拉添加的 背景层
            $(".goods-check").prop('checked', false); //else所有按钮不全选
            TotalPrice();
        }
        $(".shopCheck").change(); //执行店铺全选的操作
    });
    //计算
    function TotalPrice() {
        var allprice = 0; //总价
        $(".shop-group-item").each(function() { //循环每个店铺
            $(".check").click(function(){
                if($('.check').is(':checked')) {
                    $(".exclude-freight").show();  //不含运费
                    $(".select").show();       //选择规格下拉隐藏
                    $(".selectbg").show();     //选择规格下拉添加的 背景层
                }else{
                    $(".exclude-freight").hide();  //不含运费
                    $(".select").hide();       //选择规格下拉隐藏
                    $(".selectbg").hide();     //选择规格下拉添加的 背景层
                }
            });

            var oprice = 0; //店铺总价
            $(this).find(".goodsCheck").each(function() { //循环店铺里面的商品
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
});


// 最大医师推荐div循环下li
function recommendLi(){
    // 循环li
    $('.shop-group-item ul li').each(function(){
        if($(this).find(".check").is(':checked')) {
            // $(this).parent().remove();
            $(this).remove();
            // 如果li为0，隐藏最大div
            if($(".shop-group-item ul li").length == 0) {
                  $(".shop-group-item").hide();   //ul 父级
                  $(".shopping").hide();    //最大div
                  $(".payment-bar").hide(); //底部---结算
            }
        };
    });
}

function recommend(){
    // 循环li最大父级别
    $('.shop-group-item').each(function(){
        if($(this).find("li").length == 0) {
            $(this).remove();
        };
    });
};


// 点击确认删除选中商品
$('.affirm').click(function(){
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
        $(".hidden_field").css("display","block");  //显示底部高度
        $(".vacancy-main").hide();   //隐藏猜你喜欢
    } else {
        $(".hidden_field").css("display","none");
        $(".vacancy-main").show();   //显示猜你喜欢
    }
});

$(".foot_del").click(function() {
    $(".Each").each(function() {
        if($(this).prop('checked')) {
            $(this).parents(".imfor").remove();
              totl();
              if($(".imfor").length == 0) {
                $("#susum").text(0);
              }
        }
    })

})


// 点击删除商品出现提示框  
$('.itemdelete').click(function(){

    if($(".shop-group-item ul li").find(".check").is(':checked')) {
        $('.removeitem').show();
    }else{
        jqtoast("请先选择要删除的商品");
    };
    
});
/*$('.itemdelete').click(function(){
    $('.removeitem').show();
});*/
// 点击取消隐藏删除提示框 
$('.countermand').click(function(){
    $('.removeitem').hide();
});





