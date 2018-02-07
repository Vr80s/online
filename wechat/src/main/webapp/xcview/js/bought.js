
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
            $(".bought_main").html(template('bought_main',{items:data.resultObject.records}));

        }else{
            alert(data.errorMessage);
        }
    });
}
