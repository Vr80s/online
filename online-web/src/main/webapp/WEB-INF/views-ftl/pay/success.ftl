<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="X-UA-Compatible" content="IEedge"/>
    <meta charset="UTF-8">
    <title>支付成功-熊猫中医</title>
    <link rel="shortcut icon" href="/favicon.ico"/>
    <meta name="keywords" content="中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医"/>
    <meta name="description"
          content="熊猫中医云课堂为。课程大纲全新优化，内容有广度、有深度，顶尖讲师全程直播授课。专注整合优势教学资源、打造适合在线学习并能保证教学结果的优质教学产品，同时打造和运营一整套教育生态软件体系，为用户提供满足自身成长和发展要求的有效服务。"/>
    <meta name="renderer" content="webkit">
    <meta name="baidu-site-verification" content="UHaAQAeAQF"/>
    <link rel="stylesheet" href="/web/css/registration.css"/>
    <!--公共头部和底部样式-->
    <link rel="stylesheet" href="/web/html/school/school-header/header.css"/>
    <link rel="stylesheet" href="/web/css/footer.css"/>
    <link rel="stylesheet" href="/web/font/iconfont.css"/>
    <!--公共头部和底部样式结束-->
    <!--登陆的bootstrap样式-->
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link href="/web/bootstrap-select/bootstrap.min.css" rel="stylesheet">
    <!--登陆的bootstrap样式结束-->
    <link rel="stylesheet" href="/web/css/school/pay-success.css"/>
</head>
<body>
<header>
<#include "../school/header-body.ftl">
</header>
<div class="wp">
    <div class="main-top">
        <div class="buy-icon">
            <img src="/web/images/buy-success.png"/>
            <span>报名成功</span>
        </div>
        <div class="buy-information">
        <#list courses as course >
            <div class="cover-img z">
                <img src="${course.smallImgPath}"/>
            </div>
            <p class="z">${course.gradeName}</p>
            <div class="go-play">
                <button type="button" class="J-course-detail" data-courseId="${course.id}">查看</button>
            </div>
        </#list>
        </div>

        <p class="z study-core">查看已购课程，<a href="/web/html/personal-center/personal-index.html">请前往学习中心</a></p>
        <p class="z class-list">继续寻找感兴趣的课程，<a href="/courses/recommendation">请前往学堂</a></p>
    </div>
<#if applyInfo??>
    <!--判断线下课回显-->
    <div class="registration">
        <div class="registration-title">报名信息</div>
        <div class="registration-border"></div>
        <div class="registration-name">
            <div class="registration-compellation">姓名</div>
            <div class="registration-show">${applyInfo.realName}</div>
            <div class="both"></div>
        </div>
        <div class="registration-mobile">
            <div class="registration-cell">手机号</div>
            <div class="registration-number">${applyInfo.mobile}</div>
            <div class="both"></div>
        </div>
        <div class="registration-weixin">
            <div class="registration-weix">微信号</div>
            <div class="registration-wx">${applyInfo.wechatNo}</div>
            <div class="both"></div>
        </div>
        <div class="registration-sex">
            <div class="registration-sexs">性别</div>
            <div class="registration-gender"><#if applyInfo.sex == 1>男<#else>女</#if></div>
            <div class="both"></div>
        </div>
    </div>
</#if>

    <!--猜你喜欢-->
    <#if (recommendCourses?size>0) >
        <div class="main-bottom">
            <h4 class="video-title">猜你喜欢</h4>

        <#list recommendCourses as course>
            <div class="course clearfix">
                <a style="cursor:pointer" href="/courses/${course.id}/info" target="_blank">
                    <div class="img">
                        <img src="${course.bigImgPath}">
                    </div>
                    <!--<span class="classCategory">音频</span>-->
                    <div class="detail">
                        <p class="title" data-text="音频测试3" title="音频测试3">${course.gradeName}</p>
                        <p class="timeAndTeac"><span>讲师：<span class="teacher">${course.name}</span></span>
                        </p>
                        <p class="info clearfix">
                            <span>
                                <span class="price"><#if course.currentPrice gt 0>${course.currentPrice}
                                    <span>熊猫币</span><#else >
                                    免费</#if></span>
                            </span>
                            <span class="stuCount">
                                <img src="/web/images/studentCount.png" alt="">
                                <span class="studentCou">${course.learndCount!"0"}</span>
                            </span>
                        </p>
                    </div>
                </a>
            </div>
        </#list>
        </div>
    </#if>
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
<script src="/web/js/school/pay-success.js" type="text/javascript" charset="utf-8"></script>

</body>
</html>