/*点击性别开始*/
    $(".sex").click(function(){
        $(".popup").show();
    });
    $("#man").click(function(){
        $(".sex_show").html($("#man").html());
         getApply();
    });
    $("#she").click(function(){
        $(".sex_show").html($("#she").html());
         getApply();
    });
    $(".popup").click(function(){
        $(".popup").hide();
    });
    /*点击性别结束*/
    function getApply(){
    if($('.name input').val()==''
        ||$('.tel input').val()==''
        ||$('.age input').val()==''
        ||$('.sex_show').html()==''){
        $(".buttom").css("opacity","0.5");
        return false;
    }else{
        $(".buttom").css("opacity","1");
        return true;
    };
}
    // 姓名
    $('.name input').keyup(function(){
        getApply();
    });
    // 手机号
    $('.tel input').keyup(function(){
        getApply();
    });

    // 微信
    $('.age input').keyup(function(){
        getApply();
    });    

/*function getQueryString(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r!=null) return r[2]; return '';
}*/
var courseId = getQueryString('orderId');
/*console.log("courseId");
alert(courseId);*/
$(".buttom").click(function(){
        // var courseId = getQueryString('course_id');
        var realName = $(".name input").val();
        var mobile = $(".tel input").val();
        if (!(/^1[345678]\d{9}$/.test(mobile))) {
            webToast("手机号格式不正确","middle",1500);
            return false;
        }
        var wechatNo = $(".age input").val();

        if (!(/^[a-zA-Z]([-_a-zA-Z0-9]{5,19})+$/.test(wechatNo))) {
            webToast("微信号格式不正确","middle",1500);
            return false;
        }
        // var sex = $(".sex .sex_show").html();
        var sex = "";
        if($(".sex_show").html()=='女'){
            sex = 0;
        }else if($(".sex_show").html()=='男'){
            sex = 1;
        }
        requestService("/xczh/apply/add",{'realName':realName,"mobile":mobile,"wechatNo":wechatNo,"sex":sex},function(data) {
             window.location.href = "purchase.html?orderId=" + courseId + "";
        });
});
