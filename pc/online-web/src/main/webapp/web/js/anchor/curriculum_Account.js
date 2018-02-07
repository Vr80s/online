$(function(){
	
	//判断账户身份显示效果
	if(localStorage.AccountStatus == 1){
		//主播是医师的身份
		//医馆的消失
		$('.begin_approve .clinic_two').addClass('hide');
		$('.account_main .account_two').addClass('hide');
		
		//医师的显示
		$('.begin_approve .physician_one').removeClass('hide');
		$('.account_main .physician_two').removeClass('hide');
	
	}else if(localStorage.AccountStatus == 2){
		//主播是医馆的身份
		
		//医师的消失
		$('.begin_approve .physician_one').addClass('hide');
		$('.account_main .physician_two').addClass('hide');
		
		//医馆的显示
		$('.begin_approve .clinic_two').removeClass('hide');
		$('.account_main .account_two').removeClass('hide');
		
	}
	
	
	
	//主播是医师的时候点击提交按钮
	
	$('.physician_two .approve').click(function(){
		var NameWarnings = $.trim($('.physician_two .name_put0').val());
		var NameCard = $.trim($('.physician_two .name_put1').val());
		
		var name = $.trim($('.physician_two .name_put0').val());
		var name_pass = /^[\u4E00-\u9FA5]{1,6}$/;;
		var doc_Idnum = $.trim($('#AutList .doc_Idnum').val());
		var doc_Idnum_pass = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
		var personInt = UE.getEditor('editor').getContent();
		
		//姓名为空
		if(NameWarnings == ''){
			$('.physician_two .two_warning0').removeClass('hide');
			return false;
		}else{
			$('.physician_two .two_warning0').addClass('hide');
		}
		
		//身份证号为空
		if(NameCard == ''){
			$('.physician_two .two_warning1').removeClass('hide');
			return false;
		}else{
			$('.physician_two .two_warning1').addClass('hide');
		}
		
	})

	
	
	
	
	
})
