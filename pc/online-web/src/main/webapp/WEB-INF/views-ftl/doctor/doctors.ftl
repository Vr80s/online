<!DOCTYPE html>
<!-- saved from url=(0056)http://dev.ixincheng.com/web/html/bestPractitioners.html -->
<html><head lang="en"><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--[if IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IEedge">

    <title>熊猫中医 - 名医</title>
    <link rel="shortcut icon" href="http://dev.ixincheng.com/favicon.ico">
    <meta name="keywords" content="中医教育,中医传承,中医线下教育，海口中医养生，国粹，传承，中医，中药，心承，熊猫中医">
    <meta name="description" content="熊猫中医云课堂为。课程大纲全新优化，内容有广度、有深度，顶尖讲师全程直播授课。专注整合优势教学资源、打造适合在线学习并能保证教学结果的优质教学产品，同时打造和运营一整套教育生态软件体系，为用户提供满足自身成长和发展要求的有效服务。">
    <meta name="renderer" content="webkit">
    <meta name="baidu-site-verification" content="UHaAQAeAQF">
    <link rel="stylesheet" href="/web/css/bootstrap.min.css">
    <link rel="stylesheet" href="/web/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link rel="stylesheet" href="/web/css/componet.css"/>
    <link rel="stylesheet" href="/web/css/header.css"/>
    <link rel="stylesheet" href="/web/css/doctor.css?v=ipandatcm_1.2.1"/>
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
    <div class="forum-banner">
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
            <div id="left">
                <em></em>
            </div>
            <div id="right">
                <em></em>
            </div>
            <div id="selector" class="selector">
                <span class="cur"></span>
                <span class=""></span>
            </div>
        </div>
        <div class="hot-article hide">
            <span class="hot-article-title">每周免费预约大师号</span>
            <p class="appointment">参与有百分之一的机会获得近期大师预约号</p>
            <div class="hot-article-list">
                <!-- TODO -->

                <div class="appointments clearfix">
                    <ul class="appointment_left">
                        <li class="appointment_doctor_pic"></li>
                        <li class="last_time">揭晓倒计时</li>
                        <li class="Count_down">14：33：30</li>
                    </ul>
                    <ul class="appointment_right">
                        <li>国医大师<span class="doctor_name">段富津</span></li>
                        <li class="appointment_doc_address"><span>黑龙江</span><span>哈尔滨</span></li>
                        <li class="appointment_doc_school"><em></em>哈尔滨中医药大学</li>
                        <li class="appointment_time"><em></em>2017.12.12下午</li>
                        <li class="appointment_btn"><button>免费预约</button></li>
                    </ul>
                </div>

                <div class="appointments clearfix">
                    <ul class="appointment_left">
                        <li class="appointment_doctor_pic"></li>
                        <li class="last_time">揭晓倒计时</li>
                        <li class="Count_down">14：33：30</li>
                    </ul>
                    <ul class="appointment_right">
                        <li>国医大师<span class="doctor_name">段富津</span></li>
                        <li class="appointment_doc_address"><span>黑龙江</span><span>哈尔滨</span></li>
                        <li class="appointment_doc_school"><em></em>哈尔滨中医药大学</li>
                        <li class="appointment_time"><em></em>2017.12.12下午</li>
                        <li class="appointment_btn"><button>免费预约</button></li>
                    </ul>
                </div> </div>
        </div>
    </div>
    <div class="forum-content clearfix">
        <div class="forum-content-left">
            <!-- TODO -->
            <div class="doctor_list clearfix" id="doc_lis1">
                <h2>国医大师</h2>
                <a href="http://dev.ixincheng.com/web/html/practitionerListing.html?name=&amp;type=4" target="_blank">更多<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span></a>
                <ul class="doctor_inf" id="guoyi">
                    <#--<li>-->
                        <#--<a href="/doctors/details/bd753d68f1cf4558a4d56cc39676c8dd" target="_blank"></a>-->
                        <#--<img src="./熊猫中医 - 名医_files/6d07a4d683b844918077dc044ffb59e6.jpg" alt="">-->
                        <#--<h5>邓铁涛&nbsp;<span>主任医师</span></h5>-->
                        <#--<p>暂无</p>-->
                        <#--<p>广东省&nbsp;广州市&nbsp; </p>-->
                    <#--</li>-->
                <#list doctors4.records as doctor>
                    <li>
                        <a href="/doctors/details/${doctor.id}" target="_blank"></a>
                        <img src="${doctor.headPortrait}" alt="">
                        <h5>${doctor.name}&nbsp;<span>${doctor.title?default('暂无')}</span></h5>
                        <p>${doctor.workTime}</p>
                        <p>${doctor.province}&nbsp;${doctor.city}&nbsp; </p>
                    </li>
                </#list>
                </ul>
            </div>


            <div class="doctor_list clearfix" id="doc_lis2">
                <h2>名老中医</h2>
                <a href="http://dev.ixincheng.com/web/html/practitionerListing.html?name=&amp;type=2" target="_blank">更多<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span></a>
                <ul class="doctor_inf" id="minglao">
                <#list doctors2.records as doctor>
                    <li>
                        <a href="/doctors/details/${doctor.id}" target="_blank"></a>
                        <img src="${doctor.headPortrait}" alt="">
                        <h5>${doctor.name}&nbsp;<span>${doctor.title?default('暂无')}</span></h5>
                        <p>${doctor.workTime}</p>
                        <p>${doctor.province}&nbsp;${doctor.city}&nbsp; </p>
                    </li>
                </#list>
                </ul>
            </div>

            <div class="doctor_list clearfix" id="doc_lis3">
                <h2>名青年中医</h2>
                <a href="http://dev.ixincheng.com/web/html/practitionerListing.html?name=&amp;type=1" target="_blank">更多<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span></a>
                <ul class="doctor_inf" id="mingqing">
                <#list doctors1.records as doctor>
                    <li>
                        <a href="/doctors/details/${doctor.id}" target="_blank"></a>
                        <img src="${doctor.headPortrait}" alt="">
                        <h5>${doctor.name}&nbsp;<span>${doctor.title?default('暂无')}</span></h5>
                        <p>${doctor.workTime}</p>
                        <p>${doctor.province}&nbsp;${doctor.city}&nbsp; </p>
                    </li>
                </#list>
                </ul>
            </div>


            <div class="doctor_list clearfix" id="doc_lis4">
                <h2>家传中医</h2>
                <a href="http://dev.ixincheng.com/web/html/practitionerListing.html?name=&amp;type=5" target="_blank">更多<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span></a>
                <ul class="doctor_inf" id="guzhongyi">
                <#list doctors5.records as doctor>
                    <li>
                        <a href="/doctors/details/${doctor.id}" target="_blank"></a>
                        <img src="${doctor.headPortrait}" alt="">
                        <h5>${doctor.name}&nbsp;<span>${doctor.title?default('暂无')}</span></h5>
                        <p>${doctor.workTime}</p>
                        <p>${doctor.province}&nbsp;${doctor.city}&nbsp; </p>
                    </li>
                </#list>
                </ul>
            </div>


            <div class="doctor_list clearfix" id="doc_lis5">
                <h2>少数民族中医</h2>
                <a href="http://dev.ixincheng.com/web/html/practitionerListing.html?name=&amp;type=3" target="_blank">更多<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span></a>
                <ul class="doctor_inf" id="shaoshu">
                <#list doctors3.records as doctor>
                    <li>
                        <a href="/doctors/details/${doctor.id}" target="_blank"></a>
                        <img src="${doctor.headPortrait}" alt="">
                        <h5>${doctor.name}&nbsp;<span>${doctor.title?default('暂无')}</span></h5>
                        <p>${doctor.workTime}</p>
                        <p>${doctor.province}&nbsp;${doctor.city}&nbsp; </p>
                    </li>
                </#list>
                </ul>
            </div>


        </div>
        <div class="forum-content-right">

            <div class="forum-hosJoin hide">
                <p>期待有志于传承和发展中医药的医馆加入</p>
                <a href="javascript:;" id="toDocJoin">医师入驻</a>
            </div>



            <div class="forum-hot-tag">
                <div class="forum-hot-tag-title">医师搜索</div>
                <div class="search_hos_box clearfix">
                    <button class="search_hos_btn">搜索</button>
                    <input class="search_hos" type="text" placeholder="请输入名字搜索医师">
                </div>
                <p>按热门科室搜索</p>
                <ul class="forum-hot-tagGround">
                <#list hotDepartments as hotDepartment>
                    <li>
                        <a href="/doctors/list?departmentId=${hotDepartment.id}" target="_blank">${hotDepartment.name}</a>
                    </li>
                </#list>
                </ul>
            </div>


            <!-- 名师报道 -->
            <div class="school_teacher ">
                <div>
                    <h4>名医报道</h4>
                    <a href="http://dev.ixincheng.com/web/html/practitioneNews.html"><span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span></a>
                </div>

                <ul class="teacher_picList clearfix" id="doctor_baodao">
                <#list recentlyNewsReports as recentlyNewsReport>
                    <li>
                        <a href="/web/html/newsDetails.html?id=315">${recentlyNewsReport.title}</a>
                    </li>
                </#list>
                </ul>
            </div>




            <!-- 名医书籍 -->
            <div class="teacher_books  hide">
                <div id="">
                    <h4>名医著作</h4>
                    <a href="http://dev.ixincheng.com/web/html/pubs.html"><span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span></a>
                </div>

                <ul class="book_list clearfix" id="boos_list">
                    <!-- TODO -->
                    <!--<li>
                        <img src="../images/books.png" alt="">
                        <div>
                        <span class="book_name">药房里买得到的传世名方</span>
                        <h5 class="book_author">佟彤</h5>
                        </div>
                    </li>-->
                <#list recentlyWritings as recentlyWriting>
                    <li>
                        <img src="${recentlyWriting.imgPath}" alt="">
                        <div>
                            <span class="book_name">${recentlyWriting.title}</span>
                            <h5 class="book_author">${recentlyWriting.author}</h5>
                        </div>
                    </li>
                </#list>
                </ul>

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
        $(".doctor").addClass("select");
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