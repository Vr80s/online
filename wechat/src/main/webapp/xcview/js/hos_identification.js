
function btn_up(){
    if($("#name").val()==''||$("#company").val()==''||$("#businessLicenseNo").val()==''
        ||$("#previewImg3").val()==''||$("#licenseForPharmaceuticalTrading").val()==''
        ||$("#previewImg4").val()==''){
        $(".error-prompt").show();
        $(".btn-up").removeAttr("onclick")
        setTimeout(function(){
            $(".error-prompt").hide();
            $(".btn-up").attr("onclick","btn_up()")
        },2000)
    }
    else{
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
                console.log(data.resultObject);
                window.location.href="../html/hos_examine.html";
            }
        });

    }
}
