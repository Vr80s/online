
var problemId = getQueryString('problemId');
$(function(){
    getProblemAnswer()
});


//获取帮助中问题列表
function getProblemAnswer() {
    requestService("/xczh/common/getProblemAnswer",{
        id:problemId
    },function(data) {
        if(data.success==true){
            $(".problem").html(template('problem',data.resultObject));

        }else{
            alert(data.errorMessage);
        }
    });
}
