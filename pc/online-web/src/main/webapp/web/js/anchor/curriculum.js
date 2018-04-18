var checkedType = "video";
$(function() {
	showCourseAttribute(1);

	$('#anchorWorkbench').css('color', '#00bc12');

	//  左侧一级菜单点击的时候保存localStorate 和 对应事件
	$('#accordion > li').click(function() {
		//右侧显示对应的盒子
		$('#right-content > div').addClass('hide');
		$('#right-content > div').eq($(this).index()).removeClass('hide');

		if($(this).attr('data-name') != "menu=2-1") {
			//右侧盒子中的显示第一个盒子中的内容
			$('#right-content > div:eq(' + $(this).index() + ') > div').addClass('hide')
			$('#right-content > div:eq(' + $(this).index() + ') > div').eq(0).removeClass('hide');

		}

		//设置hash
		location.hash = $(this).attr('data-name');
		//清空localStorage
		//		localStorage.leftTblSta == ' ';
		//里面的第一个a添加颜色
		$('#accordion > li .submenu > li >a').removeClass('leftTab_active')
		$('#accordion > li .submenu > li:first-child >a').addClass('leftTab_active')

		//点击学堂事件
		if($(this).attr('data-name') == "menu=1-1") {
			//获取课程列表方法 在course.js中
			getCourseList();
		}

		//点击我的资产
		if($(this).attr('data-name') == "menu=2-1") {
			//重置我的资产部分
			initBasaeAssetInfo();
			//里面中间结构隐藏
			$('#mymoney .content_mid').addClass('hide');
			$('#mymoney .changeType .pandaMoney').click();
		}

		//点击收益事件
		if($(this).attr('data-name') == "menu=3-1") {
			//获取课程收益方法在mymoney.js中
			getCourseResiveList(1)
		}

		//点击账号事件
		if($(this).attr('data-name') == "menu=4-1") {}

	})

	//里面的二级菜单点击设置location.hash
	$('#accordion > li .submenu >li>a').click(function(e) {
		//点击颜色效果
		$('#accordion > li .submenu >li>a').removeClass('leftTab_active');
		$(this).addClass('leftTab_active');
		e.stopPropagation();

		//右侧对应的div显示出来
		var index = $(this).parent().parent().parent().index();
		$('#right-content > div:eq(' + index + ') > div').addClass('hide')
		$('#right-content > div:eq(' + index + ') > div').eq($(this).parent().index()).removeClass('hide');
		//修改hash值
		location.hash = $(this).attr('data-name');
		//把hash存储起来
		localStorage.leftTblSta = $(this).attr('data-name');
	})

	//头像上传
	var userPic = $('.userPic').css('background')
	RequestService("/online/user/isAlive", "get", null, function(data) {
		//头像预览
		if(data.resultObject.smallHeadPhoto) {
			$('.doctor_inf>img').attr('src', data.resultObject.smallHeadPhoto);
			$('.doctor_inf>h4').text(data.resultObject.name);
		} else {
			$('.doctor_inf>img').attr('src', "/web/images/defaultHeadImg.jpg");
		}

		if(data.resultObject.info) {
			$('.doctor_inf>p').text(data.resultObject.info)
		} else {
			$('.doctor_inf>p').text('说点什么来彰显你的个性吧……')
		}
	});

	$(".doctor_inf > img,.doctor_inf .picModal").on("click", function() {
		showDel();
		$("#headImg").css("display", "block");
		$("body").css("overflow", "hidden");
		//清空右侧小图片
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
			//			$(".mask").css("display", "none");
			hideDel();
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
						//							$(".mask").css("display", "none");
						hideDel();
						$("#headImg").css("display", "none");
						location.reload();
					}

				})

			}
		})

		$(".btn-upload").css("color", "white");
	})

	//上传封面图片
	$('.fengmian_pic').click(function() {
		$('#picIpt').click();
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

	//处理点击--课程显示隐藏第一第二页
	$(".select_list .courseP").click(function() {
		$(".curriculum_two").show();
		$(".curriculum_one").hide();
	});

	//点击选择资源
	$('#a').click(function() {
		$('.a_resource').show();
	});
	$('.a_resource_close').click(function() {
		$('.a_resource').hide();
	});

	//专辑开始
	var zhuanjiCount = 1;
	$('#zhuanji .zhuanlan_top button').click(function() {
		zhuanjiCount *= -1;
		//上传
		if(zhuanjiCount < 0) {
			//顶部变化
			$(this).text('返回');
			$(this).siblings('.title').text('新专辑');
		} else {
			$(this).text('新专辑');
			$(this).siblings('.title').text('专辑');
		}
	})

	//专辑
	$('#zhuanji_bottom .baocun #submit').click(function() {
		//任职医馆的验证
		var workhosName = $.trim($('#zhuanji_bottom .zhuanlan_title').val());
		var workhosNames = $.trim($('#zhuanji_bottom .zhuanlan_title0').val());
		var workhosNamess = $.trim($('#zhuanji_bottom #picIpt').val());
		var nameNames = $.trim($('#zhuanji_bottom .zhuanlan_title1 ').val());
		var textArea = $.trim($('#zhuanji_bottom #textarea2').val());
		var selectTime = $.trim($('#zhuanji_bottom .select_time').val());
		var selectTimes = $.trim($('#zhuanji_bottom .zhuanlan_title2').val());
		var price = $.trim($('#zhuanji_bottom .zhuanlan_title3').val());
		var resourceUrl = $.trim($('#zhuanji_bottom .resourceUrl').val());

		//	专辑简介
		var zhuanjiCounts = $.trim($('#zhuanji_bottom #textarea3').val());

		//	专辑大纲
		var zhuanjiCountss = $.trim($('#zhuanji_bottom #textarea4').val());

		//	总集数
		var AlwaysPut = $.trim($('#zhuanji_bottom .zhuanlan_title03').val());

		var headPortrait = $('#zhuanji_bottom .fengmian_pic img').attr('src');

		//课程标题
		if(workhosName == '') {
			$('#zhuanji_bottom .warning0').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning0').addClass('hide');
		}

		//	副标题
		if(workhosNames == '') {
			$('#zhuanji_bottom .warning1').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning1').addClass('hide');
		}

		//	封面图
		if(workhosNamess == '') {
			$('#zhuanji_bottom .warning2').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning2').addClass('hide');
		}

		//	主播姓名
		if(nameNames == '') {
			$('#zhuanji_bottom .warning3').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning3').addClass('hide');
		}

		//	主播介绍
		if(textArea == '') {
			$('#zhuanji_bottom .warning4').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning4').addClass('hide');
		}

		//	专辑简介
		if(zhuanjiCounts == '') {
			$('#zhuanji_bottom .warning9').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning9').addClass('hide');
		}

		//	专辑大纲  
		if(zhuanjiCountss == '') {
			$('#zhuanji_bottom .warning01').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning01').addClass('hide');
		}

		//	总集数
		if(AlwaysPut == '') {
			$('#zhuanji_bottom .warning02').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning02').addClass('hide');
		}

		//	请选择开课时间
		if(selectTime == '') {
			$('#zhuanji_bottom .warning5').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning5').addClass('hide');
		}

		//	时长
		if(selectTimes == '') {
			$('#zhuanji_bottom .warning6').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning6').addClass('hide');
		}

		//	价格
		if(price == '') {
			$('#zhuanji_bottom .warning7').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning7').addClass('hide');
		}

		//	资源
		if(resourceUrl == '') {
			$('#zhuanji_bottom .warning8').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning8').addClass('hide');
		}

		//封面图是否上传
		if($('#zhuanji_bottom .fengmian_pic:has(img)').length < 1) {
			$('#zhuanji_bottom .fengmian_pic_warn').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .fengmian_pic_warn').addClass('hide');
		}

		//	alert(111)
	})

	//保存
	$('#zhuanji_bottom .baocun #submit0').click(function() {
		//任职医馆的验证
		var workhosName = $.trim($('#zhuanji_bottom .zhuanlan_title').val());
		var workhosNames = $.trim($('#zhuanji_bottom .zhuanlan_title0').val());
		var workhosNamess = $.trim($('#zhuanji_bottom #picIpt').val());
		var nameNames = $.trim($('#zhuanji_bottom .zhuanlan_title1 ').val());
		var textArea = $.trim($('#zhuanji_bottom #textarea2').val());
		var selectTime = $.trim($('#zhuanji_bottom .select_time').val());
		var selectTimes = $.trim($('#zhuanji_bottom .zhuanlan_title2').val());
		var price = $.trim($('#zhuanji_bottom .zhuanlan_title3').val());
		var resourceUrl = $.trim($('#zhuanji_bottom .resourceUrl').val());

		//	专辑简介
		var zhuanjiCounts = $.trim($('#zhuanji_bottom #textarea3').val());

		//	专辑大纲
		var zhuanjiCountss = $.trim($('#zhuanji_bottom #textarea4').val());

		//	总集数
		var AlwaysPut = $.trim($('#zhuanji_bottom .zhuanlan_title03').val());

		var headPortrait = $('#zhuanji_bottom .fengmian_pic img').attr('src');

		//课程标题
		if(workhosName == '') {
			$('#zhuanji_bottom .warning0').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning0').addClass('hide');
		}

		//	副标题
		if(workhosNames == '') {
			$('#zhuanji_bottom .warning1').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning1').addClass('hide');
		}

		//	封面图
		if(workhosNamess == '') {
			$('#zhuanji_bottom .warning2').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning2').addClass('hide');
		}

		//	主播姓名
		if(nameNames == '') {
			$('#zhuanji_bottom .warning3').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning3').addClass('hide');
		}

		//	主播介绍
		if(textArea == '') {
			$('#zhuanji_bottom .warning4').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning4').addClass('hide');
		}

		//	专辑简介
		if(zhuanjiCounts == '') {
			$('#zhuanji_bottom .warning9').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning9').addClass('hide');
		}

		//	专辑大纲  
		if(zhuanjiCountss == '') {
			$('#zhuanji_bottom .warning01').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning01').addClass('hide');
		}

		//	总集数
		if(AlwaysPut == '') {
			$('#zhuanji_bottom .warning02').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning02').addClass('hide');
		}

		//	请选择开课时间
		if(selectTime == '') {
			$('#zhuanji_bottom .warning5').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning5').addClass('hide');
		}

		//	时长
		if(selectTimes == '') {
			$('#zhuanji_bottom .warning6').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning6').addClass('hide');
		}

		//	价格
		if(price == '') {
			$('#zhuanji_bottom .warning7').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning7').addClass('hide');
		}

		//	资源
		if(resourceUrl == '') {
			$('#zhuanji_bottom .warning8').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .warning8').addClass('hide');
		}

		//封面图是否上传
		if($('#zhuanji_bottom .fengmian_pic:has(img)').length < 1) {
			$('#zhuanji_bottom .fengmian_pic_warn').removeClass('hide');
			return false;
		} else {
			$('#zhuanji_bottom .fengmian_pic_warn').addClass('hide');
		}

	})

	//添加课程为空开始     提交课程
	$('#zhuanjis_bottom .baocun #submits').click(function() {
		//	添加课程为空
		var LectureTitles = $.trim($('#zhuanjis_bottom .zhuanlan_title').val());
		var LectureTitless = $.trim($('#zhuanjis_bottom .zhuanlan_title0').val());
		var AddpicIpt = $.trim($('#zhuanjis_bottom #picIpt2').val());
		var HostName = $.trim($('#zhuanjis_bottom .zhuanlan_titles1').val());
		var HostIntroduced = $.trim($('#zhuanjis_bottom #textareas1').val());
		var Select = $.trim($('#zhuanjis_bottom #Select').val());
		var Time = $.trim($('#zhuanjis_bottom .zhuanlan_titles2').val());
		var Price = $.trim($('#zhuanjis_bottom .zhuanlan_titles3').val());
		var CourseDescriptions = $.trim($('#zhuanjis_bottom .a_textarea').val());
		var Ainput = $.trim($('#zhuanjis_bottom .a_input').val());

		//课程标题
		if(LectureTitles == '') {
			$('#zhuanjis_bottom .warnings0').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings0').addClass('hide');
		}

		//	副标题
		if(LectureTitless == '') {
			$('#zhuanjis_bottom .warnings1').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings1').addClass('hide');
		}

		//	上传图片
		if(AddpicIpt == '') {
			$('#zhuanjis_bottom .warnings2').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings2').addClass('hide');
		}

		//	主播姓名
		if(HostName == '') {
			$('#zhuanjis_bottom .warnings3').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings3').addClass('hide');
		}

		//	主播介绍
		if(HostIntroduced == '') {
			$('#zhuanjis_bottom .warnings4').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings4').addClass('hide');
		}

		//	主播分类
		if(Select == '') {
			$('#zhuanjis_bottom .warnings5').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings5').addClass('hide');
		}

		//	时长
		if(Time == '') {
			$('#zhuanjis_bottom .warnings6').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings6').addClass('hide');
		}

		//  价格
		if(Price == '') {
			$('#zhuanjis_bottom .warnings7').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings7').addClass('hide');
		}

		//  课程介绍
		if(CourseDescriptions == '') {
			$('#zhuanjis_bottom .warnings8').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings8').addClass('hide');
		}

		//  选择资源
		if(Ainput == '') {
			$('#zhuanjis_bottom .warnings9').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings9').addClass('hide');
		}

	})

	//添加课程为F空开始     提交课程
	$('#ziyuan_bottom .baocun #submit').click(function() {
		//	添加课程为空
		var ResourceTitle = $.trim($('#ziyuan_bottom .zhuanlan_title').val());
		var ResourceUploading = $.trim($('#ziyuan_bottom #picIpt3').val());
		var resourcePut = $.trim($('#ziyuan_bottom .resource_put').val());

		//课程标题
		if(ResourceTitle == '') {
			$('#ziyuan_bottom .warning0').removeClass('hide');
			return false;
		} else {
			$('#ziyuan_bottom .warning0').addClass('hide');
		}

		//封面图
		/*if(ResourceUploading == ''){
			$('#ziyuan_bottom .warning1').removeClass('hide');
			return false;
		}else{
			$('#ziyuan_bottom .warning1').addClass('hide');
		}*/

		//上传资源
		if(resourcePut == '') {
			$('#ziyuan_bottom .warning2').removeClass('hide');
			return false;
		} else {
			$('#ziyuan_bottom .warning2').addClass('hide');
		}

	})
	//资源为空结束

	//添加课程为空开始     保存
	$('#zhuanjis_bottom .baocun #submits0').click(function() {
		//	添加课程为空
		var LectureTitles = $.trim($('#zhuanjis_bottom .zhuanlan_title').val());
		var LectureTitless = $.trim($('#zhuanjis_bottom .zhuanlan_title0').val());
		var AddpicIpt = $.trim($('#zhuanjis_bottom #picIpt2').val());
		var HostName = $.trim($('#zhuanjis_bottom .zhuanlan_titles1').val());
		var HostIntroduced = $.trim($('#zhuanjis_bottom #textareas1').val());
		var Select = $.trim($('#zhuanjis_bottom #Select').val());
		var Time = $.trim($('#zhuanjis_bottom .zhuanlan_titles2').val());
		var Price = $.trim($('#zhuanjis_bottom .zhuanlan_titles3').val());
		var CourseDescriptions = $.trim($('#zhuanjis_bottom .a_textarea').val());
		var Ainput = $.trim($('#zhuanjis_bottom .a_input').val());

		//课程标题
		if(LectureTitles == '') {
			$('#zhuanjis_bottom .warnings0').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings0').addClass('hide');
		}

		//	副标题
		if(LectureTitless == '') {
			$('#zhuanjis_bottom .warnings1').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings1').addClass('hide');
		}

		//	上传图片
		if(AddpicIpt == '') {
			$('#zhuanjis_bottom .warnings2').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings2').addClass('hide');
		}

		//	主播姓名
		if(HostName == '') {
			$('#zhuanjis_bottom .warnings3').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings3').addClass('hide');
		}

		//	主播介绍
		if(HostIntroduced == '') {
			$('#zhuanjis_bottom .warnings4').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings4').addClass('hide');
		}

		//	主播分类
		if(Select == '') {
			$('#zhuanjis_bottom .warnings5').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings5').addClass('hide');
		}

		//	时长
		if(Time == '') {
			$('#zhuanjis_bottom .warnings6').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings6').addClass('hide');
		}

		//  价格
		if(Price == '') {
			$('#zhuanjis_bottom .warnings7').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings7').addClass('hide');
		}

		//  课程介绍
		if(CourseDescriptions == '') {
			$('#zhuanjis_bottom .warnings8').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings8').addClass('hide');
		}

		//  选择资源
		if(Ainput == '') {
			$('#zhuanjis_bottom .warnings9').removeClass('hide');
			return false;
		} else {
			$('#zhuanjis_bottom .warnings9').addClass('hide');
		}

	})

	//点击添加
	$(".zhaunji_tr td .add").click(function() {
		$("#zhuanjis_bottom").show();
		$("#zhuanji_bottom2").hide();
		$("#zhuanji .zhuanlan_top_one").hide();
	});

	//新专辑,新增课程 
	$(".new_box .size").click(function() {
		$(".new_box").hide();
		$("#zhuanji_bottom").hide();
		$(".zhuanlan_top_one").hide();
		$("#zhuanjis_bottom").show();
	});

	//专辑
	$("#zhuanji_bottom2 .returns").click(function() {
		$("#zhuanji_bottom2").hide();
		$("#zhuanji_bottom").show();
	});

	//新专辑返回
	$("#zhuanji_bottom .returns").click(function() {
		$("#zhuanji_bottom").hide();
		$("#zhuanji_bottom2").show();
	});

	//新专辑--添加课程
	$("#zhuanjis_bottom .returns").click(function() {
		$("#zhuanji_bottom").hide();
		$("#zhuanjis_bottom").hide();
		$("#zhuanji_bottom2").show();
	});

	//点击专辑--专辑-新专辑
	$("#zhuanji_bottom2 .zhuanlan_top .returns").click(function() {
		$("#zhuanji_bottom2").hide();
		$("#zhuanji_bottom").remove("hide");
	});
})

function showCourseAttribute(type) {
	$(".special").hide();
	if(type == 1) {
		$(".live").show();
	} else if(type == 2) {
		$(".vod").show();
	} else {
		$(".offline").show();
	}
}

function confirmBox(title, content, fn) {
	$(".confirm-title").html(title);
	$(".confirm-content").html(content);
	$(".confirm-sure").click(function() {
		fn(hideDel);
		$(".confirm-sure").unbind("click")
	})
	showDel();
}

//删除提示框出现方法
function showDel() {
	$('#deleteTip').removeClass('hide');
	$('#mask').removeClass('hide');
}

//删除提示框出现方法
function showDel_bank() {
	$('#addBankCard').removeClass('hide');
	$('#mask').removeClass('hide');
}

//删除提示消失方法
function hideDel() {
	$('#deleteTip').addClass('hide');
	$('#mask').addClass('hide');
}

//体现提示消失方法
function hideDel_bank() {
	$('#addBankCard').addClass('hide');
	$('#mask').addClass('hide');
}

//科室点击验证效果
var arr = [];
var keshiStr;
$('.account_main_alter  .keshi ').on('click', '#keshiList>li', function() {
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

function uploadFile() {
	var filemd5 = "";
	var obj_file = document.getElementById("btn_width").files[0];
	var filepath = $("#btn_width").val();
	//判断文件类型
	if(!isAccord(filepath)) {
		return false;
	}
	$("#continueUpload").hide();
	$('.progress-resource').css({
		"width": "0%"
	})
	$("#ziyuan_bottom .resource_uploading").show();
	$("#mask").removeClass('hide')
	$("#ziyuan_bottom .uploadfinish").hide();
	$("#ziyuan_bottom .updataSuccess").hide();
	//获取文件md5
	browserMD5File(obj_file, function(err, md5) {
		filemd5 = md5;
		localStorage.setItem("fileMD5", filemd5);
		xmx(0, "1", filemd5, "", "", "")
	});
}



function clearNoNum(obj) {
	obj.value = obj.value.replace(/[^\d.]/g, ""); //清除“数字”和“.”以外的字符
	obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
	if(obj.value.indexOf(".") < 0 && obj.value != "") { //以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
		obj.value = parseFloat(obj.value);
	}
}


function xmx(begin, first, filemd5, ccid, metaurl, chunkUrl) {
	var obj_file = document.getElementById("btn_width").files[0];
	chunkSize = 2097152; //2M
	var totalSize = obj_file.size; //文件总大小
	var start = begin; //每次上传的开始字节
	var end = start + chunkSize; //每次上传的结尾字节
	var blob = null;
	blob = obj_file.slice(start, end); //截取每次需要上传字节数

	formData = new FormData(); //每一次需重新创建
	formData.append('file', blob); //添加数据到表单对象中
	formData.append('fileSize', totalSize); //添加数据到表单对象中
	formData.append('filemd5', filemd5); //添加数据到表单对象中
	formData.append('fileName', obj_file.name); //添加数据到表单对象中
	formData.append('first', first); //添加数据到表单对象中
	formData.append('ccid', ccid); //添加数据到表单对象中
	formData.append('metaUrl', metaurl); //添加数据到表单对象中
	formData.append('chunkUrl', chunkUrl); //添加数据到表单对象中
	formData.append('start', start); //添加数据到表单对象中
	currentAjax = $.ajax({
		url: '/videoRes/uploadFile',
		type: 'POST',
		cache: false,
		data: formData,
		processData: false,
		contentType: false
	}).done(function(result) {
		if(result.success) {
			var ccid = result.resultObject[0];
			var metaurl = result.resultObject[1];
			var chunkUrl = result.resultObject[2];
			//计算完成百分比
			var completion = Math.round(end / totalSize * 10000) / 100.00;
			if(completion > 100) {
				completion = 100;
			}
			$('.progress-resource').css({
				"width": completion + "%"
			})
			start = end; // 累计上传字节数
			end = start + chunkSize; // 由上次完成的部分字节开始，添加下次上传的字节数
			localStorage.setItem("startChunkSize", start);
			localStorage.setItem("ccId", ccid);
			localStorage.setItem("metaUrl", metaurl);
			localStorage.setItem("chunkUrl", chunkUrl);
			// 上传文件部分累计
			if(start >= totalSize) { //如果上传字节数大于或等于总字节数，结束上传
				$("#ccId").val(result.resultObject[0]);
				var fileName = obj_file.name.substring(0, obj_file.name.lastIndexOf("."));
				$("#ziyuan_bottom .resource_uploading").hide();
				$("#mask").addClass('hide')
				$("#ziyuan_bottom .uploadfinish").show();
				$("#ziyuan_bottom .updataSuccess").show();
				var title = $("#ziyuan_bottom .zhuanlan_title").val();
				if(title == "") {
					$("#ziyuan_bottom .zhuanlan_title").val(fileName);
				}
				uploadfinished = true;
				//alert('上传完成!');
				//告诉后台上传完成后合并文件                            //返回上传文件的存放路径
				$(".propress-file").css({
					"border": "0",
					"overflow": "inherit"
				})
				$("#btn_width").css({
					"opacity": "1",
					"width": "auto",
					"z-index": "100",
					"left": "0"
				})
			} else {
				xmx(start, "2", "", ccid, metaurl, chunkUrl); // 上传字节不等与或大于总字节数，继续上传
			}
		} else {
			$("#continueUpload").show();
			//alert('上传失败');
		}
	}).fail(function() {
		$("#continueUpload").show();
		//alert('上传失败!');

	});

}

function isAccord(filepath) {
	if(filepath == "") {
		return false;
	}
	var extStart = filepath.lastIndexOf(".");
	var ext = filepath.substring(extStart, filepath.length).toUpperCase();
	if(checkedType == 'video') {
		if(ext != ".WMV" && ext != ".WM" && ext != ".ASF" && ext != ".ASX" &&
			ext != ".RM" && ext != ".RMVB" && ext != ".RA" && ext != ".RAM" && ext != ".MPG" &&
			ext != ".MPEG" && ext != ".MPE" && ext != ".VOB" && ext != ".DAT" &&
			ext != ".MOV" && ext != ".3GP" && ext != ".MP4" && ext != ".MP4V" &&
			ext != ".M4V" && ext != ".MKV" && ext != ".AVI" && ext != ".FLV" &&
			ext != ".F4V" && ext != ".MTS") {
			showTip("文件格式有误")
			return false;
		}
	} else {
		if(ext != ".MP3" && ext != ".WAV" &&
			ext != ".AIF" && ext != ".AIFF" && ext != ".AU" && ext != ".SND" &&
			ext != ".VOC" && ext != ".RA" && ext != ".MIDRMI" && ext != ".WMA" &&
			ext != ".APE" && ext != ".FLAC" && ext != ".AAC" && ext != ".M4A" &&
			ext != ".VQF") {
			showTip("文件格式有误")
			return false;
		}
	}
	return true;
}

function cancalUpdata1() {
	currentAjax.abort();
	var file = document.getElementById('btn_width');
	file.value = '';
	$('.progress-resource').css({
		"width": "0%"
	})
	$('#mask').addClass('hide');
	$("#ziyuan_bottom .uploading").hide();
	$("#ziyuan_bottom .resource_uploading").hide();
}

function continueUpload() {
	$("#continueUpload").hide();
	var start = localStorage.getItem("startChunkSize");
	var ccid = localStorage.getItem("ccId");
	var metaurl = localStorage.getItem("metaUrl");
	var chunkUrl = localStorage.getItem("chunkUrl");
	var fileMd5 = localStorage.getItem("fileMD5");
	xmx(parseInt(start), "2", "", ccid, metaurl, chunkUrl);
}

//判断选中的是视频还是音频
function changePeriod(checked) {
	checkedType = checked;
	if(checked == "video") {
		$("#btn_width").attr("accept", ".wmv,.wm,.asf,.asx,.rm,.rmvb,.ra,.ram,.mpg,.mpeg,.mpe,.vob,.dat,.mov,.3gp,.mp4,.mp4v,.m4v,.mkv,.avi,.flv,.f4v,.mts");
	} else {
		$("#btn_width").attr("accept", ".Mp3,.Wav,.aif,.aiff,.au,.snd,.voc,.ra,.midrmi,.wma,.ape,.flac,.aac,.m4a,.vqf");
	}
}