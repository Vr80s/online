



//提交反馈
function feedback() {
    if($.trim($(".textarea").val())==""){
        webToast("请输入反馈内容","middle",3000);
        return false;
    }
    var content = $(".textarea").val();
    requestService("/xczh/common/addOpinion",{
        content:content
    },function(data) {
        if(data.success==true){
            webToast("提交成功","middle",3000);
            window.location.href="my_homepage.html";

        }else{
            alert(data.errorMessage);
        }
    });
}
