<#import "../page.ftl" as cast/>
<!DOCTYPE html>
<html>
<head lang="en">
    <!--[if IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IEedge"/>
    <meta charset="UTF-8">
    <title>熊猫中医 - 大家专栏</title>
    <link rel="shortcut icon" href="/favicon.ico"/>
    <meta name="keywords"
          content="中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医"/>
    <meta name="description"
          content="熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。"/>
    <meta name="renderer" content="webkit">
    <meta name="baidu-site-verification" content="UHaAQAeAQF"/>
    <link rel="stylesheet" href="/web/css/bootstrap.min.css">
    <link rel="stylesheet" href="/web/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link rel="stylesheet" href="/web/css/componet.css"/>
    <link rel="stylesheet" href="/web/css/ftl-page.css"/>
    <link rel="stylesheet" href="/web/css/header.css"/>
    <link rel="stylesheet" href="/web/css/footer.css"/>

    <!-- 大家专栏 -->
    <link rel="stylesheet" href="/web/css/doctor/column.css"/>
    <link rel="stylesheet" href="/web/font/iconfont.css"/>

    <script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/web/js/artTemplate.js"></script>
    <script src="/web/js/header-top.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/ajax.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>

</head>
<body>
<header>
<#include "../header-body.ftl">
</header>

<!--主体-->
<div class="content_box">
    <div class="content clearfix" id="main">
        <!--左侧-->
        <div class="main_left">
            <div class="doctor_column">
                <div class="doctor_column_top">
                    <h3>大家专栏</h3>
                </div>
                <div class="doctor_column_bottom">
                    <ul id="doctor_column_list">
                    <#list specialColumns.records as specialColumn>
                        <li class="clearfix">
                            <div class="doctor_column_left">
                                <a href="${webUrl}/headline/details/${specialColumn.id}">
                                    <img src="${specialColumn.imgPath}" alt="${specialColumn.title}"/>
                                </a>
                            </div>
                            <div class="doctor_column_inf">
                                <h4>${specialColumn.title}</h4>
                                <p>${specialColumn.content}</p>
                                <span><#if specialColumn.createTime ??>${specialColumn.createTime?string('yyyy-MM-dd')}</#if></span>
                            </div>
                        </li>
                    </#list>
                    </ul>
                </div>
                <div class="doctor_column_more">
                <@cast.page pageNo=specialColumns.current totalPage=specialColumns.pages showPages=5 callUrl="${webUrl}/doctors/specialColumn?&page="/>
                </div>
            </div>
        </div>

        <!--右侧-->
        <div class="main_right">

            <!--专栏作者-->
            <div class="about_doctor">
                <h3>专栏作者</h3>
                <ul>
                <#list authors as author>
                    <li>
                        <a href="/doctors/${author.id}">
                            <div class="column_li">
                                <img src="${author.headPortrait!''}" alt="">
                                <div class="column_li_p">
                                    <p class="p0">${author.doctorName!''}</p>
                                    <p class="p1">${author.province!''} ${author.city!''}</p>
                                </div>
                                <div class="both"></div>
                            </div>
                        </a>
                    </li>
                </#list>
                </ul>
            </div>
        </div>
    </div>
</div>
<#include "../footer.ftl">

</body>
</html>
<script type="text/javascript" src="/web/js/footer.js?v=ipandatcm_1.3"></script>
<script src="/web/js/placeHolder.js"></script>
<script type="text/javascript">
    $(function () {
        $('input').placeholder();
    });
</script>