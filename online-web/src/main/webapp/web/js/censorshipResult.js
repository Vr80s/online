/**
 * Created by admin on 2016/12/1.
 */
/**
 * Created by admin on 2016/12/1.
 */
$(function () {
    var gqid = $.getUrlParam("gqid");
    var examStatu = $.getUrlParam("examStatu");
    var examHeight = $(".examBasicInfo").innerHeight() + $(".timuTypeBox").innerHeight();
    var answerCardLeft = $(".examContent").offset().left + $(".timuAndAnswer").innerWidth() + 12;

    template.helper('realTime', function (date) {
        date = parseInt(date);
        var intDiff = date * 60;
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
        return ("<span>" + hour + "</span>:<span>" + minute + "</span>:<span>" + second + "</span>");
    });
    var examInfo = '<div class="assign">关卡任务：{{items.name}}</div>' +
            '<span class="tijiaoTime">实际用时：{{items.use_time}}分钟</span></br>' +
            '<span class="tgfs">总分数/通关分数：{{items.total_score}}/{{items.pass_score_percent}}分</span>' +
            '{{if items.result==0}}' +
            '<span class="tiFenshu">得分：{{items.score}}分</span>' +
            '{{else}}' +
            '<span class="tiFenshu correntAns">得分：{{items.score}}分</span>' +
            '{{/if}}' +
            '{{if items.number_pass!==0}}' +
            '<span class="paiMing">有<span class="tongGuanConut">{{items.number_pass}}</span>人已通关，当前排名第<span class="tongGuanConut">{{items.rank}}</span>名</span>' +
            '{{/if}}'
        , questionList = '{{each quesList}}' +
            '{{if $value.question_type==0}}' +
            ' <li class="question0" data-indexId="{{$index}}" data-type="{{$value.question_type}}">' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
            '<ol class="ExamAnswer">' +
            '{{each options($value.options) as $val i}}' +
            '{{if $value.answer!=null && $value.answer!="" && $value.my_answer==[i] && $value.is_right==1}}' +
            '<li class="danxuanTi correntAns" data-selectAnswer="{{i}}">' +
            '{{optionList[i]}}<span>{{$val}}</span>' +
            '<i class="iconfont icon-zhengque"></i>' +
            '{{else}}' +
            '{{if  $value.is_right==0 && $value.my_answer==[i]}}' +
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
            '<p><span class="cankaoAnswer">参考答案：</span><span>{{canKaoAns[$value.answer]}}</span></p>' +
            '<p><span class="answerShouMing">答案说明：</span><span>{{$value.solution}}</span></p>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type==1}}' +
            ' <li class="question1" data-indexId="{{$index}}" data-type="{{$value.question_type}}">' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
            '<ol class="ExamAnswer dxt" data-da="{{$value.my_answer}}" data-isCorrect="{{$value.is_right}}"   data-answer="{{$value.answer}}">' +
            '{{each options($value.options) as $val i}}' +
            '<li class="duoxuanTi" data-r="" id="li{{i}}" data-selectAnswer="{{i}}">' +
            '{{optionList[i]}}<span>{{$val}}</span>' +
            '{{if $value.options_picture!="" && $value.options_picture!=null}}' +
            '{{#optionsImg($value.options_picture,i)}}' +
            '{{/if}}' +
            '{{/each}}' +
            '</li>' +
            '</ol>' +
            '<p><span class="cankaoAnswer">参考答案：</span><span>{{duoOptionList($value.answer,canKaoAns)}}</span></p>' +
            '<p><span class="answerShouMing">答案说明：</span><span>{{$value.solution}}</span></p>' +
            '</li>' +
            '{{/if}}' +
            '{{if $value.question_type==2}}' +
            ' <li class="question2" data-indexId="{{$index}}" data-type="{{$value.question_type}}">' +
            '{{$index+1}}、{{#$value.question_head}}({{$value.question_score}}分)' +
            '<ol class="ExamAnswer">' +
            '{{if $value.my_answer=="对"}}' +
            '{{if $value.my_answer==$value.answer}}' +
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

            '{{if $value.my_answer=="错"}}' +
            '{{if $value.my_answer==$value.answer}}' +
            '<li class="panduanTi select" data-selectAnswer="0">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>√</span>' +
            '</li>' +
            '<li class="panduanTi" data-selectAnswer="1">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>×</span>' +
            '<i class="iconfont icon-zhengque correntAns"></i>' +

            '</li>' +
            '{{else}}' +
            '<li class="panduanTi select" data-selectAnswer="0">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>√</span>' +
            '</li>' +
            '<li class="panduanTi" data-selectAnswer="1">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>×</span>' +
            '<i class="iconfont icon-cuowu errorAns"></i>' +
            '</li>' +
            '{{/if}}{{/if}}' +

            '{{if $value.my_answer=="" || $value.my_answer==null}}' +
            '<li class="panduanTi" data-selectAnswer="0">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>√</span>' +
            '</li>' +
            '<li class="panduanTi" data-selectAnswer="1">' +
            '<div><em class="panduanBtn"></em></div>' +
            '<span>×</span>' +
            '</li>' +
            '{{/if}}' +
            '<p><span class="cankaoAnswer">参考答案：</span><span>{{$value.answer}}</span></p>' +
            '<p><span class="answerShouMing">答案说明：</span><span>{{$value.solution}}</span></p>' +
            '</li>' +
            '</ol>' +
            '{{/if}}' +
            '{{/each}}',
        answerCardA =
            ' <li class="radioAnswer">' +
            '<span>单选题</span>' +
            '<ol class="clearfix">' +
            '{{each answerCard}}' +
            '{{if $value.question_type==0}}' +
            '{{if  $value.is_right==1}}' +
            ' <li class="corrent" data-answerId="{{$index}}">{{$index+1}}</li>' +
            '{{else}}' +
            ' <li class="error" data-answerId="{{$index}}">{{$index+1}}</li>' +
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
        '{{if  $value.is_right==1}}' +
        ' <li class="corrent" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        ' <li class="error" data-answerId="{{$index}}">{{$index+1}}</li>' +
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
        '{{if  $value.is_right==1}}' +
        ' <li class="corrent" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{else}}' +
        ' <li class="error" data-answerId="{{$index}}">{{$index+1}}</li>' +
        '{{/if}}' +
        '{{/if}}' +
        '{{/each}}' +
        '</ol>' +

        '</li>';
    RequestService("/barrier/getBarrierBasicInfo", "GET", {id: gqid, examStatu: examStatu}, function (data) {
        $(".examBasicInfoTop").html(template.compile(examInfo)({
            items: data.resultObject
        }));
        var result = data.resultObject.result;
        if (result == 0) {
            $(".examBasicInfo").addClass("failed");
        } else {
            $(".examBasicInfo").addClass("success");
        }
    });
    RequestService("/barrier/getCurrentPaper", "GET", {barrierId: gqid, examStatu: examStatu}, function (result) {
        var canKaoAns = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "G", "K", "L", "M", "N"];
        var optionList = ["A、", "B、", "C、", "D、", "E、", "F、", "G、", "H、", "I、", "G、", "K、", "L、", "M、", "N、"];
        $(".timu").html(template.compile(questionList)({
            canKaoAns: canKaoAns,
            optionList: optionList,
            quesList: result.resultObject
        }));
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
        var questType = [];
        $.each(result.resultObject, function (i, data) {
            questType.push(data.question_type);
        });
        Array.prototype.unique3 = function () {
            var res = [];
            var json = {};
            for (var i = 0; i < this.length; i++) {
                if (!json[this[i]]) {
                    res.push(this[i]);
                    json[this[i]] = 1;
                }
            }
            return res;
        };
        questType = questType.unique3();
        $.each(questType, function (i, a) {
            if (a == 0) {
                $(".timuTypeBox").append('<li class="select" data-timuType="0">单选题</li>');
                $(".thirdLeiAnswer").html("").append(template.compile(answerCardA)({
                    answerCard: result.resultObject
                }));

            } else if (a == 1) {
                $(".timuTypeBox").append('<li data-timuType="1">多选题</li>');
                $(".thirdLeiAnswer").append(template.compile(answerCardB)({
                    answerCard: result.resultObject
                }));

            } else if (a == 2) {
                $(".timuTypeBox").append('<li data-timuType="2">判断题</li>');
                $(".thirdLeiAnswer").append(template.compile(answerCardC)({
                    answerCard: result.resultObject
                }));

            }
        });
        $(document).scroll(function () {
            scrollTiType();
        });
        //答题卡单击事件
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
                    scrollTiType();
                    $("body,html").animate({"scrollTop": currentTop - 60 + "px"},function(){
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
            $(".timuTypeBox li").each(function(){
                var timuT=$(this).attr("data-timutype");
                var tiT=$(".question0").attr("data-type");
                if(timuT==tiT){
                    $(this).addClass("select").siblings().removeClass("select");
                }
            });
        }
        if($(document).scrollTop()>duoxuanTop){
            $(".timuTypeBox li").each(function(){
                var timuT=$(this).attr("data-timutype");
                var tiT=$(".question1").attr("data-type");
                if(timuT==tiT){
                    $(this).addClass("select").siblings().removeClass("select");
                }
            });
        }
        if($(document).scrollTop()>panduanTop){
            $(".timuTypeBox li").each(function(){
                var timuT=$(this).attr("data-timutype");
                var tiT=$(".question2").attr("data-type");
                if(timuT==tiT){
                    $(this).addClass("select").siblings().removeClass("select");
                }
            });
        }
        if ($(document).scrollTop() > examHeight) {
            $(".timuTypeBox").addClass("examFloat");
            $(".answerCard").addClass("answerCardFloat").css("left", answerCardLeft + "px");
        } else {
            $(".timuTypeBox").removeClass("examFloat");
            $(".answerCard").removeClass("answerCardFloat");
        }
    };
});
