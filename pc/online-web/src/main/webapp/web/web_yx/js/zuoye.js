/**
 * Created by admin on 2016/12/3.
 */
/**
 * Created by admin on 2016/12/1.
 */
$(function () {
    var examId = $.getUrlParam("homeworkId");
    var studentId = $.getUrlParam("studentid");

    if (!examId) {
        homeworkId="a139c23827764f2daee34d3576e429ce";
    }
    if (!studentId) {
        studentId="acad6ea0a70742a1accd6b1bde8a2354";
    }
    if (!status) {
        status = "4"
    }
    var examHeight = $(".examBasicInfo").innerHeight() + $(".timuTypeBox").innerHeight();
    var answerCardLeft = $(".examContent").offset().left + $(".timuAndAnswer").innerWidth() + 12;
    $(document).scroll(function () {
        $(".answerCard").addClass("answerCardFloat").css("left", answerCardLeft + "px");
        if ($(document).scrollTop() > examHeight + 81) {
            $(".timuTypeBox").addClass("examFloat");
            //$(".answerCard").addClass("answerCardFloat").css("left", answerCardLeft + "px");
        } else {
            $(".timuTypeBox").removeClass("examFloat");
            //$(".answerCard").removeClass("answerCardFloat");
        }
    });

    //倒计时
    function getRTime(time,date) {
        var intDiff = date*60;
      /*  var intDiff =date*10;*/
        var timr = window.setInterval(function () {
            time--;
            console.log(time)
            if (time == 0) {
                $(".examtimeOutBox").css("display", "block");
                aaa();
                clearInterval(timr);
            }
            var day = 0,
                hour = 0,
                minute = 0,
                second = 0;//时间默认值
            if (intDiff > 0) {
                day = Math.floor(intDiff / (60 * 60 * 24));
                hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
                minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
                second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
            }
            if (minute <= 9) {
                minute = '0' + minute;
            }
           /* if (hour <= 9) {
                hour = hour;
            }*/
            if (day <= 9) {
                day = day;
            }
            if (second <= 9) {
                second = '0' + second;
            }

            if(day!=0){
                $(".examTime span").html("<span>"+day + "</span>天<span>");
            }else if(hour==0){

                $(".examTime span").html("<span>"+minute + "</span>分钟<span>");

            }else if(day==0){

                $(".examTime span").html("<span>"+hour + "</span>小时<span>");
            }

            //intDiff--;
            //if (intDiff == 0) {
            //    $(".examtimeOutBox").css("display", "block");
            //   //window.clearInterval();
            //    aaa();
            //}
            window.clearInterval;
        }, 1000);
        //    if ( (intDiff--) <= 0 ) {
        //        if (intDiff <= 0) {
        //            $(".examtimeOutBox").css("display", "block");
        //            aaa();
        //
        //        }
        //    }
        // }, 1000);
    }
    function aaa() {
        var aa;
        var time3 = $(".timedaojishi").text();
        time3 = parseInt(time3);
        if (time3 != 0) {
            aa = setInterval(function () {
                $(".timedaojishi").text(time3 + "秒");
                time3--;
                if (time3 == -1) {
                    $(".examtimeOutBox").css("display", "none");
                    $(".confirm").trigger("click");
                    clearInterval(aa);

                }

            }, 1000);
        }
    }
 template.helper('backAnswer',function(i,ans,val,optionsPictureList){
     var flag=false; 
     var str="";
    for(var j=0;j<ans.length;j++){
        if(i==ans[j]){
           str='<li class="duoxuanTi select" data-selectAnswer="'+i+'"><div><em class="checkboxBtn"><i class="iconfont icon-xuanzhong"></i></em></div><span>'+val+'</span>' +   '<span>'+optionsPictureList[i]+'</span>' +
            '</li>';
        }else{
        	str='<li class="duoxuanTi" data-selectAnswer="'+i+'"><div><em class="checkboxBtn"><i class="iconfont icon-xuanzhong"></i></em></div><span>'+val+'</span>' +
            '<span>'+optionsPictureList[i]+'</span>' +
            '</li>';
        }
     }
    return str;
 });
    template.helper('isAnswer',function(a){
        var num;
        if(a==true){
            return num=0;
        }else{
            return num=1;
        }
    });

	var base = new Config().base;

    var examInfo = '<div class="examName">{{items.homework_name}}</div>' +
            '<span class="tiCount">题目数量：{{items.total_questions}}道</span>' +
            '<span class="tijiaoTime">练习结束时间：{{items.end_time}}</span>'
        , questionList = '{{each quesList}}' +
            '{{if $value.question_type==0}}' +
            ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-homework_question_id="{{$value.homework_question_id}}"  data-studenId="{{$value.student_id}}" data-homework_id={{$value.homework_id}}>' +           
            '{{$index+1}}、{{#$value.question_content}}' +
        '' +
            '<ol class="ExamAnswer">' +
            '{{each $value.optionsList as $val i}}' +
            '{{if $value.is_answer==true && $value.answer==i}}' +
            '<li class="danxuanTi select" data-selectAnswer="{{i}}">' +
            '{{else}}' +
            '<li class="danxuanTi" data-selectAnswer="{{i}}">' +
            '{{/if}}' +
            '<div><em class="radioBtn"></em></div>' +
            '<span>{{$val}}</span>' +
            '<img src="{{$value.optionsPictureList[i]}}" alt="">' +
            '{{/each}}' +
            '</li>' +
            '</ol>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type==1}}' +
            ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-homework_question_id="{{$value.homework_question_id}}"  data-studenId="{{$value.student_id}}" data-homework_id={{$value.homework_id}}>' +            '{{$index+1}}、{{#$value.question_content}}' +
            '<ol class="ExamAnswer dxt" data-da="{{$value.answer}}" data-isAnser="{{isAnswer($value.is_answer)}}">' +
            '{{each $value.optionsList as $val i}}' +
            '{{if $value.is_answer==true}}' +
//          '{{#backAnswer(i,$value.answerList,$val,$value.optionsPictureList)}}'+
            '<li class="duoxuanTi" data-selectAnswer="{{i}}">' +
            '{{else}}' +
            '<li class="duoxuanTi" data-selectAnswer="{{i}}">' +
            '{{/if}}' +
            '<div><em class="checkboxBtn"><i class="iconfont icon-xuanzhong"></i></em></div>' +
            '<span>{{$val}}</span>' +
            '<img src="{{$value.optionsPictureList[i]}}" alt="">' +
            '</li>' +
            '{{/each}}' +          
            '</ol>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type==2}}' +
            ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-homework_id="{{$value.homework_id}}" data-homework_question_id="{{$value.homework_question_id}}" data-studenId="{{$value.student_id}}" data-examId={{$value.exam_id}}>' +
            '{{$index+1}}、{{#$value.question_content}}' +
            '<ol class="ExamAnswer">' +
            '{{if $value.is_answer==true}}' +
            '{{if $value.is_answer==true && $value.answer=="对"}}' +
            '<li class="panduanTi select" data-selectAnswer="0">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>√</span>' +
            '</li>' +
            '<li class="panduanTi" data-selectAnswer="1">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>×</span>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.is_answer==true && $value.answer=="错"}}' +
            '<li class="panduanTi" data-selectAnswer="0">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>√</span>' +
            '</li>' +
            '<li class="panduanTi select" data-selectAnswer="1">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>×</span>' +
            '</li>' +
            '{{/if}}' +
            '{{else}}' +
            '<li class="panduanTi" data-selectAnswer="0">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>√</span>' +
            '</li>' +
            '<li class="panduanTi" data-selectAnswer="1">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>×</span>' +
            '</li>' +
            '</ol>' +
            '</li>' +
            '{{/if}}' +
            '{{/if}}' +
            '{{/each}}',
        answerCardA =
            ' <li class="radioAnswer">' +
            '<span>单选题</span>' +
            '<ol class="clearfix">' +
            '{{each answerCard}}' +
            '{{if $value.question_type==0}}' +
            '{{if $value.is_answer==true}}' +
            ' <li class="select" data-answerId="{{$index}}">{{$index+1}}</li>' +
            '{{else}}' +
            ' <li  data-answerId="{{$index}}">{{$index+1}}</li>' +
            '{{/if}}' +
            '{{/if}}' +
            '{{/each}}' +
            '</ol>' +
            '</li>';

    answerCardB = ' <li class="radioAnswer">' +
        '<span>多选题</span>' +
        '<ol class="clearfix">' +
        '{{each answerCard}}' +
        '{{if $value.question_type==1}}' +
        '{{if $value.is_answer==true}}' +
        '<li class="select" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        '<li data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{/if}}' +
        '{{/if}}' +
        '{{/each}}' +
        '</ol>' +
        '</li>';

    answerCardC =
        '<li class="radioAnswer">' +
        '<span>判断题</span>' +
        '<ol class="clearfix">' +
        '{{each answerCard}}' +
        '{{if $value.question_type==2}}' +
        '{{if $value.is_answer==true}}' +
        '<li class="select" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        ' <li data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{/if}}' +
        '{{/if}}' +
        '{{/each}}' +
        '</ol>' +
        '</li>';
    RequestService("/api/callThirdPost", "POST", {
        thirdUrl: base + "/bxg_anon/my/homework/startHomework",
        homeworkId:examId,
        studentId: studentId
    }, function (result) {
        var data = {
            homeworkId: result.resultObject.questionList[0].homework_id,
            studentId: result.resultObject.questionList[0].student_id
        };
        var examTotalTime = result.resultObject.homeworkData.remain;
        getRTime(examTotalTime,result.resultObject.homeworkData.duration);
        $(".examBasicInfoTop").html(template.compile(examInfo)({
            items: result.resultObject.homeworkData
        }));
        $(".timu").html(template.compile(questionList)({
            quesList: result.resultObject.questionList
        }));

        if(result.resultObject.homeworkData.remain <= 0){
            alert("练习时间已到");
            window.location.href="/web/html/myStudyCenter.html?state=2"
        }
            $(".dxt").each(function(){
                var arr=$(this).attr("data-da");
                var isAns=$(this).attr("data-isAnser");
                if(isAns==0){
                    arr=arr.substring(1,arr.length-1);
                    arr=arr.replace(/\"/g, "");
                    arr=arr.split(",");
                    console.log(arr)
                    for(i=0;i<arr.length;i++){
                        $(this).find("li").each(function(){
                            if($(this).index()==arr[i]){
                                $(this).addClass("select")
                            }
                        });
                    };
                }
            });

        var questType=[];
        $.each(result.resultObject.questionList,function(i,data){
            questType.push(data.question_type);
        });
        Array.prototype.unique3 = function(){
            var res = [];
            var json = {};
            for(var i = 0; i < this.length; i++){
                if(!json[this[i]]){
                    res.push(this[i]);
                    json[this[i]] = 1;
                }
            }
            return res;
        };
        questType=questType.unique3();
        $.each(questType,function(i,a){
            if(a==0){
                $(".timuTypeBox").append('<li class="select" data-timuType="0">单选题</li>');
                $(".thirdLeiAnswer").html(template.compile(answerCardA)({
                    answerCard: result.resultObject.questionList
                }));
            }else if(a==1){
                $(".timuTypeBox").append('<li data-timuType="1">多选题</li>');
                $(".thirdLeiAnswer").append(template.compile(answerCardB)({
                    answerCard: result.resultObject.questionList
                }));
            }else if(a==2){
                $(".timuTypeBox").append('<li data-timuType="2">判断题</li>');
                $(".thirdLeiAnswer").append(template.compile(answerCardC)({
                    answerCard: result.resultObject.questionList
                }));

            }
        });
        //单选，多选，判断选择效果
        var questionId, studentId, examId, answer,answerd=[];
        $(".timu li").click(function () {
            var Indexid,answerid;
            if ($(this).hasClass("danxuanTi") || $(this).hasClass("panduanTi")) {
                $(this).toggleClass("select").siblings().removeClass("select");
                Indexid = $(this).parent().parent().attr("data-indexId");
            } else if ($(this).hasClass("duoxuanTi")) {
                $(this).toggleClass("select");
                Indexid = $(this).parent().parent().attr("data-indexId");
            }


            var flag=true;
            var $m=$(this).parent("ol").children();
            $m.each(function () {
                if ($(this).hasClass("select")) {
                    flag=false;
                }
            });
            if(flag){
                $(".thirdLeiAnswer ol li").each(function () {
                    answerid = $(this).attr("data-answerid");
                    if (Indexid == answerid) {
                        $(this).removeClass("select");
                        return false;
                    }
                });
            }else{
                $(".thirdLeiAnswer ol li").each(function () {
                    answerid = $(this).attr("data-answerid");
                    if (Indexid == answerid) {
                        $(this).addClass("select");
                    }
                });
            }
            examId= $(this).parent().parent().attr("data-homework_id");
            studentId = $(this).parent().parent().attr("data-studenId");
            questionId= $(this).parent().parent().attr("data-homework_question_id");
            if($(this).hasClass("select")){
                answer = $(this).attr("data-selectAnswer");
            }else{
                answer="";
            }

            if ($(this).hasClass("panduanTi")) {
                if($(this).hasClass("select")){
                    if (answer == 0) {
                        answer = "对"
                    } else {
                        answer = "错"
                    }
                }else{
                    answer="";
                }
            }
            if ($(this).hasClass("duoxuanTi")) {
                answerd=[];
                $(this).parent().find(".duoxuanTi").each(function(){
                    if($(this).hasClass("select")){
                        answerd.push($(this).attr("data-selectanswer"));
                    }else{
                        answerd.push();
                    }
                });
                answerd=answerd.join();
                answerd = answerd == ""? "" : "["+answerd+"]";
                RequestService("/api/callThirdPost", "POST", {
                    thirdUrl: base + "/bxg_anon/my/homework/updateStuHomework",
                    homeworkId: examId,
                    studentId: studentId,
                    homeworkQuestionId: questionId,
                    answer: answerd
                });
            }  else {
                RequestService("/api/callThirdPost", "POST", {
                    thirdUrl: base + "/bxg_anon/my/homework/updateStuHomework",
                    homeworkId: examId,
                    studentId: studentId,
                    homeworkQuestionId: questionId,
                    answer: answer
                });
            }
            return false;
        });

        var datiStatus=false;
        $(".tijiaoAnswer").click(function () {
            RequestService("/online/user/isAlive", "GET", "", function (data) {
                if (data.success == true) {
                    datiStatus = false;
                    $(".radioAnswer li").each(function () {
                        if (!$(this).hasClass("select")) {
                            datiStatus = true;//未答完
                        }
                    });
                    if (datiStatus) {
                        $(".noCompleteHint").css("display", "block");
                    } else {
                        $(".yidawan").css("display", "block");
                    }
                }else{
                    $('#login').modal('show');
                }
            });
        });
        $(".continueAnswer").click(function(){
            $(".noCompleteHint").css("display","none");
        });
        $(".tijiao").click(function(){
            RequestService("/api/callThirdPost", "POST", {
                thirdUrl: base + "/bxg_anon/my/homework/submitStuHomework",
                homeworkId: data.homeworkId,
                studentId: data.studentId
            },function(data){
                if(data.success==true){
                    window.location.href="/web/html/myStudyCenter.html"
                }
            });
        });
        $(".confirm").click(function () {
            RequestService("/api/callThirdPost", "POST", {
                thirdUrl: base + "/bxg_anon/my/homework/submitStuHomework",
                homeworkId: data.homeworkId,
                studentId: data.studentId
            },function(data){
                if(data.success==true){
                    window.location.href="/web/html/myStudyCenter.html"
                }
            });
        });
        //答题卡单击事件
        $(".thirdLeiAnswer ol li").click(function () {
            var answerId = $(this).attr("data-answerid");
            $(".timu>li").each(function () {
                var dataIndexId = $(this).attr("data-indexid");
                var currentTop = $(this).offset().top;
                if (dataIndexId === answerId) {
                    $("body,html").animate({"scrollTop": currentTop - 59 + "px"});
                    return false;
                }
            });
        });
        $(".checked").click(function(){
            $(".yidawan").css("display","none");
        });
        $(".timuTypeBox li").click(function () {
            var timuType = $(this).attr("data-timuType");
            $(this).addClass("select").siblings().removeClass("select");
            $(".timu>li").each(function () {
                var dataType = $(this).attr("data-Type");
                var currentTop = $(this).offset().top;
                if (dataType === timuType) {
                    $("body,html").animate({"scrollTop": currentTop- 60 + "px"});
                    return false;
                }
            });
        })
    });
});