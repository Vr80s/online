
function btn_up(){
    /*if($(".wrap-user-id .userName").val()==''||$(" #cardNum").val()==''
        ||$("#previewImg").val()==''||$("#previewImg2").val()==''
        ||$("#previewImg3").val()==''||$("#previewImg4").val()==''){
        $(".error-prompt").show();
        $(".btn-up").removeAttr("onclick")
        setTimeout(function(){
            $(".error-prompt").hide();
            $(".btn-up").attr("onclick","btn_up()")
        },2000)
    }*/
    //验证
    if($.trim($(" #userName").val())==''){
        webToast("请输入真实姓名","middle",3000);
        return false;
    }
    if($.trim($(" #cardNum").val())==''){
        webToast("请输入身份证号","middle",3000);
        return false;
    }
    if($.trim($(" #previewImg").val())==''){
        webToast("请上传身份证正面","middle",3000);
        return false;
    }
    if($.trim($(" #previewImg2").val())==''){
        webToast("请上传身份证反面","middle",3000);
        return false;
    }
    if($.trim($(" #previewImg3").val())==''){
        webToast("请上传医师资格证","middle",3000);
        return false;
    }
    if($.trim($(" #previewImg4").val())==''){
        webToast("请上传职业资格证","middle",3000);
        return false;
    }
        //form提交
        var form=document.getElementById("docAutInf");
        var fd =new FormData(form);
        $.ajax({
            url: "/xczh/medical/addDoctorApply",
            type: "POST",
            data: fd,
            processData: false,  // 告诉jQuery不要去处理发送的数据
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
            success: function(data){
                console.log(data.resultObject);
                window.location.href="../html/phy_examine.html";
            }
        });

}
