
$(function(){
    getBankCardList()
});

//获取默认银行卡
function getBankCardList() {
    requestService("/xczh/medical/getDefault",{
    },function(data) {
        if(data.success==true){
            var acctp = data.resultObject.acctPan;
            acctp = acctp.slice(15);
            data.resultObject.acctPan = acctp;
            $(".account").html(template('data_bank_card',data.resultObject));

        }else{
            alert(data.errorMessage);
        }
    });
}
//点击提现
function withdraw() {
    
}

function BankCardList() {
    window.location.href="../html/select.html";
}