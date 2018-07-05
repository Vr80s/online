var gradeName ="";
var smallImgPath ="";
var description ="";
debugger;
requestGetService("/xczh/enrol/enrollmentRegulations/"+merId,data,function(data){
    if(data.success){
        var enrollmentRegulations = data.resultObject
        template.defaults.escape=false;
        enrollmentRegulations.qualification = newline(enrollmentRegulations.qualification);
        enrollmentRegulations.learningProcess = newline(enrollmentRegulations.learningProcess);
        enrollmentRegulations.rightsAndInterests = newline(enrollmentRegulations.rightsAndInterests);
        enrollmentRegulations.doctorIntroduction = newline(enrollmentRegulations.doctorIntroduction);
        enrollmentRegulations.propaganda = newline(enrollmentRegulations.propaganda);

        //分享的信息展示
        gradeName = enrollmentRegulations.title;  /*标题*/
        smallImgPath = enrollmentRegulations.posterImg;    /*img*/     /*下是--详情*/
        if(enrollmentRegulations.propaganda==null || enrollmentRegulations.propaganda==''){
            description="";
        }else{
            description = enrollmentRegulations.propaganda.stripHTML();
        }

        enrollmentRegulations.contactWay = newline(enrollmentRegulations.contactWay);

        enrollmentRegulations.startTime = enrollmentRegulations.startTime.substring(0,16);
        enrollmentRegulations.endTime = enrollmentRegulations.endTime.substring(0,16);
        enrollmentRegulations.deadline = enrollmentRegulations.deadline.substring(0,16);
        enrollmentRegulations.studyAddress = doAddress(enrollmentRegulations.studyAddress);
        $("body").html(template('inherited_introduction_temp',enrollmentRegulations));
        // console.log(enrollmentRegulations)

    }else{
        location.href="/xcview/html/apprentice/apprentice.html";
    }
},false);
// $('.general_center').html(wv+"11111111");
if(wv == 'ios'){
    $('.download').hide();
}

function newline(str){
    if(str==null) return null;
    return str.replace(/\n|\r\n/g,'<br/>');
}
function doAddress(studyAddress){
    studyAddress = studyAddress.replace("北京市-北京市","北京市");
    studyAddress = studyAddress.replace("天津市-天津市","天津市");
    studyAddress = studyAddress.replace("上海市-上海市","上海市");
    studyAddress = studyAddress.replace("重庆市-重庆市","重庆市");
    return studyAddress;
}

function apply(){
    if(authenticationCooKie()==1005){
        location.href ="/xcview/html/evpi.html";
    }else{
        location.href ='apply.html?merId='+merId;
    }
}

function cardInfo() {
    location.href = '/xcview/html/apprentice/invitation_card.html';
    // if(wv==null){
    //     location.href ='/xcview/html/apprentice/generate_card.html'
    // }else{
    //     location.href ='/xcview/html/apprentice/generate_cards.html'
    // }
}
