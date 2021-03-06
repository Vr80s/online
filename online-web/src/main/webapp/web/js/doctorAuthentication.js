$(function(){
	
	$('.forum').css('color','#000');
	$('.path .doctor').addClass('select');

	//医师认证信息前台验证
	$('#doc_Distinguish #AutList #submit').click(function(){
	//姓名
	var name = $.trim($('#AutList .doc_name').val());
	var name_pass = /^[a-zA-Z\u4e00-\u9fa5]+$/;
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
			$('#AutList .doc_name').siblings('.name_warn').text('请填写真实姓名');
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
		/*if($('#doc_Distinguish #AutList .idFont_pic:has(img)').length < 1 || $('#doc_Distinguish #AutList .idBack_pic:has(img)').length < 1 ){
			$('#doc_Distinguish #AutList .idCard_pic .warning ').removeClass('hide');
			return false;
		}else{
			$('#doc_Distinguish #AutList .idCard_pic .warning ').addClass('hide');
		}*/
		
		
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
		if($('#AutList #choosePro option:selected').text()== '请选择省' ||$('#AutList #citys option:selected').text() == '请选择市'){
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
		RequestService("/doctor/apply", "post", {
				name:name,
				cardNum:cardNum,
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
				if(data.success == false){
					$('#tip').text(data.errorMessage);
					$('#tip').toggle();
					setTimeout(function(){
						$('#tip').toggle();
					},2000)
				}else if(data.success == true){
					$('#tip').text('保存成功');
					$('#tip').toggle();
					setTimeout(function(){
						$('#tip').toggle();
						if (localStorage.AccountStatus==1 || localStorage.AccountStatus==-1) {
							location.href="/doctors/my"
						} else{
							location.href="/doctors/authentication";
						}
						

					},2000)
				}
		})
	
	})
	
	//姓名失去焦点
	$(".doc_name").blur(function(){
		var reg=/^[a-zA-Z\u4e00-\u9fa5]+$/;
		if($.trim($(".doc_name").val())== ""){
			$(".name_warn").removeClass("hide");
			$('#AutList .doc_name').siblings('.name_warn').text('姓名不能为空');
			$('#AutList .doc_name').addClass('borderColor');
			return false;
		}else if(!reg.test($(".doc_name").val())){
			$('#AutList .doc_name').siblings('.name_warn').removeClass('hide');
			$('#AutList .doc_name').siblings('.name_warn').text('请填写真实姓名');
			$('#AutList .doc_name').addClass('borderColor');
			return false;
		}else{
			$(".name_warn").addClass("hide");
			$('#AutList .doc_name').removeClass('borderColor');
		};
	});
//	身份证号失去焦点
	$(".doc_Idnum").blur(function(){
		var doc_Idnum_pass = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
		if($.trim($(".doc_Idnum").val()) == ""){
			$(".idCard_warn").removeClass("hide");
			$('#AutList .doc_Idnum').siblings('.idCard_warn').text('身份证号不能为空');
			$('#AutList .doc_Idnum').addClass('borderColor');
		}else if(!doc_Idnum_pass.test($.trim($('#AutList .doc_Idnum').val()))){
			$('#AutList .doc_Idnum').siblings('.idCard_warn').removeClass('hide');
			$('#AutList .doc_Idnum').siblings('.idCard_warn').text('身份证号格式不正确');
			$('#AutList .doc_Idnum').addClass('borderColor');
		}else{
			$(".idCard_warn").addClass("hide");
			$('#AutList .doc_Idnum').removeClass('borderColor');
		};
	});
	
//	职称失去焦点
	$(".doc_zhicheng").blur(function(){
		if($.trim($(".doc_zhicheng").val()) == ""){
			$(".doc_zhicheng_null").removeClass("hide");
			$('#AutList .doc_zhicheng').addClass('borderColor');
		}else{
			$(".doc_zhicheng_null").addClass("hide");
			$('#AutList .doc_zhicheng').removeClass('borderColor');
		};
	});
	
//	擅长失去焦点	
	$(".doc_shanchang").blur(function(){
		if($.trim($(".doc_shanchang").val())== ""){
			$(".doc_shanchang_null").removeClass("hide");
			$('#AutList .doc_shanchang').addClass('borderColor');
		}else{
			$(".doc_shanchang_null").addClass("hide");
			$('#AutList .doc_shanchang').removeClass('borderColor');
		};
	});
	
	
	
//  个人介绍  view   p
	/*$(".view p").blur(function(){
		alert(132);
		var personInt = UE.getEditor('editor').getContent();
		
		if(personInt == ''){
			$('#AutList .personIntroduct .warning').removeClass('hide');
			return false;
		}else {
			$('#AutList .personIntroduct .warning').addClass('hide');
		}
	});*/

	
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
