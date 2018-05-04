<#import "../page.ftl" as cast/>
<!DOCTYPE html>
<html>
<head lang="en">
    <!--[if IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IEedge"/>
    <meta charset="UTF-8">
    <title>熊猫中医 - 名医著作</title>
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

    <link rel="stylesheet" href="/web/css/doctor/writing.css"/>
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
            <div class="doctor_writing">
                <div class="doctor_writing_top">
                    <h3>名医著作</h3>
                </div>
                <div class="doctor_writing_bottom">
                    <ul id="doctor_writing_list">
                    <#list writings.records as writing>
                        <li class="clearfix">
                            <div class="doctor_writing_left">
                                <a href="${webUrl}/headline/details/${writing.id}">
                                    <img src="${writing.imgPath}" alt="${writing.title}"/>
                                </a>
                            </div>
                            <div class="doctor_writing_inf">
                                <h4>${writing.title}</h4>
                                <p>作者:${writing.author}</p>
                                <p>${writing.content}</p>
                            </div>
                        </li>
                    </#list>
                    </ul>
                </div>

                <div class="doctor_writing_more">
                <@cast.page pageNo=writings.current totalPage=writings.pages showPages=5 callUrl="${webUrl}/doctors/writing?page="/>
                </div>
            </div>
        </div>

        <!--右侧-->
        <div class="main_right">

            <!--名医推荐-->
            <div class="about_doctor">
                <h3>名医推荐</h3>
                <ul class="about_doctor_list clearfix" id="doc_rec">
                <#list doctors as doctor>
                    <li>
                        <a href="${webUrl}/doctors/${doctor.id}">
                        <span class="about_doctor_pic">
                                <img src="${doctor.headPortrait}" alt="暂无图片">
                            </span>
                        <p>${doctor.name}</p>
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
        $(".doctor-tab").css({"background":"#2cb82c"})
    });
</script>