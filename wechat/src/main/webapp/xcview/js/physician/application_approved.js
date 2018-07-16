// debugger
var data = {};
var doctorId = getQueryString('doctor');
var wv = getQueryString('wv');

if(doctorId == null){
    doctorId = sessionStorage.getItem("doctorId");
}else{
    sessionStorage.setItem("doctorId",doctorId);
}
var token = getQueryString('token');
var appUniqueId = getQueryString('appUniqueId');
if(token != null && token != ''){
    sessionStorage.setItem("token",token);
}else{
    token = sessionStorage.getItem("token");
}
if(appUniqueId != null && appUniqueId !=''){
    sessionStorage.setItem("appUniqueId",appUniqueId);
}else{
    appUniqueId = sessionStorage.getItem("appUniqueId");
}
if(token != null && token != ''){
    data.token = token;
}
if(appUniqueId != null && appUniqueId !=''){
    data.appUniqueId = appUniqueId;
}
if(wv != null && wv !=''){
    sessionStorage.setItem("wv",wv);
}else{
    wv = sessionStorage.getItem("wv");
}
if(wv == null || wv ==''){
    $(".footer_perch").show();
    $(".footer").show();
}


$(function(){
    // debugger
    requestGetService("/xczh/enrol/medicalEntryInformation/online",{
        doctorId:doctorId
    },function(data){
        data.resultObject.education = getEducation(data.resultObject.entryInformation);
        $("body").html(template('entry_information_temp',data.resultObject.entryInformation))
    });

})

function getEducation(education){
    if(education == 5){
        return "本科";
    }
    if(education == 6){
        return "硕士";
    }
    if(education == 7){
        return "博士";
    }
    if(education == 0){
        return "无";
    }
}
