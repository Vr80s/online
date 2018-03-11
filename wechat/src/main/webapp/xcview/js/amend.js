var mobile = "";
$(function(){
    //获取手机号
    mobile = localStorage.getItem("username");

});

function submit() {
    //验证
    if($.trim($("#oldPassword").val())==''){
        webToast("请输入旧密码","middle",1500);
        return false;
    }
    if($.trim($("#newPassword").val())==''||$.trim($("#confirmPassword").val())==''){
        webToast("请输入新密码","middle",1500);
        return false;
    }
    if($.trim($("#newPassword").val())!=$.trim($("#confirmPassword").val())){
        webToast("两次密码不一致","middle",1500);
        return false;
    }
    //提交
    requestService("/xczh/set/editPassword",{
        oldPassword:$("#oldPassword").val(),
        newPassword:$("#newPassword").val(),
        username:mobile
    },function(data) {
        if(data.success==true){
            webToast("修改成功","middle",1500);
            setTimeout(function(){
            	 requestService("/xczh/set/logout",{},function(data) {
 	                if (data.success) {
 	                	//删除所有的信息
 	                	localStorage.removeItem("token");
 					    localStorage.removeItem("userId")
 						localStorage.removeItem("name");
 					    localStorage.removeItem("smallHeadPhoto");
 					    localStorage.removeItem("sex");
 					    localStorage.removeItem("provinceName");
 					    localStorage.removeItem("cityName");
 					    localStorage.removeItem("province");
 					    localStorage.removeItem("city");
 					    localStorage.removeItem("email");
 					    localStorage.removeItem("info");
 					    localStorage.removeItem("username");
 					    localStorage.removeItem("ticket");
 					    //删除  登录时用到的cookie
 					    cookie.delete1("_uc_t_");
 					    //删除  第三方登录时用到的cookie
 					    cookie.delete1("third_party_uc_t_");
 	                    window.location.href="my_homepage.html";
 	                }
 	            });
        	},2000)
        }else{
            webToast(data.errorMessage,"middle",1500);
        }
    });
}