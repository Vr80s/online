var workTime;
$(function () {
    if (localStorage.AccountStatus == 1) {
        //主播是医师的身份
        //医馆的消失
        $('.clinics').addClass('hide');
        $('.physician').removeClass('hide');

        //初始化医师的信息
        initAuthentication();

    } else if (localStorage.AccountStatus == 2) {
        //主播是医馆的身份
        $('.clinics').removeClass('hide');
        $('.physician').addClass('hide');
        //初始化主播是的医馆信息
        initAuthenticationHos();
    }


    var anchor_details_editor = UE.getEditor('anchor_details_editor', {
        toolbars: [['source', //源代码
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
        ]],
        initialFrameWidth: 540,
        initialFrameHeight: 250,
        elementPathEnabled: false,
        autoHeightEnabled: false,
        autoFloatEnabled: true,
        enableAutoSave: false,
        imagePopup: false,
        maximumWords: 10000       //允许的最大字符数
    });

    $('#u_workTime  li').click(function () {
        $(this).toggleClass('color');
    })
});

//初始化主播是医师信息
function initAuthentication() {
    RequestService("/doctor/apply/getLastOne", "get", null, function (data) {
        if (data.resultObject == null) return;
        data = data.resultObject;
        $(".anchor_name").html(data.name);
        $(".anchor_idCard").html(data.cardNum);
        $(".anchor_idCard_a").attr("src", data.cardPositive);
        $(".anchor_idCard_b").attr("src", data.cardNegative);
        $(".anchor_qualification_certificate").attr("src", data.qualificationCertificate);
        $(".anchor_professional_certificate").attr("src", data.professionalCertificate);
    });
}

//初始化主播是医馆信息

function initAuthenticationHos() {
    RequestService("/hospital/authentication/get", "get", null, function (data) {
        if (data.resultObject == null) return;
        data = data.resultObject;
        console.log(data);
        $(".account_one .hosName").html(data.name);
        $(".account_one .companyName").html(data.company);
        $(".account_one .businessLicenseNo").text(data.businessLicenseNo)
        $(".account_one .licenseForPharmaceuticalTrading").text(data.licenseForPharmaceuticalTrading)
        $(".account_one .businessLicensePicture").attr("src", data.businessLicensePicture);
        $(".account_one .licenseForPharmaceuticalTradingPicture").attr("src", data.licenseForPharmaceuticalTradingPicture);
        $(".anchor_qualification_certificate").attr("src", data.qualificationCertificate);
        $(".anchor_professional_certificate").attr("src", data.professionalCertificate);
    });
}


function savePhysicianApply() {
    var physician = getPhysicianData();
    if (verifyPhysician(physician)) {
        RequestService("/doctor/apply", "post", physician, function (data) {
            alert(data)
        }, false);
    }
}

function getPhysicianData() {
    var data = {};
    data.name = $.trim($('.physician_name').val());
    data.cardNum = $.trim($('.physician_card').val());
    data.cardPositive = $.trim($('#cardPositiveImg img').attr('src'));
    data.cardNegative = $.trim($('#cardNegativeImg img').attr('src'));
    data.qualificationCertificate = $.trim($('#qualificationCertificateImg img').attr('src'));
    data.professionalCertificate = $.trim($('#professionalCertificateImg img').attr('src'));
    return data;
}

function verifyPhysician(data) {
    var NameWarnings = $.trim($('.physician_two .name_put0').val());
    var NameCard = $.trim($('.physician_two .name_put1').val());
    var name = $.trim($('.physician_two .name_put0').val());
    var name_pass = /^[\u4E00-\u9FA5]{1,6}$/;
    ;
    var doc_Idnum = $.trim($('#AutList .doc_Idnum').val());
    var doc_Idnum_pass = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
    var personInt = UE.getEditor('editor').getContent();

    //姓名为空
    if (data.name == '') {
        $('.warning_physician_name').removeClass('hide');
        return false;
    } else {
        $('.warning_physician_name').addClass('hide');
    }
    //身份证号为空
    if (data.cardNum == '') {
        $('.warning_physician_card').removeClass('hide');
        return false;
    } else {
        $('.warning_physician_card').addClass('hide');
    }
    //身份证号校验
    if (!isCardID(data.cardNum)) {
        $('.warning_physician_card_verify').removeClass('hide');
        return false;
    } else {
        $('.warning_physician_card_verify').addClass('hide');
    }
    if (data.cardPositive == '') {
        $('.warning_cardPositiveImg').removeClass('hide');
        return false;
    } else {
        $('.warning_cardPositiveImg').addClass('hide');
    }
    if (data.cardNegative == '') {
        $('.warning_cardNegativeImg').removeClass('hide');
        return false;
    } else {
        $('.warning_cardNegativeImg').addClass('hide');
    }
    if (data.qualificationCertificate == '') {
        $('.warning_qualificationCertificateImg').removeClass('hide');
        return false;
    } else {
        $('.warning_qualificationCertificateImg').addClass('hide');
    }
    if (data.professionalCertificate == '') {
        $('.warning_professionalCertificateImg').removeClass('hide');
        return false;
    } else {
        $('.warning_professionalCertificateImg').addClass('hide');
    }
    return true;
}

function saveClinicsApply() {
    var clinics = getClinicsData();
    if (verifyClinics(clinics)) {
        RequestService("/doctor/apply", "post", clinics, function (data) {
            alert(data)
        }, false);
    }
}

function getClinicsData() {
    var data = {};
    data.name = $.trim($('.clinics_name').val());
    data.company = $.trim($('.company').val());
    data.businessLicenseNo = $.trim($('.businessLicenseNo').val());
    data.businessLicensePicture = $.trim($('#businessLicensePictureImg img').attr('src'));
    data.licenseForPharmaceuticalTradingPicture = $.trim($('#licenseForPharmaceuticalTradingPictureImg img').attr('src'));
    return data;
}

function verifyClinics(data) {

    if (data.name == '') {
        $('.warning_name').removeClass('hide');
        return false;
    } else {
        $('.warning_name').addClass('hide');
    }
    //身份证号为空
    if (data.company == '') {
        $('.warning_company').removeClass('hide');
        return false;
    } else {
        $('.warning_company').addClass('hide');
    }
    if (data.businessLicenseNo == '') {
        $('.warning_businessLicenseNo').removeClass('hide');
        return false;
    } else {
        $('.warning_businessLicenseNo').addClass('hide');
    }
    if (data.businessLicensePicture == '') {
        $('.warning_businessLicensePictureImg').removeClass('hide');
        return false;
    } else {
        $('.warning_businessLicensePictureImg').addClass('hide');
    }
    if (data.licenseForPharmaceuticalTradingPicture == '') {
        $('.warning_licenseForPharmaceuticalTradingPictureImg').removeClass('hide');
        return false;
    } else {
        $('.warning_licenseForPharmaceuticalTradingPictureImg').addClass('hide');
    }
    return true;
}

function isCardID(sId) {
    var iSum = 0;
    var info = "";
    if (!/^\d{17}(\d|x)$/i.test(sId))
    //return "你输入的身份证长度或格式错误";
        return false;
    sId = sId.replace(/x$/i, "a");
    if (aCity[parseInt(sId.substr(0, 2))] == null)
    //return "你的身份证地区非法";
        return false;
    sBirthday = sId.substr(6, 4) + "-" + Number(sId.substr(10, 2)) + "-" + Number(sId.substr(12, 2));
    var d = new Date(sBirthday.replace(/-/g, "/"));
    if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate()))
    //return "身份证上的出生日期非法";
        return false;
    for (var i = 17; i >= 0; i--) iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11);
    if (iSum % 11 != 1)
    //return "你输入的身份证号非法";
        return false;
    return true;
}

//储存主播信息
function saveAnchorInfo() {

    //基础信息验证通过了验证医师医馆对应的信息
    if (localStorage.AccountStatus == 1) {
        var anchorInfo1 = getAnchorInfo();
        if (verifyAnchorInfo(anchorInfo1)) {
            //验证通过之后进行
            RequestService("/anchor/info", "post", anchorInfo1, function (data) {

//		console.log(data)
                if (data.success == true) {
                    showTip('修改成功')
                    setTimeout(function () {
                        $('.name_personage').click()
                    }, 2000)

                }
            })
        }
    }


    if (localStorage.AccountStatus == 2) {
        var anchorInfo2 = getAnchorInfo2();
        if (verifyAnchorInfo2(anchorInfo2)) {
            //验证通过之后进行
            RequestService("/anchor/info", "post", anchorInfo2, function (data) {

//		console.log(data)
                if (data.success == true) {
                    showTip('修改成功')
                    setTimeout(function () {
                        $('.name_personage').click()
                    }, 2000)
                }
            })
        }
    }

}


//获取的主播是医师的信息
function getAnchorInfo() {
    var data = {};
    data.name = $("#u_nickname").val();
    data.resourceId = $("#speech_select").val();
    data.profilePhoto = $("#profilePhotoImg img").attr('src');
    data.detail = UE.getEditor('anchor_details_editor').getContent();
    data.hospitalId = $("#speech_select1").val();
    if (data.hospitalId == '-1') data.hospitalId = null;
    var arr = [];
    $("#u_workTime li").each(function(){
        if($(this).hasClass("color")){
            arr.push($(this).html())
        }
    });
    workTime = arr.join(",");
    data.workTime = workTime;
    data.province = $("#demo1 #hosPro ").val();
    data.city = $('#demo1 #hosCity ').val();
    data.detailAddress = $('#demo1 textarea').val();
    data.tel = $('#doctor_baseInf .appointmentTel').val();
    return data;
}


//获取的主播是医馆的信息
function getAnchorInfo2() {
    var data = {};
    data.name = $("#u_nickname").val();
    data.resourceId = $("#speech_select").val();
    data.profilePhoto = $("#profilePhotoImg img").attr('src');
    data.detail = UE.getEditor('anchor_details_editor').getContent();
    data.province = $("#demo1 .choosePro option:selected").text();
    data.city = $('#demo1 .chooseCity option:selected').text();
    data.detailAddress = $('#demo1 textarea').val();
    data.tel = $('#u_hospital_tel').val();
    return data;
}


//主播是医师的信息验证
function verifyAnchorInfo(data) {
    if (data.name == '') {
        $('.warning_anchor_name').removeClass('hide');
        return false;
    } else {
        $('.warning_anchor_name').addClass('hide');
    }

    if (data.profilePhoto == '' || data.profilePhoto == null) {
        $('.warning_profileImgphoto').removeClass('hide');
        return false;
    } else {
        $('.warning_profileImgphoto').addClass('hide');
    }

//   if(data.video == ''){
//      $('.warning_anchor_Speech').removeClass('hide');
//      return false;
//  }else{
//      $('.warning_anchor_Speech').addClass('hide');
//  }

    if (data.detail == '') {
        $('.warning_anchor_lecturer_description').removeClass('hide');
        return false;
    } else {
        $('.warning_anchor_lecturer_description').addClass('hide');
    }


    //医师入驻的医馆名字
    // if(data.hospitalId == '-1'){
    // $('.return_warning4').removeClass('hide');
    // return false;
    // }else{
    //    $('.return_warning4').addClass('hide');
    // }

    //坐镇的时间
    if (data.workTime == '') {
        $('.return_warning7').removeClass('hide');
        return false;
    } else {
        $('.return_warning7').addClass('hide');
    }
//	
//	//医师所在省市填写
    if (data.province == '-1' || data.city == '-1') {
        $('.return_warning6').removeClass('hide');
        return false;
    } else {
        $('.return_warning6').addClass('hide');
    }

    return true;
}


//主播是医馆的信息验证
function verifyAnchorInfo2(data) {
    var phoneNumPass = /^\d[\d\-]*$/;
    if (data.name == '') {
        $('.warning_anchor_name').removeClass('hide');
        return false;
    } else {
        $('.warning_anchor_name').addClass('hide');
    }

    if (data.profilePhoto == '' || data.profilePhoto == null) {
        $('.warning_profileImgphoto').removeClass('hide');
        return false;
    } else {
        $('.warning_profileImgphoto').addClass('hide');
    }
//  
//   if(data.video == ''){
//      $('.warning_anchor_Speech').removeClass('hide');
//      return false;
//  }else{
//      $('.warning_anchor_Speech').addClass('hide');
//  }

    if (data.detail == '') {
        $('.warning_anchor_lecturer_description').removeClass('hide');
        return false;
    } else {
        $('.warning_anchor_lecturer_description').addClass('hide');
    }


    if (data.tel == '') {
        $('.return_warning8').text('预约电话不能为空');
        $('.return_warning8').removeClass('hide');
        return false;
    } else if (!phoneNumPass.test(data.tel) || data.tel.length < 7) {
        $('.return_warning8').text('电话格式不正确');
        $('.return_warning8').removeClass('hide');
        return false;
    } else {
        $('.return_warning8').addClass('hide');
    }

    //	//医师所在省市填写
    if (data.province == '请选择省' || data.city == '请选择市') {
        $('.return_warning6').removeClass('hide');
        return false;
    } else {
        $('.return_warning6').addClass('hide');
    }

    if (data.detailAddress == '') {
        $('.return_warning5 ').removeClass('hide');
        return false;
    } else {
        $('.return_warning5 ').addClass('hide');
    }

    return true;
}

//主播信息渲染
function showAnchorInfo() {
    if ($('.personal_details').hasClass('hide')) {
        $(".message_return .message_title .two").click()
    }
    $.ajax({
        type: "GET",
        url: "/anchor/info",
        async: true,
        success: function (result) {
            if (result.success) {
                if (localStorage.AccountStatus == '2') {
                    $('#demo1 .choosePro option:selected').text(result.resultObject.province);
                    $('#demo1 .chooseCity option:selected').text(result.resultObject.city);
                    $('#u_hospital_tel').val(result.resultObject.tel);

                }
                var anchor = result.resultObject;
                console.log(anchor.name)

                $('#u_nickname').val(anchor.name);
                $('#nickname').text(anchor.name);
                $('#nickname').attr('title', '');
                $('#profilePhoto').attr('src', anchor.profilePhoto);

//              $('#intersting').html(anchor.video);
                if (anchor.hospitalName) {
                    $('#hospitalName').html(anchor.hospitalName);
                } else {
                    $('#hospitalName').text('暂无');
                }
                if (anchor.workTime) {
                    $('#workTime').html("每周" + anchor.workTime);
                } else {
                    $('#workTime').html("暂无");
                }
                if (anchor.detail) {
                    $('#detail').html(anchor.detail);
                } else {
                    $('#detail').html('暂无');
                }
                if (anchor.video) {
                    $('#intersting').html(anchor.video);
                } else {
                    $('#intersting').html('暂无');
                }
                if (anchor.tel) {
                    $('#tel').text(anchor.tel);
                } else {
                    $('#tel').text('暂无');
                }
                if (anchor.province) {
                    $('#province').text(anchor.province);
                } else {
                    $('#province').text('暂无');
                }
                if (anchor.city) {
                    $('#city').text(anchor.city);
                } else {
                    $('#city').text('暂无');
                }
                if (anchor.detailAddress) {
                    $('#detailAddress').text(anchor.detailAddress);
                } else {
                    $('#detailAddress').text('暂无');
                }
            }
        }
    })
    localStorage.AnchorsTbl_accountInf = 'name_personage';
}


