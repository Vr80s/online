$(function () {
    var hosID;
    var hosList = [];

    //点击课程--新课程
    $("#kecheng_bottom2 .button").click(function () {
        $(".curriculum_two").hide();
        $(".curriculum_one").show();
        $(".row_size").show();//这是图片上传时的+和提示显示
    });
    //点击课程  返回
    $(".curriculum_one .button").click(function () {
        $(".curriculum_one").hide();
        $(".curriculum_two").show();
    });

    //点击学堂隐藏
    $(".school").click(function () {
        $(".account_main_alter").hide();
        $(".account_main_message").hide();
        $(".personal_details").hide();
        $(".message_return").hide();
        $("#zhuanji").hide();
        $("#resource").hide();
        $(".curriculum_one").hide();
        $(".curriculum_two").show();

        $("#curriculum").show();
        $(".wrap_box").show();
        $(".littleBoxs").slideUp("slow");

    });

    $(".littleBox p").click(function () {
        $(".account_main_alter").hide();
        $(".account_main_message").hide();
        $(".personal_details").hide();
        $(".message_return").hide();
    });


    //点击左侧专辑
//	$(".specialP").click(function() {
//	    $("#zhuanji_bottom2").show();
//	    $("#zhuanji_bottom").hide();
//	    $("#zhuanjis_bottom").hide();
//	});

    //点击第一页,新专辑
    $("#zhuanji_bottom2 .returns").click(function () {
        $("#zhuanji_bottom2").hide();
        $("#zhuanji_bottom").show();
        $(".row_size").show();//这是图片上传时的+和提示显示
    });

    //点击第二页返回--到第一页
    $("#zhuanji_bottom .returns").click(function () {
        $("#zhuanji_bottom").hide();
        $("#zhuanji_bottom2").show();
    });

    //关闭添加课程弹框
    $(".new_box_main p").click(function () {
        $(".new_box").hide();
    });

    //添加课程开始
    $(".new_box .new_box_main .size").click(function () {
        $("#zhuanjis_bottom").show();
        $("#zhuanji_bottom").hide();
        $(".new_box").hide();
    });

    //课程添加返回
    $("#zhuanjis_bottom .returns").click(function () {
        $("#zhuanji_bottom2").show();
        $("#zhuanjis_bottom").hide();
    });

    //这是点击资源开始
    $(".resourceP").click(function () {

    });

    $(".resource_two .zhuanlan_top button").click(function () {
        $(".resource_one").show();
        $(".resource_two").hide();
    });
    $(".resource_one .zhuanlan_top button").click(function () {
        $(".resource_one").hide();
        $(".resource_two").show();
    });

//点击收益部分  课程收益
    $('.select-udss .classResive').click(function (event) {
        $('.littleBoxss .giftResive').removeClass('activeP')
        $('.littleBoxss .classResive').addClass('activeP')
        $('#gift_Resive').addClass('hide');
        $('#kecheng_Resive').removeClass('hide');
        event.stopPropagation(); // 阻止事件冒泡
        localStorage.AnchorsTbl_myResive = 'classResive';
        getCourseResiveList(1);
        $('#test06').val('');
        $('#test07').val('');
    })

//礼物收益点击
    $('.select-udss .giftResive').click(function (event) {
        $('.littleBoxss .classResive').removeClass('activeP')
        $('.littleBoxss .giftResive').addClass('activeP')

        $('#kecheng_Resive').addClass('hide');
        $('#gift_Resive').removeClass('hide');
        event.stopPropagation(); // 阻止事件冒泡
        localStorage.AnchorsTbl_myResive = 'giftResive';
        getGiftResiveList(1);
        $('#test08').val('');
        $('#test09').val('');
    })

//点击帐号列表
    $(".account_number").click(function () {
        $("#curriculum").hide();
        $(".begin_approve").show();
        $(".school").show();
        $(".physician_two_two").hide();
        $(".message_return").hide();
        $("#resource").hide();
//  处理学堂关闭
        $(".littleBox").slideUp("slow");
        $(".name_news").addClass("activeP");
        $(".name_personage").removeClass("activeP");
    });


//点击认证信息   name_news
    $(".name_news").click(function () {
//	隐藏认证信息第二页  
        localStorage.AccountNumber = 'name_news';
    });

//点击个人信息  
    $(".name_personage").click(function () {
        localStorage.AccountNumber = 'name_personage';
    });

//账户重新认证点击开始
    $(".reauthentication .right_btn").click(function () {
        $(".begin_approve").hide();
        $(".physician_two_two").removeClass('hide');
        $(".physician_two_two").show();
    });

//点击返回按钮
    $(".account_main_alter_title .two").click(function () {
        $(".physician_two_two").hide();
        $(".begin_approve").show();
    });

//账户个人信息  
    $(".right_modification").click(function () {
        //获取资源列表渲染
        initResource(1, true);
        $(".personal_details").addClass('hide');
        $(".message_return").removeClass('hide');

        //获取主播信息回显
        RequestService("/anchor/info", "get", null, function (data) {
            if (data.success == true) {
                var anchor = data.resultObject;
                $('.account .message_return .anchor_nick_name').val(anchor.name);

                if (anchor.profilePhoto) {
                    $('.account .message_return #profilePhotoImg').html('<img id="imghead" border="0" src=' + anchor.profilePhoto + ' width="90" height="90" >')
                }

                if (anchor.detail == null) {
                    UE.getEditor('anchor_details_editor').setContent('');
                } else {
                    UE.getEditor('anchor_details_editor').setContent(anchor.detail);
                }

                if (localStorage.AccountStatus == '1') {
                    //坐诊时间渲染
                   	var workArr = anchor.wt==null?[]:anchor.wt.split(",");

                    var selectTime;
					var sureType=[];
					$('.workTime tr p img').each(function(){
							selectTime=$(this).attr("data-type");
							sureType.push(selectTime)
					})
					for (var i = 0; i < sureType.length; i++) {
		                for (j = 0; j < workArr.length; j++) {
		                    if (sureType[i] == workArr[j] && !$('.workTime tr p img').eq(i).hasClass("active")) {
		                        $('.workTime tr p').eq(i).click();
		                    }
		                }
		         	}

                    // //医馆列表的选中效果
                    // RequestService("/doctor/getHospital", "get", null, function (data) {
                    	$('.workHos_select').selectpicker('val', (anchor.hospitalId));
                    // })
                    //精彩致辞的选中状态
//                  	$('.speech_resource').selectpicker('val', (anchor.resourceId));

                } else if (localStorage.AccountStatus == '2') {
                    //医馆自己的模板渲染
                    //电话
                    if (anchor.tel) {
                        $('#u_hospital_tel').val(anchor.tel)
                    }
                }
                $('.speech_resource').selectpicker('val', (anchor.resourceId));
                var provinces = {
                    province: anchor.province,
                    city: anchor.city
                };
                $(".provinces").iProvincesSelect("init",provinces);
                $('#u_detailAddress').val(anchor.detailAddress)
            }
        })
    });

//选择医馆列表选中之后出发的事件
//     $('#speech_select1').change(function () {
//         //医馆ID的获取
//         hosID = $('#speech_select1').val()
//         if (hosID == -1) {
//             //清空信息
//             clearHosList()
//             return false;
//         }
//         var hos = getHosById(hosID);
//         if (hos == null) return;
//         //电话号码
//         $('.appointmentTel').val(hos.tel);
//     })


//清空医师入驻医馆信息列表
    function clearHosList() {
        //省分
        $('#hosPro').val('');
        //市区
        $('#hosCity').val('')
        //详细地址清空
        $('#u_detailAddress').val('')
        //电话清空
        $('#doctor_baseInf .appointmentTel').val('');
        //坐诊时间
        $('#u_workTime > li').removeClass('color');
    }

    $(".message_return .message_title .two").click(function () {
        $(".message_return").addClass("hide");
        $(".personal_details").removeClass('hide');
    });

    //进入之后判断账户中主播修改信息的页面结构
    if (localStorage.AccountStatus == 1) {
        //医师
        $('#doctor_baseInf').removeClass('hide');
        $('#hospital_baseInf').addClass('hide');
    } else if (localStorage.AccountStatus == 2) {
        //医馆
        $('#doctor_baseInf').addClass('hide');
        $('#hospital_baseInf').removeClass('hide')
    }
    $(".provinces").iProvincesSelect("init",null);
    //主播修改信息的时候获取医馆信息渲染医馆列表
    if (localStorage.AccountStatus == 1) {
        $(".cityem").add("hide")
        RequestService("/doctor/apply/listHospital/0", "get", null, function (data) {
            hosList = data.resultObject.records;
            //列表渲染
            $('#speech_select1').append('<option value="-1">请选择医馆</option>')
            $('#speech_select1').append(template('hosListTpl', {item: hosList}));
            //渲染之后在此调用插件
            $('.selectpicker_collection').selectpicker('refresh');
            $('.workHos_select').selectpicker({
                'selectedText': 'cat', size: 10
            });
        });
    }

    function getHosById(id) {
        for (var i = 0; i < hosList.length; i++) {
            if (hosList[i].id == id) {
                return hosList[i];
            }
        }
        return null;
    }
    
//  是否选择回放按钮
	$(".radio-select li").click(function(){
		$(".radio-select li").find("em").removeClass("active");
		$(this).find("em").addClass("active");
	})

//	关闭弹窗
    $(".history-live").on("click",".close-live",function(){
    	$(".history-live").addClass("hide");
    	$("#mask").addClass("hide");
    })
    
    
});


