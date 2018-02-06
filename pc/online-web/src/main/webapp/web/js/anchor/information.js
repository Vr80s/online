$(function(){
    initAuthentication();
    var anchor_details_editor = UE.getEditor('anchor_details_editor',{
        toolbars:[['source', //源代码
            'undo', //撤销
            'redo', //重做
            'bold', //加粗
            'forecolor', //字体颜色
            'backcolor', //背景色
            'indent', //首行缩进
            'removeformat',//清除格式
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
            'justifyjustify',//两端对齐
            'link', //超链接
            'unlink', //取消链接
            'simpleupload', //单图上传
            // 'insertimage', //多图上传
            'emotion', //表情
            'fullscreen'
        ] ],
        elementPathEnabled:false,
        autoHeightEnabled: false,
        autoFloatEnabled: true,
        enableAutoSave:false,
        imagePopup:false,
        maximumWords:10000       //允许的最大字符数
    });
});

function initAuthentication (){

    RequestService("/medical/doctor/apply/getLastOne", "get", null, function(data) {
        if(data.resultObject==null)return;
        data = data.resultObject;
        $(".anchor_name").html(data.name);
        $(".anchor_idCard").html(data.cardNum);
        $(".anchor_idCard_a").attr("src",data.cardPositive);
        $(".anchor_idCard_b").attr("src",data.cardNegative);
        $(".anchor_qualification_certificate").attr("src",data.qualificationCertificate);
        $(".anchor_professional_certificate").attr("src",data.professionalCertificate);
    });
}

