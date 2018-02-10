var status;
$(function(){
	
	
	
	//yx_新增
	var openId = getQueryString("openId");
	if(stringnull(openId)){
	   localStorage.setItem("openId",openId);
	}	

	
	//获取认证状态
    requestService("/xczh/medical/applyStatus",{
    },function(data) {
        if(data.success==true){
            status = data.resultObject;

        }else{
            webToast(data.errorMessage,"middle",3000);
        }
    });

    balance();

});

//人民币/熊猫币余额
function balance() {
    requestService("/xczh/manager/home",{
    },function(data) {
        if(data.success==true){
            $("#xmbNumber").text(data.resultObject.xmbCount);
            $("#courseNumber").text(data.resultObject.courseCount);
            //用户头像
            $(".header_img").html(template('userInfo',data.resultObject.user));
        }else{
            webToast(data.errorMessage,"middle",3000);
        }
    });
}
//点击我要当主播
function myAnchor() {

    if(status==1||status==3||status==5){
        window.location.href="phy_examine.html";
    }else if(status==2||status==4||status==6){
        window.location.href="hos_examine.html";
    }else{
        window.location.href="my_anchor.html";
    }


}