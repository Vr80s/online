
// 房间JID
//var ROOM_JID = course_id+'xczh@conference.47.92.39.21';

// XMPP连接
var connection = null;

// 当前状态是否连接
var connected = false;

// 当前登录的JID
var jid = "";

var giftList;
// 初始化没有参数的队列
var queue = new Queue();

// 连接状态改变的事件
function onConnect(status) {
	if (status == Strophe.Status.CONNFAIL) {
		autoLogin();
	} else if (status == Strophe.Status.AUTHFAIL) {
		autoLogin();
	} else if (status == Strophe.Status.DISCONNECTED) {
		autoLogin();
		// connected = false;
	} else if (status == Strophe.Status.CONNECTED) {
		// alert("连接成功，可以开始聊天了！");
		connected = true;

		// 当接收到<message>节，调用onMessage回调函数
		connection.addHandler(onMessage, null, 'message', null, null, null);

		// 首先要发送一个<presence>给服务器（initial presence）
		connection.send($pres().tree());

		// 发送<presence>元素，加入房间
		var pres = $pres({
			from : jid,
			to : ROOM_JID + "/" + guId// jid.substring(0,jid.indexOf("@"))//昵称
		}).c('x', {
			xmlns : 'http://jabber.org/protocol/muc'
		}).tree();
		connection.send(pres);
		// connection.sendIQ(pres);//获取房间列表
	}
}

String.prototype.replaceAll = function(FindText, RepText) {
	regExp = new RegExp(FindText, "g");
	return this.replace(regExp, RepText);
}
var sendTime;
function createGiftList(gift) {
	if (sendTime == null) {

		requestService("/bxg/common/getSystemTime", null, function(data) {
			sendTime = data;
		}, false)

	}
	if (gift.messageType == 0 || gift.messageType == 1) {
		var time = data.giftInfo == null ? data.rewardInfo.time
				: data.giftInfo.time
		if (time == null)
			return;
		if (parseInt(sendTime) > parseInt(time))
			return;
		queue.push(gift);
		createGiftShow();
	}
}
var gif = [];
var num = [];
var min = [];
var addn = [];
// 生成礼物
function giftShow(gift, f) {
	/*
	 * {"balanceTotal":"0","senderInfo":
	 * {"avatar":"http://attachment-center.ixincheng.com:38080/data/picture/other/2017/12/03/19/fdcd206c571a4225bb7c894eed464650.jpg",
	 * "userName":"宣哥","userId":"40288344601b29b701601b9aefdb0001"},
	 * "messageType":1,
	 * "giftInfo":{"continuousCount":1,"giftId":"339","count":1,"name":"杏仁糖","time":"1512308220536",
	 * "smallimgPath":"http://attachment-center.ixincheng.com:38080/data/picture/online/2017/09/23/14/0b77783055f44003bb32228eb3549887.png"},
	 * "giftCount":349}
	 */

/*	{"senderInfo":
	{"avatar":"http://attachment-center.ixincheng.com:38080/data/picture/other/2017/12/03/19/fdcd206c571a4225bb7c894eed464650.jpg","userName":"宣哥","userId":"40288344601b29b701601b9aefdb0001"},
	"messageType":0,"rewardTotal":"0.08","rewardInfo":{"rewardId":"362","price":0.01,"time":1512309378940}}*/
	// giftDsPuTong(content);
	if (gift.messageType == 1) { // 礼物
		var bottom = countChange()
		gif[f] = $("<li class='animation' style='position: absolute;bottom: "
				+ bottom
				+ "rem;'><div class='animation_div'><div class='animation_name'><p class='animation_name_p1'>"
				+ gift.senderInfo.userName
				+ "</p><p class='animation_name_p2'>送 "
				+ gift.giftInfo.name
				+ "</p></div><div class='animation_gift'><img src='"
				+ gift.giftInfo.smallimgPath
				+ "' alt='' /></div><div class='animation_span'>X<span class=addnum"
				+ f + ">1</span></div></div></li>");
		try {
			$("#liveGiftCount").html(gift.giftCount);
		} catch (error) {
			// 此处是负责例外处理的语句
		} finally {
			// 此处是出口语句
		}

	} else if (gift.messageType == 0) { // 红包
		var bottom = countChange()
		gif[f] = $("<li class='animation' style='position: absolute;bottom: "
				+ bottom
				+ "rem;'><div class='animation_div'><div class='animation_name'><p class='animation_name_p1'>"
				+ gift.senderInfo.userName
				+ "</p><p class='animation_name_p2'>打赏红包</p></div><div class='animation_gift1'><img src='/xcviews/images/packet.png' alt='' /></div></div></li>");
		try {
			$("#moneySum").html(gift.rewardTotal);
		} catch (error) {
			// 此处是负责例外处理的语句
		} finally {
			// 此处是出口语句
		}

	}
	// 添加到页面中并且2秒消失
	// $('.chatmsg-box ').append();
	gif[f].appendTo($(".chatmsg-box"));
	$(".num").html(gift.giftCount);
	$(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTo",
			"bottom");

	if (gift.giftInfo != null) {
		num[f] = gift.giftInfo.count;
	} else {
		num[f] = 100;
	}
	min[f] = 0;
	addn[f] = parseInt(Math.floor(num[f] / 100));

	if (f == 1) {
		if (num[f] < 100) {
			var s1 = setInterval(function() {
				if (min[f] < num[f]) {
					min[f]++;
					$('.addnum' + f).html(min[f])
				} else {
					clearInterval(s1);
					setTimeout(function() {
						clearGift();
					}, 1000);
				}
			}, 3000 / num[f])
		} else {
			var s1 = setInterval(function() {
				if (min[f] < num[f]) {
					min[f] += addn[f];
					$('.addnum' + f).html(min[f])
				} else {
					$('.addnum' + f).html(num[f])
					clearInterval(s1);
					setTimeout(function() {
						clearGift();
					}, 1000);
				}
			}, 2500 / 100)
		}
	} else if (f == 2) {
		if (num[f] < 100) {
			var s2 = setInterval(function() {
				if (min[f] < num[f]) {
					min[f]++;
					$('.addnum' + f).html(min[f])
				} else {
					clearInterval(s2);
					setTimeout(function() {
						clearGift();
					}, 1000);
				}
			}, 3000 / num[f])
		} else {
			var s2 = setInterval(function() {
				if (min[f] < num[f]) {
					min[f] += addn[f];
					$('.addnum' + f).html(min[f])
				} else {
					$('.addnum' + f).html(num[f])
					clearInterval(s2);
					setTimeout(function() {
						clearGift(f);
					}, 1000);
				}
			}, 2500 / 100)
		}
	} else {
		if (num[f] < 100) {
			var s3 = setInterval(function() {
				if (min[f] < num[f]) {
					min[f]++;
					$('.addnum' + f).html(min[f])
				} else {
					clearInterval(s3);
					setTimeout(function() {
						clearGift(f);
					}, 1000);
				}
			}, 3000 / num[f])
		} else {
			var s3 = setInterval(function() {
				if (min[f] < num[f]) {
					min[f] += addn[f];
					$('.addnum' + f).html(min[f])
				} else {
					$('.addnum' + f).html(num[f])
					clearInterval(s3);
					setTimeout(function() {
						clearGift(f);
					}, 1000);
				}
			}, 2500 / 100)
		}
	}

	function clearGift() {
		gif[f].remove();
		if (f == 1) {
			f1 = true;
		} else if (f == 2) {
			f2 = true;
		} else {
			f3 = true;
		}
	}

}

// 队列方法

/**
 * [Queue]
 * 
 * @param {[Int]}
 *            size [队列大小]
 */
function Queue(size) {
	var list = [];

	// 向队列中添加数据
	this.push = function(data) {
		if (data == null) {
			return false;
		}
		// 如果传递了size参数就设置了队列的大小
		if (size != null && !isNaN(size)) {
			if (list.length == size) {
				this.pop();
			}
		}
		list.unshift(data);
		return true;
	}

	// 从队列中取出数据
	this.pop = function() {
		return list.pop();
	}

	// 返回队列的大小
	this.size = function() {
		return list.length;
	}

	// 返回队列的内容
	this.quere = function() {
		return list;
	}
}

// 两个占位 f1 f2
var f1 = true;
var f2 = true;
var f3 = true;

function createGiftShow() {
	// 若f1位可用，。。。
	if (f1) {
		f1 = false;
		var gift = queue.pop();
		// 将礼物显示出来，设置显示时间。时间到了，f1 = true;，礼物占位结束。
		giftShow(gift, 1);
	} else if (f2) {// 若f2位可用，。。。
		f2 = false;
		var gift = queue.pop();
		// 将礼物显示出来，设置显示时间。时间到了，f1 = true;，礼物占位结束。
		giftShow(gift, 2);
	} else if (f3) {// 若f2位可用，。。。
		f3 = false;
		var gift = queue.pop();
		// 将礼物显示出来，设置显示时间。时间到了，f1 = true;，礼物占位结束。
		giftShow(gift, 3);
	} else if (!f1 && !f2 && !f3 && queue.size() > 0) {
		setTimeout(function() {
			createGiftShow();
		}, 4000);
	}
}

// 生成礼物
var count = 1;
function countChange() {
	if (count == 1) {
		count = 2;
		return 10;
	} else if (count == 2) {
		count = 3;
		return 7.7;
	} else {
		count = 1;
		return 5.5;
	}
}
var gif = [];
var num = [];
var min = [];
var addn = [];

// 接收到<message>
function onMessage(msg) {

	console.log(msg);
	// 解析出<message>的from、type属性，以及body子元素
	var from = msg.getAttribute('from');
	var type = msg.getAttribute('type');
	var elems = msg.getElementsByTagName('body');

	if (type == "groupchat" && elems.length > 0) {
		try {
			var body = elems[0];
			var text = Strophe.getText(body);
			text = text.replaceAll("&quot;", "\"");
			data = JSON.parse(text);
			createGiftList(data);
			console.log(text);
		} catch (err) {
			// console.info(err);
		}
	}
	return true;
}

function repalceAll(str, rstr, arstr) {
	while (str.split(rstr).length > 1) {
		str = str.replace(rstr, arstr);
	}
	return str;
}

$(document)
		.ready(
				function() {

					// 通过BOSH连接XMPP服务器
					$('#btn-login').click(
							function() {
								if (!connected) {
									connection = new Strophe.Connection(
											BOSH_SERVICE);
									connection.connect($("#input-jid").val(),
											$("#input-pwd").val(), onConnect);
									jid = $("#input-jid").val();
								}
							});

					// 发送消息
					$(".balance_send")
							.click(
									function() {

										if ($(".gift_ul_li_li .gift_p .liwu")
												.attr("giftId") == null) {

											alert("请先选择一个礼物!");
											return;
										}

										if (isNaN($("#giftCount").val())) {
											alert("非法的礼物数量!");
											return;
										}
										if ($("#giftCount").val() < 1) {
											alert("非法的礼物数量!");
											return;
										}

										var xmbShowSpan = $("#xmbShowSpan").html();
										var jiage = $(".gift_ul_li_li .gift_p .jiage").text();
										if(jiage<xmbShowSpan || jiage == 0){
											if (connected) {
												var msgJson = {
													channel : 1,
													giftId : $(
															".gift_ul_li_li .gift_p .liwu")
															.attr("giftId"), // huoqu
													count : $("#giftCount").val(),
													clientType : 3,
													liveId : course_id,
													receiverId : teacherId,
													receiverName : teacherName,
													continuousCount : 1
												};

												requestService(
														"/bxg/gift/sendGift",
														msgJson,
														function(data) {
															if (data.success == true) {
																sendMsg(data.resultObject);
																$(".send_gifts")
																		.hide();
																// 更新余额
																$("#xmbShowSpan")
																		.html(
																				data.resultObject.balanceTotal);
															} else {
															    //if ("用户账户余额不足，请充值！" == data.errorMessage) {
																	alert(data.errorMessage);
																//}
															}
														}, false)
												$("#chat-content").val('');
											} else {
												// alert("请先登录！");
												autoLogin();
											}
										}else{
											alert("余额不足");
//											$(".vanish02").show();
//											setTimeout(function(){$(".vanish02").hide();},1500);
										}
									});

					function sendMsg(data) {
						console.info(data);
						data = JSON.stringify(data);
						console.info(data);
						data = JSON.parse(data);
						console.info(data);
						// 创建一个<message>元素并发送
						var msg = $msg({
							to : ROOM_JID,
							from : jid,
							type : 'groupchat'
						// }).c("body", null,
						// data.giver+"yuxin"+data.giftName+"yuxin"+data.count);
						}).c("body", null, JSON.stringify(data));
						connection.send(msg.tree());
					}

					function autoLogin() {

						connection = new Strophe.Connection(BOSH_SERVICE);
						connection.connect(guId + '@' + host, guPwd, onConnect);
						jid = guId + '@' + host;
					}
					autoLogin();
				});