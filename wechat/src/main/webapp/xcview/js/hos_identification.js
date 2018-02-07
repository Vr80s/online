
function btn_up(){
    /*if($("#name").val()==''||$("#company").val()==''||$("#businessLicenseNo").val()==''
        ||$("#previewImg3").val()==''||$("#licenseForPharmaceuticalTrading").val()==''
        ||$("#previewImg4").val()==''){
        $(".error-prompt").show();
        $(".btn-up").removeAttr("onclick")
        setTimeout(function(){
            $(".error-prompt").hide();
            $(".btn-up").attr("onclick","btn_up()")
        },2000)
    }*/
    //验证
    if($.trim($("#name").val())==''){
        webToast("请输入医馆名称","middle",3000);
        return false;
    }
    if($.trim($("#company").val())==''){
        webToast("请输入公司名称","middle",3000);
        return false;
    }
    if($.trim($("#businessLicenseNo").val())==''){
        webToast("请输入统一社会信用代码","middle",3000);
        return false;
    }
    if($("#previewImg3").val()==''){
        webToast("请上传营业执照","middle",3000);
        return false;
    }
    if($.trim($("#licenseForPharmaceuticalTrading").val())==''){
        webToast("请输入药品经营许可证号","middle",3000);
        return false;
    }
    if($("#previewImg4").val()==''){
        webToast("请上传药品经营许可证","middle",3000);
        return false;
    }
        //form提交
        var form=document.getElementById("HospitalInfo");
        var fd =new FormData(form);
        $.ajax({
            url: "/xczh/medical/addHospitalApply",
            type: "POST",
            data: fd,
            processData: false,  // 告诉jQuery不要去处理发送的数据
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
            success: function(data){
                if(data.success==true){
                    window.location.href="../html/hos_examine.html";
                }else{
                    webToast(data.errorMessage,"middle",3000);

                }


            }
        });


}
