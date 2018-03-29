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
            <ul class="hot-article-list">
            <#list recClinics as recClinic>
                <li>
                    <a href="/web/html/clinicDetails.html?Id=${recClinic.id}">
                        <#if recClinic_index <= 2>
                            <em class="select">${recClinic_index+1}</em>
                            <#else>
                            <em>${recClinic_index+1}</em>
                        </#if>
                    ${recClinic.city}&nbsp;&nbsp;${recClinic.name}
                    </a>
                </li>
            </#list>
            </ul>
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
                                <#if clinic.authentication==true>
                                    <span class="hospital_pass">已认证</span>
                                </#if>
                                <div class="hospital_address"><em></em>
                                    <span>${clinic.province}&nbsp;&nbsp;${clinic.city}</span>
                                </div>
                                <div class="hospital_star">
                                    <#list 1..clinic.score/1 as t>
                                        <em class="full_star"></em>
                                    </#list>
                                    <#if (5-(clinic.score/1)) gt 0>
                                        <#list 1..(5-(clinic.score/1)) as t>
                                            <em class="gray_star"></em>
                                        </#list>
                                    </#if>
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
                <ul class="forum-hot-tagGround">
                    <#list hotFields as hotField>
                        <li><a href="/web/html/clinicListing.html?field=${hotField.id}" target="_blank">${hotField.name}</a></li>
                    </#list>
                </ul>
            </div>
            <div class="forum-hot-course ">
                <div class="forum-hot-course-title">
                    <span>坐诊医生招募</span>
                </div>
                <div class="hot-course">
                    <div id="box" class="slideBox clearfix">
                        <ul class="course boxContent clearfix" id="doctor_recruit_list">
                            <#list recruits as recruit>
                            <li>
                                <h4><a href="/web/html/clinicDetails.html?Id=${recruit.id}" style="color: #000;">${recruit.position}</a></h4>
                                <a href="/web/html/clinicDetails.html?Id=e5841d2a87814a968a1c1ee1f8040762">${recruit.city}&nbsp;&nbsp;${recruit.hospitalName}</a>
                            </li>
                            </#list>
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