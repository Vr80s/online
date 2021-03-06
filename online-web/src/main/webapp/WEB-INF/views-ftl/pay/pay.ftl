﻿<!DOCTYPE html>
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
<header>
<#include "../school/header-body.ftl">
</header>
<!--支付结果页面1-->
<div class="pay-result1">
    <div class="pay-result-content clearfix">
        <img class="pay-result1-close" src="/web/images/130/detail_close.png" alt=""/>
        <p class="pay-result-text">请在新打开的页面完成支付，支付完成前，请不要关闭此窗口。</p>
        <div class="pay-success-btn" data-url="${url}">已完成支付</div>
        <div class="pay-result-hint">
            <span>支付遇到问题？</span>
            <a class="kefu" href="http://p.qiao.baidu.com/cps/chat?siteId=11043846&userId=24331702"
               target="_blank">联系客服</a>
            <span>获得帮助</span>
        </div>
    </div>
</div>
<!--支付结果页面2-->
<div class="pay-result2">
    <div class="pay-result2-content clearfix">
        <img class="pay-result2-close" src="/web/images/130/detail_close.png" alt=""/>
        <div class="pay-result2-hint">
            <img src="/web/images/130/icon11.png" alt=""/>
            <span>支付尚未完成，请尝试重新支付</span>
        </div>
        <div class="pay-result2-btn">确定</div>
    </div>
</div>
<div class="recharge-modal">
    <div class="pay-result2-content clearfix">
        <img class="recharge-close" src="/web/images/130/detail_close.png" alt=""/>
        <div class="pay-result2-hint">
            <img src="/web/images/130/icon11.png" alt=""/>
            <span>请在新打开的充值页面执行充值操作</span>
        </div>
        <div class="recharge-modal-btn" style="margin-left: 110px;">已完成充值</div>
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

    <div class="recharge-box <#if !needRechargeCoin>hide</#if>">
        <button type="button" class="J-recharge" data-url="/userCoin/recharge">余额不足，去充值</button>
    </div>
    <!--去支付-->
    <div class="pay-box <#if needRechargeCoin>hide</#if>">
        <button type="button" class="J-pay" data-orderno="${order.orderNo}">去支付</button>
    </div>
    <!--同意用户协议-->
    <div class="agreement-box">
        <p class="pay-protocol-box"><em class="active"></em><span>我已阅读并同意</span></p>
        <span class="agreement-content"><a href="/web/html/payment_agree.html"
                                           target="_blank">《熊猫中医云课堂用户付费协议》</a></span>
    </div>
</div>
<!--付款提醒-->
<div class="protocol-tip">
    <div class="tip-content">
        <div class="tip-top clearfix">
            <span>报名提示</span>
            <img class="tip-close" src="/web/images/130/detail_close.png" alt=""/>
        </div>
        <div class="tip-bottom clearfix">
            <span>您好，报名课程须同意</span><span class="kuhao1">《</span>
            <a href="javascript:;" class="boxuegu-protocol2">熊猫中医云课堂用户付费协议</a><span class="kuhao1">》</span>
            <div class="tip-btn">同意</div>
        </div>
    </div>
</div>
<!--付款协议-->
<div class="protocol-shadow clearfix">
    <div class="pay-protocol-text">
        <div class="pay-protocol-top clearfix">
            <p>熊猫中医用户付费协议</p>
            <img class="pay-protocol-close" src="/web/images/130/detail_close.png" alt=""/>
        </div>
        <div class="pay-protocol-content">
            <p>欢迎使用熊猫中医云课堂服务，为保障用户权益，请用户在付费之前，详细阅读此服务协议（以下简称“本协议”）所有内容，<em class="indent">特别是加粗部分</em>。当用户点选“【我已经阅读并同意《熊猫中医云课堂-用户付费协议》】”，即表示用户同意并承诺遵守本协议。
            </p>
            <p>本协议内容包括协议正文、本协议下述明确援引的其他协议、熊猫中医云课堂已经发布的或将来可能发布的各类规则。所有规则为本协议不可分割的组成部分，与协议正文具有同等法律效力。</p>
            <h2>第一条 定义</h2>
            <p>1、熊猫中医云课堂指熊猫中医(海口)健康科技有限公司所有和经营的有关教育、学习等数字内容聚合、管理和分发的平台产品，旨在为课程发布者及用户提供教学内容的生成、传播和学习等平台服务。</p>
            <p>2、收费课程是指由熊猫中医云课堂课程研究院研发定制，并有偿提供给用户的视频、文字、图片等内容。</p>
            <p>3、课程发布者是指通过熊猫中医云课堂平台有偿或无偿向用户提供服务或发布其享有合法权益的内容供用户学习、使用的各学科研究院成员。</p>
            <h2>第二条 本协议的修订</h2>
            <p class="indent">
                熊猫中医云课堂有权对本协议进行调整或补充，并在熊猫中医云课堂平台（www.ipandatcm.com）公告。若用户继续使用熊猫中医云课堂服务的，则视为接受该协议的调整或补充，亦或用户有权终止使用该服务。</p>
            <h2>第三条 用户规则</h2>
            <p>
                1、用户可在熊猫中医云课堂平台上浏览已经发布的内容信息（包括但不限于收费课程信息等），如用户希望有偿获得该部分内容或服务，则用户需先登录或注册熊猫中医云课堂帐号或通过页面提示,选用其他第三方帐号进行登录。如用户选用其他第三方帐号进行登录的，用户应保证第三方帐号的稳定性、真实性以及可用性，如因第三方帐号原因（如第三方帐号被封号）致使用户无法登录熊猫中医云课堂的，用户应与第三方帐号的所属公司联系。用户在使用熊猫中医云课堂服务时登录的帐号是熊猫中医云课堂确认用户身份的唯一依据。</p>
            <p>2、用户理解并同意：熊猫中医云课堂平台提供有偿内容或服务实行先付款后使用的方式，用户及时、足额、合法的支付所需的款项是您使用该协议的有偿内容或服务的前提。</p>
            <p style="display:inline-block">3、用户理解并同意，熊猫中医云课堂平台发布的收费课程将采用整体购买的方式，即用户只需购买一次，就可以学习该课程所有已发布或即将发布的课时。<em
                    class="indent">关于已购买的付费课程视频资源使用时效说明如下：</em>
                <span class="indent">3.1 视频资源在排除因不可抗力因素导致的损坏、丢失、下架等之外(包含但不仅限于此)，默认为永久有效；</span>
                <span class="indent">3.2 单次购买成功后的收费课程，可在1年内自己重复学习，无需再次付费；</span>
                <span class="indent">3.3 已购买的付费视频超过1年时限后，熊猫中医云课堂将收取部分视频存储服务费（≤500元/年）。<span class="indent">但请用户注意，在符合相关法律、法规规定的情况下，熊猫中医云课堂有权根据上述规定对您购买的收费课程不予退款。</span></span>
            </p>
            <p class="indent">
                4、用户知悉并同意，用户无权对已购买的课程进行出售、转让、许可或其他方式使除用户自己以外的第三方（包括但不限于自然人、法人或其他组织）使用其已购买的课程。若用户违反本条规定，则熊猫中医云课堂有权视情况采取如下措施：
                <span class="indent">4.1 取消用户继续使用该课程的权利；</span>
                <span class="indent">4.2 限制/冻结用户的帐号；</span>
                <span class="indent">4.3 要求用户退还其通过出售、转让、许可等其他方式取得的收益；</span>
                <span class="indent">4.4 其他熊猫中医云课堂可以采取的救济措施。</span>
            </p>
            <p class="indent">5、用户了解并同意，熊猫中医云课堂可能会基于自身原因（包括但不限于：更新课程内容、改进课程安排）不定期的对收费课程进行更新而无需经过您的事先同意。</p>
            <p>
                6、用户应保管好自己的帐号和密码（包括但不限于：邮箱帐号），如因用户未保管好自己的帐号和密码而对自己、熊猫中医云课堂造成损害的，用户将负全部责任。另外，用户应对用户帐号中的所有活动和事件负全责。用户可随时改变帐号和密码。用户同意若发现有非法使用用户的帐号或出现安全漏洞的情况，立即通告熊猫中医云课堂，熊猫中医云课堂将会尽力予以妥善解决。</p>
            <h2>第四条 其他约定</h2>
            <p>
                1、所有权及知识产权：熊猫中医云课堂平台上所有内容，包括但不限于文字、软件、声音、图片、录像、图表、网站架构、网站画面的安排、网页设计、以及有偿内容或服务均由熊猫中医云课堂或其他权利人依法拥有其知识产权，包括但不限于著作权、商标权、专利权等。非经熊猫中医云课堂或其他权利人书面同意用户不得擅自使用、修改、复制、传播、改变、散布、发行或发表上述内容。如有违反，用户同意承担由此给熊猫中医云课堂、公司或其他权利人造成的一切损失。</p>
            <p>2、通知：所有发给用户的通知都可通过电子邮件、短信、系统消息、常规的信件或在网站显著位置公告的方式进行传送。</p>
            <p>3、本协议适用中华人民共和国的法律（不含冲突法）。当本协议的任何内容与中华人民共和国法律相抵触时，应当以法律规定为准，同时相关内容将按法律规定进行修改或重新解释，而本协议其他部分的法律效力不变。</p>
            <p>4、本协议自发布之日起实施，并构成用户和熊猫中医云课堂之间的共识。</p>
            <p>5、熊猫中医云课堂不行使、未能及时行使或者未充分行使本协议或者按照法律规定所享有的权利，不应被视为放弃该权利，也不影响熊猫中医云课堂在将来行使该权利。</p>
            <p>6、如果用户对本协议内容有任何疑问，请发送邮件至我们的工作人员邮箱：<span class="youxiang">[kefu@ixincheng.com]</span>。</p>

        </div>
    </div>
</div>
<script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="/web/js/artTemplate.js"></script>
<script src="/web/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>

<!--公共头部和底部-->
<script src="/web/js/common/common.js" type="text/javascript" charset="utf-8"></script>
<script src="/web/html/school/school-header/header.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="/web/js/footer.js"></script>
<!--公共头部和底部结束-->
<script src="/web/js/common_msg.js"></script>
<!--登陆结束-->
<script src="/web/js/school/pay-pay.js" type="text/javascript" charset="utf-8"></script>

</body>
<script type="text/javascript">
    var needRechargeCoin = ${needRechargeCoin?c};
    var orderId = "${order.id}";
</script>
</html>