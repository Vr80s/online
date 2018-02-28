function is_weixin() {
	var ua = navigator.userAgent.toLowerCase();
	if (ua.match(/MicroMessenger/i) == "micromessenger") {
		return true;
	} else {
		return false;
	}
	gift
}
if (!is_weixin()) {
	$(".weixin_li").remove();
}
/**
 * 判断是否需要跳转到pc网页
 */
h5PcConversions(true, course_id);

//if (localStorage.getItem("userId") == null) {
//	location.href = "/xcview/html/share.html?course_id=" + course_id;
//}
/**
 * 视频id
 */
var videoId = "";
var teacherId;
var teacherName;
var courseHead = "";
var roomNumber = "";
var lineState = 1;
var result = "";
// 统一提交的方法
requestService(
		"/xczh/course/liveDetails",
		{
			courseId : course_id
		},
		function(data) {
			if (data.success) {
				result = data.resultObject;
				// 视频id
				videoId = result.directId;
				watchState = result.watchState;
				teacherId = result.userLecturerId; // 讲师Id
				teacherName = result.name; // 讲师名

				$("#userId").val(result.userId);
				$("#teacherId").val(result.userId);
				courseHead = result.smallImgPath;

				$(".details_size span:eq(1)").html(result.giftCount);
				$(".details_size span:eq(0)").html(result.learndCount);

				// 关注数
				$(".n_guanzhu").html(result.focusCount);
				// 粉丝数
				$(".n_fensi").html(result.fansCount);

				/**
				 * 为详情页面添加数据
				 */
				$(".headImg").attr("src", result.headImg);
				$(".guanz_headImg").attr("src", result.headImg);
				$(".main_title").find('.p0').html(result.name);
				
				
				$(".details_chat1").attr("src", result.headImg);
				var children = $("#zhiboxiangqing [class='p1']").text(
						result.gradeName);
				var children = $("#zhiboxiangqing [class='p2'] span").text(
						teacherName);
				var children = $("#zhibopinglun [class='p1']").text(
						result.gradeName);
				var children = $("#zhibopinglun [class='p2']")
						.text(result.name);

				$(".anchor_center").text(result.description);

				/**
				 * 关注 0 未关注 1 关注
				 */
				if (result.isFocus == 1) {
					$(".add_follow").find('p').html("已关注");
				} else if (result.isFocus == 0) {
					$(".add_follow").find('p').html("加关注");
				}
				
				lineState = result.lineState;
				/**
				 * 直播状态1.直播中，2预告，3直播结束 4 即将直播
				 */
				if (lineState == 3) { // 隐藏送礼 //直播回放

					$("title").text("熊猫中医");
					$(".history_span").text("直播回放");
					$("#mywords").css("width", "12rem");
					$("#face").css("top", "1.45rem");
					$("#face").css('left', '0.06rem');

					$(".give_a1").hide();
					$(".give_a1_img").hide();
					$(".give_a1_span02").hide();
					$(".poson").css('right', '0rem');
					$(".poson").css('margin-left', '0.4rem');
					$(".div_img").css('margin-left', '0.35rem');   //左侧  图片笔
					$("#face").css('position', 'absolute');  //表情
					$("#face").css('top', '0');   //表情
					$("#face").css('left', '-0.05rem');  //表情
//					$("#sendChat").css('right', '-2.2rem !important');  //发送按钮向右距离
					$("#sendChat").addClass('important');  //发送按钮 添加class
					
					$(".give_a01").show();
					$("#sendChat").show();
//					点击输入框
					$("#mywords").click(function() {
						$(".give_a1").hide();
						$(".give_a1_img").hide();
						$(".div_input").css("background", "none");

					});
					$(".details_size").hide();
//					$(".poson").css('right', '-2.1rem');
					$(".div_input").css('width', '13.2rem');
					$(".coze_bottom_input").css('margin-left','0rem');
					$(".give_a01").css('margin-left','0.5rem');
					$(".give_a01").css('right','0.6rem');  //表情
//					$(".coze_bottom img").css('margin-left','0.3rem');  //微吼表情包
					
					$("#sendChat").addClass('importants');  //发送
					
					
					

//					点击表情
					$("#face").click(function() {
						$(".coze_bottom").css('bottom', '7.1rem');
//						$(".facebox-mobile").css('left', '-13.6rem');
//						$(".facebox-mobile").addClass("facebox-mobiles");
					});

					/* 点击发送 */
					$("#sendChat").click(function() {
						$(".give_a1").hide(); /* 礼物隐藏 */
						$(".give_a1").css("display", "none"); /* 礼物隐藏 */
						$(".coze_bottom").css("bottom", "0"); /* 最底部区域到最下方 */
						/*$(".face_img01").css("background","url('/xcview/images/face.png) no-repeat')";
						$(".face_img01").css("background-size","100% 100%");*/
						
						$(".face_img01").css('background','url(/xcview/images/face.png) no-repeat');
					 
						$(".face_img01").css("background-size","100% 100%");
						
					});
					
//					点击聊天
					$(".details_footer_width .li1").click(function() {
						
						$(".div_input").css("width", "12rem");
						$("#mywords").css("width", "12rem");
						$(".coze_bottom input").css("width", "12rem");
						/*$("#face").css("top", "1.45rem");
						$("#face").css('left', '0.06rem');*/
					});

				} else {

					// 正在直播
					$("title").text("熊猫中医");
					$(".history_span").text("直播中");

					$("#mywords").click(function() {
						$(".coze_bottom input").css("width", "12rem");
						$(".div_input").css("background", "none");
					});
					
					//		coze   点击其他区域，聊天区域
					$(".coze").click(function(){
						$(".send_gifts").hide();   /*礼物区域隐藏*/
						$("#sendChat").hide();  /*发送按钮隐藏*/
			   			$(".give_a01").hide();     /*表情隐藏*/
			            $(".coze_bottom input").css("width","13.5rem");   /*改变聊天input长度*/
			   			$(".give_a1").show();  /*礼物显示*/	
					});
					
			//		点击课件之间的  发送 礼物切换
					$(".details_footer_width li").click(function(){
						$(".send_gifts").hide();   /*礼物区域隐藏*/
						$("#sendChat").hide();  /*发送按钮隐藏*/
			   			$(".give_a01").hide();     /*表情隐藏*/
			            $(".coze_bottom input").css("width","13.5rem");   /*改变聊天input长度*/
			   			$(".give_a1").show();  /*礼物显示*/
			   			$(".div_input").css("background","block");  /*input背景色隐藏*/
					});

					// 点击送礼开始 --显示送礼列表
					$(".give_a1")
							.click(
									function() {

										requestService(
												"/xczh/manager/getWalletEnchashmentBalance",
												null,
												function(data) {
													if (data.success) {
														// var result =
														// data.resultObject;
														$("#xmbShowSpan")
																.html(
																		data.resultObject);
													} else {
														// alert("熊猫币余额获取失败！请稍后再试");
														$(".vanish2").show();
														setTimeout(function() {
															$(".vanish2")
																	.hide();
														}, 1500);
													}

												});
										$(".send_gifts").show();
									});

					// 点击发送
					$("#sendChat").click(function() {
						$(".give_a01").hide(); /* 表情隐藏 */
						$(this).hide(); /* 当前发送按钮隐藏 */
						$(".coze_bottom input").css("width", "13.5rem");
						$(".give_a1").show(); /* 礼物显示 */

					});
				}

				// 点击直播回放时的input mywords
				$("#mywords").click(function() {
					/*
					 * $(".send_img").css('background','url(/xcview/images/jiantou01.jpg)
					 * no-repeat');
					 */
					// $("#sendChat").css("background-size","100% 100%");
				});

				// 视频id不等于null的时候
				if (stringnull(videoId)) {
					chZJ(videoId, watchState);
				}
			}
		}, false)

// 聊天--关注开始

$(".add_follow").click(
		function() {
			// 评论id
			// lecturerId = $(this).attr("data-lecturerId");

			// 这个主播的粉丝数
			var n_fensi = $(".n_fensi").html();

			var src = $(this).find('img').attr('src');
			var type = 1;

			var htmlstr = $(".add_follow").find('p').html();

			if (htmlstr == "已关注") { // 增加关注
				type = 2;
			} else {
				type = 1;
			}
			requestService("/xczh/myinfo/updateFocus", {
				lecturerId : teacherId,
				type : type
			}, function(data) {
				if (data.success) {

					if (htmlstr == "已关注") {
						$(".add_follow").find('img').attr('src',
								'../images/follow.png');
						$(".add_follow").find('p').html("加关注");
						// $(".right_personal").find('span').html(parseInt(p)-1);

						$(".n_fensi").html(parseInt(n_fensi) - 1);
					} else {
						$(".add_follow").find('img').attr('src',
								'../images/follow_one.png');
						$(".add_follow").find('p').html("已关注");
						// 粉丝数
						$(".n_fensi").html(parseInt(n_fensi) + 1);
						// $(".right_personal").find('span').html(parseInt(p)+1);

					}
				}
			})
		});
		
		
		
		

/**
 * 点击主播头像跳转主播页面
 */
function userIndex() {
	location.href = "/xcview/html/live_personal.html?userLecturerId="
			+ teacherId;
}

/**
 * 初始化礼物列表
 */
requestService(
		"/xczh/gift/list",
		{
			pageNumber : 1,
			pageSize : 100
		},
		function(data) {
			if (data.success) {
				var result = data.resultObject;
				var html = "";
				for (var i = 0; i < result.length; i++) {
					if (result[i].price > 0) {
						html += "<li><a href='javascript: ;'><div class='gifts_div'><img src='"
								+ result[i].smallimgPath
								+ "' alt='' /></div><div class='gift_p'><p giftId='"
								+ result[i].id
								+ "' class='liwu' style='font-size:0.6rem;color:#666;'>"
								+ result[i].name
								+ "</p><p class='jiage' style='font-size:0.6rem;color:#666;'>"
								+ "" + result[i].price + "</p></div></a></li>";
					} else {
						html += "<li><a href='javascript: ;'><div class='gifts_div'><img src='"
								+ result[i].smallimgPath
								+ "' alt='' /></div><div class='gift_p'><p giftId='"
								+ result[i].id
								+ "' class='liwu' style='font-size:0.6rem;color:#666;'>"
								+ result[i].name
								+ "</p><p class='jiage' style='font-size:0.6rem;color:#666;'>0</p></div></a></li>";
					}
				}
				$(".gift_ul_li").html(html);
			}
		}, false);

/**
 * 刷新礼物排行榜
 */
function refreshGiftRanking() {
	requestService(
			"/xczh/gift/rankingList",
			{
				pageNumber : 1,
				pageSize : 10,
				liveId : course_id
			},
			function(data) {
				if (data.success) {
					var list = data.resultObject;
					var html = "";
					for (var i = 0; i < list.length; i++) {
						var pName = "";
						if (i == 0) {
							pName = "状元";
						} else if (i == 1) {
							pName = "榜眼";
						} else if (i == 2) {
							pName = "探花";
						} else {
							pName = "第" + (i + 1) + "名";
						}
						var pLogo = "";
						if (i == 0) {
							pLogo = "/xcview/images/01_03.png";
						} else if (i == 1) {
							pLogo = "/xcview/images/02_03.png";
						} else if (i == 2) {
							pLogo = "/xcview/images/03_03.png";
						}

						html += "<div class='leaderboard_list'>\n";

						if (i == 0 || i == 1 || i == 2) {
							html += "<div class='leaderboard_left'>\n";
							html += "<img src='"
									+ pLogo
									+ "' alt='' style='width: 1.6rem;height:0.9rem' /><br />";
							html += "<span>" + pName + "</span>";
							/*html += "<div class='both'></div>";*/
						} else {
							html += "<div class='leaderboard_left' style='line-height: 1.8rem;'>\n";
							html += "<span>" + pName + "</span>";
						}
						html += "<div class='both'></div>"+"</div>\n"
								+ "<div class='leaderboard_center' title="
								+ list[i].userId + " >\n" + "<img src='"
								+ list[i].smallHeadPhoto + "' alt='' />\n"
								+ "<div class='leaderboard_center_size'>\n"
								+ "<p class='p1'>" + list[i].name + "</p>\n"
								+ "<p class='p2'>\n" + "贡献&nbsp;&nbsp;<span>"
								+ list[i].giftCount + "</span>\n" + "</p>\n"
								+ "</div>\n" + "</div>\n"
								+ "<img src='' alt=''\n"
								+ "class='leaderboard_list_right' />\n"
								+ "<div class='both'></div>\n" + "</div>";

					}
					$("#phbList").html(html);
				}
			}, false);

}

// 微博分享
document.getElementById('weiboShare').onclick = function(e) {
	var p = {
		url : getServerHost() + "/wx_share.html?courseId=" + course_id,/* 获取URL，可加上来自分享到QQ标识，方便统计 */
		title : result.gradeName,/* 分享标题(可选) */
		pic : result.smallImgPath
	/* 分享图片(可选) */
	};
	var s = [];
	for ( var i in p) {
		s.push(i + '=' + encodeURIComponent(p[i] || ''));
	}
	var _src = "http://service.weibo.com/share/share.php?" + s.join('&');
	window.open(_src);
};

// qq分享
document.getElementById('qqShare').onclick = function(e) {
	var p = {
		url : getServerHost() + "/wx_share.html?courseId=" + course_id,/* 获取URL，可加上来自分享到QQ标识，方便统计 */
		desc : '中医传承', /* 分享理由(风格应模拟用户对话),支持多分享语随机展现（使用|分隔） */
		title : result.gradeName,/* 分享标题(可选) */
		summary : result.description.stripHTML(),/* 分享描述(可选) */
		pics : result.smallImgPath
	/* 分享图片(可选) */
	};
	var s = [];
	for ( var i in p) {
		s.push(i + '=' + encodeURIComponent(p[i] || ''));
	}
	var _src = "http://connect.qq.com/widget/shareqq/index.html?" + s.join('&');
	window.open(_src);
};

// qq空间分享
document.getElementById('qqShare0').onclick = function(e) {
	var p = {
		url : getServerHost() + "/wx_share.html?courseId=" + course_id,/* 获取URL，可加上来自分享到QQ标识，方便统计 */
		desc : '中医传承', /* 分享理由(风格应模拟用户对话),支持多分享语随机展现（使用|分隔） */
		title : result.gradeName,/* 分享标题(可选) */
		summary : result.description.stripHTML(),/* 分享描述(可选) */
		pics : result.smallImgPath
	/* 分享图片(可选) */
	};
	var s = [];
	for ( var i in p) {
		s.push(i + '=' + encodeURIComponent(p[i] || ''));
	}
	var _src = "https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?"
			+ s.join('&');
	window.open(_src);
};
/** ************** 微信分享 ************************ */
/*
 * 注意： 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
 * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
 * 3. 完整 JS-SDK
 * 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
 * 
 * 如有问题请通过以下渠道反馈： 邮箱地址：weixin-open@qq.com 邮件主题：【微信JS-SDK反馈】具体问题
 * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
 */
/*
 * 如果是微信浏览器的话在进行加载这部分函数
 */
if (is_weixin()) {
	var ccontrollerAddress = "/bxg/wxjs/h5JSSDK";
	var urlparm = {
		url : window.location.href
	}
	var signObj = "";
	requestService(ccontrollerAddress, urlparm, function(data) {
		if (data.success) {
			signObj = data.resultObject;
			console.log(JSON.stringify(signObj));
		}
	}, false)

	wx.config({
		debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId : signObj.appId, // 必填，企业号的唯一标识，此处填写企业号corpid
		timestamp : signObj.timestamp, // 必填，生成签名的时间戳
		nonceStr : signObj.noncestr, // 必填，生成签名的随机串
		signature : signObj.sign,// 必填，签名，见附录1
		jsApiList : [ 'checkJsApi', 'onMenuShareTimeline',
				'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo',
				'onMenuShareQZone', 'hideMenuItems', 'showMenuItems' ]
	// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	var domain = window.location.protocol + "//" + document.domain;

	wx.ready(function() {
		// 发送到朋友
		wx.onMenuShareAppMessage({
			title : result.gradeName, // 分享标题
			desc : result.description.stripHTML(), // 分享描述
			link : domain + "/wx_share.html?courseId=" + course_id, // 分享链接
			imgUrl : result.smallImgPath, // 分享图标
			type : '', // 分享类型,music、video或link，不填默认为link
			dataUrl : '', // 如果type是music或video，则要提供数据链接，默认为空
			success : function() {
				// 用户确认分享后执行的回调函数
				$(".weixin_ceng").hide();
				$(".share").hide();
			},
			cancel : function() {
				// 用户取消分享后执行的回调函数
				$(".weixin_ceng").hide();
				$(".share").hide();
			}
		});
		// 发送到朋友圈
		wx.onMenuShareTimeline({
			title : result.gradeName, // 分享标题
			link : domain + "/wx_share.html?courseId=" + course_id, // 分享链接
			imgUrl : result.smallImgPath, // 分享图标
			success : function() {
				// 用户确认分享后执行的回调函数
				$(".weixin_ceng").hide();
				$(".share").hide();

			},
			cancel : function() {
				// 用户取消分享后执行的回调函数
				// alert("取消分享");
				$(".weixin_ceng").hide();
				$(".share").hide();
			}
		});
		// 发送到qq
		wx.onMenuShareQQ({
			title : result.gradeName, // 分享标题
			desc : result.description.stripHTML(), // 分享描述
			link : domain + "/wx_share.html?courseId=" + course_id, // 分享链接
			imgUrl : result.smallImgPath, // 分享图标
			success : function() {
				// 用户确认分享后执行的回调函数
				$(".weixin_ceng").hide();
				$(".share").hide();
				// alert("分享成功");
			},
			cancel : function() {
				// 用户取消分享后执行的回调函数
				// /alert("取消分享");
				$(".weixin_ceng").hide();
				$(".share").hide();
			}
		});
	})
}
