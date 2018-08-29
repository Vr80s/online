$(function() {
//	$('.forum').css('color','#000');
//	$('.path .hospital').addClass('select');
	
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
		$('.search_teacher_ipt').val('');
		$('.search_teacher_btn').click()
//		RequestService("/hospital/getDoctors", "get", {
//			current: currentPage,
//			size: size,
//		}, function(data) {
//			if(data.success == true){
				//调用分页部分
				doctorList(1)
				//医馆列表渲染
//				$('#hosDocList').html(template('hosDocListTpl',{item:data.resultObject.records}))
//			}
//		})
	})
	
	
	
	//上线下线的按钮点击事件
$('#doc_Administration_bottom2').on('click','.downLine',function(){
	var id = $(this).attr('data-id');
	var status = $(this).attr('data-status');
	RequestService("/doctor/update", "post", {
		id:id,
		status:status
	}, function(data) {
		if(data.success == true){
			//重新渲染列表
			$('.doc_Administration_tabBtn').click();
			 doctorList(1);
			 showTip("操作成功");
		}
			
	});
})
	
	
	//删除按钮点击事件
$('#doc_Administration_bottom2').on('click','.delete',function(){
	delid = $(this).attr('data-id');
	//显示出来隐藏的弹窗
	$('#mask').removeClass('hide');
	$('#deleteTip').removeClass('hide');
	
})


//隐藏删除提示框
$('#deleteTip #cancalDel').click(function(){
	$('#deleteTip').addClass('hide');
	$('#mask').addClass('hide');
})
	


var delid;
//点击确认删除
$('#deleteTip .confirm-sure').click(function(){
	RequestService("/hospital/deleteDoctor", "post", {
		doctorId:delid,
	}, function(data) {
		if(data.success == true){
			//重新渲染列表
			$('.doc_Administration_tabBtn').click();
			 doctorList(1);
			 $('#deleteTip').addClass('hide');
			$('#mask').addClass('hide');
		}
			
	});
	
	
})
	
	
	
	
	
	
	//	分页部分
	function doctorList(current){
		RequestService("/hospital/getDoctors?size=8&current=" + current, "get", null, function(data) {
			$('#hosDocList').html(template('hosDocListTpl',{item:data.resultObject.records}))
//
			//每次请求完数据就去渲染分页部分
			if(data.resultObject.pages > 1) { //分页判断
				$(".not-data").remove();
	            $(".doctors_pages").removeClass("hide");
	            $(".doctors_pages .searchPage .allPage").text(data.resultObject.pages);
	            $("#Pagination_doctors").pagination(data.resultObject.pages, {
	                num_edge_entries: 1, //边缘页数
	                num_display_entries: 4, //主体页数
	                current_page:current-1,
	                callback: function (page) {
	                    //翻页功能
	                    doctorList(page+1);
	                }
	            });
			} else {
				$(".doctors_pages").addClass("hide");
			}
		});
	}
	
	//医师搜索功能
	$('.doc_Administration_top .search_teacher_btn').click(function(){

		var docName = $('.doc_Administration_top .search_teacher_ipt').val();
//		alert(docName)
		RequestService("/hospital/getDoctors", "get", {
			current:1,
			size:8,
			doctorName: docName
		},function(data){
			$('#hosDocList').html(template('hosDocListTpl',{item:data.resultObject.records}))
			if(!data.resultObject || !data.resultObject.records || data.resultObject.records.length == 0){
//				$('#doc_Administration_bottom2').html('<div style="padding-top:100px;text-align:center"><img src="/web/images/nosearch.png" alt="" /><p style="font-size:16px;color:#999;margin-top:35px">抱歉，没有找到“<span style="color:#00BC12">'+docName+'</span>”相关医师</p></div>');
				$('#doc_Administration_bottom2 > table').addClass('hide')
				$('#searchName').text(docName);
				$('#noDocList').removeClass('hide');
			}else{
				$('#noDocList').addClass('hide');
				$('#doc_Administration_bottom2 > table').removeClass('hide')
			}
		})
		
	})
	
	

	//医师预览功能
	$('.doc_Administration_bottom2').on('click','.preview',function() {
		changeDocId = $(this).attr('data-id');
		//获取预览的数据渲染
		RequestService("/doctor/get", "get", {
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
//		RequestService("/doctor/update", "post", {
//			id:
//			status:
//		}, function(data) {
//			console.log(data);
//
//		})
//	})
	

//	//医师编辑功能
//	$('#doc_Administration_bottom2').on('click','.edit',function() {
//		changeDocId = $(this).attr('data-id');
//		$('#mask').removeClass('hide');
//		$('#doc_Administration_bottom4').addClass('hide');
//		$('#doc_Administration_bottom3').removeClass('hide');
//		$('#doc_Administration_bottom3').css('visibility','visible');
//		//数据回显
//		RequestService("/doctor/get", "get", {
//			doctorId: changeDocId,
//		}, function(data) {
////			console.log(data);
////			$('#doc_Administration_bottom3 .' + imgname + '').html('<img src="' + data.resultObject + '" >');
//			ediotrInf(data.resultObject.name,data.resultObject.medicalDoctorAuthenticationInformation.headPortrait,data.resultObject.title,
//				data.resultObject.medicalDoctorAuthenticationInformation.titleProve,data.resultObject.departments,data.resultObject.fieldText,data.resultObject.description);
//		})
//		
//	})

	//医师编辑功能
	$('#doc_Administration_bottom2').on('click','.edit',function() {
		reset();
		changeDocId = $(this).attr('data-id');
			$('#doc_Administration_bottom2').addClass('hide');
			$('#doc_Administration_bottom').removeClass('hide');
			$(".doc_Administration_top .title").text('编辑医师');
			$(".doc_Administration_top .add_newTeacher").text("返回");
//		搜索部分隐藏
			$('.search_teacher_ipt').addClass('hide');
			$('.search_teacher_btn').addClass('hide');
//		按钮显示
		$(".new-doctorbtn").addClass("hide");
		$(".wrap-save").removeClass("hide");
		//数据回显
		RequestService("/doctor/get", "get", {
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
		$('#doc_Administration_bottom .doc_name_manage').val(name);
		//医师头像
		var headPic = '<img src='+headPortrait+'?imageMogr2/thumbnail/!120x120r|imageMogr2/gravity/Center/crop/120x120>';
		$('#doc_Administration_bottom .touxiang_pic').html(headPic);
		//医师职称
		$('#doc_Administration_bottom .doc_zhicheng').val(title);
		//职称证明
		var zhichengStr = '<img src='+titleProve+'?imageMogr2/thumbnail/!260x147r|imageMogr2/gravity/Center/crop/260x147>'+'<p id="picture-tip-zhichen">点击图片重新上传</p>';
		$('#doc_Administration_bottom .zhicheng_pic').html(zhichengStr)
		//科室不回显
		var j;
		for(var i =0 ;i < $('#shanChangList li').length ;i++){
			for(j = 0;j < departments.length; j++ ){
				if($('#shanChangList li').eq(i).text() == departments[j].name){
					$('#shanChangList li').eq(i).addClass('keshiColor');
				}
			}
		}
		//擅长
		$('#doc_Administration_bottom .doc_shanchangIpt').val(fieldText);
		//医师介绍
		UE.getEditor('editor').setContent(description);

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
	function picUpdown5(form, imgname) {
        $.ajax({
            type: 'post',
            url: "/medical/common/upload",
            data: form,
            cache: false,
            processData: false,
            contentType: false,
        }).success(function (data) {
            $('#doc_Administration_bottom3 .' + imgname + '').html('<img src="' + data.resultObject + '" >');
        });

	}

	//医馆管理图片上传部分
	//  医师真实头像
	$('#touxiang_pic_ipt').on('change', function() {
        var form = new FormData();
        form.append("image", this.files[0]);
		var reader = new FileReader();
		reader.onload = function(e) {
			picUpdown5(form, 'touxiang_pic');
		}
		reader.readAsDataURL(this.files[0])
	})

	//   职称证明
	$('#zhicheng_pic_ipt').on('change', function() {
        var form = new FormData();
        form.append("image", this.files[0]);
		var reader = new FileReader();
		reader.onload = function(e) {
			picUpdown5(form, 'zhicheng_pic');
		}
		reader.readAsDataURL(this.files[0])
	})

	//医师数据修改保存功能
	$('#doctor-save').click(function() {

		//获取数据
		var name = $.trim($('#doc_Administration_bottom .doc_name_manage').val());
		var name_pass = /^[a-zA-Z\u4e00-\u9fa5]+$/;
		var doc_zhicheng = $.trim($('#doc_Administration_bottom .doc_zhicheng').val());
		var doc_Idnum_pass = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
//		var description = $('#doc_Administration_bottom .doc_introduct').val();
		var field = $('#doc_Administration_bottom .doc_shanchangIpt').val();
		var description = UE.getEditor('editor').getContent();
		$("#doc_Administration_bottom .warning").addClass("hide");
        $("#doc_Administration_bottom input").removeClass("border_hide_null");
		//姓名验证
		 if (name == '') {
            	$('#doc_Administration_bottom .name_warn').removeClass('hide');
            	$('#doc_Administration_bottom .name_warn').text('姓名不能为空');
            	$('#doc_Administration_bottom .name_warn').siblings('input').addClass("border_hide_null");
            return false;
        } else if (!name_pass.test(name)) {
             	$('#doc_Administration_bottom .name_warn').removeClass('hide');
	            $('#doc_Administration_bottom .name_warn').text('请填写真实姓名');
	            $('#doc_Administration_bottom .name_warn').siblings('input').addClass("border_hide_null");
            return false;
        }

		//医师真实头像是否上传
		if($('#doc_Administration_bottom  .touxiang_pic:has(img)').length < 1) {
			$('#doc_Administration_bottom  .touxiang_picUpdata .warning ').removeClass('hide');
			return false;
		} else {
			$('#doc_Administration_bottom  .touxiang_picUpdata .warning ').addClass('hide');
		}

		//职称验证
		if (doc_zhicheng == '') {
            $('#doc_Administration_bottom .zhicheng_name_error').removeClass('hide');
           	$('#doc_Administration_bottom .zhicheng_name_error').siblings('input').addClass("border_hide_null");			
           return false;
        }

		//职称证明是否上传
		if($('#doc_Administration_bottom  .zhicheng_pic:has(img)').length < 1) {
			$('#doc_Administration_bottom  .zhicheng_picUpdata .warning ').removeClass('hide');
			return false;
		} else {
			$('#doc_Administration_bottom  .zhicheng_picUpdata .warning ').addClass('hide');
		}

		//科室验证
		if($('#doc_Administration_bottom .keshi .keshiColor').length == 0) {
			$('#doc_Administration_bottom  .keshi .warning ').removeClass('hide');
			return false;
		} else {
			$('#doc_Administration_bottom  .keshi .warning ').addClass('hide');
		}

		//擅长验证
		if(field == '') {
			$('#doc_Administration_bottom .shanchang_name .warning').removeClass('hide');
			return false;
		} else {
			$('#doc_Administration_bottom .shanchang_name .warning').addClass('hide');
		}

		// 医师介绍
		if(description == '') {
			$('#doc_Administration_bottom .personIntroduct .warning').removeClass('hide');
			return false;
		} else {
			$('#doc_Administration_bottom .personIntroduct .warning').addClass('hide');
		}

		//通过了以上的所有验证之后重新获取所有的数据上传
		var name = $.trim($('#doc_Administration_bottom .doc_name_manage').val());
		var headPortrait = $('#doc_Administration_bottom  .touxiang_pic img').attr('src').split("?")[0];
		var title = $.trim($('#doc_Administration_bottom .doc_zhicheng').val());
		var medicalDoctorAuthenticationInformation = $('#doc_Administration_bottom .zhicheng_pic img').attr('src').split("?")[0];
		var description =  UE.getEditor('editor').getContent();
		var field = $('#doc_Administration_bottom .doc_shanchangIpt').val();
		//医师数据上传
		RequestService("/doctor/update", "post", {
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
	       			$('#doc_Administration_tabBtn').click();
	       		},2000)
		})

	})
	
//	医馆名称失焦
	$(".hos_name").blur(function(){
		if($(".hos_name").val() == ""){
			$(".hosName_warn").removeClass("hide");
			$(".hos_name").addClass("border_hide_null");
		}else{
			$(".hosName_warn").addClass("hide");
			$(".hos_name").removeClass("border_hide_null");
		};
	});
	
//	所属公司失焦
	$(".doc_name").blur(function(){
		if($(".doc_name").val() == ""){
			$(".company_warn").removeClass("hide");
			$(".doc_name").addClass("border_hide_null");
		}else{
			$(".company_warn").addClass("hide");
			$(".doc_name").removeClass("border_hide_null");
		};
	});
	
//	统一社会信用代码失焦
	$(".doc_Idnum").blur(function(){
		if($(".doc_Idnum").val() == ""){
			$(".zhizhaoNum_warn").removeClass("hide");
			$(".doc_Idnum").addClass("border_hide_null");
		}else{
			$(".zhizhaoNum_warn").addClass("hide");
			$(".doc_Idnum").removeClass("border_hide_null");
		};
	});
	
//	药品经营许可证失焦
//	$(".doc_zhichengs").blur(function(){
//		if($(".doc_zhichengs").val() == ""){
//			$(".xukeNum_warn").removeClass("hide");
//			$(".doc_zhichengs").addClass("border_hide_null");
//		}else{
//			$(".xukeNum_warn").addClass("hide");
//			$(".doc_zhichengs").removeClass("border_hide_null");
//		};
//	});
	
	$(".doc_zhichengss").blur(function(){
		if($(".doc_zhichengss").val() == ""){
			$(".xukeNum_warn").removeClass("hide");
			$(".doc_zhichengss").addClass("border_hide_null");
		}else{
			$(".xukeNum_warn").addClass("hide");
			$(".doc_zhichengss").removeClass("border_hide_null");
		};
	});
	

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