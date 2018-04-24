<!DOCTYPE html>
<html>
<head lang="en">
    <!--[if IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IEedge"/>
    <meta charset="UTF-8">
    <title>熊猫中医 - ${articleTypeText}</title>
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
    <link rel="stylesheet" href="/web/css/header.css"/>
    <link rel="stylesheet" href="/web/css/doctor_report.css"/>
    <link rel="stylesheet" href="/web/css/footer.css"/>

    <!-- 大家专栏 -->
    <link rel="stylesheet" href="/web/css/colomn.css"/>
    <link rel="stylesheet" href="/web/font/iconfont.css"/>

    <script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/web/js/artTemplate.js"></script>
    <script src="/web/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/jquery.form.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/html5.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/jquery.dotdotdot.js" type="text/javascript" charset="utf-8"></script>
    <!-----引用layer------>
    <script type="text/javascript" src="/web/layer-v2.1/layer/layer.js"></script>
    <!-----引用layer------>
    <script type="text/javascript" src="/web/js/helpers.js"></script>
    <script src="/web/js/ajax.js" type="text/javascript" charset="utf-8"></script>

    <script src="/web/js/header.js?v=ipandatcm_1.3" type="text/javascript" charset="utf-8"></script>
</head>
<body>


<!--主体-->
<div class="content_box">
    <div class="content clearfix" id="main">
        <!--左侧-->
        <div class="main_left">
            <div class="doctor_book">
                <div class="doctor_book_top">
                    <h3>大家专栏</h3>
                </div>
                <div class="doctor_book_bottom">
                    <ul>
                    <#list specialColumns as specialColumn>
                        <li class="clearfix">
                            <div class="doctor_book_left">
                                <img src="${specialColumn.imgPath}" alt="${specialColumn.title}"/>
                            </div>
                            <div class="doctor_book_inf">
                                <h4>${specialColumn.title}</h4>
                                <p>${specialColumn.content}</p>
                                <span>${specialColumn.createTime?string('yyyy-MM-dd')}</span>
                            </div>
                        </li>
                    </#list>
                    </ul>
                </div>

                <div class="doctor_book_more hide">
                    <button>更多</button>
                </div>
            </div>


        </div>


        <!--右侧-->
        <div class="main_right">

            <!--推荐专栏作者-->
            <div class="about_doctor">
                <h3>推荐专栏作者</h3>
                <ul>


                    <!--<li >
                         <div class="colomn_li">
                             <img src="../images/criticism_img.png" alt="">
                             <div class="colomn_li_p">
                                 <p class="p0">段富津</p>
                                 <p class="p1">黑龙江 哈尔滨</p>
                            </div>
                            <div class="both"></div>
                         </div>
                    </li>
                     <li >
                         <div class="colomn_li">
                             <img src="../images/criticism_img.png" alt="">
                             <div class="colomn_li_p">
                                 <p class="p0">段富津</p>
                                 <p class="p1">黑龙江 哈尔滨</p>
                            </div>
                            <div class="both"></div>
                         </div>
                    </li>
                     <li >
                         <div class="colomn_li">
                             <img src="../images/criticism_img.png" alt="">
                             <div class="colomn_li_p">
                                 <p class="p0">段富津</p>
                                 <p class="p1">黑龙江 哈尔滨</p>
                            </div>
                            <div class="both"></div>
                         </div>
                    </li>-->
                <#list specialColumnAuthor as author>
                    <li>
                        <div class="colomn_li">
                            <img src="${author.headPro}" alt="">
                            <div class="colomn_li_p">
                                <p class="p0">段富津</p>
                                <p class="p1">黑龙江 哈尔滨</p>
                            </div>
                            <div class="both"></div>
                        </div>
                    </li>
                </#list>

                </ul>

            </div>

        </div>
    </div>
</div>

<!--大家专栏部分-->
<script type="text/template" id="report_listTpl">
    {{each inf as inf i}}
    <li class="clearfix">
        <div class="doctor_book_left">
            <a href="/web/html/columnDetails?id={{inf.id}}">
                <img src="{{inf.imgPath}}" alt=""/>
            </a>
        </div>
        <div class="doctor_book_inf">
            <h4><a href="/web/html/columnDetails.html?id={{inf.id}}" style="color: #000;">{{inf.title}}</a></h4>
            <p>{{change(inf.content)}}</p>
            <span>{{inf.author}}&nbsp;{{inf.createTime}}</span>
        </div>
    </li>

    {{/each}}
</script>


<!--专栏作者-->
<script type="text/template" id="zhuanlan_zuozheTpl">
    {{each doc as doc i}}
    <li style="position:relative">
        <a href="/web/html/symposiastx.html?doctorId={{doc.id}}" style="position:absolute;width:100%;height:100%"></a>
        <div class="colomn_li">
            <img src={{doc.headPortrait}} alt="">
            <div class="colomn_li_p">
                <p class="p0">{{doc.name}}</p>
                <p class="p1">{{doc.signature}}</p>
            </div>
            <div class="both"></div>
        </div>
    </li>
    {{/each}}
</script>


</body>
</html>
<script src="/web/js/jquery.pagination.js"></script>
<script src="/web/js/colomn.js"></script>
<script type="text/javascript" src="/web/js/footer.js?v=ipandatcm_1.3"></script>
<script src="/web/js/placeHolder.js"></script>
<script type="text/javascript">
    $(function () {
        $('input').placeholder();
    });
</script>