/**
 * 先写一部分吧js把，今后在完善
 */
var controllerAddress = "/bxg/bunch/list";
var pageNumber = 1;
var pageSize = 5;
var loadFlag = true;
/**
 * 得到分类
 */
function requestCategorylist() {
	requestService("/bxg/menu/list", null, function(data) {
		if (data.success) {
			// 直播数据结果
			var categoryList = data.resultObject;
			var liveHtml0 = "<ul class='page' id='pagenavi'>";
			for (var int = 1; int < categoryList.length + 1; int++) {
				var category = categoryList[int - 1];
				liveHtml0 += "<li class='frequency_div" + int + "' name='"
						+ category.id + "'>";
				liveHtml0 += "<p >" + category.name + "</p>";
				/*liveHtml0 += "<img src='../images/indent_bg.png' alt=''>";*/
				liveHtml0 += "</li>";
			}
			liveHtml0 += "</ul>";

			$("#frequency_div").html(liveHtml0);
			/*requestbunchList(true, bunchType);*/
			return true;
		} else {
			loadFlag = false;
		}
	}, false);
}
requestCategorylist();

/*
 * 上面的分类 点击事件
 */
$("#pagenavi li").click(function(){
	
	$("#pagenavi li").each(function(){
		$(this).removeClass('frequency_div_p');
		//$(".frequency_div2 img").hide();
	});
	
	$(this).addClass('frequency_div_p');
	var name = $(this).attr("name");
	requestbunchList(true, name);
})




var multimedia_type = getQueryString("multimedia_type");
if (multimedia_type == 1) { // 视频
	$(".base ul li:eq(1) a").css("background","url(../images/a02.png) no-repeat");
	$(".base ul li:eq(1) a").css("background-size", "100% 100%");

	$("title").text("视频");
	$(".home_top span").html("视频");
} else {
	$(".base ul li:eq(2) a").css("background","url(../images/a03.png) no-repeat");
	$(".base ul li:eq(2) a").css("background-size", "100% 100%");
	
	$("title").text("音频");
	$(".home_top span").html("音频");
	$(".base_li2_a2 p").css("color","#00BC12");
	$(".base_li2_a1 p").css("color","#333");
	
}
var bunchType = sessionStorage.getItem("bunchType");
if (!stringnull(bunchType)) {
	bunchType = 1;
	$(".frequency_div" + bunchType + "").click();
} else {
	$(".frequency_div" + (Number(bunchType)+1) + "").click();// 类别获取焦点
}
// 跳转到观看记录页面
document.getElementById("home_img_right").addEventListener("tap", function() {

	if (multimedia_type == 1) { // 视频
		sessionStorage.setItem("historyType", 2);
	} else {
		sessionStorage.setItem("historyType", 3);
	}
	location.href = "/bxg/xcpage/history";
})

// 点击搜索进入关键字搜索页面吧....
document.getElementById("home_img").addEventListener("tap", function() {
	if (multimedia_type == 1) { // 视频
		sessionStorage.setItem("queryType", 2)
	} else {
		sessionStorage.setItem("queryType", 3);
	}
	location.href = "/bxg/xcpage/queryResult";
})

/**
 * ************************************请求列表页**************************************************
 */
/**
 * 请求点播列表
 * 
 * @param downOrOn
 *            上拉加载还是下拉刷新 true 下拉 false 上拉
 */
function requestbunchList(downOrOn, menuId) {
	if (downOrOn) {
		pageNumber = 1;
	} else {
		pageNumber = pageNumber + 1;
	}
	var urlparm = {
		pageNumber : pageNumber,
		pageSize : pageSize,
		menu_id : menuId,
		multimedia_type : multimedia_type
	};
	requestService(
			controllerAddress,
			urlparm,
			function(data) {
				if (data.success) {
					
					/**
					 * 清空
					 */
					var content = document.getElementById("video_list_ul");
						if (downOrOn) {
							content.innerHTML = "";
						}
						// 直播数据结果
						var bunchList = data.resultObject;
					
					if (bunchList.length > 0) {
						for (var int = 0; int < bunchList.length; int++) {

							var odiv = document.createElement("div");
							odiv.className = "mui-table-view-cell";
							odiv.id = 'div' + int;
							var listHtml = "<ul>";
							var object = bunchList[int];
							listHtml += "<li id="+object.id+"><div class='video_list_img'>";
							listHtml += "<img src="+object.smallImgPath+"  alt='' /></div>";
							listHtml += "<div class='video_list_size'>";

							listHtml += "<div class='video_list_size_title'>"
									+ object.gradeName + "</div>";
							listHtml += "<div class='video_list_size_left video_list_size1'>"
									+ "<img src='../images/microphone.png' alt='' /><span style='width: 2.5rem;'>"+object.name+"</span></div>";

							listHtml += "<div class='video_list_size_center video_list_size1 video_list_size1_two' style='width: 2.5rem;'>"
									+ "<img src='../images/eye.png' alt='' /><span>"
									+ object.learndCount + "</span></div>";

							listHtml += "<div class='video_list_size_right video_list_size1' style='width: 3rem;margin-right: 0;'>"
									+ "<img src='../images/time.png' alt='' /><span>"
									+ object.courseLength + "小时" + "</span></div>";

							listHtml += "<div class='both'></div></div><div class='both'></div></li>";

							listHtml += "</ul>";

							odiv.innerHTML = listHtml;
							content.appendChild(odiv);
						}
						mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);
						mui('#refreshContainer').pullRefresh().refresh(true);
					} else {
						var cells = document.body.querySelectorAll('.mui-table-view-cell');
						var num = cells.length;
						console.log(num);
						if (num == "0") {
							document.getElementById("video_list_ul").innerHTML = "";
							var odiv = document.createElement("div");
							odiv.id = 'content';
							
							var img_str = "video_null.png";
							var tip_str = '音频正在赶来的路上...';
							if(multimedia_type==1){
								img_str = "video_null.png";
								tip_str = '视频正在赶来的路上...';
							}	
							odiv.innerHTML = '<img src="/xcviews/images/'+img_str+'" alt="" class="kongbai" /><p>'+tip_str+'</p>';
							document.getElementById("video_list_ul").appendChild(odiv);
						} else {
							mui('#refreshContainer').pullRefresh().endPullupToRefresh(true);
						}
					}
				} else {
				}
			});
}

mui("#refreshContainer").on('tap', 'li', function (event) {
	var ev = this;
	var courseId = ev.id;
	/**
	 * 增加观看记录
	 */
	addHistory(courseId,Number(multimedia_type)+1);
	sessionStorage.setItem("bunchPage", 1);
	$("#frequency_div li").each(
			function(index, element) {
				var className = element.className;
				if (className.indexOf("frequency_div_p") != -1) {
					sessionStorage.setItem("bunchType", index);
					location.href = "/xcviews/html/particulars.html?courseId="
							+ courseId;
					return;
				}
	});
});

/**
 * ************************************ 页面刷新下刷新事件
 * **************************************************
 */
mui.init();
mui.init({
	pullRefresh : {
		container : '#refreshContainer',
		down : {
			callback : pulldownRefresh
		},
		up : {
			contentrefresh : '正在加载...',
			callback : pullupRefresh
		}
	}
});
/**
 * 下拉刷新
 */
function pulldownRefresh() {
	setTimeout(function() {
		$("#frequency_div li").each(function(index, element) {
			var className = element.className;
			if (className.indexOf("frequency_div_p") != -1) {
				var name = $(this).attr("name");
				requestbunchList(true, name);
				return;
			}
		});
		mui('#refreshContainer').pullRefresh().endPulldownToRefresh(); // refresh
																		// completed
	}, 500);
};
var count = 0;
/**
 * 上拉加载具体业务实现
 */
function pullupRefresh() {
	setTimeout(function() {
		$("#frequency_div li").each(function(index, element) {
			var className = element.className;
			if (className.indexOf("frequency_div_p") != -1) {
				var name = $(this).attr("name");
				requestbunchList(false, name);
				return;
			}
		});
	}, 500);
}
