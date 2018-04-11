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
    <link rel="stylesheet" href="/web/css/bbs/my-reply.css">
    <link rel="stylesheet" href="/web/font/iconfont.css">

    <script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/web/js/artTemplate.js"></script>
    <script src="/web/js/header-top.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/ajax.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/jquery.pagination.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>

</head>

<header>
<#include "../header-body.ftl">
</header>
<body>
<div class="wp">
    <div class="route">
        <a href="">个人中心</a>&gt;<span>我的论坛</span>
    </div>

    <div class="wrap-myPost">
        <div class="wrap-header">
            <div class="head-portrait">
                <img src="${user.smallHeadPhoto}"/>
            </div>
            <p class="userName">${user.name}</p>
        </div>
        <!--论坛帖子/回复开始-->
        <div class="mytabbar">
            <ul class="post-reply">
                <li class="J-post post-active">我的帖子</li>
                <li class="J-reply">我的回复</li>
            </ul>
        </div>
        <!--我的帖子-->
        <div class="content-post">
            <div class="reply-title">
                <table class="list-title">
                    <tr>
                        <td>主题</td>
                        <td>板块</td>
                        <td>发帖时间</td>
                    </tr>
                </table>
            </div>
            <table id="post_table">

            </table>


        </div>
        <!--我的回复-->
        <div class="content-reply contro">
            <div class="reply-title">
                <table class="list-title">
                    <tr>
                        <td>主题</td>
                        <td>板块</td>
                        <td>回复时间</td>
                    </tr>
                </table>
            </div>
            <!--回复的具体内容-->
            <!--1-->
            <table id="reply_table">
            </table>
        </div>
        <div class="pages">
            <div id="Pagination"></div>
        </div>
    </div>
</div>
<#include "../footer.ftl">
<script src="/web/js/bbs/user.js" type="text/javascript" charset="utf-8"></script>
</body>
</html>
