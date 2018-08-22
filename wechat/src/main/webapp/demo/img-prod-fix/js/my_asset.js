
$(function(){

    balance();
    transactionRecord();

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
//交易记录
function transactionRecord() {
    requestService("/xczh/manager/settlementList",{
        pageNumber:1,
        pageSize:10
    },function(data) {
        if(data.success==true){
            $(".record_course_div").html(template('record_course_div',{items:data.resultObject}));
        }else{
            alert(data.errorMessage);
        }
    });
}