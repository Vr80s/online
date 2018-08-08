var courseId = getQueryString("courseId");

$(function(){


});

//发送讨论内容
function sendContent(contentType,content,question) {
    requestService("/xczh/course/live/audio/courseLiveAudioContent",{
        courseId:courseId,
        contentType:contentType,
        content:content,
        question:question
    },function (data) {
        if (data.success == true) {


        }
    });
}

