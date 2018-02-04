
$(function(){
    getBankCardList()
});

//获取默认银行卡
function getBankCardList() {
    requestService("/xczh/medical/userBankList",{
    },function(data) {
        if(data.success==true){
            var acctp = data.resultObject[0].acctPan;
            acctp = acctp.slice(17);
            data.resultObject[0].acctPan = acctp;
            $(".account").html(template('data_bank_card',data.resultObject[0]));

        }else{
            alert(data.errorMessage);
        }
    });
}


function BankCardList() {
    window.location.href="../html/bankcards.html";
}