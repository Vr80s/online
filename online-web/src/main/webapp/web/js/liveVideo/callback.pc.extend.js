/**
 * Created by shanglt on 15/12/10.
 */
$(function () {
    Template.init();
});



// 接收聊天信息
function on_cc_live_chat_msg(data) {
    $('#chat-list').append(Template.chatMsg({
        name: data.username,
        content: showEm(data.msg)
    }));

    chatScroll();
}

// 接收发送私聊
function on_cc_live_chat_private_question(data) {
    $("#chat-list").append(Template.privateChatMsg({
        fromUserName: data.username,
        toUserName: '管理员',
        content: showEm(data.msg),
    }));

    chatScroll();
}

// 接收回答私聊
function on_cc_live_chat_private_answer(data) {
    $("#chat-list").append(Template.privateChatMsg({
        fromUserName: '管理员',
        toUserName: data.tousername,
        content: showEm(data.msg),
    }));

    chatScroll();
}

// 提问
function on_cc_live_qa_question(data) {
    var question = data.value;
    $("#qas").append(Template.question({
        id: question.id,
        questionUserId: question.userId,
        questionUserName: question.userName,
        content: question.content,
    }));

    qaScroll();
}

// 回答
function on_cc_live_qa_answer(data) {

    var answer = data.value;
    // 私密回答只能自己看
    if (answer.isPrivate) {
        return;
    }

    $("#" + answer.questionId).append(Template.answer({
        answerUserName: answer.userName,
        content: answer.content,
        isFromMe: Viewer.isMe(answer.questionUserId)
    })).attr('isAnswer', 1);

    var isOnlyMyOwnQas = $(this).find('i').hasClass('active');
    if (isOnlyMyOwnQas && !Viewer.isMe(answer.questionUserId)) {
        return;
    }
    $("#" + answer.questionId).show();

    qaScroll();
}

function on_cc_callback_pages(data){
    //{
    //    "time": 11,  图片时间
    //    "url": "http://image.csslcloud.net/image/3CD4C4DF4DF8E0CB9C33DC5901307461/73D088D5AC02E6B1/0.jpg",  图片地址
    //    "docId": "73D088D5AC02E6B1",  文档ID
    //    "docName": "英语.pptx",  文档名称
    //    "docTotalPage": 11,  文档总页数
    //    "pageNum": 0,  当前页码
    //    "encryptDocId": "73D088D5AC02E6B1",  文档ID
    //    "useSDK": false
    //}
    console.log(data);
}

var Template = {
    init: function () {
        if($("#chatMsgTemplate").length){
            this.chatMsg = Handlebars.compile($("#chatMsgTemplate").html());
        }
        if($("#privateChatMsgTemplate").length){
            this.privateChatMsg = Handlebars.compile($("#privateChatMsgTemplate").html());
        }
        if($("#questionTemplate").length){
            this.question = Handlebars.compile($("#questionTemplate").html());
        }
        if($("#answerTemplate").length){
            this.answer = Handlebars.compile($("#answerTemplate").html());
        }
    }
};


var Viewer = {
    id: $('#viewerId').val(),
    name: $('#viewerName').val(),
    role: $('#viewerRole').val(),
    sessionId: $.cookie('sessionid'),
    isMe: function (viwerId) {
        return viwerId == this.id;
    }
};

function chatScroll() {
    $("#chat-list").parent().scrollTop($("#chat-list").height());
}

function qaScroll() {
    $("#qas").parent().scrollTop($("#qas").height());
}