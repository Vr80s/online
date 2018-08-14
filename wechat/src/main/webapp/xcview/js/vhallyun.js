// 刷新页面 --》在观看
$(".video_end_top1 .div img").click(function() {
	setTimeout(function() {
		location.reload();
	}, 2000)
});


// 直播状态1.直播中，2预告，3直播结束 4 即将直播
if (lineState == 1 || lineState == 3) {
	// 初始化 微吼云播放器
	elsBind();

}

if (lineState == 1 || lineState == 3 || lineState == 4) {
	initChat();
	// 初始化消息
	msgList(0, 100);
}

var initVideoFalg =  0;
function elsBind() {

	window.doc = {};
	
	var readyCallback = function() {
		 initVideoFalg = 1;  	
			window.doc = new VhallDocPassive({
				channelId: vhallObj.channelId,// 频道Id
				docNode: 'my-doc-area' // 文档显示节点div id
			});

			var roomId = (lineState == 1 ? vhallObj.roomId : "");
			var liveType = (lineState == 1 ? "live" : "vod");
			var recordId = (lineState == 1 ? "" : vhallObj.recordId);

			VhallPlayer.init({
				roomId: roomId,
				type: liveType,
				recordId: recordId,
				// 回放Id，点播必填，直播不写
				videoNode: 'myVideo',
				complete: function() {
					VhallPlayer.play();
				}
			});
	}

	window.Vhall.ready(readyCallback);

	window.Vhall.config({
		appId: vhallObj.appId,
		// 应用 ID ,必填
		accountId: vhallObj.accountId,
		// 第三方用户唯一标识,必填
		token: vhallObj.token // token必填
	});

}

setInterval(function() {
	try {
		var netWorkstate = VhallPlayer.getNetworkState();
		if (netWorkstate == 3) {
			$(".video_end_top4").show();
		}
	} catch (error) {
		console.log(error);
		
		if(initVideoFalg !=1){
	 		elsBind();
	 	}
	}
}, 1000)


/**
 * 初始化消息
 */

function initChat() {
	setTimeout(function() {
		try {
			var md = document.getElementsByTagName("video")[0];
			if (md) {
				//开始播放
				//md.play();
				//$("video").attr("poster",courseHead);
				md.addEventListener("ended", function() {
					console.log("播放结束了");
				});
			}
		} catch (error) {
			console.log(error);
		}
		window.Vhall.ready(function() {
			/**
			 * 初始化聊天对象
			 */
			window.chat = new VhallChat({
				channelId: vhallObj.channelId
				// 频道Id
			});
			/**
			 * 监听聊天消息
			 */
			window.chat.on(function(msg) {
				// 在此收到聊天消息，消息内容为msg
				if (msg) {
					var str = chatLoad(msg, true);
					if (str != "") {
						$("#chatmsg").append(str);
					}
				}
				$("#mywords").val('');
			});

			/**
			 * 监听自定义消息
			 */
			window.chat.onCustomMsg(function(msg) {
				msg = JSON.parse(msg);
				// 在聊天消息中显示
				var str = chatLoad(msg, false);
				if (str != "") {
					$("#chatmsg").append(str);
				}
				if (msg.type == 11) { // 礼物
					// 浮动效果
					createGiftList(msg.message);

				} else if (msg.type == 12) { // 开始直播

					setTimeout(function() {

						location.reload();

					}, 4000)

				} else if (msg.type == 13) { // 结束直播  --》  生成点播

					$(".video_end_top2").show();

				} else if (msg.type == 14) { // 退出直播间，但是没有结束直播

					$(".video_end_top3").show();

				} else if (msg.type == 16) { // 回放生成成功

					$(".video_end_top0").hide();
					$(".video_end_top2").hide();
					$(".video_end_top1").show();

				} else if (msg.type == 17) { // 回放生成失败

					$(".video_end_top0").hide();
					$(".video_end_top2").hide();
					$(".video_end_top1").hide();
					$(".video_end_top").show();
				}

				$(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTo", "bottom");
			})

			window.chat.join(function(msg) {
				viewJoinleaveRoomInfo(msg, "join");
			})

			window.chat.leave(function(msg) {
				viewJoinleaveRoomInfo(msg, "leave");
			})
		});
		// 在初始化一个消息的
		window.Vhall.config({
			appId: vhallObj.appId,
			// 应用 ID ,必填
			accountId: vhallObj.accountId,
			// 第三方用户唯一标识,必填
			token: vhallObj.token
			// token必填
		});
	}, 1000);


	/**
	 * 发送聊天消息
	 */
	$("#sendChat").click(function() {
		$(".coze_bottom").css("bottom", "0rem"); // 这是输入框在最底部,添加到其他文件不起作用
		var text = $("#mywords").val();
		if (isNotBlank(text)) {
			var content = {
				type: 10,
				// 消息类型 10 聊天消息 礼物 11
				message: {
					content: text,
					// 发送的内容
					role: "normal" // 发送人的角色 主播： host 普通用户： normal
				}
			}
			/**
			 * 发送消息
			 */
			requestService("/xczh/vhall/vhallYunSendMessage", {
				channel_id: vhallObj.channelId,
				body: JSON.stringify(content)
			}, function(data) {
				if (data.success) {
					$("#mywords").val('');
				}
			});
		} else {
			webToast("评论内容不能为空", "middle", 1500);
		}
	});

}


$(".chatmsg-box").mCustomScrollbar();

/**
 * 获取消息列表
 *
 * @param {}
 *            pos 第几页
 * @param {}
 *            limit 每页多少条
 */

function msgList(pos, limit) {

	var num = (pos - 1) * limit;
	num = num < 0 ? 0 : num;

	var params = {
		channel_id: vhallObj.channelId,
		pos: num,
		limit: limit,
		start_time: "2017/01/01"
	}
	/**
	 * 获取列表啦
	 */
	requestService("/xczh/vhall/vhallYunMessageList", params, function(data) {
		if (data.success && data.resultObject.code == 200) {
			var res = data.resultObject;
			var e = "";
			for (var i = 0; i < res.data.length; i++) {
				var item = res.data[i].data;
				e += chatLoad(JSON.parse(item), true);
			}
			if (e != "") {
				$("#chatmsg").html(e);
			}
		}
	});
}

/**
 * 进入或退出直播间
 *
 * @param {}
 *            msg
 * @param {}
 *            joinOrLeave
 */

function viewJoinleaveRoomInfo(msg, joinOrLeave) {

	var userName = msg.nick_name;

	var content = "进入直播间";
	if (joinOrLeave == "join") {
		content = "进入直播间";
	} else if (joinOrLeave == "leave") {
		content = "退出直播间";
	}
	console.log("参与人数：" + msg.connection_online_num);
	console.log("当前在线人数：" + msg.user_online_num);
	var str = "<div class='coze_cen_ri'> " + "  <div class='coze_cen_bg_ri'> " + "<span class='span_name'>" + userName + "：</span>" + // 用户名
	"   " + content + "  " + " </div> " + " <div class='both'></div></div>";

	$("#chatmsg").append(str);
	$(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTo", "99999");

	var learndCount = sessionStorage.getItem("learndCount");
	if (isNotBlank(learndCount) && isNotBlank(msg.connection_online_num)) {
		learndCount = parseInt(learndCount) + parseInt(msg.connection_online_num);
	}
	$(".details_size span:eq(0)").html(learndCount);
}

/**
 * 聊天消息渲染
 *
 * @param {}
 *            content 自定义内容体
 * @return {} 是否转义
 */

function chatLoad(content, isParse) {

	try {
		var content = isParse ? JSON.parse(content) : content;
		var message = content.message;

		var htmlStr = "";
		if (content.type == 10) { // 聊天消息
			var userName = message.username;
			if (isNotBlank(message.role) && message.role == "host") { // 说明是主播
				userName = "<span class='span_zhubo'>主播</span>" + message.username;
			}
			//替换表情为url
			var contentEmoji = replaceEmoji(message.content);

			htmlStr = "<div class='coze_cen_ri'> " + "  <div class='coze_cen_bg_ri'> " + "<span class='span_name'>" + userName + "：</span>" + contentEmoji + "  " + " </div> " + " <div class='both'></div></div>";

		} else if (content.type == 11) { // 礼物消息
			htmlStr = "<div class='coze_cen_ri'> " + "<div class='coze_cen_bg_ri'>" + "<span class='span_name'>" + message.senderInfo.userName + "：</span>" + "赠送给主播1个<span style='color: #F97B49;'>" + message.giftInfo.name + "</span>" + " </div> " + "<div class='both'></div></div>";
		}
		return htmlStr;
	} catch (err) {
		console.error(err);
		return "";
	}
}

// 请求--》json文件
var emoji = [{"text":"[微笑]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_1@2x.png"},{"text":"[撇嘴]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_2@2x.png"},{"text":"[色]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_3@2x.png"},{"text":"[发呆]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_4@2x.png"},{"text":"[得意]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_5@2x.png"},{"text":"[流泪]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_6@2x.png"},{"text":"[害羞]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_7@2x.png"},{"text":"[闭嘴]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_8@2x.png"},{"text":"[睡]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_9@2x.png"},{"text":"[哭]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_10@2x.png"},{"text":"[尴尬]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_11@2x.png"},{"text":"[发怒]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_12@2x.png"},{"text":"[调皮]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_13@2x.png"},{"text":"[呲牙]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_14@2x.png"},{"text":"[惊讶]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_15@2x.png"},{"text":"[难过]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_16@2x.png"},{"text":"[酷]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_17@2x.png"},{"text":"[汗]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_18@2x.png"},{"text":"[抓狂]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_19@2x.png"},{"text":"[吐]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_20@2x.png"},{"text":"[偷笑]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_21@2x.png"},{"text":"[愉快]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_22@2x.png"},{"text":"[白眼]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_23@2x.png"},{"text":"[傲慢]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_24@2x.png"},{"text":"[饥饿]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_25@2x.png"},{"text":"[困]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_26@2x.png"},{"text":"[惊恐]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_27@2x.png"},{"text":"[流汗]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_28@2x.png"},{"text":"[憨笑]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_29@2x.png"},{"text":"[悠闲]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_30@2x.png"},{"text":"[奋斗]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_31@2x.png"},{"text":"[咒骂]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_32@2x.png"},{"text":"[疑问]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_33@2x.png"},{"text":"[嘘]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_34@2x.png"},{"text":"[晕]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_35@2x.png"},{"text":"[疯了]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_36@2x.png"},{"text":"[衰]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_37@2x.png"},{"text":"[骷髅]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_38@2x.png"},{"text":"[敲打]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_39@2x.png"},{"text":"[再见]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_40@2x.png"},{"text":"[擦汗]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_41@2x.png"},{"text":"[抠鼻]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_42@2x.png"},{"text":"[鼓掌]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_43@2x.png"},{"text":"[糗大了]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_44@2x.png"},{"text":"[坏笑]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_45@2x.png"},{"text":"[左哼哼]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_46@2x.png"},{"text":"[右哼哼]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_47@2x.png"},{"text":"[哈欠]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_48@2x.png"},{"text":"[鄙视]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_49@2x.png"},{"text":"[委屈]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_50@2x.png"},{"text":"[快哭了]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_51@2x.png"},{"text":"[阴险]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_52@2x.png"},{"text":"[亲亲]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_53@2x.png"},{"text":"[吓]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_54@2x.png"},{"text":"[可怜]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_55@2x.png"},{"text":"[菜刀]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_56@2x.png"},{"text":"[西瓜]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_57@2x.png"},{"text":"[啤酒]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_58@2x.png"},{"text":"[篮球]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_59@2x.png"},{"text":"[乒乓]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_60@2x.png"},{"text":"[咖啡]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_61@2x.png"},{"text":"[饭]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_62@2x.png"},{"text":"[猪头]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_63@2x.png"},{"text":"[玫瑰]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_64@2x.png"},{"text":"[凋谢]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_65@2x.png"},{"text":"[嘴唇]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_66@2x.png"},{"text":"[爱心]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_67@2x.png"},{"text":"[心碎]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_68@2x.png"},{"text":"[蛋糕]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_69@2x.png"},{"text":"[闪电]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_70@2x.png"},{"text":"[炸弹]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_71@2x.png"},{"text":"[刀]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_72@2x.png"},{"text":"[足球]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_73@2x.png"},{"text":"[瓢虫]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_74@2x.png"},{"text":"[便便]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_75@2x.png"},{"text":"[月亮]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_76@2x.png"},{"text":"[太阳]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_77@2x.png"},{"text":"[礼物]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_78@2x.png"},{"text":"[拥抱]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_79@2x.png"},{"text":"[强]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_80@2x.png"},{"text":"[弱]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_81@2x.png"},{"text":"[握手]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_82@2x.png"},{"text":"[胜利]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_83@2x.png"},{"text":"[抱拳]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_84@2x.png"},{"text":"[勾引]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_85@2x.png"},{"text":"[拳头]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_86@2x.png"},{"text":"[差劲]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_87@2x.png"},{"text":"[爱你]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_88@2x.png"},{"text":"[NO]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_89@2x.png"},{"text":"[OK]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_90@2x.png"}];

var pattern1 = /\[[\u4e00-\u9fa5]+\]/g;
var pattern2 = /\[[\u4e00-\u9fa5]+\]/;
/**
 * 表情
 */

function replaceEmoji(contents) {
	content = contents.match(pattern1);
	if (content == null) {
		return contents;
	}
	str = contents;
	for (i = 0; i < content.length; i++) {
		var src = "";
		for (j = 0; j < emoji.length; j++) {
			if (emoji[j].text == content[i]) {
				src = emoji[j].imgUrl;
				break;
			}
		}
		if (src != "") {
			var imgBag = "<img src=" + src + " style='width:20px;' />";
			str = str.replace(pattern2, imgBag);
		}
	}
	return str;
}