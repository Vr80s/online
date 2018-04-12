$(function () {
    $('.bbs-tab').addClass("select");
    $('#J_content').show();
    var contentEditor = UE.getEditor('J_content', {
        toolbars: [
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
        ],
        elementPathEnabled: false,
        autoHeightEnabled: false,
        autoFloatEnabled: true,
        enableAutoSave: false,
        imagePopup: false,
        maximumWords: 10000
    });
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function (action) {
        var url = '/ueditor/upload';
        if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage' || action == 'uploadvideo') {
            return url;
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    };

    $('.layui-btn').on('click', function (e) {
        e.preventDefault();
        var $this = $(this);
        var content = contentEditor.getContent();
        var labelId = $('#label_select').val();
        var title = $('#L_title').val();
        if (!title) {
            tip('标题不能为空');
            return false;
        }
        if (title.length < 5 || title.length > 50) {
            tip('标题长度5-50');
            return false;
        }
        if (!labelId) {
            tip('标签必选！');
            return false;
        }
        if (!content) {
            tip('输入的内容不能为空');
            return false;
        }
        if (content.length > 10000) {
            tip('您输入的内容过多');
            return false;
        }
        var post = {
            title: title,
            content: content,
            labelId: labelId
        };
        if ($this.attr('disabled')) {
            return false;
        }
        $this.attr('disabled', 'disabled');
        $.ajax({
            url: '/bbs',
            method: 'POST',
            data: JSON.stringify(post),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    tip("发布成功");
                    window.location.href = '/bbs';
                } else {
                    $this.removeAttr('disabled');
                    tip(data.errorMessage);
                }
            }
        });
    });
});