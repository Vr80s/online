$(function() {
	//初始化页面和每页多少条数据
	window.currentPage = 1;
	window.size = 8;;

	//点击左侧医师管理按钮进行对应数据请求
	$('#doc_Administration_tabBtn').click(function() {
		//		alert(1)
		//添加医师信息部分隐藏
		$('#doc_Administration #doc_Administration_bottom').addClass('hide');
		RequestService("/medical/hospital/getDoctors", "get", {
			currentPage: currentPage,
			size: size,
		}, function(data) {
			console.log(data)
		})
	})

	//医师预览功能
	$('.doc_Administration_bottom2 .preview').click(function() {
		$('#mask').removeClass('hide');
		$('#doc_Administration_bottom3').addClass('hide');
		$('#doc_Administration_bottom4').removeClass('hide');
	})

	//医师编辑功能
	$('#doc_Administration_bottom2 .edit').click(function() {
		$('#mask').removeClass('hide');
		$('#doc_Administration_bottom4').addClass('hide');
		$('#doc_Administration_bottom3').removeClass('hide');
	})

	//医师编辑关闭按钮
	$('#doc_Administration_bottom4 .close_doc_inf').click(function() {
		$('#mask').addClass('hide');
		$('#doc_Administration_bottom4').addClass('hide');
	})

	//医师预览关闭按钮
	$('#doc_Administration_bottom3 .close_doc_inf').click(function() {
		$('#mask').addClass('hide');
		$('#doc_Administration_bottom3').addClass('hide');
	})

	
	


	

	//上传图片调用的接口
	function picUpdown5(baseurl, imgname) {
		RequestService("/medical/common/upload", "post", {
			image: baseurl,
		}, function(data) {
			console.log(data);
			$('#doc_Administration_bottom3 .' + imgname + '').html('<img src="' + data.resultObject + '" >');
		})

	}

	//医馆管理图片上传部分
	//  医师真实头像
	$('#touxiang_pic_ipt').on('change', function() {
		var reader = new FileReader();
		reader.onload = function(e) {
			picUpdown5(reader.result, 'touxiang_pic');
		}
		reader.readAsDataURL(this.files[0])
	})

	//   职称证明
	$('#zhicheng_pic_ipt').on('change', function() {
		var reader = new FileReader();
		reader.onload = function(e) {
			picUpdown5(reader.result, 'zhicheng_pic');
		}
		reader.readAsDataURL(this.files[0])
	})

	//医师数据修改保存功能
	$('#doc_Administration_bottom3 #submit').click(function() {

		//获取数据
		var name = $.trim($('#doc_Administration_bottom3 .doc_name').val());
		var name_pass = /^[\u4E00-\u9FA5]{1,6}$/;;
		var doc_zhicheng = $.trim($('#doc_Administration_bottom3 .doc_zhicheng').val());
		var doc_Idnum_pass = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
//		var description = $('#doc_Administration_bottom3 .doc_introduct').val();
		var field = $('#doc_Administration_bottom3 .doc_shanchangIpt').val();
		var description = UE.getEditor('editor').getContent();
		
		//姓名验证
		if(name == '') {
			$('#doc_Administration_bottom3 .doc_name').siblings('.name_warn').removeClass('hide');
			$('#doc_Administration_bottom3 .doc_name').siblings('.name_warn').text('姓名不能为空');
			return false;
		} else if(!name_pass.test(name)) {
			$('#doc_Administration_bottom3 .doc_name').siblings('.name_warn').removeClass('hide');
			$('#doc_Administration_bottom3 .doc_name').siblings('.name_warn').text('姓名格式不正确');
			return false;
		} else {
			$('#doc_Administration_bottom3 .doc_name').siblings('.name_warn').addClass('hide');
		}

		//医师真实头像是否上传
		if($('#doc_Administration_bottom3  .touxiang_pic:has(img)').length < 1) {
			$('#doc_Administration_bottom3  .touxiang_picUpdata .warning ').removeClass('hide');
			return false;
		} else {
			$('#doc_Administration_bottom3  .touxiang_picUpdata .warning ').addClass('hide');
		}

		//职称验证
		if(doc_zhicheng == '') {
			$('#doc_Administration_bottom3 .zhicheng_name .warning').removeClass('hide');
			return false;
		} else {
			$('#doc_Administration_bottom3 .zhicheng_name .warning').addClass('hide');
		}

		//职称证明是否上传
		if($('#doc_Administration_bottom3  .zhicheng_pic:has(img)').length < 1) {
			$('#doc_Administration_bottom3  .zhicheng_picUpdata .warning ').removeClass('hide');
			return false;
		} else {
			$('#doc_Administration_bottom3  .zhicheng_picUpdata .warning ').addClass('hide');
		}

		//科室验证
		if($('#doc_Administration_bottom3 .keshi .keshiColor').length == 0) {
			$('#doc_Administration_bottom3  .keshi .warning ').removeClass('hide');
			return false;
		} else {
			$('#doc_Administration_bottom3  .keshi .warning ').addClass('hide');
		}

		//擅长验证
		if(field == '') {
			$('#doc_Administration_bottom3 .shanchang_name .warning').removeClass('hide');
			return false;
		} else {
			$('#doc_Administration_bottom3 .shanchang_name .warning').addClass('hide');
		}

		// 医师介绍
		if(description == '') {
			$('#doc_Administration_bottom3 .personIntroduct .warning').removeClass('hide');
			return false;
		} else {
			$('#doc_Administration_bottom3 .personIntroduct .warning').addClass('hide');
		}

		//通过了以上的所有验证之后重新获取所有的数据上传
		var name = $.trim($('#doc_Administration_bottom3 .doc_name').val());
		var headPortrait = $('#doc_Administration_bottom3  .touxiang_pic img').attr('src');
		var title = $.trim($('#doc_Administration_bottom3 .doc_zhicheng').val());
		var medicalDoctorAuthenticationInformation = $('#doc_Administration_bottom3  .zhicheng_pic img').attr('src');
		var description =  UE.getEditor('editor').getContent();
		var field = $('#doc_Administration_bottom3 .doc_shanchangIpt').val();
		//医师数据上传
		RequestService("/medical/hospital/addDoctor", "post", {
			name: name,
			headPortrait: headPortrait,
			title: title,
			medicalDoctorAuthenticationInformation: medicalDoctorAuthenticationInformation,
			description: description,
			fieldText: field,
			"departments[0].id": '0f4df242c3294902a87b8bc0a0ffe4d8'
			//				departments:keshiStr
		}, function(data) {
			console.log(data);

		})

	})

	// 可是我点击验证效果
	var arr = [];
	var keshiStr;
	$('#doc_Administration_bottom3  .keshi ').on('click', '#shanChangList2>li', function() {
		if($(this).hasClass('keshiColor')) {
			//删除第二次选中的
			for(var i = 0; i < arr.length; i++) {
				if($(this).attr('data-id') == arr[i]) {
					arr.splice(i, 1)
				}
			}
			//			console.log(arr.toString())
			keshiStr = arr.toString();
			$(this).removeClass('keshiColor');
		} else {
			$(this).addClass('keshiColor');
			arr.push($(this).attr('data-id'));
			//			console.log(arr.toString())
			keshiStr = arr.toString();
		}
		console.log(keshiStr)
	})

})