var userPic = $('.userPic').css('background')

//请求头像 名字 个性签名
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
		} else {

		}
	};
});

//获取医师认证状态控制左侧tab栏
RequestService("/medical/common/isDoctorOrHospital", "GET", null, function(data) {
	if(data.success == true) {
		if(data.resultObject != 1) {
			//医师认证未成功
			$('.DocAut_btn').removeClass('hide');
			$('.ImDoc_btn').addClass('hide');
			if(data.resultObject == 3 || data.resultObject == 5) {
				//认证中
				seeAutStatus();
				$('#docAut_tip').removeClass('hide');
			} else if(data.resultObject == 7) {
				//未认证
				//	       			$('#docNoPass_tip').removeClass('hide');
				//拒绝情况
				RequestService("/doctor/apply/getLastOne", "get", null, function(data) {
					if(data.resultObject.status == 0){
						seeAutStatus();
						$('#docAut_tip').removeClass('hide');
					}
				})
				$('#AutList').removeClass('hide');
				$('#AutStatus').addClass('hide');
			}
		} else if(data.resultObject == 1) {
			//医师认证成功
			//	       		$('#docpass_tip').removeClass('hide');
			$('.ImDoc_btn').removeClass('hide');
			$('.DocAut_btn').addClass('hide');
			$('#AutList').addClass('hide');
			$('#AutStatus').removeClass('hide');
		}
	}
    var hash = $.trim(window.location.hash);
	//判断是否是重新认证
	if(hash == "#update") {
        autagain();
	}

});

$(".doctor_inf >img").attr('src', userPic)
$(".doctor_inf > img,.news_nav .picModal").on("click", function() {
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

function fileClick() {
	return $("#upload-file").click();
}
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
					window.location.reload();
				}

			})

		}
	})

	$(".btn-upload").css("color", "white");
})

//问答下拉列表功能
$('.wenda').click(function() {
	$('.wenda_list').slideToggle();
})

//论坛下拉列表功能
$('.luntan').click(function() {
	$('.luntan_list').slideToggle();
})

//左侧鼠标移动上去变色效果
$('#doctor_in_inf .news_nav ul li a').mouseenter(function() {
	$(this).children('span').css('color', '#00bc12')
})
//鼠标移除
$('#doctor_in_inf .news_nav ul li a').mouseout(function() {
	$(this).children('span').css('color', '#cacbcb')
})
//点击变色效果
$('#doctor_in_inf .news_nav ul li a').click(function() {
	$('#doctor_in_inf .news_nav ul li a').removeClass('color');
	$('#doctor_in_inf .news_nav ul li a > span').removeClass('color');
	$(this).addClass('color');
	$(this).children('span').addClass('color');

})

//点击我是医师
function goDocPage() {
	window.location.href = "/doctors/my";
}

//点击首页状态提示中的查看认证状态
function seeAutStatus() {
	$('.DocAut_btn > a ').click();
	$('#AutList').addClass('hide');
	$('#AutStatus').removeClass('hide');

}
//点击去认证
function gotToAut() {
	$('.DocAut_btn > a ').click();
	$('#AutList').removeClass('hide');
	$('#AutStatus').addClass('hide');
}
var nullHead='<p style="font-size: 90px;height: 80px;font-weight: 300;color: #d8d8d8;text-align: center;line-height: 80px;">+</p>'+
			'<p style="text-align: center;color: #999;font-size: 14px;">求真相</p>'
			
var nullZhichen='<p style="font-size: 90px;height: 100px;font-weight: 300;color: #d8d8d8;text-align: center;">+</p>'+
			'<p style="text-align: center;color: #999;font-size: 14px;">点击上传职称证明图片</p>'
//查看状态之后的重新认证
function autagain() {
	//医师认证状态回显示
	RequestService("/doctor/apply/getLastOne", "get", null, function(data) {
		if( data.success == true && data.resultObject != null){
            $('#AutList').removeClass('hide');
            $('#AutStatus').addClass('hide');
			var result = data.resultObject;
			//姓名
			$('#AutList .doc_name').val(result.name);
			//身份证号码
			$('#AutList .doc_Idnum').val(result.cardNum);
			//医师资格证
			$('#AutList .teacher_pic').html("<img src="+result.qualificationCertificate+">");
			//执业资格证
			$('#AutList .zhiye_pic').html("<img src="+result.professionalCertificate+">");
			// 真实头像
			if(result.headPortrait==null || result.headPortrait==""){
				$('#AutList .touxiang_pic').html(nullHead);
			}else{
				$('#AutList .touxiang_pic').html("<img src="+result.headPortrait+">");
			}
			//职称			
			$('#AutList .doc_zhicheng').val(result.title);
			// 职称证明
			if(result.titleProve==null || result.titleProve==""){
				$('#AutList .zhicheng_pic').html(nullZhichen);				
			}else{
				$('#AutList .zhicheng_pic').html("<img src="+result.titleProve+">");
			}
			//科室
			var j;
			for(var i = 0; i < $('#AutList #keshiList li').length; i++) {
				for(j = 0; j < result.medicalDepartments.length; j++) {
					if($('#AutList #keshiList li').eq(i).text() == result.medicalDepartments[j].name && !$('#AutList #keshiList li').eq(i).hasClass('keshiColor')) {
						$('#AutList #keshiList li').eq(i).click();
					}
				}
			}
			//擅长
			$('#AutList .doc_shanchang').val(result.field);
			//个人介绍 editor
//          UE.getEditor('editor').addListener('ready', function (editor) {
                UE.getEditor('editor').setContent(result.description);
//          });

			//省份
			for(var i = 0 ;i < $('#AutList #choosePro option').length ;i++){
				if($('#AutList #choosePro option').eq(i).text() == result.province){
					$('#AutList #choosePro option').eq(i).attr('selected','selected')
				}
			}
			//城市
			$('#AutList #citys option:selected').text(result.city);
			
		}
	})
}







//医师认证状态信息显示
showAutStatus();

function showAutStatus() {
	//医师认证状态和认证信息显示
	RequestService("/doctor/apply/getLastOne", "get", null, function(data) {
		//头像预览
		console.log(data);
		if(data.resultObject != null) {
			//首页部分状态提示部分
			$('#shouyeStatus').html(template('shouyeTipTpl', data.resultObject));

			//内部状态模板
			$('#AutStatus').html(template('docAutStatus_Tpl', data.resultObject));
			$('#AutStatus .personIntroduct > p').html(data.resultObject.description)
		} else {
			$('#doc_doctor #docNoPass_tip').removeClass('hide');
		}

	});
}

//获取科室内容渲染页面
RequestService("/doctor/apply/listDepartment/0", "get", null, function(data) {
	console.log(data);
	//				 $('#doc_Distinguish .'+imgname+'').html('<img src="'+data.resultObject+'" >');
	$('#keshiList').html(template('keshiTpl', {
		item: data.resultObject.records
	}));
})

//上传图片调用的接口
function picUpdown(baseurl, imgname) {
	RequestService("/medical/common/upload", "post", {
		image: baseurl,
	}, function(data) {
		console.log(data);
		$('#doc_Distinguish .' + imgname + '').html('<img src="' + data.resultObject + '" >');
	})

}

//医师认证上传图片处理

//身份证正面 idFont_pic
$('#idFont_pic_ipt').on('change', function() {
	//	console.log(this.files[0].size)  
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
		//		console.log( reader.result);  //或者 e.target.result都是一样的，都是base64码
		//		$('#img').attr('src',reader.result)
		picUpdown(reader.result, 'idFont_pic');
	}
	reader.readAsDataURL(this.files[0])
	//filses就是input[type=file]文件列表，files[0]就是第一个文件，这里就是将选择的第一个图片文件转化为base64的码
	//进行图片展示
})

//身份证反面
$('#idBack_pic_ipt').on('change', function() {
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
		picUpdown(reader.result, 'idBack_pic');
	}
	reader.readAsDataURL(this.files[0])
})

//  医师资格证
$('#teacher_pic_ipt').on('change', function() {
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
		picUpdown(reader.result, 'teacher_pic');
	}
	reader.readAsDataURL(this.files[0])
})

//  执业资格证
$('#zhiiye_pic_ipt').on('change', function() {
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
		picUpdown(reader.result, 'zhiye_pic');
	}
	reader.readAsDataURL(this.files[0])
})

//   真实头像
$('#touxiang_pic_ipt').on('change', function() {
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
		picUpdown(reader.result, 'touxiang_pic');
	}
	reader.readAsDataURL(this.files[0])
})

//  职称证明
$('#zhicheng_pic_ipt').on('change', function() {
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
		picUpdown(reader.result, 'zhicheng_pic');
	}
	reader.readAsDataURL(this.files[0])
})
