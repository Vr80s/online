$(function(){
    debugger
    requestGetService("/xczh/enrol/medicalEntryInformation/"+merId,data,function(data){
        data.resultObject.education = getEducation(data.resultObject.education);
		$("body").html(template('entry_information_temp',data.resultObject))
	});

})

function getEducation(education){
    if(education == 5){
        return "本科";
    }
    if(education == 6){
        return "硕士";
    }
    if(education == 7){
        return "博士";
    }
    if(education == 0){
        return "无";
    }
}
