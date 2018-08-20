var price=5;
var rate;
var env;
//var rate=10;//一元兑换10熊猫币
var orderIsExit = "";

//	 	点击充值按钮
$(".alipay").click(function(){
	$(".weixin-er").hide();
	$(".alipay-er").show();
	$(".alipay").css("background","#fff");
	$(".weixin").css("background","none");
	
	
	alipayMadeCode();
});

$(".weixin").click(function(){
	$(".weixin-er").show();
	$(".alipay-er").hide();
	$(".alipay").css("background","none");
	$(".weixin").css("background","#fff");
	
	weixinpayMadeCode();
});

var out_trade_no="";
var qr_code ="";
$('.chongZhi').click(function(){
	
	
    RequestService("/userCoin/userDataForRecharge", "GET", {
    }, function (data) {
        rate = data.resultObject.rate;
        account = data.resultObject.account;
        balanceTotal = data.resultObject.balanceTotal;
        env = data.resultObject.env;
        $("#account").html(account);
        $(".balanceTotal").html(balanceTotal);
        
        $(".alipay").click();
        
    },false);
	console.log(1)
	// price=100/rate;//初始化为10元
	//应付金额
	var price = $("#money_item .orange").attr("data-value");
	$('.number').text(price);
	$('#main1').addClass('show')
	$('.mask').css({'display':'block'})
})


//点击变色效果
$('.content_two li').click(function(){
	$('.content_two li').removeClass('orange')
	$('.content_two li').find('.i-selector').removeClass('show')
	$(this).addClass('orange')
	$(this).find('.i-selector').addClass('show')
	//选择对应的充值数下面对应变化数值
    price = $(this).attr('data-value');
	$('.number').text(price) ;
	$('.erweima').animate({'display':'none'}).hide(100)
	
	
	var payment = "alipay";
	var alidisplay = $('.alipay-er').css('display')
	var wxdisplay = $('.weixin-er').css('display')
	if(alidisplay=="none" && wxdisplay=="block"){
		payment = "weixipay";
	}
	
	if(payment == "alipay"){

		alipayMadeCode();
	}else if(payment == "weixipay"){
        weixinpayMadeCode();
	}
})

/**
 * 阿里支付-- 生成二维码
 */
function alipayMadeCode(){
     var price = $("#money_item .orange").attr("data-value");
     RequestService("/web/alipay/rechargeQrCode/"+price+"/", "GET", {
	 }, function (data) {
		try{
			if(data.success){ 
				out_trade_no = data.resultObject.alipay_trade_precreate_response.out_trade_no;
    	 		qr_code = data.resultObject.alipay_trade_precreate_response.qr_code;
    	 		$("#qrcodeCanvas3").html("");
    	 		//$("#ali_qrcode").attr("src",qr_code);
				$("#qrcodeCanvas3").qrcode({
					render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
					text : qr_code,    //扫描了二维码后的内容显示,在这里也可以直接填一个网址，扫描二维码后
					width : "130",               //二维码的宽度
					height : "130",              //二维码的高度
					background : "#ffffff",       //二维码的后景色
					foreground : "#000000",        //二维码的前景色
					foreground : "#000000",
					src: '/web/images/yrx.png'             //二维码中间的图片
				});
				//执行先试试微信的
				executeLunxun();
    	 	}else{
    	 	  showTip("二维码生成失败");
    	 	}
		}catch(error){
		  showTip("二维码生成失败");
		}
	 })
}

/**
 *  微信支付生成二维码
 */
function weixinpayMadeCode(){
     var price = $("#money_item .orange").attr("data-value");
     RequestService("/web/wxPay/rechargeCode/"+price+"/", "GET", {
	 }, function (data) {
	 	console.log(data);
		try{
			if(data.success){ 
				out_trade_no = data.resultObject.orderNo;
    	 		qr_code = data.resultObject.codeimg;
    	 		$("#wx_qrcode").attr("src",qr_code);
    	 		//执行先试试微信的
				executeLunxun();
    	 	}else{
    	 		showTip("二维码生成失败");
    	 	}
		}catch(error){
			showTip("二维码生成失败");
		}
	 })
}

//关闭
$('.close').click(function(){
	closeCz();
	//清理轮询
	if(orderIsExit!=""){
		window.clearInterval(orderIsExit);
	}
})
function closeCz(){
	$('#main1').removeClass('show')
	$('#mask').hide();
	$('.mask').css({'display':'none'})
	$('.erweima').css({'display':'none'})
	$('.content_two li:first-child').click()
	$('.content_three li:first-child').click()
}

// 移动上去边框变色效果
$('.content_two li').mouseover(function(){
	$('.content_two li').css({'border-color':'#e3e5ea'})
	$(this).css({'border-color':'#F97B49'})
})

//移出复原
$('.content_two li').mouseout(function(){
	$(this).css({'border-color':'#e3e5ea'})
})



//微信支付宝移动边框变色
$('.content_three li').mouseover(function(){
	$('.content_three li').css({'border-color':'#e3e5ea'})
	$(this).css({'border-color':'#FF8A00'})
})
$('.content_three li').mouseout(function(){

	$(this).css({'border-color':'#e3e5ea'})
})


//点击变色效果
$('.content_three li').click(function(){
	$('.content_three li').removeClass('orange')
	$(this).addClass('orange')
	$('.content_three li').find('.i-selector').removeClass('show')
	$(this).find('.i-selector').addClass('show')
	$('.erweima').animate({'display':'none'}).hide(100)
})


//点及其他数量
$('.inTips').click(function(){
	$('#editPayNum').addClass('show');
	$('.baseNum').addClass('show');
	$(this).addClass('hide');
	$('.baseNum').focus();
	$('.inputTips').css({'visibility':'visible'})
})

//其他金额的input只能输入数字
$('#editPayNum').keyup(function(){
	$(this).val($(this).val().replace(/\D/g,''));
	if($(this).val()*1>20000000){
		$(this).val('20000000')
		var a =  $('#editPayNum').val();
		price=a/rate;
		$("#editPayNum").attr("data-value",a);
		$(".other").attr("data-value",a);
		$('.number').text(price) ;
	}
})

//点及其余的四个
//$('.content_two li:nth-child(-n+8)').click(function(){
////	$('.editPayNum').val('') 
//	$('.inputTips').css({'visibility':'hidden'})
//	$('.inTips').removeClass('hide')
//	$('#editPayNum').removeClass('show')
//	$('.baseNum').removeClass('show')
//})

//其他金额的li点击事件
/*$('.content_two li:nth-child(5)').click(function(){
	console.log()
	var a =  $('#editPayNum').val()
	$('.number').text(a) ;
	if(!a){
	$('.number').text(1)
	}
})*/



//其他数量数据同步
function showNum(){
	if(!$('#editPayNum').val()){
		$('.number').text('0') ;
	}
	var a =  $('#editPayNum').val();
	price=a/rate;
	$("#editPayNum").attr("data-value",a);
	$(".other").attr("data-value",a);
	$('.number').text(price) ;
}

//支付
$('.goPay').click(function(){
	if(price==""||price==null||price==0)return;
	// if(price*rate<100||price*rate>200000){
	// 	if(env == "prod"){
	// 		$('#wain').css({'display':'block'});
	// 		return;
	// 	}
	// }
	
//	window.open("/userCoin/recharge/pay?price="+price);
	location.href="/userCoin/recharge/pay?price="+price;
		setTimeout(function (){
			closeCz()
		},1000);
})


//支付成功执行得效果
function paySuccess(){
	$('.czym').addClass('hide');
	$('.pay-success').addClass('show')
}

//点击继续支付执行得效果
$('#pay-continue').click(function(){
	$('.czym').removeClass('hide');
	$('.pay-success').removeClass('show')
})


/**
 * 执行轮询查看是否充值
 */
function executeLunxun(){
	if(out_trade_no!=""){
	    orderIsExit = setInterval(function() {
			try {
				console.log("out_trade_no:"+out_trade_no);
				RequestService("/userCoin/checkRechargeOrder", "GET", {
			     orderNo: out_trade_no
				}, function (data) {
				    if (data.resultObject === true) { //支付成功
				    	showTip("充值成功");
				    	$('.close').click();
				    	//刷新熊猫币余额
				        refreshBalance();
				    } else {
				    	console.log("还没有去充值呢");
				    }
				}, false);
				
			} catch (error) {
				console.log(error);
			}
	 	}, 3000)
	}
}











