// 请求--》json文件
var emoji = [];
$.ajax({
    type: "POST", // 请求方式
    url: "https://file.ipandatcm.com/static/img/emoji.json", // 地址，就是json文件的请求路径
    dataType: "json", // 数据类型可以为 text xml json script jsonp
    async: false,
    success: function (result) { // 返回的参数就是 action里面所有的有get和set方法的参数
        emoji = result;
    }
});

/**
 * 请求token
 */
requestGetService("/xczh/vhall/vhallYunToken", {
    roomId: vhallObj.roomId,
    channelId: vhallObj.channelId
}, function (data) {
    if (data.success) {
        vhallObj.token = data.resultObject;
    }
}, false);



 // 刷新页面 --》在观看
$(".video_end_top1 .div img").click(function(){
	setTimeout(function () {
		location.reload();
	},2000)
});

		

// 直播状态1.直播中，2预告，3直播结束 4 即将直播
if (lineState == 1 || lineState == 3) {
    // 初始化 微吼云播放器
    elsBind();
    
}

if(lineState == 1 || lineState == 3 || lineState == 4){
	initChat();
	// 初始化消息
    msgList(0, 10);
}


/**
 * 初始化视频播放器、文档
 */
function elsBind() {
    window.doc = {};

    var readyCallback = function () {

        window.doc = new VhallDocPassive({
            channelId: vhallObj.channelId, // 频道Id
            docNode: 'my-doc-area' // 文档显示节点div id
        });

        var roomId = (lineState == 1 ? vhallObj.roomId : "");
        var liveType = (lineState == 1 ? "live" : "vod");
        var recordId = (lineState == 1 ? "" : vhallObj.recordId);

        VhallLive.init({
            roomId: roomId,
            type: liveType,
            recordId: recordId, // 回放Id，点播必填，直播不写
            videoNode: 'myVideo',
            complete: function () {
                VhallLive.play();
            }
        });
    }

    window.Vhall.ready(readyCallback);

    window.Vhall.config({
        appId: vhallObj.appId, // 应用 ID ,必填
        accountId: vhallObj.accountId, // 第三方用户唯一标识,必填
        token: vhallObj.token
        // token必填
    });


}

/**
 * 初始化消息
 */
function initChat(){

	
	 setTimeout(function () {
    	try{
		    var md = document.getElementsByTagName("video")[0];
		    if(md){
		    	md.addEventListener("ended", function () {
	            	console.log("播放结束了");
		        });
		        md.addEventListener("progress", function () {
		            //console.log("浏览器正在获取媒体数据");
		        });
		        md.addEventListener("suspend", function () {
		            $(".video_end_top4").show();
		            console.log("浏览器暂停获取媒体数据，但是下载过程并滑正常结束");
		        });
		        md.addEventListener("error", function () {
		        	$(".video_end_top4").show();
		            console.log("获取媒体数据过程中出错");
		        });
		    }
	       
    	}catch(error){
    	  console.log(error);
    	}

        window.Vhall.ready(function () {
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
            window.chat.on(function (msg) {
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
            window.chat.onCustomMsg(function (msg) {
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

					setTimeout(function () {
						location.reload();
					},2000)

                } else if (msg.type == 13) { // 结束直播  --》  生成点播

                    $(".video_end_top2").show();

                } else if (msg.type == 14) { // 退出直播间，但是没有结束直播

                	 $(".video_end_top3").show();

                }  else if (msg.type == 16) { // 回放生成成功

                    $(".video_end_top0").hide();
                    $(".video_end_top2").hide();
                    $(".video_end_top1").show();

                } else if (msg.type == 17) { // 回放生成失败

                    $(".video_end_top0").hide();
                    $(".video_end_top2").hide();
                    $(".video_end_top1").hide();
                    $(".video_end_top").show();
                }
            })

            window.chat.join(function (msg) {
                viewJoinleaveRoomInfo(msg, "join");
            })

            window.chat.leave(function (msg) {
                viewJoinleaveRoomInfo(msg, "leave");
            })
        });
        // 在初始化一个消息的
        window.Vhall.config({
            appId: vhallObj.appId, // 应用 ID ,必填
            accountId: vhallObj.accountId, // 第三方用户唯一标识,必填
            token: vhallObj.token
            // token必填
        });
    }, 1000);
    
    
     /**
     * 发送聊天消息
     */
    $("#sendChat").click(function () {
        $(".coze_bottom").css("bottom", "0rem"); // 这是输入框在最底部,添加到其他文件不起作用
        var text = $("#mywords").val();
        if (text != null) {
            var content = {
                    type: 10, // 消息类型 10 聊天消息 礼物 11
                    message: {
                        content: text, // 发送的内容
//                        headImg: localStorage.getItem("smallHeadPhoto"), // 发送的头像
//                        username: localStorage.getItem("name"), // 发送的用户名
                        role: "normal" // 发送人的角色 主播： host 普通用户： normal
                    }
                }
                /**
                 * 发送消息
                 */
            requestService("/xczh/vhall/vhallYunSendMessage", {
                channel_id: vhallObj.channelId,
                body: JSON.stringify(content)
            }, function (data) {
                if (data.success) {
                    $("#mywords").val('');
                }
            });
        }
    });
    
}


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
    requestService("/xczh/vhall/vhallYunMessageList", params, function (data) {
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
    console.log("参与人数：" + msg.data.connection_online_num);
    console.log("当前在线人数：" + msg.data.user_online_num);
    var str = "<div class='coze_cen_ri'> " + "  <div class='coze_cen_bg_ri'> " + "<span class='span_name'>" + userName + "：</span>" + // 用户名
        "   " + content + "  " + " </div> " + " <div class='both'></div></div>";

    $("#chatmsg").append(str);
    $(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTop",
        "bottom");

    var learndCount = sessionStorage.getItem("learndCount");
    if (isNotBlank(learndCount) && isNotBlank(msg.data.attend_count)) {
        learndCount = parseInt(learndCount) + parseInt(msg.data.attend_count);
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
        if(src!=""){
			var imgBag = "<img src="+src+" style='width:20px;' />";
		    str = str.replace(pattern2, imgBag);
		}
    }
    return str;
}