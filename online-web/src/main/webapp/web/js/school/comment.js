/*
 * 点击我要评价
 *	 
 */
$(".want-evaluate").click(function() {
	// 判断是否登录
	if (!loginStatus) { //未登录
		localStorage.username = null;
		localStorage.password = null;
		if ($(".login").css("display") == "block") {
			$(".login").css("display", "none");
			$(".logout").css("display", "block");
			$('#login').modal('show');
		} else {
			$('#login').modal('show');
		}
	} else { //已登录
		//没有购买以及购买或者评论过的，隐藏这写东东
		if(commentCode == 0 || commentCode == 2){
			$(".commentCode").hide();
		}
		$(".bg-modal").removeClass("hide");
		$(".wrap-modal").removeClass("hide");
		$(".submission").css("background", "999");
	}
});
// 关闭窗口
$(".close-impression").click(function() {
	emptyFromValue();
	$(".bg-modal").addClass("hide");
	$(".wrap-modal").addClass("hide");
});

// 回复评论和点赞按钮加颜色
$(".operation-reply-li").click(function() {
			var criticizeId = $(this).parent(".operation-reply").attr(
					"data-criticizeId");

			var falg = true;
			if ($(this).hasClass("selected")) {
				$(this).removeClass("selected");
				falg = false;
			} else {
				$(this).addClass("selected");
			}
			var paramsObj = {
				criticizeId : criticizeId,
				praise : falg
			};
			RequestService("/criticize/updatePraise", "post", paramsObj,
					function(data) {
						if (data.success) {
//							var obj = $(".operation-reply li").eq(0).children(".praiseSum");
//							var praiseSum = obj[0].innerText;
//							if (falg) {
//								praiseSum = parseInt(praiseSum) + 1;
//							} else {
//								praiseSum = parseInt(praiseSum) - 1;
//							}
//							obj[0].innerText = praiseSum;
//							alert("中");
							location.reload();
						} else {
							alert("点赞失败");
						}
					})
		})

// 点击回复图标出现输入框
$(".reply-icon").click(function() {
	if ($(this).hasClass("selected")) {
		$(this).removeClass("selected");
	} else {
		$(this).addClass("selected");
	}
	// 判断存不存在此cookie
	// 请求下后台吧
	if (loginStatus) {
		$(".header-input img").attr("src",smallHeadPhoto);
		$(".reply_login").removeClass("hide");
		$(".reply_no_login").addClass("hide");
	} else {
		$(".reply_login").removeClass("hide");
		$(".reply_no_login").addClass("hide");
	}
	if ($(this).hasClass("selected")) {
		$(this).parents().siblings(".wrap-input").removeClass("hide");
	} else {
		$(this).parents().siblings(".wrap-input").addClass("hide");
	}
})

// 提交回复
$(".reply_criticize").click(function() {
			var criticizeId = $(this).parent(".wrap-input").attr(
					"data-criticizeId");
			var criticizeContent = $(this).parent(".wrap-input").find("input")
					.val();
			if (criticizeContent == null || criticizeContent == undefined || criticizeContent == "") {
				return;
			}
			var paramsObj = {
				content : criticizeContent,
				criticizeId : criticizeId,
			}
			if (collection == 1) {
				paramsObj.collectionId = courseId;
			}
			RequestService("/criticize/saveReply", "post", paramsObj, function(
					data) {
				if (data.success) {
					//alert("回复成功");
					location.reload();
				} else {
					alert("回复失败");
				}
			})
		})

// 星星五星好评
$('.impression-star img').each(function(index) {
	var star = '../../web/images/star-dim.png'; // 普通灰色星星图片的存储路径
	var starRed = '../../web/images/star-light.png'; // 红色星星图片存储路径
	var prompt = [ '1分', '2分', '3分', '4分', '5分' ]; // 评价提示语
	this.id = index; // 遍历img元素，设置单独的id
	$(this).on("mouseover click", function() { // 设置鼠标滑动和点击都会触发事件
		$('.impression-star img').attr('src', star);// 当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色
		$(this).attr('src', starRed); // 设置鼠标当前所在图片为打星颜色图
		$(this).prevAll().attr('src', starRed); // 设置鼠标当前的前面星星图片为打星颜色图
		$(this).siblings('span').text(prompt[this.id]); // 根据id的索引值作为数组的索引值

		isSubmit();
	});
});
// 节目内容
$('.impression-face img').each(function(index) {
	var star = '../../web/images/gs.png'; // 普通灰色星星图片的存储路径
	var starRed = '../../web/images/rs.png'; // 红色星星图片存储路径
	var prompt = [ '一般', '一般', '好', '好', '很好' ]; // 评价提示语
	this.id = index; // 遍历img元素，设置单独的id
	$(this).on("mouseover click", function() { // 设置鼠标滑动和点击都会触发事件
		$('.impression-face img').attr('src', star);// 当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色
		$(this).attr('src', starRed); // 设置鼠标当前所在图片为打星颜色图
		$(this).prevAll().attr('src', starRed); // 设置鼠标当前的前面星星图片为打星颜色图
		$(this).siblings('span').text(prompt[this.id]); // 根据id的索引值作为数组的索引值

		isSubmit();
	});
});
// 主播演绎
$('.impression-show img').each(function(index) {
	var star = '../../web/images/gs.png'; // 普通灰色星星图片的存储路径
	var starRed = '../../web/images/rs.png'; // 红色星星图片存储路径
	var prompt = [ '一般', '一般', '好', '好', '很好' ]; // 评价提示语
	this.id = index; // 遍历img元素，设置单独的id
	$(this).on("mouseover click", function() { // 设置鼠标滑动和点击都会触发事件
		$('.impression-show img').attr('src', star);// 当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色
		$(this).attr('src', starRed); // 设置鼠标当前所在图片为打星颜色图
		$(this).prevAll().attr('src', starRed); // 设置鼠标当前的前面星星图片为打星颜色图
		$(this).siblings('span').text(prompt[this.id]); // 根据id的索引值作为数组的索引值

		isSubmit();
	});
});
// 很赞,干货很多
$(".impression-setlist li").click(function() {
	if ($(this).hasClass("impression-active")) {
		$(this).removeClass("impression-active");
	} else {
		$(this).addClass("impression-active");
	}
	isSubmit();
})

//$("#commentContent").change(
//		function() {
//			var commentContent = $(this).val();
//			if (commentContent == null || commentContent == ""
//					|| commentContent.trim().length <= 0) {
//				$('.submission').attr("disabled", true);
//				$(".submission").css("background",  "#999");
//			}
//			isSubmit();
//})

$('#commentContent').keyup(function(){
	var commentContent = $(this).val();
	if (commentContent == null || commentContent == "" || commentContent.trim().length <= 0) {
		$('.submission').attr("disabled", true);
		$(".submission").css("background",  "#999");
	}
	isSubmit();
})


/**
 * 清空属性啦
 */
function emptyFromValue() {

	$('.impression-star img,.impression-face img,.impression-show img').attr(
			'src', "../../web/images/star-dim.png");
	$('.impression-star span,.impression-face span,.impression-show span')
			.text("");
	$('.impression-setlist li').removeClass("impression-active");
	$("#commentContent").val("");
}

/**
 * 验证表单是否提交
 */
function isSubmit() {
	
	if(commentCode == 1){
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
		$(".impression-setlist li ").each(
				function() {
					var className = $(this).attr("class");
					if (className != null && className != undefined
							&& className.indexOf("impression-active") != -1) {
						value += $(this).attr("data-value") + ",";
					}
				})
		if (value == "") {
			$('.submission').attr("disabled", true);
			$(".submission").css("background", "#999");
			return;
		}
	}
	
	var commentContent = $("#commentContent").val();
	if (commentContent == null || commentContent == ""
			|| commentContent.trim().length <= 0) {
		$('.submission').attr("disabled", true);
		$(".submission").css("background", "#999");
		return;
	}
	
	
	$('.submission').removeAttr("disabled");
	$(".submission").css("background", "#00BC12");
}

// 点击发表评论
$(".submission").click(function() {
	// 发表评论
	var star = $(".impression-star img[src='../../web/images/star-light.png']").length;
	var face = $(".impression-face img[src='../../web/images/rs.png']").length;
	var show = $(".impression-show img[src='../../web/images/rs.png']").length;
	var value = "";
	$(".impression-setlist li ").each(function() {
		var className = $(this).attr("class");
		if (className != null
				&& className != undefined
				&& className
						.indexOf("impression-active") != -1) {
			value += $(this).attr("data-value")
					+ ",";
		}
	})
	if (value.length > 0) {
		value = value.substring(0, value.length - 1);
	}

	var commentContent = $("#commentContent").val();

	var paramsObj = {
		overallLevel : star,
		contentLevel : face,
		deductiveLevel : show,
		criticizeLable : value,
		content : commentContent,
		courseId : courseId,
		userId : userId
	};
	if (collection == 1) {
		paramsObj.collectionId = courseId;
	}
	/*
	 * 啦啦啦啦啦。提交评论啦
	 */
	RequestService("/criticize/saveCriticize", "post",
			paramsObj, function(data) {
		if (data.success) {
//			emptyFromValue();
//			$(".bg-modal").addClass("hide");
//			$(".wrap-modal").addClass("hide");
			location.reload();
		} else {
			alert("不中啊");
		}
	})
});