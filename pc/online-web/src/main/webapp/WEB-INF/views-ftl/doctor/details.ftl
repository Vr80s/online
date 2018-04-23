<!DOCTYPE html>
<html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--[if IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IEedge">

    <title>${tk.title?default('')}熊猫中医名医</title>
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="keywords" content="${tk.keywords?default('')}中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医">
    <meta name="description" content="熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="/web/css/bootstrap.min.css">
    <link rel="stylesheet" href="/web/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link rel="stylesheet" href="/web/css/componet.css"/>
    <link rel="stylesheet" href="/web/css/header.css"/>
    <link rel="stylesheet" href="/web/css/doctor_details.css?v=ipandatcm_1.2.1"/>
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
<header>
<#include "../header-body.ftl">
</header>
<div class="header" id="doctor_detail_header">
    <div class="header_inf content">
        <div class="header_inf_left">
            <img src="${doctor.headPortrait}" alt="${doctor.name}">
            <!--<img src=../images/doctor_detail/xunzhang.png alt="">-->
        </div>
        <div class="header_inf_right">
            <span>${doctor.name}  </span><span>${doctor.title?default('暂无')}</span>
            <div class="doctor_inf1">
                <span class="zhiwu"><em></em>${doctor.title?default('暂无')}</span>
            <#if doctor.medicalHospitalVo ??>
                <span class="yiguan"><em></em>${doctor.medicalHospitalVo.name}</span>
            </#if>
                <span class="dizhi"><em></em>${doctor.city}</span>
            </div>
            <div class="doctor_inf2">
                <p>主治：
                <#list doctor.fields as field>
                    <span>${field.name}</span>
                </#list>
                </p>
            </div>
        </div>
    </div>
</div>
<div class="content_box">
    <div class="content clearfix" id="main">
        <!--左侧-->
    <div class="main_left">
        <!--医生简介-->
        <div class="doctor_int">
            <p>${doctor.description}</p>
        </div>
        <!--坐诊医馆-->
    <#if doctor.medicalHospital??>
        <div id="doc_hospital">
            <div class="hospital clearfix">
                <a class="to_hospital_detail"></a>
                <h3>坐诊医馆</h3>
                <div class="hospital_pic">
                    <img src="${doctor.medicalHospital.medicalHospitalPictures[0].picture}"
                         alt="${doctor.medicalHospital.name}">
                </div>
                <div class="hospital_inf">
                    <p>${doctor.medicalHospital.name}</p>
                    <p>预约电话：<span>${doctor.medicalHospital.tel?default('暂无')}</span></p>
                    <p>坐诊时间：<span
                            style="vertical-align: text-top;display: inline-block;width: 455px;">${doctor.medicalHospital.workTime?default('暂无')}</span>
                    </p>
                    <p>地 &nbsp;&nbsp;&nbsp;&nbsp; 址：
                        <span style="vertical-align: text-top;display: inline-block;width: 455px;">
                                    <span>${doctor.medicalHospital.detailedAddress?default('暂无')}</span>
                                </span>
                    </p>
                </div>
            </div>
        </div>
    </#if>

        <!--课程-->
        <div class="class clearfix hide">
            <div class="class_top">
                <span>课程</span>
                <a href="javascript:;">
                    更多&nbsp;&gt;
                </a>
            </div>

            <div class="class_bottom">
                <div class="class_inf">
                    <div class="class_inf_pic">
                        <img src="../images/doctor_detail/hospital_pic.png" alt="">
                    </div>
                    <div class="class_inf_bottom">
                        <p class="class_title">冯世纶：中医外治四期班第二季</p>
                        <p class="class_address"><em></em>北京</p>
                        <p class="class_pirce_person">
                            <span class="class_pirce">￥400</span>
                            <span class="class_person"><em></em>30</span>
                        </p>
                    </div>
                </div>


                <div class="class_inf">
                    <div class="class_inf_pic">
                        <img src="../images/doctor_detail/hospital_pic.png" alt="">
                    </div>
                    <div class="class_inf_bottom">
                        <p class="class_title">冯世纶：中医外治四期班第二季</p>
                        <p class="class_address"><em></em>北京</p>
                        <p class="class_pirce_person">
                            <span class="class_pirce">￥400</span>
                            <span class="class_person"><em></em>30</span>
                        </p>
                    </div>
                </div>


                <div class="class_inf">
                    <div class="class_inf_pic">
                        <img src="../images/doctor_detail/hospital_pic.png" alt="">
                    </div>
                    <div class="class_inf_bottom">
                        <p class="class_title">冯世纶：中医外治四期班第二季</p>
                        <p class="class_address"><em></em>北京</p>
                        <p class="class_pirce_person">
                            <span class="class_pirce">￥400</span>
                            <span class="class_person"><em></em>30</span>
                        </p>
                    </div>
                </div>


                <div class="class_inf">
                    <div class="class_inf_pic">
                        <img src="../images/doctor_detail/hospital_pic.png" alt="">
                    </div>
                    <div class="class_inf_bottom">
                        <p class="class_title">冯世纶：中医外治四期班第二季</p>
                        <p class="class_address"><em></em>北京</p>
                        <p class="class_pirce_person">
                            <span class="class_pirce">￥400</span>
                            <span class="class_person"><em></em>30</span>
                        </p>
                    </div>
                </div>


                <div class="class_inf">
                    <div class="class_inf_pic">
                        <img src="../images/doctor_detail/hospital_pic.png" alt="">
                    </div>
                    <div class="class_inf_bottom">
                        <p class="class_title">冯世纶：中医外治四期班第二季</p>
                        <p class="class_address"><em></em>北京</p>
                        <p class="class_pirce_person">
                            <span class="class_pirce">￥400</span>
                            <span class="class_person"><em></em>30</span>
                        </p>
                    </div>
                </div>

                <div class="class_inf">
                    <div class="class_inf_pic">
                        <img src="../images/doctor_detail/hospital_pic.png" alt="">
                    </div>
                    <div class="class_inf_bottom">
                        <p class="class_title">冯世纶：中医外治四期班第二季</p>
                        <p class="class_address"><em></em>北京</p>
                        <p class="class_pirce_person">
                            <span class="class_pirce">￥400</span>
                            <span class="class_person"><em></em>30</span>
                        </p>
                    </div>
                </div>

            </div>

        </div>

        <!--专栏-->
    <#if specialColumns.total gt 0>
        <div class="zhuanlan clearfix" id="zhuanlan">
            <div class="class_top">
                <span>专栏-待完成</span>
                <a href="${webUrl}/web/html/columnListing.html?doctorId=9e9bd074e0e2461eb44a77da6b9b8199"
                   id="more_zhuanlan">
                    更多<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>
                </a>
            </div>

            <ul class="zhuanlan_list" id="zhuanlan_list">
                <#list specialColumns.records as specialColumn>
                    <li class="clearfix">
                        <div class="zhuanlan_left">
                            <a href="${webUrl}/headline/details/${specialColumn.id}">
                                <img src="${specialColumn.imgPath}" alt="${specialColumn.title}">
                                <a href="${webUrl}/headline/details/${specialColumn.id}">
                        </div>
                        <div class="zhuanlan_right">
                            <h3><a href="${webUrl}/headline/details/${specialColumn.id}" style="color: #000;">${specialColumn.title}</a></h3>
                            <p>${specialColumn.content}</p>
                            <span>${(specialColumn.createTime?string("yyyy-MM-dd"))!}</span>
                        </div>
                    </li>
                </#list>
            </ul>
        </div>
    </#if>
    <#if newsReports.total gt 0>
        <!--媒体报道-->
        <div class="zhuanlan clearfix" id="media_report">
            <div class="class_top">
                <span>媒体报道-待完成</span>
                <a href="${webUrl}/web/html/practitioneNews.html?doctorId=9e9bd074e0e2461eb44a77da6b9b8199"
                   class="more_madia_report">
                    更多<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>
                </a>
            </div>

            <ul class="zhuanlan_list" id="meaid_list">
                <#list newsReports.records as newsReport>
                    <li class="clearfix">
                        <div class="zhuanlan_left">
                            <a href="${webUrl}/headline/details/${newsReport.id}"><img src="${newsReport.imgPath}"
                                                                                       alt="${newsReport.title}"></a>
                        </div>
                        <div class="zhuanlan_right">
                            <h3><a href="${webUrl}/headline/details/${newsReport.id}"
                                   style="color: #000;">${newsReport.title}</a></h3>
                            <p>${newsReport.content}</p>
                            <span>${(newsReport.createTime?string("yyyy-MM-dd"))!}</span>
                        </div>
                    </li>
                </#list>
            </ul>
        </div>

    </div>
    </#if>


        <!--右侧-->
        <div class="main_right hide">
            <!--帐号认领-->
            <div class="renling hide">
                <p>若您是李辅仁本人，可以认领此帐号</p>
                <a href="javascript:;">认领</a>
            </div>
            <!--著作-->
            <div class="zhuzuo clearfix hide">
                <div class="zhuzuo_title">
                    <h3>著作-待完成</h3>
                    <a href="${webUrl}/web/html/pubs.html?doctorId=9e9bd074e0e2461eb44a77da6b9b8199"><span
                            class="glyphicon glyphicon-menu-right" aria-hidden="true"></span></a>
                </div>

                <div id="zhuzuo_list">
                    <!--<div class="zhuzuo_left">
                        <img src="../images/doctor_detail/book2.png" alt="">
                        <p>施今墨对药</p>
                    </div>-->
                <#list writings as writing>
                    <div class="zhuzuo_left">
                        <img src="${writing.imgPath}" alt="">
                        <p>${writing.title}</p>
                    </div>
                </#list>
                </div>

            </div>
            <!--相关医师-->
            <div class="about_doctor hide">
                <h3>相关医师</h3>
                <ul class="about_doctor_list clearfix">
                    <li>
                        <span class="about_doctor_pic">
                             <img src="../images/doctor_detail/touxiang.png" alt="">
                        </span>
                        <p>朱春苏</p>

                    </li>

                    <li>
                        <span class="about_doctor_pic">
                             <img src="../images/doctor_detail/touxiang.png" alt="">
                        </span>
                        <p>朱春苏</p>

                    </li>

                    <li>
                        <span class="about_doctor_pic">
                             <img src="../images/doctor_detail/touxiang.png" alt="">
                        </span>
                        <p>朱春苏</p>

                    </li>

                    <li>
                        <span class="about_doctor_pic">
                             <img src="../images/doctor_detail/touxiang.png" alt="">
                        </span>
                        <p>朱春苏</p>

                    </li>

                    <li>
                        <span class="about_doctor_pic">
                             <img src="../images/doctor_detail/touxiang.png" alt="">
                        </span>
                        <p>朱春苏</p>

                    </li>

                    <li>
                        <span class="about_doctor_pic">
                             <img src="../images/doctor_detail/touxiang.png" alt="">
                        </span>
                        <p>朱春苏1</p>

                    </li>

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
    $(function () {
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
            }
            ;
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