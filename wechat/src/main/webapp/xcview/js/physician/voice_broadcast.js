var courseId = getQueryString("courseId");
// courseId = 800;
var barrageData = [];
var SixHide = 0;
var isQuestion = "";
var audio = document.createElement('AUDIO');
var currentAudio = {}; //当前播放的语音
var audioList = [] ;// 音频列表 从 dataList 过滤得到
var currenttimeLine = {}; // 当前播放语音的控制器
var autoplay = true;// 是否自动播放
var dataList = [];//消息存放
var teacherId;  //讲师id
$(function(){
    getAccessToken();
    getBarrageList();
    getLiveAudioContentList(1,'');

});

// 跳转完善信息页--点击关注的时候如果没有完善信息跳转到完善信息页面
function getFlagStatus() {
    var falg = USER_NORMAL;
    var user_cookie = cookie.get("_ipandatcm_user_");
    var third_party_cookie = cookie.get("_third_ipandatcm_user_");
    if (isBlank(user_cookie)) {
        falg = USER_UN_LOGIN;
        if (isNotBlank(third_party_cookie)) {
            falg = USER_UN_BIND;
        }
    }
    return falg;
}

/**
头部课件区域渲染
**/
requestService("/xczh/course/liveDetails",{courseId:courseId},function (data) {
    if (data.success == true) {
        
        //lineState 直播课程状态 1直播中， 2预告，3直播结束 ， 4 即将直播 ，5 准备直播 ，6 异常直播
        if (data.resultObject.lineState == 1) {
            $('.surface_plot').hide();  //即将直播位置
            $('.courseware').show();    //课件
        }else{
            $('.courseware').hide();
            $('.surface_plot').show();
            // 开播前10分钟详情
            $('.surface_plot').html(template('surface_plot', {items: data.resultObject}));
        };

        //关注区域
        $('.attention_main').html(template('attention_main', {items: data.resultObject}));
        if (data.resultObject.lineState == 1) {
            $(".subscribe").hide();  //预约
            $(".live_streaming").show();  //直播中
        }else if (data.resultObject.lineState == 2 || data.resultObject.lineState == 4 ||data.resultObject.lineState == 5 || data.resultObject.lineState == 6) {
            $(".subscribe").show();  //预约
            $(".live_streaming").hide();  //直播中
        }else if (data.resultObject.lineState == 3) {
            $(".subscribe").show();  //预约
            $(".live_streaming").hide();  //直播中
        };

        // 点击关注
        // $(".booking_person").click(function(){
        $('.attention_main').on('click','.booking_person',function(){
            teacherId = data.resultObject.userLecturerId; // 讲师Id
            //type 1 增加关注 2 取消关注
            var type = 1;
            var htmlstr = $(".booking_person").find('p').html();
            if (htmlstr == "加关注") { // 增加关注
                type = 2;
            } else {
                type = 1;
            }
            requestService("/xczh/myinfo/updateFocus", {
                lecturerId: teacherId,
                type: type
            }, function (data) {
                if (data.success) {
                    if (htmlstr == "已关注") {
                        $(".booking_person").find('img').attr('src','/xcview//images/weigz.png');
                        $(".booking_person").find('p').html("加关注");
                        $(".booking_person").removeClass("booking_person_bg_two");
                        $(".booking_person").addClass("booking_person_bg");
                    } else {
                        $(".booking_person").find('img').attr('src','/xcview//images/yigz.png');
                        $(".booking_person").find('p').html("已关注");
                        $(".booking_person").removeClass("booking_person_bg");
                        $(".booking_person").addClass("booking_person_bg_two");
                        jqtoast("关注成功");
                    }
                }
            })

        });

    }
});


//音频播放
function audioPlay() {
    if($.isEmptyObject(currentAudio)){
        currentAudio = audioList[0];
    }
    currenttimeLine = {
        line:$('.message[data-id-'+currentAudio.id+'] .soundbody').find('.play_line'),
        btn:$('.message[data-id-'+currentAudio.id+'] .soundbody').find('.play_btn')
    }
    audio.src = currentAudio.content;
    audio.play()
    $('.soundbody').removeClass('playing_')
    $('.message[data-id-'+currentAudio.id+'] .soundbody').addClass('playing_')
    audioControll()
    setPlayPos(currentAudio.id)
}
//设置进入位置
function setPlayPos(id) {
    //以最后播放的语音 id
    localStorage.setItem('LastPlay', id)

}
//自动播放下一段语音
function playNext(){
    var next_id , index_ = _.findIndex(audioList,function(o){ return o.id == currentAudio.id})
    if(index_<audioList.length - 1){
        next_id = index_ + 1
        currentAudio = audioList[next_id]
        audioPlay()
    }
}



//音频控制
var old_touchEvent = null;
function audioControll(){
    if(old_touchEvent){
        old_touchEvent.removeEventListener('touchstart',onTouchStart)
        old_touchEvent.removeEventListener('touchmove',onTouchStart)
        old_touchEvent.removeEventListener('touchend',onTouchStart)
    }
    currenttimeLine.btn.get(0).addEventListener('touchstart',onTouchStart,false)
    currenttimeLine.btn.get(0).addEventListener('touchmove',onTouchMove,false)
    currenttimeLine.btn.get(0).addEventListener('touchend',onTouchEnd,false)
    touchEvent.offsetX = currenttimeLine.line.offset().left
    touchEvent.width = currenttimeLine.line.parent().width()
    touchEvent.second = parseInt(currentAudio.length)
    old_touchEvent = currenttimeLine.btn.get(0)
}
var touchEvent = {
    startX:0,
    endX:0,
    currentX:0,
    offsetX:0,
    width:0,
    second:0
}
function onTouchStart(e){
    touchEvent.startX = e.targetTouches[0].pageX
    audio.pause()
    currenttimeLine.btn.next().show()
}
function onTouchEnd(e){
    touchEvent.endX = e.changedTouches[0].pageX
    audio.play()
    currenttimeLine.btn.next().hide()
}

function onTouchMove(e){
    var dur = e.changedTouches[0].pageX - touchEvent.offsetX
    dur = Math.round(touchEvent.second / touchEvent.width * dur)
    if(dur<0){
        dur = 0
    }else if(dur>touchEvent.second ){
        dur = touchEvent.second
    }
    currenttimeLine.btn.next().find('span').text(dur)
    audio.currentTime = dur
}

// 返回上次播放位置
function getLastPlay() {
    var LastPlay = localStorage.getItem('LastPlay');
    if(LastPlay == '' || LastPlay == null || LastPlay == 'false' ||LastPlay == 'null'){
        LastPlay = false
    }
    return LastPlay;
}

//点击按钮播放
$('.home').on('click', '.play_status', function () {
    var msg = $(this).parents('.item'),messageId = msg.attr('data-message-id'),messageInfo;
    if(messageId == currentAudio.id){
        if(audio.paused){
            audio.play()
            $(this).removeClass('paused_')
        }else{
            audio.pause()
            $(this).addClass('paused_')
        }
        return;
    }
    currentAudio = getInfoFromData(messageId)
    audioPlay()
})
/**
 * 根据id查找数据
 *
 * @param {any} messageid
 */
function getInfoFromData(messageid) {
    var data = _.find(dataList,function(o){
        return o.id == messageid;
    })
    return data;
}


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
function getLiveAudioContentList(pageNumber,downOrUp) {
    requestGetService("/xczh/course/live/audio/courseLiveAudioContent/"+courseId,{
        courseId:courseId,
        pageNumber:pageNumber
    },function (data) {
        if (data.success == true) {
            var records = data.resultObject.records;

            if(downOrUp=='down'){
                if(obj.length==0){
                }else{
                }
                // 列表
                $(".scroll-wrapper").prepend(template('msg',{items:records}));
                miniRefresh1.endDownLoading(true);// 结束下拉刷新
            } else if(records.length==0){
                miniRefresh1.endDownLoading(true);// 结束上拉加载
            } else {
                $(".scroll-wrapper").html(template('msg',{items:records}));
                //miniRefresh1.endUpLoading(false);
                //miniRefresh1.endDownLoading(true);// 结束下拉刷新
            }

            //$(".scroll-wrapper").html(template('msg',{items:records}));
            dataList = records;
            for(var i=0;i<records.length;i++){
                if(records[i].contentType == 2) {
                    audioList.push(records[i]);
                }
            }
            var LastPlay = getLastPlay()
            if(LastPlay !== false){
                $('.tips').show()

            }
            audioPlay();

            //控制
            audio.onended = function(){
                $('.message[data-id-'+currentAudio.id+'] .soundbody').addClass('played').removeClass('playing_');
                if(autoplay){
                    playNext()
                }
            }
            //进度条动画
            audio.ontimeupdate = function(){
                currenttimeLine.line.css('width',Math.ceil(audio.currentTime / audio.duration * 100) + '%');
                currenttimeLine.btn.css('left',Math.ceil(audio.currentTime / audio.duration * 100) + '%');
                currenttimeLine.btn.next().css('left',Math.ceil(audio.currentTime / audio.duration * 100) + '%')
            }

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

//音频直播课程内容列表刷新
// 初始化页码
/*var page1 = 1;

// miniRefresh 对象
var miniRefresh1 = new MiniRefresh({
    container: '#minirefresh1',
    down: {
        //isLock: true,//是否禁用下拉刷新
        callback: function () {
            page1++;
            getLiveAudioContentList(page1,'down');

        }
    }
});*/


