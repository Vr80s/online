$(function(){
    courseList(1);
    courseCollectionList(1);
    courseResourceList(1);
    $(".course_search").click(function(){
        courseList(1);
    })
    $(".course_collection_search").click(function(){
        courseCollectionList(1);
    })
    $(".course_resource_search").click(function(){
        courseResourceList(1);
    })
});


function courseList(current){
    var courseForm;
    var multimediaType;
    var courseType = $("#course_type").val();
    var courseName = $("#course_name").val();
    if(courseType == 1){
        courseForm = 1;
    }else if(courseType == 2){
        courseForm = 2;
        multimediaType = 1;
    }else if(courseType == 3){
        courseForm = 2;
    }else if(courseType == 4){
        courseForm = 2;
        multimediaType = 2;
    }
    var url ="/anchor/course/getCourseApplyList?size=10&current="+current;
    if(courseForm!=null){
        url += "&courseForm="+courseForm;
    }
    if(multimediaType!=null){
        url += "&multimediaType="+multimediaType;
    }
    if(courseName!=null){
        url += "&title="+courseName;
    }
    debugger
    RequestService(url, "get", null, function(data) {
        $("#course_list").html(template('course_list_tpl', data.resultObject));
        debugger
        //每次请求完数据就去渲染分页部分
        if (data.resultObject.pages > 1) { //分页判断
            $(".not-data").remove();
            $(".course_pages").css("display", "block");
            $(".course_pages .searchPage .allPage").text(data.resultObject.pages);
            $("#Pagination").pagination(data.resultObject.pages, {
                num_edge_entries: 1, //边缘页数
                num_display_entries: 4, //主体页数
                current_page:current-1,
                callback: function (page) {
                    //翻页功能
                    courseList(page+1);
                }
            });
        } else {
            $(".course_pages").css("display", "none");
        }
    });
}

function courseCollectionList(current){
    var multimediaType = $("#course_collection_type").val();
    var courseName = $("#course_collection_name").val();
    var url ="/anchor/course/getCollectionApplyList?size=10&current="+current;
    if(multimediaType!=null && multimediaType!=""){
        url += "&multimediaType="+multimediaType;
    }
    if(courseName!=null && courseName!=""){
        url += "&title="+courseName;
    }
    debugger
    RequestService(url, "get", null, function(data) {
        $("#collection_list").html(template('course_collection_list_tpl', data.resultObject));
        debugger
        //每次请求完数据就去渲染分页部分
        if (data.resultObject.pages > 1) {
            //分页判断
            $(".not-data").remove();
            $(".collection_pages").css("display", "block");
            $(".collection_pages .searchPage .allPage").text(data.resultObject.pages);
            $("#Pagination_collection").pagination(data.resultObject.pages, {
                num_edge_entries: 1, //边缘页数
                num_display_entries: 4, //主体页数
                current_page:current-1,
                callback: function (page) {
                    //翻页功能
                    courseCollectionList(page+1);
                }
            });
        } else {
            $(".collection_pages").css("display", "none");
        }
    });
}

function courseResourceList(current){
    var url ="/anchor/course/getCourseResourceList?size=10&current="+current;
    debugger
    RequestService(url, "get", null, function(data) {
        $("#resource_list").html(template('course_resource_list_tpl', data.resultObject));
        debugger
        //每次请求完数据就去渲染分页部分
        if (data.resultObject.pages > 1) {
            //分页判断
            $(".not-data").remove();
            $(".resource_pages").css("display", "block");
            $(".resource_pages .searchPage .allPage").text(data.resultObject.pages);
            $("#Pagination_resource").pagination(data.resultObject.pages, {
                num_edge_entries: 1, //边缘页数
                num_display_entries: 4, //主体页数
                current_page:current-1,
                callback: function (page) {
                    //翻页功能
                    courseResourceList(page+1);
                }
            });
        } else {
            $(".collection_pages").css("display", "none");
        }
    });
}


function showResourceList(){
    //底部变化
    $('#ziyuan_bottom').addClass('hide');
    $('#ziyuan_bottom2').removeClass('hide');
}
$(function () {
    //添加资源
    $('#ziyuan_bottom .baocun #submit').click(function(){
        if(validateResource()){
            debugger
            var data = {};
            data.title = $.trim($('#ziyuan_bottom .zhuanlan_title').val());
            data.resource = $.trim($('#ccId').val());
            data.multimediaType = $("input[name='resource_multimediaType']:checked").val();
            $.ajax({
                type: "post",
                url: bath + "/anchor/course/saveCourseResource",
                data:JSON.stringify(data),
                contentType:"application/json",
                async: false,
                success: function(data) {
                    console.log(data);
                    if(data.success === true) {
                        showResourceList();
                    } else {
                        alert(data.errorMessage())
                    }
                }
            });
        }
    })
});
function validateResource(){
    var title = $.trim($('#ziyuan_bottom .zhuanlan_title').val());
    var resource = $.trim($('#ccId').val());
    //资源标题
    if(title == ''){
        $('#ziyuan_bottom .warning0').removeClass('hide');
        return false;
    }else{
        $('#ziyuan_bottom .warning0').addClass('hide');
    }

    //资源文件
    if(resource == ''){
        $('#ziyuan_bottom .warning1').html("请先上传资源文件");
        $('#ziyuan_bottom .warning1').removeClass('hide');
        return false;
    }else  if(!uploadfinished){
        $('#ziyuan_bottom .warning1').html("资源文件未上传完成");
        $('#ziyuan_bottom .warning1').removeClass('hide');
        return false;
    }else{
        $('#ziyuan_bottom .warning1').addClass('hide');
    }
    return true;
}