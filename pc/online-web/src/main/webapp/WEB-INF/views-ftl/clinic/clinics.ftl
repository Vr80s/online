<!DOCTYPE html>
<!-- saved from url=(0056)http://dev.ixincheng.com/doctors -->
<html><head lang="en"><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--[if IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IEedge">

    <title>熊猫中医-医馆</title>
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="keywords" content="中医教育,中医传承,中医线下教育，海口中医养生，国粹，传承，中医，中药，心承，熊猫中医">
    <meta name="description" content="熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="/web/css/bootstrap.min.css">
    <link rel="stylesheet" href="/web/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link rel="stylesheet" href="/web/css/componet.css"/>
    <link rel="stylesheet" href="/web/css/header.css"/>
    <link rel="stylesheet" href="/web/css/hospital.css"/>
    <link rel="stylesheet" href="/web/css/footer.css"/>
    <link rel="stylesheet" href="/web/font/iconfont.css"/>

    <script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/web/js/artTemplate.js"></script>
    <script type="text/javascript" src="/web/js/jquery.SuperSlide.2.1.1.js"></script>
    <script src="/web/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/ajax.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/header-top.js" type="text/javascript" charset="utf-8"></script>

</head>
<body>
<!--提示-->
<div class="webSiteNotice" style="display:none;">
    <div class="innerBox clearfix">
        <i class="iconfont icon-xiaoxilaba xiaoxilaba"></i>
        <span class="noticeInfo"></span>
        <i class="iconfont icon-guanbi noticeClose"></i>
    </div>
</div>
<header>
    <#include "../header-body.ftl">
</header>

<div id="tip" style="display: none;">
    您已完成了医馆注册，不能进行医师注册!
</div>


<div id="forum" class="clearfix">
    <div class="forum-banner clearfix">
        <div class="banner">
            <ul id="slider" class="slider">
                <#list banners as banner>
                    <#if banner_index==0>
                    <li style="z-index: 2;">
                        <a href="${banner.imgHref}" target="_blank" style="background:url(${banner.imgPath})no-repeat top center;background-size:100% 100%">
                        <#else>
                    <li>
                        <a href="${banner.imgHref}" target="_blank" style="background:url(${banner.imgPath})no-repeat top center;%">
                        </#if>
                        <div class="image-overlay">
                        </div>
                        </a>
                    </li>
                </#list>
            </ul>
            <div id="left"><em></em></div>
            <div id="right"><em></em></div>
            <div id="selector" class="selector">

                <span class="cur"></span><span class=""></span></div>
        </div>
        <div class="hot-article">
            <span class="hot-article-title">优秀医馆</span>
            <ul class="hot-article-list"><li><a href="/web/html/clinicDetails.html?Id=932984f654cd41e9b6baef9451763912"><em class="select">1</em>北京市&nbsp;&nbsp;慈方中医馆北苑店</a></li><li><a href="/web/html/clinicDetails.html?Id=a795f0c6adfb40ad9f8469d014ffe245"><em class="select">2</em>深圳市&nbsp;&nbsp;深圳九味堂</a></li><li><a href="/web/html/clinicDetails.html?Id=5362f094fee3498d934b125b98c05595"><em class="select">3</em>深圳市&nbsp;&nbsp;五味中医馆</a></li><li><a href="/web/html/clinicDetails.html?Id=f5750b879b5441a68c288caf8366f80f"><em>4</em>普洱市&nbsp;&nbsp;淞茂中医馆</a></li><li><a href="/web/html/clinicDetails.html?Id=72e3e519f6d44f98a296fc2432a69be4"><em>5</em>北京市&nbsp;&nbsp;合顺堂中医馆</a></li><li><a href="/web/html/clinicDetails.html?Id=5f2d1a8d5b414aff86c1b05c4c7a9db6"><em>6</em>北京市&nbsp;&nbsp;博爱堂</a></li><li><a href="/web/html/clinicDetails.html?Id=11f500e6cb4b49be8a77d9e76531c595"><em>7</em>北京市&nbsp;&nbsp;孔医堂</a></li></ul>
        </div>
    </div>
    <div class="forum-content clearfix">
        <div class="forum-content-left">
            <div class="forum-content-info">
                <h3 class="hospital_title">医馆</h3>
                <div id="hospital_list">
                    <#list clinics.records as clinic>
                        <div class="hospitals">
                            <a href="/web/html/clinicDetails.html?Id=08a08cf4f87848298576838206653c39" id="08a08cf4f87848298576838206653c39" target="_blank"></a>
                            <img src="${clinic.medicalHospitalPictures[0].picture}" style="width: 100%;height: 147px;" alt="${clinic.name}">
                            <div class="hospital_inf">
                                <span class="hospital_name">${clinic.name}</span>
                                <span class="hospital_pass">已认证</span>
                                <div class="hospital_address"><em></em>
                                    <span>${clinic.name}</span>
                                </div>
                                <div class="hospital_star">
                                    <em class="full_star"></em>
                                    <em class="full_star"></em>
                                    <em class="full_star"></em>
                                    <em class="full_star"></em>
                                    <em class="full_star"></em>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>

                <div class="more_hospital">
                    <button>更多</button>
                </div>
            </div>
            <div class="pages">
                <div id="Pagination"></div>
            </div>
        </div>
        <div class="forum-content-right">

            <div class="forum-hosJoin">
                <p>期待有志于传承和发展中医药的医馆加入</p>
                <a href="javascript:;" id="toHosJoin">医馆入驻</a>
            </div>


            <div class="forum-hot-tag">
                <div class="forum-hot-tag-title">医馆搜索</div>
                <div class="search_hos_box clearfix">
                    <button class="search_hos_btn">搜索</button>
                    <input class="search_hos" type="text" placeholder="请输入名字搜索医馆">
                </div>
                <p>按擅长领域搜索</p>
                <ul class="forum-hot-tagGround"><li><a href="/web/html/clinicListing.html?name=&amp;field=95a9064d30d849a683ced18607cded32" target="_blank">妇科</a></li><li><a href="/web/html/clinicListing.html?name=&amp;field=4afdaa92eef44489ba9bfa8a5d13fa5d" target="_blank">失眠</a></li><li><a href="/web/html/clinicListing.html?name=&amp;field=3f1a219d345c4b3ca1d4880a9e88878e" target="_blank">月经不调</a></li><li><a href="/web/html/clinicListing.html?name=&amp;field=15df5e58e2ba4353a74331b4910b72b9" target="_blank">高血压</a></li><li><a href="/web/html/clinicListing.html?name=&amp;field=fd9758320e234fee9d81a20b65b30996" target="_blank">内科</a></li><li><a href="/web/html/clinicListing.html?name=&amp;field=4a763366c3684693b30f8558982dde39" target="_blank">不孕不育</a></li><li><a href="/web/html/clinicListing.html?name=&amp;field=e73e48fad2064aa08b6585aa2a1e9a0c" target="_blank">脾胃病</a></li><li><a href="/web/html/clinicListing.html?name=&amp;field=cdca88eddb2b4c83b40d5398511cebe6" target="_blank">冠心病</a></li><li><a href="/web/html/clinicListing.html?name=&amp;field=0100c335457e47e3ab0d88e433222c4c" target="_blank">高血脂</a></li><li><a href="/web/html/clinicListing.html?name=&amp;field=fc3695a88d124f518b29c0151283eb62" target="_blank">结节病</a></li></ul>
            </div>
            <div class="forum-hot-course ">
                <div class="forum-hot-course-title">
                    <span>坐诊医生招募</span>
                </div>
                <div class="hot-course">
                    <div id="box" class="slideBox clearfix">
                        <ul class="course boxContent clearfix" id="doctor_recruit_list">
                            <li>
                                <h4><a href="/web/html/clinicDetails.html?Id=e5841d2a87814a968a1c1ee1f8040762" style="color: #000;">招募药剂师</a></h4>
                                <a href="/web/html/clinicDetails.html?Id=e5841d2a87814a968a1c1ee1f8040762">北京市&nbsp;&nbsp;豫医堂1号馆</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "../footer.ftl">
</body>
<script src="/web/js/jquery.pagination.js"></script>
<script src="/web/js/placeHolder.js"></script>
<script type="application/javascript">
    $(function(){
        $(".doctor-tab").addClass("select");
    });
    //banner
    function init() {
        var $sliders = $('#slider li');
        var $selector = $('#selector');
        var $selectors = $('#selector span');
        var $left = $('#left');
        var $right = $('#right');
        //自动切换
        var step = 0;

        function autoChange() {
            if (step === $sliders.length) {
                step = 0;
            };
            $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
            $selectors.eq(step).addClass('cur').siblings().removeClass('cur');
            step++;
        }

        var timer = window.setInterval(autoChange, 2000);

        //点击圆圈切换
        $selector.on('click', function (e) {
            var $target = $(e.target);
            if ($target.get(0).tagName === 'SPAN') {
                window.clearInterval(timer);
                $target.addClass('cur').siblings().removeClass('cur');
                step = $target.index();
                $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
                timer = window.setInterval(autoChange, 2000);
            }
        });

        //点击左右按钮切换
        $left.on('click', function () {
            window.clearInterval(timer);
            step--;
            if (step < 0) {
                step = $sliders.length - 1;
                $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
                $selectors.eq(step).addClass('cur').siblings().removeClass('cur');
            } else {
                $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
                $selectors.eq(step).addClass('cur').siblings().removeClass('cur');
            }
            timer = window.setInterval(autoChange, 2000);
        });
        $right.on('click', function () {
            window.clearInterval(timer);
            step++;
            if (step > $sliders.length - 1) {
                step = 0;
                $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
                $selectors.eq(step).addClass('cur').siblings().removeClass('cur');
            } else {
                $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
                $selectors.eq(step).addClass('cur').siblings().removeClass('cur');
            }
            timer = window.setInterval(autoChange, 2000);
        })
    }
    init();
</script>