<!DOCTYPE html>
<html lang="en" xmlns=http://www.w3.org/1999/xhtml>

<head>
    <!--[if IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IEedge"/>
    <meta charset="UTF-8">
    <title>熊猫中医云课堂 - 微信支付</title>
    <!--<link rel="shortcut icon" href="../../favicon.ico" />-->
    <meta name="keywords" content="中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医"/>
    <meta name="description" content="熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。"/>
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="/web/css/weixinPayPage.css">
    <link rel="stylesheet" href="/web/css/footer.css">
    <script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/ajax.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/web/js/artTemplate.js"></script>

</head>

<body>
<!--头部-->
<div id="weixinLogo">
    <div class="weinxinHeader">
        <img src="/web/images/130/winxinLogo.png" alt=""/>
        <span>微信支付</span>
    </div>
</div>
<div id="weixinPayPage">
    <div class="weixinOrderInfo clearfix">
        <div class="orderInfo">
            <div class="orderNumberBox">
                <span>订单编号：</span><span class="orderNumber">${orderNo}</span>
            </div>
        </div>
        <div class="price">
            <span>应付金额：</span><i>￥</i><span class="coursePrice">${price}</span>
        </div>
        <div class="orderName clearfix">
            <p class="tt">订单详情：</p>
            <p class="order-courseName">
            ${courseName}
            </p>
            <div class="spread">

            </div>
        </div>
    </div>
    <div class="saoma">
        <div class="erweima">
            <img src="${codeimg}" alt=""/>
        </div>
        <div class="saomaHint">
            <img src="/web/images/130/weixinhint.png" alt=""/>
        </div>
    </div>
</div>
<div class="zhezao">
</div>
<div class="tank">
    支付完成，将在 <span>5s</span> 后自动跳转
</div>
<script src="/web/js/footer.js?v=ipandatcm_1.3" type="text/javascript"></script>
<script>
    var recharge = '${recharge!''}';
    var preurl = '${preurl!''}' ? decodeURIComponent('${preurl!''}') : null;
    $(".spread").off("click").on("click", function () {
        if ($(this).hasClass("active")) {
            $(this).removeClass("active");
            $(".orderName .order-courseName").height("30px");
        } else {
            $(this).addClass("active");
            $(".orderName .order-courseName").height("auto");
        }
    });
    $(document).ready(function () {
        c = setInterval(lunxu, 3000);//每3秒执行一次checkIsExist方法
    });

    function lunxu() {
        if (recharge !== "recharge") {
            RequestService("/web/checkOrder", "GET", {
                orderNo: $(".orderNumber").html()
            }, function (data) {
                if (data.resultObject === "yes") {
                    window.clearInterval(c);
                    $(".zhezao").show();
                    $(".tank").show();
                    var m = 5;
                    window.setInterval(function () {
                        m--;
                        if (m === 0) {
                            window.location.href = "/order/pay/success?orderId=" + '${orderId}';
                        }
                        $(".tank span").html(m + "s");
                    }, 1000);
                } else {
                }
            }, false);
        } else {
            RequestService("/userCoin/checkRechargeOrder", "GET", {
                orderNo: $(".orderNumber").html()
            }, function (data) {
                if (data.resultObject === true) {
                    window.clearInterval(c);
                    $(".zhezao").show();
                    $(".tank").show();
                    var m = 5;
                    window.setInterval(function () {
                        m--;
                        if (m === 0) {
                            //点击跳转充值记录
                            localStorage.setItem("personcenter", "mymoney ");
                            localStorage.setItem("findStyle", "profile ");
                            location.href = "/web/html/personal-center/personal-index.html#menu4"
                        }
                        $(".tank span").html(m + "s");
                    }, 1000);
                } else {
                }
            }, false);
        }
    }
</script>
</body>

</html>