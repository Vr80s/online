$(function(){
	//医师认证信息前台验证
	$('#doc_Distinguish #AutList #submit').click(function(){
	//姓名
	var name = $.trim($('#AutList .doc_name').val());
	var name_pass = /^[\u4E00-\u9FA5]{1,6}$/;;
	var doc_Idnum = $.trim($('#AutList .doc_Idnum').val());
	var doc_Idnum_pass = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
	var personInt = UE.getEditor('editor').getContent();

		//姓名验证
		if(name == ''){
			$('#AutList .doc_name').siblings('.name_warn').removeClass('hide');
			$('#AutList .doc_name').siblings('.name_warn').text('姓名不能为空');
			$('#AutList .doc_name').addClass('borderColor');
			return false;
		}else if(!name_pass.test(name)){
			$('#AutList .doc_name').siblings('.name_warn').removeClass('hide');
			$('#AutList .doc_name').siblings('.name_warn').text('姓名格式不正确');
			$('#AutList .doc_name').addClass('borderColor');
			return false;
		}else {
			$('#AutList .doc_name').removeClass('borderColor');
			$('#AutList .doc_name').siblings('.name_warn').addClass('hide');
		}
	
		//身份证验证
		if(doc_Idnum == ''){
			$('#AutList .doc_Idnum').siblings('.idCard_warn').removeClass('hide');
			$('#AutList .doc_Idnum').siblings('.idCard_warn').text('身份证号不能为空');
			$('#AutList .doc_Idnum').addClass('borderColor');
			return false;
		}else if(!doc_Idnum_pass.test(doc_Idnum)){
			$('#AutList .doc_Idnum').siblings('.idCard_warn').removeClass('hide');
			$('#AutList .doc_Idnum').siblings('.idCard_warn').text('身份证号格式不正确');
			$('#AutList .doc_Idnum').addClass('borderColor');
			return false;
		}else{
			$('#AutList .doc_Idnum').removeClass('borderColor');
			$('#AutList .doc_Idnum').siblings('.idCard_warn').addClass('hide');
		}
		
		
		
		
		//判断身份证图片是否上传
		if($('#doc_Distinguish #AutList .idFont_pic:has(img)').length < 1 || $('#doc_Distinguish #AutList .idBack_pic:has(img)').length < 1 ){
			$('#doc_Distinguish #AutList .idCard_pic .warning ').removeClass('hide');
			return false;
		}else{
			$('#doc_Distinguish #AutList .idCard_pic .warning ').addClass('hide');
		}
		
		
		//教师资格证图片是否上传
		if($('#doc_Distinguish #AutList .teacher_pic:has(img)').length < 1){
			$('#doc_Distinguish #AutList .teacher_picUpdata .warning ').removeClass('hide');
			return false;
		}else{
			$('#doc_Distinguish #AutList .teacher_picUpdata .warning ').addClass('hide');
		}
		
		//执业资格证图片是否上传
		if($('#doc_Distinguish #AutList .zhiye_pic:has(img)').length < 1){
			$('#doc_Distinguish #AutList .zhiye_picUpdata .warning ').removeClass('hide');
			return false;
		}else{
			$('#doc_Distinguish #AutList .zhiye_picUpdata .warning ').addClass('hide');
		}
		
		//真实头像是否上传
		if($('#doc_Distinguish #AutList .touxiang_pic:has(img)').length < 1){
			$('#doc_Distinguish #AutList .touxiang_picUpdata .warning ').removeClass('hide');
			return false;
		}else{
			$('#doc_Distinguish #AutList .touxiang_picUpdata .warning ').addClass('hide');
		}
		
		//职称验证
		if($('#AutList .zhicheng_name input').val() == ''){
			$('#AutList .doc_zhicheng').addClass('borderColor');
			$('#AutList .zhicheng_name .warning').removeClass('hide');
			return false;
		}else{
			$('#AutList .doc_zhicheng').removeClass('borderColor');
			$('#AutList .zhicheng_name .warning').addClass('hide');
		}
		
		//职称证明是否上传
		if($('#doc_Distinguish #AutList .zhicheng_pic:has(img)').length < 1){
			$('#doc_Distinguish #AutList .zhicheng_picUpdata .warning ').removeClass('hide');
			return false;
		}else{
			$('#doc_Distinguish #AutList .zhicheng_picUpdata .warning ').addClass('hide');
		}
		
		
		//科室验证
		if($('#AutList .keshi .keshiColor').length == 0){
			$('#doc_Distinguish #AutList .keshi .warning ').removeClass('hide');
			return false;
		}else{
			$('#doc_Distinguish #AutList .keshi .warning ').addClass('hide');
		}
		
		
		//擅长
		if($('#AutList .shanchang input').val() == ''){
			$('#AutList .shanchang').addClass('borderColor');
			$('#AutList .shanchang .warning').removeClass('hide');
			return false;
		}else{
			$('#AutList .shanchang').removeClass('borderColor');
			$('#AutList .shanchang .warning').addClass('hide');
		}
		
		// 个人介绍
		if(personInt == ''){
			$('#AutList .personIntroduct .warning').removeClass('hide');
			return false;
		}else {
			$('#AutList .personIntroduct .warning').addClass('hide');
		}
		
		
		//城市判断
		if($('#AutList #choosePro option:selected').text()== '请选择所在省' ||$('#AutList #citys option:selected').text() == '请选择所在市'){
			$('#AutList .doc_address .warning').removeClass('hide');
			return false;
		}else{
			$('#AutList .doc_address .warning').addClass('hide');
		}
		
		
		
		//获取页面所有信息
		var name = $('#doc_Distinguish #AutList .doc_name').val();
		var cardNum = $('#doc_Distinguish #AutList .doc_Idnum').val();
		var cardPositive = $('#doc_Distinguish #AutList .idFont_pic img').attr('src');
		var cardNegative = $('#doc_Distinguish #AutList .idBack_pic img').attr('src');
		var qualificationCertificate =  $('#doc_Distinguish #AutList .teacher_pic img').attr('src');
		var professionalCertificate =  $('#doc_Distinguish #AutList .zhiye_pic img').attr('src');
		var headPortrait =  $('#doc_Distinguish #AutList .touxiang_pic img').attr('src');
		var title = $('#doc_Distinguish #AutList .doc_zhicheng').val();
		var titleProve = $('#doc_Distinguish #AutList .zhicheng_pic img').attr('src');
		var field = $('#doc_Distinguish #AutList .doc_shanchang').val();
		var description = UE.getEditor('editor').getContent();
		var province = $('#AutList #choosePro option:selected').text();
		var city = $('#AutList #citys option:selected').text();
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
				$('#tip').text('入驻申请信息提交失败！');
	       		$('#tip').toggle();
	       		setTimeout(function(){
	       			$('#tip').toggle();
	       		},1500)
			}else if(data.success == true){
				$('#tip').text('入驻申请信息提交成功！');
	       		$('#tip').toggle();
	       		setTimeout(function(){
	       			$('#tip').toggle();
	       		},1500)
	       		localStorage.AutStatus = 1;
	       		window.location.reload();
//	       		$('#doc_Distinguish #AutList').addClass('hide');
//	       		$('#doc_Distinguish #AutStatus').removeClass('hide');
	       		
			}

		})
	
	})
	

	
	//科室点击验证效果
	var arr = [];
	var keshiStr;
	$('#doc_Distinguish #AutList .keshi ').on('click','#keshiList>li',function(){
		if($(this).hasClass('keshiColor')){
		//删除第二次选中的
			for(var i = 0 ;i < arr.length; i++){
				if($(this).attr('data-id') == arr[i]){
					arr.splice(i,1)
				}
			}
//			console.log(arr.toString())
			keshiStr = arr.toString();
			$(this).removeClass('keshiColor');	
		}else{
			$(this).addClass('keshiColor');
			arr.push($(this).attr('data-id'));
//			console.log(arr.toString())
			keshiStr = arr.toString();
		}
		console.log(keshiStr)
	})
	

	
})
