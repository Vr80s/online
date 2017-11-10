/**
 * Created by admin on 2016/12/1.
 */
/**
 * Created by admin on 2016/12/1.
 */
$(function () {
    var examId = $.getUrlParam("examId");
    var studentId = $.getUrlParam("studentid");
    if (!examId) {
        examId = "433f549bf9474696b53bc3865f77f676"
    }
    if (!studentId) {
        studentId = "147bad5e44404a7cb86b3d5aa0ec8f48"
    }
    if (!status) {
        status = "4"
    }

    var examHeight = $(".examBasicInfo").innerHeight() + $(".timuTypeBox").innerHeight();
    var answerCardLeft = $(".examContent").offset().left + $(".timuAndAnswer").innerWidth() + 12;
    $(document).scroll(function () {
        if ($(document).scrollTop() > examHeight + 330) {
            $(".timuTypeBox").addClass("examFloat");
            $(".answerCard").addClass("answerCardFloat").css("left", answerCardLeft + "px")
        } else {
            $(".timuTypeBox").removeClass("examFloat");
            $(".answerCard").removeClass("answerCardFloat");
        }
    });


	var base = new Config().base;

    var examScore =
        '<ul>' +
        '<li>单选题</li>' +
            '{{if examScore!=""&&examScore!=null}}'+
            '<li class="correntAnswer">{{examScore.correctNums}}</li>' +
            '<li class="errorAnser">{{examScore.errorNums}}</li>' +
            '<li>{{examScore.unansweredNums}}</li>' +
            '<li class="totalDeFen">{{examScore.score}}</li>' +
            '{{else}}'+
            '<li class="correntAnswer">--</li>' +
            '<li>--</li>' +
            '<li>--</li>' +
            '<li>--</li>' +
            '{{/if}}'+
        '</ul>';
    var examScore1 =
        '<ul>' +
        '<li>多选题</li>' +
        '{{if examScore!=""&&examScore!=null}}'+
        '<li class="correntAnswer">{{examScore.correctNums}}</li>' +
        '<li class="errorAnser">{{examScore.errorNums}}</li>' +
        '<li>{{examScore.unansweredNums}}</li>' +
        '<li class="totalDeFen">{{examScore.score}}</li>' +
        '{{else}}'+
        '<li class="correntAnswer">--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        '{{/if}}'+
        '</ul>';
    var examScore2 =
        '<ul>' +
        '<li>判断题</li>' +
        '{{if examScore!=""&&examScore!=null}}'+
        '<li class="correntAnswer">{{examScore.correctNums}}</li>' +
        '<li class="errorAnser">{{examScore.errorNums}}</li>' +
        '<li>{{examScore.unansweredNums}}</li>' +
        '<li class="totalDeFen">{{examScore.score}}</li>' +
        '{{else}}'+
        '<li class="correntAnswer">--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        '{{/if}}'+
        '</ul>';
    var examScore3 =
        '<ul>' +
        '<li>填空题</li>' +
        '{{if examScore!=""&&examScore!=null}}'+
        //'<li class="correntAnswer">{{examScore.correctNums}}</li>' +
        '<li class="correntAnswer">-</li>' +
        //'<li class="errorAnser">{{examScore.errorNums}}</li>' +
        '<li class="errorAnser">-</li>' +
        '<li>{{examScore.unansweredNums}}</li>' +
        '<li class="totalDeFen">{{examScore.score}}</li>' +
        '{{else}}'+
        '<li class="correntAnswer">--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        '{{/if}}'+
        '</ul>';
    var examScore4 =
        '<ul>' +
        '<li>简答题</li>' +
        '{{if examScore!=""&&examScore!=null}}'+
        //'<li class="correntAnswer">{{examScore.correctNums}}</li>' +
        '<li class="correntAnswer">-</li>' +
        //'<li>{{examScore.errorNums}}</li>' +
        '<li>-</li>' +
        '<li>{{examScore.unansweredNums}}</li>' +
        '<li>{{examScore.score}}</li>' +
        '{{else}}'+
        '<li class="correntAnswer">--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        '{{/if}}'+
        '</ul>';


    var examInfo = '<div class="examName">{{items.examName}}</div>' +
            '<span class="tiCount">题目数量：{{items.questionCount}}道</span>' +
            '<span class="tijiaoTime">交卷时间：{{items.endTime}}</span>'
        , questionList = '{{each quesList}}' +
            '{{if $value.question_type==0}}' +
                ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-questionId="{{$value.exam_question_id}}" data-studenId="{{$value.student_id}}" data-examId={{$value.exam_id}}>' +
                     '{{$index+1}}、{{#$value.question_content}}({{$value.score}}分)' +
                            '<ol class="ExamAnswer">' +
                            '{{each $value.optionsList as $val i}}' +
                            '{{if $value.is_answer==true && $value.answer==i && $value.is_correct==true}}' +
                            '<li class="danxuanTi errorAns" data-selectAnswer="{{i}}">' +
                            '<span>{{$val}}</span>' +
                            '<i class="iconfont icon-zhengque"></i>' +
                            '{{else}}' +
                            '{{if  $value.is_correct==false && $value.answer==i && $value.answer!=""}}' +
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
                '<p><span class="cankaoAnswer">参考答案：</span><span>{{#$value.queAnswer}}</span></p>' +
                '<p><span class="answerShouMing">答案说明：</span><span>{{#$value.solution}}</span></p>' +
                '</li>' +
            '{{/if}}' +
            '{{if $value.question_type==1}}' +
            ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-questionId="{{$value.exam_question_id}}" data-questionId="{{$value.exam_question_id}}" data-studenId="{{$value.student_id}}" data-examId={{$value.exam_id}}>' +
            '{{$index+1}}、{{#$value.question_content}}' +
            '<ol class="ExamAnswer dxt" data-da="{{$value.answer}}" data-isCorrect="{{$value.is_correct}}" data-isAnswer="{{$value.is_answer}}"  data-answer="{{$value.queAnswer}}">' +
            '{{each $value.optionsList as $val i}}' +
            '{{if $value.is_answer==true && $value.answer==i && $value.is_correct==true}}' +
            '<li class="duoxuanTi errorAns" data-r="" id="li{{i}}" data-selectAnswer="{{i}}">' +
            '<span>{{$val}}</span>' +
            '<i class="iconfont icon-zhengque"></i>' +
            '{{else}}' +
            '{{if  $value.is_correct==false && $value.answer==i && $value.answer!=""}}' +
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
            '<p><span class="cankaoAnswer">参考答案：</span><span>{{#$value.queAnswer}}</span></p>' +
            '<p><span class="answerShouMing">答案说明：</span><span>{{#$value.solution}}</span></p>' +
            '</li>' +
            '{{/if}}' +
                '{{if $value.question_type==2}}' +
                ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-questionId="{{$value.exam_question_id}}" data-questionId="{{$value.exam_question_id}}" data-studenId="{{$value.student_id}}" data-examId={{$value.exam_id}}>' +
                '{{$index+1}}、{{#$value.question_content}}({{$value.score}}分)' +
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
                            '{{/if}}' +
                         '{{/if}}'+

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
                        '{{/if}}' +
                   '{{/if}}' +
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

                '<p><span class="cankaoAnswer">参考答案：</span><span>{{#$value.queAnswer}}</span></p>' +
                '<p><span class="answerShouMing">答案说明：</span><span>{{#$value.solution}}</span></p>' +
                '</li>' +
                '{{/if}}' +
            '{{if $value.question_type==3}}' +
            ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-questionId="{{$value.exam_question_id}}" data-questionId="{{$value.exam_question_id}}" data-studenId="{{$value.student_id}}" data-examId={{$value.exam_id}}>' +
            '{{$index+1}}、{{#$value.question_content}}' +
            '<div class="tiankongTi">' +
            //'<span>请写出输出结果：</span>' +
            //'{{if $value.is_answer==true}}' +
            //'<input type="text" class="tiankongTiInput" value="{{$value.answer}}"/>' +
            //'{{else}}' +
            //'<input type="text" class="tiankongTiInput"/>' +
            //'{{/if}}' +
            ' </div>' +
            '<p><span class="cankaoAnswer">参考答案：</span><span>{{#$value.queAnswer}}</span></p>' +
            '<p><span class="answerShouMing">答案说明：</span><span>{{#$value.solution}}</span></p>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type==4}}' +
            ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-questionId="{{$value.exam_question_id}}" data-questionId="{{$value.exam_question_id}}" data-studenId="{{$value.student_id}}" data-examId={{$value.exam_id}}>' +
            '{{$index+1}}、{{#$value.question_content}}' +
            '<div class="examRichText">' +
            '<textarea class="editor" autofocus maxlength="7000" name="content"">{{$value.answer}}</textarea>' +
            ' </div>' +
            //'<p><span class="cankaoAnswer">我的答案：</span><span>{{$value.answer}}</span></p>' +
            '<p><span class="cankaoAnswer">参考答案：</span><span>{{#$value.queAnswer}}</span></p>' +
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
    answerCardD =
        '<li class="radioAnswer">' +
        '<span>填空题</span>' +
        '<ol class="clearfix">' +
        '{{each answerCard}}' +
        '{{if $value.question_type==3}}' +
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
    answerCardE =
        '<li class="radioAnswer">' +
        '<span>简答题</span>' +
        '<ol class="clearfix">' +
        '{{each answerCard}}' +
        '{{if $value.question_type==4}}' +
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


    RequestService("/api/callThirdPost", "POST", {
        thirdUrl: base + "/bxg_anon/my/exam/findStudentExam",
        examId: examId,
        studentId: studentId
    }, function (result) {
        //console.log(result+"===");
        var data = {
            examId: result.resultObject.stuExamList[0].exam_id,
            studentId: result.resultObject.stuExamList[0].student_id
        };
        if(result.resultObject.examData.status==4){
            $(".timuTypeAndSocre").css("display","block");
            $(".timuTotal").append(template.compile(examScore)({
                examScore:result.resultObject.statList.danxuan
            }));
            $(".timuTotal").append(template.compile(examScore1)({
                examScore:result.resultObject.statList.duoxuan
            }));
            $(".timuTotal").append(template.compile(examScore2)({
                examScore:result.resultObject.statList.panduan
            }));
            $(".timuTotal").append(template.compile(examScore3)({
                examScore:result.resultObject.statList.tiaokong
            }));

            $(".timuTotal").append(template.compile(examScore4)({
                examScore:result.resultObject.statList.jiandas
            }));

        }else{
            $(".timuTypeAndSocre").css("display","none");
        }

        $(".examBasicInfoTop").html(template.compile(examInfo)({
            items: result.resultObject.examData
        }));
        $(".timu").html(template.compile(questionList)({
            quesList: result.resultObject.stuExamList
        }));
        $(".tiankongtiInput").attr("readonly","true");
        $(".editor").attr("readonly","true");
        $(".dxt").each(function(){
            var _this=$(this).find("li");
            var arr=_this.parent().attr("data-da");
            var isCorrect=_this.parent().attr("data-isCorrect");
            var isAnswer=_this.parent().attr("data-isAnswer");
            var Answer=$(this).attr("data-answer");
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
        var questType=[];
        $.each(result.resultObject.stuExamList,function(i,data){
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
                    answerCard: result.resultObject.stuExamList
                }));

            }else if(a==1){
                $(".timuTypeBox").append('<li data-timuType="1">多选题</li>');
                $(".thirdLeiAnswer").append(template.compile(answerCardB)({
                    answerCard: result.resultObject.stuExamList
                }));

            }else if(a==2){
                $(".timuTypeBox").append('<li data-timuType="2">判断题</li>');
                $(".thirdLeiAnswer").append(template.compile(answerCardC)({
                    answerCard: result.resultObject.stuExamList
                }));

            }else if(a==3){
                $(".timuTypeBox").append('<li data-timuType="3">填空题</li>');
                $(".thirdLeiAnswer").append(template.compile(answerCardD)({
                    answerCard: result.resultObject.stuExamList
                }));


            }else if(a==4){
                $(".timuTypeBox").append('<li data-timuType="4">简答题</li>');
                $(".thirdLeiAnswer").append(template.compile(answerCardE)({
                    answerCard: result.resultObject.stuExamList
                }));


            }
        });
        $(".defen").html(result.resultObject.examData.score + '<span>分</span>');
        $(".totalFen").text("满分" + result.resultObject.examData.total_score);
        var denfen = result.resultObject.examData.score;
        var total = result.resultObject.examData.total_score;
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

                console.log(dataIndexId+"2222");
            });

        });
        $("body,html").animate({"scrollTop": "0px"});
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
               //console.log(dataType+"1111");
            });
    //console.log(timuType);
        })
    });
});
//能获取到id