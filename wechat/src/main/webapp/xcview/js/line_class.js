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
        $(".buttom").css("opacity","0.3");
        return false;
    }else{
        $(".buttom").css("opacity","1");
        // 点击按钮提交表单 
$(".buttom").click(function(){
    
        var realName = $(".name input").val();
        var mobile = $(".tel input").val();
        if (!(/^1[345678]\d{9}$/.test(mobile))) {
            $(".web_toast").removeClass("web_toasts");
            webToast("手机号格式不正确","middle",1500);
            return false;
        }
        var wechatNo = $(".age input").val();

        /*if (!(/^(?=.*\d)[a-z\d]{6,20}$/i.test(wechatNo))) {
            webToast("仅支持6-20字母、数字、下划线或减号","middle",1500);
            $(".web_toast").addClass("web_toasts");
            return false;
        }*/
        var sex = "";
        if($(".sex_show").html()=='女'){
            sex = 0;
        }else if($(".sex_show").html()=='男'){
            sex = 1;
        }
        requestService("/xczh/apply/add",{
            'realName':realName,"mobile":mobile,
            "wechatNo":wechatNo,"sex":sex,
            "courseId":courseId},
            function(data) {
                if(data.success){ //报名成功
                    /**
                     * 判断这个课程是否是免费了
                     *  courseId
                     */
                    requestService("/xczh/course/userCurrentCourseStatus",{'courseId':courseId},function(data) {
                       if(data.success){ //报名成功
                           var obj = data.resultObject;
                           /**
                            * 免费的并且是没有学习的
                            */
                           if(obj.watchState == 1 && obj.learning == 0){
                                /*
                                 * 添加学习信息 -->去猜你喜欢页面
                                 */
                                requestService("/xczh/history/add", {courseId: courseId,recordType: 1
                                }, function (data) {});
                                
                                window.location.href = "/xcview/html/buy_prosperity.html?courseId="+courseId;
                           }else{
                               /*
                                 * 去购买页面
                                 */
                                window.location.href = "purchase.html?orderId=" + orderId + "";
                           }
                        }else{
                          $(".web_toast").removeClass("web_toasts");
                           webToast("课程信息有误","middle",1500);

                        }
                    })
                }else{
                  $(".web_toast").removeClass("web_toasts");
                    webToast(data.errorMessage,"middle",1500);
                }
        });

       
});

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

var courseId = getQueryString('courseId');
var orderId = getQueryString('orderId');
// var submitted = getQueryString('submitted');
/**
 * 提交表单
 * @returns
 */
 // 判断是否提交
 // var submitted = "";

  // if(submitted){ //线下课
      // $(".hint").show();
      /**
       * 查询这个信息
       */
       /* var submitted ="";
        var realName = $(".name input").val();
        var mobile = $(".tel input").val();
        var wechatNo = $(".age input").val();
        var sex = $(".sex_show").html();*/
        // var submitted ="";
        requestGetService("/xczh/apply/applyInfo",{'courseId':orderId},function(data){
        // requestGetService("/xczh/apply/applyInfo",null,function(data){
            if(data.success){
                // 调接口成功
                var result = data.resultObject.applyInfo;
                var submitted = data.resultObject.submitted;
                // 这个判断你要问问是需要怎么处理，没有需要怎么处理
                if(submitted){
                    
                    $(".opacity").css("opacity","0.3");
                }else{
                    $(".opacity").css("opacity","0.3");
                }
                $(".name input").val(result.realName);
                $(".tel input").val(result.mobile);
                $(".age input").val(result.wechatNo);
                // $(".sex_show").html(result.sex);
                if(result.sex==0){
                   $(".sex_show").html("女");
               }else if(result.sex==1){
                   $(".sex_show").html("男");
               }else{
                   $(".sex_show").html("未知");
               }
            }else{
              $(".web_toast").removeClass("web_toasts");
                // 提示错误信息  // 调接口失败
                webToast(data.errorMessage,"middle",1500);
            }
        })
        // console.log(data);
 // }


