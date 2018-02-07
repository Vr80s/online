var mobile = "";
$(function(){
    //获取手机号
    mobile = localStorage.getItem("username");
    $("#mobileShow").html(mobile);


});

