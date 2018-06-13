$(document).ready(function ($) {

    $('#pass').click(function () {

        // if(confirm("确定认证成功？")){
        var applyId = $('#applyId').val();

        $.ajax({
            type: "POST",
            url: "/medical/doctor/apply/updateStatus",
            data: {
                id: applyId,
                status: 1
            },
            async: true,
            success: function (result) {
                if (result.success == true) {
                    window.location.href = "/home#medical/doctor/apply/index"
                }
                else {
                    alertInfo(result.errorMessage);
                }
            },
            complete: function (result) {
            }
        });
        // }
    });

});

function confirmNotPass() {
    openDialog("setDismissalDialog", "setDismissalDiv", "拒绝申请理由", 500, 400, true, "确定", function () {
        var remark = $('#remark').val();
        var applyId = $('#applyId').val();
        if (!remark) {
            alertInfo('输入不通过理由!');
            return false;
        }
        if (remark.length > 255) {
            alertInfo('理由不能超过255个字');
            return false;
        }
        $.ajax({
            type: "POST",
            url: "/medical/doctor/apply/updateStatus",
            data: {
                id: applyId,
                status: 0,
                remark: remark
            },
            async: true,
            success: function (result) {
                if (result.success == true) {
                    window.location.href = "/home#medical/doctor/apply/index"
                }
                else {
                    alertInfo(result.errorMessage);
                }
            },
            complete: function (result) {
            }
        });
    });
}