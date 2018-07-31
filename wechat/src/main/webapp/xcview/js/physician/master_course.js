var doctorId = getQueryString("doctor");
// 在线弟子申请的状态 1->未报名 2->没有审核 3->审核未通过 4->审核已通过 . 值是1与3 去提交信息页面 2与4 去申请信息查看页
requestGetService("/xczh/host/doctor/apprentice",{doctorId:doctorId},function (data) {
    if (data.success == true) {
        // 获取判断是否提交申请弟子信息  
        $('.disciple_application_state').html(template('disciple_application_state_id', {items: data.resultObject}));
        
        // $('.subscribe_id').html(template('subscribe_id', {items: data.resultObject.treatments}));
        // 跟师直播--师承
        if (isBlank(data.resultObject.apprenticeCourses)) {
            $(".wrap_vedio_main").hide();
        } else{
            $(".wrap_vedio_main").show();
            // 跟师直播开始
            // 跟师直播开始
            var apprenticeCourses = data.resultObject.apprenticeCourses;
            for(var i=0;i<apprenticeCourses.length;i++){
                if(apprenticeCourses[i].teaching){
                    apprenticeCourses[i].teaching=1;
                }else{
                    apprenticeCourses[i].teaching=0;
                }
            }
            $('#teacher_hide').html(template('teacher_hide_id', {items: apprenticeCourses}));
        }

    }

});