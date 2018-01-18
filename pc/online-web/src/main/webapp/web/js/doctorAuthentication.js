$(function(){
	//医师认证信息前台验证
	$('#doc_Distinguish #submit').click(function(){
	//姓名
	var name = $.trim($('.doc_name').val());
	var name_pass = /^[\u4E00-\u9FA5]{1,6}$/;;
	var doc_Idnum = $.trim($('.doc_Idnum').val());
	var doc_Idnum_pass = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
		//姓名验证
		if(name == ''){
			$('.doc_name').siblings('.name_warn').removeClass('hide');
			$('.doc_name').siblings('.name_warn').text('姓名不能为空');
			return false;
		}
		if(!name_pass.test(name)){
			$('.doc_name').siblings('.name_warn').removeClass('hide');
			$('.doc_name').siblings('.name_warn').text('姓名格式不正确');
			return false;
		}
	
		//身份证验证
		if(doc_Idnum == ''){
			$('.doc_Idnum').siblings('.idCard_warn').removeClass('hide');
			$('.doc_Idnum').siblings('.idCard_warn').text('身份证号不能为空');
			return false;
		}
		
		if(!doc_Idnum_pass.test(doc_Idnum)){
			$('.doc_Idnum').siblings('.idCard_warn').removeClass('hide');
			$('.doc_Idnum').siblings('.idCard_warn').text('身份证号格式不正确');
			return false;
		}
	
	})
	
	//科室点击验证效果
	$('.keshi ul li').click(function(){
		if($(this).hasClass('keshiColor')){
			$(this).removeClass('keshiColor')
		}else{
			$(this).addClass('keshiColor');
		}
	})
})
