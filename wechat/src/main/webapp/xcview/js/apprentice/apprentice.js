$(function(){
    var tokenStr = "";
    if(data.token!=null&&data.appUniqueId!=null){
        tokenStr = "&token="+data.token+"&appUniqueId="+data.appUniqueId;
    }
    data.page=1;
    data.size=1000
    requestGetService("/xczh/enrol/enrollmentRegulations",data,function(data){
        var enrollmentRegulationsList = data.resultObject.records;
        for(var i = 0;i < enrollmentRegulationsList.length;i++){
            enrollmentRegulationsList[i].tokenStr = tokenStr;
        }
		$(".apprentice_list").html(template('apprentice_list_tmp',{items:enrollmentRegulationsList}))
	});

})
