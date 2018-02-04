
$(function () {
    requestService("/xczh/medical/doctorInfo", {
    }, function (data) {
        //	医师认证信息
        $(".wp").html(template('doctorInfo',data.resultObject.medicalDoctor));
    });
})
