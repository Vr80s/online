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
    }
    //倒计时
    function getRTime(date) {
        var intDiff = date;
        var ww;
        ww = window.setInterval(function () {
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

            if (day != 0) {
                $(".examTime span").html("<span>" + day + "</span>天<span>");
            } else if (hour == 0) {
                $(".examTime span").html("<span>" + minute + "</span>分钟<span>");
            } else if (day == 0) {
                $(".examTime span").html("<span>" + hour + "</span>小时<span>");
            }

            intDiff--;
            if (intDiff <= 0) {
                $(".examTime span").html("<span>0分钟<span>");
                $(".examtimeOutBox").css("display", "block");
                window.clearInterval(ww);
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

    var examInfo = '<div class="examName" title="{{items.paper_name}}">{{items.paper_name}}</div>' +
            '<span class="tiCount">题目数量：{{items.questionNum}}道</span>' +
            '<span class="tijiaoTime">交卷时间：{{items.end_time}}</span>'
        , questionList = '{{each quesList as $value $index}}' +
            '{{if $value.question_type=="0"}}' +
            ' <li class="question0" data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-guankaId="{{$value.id}}">' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
            '<ol class="ExamAnswer">' +
            '{{each options($value.options) as $val i}}' +
            '{{if $value.user_answer==[i]}}' +
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
            ' <li class="question1" data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-guankaId="{{$value.id}}" >' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
            '<ol class="ExamAnswer dxt" data-da="{{$value.user_answer}}">' +
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
            ' <li class="question2" data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-guankaId="{{$value.id}}">' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
            '<ol class="ExamAnswer">' +
            '{{if $value.user_answer!="" && $value.user_answer!=null}}' +
            '{{if $value.user_answer=="对"}}' +
            '<li class="panduanTi select" data-selectAnswer="0">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>√</span>' +
            '</li>' +
            '<li class="panduanTi" data-selectAnswer="1">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>×</span>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.user_answer=="错"}}' +
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
            '{{/if}}'+
            '{{if $value.question_type=="3"}}' +
            ' <li class="question3" data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-guankaId="{{$value.id}}" data-tkAnswer="{{$value.user_answer}}">' +
            '{{$index+1}}、{{#tiankongTi($value.question_head)}}({{$value.question_score}}分)' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type=="4"}}' +
            ' <li class="question4" data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-guankaId="{{$value.id}}">' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
                    '<div class="examRichText">'+
                    '<textarea id="editor{{$index}}"  maxlength="7000" name="content" style="width:746px;height:243px"></textarea>'+
                    '</div>'+
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type=="6"}}' +
            ' <li class="question6" data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-guankaId="{{$value.id}}">' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
            '<div class="download">'+
                '<span>素材：</span>' +
                '{{if $value.attachment_url!="" && $value.attachment_url!=null}}'+
                '<a href="{{$value.attachment_url}}" target="_blank">{{#attachmentName($value.attachment_url)}}</a>'+
                '{{/if}}'+
                '</div>'+
                '<div class="upload">'+
                '<p>上传附件：</p>'+
                '<div class="upBtn">'+
                '<form id="form{{$index}}"  method="post" enctype="multipart/form-data">'+
                '<input type="hidden" name="projectName" value="online"/>'+
                '<input type="hidden" name="fileType" class="fileType" value="2"/>'+
                '<span class="chooseFile">选择文件</span>'+
                '<input type="file" name="upload" class="file"/>'+
                ' </form>'+
                '</div>'+
                '{{if $value.answer_attachment_url!="" && $value.answer_attachment_url!=null}}'+
                '<a class="attachmentName" href="{{$value.answer_attachment_url}}"  target="_blank">{{#attachmentName($value.answer_attachment_url)}}</a>'+
                '{{/if}}'+
                '<span><em>*</em>请上传格式为jpg、gif、png、txt、word、excel、zip、rar且小于100M的文件</span>'+
                '</div>'+
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type=="5"}}' +
            ' <li class="question5" data-indexId="{{$index}}" data-type="{{$value.question_type}}" data-guankaId="{{$value.id}}">' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
                '<div class="examRichText">'+
            '<textarea id="editor{{$index}}"  maxlength="7000" name="content" style="width:746px;height:243px"></textarea>'+
                '</div>'+
            '<div class="upload">'+
            '<p>上传附件：</p>'+
            '<div class="upBtn">'+
            '<form id="form{{$index}}"  method="post" enctype="multipart/form-data">'+
            '<input type="hidden" name="projectName" value="online"/>'+
            '<input type="hidden" name="fileType" class="fileType" value="2"/>'+
            '<span class="chooseFile">选择文件</span>'+
            '<input type="file" name="upload" class="file"/>'+
            ' </form>'+
            '</div>'+
            '{{if $value.answer_attachment_url!="" && $value.answer_attachment_url!=null}}'+
            '<a class="attachmentName" href="{{$value.answer_attachment_url}}" target="_blank">{{#attachmentName($value.answer_attachment_url)}}</a>'+
            '{{/if}}'+
            '<span><em>*</em>请上传格式为jpg、gif、png、txt、word、excel、zip、rar且小于100M的文件</span>'+
            '</div>'+
            '</li>' +
            '{{/if}}' +
    '{{/each}}',
        answerCardA =
            ' <li class="radioAnswer">' +
            '<span>单选题</span>' +
            '<ol class="clearfix">' +
            '{{each answerCard}}' +
            '{{if $value.question_type==0}}' +
            '{{if $value.user_answer!="" && $value.user_answer!=null}}' +
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
        '{{if $value.user_answer!="" && $value.user_answer!=null}}' +
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
        '{{if $value.user_answer!="" && $value.user_answer!=null}}' +
        '<li class="select" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        ' <li data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{/if}}' +
        '{{/if}}' +
        '{{/each}}' +
        '</ol>' +
        '</li>';
    answerCardD=
        '<li class="radioAnswer">' +
        '<span>填空题</span>' +
        '<ol class="clearfix">' +
        '{{each answerCard}}' +
        '{{if $value.question_type==3}}' +
        '{{if $value.user_answer!="" && $value.user_answer!=null}}' +
        '<li class="select" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        ' <li data-answerId="{{$index}}">{{$index+1}}</li>' +
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
        '{{if $value.user_answer!="" && $value.user_answer!=null}}' +
        '<li class="select" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        ' <li data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{/if}}' +
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
        '{{if ($value.user_answer!="" && $value.user_answer!=null) || ($value.answer_attachment_url!="" && $value.answer_attachment_url!=null)}}' +
        '<li class="select" data-answerId="{{$index}}" data-questionType="{{$value.question_type}}">{{$index+1}}</li>' +
        '{{else}}' +
        ' <li data-answerId="{{$index}}" data-questionType="{{$value.question_type}}">{{$index+1}}</li>' +
        '{{/if}}' +
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
        '{{if ($value.user_answer!="" && $value.user_answer!=null)  || ($value.answer_attachment_url!="" && $value.answer_attachment_url!=null)}}' +
        '<li class="select" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        ' <li data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{/if}}' +
        '{{/if}}' +
        '{{/each}}' +
        '</ol>' +
        '</li>';
    RequestService("/online/user/isAlive","GET",null,function(data){
        if(data.success==true){
            var courseId;
            RequestService("/paper/getMyPaper", "GET", {paperId: paperId}, function (data) {
                var optionList = ["A、", "B、", "C、", "D、", "E、", "F、", "G、", "H、", "I、", "G、", "K、", "L、", "M、", "N、"];
                var paper = data.resultObject.paper;
                courseId=data.resultObject.baseInfo[0].course_id;
                var currentTime =new Date(data.resultObject.baseInfo[0].currentTime).getTime();
                var endTime =new Date(data.resultObject.baseInfo[0].end_time).getTime();
                var minutes=(endTime-currentTime)/1000;

                $(".examBasicInfoTop").html(template.compile(examInfo)({
                    items: data.resultObject.baseInfo[0]
                }));
                $(".timu").html(template.compile(questionList)({
                    optionList: optionList,
                    quesList: paper
                }));
                //富文本
                if ($("textarea").length != 0) {
                    //富文本编辑器
                    var sm_toolbar = ['italic', 'bold', 'underline', 'strikethrough', '|', 'blockquote', 'code',"link"];
                    Simditor.locale = 'zh-CN';
                    $("textarea").each(function(index){
                        var textAreaId=$(this).attr("id");
                        var editor="editor"+index;
                        var editor= new Simditor({
                            textarea: $('#'+textAreaId),
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
                    });
                    $(".toolbar-item-code").trigger("click");
                }
                RequestService("/online/user/isAlive","GET",null,function(data){
                    if(data.success==true){
                        upLoadFile();//上传附件
                    }else{
                        $("#login").modal('show');
                    }
                });

                $("body,html").scrollTop(0);

                if (minutes<=0) {
                    $(".examTime span").html("<span>0分钟<span>");
                    $(".examtimeOutBox").css("display", "block");
                    aaa();
                } else {
                    $(".examtimeOutBox").css("display", "none");
                    getRTime(minutes);
                };



                var questType = [];
                $.each(paper, function (i, data) {
                    questType.push(data.question_type);
                });
                questType = questType.unique3();
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
                //单选，多选，判断选择效果
                var examId, answer, answerd = [];
                $(".timu li").click(function () {
                    var Indexid, answerid;
                    if ($(this).hasClass("danxuanTi") || $(this).hasClass("panduanTi")) {
                        $(this).addClass("select").siblings().removeClass("select");
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
                });
                //多选效果
                $(".dxt").each(function () {
                    var arr = $(this).attr("data-da");
                    if (arr != null && arr != "") {
                        arr = JSON.parse(arr);
                        for (var i = 0; i < arr.length; i++) {
                            $(this).find("li").each(function () {
                                if ($(this).index() == arr[i]) {
                                    $(this).addClass("select")
                                }
                            });
                        };
                    }
                });
                //填空题
                $(".timu li.question3").each(function () {
                    var tkThis=$(this);
                    var type=$(this).attr("data-type");
                    var examId=$(this).attr("data-guankaid");
                    var IndexId=$(this).attr("data-indexid");     //题号
                    //填空题答案回写
                    var tkAnswer=tkThis.attr("data-tkAnswer");
                    if(tkAnswer!=""){
                        tkAnswer=JSON.parse(tkAnswer);
                        for(var i=0;i<tkAnswer.length;i++){
                            $(this).find("span").eq(i).text(tkAnswer[i]);
                        }
                    }
                    var tiankongContent;
                    //tleng填空题 空的个数
                    var  tleng =  tkThis.find("input").length;
                    var  tk = [];           //输入的内容
                    var  _this = $(this);
                    $(this).find("input").off("blur").on("blur", function () {
                        tk = [];
                        for(var n=0;n<tleng;n++){
                            tiankongContent =  _this.find("input").eq(n).val();
                            tiankongContent = tiankongContent == undefined ? "" : tiankongContent;
                            tk.push('"'+tiankongContent+'"');
                        }
                        tk = tk.join();
                        tk = tk == "" ? "" : "[" + tk + "]";
                        //回调函数
                        updateAnswer(examId, tk);
                        if(tk.length !=0){
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
                    });
                });
                //简答题和代码题答案回写
                for(var i=0;i<paper.length;i++){
                    var userAnswer=paper[i].user_answer;
                    if( (userAnswer!="" && userAnswer!=null) && paper[i].question_type==4 || paper[i].question_type==6){
                        $(".simditor-body").each(function(){
                            var timuIndex = $(this).parent().parent().parent().parent().attr("data-indexid");
                            if(i==timuIndex){
                                $(this).html("<p>"+userAnswer+"</p>");
                            }
                        });
                    }
                }
                //简答题
                $(".simditor-body").unbind("blur").each(function () {
                    $(this).on('blur', function () {
                        var _this=$(this);
                        var IndexId = _this.parent().parent().parent().parent().attr("data-indexid");
                        examId = _this.parent().parent().parent().parent().attr("data-guankaid");
                        var richText = $(_this ).html();
                        if (richText !== "") {
                            $(".thirdLeiAnswer ol li").each(function () {
                                var answerid = $(this).attr("data-answerid");
                                if (IndexId == answerid) {
                                    $(this).addClass("select");
                                    /* return false;*/
                                }
                            });
                        } else {
                            richText = "<p><br></p>";
                            $(".thirdLeiAnswer ol li").each(function () {
                                var answerid = $(this).attr("data-answerid");
                                if (IndexId == answerid) {
                                    $(this).removeClass("select");
                                    /*return false;*/
                                }
                            });
                        }
                        updateAnswer(examId,richText);
                    })
                });
                $(".timu li").click(function () {
                    examId = $(this).parent().parent().attr("data-guankaId");
                    if ($(this).hasClass("danxuanTi") || $(this).hasClass("panduanTi")) {
                        if ($(this).hasClass("select")) {
                            answer = $(this).attr("data-selectAnswer");
                        } else {
                            answer = "";
                        }
                        if ($(this).hasClass("panduanTi")) {
                            if ($(this).hasClass("select")) {
                                if (answer == 0) {
                                    answer = "对"
                                } else {
                                    answer = "错"
                                }
                            } else {
                                answer = "";
                            }
                        }
                        updateAnswer(examId, answer);
                    } else if ($(this).hasClass("duoxuanTi")) {
                        answerd = [];
                        $(this).parent().find(".duoxuanTi").each(function () {
                            if ($(this).hasClass("select")) {
                                answerd.push('"' + $(this).attr("data-selectanswer") + '"');
                            }
                        });
                        answerd = answerd.join();
                        answerd = answerd == "" ? "" : "[" + answerd + "]";
                        updateAnswer(examId, answerd)
                    }
                });
                var datiStatus = false;
                $(".tijiaoAnswer").click(function () {
                    RequestService("/online/user/isAlive", "GET", "", function (data) {
                        if (data.success == true) {
                            datiStatus = false;
                            var count = [];
                            $(".radioAnswer li").each(function () {
                                if (!$(this).hasClass("select")) {
                                    count.push($(this).attr("data-answerid"));
                                    datiStatus = true;//未答完
                                }
                            });
                            if (datiStatus) {
                                var str = "";
                                for (var i = 0; i < count.length; i++) {
                                    if (i < 5) {
                                        str += "【" + ++count[i] + "】";
                                    } else {
                                        str += "…";
                                        break;
                                    }
                                }
                                $(".noCompleteHint .noCompleteContent .count").html(str);
                                $(".noCompleteHint").css("display", "block");
                            } else {
                                $(".yidawan").css("display", "block");
                            }
                        } else {
                            $('#login').modal('show');
                        }
                    });
                });
                $(".continueAnswer").click(function () {
                    $(".noCompleteHint").css("display", "none");
                });

                $(".tijiao").click(function () {
                    RequestService("/online/user/isAlive","GET",null,function(data){
                        if(data.success==true){
                            submitAnswer();
                        }else{
                            $("#login").modal('show');
                        }
                    });
                });

                $(".confirm").click(function () {
                    RequestService("/online/user/isAlive","GET",null,function(data){
                        if(data.success==true){
                            submitAnswer();
                        }else{
                            $("#login").modal('show');
                        }
                    });
                });
                $(".checked").click(function () {
                    $(".yidawan").css("display", "none");
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
                            $(".timuTypeBox li").each(function () {
                                var timutype = $(this).attr("data-timutype");
                                if (timutype == dataType) {
                                    scrollTiType();
                                }
                            });
                            $("body,html").animate({"scrollTop": currentTop - 59 + "px"}, function () {
                                scrollTiType();
                                $(document).bind("scroll", function () {
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
                            $("body,html").animate({"scrollTop": currentTop - 60 + "px"}, function () {
                                scrollTiType();
                                $(document).bind("scroll", function () {
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
            function updateAnswer(examId, answer,attachment) {
                RequestService("/paper/updateQuestionById", "POST", {
                    questionId: examId,
                    answer: answer,
                    attachment:attachment
                },function(data){
                    if(data.success==false){
                        $('#login').modal('show');
                    }
                });
            }

            function submitAnswer() {
                RequestService("/paper/submitPaper", "POST", {
                    paperId: paperId
                }, function (data) {
                    if (data.success==true) {
                        window.location.href = "/web/html/CourseDetailZhiBo.html?courseId="+courseId+"&work=2";
                    }
                });
            };
            function rTips(errorMessage) {
                $(".rTips").text(errorMessage);
                $(".rTips").css("display", "block");
                setTimeout(function() {
                    $(".rTips").css("display", "none");
                }, 2000)
            }
            function upLoadFile(){
                $(".file").change(function(){
                    var filepath = $(this).val();
                    var fileFormat=/(\.txt|\.doc|\.docx|\.xls|\.xlsx|\.zip|\.rar|\.png|\.jpg|\.jpeg|\.gif)$/i;
                    var examId=$(this).parent().parent().parent().parent().attr("data-guankaid");
                    var IndexId=$(this).parent().parent().parent().parent().attr("data-indexid");
                    var answer=$(this).parentsUntil(".upload").prev().find(".simditor-body").text();
                    var fileSize=$(this).get(0).files[0].size /1024 /1024;
                    if(fileFormat.test(filepath) && fileSize<100){
                        var formId=$(this).parent().prop("id");
                        var id="#"+formId;
                        $(this).parent().attr("action",bath+"/attachmentCenter/upload");
                        $("#"+formId).ajaxSubmit(function(data) {
                            if(data.error==0){
                                updateAnswer(examId,answer, data.url);
                                $("#"+formId).parents(".upload").find("a").remove();
                                $("#"+formId).parentsUntil(".upload").after('<a class="attachmentName" href="'+data.url+'" target="_blank">'+data.orgFileName+'</a>');
                                $(".thirdLeiAnswer ol li").each(function () {
                                    var  answerid = $(this).attr("data-answerid");
                                    if (IndexId == answerid) {
                                        $(this).addClass("select");
                                        return false;
                                    }
                                });
                            }
                        });
                        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
                    }else{
                        rTips("请上传格式为txt、word、excel、zip、rar且小于100M的文件");
                    }
                });
            }

        }else{
            $('#login').modal('show');
        }
    });


});