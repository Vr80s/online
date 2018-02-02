
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
            $(".bank_card").html(template('bank_card_id',{items:data.resultObject}));
            var aBtn1=$('.show');
            var bankCardId ="";
            for(i=0;i<aBtn1.length;i++){
                $(aBtn1[i]).find(".bank_card_one").click(function(){
                    $(this).siblings(".untie").show();
                    bankCardId = $(this).find("#bankCardId").html();
                });
                $(aBtn1[i]).find(".div1").click(function(){
                    $(this).parent().parent(".untie").hide();
                });
                $(aBtn1[i]).find(".div0").click(function(){
                    deleteBankCardById(bankCardId);
                });
            }
        }else{
            alert(data.errorMessage);
        }
    });
}
//删除银行卡
function deleteBankCardById(bankCardId) {
    requestService("/xczh/medical/deleteBankCard",{
        id:bankCardId
    },function(data) {
        if(data.success==true){
            getBankCardList();
        }else{
            alert(data.errorMessage);
        }
    });
}

function addBankCard() {
    window.location.href="bank_card.html";
}