<#import "../page.ftl" as cast/>
<!DOCTYPE html>
<html>
<head>
    <title>熊猫中医论坛-中医药传承创新平台</title>
    <meta name="keywords" content="中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医"/>
    <meta name="description"
          content="${description!'熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。'}"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta charset="utf-8">
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


    <script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.all.min.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="/ueditor/lang/zh-cn/zh-cn.js"></script>

    <script src="/web/js/bbs/detail.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>

<header>
<#include "../header-body.ftl">
</header>
<#include "../tip.ftl">

<div class="main layui-clear">
    <div class="wrap">
        <div class="content detail" style="margin-right:352px;width: 890px;">
            <div class="fly-panel detail-box">
                <h1></h1>
                <div class="fly-tip fly-detail-hint" data-id="">
                <#if post.top>
                    <span class="fly-tip-stick hide">置顶</span>
                </#if>
                <#if post.good>
                    <span class="fly-tip-jing hide">精帖</span>
                </#if>
                    <span class="hide">主题</span>
                    <div class="fly-list-hint hide">
                        <i class="iconfont" title="回答">&#xe60c;</i>${post.replyCount}
                    </div>
                </div>
                <div class="detail-about layui-clear">
                    <a class="jie-user" href="javascript:;">
                        <img src="${post.smallHeadPhoto!''}" alt="" class="asdc">
                        <cite class="hide">
                        ${post.name}
                            <em>发表于：${post.initTime?string('yyyy-MM-dd HH:mm:ss')} </em>
                        </cite>
                    </a>
                    <span class="publishTitle">${post.title}</span><br/>
                    <span style="float:left" class="publishInf">
                        <em>${post.labelName}</em>
                        <em>${post.name}</em>
                        <em>发表于：${post.initTime?string('yyyy-MM-dd HH:mm:ss')}</em>
                        <em>浏览(${post.browseCount})</em>
                        <em>回复(${post.replyCount})</em>
                    </span>
                </div>
                <input type="hidden" value="${post.id}" id="J_id">
                <div class="detail-body photos" style="margin-bottom: 20px;">
                ${post.content}
                </div>
            </div>


            <div class="fly-panel detail-box" style="padding-top: 0;">
                <a name="comment"></a>
                <ul class="jieda photos" id="jieda">
                <#list replies.records as reply>
                    <li data-id="${reply.id}" class="jieda-daan">
                        <a name="item-121212121212"></a>
                        <div class="detail-about detail-about-reply">
                            <a class="jie-user" href="javascript:;">
                                <img src="${reply.smallHeadPhoto!''}" alt="">
                                <cite style="color:#666">
                                    <i style="font-weight:700">${reply.name}</i>&nbsp;&nbsp;发表于
                                    <i>${reply.initTime?string('yyyy-MM-dd HH:mm:ss')}</i>
                                </cite>
                            </a>
                            <div class="detail-hits hide">
                                <span>${reply.initTime?string('yyyy-MM-dd HH:mm:ss')}</span>
                            </div>
                        </div>

                        <#if reply.toReply??>
                            <div class="detail-body jieda-body" style="visibility: hidden;height:10px">
                                占位置作用
                            </div>

                            <!-- 杨宣新增被回复的回复信息    -->
                            <div style="padding: 0 30px;background-color: #f9f9f9;" class="replay">
                                <p>
                                    <span>"</span>${reply.toReply.name}&nbsp;<span>发表于<span>&nbsp;<span>${reply.toReply.initTime?string('yyyy-MM-dd HH:mm:ss')}</span>
                                </p>
                                <p>${reply.toReply.content}<span>"</span></p>
                            </div>
                            <div class="detail-body jieda-body">
                                <p>${reply.content}</p>
                            </div>
                        <#else >
                            <div class="detail-body jieda-body ">
                                <p>${reply.content}</p>
                            </div>
                        </#if>

                        <div class="jieda-reply">
                            <span type="reply" class="reply" data-posts="${post.id}" data-content="${reply.content}"
                                  data-id="${reply.id}">回复</span>
                        </div>

                    </li>

                </#list>

                <#if replies.total == 0>
                    <li class="fly-none">暂无任何回复</li>
                </#if>
                <#assign pageUrl = "/bbs/${post.id}?"/>
                <@cast.page pageNo=replies.current totalPage=replies.pages showPages=5 callUrl="${pageUrl}&page="/>
                </ul>
            </div>


            <p style="text-align: right;"><a href="javascript:;" onclick="goBack()">返回列表</a></p>

            <div class="layui-form layui-form-pane" style="position: relative;">
                <div class="layui-form-abso" style="display:none;">
                </div>
                <form method="post">
                    <div class="layui-form-item layui-form-text">
                        <div class="layui-input-block">
                                <textarea id="replyContentEditor" name="content"
                                          placeholder="" class="layui-textarea fly-editor"
                                          style="height: 150px;"></textarea>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <button class="layui-btn J-submit" lay-filter="posts" lay-submit
                                style="float:right;background-color:#2cb82c">提交回复
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- 回复的提示框  -->
<form class="reply_list hide" action="">
    <div>
        <p style="font-weight:700;font-size:16px">参与/回复主题</p>
        <p style="font-size:14px">RE:<span>${post.title}</span></p>
        <p style="font-size:14px;color:#666"><span id="replyName">${post.name!''}</span>&nbsp;发表于
            <span id="replyTime">${post.initTime?string('yyyy-MM-dd HH:mm:ss')}</span></p>
        <p id="replyContent" style="font-size:14px;color:#666;overflow: auto;max-height: 100px;margin: 10px;">
        </p>
        <div>
            <span class="cancel_reply">X</span>
            <div>
                <textarea id="toReplyContentEditor" name="content" style="height: 150px"></textarea>
            </div>
            <div class="layui-form-item">
                <button class="layui-btn J-toReply-submit" style="background-color:#2cb82c" lay-filter="reply"
                        lay-submit
                        data-replyid="">
                    提交回复
                </button>
            </div>
        </div>
    </div>
</form>

<#include "../footer.ftl">
<script>
    function goBack() {
        window.history.go(-1);
    }
</script>
</body>
</html>