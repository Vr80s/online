
$(function(){
//		点击人民币提现   出现添加银行卡提示             withdraws.html--提现页   点击提现之前已经添加过银行卡直接到提现页
    $(".balance .div02").click(function(){
        requestService("/xczh/medical/userBankList",{
        },function(data) {
            if(data.success==true){
                if(data.resultObject.length>0){
                    window.location.href="withdraws.html";
                }else {
                    $(".bank_card").show();
                }
            }else{
                alert(data.errorMessage);
            }
        });

    });

    $(".bank_card_main_fixed .div0").click(function(){
        $(".bank_card").hide();
    });
    $(".bank_card_main_fixed .div1").click(function(){
        $(".bank_card").hide();
    });

});

