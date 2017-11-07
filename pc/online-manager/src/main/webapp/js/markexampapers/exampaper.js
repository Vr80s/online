$(function() {
    //绑定试卷数据
    var record_id = $("#examNum").find("option:selected").val();
    if(record_id!=undefined){
        dealPaperInfo(record_id);
    }
});

function goBack(grade_id,grade_name) {
    var grade_id = $("#grade_id").val();
    var grade_name = $("#grade_name").val();
    var barrier_id = $("#barrier_id").val();
    var barrier_name = $("#barrier_name").val();
    window.location.href=basePath+'/home#markexampapers/record/index?grade_id='+ grade_id +'&barrier_id='+barrier_id+'&grade_name='+grade_name+'&barrier_name='+barrier_name;
}
function change(obj) {
    //绑定试卷数据
    dealPaperInfo(obj.value);
}
function dealPaperInfo(record_id) {
    ajaxRequest(basePath+'/markexampapers/getExamPaper',{"record_id":record_id},function(res) {
        //试卷分数
        $("#examScore").html("试卷分数："+res.examScore+"分");
        var my_answer_temp = "ABCDEF";
        //拼接试卷内容
        var shijuan = '<li>'+
            '<div class="tiType clearfix">'+
            '<div class="tiType1">'+
            '<em class="bottom"></em>'+
            '<span>单选题</span>'+
            '<span>共'+res.paper0.total_count+'题</span>'+
            '</div>'+
            '<div class="tiType2">'+
            '<span>正确题数：'+res.paper0.right_count+'</span>'+
            '<span>错误题数：'+res.paper0.wrong_count+'</span>'+
            '<span>得分：'+res.paper0.right_score+'</span>'+
            '<span>满分：'+res.paper0.total_score+'</span>'+
            '</div>'+
            '</div>' +
            '<ol class="tiContent">';
        for(var i=0;i<res.paper0.examPaper.length;i++) {
            //答案是否正确
            var right = "";
            if (res.paper0.examPaper[i].is_right == 1) {
                right = "zhengque.gif";
            } else {
                right = "cuowu.png";
            }
            shijuan += '<li>' +
                '<span style="font-weight: bold;font-size: small;display: inline-block;">'+(i+1)+'. '+ res.paper0.examPaper[i].question_head +'&nbsp;（'+res.paper0.examPaper[i].question_score+ '分）</span>' +
                '<ul class="xuanxiang">' +
                '<li>';
            //选项
            var options = [];
            if(res.paper0.examPaper[i].options!=null && res.paper0.examPaper[i].options!=""){
                options = JSON.parse(res.paper0.examPaper[i].options);
                for (var j = 0; j < options.length; j++) {
                    shijuan += '<div><input disabled="disabled" type="radio" name="type0_' + i + '" value="' + j + '"';
                    if(res.paper0.examPaper[i].my_answer==j){
                        shijuan +='checked="checked"';
                    }
                    shijuan += '/><span>&nbsp;' + my_answer_temp.charAt(j) +'、'+ options[j] + '</span>';
                    //选项图片
                    var options_picture = [];
                    if(res.paper0.examPaper[i].options_picture!=null && res.paper0.examPaper[i].options_picture!="") {
                        options_picture = JSON.parse(res.paper0.examPaper[i].options_picture);
                        if(options_picture.length>=j && options_picture[j]!=null){
                            shijuan += '</br><img src="'+options_picture[j]+'"/>';
                        }
                    }
                    shijuan += '</div>';
                }
            }
            //学员答案解析
            var my_answer = "";
            if (res.paper0.examPaper[i].my_answer != null && res.paper0.examPaper[i].my_answer != "") {
                my_answer = my_answer_temp.charAt(res.paper0.examPaper[i].my_answer);
            }
            var answer = "";
            if(res.paper0.examPaper[i].answer != null && res.paper0.examPaper[i].answer != ""){
                answer = my_answer_temp.charAt(res.paper0.examPaper[i].answer);
            }
            shijuan +='</li>'+
                '</ul>'+
                '<div class="tiInfo">'+
                '<div><span>学员答案：</span><span>'+my_answer+'</span></div>'+
                '<div><span>参考答案：</span><span>'+answer+'</span></div>'+
                '<div><span>答案说明：</span><span>'+res.paper0.examPaper[i].solution+'</span></div>'+
                '<div><span>是否正确：</span><img src="'+basePath+'/images/'+right+'" alt=""/></div>'+
                '</div>'+
                '</li>';
        }
        shijuan += '</ol></li>';
        shijuan += '<li>'+
            '<div class="tiType clearfix">'+
            '<div class="tiType1">'+
            '<em class="bottom"></em>'+
            '<span>多选题</span>'+
            '<span>共'+res.paper1.total_count+'题</span>'+
            '</div>'+
            '<div class="tiType2">'+
            '<span>正确题数：'+res.paper1.right_count+'</span>'+
            '<span>错误题数：'+res.paper1.wrong_count+'</span>'+
            '<span>得分：'+res.paper1.right_score+'</span>'+
            '<span>满分：'+res.paper1.total_score+'</span>'+
            '</div>'+
            '</div><ol class="tiContent">';
        for(var i=0;i<res.paper1.examPaper.length;i++){
            //答案是否正确
            var right = "";
            if(res.paper1.examPaper[i].is_right==1){
                right="zhengque.gif";
            }else{
                right="cuowu.png";
            }
            shijuan += '<li>'+
                '<span style="font-weight: bold;font-size: small;">'+(res.paper0.examPaper.length+i+1)+'. '+res.paper1.examPaper[i].question_head+'&nbsp;（'+res.paper1.examPaper[i].question_score+ '分）</span>'+
                '<ul class="xuanxiang">'+
                '<li>';
            //选项
            var options = [];
            if(res.paper1.examPaper[i].options!=null && res.paper1.examPaper[i].options!="") {
                options = JSON.parse(res.paper1.examPaper[i].options);
                for (var j = 0; j < options.length; j++) {
                    shijuan += '<div><input disabled="disabled" type="checkbox" name="type1_' + i + '" value="' + j + '"';
                    if (res.paper1.examPaper[i].my_answer != null && res.paper1.examPaper[i].my_answer != "") {
                        var my_answer_arr = JSON.parse(res.paper1.examPaper[i].my_answer);
                        for (var k = 0; k < my_answer_arr.length; k++) {
                            if (my_answer_arr[k] == j) {
                                shijuan += 'checked="checked"';
                            }
                        }
                    }
                    shijuan += '/><span>&nbsp;' + my_answer_temp.charAt(j) + '、' + options[j] + '</span>';
                    //选项图片
                    var options_picture = [];
                    if(res.paper1.examPaper[i].options_picture!=null && res.paper1.examPaper[i].options_picture!="") {
                        options_picture = JSON.parse(res.paper1.examPaper[i].options_picture);
                        if(options_picture.length>=j && options_picture[j]!=null && options_picture[j]!=""){
                            shijuan += '</br><img src="'+options_picture[j]+'"/>';
                        }
                    }
                    shijuan += '</div>';
                }
            }
            //学员答案解析
            var my_answer_temp = "ABCDEF";
            var my_answer = [];
            if (res.paper1.examPaper[i].my_answer != null && res.paper1.examPaper[i].my_answer != "") {
                var my_answer_arr = JSON.parse(res.paper1.examPaper[i].my_answer);
                for(var j=0;j<my_answer_arr.length;j++){
                    my_answer.push(my_answer_temp.charAt(my_answer_arr[j]));
                }
            }
            var answer = [];
            if (res.paper1.examPaper[i].answer != null && res.paper1.examPaper[i].answer != "") {
                var answer_arr = JSON.parse(res.paper1.examPaper[i].answer);
                for(var j=0;j<answer_arr.length;j++) {
                    answer.push(my_answer_temp.charAt(answer_arr[j]));
                }
            }
            shijuan +='</li>'+
                '</ul>'+
                '<div class="tiInfo">'+
                '<div><span>学员答案：</span><span>'+my_answer+'</span></div>'+
                '<div><span>参考答案：</span><span>'+answer+'</span></div>'+
                '<div><span>答案说明：</span><span>'+res.paper1.examPaper[i].solution+'</span></div>'+
                '<div><span>是否正确：</span><img src="'+basePath+'/images/'+right+'" alt=""/></div>'+
                '</div>'+
                '</li>';
        }
        shijuan += '</ol></li>';
        shijuan += '<li>'+
            '<div class="tiType clearfix">'+
            '<div class="tiType1">'+
            '<em class="bottom"></em>'+
            '<span>判断题</span>'+
            '<span>共'+res.paper2.total_count+'题</span>'+
            '</div>'+
            '<div class="tiType2">'+
            '<span>正确题数：'+res.paper2.right_count+'</span>'+
            '<span>错误题数：'+res.paper2.wrong_count+'</span>'+
            '<span>得分：'+res.paper2.right_score+'</span>'+
            '<span>满分：'+res.paper2.total_score+'</span>'+
            '</div>'+
            '</div><ol class="tiContent">';
        for(var i=0;i<res.paper2.examPaper.length;i++){
            var right = "";//答案是否正确
            if(res.paper2.examPaper[i].is_right==1){
                right="zhengque.gif";
            }else{
                right="cuowu.png";
            }
            shijuan +='<li>'+
                '<span style="font-weight: bold;font-size: small;display: inline-block;">'+(res.paper0.examPaper.length+res.paper1.examPaper.length+i+1)+'. '+res.paper2.examPaper[i].question_head+'&nbsp;（'+res.paper2.examPaper[i].question_score+ '分）</span>'+
                '<ul class="xuanxiang">'+
                '<li>'+
                '<div><input disabled="disabled" type="radio" name="type2_'+i+'" value="对"';
            if(res.paper2.examPaper[i].my_answer=="对"){
                shijuan +='checked="checked"';
            }
            shijuan +='/><span>A. 对</span></div>'+
                '<div><input disabled="disabled" type="radio" name="type2_'+i+'" value="错"';
            if(res.paper2.examPaper[i].my_answer=="错"){
                shijuan +='checked="checked"';
            }
            shijuan +='/><span>B. 错</span></div>'+
                '</li>'+
                '</ul>'+
                '<div class="tiInfo">'+
                '<div><span>学员答案：</span><span>'+res.paper2.examPaper[i].my_answer+'</span></div>'+
                '<div><span>参考答案：</span><span>'+res.paper2.examPaper[i].answer+'</span></div>'+
                '<div><span>答案说明：</span><span>'+res.paper2.examPaper[i].solution+'</span></div>'+
                '<div><span>是否正确：</span><img src="'+basePath+'/images/'+right+'" alt=""/></div>'+
                '</div>'+
                '</li>';
        }
        shijuan += '</ol></li>';
        $("#shiJuanBox").html(shijuan);

        //题型点击事件
        $(".tiType").each(function(){
            $(this).click(function(){
                $(this).next(".tiContent").toggle();
                $(this).find("em").toggleClass("bottom top");
            })
        })
    });
}

