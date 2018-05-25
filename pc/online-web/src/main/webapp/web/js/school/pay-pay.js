$(function () {

    var paySuccessUrl = "/order/pay/success?orderId=" + orderId;
    $(".select-pay li").click(function () {
        $(".select-pay li").removeClass("select-confirm").find("span").addClass("hide");
        $(this).addClass("select-confirm").find("span").removeClass("hide");
        var $rechargeBox = $('.recharge-box');
        var $payBox = $('.pay-box');
        if ($(this).data('type') === 1 && needRechargeCoin) {
            $rechargeBox.removeClass("hide");
            $payBox.addClass("hide");
        } else {
            $rechargeBox.addClass("hide");
            $payBox.removeClass("hide");
        }
    });

    //支付结果页面1
    $(".pay-success-btn").click(function () {
        RequestService("/web/getOrderStatusById", "GET", {
            orderId: orderId
        }, function (data) {
            if (data.resultObject === 1) {
                $(".pay-result1").css("display", "none");
                window.location.href = paySuccessUrl;
            } else {
                $(".pay-result1").css("display", "none");
                $(".pay-result2").css("display", "block");
            }
        }, false);
    });
    //支付结果页面2
    $(".pay-result2-btn").click(function () {
        $(".pay-result2").css("display", "none");
    });

    $(".pay-protocol-close").click(function () {
        $(".protocol-shadow").css("display", "none");
    });

    $(".pay-result1-close").click(function () {
        $(".pay-result1").css("display", "none");
    });
    $(".pay-result2-close").click(function () {
        $(".pay-result2").css("display", "none");
    });

    $(".recharge-close").click(function () {
        $(".recharge-modal").css("display", "none");
    });

    $('.recharge-modal-btn').on('click', function () {
        window.location.reload();
    });

    $('.J-recharge').on('click', function () {
        $(".recharge-modal").css("display", "block");
        window.open("/userCoin/recharge?orderId=" + orderId);
    });
    $('.J-pay').on('click', function () {
        var $this = $(this);
        var payType = 1;
        $(".pay-way").each(function () {
            if ($(this).hasClass("select-confirm")) {
                payType = $(this).data("type");
            }
        });

        var orderNo = $this.data('orderno');
        RequestService("/web/getOrderStatusById", "GET", {
            orderId: orderId
        }, function (data) {
            if (data.resultObject === 1) {
                showTip('订单已支付', function () {
                    window.location.href = paySuccessUrl;
                });
            } else {
                if (payType === 1) {//熊猫币支付
                    //直接使用熊猫币扣款
                    $.ajax({
                        url: '/order/pay/' + orderNo,
                        method: 'POST',
                        success: function (resp) {
                            if (resp.success) {
                                window.location.href = paySuccessUrl;//支付成功页
                            } else {
                                showTip(resp.errorMessage);
                            }
                        }
                    })
                } else if (payType === 2) {//支付宝支付
                    $(".pay-result1").css("display", "block");
                    window.open("/web/alipay/unifiedorder/" + orderNo);

                } else if (payType === 3) {//微信支付
                    $(".pay-result1").css("display", "block");
                    window.open("/web/wxPay/" + orderNo);
                }
            }
        }, false);

    });
});
