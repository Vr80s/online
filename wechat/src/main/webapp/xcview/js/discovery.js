var status;
//	判断是否为游客点击
var user_cookie = cookie.get("_uc_t_");

$(function(){
    //获取认证状态
    requestService("/xczh/medical/applyStatus",{
    },function(data) {
        if(data.success==true){
            status = data.resultObject;

        }else{
            webToast(data.errorMessage,"middle",3000);
        }
    });
});

	
//点击我要当主播
function myAnchor() {
    localStorage.setItem("judgeSkip", "find");
    	if(user_cookie == null || user_cookie == ''){
        window.location.href="enter.html";         //判断是否为游客并跳转登陆界面
    }
    else if(status==1||status==3||status==5){
        window.location.href="phy_examine.html";
    }else if(status==2||status==4||status==6){
        window.location.href="hos_examine.html";
    }else{
        window.location.href="my_anchor.html";
    }


}