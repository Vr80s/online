<!-- 导入自定义ftl -->
<#import "../page.ftl" as cast/>
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
                    <h3>筛选</h3>
                    <div class="doctor_search_ipt">
                        <form action="/doctors/list" method="get">
                            <input type="hidden" name="departmentId" value="${echoMap.departmentId?default('')}"/>
                            <input type="hidden" name="type" value="${echoMap.type?default('')}"/>
                            <input type="text" placeholder="输入名字搜索医师" name="name" value="${echoMap.name?default('')}"/>
                            <button type="submit">搜索</button>
                        </form>
                    </div>
                </div>
                <div class="doctor_search_bottom">
                    <div class="doctor_search_class">
                        <!--<span>分类：</span>-->
                        <span style="padding-left: 28px;">分类：</span>
                        <ul class="clearfix" id="doctor_search_class">
                        <#if echoMap.type?default("")?trim?length == 0>
                            <li><a href="javascript:;" class="color">全部</a></li>
                        <#else >
                            <li>
                                <a href="${webUrl}/doctors/list?page=1&name=${echoMap.name?default("")}&departmentId=${echoMap.departmentId?default("")}">全部</a>
                            </li>
                        </#if>
                        <#list doctorTypeList as doctorType>
                            <#if echoMap.type?? && doctorType.code == echoMap.type>
                                <li><a href="javascript:;" data-type="${doctorType.code}"
                                       class="color">${doctorType.value}</a></li>
                            <#else>
                                <li>
                                    <a href="${webUrl}/doctors/list?page=1&name=${echoMap.name?default("")}&type=${doctorType.code?default("")}&departmentId=${echoMap.departmentId?default("")}"
                                       data-type="${doctorType.code}">${doctorType.value}</a></li>
                            </#if>
                        </#list>
                        </ul>
                    </div>
                    <div class="doctor_search_keshi">
                        <span style="padding-left: 28px;">科室：</span>
                        <ul class="clearfix" id="doctor_search_keshi">
                        <#if echoMap.departmentId?default("")?trim?length == 0>
                            <li><a href="javascript:;" class="color">全部</a></li>
                        <#else >
                            <li>
                                <a href="${webUrl}/doctors/list?page=1&name=${echoMap.name?default("")}&type=${echoMap.type?default("")}">全部</a>
                            </li>
                        </#if>
                        <#list departments.records as department>
                            <#if echoMap.departmentId?? &&department.id == echoMap.departmentId>
                                <li><a href="javascript:;" class="color"
                                       data-id="${department.id}">${department.name}</a></li>
                            <#else>
                                <li>
                                    <a href="${webUrl}/doctors/list?page=1&name=${echoMap.name?default("")}&type=${echoMap.type?default("")}&departmentId=${department.id?default("")}"
                                       data-id="${department.id}">${department.name}</a></li>
                            </#if>
                        </#list>
                        </ul>
                    </div>
                    <div class="doctor_search_condition">
                        <span>筛选条件：</span>
                        <ul class="clearfix">
                        <#if echoMap.type??>
                            <li id="doctor_search_condition1" class="">分类：
                                <div style="display: inline-block;"><span
                                        data-type="${echoMap.type}">${echoMap.typeText}</span></div>
                                <a href="${webUrl}/doctors/list?page=1&name=${echoMap.name?default("")}&departmentId=${echoMap.departmentId?default("")}"></a>
                            </li>
                        </#if>
                        <#if echoMap.departmentId??>
                            <li id="doctor_search_condition2" class="">科室：
                                <div style="display: inline-block;"><span
                                        data-id="${echoMap.departmentId}">${echoMap.departmentText}</span></div>
                                <a href="${webUrl}/doctors/list?page=1&name=${echoMap.name?default("")}&type=${echoMap.type?default("")}"></a>
                            </li>
                        </#if>
                        <#if !(echoMap.departmentId?? || echoMap.type??)>
                            <li id="doctor_search_condition3" style="border:none;color: #999;" class="">暂无筛选条件</li>
                        </#if>
                        </ul>
                    </div>
                </div>
            </div>

            <!--名医列表展示-->
            <div class="doctor_list">
                <div id="search_num" style="height: 60px;line-height: 60px;border-bottom: 1px solid #f0f0f0;">
                    共找到${doctors.total}位名医
                </div>
                <ul id="doctor_list">
                <#if doctors.total gt 0>
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
                <#else>
                    <div style="padding-top:100px;text-align:center;padding-bottom: 100px;">
                        <img src="/web/images/nosearch.png" alt="">
                        <p style="font-size:16px;color:#999">抱歉，没有找到“
                            <span style="color:#00BC12">${echoMap.name?default('')}</span>”相关医师
                        </p>
                    </div>
                </#if>
                </ul>
                <!-- 使用该标签 -->
            <@cast.page pageNo=doctors.current totalPage=doctors.pages showPages=5 callUrl="${webUrl}/doctors/list?name="+echoMap.name?default("")+"&type="+echoMap.type?default("")+"&departmentId="+echoMap.departmentId?default("")+"&page="/>
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
    $(function () {
        $(".doctor-tab").addClass("select");
    });
</script>