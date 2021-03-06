<#import "../page.ftl" as cast/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--[if IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IEedge">

    <title>${tk.title?default('')}熊猫中医头条</title>
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="keywords" content="${tk.keywords?default('')}中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医">
    <meta name="description" content="${description}">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="/web/css/bootstrap.min.css">
    <link rel="stylesheet" href="/web/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link rel="stylesheet" href="/web/css/componet.css"/>
    <link rel="stylesheet" href="/web/css/header.css"/>
    <link rel="stylesheet" href="/web/css/forumDetail.css"/>
    <link rel="stylesheet" href="/web/css/footer.css"/>
    <link rel="stylesheet" href="/web/css/ftl-page.css"/>
    <link rel="stylesheet" href="/web/font/iconfont.css"/>

    <script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/web/js/artTemplate.js"></script>
    <script type="text/javascript" src="/web/js/jquery.SuperSlide.2.1.1.js"></script>
    <script src="/web/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/common/common.js" type="text/javascript" charset="utf-8"></script>
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
            <input type="hidden" value="${article.typeId!'0'}" id="J-page-flag">
        <#if writing??>
            <div class="forum-detailInfo">
                <div class="writing-buy-link">
                    <div class="wrap-form-img" style="float: left;">
                        <img src="${writing.imgPath}">
                    </div>
                    <div class="writing-author-info">
                        <div class="writing-author-name">
                            <p class="title">${writing.title}</p>
                            <p class="name">作者: ${writing.author}</p>
                        </div>
                        <#if writing.buyLink??>
                            <div class="writing-go-buy">
                                    <a href="${writing.buyLink}" target="_blank">
                                        去购买
                                    </a>
                            </div>
                        </#if>
                    </div>
                </div>
                <div class="forum-detail-content">${article.content}</div>
            </div>
        <#else>
            <div class="forum-detailInfo">
                <div class="forum-detail">
                    <div class="forum-detail-title">${article.title}</div>
                    <div class="forum-info-tags">
                        <img src="/web/images/studentCount.png">
                        <span>${article.author!''}&nbsp;&nbsp;&nbsp;${(article.createTime?string("yyyy-MM-dd HH:mm"))!}</span>
                        <#if article.typeId != '8' && article.typeId != '9'>
                            <a href="${webUrl}/headline/list/${article.typeId}"
                               style="color: #188EEE;margin-left:20px">${article.type!""}</a>
                        <#else>
                            <span style="color: #188EEE;margin-left:20px">${article.type!""}</span>
                        </#if>
                    </div>
                </div>
                <div class="forum-detail-content">${article.content}</div>
            </div>
            <#if article.url??>
                <div style="width: 820px;height: 60px;background-color: #F0F0F0;font-size:14px;padding-top:20px;padding-left:20px;margin-top:40px">
                    <span>本文转自 <a target="_blank" href="${article.url}">原文链接</a> 转载请注明出处</span>
                </div>
            </#if>
        </#if>
            <div class="forum-community clearfix">
                <div class="forum-community-title">参与讨论</div>
                <div class="forum-community-loginHit" style="display: none;">
                    请<span class="forum-community-lgoin">登录</span>后参与讨论
                </div>
                <textarea name="" id="" class="forum-community-textarea" placeholder="说点儿什么吧……" cols="30"
                          rows="10"></textarea>
                <div class="emptyHit" style="display: none;"><i class="iconfont icon-tanhao"></i>请输入内容</div>
                <button type="button" class="community-submit" disabled="true">提交</button>
            </div>
            <div class="all-comment">
                <div class="all-comment-title">
                    <span>全部评论</span>
                    <span class="community-count">${appraises.total}条</span>
                </div>

            <#if appraises?? && appraises.records?size gt 0 >
                <div class="forum-communitybox">
                    <#list appraises.records as appraise>
                        <div class="forum-community-content">
                            <div class="forum-comment-content clearfix">
                                <div class="img"><img src="${appraise.smallHeadPhoto}"/></div>
                                <div class="forum-comment-right">
                                    <div class="comment-personAndTime">
                                        <span class="comment-person">${appraise.name}</span>
                                        <!--<em></em>-->
                                        <span class="comment-time">${(appraise.createTime?string("yyyy-MM-dd hh:mm"))!}</span>
                                    </div>
                                    <div class="comment-info">${appraise.content}</div>
                                    <#if appraise.nickName??>
                                        <div class="background-replay">
                                            <div class="comment-info">
                                                <div class="img" style="margin: 10px 12px 0 15px;"><img
                                                        src="${appraise.replySmallHeadPhoto}"/></div>
                                                <div class="replay-write">
                                                    <span class="replyName">${appraise.nickName}</span>
                                                    <span class="comment-time">${(appraise.replyCreateTime?string("yyyy-MM-dd hh:mm"))!}</span>
                                                    <#if appraise.replyContent??>
                                                        <div class="comment-info">${appraise.replyContent}</div>
                                                    <#else>
                                                        <div class="comment-info">该评论已被删除！</div>
                                                    </#if>
                                                </div>

                                            </div>

                                        </div>
                                    </#if>
                                    <div class="reply-comment">
                                        <!--<i class="iconfont icon-huifu"></i>-->
                                        回复
                                    </div>
                                    <#if appraise.isMySelf==true>
                                        <div class="reply-delete" data-commentId="${appraise.id}">
                                            <!--<i class="iconfont icon-shanchu"></i>-->
                                            删除
                                        </div>
                                    </#if>

                                    <div class="replay-box">
                                        <!--<p class="replyPerson">回复 ${appraise.name}：</p>-->
                                        <#if userSmallHeadPhoto=="">
                                            <img class="pinglunSanjiao" src="/web/images/defaultHead/18.png">
                                        <#else>
                                            <img class="pinglunSanjiao" src="${userSmallHeadPhoto}">
                                        </#if>
                                        <input class="reply-input" placeholder="写下您的评论..."/>
                                        <div class="emptyHit">
                                            <i class="iconfont icon-tanhao"></i>
                                            请输入内容
                                        </div>
                                        <div class="reply-btn">
                                            <div class="cancle">取消</div>
                                            <button type="button" disabled="true" class="reply" data-targetId="${appraise.userId}"
                                                 data-replyCommentId="${appraise.id}">
                                                回复
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
            <#else>
                <!--暂无评论显示背景图-->
                <div class="nodata-box">
                    <div class="no-question-img">
                        <img src="/web/images/icon-nodata.png"/>
                    </div>
                    <p>暂无数据</p>
                </div>
            </#if>
            </div>


            <!-- 使用该标签 -->
        <@cast.page pageNo=appraises.current totalPage=appraises.pages showPages=5 callUrl="${webUrl}/headline/details/"+echoMap.id?default("")+"?page="/>

        </div>
        <div class="forum-content-right">
        <#if writingAuthor??>
            <div class="report-doctor-list">
                <div style="background: #fff;">
                    <p class="name" style="padding:20px;padding-bottom: 0px;">作者</p>
                    <div>
                        <div class="report_right_doctor clearfix">
                            <div class="report_right_name">
                                <#if writingAuthor.headPortrait??>
                                    <img src="${writingAuthor.headPortrait}" alt="医师头像">
                                <#else>
                                    <img src="/web/images/defaultHead/18.png" alt="医师头像">
                                </#if>

                                <div class="report_right_name_p">
                                    <p class="p0"><a
                                            href="${webUrl}/doctors/${writingAuthor.id}"
                                            style="color: #000;">${writingAuthor.name!""}</a>
                                    </p>
                                    <p class="p1">${writingAuthor.province!""} ${writingAuthor.city!""}</p>
                                </div>
                            </div>
                            <div class="both"></div>
                        </div>
                    </div>
                </div>
            </div>
        </#if>
        <#if reportDoctors?? && (reportDoctors?size >0)>
            <div class="report-doctor-list">
                <div style="background: #fff;">
                    <p class="name" style="padding:20px;padding-bottom: 0px;">报道的名医</p>
                <div>
                    <#list reportDoctors as reportDoctor>
                        <div class="report_right_doctor clearfix">
                            <div class="report_right_name">
                                <#if reportDoctor.headPortrait??>
                                    <img src="${reportDoctor.headPortrait}" alt="医师头像">
                                <#else>
                                    <img src="/web/images/defaultHead/18.png" alt="医师头像">
                                </#if>

                                <div class="report_right_name_p">
                                    <p class="p0"><a
                                            href="${webUrl}/doctors/${reportDoctor.doctorId}"
                                            style="color: #000;">${reportDoctor.doctorName!""}</a>
                                    </p>
                                    <p class="p1">${reportDoctor.province!""} ${reportDoctor.city!""}</p>
                                </div>
                            </div>
                            <div class="both"></div>
                        </div>
                    </div>
                    </#list>
                </div>
            </div>
        </#if>
        <#if authors?? && (authors?size >0)>
            <div class="report-doctor-list">
                <div style="background: #fff;">
                    <p class="name" style="padding:20px;padding-bottom: 0px;">文章作者</p>
                <div>
                    <#list authors as author>
                        <div class="report_right_doctor clearfix">
                            <div class="report_right_name">
                                <img src="${author.headPortrait}" alt="医师头像">
                                <div class="report_right_name_p">
                                    <p class="p0"><a
                                            href="${webUrl}/doctors/${author.doctorId}"
                                            style="color: #000;">${author.doctorName!""}</a>
                                    </p>
                                    <p class="p1">${author.province!""} ${author.city!""}</p>
                                </div>
                            </div>
                            <div class="both"></div>
                        </div>
                    </div>
                    </#list>
                </div>
            </div>
        </#if>
        <#if suggestedArticles?? && suggestedArticles?size gt 0>
            <div class="hot-article" style="height: auto">
                <span class="hot-article-title">推荐阅读</span>
                <ul class="hot-article-list">
                    <#list suggestedArticles as suggestedArticle>


                        <li>
                            <a href="${webUrl}/headline/details/${suggestedArticle.id}">
                                <#if suggestedArticle_index <= 2>
                                    <p class="setSelect">${suggestedArticle_index+1}</p>
                                <#else>
                                    <p>${suggestedArticle_index+1}</p>
                                </#if>
                                <span style="margin-bottom: 20px"
                                      title="${suggestedArticle.title}">${suggestedArticle.title}</span>
                            </a>
                        </li>

                    </#list>
                </ul>
            </div>
        </#if>

        <#if writings?? && (writings.total gt 0)>
            <!-- 名医书籍 -->
            <div class="teacher_books">
                <div id="">
                    <h4>名医书籍</h4>
                    <a href="${webUrl}/doctors/writing">
                        <span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>
                    </a>
                </div>

                <ul class="book_list clearfix" id="boos_list">
                    <#list writings.getRecords() as writing>
                        <li>
                            <a href="/headline/details/${writing.id}" style="color: #0C0C0C;display: inline;">
                                <img src="${writing.imgPath}" alt="">
                            </a>
                            <div>
                                <a href="/headline/details/${writing.id}" style="color: #0C0C0C">
                                    <span class="book_name">${writing.title}</span>
                                    <h5 class="book_author">${writing.author}</h5>
                                </a>
                            </div>
                        </li>

                    </#list>
                </ul>

            </div>
        </#if>
        <#--<div class="forum-hot-tag hide">-->
        <#--<div class="forum-hot-tag-title">热门标签</div>-->
        <#--<ul class="forum-hot-tagGround">-->
        <#--<li><a href="/web/html/forumBiaoqian.html?tagId=08a0ce5651da46bb8b71635ee9e7a524" target="_blank">中医药</a></li>-->
        <#--<li><a href="/web/html/forumBiaoqian.html?tagId=112378305d5941358dab73f440ee9283" target="_blank">养生</a></li>-->
        <#--<li><a href="/web/html/forumBiaoqian.html?tagId=2f381d98e83f4e14a98c59dc0bbd01bb" target="_blank">大数据</a></li><li>-->
        <#--<a href="/web/html/forumBiaoqian.html?tagId=373131270a494681a5f700b969ff8619" target="_blank">文化</a></li>-->
        <#--<li><a href="/web/html/forumBiaoqian.html?tagId=3c6048aed75649aca0da896776c07dba" target="_blank">法规</a></li>-->
        <#--<li><a href="/web/html/forumBiaoqian.html?tagId=456a5c0589d14778aa76841537794dac" target="_blank">医疗服务</a></li>-->
        <#--<li><a href="/web/html/forumBiaoqian.html?tagId=4f8f4fb88b794d7c8cde96fea7e04467" target="_blank">智能</a></li>-->
        <#--<li><a href="/web/html/forumBiaoqian.html?tagId=59e915046b4f423ebc7a5d12fa4dec12" target="_blank">要闻</a></li></ul>-->
        <#--</div>-->

        </div>
    </div>
</div>
<#include "../footer.ftl">
</body>
<script src="/web/js/jquery.pagination.js"></script>
<script src="/web/js/placeHolder.js"></script>
<script src="/web/js/modal.js"></script>
<script src="/web/js/common_msg.js"></script>
<script type="application/javascript">
    var articleId = ${echoMap.id};

    $(function () {
        var typeId = $("#J-page-flag").val();
        if (typeId == '8' || typeId == '9' ) {
            $(".doctor-tab").addClass("select");
        } else {
            $(".headline").addClass("select");
        }
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
            }
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


    (function () {
        $(".forum-community-lgoin").click(function () {
            $('#login').modal('show');
        });
        //评论
        RequestService("/online/user/isAlive", "GET", null, function (data) {
            if (data.success == false) {
                $(".forum-community-loginHit").css("display", "block");
            } else {
                $(".forum-community-loginHit").css("display", "none");
            }
        });

        //删除
        $(".reply-delete").each(function () {
            $(this).click(function () {
                var commentId = $(this).attr("data-commentId");
                confirmBox.open("删除评论", "确定删除该条评论？", function (closefn) {
                    RequestService('/bxs/article/deleteAppraiseId', "POST", {
                        appraiseId: commentId
                    }, function (data) {
                        if (data.success == true) {
                            window.location.href = "/headline/details/" + articleId;
                        }
                    });
                    closefn();
                });
            })
        });

        $(".reply-comment").each(function () {
            $(this).click(function () {
                var _rThis = $(this);
                $(".replay-box .emptyHit").hide();
                if (_rThis.parent().find(".replay-box").css("display") == "block") {
                    _rThis.parent().find(".replay-box").css("display", "none");
                } else {
                    $(".replay-box").css("display", "none");
                    _rThis.parent().find(".replay-box").toggle();
                }
            })
        });
        $(".cancle").each(function () {
            $(this).click(function () {
                $(this).parent().parent().hide();
            })
        });
//      $(".reply-input").focus(function () {
//          var indentLength = $(this).parent().find(".replyPerson").width();
//          $(".reply-input").css("textIndent", indentLength + "px");
//          $(".replay-box .emptyHit").hide();
//      });
//回复评论
        $(".reply").each(function () {
            var forumThs = $(this);
            forumThs.click(function () {
                RequestService("/online/user/isAlive", "GET", null, function (data) {
                    if (data.success == false) {
                        $('#login').modal('show');
                    } else {
                        var replayCon = forumThs.parent().parent().find(".reply-input").val().trim();
                        var targetId = forumThs.attr("data-targetid");
                        var replyCommentId = forumThs.attr("data-replyCommentId");
                        if (replayCon == "") {
                            $(".replay-box .emptyHit").show();
                        } else {
                            RequestService("/bxs/article/saveAppraise", "POST", {
                                article_id: articleId,
                                content: replayCon,
                                target_user_id: targetId,
                                reply_comment_id: replyCommentId
                            }, function (data) {
                                if (data.success) {
                                    window.location.href = "/headline/details/" + articleId;
                                }
                            })
                        }
                    }
                });
            })
        });
        $(".reply-input").keyup(function(){
			var $this = $(this);
			var commentContent = $(this).val();
			var btnColor = $this.siblings(".reply-btn").find(".reply");
			if (commentContent != null && commentContent != "" && commentContent.trim().length > 0) {
				btnColor.attr("disabled", false);
				btnColor.css("background",  "#00BC12");
			}else{
				btnColor.attr("disabled", true);
				btnColor.css("background",  "#999");
			}
		})
//提交评论       
        $(".community-submit").click(function () {
            var commentCon = $(".forum-community-textarea").val().trim();
          
                $(".forum-community .emptyHit").css("display", "none");
                RequestService('/bxs/article/saveAppraise', 'POST', {
                    article_id: articleId,
                    content: commentCon
                }, function (data) {
                    if (data.success) {
                        window.location.href = "/headline/details/" + articleId;
                    }
                });

        });

        $(".forum-community-textarea").keyup(function(){
			var $this = $(this);
			var commentContent = $(this).val();
			var btnColor = $this.siblings(".community-submit");
			if (commentContent != null && commentContent != "" && commentContent.trim().length > 0) {
				btnColor.attr("disabled", false);
				btnColor.css("background",  "#00BC12");
			}else{
				btnColor.attr("disabled", true);
				btnColor.css("background",  "#999");
			}
		})
    })();
</script>

<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"啦啦啦","bdMini":"2","bdMiniList":false,"bdPic":"https://file.ipandatcm.com/18707111404/827dbb6d1d49d55d.jpg","bdStyle":"0","bdSize":"16"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["qzone","tsina","tqq","renren","weixin"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
