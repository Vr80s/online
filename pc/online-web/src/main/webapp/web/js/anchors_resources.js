$(function() {
	//	确定,取消弹窗初始化
	comfirmBox.init();
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
	setTimeout(function() {
		if(localStorage.docTblSta == 'doc_hos') $('.select_list li:first-child').click();
		if(localStorage.docTblSta == 'doc_zhuanlan') $('.select_list li:nth-child(2)').click();
		if(localStorage.docTblSta == 'doc_book') $('.select_list li:nth-child(3)').click();
		if(localStorage.docTblSta == 'doc_media') $('.select_list li:nth-child(4)').click();
		if(localStorage.docTblSta == 'doc_admit') $('.select_list li:nth-child(5)').click();
	}, 100)

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

	//---------------------------------头像上传部分结束，专栏部分开始-------------------------------------------------

var ue = UE.getEditor('column-content', {
		toolbars: [
			[
				'undo', //撤销
				'redo', //重做
				'bold', //加粗
				'forecolor', //字体颜色
				'backcolor', //背景色
				'indent', //首行缩进
				'removeformat', //清除格式
				'formatmatch', //格式刷
				'blockquote', //引用
				'fontfamily', //字体
				'fontsize', //字号
				'paragraph', //段落格式
				'italic', //斜体
				'underline', //下划线
				'strikethrough', //删除线
				'superscript', //上标
				'subscript', //下标
				'touppercase', //字母大写
				'tolowercase', //字母小写
				'justifyleft', //居左对齐
				'justifyright', //居右对齐
				'justifycenter', //居中对齐
				'justifyjustify', //两端对齐
				'link', //超链接
				'unlink', //取消链接
				'simpleupload', //单图上传
				// 'insertimage', //多图上传
				//				'emotion', //表情
				'fullscreen'
			]
		],
		initialFrameWidth: 540,
        initialFrameHeight:220,
		elementPathEnabled: false,
		autoHeightEnabled: false,
		autoFloatEnabled: true,
		enableAutoSave: false,
		imagePopup: false,
		maximumWords: 10000 //允许的最大字符数
	});
	//	专栏部分
	//	专栏部分，点击发布效果
	$('#zhuanlan .zhuanlan_top button').click(function() {
		var recruit_btn = $(this).text()
		if(recruit_btn == "发布") {
			$(".column-sava-publish").removeAttr("disabled", "disabled");
			resetColumn();
			$('#zhuanlan_bottom2').addClass('hide');
			$('#zhuanlan_bottom').removeClass('hide');
			$(this).text("返回")
			$(".recruit-wrap-title p").text("专栏");
			//			保存按钮显现
			$(".column-new-button").removeClass("hide");
			$(".column-edit-button").addClass("hide");

		} else {
			$('#zhuanlan_bottom2').removeClass('hide');
			$('#zhuanlan_bottom').addClass('hide');
			$(this).text("发布")
			$(".recruit-wrap-title p").text("专栏")
		}
	})
	//专栏部分，封面图上传
	function columnUpdown(baseurl, imgname) {
		RequestService("/medical/common/upload", "post", {
			image: baseurl,
		}, function(data) {
			$('#zhuanlan .zhuanlan_bottom  .' + imgname + '').html('<img src="' + data.resultObject + '" >');
		})
	}
	$('#zhuanlan_picIpt').on('change', function() {		
		if(this.files[0].size > 2097152) {
			$('#tip').text('上传图片不能大于2M');
			$('#tip').toggle();
			setTimeout(function() {
				$('#tip').toggle();
			}, 2000)
//			showTip("上传图片不能大于2M");
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
			columnUpdown(reader.result, 'column-picter');
		}
		reader.readAsDataURL(this.files[0])
	});
	//点击专栏的发布和保存
	function columnRecruit(data) {
		if(data.title == "") {
			$(".column-title-warning").removeClass("hide");
			return false;
		} else {
			$(".column-title-warning").addClass("hide");
		}
		if($(".column-picter img").length == 0) {
			$(".column-picter-warning").removeClass("hide");
			return false;
		} else {
			$(".column-picter-warning").addClass("hide");
		}
		if(data.content == "") {
			$(".column-text-warning").removeClass("hide");
			return false;
		} else {
			$(".column-text-warning").addClass("hide");
		}
		return true;
	}
	$(".column-sava-publish").click(function() {
		var columeStatus = $(this).attr("data-status");
		var data = {
			"title": $.trim($(".column-title").val()),
			"imgPath": $(".column-picter img").attr("src"),
//			"content": $.trim($(".column-text").val()),
			"content": UE.getEditor('column-content').getContent(),
			"status": columeStatus
		}
		if(columnRecruit(data)) {
			$(this).attr("disabled", "disabled");
			$.ajax({
				type: "POST",
				url: bath + "/doctor/article/specialColumn",
				data: JSON.stringify(data),
				contentType: "application/json",
				success: function(data) {
					if(data.success == true) {
						showTip("保存成功");
						$("#nav-colume").click();
						columnList(1);
					} else {
						showTip("保存失败");
					}
				}
			});
		};

	})

	//专栏部分的列表
	columnList(1)

	function columnList(pages) {
		RequestService("/doctor/article/specialColumn", "GET", {
			"page": pages
		}, function(data) {
			if(data.success = true) {
				getData = data.resultObject.records;
				if(getData.length == 0) {
					$(".columnNodata").removeClass("hide");
					$(".zhuanlan_bottom2 table").addClass("hide");
				} else {
					$(".columnNodata").addClass("hide");
					$(".zhuanlan_bottom2").removeClass("hide");
					$(".zhuanlan_bottom2 table").removeClass("hide");
					$("#column-wrap-list").html(template('column-list', {
						items: data.resultObject.records
					}));
				}
				if(data.resultObject.pages > 1) { //分页判断
					$(".not-data").remove();
					$(".column_pages").removeClass("hide");
					$(".column_pages .searchPage .allPage").text(data.resultObject.pages);
					$("#Pagination_column").pagination(data.resultObject.pages, {
						num_edge_entries: 1, //边缘页数
						num_display_entries: 4, //主体页数
						current_page: pages - 1,
						callback: function(page) {
							//翻页功能
							columnList(page + 1);
						}
					});
				} else {
					$(".column_pages").addClass("hide");
				}
			} else {
				showTip("获取数据失败");
			}
			//		上下架执行方法
			initEvent();
		})
	}
	//专栏部分的预览  828行
	//预览关闭功能
	$('#preview .preview_top img').click(function() {
		$('#preview').addClass('hide');
		$('#mask').addClass('hide')
	})
	//专栏部分，上架、下线功能
	function initEvent() {
		$('.fluctuate').click(function() {
			var id = $(this).attr("data-id"),
				status = $(this).attr("data-status");
			RequestService("/doctor/article/specialColumn/" + id + '/' + status, "PUT", null, function(data) {
				if(data.success == true) {
					showTip("操作成功");
					columnList(1);
				} else {
					showTip("操作失败");
				}
			})
		});
		//专栏部分，删除
		$('.column-delete').click(function() {
			var id = $(this).attr("data-id")
			comfirmBox.open("专栏", "确定删除该条专栏吗？", function(closefn) {
				RequestService("/doctor/article/specialColumn/" + id, "DELETE", null, function(data) {
					if(data.success == true) {
						showTip("删除成功");
						columnList(1);
					} else {
						showTip("删除失败");
					}
				})
				closefn();
			});
		});
		//专栏部分,点击编辑保存
		$(".save-edit-publish").click(function() {
			var editId = $("#column-id").val(),
				editData = {
					"title": $.trim($(".column-title").val()),
					"imgPath": $(".column-picter img").attr("src"),
					"content": UE.getEditor('column-content').getContent()
				};
			if(columnRecruit(editData)) {
				$.ajax({
					type: "PUT",
					url: bath + "/doctor/article/specialColumn/" + editId,
					contentType: "application/json",
					data: JSON.stringify(editData),
					success: function(data) {
						if(data.success == true) {
							showTip("保存成功");
							columnList(1);
							$("#nav-colume").click();
						} else {
							showTip("保存失败");
						}
					}
				});

			}
		});
	}

	//---------------------------------------专栏部分结束，著作部分开始--------------------------------------
	//	著作部分,富文本设置
	var ue = UE.getEditor('work-suggest', {
		toolbars: [
			[
				'undo', //撤销
				'redo', //重做
				'bold', //加粗
				'forecolor', //字体颜色
				'backcolor', //背景色
				'indent', //首行缩进
				'removeformat', //清除格式
				'formatmatch', //格式刷
				'blockquote', //引用
				'fontfamily', //字体
				'fontsize', //字号
				'paragraph', //段落格式
				'italic', //斜体
				'underline', //下划线
				'strikethrough', //删除线
				'superscript', //上标
				'subscript', //下标
				'touppercase', //字母大写
				'tolowercase', //字母小写
				'justifyleft', //居左对齐
				'justifyright', //居右对齐
				'justifycenter', //居中对齐
				'justifyjustify', //两端对齐
				'link', //超链接
				'unlink', //取消链接
				'simpleupload', //单图上传
				// 'insertimage', //多图上传
				//				'emotion', //表情
				'fullscreen'
			]
		],
		initialFrameWidth: 540,
        initialFrameHeight:220,
		elementPathEnabled: false,
		autoHeightEnabled: false,
		autoFloatEnabled: true,
		enableAutoSave: false,
		imagePopup: false,
		maximumWords: 10000 //允许的最大字符数
	});
	
	
	//	著作部分
	//	著作部分,点击发布切换效果
	$('#zhuzuo .zhuzuo_top button').click(function() {
		$(".work-save-publish").removeAttr("disabled");
		var workSelect = $(this).text()
		if(workSelect == "发布") {
			resetWork();
			$('.zhuzuo_bottom2').addClass('hide');
			$('.zhuzuo_bottom').removeClass('hide');
			$(this).text("返回")
			$(".zhuzuo_top .title").text("新著作");
			//		保存按钮显现
			$(".anlyWorSave").addClass("hide");
			$(".allWorkSave").removeClass("hide");
		} else {
			$('.zhuzuo_bottom2').removeClass('hide');
			$('.zhuzuo_bottom').addClass('hide');
			$(this).text("发布")
			$(".zhuzuo_top .title").text("著作")
		}
	})
	//	著作部分,封面图上传
	function workUpdown(baseurl, imgname) {
		RequestService("/medical/common/upload", "post", {
			image: baseurl,
		}, function(data) {
			$('#zhuzuo .zhuzuo_bottom  .' + imgname + '').html('<img src="' + data.resultObject + '" >');
		})
	}
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
			workUpdown(reader.result, 'work-picter');
		}
		reader.readAsDataURL(this.files[0])
	})
	//	著作部分,点击发布验证文本框
	function workValidate(workData) {
		if(workData.title == ""){
			$(".work-book-warning").removeClass("hide");
			return false;
		} else {
			$(".work-book-warning").addClass("hide");
		}
		if(workData.author == ""){
			$(".work-author-warning").removeClass("hide");
			return false;
		} else {
			$(".work-author-warning").addClass("hide");
		}
		if($(".work-picter img").length == "0") {
			$(".work-picter-warning").removeClass("hide");
			return false;
		} else {
			$(".work-picter-warning").addClass("hide");
		}
		if(workData.remark == ""){
			$(".work-text-warning").removeClass("hide");
			return false;
		} else {
			$(".work-text-warning").addClass("hide");
		}
		if(workData.buyLink == ""){
			$(".work-link-warning").removeClass("hide");
			return false;
		} else {
			$(".work-link-warning").addClass("hide");
		}
		return true;
	}
	$(".work-save-publish").click(function(){
		var worksaveId = $(this).attr("data-status"),
			workData = {
				"title": $(".work-title").val(),
				"author": $(".work-author").val(),
				"imgPath": $(".work-picter img").attr("src"),
				"remark": UE.getEditor('work-suggest').getContent(),
				"buyLink": $(".work-link").val(),
				"status": worksaveId
			}
		if(workValidate(workData)) {
			$(this).attr("disabled", "disabled");
			$.ajax({
				type: "POST",
				url: bath + "/doctor/writing",
				data: JSON.stringify(workData),
				contentType: "application/json",
				success: function(data) {
					if(data.success == true) {
						showTip("保存成功");
						$("#nav-work").click();
						workList(1);
					} else {
						showTip("保存失败");
					}
				}
			});
		}
	});
	//	著作部分,著作列表
	workList(1)

	function workList(pages) {
		RequestService("/doctor/writing", "GET", {
			"page": pages
		}, function(data) {
			getWorkdata = data.resultObject.records;
			if(getWorkdata.length == 0) {
				$(".workNodata").removeClass("hide");
				$(".zhuzuo_bottom2 table").addClass("hide");
			} else {
				$(".workNodata").addClass("hide");
				$(".zhuzuo_bottom2 table").removeClass("hide");
				$("#work-table").html(template("work-template", {
					items: data.resultObject.records
				}));
			}
			//分页添加
			if(data.resultObject.pages > 1) { //分页判断
				$(".not-data").remove();
				$(".works_pages").removeClass("hide");
				$(".works_pages .searchPage .allPage").text(data.resultObject.pages);
				$("#Pagination_works").pagination(data.resultObject.pages, {
					num_edge_entries: 1, //边缘页数
					num_display_entries: 4, //主体页数
					current_page: pages - 1,
					callback: function(page) {
						//翻页功能
						workList(page + 1);
					}
				});
			} else {
				$(".works_pages").addClass("hide");
			}
			//分页添加结束		
			initWork()
		})

	}
		//	著作部分,预览1283
		//著作部分，预览关闭功能
		$("#work-preview-page .common-top img").click(function() {
			$(".work-preview-page").addClass("hide");
			$("#mask").addClass("hide")
		})
		//	著作部分,上、下架
		function initWork() {
			$(".work-fluctuate").click(function() {
				var workId = $(this).attr("data-id"),
					workStatus = $(this).attr("data-status");
				RequestService("/doctor/writing/" + workId + "/" + workStatus, "PUT", null, function(data) {
					if(data.success == true) {
						showTip("操作成功");
						//重新渲染列表
						workList(1);
					} else {
						showTip("操作失败");
					}
				})

			});

			//	著作部分,删除		
			//		$(".work-delete").click(function(){
			//			var deleteId=$(this).attr("data-delete");
			//			comfirmBox.open("著作","确定删除该条著作吗？",function(closefn){
			//				RequestService("/hospitalRecruit/"+deleteId+"","DELETE",null,function(data){
			//					if(data.success == true){
			//						showTip("删除成功");
			//						workList(1);
			//						closefn();
			//					}else{
			//						showTip("删除失败");
			//						closefn();
			//					}
			//						
			//				})	
			//			})
			//		})
		}
		//	著作部分,编辑后保存
		$(".only-save-work").click(function() {
			var workEditId = $("#workId").val(),
				editDataWork = {
					"title": $(".work-title").val(),
					"author": $(".work-author").val(),
					"imgPath": $(".work-picter img").attr("src"),
					"remark": UE.getEditor('work-suggest').getContent(),
					"buyLink": $(".work-link").val(),
				}
			if(workValidate(editDataWork)) {
				$(".only-save-work").attr("disabled", "disabled");
				$.ajax({
					type: "PUT",
					url: bath + "/doctor/writing/" + workEditId + "",
					data: JSON.stringify(editDataWork),
					contentType: "application/json",
					success: function(data) {
						if(data.success == true) {
							showTip("保存成功");
							workList(1);
							$("#nav-work").click();
						} else {
							showTip("保存失败");
						}
					}
				});
			}

		});

		//---------------------------------------著作部分结束，媒体报道部分开始--------------------------------------
	var ue = UE.getEditor('media-context', {
		toolbars: [
			[
				'undo', //撤销
				'redo', //重做
				'bold', //加粗
				'forecolor', //字体颜色
				'backcolor', //背景色
				'indent', //首行缩进
				'removeformat', //清除格式
				'formatmatch', //格式刷
				'blockquote', //引用
				'fontfamily', //字体
				'fontsize', //字号
				'paragraph', //段落格式
				'italic', //斜体
				'underline', //下划线
				'strikethrough', //删除线
				'superscript', //上标
				'subscript', //下标
				'touppercase', //字母大写
				'tolowercase', //字母小写
				'justifyleft', //居左对齐
				'justifyright', //居右对齐
				'justifycenter', //居中对齐
				'justifyjustify', //两端对齐
				'link', //超链接
				'unlink', //取消链接
				'simpleupload', //单图上传
				// 'insertimage', //多图上传
				//				'emotion', //表情
				'fullscreen'
			]
		],
		initialFrameWidth: 540,
        initialFrameHeight:220,
		elementPathEnabled: false,
		autoHeightEnabled: false,
		autoFloatEnabled: true,
		enableAutoSave: false,
		imagePopup: false,
		maximumWords: 10000 //允许的最大字符数
	});
	
	//	媒体报道部分
		//	媒体报道部分,点击发布切换效果
		$('#media_report .media_report_top button').click(function() {
			$(".media-save-publish").removeAttr("disabled");
			var mediaSelect = $(this).text()
			if(mediaSelect == "发布") {
				resetMedia()
				$('.media_report_bottom2').addClass('hide');
				$('.media_report_bottom').removeClass('hide');
				$(this).text("返回")
				//			$(".zhuzuo_top .title").text("媒体报道");	
				//	保存按钮显示和隐藏
				$(".media-save-allwrap").removeClass("hide");
				$(".media-save-wrap").addClass("hide");
			} else {
				$('.media_report_bottom2').removeClass('hide');
				$('.media_report_bottom').addClass('hide');
				$(this).text("发布")
				//			$(".zhuzuo_top .title").text("著作")	
			}
		})
		//	媒体报道部分,封面图上传
		function mediaUpdown(baseurl, imgname) {
			RequestService("/medical/common/upload", "post", {
				image: baseurl,
			}, function(data) {
				$('#media_report .media_report_bottom  .' + imgname + '').html('<img src="' + data.resultObject + '" >');
			})
		}
		$('#media_picIpt').on('change', function() {
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
				mediaUpdown(reader.result, 'media-picter');
			}
			reader.readAsDataURL(this.files[0])
		})
		//	媒体报道部分,点击发布验证文本框
		function mediaValidate(mediaData) {
			if(mediaData.title == "") {
				$(".media-title-warning").removeClass("hide");
				return false;
			} else {
				$(".media-title-warning").addClass("hide");
			}
			if(mediaData.author == "") {
				$(".media-author-warning").removeClass("hide");
				return false;
			} else {
				$(".media-author-warning").addClass("hide");
			}
			if($(".media-picter img").length == "0") {
				$(".media-picter-warning").removeClass("hide");
				return false;
			} else {
				$(".media-picter-warning").addClass("hide");
			}
			if(mediaData.content == "") {
				$(".media-text-warning").removeClass("hide");
				return false;
			} else {
				$(".media-text-warning").addClass("hide");
			}
			if(mediaData.url == "") {
				$(".media-url-warning").removeClass("hide");
				return false;
			} else {
				$(".media-url-warning").addClass("hide");
			}
			return true;
		}
		$(".media-save-publish").click(function() {
			var mediaSaveId = $(this).attr("data-status"),
				mediaData = {
					"title": $(".media-title").val(),
					"author": $(".media-author").val(),
					"imgPath": $(".media-picter img").attr("src"),
					"content": UE.getEditor('media-context').getContent(),
					"url": $(".media-link").val(),
					"status": mediaSaveId
				}
			if(mediaValidate(mediaData)) {
				$(this).attr("disabled", "disabled");
				$.ajax({
					type: "POST",
					url: bath + "/doctor/article/report",
					data: JSON.stringify(mediaData),
					contentType: "application/json",
					success: function(data) {
						if(data.success == true) {
							showTip("保存成功");
							$("#nav-media").click();
							mediaList(1);
						} else {
							showTip("保存失败");
						}
					}
				});
			}
		});
		//	媒体报道部分,媒体报道列表	
		mediaList(1)

		function mediaList(pages) {
			RequestService("/doctor/article/report", "GET", {
				"page": pages
			}, function(data) {
				getMediadata = data.resultObject.records;
				if(getMediadata.length == 0) {
					$(".mediaNodata").removeClass("hide");
					$(".media_report_bottom2 table").addClass("hide");
				} else {
					getMediadata = data.resultObject.records;
					$(".mediaNodata").addClass("hide");
					$(".media_report_bottom2 table").removeClass("hide");
					$("#media-table").html(template("media-template", {
						items: data.resultObject.records
					}));
				}
				//分页添加
				if(data.resultObject.pages > 1) { //分页判断
					$(".not-data").remove();
					$(".media_pages").removeClass("hide");
					$(".media_pages .searchPage .allPage").text(data.resultObject.pages);
					$("#Pagination_media").pagination(data.resultObject.pages, {
						num_edge_entries: 1, //边缘页数
						num_display_entries: 4, //主体页数
						current_page: pages - 1,
						callback: function(page) {
							//翻页功能
							mediaList(page + 1);
						}
					});
				} else {
					$(".media_pages").addClass("hide");
				}
				//分页添加结束		
				initMedia()
			})
		};

		function initMedia() {
			//媒体报道部分,预览1335行
			//媒体报道部分,关闭预览弹窗
			$("#media-preview .media-preview-top img").click(function() {
				$("#media-preview").addClass("hide");
				$("#mask").addClass("hide");
			})
			//媒体报道部分,上、下架		
			$(".media-select").click(function() {
				var mediaId = $(this).attr("data-id"),
					mediaFluctuate = $(this).attr("data-status");
				RequestService("/doctor/article/report/" + mediaId + "/" + mediaFluctuate + "", "PUT", null, function(data) {
					if(data.success == true) {
						showTip("操作成功");
						mediaList(1);
					} else {
						showTip("操作失败");
					}
				})
			})

			//媒体报道部分,删除		
			$(".media-delete").click(function() {
				var deleteMediaId = $(this).attr("data-delete");
				comfirmBox.open("媒体报道", "确定删除该条报道吗？", function(closefn) {
					RequestService("/doctor/article/report/" + deleteMediaId + "", "DELETE", null, function(data) {
						if(data.success == true) {
							showTip("删除成功");
							mediaList(1)
						} else {
							showTip("删除失败");
						}
					})
					closefn(); // 关闭弹窗
				});
			})

			//媒体报道部分,编辑后的保存	
			$(".media-only-save").click(function() {
				var mediaEditId = $("#mediaId").val(),
					editDataMedia = {
						"title": $(".media-title").val(),
						"author": $(".media-author").val(),
						"imgPath": $(".media-picter img").attr("src"),
						"content": UE.getEditor('media-context').getContent(),
						"url": $(".media-link").val(),
					};
				if(mediaValidate(editDataMedia)) {
					$(".media-only-save").attr("disabled", "disabled");
					$.ajax({
						type: "PUT",
						url: bath + "/doctor/article/report/" + mediaEditId + "",
						data: JSON.stringify(editDataMedia),
						contentType: "application/json",
						success: function(data) {
//							if(data.success == true) {
								showTip("保存成功");
								mediaList(1);
								$("#nav-media").click();
//							} else {
//								showTip("保存失败");
//							}
						}
					});
				}

			});

		}

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
	RequestService("/doctor/apply/listHospital/0", "get", null, function(data) {
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
		RequestService("/hospital/getHospitalById", "get", {
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

		RequestService("/doctor/joinHospital", "post", {
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
		RequestService("/doctor/authentication/get", "get", {}, function(data) {
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
		RequestService("/doctor/apply/getLastOne", "get", null, function(data) {
			//头像预览
			console.log(data);
			$('#renzheng_status_list').html(template('renzheng_statusTpl', data.resultObject));
			//个人信息渲染
			$('.personIntroduct .introductInf').html(data.resultObject.description);
		});
	}

	//如果入驻了医馆进入获取数据
	$('#docJoinHos').click(function() {
		RequestService("/doctor/getHospital", "get", null, function(data) {
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

	//-----------------------------------------专栏部分，预览,编辑回显--------------------------------------
	//用html方法清空上传的照片
	var defaultPicter = '<p style="font-size: 90px;height: 100px;font-weight: 300;color: #d8d8d8;text-align: center;">+</p>' +
		'<p style="text-align: center;color: #999;font-size: 14px;">点击上传封面图片</p>';

	//显示预览功能
	var getData;

	function showPreview(index) {
		var columnPreview = getData[index];
		$(".preview-column-title").text(columnPreview.title);
		$(".preview-column-picter img").attr("src", columnPreview.imgPath);
		$(".preview-column-content").html(columnPreview.content);
		$('#preview').removeClass('hide');
		$('#mask').removeClass('hide')
	}

	//专栏部分，	点击编辑
	//回显所有数据，id隐藏
	//1.获取所有修改后的值2.校验所有值3.将所有值提交到后台
	function columnEdit(index) {
		resetColumn(index)
		echoColumn(index)
		$(".zhuanlan_bottom").removeClass("hide");
		$(".zhuanlan_bottom2").addClass("hide");
		$('#zhuanlan .zhuanlan_top .title').text('专栏编辑');
		$('#zhuanlan .zhuanlan_top button').text('返回');
		//		保存按钮显现
		$(".column-new-button").addClass("hide");
		$(".column-edit-button").removeClass("hide");
	}

	function resetColumn(index) {
		$("#column-id").val("");
		$(".column-title").val("");
		$(".column-picter").html(defaultPicter)
		$("#zhuanlan_picIpt").val("");
		$(".column-text").val("");
		$(".warning").addClass("hide");
		UE.getEditor('column-content').setContent("");
	}

	function echoColumn(index) {
		var columnGetdata = getData[index];
		$("#column-id").val(columnGetdata.id);
		$(".column-title").val(columnGetdata.title);
		$(".column-picter").html("<img src=" + columnGetdata.imgPath + " />");
//		$(".column-text").val(columnGetdata.content);
		UE.getEditor('column-content').setContent(columnGetdata.content);
	}
	//-----------------------------------------著作部分，预览,编辑回显--------------------------------------
	var getWorkdata;
	function workPreview(index) {
		var workPreviewData = getWorkdata[index];
		$(".preview-work-title").text(workPreviewData.title);
		$(".preview-work-author").text(workPreviewData.author);
		$(".preview-work-picter img").attr("src", workPreviewData.imgPath);
		$(".preview-work-present").html(workPreviewData.remark);
		$(".preview-work-link").text(workPreviewData.buyLink);
		$("#work-preview-page").removeClass("hide");
		$("#mask").removeClass("hide")
	}

//著作部分，	点击编辑
//回显所有数据，id隐藏
//1.获取所有修改后的值2.校验所有值3.将所有值提交到后台
function workEdit(index) {
	resetWork(index);
	echoWork(index);
	$(".zhuzuo_bottom").removeClass("hide");
	$(".zhuzuo_bottom2").addClass("hide");
	$('#zhuzuo .zhuzuo_top .title').text('著作编辑');
	$('#zhuzuo .zhuzuo_top button').text('返回');
	//		保存按钮显现
	$(".allWorkSave").addClass("hide");
	$(".anlyWorSave").removeClass("hide");
};

function resetWork(index) {
	$("#workId").val("");
	$(".work-title").val("");
	$(".work-author").val("");
	$(".work-picter").html(defaultPicter);
	$("#zhuzuo_picIpt").val("");
//	$(".work-text").val("");
 	UE.getEditor('work-suggest').setContent(""),
	$(".work-link").val("");
	$(".warning").addClass("hide");
	$(".only-save-work").removeAttr("disabled");
};

function echoWork(index) {
	var workData = getWorkdata[index];
	$("#workId").val(workData.id);
	$(".work-title").val(workData.title);
	$(".work-author").val(workData.author);
	$(".work-picter").html("<img src=" + workData.imgPath + " />");
//	$(".work-text").val(workData.remark);
	UE.getEditor('work-suggest').setContent(workData.remark),
	$(".work-link").val(workData.buyLink);
};
//-----------------------------------------媒体报道部分，预览,编辑回显--------------------------------------
//媒体报道部分,预览
var getMediadata;

function mediaRreview(index) {
	var previewData = getMediadata[index];
	$(".preview-media-title").text(previewData.title);
	$(".preview-media-author").text(previewData.author);
	$(".preview-media-picter img").attr("src", previewData.imgPath);
	$(".preview-media-present").html(previewData.content);
	$(".preview-media-link").text(previewData.url);
	//		预览弹窗
	$("#media-preview").removeClass("hide");
	$("#mask").removeClass("hide");
}
//媒体报道部分,点击编辑
function mediaEditId(index) {
	resetMedia(index);
	echoMedia(index);
	$(".media_report_bottom").removeClass("hide");
	$(".media_report_bottom2").addClass("hide");
	$('#media_report .media_report_top .title').text('报道编辑');
	$('#media_report .media_report_top button').text('返回');
	//	保存按钮显示和隐藏
	$(".media-save-allwrap").addClass("hide");
	$(".media-save-wrap").removeClass("hide");
}

function resetMedia(index) {
	$("#mediaId").val("");
	$(".media-title").val("");
	$(".media-author").val("");
	$(".media-picter").html(defaultPicter);
	$("#media_picIpt").val("");
//	$(".media-text").val("");
	UE.getEditor('media-context').setContent(""),
	$(".media-link").val("");
	$(".warning").addClass("hide");
	$(".media-only-save").removeAttr("disabled");
}

function echoMedia(index) {
	var mediaData = getMediadata[index];
	$("#mediaId").val(mediaData.id);
	$(".media-title").val(mediaData.title);
	$(".media-author").val(mediaData.author);
	$(".media-picter").html("<img src=" + mediaData.imgPath + " />");
//	$(".media-text").val(mediaData.content);
	UE.getEditor('media-context').setContent(mediaData.content),
	$(".media-link").val(mediaData.url);
}