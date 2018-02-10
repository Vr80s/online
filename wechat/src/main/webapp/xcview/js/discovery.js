var status;
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

    if(status==1||status==3||status==5){
        window.location.href="phy_examine.html";
    }else if(status==2||status==4||status==6){
        window.location.href="hos_examine.html";
    }else{
        window.location.href="my_anchor.html";
    }


}