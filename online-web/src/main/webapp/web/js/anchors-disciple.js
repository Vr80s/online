$(function(){
	
//----------------------------------远程诊疗开始--------------------------------------------
	
//	远程诊疗点击发布/返回切换
	$(".long-range-top button").click(function(){
		if($(this).text()=="创建预约时间"){
			$(".long-range-table").addClass("hide");
			$(".establish-time-wrap").removeClass("hide");
			$(this).text("返回");
			$(this).siblings("span").text("预约时间");
			$(".new-range-btn").removeClass("hide")//显示新建时间
			$(".ruturn-edit-range").addClass("hide");//隐藏编辑的按钮
			clearRangeTime()//清空预约时间
		}else{
			$(".long-range-table").removeClass("hide");
			$(".establish-time-wrap").addClass("hide");	
			$(this).text("创建预约时间");
			$(this).siblings("span").text("远程诊疗");
//			关闭审核
			$(".appointment-details").addClass("hide")
		}
	})
//	点击审核	
	$(".to-examine").click(function(){
			$(".long-range-table").addClass("hide");
			$(".appointment-details").removeClass("hide");
			$(this).parents().find(".long-range-table").siblings(".long-range-top").find("span").text("预约详情");
			$(this).parents().find(".long-range-table").siblings(".long-range-top").find("button").text("返回");
	})
//	点击查看
	$(".see-inf-modal").click(function(){
		$(".see-btn-modal").removeClass("hide");
		$("#mask").removeClass("hide");
	})
//	关闭查看
	$(".see-disciple-top span").click(function(){
		$(".see-btn-modal").addClass("hide");
		$("#mask").addClass("hide");
	})





//	弟子报名信息弹窗	
	$(".appointment-name").click(function(){
		$(".disciple-inf-modal").removeClass("hide");
		$("#mask").removeClass("hide");
		$(".see-btn-modal").addClass("hide");  //关闭查看
	})
	
//	关闭弟子报名信息	
	$(".disciple-inf-top span").click(function(){
		$(".disciple-inf-modal").addClass("hide");
		$("#mask").addClass("hide");
	})
	
	
	
	
	
	
	
	
//----------------------------------远程诊疗介结束，师承直播开始--------------------------------------------
	
//	师承点击发布/返回切换
	$(".live-top-title button").click(function(){
		if($(this).text()=="发布"){
			$(".disciple-list-wrap").addClass("hide");
			$(".disciple-set-wrap").removeClass("hide");
			$(this).text("返回");
            resetCourseForm(true);
		}else{
			$(".disciple-list-wrap").removeClass("hide");
			$(".disciple-set-wrap").addClass("hide");	
			$(this).text("发布");
		}
	})
//	跟师直播封面上传
 	function classUpdown(baseurl, imgname) {
        RequestService("/medical/common/upload", "post", {
            image: baseurl,
        }, function (data) {
            $('.class-fengmian  .' + imgname + '').html('<img src="' + data.resultObject + '?imageMogr2/thumbnail/!260x147r'+'|imageMogr2/gravity/Center/crop/260x147"" alt="课程封面">');
        })
    }
    $('#class_picIpt').on('change', function () {
        if (this.files[0].size > 2097152) {
            $('#tip').text('上传图片不能大于2M');
            $('#tip').toggle();
            setTimeout(function () {
                $('#tip').toggle();
            }, 2000)
//			showTip("上传图片不能大于2M");
            return false;
        }
        if (!(this.files[0].type.indexOf('image') == 0 && this.files[0].type && /\.(?:jpg|png|gif)$/.test(this.files[0].name))) {
            $('#tip').text('图片格式不正确');
            $('#tip').toggle();
            setTimeout(function () {
                $('#tip').toggle();
            }, 2000)
            return false;
        }
        var reader = new FileReader();
        reader.onload = function (e) {
            classUpdown(reader.result, 'disciple-wrap-img');
        }
        reader.readAsDataURL(this.files[0])
    });

//	关闭选择弟子弹窗
	$(".pupil-top").click(function(){
		$(".pupil-modal-wrap").addClass("hide");
		$("#mask").addClass("hide");
	})

//	全选弟子
	$(".all-select-main").click(function(){
		var thatImg=$(this).find("img");
			thatImg.toggleClass("active");
			if(thatImg.hasClass("active")==true){
				$(".sava-pupil li p img").addClass("active");
			}else{
				$(".sava-pupil li p img").removeClass("active");
			}
	});

//----------------------------------跟师直播介结束，我的弟子开始--------------------------------------------



//----------------------------------问答解惑结束，师承管理开始--------------------------------------------

	//	新增招生简章/返回切换
	$(".namage-top button").click(function(){
		if($(this).text()=="新增招生简章"){
			$(".namage-list-table").addClass("hide");
			$(".recruit-students").removeClass("hide");		
			$(this).text("返回");
			
			$(".teacher-name").val(anchors);
			clearRecruit();
			$(".recruit-text-up").removeClass("hide");
			$(".edit-save").addClass("hide");
		}else{
			$(".namage-list-table").removeClass("hide");
			$(".recruit-students").addClass("hide");	
			$(this).text("新增招生简章");
		}
	})


//	师承封面
	function manageUpdown(baseurl, imgname) {
        RequestService("/medical/common/upload", "post", {
            image: baseurl,
        }, function (data) {
            $('.comment-set-rules  .' + imgname + '').html('<img src="' + data.resultObject + '?imageMogr2/thumbnail/!260x147r'+'|imageMogr2/gravity/Center/crop/260x147"" alt="课程封面">');
        })
    }
    $('#manage_picIpt').on('change', function () {
        if (this.files[0].size > 2097152) {
            $('#tip').text('上传图片不能大于2M');
            $('#tip').toggle();
            setTimeout(function () {
                $('#tip').toggle();
            }, 2000)
//			showTip("上传图片不能大于2M");
            return false;
        }
        if (!(this.files[0].type.indexOf('image') == 0 && this.files[0].type && /\.(?:jpg|png|gif)$/.test(this.files[0].name))) {
            $('#tip').text('图片格式不正确');
            $('#tip').toggle();
            setTimeout(function () {
                $('#tip').toggle();
            }, 2000)
            return false;
        }
        var reader = new FileReader();
        reader.onload = function (e) {
            manageUpdown(reader.result, 'mamage-wrap-img');
        }
        reader.readAsDataURL(this.files[0])
    });

//	相关介绍富文本
var ue = UE.getEditor('about-introduce', {
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
        initialFrameHeight: 220,
        elementPathEnabled: false,
        autoHeightEnabled: false,
        autoFloatEnabled: true,
        enableAutoSave: false,
        imagePopup: false,
        maximumWords: 3000 //允许的最大字符数
    });

//	招生简介
var ue = UE.getEditor('introduction-enrolment', {
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
        initialFrameHeight: 220,
        elementPathEnabled: false,
        autoHeightEnabled: false,
        autoFloatEnabled: true,
        enableAutoSave: false,
        imagePopup: false,
        maximumWords: 3000 //允许的最大字符数
    });

})
