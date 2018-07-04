<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="X-UA-Compatible" content="IEedge"/>
    <meta charset="UTF-8">
    <title>充值-熊猫中医</title>
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
    <link rel="stylesheet" href="/web/css/school/pay-recharge.css"/>
</head>
<body>
<header>
<#include "../school/header-body.ftl">
</header>
<div class="wp">
    <div class="main-top">
        <div class="recharge-explain">
            <p class="z explain">选择充值额度<span>兑换比例说明：1元=10熊猫币</span></p>
            <p class="y balance">余额：${balance!'0'}熊猫币</span></p>
        </div>
        <div class="select-pay-money">
            <ul>
            <#if envFlag?? && envFlag != "prod">
            <#-- <li class="select-money" data-val="0.1">
                 <p>1熊猫币</p>
                 <p class="cny">&yen; 0.1</p>
             </li>  -->
                <li data-val="10">
                    <p>100熊猫币</p>
                    <p class="cny">&yen; 10</p>
                </li>
            <#else>
                <li class="select-money" data-val="10">
                    <p>100熊猫币</p>
                    <p class="cny">&yen; 10</p>
                </li>
            </#if>
                <li data-val="50">
                    <p>500熊猫币</p>
                    <p class="cny">&yen; 50</p>
                </li>
                <li data-val="100">
                    <p>1000熊猫币</p>
                    <p class="cny">&yen; 100</p>
                </li>
                <li data-val="200">
                    <p>2000熊猫币</p>
                    <p class="cny">&yen; 200</p>
                </li>
                <li data-val="500">
                    <p>5000熊猫币</p>
                    <p class="cny">&yen; 500</p>
                </li>
            </ul>
        </div>
    </div>
    <div class="main-bottom">
        <h4>选择支付方式</h4>
        <ul class="select-pay">
            <li class="select-confirm" data-type="1">
                <div class="pay-logo">
                    <img src="/web/images/live/alipay.png"/>
                </div>
                <p>支付宝</p>
                <span class="icon-sure"></span>
            </li>
            <li data-type="2">
                <div class="pay-logo">
                    <img src="/web/images/live/weixin.png"/>
                </div>
                <p>微信</p>
                <span class="icon-sure hide"></span>
            </li>
        </ul>
    </div>
    <!--立即充值-->
    <div class="recharge-box">
        <button type="button" class="J-recharge">立即充值</button>
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
<script src="/web/js/school/pay-recharge.js" type="text/javascript" charset="utf-8"></script>

</body>

</html>