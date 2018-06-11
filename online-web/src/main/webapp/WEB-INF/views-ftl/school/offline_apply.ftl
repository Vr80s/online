<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="X-UA-Compatible" content="IEedge"/>
    <meta charset="UTF-8">
    <title>熊猫中医云课堂 - 线上中医教育</title>
    <link rel="shortcut icon" href="/favicon.ico"/>
    <meta name="keywords" content="中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医"/>
    <meta name="description"
          content="熊猫中医云课堂为。课程大纲全新优化，内容有广度、有深度，顶尖讲师全程直播授课。专注整合优势教学资源、打造适合在线学习并能保证教学结果的优质教学产品，同时打造和运营一整套教育生态软件体系，为用户提供满足自身成长和发展要求的有效服务。"/>
    <meta name="renderer" content="webkit">
    <meta name="baidu-site-verification" content="UHaAQAeAQF"/>
    <!--头部/底部样式-->
    <link rel="stylesheet" href="/web/html/school/school-header/header.css"/>
    <link rel="stylesheet" href="/web/css/footer.css"/>
    <link rel="stylesheet" href="/web/font/iconfont.css"/>

    <!--登陆的bootstrap样式-->
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link href="/web/bootstrap-select/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="/web/css/registration_sites.css"/>
</head>

<body>
<header>
<#include "header-body.ftl">
</header>
<div class="main">
    <div class="title">填写报名信息</div>
    <div class="hint">
        <div class="img"><img src="/web/images/buy-success.png" alt=""/></div>
        <div class="size">为保障您的个人权益，请填写相应信息，便于活动提醒、入场时的身份确认、以及特殊通知的送达，以确保您能顺利进入会场，避免他人冒充。</div>
        <div class="both"></div>
    </div>
    <input type="hidden" id="J_courseId" value="${courseId}">
    <div class="center">
        <div class="name">
            <div class="xx">*</div>
            <div class="names">姓名</div>
            <div class="input"><input type="text" id="J_realName" value="${realName}"/></div>
        </div>
        <div class="both"></div>
        <div class="tel">
            <div class="xx">*</div>
            <div class="tels">手机号</div>
            <div class="input"><input type="tel" id="J_mobile" value="${mobile}"/></div>
        </div>
        <div class="both"></div>
        <div class="weixin">
            <div class="xx">*</div>
            <div class="weixins">微信号</div>
            <div class="input"><input type="text" id="J_wechatNo" value="${wechatNo}"/></div>
        </div>
        <div class="both"></div>
        <div class="sex">
            <div class="xx">*</div>
            <div class="sexs">性别</div>
            <div class="select">
                <select name="sex" id="J_sex" value="${sex}">
                    <option>请选择</option>
                    <option value="1" <#if sex=="1">selected</#if>>男</option>
                    <option value="0" <#if sex=="0">selected</#if>>女</option>
                </select>
            </div>
        </div>
    </div>
    <div class="both"></div>
    <div class="button J-submit">提交</div>

</div>


</body>
<script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="/web/js/artTemplate.js"></script>
<script src="/web/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>

<!--公共头部和底部-->
<script src="/web/js/ajax.js" type="text/javascript" charset="utf-8"></script>
<script src="/web/html/school/school-header/header.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="/web/js/footer.js"></script>
<script type="text/javascript" src="/web/js/common_msg.js"></script>
<script type="text/javascript" src="/web/js/school/offline-apply.js"></script>
<!--公共头部和底部结束-->
<!--登陆结束-->


</html>