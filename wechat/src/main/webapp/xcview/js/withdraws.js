
var bankCardId="";
$(function(){
    getBankCardList();

});
//获取验证码
var countdown=60;
function send(){
    requestService("/xczh/manager/sendSMSCode",{
    },function(data) {

    });
    var obj = $("#btn");
    settime(obj);
}
function settime(obj) { //发送验证码倒计时
    if (countdown == 0) {
        obj.attr('disabled',false);
        obj.val("获取验证码");
        $("#btn").css("background","#00bc12")
        countdown = 60;
        return;
    } else {
        obj.attr('disabled',true);
        obj.val("(" + countdown + ")S");
        countdown--;
        $("#btn").css("background","#ccc")
    }
    setTimeout(function() {
            settime(obj) }
        ,1000)
}
//获取默认银行卡
function getBankCardList() {
    requestService("/xczh/medical/getDefault",{
    },function(data) {
        if(data.success==true){
            var acctp = data.resultObject.acctPan;
            acctp = acctp.slice(15);
            data.resultObject.acctPan = acctp;
            bankCardId = data.resultObject.id;
            $(".account").html(template('data_bank_card',data.resultObject));

        }else{
            alert(data.errorMessage);
        }
    });
}
//点击提现
function withdraw() {
    var Ipt0 = $.trim($('.input').val());
    var Ipt1 = $.trim($('.input0').val());
    requestService("/xczh/manager/withdrawal",{
        rmbNumber:Ipt0,
        bankCardId:bankCardId,
        smsCode:Ipt1,
        orderFrom:2
    },function(data) {
        if(data.success==true){
            alert("提现成功");
        }else{
            alert("提现失败");
        }
    });

}

function BankCardList() {
    window.location.href="../html/select.html";
}