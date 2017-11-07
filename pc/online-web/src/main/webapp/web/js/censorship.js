/**
 * Created by admin on 2016/12/1.
 */
$(function () {
    //解析url地址
    var ourl = document.location.search;
    var apams = ourl.substring(1).split("&");
    var arr = [];
    for (i = 0; i < apams.length; i++) {
        var apam = apams[i].split("=");
        arr[i] = apam[1];
        var gqid = arr[0];
    };
    //倒计时
    function getRTime(date) {
        date = parseInt(date);
        var intDiff = date;
        var ww;
        ww = setInterval(function () {
            var hour = 0,
                minute = 0,
                second = 0;//时间默认值
            if (intDiff > 0) {
                hour = Math.floor(intDiff / (60 * 60));
                minute = Math.floor(intDiff / 60) - (hour * 60);
                second = Math.floor(intDiff) - (hour * 60 * 60) - (minute * 60);
            }
            if (minute <= 9) {
                minute = '0' + minute;
            }
            if (hour <= 9) {
                hour = '0' + hour;
            }
            if (second <= 9) {
                second = '0' + second;
            }
            $(".examTime span").html("<span>" + hour + "</span>:<span>" + minute + "</span>:<span>" + second + "</span>");
            intDiff--;
            if (intDiff == 0) {
                $(".examtimeOutBox").css("display", "block");
                clearInterval(ww);
                aaa();
            }
        }, 1000);
    }
/*时间倒计时*/
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

    var examInfo = '<div class="examName">关卡任务：{{items.name}}</div>' +
            '<span class="tongguanfenshu">总分数/通关分数：{{items.total_score}}/{{items.pass_score_percent}}分</span>'+
            '<span class="tijiaoTime">闯关时长：{{items.limit_time}}分钟</span>'+
            '<span class="tiCount">闯关题数：{{items.sum_total}}道</span>'
        , questionList = '{{each quesList as $value $index}}' +
            '{{if $value.question_type=="0"}}' +
            ' <li class="question0" data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-guankaId="{{$value.id}}" data-recordId="{{$value.record_id}}">' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
            '<ol class="ExamAnswer">' +
            '{{each options($value.options) as $val i}}' +
            '{{if $value.my_answer==[i]}}' +
            '<li class="danxuanTi select" data-selectAnswer="{{i}}">' +
            '{{else}}' +
            '<li class="danxuanTi" data-selectAnswer="{{i}}">' +
            '{{/if}}' +
            '<div><em class="radioBtn"></em></div>' +
            '{{optionList[i]}}<span>{{$val}}</span>' +
            '{{if $value.options_picture!="" && $value.options_picture!=null}}' +
            '{{#optionsImg($value.options_picture,i)}}' +
            '{{/if}}' +
            '{{/each}}' +
            '</li>' +
            '</ol>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type==1}}' +
            ' <li class="question1" data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-guankaId="{{$value.id}}" data-recordId="{{$value.record_id}}">' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
            '<ol class="ExamAnswer dxt" data-da="{{$value.my_answer}}">' +
            '{{each options($value.options) as $val i}}' +
            '<li class="duoxuanTi" data-selectAnswer="{{i}}">' +
            '<div><em class="checkboxBtn"><i class="iconfont icon-xuanzhong"></i></em></div>' +
            '{{optionList[i]}}<span>{{$val}}</span>' +
            '{{if $value.options_picture!="" && $value.options_picture!=null}}' +
            '{{#optionsImg($value.options_picture,i)}}' +
            '{{/if}}' +
            '</li>' +
            '{{/each}}' +
            '</ol>' +
            '</li>' +
            '{{/if}}' +
                '{{if $value.question_type==2}}' +
                    ' <li class="question2" data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-guankaId="{{$value.id}}" data-recordId="{{$value.record_id}}">' +
                    '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
                        '<ol class="ExamAnswer">' +
                        '{{if $value.my_answer!="" && $value.my_answer!=null}}' +
                            '{{if $value.my_answer=="对"}}' +
                                '<li class="panduanTi select" data-selectAnswer="0">' +
                                '<div><em class="panduanBtn"></em></div>' +
                                '<span>√</span>' +
                                '</li>' +
                                '<li class="panduanTi" data-selectAnswer="1">' +
                                '<div><em class="panduanBtn"></em></div>' +
                                '<span>×</span>' +
                                '</li>' +
                            '{{/if}}' +
                            '{{if $value.my_answer=="错"}}' +
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
                        '{{/if}}' +
                        '</ol>' +
                    '</li>' +
                '{{/if}}' +
            '{{/each}}',
        answerCardA =
            ' <li class="radioAnswer">' +
            '<span>单选题</span>' +
            '<ol class="clearfix">' +
            '{{each answerCard}}' +
            '{{if $value.question_type==0}}' +
            '{{if $value.my_answer!="" && $value.my_answer!=null}}' +
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
        '{{if $value.my_answer!="" && $value.my_answer!=null}}' +
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
        '{{if $value.my_answer!="" && $value.my_answer!=null}}' +
        '<li class="select" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        ' <li data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{/if}}' +
        '{{/if}}' +
        '{{/each}}' +
        '</ol>' +
        '</li>';
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


   RequestService("/barrier/createUserTestPaper","POST",{barrierId:gqid},function(data){
       RequestService("/barrier/getBarrierBasicInfo","GET",{id:gqid,examStatu:0},function(result) {
           $(".examBasicInfoTop").html(template.compile(examInfo)({
               items: result.resultObject
           }));
           var examTotalTime = result.resultObject.limit_time;
           var userTime=result.resultObject.current_usetime;
           if(userTime>=examTotalTime*60){
               $(".examtimeOutBox").css("display", "block");
               aaa();
           }else{
               $(".examtimeOutBox").css("display", "none");
               getRTime(examTotalTime*60-userTime);
           }
       });
       var optionList=["A、","B、","C、","D、","E、","F、","G、","H、","I、","G、","K、","L、","M、","N、"];
       $(".timu").html(template.compile(questionList)({
           optionList:optionList,
           quesList: data.resultObject
       }));
       var questType=[];
       $.each(data.resultObject,function(i,data){
           questType.push(data.question_type);
       });
       questType=questType.unique3();
       $.each(questType,function(i,a){
           if(a==0){
               $(".timuTypeBox").append('<li class="d0 select" data-timuType="0">单选题</li>');
               $(".thirdLeiAnswer").html(template.compile(answerCardA)({
                   answerCard: data.resultObject
               }));
           }else if(a==1){
               $(".timuTypeBox").append('<li class="d1" data-timuType="1">多选题</li>');
               $(".thirdLeiAnswer").append(template.compile(answerCardB)({
                   answerCard: data.resultObject
               }));
           }else if(a==2){
               $(".timuTypeBox").append('<li class="d2" data-timuType="2">判断题</li>');
               $(".thirdLeiAnswer").append(template.compile(answerCardC)({
                   answerCard: data.resultObject
               }));
           }
       });
       //单选，多选，判断选择效果
       var  examId, answer,answerd = [];
       $(".timu li").click(function () {
           var Indexid, answerid;
           if ($(this).hasClass("danxuanTi") || $(this).hasClass("panduanTi")) {
               $(this).toggleClass("select").siblings().removeClass("select");
               Indexid = $(this).parent().parent().attr("data-indexId");
           } else if ($(this).hasClass("duoxuanTi")) {
               $(this).toggleClass("select");
               Indexid = $(this).parent().parent().attr("data-indexId");
           }

           var flag = true;
           var $m = $(this).parent("ol").children();
           $m.each(function () {
               if ($(this).hasClass("select")) {
                   flag = false;
               }
           });


           if (flag) {
               $(".thirdLeiAnswer ol li").each(function () {
                   answerid = $(this).attr("data-answerid");
                   if (Indexid == answerid) {
                       $(this).removeClass("select");
                       return false;
                   }
               });
           } else {
               $(".thirdLeiAnswer ol li").each(function () {
                   answerid = $(this).attr("data-answerid");
                   if (Indexid == answerid) {
                       $(this).addClass("select");
                   }
               });
           }
           return false;
       });
       $(".timu li").click(function () {
           examId = $(this).parent().parent().attr("data-guankaId");
           if ($(this).hasClass("danxuanTi") || $(this).hasClass("panduanTi")) {
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
               updateAnswer(examId, answer);
           } else if ($(this).hasClass("duoxuanTi")) {
               answerd=[];
               $(this).parent().find(".duoxuanTi").each(function(){
                   if($(this).hasClass("select")){
                       answerd.push('"'+$(this).attr("data-selectanswer")+'"');
                   }
               });
               answerd=answerd.join();
               answerd = answerd == ""? "" : "["+answerd+"]";
               updateAnswer(examId, answerd)
           }
       });

       var datiStatus = false;
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
       $(".continueAnswer").click(function () {
           $(".noCompleteHint").css("display", "none");
       });

       $(".tijiao").click(function () {
           submitAnswer();
       });

       $(".confirm").click(function () {
           submitAnswer();
       });
       $(".checked").click(function(){
           $(".yidawan").css("display","none");
       });
       $(".dxt").each(function(){
           var arr=$(this).attr("data-da");
           if(arr!=null && arr!=""){
               arr=JSON.parse(arr);
               for(var i=0;i<arr.length;i++){
                   $(this).find("li").each(function(){
                       if($(this).index()==arr[i]){
                           $(this).addClass("select")
                       }
                   });
               };
           }
       });

       $(document).scroll(function () {
           scrollTiType();
       });
       $(".thirdLeiAnswer ol li").click(function () {
           var answerId = $(this).attr("data-answerid");
           $(document).unbind("scroll");
           $(".timu>li").each(function () {
               var dataIndexId = $(this).attr("data-indexid");
               var dataType = $(this).attr("data-type");
               var currentTop = $(this).offset().top;
               if (dataIndexId === answerId) {
                   $(".timuTypeBox li").each(function(){
                       var timutype=$(this).attr("data-timutype");
                       if(timutype==dataType){
                           scrollTiType();
                       }
                   });
                   $("body,html").animate({"scrollTop": currentTop - 59 + "px"},function(){
                       scrollTiType();
                       $(document).bind("scroll",function(){
                           scrollTiType();
                       });
                   });
                   return false;
               }
           });
       });
       $(".timuTypeBox li").click(function () {
           var timuType = $(this).attr("data-timuType");
           $(document).unbind("scroll");
           $(".timu>li").each(function () {
               var dataType = $(this).attr("data-Type");
               var currentTop = $(this).offset().top;
               if (dataType === timuType) {
                   $("body,html").animate({"scrollTop": currentTop- 60 + "px"},function(){
                   		scrollTiType();
                   		$(document).bind("scroll",function(){
				           scrollTiType();
				       });
                   });
                   return false;
               }
           });
       })
   });
    function scrollTiType(){
        var examHeight = $(".examBasicInfo").innerHeight() + $(".timuTypeBox").innerHeight();
        var answerCardLeft = $(".examContent").offset().left + $(".timuAndAnswer").innerWidth() + 20;
        if ($(document).scrollTop() > examHeight) {
            $(".timuTypeBox").addClass("examFloat");
            $(".answerCard").addClass("answerCardFloat").css("left", answerCardLeft + "px");
        } else {
            $(".timuTypeBox").removeClass("examFloat");
            $(".answerCard").removeClass("answerCardFloat");
        }
        if($(".timu li").hasClass("question0")){
            var danxuanTop=$(".question0").offset().top-80;
        }
        if($(".timu li").hasClass("question1")){
            var duoxuanTop=$(".question1").offset().top-80;
        }
        if($(".timu li").hasClass("question2")){
            var panduanTop=$(".question2").offset().top-80;
        }

        if($(document).scrollTop()>danxuanTop){
            $(".d0").addClass("select").siblings().removeClass("select");
        }
        if($(document).scrollTop()>duoxuanTop){
            $(".d1").addClass("select").siblings().removeClass("select");
        }
        if($(document).scrollTop()>panduanTop){
            $(".d2").addClass("select").siblings().removeClass("select");
        }
    };
    function updateAnswer(examId, answer) {
        RequestService("/barrier/updateQuestionById", "POST", {
            questionId: examId,
            answer: answer
        });
    }
    function submitAnswer(){
        var recordId = $(".timu li:first-child").attr("data-recordId");
        RequestService("/barrier/submitPaper", "POST", {
            recordId:recordId
        },function(data){
            if(data.resultObject.result==0){
                window.location.href="/web/html/censorshipResult.html?gqid="+gqid+"&examStatu=2";
            }else{
                window.location.href="/web/html/censorshipResult.html?gqid="+gqid+"&examStatu=1";
            }
        });
    }
});