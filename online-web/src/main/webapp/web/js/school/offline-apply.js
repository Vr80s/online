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
            window.location.href = "/courses/" + courseId + "/info";
        }, false);
    }

    $('.J-submit').on('click', function (e) {
        var mobile = $('#J_mobile').val();
        var realName = $('#J_realName').val();
        var wechatNo = $('#J_wechatNo').val();
        var sex = $('#J_sex').val();
        var courseId = $('#J_courseId').val();
        if (!mobile) {
            showTip("手机号不能为空");
            return false;
        }
        if (!realName) {
            showTip("名字不能为空");
            return false;
        }
        if (!wechatNo) {
            showTip("微信号不能为空");
            return false;
        }
        if (!sex) {
            showTip("性别不能为空");
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
                } else {
                    showTip(resp.errorMessage);
                }
            }
        })
    });

});