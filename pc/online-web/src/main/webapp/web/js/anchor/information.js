$(function(){
    initAuthentication();
});

function initAuthentication (){

    RequestService("/medical/doctor/apply/getLastOne", "get", null, function(data) {
        data = data.resultObject;
        $(".anchor_name").html(data.name);
        $(".anchor_idCard").html(data.cardNum);
        $(".anchor_idCard_a").attr("src",data.cardPositive);
        $(".anchor_idCard_b").attr("src",data.cardNegative);
        $(".anchor_qualification_certificate").attr("src",data.qualificationCertificate);
        $(".anchor_professional_certificate").attr("src",data.professionalCertificate);
    });
}

