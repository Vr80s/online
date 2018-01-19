function pass(){
	debugger
    $.ajax({
        type:'post',
        url:basePath+'/cloudclass/courseApply/pass?courseApplyId='+courseApplyId,
        dataType:'json',
        success:function(data){
            debugger
            alertInfo(data.errorMessage,function(){
                if(data.success){
                    window.location.reload();
                }
            });
        }
    }) ;
}
function notPass(){
    debugger
    var cadata = {};
    cadata.id=courseApplyId;
    cadata.dismissal=$("#dismissal").val();
    cadata.dismissalRemark=$("#dismissalRemark").val();
    $.ajax({
        type:'post',
        url:basePath+'/cloudclass/courseApply/notPass',
        data:JSON.stringify(cadata),
        contentType:'application/json',
        dataType:'json',
        success:function(data){
            alertInfo(data.errorMessage,function(){
                if(data.success){
                    window.location.reload();
                }
            });
        }
    }) ;
}

function confirmNotPass() {
    openDialog("setDismissalDialog", "setDismissalDiv", "拒绝申请理由", 500, 400, true, "确定", function () {
        notPass();
    });
}
