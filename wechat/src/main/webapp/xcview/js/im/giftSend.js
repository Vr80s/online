
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

/*
 * 获取im配置信息
 */
var guId = "";
var guPwd = "";
var room_id="";
var host = "";
var BOSH_SERVICE="";
var ROOM_JID="";
/**
 * 这个
 * @param status
 * @returns
 */
var course_id = getQueryString("courseId");
requestService("/xczh/common/getImServerConfig", {courseId : course_id}, function(data) {
    if (data.success) {
        guId = data.resultObject.guId;
        guPwd = data.resultObject.guPwd;
        room_id=data.resultObject.roomId;
        host = data.resultObject.host;
        BOSH_SERVICE=data.resultObject.boshService;
        ROOM_JID=data.resultObject.roomJId;
    }
},false);


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
if (sendTime == null) {
    requestService("/bxg/common/getSystemTime", null, function(data) {
        sendTime = data;
    }, false)
}
function createGiftList(gift) {
    if(gift.messageType == 2){//直播开始了
    	//当前时间 
    	if(parseInt(sendTime) < parseInt(gift.sendTime)){
        	console.log("开始直播了，建议再次刷新页面   >>>>");
        	
        	$(".video_end_top0").hide();
        	$(".video_end_top").hide(); 
        	
        	//刷新页面 --》在观看
        	location.reload();
    	}
    	return;
    }else if(gift.messageType == 3){ //直播结束了
    	if(parseInt(sendTime) < parseInt(gift.sendTime)){
	    	 console.log("直播结束了，建议再次刷新页面   >>>>");
			 $("#video").html("");
			 $(".video_end_top0").hide();
			 $(".video_end_top").show(); 
    	} 
    	return;
    }
    if (gift.messageType == 0 || gift.messageType == 1) {
        var time = data.giftInfo == null ? data.rewardInfo.time: data.giftInfo.time
        if (time == null)
            return;
        if (parseInt(sendTime) > parseInt(time))
            return;

        //TODO  将礼物添加到队列中  ---》 这时可能会有很多多来的，依次存放哎队列中
        // queue.push(gift);
        if($("#"+data.senderInfo.userId+data.giftInfo.giftId).length>0){
            giftShow(data,$("#"+data.senderInfo.userId+data.giftInfo.giftId).attr("xh"),true);
            console.info("lianji")
        }else{
            queue.push(data);
            createGiftShow();
            console.info("danji");
        }

        // createGiftShow();
    }
}
var gif = [];
var num = [];
var min = [];
var addn = [];
// 生成礼物
function giftShow(gift, f,continuous) {
    if(continuous){
        $("#"+gift.senderInfo.userId+gift.giftInfo.giftId).html(gift.giftInfo.continuousCount);
        $('.addnum'+f).data("sto",new Date().getTime())
        return;
    }

    if (gift.messageType == 1) { // 礼物
        var bottom = countChange(f)
        gif[f] = $("<li class='animation' id='gift"+f+"' style='position: absolute;top: "
            + bottom
            + "rem;'><div class='animation_div'><div class='animation_name'><p class='animation_name_p1'>"
            + gift.senderInfo.userName
            + "</p><p class='animation_name_p2'>送出"
            + gift.giftInfo.name
            + "</p></div><div class='animation_gift'><img src='"
            + gift.giftInfo.smallimgPath
            + "' alt='' /></div><div class='animation_span'>X<span class=addnum"
            + f + "  id='"+gift.senderInfo.userId+gift.giftInfo.giftId+"' xh='"+f+"' >1</span></div></div></li>");
        try {
            $("#liveGiftCount").html(gift.continuousCount);
        } catch (error) {
            // 此处是负责例外处理的语句
        } finally {
            // 此处是出口语句
        }

    } else if (gift.messageType == 0) { // 红包
        var bottom = countChange(f)
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

    gif[f].appendTo($(".chatmsg-box"))
    .css("left", "-9.55rem")//初始未知
    .animate({// 设置运动
    	       "left": "0"
    	     },500,"linear",
    function(){
    	 if (f == 1) {
        $('.addnum'+f).html(gift.giftInfo.continuousCount);
        $('.addnum'+f).data("sto",new Date().getTime());
    } else if (f == 2) {
        $('.addnum'+f).html(gift.giftInfo.continuousCount);
        $('.addnum'+f).data("sto",new Date().getTime());
    } else {
        $('.addnum'+f).html(gift.giftInfo.continuousCount);
        $('.addnum'+f).data("sto",new Date().getTime());
    }
    });
   
    
    
    $(".num").html(gift.giftCount);  /*礼物总数*/
    $(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTo","bottom");   /*评论到最底部*/

    function clearGift() {
        // gif[f].remove();
        $("#gift"+f).remove();
        if(f == 1){
            f1 = true;
        }else if(f == 2){
            f2 = true;
        }else{
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
        },4000);
    }
}

// 生成礼物
//var count = 1;
function countChange(count) {
    if (count == 3) {
        count = 2;
        return 16.4;
    } else if (count == 2) {
        count = 3;
        return 13.9;
    } else {
        count = 1;
        return 11.4;
    }
}
var gif = [];
var num = [];
var min = [];
var addn = [];

/*
 * 接受来自im 的即时通信消息
 * 	得到发送礼物的封装好的数据
 */
function onMessage(msg) {

//  console.log(msg);
    /*
     *  解析出<message>的from、type属性，以及body子元素
     */
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

//          console.log(text);
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

$(document).ready(function() {

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
    $(".balance_send").click(function() {
        if ($(".gift_ul_li_li .gift_p .liwu").attr("giftId") == null) {
            alert("请先选择一个礼物!");
            return;
        }
        /*if (isNaN($("#giftCount").val())) {
            alert("非法的礼物数量!");
            return;
        }
        if ($("#giftCount").val() < 1) {
            alert("非法的礼物数量!");
            return;
        }*/
        var xmbShowSpan = $("#xmbShowSpan").html(); //1
        var jiage = $(".gift_ul_li_li .gift_p .jiage").text();  //1
//      if(jiage<xmbShowSpan || jiage == 0){
        if(jiage<xmbShowSpan || jiage <= 0){
            if (connected) {
                var msgJson = {
                    channel : 1,
                    giftId : $(".gift_ul_li_li .gift_p .liwu").attr("giftId"), // huoqu
                    count : 1,     /*$("#giftCount").val()这是选择礼物数--默认为1*/
                    clientType : 3,
                    liveId : course_id,
                    receiverId : teacherId,
                    receiverName : teacherName,
                    continuousCount : 1
                };
                requestService("/xczh/gift/sendGift",
                    msgJson,
                    function(data) {
                        if (data.success == true) {
/*console.info("smessages");*/
                            /**
                             * 发送IM消息
                             */
                            sendMsg(data.resultObject);

                            //隐藏发送礼物的，连击效果，暂时不隐藏

                            //$(".send_gifts").hide();
                            // 更新余额
                            $("#xmbShowSpan").html(data.resultObject.balanceTotal);
                        } else {
                            //if ("用户账户余额不足，请充值！" == data.errorMessage) {
                            alert(data.errorMessage);
                            //}
                        }
                    })
                $("#chat-content").val('');
            } else {
                // alert("请先登录！");
                autoLogin();
            }
        }else{
//          alert("余额不足");
            
//          recharges.html?recharges_blck=2'
			var courseId = getQueryString("courseId");
            location.href ='/xcview/html/recharges.html?recharges_blck=3&courseId='+courseId;
            
//											$(".vanish02").show();
//											setTimeout(function(){$(".vanish02").hide();},1500);
        }
    });

    function sendMsg(data) {
//      console.info(data);
        data = JSON.stringify(data);
//      console.info(data);
        data = JSON.parse(data);
//      console.info(data);
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
$(function () {
    setInterval(function(){
        for(var i=1;i<5;i++){
//             console.info(i+":"+$('.addnum'+i).data("sto"));
            // debugger
            var t = new Date().getTime()-$('.addnum'+i).data("sto");
            if(t>3000){
                var f = $('.addnum'+i).attr("xh");
                if(f == 1){
                    f1 = true;
                }else if(f == 2){
                    f2 = true;
                }else{
                    f3 = true;
                }
                $("#gift"+i).remove();
            }
        }
    },500)
});
