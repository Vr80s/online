<!-- 导入自定义ftl -->
<#import "../page.ftl" as cast/>
<!DOCTYPE html>
<html><head lang="en"><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
    <link rel="stylesheet" href="/web/css/doctor_list.css"/>
    <link rel="stylesheet" href="/web/css/footer.css"/>
    <link rel="stylesheet" href="/web/css/ftl-page.css"/>
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

<div class="content_box">
    <div class="content clearfix" id="main">
        <!--左侧-->
        <div class="main_left">

            <!--名医搜索列表-->
            <div class="doctor clearfix">
                <div class="doctor_search_top clearfix">
                    <h3>${clinic.name}</h3>
                    <#--<div class="doctor_search_ipt">-->
                        <#--<form action="/doctors/list" method="get">-->
                            <#--<input type="hidden" name="departmentId" value="${echoMap.departmentId?default('')}"/>-->
                            <#--<input type="hidden" name="type" value="${echoMap.type?default('')}"/>-->
                            <#--<input type="text" placeholder="输入名字搜索医师" name="name" value="${echoMap.name?default('')}"/>-->
                            <#--<button type="submit">搜索</button>-->
                        <#--</form>-->
                    <#--</div>-->
                </div>
            </div>

            <!--名医列表展示-->
            <div class="doctor_list">
                <div id="search_num" style="height: 60px;line-height: 60px;border-bottom: 1px solid #f0f0f0;">共找到${doctors.total}位名医</div>
                <ul id="doctor_list">
                    <!--<li class="clearfix">
                        <div class="doctor_pic">
                            <img src="../images/doctor_detail/touxiang.png" alt="" />
                        </div>
                        <div class="doctor_inf">
                            <h4>施小墨&nbsp;&nbsp;胡庆余堂国医馆&nbsp;&nbsp; 广东&nbsp;广州</h4>
                            <span></span>
                            <p>科室： <span>中医内科/儿科/肿瘤科</span></p>
                            <p>擅长：<span>中西医结合治肿瘤、脾胃病、血液病、睡眠障碍、糖尿病、内科杂病等。</span> </p>

                        </div>
                    </li>-->
                <#list doctors.records as doctor>
                    <li class="clearfix">
                        <a href="${webUrl}/doctors/${doctor.id}" id="${doctor.id}"></a>
                        <div class="doctor_pic">
                            <img src="${doctor.headPortrait!''}" alt="${doctor.name}">
                        </div>
                        <div class="doctor_inf">
                            <h4>${doctor.name}&nbsp;&nbsp;&nbsp;&nbsp;
                            ${doctor.province?default('')}&nbsp;${doctor.city?default('')}
                            </h4>
                            <span>${doctor.title?default('暂无')}</span>
                            <!--<p>科室： <span>中医内科/儿科/肿瘤科</span></p>-->
                        </div>
                    </li>
                </#list>
                </ul>
                <!-- 使用该标签 -->
                <@cast.page pageNo=doctors.current totalPage=doctors.pages showPages=5 callUrl="${webUrl}/clinics/${clinic.id}/doctors?page="/>
            </div>


        </div>


        <!--右侧-->
        <div class="main_right">

            <!--名医推荐-->
            <div class="about_doctor">
                <h3>名医推荐</h3>
                <ul class="about_doctor_list clearfix" id="doc_rec">
                    <#list recDoctors as doctor>
                        <li>
                            <a href="${webUrl}/doctors/${doctor.id}"></a>
                            <span class="about_doctor_pic">
                                <img src="${doctor.headPortrait!''}" alt="暂无图片">
                            </span>
                            <p>${doctor.name}</p>
                        </li>
                    </#list>
                </ul>
            </div>
        </div>
    </div>
</div>

<#include "../footer.ftl">
</body>
<script src="/web/js/placeHolder.js"></script>
<script type="application/javascript">
    $(function() {
        $(".doctor-tab").addClass("select");
    });
</script>