var courseId = getQueryString("courseId");
courseId = 814;
$(function(){

    //getcontentList();
});

//获取讨论内容列表
function getcontentList() {
    requestGetService("/xczh/course/live/audio/courseLiveAudioContent/"+courseId,{
        courseId:courseId,
        pageNumber:1,
        endTime:null
    },function (data) {
        if (data.success == true) {

        }
    });

}

//发送讨论内容
function sendContent() {
    var question = false;
    if($(".radio_put").is(':checked')) {
        question = true;
    }
    var content = $("#chat_put").val();
    var courseLiveAudioContent = {
        "courseId": courseId,
        "courseLiveAudioDiscussionVO": {question:question},
        "contentType": 1,
        "content": content
    };
    $.ajax({
        type: "post",
        url: "/xczh/course/live/audio/courseLiveAudioContent",
        data:JSON.stringify(courseLiveAudioContent),
        contentType:"application/json",
        async:false,
        success: function(data) {
            if(data.success === true) {

            }
        }
    });
}


// 点击发送--还原输入区域
$(".transmit").click(function(){
    var commentArea = $(".comment_area").css("display");  /*獲取全部評論顯示隱藏*/
    if (commentArea == "block") {
        $(".footer_bg").hide();
    }else{
        sendContent();
        sendMsgBtn();   //点击发送方法
        // alert(22);
        // 全部评论隱藏--點擊發送
        $(".barrage_switch").hide();  /*弹幕隐藏*/
        $(".chat_all").hide();      /*查看更多评论隐藏*/
        $(".chat").removeClass("chats");  /*增加input长度*/
        $(".chat_put").removeClass("chat_puts");
        $(".transmit").hide();   /*发送按钮*/
        $(".footer_bg").hide();  /*整屏按钮*/
        $(".barrage_switch").show();  /*弹幕*/
        $(".chat_all").show();      /*查看更多评论*/
    };

});
