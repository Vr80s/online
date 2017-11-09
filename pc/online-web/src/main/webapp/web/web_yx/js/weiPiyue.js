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
        if ($(document).scrollTop() > examHeight + 81) {
            $(".timuTypeBox").addClass("examFloat");
            $(".answerCard").addClass("answerCardFloat").css("left", answerCardLeft + "px")
        } else {
            $(".timuTypeBox").removeClass("examFloat");
            $(".answerCard").removeClass("answerCardFloat");
        }
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


    var examInfo = '<div class="examName">{{items.examName}}</div>' +
            '<span class="tiCount">题目数量：{{items.questionCount}}道</span>' +
            '<span class="tijiaoTime">交卷时间：{{items.endTime}}</span>'
        , questionList = '{{each quesList}}' +
            '{{if $value.question_type==0}}' +
            ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-questionId="{{$value.exam_question_id}}" data-studenId="{{$value.student_id}}" data-examId={{$value.exam_id}}>' +
            '{{$index+1}}、{{#$value.question_content}}({{$value.score}}分)' +
            '<ol class="ExamAnswer">' +
            '{{each $value.optionsList as $val i}}' +
            '{{if $value.is_answer==true && $value.answer==i}}' +
            '<li class="danxuanTi select" data-selectAnswer="{{i}}">' +
            '{{else}}' +
            '<li class="danxuanTi" data-selectAnswer="{{i}}">' +
            '{{/if}}' +
            '<div><em class="radioBtn"></em></div>' +
            '<span>{{$val}}</span>' +
            '<img src="{{$value.optionsPictureList[i]}}"/>' +
            '{{/each}}' +
            '</li>' +
            '</ol>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type==1}}' +
                ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-questionId="{{$value.exam_question_id}}" data-questionId="{{$value.exam_question_id}}" data-studenId="{{$value.student_id}}" data-examId={{$value.exam_id}}>' +
                '{{$index+1}}、{{#$value.question_content}}({{$value.score}}分)' +
                    '<ol class="ExamAnswer dxt" data-da="{{$value.answer}}" data-isAnser="{{isAnswer($value.is_answer)}}">' +
                        '{{each $value.optionsList as $val i}}' +
                            '{{if $value.is_answer==true}}' +
                                    '<li class="duoxuanTi" data-selectAnswer="{{i}}">' +
                            '{{else}}' +
                                     '<li class="duoxuanTi" data-selectAnswer="{{i}}">' +
                            '{{/if}}' +
                                 '<div><em class="checkboxBtn"><i class="iconfont icon-xuanzhong"></i></em></div>' +
                                 '<span>{{$val}}</span>' +
                                 '<img src="{{$value.optionsPictureList[i]}}"/>' +
                        '</li>' +
                        '{{/each}}' +
                    '</ol>' +
                '</li>' +
            '{{/if}}' +
                    '{{if $value.question_type==2}}' +
                        ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-questionId="{{$value.exam_question_id}}" data-questionId="{{$value.exam_question_id}}" data-studenId="{{$value.student_id}}" data-examId={{$value.exam_id}}>' +
                        '{{$index+1}}、{{#$value.question_content}}({{$value.score}}分)' +
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
                                '{{/if}}' +
                            '</ol>' +
                        '</li>' +

                    '{{/if}}' +
            '{{if $value.question_type==3}}' +
            ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-questionId="{{$value.exam_question_id}}" data-questionId="{{$value.exam_question_id}}" data-studenId="{{$value.student_id}}" data-examId={{$value.exam_id}}>' +
            '{{$index+1}}、{{#$value.question_content}}({{$value.score}}分)' +
            '<div class="tiankongTi">' +
            //'<span>请写出输出结果：</span>' +
            //'{{if $value.is_answer==true}}' +
            //'<input type="text" readonly="readonly" class="tiankongTiInput" value="{{$value.answer}}"/>' +
            //'{{else}}' +
           // '{{if $value.is_answer==true}}' +
           // '<input type="text" readonly="readonly" class="tiankongTiInput"/>' +
           // '{{/if}}' +
            ' </div>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type==4}}' +
            ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-questionId="{{$value.exam_question_id}}" data-questionId="{{$value.exam_question_id}}" data-studenId="{{$value.student_id}}" data-examId={{$value.exam_id}}>' +
            '{{$index+1}}、{{#$value.question_content}}({{$value.score}}分)' +
            '<div class="examRichText">' +
            '<textarea class="editor" autofocus maxlength="7000" name="content""></textarea>' +
            ' </div>' +
            '</li>' +
            '{{/if}}' +
            '{{/each}}',
        answerCardA =
            ' <li class="radioAnswer">' +
            '<span>单选题</span>' +
            '<ol class="clearfix">' +
            '{{each answerCard}}' +
            '{{if $value.question_type==0}}' +
            '{{if $value.is_answer==true}}' +
            ' <li class="corrent" data-answerId="{{$index}}">{{$index+1}}</li>' +
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
        '<li class="corrent" data-answerId="{{$index}}">{{$index+1}}</li>' +
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
        '<li class="corrent" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        ' <li data-answerId="{{$index}}">{{$index+1}}</li>' +
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
        '{{if $value.is_answer==true}}' +
        ' <li class=" corrent" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        ' <li class="noAnswer" data-answerId="{{$index}}">{{$index+1}}</li>' +
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
        '{{if $value.is_answer==true}}' +
        ' <li class="corrent" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        ' <li class="noAnswer" data-answerId="{{$index}}">{{$index+1}}</li>' +
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
        $(".examBasicInfoTop").html(template.compile(examInfo)({
            items: result.resultObject.examData
        }));
        $(".timu").html(template.compile(questionList)({
            quesList: result.resultObject.stuExamList
        }));
        $(".tiankongtiInput").attr("readonly","true");
        $(".editor").attr("readonly","true");
        /*if($("textarea").length!=0){
            //富文本编辑器
            //var sm_toolbar = ['italic', 'bold', 'underline', 'strikethrough', '|', 'blockquote', 'code', 'link', 'image'];
            var sm_toolbar = [];
            Simditor.locale = 'zh-CN'
            var editor = new Simditor({
                textarea: $('.editor'),
                toolbar: sm_toolbar,
                defaultImage: 'g',
                pasteImage: false,
                toolbarHidden: false,
                toolbarFloat: false,
                placeholder: '',
                upload: {
                    url: bath + "/online/util/upload4Simditor",
                    params: null,
                    fileKey: 'attachment',
                    connectionCount: 3,
                    leaveConfirm: '正在上传文件，如果离开上传会自动取消'
                },
                success: function (data) {

                }
            });
            //console.log(sm_toolbar);
        }
        $(".timu > li").find(".simditor-body").attr("contenteditable","false");*/
        var _0li = $(".timu > li").length;
        console.log("wwwww"+_0li)

        var resultA= result.resultObject.stuExamList;
        for(var j=0;j<_0li;j++){
            if(resultA[j].answer !== "" && resultA[j].answer !== null){
                console.log(resultA[j].answer+'ooooo');
               $(".timu > li").eq(j).find(".editor").text(resultA[j].answer);

            }

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
            console.log(a);
            if(a==0){
                $(".timuTypeBox").append('<li class="select" data-timuType="0">单选题</li>');
                $(".thirdLeiAnswer").html(template.compile(answerCardA)({
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
                }))
            }else if(a==4){
                $(".timuTypeBox").append('<li data-timuType="4">简答题</li>');
                $(".thirdLeiAnswer").append(template.compile(answerCardE)({
                    answerCard: result.resultObject.stuExamList
                }));
            }
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
            });
        })
    });
});
