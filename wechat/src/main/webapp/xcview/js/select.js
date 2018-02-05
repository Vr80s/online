

var bank_card_id="";
$(function(){
    getBankCardList()
});

//点击下一步
function clickNext() {
    var acct_name = $("#acct_name").val();//持卡人
    var acct_pan = $("#acct_pan").val();//卡号
    var bankCard_id = $("#bankCard_id").val();//所属银行code
    var cert_id = $("#cert_id").val();//身份证号
    if(bankCard_id==null||bankCard_id==""){
        return ""
    }
    requestService("/xczh/medical/addBankCard",{
        acctName:acct_name,
        acctPan:acct_pan,
        certId:cert_id,
        tel:bankCard_id
    },function(data) {
        if(data.success==true){
            alert(data.resultObject);
        }else{
            alert(data.errorMessage);
        }
    });
}
//获取银行卡列表
function getBankCardList() {
    requestService("/xczh/medical/userBankList",{
    },function(data) {
        if(data.success==true){
            for(var i=0;i<data.resultObject.length;i++){
                var acctp = data.resultObject[i].acctPan;
                acctp = acctp.slice(17);
                data.resultObject[i].acctPan = acctp;
            }
            $(".select_main").html(template('select_bank_card',{items:data.resultObject}));
            var aBtn=$('.select_main .main_one');
            for(i=0;i<aBtn.length;i++){
                $(aBtn[i]).click(function(){
                    for(i=0;i<aBtn.length;i++){
                        $(this).find(".main_one_right0").addClass('main_one_right');
                        $(aBtn[i]).find(".main_one_right0").removeClass('main_one_right');
                    }
                    $(this).find(".main_one_right0").addClass('main_one_right');
                    $(aBtn[i]).find(".main_one_right0").removeClass('main_one_right');

                })

            }
            $(aBtn[0]).click();

        }else{
            alert(data.errorMessage);
        }
    });
}
//删除银行卡
function deleteBankCardById(bankCardId) {
    bank_card_id=bankCardId;
    alert(bankCardId)
}
function back() {
    if(bank_card_id!=""){
        requestService("/xczh/medical/updateDefault",{
            id:bank_card_id
        },function(data) {
            if(data.success==true){
                alert(data.resultObject);
                window.history.go(-1);
            }else{
                alert(data.errorMessage);
            }
        });

    }

}

function addBankCard() {
    window.location.href="../html/bank_card.html";
}