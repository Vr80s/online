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
    <script src="/web/js/header-top.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/ajax.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/web/js/artTemplate.js"></script>

    <script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.all.min.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="/ueditor/lang/zh-cn/zh-cn.js"></script>

    <script src="/web/js/bbs/add.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>

<header>
<#include "../header-body.ftl">
</header>
<#include "../tip.ftl">

<div class="main layui-clear">
    <div class="fly-panel" pad20>
        <h2 class="page-title">发布帖子</h2>

        <div class="layui-form layui-form-pane">
            <form method="post">
                <div class="layui-form-item">
                    <label for="L_title" class="layui-form-label">标题</label>
                    <div class="layui-input-block">
                        <input type="text" id="L_title" name="title" required lay-verify="required" autocomplete="off"
                               class="layui-input" placeholder="请输入5-50字的标题">
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">标签选择框</label>
                        <div class="layui-input-block">
                            <div class="layui-input-inline">
                                <select lay-verify="required" lay-search="" id="label_select"
                                        name="labelId" style="display: block;">
                                    <option value="">请选择发帖标签</option>
                                <#list labels as label>
                                    <option value="${label.id}">${label.name}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="layui-form-item layui-form-text">
                    <div class="layui-input-block">
                        <textarea id="J_content" name="content" placeholder="请输入内容" style="height: 260px; display: none;"></textarea>
                    </div>
                    <label for="L_content" class="layui-form-label hide" style="top: -2px;">描述</label>
                </div>


                <div class="layui-form-item">
                    <button class="layui-btn" lay-filter="*" lay-submit style="float:right; background-color: #2cb82c;">
                        立即发布
                    </button>
                </div>
            </form>
        </div>
    </div>

<#include "../footer.ftl">

</body>
</html>