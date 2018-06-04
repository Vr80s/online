$(function(){
    debugger
    requestGetService("/xczh/enrol/enrollmentRegulations/"+merId+"/cardInfo",data,function(data){
        if(data.success){
            var cardInfo = data.resultObject;
            $(".profilePicture").attr("src",cardInfo.profilePicture);
            $(".title").html(cardInfo.title);
            $(".propaganda").html(cardInfo.propaganda);
            $(".doctorIntroduction").html(cardInfo.doctorIntroduction);
            $(".yuan").html("￥"+cardInfo.tuition);
            $(".countLimit").html(cardInfo.countLimit+"人");
            $(".deadline").html(cardInfo.deadline);
            $(".studyAddress").html(cardInfo.studyAddress);
            $("#qrcode").qrcode(cardInfo.enrolShareUrl);
        }else{
            location.href="/xcview/html/apprentice/apprentice.html";
        }
    },false);
    $("#creat").click(function(){
        html2canvas($("#generate_card"),{ // $(".myimg")是你要复制生成canvas的区域，可以自己选
            onrendered:function(canvas){
                dataURL =canvas.toDataURL("image/png");
                //下载图片
                $('.myimg img').attr( 'src' ,  dataURL );
            }
        })
        $("#generate_card").hide();
    })

       $("#creat").click();
    $(".invitation_share img").click(function(){
        $(".weixin_ceng").show();
    });
    $(".weixin_ceng").click(function(){
        $(".weixin_ceng").hide();
    });

});