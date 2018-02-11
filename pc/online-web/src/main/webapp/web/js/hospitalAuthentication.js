//此处是医馆入住点击提交信息时候进行验证的部分
$(function(){
	
	
//	$('#doc_Administration #doc_Administration_bottom').addClass('hide');
	//点击医馆信息提交

	
	
	$('#hos_Administration .hos_base_inf #submit').click(function(){

		var hosName = $.trim($('.hos_base_inf .doc_zhicheng').val());
		var hosIntroduct = $.trim($('.hos_base_inf .personIntroduct textarea').val());
		var name = $.trim($('.hos_base_inf .doc_shanchang').val());
		var name_pass = /^[\u4E00-\u9FA5]{1,6}$/;
		var WeChat =  $.trim($('.hos_base_inf .hos_weixin').val());
		var WeChatPatt = /^[a-zA-Z\d_-]{5,}$/;
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
		
			
		//城市判断
		if($('#hos_Administration .hos_base_inf #choosePro  option:selected').text()== '请选择省' ||$('#hos_Administration .hos_base_inf  #citys option:selected').text() == '请选择市'){
			$('#hos_Administration .hos_base_inf .doc_address .warning').removeClass('hide');
			return false;
		}else{
			$('#hos_Administration .hos_base_inf .doc_address .warning').addClass('hide');
		}
		
		
		
		
		
		var data={};
		data.hosName = $.trim($('.hos_base_inf .doc_zhicheng').val());
		data.hosIntroduct = $.trim($('.hos_base_inf .personIntroduct textarea').val());
		data.contactor = $.trim($('.hos_base_inf .doc_shanchang').val());
		data.WeChat =  $.trim($('.hos_base_inf .hos_weixin').val());
		data.email = $.trim($('.hos_base_inf .doc_hospital').val());
		data.province = $('#hos_Administration .hos_base_inf #choosePro  option:selected').text();
		data.city = $('#hos_Administration .hos_base_inf  #citys option:selected').text();
		data.wechat =  $('#hos_Administration .hos_base_inf  .hos_weixin').val();
		data.headPortrait = $('.hos_base_inf   .touxiang_pic img').attr('src');
        data.description = UE.getEditor('editor2').getContent();
        data.detailAddress = $.trim($('#hos_Administration .hos_base_inf .doc_address textarea').val());
		var hoslist = [];
		for(var i = 0;i < $('#hos_pic img').length ; i++){
			    hoslist.push($('#hos_pic img').eq(i).attr('src'));
		}
		var fieldIds = [];
		for(var i=0;i<$('.hos_base_inf .keshi .keshiColor').length;i++){
            fieldIds.push($('.hos_base_inf .keshi .keshiColor').eq(i).attr("data-id"))
		}
		data.pictures = hoslist;
		data.fieldIds = fieldIds;
		//通过验证进行医馆基础信息的数据上传
		//发送认证请求
        $.ajax({
            type: "post",
            url: "/medical/hospital/update",
            data:JSON.stringify(data),
            contentType:"application/json",
            async: false,
            success: function(data) {
                if(data.success == false){
                    $('#tip').text('保存失败，请重试');
                    $('#tip').toggle();
                    setTimeout(function(){
                        $('#tip').toggle();
                    },2000)
                }else if(data.success == true){
                    $('#tip').text('保存成功');
                    $('#tip').toggle();
                    setTimeout(function(){
                        $('#tip').toggle();
                        window.scrollTo(0,0);
                    },2000)
                    
                }
            }
        });
	})
	
	
	
	//医馆管理里头的医馆认证信息提交
	$('.hos_renzheng_inf .bottomContent #submit').click(function(){
//		alert(222);
		var company = $.trim($('.hos_renzheng_inf .bottomContent .doc_name').val());
		var zhizhaoNum = $.trim($('.hos_renzheng_inf .bottomContent .doc_Idnum').val());
		var xukeNum = $.trim($('.hos_renzheng_inf .bottomContent .doc_zhicheng').val());
		var Number = /^[0-9A-Z]{18}$/;
		var hosName = $.trim($('.hos_renzheng_inf .bottomContent .hos_name').val());
		var xukeNumPass = /^[\u4E00-\u9FA5]{1}[A-Za-z]{2}[0-9]{7}$/;
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
			$('.hos_renzheng_inf .bottomContent .zhizhaoNum_warn').text('统一社会信用代码不能为空');
			$('.hos_renzheng_inf .bottomContent .zhizhaoNum_warn').removeClass('hide');
			return false;
		}else if(!Number.test(zhizhaoNum)){
			$('.hos_renzheng_inf .bottomContent .zhizhaoNum_warn').text('统一社会信用代码格式错误');
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
			$('.hos_renzheng_inf .bottomContent .xukeNum_warn').text('药品经营许可证号不能为空');
			$('.hos_renzheng_inf .bottomContent .xukeNum_warn').removeClass('hide');
			return false;
		}
		else if(!xukeNumPass.test(xukeNum)){
			$('.hos_renzheng_inf .bottomContent .xukeNum_warn').text('药品经营许可证号格式错误');
			$('.hos_renzheng_inf .bottomContent .xukeNum_warn').removeClass('hide');
			return false;
		}
		else{
			$('.hos_renzheng_inf .bottomContent .xukeNum_warn').addClass('hide');
		}
		
		
		//药品经营许照片验证
		if($('.hos_renzheng_inf .bottomContent .zhicheng_pic:has(img)').length < 1){
			$('.hos_renzheng_inf .bottomContent .xukePic_warn ').removeClass('hide');
			return false;
		}else{
			$('.hos_renzheng_inf .bottomContent .xukePic_warn ').addClass('hide');
		}
		
		

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
				$('#tip').text(data.errorMessage);
	       		$('#tip').toggle();
	       		setTimeout(function(){
	       			$('#tip').toggle();
	       		},2000)
//	       		window.location.reload();
			}else if(data.success == true){
				$('#tip').text('保存成功');
	       		$('#tip').toggle();
	       		setTimeout(function(){
	       			$('#tip').toggle();
	       		},2000)
	       		window.location.reload();
			}

		})
		
	})
	
	//添加医师里头的医疗领域部分数据获取   改成了科室
		RequestService("/medical/doctor/apply/listDepartment/0", "get", {			
			}, function(data) {
				console.log(data);
			if(data.success == false){
				alert('获取认证状态数据失败');
			}else if(data.success == true){
//				alert('认证成功');
				//医馆数据渲染
				$('#shanChangList').html(template('shanChangTpl',{item:data.resultObject.records}))
				$('#shanChangList2').html(template('shanChangTpl2',{item:data.resultObject.records}))
			}

		})
	
	
	
	//医馆信息的回显数据渲染
	if(localStorage.AccountStatus == 2){
		RequestService("/medical/hospital/authentication/get", "get", {			
			}, function(data) {
				console.log(data);
			if(data.success == false){
//				alert('获取认证状态数据失败');
//				$('#tip').text('获取认证状态数据失败');
//	       		$('#tip').toggle();
//	       		setTimeout(function(){
//	       			$('#tip').toggle();
//	       		},2000)
			}else if(data.success == true){
//				alert('认证成功');
				//医馆数据渲染
				$('#hosAutStatus').html(template('hosAutStatusTpl',data.resultObject))
			}

		})
	}else{
			RequestService("/medical/hospital/apply/getLastOne", "get", {			
			}, function(data) {
				console.log(data);
			if(data.success == false){
//				alert('获取认证状态数据失败');
//				$('#tip').text('获取认证状态数据失败');
//	       		$('#tip').toggle();
//	       		setTimeout(function(){
//	       			$('#tip').toggle();
//	       		},2000)
			}else if(data.success == true){
//				alert('认证成功');
				//医馆数据渲染
				$('#hosAutStatus').html(template('hosAutStatusTpl',data.resultObject))
			}

		})
	}

	
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
	
	
	
	
	//医师管理保存功能
	$('#doc_Administration_bottom #submit').click(function(){
		

	//获取数据
	var name = $.trim($('#doc_Administration_bottom .doc_name').val());
	var name_pass = /^[\u4E00-\u9FA5]{1,6}$/;;
	var doc_zhicheng = $.trim($('#doc_Administration_bottom .doc_zhicheng').val());
	var doc_Idnum_pass = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
	var description = UE.getEditor('editor').getContent();
	var field = $('#doc_Administration_bottom .doc_shanchangIpt').val();
	
	
	//姓名验证
		if(name == ''){
			$('#doc_Administration_bottom .doc_name').siblings('.name_warn').removeClass('hide');
			$('#doc_Administration_bottom .doc_name').siblings('.name_warn').text('姓名不能为空');
			return false;
		}else if(!name_pass.test(name)){
			$('#doc_Administration_bottom .doc_name').siblings('.name_warn').removeClass('hide');
			$('#doc_Administration_bottom .doc_name').siblings('.name_warn').text('姓名格式不正确');
			return false;
		}else {
			$('#doc_Administration_bottom .doc_name').siblings('.name_warn').addClass('hide');
		}
	
	
	//医师真实头像是否上传
	if($('#doc_Administration_bottom  .touxiang_pic:has(img)').length < 1){
		$('#doc_Administration_bottom  .touxiang_picUpdata .warning ').removeClass('hide');
		return false;
	}else{
		$('#doc_Administration_bottom  .touxiang_picUpdata .warning ').addClass('hide');
	}
	
	
	
	//职称验证
		if(doc_zhicheng == ''){
			$('#doc_Administration_bottom .zhicheng_name .warning').removeClass('hide');
			return false;
		}else{
			$('#doc_Administration_bottom .zhicheng_name .warning').addClass('hide');
		}
	
	
	
	//职称证明是否上传
	if($('#doc_Administration_bottom  .zhicheng_pic:has(img)').length < 1){
		$('#doc_Administration_bottom  .zhicheng_picUpdata .warning ').removeClass('hide');
		return false;
	}else{
		$('#doc_Administration_bottom  .zhicheng_picUpdata .warning ').addClass('hide');
	}
	
	
	//科室验证
	if($('#doc_Administration_bottom .keshi .keshiColor').length == 0){
			$('#doc_Administration_bottom  .keshi .warning ').removeClass('hide');
			return false;
		}else{
			$('#doc_Administration_bottom  .keshi .warning ').addClass('hide');
		}
		
	//擅长验证
	if(field == ''){
			$('#doc_Administration_bottom .shanchang_name .warning').removeClass('hide');
			return false;
		}else {
			$('#doc_Administration_bottom .shanchang_name .warning').addClass('hide');
		}
		
	
	// 个人介绍
		if(description == ''){
			$('#doc_Administration_bottom .personIntroduct .warning').removeClass('hide');
			return false;
		}else {
			$('#doc_Administration_bottom .personIntroduct .warning').addClass('hide');
		}
	
	
	//通过了以上的所有验证之后重新获取所有的数据上传
	var name = $.trim($('#doc_Administration_bottom .doc_name').val());
	var headPortrait = $('#doc_Administration_bottom  .touxiang_pic img').attr('src');
	var title = $.trim($('#doc_Administration_bottom .doc_zhicheng').val());
	var medicalDoctorAuthenticationInformation = $('#doc_Administration_bottom  .zhicheng_pic img').attr('src');
	var description = UE.getEditor('editor').getContent();
	var field = $('#doc_Administration_bottom .doc_shanchangIpt').val();
	//医师数据上传
	RequestService("/medical/doctor/add", "post", {
				name: name,
				headPortrait:headPortrait,
				title:title,
				titleProve:medicalDoctorAuthenticationInformation,
				description:description,
				fieldText:field,
				departmentIds:keshiStr
			}, function(data) {
				if(data.success == true){
				$('#tip').text('保存成功');
	       		$('#tip').toggle();
	       		setTimeout(function(){
	       			$('#tip').toggle();
	       			$('#doc_Administration_tabBtn').click();
	       		},2000)
				}
			})
	})
	
	
	
	
	
	
	
	// 擅长领域点击验证效果
	var arr = [];
	var keshiStr;
	$('#doc_Administration_bottom  .keshi ').on('click','#shanChangList>li',function(){
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





