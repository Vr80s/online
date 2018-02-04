

	function btn_up(){
		if($(".wrap-user-id .userName").val()==''){
			$(".error-prompt").show();
			$(".btn-up").removeAttr("onclick")
			setTimeout(function(){
				$(".error-prompt").hide();
				$(".btn-up").attr("onclick","btn_up()")
			},2000)
		}
		else{
            var formData = new FormData();
            var userName = $("#userName").val();
            var cardNum = $("#cardNum").val();
            formData.append("name",userName);
            formData.append("cardNum",cardNum);
            formData.append("cardPositiveFile",$("#previewImg")[0].files[0]);
            formData.append("cardNegativeFile",$("#previewImg2")[0].files[0]);
            formData.append("qualificationCertificateFile",$("#previewImg3")[0].files[0]);
            formData.append("professionalCertificateFile",$("#previewImg4")[0].files[0]);

             /*requestService("/xczh/medical/addDoctorApply", {
                 name: userName,
                 cardNum: cardNum,
                 cardPositiveFile: $("#previewImg")[0].files[0],
                 cardNegativeFile: $("#previewImg2")[0].files[0],
                 qualificationCertificateFile: $("#previewImg3")[0].files[0],
                 professionalCertificateFile: $("#previewImg4")[0].files[0]
             }, function (data) {
             	alert(data.resultObject);
                 //	课程名称/等级/评论
             });*/

            /*$("#formId").attr("action", "/xczh/medical/addDoctorApply");
            $("#formId").ajaxForm(function(data){

                if(data.success){
                    location.href="../html/phy_examine.html";
                	alert(data.resultObject);
                }else{
                    alert(data.errorMessage);
                }
            });*/
			//location.href="../html/phy_examine.html"
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
                }
            });


		}
	}
		




