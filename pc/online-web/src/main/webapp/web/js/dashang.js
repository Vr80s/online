var selected=0;
$(function(){
	//移入
//	$('.rss-pay').mouseover(function(){
//		console.log(1)
//		$('.rss-reward-down').addClass('show')
//		$('.rss-reward-down').stop().animate({marginTop:'10px'},400,function(){
//				$('.rss-reward-down').stop().animate({marginTop:'-10px'},400)
//		})
//	})
	//移出
//	$('.rss-pay').mouseout(function(){
//		$('.rss-reward-down').removeClass('show')
//	})

	//点击
	$('.rss-pay').click(function(){
		initReward();
		$('.mask').addClass('show')
		$('.popup').addClass('show')
	})

	//关闭
	$('.popup-close').click(function(){
		rewardClose();
	})

	//按钮变色效果
	$('#reward-fee li').click(function(){
		$('#reward-fee li').removeClass('on')
		$(this).addClass('on');
		if($('.jsCheck').val()=='金额不能为空'||$('.jsCheck').val()=='金额格式不正确'){
			$('.jsCheck').val('')
			$('.jsCheck').removeClass('wain')
		}
	})
	$("#alipay-div").click(function(){
		selected=0;
	});
	$("#weixinpay-div").click(function(){
		selected=1;
	});


	//支付方式切换效果
	$('.reward-type').click(function(){
		$('.reward-type div').removeClass('jsChecked')
		$(this).find('div').addClass('jsChecked')
//		$(this).prev().children().eq(0).prop("checked",true);
	})

	var payFlag=true;
	var isFreeDom=false;
	//点击支付微信二维码出现
	$('.popup-btn-yes').click(function(){
		if($(".l0").hasClass("on")||$(".l1").hasClass("on")||$(".l2").hasClass("on")){
			isFreeDom = false;
		}else{
			isFreeDom = true;
		}
		
		//金额不能为空
		if(isFreeDom && $('.jsCheck').parent().hasClass('on')&& ($('.jsCheck').val() == '' )){
//			$('.jsCheck').val('金额不能为空' ) 
			$('.jsCheck').val('金额不能为空')
			$('.jsCheck').addClass('wain')
			return false;
		}
		if(isFreeDom && (!/^([1-9]\d{0,9}|0)([.]?|(\.\d{1,2})?)$/.test($('.jsCheck').val())||($('.jsCheck').val()*1)<=0)){
			payFlag = false;
			$('.jsCheck').val('金额格式不正确' )
			$('.jsCheck').addClass('wain')
			return false;
		}
		var rewardId;
		var price;
		if(isFreeDom){
			price=$(".jsCheck").val();
			rewardId=$(".jsCheck").attr("data-id");
		}else{
			price=$(".on").attr("data-val");
			rewardId=$(".on").attr("data-id");
		}
		// 微信支付
		if(selected==1){
			RequestService("/web/wxPay/reward", "GET", {
				rewardId:rewardId,
				actualPay:price,
				receiver:teacherId,
				liveId:course_id,
		    }, function (data) {
		        console.info(data.resultObject);
		        $('#wxpay-qrcode').animate({'display':'block'}).fadeIn(500)
		        $('#qrcode').attr('src', data.resultObject);
		    });
		}else{
		   //支付宝支付
			window.open("/web/alipay/reward?rewardId="+rewardId+"&price="+price+"&receiver="+teacherId+"&liveId="+course_id);
		}
		
	})
	RequestService("/reward/getReward", "GET", {
    }, function (data) {
        if(data.success==true){
            createRewardList(data.resultObject);
        }else{
            console.info();
        }
    });

	function createRewardList(rewardList){
		var list=[];
		for(var i in rewardList){
			if(rewardList[i].isFreeDom==false){
				list.push(rewardList[i]);
				console.info(list);
			}else if(rewardList[i].isFreeDom==true){
				$(".jsCheck").attr("data-id",rewardList[i].id);
				$(".custom-fee").show();
			}
		}
		for(var i in list){
			$(".l"+i).attr("data-val",list[i].price);
			$(".l"+i).attr("data-id",list[i].id);
			$(".l"+i).html(list[i].price+"元");
			$(".l"+i).show();
		}
	}
	
	function initReward(){
		payFlag=true;
		isFreeDom=false;
		$('.jsCheck').val('')
		$('.jsCheck').removeClass('wain');
		$('#reward-fee li').removeClass('on');
		$(".l0").addClass('on');
	}
	
	
//	$('.popup-btn-yes').click(function(){
//		console.log(888)
//	})
})

function rewardClose(){
	$('.mask').removeClass('show')
	$('.popup').removeClass('show')
	$('#wxpay-qrcode').css({'display':'none'})
	$('.reward-type div').removeClass('jsChecked')
	$('.reward-type-zhifubao > div').addClass('jsChecked')
	selected = 0;
	$('.jsCheck').val('');
	$('.jsCheck').removeClass('wain')
}
