//requestService
// $(function(){
    var doctorId = getQueryString("doctor");
    var treatmentId = getQueryString("dataId");
    requestGetService("/doctor/treatment",{doctorId:doctorId},function (data) {
        if (data.success == true) {
            // 提交预约信息
            $('.main').html(template('main_id', {items: data.resultObject}));
            $(".handler_tel input").val(data.resultObject.tel);
            // $(".tel input").val(result.mobile);
            // 时间段
            $('.time_popout_main_ul').html(template('time_popout_main_ul_id', {items: data.resultObject.treatments}));

            // 当前选中的诊疗id
            var zhengliaoid = getQueryString('dataId');
            // alert("链接 "+treatmentId);
            // 找到对应的，把它下面的图片显示
            $(".zhengliao_"+treatmentId).find('img').css("opacity","1");
            // 找到该li下面的时间段
            var zhengliao = $(".zhengliao_"+treatmentId).find('span').html();// 时间段
            $(".handler_time span").html(zhengliao);
            $(".time_popout_main_ul li").click(function(){
                var id=$(this).find("span").attr("data-ids");
                $(".handler_time span").attr("data-id",id);
                $('.zhengliao_img').css("opacity","0");
                $(".zhengliao_"+id).find('img').css("opacity","1");
                var html = $(this).find("span").html();
                treatmentId = id;
                zhengliao = $(this).find('span').html();// 时间段
                $(".handler_time span").html(html);
                // alert("时间段："+zhengliao);

                $(".time_popout").hide();
            });
            //$(".zhengliao_"+zhengliaoid).click();
            // 循环li
            /*var aBtn=$('.time_popout_main ul li');
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
                })
            }*/
            // $(aBtn[0]).click();

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
                window.location.href="/xcview/html/physician/physicians_page.html?doctor="+doctorId; 
            });

            // 申请失败--我知道啦
            $(".failure_popout_hide").click(function(){
                $(".failure_popout").hide();
                // window.location.href="/xcview/html/physician/physicians_page.html?doctor="+doctorId;
            });

            $('.textarea').keyup(function(){
                var tel = $(".handler_tel_put").val(); //预留电话
                var question = $(".textarea").val(); //请简单描述您的问题
                
                if(tel != '' && question != '' && $('#checkbox1').is(':checked')){
                    $(".handler_btn").css("opacity","1");
                }else{
                    $(".handler_btn").css("opacity","0.5");  
                }

            })

            $('.handler_tel_put').keyup(function(){
                var tel = $(".handler_tel_put").val(); //预留电话
                var question = $(".textarea").val(); //请简单描述您的问题
                
                if(tel != '' && question != '' && $('#checkbox1').is(':checked')){
                    $(".handler_btn").css("opacity","1");
                }else{
                    $(".handler_btn").css("opacity","0.5");  
                }

            });

            $('#checkbox1').click(function(){
                var tel = $(".handler_tel_put").val(); //预留电话
                var question = $(".textarea").val(); //请简单描述您的问题
                
                if(tel != '' && question != '' && $('#checkbox1').is(':checked')){
                    $(".handler_btn").css("opacity","1");
                }else{
                    $(".handler_btn").css("opacity","0.5");  
                }

            });



        }
    });

    
    // 点击提交表单按钮
    $(".handler_btn").click(function(){
        var name = $(".booking_person_span").html(); //预约人
        var time = $(".handler_time_span").html();   //时间
        var tel = $(".handler_tel_put").val(); //预留电话
        var apprenticeId = $('#J_apprenticeId').val();
        var question = $(".textarea").val(); //请简单描述您的问题
        
        if (!(/^1[346578]\d{9}$/.test(tel))) {
            jqtoast("请输入正确的手机号");
            return false;
        }

        if (isBlank(question)) {
            jqtoast("诊疗问题不能为空");
            return false;
        }

        /*var agreementchecked = document.getElementById("checkbox1").checked;
        if (!agreementchecked) {
            jqtoast("请同意协议内容");
            return false;
        }*/

        if(!$('#checkbox1').is(':checked')) {
            jqtoast("请同意协议内容");
            return false;
        }

        requestService("/doctor/treatment/appointmentInfo",{
            treatmentId:treatmentId,
            apprenticeId: apprenticeId,
            name:name,
            tel:tel,
            question:question
        },function (data) {
            if (data.success == true) {    
                $(".prosperity_popout").show();    //申请完成
            }else{

                if (data.code == 5004) {
                    $(".failure_popout").show();   //已经预约
                }else{
                    jqtoast(data.errorMessage);
                };
                
            }
        },false);

    });


    // 我知道了--失败
    $(".failure_popout_hide").click(function(){
        $(".failure_popout").hide();
    });

    // 我知道了--成功
    $(".prosperity_popout_hide").click(function(){
        $(".prosperity_popout").hide();
        window.location.href="/xcview/html/physician/physicians_page?doctor="+doctorId; 
    });

// });





