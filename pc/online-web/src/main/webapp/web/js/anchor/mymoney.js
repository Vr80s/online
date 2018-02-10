$(function(){

	
	
	//底部固定表单部分点击人民币熊猫币切换效果
	//点击人民币按钮
	$('#mymoney .rmbMoney ').click(function(){
		//变色
		$('#mymoney .pandaMoney').removeClass('orange')
		$(this).addClass('orange');
		
		//变表单
		$('#mymoney .pandaTable').addClass('hide');
		$('#mymoney .rmbTable').removeClass('hide');
		
	})
	
	
	//点击熊猫币按钮
	$('#mymoney .pandaMoney').click(function(){
		//变色
		$('#mymoney .rmbMoney').removeClass('orange')
		$(this).addClass('orange');
		
		//变表单
		//变表单
		$('#mymoney .rmbTable').addClass('hide');
		$('#mymoney .pandaTable').removeClass('hide');
	})
	
	
	
	
	
	//顶部的几个绿色按钮点击事件
	//结算按钮点击出现结算结构
	$('#mymoney .Settlement').click(function(){
		//表单重置
		content_SettlementReset();
		//显示对应表单内容
		showContent('content_Settlement',$(this).text());
		
	})
	
	//结算中的确定按钮点击
	$('#mymoney .content_Settlement .content_Settlement_btn').click(function(){
		var toResultNum = $.trim($('.content_Settlement #toResultIpt').val());
		//验证
		if(toResultNum == ''){
			$('.toResultIpt_warn').removeClass('hide');
			return false;
		}else{
			$('.toResultIpt_warn').addClass('hide');
		}
	})
	
	
	
	
	
	

	//提现按钮点击出发事件
	$('#mymoney .toCash').click(function(){
		showContent('content_toCash',$(this).text())
	})
	
	//提现中的确定按钮点击
	$('#mymoney .content_toCash .toCashSure_btn').click(function(){
		var toCashNum = $.trim($('.content_toCash #tpCashIpt').val());
		var userName = $.trim($('.content_toCash #userNameIpt').val());
		var phoneNum = $.trim($('.content_toCash #phonePwdIpt').val());
		
		//验证
		//提现金额
		if(toCashNum == ''){
			$('.tpCashIpt_warn').removeClass('hide');
			return false;
		}else{
			$('.tpCashIpt_warn').addClass('hide');
		}
		
		//户名
		if(userName == ''){
			$('.userNameIpt_warn').removeClass('hide');
			return false;
		}else{
			$('.userNameIpt_warn').addClass('hide');
		}
		
		//手机验证码
		if(phoneNum == ''){
			$('.phonePwdIpt_warn').removeClass('hide');
			return false;
		}else{
			$('.phonePwdIpt_warn').addClass('hide');
		}
		
	})
	
	
	
	
	
	//管理按钮点击触发事件
	$('#mymoney .administration').click(function(){
		showContent('content_Administration',$(this).text())
	})
	
	
	
	
	
	//新增银行卡
	$('#mymoney .addNew').click(function(){
		showContent('content_add',$(this).text())
	})
	
	
	//新增银行卡中的确定按钮点击
	$('#mymoney .content_add .addNewCard').click(function(){
		var content_add_name = $.trim($('.content_add #content_add_name').val());
		var content_add_card = $.trim($('.content_add #content_add_card').val());
		var content_add_bank = $.trim($('.content_add #content_add_bank').val());
		var content_add_idCard =  $.trim($('.content_add #content_add_idCard').val());
		
		
		//验证
		//户名
		if(content_add_name == ''){
			$('.content_add_name_warn').removeClass('hide');
			return false;
		}else{
			$('.content_add_name_warn').addClass('hide');
		}
		
		//卡号
		if(content_add_card == ''){
			$('.content_add_card_warn').removeClass('hide');
			return false;
		}else{
			$('.content_add_card_warn').addClass('hide');
		}
		
		//选择银行
		if(content_add_bank == '-1' ){
			$('.content_add_bank_warn').removeClass('hide');
			return false;
		}else{
			$('.content_add_bank_warn').addClass('hide');
		}
		
		//身份证号
		if(content_add_idCard == ''){
			$('.content_add_idCard_warn').removeClass('hide');
			return false;
		}else{
			$('.content_add_idCard_warn').addClass('hide');
		}
		
	})
	
	
	/*@点击顶部的按钮对应的中间变动结构显示出来
	 *@输入参数1为对应显示部分的模块类名
	 *@输入参数2为点击的按钮的名字
	 * */
	function showContent(contentName,btnName){
		$('#toCashTip').addClass('hide')
		//判断隐藏部分的头部标题名字
		var contentTopName;
		if(btnName == '管理'){
			contentTopName = '银行卡管理'
		}else if(btnName == '新增'){
			contentTopName = '新增银行卡'
		}else if(btnName == '提现'){
			contentTopName = "提现"
			$('#toCashTip').removeClass('hide')
		}else{
			contentTopName = btnName
		}
		$('#mymoney .content_mid > div').addClass('hide');
		$('#mymoney .content_mid').removeClass('hide');
		$('.content_mid .content_mid_top .title').text(contentTopName);
		$(".content_mid ."+contentName+"").removeClass('hide');
	}
	
	
	//结算的重置部分
	function content_SettlementReset(){
		$('.content_Settlement #toResultIpt').val('');
		$('.toResultIpt_warn').addClass('hide');
	}
	
	
	
	
	
	
	
	
	var count = 1;
	//我的收益部分的js 开始
	//点击排行榜切换部分
	$('.toRankingList').click(function(){
		count *= -1;
		if(count == -1){
			$(this).text('返回');
			$(this).siblings('.title').text('排行榜');
			//底部列表的变化
			$('.gift_Resive_mid').addClass('hide');
			$('.gift_Resive_bottom').addClass('hide');
			$('.gift_Resive_bottom2').removeClass('hide');
		}else{
			$(this).text('排行榜');
			$(this).siblings('.title').text('礼物订单');
			//底部列表的变化
			$('.gift_Resive_bottom2').addClass('hide');
			$('.gift_Resive_mid').removeClass('hide');
			$('.gift_Resive_bottom').removeClass('hide');
		}
		
	})
	
});