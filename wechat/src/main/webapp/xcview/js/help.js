
$(function(){
    getProblemAnswer()
});


//获取帮助中问题列表
function getProblemAnswer() {
    requestService("/xczh/common/getProblems",{
    },function(data) {
        if(data.success==true){
            $(".help_main").html(template('help_main',{items:data.resultObject}));

        }else{
            alert(data.errorMessage);
        }
    });
}

//跳转问题详情
function problem(id){
    window.location.href="problem.html?problemId="+id+"";
}
