$(function(){
    showCourseAttribute(1);
    
    $('#anchorWorkbench').css('color','#00bc12');
    
//	左右两边tab切换
	$(".select_list li").click(function(){
		$(".select_list li").removeClass("active");
		$(this).addClass("active");
//				$(".wrap_box .little_box").hide();
//		$(".select_box").hide().eq($(this).index()).show();
//				图标颜色变化
		$(".left_range").removeClass("ino_color").eq($(this).index()).addClass("ino_color");
	})
	
	//点击出现下拉栏  学堂
	$(".select_list .select-ud").bind('click',function(event){
		event.stopPropagation();
		$(".select_list .littleBox").stop().slideToggle();
		$('.myResive').css('display','none');
		$('#mymoney').addClass('hide');
		
		
		//另两个上啦
		$(".littleBoxs").slideUp("slow");
		$(".littleBoxss").slideUp("slow");
		
		//小箭头调整
		$(".select_list .account_number .arrow_jt").removeClass("glyphicon-triangle-bottom");
		$(".select_list .account_number .arrow_jt").addClass("glyphicon-triangle-left")	;
		$(".select_list #myResive_tbl .arrow_jt").removeClass("glyphicon-triangle-bottom");
		$(".select_list #myResive_tbl .arrow_jt").addClass("glyphicon-triangle-left")	;

	})
	
	//点击出现下拉栏        收益
	$(".select_list .select-udss").bind('click',function(event){
		$('#mymoney').addClass('hide');
		$('#kecheng_Resive').removeClass('hide');
		$('.wrap_box .little_box').css('display','none');
		$('.account .account_main').css('display','none');
		$('.myResive ').css('display','block');
		$('.myResive .little_box1').css('display','block');
		event.stopPropagation();
		$(".select_list .littleBoxss").stop().slideToggle();
		
		//另两个上啦
		$(".littleBox").slideUp("slow");
		$(".littleBoxs").slideUp("slow");
		//小箭头调整
		$(".select_list .school .arrow_jt").removeClass("glyphicon-triangle-bottom");
		$(".select_list .school .arrow_jt").addClass("glyphicon-triangle-left")	;
		$(".select_list .account_number .arrow_jt").removeClass("glyphicon-triangle-bottom");
		$(".select_list .account_number .arrow_jt").addClass("glyphicon-triangle-left")	;
		
	})
	
	

	//点击出现下拉栏  账号
	$(".select_list .select-uds").bind('click',function(event){
		$('#mymoney').addClass('hide');
//		$('.account').removeClss('hide');
		$('.wrap_box .little_box').css('display','none');
		$('.myResive ').css('display','none');
		$('.account').css('display','block');
		event.stopPropagation();
		$(".select_list .littleBoxs").stop().slideToggle();
		
		
		
		//另两个上啦
		$(".littleBox").slideUp("slow");
		$(".littleBoxss").slideUp("slow");
		
		//小箭头调整
		$(".select_list .school .arrow_jt").removeClass("glyphicon-triangle-bottom");
		$(".select_list .school .arrow_jt").addClass("glyphicon-triangle-left")	;
		$(".select_list #myResive_tbl .arrow_jt").removeClass("glyphicon-triangle-bottom");
		$(".select_list #myResive_tbl .arrow_jt").addClass("glyphicon-triangle-left")	;
		
	})
	
	//点击我的资产 下拉栏上去
	$('#mymoneyTbl').click(function(){
        initBasaeAssetInfo();
		$('.wrap_box .little_box').css('display','none');
		$('.account').css('display','none');
		$('.myResive').css('display','none');
		$('#mymoney').removeClass('hide');
		$('#mymoney').removeClass('hide');
		$('#mymoney .content_mid').addClass('hide');
		$('#mymoney .changeType .pandaMoney').click();
	})

	$(".setTop").click(function(){
		$(".select_list .littleBox").slideUp();
		$(".select_list .littleBoxs").slideUp();
		$(".select_list .littleBoxss").slideUp();
		
		$(".select-ud").removeAttr("id");
		$(".select_list .arrow_jt").removeClass("glyphicon-triangle-bottom");
		$(".select_list .arrow_jt").addClass("glyphicon-triangle-left")	;
	})
//			对课程目录下小的下拉div进行操作		
	$(".select_list .select-ud .littleBox p").bind('click',function(event){
		event.stopPropagation();
		$(".select_list .littleBox p").removeClass("activeP");
		$(this).addClass("activeP");
		$(".wrap_box .little_box").hide().eq($(this).index()).show();  /*课程显示*/
		$(".account .account_mains").hide();   /*账号隐藏*/
		$(".myResive .little_box1").hide()  	/*收益隐藏*/
		$(".select_box").hide();
	})

	$(".select_list .select-uds .littleBoxs p").bind('click',function(event){
		event.stopPropagation();
		$(".select_list .littleBoxs p").removeClass("activeP");
		$(this).addClass("activeP");
		$(".account .account_mains").hide().eq($(this).index()).show();  /*账户显示*/
		$(".wrap_box .little_box").hide();   /*课程隐藏*/
		$(".myResive .little_box1").hide()  	/*收益隐藏*/
		$(".select_box").hide();
	})
	
	$(".select_list .select-udss .littleBoxs p").bind('click',function(event){
		event.stopPropagation();
		$(".select_list .littleBoxs p").removeClass("activeP");
		$(this).addClass("activeP");
		$(".myResive .little_box1").hide().eq($(this).index()).show();  /*收益显示*/
		$(".wrap_box .little_box").hide();   /*课程隐藏*/
		$(".account .account_mains").hide();   /*账号隐藏*/
		$(".select_box").hide();
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
	
	$(".select-udss").click(function(){
		if($(this).attr("id")=="open_list"){
			$(this).removeAttr("id")
			$(".select_list .select-udss .arrow_jt").addClass("glyphicon-triangle-left");
			$(".select_list .select-udss .arrow_jt").removeClass("glyphicon-triangle-bottom");
		}
		else{
			$(this).attr("id","open_list")
			$(".select_list .select-udss .arrow_jt").addClass("glyphicon-triangle-bottom");
			$(".select_list .select-udss .arrow_jt").removeClass("glyphicon-triangle-left");
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

//处理点击--课程显示隐藏第一第二页
$(".select_list .courseP").click(function() {
    $(".curriculum_two").show();
    $(".curriculum_one").hide();
});

//点击选择资源
$('#a').click(function(){
	$('.a_resource').show();
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
//	$('#zhuanji #zhuanji_bottom2').addClass('hide');
//	$('#zhuanji #zhuanji_bottom').removeClass('hide');
	}else{
		//取消上传
		$(this).text('新专辑');
		$(this).siblings('.title').text('专辑');
		//底部变化
//		$('#zhuanji  #zhuanji_bottom').addClass('hide');
//		$('#zhuanji  #zhuanji_bottom2').removeClass('hide');
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

//专辑
$('#zhuanji_bottom .baocun #submit').click(function(){
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

	var headPortrait  =  $('#zhuanji_bottom .fengmian_pic img').attr('src');
	
	
	//课程标题
	if(workhosName == ''){
		$('#zhuanji_bottom .warning0').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning0').addClass('hide');
	}
	
//	副标题
	if(workhosNames == ''){
		$('#zhuanji_bottom .warning1').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning1').addClass('hide');
	}
	
//	封面图
	if(workhosNamess == ''){
		$('#zhuanji_bottom .warning2').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning2').addClass('hide');
	}
	
//	主播姓名
	if(nameNames == ''){
		$('#zhuanji_bottom .warning3').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning3').addClass('hide');
	}
	
//	主播介绍
	if(textArea == ''){
		$('#zhuanji_bottom .warning4').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning4').addClass('hide');
	}
	
	//	专辑简介
	if(zhuanjiCounts == ''){
		$('#zhuanji_bottom .warning9').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning9').addClass('hide');
	}
	
//	专辑大纲  
	if(zhuanjiCountss == ''){
		$('#zhuanji_bottom .warning01').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning01').addClass('hide');
	}
	
//	总集数
	if(AlwaysPut == ''){
		$('#zhuanji_bottom .warning02').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning02').addClass('hide');
	}
	
	
	
	
//	请选择开课时间
	if(selectTime == ''){
		$('#zhuanji_bottom .warning5').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning5').addClass('hide');
	}
	
//	时长
	if(selectTimes == ''){
		$('#zhuanji_bottom .warning6').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning6').addClass('hide');
	}
	
//	价格
	if(price == ''){
		$('#zhuanji_bottom .warning7').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning7').addClass('hide');
	}
	
//	资源
	if(resourceUrl == ''){
		$('#zhuanji_bottom .warning8').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning8').addClass('hide');
	}
	
	
	//封面图是否上传
		if($('#zhuanji_bottom .fengmian_pic:has(img)').length < 1){
			$('#zhuanji_bottom .fengmian_pic_warn').removeClass('hide');
			return false;
		}else{
			$('#zhuanji_bottom .fengmian_pic_warn').addClass('hide');
		}
	
	
//	alert(111)
})

//保存
$('#zhuanji_bottom .baocun #submit0').click(function(){
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


	var headPortrait  =  $('#zhuanji_bottom .fengmian_pic img').attr('src');
	
	
	//课程标题
	if(workhosName == ''){
		$('#zhuanji_bottom .warning0').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning0').addClass('hide');
	}
	
//	副标题
	if(workhosNames == ''){
		$('#zhuanji_bottom .warning1').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning1').addClass('hide');
	}
	
//	封面图
	if(workhosNamess == ''){
		$('#zhuanji_bottom .warning2').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning2').addClass('hide');
	}
	
//	主播姓名
	if(nameNames == ''){
		$('#zhuanji_bottom .warning3').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning3').addClass('hide');
	}
	
//	主播介绍
	if(textArea == ''){
		$('#zhuanji_bottom .warning4').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning4').addClass('hide');
	}
	
//	专辑简介
	if(zhuanjiCounts == ''){
		$('#zhuanji_bottom .warning9').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning9').addClass('hide');
	}
	
//	专辑大纲  
	if(zhuanjiCountss == ''){
		$('#zhuanji_bottom .warning01').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning01').addClass('hide');
	}
	
//	总集数
	if(AlwaysPut == ''){
		$('#zhuanji_bottom .warning02').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning02').addClass('hide');
	}
	
	
	
//	请选择开课时间
	if(selectTime == ''){
		$('#zhuanji_bottom .warning5').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning5').addClass('hide');
	}
	
//	时长
	if(selectTimes == ''){
		$('#zhuanji_bottom .warning6').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning6').addClass('hide');
	}
	
	//	价格
	if(price == ''){
		$('#zhuanji_bottom .warning7').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning7').addClass('hide');
	}
	
//	资源
	if(resourceUrl == ''){
		$('#zhuanji_bottom .warning8').removeClass('hide');
		return false;
	}else{
		$('#zhuanji_bottom .warning8').addClass('hide');
	}
	
	
	
	//封面图是否上传
		if($('#zhuanji_bottom .fengmian_pic:has(img)').length < 1){
			$('#zhuanji_bottom .fengmian_pic_warn').removeClass('hide');
			return false;
		}else{
			$('#zhuanji_bottom .fengmian_pic_warn').addClass('hide');
		}
	
	
})

//添加课程为空开始     提交课程
$('#zhuanjis_bottom .baocun #submits').click(function(){
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
	if(LectureTitles == ''){
		$('#zhuanjis_bottom .warnings0').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings0').addClass('hide');
	}
	
	
	//	副标题
	if(LectureTitless == ''){
		$('#zhuanjis_bottom .warnings1').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings1').addClass('hide');
	}
	
//	上传图片
	if(AddpicIpt == ''){
		$('#zhuanjis_bottom .warnings2').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings2').addClass('hide');
	}
	
//	主播姓名
	if(HostName == ''){
		$('#zhuanjis_bottom .warnings3').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings3').addClass('hide');
	}
	
//	主播介绍
	if(HostIntroduced == ''){
		$('#zhuanjis_bottom .warnings4').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings4').addClass('hide');
	}
	
//	主播分类
	if(Select == ''){
		$('#zhuanjis_bottom .warnings5').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings5').addClass('hide');
	}
	
//	时长
	if(Time == ''){
		$('#zhuanjis_bottom .warnings6').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings6').addClass('hide');
	}
	
//  价格
	if(Price == ''){
		$('#zhuanjis_bottom .warnings7').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings7').addClass('hide');
	}
	
//  课程介绍
	if(CourseDescriptions == ''){
		$('#zhuanjis_bottom .warnings8').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings8').addClass('hide');
	}
	
//  选择资源
	if(Ainput == ''){
		$('#zhuanjis_bottom .warnings9').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings9').addClass('hide');
	}
	
})

//添加课程为空开始     提交课程
$('#ziyuan_bottom .baocun #submit').click(function(){
//	添加课程为空
	var ResourceTitle = $.trim($('#ziyuan_bottom .zhuanlan_title').val());
	var ResourceUploading = $.trim($('#ziyuan_bottom #picIpt3').val());
	var resourcePut = $.trim($('#ziyuan_bottom .resource_put').val());

	//课程标题
	if(ResourceTitle == ''){
		$('#ziyuan_bottom .warning0').removeClass('hide');
		return false;
	}else{
		$('#ziyuan_bottom .warning0').addClass('hide');
	}
	
	//封面图
	if(ResourceUploading == ''){
		$('#ziyuan_bottom .warning1').removeClass('hide');
		return false;
	}else{
		$('#ziyuan_bottom .warning1').addClass('hide');
	}
	
	//上传资源
	if(resourcePut == ''){
		$('#ziyuan_bottom .warning2').removeClass('hide');
		return false;
	}else{
		$('#ziyuan_bottom .warning2').addClass('hide');
	}
	
})
//资源为空结束

//添加课程为空开始     保存
$('#zhuanjis_bottom .baocun #submits0').click(function(){
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
	if(LectureTitles == ''){
		$('#zhuanjis_bottom .warnings0').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings0').addClass('hide');
	}
	
	
	//	副标题
	if(LectureTitless == ''){
		$('#zhuanjis_bottom .warnings1').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings1').addClass('hide');
	}
	
//	上传图片
	if(AddpicIpt == ''){
		$('#zhuanjis_bottom .warnings2').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings2').addClass('hide');
	}
	
//	主播姓名
	if(HostName == ''){
		$('#zhuanjis_bottom .warnings3').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings3').addClass('hide');
	}
	
//	主播介绍
	if(HostIntroduced == ''){
		$('#zhuanjis_bottom .warnings4').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings4').addClass('hide');
	}
	
//	主播分类
	if(Select == ''){
		$('#zhuanjis_bottom .warnings5').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings5').addClass('hide');
	}
	
//	时长
	if(Time == ''){
		$('#zhuanjis_bottom .warnings6').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings6').addClass('hide');
	}
	
//  价格
	if(Price == ''){
		$('#zhuanjis_bottom .warnings7').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings7').addClass('hide');
	}
	
//  课程介绍
	if(CourseDescriptions == ''){
		$('#zhuanjis_bottom .warnings8').removeClass('hide');
		return false;
	}else{
		$('#zhuanjis_bottom .warnings8').addClass('hide');
	}
	
//  选择资源
	if(Ainput == ''){
		$('#zhuanjis_bottom .warnings9').removeClass('hide');
		return false;
	}else{
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
//  $("#zhuanji_bottom2").show();
    $("#zhuanji_bottom").show();
});

//新专辑返回
$("#zhuanji_bottom .returns").click(function() {
    $("#zhuanji_bottom").hide();
    $("#zhuanji_bottom2").show();
//  $("#zhuanji_bottom").show();
});

//新专辑--添加课程
$("#zhuanjis_bottom .returns").click(function() {
    $("#zhuanji_bottom").hide();
    $("#zhuanjis_bottom").hide();
    $("#zhuanji_bottom2").show();
//  $("#zhuanji_bottom").show();
});

//点击专辑--专辑-新专辑
$("#zhuanji_bottom2 .zhuanlan_top .returns").click(function() {
//  $("#zhuanjis_bottom").hide();
    $("#zhuanji_bottom2").hide();
    $("#zhuanji_bottom").remove("hide");
});

//专辑结束

//账户认证信息为空
$('.account_two .approve').click(function(){
	//医馆认证
	
//	医馆名称
	var namePut = $.trim($('.account_two .name_put').val());
	
	//医馆名称
	if(namePut == ''){
//		$('.account_two .warning2').removeClass('hide');
//		$('.account_two #name_put').removeClass('hide_pop_up');
		return false;
	}else{
//		$('.account_two .warning2').addClass('hide');
//		$('.account_two #name_put').addClass('show_pop_up');
	}
	
})

//判断医馆认证开始
$('.account_two .approve').click(function(){
//	添加医馆名称为空
	var NameWarning = $.trim($('.account_two .name_put2').val());
	
//	添加公司名称为空
	var CompanyWarning = $.trim($('.account_two .name_put0').val());
	
//	添加公司名称为空
	var CreditWarning = $.trim($('.account_two .name_put1').val());
	
//营业执照
	var ImgWarning = $.trim($('.account_two #previewImg4').val());
	
//药品经营许可证
	var DrugWarning = $.trim($('.account_two #previewImg5').val());
	
	//医馆名称
	if(NameWarning == ''){
		$('.account_two .two_warning0').removeClass('hide');
		return false;
	}else{
		$('.account_two .two_warning0').addClass('hide');
	}
	
	//公司名称
	if(CompanyWarning == ''){
		$('.account_two .two_warning1').removeClass('hide');
		return false;
	}else{
		$('.account_two .two_warning1').addClass('hide');
	}
	
//统一社会信用代码不能为空
	if(CreditWarning == ''){
		$('.account_two .two_warning2').removeClass('hide');
		return false;
	}else{
		$('.account_two .two_warning2').addClass('hide');
	}
	
//营业执照	
	if(ImgWarning == ''){
		$('.account_two .two_warning3').removeClass('hide');
		return false;
	}else{
		$('.account_two .two_warning3').addClass('hide');
	}

//药品经营许可证
	if(DrugWarning == ''){
		$('.account_two .two_warning3').removeClass('hide');
		return false;
	}else{
		$('.account_two .two_warning3').addClass('hide');
	}
	
	
	
})
//判断医馆认证结束
})

function showCourseAttribute(type){
    $(".special").hide();
    if(type==1){
        $(".live").show();
    }else if(type==2){
        $(".vod").show();
    }else{
    	$(".offline").show();
	}
}


function confirmBox(title,content,fn){
    $(".confirm-title").html(title);
    $(".confirm-content").html(content);
    $(".confirm-sure").click(function(){
        fn(hideDel);
        $(".confirm-sure").unbind("click")
    })
    showDel();
}
//删除提示框出现方法
function showDel(){
    $('#deleteTip').removeClass('hide');
    $('#mask').removeClass('hide');
}

//删除提示消失方法
function hideDel(){
    $('#deleteTip').addClass('hide');
    $('#mask').addClass('hide');
}


//出现黑色提示弹窗方法
function showTip(contant,fn){
    $('#blackTip').text(contant).show()
    setTimeout(function(){
        $('#blackTip').text(contant).hide();
        if(fn!=null)fn();
    },2000)

}

//科室点击验证效果
	var arr = [];
	var keshiStr;
	$('.account_main_alter  .keshi ').on('click','#keshiList>li',function(){
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
