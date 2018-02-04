
$(function () {
    requestService("/xczh/medical/hospitalInfo", {
    }, function (data) {
        //	医师认证信息
        $(".wp").html(template('hospitalInfo',data.resultObject.medicalHospital));
    });
})
