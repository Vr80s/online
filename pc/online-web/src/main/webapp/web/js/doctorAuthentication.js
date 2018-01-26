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
		}else if(!name_pass.test(name)){
			$('.doc_name').siblings('.name_warn').removeClass('hide');
			$('.doc_name').siblings('.name_warn').text('姓名格式不正确');
			return false;
		}else {
			$('.doc_name').siblings('.name_warn').addClass('hide');
		}
	
		//身份证验证
		if(doc_Idnum == ''){
			$('.doc_Idnum').siblings('.idCard_warn').removeClass('hide');
			$('.doc_Idnum').siblings('.idCard_warn').text('身份证号不能为空');
			return false;
		}else if(!doc_Idnum_pass.test(doc_Idnum)){
			$('.doc_Idnum').siblings('.idCard_warn').removeClass('hide');
			$('.doc_Idnum').siblings('.idCard_warn').text('身份证号格式不正确');
			return false;
		}else{
			$('.doc_Idnum').siblings('.idCard_warn').addClass('hide');
		}
		
		
		
		
		//判断身份证图片是否上传
		if($('#doc_Distinguish .idFont_pic:has(img)').length < 1 || $('#doc_Distinguish .idBack_pic:has(img)').length < 1 ){
			$('#doc_Distinguish .idCard_pic .warning ').removeClass('hide');
			return false;
		}else{
			$('#doc_Distinguish .idCard_pic .warning ').addClass('hide');
		}
		
		
		//教师资格证图片是否上传
		if($('#doc_Distinguish .teacher_pic:has(img)').length < 1){
			$('#doc_Distinguish .teacher_picUpdata .warning ').removeClass('hide');
			return false;
		}else{
			$('#doc_Distinguish .teacher_picUpdata .warning ').addClass('hide');
		}
		
		//执业资格证图片是否上传
		if($('#doc_Distinguish .zhiye_pic:has(img)').length < 1){
			$('#doc_Distinguish .zhiye_picUpdata .warning ').removeClass('hide');
			return false;
		}else{
			$('#doc_Distinguish .zhiye_picUpdata .warning ').addClass('hide');
		}
		
		//真实头像是否上传
		if($('#doc_Distinguish .touxiang_pic:has(img)').length < 1){
			$('#doc_Distinguish .touxiang_picUpdata .warning ').removeClass('hide');
			return false;
		}else{
			$('#doc_Distinguish .touxiang_picUpdata .warning ').addClass('hide');
		}
		
		//职称验证
		if($('.zhicheng_name input').val() == ''){
			$('.zhicheng_name .warning').removeClass('hide');
			return false;
		}else{
			$('.zhicheng_name .warning').addClass('hide');
		}
		
		//职称证明是否上传
		if($('#doc_Distinguish .zhicheng_pic:has(img)').length < 1){
			$('#doc_Distinguish .zhicheng_picUpdata .warning ').removeClass('hide');
			return false;
		}else{
			$('#doc_Distinguish .zhicheng_picUpdata .warning ').addClass('hide');
		}
		
		
		//科室验证
		if($('.keshi .keshiColor').length == 0){
			$('#doc_Distinguish .keshi .warning ').removeClass('hide');
			return false;
		}else{
			$('#doc_Distinguish .keshi .warning ').addClass('hide');
		}
		
		
		//擅长
		if($('.shanchang input').val() == ''){
			$('.shanchang .warning').removeClass('hide');
			return false;
		}else{
			$('.shanchang .warning').addClass('hide');
		}
		
		// 个人介绍
		if($('.personIntroduct textarea').val() == ''){
			$('.personIntroduct .warning').removeClass('hide');
			return false;
		}else {
			$('.personIntroduct .warning').addClass('hide');
		}
		
		
		//任职医馆
		if($('.work_hos input').val() == ''){
			$('.work_hos .warning').removeClass('hide');
			return false;
		}else{
			$('.work_hos .warning').addClass('hide');
		}
		
		
		//获取页面所有信息
		var name = $('#doc_Distinguish .doc_name').val();
		var cardNum = $('#doc_Distinguish .doc_Idnum').val();
		var cardPositive = $('#doc_Distinguish .idFont_pic img').attr('src');
		var cardNegative = $('#doc_Distinguish .idBack_pic img').attr('src');
		var qualificationCertificate =  $('#doc_Distinguish .teacher_pic img').attr('src');
		var professionalCertificate =  $('#doc_Distinguish .zhiye_pic img').attr('src');
		var headPortrait =  $('#doc_Distinguish .touxiang_pic img').attr('src');
		var title = $('#doc_Distinguish .doc_zhicheng').val();
		var titleProve = $('#doc_Distinguish .zhicheng_pic img').attr('src');
		var field = $('#doc_Distinguish .doc_shanchang').val();
		var description = $('#doc_Distinguish .personIntroduct textarea').val();
		var province = $('#choosePro option:selected').text();
		var city = $('#citys option:selected').text();
		//发送认证请求
		RequestService("/medical/doctor/apply", "post", {
				name:name,
				cardNum:cardNum,
				cardPositive:cardPositive,
				cardNegative:cardNegative,
				qualificationCertificate:qualificationCertificate,
				professionalCertificate:professionalCertificate,
				headPortrait:headPortrait,
				title:title,
				titleProve:titleProve,
				departments:keshiStr,
				field:field,
				description:description,
				province:province,
				city:city
			}, function(data) {
				console.log(data);
			if(data.success == false){
				alert('认证失败');
			}else if(data.success == true){
				alert('认证成功');
			}

		})
	
	})
	

	
	//科室点击验证效果
	var arr = [];
	var keshiStr;
	$('#doc_Distinguish .keshi ').on('click','#keshiList>li',function(){
		if($(this).hasClass('keshiColor')){
		//删除第二次选中的
			for(var i = 0 ;i < arr.length; i++){
				if($(this).text() == arr[i]){
					arr.splice(i,1)
				}
			}
//			console.log(arr.toString())
			keshiStr = arr.toString();
			$(this).removeClass('keshiColor');	
		}else{
			$(this).addClass('keshiColor');
			arr.push($(this).text());
//			console.log(arr.toString())
			keshiStr = arr.toString();
		}
//		console.log(keshiStr)
	})
	

	
})
