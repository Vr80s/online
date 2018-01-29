//此处是医馆入住点击提交信息时候进行验证的部分
$(function(){
	//点击医馆信息提交
	
	
	$('#hos_Administration .hos_base_inf #submit').click(function(){
		var hosName = $.trim($('.hos_base_inf .doc_zhicheng').val());
		var hosIntroduct = $.trim($('.hos_base_inf .personIntroduct textarea').val());
		var name = $.trim($('.hos_base_inf .doc_shanchang').val());
		var name_pass = /^[\u4E00-\u9FA5]{1,6}$/;
		var WeChat =  $.trim($('.hos_base_inf .hos_weixin').val());
		var WeChatPatt = /^[a-zA-Z\d_]{5,}$/;
		var email = $.trim($('.hos_base_inf .doc_hospital').val());
		var emailPatt = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/ ;
		
		//医馆头像判断
		if($('.hos_base_inf   .touxiang_pic:has(img)').length < 1){
			$('.hos_base_inf .touxiang_picUpdata .warning ').removeClass('hide');
			return false;
		}else{
			$('.hos_base_inf .touxiang_picUpdata .warning ').addClass('hide');
		}
		
		
		//医馆名称的校验
		if(hosName == ''){
			$('.hos_base_inf .doc_zhicheng').siblings('.hosName_warn').removeClass('hide');
			$('.hos_base_inf .doc_zhicheng').siblings('.hosName_warn').text('医馆名称不能为空');
			return false;
		}else{
			$('.hos_base_inf .doc_zhicheng').siblings('.hosName_warn').addClass('hide');
		}
		
		//医馆图片判断
		if($('.hos_base_inf  .zhicheng_pic:has(img)').length < 1){
			$('.hos_base_inf .zhicheng_picUpdata .warning ').removeClass('hide');
			return false;
		}else{
			$('.hos_base_inf .zhicheng_picUpdata .warning ').addClass('hide');
		}
		
		
		// 医疗领域判断
		if($('.hos_base_inf .keshi .keshiColor').length == 0){
			$('.hos_base_inf .keshi .warning ').removeClass('hide');
			return false;
		}else{
			$('.hos_base_inf .keshi .warning ').addClass('hide');
		}
		
		
		//医馆介绍判断
		if($('.hos_base_inf .personIntroduct textarea').val() == ''){
			$('.hos_base_inf .personIntroduct .warning').removeClass('hide');
			return false;
		}else {
			$('.hos_base_inf .personIntroduct .warning').addClass('hide');
		}
		
		
		//联系人姓名判断
		if(name == ''){
			$('.contant_name_warn').removeClass('hide');
			$('.contant_name_warn').text('姓名不能为空');
			return false;
		}else if(!name_pass.test(name)){
			$('.contant_name_warn').removeClass('hide');
			$('.contant_name_warn').text('姓名格式不正确');
			return false;
		}else {
			$('.contant_name_warn').addClass('hide');
		}
		
		//邮箱判断
		if(email !== '' && !emailPatt.test(email)){
			$('.hos_base_inf .email_warn').removeClass('hide');
			return false;
		}else{
			$('.hos_base_inf .email_warn').addClass('hide');
		}
		
		//微信
		if(WeChat !== '' && !WeChatPatt.test(WeChat)){
			$('.hos_base_inf .WeChat_warn').removeClass('hide');
			return false;
		}else{
			$('.hos_base_inf .WeChat_warn').addClass('hide');
		}
		
		
		
		//通过验证进行医馆基础信息的数据上传
		//发送认证请求
//		RequestService("/medical/doctor/apply", "post", {
//				
//			}, function(data) {
//				console.log(data);
//			if(data.success == false){
//				alert('认证失败');
//			}else if(data.success == true){
//				alert('认证成功');
//			}
//
//		})
	})
	
	
	
	//医馆管理里头的医馆认证信息提交
	$('.hos_renzheng_inf .bottomContent #submit').click(function(){
//		alert(222);
		var company = $.trim($('.hos_renzheng_inf .bottomContent .doc_name').val());
		var zhizhaoNum = $.trim($('.hos_renzheng_inf .bottomContent .doc_Idnum').val());
		var xukeNum = $.trim($('.hos_renzheng_inf .bottomContent .doc_zhicheng').val());
		var Number = /^[0-9]*$/;
		var hosName = $.trim($('.hos_renzheng_inf .bottomContent .hos_name').val());
		
		//医馆名称验证
		if(hosName == ''){
			$('.hos_renzheng_inf .bottomContent .hosName_warn').removeClass('hide');
			return false;
		}else{
			$('.hos_renzheng_inf .bottomContent .hosName_warn').addClass('hide');
		}
		
		
		//公司验证
		if(company == ''){
			$('.hos_renzheng_inf .bottomContent .company_warn').removeClass('hide');
			return false;
		}else{
			$('.hos_renzheng_inf .bottomContent .company_warn').addClass('hide');
		}
		
		
		//营业执照号
		if(zhizhaoNum == ''){
			$('.hos_renzheng_inf .bottomContent .zhizhaoNum_warn').text('营业执照号不能为空');
			$('.hos_renzheng_inf .bottomContent .zhizhaoNum_warn').removeClass('hide');
			return false;
		}else if(!Number.test(zhizhaoNum)){
			$('.hos_renzheng_inf .bottomContent .zhizhaoNum_warn').text('营业执照号格式错误');
			$('.hos_renzheng_inf .bottomContent .zhizhaoNum_warn').removeClass('hide');
			return false;
		}else{
			$('.hos_renzheng_inf .bottomContent .zhizhaoNum_warn').addClass('hide');
		}
		
		
		//营业执照照片验证
		if($('.hos_renzheng_inf .bottomContent .teacher_pic:has(img)').length < 1){
			$('.hos_renzheng_inf .bottomContent .zhizhaoPic_warn ').removeClass('hide');
			return false;
		}else{
			$('.hos_renzheng_inf .bottomContent .zhizhaoPic_warn ').addClass('hide');
		}
		
		
		//药品经营许可证号验证
		if(xukeNum == ''){
			$('.hos_renzheng_inf .bottomContent .xukeNum_warn').text('营业执照号不能为空');
			$('.hos_renzheng_inf .bottomContent .xukeNum_warn').removeClass('hide');
			return false;
		}else if(!Number.test(xukeNum)){
			$('.hos_renzheng_inf .bottomContent .xukeNum_warn').text('营业执照号格式错误');
			$('.hos_renzheng_inf .bottomContent .xukeNum_warn').removeClass('hide');
		}else{
			$('.hos_renzheng_inf .bottomContent .xukeNum_warn').addClass('hide');
		}
		
		
		//药品经营许照片验证
		if($('.hos_renzheng_inf .bottomContent .zhicheng_pic:has(img)').length < 1){
			$('.hos_renzheng_inf .bottomContent .xukePic_warn ').removeClass('hide');
			return false;
		}else{
			$('.hos_renzheng_inf .bottomContent .xukePic_warn ').addClass('hide');
		}
		
		
		alert('提交成功了')
		var name = $('.hos_renzheng_inf .bottomContent .hos_name').val();
		var company = $('.hos_renzheng_inf .bottomContent .doc_name').val();
		var businessLicenseNo = $('.hos_renzheng_inf .bottomContent .doc_Idnum').val();
		var businessLicensePicture =  $('.hos_renzheng_inf .bottomContent .teacher_pic img').attr('src');
		var licenseForPharmaceuticalTrading = $('.hos_renzheng_inf .bottomContent .doc_zhicheng').val();
		var licenseForPharmaceuticalTradingPicture = $('.hos_renzheng_inf .bottomContent .zhicheng_pic img').attr('src');
		
		//提交医馆认证数据
		RequestService("/medical/hospital/apply", "post", {
				name:name,
				company:company,
				businessLicenseNo:businessLicenseNo,
				businessLicensePicture:businessLicensePicture,
				licenseForPharmaceuticalTrading:licenseForPharmaceuticalTrading,
				licenseForPharmaceuticalTradingPicture:licenseForPharmaceuticalTradingPicture
			}, function(data) {
				console.log(data);
			if(data.success == false){
				alert('认证失败');
			}else if(data.success == true){
				alert('认证成功');
			}

		})
		
	})
	
	
	
	//医馆信息的回显数据渲染
	RequestService("/medical/hospital/apply/getLastOne", "get", {			
			}, function(data) {
				console.log(data);
			if(data.success == false){
				alert('获取认证状态数据失败');
			}else if(data.success == true){
//				alert('认证成功');
				//医馆数据渲染
				$('#hosAutStatus').html(template('hosAutStatusTpl',data.resultObject))
			}

		})
	
	//上传图片调用的接口
	function picUpdown(baseurl,imgname){
	RequestService("/medical/common/upload", "post", {
				image: baseurl,
			}, function(data) {
				console.log(data);
				 $('#doc_Administration_bottom .'+imgname+'').html('<img src="'+data.resultObject+'" >');
			})

}
	
	
	
	
	//医馆管理图片上传部分
	//  医师真实头像
	$('#touxiang_pic_ipt').on('change',function(){
		var reader=new FileReader();
	  	reader.onload=function(e){
		picUpdown(reader.result,'touxiang_pic');
		}  
		reader.readAsDataURL(this.files[0])
	})
		
	//   职称证明
	$('#zhicheng_pic_ipt').on('change',function(){
		var reader=new FileReader();
	  	reader.onload=function(e){
		picUpdown(reader.result,'zhicheng_pic');
		}  
		reader.readAsDataURL(this.files[0])
	})
	
	
})





