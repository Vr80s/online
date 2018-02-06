
$(function(){

    balance();

});

//人民币/熊猫币余额
function balance() {
    requestService("/xczh/manager/anchorConsoleNumber",{
    },function(data) {
        if(data.success==true){
            $("#rmbNumber").text(data.resultObject.rmbNumber);
            $("#xmbNumber").text(data.resultObject.xmbNumber);
            $("#courseNumber").text(data.resultObject.courseNumber);
        }else{
            alert(data.errorMessage);
        }
    });
}