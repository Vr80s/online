function getApply(){
    var apply = data;
    apply.merId = merId;
    apply.name=$('.name input').val();
    apply.tel=$('.tel input').val();
    apply.age=$('.age input').val();
    apply.nativePlace=$('.birthplace input').val();
    apply.educationExperience=$('.learning_experience .copyreader').val();
    apply.medicalExperience=$('.practice_medicine .copyreader').val();
    apply.goal=$('.study .copyreader').val();
    apply.education=$('.education_show').attr("data-id");
    if($(".sex_show").html()=='女'){
        apply.sex = 0;
    }else if($(".sex_show").html()=='男'){
        apply.sex = 1;
    }
    return apply;
}

function isBlank (str){
    return str == null || str === '';
}

function verifyParams (){
    if($(".buttom").attr("data-able")!=1){
        return false;
    }
    var apply = getApply();
    if(isBlank(apply.name)
        ||isBlank(apply.tel)
        ||isBlank(apply.sex)
        ||isBlank(apply.age)
        ||isBlank(apply.nativePlace)
        ||isBlank(apply.educationExperience)
        ||isBlank(apply.medicalExperience)
        ||isBlank(apply.education)
        ||isBlank(apply.goal)){
        $(".buttom").css("opacity","0.5");
        return false;
    }
    
    //验证手机号
//    if (!(/^1[346578]\d{9}$/.test(apply.tel)) && apply.tel.trim().length == 11) {
//        webToast("请输入正确的手机号", "middle", 1500);
//        $(".buttom").css("opacity","0.5");
//        return false;
//    }
    
    $(".buttom").css("opacity","1");
    return true;
    
}
$(function(){
    // 姓名
    $('.name input').keyup(function(){
        verifyParams();
    });
    // 年龄
    $('.age input').keyup(function(){
        verifyParams();
    });

    // 性别
    $('.sex .sex_show').keyup(function(){
        verifyParams();
    });
    // 籍贯
    $('.birthplace input').keyup(function(){
        verifyParams();
    });
    // 学习经历
    $('.learning_experience .copyreader').keyup(function(){
        verifyParams();
    });
    // 行医经历
    $('.practice_medicine .copyreader').keyup(function(){
        verifyParams();
    });
    // 学中医目的
    $('.study .copyreader').keyup(function(){
        verifyParams();
    });
    /*点击性别开始*/
    $(".sex").click(function(){
        $(".popup").show();
    });
    $("#man").click(function(){
        $(".sex_show").html($("#man").html());
        verifyParams();
    });
    $("#she").click(function(){
        $(".sex_show").html($("#she").html());
        verifyParams();
    });
    $(".popup").click(function(){
        $(".popup").hide();
    });
    /*点击性别结束*/

    /*点击学历开始*/
    $(".education").click(function(){
        $(".education_background").show();
    });
    $("#undergraduate").click(function(){
        $(".education_show").html($("#undergraduate").html());
        verifyParams();
    });
   /* $("#master").click(function(){
        $(".education_show").html($("#master").html());
        verifyParams();
    });
    $("#doctor").click(function(){
        $(".education_show").html($("#doctor").html());
        verifyParams();
    });
    $("#not_have").click(function(){
        $(".education_show").html($("#not_have").html());
        verifyParams();
    });*/
    $(".edu").click(function(){
        $(".education_show").html($(this).html());
        $(".education_show").attr("data-id",$(this).attr("data-id"));
        verifyParams();
    });
    $(".education_background").click(function(){
        $(".education_background").hide();
    });
    /*点击学历结束*/

    // 点击学历开始
    $(".buttom").click(function(){
        if(verifyParams()){
            $(".buttom").attr("data-able","0");
            var apply = getApply();
            var token = sessionStorage.getItem("token");
            var appUniqueId = sessionStorage.getItem("appUniqueId");
            if(token !=null && token!=''){
                apply.token = token;
            }
            if(appUniqueId !=null && appUniqueId!=''){
                apply.appUniqueId = appUniqueId;
            }
            requestService("/xczh/enrol/medicalEntryInformation",apply,function(data){
                if(data.success){
                    webToast(data.resultObject,"middle",1500);
                    setTimeout("window.location.href ='information_registration.html'",1600);
                }else{
                    $(".buttom").attr("data-able","1");
                    webToast(data.errorMessage,"middle",1500);
                }
            },false);
        }
    });
});