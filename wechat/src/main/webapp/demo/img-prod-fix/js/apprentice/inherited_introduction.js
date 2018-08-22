var gradeName ="";
var smallImgPath ="";
var description ="";
// debugger;
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
        smallImgPath = enrollmentRegulations.coverImg;    /*img*/     /*下是--详情*/
        if(enrollmentRegulations.ceremonyAddress==null || enrollmentRegulations.ceremonyAddress==''){
            description="";
        }else{
            description = enrollmentRegulations.ceremonyAddress.stripHTML();
        }

        enrollmentRegulations.contactWay = newline(enrollmentRegulations.contactWay);

        enrollmentRegulations.startTime = enrollmentRegulations.startTime.substring(0,16);
        enrollmentRegulations.endTime = enrollmentRegulations.endTime.substring(0,16);
        enrollmentRegulations.deadline = enrollmentRegulations.deadline.substring(0,16);
        enrollmentRegulations.studyAddress = doAddress(enrollmentRegulations.studyAddress);
        $("body").html(template('inherited_introduction_temp',enrollmentRegulations));
        // console.log(enrollmentRegulations)

        var obj =  data.resultObject;
        var startStr =  data.resultObject.deadline;
        if(obj!=null && startStr!=null){
            
             //兼容ios和安卓了
             var deadline = startStr.replace(/\-/g, "/");
             setInterval(timer, 1000);
             function timer() {
                //设置结束的时间
                var endtime = new Date(deadline);
                //设置当前时间
                var now = new Date();
                //得到结束与当前时间差 ： 毫秒
                var t = endtime.getTime() - now.getTime();
                
                if (t > 0) {
                //得到剩余天数
                var tian = Math.floor(t / 1000 / 60 / 60 / 24);
                //得到还剩余的小时数（不满一天的小时）
                var h = Math.floor(t / 1000 / 60 / 60 % 24);
                //得到分钟数
                var m = Math.floor(t / 1000 / 60 % 60);
                //得到的秒数
                var s = Math.floor(t / 1000 % 60);
                var str = "报名截止时间：<span class='times'>" + tian + "</span> 天 <span class='times'>" + h + "</span> 小时  <span class='times'>" + m + "</span> 分 <span class='times'>" + s + "</span> 秒";
                $("#box1").html(str);
                $("#box1").show();
                } else {
                    //clearInterval(timer); //这里可以添加倒计时结束后需要执行的事件 
                    $("#box1").html("报名时间已结束");
                    $("#box1").hide();
                    // 底部修改--报名已结束
                    $(".apply").attr("onclick","apply();");
                    $(".apply").removeAttr("onclick");
                    $(".apply").css("background","#bbb");
                    $(".apply").html("报名已结束");
                }
            }
        
        }


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

function cardInfo() {
    location.href = '/xcview/html/apprentice/invitation_card.html';
    // if(wv==null){
    //     location.href ='/xcview/html/apprentice/generate_card.html'
    // }else{
    //     location.href ='/xcview/html/apprentice/generate_cards.html'
    // }
}

function apply() {
    requestGetService("/xczh/set/check",data,function(data){
        if(data.success){
            location.href='apply.html?merId=' + merId;
        }
    })
}

/*function share_back(){
    if(isNotBlank(wv)){
        location.href='/xcview/html/apprentice/apprentice.html'
    }else{
        common_share_back();
    }
}*/
function share_back(){
    if(isNotBlank(wv)){
        location.href='/xcview/html/apprentice/apprentice.html'
    }else{
        common_share_back();
    }
}