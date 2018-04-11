<#import "../page.ftl" as cast/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>熊猫中医-论坛</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/web/images/favicon.ico">
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
                    <td rowspan='2' style="<#if !(type??) || !(labelId??)>background-color:#e2e2e2</#if>">
                        <a href='/bbs'>
                            <img style='vertical-align: bottom;' src='../web/images/bbs/all.png' alt=''>
                            <br>
                            <span style='display: inline-block;vertical-align: top;margin-top: -20px;margin-left: 67px;'>
                                全部
                            </span>
                        </a>
                    </td>
                <#list top3Labels as label>
                    <td style="<#if (type?? && labelId?? && labelId == label.id)>background-color:#e2e2e2</#if>">
                        <a href='/bbs?type=label&labelId=${label.id}'>
                            <img style='margin-right:10px' src="${label.labelImgUrl}">${label.name}
                        </a>
                    </td>
                </#list>
                </tr>
                <tr>
                <#list otherLabels as label>
                    <td style="<#if (type?? && labelId?? && labelId == label.id)>background-color:#e2e2e2</#if>">
                        <a href='/bbs?type=label&labelId=${label.id}'><img style='margin-right:10px'
                                                                           src=${label.labelImgUrl}>${label.name}</a>
                    </td>
                </#list>
                </tr>
                </tbody>
            </table>

            <div class="fly-tab fly-tab-index">
        <span>
          <a href="/bbs" class="all <#if !(type??) || type=='label'>color</#if>">全部</a>
          <a href="/bbs?type=hot" class="hot <#if type?? && type=='hot'>color</#if>">热门</a>
          <a href="/bbs?type=good" class="good <#if type?? && type=='good'>color</#if>">精品</a>
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
                            <a href="/bbs/${post.id}">${post.title!''}</a>
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
                            <span class="hide"><a href="user/home?id=${post.userId}">${post.name}</a></span>
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
        <#assign pageUrl = "/bbs?"/>
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
                    <a href="${hot.id}">${hot.title}</a>
                    <span class="hide"><i class="iconfont">&#xe60c;</i></span>
                </dd>
            <#else >
                <dd>
                    <i class="botNum">${index+1}</i>
                    <a href="${hot.id}">${hot.title}</a>
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