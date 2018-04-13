$(function () {
    $('.bbs-tab').addClass("select");
    var toolbars = [
        [
            'undo', //撤销
            'redo', //重做
            'bold', //加粗
            'forecolor', //字体颜色
            'backcolor', //背景色
            'indent', //首行缩进
            'removeformat', //清除格式
            'formatmatch', //格式刷
            'blockquote', //引用
            'fontfamily', //字体
            'fontsize', //字号
            'paragraph', //段落格式
            'italic', //斜体
            'underline', //下划线
            'strikethrough', //删除线
            'superscript', //上标
            'subscript', //下标
            'touppercase', //字母大写
            'tolowercase', //字母小写
            'justifyleft', //居左对齐
            'justifyright', //居右对齐
            'justifycenter', //居中对齐
            'justifyjustify', //两端对齐
            'link', //超链接
            'unlink', //取消链接
            'simpleupload', //单图上传
            'emotion', //表情
            'fullscreen'
        ]
    ];
    var options = {
        toolbars: toolbars,
        elementPathEnabled: false,
        autoHeightEnabled: false,
        autoFloatEnabled: true,
        enableAutoSave: false,
        imagePopup: false,
        maximumWords: 10000
    };

    var replyContentEditor = UE.getEditor('replyContentEditor', options);
    var toReplyContentEditor;
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function (action) {
        var url = '/ueditor/upload';
        if (action === 'uploadimage' || action === 'uploadscrawl' || action === 'uploadimage' || action === 'uploadvideo') {
            return url;
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    };

    //回复帖子
    $('.J-reply-submit').on('click', function (e) {
        e.preventDefault();
        var content = replyContentEditor.getContent();
        if (!content) {
            tip("输入的内容不能为空");
            return false;
        }
        if (content.length > 5000) {
            tip("输入的内容过多");
            return false;
        }
        var reply = {};
        reply.content = content;
        reply.postId = $('#J_id').val();
        $.ajax({
            url: '/bbs/reply',
            method: 'POST',
            data: JSON.stringify(reply),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    tip("回复成功");
                    location.href = "/bbs/" + reply.postId;
                } else {
                    tip(data.errorMessage, {icon: 7});
                }
            }
        });
    });

    //回复的回复
    $('.J-toReply-submit').on('click', function (e) {
        e.preventDefault();
        var toReplyContent = toReplyContentEditor.getContent();
        if (!toReplyContent) {
            tip("输入的内容不能为空");
            return false;
        }
        if (toReplyContent.length > 5000) {
            tip("输入的内容过多");
            return false;
        }
        var toReply = {};
        toReply.content = toReplyContent;
        toReply.postId = $('#J_id').val();
        toReply.targetReplyId = $(this).data('replyid');
        $.ajax({
            url: '/bbs/reply',
            method: 'POST',
            data: JSON.stringify(toReply),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    tip("回复成功");
                    location.href = "/bbs/" + toReply.postId;
                } else {
                    tip(data.errorMessage);
                }
            }
        });
    });

    $('.reply').on('click', function () {
        $('.reply_list').removeClass('hide');
        var id = $(this).data('id');
        $("#replyContent").html($('.reply-content-' + id).html());
        console.log($('.reply-content-' + id).html());
        $('.J-toReply-submit').data('replyid', id);
        toReplyContentEditor = UE.getEditor('toReplyContentEditor', options);
    });

    $('.cancel_reply').on('click', function () {
        $('.reply_list').addClass('hide');
    })
});