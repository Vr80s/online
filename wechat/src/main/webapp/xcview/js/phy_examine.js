
$(function () {
    requestService("/xczh/medical/doctorInfo", {
    }, function (data) {
        //	医师认证信息
        $(".wp").html(template('doctorInfo',data.resultObject.medicalDoctor));
        $(".wp").html(template('doctorInfo',data.resultObject.medicalDoctor));
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