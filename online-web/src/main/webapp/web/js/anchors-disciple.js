$(function(){
//	市场点击发布/返回切换
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
    
//	选择弟子弹出框
	$(".select-pupil-btn").click(function(){
		$(".pupil-modal-wrap").removeClass("hide");
		$("#mask").removeClass("hide")
	})
//	关闭选择弟子弹窗
	$(".pupil-top").click(function(){
		$(".pupil-modal-wrap").addClass("hide");
		$("#mask").addClass("hide");
	})



//	单个点击弟子
	$(".sava-pupil li .set-lable").click(function(){
		var thatImg=$(this).find("img");
		thatImg.toggleClass("active");
	});
	
	
	
	
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

//	点击打开模态框
	$(".see-disciple").click(function(){
		$("#mask").removeClass("hide")
		$(".see-disciple-modal").removeClass("hide");
	})
//	关闭模态框
	$(".see-modal-top span img").click(function(){
		$("#mask").addClass("hide")
		$(".see-disciple-modal").addClass("hide");
	})
	


//----------------------------------我的弟子结束，问答解惑开始--------------------------------------------

//	编辑/回复
	$(".edit-question").click(function(){
		$("#mask").removeClass("hide");
		$(".answer-edit-replay").removeClass("hide");
	})
//	关闭编辑回复
	$(".edit-replay-top").click(function(){
		$("#mask").addClass("hide");
		$(".answer-edit-replay").addClass("hide");
	})


//----------------------------------问答解惑结束，师承管理开始--------------------------------------------

	//	新增招生简章/返回切换
	$(".namage-top button").click(function(){
		if($(this).text()=="新增招生简章"){
			$(".namage-list-table").addClass("hide");
			$(".recruit-students").removeClass("hide");		
			$(this).text("返回");
		}else{
			$(".namage-list-table").removeClass("hide");
			$(".recruit-students").addClass("hide");	
			$(this).text("新增招生简章");
		}
	})

//	查看弹框







})
