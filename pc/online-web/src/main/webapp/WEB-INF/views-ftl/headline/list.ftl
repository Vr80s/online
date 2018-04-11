<#import "../page.ftl" as cast/>
<!DOCTYPE html>
<html>
<head lang="en"><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--[if IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IEedge">

    <title>${tk.title?default('')}熊猫中医头条</title>
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="keywords" content="${tk.keywords?default('')}中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医">
    <meta name="description" content="熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="/web/css/bootstrap.min.css">
    <link rel="stylesheet" href="/web/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link rel="stylesheet" href="/web/css/componet.css"/>
    <link rel="stylesheet" href="/web/css/header.css"/>
    <link rel="stylesheet" href="/web/css/news.css"/>
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

<div id="forum" class="clearfix">
    <div class="forum-content clearfix">
        <div class="forum-content-left">
            <div class="forum-type" style="height:62px;">
                <ul class="forum-content-tag clearfix">
                    <#list articleType as at>
                        <#if at.id==echoMap.type>
                            <a href="/headline/list/${at.id}" style="display: block;"><li class="select" ><em class="select"></em>${at.name}</li></a>
                        <#else >
                            <a href="/headline/list/${at.id}" style="display: block;"><li ><em class="select1"></em>${at.name}</li></a>
                        </#if>
                    </#list>
                </ul>
            </div>
            <div class="forum-content-info">
                <#list articles.records as article>
                    <div class="forum-info clearfix">
                        <a href="/headline/details/${article.id}" target="_blank">
                            <img class="forum-info-left" src="${article.imgPath}" alt=""/>
                        </a>
                        <div class="forum-info-right">
                            <div class="forum-info-title">
                                <a href="/headline/details/${article.id}" target="_blank">${article.title}</a>
                            </div>
                            <div class="forum-info-content dot-ellipsis">
                            ${article.content}
                            </div>
                            <div class="forum-info-tags">
                                <span>${article.source}<em></em>${(article.createTime?string("yyyy-MM-dd"))!}</span>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
            <!-- 使用该标签 -->
        <@cast.page pageNo=articles.current totalPage=articles.pages showPages=5 callUrl="/headline/list/"+echoMap.type?default("")+"?page="/>

        </div>
        <div class="forum-content-right" style="position: absolute; left: 880px;">
            <!--推荐专栏作者-->
            <div class="zhuanlan_zuozhe hide" id="zhuanlan_bigbox">
                <h4>推荐专栏作者</h4>
                <ul id="zhuanlan_zuozhe">
                    <!--<li class="clearfix">
                        <div class="zuozhe_touxaing">
                            <img src="../images/doctor_detail/touxiang.png" alt="" />
                        </div>
                        <div class="zuozhe_inf">
                            <span>鹿明中医</span>
                            <p>关注中医药发展创业</p>
                        </div>
                    </li>

                    <li class="clearfix">
                        <div class="zuozhe_touxaing">
                            <img src="../images/doctor_detail/touxiang.png" alt="" />
                        </div>
                        <div class="zuozhe_inf">
                            <span>鹿明中医</span>
                            <p>关注中医药发展创业</p>
                        </div>
                    </li>

                    <li class="clearfix">
                        <div class="zuozhe_touxaing">
                            <img src="../images/doctor_detail/touxiang.png" alt="" />
                        </div>
                        <div class="zuozhe_inf">
                            <span>鹿明中医</span>
                            <p>关注中医药发展创业</p>
                        </div>
                    </li>

                    <li class="clearfix">
                        <div class="zuozhe_touxaing">
                            <img src="../images/doctor_detail/touxiang.png" alt="" />
                        </div>
                        <div class="zuozhe_inf">
                            <span>鹿明中医</span>
                            <p>关注中医药发展创业</p>
                        </div>
                    </li>-->
                </ul>
            </div>


            <div class="hot-article">
                <span class="hot-article-title">推荐阅读</span>
                <ul class="hot-article-list">
                    <#list hotArticle as ha>
                        <li>
                            <a href="/web/html/forumDetail.html?articleId=${ha.id}" target="_blank">
                                <span title="${ha.title}">${ha.title}</span>
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
<script src="/web/js/jquery.pagination.js"></script>
<script src="/web/js/placeHolder.js"></script>
<script type="application/javascript">
    $(function(){
        $(".headline").addClass("select");
    });

</script>