var mobile = "";
$(function(){
    //获取手机号
    mobile = localStorage.getItem("username");

});

function submit() {
    //验证
    if($.trim($("#oldPassword").val())==''){
    	jqtoast("请输入旧密码");
        $(".web_toast").removeClass("web_toasts");
        return false;
    }
    if($.trim($("#newPassword").val())==''||$.trim($("#confirmPassword").val())==''){
    	jqtoast("请输入新密码");
        $(".web_toast").removeClass("web_toasts");
        return false;
    }
    if($.trim($("#newPassword").val())!=$.trim($("#confirmPassword").val())){
    	jqtoast("两次密码不一致");
        $(".web_toast").removeClass("web_toasts");
        return false;
    }

    


    //提交  
    requestService("/xczh/set/editPassword",{
        oldPassword:$("#oldPassword").val(),
        newPassword:$("#newPassword").val(),
        username:mobile
    },function(data) {
        if(data.success==true){
        	jqtoast("修改成功");
            setTimeout(function(){
//            	 requestService("/xczh/set/logout",{},function(data) {
// 	                if (data.success) {
 	                	//删除所有的信息
 	                    window.location.href="lickacc_mobile.html";
// 	                }
// 	            });
        	},2000)
        }else{
            /*var newPassword = document.getElementById("newPassword").value;
            if( /^[a-zA-Z0-9]{6,18}$/.test(newPassword)){
                alert(456);
            }

            var confirmPassword = document.getElementById("confirmPassword").value;
            if( /^[a-zA-Z0-9]{6,18}$/.test(confirmPassword)){
                alert(123);
            }*/
            // webToast(data.errorMessage,"middle",1500);

//          webToast("格式错误，密码为6-18位英文大小写字母或者阿拉伯数字","middle",1500);
			jqtoast("您输入的旧密码不正确");
            $(".web_toast").addClass("web_toasts");
           /* $(".web_toast").css("left","50%");
            $(".web_toast").css("margin-left","-2.9rem");*/
        }
    });


}