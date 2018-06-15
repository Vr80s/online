$(function(){
    debugger
    requestGetService("/xczh/enrol/enrollmentRegulations/"+merId,data,function(data){
        $(".recruit").html(data.resultObject.regulations)
        $(".main_title").html(data.resultObject.title)
	});

})
