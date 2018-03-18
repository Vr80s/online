/**
 * Created by admin on 2016/11/3.
 */
$(function () {
    if (courseId == "undefined") {
        var courseId = 142;
    }
//解析url地址
    var ourl = document.location.search;
    var apams = ourl.substring(1).split("&");
    var arr = [];
    for (i = 0; i < apams.length; i++) {
        var apam = apams[i].split("=");
        arr[i] = apam[1];
        var courseId = arr[0];
        var courseType=arr[1];
        var fre=arr[2];
    };
    $(".header_left .path a").each(function() {
        if($(this).text() == "云课堂") {
            $(this).addClass("select");
        } else {
            $(this).removeClass("select");
        }
    });
    var courseIntroduction = '{{each items}}' +
        '{{if $index==0}}'+
        '<li class="select" data-courseIntroId="{{$value.courseId}}" data-id="{{$value.id}}" data-nextId="{{$value.nextId}}" data-index="{{$index}}">' +
        '<em class="select0"></em>' +
        '{{else}}'+
        '<li  data-courseIntroId="{{$value.courseId}}" data-id="{{$value.id}}" data-nextId="{{$value.nextId}}" data-index="{{$index}}">' +
        '<em></em>' +
        '{{/if}}'+
        '<span>{{$value.courseTitle}}</span>' +
        '</li>' +
        '{{/each}}';
    var emptyDefaul =
        "<div class='page-no-result'>" +
        "<img src='../images/personcenter/my_nodata.png'>" +
        "<div class='no-title'>暂无数据</div>" +
        "</div>";
    var emptyDefau2 =
        "<div class='page-no-result1'>" +
        "<img src='../images/personcenter/my_nodata.png'>" +
        "<div class='no-title'>暂无数据</div>" +
        "</div>";
    var id;
    var nextId;
    //加载数据
    RequestService("/course/findCourseOrderById","GET",{courseId:courseId},function(data){
        $(".mycourseName").text(data.resultObject.courseName);
    });
    RequestService('/course/getCourseDescriptions', "GET", {courseId: courseId}, function (data) {
        if(data.resultObject==null||data.resultObject==""){
            $(".courseIntroductionPage-items").html(template.compile(emptyDefaul));
            $(".courseIntroductionPage-introduce").html(template.compile(emptyDefau2));
            $(".courseIntroductionPage-introduce-btn").css("display","none");
        }else{
            $(".courseIntroductionPage-items").html(template.compile(courseIntroduction)({
                items:data.resultObject
            }));
            if(data.resultObject==null || data.resultObject==""){
                $(".courseIntroductionPage-introduce").html(template.compile(emptyDefau2));
            }else{
                $(".courseIntroductionPage-introduce").html(data.resultObject[0].courseContent);
            }
            $(".menuName").text(data.resultObject[0].menuName);
            $(".courseIntroductionPage-introduce-btn").css("display","block");
            getId();
            //左边单击事件
            $(".courseIntroductionPage-items li").on("click",function(){
                id=$(this).attr("data-id");
                courseId=$(this).attr("data-courseIntroId");
                nextId = $(this).attr("data-nextId");
                $(this).addClass("select").siblings().removeClass("select");
                $(".courseIntroductionPage-items li").find("em").removeClass();
                $(this).find("em").addClass("select"+ $(this).index()).siblings();
                $(".courseIntroductionPage-introduce").empty();
                changeTab();
                RequestService("/course/getCourseDescriptionById","GET",{id:id,courseId:courseId},function(data){
                    $(".courseIntroductionPage-introduce").html(data.resultObject.courseContent);
                })
            });
            //下一集的按钮
            $(".courseIntroductionPage-introduce-btn").click(function(){
                $(".courseIntroductionPage-introduce").empty();
                if(nextId == "" || nextId==null){
                    $(".courseIntroductionPage-introduce-btn").css("display","none");
                    $(".courseIntroductionPage-introduce-try").css("display","block");
                    var index=$(".courseIntroductionPage-items").find(".select").next().attr("data-index");
                    $(".courseIntroductionPage-items").find(".select").next().addClass("select").siblings().removeClass("select");
                    $(".courseIntroductionPage-items li").find("em").removeClass();
                    $(".courseIntroductionPage-items").find(".select").find("em").addClass("select"+index);
                }else{
                    $(".courseIntroductionPage-introduce-btn").css("display","block");
                    $(".courseIntroductionPage-introduce-try").css("display","none");
                    var index=$(".courseIntroductionPage-items").find(".select").next().attr("data-index");
                    $(".courseIntroductionPage-items").find(".select").next().addClass("select").siblings().removeClass("select");
                    $(".courseIntroductionPage-items li").find("em").removeClass();
                    $(".courseIntroductionPage-items").find(".select").find("em").addClass("select"+index);
                    changeTab();
                }
            });
            if(nextId == "" || nextId==null){
                $(".courseIntroductionPage-introduce-btn").css("display","none");
                $(".courseIntroductionPage-introduce-try").css("display","block");
            }else{
                $(".courseIntroductionPage-introduce-btn").css("display","block");
                $(".courseIntroductionPage-introduce-try").css("display","none");
            }
            if(data.resultObject.length==1){
                $(".courseIntroductionPage-introduce-btn").css("display","none");
                $(".courseIntroductionPage-introduce-try").css("display","block");
            }else{
                $(".btn-content").text(data.resultObject[1].courseTitle);
            }
        }

        //试听看看跳转
        if(fre==1){//1是免费
            $(".courseIntroductionPage-btn,.courseIntroductionPage-introduce-try").attr("href","/web/html/freeCourseDetailPage.html?courseId="+courseId+'&courseType='+courseType+'&free='+fre);
        }else{
//            if(courseType==0){
//                $(".courseIntroductionPage-btn,.courseIntroductionPage-introduce-try").attr("href","/web/html/CourseDetail.html?courseId="+courseId+'&courseType='+courseType+'&free='+fre);
//            }else{
                $(".courseIntroductionPage-btn,.courseIntroductionPage-introduce-try").attr("href","/web/html/payCourseDetailPage.html?courseId="+courseId+'&courseType='+courseType+'&free='+fre);
//            }
        }
    });
    function getId(){
        $(".courseIntroductionPage-items li").each(function(){
            var $this=$(this);
            if ($this.hasClass("select")) {
                courseId=$this.attr("data-courseIntroId");
                id=$this.attr("data-id");
                nextId=$this.attr("data-nextId");
            }
        })
    }
    function changeTab(){
        getId();
        if(nextId == "" || nextId==null){
            $(".courseIntroductionPage-introduce-btn").css("display","none");
            $(".courseIntroductionPage-introduce-try").css("display","block");
        }else{
            $(".courseIntroductionPage-introduce-btn").css("display","block");
            $(".courseIntroductionPage-introduce-try").css("display","none");
        }
        $(".courseIntroductionPage-items li").each(function(){
            if ($(this).hasClass("select")) {
                $(".btn-content").text($(this).next().text());
                id=$(this).attr("data-id");
            }
        })
        RequestService("/course/getCourseDescriptionById","GET",{id:id,courseId:courseId},function(data){
            $(".courseIntroductionPage-introduce").html(data.resultObject.courseContent);
        })
    }
})
