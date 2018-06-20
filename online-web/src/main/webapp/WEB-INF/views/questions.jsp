<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en" xmlns=http://www.w3.org/1999/xhtml xmlns:bd=http://www.baidu.com/2010/xbdml>
<head>
    <!--[if IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IEedge"/>
    <meta charset="UTF-8">
    <meta HTTP-EQUIV="pragma" CONTENT="no-cache">
    <meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <meta HTTP-EQUIV="expires" CONTENT="0">
    <title>问答 - 熊猫中医</title>
    <link rel="shortcut icon" href="../../favicon.ico"/>
    <meta name="keywords"
          content="中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医"/>
    <meta name="description"
          content="熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。"/>
</head>
<link rel="stylesheet" href="/web/css/bootstrap.min.css">
<link rel="stylesheet" href="/web/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css"/>
<link rel="stylesheet" href="/web/css/myprofile.css"/>
<link rel="stylesheet" href="/web/css/mylogin.css"/>
<link rel="stylesheet" href="/web/css/componet.css"/>
<link rel="stylesheet" href="/web/css/header.css">
<link rel="stylesheet" href="/web/css/ansAndQus.css">
<link rel="stylesheet" href="/web/font/iconfont.css">
<link rel="stylesheet" href="/web/css/footer.css">


<body>
<div class="page-ans-qus">
    <ul class="my-ans-nav">
    </ul>
    <div class="content-list-qus">
        <div class="control-bar">
            <div class="select-type">
                <span class="menu-title" title="全部分类"></span>
                <span class="menu-title1"></span>
            </div>
            <div class="search-ansAndQus">
                <i class="iconfont icon-fangdajing"></i>
                <input type="text" placeholder="输入关键字"/>
            </div>
        </div>
        <div class="content" style="width: 890px;">
            <div class='ansAndQus-left'>
                <div class='ansAndQus-Title'>
                    <span data-status="-1">全部</span>
                    <span data-status="1">未解决</span>
                    <span data-status="2">已解决</span>

                    <div class="title-zhanwei"></div>
                </div>
            </div>
            <div class="ansAndQus-left-list">
                <img class="loadingImg" src="/web/images/ansandqus/loading.gif" alt=""/>
                <p class="loadingWord">加载中</p>
            </div>
            <div class="pages">
                <div id="Pagination"></div>
                <div class="searchPage">
                    <span class="page-sum">共<strong class="allPage">0</strong>页</span>
                    <span class="page-go">跳转<input type="text" style="width: 37px;" min="1" max="">页</span>
                    <a href="javascript:;" class="page-btn">确定</a>
                </div>
            </div>
        </div>
    </div>

    <div class="contentRigh">
        <div class='ansAndQusRight-top'>
            <div class='ansAndQusRight-top-back'>
                <p><img src='/web/images/ansandqus/ask.png'/></p>

                <p>你在学习中遇到了什么问题呢？</p>
                <a href='javascript:;' class='goQuiz'>我要提问</a>
            </div>
        </div>
        <div class='ansAndQusRight-bottom'>
            <p class='ansAndQusRight-top-title'>热门问答</p>
            <div class='ansAndQusRight-hot'>

            </div>
        </div>
        <div class='ansAndQusRight-bottom2' style="display: none;">
            <div class='weekly'>
                <p class='weekly-title'>周回答排行榜</p>
                <div class='weeklyChart'>
                </div>
            </div>
        </div>

    </div>
</div>


<!-----------------------下拉模板---------------------->
<script type="text/html" id="select-con">
    <ul class="select-type-ul">
        {{each menu}}
        <li data-id="{{$value.menu.id}}" data-type="0">
            <span class="flag" data-id="{{$value.menu.id}}" data-type="0" title="{{$value.menu.name}}">{{$value.menu.name}}</span>

            <div class="select-type-ul-dv">
                <div class="select-type-ul-dv-d">
                    {{$value.menu.name}}
                </div>
                <div class="select-type-ul-dv-p">
                    {{each $value.tag}}
                    <a class="menuItem" data-id="{{$value.id}}" data-type="1"
                       title="{{$value.name}}">{{$value.name}}</a>
                    {{/each}}
                </div>
            </div>
        </li>
        {{/each}}
    </ul>
</script>
<script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
<script src="/web/js/jquery.pagination.js"></script>
<script type="text/javascript" src="/web/js/bootstrap.js"></script>
<script type="text/javascript" src="/web/js/artTemplate.js"></script>
<script type="text/javascript" src="/web/js/jquery.form.min.js"></script>
<script src="/web/js/ajax.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="/web/js/helpers.js"></script>
<script src="/web/js/html5.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="/web/js/jquery.dotdotdot.js"></script>
<script type="text/javascript" src="/web/js/ansAndQus.js"></script>






<script src="/web/js/header.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="/web/js/base.js"></script>
<script type="text/javascript" src="/web/js/footer.js"></script>
<script src="/web/js/placeHolder.js"></script>
<script type="text/javascript">
    $(function () {
        $('input').placeholder();
    });
</script>
</body>

</html>