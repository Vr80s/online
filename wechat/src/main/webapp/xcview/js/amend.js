var mobile = "";
$(function(){
    //获取手机号
    mobile = localStorage.getItem("username");

});

function submit() {
    //验证
    if($.trim($("#oldPassword").val())==''){
        webToast("请输入旧密码","middle",3000);
        return false;
    }
    if($.trim($("#newPassword").val())==''||$.trim($("#confirmPassword").val())==''){
        webToast("请输入新密码","middle",3000);
        return false;
    }
    if($.trim($("#newPassword").val())!=$.trim($("#confirmPassword").val())){
        webToast("两次密码不一致","middle",3000);
        return false;
    }
    //提交
    requestService("/xczh/set/editPassword",{
        oldPassword:$("#oldPassword").val(),
        newPassword:$("#newPassword").val(),
        username:mobile
    },function(data) {
        if(data.success==true){
            webToast("修改成功","middle",3000);
            window.location.href='lickacc_mobile.html';
        }else{
            webToast(data.errorMessage,"middle",3000);
        }
    });
}