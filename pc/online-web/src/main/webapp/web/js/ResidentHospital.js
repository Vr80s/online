var userPic = $('.userPic').css('background')
//顶部的医馆按钮变色效果
$('.forum').css('color','#000');
$('.path .hospital').addClass('select');



RequestService("/online/user/isAlive", "get", null, function(data) {
			//头像预览
			if(data.resultObject.smallHeadPhoto) {
				if(data.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
					$('.doctor_inf>img').attr('src',data.resultObject.smallHeadPhoto)
				} else {
					
				}
			};
		});

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
		}
			
	});
})
	





//获取医馆认证状态控制左侧tab栏
 RequestService("/medical/common/isDoctorOrHospital","GET",null,function(data){
	       if(data.success == true ){
	       	if(data.resultObject.indexOf(2) == -1){
	       		//医馆认证未成功显示出来认证失败的页面
	       		
	       		$('#hos_Administration .hos_renzheng_inf .bottomContent').addClass('hide');
	       		$('#hos_Administration .hos_renzheng_inf .bottomContent2').removeClass('hide');
	       		
	       		if(data.resultObject.indexOf(3) != -1){
	       			//认证中
	       			$('#hos_Administration .hos_renzheng_inf .bottomContent').addClass('hide');
	       			$('#hos_Administration .hos_renzheng_inf .bottomContent2').removeClass('hide');

	       		}else if(data.resultObject.indexOf(7) != -1){
	       			//未认证
//	       			$('#docNoPass_tip').removeClass('hide');	
	       			$('#hos_Administration .hos_renzheng_inf .bottomContent2').addClass('hide');
	       			$('#hos_Administration .hos_renzheng_inf .bottomContent').removeClass('hide');
	       		}
	       	}else if(data.resultObject.indexOf(2) != -1){
	       		//医馆认证成功 左侧tab显示出来 医馆基础信息显示出来
	       		$('#doc_Administration_tabBtn').removeClass('hide');
	       		$('#hos_base_inf').removeClass('hide');
	       		$('#hos_Administration .hos_renzheng_inf .bottomContent').addClass('hide');
	       		$('#hos_Administration .hos_renzheng_inf .bottomContent2').removeClass('hide');
	       	}
	       }

	    });






$(".doctor_inf >img").attr('src',userPic)
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




function fileClick() {
	return $("#upload-file").click();
}
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



//医馆管理下拉列表功能
$('.luntan').click(function(){
	$('.luntan_list').slideToggle();
})

//左侧鼠标移动上去变色效果
$('#doctor_in_inf .news_nav ul li a').mouseenter(function(){
	$(this).children('span').css('color','#00bc12')
})
//鼠标移除
$('#doctor_in_inf .news_nav ul li a').mouseout(function(){
	$(this).children('span').css('color','#cacbcb')
})
//点击变色效果
$('#doctor_in_inf .news_nav ul li a').click(function(){
	$('#doctor_in_inf .news_nav ul li a').removeClass('color');
	$('#doctor_in_inf .news_nav ul li a > span').removeClass('color');
	$(this).addClass('color');
	$(this).children('span').addClass('color');
	
})

//点击其他的时候下拉的列表还原
$('.hos_left_list > li:nth-child(n+3)').click(function(){
	$('.luntan_list').slideUp();
})





//医馆管理部分
//医馆管理部分下拉列表点击 右侧内容对应变化
$('#hos_base_inf').click(function(){
	$('.hos_renzheng_inf').addClass('hide');
	$('.hos_base_inf ').removeClass('hide');
})

$('#hos_renzhneg_inf').click(function(){
	$('.hos_base_inf ').addClass('hide');
	$('.hos_renzheng_inf').removeClass('hide');
	
})
//内部医疗领域选择功能
//$('#hos_Administration .hos_base_inf .keshi ul li').click(function(){
//		if($(this).hasClass('keshiColor')){
//			$(this).removeClass('keshiColor')
//		}else{
//			$(this).addClass('keshiColor');
//		}
//	})


//获取医疗领域数据
RequestService("/medical/hospital/getFields/0", "get", null, function(data) {
				console.log(data);
//				 $('#doc_Distinguish .'+imgname+'').html('<img src="'+data.resultObject+'" >');
			$('#areaList').html(template('areaTpl', {item:data.resultObject.records}));
			})





//医馆基础信息上传图片调用的接口
function picUpdown(baseurl,imgname){
	RequestService("/medical/common/upload", "post", {
				image: baseurl,
			}, function(data) {
				console.log(data);
				 $('#hos_Administration .hos_base_inf  .'+imgname+'').html('<img src="'+data.resultObject+'" >');
			})
}


//医馆认证上传图片调用的接口
function picUpdown3(baseurl,imgname){
	RequestService("/medical/common/upload", "post", {
				image: baseurl,
			}, function(data) {
				console.log(data);
				 $('#hos_Administration .hos_renzheng_inf  .'+imgname+'').html('<img src="'+data.resultObject+'" >');
			})
}




//上传图片调用的接口
function picUpdown2(baseurl,imgname){
	RequestService("/medical/common/upload", "post", {
				image: baseurl,
			}, function(data) {
				console.log(data);
				if($('#hos_Administration .hos_base_inf  .'+imgname+' img').length > 1){
					$('#hos_Administration .hos_base_inf  .zhicheng_pic').css('padding-left','110px')
					 $('#hos_Administration .hos_base_inf  .'+imgname+'').css('float','right');
				}else{
					$('#hos_Administration .hos_base_inf  .'+imgname+'').css('float','none');
				}
				if($('#hos_Administration .hos_base_inf  .'+imgname+' img').length == 9){
					alert('最多上传9张');
					return false;
				}
				 $('#hos_Administration .hos_base_inf  .'+imgname+'').append('<img src="'+data.resultObject+'" >');
			})
}



//医馆头像上传
	$('#touxiang_pic_ipt').on('change',function(){
	var reader=new FileReader();
  	reader.onload=function(e){
	picUpdown(reader.result,'touxiang_pic');
	}  
	reader.readAsDataURL(this.files[0])
})
	
//医馆图片上传
	$('#zhicheng_pic_ipt').on('change',function(){
	var reader=new FileReader();
  	reader.onload=function(e){
	picUpdown2(reader.result,'zhicheng_pic');
	}  
	reader.readAsDataURL(this.files[0])
})
	
	
//医馆科室选择生成对应的数组
	var arr = [];
	var keshiStr;
	$('#hos_Administration .hos_base_inf ').on('click','#areaList>li',function(){
		if($(this).hasClass('keshiColor')){
		//删除第二次选中的
			for(var i = 0 ;i < arr.length; i++){
				if($(this).text() == arr[i]){
					arr.splice(i,1)
				}
			}
//			console.log(arr.toString())
			keshiStr = arr.toString();
			$(this).removeClass('keshiColor');	
		}else{
			$(this).addClass('keshiColor');
			arr.push($(this).text());
//			console.log(arr.toString())
			keshiStr = arr.toString();
		}
		console.log(keshiStr)
	})	
	
	
	
//此处是医馆管理 里面的医馆认证部分的功能

//营业执照图片上传
	$('#zhizhao_pic_ipt').on('change',function(){
	var reader=new FileReader();
  	reader.onload=function(e){
	picUpdown3(reader.result,'teacher_pic');
	}  
	reader.readAsDataURL(this.files[0])
})

//药品经营许可证上传
	$('#xuke_pic_ipt').on('change',function(){
	var reader=new FileReader();
  	reader.onload=function(e){
	picUpdown3(reader.result,'zhicheng_pic');
	}  
	reader.readAsDataURL(this.files[0])
})






















//	公告部分
//	公告部分点击发布效果
var NoticeCount = 1;
$('#Notice_Administration .Notice_top button').click(function(){
	NoticeCount *= -1;
	//发布
	if(NoticeCount < 0){
		//顶部变化
		$(this).text('返回');
		$(this).siblings('.title').text('新公告');
		//底部变化
		$('#Notice_bottom2').addClass('hide');
		$('#Notice_bottom').removeClass('hide');
	}else{
	//取消发布
		$(this).text('新公告');
		$(this).siblings('.title').text('公告管理');
		//底部变化
		$('#Notice_bottom').addClass('hide');
		$('#Notice_bottom2').removeClass('hide');
	}
	
})





//医师管理部分
//顶部点击切换底部内容功能
var newTeacehr = 1;
$('#doc_Administration .add_newTeacher').click(function(){
	newTeacehr *= -1;
	//发布
	if(newTeacehr < 0){
		//顶部变化
		$(this).text('返回');
		$(this).siblings('.title').text('新医师');
		//底部变化
		$('#doc_Administration_bottom2').addClass('hide');
		$('#doc_Administration_bottom').removeClass('hide');
		//搜索部分隐藏
		$('.search_teacher_ipt').addClass('hide');
		$('.search_teacher_btn').addClass('hide');
	}else{
	//取消发布
		$(this).text('新医师');
		$(this).siblings('.title').text('医师管理');
		//底部变化
		$('#doc_Administration_bottom').addClass('hide');
		$('#doc_Administration_bottom2').removeClass('hide');
		//搜索部分显示
		$('.search_teacher_ipt').removeClass('hide');
		$('.search_teacher_btn').removeClass('hide');
	}
	
})

////医师预览功能
//$('.doc_Administration_bottom2 .preview').click(function(){
//	$('#mask').removeClass('hide');
//	$('#doc_Administration_bottom3').addClass('hide');
//	$('#doc_Administration_bottom4').removeClass('hide');
//})
//
//
////医师编辑功能
//$('#doc_Administration_bottom2 .edit').click(function(){
//	$('#mask').removeClass('hide');
//	$('#doc_Administration_bottom4').addClass('hide');
//	$('#doc_Administration_bottom3').removeClass('hide');
//})
//
//
////医师编辑关闭按钮
//$('#doc_Administration_bottom4 .close_doc_inf').click(function(){
//	$('#mask').addClass('hide');
//	$('#doc_Administration_bottom4').addClass('hide');
//})
//
////医师预览关闭按钮
//$('#doc_Administration_bottom3 .close_doc_inf').click(function(){
//	$('#mask').addClass('hide');
//	$('#doc_Administration_bottom3').addClass('hide');
//})






//内部医疗领域选择功能
$('#doc_Administration  .keshi ul li').click(function(){
		if($(this).hasClass('keshiColor')){
			$(this).removeClass('keshiColor')
		}else{
			$(this).addClass('keshiColor');
		}
	})

//点击重新认证按钮的效果
function hosAgainAut(){

	$('#hos_Administration .hos_renzheng_inf .bottomContent2 ').addClass('hide');
	$('#hos_Administration .hos_renzheng_inf .bottomContent').removeClass('hide');
	
}


