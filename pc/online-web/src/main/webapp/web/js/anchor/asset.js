$(function(){
$("#toResultIpt").keyup(function () {
    var resultIpt = $.trim($("#toResultIpt").val());
    if(resultIpt==null||resultIpt==''||resultIpt==0){
        $(".hdrmb").addClass('hide');
    }else{
        $(".hdrmb").removeClass('hide');
    }
    $("#toResultIptRmb").html(resultIpt/10);
});
});
var bankList;
function initBasaeAssetInfo (){
    RequestService("/anchor/asset/getBaseAssetInfo", "get", null, function(data) {
        $(".coinBalance").html(data.resultObject.coinBalance);
        $("#toResultIpt").attr("placeholder","本次最多可结算"+data.resultObject.coinBalance+"熊猫币");
        $(".rmb").html(data.resultObject.rmb);
        $(".bankCount").html(data.resultObject.bankCount);
    });
    getCoinTransactionList(1);
    getRmbTransactionList(1);
    getBankList();
    getBankCardList();
}
function getCoinTransactionList (current){
    RequestService("/anchor/asset/getCoinTransactionList?size=10&current="+current, "get", null, function(data) {
        for(var i=0;i<data.resultObject.records.length;i++){
            if(data.resultObject.records[i].VALUE>0){
                data.resultObject.records[i].VALUE = "+"+data.resultObject.records[i].VALUE;
            }
        }
        $("#coin_transaction_list").html(template('coin_transaction_list_tpl', data.resultObject));
        debugger
        //每次请求完数据就去渲染分页部分
        if (data.resultObject.pages > 1) { //分页判断
            $(".not-data").remove();
            $(".coin_transaction_pages").css("display", "block");
            $(".coin_transaction_pages .searchPage .allPage").text(data.resultObject.pages);
            $("#Pagination_coinTransaction").pagination(data.resultObject.pages, {
                num_edge_entries: 1, //边缘页数
                num_display_entries: 4, //主体页数
                current_page:current-1,
                callback: function (page) {
                    //翻页功能
                    getCoinTransactionList(page+1);
                }
            });
        } else {
            $(".coin_transaction_pages").css("display", "none");
        }
    });
}

function getRmbTransactionList (current){
    RequestService("/anchor/asset/getRmbTransactionList?size=10&current="+current, "get", null, function(data) {
        for(var i=0;i<data.resultObject.records.length;i++){
            if(data.resultObject.records[i].value>0){
                data.resultObject.records[i].value = "+"+data.resultObject.records[i].value;
            }
            if(data.resultObject.records[i].form == null){
                data.resultObject.records[i].form='—';
            }
            if(data.resultObject.records[i].acctPan == null){
                data.resultObject.records[i].acctPan='—';
            }
            if(data.resultObject.records[i].status == null){
                data.resultObject.records[i].status='—';
            }
            if(data.resultObject.records[i].dismissalRemark == null){
                data.resultObject.records[i].dismissalRemark='—';
            }
        }
        $("#rmb_transaction_list").html(template('rmb_transaction_list_tpl', data.resultObject));
        debugger
        //每次请求完数据就去渲染分页部分
        if (data.resultObject.pages > 1) { //分页判断
            $(".not-data").remove();
            $(".rmb_transaction_pages").css("display", "block");
            $(".rmb_transaction_pages .searchPage .allPage").text(data.resultObject.pages);
            $("#Pagination_rmbTransaction").pagination(data.resultObject.pages, {
                num_edge_entries: 1, //边缘页数
                num_display_entries: 4, //主体页数
                current_page:current-1,
                callback: function (page) {
                    //翻页功能
                    getRmbTransactionList(page+1);
                }
            });
        } else {
            $(".rmb_transaction_pages").css("display", "none");
        }
    });
}

function getBankList (){
    if(bankList == null){
        RequestService("/anchor/asset/getBankList", "get", null, function(data) {
            var str = '';
            bankList = data.resultObject;
            for(var i=0;i<data.resultObject.length;i++){
                str += '<option value="'+data.resultObject[i].code+'">'+data.resultObject[i].value+'</option>';
            }
            $("#content_add_bank").html(str);
        });
    }else{
        var str = '';
        for(var i=0;i<bankList.length;i++){
            str += '<option value="'+bankList[i].code+'">'+bankList[i].value+'</option>';
        }
        $("#content_add_bank").html(str);
    }
}

function getBankCardList(){
    RequestService("/anchor/asset/getBankCardList", "get", null, function(data) {
        $("#bank_card_list").html(template('bank_card_list_tpl', data));
    });
}

function saveSettlement(){
    var amount = $.trim($("#toResultIpt").val());
    if(amount==null||amount==''||amount==0){
        $(".toResultIpt_warn").removeClass("hide");
        return;
    }else{
        $(".toResultIpt_warn").addClass("hide");
    }
    RequestService("/anchor/settlement", "post", {"amount":amount}, function(data) {
        showTip(data.resultObject);
        initBasaeAssetInfo();
    });
}

function saveBankCard(){
    var data = {};
    data.acctName = $.trim($('.content_add #content_add_name').val());
    data.acctPan = $.trim($('.content_add #content_add_card').val());
    data.tel = $.trim($('.content_add #content_add_bank').val());
    data.certId = $.trim($('.content_add #content_add_idCard').val());
    if(verifyBankCard(data)){
        RequestService("/anchor/asset/saveBankCard", "post", data, function(data) {
            if(data.success){
                showTip(data.resultObject);
                initBasaeAssetInfo();
            }else {
                showTip(data.errorMessage);
            }
        });
    }
}

function deleteBankCard(id){
    var title="移除";
    var content="确认移除该银行卡？";
    confirmBox(title,content,function(closefn){
        RequestService("/anchor/asset/deleteBankCard?id="+id, "get", null, function(data) {
            closefn();
            if(data.success){
                showTip(data.resultObject);
                initBasaeAssetInfo();
                getBankCardList();
            }else {
                showTip(data.errorMessage);
            }
        });
    });
}

function verifyBankCard(data){

    //验证
    //户名
    if(!isNv(data.acctName)){
        $('.content_add_name_warn').removeClass('hide');
        return false;
    }else{
        $('.content_add_name_warn').addClass('hide');
    }

    //卡号
    if(!isNv(data.acctPan)){
        $('.content_add_card_warn').removeClass('hide');
        return false;
    }else{
        $('.content_add_card_warn').addClass('hide');
    }

    //选择银行
    if(!isNv(data.tel) ){
        $('.content_add_bank_warn').removeClass('hide');
        return false;
    }else{
        $('.content_add_bank_warn').addClass('hide');
    }

    //身份证号
    if(!isNv(data.certId)){
        $('.content_add_idCard_warn').removeClass('hide');
        return false;
    }else{
        $('.content_add_idCard_warn').addClass('hide');
    }
    //身份证号
    if(!isCardID(data.certId)){
        $('.content_add_idCard_gs_warn').removeClass('hide');
        return false;
    }else{
        $('.content_add_idCard_gs_warn').addClass('hide');
    }
    return true;
}

function isNv(v){
    if(v==null || v==''){
        return false;
    }
    return true;
}