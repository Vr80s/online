//requestService
$(function(){

    var doctorId = getQueryString("doctor");
    requestGetService("/doctor/treatment",{doctorId:doctorId},function (data) {
        if (data.success == true) {
            // 提交预约信息
            $('.main').html(template('main_id', {items: data.resultObject}));
            $(".handler_tel input").val(data.resultObject.tel);
            // $(".tel input").val(result.mobile);
            // 时间段
            $('.time_popout_main_ul').html(template('time_popout_main_ul_id', {items: data.resultObject.treatments}));



            // 循环li
            var aBtn=$('.time_popout_main ul li');
            for(i=0;i<aBtn.length;i++){

                $(aBtn[i]).click(function(){
                    for(i=0;i<aBtn.length;i++){
                        var html = $(this).find("span").html();
                        var id=$(this).find("span").attr("data-ids");
                        $(".handler_time span").attr("data-id",id);
                        $(".handler_time span").html(html);
                        $(".time_popout").hide();
                        $(aBtn[i]).find("img").css("opacity","0");
                        $(this).find("img").css("opacity","1");
                    }
                    /*$(this).removeClass();  
                    $(this).addClass('active6');*/
                })
            }
            $(aBtn[0]).click();

            // 点击预约时间显示
            $(".handler_time_click").click(function(){
                $(".time_popout").show();
            });
            // 点击预约时间显示后的背景
            $(".time_popout_bg").click(function(){
                $(".time_popout").hide();
            });

            // 申请成功--我知道啦
            $(".prosperity_popout_hide").click(function(){
                $(".prosperity_popout").hide();
            });

            // 申请失败--我知道啦
            $(".failure_popout_hide").click(function(){
                $(".failure_popout").hide();
            });
        }
    });


    // 点击提交表单按钮
    $(".handler_btn").click(function(){
        // var doctorId = getQueryString("doctor");
        var id=$(".handler_time_span").attr("data-id");
        var name = $(".booking_person_span").html(); //预约人
        // var treatmentId = $(".handler_time_span").html(); //预约时间
        var tel = $(".handler_tel_put").val(); //预留电话
        if (!(/^1[346578]\d{9}$/.test(tel))) {

            jqtoast("请输入正确的手机号");
            return false;
        }
        var question = $(".textarea").val(); //请简单描述您的问题

        requestService("/doctor/treatment/appointmentInfo",{
            treatmentId:id,
            name:name,
            tel:tel,
            question:question
        },function (data) {
            if (data.success == true) {
                // webToast("提交成功","middle",1500);
                jqtoast("提交成功");
            }else{
                jqtoast(data.errorMessage);
            }
        });

    });





});





