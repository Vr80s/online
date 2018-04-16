 var checkedType="video";
$(function() {
    showCourseAttribute(1);


    $('#anchorWorkbench').css('color', '#00bc12');
    //进入页面定位之前位置
//  if(location.hash == "#menu=1"){
//  	$("#accordion li[data-name='#menu=1']").click();
//  }


//  左侧一级菜单点击的时候保存localStorate 和 对应事件
    $('#accordion > li').click(function () {

        //设置hash
        location.hash = $(this).attr('data-name');
        //清空localStorage
        localStorage.leftTblSta == ' ';

        $('#accordion > li .submenu > li >a').removeClass('leftTab_active')
        $('#accordion > li .submenu > li:first-child >a').addClass('leftTab_active')


        //点击学堂事件
        if ($(this).attr('data-name') == "menu=1") {
            $('#mymoney').hide();
            $('.myResive').hide();
            $('.account').hide();
            $('.wrap_box #curriculum').show();
            //课程列表展示第一页
            $(".curriculum_one").hide();
            $(".curriculum_two").show();
            //触发点击课程事件
            $(".courseP").click()


        }

        //点击我的资产
        if ($(this).attr('data-name') == "menu=2") {
            //重置我的资产部分
            initBasaeAssetInfo();
            $('.wrap_box .little_box').hide();
            $('.account').hide()
            $('.myResive').hide()
            $('#mymoney').show();
            $('#mymoney').removeClass('hide');

            //里面中间结构隐藏
            $('#mymoney .content_mid').addClass('hide');
            $('#mymoney .changeType .pandaMoney').click();
        }


        //点击我的收益
        if ($(this).attr('data-name') == "menu=3") {
            $('.wrap_box .little_box').hide();
            $('.account').hide()
            $('#mymoney').hide();
            $('.myResive').show();
            $('.myResive .little_box1').show();

            $('.select-udss .classResive').click();

        }

        //点击我的账号
        if ($(this).attr('data-name') == "menu=4") {
            $('.wrap_box .little_box').hide();
            $('#mymoney').hide();
            $('.myResive').hide();
            $('.account').show()

            $(".name_news").click();
        }
    })


//里面的二级菜单点击设置location.hash
    $('#accordion > li .submenu >li>a').click(function (e) {
        $('#accordion > li .submenu >li>a').removeClass('leftTab_active');
        $(this).addClass('leftTab_active');
        e.stopPropagation();
        debugger
        location.hash = $(this).attr('data-name');
        leftTblSta();
    })


    function leftTblSta() {
        if (location.hash == '#menu=1-1') {
            $(".courseP").click();
        }
        if (location.hash == '#menu=1-2') {
            $('.specialP').click();
        }
        if (location.hash == '#menu=1-3') {
            $('.liveP').click();
        }
        if (location.hash == '#menu=1-4') {
            $('.resourceP').click();
        }
        if (location.hash == '#menu=3-1') {
            $('.classResive').click();
        }
        if (location.hash == '#menu=3-2') {
            $('.giftResive').click();
        }
        if (location.hash == '#menu=4-1') {
            $('.name_news').click();
        }
        if (location.hash == '#menu=4-2') {
            $('.name_personage').click();
        }
    }

    //对课程目录下小的下拉div进行操作
    $(".select_list .select-ud .littleBox p").bind('click', function (event) {
        event.stopPropagation();
        $(".select_list .littleBox p").removeClass("activeP");
        $(this).addClass("activeP");
        localStorage.AnchorsTbl_School = $(this).attr('data-name');
        $(".wrap_box .little_box").hide().eq($(this).index()).show();
        /*课程显示*/
        $(".account .account_mains").hide();
        /*账号隐藏*/
        $(".myResive .little_box1").hide()
        /*收益隐藏*/
        $(".select_box").hide();
    })

    $(".select_list .select-uds .littleBoxs p").bind('click', function (event) {
        event.stopPropagation();
        $(".select_list .littleBoxs p").removeClass("activeP");
        $(this).addClass("activeP");
        $(".account .account_mains").hide().eq($(this).index()).show();
        /*账户显示*/
        $(".wrap_box .little_box").hide();
        /*课程隐藏*/
        $(".myResive .little_box1").hide()
        /*收益隐藏*/
        $(".select_box").hide();
    })

    $(".select_list .select-udss .littleBoxs p").bind('click', function (event) {
        event.stopPropagation();
        $(".select_list .littleBoxs p").removeClass("activeP");
        $(this).addClass("activeP");
        $(".myResive .little_box1").hide().eq($(this).index()).show();
        /*收益显示*/
        $(".wrap_box .little_box").hide();
        /*课程隐藏*/
        $(".account .account_mains").hide();
        /*账号隐藏*/
        $(".select_box").hide();
    })

    //头像上传
    var userPic = $('.userPic').css('background')
    RequestService("/online/user/isAlive", "get", null, function (data) {
        //头像预览
        if (data.resultObject.smallHeadPhoto) {
            $('.doctor_inf>img').attr('src', data.resultObject.smallHeadPhoto);
            $('.doctor_inf>h4').text(data.resultObject.name);
        } else {
            $('.doctor_inf>img').attr('src', "/web/images/defaultHeadImg.jpg");
        }

        if (data.resultObject.info) {
            $('.doctor_inf>p').text(data.resultObject.info)
        } else {
            $('.doctor_inf>p').text('说点什么来彰显你的个性吧……')
        }
    });


    $(".doctor_inf > img,.doctor_inf .picModal").on("click", function () {
        showDel();
        $("#headImg").css("display", "block");
        $("body").css("overflow", "hidden");
        //清空右侧小图片
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
//			$(".mask").css("display", "none");
            hideDel();
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
                //							layer.msg("图片限于bmp,png,gif,jpeg,jpg格式", {
                //								icon: 2
                //							});
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

    $('.fileUpdata').click(function () {
        return $("#upload-file").click();
    })


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
//							$(".mask").css("display", "none");
                        hideDel();
                        $("#headImg").css("display", "none");
                        location.reload();
                    }

                })

            }
        })

        $(".btn-upload").css("color", "white");
    })

    //上传封面图片
    $('.fengmian_pic').click(function () {
        $('#picIpt').click();
    })

//	专栏部分
//	专栏部分点击发布效果
    var zhuanlanCount = 1;
    $('#zhuanlan .zhuanlan_top button').click(function () {
        zhuanlanCount *= -1;
        //发布
        if (zhuanlanCount < 0) {
            //顶部变化
            $(this).text('返回');
            $(this).siblings('.title').text('新专栏');
            //底部变化
            $('#zhuanlan_bottom2').addClass('hide');
            $('#zhuanlan_bottom').removeClass('hide');
        } else {
            //取消发布
            $(this).text('发布');
            $(this).siblings('.title').text('专栏');
            //底部变化
            $('#zhuanlan_bottom').addClass('hide');
            $('#zhuanlan_bottom2').removeClass('hide');
        }

    })

//处理点击--课程显示隐藏第一第二页
    $(".select_list .courseP").click(function () {
        $(".curriculum_two").show();
        $(".curriculum_one").hide();
    });

//点击选择资源
    $('#a').click(function () {
        $('.a_resource').show();
    });
    $('.a_resource_close').click(function () {
        $('.a_resource').hide();
    });


//专辑开始
    var zhuanjiCount = 1;
    $('#zhuanji .zhuanlan_top button').click(function () {
        zhuanjiCount *= -1;
        //上传
        if (zhuanjiCount < 0) {
            //顶部变化
            $(this).text('返回');
            $(this).siblings('.title').text('新专辑');
        } else {
            $(this).text('新专辑');
            $(this).siblings('.title').text('专辑');
        }
    })

//点击添加
    $(".zhaunji_tr td .add").click(function () {
        $("#zhuanjis_bottom").show();
        $("#zhuanji_bottom2").hide();
        $("#zhuanji .zhuanlan_top_one").hide();
    });

//新专辑,新增课程 
    $(".new_box .size").click(function () {
        $(".new_box").hide();
        $("#zhuanji_bottom").hide();
        $(".zhuanlan_top_one").hide();
        $("#zhuanjis_bottom").show();
    });

//专辑
    $("#zhuanji_bottom2 .returns").click(function () {
        $("#zhuanji_bottom2").hide();
        $("#zhuanji_bottom").show();
    });

//新专辑返回
    $("#zhuanji_bottom .returns").click(function () {
        $("#zhuanji_bottom").hide();
        $("#zhuanji_bottom2").show();
    });

//新专辑--添加课程
    $("#zhuanjis_bottom .returns").click(function () {
        $("#zhuanji_bottom").hide();
        $("#zhuanjis_bottom").hide();
        $("#zhuanji_bottom2").show();
    });

//点击专辑--专辑-新专辑
    $("#zhuanji_bottom2 .zhuanlan_top .returns").click(function () {
        $("#zhuanji_bottom2").hide();
        $("#zhuanji_bottom").remove("hide");
    });
})
    function showCourseAttribute(type) {
        $(".special").hide();
        if (type == 1) {
            $(".live").show();
        } else if (type == 2) {
            $(".vod").show();
        } else {
            $(".offline").show();
        }
    }


    function confirmBox(title, content, fn) {
        $(".confirm-title").html(title);
        $(".confirm-content").html(content);
        $(".confirm-sure").click(function () {
            fn(hideDel);
            $(".confirm-sure").unbind("click")
        })
        showDel();
    }

//删除提示框出现方法
    function showDel() {
        $('#deleteTip').removeClass('hide');
        $('#mask').removeClass('hide');
    }

//删除提示框出现方法
    function showDel_bank() {
        $('#addBankCard').removeClass('hide');
        $('#mask').removeClass('hide');
    }


//删除提示消失方法
    function hideDel() {
        $('#deleteTip').addClass('hide');
        $('#mask').addClass('hide');
    }


//体现提示消失方法
    function hideDel_bank() {
        $('#addBankCard').addClass('hide');
        $('#mask').addClass('hide');
    }

//科室点击验证效果
    var arr = [];
    var keshiStr;
    $('.account_main_alter  .keshi ').on('click', '#keshiList>li', function () {
        if ($(this).hasClass('keshiColor')) {
            //删除第二次选中的
            for (var i = 0; i < arr.length; i++) {
                if ($(this).attr('data-id') == arr[i]) {
                    arr.splice(i, 1)
                }
            }
//			console.log(arr.toString())
            keshiStr = arr.toString();
            $(this).removeClass('keshiColor');
        } else {
            $(this).addClass('keshiColor');
            arr.push($(this).attr('data-id'));
//			console.log(arr.toString())
            keshiStr = arr.toString();
        }
        console.log(keshiStr)
    })

    function clearNoNum(obj) {
        obj.value = obj.value.replace(/[^\d.]/g, "");  //清除“数字”和“.”以外的字符
        obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的
        obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');//只能输入两个小数
        if (obj.value.indexOf(".") < 0 && obj.value != "") {//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
            obj.value = parseFloat(obj.value);
        }
    }

    function xmx(begin, first, filemd5, ccid, metaurl, chunkUrl) {
        var obj_file = document.getElementById("btn_width").files[0];
        chunkSize = 2097152;  //2M
        var totalSize = obj_file.size;        //文件总大小
        var start = begin;                //每次上传的开始字节
        var end = start + chunkSize;        //每次上传的结尾字节
        var blob = null;
        blob = obj_file.slice(start, end);    //截取每次需要上传字节数

        formData = new FormData();        //每一次需重新创建
        formData.append('file', blob);    //添加数据到表单对象中
        formData.append('fileSize', totalSize);    //添加数据到表单对象中
        formData.append('filemd5', filemd5);    //添加数据到表单对象中
        formData.append('fileName', obj_file.name);    //添加数据到表单对象中
        formData.append('first', first);    //添加数据到表单对象中
        formData.append('ccid', ccid);    //添加数据到表单对象中
        formData.append('metaUrl', metaurl);    //添加数据到表单对象中
        formData.append('chunkUrl', chunkUrl);    //添加数据到表单对象中
        formData.append('start', start);    //添加数据到表单对象中
        currentAjax = $.ajax({
            url: '/videoRes/uploadFile',
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false
        }).done(function (result) {
            if (result.success) {
                var ccid = result.resultObject[0];
                var metaurl = result.resultObject[1];
                var chunkUrl = result.resultObject[2];
                //计算完成百分比
                var completion = Math.round(end / totalSize * 10000) / 100.00;
                if (completion > 100) {
                    completion = 100;
                }
                $('.progress-resource').css({
                    "width": completion + "%"
                })
                start = end;            // 累计上传字节数
                end = start + chunkSize;    // 由上次完成的部分字节开始，添加下次上传的字节数
                localStorage.setItem("startChunkSize", start);
                localStorage.setItem("ccId", ccid);
                localStorage.setItem("metaUrl", metaurl);
                localStorage.setItem("chunkUrl", chunkUrl);
                // 上传文件部分累计
                if (start >= totalSize) {    //如果上传字节数大于或等于总字节数，结束上传
                    $("#ccId").val(result.resultObject[0]);
                    var fileName = obj_file.name.substring(0, obj_file.name.lastIndexOf("."));
                    $("#ziyuan_bottom .resource_uploading").hide();
                    $("#mask").addClass('hide')
                    $("#ziyuan_bottom .uploadfinish").show();
                    $("#ziyuan_bottom .updataSuccess").show();
                    var title = $("#ziyuan_bottom .zhuanlan_title").val();
                    if (title == "") {
                        $("#ziyuan_bottom .zhuanlan_title").val(fileName);
                    }
                    uploadfinished = true;
                    //alert('上传完成!');
                    //告诉后台上传完成后合并文件                            //返回上传文件的存放路径
                    $(".propress-file").css({"border": "0", "overflow": "inherit"})
                    $("#btn_width").css({"opacity": "1", "width": "auto", "z-index": "100", "left": "0"})
                } else {
                    xmx(start, "2", "", ccid, metaurl, chunkUrl);        // 上传字节不等与或大于总字节数，继续上传
                }
            } else {
                $("#continueUpload").show();
                //alert('上传失败');
            }
        }).fail(function () {
            $("#continueUpload").show();
            //alert('上传失败!');

        });

    }

    function isAccord(filepath) {
        if (filepath == "") {
            return false;
        }
        var extStart = filepath.lastIndexOf(".");
        var ext = filepath.substring(extStart, filepath.length).toUpperCase();
        if (checkedType == 'video') {
            if (ext != ".WMV" && ext != ".WM" && ext != ".ASF" && ext != ".ASX" &&
                ext != ".RM" && ext != ".RMVB" && ext != ".RA" && ext != ".RAM" && ext != ".MPG"
                && ext != ".MPEG" && ext != ".MPE" && ext != ".VOB" && ext != ".DAT"
                && ext != ".MOV" && ext != ".3GP" && ext != ".MP4" && ext != ".MP4V"
                && ext != ".M4V" && ext != ".MKV" && ext != ".AVI" && ext != ".FLV"
                && ext != ".F4V" && ext != ".MTS") {
                showTip("文件格式有误")
                return false;
            }
        } else {
            if (ext != ".MP3" && ext != ".WAV"
                && ext != ".AIF" && ext != ".AIFF" && ext != ".AU" && ext != ".SND"
                && ext != ".VOC" && ext != ".RA" && ext != ".MIDRMI" && ext != ".WMA"
                && ext != ".APE" && ext != ".FLAC" && ext != ".AAC" && ext != ".M4A"
                && ext != ".VQF") {
                showTip("文件格式有误")
                return false;
            }
        }
        /*if (ext != ".WMV" && ext != ".WM" && ext != ".ASF" && ext != ".ASX" &&
            ext != ".RM"&& ext != ".RMVB" && ext != ".RA" && ext != ".RAM" && ext != ".MPG"
            && ext != ".MPEG" && ext != ".MPE" && ext != ".VOB" && ext != ".DAT"
            && ext != ".MOV" && ext != ".3GP" && ext != ".MP4" && ext != ".MP4V"
            && ext != ".M4V" && ext != ".MKV" && ext != ".AVI" && ext != ".FLV"
            && ext != ".F4V" && ext != ".MTS" && ext != ".MP3" && ext != ".WAV"
            && ext != ".AIF" && ext != ".AIFF" && ext != ".AU" && ext != ".SND"
            && ext != ".VOC" && ext != ".RA" && ext != ".MIDRMI" && ext != ".WMA"
            && ext != ".APE" && ext != ".FLAC" && ext != ".AAC" && ext != ".M4A"
            && ext != ".VQF" ) {
            showTip("文件格式有误")
            return false;
        }*/
        return true;
    }

    function cancalUpdata1() {
        currentAjax.abort();
        var file = document.getElementById('btn_width');
        file.value = '';
        $('.progress-resource').css({
            "width": "0%"
        })
        $('#mask').addClass('hide');
        $("#ziyuan_bottom .uploading").hide();
        $("#ziyuan_bottom .resource_uploading").hide();
    }

    function continueUpload() {
        $("#continueUpload").hide();
        var start = localStorage.getItem("startChunkSize");
        var ccid = localStorage.getItem("ccId");
        var metaurl = localStorage.getItem("metaUrl");
        var chunkUrl = localStorage.getItem("chunkUrl");
        var fileMd5 = localStorage.getItem("fileMD5");
        xmx(parseInt(start), "2", "", ccid, metaurl, chunkUrl);
    }

//判断选中的是视频还是音频
    function changePeriod(checked) {
        checkedType = checked;
        if (checked == "video") {
            $("#btn_width").attr("accept", ".wmv,.wm,.asf,.asx,.rm,.rmvb,.ra,.ram,.mpg,.mpeg,.mpe,.vob,.dat,.mov,.3gp,.mp4,.mp4v,.m4v,.mkv,.avi,.flv,.f4v,.mts");
        } else {
            $("#btn_width").attr("accept", ".Mp3,.Wav,.aif,.aiff,.au,.snd,.voc,.ra,.midrmi,.wma,.ape,.flac,.aac,.m4a,.vqf");
        }
    }
