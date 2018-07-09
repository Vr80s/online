var getAnchorsId;
$(function () {
	
 		RequestService("/medical/common/getDoctorByUserId", "get", null, function (data) {
        	if (data.success==true) {
        		getAnchorsId=data.resultObject.doctorId
        		
        	} else{
        		showTip("获取医师ID失败");
        	}
        },false);
        
    $("#docOrHos").css({"color": "#2cb82c"})
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
    setTimeout(function () {
    	if (localStorage.docTblSta == 'doc_dynamic' || localStorage.docTblSta== null) $('.select_list li:first-child').click();
    	if (localStorage.docTblSta == 'doc_banner') $('.select_list li:nth-child(2)').click();
    	
        if (localStorage.docTblSta == 'doc_hos') $('.select_list li:nth-child(3)').click();
        if (localStorage.docTblSta == 'doc_zhuanlan') $('.select_list li:nth-child(4)').click();
        if (localStorage.docTblSta == 'doc_book') $('.select_list li:nth-child(5)').click();
        if (localStorage.docTblSta == 'doc_media') $('.select_list li:nth-child(6)').click();
        if (localStorage.docTblSta == 'doc_admit') $('.select_list li:nth-child(7)').click();
    }, 100)



    //重新认证按钮
    $('#doc_Distinguish ').on("click", ".renzhengAgain", function () {
        localStorage.AutStatus = "AutAgain";
        window.location.href = "/web/html/ResidentDoctor.html";
    })

    //	点击左侧tab按钮右侧页面变化效果
    $(".select_list li").click(function () {
    	var navText=$(this).find("span").text();
        $(".select_list li").removeClass("active");
        $(this).addClass("active");
        $(".wrap_box .little_box").hide()
        $(".select_box").hide().eq($(this).index()).show();

        window.localStorage.docTblSta = $(this).attr('data-name')

        //判断顶部是否具有返回
        if ($(".select_box").eq($(this).index()).find('.changeStaBtn').text() == '返回') {
            $(".select_box").eq($(this).index()).find('.changeStaBtn').click();
        }
        //图标颜色变化
        $(".left_range").removeClass("ino_color").eq($(this).index()).addClass("ino_color");
        
		//小箭头
 		$(".select_list .arrow_jt").attr("src","/web/images/icon-select-right.png"); 	
 		
// 		点击后加载数据
 			if(navText=="我的动态"){	
 			newsList(1,getAnchorsId);
	 		}else if(navText=="轮播图设置"){
	 			bannerList(1);
	 		}else if(navText=="医案专栏"){
	 			columnList(1);
	 		}else if(navText=="著作"){
	 			    workList(1);
	 		}else if(navText=="媒体报道"){
	 			    mediaList(1);
	 		}
    })

    $(".select_list .select-ud").bind('click', function (event) {
        event.stopPropagation();
        $(".select_list .littleBox").stop().slideToggle();

    })

    $(".setTop").click(function () {
        $(".select_list .littleBox").slideUp()
        $(".select-ud").removeAttr("id")
        $(".select_list .arrow_jt").removeClass("glyphicon-triangle-bottom")
        $(".select_list .arrow_jt").addClass("glyphicon-triangle-left")
    })
    //对课程目录下小的下拉div进行操作
    $(".select_list .littleBox p").bind('click', function (event) {
        event.stopPropagation();
        $(".select_list .littleBox p").removeClass("activeP");
        $(this).addClass("activeP");
        $(".contentWrap .little_box").hide().eq($(this).index()).show();
    })

    //		下拉小箭头设置
    $(".select-ud").click(function () {
        if ($(this).attr("id") == "open_list") {
            $(this).removeAttr("id")
            $(".select_list .arrow_jt").attr("src","/web/images/icon-select-right.png")

        } else {
            $(this).attr("id", "open_list")
             $(".select_list .arrow_jt").attr("src","/web/images/icon-select-down.png")

        }
    })

    //头像上传
    var userPic = $('.userPic').css('background')
    console.log(userPic)

    RequestService("/online/user/isAlive", "get", null, function (data) {
        //头像预览
        if (data.resultObject.smallHeadPhoto) {
            if (data.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
                //头像
                $('.doctor_inf>img').attr('src', data.resultObject.smallHeadPhoto);
                //名字
                $('.doctor_inf>h4').text(data.resultObject.name);

//				if(data.resultObject.info) {
//					//个性签名
//					$('.doctor_inf>p').text(data.resultObject.info)
//				}
            } else {

            }
        }
        ;
    });

    $(".doctor_inf >img").attr('src', userPic)
    $(".doctor_inf > img,.doctor_inf .picModal").on("click", function () {
        $(".mask").css("display", "block");
        $("#headImg").css("display", "block");
        $("body").css("overflow", "hidden");
        //清空右侧小图片
        //						$('.cropped-con').html('');
        RequestService("/online/user/isAlive", "get", null, function (data) {
            var path;
            //头像预览
            if (data.resultObject.smallHeadPhoto) {
                if (data.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
                    path = data.resultObject.smallHeadPhoto;
                } else {
                    path = bath + data.resultObject.smallHeadPhoto
                }
            }
            ;
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

        $(".imgClose,.btn-quit").click(function () {
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
        $('#upload-file').on('change', function () {
            var filepath = $(this).val();
            if (filepath == "") {
                return false;
            }
            var extStart = filepath.lastIndexOf(".");
            var ext = filepath.substring(extStart, filepath.length).toUpperCase();
            if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
                //							layer.msg("图片限于bmp,png,gif,jpeg,jpg格式", {
                //								icon: 2
                //							});
                $(".rrrrTips").text("图片限于bmp,png,gif,jpeg,jpg格式").css("display", "block");
                var file = document.getElementById("upload-file");
                clearFileInput(file);
                setTimeout(function () {
                    $(".rrrrTips").css("display", "none");
                }, 1500);
            } else if (($("#upload-file").get(0).files[0].size / 1024 / 1024) > 1) {
                $(".rrrrTips").text("图片大小不超过1M").css("display", "block");
                var file = document.getElementById("upload-file")
                clearFileInput(file);
                setTimeout(function () {
                    $(".rrrrTips").css("display", "none");
                }, 1500);
            } else {
                if (filepath) {
                    $(".img-box1").css("display", "none");
                    $(".imageBox").css("display", "block");
                    $(".tc").css("display", "block");
                    var reader = new FileReader();
                    reader.onload = function (e) {
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

        $(".imageBox").on("mousemove mouseup", function () {
            getImg()
        })
    }

    //function fileClick() {
    //	return $("#upload-file").click();
    //}
    $('.fileUpdata').click(function () {
        return $("#upload-file").click();
    })
    $(".btn-upload").click(function (evt) {
        evt.preventDefault();
        if ($(".btn-upload").attr("data-img") != undefined && $(".btn-upload").attr("data-img") != "") {
        } else {
            $(".rrrrrTips").text("请选择图片").css("display", "block");
            setTimeout(function () {
                $(".rrrrrTips").css("display", "none");
            }, 1500);
            return false;
        }
        $(".btn-upload").css("color", "white");
        //	if($(".upload_pictures_bottom_upload").attr("data-id") && $(".upload_pictures_bottom_upload").attr("data-id") != '/webview/images/usershow/defaultHeadImg.jpg') {
        RequestService("/online/user/updateHeadPhoto", "post", {
            image: $(".btn-upload").attr("data-img"),
        }, function (data) {
            if (data.success == true) {
                RequestService("/online/user/isAlive", "get", null, function (t) {
                    var path;
                    if (t.resultObject.smallHeadPhoto) {
                        if (t.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
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
//---------------------------------头像上传部分结束,动态开始---------------------------------  


function isBlank(str){
	return str == "" || str == null;
}
var activityType;

//	发布动态验证
	function checkContent(data){
	//	普通动态
		if($(".photo-wrap").hasClass("hide")==true && $(".video-wrap").hasClass("hide")==true && $(".consilia-wrap").hasClass("hide")==true){
			if(isBlank(data.content)){
				$(".activity-error").removeClass("hide");
				return false;
			}else{
				$(".activity-error").addClass("hide");
				activityType=1;
			}
	//	图片动态
		}else if($(".photo-wrap").hasClass("hide")==false && $(".video-wrap").hasClass("hide")==true && $(".consilia-wrap").hasClass("hide")==true){
				$(".activity-error").addClass("hide");
				activityType=2;
	//	视频动态
		}else if($(".photo-wrap").hasClass("hide")==true && $(".video-wrap").hasClass("hide")==false && $(".consilia-wrap").hasClass("hide")==true){
	//		视频videoFinsh
			if(isBlank(saveVideoId)){
				$(".video-null").removeClass("hide");
				return false;			
			}else if(videoFinsh==1){
				showTip("视频上传中，请稍候！");
				$(".video-null").addClass("hide");
				return false;
			}else if(videoFinsh==2){
				$(".video-null").addClass("hide");	
				
			}
			
	//		标题
			$(".activity-error").addClass("hide");
			if(data.title == ""){
				$(".video-error").removeClass("hide");
				return false;
			}else{
				$(".video-error").addClass("hide");			
			}
	//		封面
			if(isBlank(data.coverImg)){
				$(".video-fengmian-error").removeClass("hide");
				return false;
			}else{
				$(".video-fengmian-error").addClass("hide");
			}
			activityType=3;
	//	医案
		}else if($(".photo-wrap").hasClass("hide")==true && $(".video-wrap").hasClass("hide")==true && $(".consilia-wrap").hasClass("hide")==false){
				if($(".result-list .select-text img").length==0){
					$(".article-null").removeClass("hide");
					return false;
				}else{
					$(".article-null").addClass("hide");
				}		
			activityType=4;
		}
		return true;
	}
//	获取参数值
	var naturalWidth=[],
		naturalHeight=[];
	function getPostData(){
		var data = {};
		data.type = activityType;
		data.content = $.trim($(".publish-activity").val());
		data.title = $.trim($(".video-title input").val());
		data.coverImg = $(".video-cover-pic img").attr("src");	
		var imgs = [];
		$(".save-photo ul li .insertImg").each(function(index){
			imgs.push($(this).attr("src")+"?"+"w"+"="+naturalWidth[index]+"&"+"h"+"="+naturalHeight[index]);
		})
		data.pictures = imgs.join("@#$%&*!");
		data.video = saveVideoId;
		
	//	专栏文章
		data.articleId=$(".result-list .select-text img").parent().attr("data-id");
		return data;
	}
//	点击发布动态
	$(".btn-deliver").click(function(){	
			getPicter();
		var post = getPostData();
		if(checkContent(post)){	
			
			$(".btn-deliver").attr("disabled","disabled");
		 	post.type = activityType;
			RequestService("/doctor/posts", "post", post , function (data) {
	        	if(data.success==true){
	        		showTip("发布成功");
	        		$(".btn-deliver").removeAttr("disabled");
	        		newsList(1,getAnchorsId);
	        		closeImages();		//关闭图片
	        		closeConsilia();	//关闭文章
	        		closeVideo();		//关闭视频并重置
	        		clearTextarea();	//清空顶部动态文本       		
	        		clearConsilia();	//清空文章  
	        		activityTabClass(); //动态tab颜色
	        		saveVideoId="";    	//清空视频Id
	        	}else{
	        		$(".btn-deliver").removeAttr("disabled");
	        	}
	        })
	
		}
	})
//	获取图片宽、高
	function getPicter(){		
		$('.insertImg').each(function(index){
				naturalWidth[index] = $(this)[0].naturalWidth;
   				naturalHeight[index] = $(this)[0].naturalHeight;
// 				
		})
	}

//  视频/图片/医案tab颜色变化
	function activityTabClass(){
		if($(".photo-wrap").hasClass("hide")==true && $(".video-wrap").hasClass("hide")==true && $(".consilia-wrap").hasClass("hide")==true){
		    $(".activity-nav li").removeClass("active");
		}else if($(".photo-wrap").hasClass("hide")==false && $(".video-wrap").hasClass("hide")==true && $(".consilia-wrap").hasClass("hide")==true){
		    $(".activity-nav li").removeClass("active");
		    $(".activity-nav li:first").addClass("active");
		}else if($(".photo-wrap").hasClass("hide")==true && $(".video-wrap").hasClass("hide")==false && $(".consilia-wrap").hasClass("hide")==true){
		    $(".activity-nav li").removeClass("active");
		    $(".activity-nav li:nth-child(2)").addClass("active");
		}else if($(".photo-wrap").hasClass("hide")==true && $(".video-wrap").hasClass("hide")==true && $(".consilia-wrap").hasClass("hide")==false){
		    $(".activity-nav li").removeClass("active");
		    $(".activity-nav li:nth-child(3)").addClass("active");
		}
	}

//	点击图片
    $(".open-photo").click(function(){
    	$(".comment-wrong").addClass("hide");
    	if($(".video-wrap").hasClass("hide")==false){
      		comfirmBox.open("标题","确定放弃视频编辑吗？",function(closefn){
      			$('#photo_picIpt').click();
      			$(".activity-nav li").removeClass("active");
      			closeVideo();
				closefn();
				clearTextarea()   //清空发布状态
				return
			});
    	}else if($(".consilia-wrap").hasClass("hide")==false){
    		comfirmBox.open("标题","确定放弃医案编辑吗？",function(closefn){
    			$('#photo_picIpt').click();
    			$(".activity-nav li").removeClass("active");
    			closeConsilia();
    			closefn();
    			clearTextarea()   //清空发布状态
    			return
			});
    	}else{
    		$('#photo_picIpt').click();
    	}

    })
//	点击视频
	$(".vedio-nav").click(function(){
	$(".comment-wrong").addClass("hide");
	if($(".photo-wrap").hasClass("hide")==false){
    		comfirmBox.open("标题","确定放弃图片编辑吗？",function(closefn){
    			closeImages();
    			$(".video-wrap").removeClass("hide");
    			activityTabClass();
				closefn();
				clearTextarea()   //清空发布状态
			});
    	}else if($(".consilia-wrap").hasClass("hide")==false){
    		comfirmBox.open("标题","确定放弃医案编辑吗？",function(closefn){
				closeConsilia()
				
				$(".video-wrap").removeClass("hide");
				activityTabClass()
				closefn();
				clearTextarea()   //清空发布状态
			});
    	}else{
    			$(".video-wrap").removeClass("hide");
    			activityTabClass()
//				$(".video-title input").val("");
    	}
})
// 	点击医案  
	$(".consilia-nav").click(function(){
		$(".comment-wrong").addClass("hide");  //清除提示
		$(".add-consilia").click();      //指向医案
		if($(".photo-wrap").hasClass("hide")==false){
	    		comfirmBox.open("标题","确定放弃视频图片吗？",function(closefn){
	    			closeImages();
	    			
	    			$(".consilia-wrap").removeClass("hide");
	    			activityTabClass()
					closefn();
					clearConsilia();  //清空文章
					clearTextarea()   //清空发布状态
				});
	    	}else if($(".video-wrap").hasClass("hide")==false){
	    		comfirmBox.open("标题","确定放弃视频编辑吗？",function(closefn){
	      			closeVideo();
	      			
	      			$(".consilia-wrap").removeClass("hide");
	      			activityTabClass()
					clearConsilia();  //清空文章
					closefn();
					clearTextarea()   //清空发布状态
				});
	    	}else{
	    			$(".consilia-wrap").removeClass("hide");
	    			activityTabClass()
	
	    	}
	})  

//  关闭图片
	$(".photo-number img").click(function(){
		closeImages();
		activityTabClass();
	})
//	关闭视频
	$(".close-video").click(function(){
		closeVideo();
		activityTabClass();
	})
//	关闭医案
	$(".close-consilia").click(function(){
		closeConsilia();
		activityTabClass();
	})

	

//	上传动态图片
    function activityUpPhoto(baseurl, imgname) {
        RequestService("/medical/common/upload", "post", {
            image: baseurl,
        }, function (data) {
        		$(".photo-wrap").removeClass("hide");
        	  var addPhoto='<li>'+
					'<img class="insertImg" src="' + data.resultObject + '?imageMogr2/thumbnail/!300x300r'+'|imageMogr2/gravity/Center/crop/300x300" alt="照片" />'+
					'<p><img class="img-number" src="/web/images/delete-img.png" alt="删除照片" title="删除照片" /></p>'+
				'</li>';
				$('.save-photo ul').append(addPhoto); 
				photoNumber();
				activityTabClass();
				
        })
    }
    $('#photo_picIpt').on('change', function () {
    	if($(".save-photo .img-number").length==9){
    		showTip("最多发表9张图片");
    		return false;
    	}
        if (this.files[0].size > 2097152) {
            $('#tip').text('上传图片不能大于2M');
            $('#tip').toggle();
            setTimeout(function () {
                $('#tip').toggle();
            }, 2000)
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
            activityUpPhoto(reader.result, 'open-photo');
        }
        reader.readAsDataURL(this.files[0])
        $('#photo_picIpt').val("");
    })

//	图片数量
	function photoNumber(){
			var photoNumber=$(".save-photo .img-number").length;
			$(".photo-number p span").html(photoNumber);
	} 

//	删除图片
	$('.save-photo').on('click', 'p', function() {
		$(this).parent().remove();
		if($(".save-photo ul li").length==0){
			closeImages();
			activityTabClass();

		}
		photoNumber()
	})

//	视频封面
 	function videoUpdown(baseurl, imgname) {
        RequestService("/medical/common/upload", "post", {
            image: baseurl,
        }, function (data) {
            $('.video-cover  .' + imgname + '').html('<img src="' + data.resultObject + '" >');
        })
    }
    $('#video_picIpt').on('change', function () {
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
            videoUpdown(reader.result, 'video-cover-pic');
        }
        reader.readAsDataURL(this.files[0])
    });

//	添加医案/添加专栏/添加媒体选项卡
	$(".consilia-status-nav li").click(function(){
	var typeId=$(this).attr("data-type");
	if($(this).hasClass("active")==false){
		clearConsilia();
		$(".article-null").addClass("hide")	         //隐藏文章提示
		if(typeId==8){
			$(".consilia-search-wrap input").attr("placeholder","搜索医案标题").val("");

		}else if(typeId==4){
			$(".consilia-search-wrap input").attr("placeholder","搜索专栏标题").val("");

		}else if(typeId==7){
			$(".consilia-search-wrap input").attr("placeholder","搜索媒体报道标题").val("");

		}else if(typeId==9){
			$(".consilia-search-wrap input").attr("placeholder","搜索著作标题").val("");

		}			
	}
	$(".consilia-status-nav li").removeClass("active");
	$(this).addClass("active");

	$(".search-input img").click();
	
});
//	医案搜索
	$(".search-input img").click(function(){
	var searchId=$(".consilia-status-nav ul .active").attr("data-type");
	var keyword=$(".search-input input").val();
		$(".article-null").addClass("hide")	         //隐藏文章提示
		 RequestService("/doctor/article/list", "GET", {
           "type":searchId,
           "keyword":keyword
        }, function (data) {
        	if (data.success==true) {
        		var searchData=data.resultObject.records;
//      		判断索搜到的条数
//      		$(".consilia-result .search-keyword").text(keyword);
        		if(isBlank(data.resultObject.total)){
        			$(".consilia-result .number-search").text("0");
        		}else{
        			$(".consilia-result .number-search").text(data.resultObject.total);
        		}
//      		判断索搜到的条数
        		$(".consilia-result").removeClass("hide");
        		if(isBlank(searchData)){
        			$(".result-list table").addClass("hide");
        		}else{
    				$("#all-article-list").html(template("template-list",{items:searchData}))
    				$(".result-list table").removeClass("hide");
        		}
        	} else{
        		showTip("搜索失败");
        	}
        	selectSearch();		//选择要发布的文章
        })
})
//	搜索出来的结果选择要发布的内容
	function selectSearch(){
	$(".select-text").click(function(){
		if($(this).find("img").length!=0){
			$(".select-text").html("").css({"border-color":"#bbb9b9"});
	
		}else{
			$(".select-text").html("").css({"border-color":"#bbb9b9"});
			$(this).append('<img src="/web/images/submit.png"/>').css({"border-color":"#35b658"});	
		}
		
	})
}

//	控制阅读更多
	function controllerText (){
	var $dot5 = $('.show-text');
    $dot5.each(function () {
        if ($(this).height() > 96) {
            $(this).attr("data-txt", $(this).attr("data-text"));
            $(this).height(96);
            $(this).append('<span class="qq" style="margin-right:60px"> <a class="toggle" href="###" style="color:#2cb82c"><span class="opens" style="font-size:16px;">展开<span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span></span><span class="closes">收起<span class="glyphicon glyphicon-menu-up" aria-hidden="true"></span></span></a></span>');
        }
        var $dot4 = $(this);

        function createDots() {
            $dot4.dotdotdot({
                after: 'span.qq'
            });
        }
        function destroyDots() {
            $dot4.trigger('destroy');
        }

        createDots();
        $dot4.on(
            'click',
            'a.toggle',
            function () {
                $dot4.toggleClass('opened');

                if ($dot4.hasClass('opened')) {
                    destroyDots();
                } else {
                    createDots();
                }
                return false;
            }
        );
    });
}

template.config("escape", false);

//	动态列表	
var posts;
	function newsList(pages,getAnchorsId){
	 RequestService("/doctor/posts", "GET", {
            "pageNumber" : pages,
            "pageSize" : 10,
            "doctorId" :getAnchorsId
        }, function (data) {
        	if(data.success==true){
        		posts=data.resultObject.records;
        		
        		if(isBlank(posts)){
        			$(".banner-dongtai").removeClass("hide");
        		}else{
        			$(".banner-dongtai").addClass("hide");
        			for(var i=0;i<posts.length;i++){
//						处理图片动态
						if(posts[i].imgStr!=null){
							for(var j=0; j<posts[i].imgStr.length;j++){
								posts[i].imgStr[j].imgUrl=posts[i].imgStr[j].imgUrl+"?"+"imageMogr2/thumbnail/!300x300r"+"|"+"imageMogr2/gravity/Center/crop/300x300"
							}
						}	
//						处理文章封面图
						if(posts[i].articleImgPath!=null){
								posts[i].articleImgPath=posts[i].articleImgPath+"?"+"imageMogr2/thumbnail/!250x140r"+"|"+"imageMogr2/gravity/Center/crop/250x140"
						}
//						处理课程封面图
						if(posts[i].smallImgPath!=null){
								posts[i].smallImgPath=posts[i].smallImgPath+"?"+"imageMogr2/thumbnail/!250x140r"+"|"+"imageMogr2/gravity/Center/crop/250x140"
						}
//      				给点赞加个字段
        				posts[i].postsLikes="";
        				var fabulous= "";
        				    for(var j=0;j<posts[i].doctorPostsLikeList.length;j++){
		        				if(!isBlank(posts[i].doctorPostsLikeList[j].userName)){
		        					fabulous+=posts[i].doctorPostsLikeList[j].userName+"，";
		        				}
		        				
	        				}
        				    posts[i].postsLikes=fabulous.substr(0,fabulous.length-1);;  

//      				视频封面图	  

        			}
        			
        		}
        		$("#comment-text-wrap").html(template("newsTemplate", {items:posts}));
        		$(".consilia-text img").remove();
              	controllerText();   //获取文本后的展示更多            
//            	 分页
              	 if (data.resultObject.pages > 1) { //分页判断
                    $(".not-data").remove();
                    $(".activity_pages").removeClass("hide");
                    $(".activity_pages .searchPage .allPage").text(data.resultObject.pages);  //传的页数的参数
                    $("#Pagination_activity").pagination(data.resultObject.pages, {			//传的页数的参数
                        num_edge_entries: 1, //边缘页数
                        num_display_entries: 4, //主体页数
                        current_page: pages - 1,  //共几页
                        callback: function (page) {
                            //翻页功能
                            //newsList(page + 1);
                            newsList(page + 1,getAnchorsId);
                        }
                    });
                } else {
                    $(".activity_pages").addClass("hide");
                }
                showVideos();
//             分页结束
        	}else{
        		showTip("获取动态列表失败")
        	}
        	isZhiding();		//置顶
        	deleteActivity();	//删除
        	editVersion();		//编辑内容弹出框
        	editFinish();		//编辑内容完成
        	anchorReply();		//回复弹出框
        	myFabulous();		//点赞
        	finishReply();		//完成回复按钮
        	btnColorReply();	//回复颜色按钮
        	deleteReply();		//删除回复按钮
        })
}
//  视频

	function showVideos(){
		$("#comment-text-wrap .save-video-wrap").each(function(index){
			var that = $(this);
			var videoId = that.attr("data-video");
			var dataCoverimg=that.attr("data-coverimg");
			RequestService("/online/vedio/getPlayCodeByVideoId", "GET", {
				width: 920,
				height: 520,
				directId:videoId,
				autoPlay: false
			}, function(data) {
				if(data.success == true) {									
					var playCodeStr = data.resultObject;
		            var playCodeObj = JSON.parse(playCodeStr);
		            var playCode = playCodeObj.video.playcode;
		            playCode = playCode.replace("playertype=1","playertype=1&img_path="+dataCoverimg+"");
					that.html(playCode);	
				}
			})
		});
	}

//	function showVideos(){
//		$("#comment-text-wrap .save-video-wrap").each(function(){
//			var that = $(this);
//			var videoId = that.attr("data-video");
//			RequestService("/online/vedio/getVideoPlayCodeByVideoId", "GET", {
//	            videoId: videoId,
//				width: "920",
//				height: "520",
//				autoPlay: false
//			}, function(data) {
//				if(data.success == true) {
//					var videoStr = data.resultObject.replace('<param name="allowScriptAccess" value="always" />','<param name="allowScriptAccess" value="always" /><param name="flashvars" value="img_path=https://file.ipandatcm.com/18614173351/de0d6171e55c2ae1.jpg">');
//					that.html(videoStr);						
//				} else if(data.success == false) {
//					alert("播放发生错误，请清除缓存重试");
//				}
//			});
//		});
//	}
//	是否置顶
	function isZhiding(){
	$(".host-top").click(function(){
		var id=$(this).attr("data-id"),
			stick=$(this).attr("data-stick");
		RequestService("/doctor/posts/"+id+"/"+stick, "post",null, function (data) {
        	if(data.success == true){
        		showTip(data.resultObject);
        		newsList(1,getAnchorsId);;
        	}else{
        		showTip(data.resultObject);
        	}
        })
			
	})
}
//	删除
	function deleteActivity(){
		$(".delete-activity").click(function(){
			var id=$(this).attr("data-id")
			RequestService("/doctor/posts/"+id, "delete", null , function (data) {
	        	if(data.success == true){
	        		showTip(data.resultObject);
	        		//newsList(1);
	        		newsList(1,getAnchorsId);
	        	}else{
	        		showTip(data.resultObject);
	        	}
	        })
				
		})
	}
//	编辑内容
	function editVersion(){
		$(".edit-version").click(function(){
		var that=$(this);
		var isEditVersion = that.parent().parent().parent().siblings(".edit-content");
		var textHide = that.parent().parent().parent().siblings(".news-text-save").find(".show-text");
		var contentId=that.attr("data-id");
		if(isEditVersion.hasClass("hide")){
			RequestService("/doctor/posts/"+contentId, "GET", null , function (data) {
				if(data.success==true){
					isEditVersion.find("textarea").val(data.resultObject.content)
					that.text("取消编辑");
					isEditVersion.removeClass("hide");
					textHide.addClass("hide");
				}
				
			})
		}else{
			that.text("编辑内容");
			isEditVersion.addClass("hide");
			textHide.removeClass("hide");
		}	
	})
	}

//	编辑内容后点击完成
	function editFinish(){
		$(".edit-content button").click(function(){
		    var data = {};
            data.id=$(this).attr("data-id"),
                data.type=$(this).attr("data-type"),
                data.content=$(this).siblings("textarea").val();

		RequestJsonService("/doctor/posts", "PUT",JSON.stringify(data) , function (data) {
        	if (data.success=true) {
        		showTip(data.resultObject);
        		$(".edit-content").addClass("hide");
        		//newsList(1);
        		newsList(1,getAnchorsId);
        		editVersion();
        	} else{
        		showTip(data.resultObject);
        		editVersion();
        	}
        })
		
	})
	}
//	清空编辑内容的文本
	function clearEditText(){
		$(".edit-content textarea").val("");
	}

//	点赞
	function myFabulous(){
		$(".fabulous-box .fabulous").click(function(){
			var postsId = $(this).attr("data-id");
			var fabulousBox=$(this).parent();
			if($(this).hasClass("active")){			
					RequestService("/doctor/posts/"+postsId+"/like/"+0, "POST", null , function (data) {
		        	if(data.success==true){
		        		var like = {postId:postsId};
		        		like.list = data.resultObject.list;
		        		like.postsLikes="";
        					var fabulous= "";
			        		for(var i=0;i<like.list.length;i++){
			        			like.postsLikes
			        			if(!isBlank(like.list[i].userName)){
		        					fabulous+=like.list[i].userName+"，";
		        				}
			        			
			        		}
			        		
			        		like.postsLikes=fabulous.substr(0,fabulous.length-1);
						fabulousBox.html(template("fabulous-template", {like:like}));
						myFabulous();
						
		        	}else{
		        		showTip("取消失败");
		        	}
		        })
			}else{
					RequestService("/doctor/posts/"+postsId+"/like/"+1, "POST", null , function (data) {
			        if(data.success==true){
			        		var like = {postId:postsId};
			        		like.list = data.resultObject.list;		        	
			        		like.praise = data.resultObject.praise;

			        		
			        		
			        		like.postsLikes="";
        					var fabulous= "";
			        		for(var i=0;i<like.list.length;i++){
			        			like.postsLikes
			        			if(!isBlank(like.list[i].userName)){
		        					fabulous+=like.list[i].userName+"，";
		        				}
			        			
			        		}
			        		
			        		like.postsLikes=fabulous.substr(0,fabulous.length-1);
			        		
							fabulousBox.html(template("fabulous-template", {like:like}));
							myFabulous();
			        	}else{
			        		showTip("点赞失败");
			        	}
		        })
			}
			
			
		})
	}

//	input框前面回复文字设置
	function anchorReply(){				
		$(".anchor-reply").unbind("click");
		$(".anchor-reply").click(function(){
			var btnReply=$(this).parent().parent().siblings(".reply-user-wrap");
		//		
			btnReply.toggleClass("hide");
			var inputWidth=btnReply.find(".reply-bottom-wrap").width()-btnReply.find("span").width();
			btnReply.find("input").css({"width":inputWidth-10+"px"})
		})
	}
//	回复有字后,按钮变绿
function btnColorReply(){
//	$(".reply-content").unbind("keyup");
	$(".reply-content").keyup(function(){
		var $this = $(this);
		var commentContent = $(this).val();
		var aaa = $this.parent().siblings(".reply-finish").children("button");
		if (commentContent != null && commentContent != "" && commentContent.trim().length > 0) {
			aaa.attr("disabled", false);
			aaa.css("background",  "#00BC12");
		}else{
			aaa.attr("disabled", true);
			aaa.css("background",  "#999");
		}
	})
}

//	input点击完成回复
	function finishReply(){		
		$(".reply-finish button").unbind("click");
		$(".reply-finish button").click(function(){		
			var that=$(this),
				postsId=that.attr("data-id"),
				commentId=that.attr("data-comid"),
				replyBox=that.parent().parent().parent().parent();
				content=that.parent().siblings(".reply-bottom-wrap").find("input").val();
				that.attr("disabled","disabled")
			RequestService("/doctor/posts/"+postsId+"/comment", "POST", {
		          "postsId" : postsId,
		          "commentId" : commentId,
		          "content" : content
		        }, function (data) {
		        	if (data.success==true) {
		        		showTip("回复成功");
		        		that.removeAttr("disabled");
		        		that.parent().parent().siblings(".reply-teacher").find(".anchor-reply").click();
		        		$(".reply-bottom-wrap input").val("");
		        		var reply={"postsId":postsId,"id":commentId};
		        			reply.list=data.resultObject;
		        			replyBox.html(template("reply-template", {replyList:reply}));
							finishReply();   //回调
		        			anchorReply();	//回复
		        			deleteReply();	//删除
		        			btnColorReply(); //回复按钮变绿
		        	} else{
		        		that.removeAttr("disable");
		        		showTip("回复失败");
		        	}
		        })
			
		})
	}
//	input点击删除 
	function deleteReply(){
		$(".reply-teacher .detele-reply").unbind("click");
		$(".reply-teacher .detele-reply").click(function(){			
			var that=$(this),
				postsId=that.attr("data-id"),
				id=that.attr("data-comid");
			RequestService("/doctor/posts/"+id+"/comment/"+postsId, "delete", null , function (data) {
		        	if(data.success==true){
		        		that.parent().parent().parent().remove();
		        		showTip("删除成功");
		        		deleteLast();
		        	}else{
		        		showTip("删除失败");
		        	}
		        })
			
		})
	}
//  input点击删除到最后一条并无点赞
    function deleteLast(){
    	if($(".reply-fabulous-wrap").children().length<0){
    		$(".reply-fabulous-wrap").addClass("hide");
    	}else{
    		$(".reply-fabulous-wrap").removeClass("hide");
    	}
    }




//---------------------------------动态部分结束，轮播部分开始-------------------------------------------------


//	新增轮播图按钮切换页面
	$(".banner-set-top button").click(function(){
	if($(this).text()=="添加轮播图"){
		$(".banner-main").addClass("hide");
		$(".banner-set-wrap").removeClass("hide");
		$(".banner-all-tip").addClass("hide");
		$(this).text("返回");
		$(".banner-submission-wrap button").removeAttr("disabled");
	}else{
		$(".banner-main").removeClass("hide");
		$(".banner-set-wrap").addClass("hide");
		$(this).text("添加轮播图");
		resetBanner();
	}
})

//	轮播图列表
	

	function bannerList(pages){
		RequestService("/doctor/banner", "GET", {
			"page":pages,
			"pageSize":10
		} , function (data) {
	    	if(data.success==true){
	    		list=data.resultObject.records;
	    		if(list.length==0){
	    			$(".banner-nulldata").removeClass("hide");
	    		}else{
	    			$(".banner-nulldata").addClass("hide");
	    			$("#banner-list-content").html(template("banner-template",{items:data.resultObject.records}));
	    		}
	//            	 分页
	          	 if (data.resultObject.pages > 1) { //分页判断
	                $(".not-data").remove();
	                $(".banner_pages").removeClass("hide");
	                $(".banner_pages .searchPage .allPage").text(data.resultObject.pages);  //传的页数的参数
	                $("#Pagination_banner").pagination(data.resultObject.pages, {			//传的页数的参数
	                    num_edge_entries: 1, //边缘页数
	                    num_display_entries: 4, //主体页数
	                    current_page: pages - 1,  //共几页
	                    callback: function (page) {
	                        //翻页功能
	                        bannerList(page + 1);
	                    }
	                });
	            } else {
	                $(".banner_pages").addClass("hide");
	            }
	//             分页结束
				isRelease();    //上下架触发方法


	    	}else{
	    		showTip("获取列表失败")
	    	}
	      
	    })
	}

//	轮播图上下架
	function isRelease(){
		$(".is-release").click(function(){
			var releaseStatus=$(this).attr("data-status"),
				releaseId=$(this).attr("data-id");
				RequestService("/doctor/banner/"+releaseId+"/"+releaseStatus+"", "PUT",null, function (data) {
			        if(data.success==true){
			        	showTip("操作成功");
			        	bannerList(1)
			        }else{
                        showTip(data.errorMessage ? data.errorMessage : "操作失败");
			        }
			    })
		})
	}

//  验证轮播图
	function RecruitBanner(data){
//		是否上传图片
		if($(".right-banner .banner-box img").length==0){
			$(".banner-fengmian").removeClass("hide");
			return false;
		}else{
			$(".banner-fengmian").addClass("hide");
		}
//		是否选择类型
		if(data.type==null){
			$(".banner-error-style").removeClass("hide");
			return false;
		}else{
			$(".banner-error-style").addClass("hide");
		}
//		连接到	
		if(isBlank(data.linkParam) && data.type != 6){
			$(".banner-error-link").removeClass("hide");
			return false;
		}else{
			$(".banner-error-link").addClass("hide");
		}
//		选取时间
		if((isBlank(data.startTime) && !isBlank(data.endTime)) || (!isBlank(data.startTime) && isBlank(data.endTime))){
			$(".banner-error-time").removeClass("hide");
			return false;
		}else if(!isBlank(data.startTime) && !isBlank(data.endTime)){
		    if (data.startTime>data.endTime) {
                showTip("开始时间不能大于结束时间");
                $(".banner-error-time").addClass("hide");
                return false;
            }
            if (data.endTime < new Date()) {
		        showTip("结束时间不能小于当前时间");
                $(".banner-error-time").addClass("hide");
                return false;
            }
		}
		else{
			$(".banner-error-time").addClass("hide");
		}
		return data;
	}
		
//	创建轮播图
	$(".banner-submission-wrap button").click(function(){
			var $this=$(this);
			var data={
				"imgUrl":$(".right-banner .banner-box img").attr("src"),
				"type":$(".banner-link-wrap li .active").attr("data-type"),
				"linkParam":$("#posts_resource_select").val(),
				"startTime":$("#start-time").val(),
				"endTime":$("#end-time").val()
			};
			var id =  $("#J-banner-submit").data('id');
			var method = "POST";
			var url = bath + "/doctor/banner";
			if (id) {
			    method = "PUT";
			    url = url  + "/" + id;
            }
			if(RecruitBanner(data)){
				   $.ajax({
		                type: method,
		                url: url,
		                data: JSON.stringify(data),
		                contentType: "application/json",
		                success: function (data) {
		                	$this.attr("disabled","disabled");
		                    if (data.success == true) {
								bannerList(1);
								showTip(id ? "更新成功" : "创建成功");
								resetBanner();
								$(".banner-set-top button").click();
		                    } else {
		                    	$(".banner-submission-wrap button").removeAttr("disabled")
								showTip("保存失败");
		                    }
		                }
	            });
			}
		})

//	上传轮播图照片
	 function bannerUpdown(baseurl, imgname) {
	        RequestService("/medical/common/upload", "post", {
	            image: baseurl,
	        }, function (data) {
	            $('.right-banner  .' + imgname + '').html('<img src="' + data.resultObject + '" >');
	        })
	    }
	    $('#banner_picIpt').on('change', function () {
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
            bannerUpdown(reader.result, 'banner-box');
        }
        reader.readAsDataURL(this.files[0])
    });
	var listData = [[],[],[],[], [], []];
//	选择链接类型下拉
	$(".banner-link-wrap li").click(function(){
		var linkType=$(this).find("em").attr("data-type");
		var selectType = null;
		if (linkType == 6) {
            $('.J-linkParam-div').hide();
        } else {
            $('.J-linkParam-div').show();
        }
			if(linkType==1 || linkType==2){
				if (linkType == 2) {
					selectType = 1;
				}
				if(listData[linkType-1].length==0){
					RequestService("/doctor/course/list", "get", {
				        "type":selectType
				        }, function (data) {
				       if (data.success==true) {
				       		var courses = data.resultObject;
				       		var courseRecourses = [];
				       		for (var i = 0; i < courses.length; i++) {
				       			var course = {}; 
				       			course.id = courses[i].id;
				       			course.title = courses[i].courseName;
				       			courseRecourses.push(course);
				       		}
				       		listData[linkType-1] = courseRecourses;
				       }
					},false)
				}
			}else if(linkType==3 || linkType==4){
				if (linkType == 3){
					selectType = 4;
				}else{
					selectType = 8;
				}
				if(listData[linkType-1].length==0){
					RequestService("/doctor/article/list", "get", {
				        "type":selectType
				        }, function (data) {
				       if (data.success==true) {
				       		var articles = data.resultObject.records;
				       		listData[linkType-1] = articles;
				       } else{
				       	
				       }
					},false)
				}
			} else if (linkType == 5) {
                if(listData[linkType-1].length==0){
                    RequestService("/doctor/regulations/list", "get", null, function (data) {
                        if (data.success==true) {
                            var regulations = data.resultObject;
                            listData[linkType-1] = regulations;
                        } else{

                        }
                    },false)
                }
            }
			showSelect(linkType);
		$(".banner-link-wrap li em").removeClass("active");
		$(this).find("em").addClass("active");
	});

	function showSelect(type){
		var data = listData[type-1];
		var name;
		if(type==1){
			name="课程";
		}else if(type==2){
			name="直播";
		}else if(type==3){
			name="专栏";
		}else if(type==4){
			name="医案";
		} else if (type == 5) {
		    name = "招生简章";
        } else {
		    return ;
        }
        var str='<option value="" style="color:#adadad">选择一个'+name+'</option>';
        
        for(var i=0;data.length>i;i++){
            str += "<option value='"+data[i].id+"'>"+data[i].title+"</option>";
        }
        $("#posts_resource_select").html(str);
        $('.posts_resource').selectpicker('refresh');
        $('.posts_resource').selectpicker({
            'selectedText': 'cat',size:10
        });
	}
//调用插件
	$('.posts_resource').selectpicker({
	        'selectedText': 'cat',size:10
	});










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
        initialFrameHeight: 220,
        elementPathEnabled: false,
        autoHeightEnabled: false,
        autoFloatEnabled: true,
        enableAutoSave: false,
        imagePopup: false,
        maximumWords: 10000 //允许的最大字符数
    });
    //	专栏部分
    //	专栏部分，点击发布效果
    $('#zhuanlan .zhuanlan_top button').click(function () {
        var recruit_btn = $(this).text()
        if (recruit_btn == "发布") {
//      	选择状态弹出
        	$("#mask").removeClass("hide");
        	$(".column-btn-box").removeClass("hide");
        	
        	
//          $(".column-sava-publish").removeAttr("disabled", "disabled");
//          resetColumn();
//          $('#zhuanlan_bottom2').addClass('hide');
//          $('#zhuanlan_bottom').removeClass('hide');
//          $(this).text("返回")
//          $(".recruit-wrap-title p").text("专栏");
//          //			保存按钮显现
//          $(".column-new-button").removeClass("hide");
//          $(".column-edit-button").addClass("hide");

        } else {
            $('#zhuanlan_bottom2').removeClass('hide');
            $('#zhuanlan_bottom').addClass('hide');
            $(this).text("发布")
            $('#zhuanlan .zhuanlan_top .title').text('专栏医案')
        }
    })
//  关闭选择框
	function closeColumnBox () {
		$("#mask").addClass("hide");
	    $(".column-btn-box").addClass("hide");
	}
//  点击取消选择
	$(".cancel-column").click(function(){
		closeColumnBox();
	})
//	点击确认选择
	var pointId;
    $(".confirm-column").click(function(){
    	closeColumnBox();
    	$(".column-point .active").each(function(){
    		pointId=$(".column-point .active").attr("data-type");
    	})
		 	$(".column-sava-publish").removeAttr("disabled", "disabled");
            resetColumn();
            $('#zhuanlan_bottom2').addClass('hide');
            $('#zhuanlan_bottom').removeClass('hide');
            $('#zhuanlan .zhuanlan_top button').text("返回")
            $(".recruit-wrap-title p").text("医案专栏");
            //			保存按钮显现
            $(".column-new-button").removeClass("hide");
            $(".column-edit-button").addClass("hide");
	})

//	选择radio类型
	$(".column-point").click(function(){
		$(".column-point p em").removeClass("active");
		$(this).find("em").addClass("active");
	})


    //专栏部分，封面图上传
    function columnUpdown(baseurl, imgname) {
        RequestService("/medical/common/upload", "post", {
            image: baseurl,
        }, function (data) {
            $('#zhuanlan .zhuanlan_bottom  .' + imgname + '').html('<img src="' + data.resultObject + '" >');
        })
    }

    $('#zhuanlan_picIpt').on('change', function () {
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
            columnUpdown(reader.result, 'column-picter');
        }
        reader.readAsDataURL(this.files[0])
    });

    //点击专栏的发布和保存
    function columnRecruit(data) {
        if (data.title == "") {
            $(".column-title-warning").removeClass("hide");
            return false;
        } else {
            $(".column-title-warning").addClass("hide");
        }
        if ($(".column-picter img").length == 0) {
            $(".column-picter-warning").removeClass("hide");
            return false;
        } else {
            $(".column-picter-warning").addClass("hide");
        }
        if (data.content == "") {
            $(".column-text-warning").removeClass("hide");
            return false;
        } else {
            $(".column-text-warning").addClass("hide");
        }
        return true;
    }

    $(".column-sava-publish").click(function () {
        var columeStatus = $(this).attr("data-status");
        var data = {
            "title": $.trim($(".column-title").val()),
            "imgPath": $(".column-picter img").attr("src"),
			"typeId": pointId,
            "content": UE.getEditor('column-content').getContent(),            
            "status": columeStatus
        }
        if (columnRecruit(data)) {
            $(this).attr("disabled", "disabled");
            $.ajax({
                type: "POST",
                url: bath + "/doctor/article/specialColumn",
                data: JSON.stringify(data),
                contentType: "application/json",
                success: function (data) {
                    if (data.success == true) {
                        showTip("保存成功");
                        $("#nav-colume").click();
                        columnList(1);
                    } else {
                    	$(".column-sava-publish").removeAttr("disabled");
                        showTip("保存失败");
                    }
                }
            });
        }
        ;

    })
//	专栏列表筛选
	$(".column-select-wrap select").on("change",function(){
		columnList(1,$(".column-select-wrap select").val())
	})


    //专栏部分的列表
    function columnList(pages,selectId) {
    	var data = {
			page:pages,
			size:10
		};
    	if(selectId!=null && selectId!=""){
			data.type=selectId;
		};
        RequestService("/doctor/article/specialColumn", "GET", data , function (data) {
            if (data.success = true) {
                columns = data.resultObject.records;
                if (columns.length == 0) {
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
                if (data.resultObject.pages > 1) { //分页判断
                    $(".not-data").remove();
                    $(".column_pages").removeClass("hide");
                    $(".column_pages .searchPage .allPage").text(data.resultObject.pages);
                    $("#Pagination_column").pagination(data.resultObject.pages, {
                        num_edge_entries: 1, //边缘页数
                        num_display_entries: 4, //主体页数
                        current_page: pages - 1,
                        callback: function (page) {
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
    $('#preview .preview_top img').click(function () {
        $('#preview').addClass('hide');
        $('#mask').addClass('hide')
    })

    //专栏部分，上架、下线功能
    function initEvent() {
        $('.fluctuate').click(function () {
            var id = $(this).attr("data-id"),
                status = $(this).attr("data-status");
            RequestService("/doctor/article/specialColumn/" + id + '/' + status, "PUT", null, function (data) {
                if (data.success == true) {
                    showTip("操作成功");
                    columnList(1);
                } else {
                    showTip("操作失败");
                }
            })
        });
        //专栏部分，删除
        $('.column-delete').click(function () {
            var id = $(this).attr("data-id")
            comfirmBox.open("专栏", "确定删除该条信息吗？", function (closefn) {
                RequestService("/doctor/article/specialColumn/" + id, "DELETE", null, function (data) {
                    if (data.success == true) {
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
        $(".save-edit-publish").click(function () {
            var editId = $("#column-id").val(),
                editData = {
                    "title": $.trim($(".column-title").val()),
                    "imgPath": $(".column-picter img").attr("src"),
                    "content": UE.getEditor('column-content').getContent()
                };
            if (columnRecruit(editData)) {
                $.ajax({
                    type: "PUT",
                    url: bath + "/doctor/article/specialColumn/" + editId,
                    contentType: "application/json",
                    data: JSON.stringify(editData),
                    success: function (data) {
                        if (data.success == true) {
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
        initialFrameHeight: 220,
        elementPathEnabled: false,
        autoHeightEnabled: false,
        autoFloatEnabled: true,
        enableAutoSave: false,
        imagePopup: false,
        maximumWords: 10000 //允许的最大字符数
    });


    //	著作部分
    //	著作部分,点击发布切换效果
    $('#zhuzuo .zhuzuo_top button').click(function () {
        $(".work-save-publish").removeAttr("disabled");
        var workSelect = $(this).text()
        if (workSelect == "发布") {
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
        }, function (data) {
            $('#zhuzuo .zhuzuo_bottom  .' + imgname + '').html('<img src="' + data.resultObject + '" >');
        })
    }

    $('#zhuzuo_picIpt').on('change', function () {
        if (this.files[0].size > 2097152) {
            $('#tip').text('上传图片不能大于2M');
            $('#tip').toggle();
            setTimeout(function () {
                $('#tip').toggle();
            }, 2000)
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
            workUpdown(reader.result, 'work-picter');
        }
        reader.readAsDataURL(this.files[0])
    })

    //	著作部分,点击发布验证文本框
    function workValidate(workData) {
        var urlHttp = /^http:\/\//;
        if (workData.title == "") {
            $(".work-book-warning").removeClass("hide");
            return false;
        } else {
            $(".work-book-warning").addClass("hide");
        }
        if (workData.author == "") {
            $(".work-author-warning").removeClass("hide");
            return false;
        } else {
            $(".work-author-warning").addClass("hide");
        }
        if ($(".work-picter img").length == "0") {
            $(".work-picter-warning").removeClass("hide");
            return false;
        } else {
            $(".work-picter-warning").addClass("hide");
        }
        if (workData.remark == "") {
            $(".work-text-warning").removeClass("hide");
            return false;
        } else {
            $(".work-text-warning").addClass("hide");
        }
        if (workData.buyLink == "") {
            $(".work-link-warning").removeClass("hide");
            $(".work-link2-warning").addClass("hide");
            return false;
        } else {
            $(".work-link-warning").addClass("hide");
        }
        if (!urlHttp.test(workData.buyLink)) {
            $(".work-link2-warning").removeClass("hide");
            return false;
        } else {
            $(".work-link2-warning").addClass("hide");

        }
        return true;
    }

    $(".work-save-publish").click(function () {
        var worksaveId = $(this).attr("data-status"),
            workData = {
                "title": $(".work-title").val(),
                "author": $(".work-author").val(),
                "imgPath": $(".work-picter img").attr("src"),
                "remark": UE.getEditor('work-suggest').getContent(),
                "buyLink": $(".work-link").val(),
                "status": worksaveId
            }
        if (workValidate(workData)) {
            $(this).attr("disabled", "disabled");
            $.ajax({
                type: "POST",
                url: bath + "/doctor/writing",
                data: JSON.stringify(workData),
                contentType: "application/json",
                success: function (data) {
                    if (data.success == true) {
                        showTip("保存成功");
                        $("#nav-work").click();
                        workList(1);
                    } else {
                    	$(".work-save-publish").removeAttr("disabled");
                        showTip("保存失败");
                    }
                }
            });
        }
    });
    //	著作部分,著作列表


    function workList(pages) {
        RequestService("/doctor/writing", "GET", {
            "page": pages
        }, function (data) {
            getWorkdata = data.resultObject.records;
            if (getWorkdata.length == 0) {
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
            if (data.resultObject.pages > 1) { //分页判断
                $(".not-data").remove();
                $(".works_pages").removeClass("hide");
                $(".works_pages .searchPage .allPage").text(data.resultObject.pages);
                $("#Pagination_works").pagination(data.resultObject.pages, {
                    num_edge_entries: 1, //边缘页数
                    num_display_entries: 4, //主体页数
                    current_page: pages - 1,
                    callback: function (page) {
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
    $("#work-preview-page .common-top img").click(function () {
        $(".work-preview-page").addClass("hide");
        $("#mask").addClass("hide")
    })

    //	著作部分,上、下架
    function initWork() {
        $(".work-fluctuate").click(function () {
            var workId = $(this).attr("data-id"),
                workStatus = $(this).attr("data-status");
            RequestService("/doctor/writing/" + workId + "/" + workStatus, "PUT", null, function (data) {
                if (data.success == true) {
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
        //				RequestService("/hospital/recruit/"+deleteId+"","DELETE",null,function(data){
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
    $(".only-save-work").click(function () {
        var workEditId = $("#workId").val(),
            editDataWork = {
                "title": $(".work-title").val(),
                "author": $(".work-author").val(),
                "imgPath": $(".work-picter img").attr("src"),
                "remark": UE.getEditor('work-suggest').getContent(),
                "buyLink": $(".work-link").val(),
            }
        if (workValidate(editDataWork)) {
            $(".only-save-work").attr("disabled", "disabled");
            $.ajax({
                type: "PUT",
                url: bath + "/doctor/writing/" + workEditId + "",
                data: JSON.stringify(editDataWork),
                contentType: "application/json",
                success: function (data) {
                    if (data.success == true) {
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
        initialFrameHeight: 220,
        elementPathEnabled: false,
        autoHeightEnabled: false,
        autoFloatEnabled: true,
        enableAutoSave: false,
        imagePopup: false,
        maximumWords: 10000 //允许的最大字符数
    });

    //	媒体报道部分
    //	媒体报道部分,点击发布切换效果
    $('#media_report .media_report_top button').click(function () {
        $(".media-save-publish").removeAttr("disabled");
        var mediaSelect = $(this).text()
        if (mediaSelect == "发布") {
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
        }, function (data) {
            $('#media_report .media_report_bottom  .' + imgname + '').html('<img src="' + data.resultObject + '" >');
        })
    }

    $('#media_picIpt').on('change', function () {
        if (this.files[0].size > 2097152) {
            $('#tip').text('上传图片不能大于2M');
            $('#tip').toggle();
            setTimeout(function () {
                $('#tip').toggle();
            }, 2000)
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
            mediaUpdown(reader.result, 'media-picter');
        }
        reader.readAsDataURL(this.files[0])
    })

    //	媒体报道部分,点击发布验证文本框
    function mediaValidate(mediaData) {
        if (mediaData.title == "") {
            $(".media-title-warning").removeClass("hide");
            return false;
        } else {
            $(".media-title-warning").addClass("hide");
        }
        if (mediaData.author == "") {
            $(".media-author-warning").removeClass("hide");
            return false;
        } else {
            $(".media-author-warning").addClass("hide");
        }
        if ($(".media-picter img").length == "0") {
            $(".media-picter-warning").removeClass("hide");
            return false;
        } else {
            $(".media-picter-warning").addClass("hide");
        }
        if (mediaData.content == "") {
            $(".media-text-warning").removeClass("hide");
            return false;
        } else {
            $(".media-text-warning").addClass("hide");
        }
        if (mediaData.url == "") {
            $(".media-url-warning").removeClass("hide");
            return false;
        } else {
            $(".media-url-warning").addClass("hide");
        }
        return true;
    }

    $(".media-save-publish").click(function () {
        var mediaSaveId = $(this).attr("data-status"),
            mediaData = {
                "title": $(".media-title").val(),
                "author": $(".media-author").val(),
                "imgPath": $(".media-picter img").attr("src"),
                "content": UE.getEditor('media-context').getContent(),
                "url": $(".media-link").val(),
                "status": mediaSaveId
            }
        if (mediaValidate(mediaData)) {
            $(this).attr("disabled", "disabled");
            $.ajax({
                type: "POST",
                url: bath + "/doctor/article/report",
                data: JSON.stringify(mediaData),
                contentType: "application/json",
                success: function (data) {
                    if (data.success == true) {
                        showTip("保存成功");
                        $("#nav-media").click();
                        mediaList(1);
                    } else {
                        showTip("保存失败");
                        $(".media-save-publish").removeAttr("disabled");
                    }
                }
            });
        }
    });
    //	媒体报道部分,媒体报道列表

    function mediaList(pages) {
        RequestService("/doctor/article/report", "GET", {
            "page": pages
        }, function (data) {
            getMediadata = data.resultObject.records;
            if (getMediadata.length == 0) {
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
            if (data.resultObject.pages > 1) { //分页判断
                $(".not-data").remove();
                $(".media_pages").removeClass("hide");
                $(".media_pages .searchPage .allPage").text(data.resultObject.pages);
                $("#Pagination_media").pagination(data.resultObject.pages, {
                    num_edge_entries: 1, //边缘页数
                    num_display_entries: 4, //主体页数
                    current_page: pages - 1,
                    callback: function (page) {
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
        $("#media-preview .media-preview-top img").click(function () {
            $("#media-preview").addClass("hide");
            $("#mask").addClass("hide");
        })
        //媒体报道部分,上、下架
        $(".media-select").click(function () {
            var mediaId = $(this).attr("data-id"),
                mediaFluctuate = $(this).attr("data-status");
            RequestService("/doctor/article/report/" + mediaId + "/" + mediaFluctuate + "", "PUT", null, function (data) {
                if (data.success == true) {
                    showTip("操作成功");
                    mediaList(1);
                } else {
                    showTip("操作失败");
                }
            })
        })

        //媒体报道部分,删除
        $(".media-delete").click(function () {
            var deleteMediaId = $(this).attr("data-delete");
            comfirmBox.open("媒体报道", "确定删除该条报道吗？", function (closefn) {
                RequestService("/doctor/article/report/" + deleteMediaId + "", "DELETE", null, function (data) {
                    if (data.success == true) {
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
        $(".media-only-save").click(function () {
            var mediaEditId = $("#mediaId").val(),
                editDataMedia = {
                    "title": $(".media-title").val(),
                    "author": $(".media-author").val(),
                    "imgPath": $(".media-picter img").attr("src"),
                    "content": UE.getEditor('media-context').getContent(),
                    "url": $(".media-link").val(),
                };
            if (mediaValidate(editDataMedia)) {
                $(".media-only-save").attr("disabled", "disabled");
                $.ajax({
                    type: "PUT",
                    url: bath + "/doctor/article/report/" + mediaEditId + "",
                    data: JSON.stringify(editDataMedia),
                    contentType: "application/json",
                    success: function (data) {
                        showTip("保存成功");
                        mediaList(1);
                        $("#nav-media").click();
                    }
                });
            }

        });

    }

})

//医馆部分
//点击变色效果
//$('.hospital_worktime ul li ').click(function () {
//  if ($(this).hasClass('color')) {
//      $(this).removeClass('color')
//  } else {
//      $(this).addClass('color');
//  }
//
//})
var sittingArr;
var workTime;
$('.hospital_worktime td p').click(function () {	
	if($(this).find("img").hasClass("hide")){
		$(this).find("img").removeClass("hide").addClass("active");
	}else{
		$(this).find("img").addClass("hide").removeClass("active");
	}	
})
function getSitting(){
	sittingArr=[];
	$(".hospital_worktime tr p .active").each(function(){		
			sittingArr.push($(this).attr("data-type"));					
	})
	workTime=sittingArr.join(",");
}






var hosList = [];
var hosID;
//医师认证状态和认证信息显示
RequestService("/doctor/apply/listHospital/0", "get", null, function (data) {
    //列表渲染
    // $('#id_select').append('<option value="-1">请选择医馆</option>')
    hosList = data.resultObject.records;
    $('#id_select').append(template('hosListTpl', {
        item: hosList
    }));
    $('.selectpicker_collection').selectpicker('refresh');
    //渲染之后在此调用插件
    $('#hospital_bottom .selectpicker').selectpicker({
        'selectedText': 'cat',
        size: 10
    });

});

//选择平台已有医馆
$('#hospital_bottom .chooseHospital').click(function () {
    $('.mask').css('display', 'block');
    $('#hosChoose').removeClass('hide');
})

//点击关闭选择医馆列表
$('#close_hosChoose').click(function () {
    $('.mask').css('display', 'none');
    $('#hosChoose').addClass('hide');
})

//选择医馆列表选中之后出发的事件
$('#id_select').change(function () {
    $('#hospital_bottom .zhuanlan_title').val($("#id_select option:selected").text())
    $('.mask').css('display', 'none');
    $('#hosChoose').addClass('hide');

    //医馆ID的获取
    hosID = $('#id_select').val()
    if (hosID == -1) {
        //清空信息
        clearHosList()
        return false;
    }
    var hos = getHosById(hosID);
    if (hos == null) return;

    //省
    $('#hosPro').val(hos.province)
    //市
    $('#hosCity').val(hos.city)
    //详细地址
    $('#detail_address').val(hos.detailedAddress)
    //医馆封面
    if (hos.medicalHospitalPictures != "") {
        var pictureData = hos.medicalHospitalPictures[0];
        $('#hospital .fengmian_pic').html('<img src=' + pictureData.picture + ' alt="">')
    }
    //电话号码
    $('.hosContantTel .hosTel').val(hos.tel);
})

function getHosById(id) {
    for (var i = 0; i < hosList.length; i++) {
        if (hosList[i].id == id) {
            return hosList[i];
        }
    }
    return null;
}

//清空医师入驻医馆信息列表
function clearHosList() {
    //省分
    $('#hosPro').val('医馆所在省份');
    //市区
    $('#hosCity').val('医馆所在市区')
    //详细地址清空
    $('#detail_address').val('')
    //封面清空
    $('#hospital .fengmian_pic').html('	<p style="font-size: 90px;height: 100px;font-weight: 300;color: #d8d8d8;text-align: center;">+</p><p style="text-align: center;color: #999;font-size: 14px;">暂无医馆封面图</p>');
    //电话清空
    $('#hospital .hosTel').val('');
    //坐诊时间
//  $('#hospital .hospital_worktime ul li').removeClass('color keshiColor');
	$('#hospital .hospital_worktime td p img').remove();

}

//医师-医馆封面上传图片调用的接口
function picUpdown(baseurl, imgname) {
    RequestService("/medical/common/upload", "post", {
        image: baseurl,
    }, function (data) {
        console.log(data);
        $('#hospital_bottom .' + imgname + '').html('<img src="' + data.resultObject + '" >');
    })
}

//医馆封面上传
$('#fengmian_picIpt').on('change', function () {
    var reader = new FileReader();
    reader.onload = function (e) {
        picUpdown(reader.result, 'fengmian_pic');
    }
    reader.readAsDataURL(this.files[0])
})

//每周坐诊点击生成数组数据
//var arr = [];
//var workTime;
//$('.hospital_worktime  ul li').click(function () {
//  if ($(this).hasClass('keshiColor')) {
//      //删除第二次选中的
//      for (var i = 0; i < arr.length; i++) {
//          if ($(this).text() == arr[i]) {
//              arr.splice(i, 1)
//          }
//      }
//
//      workTime = arr.toString();
//      $(this).removeClass('keshiColor');
//  } else {
//      $(this).addClass('keshiColor');
//      arr.push($(this).text());
//
//      workTime = arr.toString();
//  }
//  console.log(workTime)
//})

//医师入住医馆信息上传
$('#hospital_bottom #submit').click(function () {
	getSitting()
    //任职医馆的验证
    //任职医馆验证
    if (hosID == '' || hosID == '-1') {
        $('#hospital_bottom .work_hos_warn').removeClass('hide');
        return false;
    } else {
        $('#hospital_bottom .work_hos_warn').addClass('hide');
    }


    //坐诊时间验证
    if ($('.hospital_worktime tr p .active').length == 0) {
        $('#hospital_bottom .hospital_worktime_warn  ').removeClass('hide');
        return false;
    } else {
        $('#hospital_bottom .hospital_worktime_warn  ').addClass('hide');
    }

    RequestService("/doctor/joinHospital", "post", {
        workTime: workTime,
        hospitalId: hosID
    }, function (data) {
        if (data.success == true) {
            //				alert('上传成功')
            $('#tip').text('加入成功！');
            $('#tip').toggle();
            setTimeout(function () {
                $('#tip').toggle();
                window.location.reload();
            }, 2000)

        } else {
            //				alert('您不是医师，不能加入医馆')
            $('#tip').text(data.errorMessage);
            $('#tip').toggle();
            setTimeout(function () {
                $('#tip').toggle();
            }, 2000)
        }
    });
    //	alert(111)
})

if (localStorage.AccountStatus == 1) {
    RequestService("/doctor/authentication/get", "get", {}, function (data) {
        console.log(data);
        if (data.success == false) {
            //				alert('获取认证状态数据失败');
            //				$('#tip').text('获取认证状态数据失败');
            //	       		$('#tip').toggle();
            //	       		setTimeout(function(){
            //	       			$('#tip').toggle();
            //	       		},2000)
        } else if (data.success == true) {
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
    RequestService("/doctor/apply/getLastOne", "get", null, function (data) {
        //头像预览
        console.log(data);
        $('#renzheng_status_list').html(template('renzheng_statusTpl', data.resultObject));
        //个人信息渲染
        $('.personIntroduct .introductInf').html(data.resultObject.description);
    });
}

//如果入驻了医馆进入获取数据
$('#docJoinHos').click(function () {
    RequestService("/doctor/getHospital", "get", null, function (data) {
        if (data.success == true) {
            //入住过医馆
            //头像预览
            $('#hospital_bottom .selectpicker').selectpicker('val', (data.resultObject.id));
            if (data.resultObject.visitTime == null) return;
            //坐诊时间渲染
            var workArr = data.resultObject.visitTime.split(",");

				
			var selectTime;
			var sureType=[];
			var saveData;
			
			$('.hospital_worktime tr p img').each(function(){
					selectTime=$(this).attr("data-type");
					sureType.push(selectTime)
			})
			for (var i = 0; i < sureType.length; i++) {
                for (j = 0; j < workArr.length; j++) {
                    if (sureType[i] == workArr[j] && !$('.hospital_worktime tr p img').eq(i).hasClass("active")) {
                        $('.hospital_worktime tr p').eq(i).click();
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

var defaultWoekPicter = '<p style="font-size: 56px;height: 75px;font-weight: 300;color: #d8d8d8;text-align: center;">+</p>' +
    '<p style="text-align: center;color: #999;font-size: 14px;">点击上传封面</p>';
//显示预览功能
var columns;

function showPreview(index) {
    var columnPreview = columns[index];
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
    $('#zhuanlan .zhuanlan_top .title').text('专栏医案编辑');
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
    var column = columns[index];
    $("#column-id").val(column.id);
    $(".column-title").val(column.title);
    $(".column-picter").html("<img src=" + column.imgPath + " />");
//		$(".column-text").val(columnGetdata.content);
    UE.getEditor('column-content').setContent(column.content);
}

//-----------------------------------------著作部分，预览,编辑回显--------------------------------------
var getWorkdata;

function workPreview(index) {
    var workPreviewData = getWorkdata[index];
    $(".preview-work-title").text(workPreviewData.title);
    $(".preview-work-author").text(workPreviewData.author);
    $(".preview-work-picter img").attr("src", workPreviewData.imgPath);
    $(".preview-work-present").html(workPreviewData.remark);
    $(".preview-work-link").html(workPreviewData.buyLink ? '<a href="' + workPreviewData.buyLink + '" target="_blank">' + workPreviewData.buyLink + '</a>' : '');
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
    $(".work-picter").html(defaultWoekPicter);
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
    $(".preview-media-link").html(previewData.url ? '<a href="' + previewData.url + '" target="_blank">' + previewData.url + '</a>' : '');
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



//-----------------------------------------我的动态-------------------------------

//videoFinsh 1上传中  2结束上传
 	var videoFinsh;
	function uploadFile() {		
		var filemd5 = "";
		var obj_file = document.getElementById("video-up").files[0];
		var filepath = $("#video-up").val();		
		//判断文件类型
		if(!isAccord(filepath)) {
			return false;
		}
		$("#continueUpload").hide();
		$('.progress-resource').css({"width": "0%"})
		$(".resource_uploading").show();
		$(".up-success").addClass("hide")    	//显示条出来后隐藏
		$("#video-up").css({"display":"none"})	//显示条出来后隐藏
		$(".saveUrl").addClass("hide");			//显示条出来后隐藏
		$(".againUp").addClass("hide")//		成功后显示
		videoFinsh=1;
		$(".video-null").addClass("hide");
		//获取文件md5
		browserMD5File(obj_file, function(err, md5) {
			filemd5 = md5;
			localStorage.setItem("fileMD5", filemd5);
			xmx(0, "1", filemd5, "", "", "")
		});
	}
//视频上传
	var saveVideo;    //上传时的文件名
	var saveVideoId;  //上传时的ccid
	var isAjax;       //判断文件在上传时有没有走ajax 
	function xmx(begin, first, filemd5, ccid, metaurl, chunkUrl) {
		var obj_file = document.getElementById("video-up").files[0];
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
				isAjax=1;    //判断有没有进入ajax，给取消上传一个判定的标准
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
				$(".file-percent").html("已上传"+completion.toFixed(1)+"%");
				var allNumber=totalSize/(1024*1024);
				var midwayNumber=completion/100*allNumber;
				$(".total-size").html(midwayNumber.toFixed(1)+"MB"+"/"+allNumber.toFixed(1)+"MB")
				
				start = end; // 累计上传字节数
				end = start + chunkSize; // 由上次完成的部分字节开始，添加下次上传的字节数
				localStorage.setItem("startChunkSize", start);
				localStorage.setItem("ccId", ccid);
				localStorage.setItem("metaUrl", metaurl);
				localStorage.setItem("chunkUrl", chunkUrl);
				videoFinsh=1;
				// 上传文件部分累计
				if(start >= totalSize) { //如果上传字节数大于或等于总字节数，结束上传
					videoFinsh=2;
					$(".video-null").addClass("hide");
					$("#ccId").val(result.resultObject[0]);
					saveVideoId=result.resultObject[0];    //保存下载成功后的ID（资源）
					$(".resource_uploading").hide();
					$(".up-success").removeClass("hide")    //上传成功后显示
					$(".saveUrl").removeClass("hide");//	成功后显示
					$(".againUp").removeClass("hide")//		成功后显示
					//显示条出来后隐藏
					$(".file-percent").html("已上传"+0+"%");   //成功后清零
					$(".total-size").html(0+"MB"+"/"+0+"MB")  //成功后清零
					
					saveVideo=$("#video-up").val().slice(12); //获取input的上传文件名
					$(".saveUrl").html(saveVideo)
					uploadfinished = true;
					$("#video-up").val("");					 //清空是为了二次上传同一视频
					//alert('上传完成!');
					//告诉后台上传完成后合并文件                            //返回上传文件的存放路径
//					$("#video-up").css({"display":"block"})
					clearData();
	//	
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
	
			if(ext != ".WMV" && ext != ".WM" && ext != ".ASF" && ext != ".ASX" &&
				ext != ".RM" && ext != ".RMVB" && ext != ".RA" && ext != ".RAM" && ext != ".MPG" &&
				ext != ".MPEG" && ext != ".MPE" && ext != ".VOB" && ext != ".DAT" &&
				ext != ".MOV" && ext != ".3GP" && ext != ".MP4" && ext != ".MP4V" &&
				ext != ".M4V" && ext != ".MKV" && ext != ".AVI" && ext != ".FLV" &&
				ext != ".F4V" && ext != ".MTS") {
				showTip("文件格式有误")
				return false;
			}
	
		return true;
	}
	function cancalUpdata1() {
		if(isAjax=1){
			currentAjax.abort();
		}
		
		var file = document.getElementById('video-up');
		file.value = '';
		$('.progress-resource').css({
			"width": "0%"
		})
		$(".file-percent").html("已上传"+0+"%");   //取消后清零
		$(".total-size").html(0+"MB"+"/"+0+"MB")  //取消后清零
		$(".uploading").hide();
		$(".resource_uploading").hide();
		videoFinsh=2;		// 校验为2是让它在取消时充值提示
		saveVideoId="";    //取消上传后要把视频ID设为空
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

//	重新上传
	$(".againUp").click(function(){
		$("#video-up").click();
	})
	
//	上传成功后去掉视频的参数
	function clearData(){
		var start = localStorage.removeItem("startChunkSize");
		var ccid = localStorage.removeItem("ccId");
		var metaurl = localStorage.removeItem("metaUrl");
		var chunkUrl = localStorage.removeItem("chunkUrl");
		var fileMd5 = localStorage.removeItem("fileMD5");
	}
//重置状态，关闭图片，视频，文章等
	function closeImages(){
		$(".save-photo ul").html("");
		$(".photo-wrap").addClass("hide");		
	}
	function closeVideo(){
		$(".video-cover-pic").html(defaultPicter);
		$(".video-wrap").addClass("hide");
		$(".video-title input").val("");
		$(".video-cover input").val("");

		$(".up-success").addClass("hide");//	关闭视频隐藏 成功提示
		$(".saveUrl").addClass("hide");//	关闭视频隐藏 上传路径
		$(".againUp").addClass("hide");//	关闭视频隐藏  重新上传
		videoFinsh=2;		// 校验为2是让它在关闭时充值提示
		saveVideoId="";    //关闭视频后要把视频ID设为空

//		重置视频
        $(".uploadfinish").hide();
        $(".updataSuccess").hide();
        $(".video-null").addClass('hide');
        document.getElementById('addResource').reset();
        $("#ccName").hide();
       	$("#video-up").hide();
       	$(".resource_uploading").hide();
	}
	function closeConsilia(){
		$(".consilia-wrap").addClass("hide");
	}
	function clearTextarea(){
		$(".publish-activity").val("");
	}
//	清空文章的状态
	function clearConsilia(){
		$(".result-list table").addClass("hide");	//隐藏底下搜索到的列表
			$(".consilia-result").addClass("hide");     //隐藏搜索条数
			$(".result-list table .select-text img").remove();    //清空勾选搜索到的列表
	}
//	专辑下架不跳转给出提示
	function lowerText(){
		showTip("该内容下架")
	}
//  课程下架不跳转给出提示
	function lowerFrame(){
		showTip("该内容下架")
	}



//轮播图设置====================
var resetInputVal='<p style="font-size: 90px;height: 100px;font-weight: 300;color: #d8d8d8;text-align: center;">+</p>'+
    '<p style="text-align: center;color: #999;font-size: 14px;">点击上传封面图片</p>'
var resetSelect='<option value="" style="color: #999999;">请选择课程</option>'

var list;
//	点击编辑
function editBanner(index){
    resetBanner()
    eachBanner(index)
    $(".banner-set-top button").click();
}
//	重置轮播图设置
function resetBanner(){
    $(".banner-box").html(resetInputVal);
    $(".right-banner input").val("");
    $(".banner-link-wrap li em").removeClass("active");
    // $("#select-link").html(resetSelect);
    $("#start-time").val("");
    $("#end-time").val("");
    $("#posts_resource_select").html(resetSelect);
    $('#resetSelect .selectpicker').selectpicker('refresh')
    $("#J-banner-submit").data('id', "");
}

//	回显轮播图设置
function eachBanner(index){
    var bannerSet=list[index];
    $(".banner-box").html("<img src=" + bannerSet.imgUrl + " />");
    $(".type"+bannerSet.type).click();
    $("#start-time").val(bannerSet.startTime);
    $("#end-time").val(bannerSet.endTime);
    $("#J-banner-submit").data('id', bannerSet.id);
    $("#posts_resource_select").val(bannerSet.linkParam);
    $('#resetSelect .selectpicker').selectpicker('refresh')
}



//初始化课程动态 ----临时执行方法
function initialization() {
    RequestService("/doctor/posts/initialization", "post", null, function (data) {
        if (data.success == true) {
            alert("初始化成功")
        } else {
            alert("初始化失败")
        }
    });
}