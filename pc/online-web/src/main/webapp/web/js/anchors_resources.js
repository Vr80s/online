$(function() {
	//医师按钮变色效果
	$('.forum').css('color', '#000');
	$('.path .doctor').addClass('select');
	//进行认证状态的验证 控制左侧tab的显示隐藏
	//	 RequestService("/medical/common/isDoctorOrHospital","GET",null,function(data){
	//	       if(data.success == true ){
	//	       	if(data.resultObject.indexOf(1) != -1){
	//	       		//医师认证成功 
	//	       		$('.docSuccessBtn').removeClass('hide');
	//	       		$('.docSuccessBox').removeClass('hide');
	//	       	}
	//	       }
	//	    });

	
	//定位之前点击过的位置
	setTimeout(function(){
		if(localStorage.docTblSta == 'doc_hos') $('.select_list li:first-child').click();
		if(localStorage.docTblSta == 'doc_zhuanlan') $('.select_list li:nth-child(2)').click();
		if(localStorage.docTblSta == 'doc_book') $('.select_list li:nth-child(3)').click();
		if(localStorage.docTblSta == 'doc_media') $('.select_list li:nth-child(4)').click();
		if(localStorage.docTblSta == 'doc_admit') $('.select_list li:nth-child(5)').click();
	},100)
	
	
	

	
	//重新认证按钮
	$('#doc_Distinguish ').on("click", ".renzhengAgain", function() {
		localStorage.AutStatus = "AutAgain";
		window.location.href = "/web/html/ResidentDoctor.html";
	})

	//	点击左侧tab按钮右侧页面变化效果
	$(".select_list li").click(function() {
		$(".select_list li").removeClass("active");
		$(this).addClass("active");
		$(".wrap_box .little_box").hide()
		$(".select_box").hide().eq($(this).index()).show();
		
		window.localStorage.docTblSta = $(this).attr('data-name')
	
		//判断顶部是否具有返回
		if($(".select_box").eq($(this).index()).find('.changeStaBtn').text() == '返回') {
			$(".select_box").eq($(this).index()).find('.changeStaBtn').click();
		}

		//图标颜色变化
		$(".left_range").removeClass("ino_color").eq($(this).index()).addClass("ino_color")
	})

	$(".select_list .select-ud").bind('click', function(event) {
		event.stopPropagation();
		$(".select_list .littleBox").stop().slideToggle();

	})

	$(".setTop").click(function() {
		$(".select_list .littleBox").slideUp()
		$(".select-ud").removeAttr("id")
		$(".select_list .arrow_jt").removeClass("glyphicon-triangle-bottom")
		$(".select_list .arrow_jt").addClass("glyphicon-triangle-left")
	})
	//对课程目录下小的下拉div进行操作		
	$(".select_list .littleBox p").bind('click', function(event) {
		event.stopPropagation();
		$(".select_list .littleBox p").removeClass("activeP");
		$(this).addClass("activeP");
		$(".wrap_box .little_box").hide().eq($(this).index()).show();
		$(".select_box").hide()
	})

	//		下拉小箭头设置	
	$(".select-ud").click(function() {
		if($(this).attr("id") == "open_list") {
			$(this).removeAttr("id")
			$(".select_list .arrow_jt").addClass("glyphicon-triangle-left")
			$(".select_list .arrow_jt").removeClass("glyphicon-triangle-bottom")
		} else {
			$(this).attr("id", "open_list")
			$(".select_list .arrow_jt").addClass("glyphicon-triangle-bottom")
			$(".select_list .arrow_jt").removeClass("glyphicon-triangle-left")
		}
	})

	//头像上传
	var userPic = $('.userPic').css('background')
	console.log(userPic)

	RequestService("/online/user/isAlive", "get", null, function(data) {
		//头像预览
		if(data.resultObject.smallHeadPhoto) {
			if(data.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
				//头像
				$('.doctor_inf>img').attr('src', data.resultObject.smallHeadPhoto);
				//名字
				$('.doctor_inf>h4').text(data.resultObject.name);

				if(data.resultObject.info) {
					//个性签名
					$('.doctor_inf>p').text(data.resultObject.info)
				}
				$('.')
			} else {

			}
		};
	});

	$(".doctor_inf >img").attr('src', userPic)
	$(".doctor_inf > img,.doctor_inf .picModal").on("click", function() {
		$(".mask").css("display", "block");
		$("#headImg").css("display", "block");
		$("body").css("overflow", "hidden");
		//清空右侧小图片
		//						$('.cropped-con').html('');
		RequestService("/online/user/isAlive", "get", null, function(data) {
			var path;
			//头像预览
			if(data.resultObject.smallHeadPhoto) {
				if(data.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
					path = data.resultObject.smallHeadPhoto;
				} else {
					path = bath + data.resultObject.smallHeadPhoto
				}
			};
			$('.cropped-con').html('');
			$('.cropped-con').append('<img src="' + path + '" align="absmiddle" style="width:80px;height:80px;margin-top:28px;border-radius:80px;" class="img01"><p style="font-size:12px;color:#666;margin-top:6px;">80px*80px</p>');
			$('.cropped-con').append('<img src="' + path + '" align="absmiddle" style="width:40px;height:40px;margin-top:28px;border-radius:40px;"><p style="font-size:12px;color:#666;margin-top:6px;">40px*40px</p>');
			img()
			//新插件
		});
	})

	function img() {
		//清空文件
		function clearFileInput(file) {
			var form = document.createElement('form');
			document.body.appendChild(form);
			//记住file在旧表单中的的位置
			var pos = file.nextSibling;
			form.appendChild(file);
			form.reset();
			pos.parentNode.insertBefore(file, pos);
			document.body.removeChild(form);
		}
		$(".imgClose,.btn-quit").click(function() {
			$('.cropped-con').html('');
			$(".img-box1").css("display", "block");
			$(".imageBox").css("display", "none");
			$(".tc").css("display", "none");
			$(".mask").css("display", "none");
			$("#headImg").css("display", "none");
			$("body").css("overflow", "auto");
			var file = document.getElementById("upload-file");
			clearFileInput(file);
			$(".btn-upload").attr("data-img", "");
			$(".imageBox").css("background-image", "");
		})
		var options = {
			thumbBox: '.thumbBox',
			spinner: '.spinner',
			imgSrc: ""
		}
		var cropper = $('.imageBox').cropbox(options);
		var img = "";
		$('#upload-file').on('change', function() {
			var filepath = $(this).val();
			if(filepath == "") {
				return false;
			}
			var extStart = filepath.lastIndexOf(".");
			var ext = filepath.substring(extStart, filepath.length).toUpperCase();
			if(ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
				//							layer.msg("图片限于bmp,png,gif,jpeg,jpg格式", {
				//								icon: 2
				//							});
				$(".rrrrTips").text("图片限于bmp,png,gif,jpeg,jpg格式").css("display", "block");
				var file = document.getElementById("upload-file");
				clearFileInput(file);
				setTimeout(function() {
					$(".rrrrTips").css("display", "none");
				}, 1500);
			} else if(($("#upload-file").get(0).files[0].size / 1024 / 1024) > 1) {
				$(".rrrrTips").text("图片大小不超过1M").css("display", "block");
				var file = document.getElementById("upload-file")
				clearFileInput(file);
				setTimeout(function() {
					$(".rrrrTips").css("display", "none");
				}, 1500);
			} else {
				if(filepath) {
					$(".img-box1").css("display", "none");
					$(".imageBox").css("display", "block");
					$(".tc").css("display", "block");
					var reader = new FileReader();
					reader.onload = function(e) {
						options.imgSrc = e.target.result;
						cropper = $('.imageBox').cropbox(options);
						getImg();
					}
					reader.readAsDataURL(this.files[0]);
					this.files = [];
					getImg();
					//								return $(".imageBox").click(function() {
					//									getImg();
					//								});
				}
			}
		})

		function getImg() {
			img = cropper.getDataURL();
			$('.cropped-con').html('');
			$('.cropped-con').append('<img src="' + img + '" align="absmiddle" style="width:80px;height:80px;margin-top:28px;border-radius:80px;" class="img01"><p style="font-size:12px;color:#666;margin-top:6px;">80px*80px</p>');
			$('.cropped-con').append('<img src="' + img + '" align="absmiddle" style="width:40px;height:40px;margin-top:28px;border-radius:40px;"><p style="font-size:12px;color:#666;margin-top:6px;">40px*40px</p>');
			$(".btn-upload").attr("data-img", img);
		}
		$(".imageBox").on("mousemove mouseup", function() {
			getImg()
		})
	}

	//function fileClick() {
	//	return $("#upload-file").click();
	//}
	$('.fileUpdata').click(function() {
		return $("#upload-file").click();
	})
	$(".btn-upload").click(function(evt) {
		evt.preventDefault();
		if($(".btn-upload").attr("data-img") != undefined && $(".btn-upload").attr("data-img") != "") {} else {
			$(".rrrrrTips").text("请选择图片").css("display", "block");
			setTimeout(function() {
				$(".rrrrrTips").css("display", "none");
			}, 1500);
			return false;
		}
		$(".btn-upload").css("color", "white");
		//	if($(".upload_pictures_bottom_upload").attr("data-id") && $(".upload_pictures_bottom_upload").attr("data-id") != '/webview/images/usershow/defaultHeadImg.jpg') {
		RequestService("/online/user/updateHeadPhoto", "post", {
			image: $(".btn-upload").attr("data-img"),
		}, function(data) {
			if(data.success == true) {
				RequestService("/online/user/isAlive", "get", null, function(t) {
					var path;
					if(t.resultObject.smallHeadPhoto) {
						if(t.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
							path = t.resultObject.smallHeadPhoto;
						} else {
							path = bath + t.resultObject.smallHeadPhoto
						}
						$(".userPic").css({
							background: "url(" + path + ") no-repeat",
							backgroundSize: "100% 100%"
						});
						//							$("doctor_inf >img").css({
						//								background: "url(" + path + ") no-repeat",
						//								backgroundSize: "100% 100%"
						//							});
						$(".doctor_inf >img").attr('src', path)

						var file = document.getElementById("upload-file")
						//清空文件
						function clearFileInput(file) {
							var form = document.createElement('form');
							document.body.appendChild(form);
							//记住file在旧表单中的的位置
							var pos = file.nextSibling;
							form.appendChild(file);
							form.reset();
							pos.parentNode.insertBefore(file, pos);
							document.body.removeChild(form);
						}
						clearFileInput(file);
						$('.cropped-con').html('');
						$(".img-box1").css("display", "block");
						$(".imageBox").css("display", "none");
						$(".tc").css("display", "none");
						$(".mask").css("display", "none");
						$("#headImg").css("display", "none");
						location.reload();
					}

				})

			}
		})

		$(".btn-upload").css("color", "white");
	})

	//	专栏部分
	//	专栏部分点击发布效果
	var zhuanlanCount = 1;
	$('#zhuanlan .zhuanlan_top button').click(function() {
		zhuanlanCount *= -1;
		//发布
		if(zhuanlanCount < 0) {
			//顶部变化
			$(this).text('返回');
			$(this).siblings('.title').text('新专栏');
			//底部变化
			$('#zhuanlan_bottom2').addClass('hide');
			$('#zhuanlan_bottom').removeClass('hide');
		} else {
			//取消发布
			$(this).text('发布');
			$(this).siblings('.title').text('专栏');
			//底部变化
			$('#zhuanlan_bottom').addClass('hide');
			$('#zhuanlan_bottom2').removeClass('hide');
		}

	})

	//	著作部分
	//	著作部分点击发布效果
	var zhuzuoCount = 1;
	$('#zhuzuo .zhuzuo_top button').click(function() {
		zhuzuoCount *= -1;
		//发布
		if(zhuzuoCount < 0) {
			//顶部变化
			$(this).text('返回');
			$(this).siblings('.title').text('著作编辑');
			//底部变化
			$('.zhuzuo_bottom2').addClass('hide');
			$('.zhuzuo_bottom').removeClass('hide');
		} else {
			//取消发布
			$(this).text('发布');
			$(this).siblings('.title').text('著作');
			//底部变化
			$('.zhuzuo_bottom').addClass('hide');
			$('.zhuzuo_bottom2').removeClass('hide');
		}

	})

	//	媒体报道部分
	//	媒体报道部分点击发布效果
	var meitiCount = 1;
	$('#media_report .media_report_top button').click(function() {
		meitiCount *= -1;
		//发布
		if(meitiCount < 0) {
			//顶部变化
			$(this).text('返回');
			$(this).siblings('.title').text('报道编辑');
			//底部变化
			$('.media_report_bottom2').addClass('hide');
			$('.media_report_bottom').removeClass('hide');
		} else {
			//取消发布
			$(this).text('发布');
			$(this).siblings('.title').text('媒体报道');
			//底部变化
			$('.media_report_bottom').addClass('hide');
			$('.media_report_bottom2').removeClass('hide');
		}

	})

	//资源部分
	//资源部分点击上传资源
	var ziyuanCount = 1;
	$('#resource .zhuanlan_top button').click(function() {
		ziyuanCount *= -1;
		//上传
		if(ziyuanCount < 0) {
			//顶部变化
			$(this).text('返回');
			$(this).siblings('.title').text('新资源');
			//底部变化
			$('#ziyuan_bottom2').addClass('hide');
			$('#ziyuan_bottom').removeClass('hide');
		} else {
			//取消上传
			$(this).text('上传资源');
			$(this).siblings('.title').text('资源');
			//底部变化
			$('#ziyuan_bottom').addClass('hide');
			$('#ziyuan_bottom2').removeClass('hide');
		}
	})
})

//医馆部分
//点击变色效果
$('.hospital_worktime ul li ').click(function() {
	if($(this).hasClass('color')) {
		$(this).removeClass('color')
	} else {
		$(this).addClass('color');
	}

})

//医师认证状态和认证信息显示
RequestService("/medical/doctor/apply/listHospital/0", "get", null, function(data) {
	//头像预览
	console.log(data);

	//列表渲染
	$('#id_select').append('<option value="-1">请选择医馆</option>')
	$('#id_select').append(template('hosListTpl', {
		item: data.resultObject.records
	}));

	//渲染之后在此调用插件
	$('.selectpicker').selectpicker({
		'selectedText': 'cat',
		size: 10
	});

});

//选择平台已有医馆
$('#hospital_bottom .chooseHospital').click(function() {
	$('.mask').css('display', 'block');
	$('#hosChoose').removeClass('hide');
})

//点击关闭选择医馆列表
$('#close_hosChoose').click(function() {
	$('.mask').css('display', 'none');
	$('#hosChoose').addClass('hide');
})

//选择医馆列表选中之后出发的事件
$('#id_select').change(function() {
	$('#hospital_bottom .zhuanlan_title').val($("#id_select option:selected").text())
	$('.mask').css('display', 'none');
	$('#hosChoose').addClass('hide');

	//医馆ID的获取
	hosID = $('#id_select').val()
	if(hosID == -1) {
		//清空信息
		clearHosList()
		return false;
	}

	//	alert(hosID);
	//获取对应的医馆信息渲染到页面上
	RequestService("/medical/hospital/getHospitalById", "get", {
		id: hosID,
	}, function(data) {
		console.log(data);
		//省
		if(data.resultObject.province) {
			$('#hosPro').val(data.resultObject.province)
		}
		//市
		if(data.resultObject.city) {
			$('#hosCity').val(data.resultObject.city)
		}
		//详细地址
		if(data.resultObject.detailedAddress) {
			//					$('#detail_address').text('');
			$('#detail_address').val(data.resultObject.detailedAddress)
		}
		//医馆封面
		if(data.frontImg) {
			$('#hospital .fengmian_pic').html('<img src=' + frontImg + ' alt="">')
		}
		//电话号码
		if(data.resultObject.tel) {
			$('.hosContantTel .hosTel').val(data.resultObject.tel);
		}
	})
})

//清空医师入驻医馆信息列表
function clearHosList() {
	//省分
	$('#hosPro').val('医馆所在省份');
	//市区
	$('#hosCity').val('医馆所在市区')
	//详细地址清空
	$('#detail_address').val('')
	//封面清空
	$('#hospital .fengmian_pic').html('	<p style="font-size: 90px;height: 100px;font-weight: 300;color: #d8d8d8;text-align: center;">+</p><p style="text-align: center;color: #999;font-size: 14px;"></p>');
	//电话清空
	$('#hospital .hosTel').val('');
	//坐诊时间
	$('#hospital .hospital_worktime ul li').removeClass('color keshiColor');

}

//医师-医馆封面上传图片调用的接口
function picUpdown(baseurl, imgname) {
	RequestService("/medical/common/upload", "post", {
		image: baseurl,
	}, function(data) {
		console.log(data);
		$('#hospital_bottom .' + imgname + '').html('<img src="' + data.resultObject + '" >');
	})
}

//医馆封面上传
$('#fengmian_picIpt').on('change', function() {
	var reader = new FileReader();
	reader.onload = function(e) {
		picUpdown(reader.result, 'fengmian_pic');
	}
	reader.readAsDataURL(this.files[0])
})

//每周坐诊点击生成数组数据
var arr = [];
var workTime;
$('.hospital_worktime  ul li').click(function() {
	if($(this).hasClass('keshiColor')) {
		//删除第二次选中的
		for(var i = 0; i < arr.length; i++) {
			if($(this).text() == arr[i]) {
				arr.splice(i, 1)
			}
		}
		//			console.log(arr.toString())
		workTime = arr.toString();
		$(this).removeClass('keshiColor');
	} else {
		$(this).addClass('keshiColor');
		arr.push($(this).text());
		//			console.log(arr.toString())
		workTime = arr.toString();
	}
	console.log(workTime)
})

//医师入住医馆信息上传
$('#hospital_bottom #submit').click(function() {
	//任职医馆的验证
	var workhosName = $.trim($('#hospital_bottom .zhuanlan_title').val());
	var province = $.trim($('#hosPro').val());
	var city = $.trim($('#hosCity').val());
	var detailedAddress = $.trim($('#hospital_bottom #detail_address').val());
	var hosTel = $.trim($('#hospital_bottom .hosTel').val());
	var phonePass = /^1[3,4,5,7,8]\d{9}$/gi;
	var headPortrait = $('#hospital_bottom .fengmian_pic img').attr('src');

	//任职医馆验证
	if(workhosName == '') {
		$('#hospital_bottom .work_hos_warn').removeClass('hide');
		return false;
	} else {
		$('#hospital_bottom .work_hos_warn').addClass('hide');
	}

	//封面图是否上传
	//		if($('#hospital_bottom .fengmian_pic:has(img)').length < 1){
	//			$('#hospital_bottom .fengmian_pic_warn').removeClass('hide');
	//			return false;
	//		}else{
	//			$('#hospital_bottom .fengmian_pic_warn').addClass('hide');
	//		}
	//
	//医馆电话
	//	if(hosTel == ''){
	//		$('#hospital_bottom .hosTel_warn').text('手机号不能为空');
	//		$('#hospital_bottom .hosTel_warn').removeClass('hide');
	//		return false;
	//	}else if(!phonePass.test(hosTel)){
	//		$('#hospital_bottom .hosTel_warn').text('手机号格式不正确');
	//		$('#hospital_bottom .hosTel_warn').removeClass('hide');
	//		return false;
	//	}else{
	//		$('#hospital_bottom .hosTel_warn').addClass('hide');
	//	}

	//坐诊时间验证
	if($('#hospital_bottom .hospital_worktime .keshiColor').length == 0) {
		$('#hospital_bottom .hospital_worktime_warn  ').removeClass('hide');
		return false;
	} else {
		$('#hospital_bottom .hospital_worktime_warn  ').addClass('hide');
	}

	RequestService("/medical/doctor/joinHospital", "post", {
		workTime: workTime,
		hospitalId: hosID
	}, function(data) {
		if(data.success == true) {
			//				alert('上传成功')
			$('#tip').text('加入成功！');
			$('#tip').toggle();
			setTimeout(function() {
				$('#tip').toggle();
				window.location.reload();
			}, 2000)

		} else {
			//				alert('您不是医师，不能加入医馆')
			$('#tip').text(data.errorMessage);
			$('#tip').toggle();
			setTimeout(function() {
				$('#tip').toggle();
			}, 2000)
		}
	});
	//	alert(111)
})

if(localStorage.AccountStatus == 1) {
	RequestService("/medical/doctor/authentication/get", "get", {}, function(data) {
		console.log(data);
		if(data.success == false) {
			//				alert('获取认证状态数据失败');
			//				$('#tip').text('获取认证状态数据失败');
			//	       		$('#tip').toggle();
			//	       		setTimeout(function(){
			//	       			$('#tip').toggle();
			//	       		},2000)
		} else if(data.success == true) {
			//				alert('认证成功');
			//医馆数据渲染
			//				$('#hosAutStatus').html(template('hosAutStatusTpl',data.resultObject))
			$('#renzheng_status_list').html(template('renzheng_statusTpl', data.resultObject));
			//个人信息渲染
			$('.personIntroduct .introductInf').html(data.resultObject.description);
		}

	})
} else {
	//医师认证状态和认证信息显示
	RequestService("/medical/doctor/apply/getLastOne", "get", null, function(data) {
		//头像预览
		console.log(data);
		$('#renzheng_status_list').html(template('renzheng_statusTpl', data.resultObject));
		//个人信息渲染
		$('.personIntroduct .introductInf').html(data.resultObject.description);
	});
}

//如果入驻了医馆进入获取数据
$('#docJoinHos').click(function() {
	RequestService("/medical/doctor/getHospital", "get", null, function(data) {
		if(data.success == true) {
			//入住过医馆
			//头像预览
			$('.selectpicker').selectpicker('val', (data.resultObject.id));
			//坐诊时间渲染
			var workArr = data.resultObject.visitTime.split(",");
			console.log(workArr)

			var j;
			for(var i = 0; i < $('.hospital_worktime ul li ').length; i++) {
				for(j = 0; j < workArr.length; j++) {
					if($('.hospital_worktime ul li').eq(i).text() == workArr[j] && !$('.hospital_worktime ul li').eq(i).hasClass('color keshiColor')) {
						$('.hospital_worktime ul li').eq(i).click();
						//                  $('.hospital_worktime ul li').eq(i).addClass('color keshiColor');
					}
				}
			}
		}

	});

})

//下架、删除提示的显示和隐藏效果

function showDel() {
	$('#deleteTip').removeClass('hide');
	$('#mask').removeClass('hide');
}

function hideDel() {
	$('#deleteTip').addClass('hide');
	$('#mask').addClass('hide');
}

//下线功能
function downLine() {
	showDel()
}

//预览关闭功能
$('.close_preview').click(function() {
	$('#preview').addClass('hide');
	$('#mask').addClass('hide')

})

//显示预览功能
function showPreview() {
	$('#preview').removeClass('hide');
	$('#mask').removeClass('hide')
}


//医师媒体报道上传图片调用的接口
function picUpdown(baseurl, imgname) {
//	RequestService("/medical/common/upload", "post", {
//		image: baseurl,
//	}, function(data) {
//		$('#hos_Administration .hos_base_inf  .' + imgname + '').html('<img src="' + data.resultObject + '" >');
//	})
}



//媒体报道图片上传
$('#zhuzuo_picIpt').on('change', function() {
	if(this.files[0].size > 2097152) {
		$('#tip').text('上传图片不能大于2M');
		$('#tip').toggle();
		setTimeout(function() {
			$('#tip').toggle();
		}, 2000)
		return false;
	}
	if(!(this.files[0].type.indexOf('image') == 0 && this.files[0].type && /\.(?:jpg|png|gif)$/.test(this.files[0].name))) {
		$('#tip').text('图片格式不正确');
		$('#tip').toggle();
		setTimeout(function() {
			$('#tip').toggle();
		}, 2000)
		return false;
	}

	var reader = new FileReader();
	reader.onload = function(e) {
		console.log(reader.result)
//		picUpdown(reader.result, 'touxiang_pic');
	}
	reader.readAsDataURL(this.files[0])
})