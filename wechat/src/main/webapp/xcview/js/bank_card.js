
$(function(){

    requestService("/xczh/medical/getBankCardList",{
    },function(data) {
        var cardList = data.resultObject;
        var listres=[];
        for(var i=0;i<cardList.length;i++){
            var map={};
            map.value=cardList[i].code+"";
            map.text=cardList[i].value;
            listres[i]=map;
        }
        //选择银行
        var city_picker2 = new mui.PopPicker({layer:1});
        city_picker2.setData(listres);
        $("#bankCard_text").on("tap", function(){
            setTimeout(function(){
                city_picker2.show(function(items){
                    $("#bankCard_id").val((items[0] || {}).value);
                    $("#bankCard_text").html((items[0] || {}).text);
                });
            },200);
        });
    });
});

//点击下一步
function clickNext() {
    var acct_name = $("#acct_name").val();//持卡人
    var acct_pan = $("#acct_pan").val();//卡号
    var bankCard_id = $("#bankCard_id").val();//所属银行code
    var cert_id = $("#cert_id").val();//身份证号
    if(bankCard_id==null||bankCard_id==""){
        alert("请选择银行")
        return
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