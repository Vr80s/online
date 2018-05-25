$(function(){
	
	var index = 0;
	//表名是专辑
	if(collection == 1){ //
		$(".buy_tab").removeClass("hide");
		$(".no_buy_tab").remove();
		if(type == "info"){
			$(".wrap-sidebar ul li").eq(1).addClass("active-footer");
			index =1;
		}
	}else{
		$(".no_buy_tab").removeClass("hide");
		$(".buy_tab").remove();
		if(type == "info"){
			$(".wrap-sidebar ul li").eq(0).addClass("active-footer");
		}
	}
	//type对应显示
	//outline  comment    info   aq    selection
	if(type == "selection"){
		$(".wrap-sidebar ul li").eq(0).addClass("active-footer");
	}else if(type == "outline"){
		$(".wrap-sidebar ul li").eq(1).addClass("active-footer");
		index =1;
	}else if(type == "comment"){
		$(".wrap-sidebar ul li").eq(2).addClass("active-footer");
		index =2;
	}else if(type == "aq"){
		$(".wrap-sidebar ul li").eq(3).addClass("active-footer");
		index =3;
	}
	$(".sidebar-content").addClass("hide").eq(index).removeClass("hide")
//	详情/评价/常见问题	选项卡
    $(".wrap-sidebar ul li").click(function () {
        $(".wrap-sidebar ul li").removeClass("active-footer");
        $(this).addClass("active-footer");
        $(".sidebar-content").addClass("hide").eq($(this).index()).removeClass("hide")
    })
//	点击我要评价
    $(".want-evaluate").click(function () {

        //判断是否登录
        RequestService("/online/user/isAlive", "get", null, function (data) {
            if (!data.success) {
                localStorage.username = null;
                localStorage.password = null;
                if ($(".login").css("display") == "block") {
                    $(".login").css("display", "none");
                    $(".logout").css("display", "block");
                    $('#login').modal('show');
                } else {
                    $('#login').modal('show');
                }
            } else {
                $(".bg-modal").removeClass("hide");
                $(".wrap-modal").removeClass("hide");
            }
        })

    });
    //关闭窗口
    $(".close-impression").click(function () {
        emptyFromValue();
        $(".bg-modal").addClass("hide");
        $(".wrap-modal").addClass("hide");
    });

//	回复评论和点赞按钮加颜色
    $(".operation-reply li").eq(0).click(function () {
        var criticizeId = $(this).parent(".operation-reply").attr("data-criticizeId");

        var falg = true;
        if ($(this).hasClass("selected")) {
            $(this).removeClass("selected");
            falg = false;
        } else {
            $(this).addClass("selected");
        }
        var paramsObj = {
            criticizeId: criticizeId,
            praise: falg
        };
        RequestService("/criticize/updatePraise",
            "post", paramsObj, function (data) {
                if (data.success) {
                    var obj = $(".operation-reply li").eq(0).children(".praiseSum");
                    var praiseSum = obj[0].innerText;
                    if (falg) {
                        praiseSum = parseInt(praiseSum) + 1;
                    } else {
                        praiseSum = parseInt(praiseSum) - 1;
                    }
                    obj[0].innerText = praiseSum;
                    alert("中");
                } else {
                    alert("不中啊");
                }
            })
    })

//	点击回复图标出现输入框
    $(".reply-icon").click(function () {

        if ($(this).hasClass("selected")) {
            $(this).removeClass("selected");
        } else {
            $(this).addClass("selected");
        }
        //判断存不存在此cookie
        //请求下后台吧
        RequestService("/online/user/isAlive", "get", null, function (data) {
            if (data.success == true) {
                var result = data.resultObject;
                $(".header-input img").attr("src", result.smallHeadPhoto);
                $(".reply_login").removeClass("hide");
                $(".reply_no_login").addClass("hide");
            } else {
                $(".reply_login").removeClass("hide");
                $(".reply_no_login").addClass("hide");
            }
        })

        if ($(this).hasClass("selected")) {
            $(this).parents().siblings(".wrap-input").removeClass("hide");
        } else {
            $(this).parents().siblings(".wrap-input").addClass("hide");
        }
    })

    //提交回复
    $(".reply_criticize").click(function () {
        var criticizeId = $(this).parent(".wrap-input").attr("data-criticizeId");
        var criticizeContent = $(this).parent(".wrap-input").find("input").val();
        if (criticizeContent == null ||
            criticizeContent == undefined ||
            criticizeContent == "") {
            return;
        }
        var paramsObj = {
            content: criticizeContent,
            criticizeId: criticizeId,
        }
        if (collection == 1) {
            paramsObj.collectionId = courseId;
        }
        RequestService("/criticize/saveReply",
            "post", paramsObj, function (data) {
                if (data.success) {
                    alert("回复成功");
                } else {
                    alert("回复失败");
                }
            })
    })


//星星五星好评
    $('.impression-star img').each(function (index) {
        var star = '../../web/images/star-dim.png';    //普通灰色星星图片的存储路径
        var starRed = '../../web/images/star-light.png';     //红色星星图片存储路径
        var prompt = ['1分', '2分', '3分', '4分', '5分'];   //评价提示语
        this.id = index;      //遍历img元素，设置单独的id
        $(this).on("mouseover click", function () {    //设置鼠标滑动和点击都会触发事件
            $('.impression-star img').attr('src', star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色
            $(this).attr('src', starRed);        //设置鼠标当前所在图片为打星颜色图
            $(this).prevAll().attr('src', starRed);  //设置鼠标当前的前面星星图片为打星颜色图
            $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值

            isSubmit();
        });
    });
//节目内容
    $('.impression-face img').each(function (index) {
        var star = '../../web/images/gs.png';    //普通灰色星星图片的存储路径
        var starRed = '../../web/images/rs.png';     //红色星星图片存储路径
        var prompt = ['一般', '一般', '好', '好', '很好'];   //评价提示语
        this.id = index;      //遍历img元素，设置单独的id
        $(this).on("mouseover click", function () {    //设置鼠标滑动和点击都会触发事件
            $('.impression-face img').attr('src', star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色
            $(this).attr('src', starRed);        //设置鼠标当前所在图片为打星颜色图
            $(this).prevAll().attr('src', starRed);  //设置鼠标当前的前面星星图片为打星颜色图
            $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值

            isSubmit();
        });
    });
//主播演绎
    $('.impression-show img').each(function (index) {
        var star = '../../web/images/gs.png';    //普通灰色星星图片的存储路径
        var starRed = '../../web/images/rs.png';     //红色星星图片存储路径
        var prompt = ['一般', '一般', '好', '好', '很好'];   //评价提示语
        this.id = index;      //遍历img元素，设置单独的id
        $(this).on("mouseover click", function () {    //设置鼠标滑动和点击都会触发事件
            $('.impression-show img').attr('src', star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色
            $(this).attr('src', starRed);        //设置鼠标当前所在图片为打星颜色图
            $(this).prevAll().attr('src', starRed);  //设置鼠标当前的前面星星图片为打星颜色图
            $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值

            isSubmit();
        });
    });
//  很赞,干货很多
    $(".impression-setlist li").click(function () {
        if ($(this).hasClass("impression-active")) {
            $(this).removeClass("impression-active");
        } else {
            $(this).addClass("impression-active");
        }
        isSubmit();
    })

    $("#commentContent").change(function () {
        var commentContent = $(this).val();
        if (commentContent == null || commentContent == "" ||
            commentContent.trim().length <= 0) {
            $('.submission').attr("disabled", true);
            $(".submission").css("background", "");
        }
        isSubmit();
    })

    /**
     *    清空属性啦
     */
    function emptyFromValue() {

        $('.impression-star img,.impression-face img,.impression-show img').attr('src', "../../web/images/star-dim.png");
        $('.impression-star span,.impression-face span,.impression-show span').text("");
        $('.impression-setlist li').removeClass("impression-active");
        $("#commentContent").val("");
    }


    /**
     * 验证表单是否提交
     */
    function isSubmit() {
        var star = $(".impression-star img[src='../../web/images/star-light.png']").length;
        if (star.length <= 0) {

            return;
        }
        var face = $(".impression-face img[src='../../web/images/rs.png']").length;
        if (face.length <= 0) {
            return;
        }
        var show = $(".impression-show img[src='../../web/images/rs.png']").length;
        if (show.length <= 0) {
            return;
        }
        var value = "";
        $(".impression-setlist li ").each(function () {
            var className = $(this).attr("class");
            if (className != null && className != undefined && className.indexOf("impression-active") != -1) {
                value += $(this).attr("data-value") + ",";
            }
        })
        if (value == "") {
            $('.submission').attr("disabled", true);
            $(".submission").css("background", "");
            return;
        }
        var commentContent = $("#commentContent").val();
        if (commentContent == null || commentContent == "" ||
            commentContent.trim().length <= 0) {
            $('.submission').attr("disabled", true);
            $(".submission").css("background", "");
            return;
        }
        $('.submission').removeAttr("disabled");
        $(".submission").css("background", "#00BC12");
    }

//	点击发表评论
    $(".submission").click(function () {
        //发表评论
        var star = $(".impression-star img[src='../../web/images/star-light.png']").length;
        var face = $(".impression-face img[src='../../web/images/rs.png']").length;
        var show = $(".impression-show img[src='../../web/images/rs.png']").length;
        var value = "";
        $(".impression-setlist li ").each(function () {
            var className = $(this).attr("class");
            if (className != null && className != undefined && className.indexOf("impression-active") != -1) {
                value += $(this).attr("data-value") + ",";
            }
        })
        if (value.length > 0) {
            value = value.substring(0, value.length - 1);
        }

        var commentContent = $("#commentContent").val();

//		overallLevel	否	float	总体印像 1-5 星级
//		contentLevel	否	float	节目内容 1-5 星级
//		deductiveLevel	否	float	主播演绎 1-5 星级
//		criticizeLable	否	String	可选择多个，参数拼接传递。例如：1,2,3 1.很赞 2 干货很多 3超值推荐 4喜欢 5买对了
//		content	是	String	评价内容
//		courseId	否	String	评价的课程id
//		userId	是	String	评价的主播id
//		collectionId	否	专辑的课程id	如果是专辑及下子课程评论或者回复，传递此专辑的课程id。
        var paramsObj = {
            overallLevel: star,
            contentLevel: face,
            deductiveLevel: show,
            criticizeLable: value,
            content: commentContent,
            courseId: courseId,
            userId: userId
        };
        if (collection == 1) {
            paramsObj.collectionId = courseId;
        }
        /*
         * 啦啦啦啦啦。提交评论啦
         */
        RequestService("/criticize/saveCriticize", "post", paramsObj, function (data) {
            if (data.success) {
                alert("评论成功");
                emptyFromValue();
                $(".bg-modal").addClass("hide");
                $(".wrap-modal").addClass("hide");
            } else {
                alert("不中啊");
            }
        })

    });

    $('.J-course-buy').on('click', function(e) {
        var $this = $(this);
        var id = $this.data('id');
        $this.attr("disabled", "disabled");
        if (!id) {
            showTip("无法获取课程id");
        }
        RequestService("/order/" + id, "POST", null, function(data){
            if (data.success) {
                window.location.href = "/order/pay?orderId=" + data.resultObject;
            } else {
                showTip(data.errorMessage);
            }
        });
    });
});
