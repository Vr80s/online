$(function(){
    courseVodList(1);
});

function courseVodList(current){
    RequestService("/anchor/course/getCourseApplyList?size=10&current="+current, "get", null, function(data) {
        $("#course_vod_list").html(template('course_vod_list_tpl', data.resultObject));
        debugger
        //每次请求完数据就去渲染分页部分
        if (data.resultObject.pages > 1) { //分页判断
            $(".not-data").remove();
            $(".course_vod_content .pages").css("display", "block");
            $(".course_vod_content .pages .searchPage .allPage").text(data.resultObject.pages);
                $("#Pagination").pagination(data.resultObject.pages, {
                    num_edge_entries: 1, //边缘页数
                    num_display_entries: 4, //主体页数
                    current_page:current-1,
                    callback: function (page) { //翻页功能
                        courseVodList(page+1);
                        // alert(page);
                    }
                });
        } else {
            $(".course_vod_content .pages").css("display", "none");
        }
    });
}