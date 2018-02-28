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
    getPhoneNumber();
});
var bankList;
var baseAssetInfo;
function initBasaeAssetInfo (){
    RequestService("/anchor/asset/getBaseAssetInfo", "get", null, function(data) {
        baseAssetInfo = data.resultObject;
        $(".coinBalance").html(data.resultObject.coinBalance);
        $("#toResultIpt").attr("placeholder","本次最多可结算"+parseInt(data.resultObject.coinBalance)+"熊猫币");
        $(".rmb").html(data.resultObject.rmb);
        $(".bankCount").html(data.resultObject.bankCount);
        $(".hdrmb").addClass('hide');
        $(".amount").val('');
        $(".vcode").val('');
        $("#content_add_name").val('');
        $("#content_add_card").val('');
        $("#content_add_idCard").val('');

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
                data.resultObject.records[i].form='无';
            }
            if(data.resultObject.records[i].acctPan == null){
                data.resultObject.records[i].acctPan='无';
            }
            if(data.resultObject.records[i].status == null){
                data.resultObject.records[i].status='无';
            }
            if(data.resultObject.records[i].dismissalRemark == null){
                data.resultObject.records[i].dismissalRemark='无';
            }
        }
        $("#rmb_transaction_list").html(template('rmb_transaction_list_tpl', data.resultObject));

		//银行卡处理
        for(var i = 0;i < $('.bankCard').length;i++){
        	if($('.bankCard').eq(i).text() != '无'){
        		$('.bankCard').eq(i).text($('.bankCard').eq(i).text().trim().replace(/^\d{15}/, '***** ***** **** ')) ;
        	}
        }
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


//银行卡账号处理
 function stringHidePart2(strObj){
            var strLength = strObj.length;
            var star = ''; 
            var strRel = '';
            if(strLength>3){
                var hideSec = strObj.substring(3);    //星号部分
                for(var i=4;i<hideSec.length;i++){
                    star+= "*";
                }
            };
            strRel = strObj.substring(0,3) + star + strObj.substr(strObj.length-4);
            // strRel=strRel.replace(/\s/g,'').replace(/(.{4})/g,"$1 ");

            return strRel;
        };


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
        for(var i=0;i<data.resultObject.length;i++){
            data.resultObject[i].tailNumber = data.resultObject[i].acctPan.substring(17,21);
            if(data.resultObject[i].default){
                $("#userNameIpt").val(data.resultObject[i].acctName);
                bankCardId = data.resultObject[i].id;
            }
        }
        $("#bank_card_list").html(template('bank_card_list_tpl', data));
        $("#bank_card").html(template('bank_card_tpl', data));
        //提现中的银行卡点击选中效果
        $('#mymoney .content_toCash .chooseCard ul li').click(function(){
            $('#mymoney .content_toCash .chooseCard ul li').removeClass('redBorder')
            $(this).addClass('redBorder');
            $("#userNameIpt").val($(this).attr("acctName"));
            bankCardId = $(this).attr("bankCardId");
        })
    });
}

function saveSettlement(){
    $('.waring').addClass('hide');
    var amount = $.trim($("#toResultIpt").val());
    if(amount==null||amount==''||amount==0){
        $(".toResultIpt_warn").removeClass("hide");
        return;
    }else{
        $(".toResultIpt_warn").addClass("hide");
    }
    if(parseInt(amount)>parseInt(baseAssetInfo.coinBalance)){
        $(".toResultIpt_warn_beyond").removeClass("hide");
        return;
    }else{
        $(".toResultIpt_warn_beyond").addClass("hide");
    }
    RequestService("/anchor/settlement", "post", {"amount":amount}, function(data) {
        showTip(data.resultObject);
        $("#toResultIpt").val("");
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

function setDefaultBankCard(id){
    RequestService("/anchor/asset/setDefaultBankCard?id="+id, "post", null, function(data) {
        if(data.success){
            showTip(data.resultObject);
            getBankCardList();
        }else {
            showTip(data.errorMessage);
        }
    });
}
function sendVerificationCode(){
    RequestService("/anchor/sendVerificationCode" ,"post", null, function(data) {
        if(data.success){
//          showTip(data.resultObject);
            $('.phonePwdIpt_warn').addClass('hide');
            //倒计时部分
				var myTime=90;
				var timer=null;
				timer=setInterval(auto,1000);						
				function auto(){
					myTime--;
					$(".getPassWord").html(myTime+ 's');
					$(".getPassWord").removeAttr('onclick');
					$(".getPassWord").css({"background":"#dedede","color":"#999999"})
					if(myTime==0){
						setTimeout(function(){
						clearInterval(timer)
						$(".getPassWord").html('获取验证码');
						$(".getPassWord").attr('onclick','btn_cade()');
						$(".getPassWord").css({"background":"#00bd12","color":"white"})
						},1000)								
					}
				}	
				
        }else {
//      	phonePwdIpt_warn 
//          showTip(data.errorMessage);
			$('.phonePwdIpt_warn').text(data.errorMessage)
            $('.phonePwdIpt_warn').removeClass('hide');
        }
    });
    
}
function getPhoneNumber (){
    RequestService("/anchor/asset/getPhoneNumber", "get", null, function(data) {
        $("#phoneNumber").html(data.resultObject);
    });
}
var bankCardId;
//提现中的确定按钮点击
function saveEnchashment(){
    var data = {};
    data.amount = $.trim($('.amount').val());
    data.code = $.trim($('.vcode').val());
    data.bankCardId = bankCardId
    if(verifyEnchashment(data)){
        RequestService("/anchor/enchashment", "post", data, function(data) {
            if(data.success){
                showTip(data.resultObject);
                initBasaeAssetInfo();
                window.location.reload();
            }else {
                showTip(data.errorMessage);
            }
        });
    }
}

function verifyEnchashment(data){
    //提现金额
    $('.waring').addClass('hide');
    if(!isNv(data.amount)){
        $('.warning_amount_null').removeClass('hide');
        return false;
    }else{
        $('.warning_amount_null').addClass('hide');
    }
    if(!numberCk(data.amount)){
        $('.warning_amount_illegal').removeClass('hide');
        return false;
    }else{
        $('.warning_amount_illegal').addClass('hide');
    }
    if(parseInt(baseAssetInfo.rmb)<parseInt(data.amount)){
        $('.warning_amount_beyond').removeClass('hide');
        return false;
    }else{
        $('.warning_amount_beyond').addClass('hide');
    }
    //户名
    if(!isNv(data.bankCardId)){
        $('.warning_bank_card_id').removeClass('hide');
        return false;
    }else{
        $('.warning_bank_card_id').addClass('hide');
    }
    //手机验证码
    if(!isNv(data.code)){
    	$('.phonePwdIpt_warn').text('请输入手机验证码')
        $('.phonePwdIpt_warn').removeClass('hide');
        return false;
    }else{
        $('.phonePwdIpt_warn').addClass('hide');
    }
    return true;
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