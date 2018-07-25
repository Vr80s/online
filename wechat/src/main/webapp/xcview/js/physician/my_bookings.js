//requestService
$(function(){
    var id = getQueryString("id");
    requestGetService("/doctor/treatment/appointmentInfo",{id:id},function (data) {
        if (data.success == true) {
            // 提交预约信息
            $('.main').html(template('main_id', {items: data.resultObject}));
        }
    });

    

});





