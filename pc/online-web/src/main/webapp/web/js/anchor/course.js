$(function(){
    initResource(1);
    courseCollectionList(1);
    courseResourceList(1);
    courseLiveList(1);
    initMenu();
    initVhallInfo();
    $(".course_search").click(function(){
        courseList(1);
    })
    $(".course_collection_search").click(function(){
        courseCollectionList(1);
    })
    $(".course_resource_search").click(function(){
        courseResourceList(1);
    })
    $(".addCourse").click(function(){
        saveCourse();
    });
});

/**
 * Description：课程列表
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/2 0002 下午 9:09
 **/
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
        courseForm = 3;
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

/**
 * Description：新增课程
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/3 0003 下午 5:56
 **/
function saveCourse(){
    var course = getCourseData();
    debugger
    if(verifyCourse(course)){
        $.ajax({
            type: "post",
            url: bath + "/anchor/course/saveCourseApply",
            data:JSON.stringify(course),
            contentType:"application/json",
            async: false,
            success: function(data) {
                console.log(data);
                if(data.success === true) {
                    $(".select_list .courseP").click();
                    resetCourseForm();
                } else {
                    alert(data.errorMessage)
                }
            }
        });
    }
}

function resetCourseForm(){
    document.getElementById("courseForm").reset();
    UE.getEditor('editor').setContent('');
    UE.getEditor('editor_cd').setContent('');
    $("#courseImg").html("");
    $("#citys").empty();
    showCourseAttribute(1);
    initResource(1);
}
/**
 * Description：获取新增课程所有参数
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/3 0003 下午 5:54
 **/
function getCourseData(){
    var course = {};
    course.title = $.trim($('.course_title').val());
    course.subtitle = $.trim($('.course_subtitle').val());
    course.imgPath = $.trim($('#courseImg img').attr('src'));
    course.lecturer = $.trim($('.course_lecturer ').val());
    course.lecturerDescription = getLDContent();
    course.courseForm = $("input[name='course_form']:checked").val();
    course.courseMenu = $.trim($('#menu_select').val());
    course.price = $.trim($('.course_price').val());
    course.courseDetail = getCDContent();
    course.courseLength = $.trim($('.course_length').val());

    if(course.courseForm==1){
        course.startTime = $.trim($('.course_start_time').val());
    }else if(course.courseForm==2){
        course.resourceId = $('.course_resource').val();
        course.multimediaType = $("input[name='course_multimedia_type']:checked").val();
    }else{
        course.startTime = $.trim($('.course_start_time').val());
        course.endTime = $.trim($('.course_end_time').val());
        course.province = $(".course_province").find("option:selected").text();
        course.city = $(".course_city").find("option:selected").text();
        course.address = $(".course_address").val();
        if(course.province==''||course.city==''||course.address==''){
            course.address='';
        }else{
            course.address = course.province+"-"+course.city+" "+course.address;
        }
    }

    return course;
}

/**
 * Description：校验新增课程参数
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/2 0002 下午 9:36
 **/
function verifyCourse(course){
    //课程标题
    if(course.title == ''){
        $('.warning_course_title').removeClass('hide');
        return false;
    }else{
        $('.warning_course_title').addClass('hide');
    }
    //副标题
    if(course.subtitle == ''){
        $('.warning_course_subtitle').removeClass('hide');
        return false;
    }else{
        $('.warning_course_subtitle').addClass('hide');
    }
    //封面图
    if(course.imgPath == ''){
        $('.warning_course_imgPath ').removeClass('hide');
        return false;
    }else{
        $('.warning_course_imgPath').addClass('hide');
    }
    //主播姓名
    if(course.lecturer == ''){
        $('.warning_course_lecturer').removeClass('hide');
        return false;
    }else{
        $('.warning_course_lecturer').addClass('hide');
    }
    //主播介绍
    if(course.lecturerDescription == ''){
        $('.warning_course_lecturer_description').removeClass('hide');
        return false;
    }else{
        $('.warning_course_lecturer_description').addClass('hide');
    }
    //请选择开课时间
    if(course.startTime == '' && (course.courseForm==1||course.courseForm==3)){
        $('.warning_course_start_time').removeClass('hide');
        return false;
    }else{
        $('.warning_course_start_time').addClass('hide');
    }
    if((course.courseForm==1||course.courseForm==3)){
        var startTime =  new Date(course.startTime.replace(/-/g,"/"));
        if(startTime<new Date()){
            $('.warning_course_time1').removeClass('hide');
            return false;
        }else{
            $('.warning_course_time1').addClass('hide');
        }
    }
    //请选择结课时间
    if(course.endTime == '' && course.courseForm==3){
        $('.warning_course_end_time').removeClass('hide');
        return false;
    }else{
        $('.warning_course_end_time').addClass('hide');
    }
    if(course.startTime != ''&&course.endTime != ''){
        debugger
        var startTime =  new Date(course.startTime.replace(/-/g,"/"));
        var endTime =  new Date(course.endTime.replace(/-/g,"/"));
        if(startTime>endTime){
            $('.warning_course_time').removeClass('hide');
            return false;
        }else{
            $('.warning_course_time').addClass('hide');
        }
    }
    //授课地址
    if(course.address == '' && course.courseForm==3){
        $('.warning_course_address').removeClass('hide');
        return false;
    }else{
        $('.warning_course_address').addClass('hide');
    }
    //时长
    if(course.courseLength == ''){
        $('.warning_course_length').removeClass('hide');
        return false;
    }else{
        $('.warning_course_length').addClass('hide');
    }
    //价格
    if(course.price == ''){
        $('.warning_course_price').removeClass('hide');
        return false;
    }else{
        $('.warning_course_price').addClass('hide');
    }
    //资源
    if(course.resourceId == '' && course.courseForm==2){
        $('.warning_course_resource').removeClass('hide');
        return false;
    }else{
        $('.warning_course_resource').addClass('hide');
    }
    //课程详情
    if(course.courseDetail == ''){
        $('.warning_course_details').removeClass('hide');
        return false;
    }else{
        $('.warning_course_details').addClass('hide');
    }
    return true;
}
function confirmCourseSale(state,id){
    if(state==1){
        $(".saleText").html("确认上架该课程？");
    }else{
        $(".saleText").html("确认下架该课程？");
    }
    $("#saleState").val(state);
    $("#saleCourseId").val(id);
    $(".yes_no").show();
}

/**
 * Description：更改上下架状态
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/3 0003 下午 7:09
 **/
function changeSaleState(){
    var state = $("#saleState").val();
    var courseId = $("#saleCourseId").val();
    $.ajax({
        type: "post",
        url: bath + "/anchor/course/changeSaleState",
        data:"courseApplyId="+courseId+"&state="+state,
        async: false,
        success: function(data) {
            console.log(data);
            if(data.success === true) {
                $(".select_list .courseP").click();
                resetCourseForm();
            } else {
                alert(data.errorMessage)
            }
        }
    });
}

/**
 * Description：直播列表
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/3 0003 下午 4:31
 **/
function courseLiveList(current){
    var url ="/anchor/course/getLiveApplyList?size=10&current="+current;
    debugger
    RequestService(url, "get", null, function(data) {
        $("#course_live_list").html(template('course_live_tpl', data.resultObject));
        debugger
        //每次请求完数据就去渲染分页部分
        if (data.resultObject.pages > 1) { //分页判断
            $(".not-data").remove();
            $(".course_live_pages").css("display", "block");
            $(".course_live_pages .searchPage .allPage").text(data.resultObject.pages);
            $("#Pagination_live").pagination(data.resultObject.pages, {
                num_edge_entries: 1, //边缘页数
                num_display_entries: 4, //主体页数
                current_page:current-1,
                callback: function (page) {
                    //翻页功能
                    courseLiveList(page+1);
                }
            });
        } else {
            $(".course_live_pages").css("display", "none");
        }
    });
}

/**
 * Description：开始直播
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/3 0003 下午 8:46
 **/
function startLive(id) {
    RequestService("/anchor/course/getWebinarUrl?webinarId="+id, "get", null, function(data) {
        window.open(data.resultObject);
    });
}
function previewLive(id) {
        window.open("http://e.vhall.com/"+id);
}

/**
 * Description：专辑列表
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/2 0002 下午 9:09
 **/
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

/**
 * Description：资源列表
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/2 0002 下午 9:09
 **/
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
//
// function confirmDeleteResource(resourceId){
//
// }
function deleteResource(resourceId){
    $.ajax({
        type: "post",
        url: bath + "/anchor/course/deleteCourseResource",
        data:"resourceId="+resourceId,
        async: false,
        success: function(data) {
            console.log(data);
            if(data.success === true) {
                alert(data.resultObject);
                courseResourceList(1);
            } else {
                alert(data.errorMessage)
            }
        }
    });
}
//点击选择资源
function resourcePre(){
    $('.a_resource').show();
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

/**
 * Description：初始化资源下拉框
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/2 0002 下午 9:09
 **/
function initResource(multimediaType){
    RequestService("/anchor/course/getAllCourseResources?multimediaType="+multimediaType, "get", null, function(data) {
        var resources = data.resultObject;
        var str="<option value=''>未选择</option>";
        if(resources.length>0){
            for(var i in resources){
                str += "<option value='"+resources[i].id+"'>"+resources[i].title+"</option>";
            }
        }
        $("#id_select").html(str);
        $('.selectpicker').selectpicker('refresh');
        $('.selectpicker').selectpicker({
            'selectedText': 'cat',size:10
        });
    });
}

/**
 * Description：初始化菜单
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/3 0003 上午 11:07
 **/
function initMenu(){
    RequestService("/menu/getAllMenu?type=1", "get", null, function(data) {
        var menus = data.resultObject;
        var str="";
        for(var i in menus){
            if(menus[i].id!=1){
                str += "<option value='"+menus[i].id+"'>"+menus[i].name+"</option>"
            }
        }
        $("#menu_select").html(str);
    });
}

function initVhallInfo(){
    RequestService("/anchor/course/getVhallInfo", "get", null, function(data) {
        $("#vhallAccount").html(data.resultObject.vhallAccount);
        $("#vhallPassword").html(data.resultObject.password);
    });
}

//上传图片调用的接口
function picUpdown(baseurl,imgname){
    RequestService("/medical/common/upload", "post", {
        image: baseurl,
    }, function(data) {
        console.log(data);
        $('#'+imgname+'').html('<img src="'+data.resultObject+'" style="width: 100%;height: 100%" >');
    })
}

$(function(){
    /**
     * Description：课程封面
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/2/2 0002 下午 9:12
     **/
    $('#courseImgPath').on('change',function(){
        debugger
        var reader=new FileReader();
        reader.onload=function(e){
            picUpdown(reader.result,'courseImg');
        }
        reader.readAsDataURL(this.files[0])
    })

})