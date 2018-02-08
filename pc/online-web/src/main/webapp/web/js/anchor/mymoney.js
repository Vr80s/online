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
	
	
	
	//结算按钮点击出现结算结构
	$('#mymoney .Settlement').click(function(){
		showContent('content_Settlement',$(this).text())
	})
	
	
	
	
	//提现按钮点击出发事件
	$('#mymoney .toCash').click(function(){
		showContent('content_toCash',$(this).text())
	})
	
	
	//管理按钮点击触发事件
	$('#mymoney .administration').click(function(){
		showContent('content_Administration',$(this).text())
	})
	
	
	
	//新增银行卡
	$('#mymoney .addNew').click(function(){
		showContent('content_add',$(this).text())
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
});