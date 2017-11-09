/**
 * Created by admin on 2016/12/16.
 */
/**
 * Created by admin on 2016/12/1.
 */
/**
 * Created by admin on 2016/12/1.
 */
/**
 * Created by admin on 2016/12/1.
 */
$(function () {
    var examScore =
        '<ul>' +
        '<li>单选题</li>' +
        '{{if examScore!=""&&examScore!=null}}'+
        '<li class="correntAnswer">{{examScore.correctNums}}</li>' +
        '<li class="errorAnser">{{examScore.errorNums}}</li>' +
        '<li>{{examScore.unansweredNums}}</li>' +
        //'<li class="totalDeFen">{{examScore.score}}</li>' +
        '{{else}}'+
        '<li class="correntAnswer">--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        //'<li>--</li>' +
        '{{/if}}'+
        '</ul>';
    var examScore1 =
        '<ul>' +
        '<li>多选题</li>' +
        '{{if examScore!=""&&examScore!=null}}'+
        '<li class="correntAnswer">{{examScore.correctNums}}</li>' +
        '<li class="errorAnser">{{examScore.errorNums}}</li>' +
        '<li>{{examScore.unansweredNums}}</li>' +
        //'<li class="totalDeFen">{{examScore.score}}</li>' +
        '{{else}}'+
        '<li class="correntAnswer">--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        //'<li>--</li>' +
        '{{/if}}'+
        '</ul>';
    var examScore2 =
        '<ul>' +
        '<li>判断题</li>' +
        '{{if examScore!=""&&examScore!=null}}'+
        '<li class="correntAnswer">{{examScore.correctNums}}</li>' +
        '<li class="errorAnser">{{examScore.errorNums}}</li>' +
        '<li>{{examScore.unansweredNums}}</li>' +
        //'<li class="totalDeFen">{{examScore.score}}</li>' +
        '{{else}}'+
        '<li class="correntAnswer">--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        //'<li>--</li>' +
        '{{/if}}'+
        '</ul>';



    template.helper('isAnswer',function(a){
        var num;
        if(a==true){
            return num=0;
        }else{
            return num=1;
        }
    });
    var examInfo = '<div class="examName">{{items.homework_name}}</div>' +
            '<span class="tiCount">题目数量：{{items.total_questions}}道</span>' +
            '<span class="tijiaoTime">交卷时间：{{items.end_time}}</span>'
        , questionList = '{{each quesList}}' +
            '{{if $value.question_type==0}}' +
            ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-homework_question_id="{{$value.homework_question_id}}" data-studenId="{{$value.student_id}}" data-homework_id={{$value.homework_id}}>' +
            '{{$index+1}}、{{#$value.question_content}}' +
                    '<ol class="ExamAnswer">' +
                    '{{each $value.optionsList as $val i}}' +
                    '{{if $value.is_answer==true  &&  $value.answer==i && $value.is_correct==true}}' +
                         '<li class="danxuanTi errorAns" data-selectAnswer="{{i}}">' +
                             '<span>{{$val}}</span>' +
                        '<i class="iconfont icon-zhengque"></i>' +
                    '{{else}}' +
                        '{{if $value.answer==i && $value.answer!=""}}' +
                        //'{{if $value.answer==i }}' +
                             '<li class="danxuanTi correntAns" data-selectAnswer="{{i}}">' +
                                 '<span>{{$val}}</span>' +
                            '<i class="iconfont icon-cuowu"></i>' +
                        '{{else}}' +
                            '<li class="danxuanTi" data-selectAnswer="{{i}}">' +
                                 '<span>{{$val}}</span>' +
                        '{{/if}}' +
                    '{{/if}}' +
                    '<img src="{{$value.optionsPictureList[i]}}"/>' +
                    '{{/each}}' +
                    '</li>' +
                    '</ol>' +
            '<p><span class="cankaoAnswer">参考答案：</span><span>{{$value.queAnswer}}</span></p>' +
            '<p><span class="answerShouMing">答案说明：</span><span>{{#$value.solution}}</span></p>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type==1}}' +
            ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-homework_question_id="{{$value.homework_question_id}}" data-studenId="{{$value.student_id}}" data-homework_id={{$value.homework_id}}>' +
            '{{$index+1}}、{{#$value.question_content}}' +
                    '<ol class="ExamAnswer dxt" data-da="{{$value.answer}}" data-isCorrect="{{isAnswer($value.is_correct)}}" data-isAnswer="{{isAnswer($value.is_answer)}}" data-answer="{{$value.queAnswer}}">' +
                    '{{each $value.optionsList as $val i}}' +
                    '{{if $value.is_answer==true && $value.answer==i && $value.is_correct==false}}' +
                    '<li class="duoxuanTi errorAns" data-r="" id="li{{i}}" data-selectAnswer="{{i}}">' +
                    '<span>{{$val}}</span>' +
                    '<i class="iconfont icon-zhengque"></i>' +
                    '{{else}}' +
                    '{{if  $value.is_correct==true && $value.answer==i}}' +
                    '<li class="duoxuanTi correntAns" data-r="" id="li{{i}}" data-selectAnswer="{{i}}">' +
                    '<span>{{$val}}</span>' +
                    '<i class="iconfont icon-cuowu"></i>' +
                    '{{else}}' +
                    '<li class="duoxuanTi" data-r="" id="li{{i}}" data-selectAnswer="{{i}}">' +
                    '<span>{{$val}}</span>' +
                    '{{/if}}' +
                    '{{/if}}' +
                    '<img src="{{$value.optionsPictureList[i]}}"/>' +
                    '{{/each}}' +
                    '</li>' +
                    '</ol>' +
            '<p><span class="cankaoAnswer">参考答案：</span><span>{{$value.queAnswer}}</span></p>' +
            '<p><span class="answerShouMing">答案说明：</span><span>{{#$value.solution}}</span></p>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type==2}}' +
                ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-homework_id="{{$value.homework_id}}" data-homework_question_id="{{$value.homework_question_id}}" data-studenId="{{$value.student_id}}" data-examId={{$value.exam_id}}>' +
                '{{$index+1}}、{{#$value.question_content}}' +
                        '<ol class="ExamAnswer">' +
                        '{{if $value.answer=="对"}}' +
                        '{{if $value.answer==$value.queAnswer}}' +
                        '<li class="panduanTi select" data-selectAnswer="0">' +
                        '<div><em class="panduanBtn"></em></div>' +
                        '<span>√</span>' +
                        '<i class="iconfont icon-zhengque errorAns"></i>' +
                        '</li>' +
                        '<li class="panduanTi" data-selectAnswer="1">' +
                        '<div><em class="panduanBtn"></em></div>' +
                        '<span>×</span>' +
                        '</li>' +
                        '{{else}}'+
                        '<li class="panduanTi select" data-selectAnswer="0">' +
                        '<div><em class="panduanBtn"></em></div>' +
                        '<span>√</span>' +
                        '<i class="iconfont icon-cuowu correntAns"></i>' +
                        '</li>' +
                        '<li class="panduanTi" data-selectAnswer="1">' +
                        '<div><em class="panduanBtn"></em></div>' +
                        '<span>×</span>' +
                        '</li>' +
                         '{{/if}}{{/if}}'+
                        '{{if $value.answer=="错"}}' +
                        '{{if $value.answer==$value.queAnswer}}' +
                        '<li class="panduanTi select" data-selectAnswer="0">' +
                        '<div><em class="panduanBtn"></em></div>' +
                        '<span>√</span>' +
                        '</li>' +
                        '<li class="panduanTi" data-selectAnswer="1">' +
                        '<div><em class="panduanBtn"></em></div>' +
                        '<span>×</span>' +
                        '<i class="iconfont icon-zhengque errorAns"></i>' +
                        '</li>' +
                        '{{else}}'+
                        '<li class="panduanTi select" data-selectAnswer="0">' +
                        '<div><em class="panduanBtn"></em></div>' +
                        '<span>√</span>' +
                        '</li>' +
                        '<li class="panduanTi" data-selectAnswer="1">' +
                        '<div><em class="panduanBtn"></em></div>' +
                        '<span>×</span>' +
                        '<i class="iconfont icon-cuowu correntAns"></i>' +
                        '</li>' +
                        '{{/if}}{{/if}}' +
                        '{{if $value.is_answer==false}}' +
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

                    '<p><span class="cankaoAnswer">参考答案：</span><span>{{$value.queAnswer}}</span></p>' +
                    '<p><span class="answerShouMing">答案说明：</span><span>{{#$value.solution}}</span></p>' +
                '</li>' +
            '{{/if}}' +
            '{{/each}}',
        answerCardA =
            ' <li class="radioAnswer">' +
            '<span>单选题</span>' +
            '<ol class="clearfix">' +
            '{{each answerCard}}' +
            '{{if $value.question_type==0}}' +
            '{{if $value.is_answer==false}}' +
            ' <li class="noAnswer" data-answerId="{{$index}}">{{$index+1}}</li>' +
            '{{else}}' +
            '{{if  $value.is_correct==true}}' +
            ' <li class="corrent" data-answerId="{{$index}}">{{$index+1}}</li>' +
            '{{else}}' +
            ' <li class="error" data-answerId="{{$index}}">{{$index+1}}</li>' +
            '{{/if}}' +
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
        '{{if $value.is_answer==false}}' +
        ' <li class="noAnswer" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        '{{if  $value.is_correct==true}}' +
        ' <li class="corrent" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        ' <li class="error" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{/if}}' +
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
        '{{if $value.is_answer==false}}' +
        ' <li class="noAnswer" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        '{{if  $value.is_correct==true}}' +
        ' <li class="corrent" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        ' <li class="error" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{/if}}' +
        '{{/if}}' +
        '{{/if}}' +
        '{{/each}}' +
        '</ol>' +
        '</li>';


    timuType = '{{each timu}}' +
        '{{if $value.question_type==0}}' +
        '<li class="select" data-timuType="{{$index}}">单选题</li>' +
        '{{/if}}' +
        '{{if $value.question_type==1}}' +
        ' <li data-timuType="1">多选题</li>' +
        '{{/if}}' +
        '{{if $value.question_type==2}}' +
        ' <li data-timuType="1">判断题</li>' +
        '{{/if}}' +
        '{{/each}}';

    var examId = $.getUrlParam("homeworkId");
    var studentId = $.getUrlParam("studentid");
    var status = $.getUrlParam("status");
    if (!examId) {
        examId = "a139c23827764f2daee34d3576e429ce"
    }
    if (!studentId) {
        studentId = "acad6ea0a70742a1accd6b1bde8a2354"
    }
    if (!status) {
        status = "4"
    }

    var examHeight = $(".examBasicInfo").innerHeight() + $(".timuTypeBox").innerHeight();
    var answerCardLeft = $(".examContent").offset().left + $(".timuAndAnswer").innerWidth() + 12;
    $(document).scroll(function () {
        if ($(document).scrollTop() > examHeight + 81) {
            $(".timuTypeBox").addClass("examFloat");
            $(".answerCard").addClass("answerCardFloat").css("left", answerCardLeft + "px")
        } else {
            $(".timuTypeBox").removeClass("examFloat");
            $(".answerCard").removeClass("answerCardFloat");
        }
    });

	var base = new Config().base;

    RequestService("/api/callThirdPost", "POST", {
        thirdUrl: base + "/bxg_anon/my/homework/findStudentHomework",
        homeworkId: examId,
        studentId: studentId,
        status: status
    }, function (result) {
        var data = {
            homeworkId: result.resultObject.homeworkList[0].homework_id,
            studentId: result.resultObject.homeworkList[0].student_id
        };
        /*if(answer==""){
            answer== null;
            console.log(123)
        }*/

        $(".timuTotal").append(template.compile(examScore)({
            examScore:result.resultObject.statList.danxuan
        }));


        $(".timuTotal").append(template.compile(examScore1)({
            examScore:result.resultObject.statList.duoxuan
        }));

        $(".timuTotal").append(template.compile(examScore2)({
            examScore:result.resultObject.statList.panduan
        }));
        var questType=[];
        $.each(result.resultObject.homeworkList,function(i,data){
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
                $(".thirdLeiAnswer").html("").append(template.compile(answerCardA)({
                    answerCard: result.resultObject.homeworkList
                }));
            }else if(a==1){
                $(".timuTypeBox").append('<li data-timuType="1">多选题</li>');
                $(".thirdLeiAnswer").append(template.compile(answerCardB)({
                    answerCard: result.resultObject.homeworkList
                }));
            }else if(a==2){
                $(".timuTypeBox").append('<li data-timuType="2">判断题</li>');
                $(".thirdLeiAnswer").append(template.compile(answerCardC)({
                    answerCard: result.resultObject.homeworkList
                }));
            }
        });

        $(".examBasicInfoTop").html(template.compile(examInfo)({
            items: result.resultObject.homeworkData
        }));
        $(".timu").html(template.compile(questionList)({
            quesList: result.resultObject.homeworkList
        }));

        $(".dxt").each(function(){
            var _this=$(this).find("li");
            var arr=_this.parent().attr("data-da");
            var isCorrect=_this.parent().attr("data-isCorrect");
            var isAnswer=_this.parent().attr("data-isAnswer");
            var Answer=_this.parent().attr("data-answer");
            var ansCheck=Answer.split(",");
            var aC=[];
            for(var i=0;i<ansCheck.length;i++){
                if(ansCheck[i]=="A"){
                    aC.push("0");
                }else if(ansCheck[i]=="B"){
                    aC.push("1");
                }else if(ansCheck[i]=="C"){
                    aC.push("2");
                }else if(ansCheck[i]=="D"){
                    aC.push("3");
                }
            }
            arr=arr.substring(1,arr.length-1);
            arr=arr.replace(/\"/g, "");
            arr=arr.split(",");

            for(i=0;i<aC.length;i++){
                for(var j=0;j<arr.length;j++){
                    if(aC[i] == arr[j]){
                        $(this).find('#li'+arr[j]+'').addClass('errorAns').removeClass('correntAns');
                        $(this).find('#li'+arr[j]+'').attr("data-r",'1');
                    }else {
                        $(this).find('#li'+arr[j]+'').addClass('correntAns').removeClass('errorAns');
                    }
                }
            };

            for(var j=0;j<arr.length;j++) {
                if (aC.indexOf(arr[j]) != -1) {
                    $(this).find('#li' + arr[j] + '').addClass('errorAns');
                    $(this).find('#li' + arr[j] + '').append('<i class="iconfont icon-zhengque errorAns"></i>');
                } else {
                    $(this).find('#li' + arr[j] + '').addClass('correntAns');
                    $(this).find('#li' + arr[j] + '').append('<i class="iconfont icon-cuowu correntAns"></i>');
                }

            }

        });
        var arropud = result.resultObject.homeworkData.correct_percent.split(".");
        if (parseInt(arropud[1]) < 0) {
            $(".defen").text(result.resultObject.homeworkData.correct_percent+"%");
        } else {
            $(".defen").text(arropud[0]+"%");
        }
        var denfen = result.resultObject.homeworkData.correntCount;
        var total = result.resultObject.homeworkData.total_questions;
        var sub = total - denfen;
        ccjindu(denfen, sub);

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

        $(".timuTypeBox li").click(function () {
            var timuType = $(this).attr("data-timuType");
            $(this).addClass("select").siblings().removeClass("select");
            $(".timu>li").each(function () {
                var dataType = $(this).attr("data-Type");
                var currentTop = $(this).offset().top;
                if (dataType === timuType) {
                    $("body,html").animate({"scrollTop": currentTop - 60 + "px"});
                    return false;
                }
            });
        })
    });
});
