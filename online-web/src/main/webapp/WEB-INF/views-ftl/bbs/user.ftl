<#import "../page.ftl" as cast/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>熊猫中医论坛-中医药传承创新平台</title>
    <meta name="keywords" content="中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医"/>
    <meta name="description" content="熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <link rel="stylesheet" href="/web/css/bootstrap.min.css">
    <link rel="stylesheet" href="/web/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link rel="stylesheet" href="/web/css/componet.css"/>
    <link rel="stylesheet" href="/web/css/header.css"/>
    <link rel="stylesheet" href="/web/css/footer.css"/>
    <!--<link rel="stylesheet" href="/web/css/ftl-page.css"/>-->
    <link rel="stylesheet" href="/web/css/bbs/my-reply.css">
    <link rel="stylesheet" href="/web/font/iconfont.css">
  	<!--分页部分start-->
	<link rel="stylesheet" href="/web/css/componet.css" />
	<link rel="stylesheet" href="/web/css/componet-pages.css" />
	<!--分页部分end-->
	
    <script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/web/js/artTemplate.js"></script>
    <script src="/web/js/header-top.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/ajax.js" type="text/javascript" charset="utf-8"></script>
    <!--<script src="/web/js/jquery.pagination.js" type="text/javascript" charset="utf-8"></script>-->
    <script src="/web/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
	<!--分页部分start-->
	<script src="/web/js/anchor/pages/jquery.pagination-course.js"></script>
	<!--分页部分start-->
</head>

<header>
<#include "../header-body.ftl">
</header>
<body>
<div class="wp">
    <div class="route">
        <a href="">个人中心</a>&gt;<span>我的帖子</span>
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
			<!--分页部分start-->
				<div class="post_pages pages hide" style="margin-top: 20px;">  <!--如需要复制分页doctors_pages要改类名并在JS里对应-->
					<div id="Pagination_post" class="pagination-pages"></div>	<!--如需要复制分页Pagination_doctors要改类名并在JS里对应-->
					<div class="searchPage">
						<span class="page-sum">共<strong class="allPage">0</strong>页</span>
						<span class="page-go">跳转<input type="text" style="width: 37px;" min="1" max="">页</span>
						<a href="javascript:;" class="page-btn">确定</a>
					</div>
				</div>
			<!--分页部分end-->

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
            <!--分页部分start-->
				<div class="reply_pages pages hide" style="margin-top: 20px;">  <!--如需要复制分页doctors_pages要改类名并在JS里对应-->
					<div id="Pagination_reply" class="pagination-pages"></div>	<!--如需要复制分页Pagination_doctors要改类名并在JS里对应-->
					<div class="searchPage">
						<span class="page-sum">共<strong class="allPage">0</strong>页</span>
						<span class="page-go">跳转<input type="text" style="width: 37px;" min="1" max="">页</span>
						<a href="javascript:;" class="page-btn">确定</a>
					</div>
				</div>
			<!--分页部分end-->
        </div>
       
    </div>
</div>
<#include "../footer.ftl">
<script src="/web/js/bbs/user.js" type="text/javascript" charset="utf-8"></script>
</body>
</html>
