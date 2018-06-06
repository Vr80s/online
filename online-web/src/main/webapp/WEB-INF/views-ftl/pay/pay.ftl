<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="X-UA-Compatible" content="IEedge"/>
    <meta charset="UTF-8">
    <title>订单支付-熊猫中医</title>
    <link rel="shortcut icon" href="/favicon.ico"/>
    <meta name="keywords" content="中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医"/>
    <meta name="description"
          content="熊猫中医云课堂为。课程大纲全新优化，内容有广度、有深度，顶尖讲师全程直播授课。专注整合优势教学资源、打造适合在线学习并能保证教学结果的优质教学产品，同时打造和运营一整套教育生态软件体系，为用户提供满足自身成长和发展要求的有效服务。"/>
    <meta name="renderer" content="webkit">
    <meta name="baidu-site-verification" content="UHaAQAeAQF"/>
    <!--公共头部和底部样式-->
    <link rel="stylesheet" href="/web/html/school/school-header/header.css"/>
    <link rel="stylesheet" href="/web/css/footer.css"/>
    <link rel="stylesheet" href="/web/font/iconfont.css"/>
    <!--公共头部和底部样式结束-->
    <!--登陆的bootstrap样式-->
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link href="/web/bootstrap-select/bootstrap.min.css" rel="stylesheet">

    <!--登陆的bootstrap样式结束-->
    <link rel="stylesheet" href="/web/css/school/pay-pay.css"/>
</head>
<body>
<!--支付结果页面1-->
<div class="pay-result1">
    <div class="pay-result-content clearfix">
        <img class="pay-result1-close" src="/web/images/130/detail_close.png" alt="" />
        <p class="pay-result-text">请在新打开的页面完成支付，支付完成前，请不要关闭此窗口。</p>
        <div class="pay-success-btn" data-url="${url}">已完成支付</div>
        <div class="pay-result-hint">
            <span>支付遇到问题？</span>
            <a class="kefu" href="http://p.qiao.baidu.com/cps/chat?siteId=11043846&userId=24331702" target="_blank">联系客服</a>
            <span>获得帮助</span>
        </div>
    </div>
</div>
<!--支付结果页面2-->
<div class="pay-result2">
    <div class="pay-result2-content clearfix">
        <img class="pay-result2-close" src="/web/images/130/detail_close.png" alt="" />
        <div class="pay-result2-hint">
            <img src="/web/images/130/icon11.png" alt="" />
            <span>支付尚未完成，请尝试重新支付</span>
        </div>
        <div class="pay-result2-btn">确定</div>
    </div>
</div>
<div class="recharge-modal">
    <div class="pay-result2-content clearfix">
        <img class="recharge-close" src="/web/images/130/detail_close.png" alt="" />
        <div class="pay-result2-hint">
            <img src="/web/images/130/icon11.png" alt="" />
            <span>请在新打开的充值页面执行充值操作</span>
        </div>
        <div class="recharge-modal-btn">已完成充值</div>
    </div>
</div>
<div class="wp">
    <div class="main-top">
        <h4>请在24小时内完成支付，否则可能导致购买失败</h4>
        <p class="order-number">订单号：${order.orderNo}</p>
    <#list courses as course>
        <ul class="class-details">
            <li>课程详情：</li>
            <li><img src="${course.smallImgPath}"/></li>
            <li>${course.gradeName}</li>
        </ul>
    </#list>

        <p class="pay-money cl">需支付金额：<span>${order.actualPay}</span>熊猫币</p>
    </div>

    <div class="main-bottom">
        <h4>选择支付方式</h4>
        <ul class="select-pay">
            <li class="select-confirm pay-way" data-type="1">
                <div class="pay-logo">
                    <img src="/web/images/pay-panda.png"/>
                </div>
                <p>熊猫币</p>
                <span class="icon-sure"></span>
            </li>
            <li class="pay-way" data-type="2">
                <div class="pay-logo">
                    <img src="/web/images/live/alipay.png"/>
                </div>
                <p>支付宝</p>
                <span class="icon-sure hide"></span>
            </li>
            <li class="pay-way" data-type="3">
                <div class="pay-logo">
                    <img src="/web/images/weixin.png"/>
                </div>
                <p>微信</p>
                <span class="icon-sure hide"></span>
            </li>
        </ul>
        <p class="balance cl">余额：${balance!'0'}熊猫币</p>
    </div>
    <!--去充值-->

    <div class="recharge-box <#if !needRechargeCoin>hide</#if>" >
        <button type="button" class="J-recharge" data-url="/userCoin/recharge">余额不足，去充值</button>
    </div>
    <!--去支付-->
    <div class="pay-box <#if needRechargeCoin>hide</#if>">
        <button type="button" class="J-pay" data-orderno="${order.orderNo}">去支付</button>
    </div>
    <!--同意用户协议-->
    <div class="agreement-box">
        <p><em></em><span>我已阅读并同意</span></p>
        <span class="agreement-content"><a href="/web/html/payment_agree.html"
                                           target="_blank">《熊猫中医云课堂用户付费协议》</a></span>
    </div>
</div>

<script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="/web/js/artTemplate.js"></script>
<script src="/web/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>

<!--公共头部和底部-->
<script src="/web/js/ajax.js" type="text/javascript" charset="utf-8"></script>
<script src="/web/html/school/school-header/header.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="/web/js/footer.js"></script>
<!--公共头部和底部结束-->

<!--登陆结束-->
<script src="/web/js/school/pay-pay.js" type="text/javascript" charset="utf-8"></script>

</body>
<script type="text/javascript">
    var needRechargeCoin = ${needRechargeCoin?c};
    var orderId = "${order.id}";
</script>
</html>