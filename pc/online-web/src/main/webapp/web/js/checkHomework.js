/**
 * Created by admin on 2017/2/28.
 */
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
        var paperId = arr[0];
    };
    //把填空题 题干里的【】替换成INPUT框
    template.helper("tiankongTi",function(question_head){
        var tkReg=/(【[/w/W]*】)/g;
        question_head=question_head.replace(tkReg,function(){
            return '<span class="tiankongTiInput"></span>';
        });
        return question_head;
    });
    var examInfo = '<div class="examName" title="{{items.paper_name}}">{{items.paper_name}}</div>' +
            '<span class="tiCount">题目数量：{{items.questionNum}}道</span>' +
            '<span class="tijiaoTime">交卷时间：{{items.end_time}}</span>'
        , questionList = '{{each quesList}}' +
            '{{if $value.question_type==0}}' +
            ' <li class="question0" data-indexId="{{$index}}" data-type="{{$value.question_type}}">' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
            '<ol class="ExamAnswer">' +
            '{{each options($value.options) as $val i}}' +
            '{{if $value.answer!=null && $value.answer!="" && $value.user_answer==[i] && $value.answer==$value.user_answer}}' +
            '<li class="danxuanTi correntAns" data-selectAnswer="{{i}}">' +
            '{{optionList[i]}}<span>{{$val}}</span>' +
            '<i class="iconfont icon-zhengque"></i>' +
            '{{else}}' +
            '{{if $value.answer!=$value.user_answer && $value.user_answer==[i]}}' +
            '<li class="danxuanTi errorAns" data-selectAnswer="{{i}}">' +
            '{{optionList[i]}}<span>{{$val}}</span>' +
            '<i class="iconfont icon-cuowu"></i>' +
            '{{else}}' +
            '<li class="danxuanTi" data-selectAnswer="{{i}}">' +
            '{{optionList[i]}}<span>{{$val}}</span>' +
            '{{/if}}' +
            '{{/if}}' +
            '{{if $value.options_picture!="" && $value.options_picture!=null}}' +
            '{{#optionsImg($value.options_picture,i)}}' +
            '{{/if}}' +
            '{{/each}}' +
            '</li>' +
            '</ol>' +
            '<p><span class="cankaoAnswer">参考答案：</span><span>{{#canKaoAns[$value.answer]}}</span></p>' +
            '<p><span class="answerShouMing">答案说明：</span><span>{{#$value.solution}}</span></p>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type==1}}' +
            ' <li class="question1" data-indexId="{{$index}}" data-type="{{$value.question_type}}">' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
            '<ol class="ExamAnswer dxt" data-da="{{$value.user_answer}}"  data-answer="{{$value.answer}}">' +
            '{{each options($value.options) as $val i}}' +
            '<li class="duoxuanTi" data-r="" id="li{{i}}" data-selectAnswer="{{i}}">' +
            '{{optionList[i]}}<span>{{$val}}</span>' +
            '{{if $value.options_picture!="" && $value.options_picture!=null}}' +
            '{{#optionsImg($value.options_picture,i)}}' +
            '{{/if}}' +
            '{{/each}}' +
            '</li>' +
            '</ol>' +
            '<p><span class="cankaoAnswer">参考答案：</span><span>{{#duoOptionList($value.answer,canKaoAns)}}</span></p>' +
            '<p><span class="answerShouMing">答案说明：</span><span>{{#$value.solution}}</span></p>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type==2}}' +
            ' <li class="question2" data-indexId="{{$index}}" data-type="{{$value.question_type}}">' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
            '<ol class="ExamAnswer">' +
            '{{if $value.user_answer=="对"}}' +
            '{{if $value.user_answer==$value.answer}}' +
            '<li class="panduanTi select" data-selectAnswer="0">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>√</span>' +
            '<i class="iconfont icon-zhengque correntAns"></i>' +
            '</li>' +
            '<li class="panduanTi" data-selectAnswer="1">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>×</span>' +
            '</li>' +
            '{{else}}' +
            '<li class="panduanTi select" data-selectAnswer="0">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>√</span>' +
            '<i class="iconfont icon-cuowu errorAns"></i>' +
            '</li>' +
            '<li class="panduanTi" data-selectAnswer="1">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>×</span>' +
            '</li>' +
            '{{/if}}{{/if}}' +

            '{{if $value.user_answer=="错"}}' +
            '{{if $value.user_answer==$value.answer}}' +
            '<li class="panduanTi" data-selectAnswer="0">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>√</span>' +
            '</li>' +
            '<li class="panduanTi select" data-selectAnswer="1">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>×</span>' +
            '<i class="iconfont icon-zhengque correntAns"></i>' +

            '</li>' +
            '{{else}}' +
            '<li class="panduanTi" data-selectAnswer="0">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>√</span>' +
            '</li>' +
            '<li class="panduanTi select" data-selectAnswer="1">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>×</span>' +
            '<i class="iconfont icon-cuowu errorAns"></i>' +
            '</li>' +
            '{{/if}}{{/if}}' +

            '{{if $value.user_answer=="" || $value.user_answer==null}}' +
            '<li class="panduanTi" data-selectAnswer="0">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>√</span>' +
            '</li>' +
            '<li class="panduanTi" data-selectAnswer="1">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>×</span>' +
            '</li>' +
            '{{/if}}' +
            '<p><span class="cankaoAnswer">参考答案：</span><span>{{#$value.answer}}</span></p>' +
            '<p><span class="answerShouMing">答案说明：</span><span>{{#$value.solution}}</span></p>' +
            '</li>' +
            '</ol>' +
            '{{/if}}' +
            '{{if $value.question_type=="3"}}' +
            ' <li class="question3" data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-guankaId="{{$value.id}}" data-tkAnswer="{{$value.user_answer}}">' +
            '{{$index+1}}、{{#tiankongTi($value.question_head)}}({{$value.question_score}}分)' +
            '<p><span class="cankaoAnswer">参考答案：</span><span>{{tiankkongReview($value.answer)}}</span></p>' +
            '<p><span class="answerShouMing">答案说明：</span><span>{{$value.solution}}</span></p>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type=="4"}}' +
            ' <li class="question4" data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-guankaId="{{$value.id}}">' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
            '<div class="examRichText">'+
            '<textarea class="editor"  maxlength="7000" name="content" style="width:746px;height:243px"></textarea>'+
            '</div>'+
            '<p><span class="cankaoAnswer">参考答案：</span><span>{{#$value.answer}}</span></p>' +
            '<p><span class="answerShouMing">答案说明：</span><span>{{#$value.solution}}</span></p>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type=="6"}}' +
            ' <li class="question6" data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-guankaId="{{$value.id}}">' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
            '<div class="download">'+
            '<p>素材：' +
            '{{if $value.attachment_url!="" && $value.attachment_url!=null}}'+
            '<a href="{{$value.attachment_url}}" target="_blank">{{#attachmentName($value.attachment_url)}}</a>'+
            '{{/if}}'+
            '</p>'+
            '</div>'+
            '<div class="upload">'+
            '<p>上传附件：</p>'+
            '<div class="upBtn">'+
            '<form id="form1"  method="post" enctype="multipart/form-data">'+
            '<span class="chooseFile">选择文件</span>'+
            '<input type="file" name="upload" class="file"/>'+
            ' </form>'+
            '</div>'+
            '{{if $value.answer_attachment_url!="" && $value.answer_attachment_url!=null}}'+
            '<a class="attachmentName" href="{{$value.answer_attachment_url}}"  target="_blank">{{#attachmentName($value.answer_attachment_url)}}</a>'+
            '{{/if}}'+
            '<span><em>*</em>请上传格式为jpg、gif、png、txt、word、excel、zip、rar且小于100M的文件</span>'+
            '</div>'+
            '<p><span class="answerShouMing">答案说明：</span><span>{{#$value.solution}}</span></p>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type=="5"}}' +
            ' <li class="question5" data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-guankaId="{{$value.id}}">' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
            '<div class="examRichText">'+
            '<textarea class="editor" maxlength="7000" name="content" style="width:746px;height:243px"></textarea>'+
            '</div>'+
            '<div class="upload">'+
            '<p>上传附件：</p>'+
            '<div class="upBtn">'+
            '<form id="form1"  method="post" enctype="multipart/form-data">'+
            '<span class="chooseFile">选择文件</span>'+
            '<input type="file" name="upload" class="file"/>'+
            ' </form>'+
            '</div>'+
            '{{if $value.answer_attachment_url!="" && $value.answer_attachment_url!=null}}'+
            '<a class="attachmentName" href="{{$value.answer_attachment_url}}"  target="_blank">{{#attachmentName($value.answer_attachment_url)}}</a>'+
            '{{/if}}'+
            '<span><em>*</em>请上传格式为jpg、gif、png、txt、word、excel、zip、rar且小于100M的文件</span>'+
            '</div>'+
            '<p><span class="cankaoAnswer">参考答案：</span>' +
            '{{if $value.attachment_url!="" && $value.attachment_url!=null}}'+
            '<a class="attachmentName" href="{{$value.attachment_url}}"  target="_blank">{{#attachmentName($value.attachment_url)}}</a>'+
            '{{/if}}'+
            '</p>' +
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
                '{{if $value.user_answer!="" && $value.user_answer!=null}}'+
                    '{{if  $value.user_answer==$value.answer}}' +
                    ' <li class="corrent" data-answerId="{{$index}}">{{$index+1}}</li>' +
                    '{{else}}' +
                    ' <li class="error" data-answerId="{{$index}}">{{$index+1}}</li>' +
                    '{{/if}}' +
                  '{{else}}'+
                  ' <li class="noAnswer" data-answerId="{{$index}}">{{$index+1}}</li>' +
                '{{/if}}'+
            '{{/if}}' +
            '{{/each}}' +
            '</ol>' +
            '</li>';

    answerCardB = ' <li class="radioAnswer">' +
        '<span>多选题</span>' +
        '<ol class="clearfix">' +
        '{{each answerCard}}' +
        '{{if $value.question_type==1}}' +
        '{{if $value.user_answer!="" && $value.user_answer!=null}}'+
        '{{if  $value.user_answer==$value.answer}}' +
        ' <li class="corrent" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        ' <li class="error" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{/if}}' +
        '{{else}}'+
        ' <li class="noAnswer" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{/if}}'+
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
        '{{if $value.user_answer!="" && $value.user_answer!=null}}'+
        '{{if  $value.user_answer==$value.answer}}' +
        ' <li class="corrent" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        ' <li class="error" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{/if}}' +
        '{{else}}'+
        ' <li class="noAnswer" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{/if}}'+
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
        '<li data-answerId="{{$index}}">{{$index+1}}</li>' +
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
        '<li data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{/if}}' +
        '{{/each}}' +
        '</ol>' +
        '</li>';
    answerCardF =
        '<li class="radioAnswer">' +
        '<span>代码题</span>' +
        '<ol class="clearfix">' +
        '{{each answerCard}}' +
        '{{if $value.question_type==5}}' +
        '<li data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{/if}}' +
        '{{/each}}' +
        '</ol>' +
        '</li>';
    answerCardG =
        '<li class="radioAnswer">' +
        '<span>实操题</span>' +
        '<ol class="clearfix">' +
        '{{each answerCard}}' +
        '{{if $value.question_type==6}}' +
        '<li data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{/if}}' +
        '{{/each}}' +
        '</ol>' +
        '</li>';
    var examScore =
        '<ul>' +
        '<li>单选题</li>' +
        '{{if examScore!=""&&examScore!=null}}'+
        '<li class="correntAnswer">{{examScore.right_sum}}</li>' +
        '<li class="errorAnser">{{examScore.wrong_sum}}</li>' +
        '<li>{{examScore.undo_sum}}</li>' +
        '<li class="errorAnser">{{examScore.score}}</li>' +
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
        '<li class="correntAnswer">{{examScore.right_sum}}</li>' +
        '<li class="errorAnser">{{examScore.wrong_sum}}</li>' +
        '<li>{{examScore.undo_sum}}</li>' +
        '<li class="errorAnser">{{examScore.score}}</li>' +
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
        '<li class="correntAnswer">{{examScore.right_sum}}</li>' +
        '<li class="">{{examScore.wrong_sum}}</li>' +
        '<li>{{examScore.undo_sum}}</li>' +
        '<li class="errorAnser">{{examScore.score}}</li>' +
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
        '<li class="correntAnswer">--</li>' +
        '<li class="errorAnser">--</li>' +
        '<li>{{examScore.undo_sum}}</li>' +
        '<li class="errorAnser">{{examScore.score}}</li>' +
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
        '<li class="correntAnswer">--</li>' +
        '<li class="errorAnser">--</li>' +
        '<li>{{examScore.undo_sum}}</li>' +
        '<li class="errorAnser">{{examScore.score}}</li>' +
        '{{else}}'+
        '<li class="correntAnswer">--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        '{{/if}}'+
        '</ul>';
    var examScore5 =
        '<ul>' +
        '<li>实操题</li>' +
        '{{if examScore!=""&&examScore!=null}}'+
        '<li class="correntAnswer">--</li>' +
        '<li class="errorAnser">--</li>' +
        '<li>{{examScore.undo_sum}}</li>' +
        '<li class="errorAnser">{{examScore.score}}</li>' +
        '{{else}}'+
        '<li class="correntAnswer">--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        '{{/if}}'+
        '</ul>';
    var examScore6 =
        '<ul>' +
        '<li>代码题</li>' +
        '{{if examScore!=""&&examScore!=null}}'+
        '<li class="correntAnswer">--</li>' +
        '<li class="errorAnser">--</li>' +
        '<li>{{examScore.undo_sum}}</li>' +
        '<li class="errorAnser">{{examScore.score}}</li>' +
        '{{else}}'+
        '<li class="correntAnswer">--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        '<li>--</li>' +
        '{{/if}}'+
        '</ul>';

    RequestService("/online/user/isAlive","GET",null,function(data){
        if(data.success==true){
            RequestService("/paper/findMyPaperOrScore","GET",{paperId:paperId},function(data){
                var canKaoAns = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "G", "K", "L", "M", "N"];
                var optionList = ["A、", "B、", "C、", "D、", "E、", "F、", "G、", "H、", "I、", "G、", "K、", "L、", "M、", "N、"];
                $(".examBasicInfoTop").html(template.compile(examInfo)({
                    items: data.resultObject.paperInfo[0]
                }));
                if(data.resultObject.score==null || data.resultObject.score==""){
                    $(".timuTypeAndSocre").css("display","none");
                }else{
                    var denfen=0;
                    var total=data.resultObject.paperInfo[0].zscore;
                    for(var i=0;i<data.resultObject.score.length;i++){
                        var score=parseFloat(data.resultObject.score[i].score);
                        denfen+= score;
                    }
                    if(denfen.toString().indexOf(".")!=-1){
                        denfen=denfen.toFixed(1);
                    }
                    $(".defen").html(denfen);
                    $(".totalFen").html("试卷总分为："+total+"分");
                    var sub = total - denfen;
                    var dFen=Math.round(denfen);
                    ccjindu(dFen, sub);
                    $(".timuTotal").append(template.compile(examScore)({
                        examScore:data.resultObject.score[0]
                    }));
                    $(".timuTotal").append(template.compile(examScore1)({
                        examScore:data.resultObject.score[1]
                    }));
                    $(".timuTotal").append(template.compile(examScore2)({
                        examScore:data.resultObject.score[2]
                    }));
                    $(".timuTotal").append(template.compile(examScore3)({
                        examScore:data.resultObject.score[3]
                    }));
                    $(".timuTotal").append(template.compile(examScore4)({
                        examScore:data.resultObject.score[4]
                    }));
                    $(".timuTotal").append(template.compile(examScore5)({
                        examScore:data.resultObject.score[6]
                    }));
                    $(".timuTotal").append(template.compile(examScore6)({
                        examScore:data.resultObject.score[5]
                    }));
                    $(".timuTypeAndSocre").css("display","block");
                }
                $(".comments span").html(data.resultObject.paperInfo[0].comment);
                var paper=data.resultObject.question;
                $(".timu").html(template.compile(questionList)({
                    canKaoAns: canKaoAns,
                    optionList: optionList,
                    quesList: paper
                }));
                $("body,html").scrollTop(0);
                var ansCheck;
                $(".dxt").each(function () {
                    var _this = $(this).find("li");
                    var arr = _this.parent().attr("data-da");
                    var Answer = $(this).attr("data-answer");

                    if (Answer != "" && Answer != null) {
                        ansCheck = JSON.parse(Answer);
                    }
                    if (arr != "" && arr != null) {
                        arr = JSON.parse(arr);
                    }

                    for (var j = 0; j < arr.length; j++) {
                        if (ansCheck.indexOf(arr[j]) != -1) {
                            $(this).find('#li' + arr[j] + ' span').addClass('correntAns');
                            $(this).find('#li' + arr[j] + ' span').append('<i class="iconfont icon-zhengque correntAns"></i>');
                        } else {
                            $(this).find('#li' + arr[j] + ' span').addClass('errorAns');
                            $(this).find('#li' + arr[j] + ' span').append('<i class="iconfont icon-cuowu errorAns"></i>');
                        }

                    }

                });
                //富文本
                if ($("textarea").length != 0) {
                    //富文本编辑器
                    var sm_toolbar = ['italic', 'bold', 'underline', 'strikethrough', '|', 'blockquote', 'code',"link"];
                    Simditor.locale = 'zh-CN';
                    var editor = new Simditor({
                        textarea: $('.editor'),
                        toolbar: sm_toolbar,
                        defaultImage: 'g',
                        pasteImage: true,
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
                }
                $(".tiankongTiInput").attr("readonly","true");
                var questType=[];
                $.each(paper,function(i,data){
                    questType.push(data.question_type);
                });
                questType=questType.unique3();
                $.each(questType, function (i, a) {
                    if (a == 0) {
                        $(".timuTypeBox").append('<li class="d0 select" data-timuType="0">单选题</li>');
                        $(".thirdLeiAnswer").html(template.compile(answerCardA)({
                            answerCard: paper
                        }));
                    } else if (a == 1) {
                        $(".timuTypeBox").append('<li class="d1" data-timuType="1">多选题</li>');
                        $(".thirdLeiAnswer").append(template.compile(answerCardB)({
                            answerCard: paper
                        }));
                    } else if (a == 2) {
                        $(".timuTypeBox").append('<li class="d2" data-timuType="2">判断题</li>');
                        $(".thirdLeiAnswer").append(template.compile(answerCardC)({
                            answerCard: paper
                        }));
                    } else if (a == 3) {
                        $(".timuTypeBox").append('<li class="d3" data-timuType="3">填空题</li>');
                        $(".thirdLeiAnswer").append(template.compile(answerCardD)({
                            answerCard: paper
                        }));
                    } else if (a == 4) {
                        $(".timuTypeBox").append('<li class="d4" data-timuType="4">简答题</li>');
                        $(".thirdLeiAnswer").append(template.compile(answerCardE)({
                            answerCard: paper
                        }));
                    }else if (a ==5) {
                        $(".timuTypeBox").append('<li class="d5" data-timuType="5">代码题</li>');
                        $(".thirdLeiAnswer").append(template.compile(answerCardF)({
                            answerCard: paper
                        }));
                    }  else if (a == 6) {
                        $(".timuTypeBox").append('<li class="d6" data-timuType="6">实操题</li>');
                        $(".thirdLeiAnswer").append(template.compile(answerCardG)({
                            answerCard:paper
                        }));
                    }
                });
                //填空题答案回写
                $(".timu li.question3").each(function(){
                    var tkAnswer=$(this).attr("data-tkAnswer");
                    if(tkAnswer!=""){
                        tkAnswer=JSON.parse(tkAnswer);
                        for(var i=0;i<tkAnswer.length;i++){
                            $(this).find("span").eq(i).text(tkAnswer[i]);
                        }
                    }
                });
                //简答题和代码题答案回写
                for(var i=0;i<paper.length;i++){
                    var userAnswer=paper[i].user_answer;
                    if( (userAnswer!="" && userAnswer!=null) && (paper[i].question_type==4 || paper[i].question_type==6)){
                        $(".simditor-body").each(function(){
                            var timuIndex = $(this).parent().parent().parent().parent().attr("data-indexid");
                            if(i==timuIndex){
                                $(this).html("<p>"+userAnswer+"</p>");
                            }
                        });
                    }
                }
                $(".simditor-body").attr("contenteditable","false");
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
                if ($(".timu li").hasClass("question0")) {
                    var danxuanTop = $(".question0").offset().top - 80;
                }
                if ($(".timu li").hasClass("question1")) {
                    var duoxuanTop = $(".question1").offset().top - 80;
                }
                if ($(".timu li").hasClass("question2")) {
                    var panduanTop = $(".question2").offset().top - 80;
                }
                if ($(".timu li").hasClass("question3")) {
                    var tiankongTop = $(".question3").offset().top - 80;
                }
                if ($(".timu li").hasClass("question4")) {
                    var jiandaTop = $(".question4").offset().top - 80;
                }
                if ($(".timu li").hasClass("question5")) {
                    var daimaTop = $(".question6").offset().top - 80;
                }
                if ($(".timu li").hasClass("question6")) {
                    var shicaoTop = $(".question5").offset().top - 80;
                }
                if ($(document).scrollTop() > danxuanTop) {
                    $(".d0").addClass("select").siblings().removeClass("select");
                }
                if ($(document).scrollTop() > duoxuanTop) {
                    $(".d1").addClass("select").siblings().removeClass("select");
                }
                if ($(document).scrollTop() > panduanTop) {
                    $(".d2").addClass("select").siblings().removeClass("select");
                }
                if ($(document).scrollTop() > tiankongTop) {
                    $(".d3").addClass("select").siblings().removeClass("select");
                }
                if ($(document).scrollTop() > jiandaTop) {
                    $(".d4").addClass("select").siblings().removeClass("select");
                }
                if ($(document).scrollTop() > daimaTop) {
                    $(".d5").addClass("select").siblings().removeClass("select");
                }
                if ($(document).scrollTop() > shicaoTop) {
                    $(".d6").addClass("select").siblings().removeClass("select");
                }

            };
        }else{
            $("#login").modal('show');
        }
    });

});