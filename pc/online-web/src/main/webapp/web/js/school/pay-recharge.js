$(function () {
//选择充值数量
    $(".select-pay-money ul li").click(function () {
        $(".select-pay-money ul li").removeClass("select-money");
        $(this).addClass("select-money");
    });
//选择支付方式
    $(".main-bottom ul li").click(function () {
        $(".main-bottom ul li").removeClass("select-confirm").find("span").addClass("hide");
        $(this).addClass("select-confirm").find("span").removeClass("hide");
    });

    $('.J-recharge').on('click', function () {
        var price = $('.select-money').data('val');//充值的价格
        var type = $('.select-confirm').data('type');

        if (price <= 0) {
            showTip("充值价格不合法");
            return;
        }
        if (type === 1) {
            window.location.href = "/web/alipay/recharge/" + price;
        } else {
            window.location.href = "/web/wxPay/recharge/" + price;
        }
    });
});
