var courseId = getQueryString("courseId");
courseId = 814;
var barrageData = [];
var SixHide = 0;
var isQuestion = "";
$(function(){
    getAccessToken();
    getBarrageList();

});

//获取讨论内容列表
function getBarrageList() {
    requestGetService("/xczh/course/live/audio/courseLiveAudioDiscussion/"+courseId,{
        courseId:courseId,
        pageNumber:1,
        pageSize:3
    },function (data) {
        if (data.success == true) {
            var records = data.resultObject.records;
            for(var i=0;i<records.length;i++){
                var msg = records[i];
                var _html="<div class=\"msgItem\"> <div class=\"text\">";
                if(msg.question) {
                    _html+="<span class=\"ask\">问</span>"+msg.content+"</div>"+
                        '<div class="avatar">'+
                        '<img src='+msg.imgUrl+' />'+
                        '</div>'+
                        '</div>';
                } else {
                    _html+=msg.content+"</div>"+
                        '<div class="avatar">'+
                        '<img src='+msg.imgUrl+' />'+
                        '</div>'+
                        '</div>';

                }
                $(".msgCont").append(_html);
            }
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
            window.Vhall.ready(function() {

                window.chat = new VhallChat({
                    channelId: result.channelId //频道Id
                });
                window.chat.onCustomMsg(function (msg) {
                    msg = JSON.parse(msg);
                    barrageData.push(msg);
                    //renderMsg(msg)
                    SixHide = 0;
                    biubiubiu();
                });
            });

            //配置初始化
            Vhall.config({
                appId : result.appId,//应用 ID ,必填
                accountId : result.accountId,//第三方用户唯一标识,必填
                token : result.accessToken,   //token必填
                channelId: result.channelId
            });

        }
    },false);

}

//获取讨论内容列表
function getcontentList(pageNumber,downOrUp) {
    if($(".problem_label_put").is(':checked')) {
        isQuestion = true;
    } else {
        isQuestion = "";
    }
    requestGetService("/xczh/course/live/audio/courseLiveAudioDiscussion/"+courseId,{
        courseId:courseId,
        question:isQuestion,
        pageNumber:pageNumber
    },function (data) {
        if (data.success == true) {
            var obj = data.resultObject.records;

            if(downOrUp=='down'){
                if(obj.length==0){

                }else{

                }
                // 列表
                $(".comment_area_main").html(template('comment_area_list',{items:obj}));
                miniRefresh.endDownLoading(true);// 结束下拉刷新
            } else if(obj.length==0){
                miniRefresh.endUpLoading(true);// 结束上拉加载
            } else {
                $(".comment_area_main").append(template('comment_area_list',{items:obj}));
                miniRefresh.endUpLoading(false);
            }
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



function renderMsg(msg) {
    var _html="<div class=\"msgItem\"> <div class=\"text\">";
    if(msg.body.question) {
        _html+="<span class=\"ask\">问</span>"+msg.body.content+"</div>"+
            '<div class="avatar">'+
            '<img src='+msg.body.imgUrl+' />'+
            '</div>'+
            '</div>';
    } else {
        _html+=msg.body.content+"</div>"+
            '<div class="avatar">'+
            '<img src='+msg.body.imgUrl+' />'+
            '</div>'+
            '</div>';

    }
    $(".msgCont").append(_html)


}



//init();  /*默认方法--可用于判断是否回放*/
// 弹幕开始
var data = ['你好世界','世界你好','啊啊啊哈哈哈哈'];  //定义最后3条


function init(){
    /*setInterval(function(){
        // console.log(data)
        biubiubiu()
    },1500)*/
    setInterval(function(){
        if(barrageData == 0 ){
            SixHide++
            if(SixHide == 6){ //设置发弹幕--6秒后移除    //SixHide == 0.5(时间，错误操作，时间小于0.5一直显示弹幕)
                $(".msgCont").html('')
            }
        }
    },1000)
}



// 发送內容后移除前面的內容
function biubiubiu(){
    if(barrageData == ''){
        return
    }
    if($(".msgCont .msgItem").length > 2){
        $(".msgCont .msgItem").eq(0).remove()
        var _tempText =  barrageData.pop()  //出栈 最新的 后加入进来的弹幕
        renderMsg(_tempText)
    }else{
        var _tempText =  barrageData.pop()
        renderMsg(_tempText)
    }
}


//刷新
// 初始化页码
var page = 1;

// miniRefresh 对象
var miniRefresh = new MiniRefresh({
    container: '#minirefresh',
    down: {
        //isLock: true,//是否禁用下拉刷新
        callback: function () {
            page = 1;
            getcontentList(page,'down');

        }
    },
    up: {
        isAuto: false,
        callback: function () {
            page++;
            getcontentList(page,'up');
        }
    }
});

