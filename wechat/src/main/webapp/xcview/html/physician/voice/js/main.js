var audio = document.createElement('AUDIO'),
    dataList = [],//消息存放
    dataindex = 0, // 消息id 
    AudioList = [] ,// 音频列表 从 dataList 过滤得到
    currentAudio = {}, //当前播放的语音
    currenttimeLine = {}, // 当前播放语音的控制器
    autoplay = true,// 是否自动播放
    pending = false, // 是否在请求数据 防止重复请求
    lastDataIndex = 0 //最后一条数据的id
    loading = $('<div class="loading"></div>')

/**
 * 模拟数据
 * 
 * @param {any} start 消息开始位置 默认 0
 * @param {any} length 每次添加长度 默认 20
 * @param {any} dur  添加方向 首 - 1 尾 1
 * @returns {list: 数据列表 }
 */
function fakeData(start,length,dur) {
    var n = length || 20, d = dur || 1,no;

    if(dur<0){
        dataindex = dataList[0].id || 0
        if(dataindex<length - 1){
             n = dataindex 
        }
        dataindex = dataindex  - length 
    }else{
        dataindex = parseInt(start) + 1
    }

    
    
    var k = new Array(n).fill(1),
        l = [];
    k.forEach(function (e,i) {
        // console.log(dataindex, i)
        l.push({
            id: dataindex + i,
            name: "第"+ (dataindex + i) +"条消息",
            img: "./voice/img/tx.png",
            type: Math.round(Math.random()) + 1,
            sound: {
                content: './voice/audio/1.mp3',
                second: '180'
            },
            text: "我是文字内容我是文字",
            likes: Math.floor(Math.random() * Math.random() * 100)
        });
    });

    return {
        list: l
    };

}

/**
 * 渲染函数
 * 
 * @param {any} data 需要添加的数据
 * @param {any} position 添加的位置 前 -1 后1 默认后
 */
function render(data, position) {
    var p = position || 1;
    if (p > 0) {
        $('.scroll-wrapper').append(template('msg', data));
        dataList = dataList.concat(data.list)
    } else {
        $('.scroll-wrapper').prepend(template('msg', data));
        dataList = data.list.concat(dataList)
    }
    //初始化播放列表
    lastDataIndex = dataList[dataList.length - 1].id
    AudioList = _.filter(dataList,function(o){
        return o.type == 1;
    })
    
}

// 返回上次播放位置
function getLastPlay() {
    var LastPlay = localStorage.getItem('LastPlay');
    if(LastPlay == '' || LastPlay == null || LastPlay == 'false' ||LastPlay == 'null'){
        LastPlay = false
    }
    return LastPlay;
}

//设置进入位置  
function setPlayPos(id) {
    // (以播放语音的id 大小排序)
    // var p = $('.message[data-id-'+currentAudio.id+']').parent().index('.item'),b = !getLastPlay() || $('.message[data-id-'+getLastPlay()+']').parent().index('.item');
    // if(p>b){
    //     localStorage.setItem('LastPlay', id)
    // }
    
    //以最后播放的语音 id
    localStorage.setItem('LastPlay', id)
}


//音频播放
function audioPlay() {
    if($.isEmptyObject(currentAudio)){
        currentAudio = AudioList[0]
    }
    currenttimeLine = {
        line:$('.message[data-id-'+currentAudio.id+'] .soundbody').find('.play_line'),
        btn:$('.message[data-id-'+currentAudio.id+'] .soundbody').find('.play_btn')
    }
    audio.src = currentAudio.sound.content;
    audio.play()
    setPlayPos(currentAudio.id)
}
//控制
audio.onended = function(){
    $('.message[data-id-'+currentAudio.id+'] .soundbody').addClass('played').removeClass('playing_');
    if(autoplay){
        playNext()
    }
}
//自动播放下一段语音
function playNext(){
    var next_id , index_ = _.findIndex(AudioList,function(o){ return o.id == currentAudio.id})
    if(index_<AudioList.length - 1){
        next_id = index_ + 1
        currentAudio = AudioList[next_id]
        audioPlay()
    }
}


audio.onplay = function(){
    $('.soundbody').removeClass('playing_')
    $('.message[data-id-'+currentAudio.id+'] .soundbody').addClass('playing_')
    audioControll()
    setPlayPos(currentAudio.id)
}

//进度条动画
audio.ontimeupdate = function(){
    currenttimeLine.line.css('width',Math.ceil(audio.currentTime / audio.duration * 100) + '%');
    currenttimeLine.btn.css('left',Math.ceil(audio.currentTime / audio.duration * 100) + '%');
    currenttimeLine.btn.next().css('left',Math.ceil(audio.currentTime / audio.duration * 100) + '%')
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
    touchEvent.second = parseInt(currentAudio.sound.second)
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
/**
 * 数据加载
 * 
 * @param {any} messageID 起始消息id
 * @param {any} userID 用户id
 * @param {any} roomId 房间号id
 * @param {any} beforeOrAfter 往前还是往后加载 默认往后
 */
function loadMessage(messageID, userID, roomId, beforeOrAfter) {
    var ba = beforeOrAfter || 'after'
    //模拟数据
    return fakeData
}
//点赞
$('.home').on('click','.like p',function(){
    var messageId =  $(this).parents('.item').attr('data-message-id')
    if($(this).hasClass('islike')){
        //取消点赞
        $(this).removeClass('islike')
    }else{
        //点赞
        $(this).addClass('islike')
    }
    console.log(messageId)
})

//回到播放位置
document.querySelector('.scroll-box').addEventListener('scroll', _.throttle(updatePosition, 600),false)

function updatePosition(e){
    if(pending){
        return;
    }
    var t = $('.message[data-id-'+currentAudio.id+']').parent().offset().top,
    h = $('.home').height(),
    mh = $('.message[data-id-'+currentAudio.id+']').parent().height();
    if(t > h || -t > mh){
       $('.currentPosition').show()
    }else{
        $('.currentPosition').hide()
    }
}

$('.currentPosition').click(function(){
    var t = $('.message[data-id-'+currentAudio.id+']').parent().position().top;
    $('.scroll-box').animate({scrollTop:Math.abs(t)},400)
})

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
// 上拉下拉刷新

document.querySelector('.scroll-box').addEventListener('scroll', _.debounce(loadmore, 800),false)

function loadmore(){
    var t = $('.scroll-box').scrollTop(),h = $('.scroll-box').height(),h_ = $('.scroll-wrapper').height();
    if(pending){
        return;
    }
   
    if(t+h > h_ - 5){
         pending = true
         $('.scroll-wrapper').append(loading)
         $('.currentPosition').hide()
         $('.scroll-box').animate({scrollTop:h_ = $('.scroll-wrapper').height()},100)
         $('.currentPosition').hide()
        //下拉模拟加载
        setTimeout(function(){
            render(fakeData(lastDataIndex,5,1),1)
            pending = false
            loading.remove()
        },1000)
    }

    if(t < 5){
        //上拉 模拟加载
        pending = true
        $('.scroll-wrapper').prepend(loading)
         $('.currentPosition').hide()
        setTimeout(function(){
            render(fakeData(lastDataIndex,5,-1),-1)
            var h_new =  $('.scroll-wrapper').height()
            loading.remove()
            $('.scroll-box').animate({scrollTop:h_new - h_},0)
            $('.scroll-box').animate({scrollTop:h_new - h_ - 120},0)
            pending = false
            
        },1000)
    }

}
//初始化
window.onload = function () {
    var LastPlay = getLastPlay()
    if(LastPlay !== false){
        $('.tips').show()
        render(fakeData(LastPlay,20,1))
    }else{
        render(fakeData(0,20,1))
    }
    
    audioPlay()
}