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


var courseId = getQueryString('courseId');
var orderId = getQueryString('orderId');
/**
 * 提交表单
 * @returns
 */
$(".buttom").click(function(){
	
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
        				   webToast("课程信息有误","middle",1500);
        			   }
        		    })
        		}else{
        			webToast(data.errorMessage,"middle",1500);
        		}
        });
});
