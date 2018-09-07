var courseId = getQueryString("courseId");
courseId = 905;
var barrageData = [];
var SixHide = 0;
var cuurentDate = getNowFormatDate();
var pageNumber = 1;
var isQuestion = "";
$(function(){
    getAccessToken();
    //getcontentList(pageNumber);

});

//获取讨论内容列表
function getcontentList(pageNumber) {
    requestGetService("/xczh/course/live/audio/courseLiveAudioContent/"+courseId,{
        courseId:courseId,
        pageNumber:pageNumber
    },function (data) {
        if (data.success == true) {
            barrageData = data.resultObject.records;
            init();
        }
    });

}
//获取课程accessToken
function getAccessToken() {
    requestGetService("/xczh/course/live/audio/courseLiveAudioAccessToken/"+courseId,null,function (data) {
        if (data.success == true) {
            var result = data.resultObject;
            appId = result.appId;
            accountId = result.accountId;
            token = result.accessToken;
            //配置初始化
            Vhall.config({
                appId : result.appId,//应用 ID ,必填
                accountId : result.accountId,//第三方用户唯一标识,必填
                token : result.accessToken,   //token必填
                channelId: result.channelId
            });
            window.chat = new VhallChat({
                channelId: result.channelId //频道Id
            });
            window.chat.onCustomMsg(function (msg) {
                msg = JSON.parse(msg);
                renderMsg(msg)
            });

        }
    },false);

}

//获取讨论内容列表
function getcontentList(pageNumber,downOrUp) {
    if($(".problem_label_put").is(':checked')) {
        isQuestion = true;
    }
    requestGetService("/xczh/course/live/audio/courseLiveAudioDiscussion/"+courseId,{
        courseId:courseId,
        question:isQuestion,
        pageNumber:pageNumber
    },function (data) {
        if (data.success == true) {
            var obj = data.resultObject.records;
            $(".comment_area_main").html(template('comment_area_list',{items:obj}));
        }
    });

}

//发送讨论内容
function sendDiscussion() {
    var question = false;
    if($(".radio_put").is(':checked')) {
        question = true;
    }
    var content = $("#chat_put").val();
    var courseLiveAudioContent = {
        "courseId": courseId,
        "question": question,
        "contentType": 1,
        "content": content
    };
    $.ajax({
        type: "post",
        url: "/xczh/course/live/audio/courseLiveAudioDiscussion",
        data:JSON.stringify(courseLiveAudioContent),
        contentType:"application/json",
        async:false,
        success: function(data) {
            if(data.success === true) {
                // 全部评论隱藏--點擊發送
                $(".barrage_switch").hide();  /*弹幕隐藏*/
                $(".chat_all").hide();      /*查看更多评论隐藏*/
                $(".chat").removeClass("chats");  /*增加input长度*/
                $(".chat_put").removeClass("chat_puts");
                $(".transmit").hide();   /*发送按钮*/
                $(".footer_bg").hide();  /*整屏按钮*/
                $(".barrage_switch").show();  /*弹幕*/
                $(".chat_all").show();      /*查看更多评论*/
            }
        }
    });
}
//弹幕开始
function init(){
    setInterval(function(){
        biubiubiu()
    },1500)
    setInterval(function(){
        if(barrageData == 0 ){
            SixHide++
            if(SixHide == 6){ //设置发弹幕--6秒后移除    //SixHide == 0.5(時間，錯誤操作，時間小於0.5一直顯示彈幕)
                $(".msgCont").html('')
            }
        }
    },1000)
}

// 發送內容后移除前面的內容
function biubiubiu(){
    if(barrageData == ''){
        getcontentList(pageNumber);
    } else {
        for(var i=0;i<barrageData.length;i++){
            if($(".msgCont .msgItem").length > 2){
                $(".msgCont .msgItem").eq(0).remove()
                var _tempText =  barrageData[i]; //出栈 最新的 后加入进来的弹幕
                showList(_tempText)
            }else{
                var _tempText =  barrageData[i];
                showList(_tempText)
            }
            if(barrageData.length == i+1){
                cuurentDate = barrageData[i].createTime;
                barrageData.length = 0;
                pageNumber++
                getcontentList(pageNumber);

            }
        }
    }

    /*if(barrageData.length <=0){
        getcontentList();
    }*/


}

// 顯示發送內容
function showList(text){
    var _html = '<div class="msgItem">'+
					'<div class="text"><span class="ask">问</span>${text.content}</div>'+
                    '<div class="avatar">'+
                    '<img src="/xcview/images/touxiang.png" />'+
                    '</div>'+
                    '</div>'
    $(".msgCont").append(_html)
}

// 点击发送--还原输入区域
$(".transmit").click(function(){
    var commentArea = $(".comment_area").css("display");  /*獲取全部評論顯示隱藏*/
    if (commentArea == "block") {
        $(".footer_bg").hide();
    }else{
        if($("#chat_put").val()==''){
            jqtoast("输入内容不能为空");
        }
        sendContent();

    };

});


//获取当前的日期时间 格式“yyyy-MM-dd HH:MM:SS”
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();
    return currentdate;
}