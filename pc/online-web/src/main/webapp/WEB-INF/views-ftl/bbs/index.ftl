<#import "../page.ftl" as cast/>
<!DOCTYPE html>
<html>
<head>
    <title>熊猫中医论坛-中医药传承创新平台</title>
    <meta name="keywords" content="中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医"/>
    <meta name="description" content="熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <link rel="stylesheet" href="/web/css/bootstrap.min.css">
    <link rel="stylesheet" href="/web/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link rel="stylesheet" href="/web/css/componet.css"/>
    <link rel="stylesheet" href="/web/css/header.css"/>
    <link rel="stylesheet" href="/web/css/footer.css"/>
    <link rel="stylesheet" href="/web/css/ftl-page.css"/>
    <link rel="stylesheet" href="/web/layui/css/layui.css">
    <link rel="stylesheet" href="/web/css/bbs/bbs.css">
    <link rel="stylesheet" href="/web/font/iconfont.css">

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

<div class="main layui-clear">
    <div class="wrap">
        <div class="content" style="width: 890px;margin-right: 352px">
            <!--后添加的分类部分-->
            <table class="kinds" id="kinds_id">
                <tbody>
                <tr>
                    <td rowspan='2' style="<#if !(labelId??)>background-color:#e2e2e2</#if>">
                        <a href='${webUrl}/bbs<#if type??>?type=${type}</#if>'>
                            <img style='vertical-align: bottom;' src='../web/images/bbs/all.png' alt=''>
                            <br>
                            <span style='display: inline-block;vertical-align: top;margin-top: -20px;margin-left: 67px;'>
                                全部
                            </span>
                        </a>
                    </td>
                <#list top3Labels as label>
                    <td style="<#if (labelId?? && labelId == label.id)>background-color:#e2e2e2</#if>">
                        <a href='${webUrl}/bbs?labelId=${label.id}<#if type??>&type=${type}</#if>'>
                            <img style='margin-right:10px' src="${label.labelImgUrl}">${label.name}
                        </a>
                    </td>
                </#list>
                </tr>
                <tr>
                <#list otherLabels as label>
                    <td style="<#if (labelId?? && labelId == label.id)>background-color:#e2e2e2</#if>">
                        <a href='${webUrl}/bbs?labelId=${label.id}<#if type??>&type=${type}</#if>'><img style='margin-right:10px'
                                                                           src=${label.labelImgUrl}>${label.name}</a>
                    </td>
                </#list>
                </tr>
                </tbody>
            </table>

            <div class="fly-tab fly-tab-index">
        <span>
          <a href="${webUrl}/bbs<#if labelId??>?labelId=${labelId}</#if>" class="all <#if !(type??)>color</#if>">全部</a>
          <a href="${webUrl}/bbs?type=hot<#if labelId??>&labelId=${labelId}</#if>" class="hot <#if type?? && type=='hot'>color</#if>">热门</a>
          <a href="${webUrl}/bbs?type=good<#if labelId??>&labelId=${labelId}</#if>" class="good <#if type?? && type=='good'>color</#if>">精品</a>
        </span>
                <div>
                    <a onclick="addPost()" class="layui-btn jie-add">发布帖子</a>
                </div>
            </div>

            <ul class="fly-list">
                <ul>
                <#list posts.records as post>
                    <li class="fly-list-li">
                            <span class="fly-list-avatar">
                                <img src="${post.smallHeadPhoto!''}" alt="">
                            </span>
                        <h2 class="fly-tip">
                            <a href="${webUrl}/bbs/${post.id}">${post.title!''}</a>
                            <#if post.top>
                                <span class="fly-tip-stick">置顶</span>
                            </#if>
                            <#if post.good>
                                <span class="fly-tip-jing">精品</span>
                            </#if>
                            <#if post.hot>
                                <span class="fly-tip-hot">热门</span>
                            </#if>
                        </h2>

                        <div class="cardCon" style="overflow: hidden;text-overflow: ellipsis;">
                        ${post.content}
                        </div>

                    <#--<#list post.imgArray as img>-->
                    <#--<img src="${img}" alt="帖子图片1">-->
                    <#--</#list>-->

                        <p>
                            <span class="hide"><a href="${webUrl}/bbs/user/home?id=${post.userId}">${post.name}</a></span>
                            <span>${post.labelName}</span>
                            <span>${post.name}发表于</span>
                            <span>${post.initTime?string('yyyy-MM-dd HH:mm:ss')} </span>
                            <span class="fly-list-hint">
                                            <i class="iconfont hide" title="回答">&#xe60c;</i>
                                    <span>|</span>${post.replyCount}
                                    人回复
                                </span>
                            <span style="padding-right:15px">|</span>
                            <span>${post.browseCount}人浏览</span>
                        </p>

                    </li>
                </#list>
                <#if posts.total == 0>
                    <li class="fly-none">没有任何帖子</li>
                </#if>
                </ul>

            </ul>
        <#assign pageUrl = "${webUrl}/bbs?"/>
        <#if RequestParameters.type??>
            <#assign pageUrl = pageUrl + "&type=" + RequestParameters.type>
        </#if>
        <#if RequestParameters.labelId??>
            <#assign pageUrl = pageUrl + "&labelId=" + RequestParameters.labelId>
        </#if>
        <#if Request.url??>
        ${Request.url}
        </#if>
        <@cast.page pageNo=posts.current totalPage=posts.pages showPages=5 callUrl="${pageUrl}&page="/>
        </div>
    </div>
    <div class="edge">
        <dl class="fly-panel fly-list-one">
            <dt class="fly-panel-title">热门帖子</dt>
        <#assign index = 0>
        <#list hots as hot>
            <#if index < 3>
                <dd>
                    <i class="topNum">${index+1}</i>
                    <a href="${webUrl}/bbs/${hot.id}">${hot.title}</a>
                    <span class="hide"><i class="iconfont">&#xe60c;</i></span>
                </dd>
            <#else >
                <dd>
                    <i class="botNum">${index+1}</i>
                    <a href="${webUrl}/bbs/${hot.id}">${hot.title}</a>
                    <span class="hide"><i class="iconfont">&#xe60c;</i></span>
                </dd>
            </#if>
            <#assign index = index + 1>
        </#list>
        </dl>
    </div>
</div>
<#include "../footer.ftl">
<script>
    $('.bbs-tab').addClass("select");

    function addPost() {
        RequestService("/online/user/isAlive", "GET", null, function (data) {
            if (!data.success) {
                $('#login').modal('show');
            } else {
                window.location.href = "/bbs/add";
            }
        })
    }
</script>

<!-- 添加回车事件 -->
<script type="text/javascript" event="onkeydown" for="document">
    if (event.keyCode == 13) {
        $('.login_btn').click();
    }
</script>

</body>
</html>