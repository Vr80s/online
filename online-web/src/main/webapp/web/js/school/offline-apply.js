$(function () {

    function createOrder(id) {
        RequestService("/order/" + id, "POST", null, function (data) {
            if (data.success) {
                window.location.href = "/order/pay?orderId=" + data.resultObject;
            } else {
                showTip(data.errorMessage);
            }
        }, false);
    }

    function addHistory(courseId, recordType) {
        RequestService("/history/add?courseId=" + courseId + "&recordType=" + recordType, "GET", null, function (data) {
            window.location.href = "/order/pay/success?courseId=" + courseId;
        }, false);
    }



	var checkInput={
//		姓名
		checkName :  function(){
			var regName=/^[a-zA-Z\u4e00-\u9fa5]+$/;  //只能输入汉字和英文
			var J_realName=$("#J_realName");
				J_realName.removeClass("active");
				J_realName.siblings(".error-tip").addClass("hide");
				if (regName.test(J_realName.val())==false) {
					J_realName.addClass("active");
					if((J_realName.val())==""){
						J_realName.siblings(".error-tip").removeClass("hide").html("姓名不能为空");
					}else{
						J_realName.siblings(".error-tip").removeClass("hide").html("请填写真实姓名");
					}
						return false;
				}else{
					return true
				}
			},	
//		手机号
		checkNunber : function(){
			var regNunber=/^[1][3,4,5,6,7,8,9][0-9]{9}$/; //验证手机号
			var J_mobile=$("#J_mobile");
				J_mobile.removeClass("active");
				J_mobile.siblings(".error-tip").addClass("hide");
			if(regNunber.test(J_mobile.val())==false){
					J_mobile.addClass("active");
				if((J_mobile.val())==""){
					J_mobile.siblings(".error-tip").removeClass("hide").html("手机号不能为空");
				}else{
					J_mobile.siblings(".error-tip").removeClass("hide").html("请输入正确的手机号");
				}
					return false;
				}else{
					return true
				}
		},
//		微信号
		checkWechat : function(){
			var wechatNo = $('#J_wechatNo');
			wechatNo.removeClass("active");
			wechatNo.siblings(".error-tip").addClass("hide");
			if (wechatNo.val()=="") {
				wechatNo.addClass("active");
				wechatNo.siblings(".error-tip").removeClass("hide").html("微信号不能为空");
				return false;
			}else{
				return true
			}
		},
//		性别
		checSex : function(){
			var sex = $('#J_sex');
				sex.removeClass("active");
				sex.siblings(".error-tip").addClass("hide");
			if (sex.val()=="请选择") {
				sex.addClass("active");
				sex.siblings(".error-tip").removeClass("hide").html("请选择性别");
				return false;
			}else{
				return true
			}
		}
	}

$("#J_realName").blur(function(){
	checkInput.checkName();
})
$("#J_mobile").blur(function(){
	checkInput.checkNunber();
})
$("#J_wechatNo").blur(function(){
	checkInput.checkWechat();
})
$("#J_sex").blur(function(){
	checkInput.checSex();
})
    $('.J-submit').on('click', function (e) {
        var mobile = $('#J_mobile').val();
        var realName = $('#J_realName').val();
        var wechatNo = $('#J_wechatNo').val();
        var sex = $('#J_sex').val();
        var courseId = $('#J_courseId').val();
      if (checkInput.checkWechat()==false || checkInput.checkName() ==false || checkInput.checkNunber()==false ||checkInput.checSex() ==false ) {
      		return false;
      }
        $.ajax({
            method: "POST",
            url: "/courses/applyInfo",
            data: {
                "mobile": mobile,
                "realName": realName,
                "wechatNo": wechatNo,
                "sex": sex,
                "courseId": courseId
            },
            success: function (resp) {
                if (resp.success) {
                    var watchState = resp.resultObject;
                    //付费
                    if (watchState == 0) {
                        createOrder(courseId);
                    } else {
                        addHistory(courseId, 1);
                    }
                } 
//              else {
//                  showTip(resp.errorMessage);
//              }
            }
        })
    });

});