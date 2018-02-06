$(function() {
	$('.forum').css('color','#000');
	$('.path .hospital').addClass('select');
	
	//初始化页面和每页多少条数据
	window.currentPage = 1;
	window.size = 8;;
	var changeDocId;
	//点击左侧医师管理按钮进行对应数据请求
	$('#doc_Administration_tabBtn').click(function() {
		//		alert(1)
		//添加医师信息部分隐藏
		$('#doc_Administration #doc_Administration_bottom').addClass('hide');
		$('#doc_Administration #doc_Administration_bottom2').removeClass('hide');
		if($('.add_newTeacher').text() == '返回'){
			$('.add_newTeacher').click()
		}
//		RequestService("/medical/hospital/getDoctors", "get", {
//			current: currentPage,
//			size: size,
//		}, function(data) {
//			if(data.success == true){
				//调用分页部分
				courseVodList(1)
				//医馆列表渲染
//				$('#hosDocList').html(template('hosDocListTpl',{item:data.resultObject.records}))
//			}
//		})
	})
	
	
	
	//上线下线的按钮点击事件
$('#doc_Administration_bottom2').on('click','.downLine',function(){
	var id = $(this).attr('data-id');
	var status = $(this).attr('data-status');
	RequestService("/medical/doctor/update", "post", {
		id:id,
		status:status
	}, function(data) {
		if(data.success == true){
			//重新渲染列表
			$('.doc_Administration_tabBtn').click();
			 courseVodList(1);
		}
			
	});
})
	
	
	
	
	
	//	分页部分
	function courseVodList(current) {
		RequestService("/medical/hospital/getDoctors?size=8&current=" + current, "get", null, function(data) {
			$('#hosDocList').html(template('hosDocListTpl',{item:data.resultObject.records}))
//			debugger
			//每次请求完数据就去渲染分页部分
			if(data.resultObject.pages > 1) { //分页判断
				$(".not-data").remove();
				$(" .pages").removeClass("hide");
				$(" .pages .searchPage .allPage").text(data.resultObject.pages);
				$("#Pagination").pagination(data.resultObject.pages, {
					num_edge_entries: 1, //边缘页数
					num_display_entries: 4, //主体页数
					current_page: current - 1,
					callback: function(page) { //翻页功能
						courseVodList(page + 1);
						// alert(page);
					}
				});
			} else {
				$(".pages").addClass("hide");
			}
		});
	}
	
	//医师搜索功能
	$('.doc_Administration_top .search_teacher_btn').click(function(){

		var docName = $('.doc_Administration_top .search_teacher_ipt').val();
//		alert(docName)
		RequestService("/medical/hospital/getDoctors", "get", {
			current:1,
			size:8,
			doctorName: docName
		},function(data){
			$('#hosDocList').html(template('hosDocListTpl',{item:data.resultObject.records}))
		})
		
	})
	
	

	//医师预览功能
	$('.doc_Administration_bottom2').on('click','.preview',function() {
		changeDocId = $(this).attr('data-id');
		//获取预览的数据渲染
		RequestService("/medical/doctor/get", "get", {
			doctorId: changeDocId,
		}, function(data) {
//			console.log(data)
			$('#doc_Administration_bottom4').html(template('docStatusTpl',data.resultObject));
			$('#myinf').html(data.resultObject.description);
//			$('#myinf>p').style.fontSize = '16px';
			$('#mask').removeClass('hide');
		$('#doc_Administration_bottom3').addClass('hide');
		$('#doc_Administration_bottom4').removeClass('hide');

		})	
	})
	
	//下线功能
//	$('#doc_Administration_bottom2').on('click','.downLine',function(){
//		if($(this).attr('data-status') == true){
//			alert(111)
//		}
//		医师数据上传
//		RequestService("/medical/doctor/update", "post", {
//			id:
//			status:
//		}, function(data) {
//			console.log(data);
//
//		})
//	})
	

	//医师编辑功能
	$('#doc_Administration_bottom2').on('click','.edit',function() {
		changeDocId = $(this).attr('data-id');
		$('#mask').removeClass('hide');
		$('#doc_Administration_bottom4').addClass('hide');
		$('#doc_Administration_bottom3').removeClass('hide');
		$('#doc_Administration_bottom3').css('visibility','visible');
		//数据回显
		RequestService("/medical/doctor/get", "get", {
			doctorId: changeDocId,
		}, function(data) {
//			console.log(data);
//			$('#doc_Administration_bottom3 .' + imgname + '').html('<img src="' + data.resultObject + '" >');
			ediotrInf(data.resultObject.name,data.resultObject.medicalDoctorAuthenticationInformation.headPortrait,data.resultObject.title,
				data.resultObject.medicalDoctorAuthenticationInformation.titleProve,data.resultObject.departments,data.resultObject.fieldText,data.resultObject.description);
		})
		
	})
	
	//编辑的时候数据回显
	function ediotrInf(name,headPortrait,title,titleProve,departments,fieldText,description){
		//姓名
		$('#doc_Administration_bottom3 .doc_name').val(name);
		//医师头像
		var headPic = '<img src='+headPortrait+'>';
		$('#doc_Administration_bottom3 .touxiang_pic').html(headPic);
		//医师职称
		$('#doc_Administration_bottom3 .doc_zhicheng').val(title);
		//职称证明
		var zhichengStr = '<img src='+titleProve+'>';
		$('#doc_Administration_bottom3 .zhicheng_pic').html(zhichengStr)
		//科室不回显
		var j;
		for(var i =0 ;i < $('#shanChangList2 li').length ;i++){
			for(j = 0;j < departments.length ;j++ ){
				if($('#shanChangList2 li').eq(i).text() == departments[j].name){
					$('#shanChangList2 li').eq(i).addClass('keshiColor');
				}
			}
		}
		//擅长
		$('#doc_Administration_bottom3 .doc_shanchangIpt').val(fieldText);
		//医师介绍
		UE.getEditor('editor1').setContent(description);

	}


	//医师预览关闭按钮
	$('#doc_Administration_bottom4').on('click','.close_doc_inf',function() {
		$('#mask').addClass('hide');
		$('#doc_Administration_bottom4').addClass('hide');
	})

	//医师编辑关闭按钮
	$('#doc_Administration_bottom3 .close_doc_inf').click(function() {
		$('#mask').addClass('hide');
		$('#doc_Administration_bottom3').addClass('hide');
	})

	
	


	

	//上传图片调用的接口
	function picUpdown5(baseurl, imgname) {
		RequestService("/medical/common/upload", "post", {
			image: baseurl,
		}, function(data) {
//			console.log(data);
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
		var description = UE.getEditor('editor1').getContent();
		
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
		var description =  UE.getEditor('editor1').getContent();
		var field = $('#doc_Administration_bottom3 .doc_shanchangIpt').val();
		//医师数据上传
		RequestService("/medical/doctor/update", "post", {
			id:changeDocId,
			name: name,
			headPortrait: headPortrait,
			title: title,
			fieldText:field,
			titleProve: medicalDoctorAuthenticationInformation,
			description: description,
			fieldText: field,
			departmentIds:keshiStr
		}, function(data) {
			
			$('#tip').text('修改成功');
	       		$('#tip').toggle();
	       		setTimeout(function(){
	       			$('#tip').toggle();
	       		},2000)
	       	$('#doc_Administration_bottom3').addClass('hide');
	       	$('#mask').addClass('hide');
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
//		console.log(keshiStr)
	})

})