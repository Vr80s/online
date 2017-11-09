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
        var examId = arr[0];
        var studentId = arr[1];
    };
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

    //倒计时
    function getRTime(date) {
        date = parseInt(date);
        var intDiff = date * 60;
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


    template.helper('isAnswer',function(a){
        var num;
        if(a==true){
            return num=0;
        }else{
            return num=1;
        }
    })


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
                /*'<div class="tiankongTi">' +
                 '<span>请写出输出结果：</span>' +
                 '{{if $value.is_answer==true}}' +
                 '<input type="text" class="tiankongTiInput" value="{{$value.answer}}"/>' +
                 '{{else}}' +
                 '<input type="text" class="tiankongTiInput"/>' +
                 '{{/if}}' +
                 ' </div>' +*/
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type==4}}' +
            ' <li data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-questionId="{{$value.exam_question_id}}" data-questionId="{{$value.exam_question_id}}" data-studenId="{{$value.student_id}}" data-examId={{$value.exam_id}}>' +
            '{{$index+1}}、{{#$value.question_content}}({{$value.score}}分)' +
            '<div class="examRichText">' +
                //'<textarea class="editor" autofocus maxlength="7000" name="content""></textarea>' +
            ' <textarea id="editor" class="editor" autocomplete="off" placeholder=""  maxlength="7000" name="content""></textarea>'+
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
        thirdUrl: base + "/bxg_anon/my/exam/startExam",
        examId: examId,
        studentId: studentId
    }, function (result) {
        var data = {
            examId: result.resultObject.questionList[0].exam_id,
            studentId: result.resultObject.questionList[0].student_id
        };

        if(result.resultObject.examData.resTime <= 0){
            alert("考试时间已到");
            window.location.href="/web/html/myStudyCenter.html?state"
        }
        $(".examBasicInfoTop").html(template.compile(examInfo)({
            items: result.resultObject.examData
        }));
        $(".timu").html(template.compile(questionList)({
            quesList: result.resultObject.questionList
        }));

       ///* if($("textarea").length!=0){
       //     //富文本编辑器
       //      var sm_toolbar = ['italic', 'bold', 'underline', 'strikethrough', '|', 'blockquote', 'code', 'link', 'image'];
       //     Simditor.locale = 'zh-CN';
       //     var editor = new Simditor({
       //         textarea: $('.editor'),
       //         toolbar: sm_toolbar,
       //         defaultImage: 'g',
       //         pasteImage: true,
       //         toolbarHidden: false,
       //         toolbarFloat: false,
       //         placeholder: '',
       //         upload: {
       //             url: bath + "/online/util/upload4Simditor",
       //             params: null,
       //             fileKey: 'attachment',
       //             connectionCount: 3,
       //             leaveConfirm: '正在上传文件，如果离开上传会自动取消'
       //         },
       //         success: function (data) {
       //         }
       //     });
       // }*/

        $(".dxt").each(function(){
            var arr=$(this).attr("data-da");
            var isAns=$(this).attr("data-isAnser");
            if(isAns==0){
                arr=arr.substring(1,arr.length-1);
                arr=arr.replace(/\"/g, "");
                arr=arr.split(",");
                console.log(arr);
                for(i=0;i<arr.length;i++){
                    $(this).find("li").each(function(){
                        if($(this).index()==arr[i]){
                            $(this).addClass("select")
                        }
                    });
                }
            }
        });
        var examTotalTime = result.resultObject.examData.resTime;
        getRTime(examTotalTime);
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
            //console.log(a);
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
            }else if(a==3){
                $(".timuTypeBox").append('<li data-timuType="3">填空题</li>');
                $(".thirdLeiAnswer").append(template.compile(answerCardD)({
                    answerCard: result.resultObject.questionList
                }));
            }else if(a==4){
                $(".timuTypeBox").append('<li data-timuType="4">简答题</li>');
                $(".thirdLeiAnswer").append(template.compile(answerCardE)({
                    answerCard: result.resultObject.questionList
                }));
            }
        });
        //单选，多选，判断选择效果
        var questionId, studentId, examId, answer,answerd = [];
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
        $(".editor").each(function () {
            $(this).off("blur").on('blur', function () {
                var   IndexId = $(this).parent().parent().attr("data-indexId");
                questionId = $(this).parent().parent().attr("data-questionId");
                studentId = $(this).parent().parent().attr("data-studenId");
                examId = $(this).parent().parent().attr("data-examId");
                var richText = $(this).val();
                var txtValue =  richText.replace(/[\n" "]+/g,"");
                if(txtValue !== ""){
                    $(".thirdLeiAnswer ol li").each(function () {
                        var  answerid = $(this).attr("data-answerid");
                        if (IndexId == answerid) {
                            $(this).addClass("select");
                            return false;
                        }
                    });
                }else {
                    richText = "";
                    $(".thirdLeiAnswer ol li").each(function () {
                        var  answerid = $(this).attr("data-answerid");
                        if (IndexId == answerid) {
                            $(this).removeClass("select");
                            return false;
                        }
                    });
                }
                console.log(richText+"====");
                updateAnswer(examId, studentId, questionId, encodeURIComponent(richText));
            })
        });
        $(".timu li").click(function () {
            questionId = $(this).parent().parent().attr("data-questionId");
            studentId = $(this).parent().parent().attr("data-studenId");
            examId = $(this).parent().parent().attr("data-examId");
            //console.log(123);
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
                updateAnswer(examId, studentId, questionId, answer);
            } else if ($(this).hasClass("duoxuanTi")) {
                answerd=[];
                $(this).parent().find(".duoxuanTi").each(function(){
                    if($(this).hasClass("select")){
                        answerd.push($(this).attr("data-selectanswer"));
                    }else{
                        // answerd = '';
                    }
                });
                answerd=answerd.join();
                //answerd="["+answerd+"]";
                answerd = answerd == ""? "" : "["+answerd+"]";
                updateAnswer(examId, studentId, questionId, answerd)
            } /*else  {
                var   IndexId = $(this).parent().parent().attr("data-indexId");
                questionId = $(this).parent().parent().attr("data-questionId");
                studentId = $(this).parent().parent().attr("data-studenId");
                examId = $(this).parent().parent().attr("data-examId");
                var tiankongContent;

                //tleng填空题 空的个数
                var  tleng =  $(this).find(".tiankongtiInput").length;
                var  tk = [];           //输入的内容
                var  _this = $(this);
                var str = "";
                $(this).find(".tiankongtiInput").off("blur").on("blur", function () {
                    var   IndexId = _this.attr("data-indexid");     //题号
                    questionId = _this.attr("data-questionid");
                    studentId = _this.attr("data-studenid");
                    examId = _this.attr("data-examid");
                    tk = [];
                    //tiankongContent =  _this.find(".tiankongtiInput").val();
                    var containSpecial = RegExp(/[\^]+/);
                    for(var n=0;n<tleng;n++){
                        tiankongContent =  _this.find(".tiankongtiInput").eq(n).val();
                        console.log("11");
                        if(containSpecial.test(tiankongContent)){
                            layer.alert("存在非法字符‘^’",{icon:5});
                            tiankongContent = tiankongContent.replace(/[\^]+/g,"");
                            _this.find(".tiankongtiInput").eq(n).val(tiankongContent);
                        }
                        tiankongContent = tiankongContent == undefined ? "" : tiankongContent;
                        tk.push(tiankongContent);
                    }
                    tk = tk == "" ? "" : tk.join("^");
                    console.log(tk+"===============");
                    var tkValue =  tk.replace(/[\^" "]+/g,"");
                    if(tkValue !=""){
                        $(".thirdLeiAnswer ol li").each(function () {
                            var  answerid = $(this).attr("data-answerid");
                            if (IndexId == answerid) {
                                $(this).addClass("select");
                                return false;
                            }
                        });
                    }else {
                        $(".thirdLeiAnswer ol li").each(function () {
                            var  answerid = $(this).attr("data-answerid");
                            if (IndexId == answerid) {
                                $(this).removeClass("select");
                                return false;
                            }
                        });
                    }
                    //回调函数
                    updateAnswer(examId, studentId, questionId, encodeURIComponent(tk));
                });

            }*/
        });
        $(".timu li").each(function () {
            var   IndexId = $(this).parent().parent().attr("data-indexId");
            questionId = $(this).parent().parent().attr("data-questionId");
            studentId = $(this).parent().parent().attr("data-studenId");
            examId = $(this).parent().parent().attr("data-examId");
            var tiankongContent;

            //tleng填空题 空的个数
            var  tleng =  $(this).find(".tiankongtiInput").length;
            var  tk = [];           //输入的内容
            var  _this = $(this);
            var str = "";
            $(this).find(".tiankongtiInput").off("blur").on("blur", function () {
                var   IndexId = _this.attr("data-indexid");     //题号
                questionId = _this.attr("data-questionid");
                studentId = _this.attr("data-studenid");
                examId = _this.attr("data-examid");
                tk = [];
                //tiankongContent =  _this.find(".tiankongtiInput").val();
                var containSpecial = RegExp(/[\^]+/);
                for(var n=0;n<tleng;n++){
                    tiankongContent =  _this.find(".tiankongtiInput").eq(n).val();
                    console.log("11");
                    if(containSpecial.test(tiankongContent)){
                        layer.alert("存在非法字符‘^’",{icon:5});
                        tiankongContent = tiankongContent.replace(/[\^]+/g,"");
                        _this.find(".tiankongtiInput").eq(n).val(tiankongContent);
                    }
                    tiankongContent = tiankongContent == undefined ? "" : tiankongContent;
                    tk.push(tiankongContent);
                }
                tk = tk == "" ? "" : tk.join("^");
                console.log(tk+"===============");
                var tkValue =  tk.replace(/[\^" "]+/g,"");
                if(tkValue !=""){
                    $(".thirdLeiAnswer ol li").each(function () {
                        var  answerid = $(this).attr("data-answerid");
                        if (IndexId == answerid) {
                            $(this).addClass("select");
                            return false;
                        }
                    });
                    //回调函数
                    updateAnswer(examId, studentId, questionId, encodeURIComponent(tk));
                }else {
                    $(".thirdLeiAnswer ol li").each(function () {
                        var  answerid = $(this).attr("data-answerid");
                        if (IndexId == answerid) {
                            $(this).removeClass("select");
                            return false;
                        }
                    });
                }
                ////回调函数
                //updateAnswer(examId, studentId, questionId, encodeURIComponent(tk));
            })
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
            RequestService("/api/callThirdPost", "POST", {
                thirdUrl: base + "/bxg_anon/my/exam/submitStudentExam",
                examId: data.examId,
                studentId: data.studentId
            },function(data){
                if(data.success==true){
                    window.location.href="/web/html/myStudyCenter.html"
                }
            });
        });

        $(".confirm").click(function () {
            RequestService("/api/callThirdPost", "POST", {
                thirdUrl: base + "/bxg_anon/my/exam/submitStudentExam",
                examId: data.examId,
                studentId: data.studentId
            },function(data){
                if(data.success==true){
                    window.location.href="/web/html/myStudyCenter.html?state"
                }
            });
        });
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
        var _0li = $(".timu > li").length;
        var resultA= result.resultObject.questionList;
        for(var j=0;j<_0li;j++){
            if(resultA[j].answer == null || resultA[j].answer ==""){

            }else {
                $(".timu > li").eq(j).find(".editor").val(result.resultObject.questionList[j].answer);
                $(".thirdLeiAnswer ol li").each(function () {
                    var answerid = $(this).attr("data-answerid");
                    if (j == answerid) {
                        $(this).addClass("select");
                    }
                });
            }

        }

    });
    function updateAnswer(examId, studentId, questinId, answer) {
        RequestService("/api/callThirdPost", "POST", {
            thirdUrl: base + "/bxg_anon/my/exam/updateStudentAns",
            examId: examId,
            studentId: studentId,
            examQuestionId: questinId,
            answer: answer
        });
    }
});

