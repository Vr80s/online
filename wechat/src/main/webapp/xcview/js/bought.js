
$(function(){
    getBoughtList()
});


//获取已购课程
function getBoughtList() {
    requestService("/xczh/manager/freeCourseList",{
        pageNumber:1,
        pageSize:10
    },function(data) {
        if(data.success==true){
        	if(data.resultObject.records.length==0 || data.resultObject.records.length==''){
        		$(".bought").hide();
        		$(".no_class").show();
        	}else{
        		$(".no_class").hide();
        		$(".bought").css({"padding-top":"0.3rem"});
        		
        	}
        	
            $(".bought_main").html(template('bought_main',{items:data.resultObject.records}));

        }else{
            alert(data.errorMessage);
        }
    });
}
