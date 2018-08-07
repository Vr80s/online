


var giftList;
// 初始化没有参数的队列
var queue = new Queue();


var course_id = getQueryString("courseId");

var vhallObj = {
    appId: "27376e92",
    accountId:localStorage.getItem("userId")
};


String.prototype.replaceAll = function(FindText, RepText) {
    regExp = new RegExp(FindText, "g");
    return this.replace(regExp, RepText);
}

var sendTime;
if (sendTime == null) {
    requestService("/xczh/common/getSystemTime", null, function(data) {
        sendTime = data;
    }, false)
}
function createGiftList(gift) {
    
    if(gift.messageType == 5){ //回放生成失败
        
        if(parseInt(sendTime) < parseInt(gift.sendTime)){
            
            $(".video_end_top0").hide();
            $(".video_end_top2").hide();
            $(".video_end_top1").hide();
            
            $(".video_end_top").show();
            console.info("回放生成失败");
        }
    }else if(gift.messageType == 4){ //回放生成 成功
        
        if(parseInt(sendTime) < parseInt(gift.sendTime)){
            
            $(".video_end_top0").hide();
            $(".video_end_top2").hide();
            $(".video_end_top1").show();
            
            console.info("回放生成成功");
        }
    }else if(gift.messageType == 2){//直播开始了
        //当前时间 
        if(parseInt(sendTime) < parseInt(gift.sendTime)){
            console.log("开始直播了，建议再次刷新页面   >>>>");
            
            $(".video_end_top0").hide();
            $(".video_end_top2").hide(); 
            //刷新页面 --》在观看
            location.reload();
        }
        return;
    }else if(gift.messageType == 3){ //直播结束了
        if(parseInt(sendTime) < parseInt(gift.sendTime)){
             console.log("直播结束了，去学习中心 >>>>");
             $("#video").html("");
             $(".video_end_top0").hide();
             //生成回访中
             $(".video_end_top2").show(); 
        } 
        return;
    }
    //if(gift.courseId!=course_id)return;   //ios传值
    if (gift.messageType == 0 || gift.messageType == 1) {
    	
    	var data = gift;
    	
        var time = data.giftInfo == null ? data.rewardInfo.time: data.giftInfo.time
        if (time == null)
            return;
        if (parseInt(sendTime) > parseInt(time))
            return;

        //将礼物添加到队列中  ---》 这时可能会有很多多来的，依次存放哎队列中
        // queue.push(gift);
        if($("#"+data.senderInfo.userId+data.giftInfo.giftId).length>0){
            giftShow(data,$("#"+data.senderInfo.userId+data.giftInfo.giftId).attr("xh"),true);
            console.info("lianji")
        }else{
            queue.push(data);
            createGiftShow();
            console.info("danji");
        }
        if(Number.isInteger(gift.giftCount)){
            try {
                //显示礼物总数
                $("#liveGiftCount").html(gift.giftCount);           
            } catch (error) {
                // 此处是负责例外处理的语句
            } finally {
                // 此处是出口语句
            }
        }
    }
}

var gif = [];
var num = [];
var min = [];
var addn = [];
// 生成礼物

//gift.messageType == 0;
// var courseId = courseId;
function giftShow(gift, f,continuous) {
    if(continuous){
        $("#"+gift.senderInfo.userId+gift.giftInfo.giftId).html(gift.giftInfo.continuousCount);
        $('.addnum'+f).data("sto",new Date().getTime())
        return;
    }
    if (gift.messageType == 1) { // 礼物
        var bottom = countChange(f)
        gif[f] = $("<li class='animation' id='gift"+f+"' style='position: fixed;top: "
            + bottom
            + "rem;'><div class='animation_div'><div class='animation_name'><p class='animation_name_p1'>"
            + gift.senderInfo.userName    //userName  用户昵称
            + "</p><p class='animation_name_p2'>送出"
            + gift.giftInfo.name
            + "</p></div><div class='animation_span'>×<span class=addnum"                //展示区域.开始默认1，修改为字段  
            + f + "  id='"+gift.senderInfo.userId+gift.giftInfo.giftId+"' xh='"+f+"' >"+gift.giftInfo.continuousCount+"</span></div><div class='animation_gift'><img src='"
            + gift.giftInfo.smallimgPath
            + "' alt='' /></div></div></li>");
        
        try {
            //显示礼物总数
            $("#liveGiftCount").html(gift.giftCount);           
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
//           },500,"linear",
             },500,"linear",
    function(){
         if (f == 1) {
        // $('.addnum'+f).html(gift.giftInfo.continuousCount);  由于点击连击的时候，数字倒数，就隐藏掉了，然后添加在默认展示区域
        $('.addnum'+f).data("sto",new Date().getTime());
    } else if (f == 2) {
        // $('.addnum'+f).html(gift.giftInfo.continuousCount);
        $('.addnum'+f).data("sto",new Date().getTime());
    } else {
        // $('.addnum'+f).html(gift.giftInfo.continuousCount);
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
        return 8.5;
    } else if (count == 2) {
        count = 1;
        return 7.3;
    } else {
        count = 1;
        return 6.1;
    }
}

/*function countChange(count) {
    if (count == 3) {
        count = 2;
        return 5.8;
    } else if (count == 2) {
        count = 3;
        return 3.5;
    } else {
        count = 1;
        return 1.2;
    }
}*/  /*这是 absolute定位，现在用的固定定位，可能分辨率不同，有影响，ab定位先留着*/

var gif = [];
var num = [];
var min = [];
var addn = [];

/*
 * 接受来自im 的即时通信消息
 *  得到发送礼物的封装好的数据
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


// 发送消息
    $(".balance_send").click(function() {
        if ($(".gift_ul_li_li .gift_p .liwu").attr("giftId") == null) {
//          alert("请先选择一个礼物!");
            webToast("请先选择一个礼物!","middle",1500);
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
        if( parseInt(xmbShowSpan) >= parseInt(jiage)  || parseInt(jiage) <= 0){
                var msgJson = {
                    channel : 1,
                    giftId : $(".gift_ul_li_li .gift_p .liwu").attr("giftId"), // huoqu
                    count : 1,     /*$("#giftCount").val()这是选择礼物数--默认为1*/
                    clientType : 3,
                    liveId : course_id,
                    receiverId : teacherId,
                    receiverName : teacherName,
                    continuousCount : 1,
                    channel_id:vhallObj.channelId
                };
                requestService("/xczh/gift/vhallSendGift",
                    msgJson,
                    
                    function(data) {
                        if (data.success == true) {

                        	/*ios传值--判断是在一个直播间*/
                            data.resultObject.courseId=course_id; 
                            
                            createRanking(data.resultObject.ranking);
                           
                            //显示礼物总数
                            $("#liveGiftCount").html(data.resultObject.giftCount);
                            
                            setTimeout(function(){
                                  $(".chatmsg-box").mCustomScrollbar("scrollTo","bottom","0");
                            },50);
                            
                            // 更新余额
                            $("#xmbShowSpan").html(data.resultObject.balanceTotal);
                        } else {
                            if ("余额不足" == data.errorMessage) { //当余额不足时去充值页面
                               var courseId = getQueryString("courseId");
                               location.href ='/xcview/html/recharges.html?recharges_blck=3&courseId='+courseId;
                            }else{ //否则弹出初五信息
//                             alert(data.errorMessage);
                               webToast(data.errorMessage,"middle",1500);
                            }
                            
                        }
                    })
                $("#chat-content").val('');
            
        }else{
//          alert("余额不足");
            
//          recharges.html?recharges_blck=2'
            var courseId = getQueryString("courseId");
            location.href ='/xcview/html/recharges.html?recharges_blck=3&courseId='+courseId;
            
//                                          $(".vanish02").show();
//                                          setTimeout(function(){$(".vanish02").hide();},1500);
        }
    });

    
        /**
     * 前端发送IM消息
     */
    function sendMsg(data) {
        console.info(data);
        data = JSON.stringify(data);
//      console.info(data);
        data = JSON.parse(data);
       
        var params = {
            channel_id:obj.channelId,
            type:"CustomBroadcast",
            body:JSON.stringify(data)
        };
        
        requestService("/xczh/vhall/vhallYunSendMessage",params,
            function(data) {
            debugger;
            if (data.success) {
                
            }   
        });  
    }
});
$(function () {
    setInterval(function(){
        for(var i=1;i<5;i++){
//             console.info(i+":"+$('.addnum'+i).data("sto"));
            // 
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
                $("#gift"+i).remove();   /*注释以后,礼物就不会隐藏*/
            }
        }
    },16)
});
