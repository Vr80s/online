var userPic = $('.userPic').css('background')
RequestService("/online/user/isAlive", "get", null, function (data) {
    //头像预览
    if (data.resultObject.smallHeadPhoto) {
        $('.doctor_inf>img').attr('src', data.resultObject.smallHeadPhoto);
        $('.doctor_inf>h4').text(data.resultObject.name);
    } else {
        $('.doctor_inf>img').attr('src', "/web/images/defaultHeadImg.jpg");
    }


});
//获取医馆认证状态控制左侧tab栏
RequestService("/medical/common/isDoctorOrHospital", "GET", null, function (data) {
    if (data.success == true) {
        var isDoctorOrHospital = data.resultObject.isDoctorOrHospital;
        if (isDoctorOrHospital != 2 && isDoctorOrHospital != -2) {
            //医馆认证未成功显示出来认证失败的页面
            $('#hos_Administration .hos_renzheng_inf .bottomContent').removeClass('hide');
            $('#hos_Administration .hos_renzheng_inf .bottomContent2').addClass('hide');

            if (isDoctorOrHospital == 4 || isDoctorOrHospital == 6) {
                //认证中
                $('#hos_Administration .hos_renzheng_inf .bottomContent').addClass('hide');
                $('#hos_Administration .hos_renzheng_inf .bottomContent2').removeClass('hide');

            } else if (isDoctorOrHospital == 7 || isDoctorOrHospital == 3 || isDoctorOrHospital == 5) {
                //拒绝的情况
                RequestService("/doctor/apply/getLastOne", "get", null, function (data) {
                    if (isDoctorOrHospital && data.resultObject.status == 0) {
//                      $('#hos_Administration .hos_renzheng_inf .bottomContent').addClass('hide');
//                      $('#hos_Administration .hos_renzheng_inf .bottomContent2').removeClass('hide');
			 			$('#hos_Administration .hos_renzheng_inf .bottomContent2').addClass('hide');
                        $('#hos_Administration .hos_renzheng_inf .bottomContent').removeClass('hide');
                        
                    }
                })
                $('#hos_Administration .hos_renzheng_inf .bottomContent').removeClass('hide');
                $('#hos_Administration .hos_renzheng_inf .bottomContent2').addClass('hide');
            }
        } else if (isDoctorOrHospital == 2 ||isDoctorOrHospital == -2) {
            //医馆认证成功 左侧tab显示出来 医馆基础信息显示出来
            $('#doc_Administration_tabBtn').removeClass('hide');
            $('#collect_Administration_tabBtn').removeClass('hide');
            $('#news_Administration_tabBtn').removeClass('hide');

            $('#hos_base_inf').removeClass('hide');
            $('#hos_Administration .hos_renzheng_inf .bottomContent').addClass('hide');
            $('#hos_Administration .hos_renzheng_inf .bottomContent2').removeClass('hide');
        }
    }

});

$(".doctor_inf >img").attr('src', userPic)
$(".doctor_inf > img,.news_nav .picModal").on("click", function () {
    $(".mask").css("display", "block");
    $("#headImg").css("display", "block");
    $("body").css("overflow", "hidden");
    //清空右侧小图片
    //						$('.cropped-con').html('');
    RequestService("/online/user/isAlive", "get", null, function (data) {
        var path;
        //头像预览
        if (data.resultObject.smallHeadPhoto) {
            if (data.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
                path = data.resultObject.smallHeadPhoto;
            } else {
                path = bath + data.resultObject.smallHeadPhoto
            }
        }
        ;
        $('.cropped-con').html('');
        $('.cropped-con').append('<img src="' + path + '" align="absmiddle" style="width:80px;height:80px;margin-top:28px;border-radius:80px;" class="img01"><p style="font-size:12px;color:#666;margin-top:6px;">80px*80px</p>');
        $('.cropped-con').append('<img src="' + path + '" align="absmiddle" style="width:40px;height:40px;margin-top:28px;border-radius:40px;"><p style="font-size:12px;color:#666;margin-top:6px;">40px*40px</p>');
        img()
        //新插件
    });
})

function img() {
    //清空文件
    function clearFileInput(file) {
        var form = document.createElement('form');
        document.body.appendChild(form);
        //记住file在旧表单中的的位置
        var pos = file.nextSibling;
        form.appendChild(file);
        form.reset();
        pos.parentNode.insertBefore(file, pos);
        document.body.removeChild(form);
    }

    $(".imgClose,.btn-quit").click(function () {
        $('.cropped-con').html('');
        $(".img-box1").css("display", "block");
        $(".imageBox").css("display", "none");
        $(".tc").css("display", "none");
        $(".mask").css("display", "none");
        $("#headImg").css("display", "none");
        $("body").css("overflow", "auto");
        var file = document.getElementById("upload-file");
        clearFileInput(file);
        $(".btn-upload").attr("data-img", "");
        $(".imageBox").css("background-image", "");
    })
    var options = {
        thumbBox: '.thumbBox',
        spinner: '.spinner',
        imgSrc: ""
    }
    var cropper = $('.imageBox').cropbox(options);
    var img = "";
    $('#upload-file').on('change', function () {
        var filepath = $(this).val();
        if (filepath == "") {
            return false;
        }
        var extStart = filepath.lastIndexOf(".");
        var ext = filepath.substring(extStart, filepath.length).toUpperCase();
        if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
            $(".rrrrTips").text("图片限于bmp,png,gif,jpeg,jpg格式").css("display", "block");
            var file = document.getElementById("upload-file");
            clearFileInput(file);
            setTimeout(function () {
                $(".rrrrTips").css("display", "none");
            }, 1500);
        } else if (($("#upload-file").get(0).files[0].size / 1024 / 1024) > 1) {
            $(".rrrrTips").text("图片大小不超过1M").css("display", "block");
            var file = document.getElementById("upload-file")
            clearFileInput(file);
            setTimeout(function () {
                $(".rrrrTips").css("display", "none");
            }, 1500);
        } else {
            if (filepath) {
                $(".img-box1").css("display", "none");
                $(".imageBox").css("display", "block");
                $(".tc").css("display", "block");
                var reader = new FileReader();
                reader.onload = function (e) {
                    options.imgSrc = e.target.result;
                    cropper = $('.imageBox').cropbox(options);
                    getImg();
                }
                reader.readAsDataURL(this.files[0]);
                this.files = [];
                getImg();
            }
        }
    })

    function getImg() {
        img = cropper.getDataURL();
        $('.cropped-con').html('');
        $('.cropped-con').append('<img src="' + img + '" align="absmiddle" style="width:80px;height:80px;margin-top:28px;border-radius:80px;" class="img01"><p style="font-size:12px;color:#666;margin-top:6px;">80px*80px</p>');
        $('.cropped-con').append('<img src="' + img + '" align="absmiddle" style="width:40px;height:40px;margin-top:28px;border-radius:40px;"><p style="font-size:12px;color:#666;margin-top:6px;">40px*40px</p>');
        $(".btn-upload").attr("data-img", img);
    }

    $(".imageBox").on("mousemove mouseup", function () {
        getImg()
    })
}

function fileClick() {
    return $("#upload-file").click();
}

$(".btn-upload").click(function (evt) {
    evt.preventDefault();
    if ($(".btn-upload").attr("data-img") != undefined && $(".btn-upload").attr("data-img") != "") {
    } else {
        $(".rrrrrTips").text("请选择图片").css("display", "block");
        setTimeout(function () {
            $(".rrrrrTips").css("display", "none");
        }, 1500);
        return false;
    }
    $(".btn-upload").css("color", "white");
    RequestService("/online/user/updateHeadPhoto", "post", {
        image: $(".btn-upload").attr("data-img"),
    }, function (data) {
        if (data.success == true) {
            RequestService("/online/user/isAlive", "get", null, function (t) {
                var path;
                if (t.resultObject.smallHeadPhoto) {
                    if (t.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
                        path = t.resultObject.smallHeadPhoto;
                    } else {
                        path = bath + t.resultObject.smallHeadPhoto
                    }
                    $(".userPic").css({
                        background: "url(" + path + ") no-repeat",
                        backgroundSize: "100% 100%"
                    });
                    $(".doctor_inf >img").attr('src', path)

                    var file = document.getElementById("upload-file")

                    //清空文件
                    function clearFileInput(file) {
                        var form = document.createElement('form');
                        document.body.appendChild(form);
                        //记住file在旧表单中的的位置
                        var pos = file.nextSibling;
                        form.appendChild(file);
                        form.reset();
                        pos.parentNode.insertBefore(file, pos);
                        document.body.removeChild(form);
                    }

                    clearFileInput(file);
                    $('.cropped-con').html('');
                    $(".img-box1").css("display", "block");
                    $(".imageBox").css("display", "none");
                    $(".tc").css("display", "none");
                    $(".mask").css("display", "none");
                    $("#headImg").css("display", "none");
                    location.reload();
                }

            })

        }
    })

    $(".btn-upload").css("color", "white");
})

//医馆管理下拉列表功能
$('.luntan').click(function () {
    $('.luntan_list').slideToggle();

})
//医馆管理下拉列表功能  箭头
$(".hos_point").bind('click', function () {
    if ($(this).attr("id") == "hos_point") {
        $(this).removeAttr("id")
        $(".hos_point_icon").removeClass("glyphicon-triangle-right")
        $(".hos_point_icon").addClass("glyphicon-triangle-bottom")

    } else {
        $(this).attr("id", "hos_point")
        $(".hos_point_icon").removeClass("glyphicon-triangle-bottom")
        $(".hos_point_icon").addClass("glyphicon-triangle-right")
    }
})
//左侧鼠标移动上去变色效果
$('#doctor_in_inf .news_nav ul li a').mouseenter(function () {
    $(this).children('i').css('color', '#00bc12')
})
//鼠标移除
$('#doctor_in_inf .news_nav ul li a').mouseout(function () {
    $(this).children('i').css('color', '#cacbcb')
})
//一级菜单点击变色效果
$('#doctor_in_inf .news_nav > ul > li > a').click(function () {
    $('#doctor_in_inf .news_nav ul li a').removeClass('color');
    $('#doctor_in_inf .news_nav ul li a > i').removeClass('color');
    $(this).addClass('color');
    $(this).children('i').addClass('color');
    if (localStorage.hos_Administration == "hos_base_inf") $('#hos_base_inf').addClass('color');
    if (localStorage.hos_Administration == "hos_renzhneg_inf") $('#hos_renzhneg_inf').addClass('color')

    //储存定位作用localstorage
    window.localStorage.hosTabSta = $(this).attr('data-name');

})

//二级菜单点击变色效果
$('#doctor_in_inf .news_nav > ul > li div a').click(function (event) {
    event.stopPropagation();
    $('#doctor_in_inf .news_nav ul li div a').removeClass('color');
    $('#doctor_in_inf .news_nav ul li div a > span').removeClass('color');
    $(this).addClass('color');
    $(this).children('span').addClass('color');

    //储存定位作用localstorage
    window.localStorage.hosTabSta = $(this).attr('data-name');

})

//点击其他的时候下拉的列表还原
$('.hos_left_list > li:nth-child(n+3)').click(function () {
    $('.luntan_list').slideUp();
    $(".hos_point").attr("id", "hos_point");
    $(".hos_point_icon").removeClass("glyphicon-triangle-bottom")
    $(".hos_point_icon").addClass("glyphicon-triangle-right")
})

function initDecsription() {
    var ue = UE.getEditor('editor2', {
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
                // 'insertimage', //多图上传
                //				'emotion', //表情
                'fullscreen'
            ]
        ],
        initialFrameWidth: 540,
        initialFrameHeight: 220,
        elementPathEnabled: false,
        autoHeightEnabled: false,
        autoFloatEnabled: true,
        enableAutoSave: false,
        imagePopup: false,
        autoFloatEnabled:false,
        maximumWords: 10000 //允许的最大字符数
    });
}
//医馆管理部分
//医馆管理部分下拉列表点击 右侧内容对应变化
$('#hos_base_inf').click(function () {
    $('.hos_renzheng_inf').addClass('hide');
    $('.hos_base_inf ').removeClass('hide');

    localStorage.hos_Administration = 'hos_base_inf';


    //调用医馆详细信息借口数据
    RequestService("/hospital/getHospitalByUserId", "get", null, function (data) {
        if (data.success && data.resultObject == null) {
            //清空
            baseInfrese();
            $(".doc_address").iProvincesSelect("init", null);
        } else if (data.success && data.resultObject != null) {
            //回显数据
            baseInfrese1(data.resultObject.headPortrait, data.resultObject.name, data.resultObject.medicalHospitalPictures, data.resultObject.fields, data.resultObject.description,
                data.resultObject.contactor, data.resultObject.email,
                data.resultObject.wechat, data.resultObject.province,
                data.resultObject.city, data.resultObject.county,
                data.resultObject.detailedAddress, data.resultObject.tel)
        }
    })
})

//医馆基础信息清空
function baseInfrese() {
    //头像
    var headPic = '<p style="font-size: 90px;height: 80px;font-weight: 300;color: #d8d8d8;text-align: center;line-height: 80px;">+</p><p style="text-align: center;color: #999;font-size: 14px;">求真相</p>';
    $('#hos_Administration .hos_base_inf .bottomContent .touxiang_pic').html(headPic);
    //医馆名称
    $('#hos_Administration .hos_base_inf .bottomContent .doc_zhicheng').val('');
    //医馆图片
    var hosPic = '<p style="font-size: 90px;height: 100px;font-weight: 300;color: #d8d8d8;text-align: center;">+</p><p style="text-align: center;color: #999;font-size: 14px;">点击上传医馆图片</p>';
    $('#hos_Administration .hos_base_inf .bottomContent #hos_pic').html('');
    //领域
    $('#hos_Administration .hos_base_inf .bottomContent #areaList li').removeClass('keshiColor');
    initDecsription();
    //富文本
    UE.getEditor('editor2').addListener( 'ready', function( editor ) {
        UE.getEditor('editor2').setContent("");
    } );

    //联系人姓名
    $('#hos_Administration .hos_base_inf .bottomContent .doc_shanchang').val('');
    //邮箱
    $('#hos_Administration .hos_base_inf .bottomContent .doc_hospital').val('');
    //微信
    $('#hos_Administration .hos_base_inf .bottomContent .hos_weixin').val('');
    //详细地址
    $('#hos_Administration .hos_base_inf .doc_address textarea').val('');
}

//医馆基础信息回显
function baseInfrese1(headPortrait, name, medicalHospitalPictures, fields,
                      description, contactor, email, wechat, province, city, county, detailedAddress, tel) {
    var data = null;
    if (isNotBlank(province) && isNotBlank(city)) {
    	data={};
    	data.province=province;
    	data.city=city;
    }
    $(".doc_address").iProvincesSelect("init",data);
    $("#hospital-inf-write .warning").addClass("hide");
	$("#hospital-inf-write input").removeClass("border_hide_null");
		    
    //头像
    if (headPortrait != null) {
        var headPic = '<img src=' + headPortrait + '>';
        $('#hos_Administration .hos_base_inf .bottomContent .touxiang_pic').html(headPic);
    }

    //医馆名称
    $('#hos_Administration .hos_base_inf .bottomContent .doc_zhicheng').attr('disabled', 'disabled');
    $('#hos_Administration .hos_base_inf .bottomContent .doc_zhicheng').css('background', '#f0f0f0')
    $('#hos_Administration .hos_base_inf .bottomContent .doc_zhicheng').val(name);
    //医馆图片
    if (medicalHospitalPictures.length > 0) {
        var hosPicStr = '';
        medicalHospitalPictures.forEach(function (v, i) {
            hosPicStr +=
                '<div style="position: relative;">' +
                '<span style="position: absolute;top: 5px;right: 5px;color:red;z-index:10;" class="hospic_del">X</span>' +
                '<img src="' + v.picture + '" >' +
                '</div>'

        })
        $('#hos_Administration .hos_base_inf .bottomContent #hos_pic').removeClass('hide').html(hosPicStr);
        if (medicalHospitalPictures.length > 3) {
            $('#hos_Administration .zhicheng_pic').css('padding-left', '110px')
        }
        if (medicalHospitalPictures.length == 9) {
            $('#upHosPic').addClass('hide');
        }
    }
    var j;
    for (var i = 0; i < $('#areaList li').length; i++) {
        for (j = 0; j < fields.length; j++) {
            if ($('#areaList li').eq(i).text() == fields[j].name && !$('#areaList li').eq(i).hasClass('keshiColor')) {
                $('#areaList li').eq(i).click();
            }
        }
    }
    initDecsription();
    //富文本
    UE.getEditor('editor2').addListener( 'ready', function( editor ) {
    	UE.getEditor('editor2').setContent(description==null?"":description);       
    } );
    //联系人姓名
    $('#hos_Administration .hos_base_inf .bottomContent .doc_shanchang').val(contactor);

    //联系人姓名
    $('#hos_Administration .hos_base_inf .bottomContent .doc_tel').val(tel);

    //邮箱
    $('#hos_Administration .hos_base_inf .bottomContent .doc_hospital').val(email);
    //微信
    $('#hos_Administration .hos_base_inf .bottomContent .hos_weixin').val(wechat);
    //详细地址
    $('#hos_Administration .hos_base_inf .doc_address textarea').val(detailedAddress);
}

//认证信息
$('#hos_renzhneg_inf').click(function () {
    $('.hos_base_inf ').addClass('hide');
    $('.hos_renzheng_inf').removeClass('hide');
    localStorage.hos_Administration = 'hos_renzhneg_inf';

})

//获取医疗领域数据
RequestService("/hospital/getFields/0", "get", null, function (data) {
    $('#areaList').html(template('areaTpl', {
        item: data.resultObject.records
    }));
})

//医馆基础信息上传图片调用的接口
function picUpdown(form, imgname) {
    $.ajax({
        type: 'post',
        url: "/medical/common/upload",
        data: form,
        cache: false,
        processData: false,
        contentType: false,
    }).success(function (data) {
        $('#hos_Administration .hos_base_inf  .' + imgname + '').html('<img src="' + data.resultObject + '?imageMogr2/thumbnail/!120x120r|imageMogr2/gravity/Center/crop/120x120' + '" >');
    });

}

//医馆认证上传图片调用的接口
function picUpdown3(form, imgname) {
    $.ajax({
        type: 'post',
        url: "/medical/common/upload",
        data: form,
        cache: false,
        processData: false,
        contentType: false,
    }).success(function (data) {
        $('#hos_Administration .hos_renzheng_inf  .' + imgname + '').html('<img src="' + data.resultObject + '" ><p class="img-agin-tip">点击图片重新上传</p>');
    });

}

//上传图片调用的接口
function picUpdown2(form, imgname) {
    $.ajax({
        type: 'post',
        url: "/medical/common/upload",
        data: form,
        cache: false,
        processData: false,
        contentType: false,
    }).success(function (data) {
        if ($('#hos_Administration .hos_base_inf  .' + imgname + ' img').length > 1) {
            $('#hos_Administration .hos_base_inf  .zhicheng_pic').css('padding-left', '110px')
            $('#hos_Administration .hos_base_inf  .' + imgname + '').css('float', 'left');
        } else {
            $('#hos_Administration .hos_base_inf  .' + imgname + '').css('float', 'left');
        }
        if ($('#hos_Administration #hos_pic img').length == 8) {
            $('#upHosPic').addClass('hide')
        }
        $('#hos_Administration #hos_pic').removeClass('hide');
        var picStr =
            '<div style="position: relative;">' +
            '<span style="position: absolute; top: 5px;right: 5px; color:red; z-index:10;" class="hospic_del">X</span>' +
            '<img src="' + data.resultObject + '?imageMogr2/thumbnail/!260x147r|imageMogr2/gravity/Center/crop/260x147' + '" >' +
            '</div>'
        $('#hos_Administration #hos_pic').append(picStr);
        $("#zhicheng_pic_ipt").val("");
        $("#picHospitalAll").addClass("hide");
    });

}

//删除医馆图片
$('#hos_pic').on('click', '.hospic_del', function () {
    $(this).parent().remove()
    $('#upHosPic').removeClass('hide')
    if ($('#hos_pic').children().length < 3) {
        $('#hos_Administration .zhicheng_picUpdata .zhicheng_pic').css('padding-left', '0px')

    }

})

//医馆头像上传
$('#touxiang_pic_ipt').on('change', function () {
    if (this.files[0].size > 2097152) {
        $('#tip').text('上传图片不能大于2M');
        $('#tip').toggle();
        setTimeout(function () {
            $('#tip').toggle();
        }, 2000)
        return false;
    }
    if (!(this.files[0].type.indexOf('image') == 0 && this.files[0].type && /\.(?:jpg|png|gif)$/.test(this.files[0].name))) {
        $('#tip').text('图片格式不正确');
        $('#tip').toggle();
        setTimeout(function () {
            $('#tip').toggle();
        }, 2000)
        return false;
    }
    var form = new FormData();
    form.append("image", this.files[0]);
    var reader = new FileReader();
    reader.onload = function (e) {
        picUpdown(form, 'touxiang_pic');
    }
    reader.readAsDataURL(this.files[0])
})

//医馆图片上传
$('#zhicheng_pic_ipt').on('change', function () {
    if (this.files[0].size > 2097152) {
        $('#tip').text('上传图片不能大于2M');
        $('#tip').toggle();
        setTimeout(function () {
            $('#tip').toggle();
        }, 2000)
        return false;
    }
    if (!(this.files[0].type.indexOf('image') == 0 && this.files[0].type && /\.(?:jpg|png|gif)$/.test(this.files[0].name))) {
        $('#tip').text('图片格式不正确');
        $('#tip').toggle();
        setTimeout(function () {
            $('#tip').toggle();
        }, 2000)
        return false;
    }
    var form = new FormData();
    form.append("image", this.files[0]);
    var reader = new FileReader();
    reader.onload = function (e) {
        picUpdown2(form, 'zhicheng_pic');
    }
    reader.readAsDataURL(this.files[0])
})

//医馆科室选择生成对应的数组
var arr = [];
var areaList;
$('#hos_Administration .hos_base_inf ').on('click', '#areaList>li', function () {
    if ($(this).hasClass('keshiColor')) {
        //删除第二次选中的
        for (var i = 0; i < arr.length; i++) {
            if ($(this).attr('data-id') == arr[i]) {
                arr.splice(i, 1)
            }
        }
        //			console.log(arr.toString())
        areaList = arr.toString();
        $(this).removeClass('keshiColor');
    } else {
        $(this).addClass('keshiColor');
        arr.push($(this).attr('data-id'));
        areaList = arr.toString();
    }
})

//此处是医馆管理 里面的医馆认证部分的功能

//营业执照图片上传
$('#zhizhao_pic_ipt').on('change', function () {
    if (this.files[0].size > 2097152) {
        $('#tip').text('上传图片不能大于2M');
        $('#tip').toggle();
        setTimeout(function () {
            $('#tip').toggle();
        }, 2000)
        return false;
    }
    if (!(this.files[0].type.indexOf('image') == 0 && this.files[0].type && /\.(?:jpg|png|gif)$/.test(this.files[0].name))) {
        $('#tip').text('图片格式不正确');
        $('#tip').toggle();
        setTimeout(function () {
            $('#tip').toggle();
        }, 2000)
        return false;
    }
    var form = new FormData();
    form.append("image", this.files[0]);
    var reader = new FileReader();
    reader.onload = function (e) {
        picUpdown3(form, 'teacher_pic');
    }
    reader.readAsDataURL(this.files[0])
})

//药品经营许可证上传
$('#xuke_pic_ipt').on('change', function () {
    if (this.files[0].size > 2097152) {
        $('#tip').text('上传图片不能大于2M');
        $('#tip').toggle();
        setTimeout(function () {
            $('#tip').toggle();
        }, 2000)
        return false;
    }
    if (!(this.files[0].type.indexOf('image') == 0 && this.files[0].type && /\.(?:jpg|png|gif)$/.test(this.files[0].name))) {
        $('#tip').text('图片格式不正确');
        $('#tip').toggle();
        setTimeout(function () {
            $('#tip').toggle();
        }, 2000)
        return false;
    }
    var form = new FormData();
    form.append("image", this.files[0]);
    var reader = new FileReader();
    reader.onload = function (e) {
        picUpdown3(form, 'zhicheng_pic');
    }
    reader.readAsDataURL(this.files[0])
})

//----------------------------------------医师部分结束，招聘管理部分开始,---------------------------------------------------

//招聘管理部分，点击职位下面内容变换
$(".recruit-btn-newjob").click(function () {
    var recruit_btn = $(this).text()
    if (recruit_btn == "新职位") {
        $(".warning").addClass("hide")
        resetRecruitForm();
        $(".recruit-box-newjob").show()
        $(".recruit-box-manage").hide()
        $(this).text("返回")
        $(".recruit-wrap-title p").text("新职位")
    } else {
        $(".recruit-box-newjob").hide()
        $(".recruit-box-manage").show()
        $(this).text("新职位")
        $(".recruit-wrap-title p").text("招聘管理")

    }

})

//招聘管理部分,点击后回到第一页
$("#collect_Administration_tabBtn").click(function () {
    if ($('.recruit-btn-newjob').text() == '返回') {
        $('.recruit-btn-newjob').click()
    }
    recruitList(1);
});

//医馆管理发布并保存
function verifyRecruit(data) {
	
	$(".recruit-box-newjob .warning").addClass("hide");
	$(".recruit-box-newjob input").removeClass("border_hide_null");
	$(".recruit-box-newjob textarea").removeClass("border_hide_null");
	
    if (data.position == "") {
        $(".warning-isdata").removeClass("hide");
        $(".warning-isdata").siblings("input").addClass("border_hide_null");
        return false;
    } 
    if (data.postDuties == "") {
        $(".warning-textarea-isdata").removeClass("hide");
        $(".warning-textarea-isdata").siblings("textarea").addClass("border_hide_null");
        return false;
    } 
    if (data.jobRequirements == "") {
        $(".warningtextarea-duty-isdata").removeClass("hide");
        $(".warningtextarea-duty-isdata").siblings("textarea").addClass("border_hide_null");
        return false;
    } 
    return true;
}

$(".recruit-save-btn-menuone").click(function () {
    var status = $(this).attr("data-id")
    var data = {
        "position": $.trim($(".recruit-isdata").val()),
        "years": $(".recruit-box-newjob .recruit-newjob-experience").iselect("val"),
        "postDuties": $recruit_textarea = $.trim($(".recruit-textarea-isdata").val()),
        "jobRequirements": $.trim($(".recruit-textarea-duty-isdata").val()),
        "status": status
    }
    if (verifyRecruit(data)) {
        $(this).attr("disabled", "disabled")
        $.ajax({
            type: "POST",
            url: bath + "/hospital/recruit",
            data: JSON.stringify(data),
            contentType: "application/json",
            success: function (data) {
                if (data.success == true) {
                    resetRecruitForm();
                    showTip("保存成功");
                    recruitList(1);
                    setTimeout(function () {
                        $("#collect_Administration_tabBtn").click();
                    }, 2000)
                } else {
                    showTip("保存失败");
                }
            }
        });
    }
})
//招聘管理列表
var recruits;

function recruitList(pages) {
    RequestService("/hospital/recruit", "GET", {
        "page": pages
    }, function (data) {
        if (data.success == true) {
            if (data.resultObject.records.length == "" || data.resultObject.records == null) {
                $(".notice_notext_two").show();
                $(".recruit-box-manage table").hide()
            } else {
                recruits = data.resultObject.records;
                $(".notice_notext_two").hide();
                $(".recruit-box-manage table").show();
                $("#recruitList-wrap").html(template('recruitList-mould', {items: data.resultObject.records}));
            }
            //分页添加
            if (data.resultObject.pages > 1) { //分页判断
                $(".not-data").remove();
                $(".recruits_pages").removeClass("hide");
                $(".recruits_pages .searchPage .allPage").text(data.resultObject.pages);
                $("#Pagination_recruits").pagination(data.resultObject.pages, {
                    num_edge_entries: 1, //边缘页数
                    num_display_entries: 4, //主体页数
                    current_page: pages - 1,
                    callback: function (page) {
                        //翻页功能
                        recruitList(page + 1);
                    }
                });
            }
            else {
                $(".recruits_pages").addClass("hide");
            }
        } else {
            showTip("获取数据失败");
        }
//分页添加结束			
    })
}


//招聘管理部分,点击预览
function recruit_preview_btn(i) {
    var recruit_Text = recruits[i];
    if (recruit_Text.years == 0) {
        $("#recruit_wrap_mune2 p").text("不限");
    } else if (recruit_Text.years == 1) {
        $("#recruit_wrap_mune2 p").text("0-1年");
    }
    else if (recruit_Text.years == 2) {
        $("#recruit_wrap_mune2 p").text("1-3年");
    }
    else if (recruit_Text.years == 3) {
        $("#recruit_wrap_mune2 p").text("3-5年");
    }
    else if (recruit_Text.years == 4) {
        $("#recruit_wrap_mune2 p").text("5-10年");
    }
    else if (recruit_Text.years == 5) {
        $("#recruit_wrap_mune2 p").text("10年以上");
    }
    $("#recruit_wrap_mune1 p").text(recruit_Text.position)
    $("#recruit_wrap_mune3 p").text(recruit_Text.postDuties);
    $("#recruit_wrap_mune4 p").text(recruit_Text.jobRequirements);
    $(".recruit_preview_bg").show()
    $(".recruit_preview_box").show()
}

$(".recruit_preview_content img").click(function () {
    $(".recruit_preview_bg").hide()
    $(".recruit_preview_box").hide()
})

//招聘管理部分,开启/关闭招聘按钮
function recruit_close_btn(t) {
    var id = $(t).attr('data-id');
    var status = $(t).attr('data-status');
    RequestService("/hospital/recruit/" + id + "/" + status, "PUT", null, function (data) {
        if (data.success == true) {
            if (status == 0) {
                showTip("关闭成功");
            } else {
                showTip("发布成功");
            }
            //重新渲染列表
            recruitList(1);
        } else {
            showTip("操作失败");
        }
    });
}

//招聘管理部分，点击删除招聘信息
function delete_recruit_btn(t) {
    var data_deleteId = $(t).attr("data-deleteId");
    confirmBox.open("公告", "确定删除该条招聘信息吗？", function (closefn) {
        RequestService("/hospital/recruit/" + data_deleteId + "", "DELETE", null, function (data) {
            if (data.success == true) {
                closefn();
                showTip("删除成功");
//	 重新渲染一遍
                recruitList(1);
            } else {
                showTip("删除失败");
                closefn();
            }
        })
    });
}

//招聘管理部分,点击编辑
//回显所有数据，id隐藏
//1.获取所有修改后的值2.校验所有值3.将所有值提交到后台
function recruit_edit(index) {
    resetRecruitForm(index);
    echoRecruit(index);
    $(".recruit-box-manage").hide();
    $(".recruit-box-newjob").show();
    $(".recruit-wrap-title p").text("编辑招聘");
    $(".recruit-btn-newjob").text("返回");
}

function echoRecruit(index) {
    var recruit_edit = recruits[index];
    //[{code:0,text:不限}]
    $("#recruitId").val(recruit_edit.id);
    $("#recruit-box-newjob .recruit-isdata").val(recruit_edit.position);
    $("#recruit-box-newjob .recruit-newjob-experience").iselect("val", recruit_edit.years);
    $("#recruit-box-newjob .recruit-textarea-isdata").val(recruit_edit.postDuties);
    $("#recruit-box-newjob .recruit-textarea-duty-isdata").val(recruit_edit.jobRequirements);
//编辑进来判断按钮
    $(".recruit-save-up").addClass("hide");
    $(".recruit-edit-save-up-wrap").removeClass("hide");
}

function resetRecruitForm(index) {
    $("#recruitId").val("");
    $("#recruit-box-newjob .recruit-newjob-experience").iselect("init", data);
    $("#recruit-box-newjob .recruit-newjob-experience").iselect("val", 0);
    $("#recruit-box-newjob .recruit-isdata").val("");
    $("#recruit-box-newjob .recruit-textarea-isdata").val("");
    $("#recruit-box-newjob .recruit-textarea-duty-isdata").val("");
    $(".recruit-save-btn-menuone").removeAttr("disabled");
//	新职位的保存-发布按钮显现
    $(".recruit-edit-save-up-wrap").addClass("hide");
    $(".recruit-save-up").removeClass("hide");
//	新职位的保存-发布按钮显现end
	$(".recruit-box-newjob input").removeClass("border_hide_null");
	$(".recruit-box-newjob textarea").removeClass("border_hide_null");
	$(".recruit-box-newjob .warning").addClass("hide");
}
//招聘编辑失焦验证
function clearErrorInf(dom){
	$("."+dom).addClass("hide");
    $("."+dom).siblings("textarea").removeClass("border_hide_null");
}
	var recruitCheckInf = {
		recruit_isdata : function(){
			var recruitName=$.trim($(".recruit-isdata").val());
			$(".warning-isdata").addClass("hide");
			$(".warning-isdata").siblings("input").removeClass("border_hide_null");
			if (recruitName == "") {
				$(".warning-isdata").removeClass("hide");
        		$(".warning-isdata").siblings("input").addClass("border_hide_null");
			}
		},
		recruit_textarea_isdata : function(){
			var textarea_isdata=$.trim($(".recruit-textarea-isdata").val());
			clearErrorInf("warning-textarea-isdata")
			if (textarea_isdata == "") {
				$(".warning-textarea-isdata").removeClass("hide");
        		$(".warning-textarea-isdata").siblings("textarea").addClass("border_hide_null");
			}
		},
		recruit_textarea_duty_isdata : function(){
			var duty_isdata=$.trim($(".recruit-textarea-duty-isdata").val());
			clearErrorInf("warningtextarea-duty-isdata")
			if (duty_isdata == "") {
				$(".warningtextarea-duty-isdata").removeClass("hide");
        		$(".warningtextarea-duty-isdata").siblings("textarea").addClass("border_hide_null");
			}
		}
	}
	$(".recruit-isdata").blur(function(){
		recruitCheckInf.recruit_isdata();
	})
	$(".recruit-textarea-isdata").blur(function(){
		recruitCheckInf.recruit_textarea_isdata();
	})
	$(".recruit-textarea-duty-isdata").blur(function(){
		recruitCheckInf.recruit_textarea_duty_isdata();
	})
//编辑过后的内容保存
function verifyRecruit(data) {
	$(".recruit-box-newjob .warning").addClass("hide");
	$(".recruit-box-newjob input").removeClass("border_hide_null");
	$(".recruit-box-newjob textarea").removeClass("border_hide_null");
    if (data.position == "") {
        $(".warning-isdata").removeClass("hide");
        $(".warning-isdata").siblings("input").addClass("border_hide_null");
        return false;
    }
    if (data.postDuties == "") {
        $(".warning-textarea-isdata").removeClass("hide");
        $(".warning-textarea-isdata").siblings("textarea").addClass("border_hide_null");
        return false;
    } 
    if (data.jobRequirements == "") {
        $(".warningtextarea-duty-isdata").removeClass("hide");
        $(".warningtextarea-duty-isdata").siblings("textarea").addClass("border_hide_null");
        return false;
    } 
    return true;
}

$(".recruit-edit-btn").click(function () {
    var edit_id = $("#recruitId").val();
    var data = {
        "position": $.trim($(".recruit-isdata").val()),
        "years": $(".recruit-box-newjob .recruit-newjob-experience").iselect("val"),
        "postDuties": $recruit_textarea = $.trim($(".recruit-textarea-isdata").val()),
        "jobRequirements": $.trim($(".recruit-textarea-duty-isdata").val())
    }
    if (verifyRecruit(data)) {
        $.ajax({
            type: "PUT",
            url: bath + "/hospital/recruit/" + edit_id + "",
            data: JSON.stringify(data),
            contentType: "application/json",
            success: function (data) {
                if (data.success == true) {
                    //			resetRecruitForm();
                    showTip("保存成功");
                    recruitList(1);
                    $("#collect_Administration_tabBtn").click();
                } else {
                    showTip("保存失败");
                }
            }
        });
    }
    ;
})
//招聘管理部分结束
//--------------------------------------招聘管理部分结束,招聘公告部分开始---------------------------------------
//	招聘公告部分，点击发布按钮
var NoticeCount = 1;
$('#Notice_Administration .Notice_top button').click(function () {
    NoticeCount *= -1;
    //发布
    if (NoticeCount < 0) {
        //顶部变化
        $(this).text('返回');
        $(this).siblings('.title').text('新公告');
        //底部变化
        $('#Notice_bottom2').addClass('hide');
        $('#Notice_bottom').removeClass('hide');
    } else {
        //取消发布
        $(this).text('新公告');
        $(this).siblings('.title').text('公告管理');
        //底部变化
        $('#Notice_bottom').addClass('hide');
        $('#Notice_bottom2').removeClass('hide');
    }

})
$("#news_Administration_tabBtn").click(function () {
    if ($(".Notice_top button").text() == '返回') {
        $(".Notice_top button").click();
    }
    announcementMethod(1)
})
//公告发布接口调用
$("#notice-release-btn").click(function () {
    var $notice_text = $.trim($("#notice-text").val());
    $(this).attr("disabled", "disabled")
    if ($notice_text == '') {
        $("#notice-release-btn").removeAttr("disabled", "disabled");
        $(".warning-notice").removeClass("hide");
        $(".warning-notice").siblings("textarea").addClass("border_hide_null");
    } else {
        RequestService("/hospital/announcement", "POST", {
            "content": $notice_text
        }, function (data) {
            if (data.success == true) {
                showTip("发布成功");
                $("#notice-text").val("")
                $(".warning-notice").addClass("hide");
                $(".warning-notice").siblings("textarea").removeClass("border_hide_null");
                $(".word-number").text('剩余' + 100 + '字');
                setTimeout(function () {
                    $("#news_Administration_tabBtn").click();
                    $("#notice-release-btn").removeAttr("disabled", "disabled");
                    //				重新渲染一遍
                }, 2000);
                announcementMethod(1);
            } else {
                showTip("发布失败");
            }
        })
    }

})
$("#notice-text").blur(function(){
	$(".warning-notice").addClass("hide");
		$(".warning-notice").siblings("textarea").removeClass("border_hide_null");
	if($.trim($("#notice-text").val())==""){
		$(".warning-notice").removeClass("hide");
		$(".warning-notice").siblings("textarea").addClass("border_hide_null");
	}
})
//公告部分，多行文本输入框剩余字数计算
function checkMaxInput(obj, maxLen) {
    if (obj.value.length > maxLen) { //如果输入的字数超过了限制
        obj.value = obj.value.substring(0, maxLen); //就去掉多余的字
        $(".word-number").text('剩余' + (maxLen - obj.value.length) + '字');
    }
    else {
        $(".word-number").text('剩余' + (maxLen - obj.value.length) + '字');
    }
}

var announcementList;
//公告管理列表接口调用
var setPage;

function announcementMethod(current) {
    RequestService("/hospital/announcement", "GET", {
        "page": current
    }, function (data) {
        if (data.success == true) {

            setPage = data.resultObject.current;   //序号
            for (var i = 0; i < data.resultObject.records.length; i++) {
                data.resultObject.records[i].Nownum = getNo(i);

            }
            announcementList = data.resultObject.records;
            if (data.resultObject.pages > 1) {
                $(".notices_pages").removeClass("hide");
                $(".notices_pages .searchPage .allPage").text(data.resultObject.pages);
                $("#Pagination_notices").pagination(data.resultObject.pages, {
                    num_edge_entries: 1, //边缘页数
                    num_display_entries: 4, //主体页数
                    current_page: current - 1,
                    callback: function (page) {
                        //翻页功能
                        announcementMethod(page + 1);

                    }
                });
            } else {
                $(".notices_pages").addClass("hide");
            }
            if (announcementList.length == 0 || announcementList == null) {
                $("#Notice_bottom2 table").hide();
                $(".notice_notext_icon").show();
            } else {
                $("#Notice_bottom2 table").show();
                $(".notice_notext_icon").hide();
                $('#notice_tbody_wrap').html(template('notice_tbody', {items: data.resultObject.records}));
            }
        } else {
            showTip("获取列表失败");
        }
    })

    function getNo(i) {
        i++;
        var j = (setPage - 1) * 10 + i;
        return j;
    }
}

//公告管理部分，点击预览--------------------------------------------------------------
function notice_btn_see(i) {
    var announcementText = announcementList[i];
    $(".notice_namage_text").text(announcementText.content);
    $(".notice_time").text("发布时间：" + announcementText.createTime);
    $(".notice_namage_see").show();
    $(".recruit_preview_bg").show();
}

$(".notice_preview_content img").click(function () {
    $(".notice_namage_see").hide();
    $(".recruit_preview_bg").hide();
})

//公告管理部分，点击删除--------------------------------------------------------------
function notice_btn_delete(t) {
    var data_id = $(t).attr("data-id");
    confirmBox.open("公告", "确定删除该条公告吗？", function (closefn) {
        RequestService("/hospital/announcement/" + data_id + "", "DELETE", null, function (data) {
            if (data.success == true) {
                closefn();
                showTip("删除成功");
//			重新渲染一遍
                announcementMethod(1)
            } else {
                showTip("删除失败");
            }
        })
    });
}

//公告部分结束
//医师管理部分
$('#doc_Administration .add_newTeacher').click(function () {
    var workSelect = $(this).text()
    if (workSelect == "新医师") {
        $('#doc_Administration_bottom2').addClass('hide');
        $('#doc_Administration_bottom').removeClass('hide');
        $(".new-doctorbtn").removeClass("hide");
        $(".wrap-save").addClass("hide");
        $(this).siblings('.title').text('新医师');
        $(this).text("返回")
//		搜索部分隐藏
        $('.search_teacher_ipt').addClass('hide');
        $('.search_teacher_btn').addClass('hide');
//		内容制空功能
        reset();
    } else {
        $('#doc_Administration_bottom').addClass('hide');
        $('#doc_Administration_bottom2').removeClass('hide');
        $(this).siblings('.title').text('医师管理');
        $(this).text("新医师");
        //		//搜索部分显示
        $('.search_teacher_ipt').removeClass('hide');
        $('.search_teacher_btn').removeClass('hide');
    }
})

function reset() {
    //姓名
    $('#doc_Administration_bottom .doc_name').val('');
    $('#doc_Administration_bottom .doc_name_manage').val('');
    //职称
    $('#doc_Administration_bottom .doc_zhicheng').val('');
    //擅长
    $('#doc_Administration_bottom .doc_shanchangIpt').val('');

    //
    $('#doc_Administration_bottom #shanChangList li').removeClass('keshiColor');
    //头像
    var headIpt = '<p style="font-size: 90px;height: 80px;font-weight: 300;color: #d8d8d8;text-align: center;line-height: 80px;">+</p><p style="text-align: center;color: #999;font-size: 14px;">求真相</p>';
    $('#doc_Administration_bottom .touxiang_pic').html(headIpt);
    //职称证明
    var zhicheng_pic = '<p style="font-size: 90px;height: 100px;font-weight: 300;color: #d8d8d8;text-align: center;">+</p><p style="text-align: center;color: #999;font-size: 14px;">点击上传职称证明图片</p>';
    $('#doc_Administration_bottom .zhicheng_pic').html(zhicheng_pic);
    //富文本框
    UE.getEditor('editor').setContent("")
     $('#doc_Administration_bottom .warning').addClass("hide");
    $('#doc_Administration_bottom input').removeClass("border_hide_null");
}

//内部医疗领域选择功能
$('#doc_Administration  .keshi ul li').click(function () {
    if ($(this).hasClass('keshiColor')) {
        $(this).removeClass('keshiColor')
    } else {
        $(this).addClass('keshiColor');
    }
})

//点击重新认证按钮的效果
function hosAgainAut() {

    $('#hos_Administration .hos_renzheng_inf .bottomContent2 ').addClass('hide');
    $('#hos_Administration .hos_renzheng_inf .bottomContent').removeClass('hide');

    //	医馆提交的数组回显
    RequestService("/hospital/apply/getLastOne", "GET", null, function (data) {
        console.log(data);
        if (data.success == true && data.resultObject != null) {
            var result = data.resultObject;

            //姓名
            $('#hos_Administration .hos_name').val(result.name);
            //所属公司
            $('#hos_Administration .doc_name').val(result.company);
            //统一社会信用代码
            $('#hos_Administration .doc_Idnum').val(result.businessLicenseNo);
            //营业执照
            $('#hos_Administration .teacher_pic').html('<img src=' + result.businessLicensePicture + '>')
            //药品经营许可证号
            $('#hos_Administration .doc_zhicheng').val(result.licenseForPharmaceuticalTrading);
            //药品经营许可证
            $('#hos_Administration .zhicheng_pic').html('<img src=' + result.licenseForPharmaceuticalTradingPicture + '>')
        }

    })

}

//页面左侧tab 刷新之后停留当前页面
$(function () {
    $('#hos_renzhneg_inf').addClass('color');
    var userStatus;
    RequestService("/medical/common/isDoctorOrHospital", "GET", null, function (data) {
        userStatus = data.resultObject.isDoctorOrHospital;
        if (localStorage.hos_Administration == "hos_base_inf" && (userStatus == 2 ||userStatus == 2)) {
            $('#hos_base_inf').click();
        }

        //医师招聘公告按钮之前点击按钮判断
        if (localStorage.hosTabSta == 'docAdm') $('#doc_Administration_tabBtn>a').click();
        if (localStorage.hosTabSta == 'colAdm') $('#collect_Administration_tabBtn>a').click();
        if (localStorage.hosTabSta == 'newAdm') $('#news_Administration_tabBtn>a').click();
        window.localStorage.removeItem('hosTabSta')
        window.localStorage.removeItem('hos_Administration')
    })
})


//公告管理部分结束--------------------------------------------------------------
/*下拉框插件 20170416-yuxin  iselect*/
/*
使用说明：
初始化：$("xxx").iselect("init",data),$("xxx")为下拉框所属容器,
		data为下拉框对应数据,格式如：[{value:1,text:一年},{value:1,text:一年},{value:1,text:一年}]
刷新：$("xxx").iselect("refresh");
取值：$("xxx").iselect("val");
设值：$("xxx").iselect("val",2);
*/
$.fn.iselect = function (option, data) {
    var iselect = {
        init: function (that, data) {
            that.data("data", data);
            this.initDom(that, data);
            this.initEvent(that);
        },
        refresh: function (that) {
            this.initDom(that, that.data("data"));
            this.initEvent(that);
        },
        initDom: function (that, data) {
            that.find(".recruit-select").remove();
            var str = '<div class="recruit-select" id="">' +
                '<p class="recruit-select-one recruit-select-exp" >请选择</p>\n' +
                '<ul class="recruit-select-lest">\n';
            for (var i = 0; i < data.length; i++) {
                str += '<li value="' + data[i].value + '">' + data[i].text + '</li>\n';
            }
            str += '</ul></div>';
            that.append(str);
        },
        initEvent: function (that) {
            //自定义下拉select
            that.find(".recruit-select p").click(function () {
                var ul = $(".recruit-select-lest");
                if (ul.css("display") == "none") {
                    ul.slideDown();
                } else {
                    ul.slideUp();
                }
            });

            that.find(".recruit-select-one").click(function () {
                var _name = $(this).attr("name");
                if ($("[name=" + _name + "]").length > 1) {
                    $("[name=" + _name + "]").removeClass("select");
                    $(this).addClass("select");
                } else {
                    if ($(this).hasClass("select")) {
                        $(this).removeClass("select");
                    } else {
                        $(this).addClass("select");
                    }
                }
            });
            that.find(".recruit-select li").click(function () {
                that.find(".recruit-select p").html($(this).text());
                that.find(".recruit-select p").attr("value", $(this).attr("value"));
                that.find(".recruit-select-lest").hide();
                /*$(".set").css({background:'none'});*/
                that.find("p").removeClass("select");
            });
        },
        getVal: function (that) {
            return that.find(".recruit-select p").attr("value");
        },
        setVal: function (that, val) {
            var data = that.data("data");
            for (var i = 0; i < data.length; i++) {
                if (data[i].value == val) {
                    that.find(".recruit-select p").html(data[i].text);
                    that.find(".recruit-select p").attr("value", data[i].value);
                }
            }
        }
    }

    if (option == "init") {
        iselect.init(this, data);
    } else if (option == "val") {
        if (data == null) {
            return iselect.getVal(this);
        } else {
            iselect.setVal(this, data);
        }
    } else if (option == "refresh") {
        iselect.refresh(this);
    }
}
/*下拉框插件 20170416-yuxin*/

//测试插件代码

var data = [];
data[0] = {};
data[0].value = 0;
data[0].text = '不限';
data[1] = {};
data[1].value = 1;
data[1].text = '0-1年';
data[2] = {};
data[2].value = 2;
data[2].text = '1-3年';
data[3] = {};
data[3].value = 3;
data[3].text = '3-5年';
data[4] = {};
data[4].value = 4;
data[4].text = '5-10年';
data[5] = {};
data[5].value = 5;
data[5].text = '10年以上';

$(".recruit-box-newjob .recruit-newjob-experience").iselect("init", data);
