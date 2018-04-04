<!DOCTYPE html>
<!-- saved from url=(0056)http://dev.ixincheng.com/doctors -->
<html><head lang="en"><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--[if IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IEedge">

    <title>熊猫中医-名医</title>
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="keywords" content="中医教育,中医传承,中医线下教育，海口中医养生，国粹，传承，中医，中药，心承，熊猫中医">
    <meta name="description" content="熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="/web/css/bootstrap.min.css">
    <link rel="stylesheet" href="/web/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link rel="stylesheet" href="/web/css/componet.css"/>
    <link rel="stylesheet" href="/web/css/header.css"/>
    <link rel="stylesheet" href="/web/css/forumDetail.css"/>
    <link rel="stylesheet" href="/web/css/footer.css"/>
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
            <div class="forum-detailInfo">
                <div class="forum-detail">
                    <div class="forum-detail-title">${article.title}</div>
                    <div class="forum-info-tags">
                        <img src="/web/images/studentCount.png" />
                        <span>${article.userId}&nbsp;&nbsp;&nbsp;${(article.createTime?string("yyyy-MM-dd"))!}</span>
                    </div>
                </div>
                <div class="forum-detail-content">${article.content}</div>
            </div>
            <div class="forum-community clearfix">
                <div class="forum-community-title">参与讨论</div>
                <div class="forum-community-loginHit" style="display: none;">
                    请<span class="forum-community-lgoin">登录</span>后参与讨论
                </div>
                <textarea name="" id="" class="forum-community-textarea" placeholder="说点儿什么吧……" cols="30" rows="10"></textarea>
                <div class="emptyHit" style="display: none;"><i class="iconfont icon-tanhao"></i>请输入内容</div>
                <div class="community-submit">提交</div>
            </div>
            <div class="all-comment">
                <div class="all-comment-title">
                    <span>全部评论</span>
                    <span class="community-count">3条</span>
                </div>
                <div class="forum-communitybox">
                    <div class="forum-community-content">
                        <#list appraises as appraise>
                            <div class="forum-community-content">
                                <div class="forum-comment-content clearfix">
                                    '<div class="img"><img  src="${appraise.small_head_photo}" /></div>
                                    <div class="forum-comment-right">
                                        <div class="comment-personAndTime">
                                            <span class="comment-person">${appraise.name}</span>
                                            <em></em>
                                            <span class="comment-time">${appraise.create_time}</span>
                                        </div>
                                        <#if appraise.nickName!="" && appraise.nickName!=null>
                                            <div class="comment-info"><span>回复&nbsp;&nbsp;<span class="replyName">${appraise.nickName}：</span></span>${appraise.content}</div>
                                            <#else>
                                            <div class="comment-info">${appraise.content}</div>
                                        </#if>
                                        <div class="reply-comment">
                                            <i class="iconfont icon-huifu"></i>
                                            回复
                                        </div>
                                        <#if appraise.mySelf==true>
                                            <div class="reply-delete" data-commentId="${appraise.id}">
                                                <i class="iconfont icon-shanchu"></i>
                                                删除
                                            </div>
                                        </#if>

                                        <div class="replay-box">
                                            <p class="replyPerson">回复 ${appraise.name}：</p>
                                            <img class="pinglunSanjiao" src="../images/ansandqus/sanjiao02.png">
                                            <input class="reply-input"/>
                                            <div class="emptyHit">
                                                <i class="iconfont icon-tanhao"></i>
                                                请输入内容
                                            </div>
                                            <div class="reply-btn">
                                                <div class="cancle">取消</div>
                                                <div class="reply" data-targetId="${appraise.user_id}">
                                                    回复
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>
            </div>

            <div class="pages" style="display: none;">
                <div id="Pagination"></div>
            </div>
        </div>
        <div class="forum-content-right">
            <div class="hot-article">
                <span class="hot-article-title">推荐阅读</span>
                <ul class="hot-article-list"><li><a href="/web/html/forumDetail.html?articleId=103" target="_blank"><span title="【熊猫头条】熊猫中医加入庭医生联盟守护家庭健康中国家">【熊猫头条】熊猫中医加入庭医生联盟守护家庭健康中国家</span></a></li><li><a href="/web/html/forumDetail.html?articleId=105" target="_blank"><span title="祝贺！熊猫中医斩获“2017国际创新创业”大赛一等奖！">祝贺！熊猫中医斩获“2017国际创新创业”大赛一等奖！</span></a></li><li><a href="/web/html/forumDetail.html?articleId=104" target="_blank"><span title="熊猫中医助力首届中国中医院院长职业化管理培训班羊城开班">熊猫中医助力首届中国中医院院长职业化管理培训班羊城开班</span></a></li><li><a href="/web/html/forumDetail.html?articleId=55" target="_blank"><span title="国家中医药管理局：鼓励建立中医电子处方数据库">国家中医药管理局：鼓励建立中医电子处方数据库</span></a></li><li><a href="/web/html/forumDetail.html?articleId=86" target="_blank"><span title="王笑频：中医药一直在“走出去”的路上">王笑频：中医药一直在“走出去”的路上</span></a></li><li><a href="/web/html/forumDetail.html?articleId=84" target="_blank"><span title="【山东】让确有专长人员在阳光下行医">【山东】让确有专长人员在阳光下行医</span></a></li><li><a href="/web/html/forumDetail.html?articleId=60" target="_blank"><span title="2017年中国国际中医药大健康博览会举行">2017年中国国际中医药大健康博览会举行</span></a></li></ul>
            </div>
            <div class="forum-hot-tag hide">
                <div class="forum-hot-tag-title">热门标签</div>
                <ul class="forum-hot-tagGround"><li><a href="/web/html/forumBiaoqian.html?tagId=08a0ce5651da46bb8b71635ee9e7a524" target="_blank">中医药</a></li><li><a href="/web/html/forumBiaoqian.html?tagId=112378305d5941358dab73f440ee9283" target="_blank">养生</a></li><li><a href="/web/html/forumBiaoqian.html?tagId=2f381d98e83f4e14a98c59dc0bbd01bb" target="_blank">大数据</a></li><li><a href="/web/html/forumBiaoqian.html?tagId=373131270a494681a5f700b969ff8619" target="_blank">文化</a></li><li><a href="/web/html/forumBiaoqian.html?tagId=3c6048aed75649aca0da896776c07dba" target="_blank">法规</a></li><li><a href="/web/html/forumBiaoqian.html?tagId=456a5c0589d14778aa76841537794dac" target="_blank">医疗服务</a></li><li><a href="/web/html/forumBiaoqian.html?tagId=4f8f4fb88b794d7c8cde96fea7e04467" target="_blank">智能</a></li><li><a href="/web/html/forumBiaoqian.html?tagId=59e915046b4f423ebc7a5d12fa4dec12" target="_blank">要闻</a></li></ul>
            </div>
            <div class="forum-hot-course hide">
                <div class="forum-hot-course-title">
                    <span>热门课程</span>
                    <div class="forum-hot-course-banner">
                        <span class="curCount currentLunbo">1</span><span class="curCount">/</span><span class="curCount allLunbo"></span>
                        <span class="prev" id="prev"></span>
                        <span class="next" id="next"></span>
                    </div>
                </div>
                <div class="hot-course">
                    <div id="box" class="slideBox clearfix">
                        <ul class="course boxContent clearfix"><div class="page-no-result"><img src="../images/personcenter/my_nodata.png"><div class="no-title">暂无数据</div></div></ul>
                    </div>
                </div>
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
    //banner
    function init() {
        var $sliders = $('#slider li');
        var $selector = $('#selector');
        var $selectors = $('#selector span');
        var $left = $('#left');
        var $right = $('#right');
        //自动切换
        var step = 0;

        function autoChange() {
            if (step === $sliders.length) {
                step = 0;
            };
            $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
            $selectors.eq(step).addClass('cur').siblings().removeClass('cur');
            step++;
        }

        var timer = window.setInterval(autoChange, 5000);

        //点击圆圈切换
        $selector.on('click', function (e) {
            var $target = $(e.target);
            if ($target.get(0).tagName === 'SPAN') {
                window.clearInterval(timer);
                $target.addClass('cur').siblings().removeClass('cur');
                step = $target.index();
                $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
                timer = window.setInterval(autoChange, 5000);
            }
        });

        //点击左右按钮切换
        $left.on('click', function () {
            window.clearInterval(timer);
            step--;
            if (step < 0) {
                step = $sliders.length - 1;
                $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
                $selectors.eq(step).addClass('cur').siblings().removeClass('cur');
            } else {
                $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
                $selectors.eq(step).addClass('cur').siblings().removeClass('cur');
            }
            timer = window.setInterval(autoChange, 5000);
        });
        $right.on('click', function () {
            window.clearInterval(timer);
            step++;
            if (step > $sliders.length - 1) {
                step = 0;
                $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
                $selectors.eq(step).addClass('cur').siblings().removeClass('cur');
            } else {
                $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
                $selectors.eq(step).addClass('cur').siblings().removeClass('cur');
            }
            timer = window.setInterval(autoChange, 5000);
        })
    }
    init();


</script>