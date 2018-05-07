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

    <title>${tk.title?default('')}熊猫中医医馆</title>
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="keywords" content="${tk.keywords?default('')}中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医">
    <meta name="description" content="熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="/web/css/bootstrap.min.css">
    <link rel="stylesheet" href="/web/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link rel="stylesheet" href="/web/css/componet.css"/>
    <link rel="stylesheet" href="/web/css/header.css"/>
    <link rel="stylesheet" href="/web/css/hospital_list.css"/>
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

<div id="forum" class="clearfix">

    <div class="forum-content clearfix">
        <div class="forum-content-left">
            <div class="forum-content-info">
                <h3 class="hospital_title">搜索结果</h3>
                <div class="search_hos_box">
                    <form action="/clinics/list" method="get">
                        <input type="hidden" name="field" value="${echoMap.field?default('')}"/>
                        <input type="text" placeholder="输入名字搜索医馆" name="name" value="${echoMap.name?default('')}"/>
                        <button type="submit">搜索</button>
                    </form>
                </div>
                <div style="border-top: 1px solid #f0f0f0;" class="search_choose">
                    <div class="hos_search_area" style="position: relative;">
                        <span>医疗领域：</span>
                        <ul class="clearfix" id="hos_search_area" style="height: 73px;overflow: hidden;">
                        <#if !(echoMap.field??)>
                            <li><a href="javascript:;" class="color">全部</a></li>
                        <#else >
                            <li><a href="${webUrl}/clinics/list?page=1&name=${echoMap.name?default("")}">全部</a></li>
                        </#if>

                        <#list fields.records as field>
                            <#if echoMap.field?? && field.id == echoMap.field>
                                <li><a class="color" href="javascript:;" data-fileid="${field.id}">${field.name}</a>
                                </li>
                            <#else >
                                <li>
                                    <a href="${webUrl}/clinics/list?page=1&field=${field.id}&name=${echoMap.name?default("")}"
                                       data-fileid="${field.id}">${field.name}</a></li>
                            </#if>
                        </#list>
                        </ul>
                        <button class="more_areaBtn">更多</button>
                    </div>

                    <div class="hos_search_condition">
                        <span>筛选条件：</span>
                        <ul class="clearfix">
                        <#if echoMap.field??>
                            <li id="hos_search_condition1">
                                <div style="display: inline-block;">
                                    <span style="color: #00BC12 ;margin-left: 5px;"
                                          data-fileid="echoMap.field">${echoMap.fieldText}</span>
                                </div>
                                <a href="${webUrl}/clinics/list?page=1&name=${echoMap.name?default("")}"
                                   style="color: #2cb82c;margin: 0 5px;"></a>
                            </li>
                        <#else >
                            <li id="hos_search_condition3" style="border:none;color: #999;">暂无筛选条件</li>
                        </#if>
                        </ul>
                    </div>
                </div>
                <div style="height: 20px;background-color:#f7f7f7 ;"></div>
                <div id="hospital_list">
                    <div style="border-bottom: 1px solid #ccc;padding: 20px 20px;margin-bottom: 20px;">
                        共找到${clinics.total}家医馆
                    </div>
                <#if clinics.total gt 0>
                    <#list clinics.records as clinic>
                        <div class="hospitals">
                            <a href="${webUrl}/clinics/${clinic.id}" id="${clinic.id}" target="_blank"></a>
                            <#if clinic.medicalHospitalPictures[0]??>
                                <img src="${clinic.medicalHospitalPictures[0].picture}"
                                     style="width: 100%;height: 147px;" alt="${clinic.name}">
                            <#else >
                                <img src="/web/images/hospitalDefault.png" style="width: 100%;height: 147px;"
                                     alt="${clinic.name}">
                            </#if>

                            <div class="hospital_inf">
                                <span class="hospital_name">${clinic.name}</span>
                                <#if clinic.authentication==true>
                                    <span class="hospital_pass">已认证</span>
                                </#if>
                                <div class="hospital_address"><em></em>
                                    <span>${clinic.province}&nbsp;&nbsp;${clinic.city}</span>
                                </div>
                                <div class="hospital_star">
                                    <#if clinic.score??>
                                        <#list 1..clinic.score/1 as t>
                                            <em class="full_star"></em>
                                        </#list>
                                        <#if (5-(clinic.score/1)) gt 0>
                                            <#list 1..(5-(clinic.score/1)) as t>
                                                <em class="gray_star"></em>
                                            </#list>
                                        </#if>
                                    <#else >
                                        <#list 1..5 as t>
                                            <em class="gray_star"></em>
                                        </#list>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </#list>
                <#else>
                    <div style="padding-top:100px;text-align:center">
                        <img src="/web/images/nosearch.png" alt="">
                        <p style="font-size:16px;color:#999">抱歉，没有找到“
                            <span style="color:#00BC12">${echoMap.name?default('')}</span>”相关医馆
                        </p>
                    </div>
                </#if>
                </div>
            </div>
            <!-- 使用该标签 -->
        <@cast.page pageNo=clinics.current totalPage=clinics.pages showPages=5 callUrl="${webUrl}/clinics/list?name="+echoMap.name?default("")+"&field="+echoMap.field?default("")+"&page="/>
        </div>

    </div>
</div>

<#include "../footer.ftl">
</body>
<script src="/web/js/placeHolder.js"></script>
<script type="application/javascript">
    $(function () {
        $(".hospital-tab").addClass("select");
        var areaStatus = 1;
        $('.more_areaBtn').click(function () {
            areaStatus *= -1;
            if (areaStatus < 0) {
                $(this).text('收起')
                $('#hos_search_area').attr('style', 'height:auto')
                $(this).attr('style', 'background:url(/web/images/up_arrow.png) 55px center no-repeat')
            } else {
                $(this).text('更多')
                $('#hos_search_area').attr('style', 'height:73px;overflow: hidden;')
                $(this).attr('style', 'background:url(/web/images/down_arr.png) 55px center no-repeat')
            }

        })
    });

</script>