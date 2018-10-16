$(function () {
    requestGetService("/xczh/constitution/questionBank",null,function(data){
        handleQuestionList(data.resultObject);
        $(".select1").show();
        creatQuestion();
    })

    requestGetService("/xczh/constitution/constitution",null,function(data){
        console.info(data);
        constitutionList = data.resultObject;
    })
});

var index = 0;
var greenQuestionList = [];
var blueQuestionList = [];
var validBlueQuestionList = [];
var answerList = [];
var sex = localStorage.getItem("healthy.sex");
var birthday = localStorage.getItem("healthy.birthday");
var constitutionList;
var progressLength = 54;

function handleQuestionList(questionList){
    for(var i=0;i<questionList.length;i++){
        if(i<39){
            if(questionList[i].no!="10-1"&&questionList[i].no!="10-2" && questionList[i].no!="34-a"&&questionList[i].no!="34-b"){
                greenQuestionList.push(questionList[i]);
            }else if((questionList[i].no == "34-a" && sex == 1) || (questionList[i].no == "34-b" && sex == 0)){
                questionList[i].no = "34";
                greenQuestionList.push(questionList[i]);
            }
        }else{
            blueQuestionList.push(questionList[i]);
        }
    }
}

function creatQuestion(){
    var question = null;
    if(index > 2){
        handleValidBlueQuestionList();
    }
    if(index < 36){
        question = greenQuestionList[index];
    }else{
        question = validBlueQuestionList[index-36];
    }

    index++;
    if(question != null){
        $(".question-list").html(index + "、" + question.content);
        $(".question-list").attr("data-id",question.id);

        $(".question-list").attr("data-no",question.no);
        if(question.no == "10") {
            $(".select-answer").unbind().click(function () {
                if($(this).attr("data-answer") != "A"){
                    $(".select-answer10").unbind().click(function(){
                        selectState(this,function (that) {
                            saveAnswer($(".question-list").attr("data-id"), $(".question-list").attr("data-no"), $(that).attr("data-answer"), $(that).attr("data-score"));
                            $(".select1").show();
                            $(".selectB").hide();
                            $(".selectC").hide();
                            creatQuestion();
                        });
                    });
                    selectState(this,function (that) {
                        $(".select1").hide();
                        $(".select" + $(that).attr("data-answer")).show();
                    });
                }else{
                    selectState(this,function (that) {
                        saveAnswer($(".question-list").attr("data-id"), $(".question-list").attr("data-no"), $(that).attr("data-answer"), $(that).attr("data-score"));
                        creatQuestion();
                    });
                }
            });
        } else {
            $(".select-answer").unbind().click(function () {
                selectState(this,function (that) {
                    if(progressLength == index+1){
                        $(".select-answer").unbind();
                        progressChange(100);
                        saveAnswer($(".question-list").attr("data-id"), $(".question-list").attr("data-no"), $(that).attr("data-answer"), $(that).attr("data-score"));
                        saveAnswerList();
                    }else{
                        saveAnswer($(".question-list").attr("data-id"), $(".question-list").attr("data-no"), $(that).attr("data-answer"), $(that).attr("data-score"));
                        creatQuestion();
                    }
                });
            });
        }
    }
}

function selectState(that,fn){
    $(that).css("background","#7CA0D7");
    setTimeout(function(){
        if(fn != null){
            $(that).css("background","#FFFFFF");
            fn(that);
        }
    },300)
}

function progressChange(num) {
    if(num==null)
        num = ((index+1)/progressLength) * 100;
    $(".progress-bar").css("width",num +"%");
}

function saveAnswer(id, no, answer, score){
    answerList.push({questionId:id,questionNo:no,answer:answer,score:score});
    // console.info(answerList);
}

function handleValidBlueQuestionList(){
    validBlueQuestionList=[];
    var validBlueQuestionNo = [];
    handleValidBlueQuestionListFlag = true;
    var answerScore = {};
    for(var i=0;i<answerList.length;i++){
        if(answerList[i].questionNo != "10"){
            answerScore[answerList[i].questionNo] = answerList[i].score;
        }else{
            answerScore[answerList[i].answer] = answerList[i].score;
        }
    }
    for(var i=0;i<constitutionList.length;i++){
        var ct = constitutionList[i];

        var score1 = getScore(answerScore[ct.subject1]);
        var score2 = getScore(answerScore[ct.subject2]);
        var score3 = getScore(answerScore[ct.subject3]);
        if((score1 + score2 + score3) > 0){
            validBlueQuestionNo[ct.subject4] = 1;
            validBlueQuestionNo[ct.subject5] = 1;
        }
    }
    for(var i=0;i<blueQuestionList.length;i++){
        if(validBlueQuestionNo[blueQuestionList[i].no] == 1){
            validBlueQuestionList.push(blueQuestionList[i]);
        }
    }
    progressLength = 37 + validBlueQuestionList.length;
    progressChange();
    console.info(validBlueQuestionList);
}

function getScore(score) {
    return score==null?0:Number(score);
}

function saveAnswerList() {
    requestPostJsonService("/xczh/constitution/record/" + sex + "/" + birthday,JSON.stringify(answerList),function (data) {
        localStorage.setItem("healthy.result",JSON.stringify(data.resultObject));
        location.href = "./healthy-result.html";
    })
}