var price;
var rate;
var env;
//var rate=10;//一元兑换10熊猫币
$('.chongZhi').click(function(){
    RequestService("/userCoin/userDataForRecharge", "GET", {
    }, function (data) {
        rate = data.resultObject.rate;
        account = data.resultObject.account;
        balanceTotal = data.resultObject.balanceTotal;
        env = data.resultObject.env;

        $("#account").html(account);
        $(".balanceTotal").html(balanceTotal);
    },false);
	console.log(1)
	price=100/rate;//初始化为10元
	$('.number').text(price);
	$('#main1').addClass('show')
	$('.mask').css({'display':'block'})
})
//关闭
$('.close').click(function(){
	closeCz();
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
	$(this).css({'border-color':'#FF8A00'})
})

//移出复原
$('.content_two li').mouseout(function(){
	$(this).css({'border-color':'#e3e5ea'})
})

//点击变色效果
$('.content_two li').click(function(){
	$('.content_two li').removeClass('orange')
	$('.content_two li').find('.i-selector').removeClass('show')
	$(this).addClass('orange')
	$(this).find('.i-selector').addClass('show')
	//选择对应的充值数下面对应变化数值
	var a = $(this).attr('data-value');
	price=a/rate;
	$('.number').text(price) ;
	$('.erweima').animate({'display':'none'}).hide(100)
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
$('.content_two li:nth-child(-n+8)').click(function(){
//	$('.editPayNum').val('') 
	$('.inputTips').css({'visibility':'hidden'})
	$('.inTips').removeClass('hide')
	$('#editPayNum').removeClass('show')
	$('.baseNum').removeClass('show')
})

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
	if(price*rate<100||price*rate>200000){
		if(env == "prod"){
			$('#wain').css({'display':'block'});
			return;
		}
	}
	
	window.open("/userCoin/recharge/pay?price="+price);
		setTimeout(function (){
			closeCz()
		},1000
		);
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



