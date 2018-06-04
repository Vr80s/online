debugger
var data = {};
var merId = getQueryString('merId');
var wv = getQueryString('wv');

if(merId == null){
    merId = sessionStorage.getItem("merId");
}else{
    sessionStorage.setItem("merId",merId);
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
    data.appUniqueId = appUniqueId;
}
if(appUniqueId != null && appUniqueId !=''){
    data.token = token;
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
