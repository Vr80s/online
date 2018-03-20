
$(function () {
    requestService("/xczh/medical/hospitalInfo", {
    }, function (data) {
        //	医师认证信息
        $(".wp").html(template('hospitalInfo',data.resultObject.medicalHospital));
    });
})

//判断跳转
function returnSkip() {

    if(localStorage.judgeSkip=="find"){
        window.location.href="discovery.html";
    }else{
        window.location.href="my_homepage.html";
    }

}