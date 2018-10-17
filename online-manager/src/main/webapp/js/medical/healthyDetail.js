var questionBank;
var record;
$(function(){

    $.ajax({
        type:"POST",
        url: "/medical/healthy/getQuestionList",
        data: {
            recordId: recordId
        },
        async:true,
        success: function( result ) {
            console.info(result);
            questionBank = result.resultObject;
        }
    },false);

    $.ajax({
        type:"POST",
        url: "/medical/healthy/getRecordById",
        data: {
            recordId: recordId
        },
        async:true,
        success: function( result ) {
            console.info(result);
            record = result.resultObject;
            createTable(record);
        }
    });
});

function createTable(result){
    var allConstitutionScoreList = result.allConstitutionScoreList;
    for(var i=0;i<allConstitutionScoreList.length;i++){
        createTableTr(allConstitutionScoreList[i].constitution,allConstitutionScoreList[i].score);
    }
    var str = "评测结果：";
    if(result.constitutionScoreList.length == 0){
        str += "恭喜您，近期身体状态良好，请继续保持";
    }else if(result.constitutionScoreList[0].score <= 5){
        str += "您的身体基本健康,但是有"+result.constitution+"的倾向";
    }else{
        str += "您有"+result.constitution+"的倾向";
    }
    $(".result").html(str);
}

function createTableTr(constitution,score){
    var tag = constitution.tag;
    createTableTd(constitution.subject1,tag,score);
    createTableTd(constitution.subject2);
    createTableTd(constitution.subject3);
    createTableTd(constitution.subject4);
    createTableTd(constitution.subject5);
}

function createTableTd(subject,tag,score) {
    var question = getQuestion(subject);
    if(subject==51){
        debugger;
    }

    question = getQuestionAnswer(question);
    console.info(subject)
    if(question.no=='10-2'){
        console.info(subject)
    }
    if(question.disease==null)question.disease="无";
    if(question.symptom==null)question.symptom="无";
    var str = '<tr>' +
        '<td>'+question.no+'</td>' +
        '<td>'+question.content+'</td>' +
        getAnswer(question.answer,question.sbj) +
        '<td>'+question.disease+'</td>' +
        '<td>'+question.symptom+'</td>' ;
        if(tag != null){
            str += '<td rowspan="5">'+ tag + '(' +score +'分)</td>';
        }
        str += '</tr>';

    $(".list").append(str);
}

function getQuestionAnswer(question){
    if(question.no=='34-a'||question.no=='34-b')question.no=34;
    // debugger
    var medicalConstitutionQuestionRecordDetailsList = record.medicalConstitutionQuestionRecordDetailsList;
    for(var i=0;i<medicalConstitutionQuestionRecordDetailsList.length;i++){
        if(medicalConstitutionQuestionRecordDetailsList[i].questionNo == question.no){
            question.answer = medicalConstitutionQuestionRecordDetailsList[i].answer;
            return question;
        }
    }
    return question;
}

function getQuestion(subject){
    var sbj = null;
    if(subject=='10-1'||subject=='10-2'){
        sbj = subject;
        subject = 10;
    }else if(subject=='34'){
        if(sex==1){
            subject = '34-a';
        }else{
            subject = '34-b';
        }
    }
    for(var i=0;i<questionBank.length;i++){
        var qb = questionBank[i];
        if(qb.no == subject){
            if(sbj!=null){
                debugger
                qb.sbj = sbj;
                if(sbj=='10-1'){
                    qb.symptom = '痰质清稀/咳嗽';
                }else{
                    qb.symptom = '干咳无痰/痰少/痰粘/咳嗽';
                }
            }
            return qb;
        }
    }
    return null;
}

function getAnswer(answer,sbj){
    // debugger
    if(answer=='10-1'){
        if(answer == sbj){
            return  '<td><span>A没有(0分)</span><span style="color: red;">10-1咳出清痰(2分)</span> <span>10-2干咳没有痰，或者痰很少但比较粘(2分)</span></td>';
        }
        return  '<td><span>A没有(0分)</span><span>10-1咳出清痰(2分)</span> <span>10-2干咳没有痰，或者痰很少但比较粘(2分)</span></td>';
    }else if(answer=='10-2'){
        if(answer == sbj){
            return  '<td><span>A没有(0分)</span><span>10-1咳出清痰(2分)</span> <span style="color: red;">10-2干咳没有痰，或者痰很少但比较粘(2分)</span></td>';
        }
        return '<td><span>A没有(0分)</span><span>10-1咳出清痰(2分)</span> <span>10-2干咳没有痰，或者痰很少但比较粘(2分)</span></td>';
    }else if(answer=='A'){
        return '<td><span style="color: red;">A没有(0分)</span> <span>B偶尔(1分)</span> <span>C经常(2分)</span></td>';
    }else if(answer=='B'){
        return '<td><span>A没有(0分)</span> <span style="color: red;">B偶尔(1分)</span> <span>C经常(2分)</span></td>';
    }else if(answer=='C'){
        return '<td><span>A没有(0分)</span> <span>B偶尔(1分)</span> <span style="color: red;">C经常(2分)</span></td>';
    }else if(answer == null){
        return '<td><span>A没有(0分)</span> <span>B偶尔(1分)</span> <span>C经常(2分)</span></td>';
    }
}