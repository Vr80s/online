$(function(){
//	左右两边tab切换
			$(".select_list li").click(function(){
				$(".select_list li").removeClass("active");
				$(this).addClass("active");
				$(".wrap_box .little_box").hide();
				$(".select_box").hide().eq($(this).index()).show();
//				图标颜色变化
				$(".left_range").removeClass("ino_color").eq($(this).index()).addClass("ino_color");
			})
			$(".select_list .select-ud").bind('click',function(event){
            	event.stopPropagation();			
				$(".select_list .littleBox").stop().slideToggle();

			})
			
			
			$(".select_list .select-uds").bind('click',function(event){
            	event.stopPropagation();			
				$(".select_list .littleBoxs").stop().slideToggle();

			})
			
			
			$(".setTop").click(function(){
				$(".select_list .littleBox").slideUp();
				$(".select_list .littleBoxs").slideUp();
				
				$(".select-ud").removeAttr("id");
				$(".select_list .arrow_jt").removeClass("glyphicon-triangle-bottom");
				$(".select_list .arrow_jt").addClass("glyphicon-triangle-left")	;	
			})
//			对课程目录下小的下拉div进行操作		
			$(".select_list .select-ud .littleBox p").bind('click',function(event){
            	event.stopPropagation();
				$(".select_list .littleBox p").removeClass("activeP");
				$(this).addClass("activeP");
				$(".wrap_box .little_box").hide().eq($(this).index()).show();
				$(".select_box").hide()
			})
			
			$(".select_list .select-uds .littleBoxs p").bind('click',function(event){
            	event.stopPropagation();
				$(".select_list1 .littleBoxs p").removeClass("activeP");
				$(this).addClass("activeP");
				$(".wrap_box1 .little_box").hide().eq($(this).index()).show();
				$(".select_box1").hide();
			})
//		下拉小箭头设置	
		$(".select-ud").click(function(){
			if($(this).attr("id")=="open_list"){
				$(this).removeAttr("id")
				$(".select_list .select-ud .arrow_jt").addClass("glyphicon-triangle-left");
				$(".select_list .select-ud .arrow_jt").removeClass("glyphicon-triangle-bottom");
			}
			else{
				$(this).attr("id","open_list")
				$(".select_list .select-ud .arrow_jt").addClass("glyphicon-triangle-bottom");
				$(".select_list .select-ud .arrow_jt").removeClass("glyphicon-triangle-left");
			}
		})
		
		$(".select-uds").click(function(){
			if($(this).attr("id")=="open_list"){
				$(this).removeAttr("id")
				$(".select_list .select-uds .arrow_jt").addClass("glyphicon-triangle-left");
				$(".select_list .select-uds .arrow_jt").removeClass("glyphicon-triangle-bottom");
			}
			else{
				$(this).attr("id","open_list")
				$(".select_list .select-uds .arrow_jt").addClass("glyphicon-triangle-bottom");
				$(".select_list .select-uds .arrow_jt").removeClass("glyphicon-triangle-left");
			}
		})





	//头像上传
	var userPic = $('.userPic').css('background')
	console.log(userPic)

	RequestService("/online/user/isAlive", "get", null, function(data) {
		//头像预览
		if(data.resultObject.smallHeadPhoto) {
			if(data.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
				$('.doctor_inf>img').attr('src',data.resultObject.smallHeadPhoto)
			} else {
				
			}
		};
	});
	$(".doctor_inf >img").attr('src',userPic)
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
			$(".btn-upload").attr("data-img","");
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
			if(filepath==""){
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
			$(".btn-upload").attr("data-img",img);
		}
		$(".imageBox").on("mousemove mouseup", function() {
			getImg()
		})
	}




//function fileClick() {
//	return $("#upload-file").click();
//}
$('.fileUpdata').click(function(){
	return $("#upload-file").click();
})
$(".btn-upload").click(function(evt) {
		evt.preventDefault();
		if($(".btn-upload").attr("data-img")!=undefined&&$(".btn-upload").attr("data-img")!=""){			
		}else{
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
							$(".doctor_inf >img").attr('src',path)
							
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




	//上传封面图片
	$('.fengmian_pic').click(function(){
		$('#picIpt').click();
	})
	
	
//	专栏部分
//	专栏部分点击发布效果
var zhuanlanCount = 1;
$('#zhuanlan .zhuanlan_top button').click(function(){
	zhuanlanCount *= -1;
	//发布
	if(zhuanlanCount < 0){
		//顶部变化
		$(this).text('返回');
		$(this).siblings('.title').text('新专栏');
		//底部变化
		$('#zhuanlan_bottom2').addClass('hide');
		$('#zhuanlan_bottom').removeClass('hide');
	}else{
	//取消发布
		$(this).text('发布');
		$(this).siblings('.title').text('专栏');
		//底部变化
		$('#zhuanlan_bottom').addClass('hide');
		$('#zhuanlan_bottom2').removeClass('hide');
	}
	
})




//资源部分
//资源部分点击上传资源
var ziyuanCount = 1;
$('#resource .zhuanlan_top button').click(function(){
	ziyuanCount *= -1;
	//上传
	if(ziyuanCount < 0){
	//顶部变化
	$(this).text('返回');
	$(this).siblings('.title').text('新资源');
	//底部变化
	$('#ziyuan_bottom2').addClass('hide');
	$('#ziyuan_bottom').removeClass('hide');
	}else{
		//取消上传
		$(this).text('上传资源');
		$(this).siblings('.title').text('资源');
		//底部变化
		$('#ziyuan_bottom').addClass('hide');
		$('#ziyuan_bottom2').removeClass('hide');
	}
})


//开始js
//课程部分
//课程部分点击
var kechengCount = 1;
$('#curriculum .zhuanlan_top button').click(function(){
	kechengCount *= -1;
	//上传
	if(kechengCount < 0){
	//顶部变化
	$(this).text('返回');
	$(this).siblings('.title').text('新课程');
	//底部变化
	$('#curriculum #kecheng_bottom2').addClass('hide');
	$('#curriculum #kecheng_bottom').removeClass('hide');
	}else{
		//取消上传
		$(this).text('新课程');
		$(this).siblings('.title').text('课程');
		//底部变化
		$('#curriculum  #kecheng_bottom').addClass('hide');
		$('#curriculum  #kecheng_bottom2').removeClass('hide');
	}
})


$('#demo2').citys({
    required:false,
    nodata:'disabled',
    onChange:function(data){
        var text = data['direct']?'(直辖市)':'';
        $('#place').text('当前选中地区：'+data['province']);
        // $('#place').text('当前选中地区：'+data['province']+text+' '+data['city']+' '+data['area']);
    }
});


$('.a_resource_close').click(function(){
	$('.a_resource').hide();
});

//专辑开始
var zhuanjiCount = 1;
$('#zhuanji .zhuanlan_top button').click(function(){
	zhuanjiCount *= -1;
	//上传
	if(zhuanjiCount < 0){
	//顶部变化
	$(this).text('返回');
	$(this).siblings('.title').text('新专辑');
	//底部变化
	$('#zhuanji #zhuanji_bottom2').addClass('hide');
	$('#zhuanji #zhuanji_bottom').removeClass('hide');
	}else{
		//取消上传
		$(this).text('新专辑');
		$(this).siblings('.title').text('专辑');
		//底部变化
		$('#zhuanji  #zhuanji_bottom').addClass('hide');
		$('#zhuanji  #zhuanji_bottom2').removeClass('hide');
	}
})

// $('#demo3').citys({
//     required:false,
//     nodata:'disabled',
//     onChange:function(data){
//         var text = data['direct']?'(直辖市)':'';
//         $('#place').text('当前选中地区：'+data['province']);
//         // $('#place').text('当前选中地区：'+data['province']+text+' '+data['city']+' '+data['area']);
//     }
// });


//更新时间
$("#times_div .div_one").click(function(){
	$(this).removeClass("div_one");
	$(this).addClass("div_one0");
});

$("#times_div .div_two").click(function(){
	$("#times_div div").removeClass("div_one0");
	$("#times_div div").addClass("div_one");
});

//删除当前行
$(".tbody_tbody tr td:last-child").click(function() {
    $(this).parent().remove();
});

//点击新专辑添加课程开始

//点击添加课程
$(".add_course").click(function() {
    $(".new_box").show();
});

//关闭添加课程弹框
$(".new_box_main p").click(function() {
    $(".new_box").hide();
});
//点击新专辑添加课程结束


//添加课程开始
$(".new_box_main .size").click(function() {
    $(".adds_course").show();
});
//添加课程结束

})
