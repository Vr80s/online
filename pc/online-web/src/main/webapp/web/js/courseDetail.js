/**
 * Description：统一课程详情页面
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date:  上午 10:52
 **/
if(courserId=="undefined"){
    var courserId=142;
}
//解析url地址
var ourl = document.location.search;
var apams = ourl.substring(1).split("&");
var arr = [];
for (i = 0; i < apams.length; i++) {
    var apam = apams[i].split("=");
    arr[i] = apam[1];
    var courserId = arr[0];
    var courseType=arr[1];
    var fre= arr[2];
};
template.helper('getId',function(courId){{
    if(courId==239){
        return true;
    }else{
        return false;
    }
}});
template.helper("stuEvluatStars", function(num) {
    var start = "";
    for(var i = 0; i < num; i++) {
        start += '<i class="iconfont icon-shoucang"></i>';
    }
    return start;
});
var collection;
window.onload=function(){
    $(".header_left .path a").each(function() {
        if($(this).text() == "云课堂") {
            $(this).addClass("select");
        } else {
            $(this).removeClass("select");
        }
    });
    $('.path .classroom').addClass('select');
    template.helper('href', function (num) {
        if (num != "") {
            return ''+bath+'/web/courseDetail/' + num;
        } else {
            return 'javascript:;';
        }
    });
    /*相关课程*/
    var relativeCourse =
        '<div class="relative-course-top clearfix">' +
        '<span>推荐课程</span>' +
        '<span class="by-the-arrow">' +
        '<span class="curCount currentLunbo">1</span>' +
        '<span class="curCount">/</span>' +
        '<span class="curCount allLunbo">1</span>' +
        '<span class="prev" id="prev"></span>' +
        '<span class="next" id="next"></span>' +
        '</span>' +
        '</div>' +
        "<div class='relativeAnsNoData'>" +
        "<img src='../images/ansandqus/my_nodata.png'/>" +
        "<p>暂无数据</p>" +
        "</div>"+
        '<div class="relative-course-bottom slide-box clearfix">' +
        '<div id="box" class="slideBox clearfix">' +
        '<ul class="course boxContent clearfix">' +
        '{{each item as $value i}}' +
        "<li>" +
        '{{#indexHref($value.description_show,$value.free,$value.id,$value.scoreName)}}'+
        '{{#qshasImg($value.smallImgPath)}}' +
        '{{#online($value.multimediaType)}}'+
        '<div class="detail">' +
        '<p class="title" data-text="{{$value.courseName}}" title="{{$value.courseName}}">{{$value.courseName}}</p>' +
        '<p class="info clearfix">' +
        '<span>' +
        '{{if $value.free == true}}' +
        '<span class="pricefree">免费</span>' +
        '{{else}}' +
        '<i>￥</i><span class="price">{{$value.currentPrice}}</span><del><i class="price1">￥</i>{{$value.originalCost}}</del>' +
        '{{/if}}' +
        '</span>' +
        '<span class="stuCount"><img src="/web/images/studentCount.png" alt=""/><span class="studentCou">{{$value.learndCount}}</span></span>' +
        '</p>' +
        '</div>' +
        '</a>' +
        "</li>" +
        '{{/each}}' +
        '</ul>' +
        '</div></div>';

    var modal =
        '<div class="rTips"></div>' +
        '<div class="sign-up-title">' +
        '<span class="sign-up-title-left">报名课程</span>' +
        '<img src="../images/alter.png">' +
        '</div>' +
        '<div class="sign-up-body">' +
        '<div class="sign-up-body-top">' +
        '<img src="{{bigImgPath}}"/>' +
        '<div class="sign-up-body-top-right">' +
        '<p class="sign-up-body-top-right-name">{{courseName}}</p>' +
        '<p class="sign-up-body-top-right-body dot-ellipsis" title="{{description}}">{{description}}</p>' +
        '</div>' +
        '</div>' +
        '<div class="sign-up-body-bottom">' +
        '{{if free == true}}' +
        '<p>价格：<span class="mianfei">免费</span></p>' +
        '</div>' +
        '</div>' +
        '<div class="sign-up-success">' +
        '<img src="/web/images/130/video_finish40.png"/></br>'+
        '<span class="signUp-courseName">{{courseName}}</span>'+
        '<span>课程报名成功</span>'+
        '</div>'+
        '<a class="gotengxun" href="javascript:;">' +
        '确认报名' +
        '</a>' +
        '<a class="baomingSucces" href="/web/html/video.html?courseId='+courserId+'" >' +
        '立即学习' +
        '</a>' +
        '{{else}}' +
        '<p>价格：<span>￥{{currentPrice}}</span><del>￥{{originalCost}}</del></p>' +
        '</div>' +
        '<a class="gotengxun" href="javascript:;>' +
        '去购买' +
        '</a>' +
        '</div>' +
        '{{/if}}';

    var problem =
        '{{each item as p}}' +
        '<p class="questionName" style="padding-right: 30px;">{{p.questionName}}</p>' +
        '<p class="problem-answers" style="padding-right: 30px;"><span>回答：</span>{{p.answers}}</p>' +
        '{{/each}}'
    var haoping = '<div class="good-reputation">' +
        '<div class="good-reputation-count">' +

        '<span class="totalPeople">(<span class="totalCount"></span>条评论)</span>' +
        ' </div> </div>';

    var stuEvalutation = '<div class="studentEvaluate">' +
        '{{each item}}' +
        ' <div class="good-repuBox clearfix">' +
        '<div class="repuImg">' +
        '{{#hasImg($value.smallPhoto)}}' +
        '<span class="repuName" title="{{$value.userName}}">{{$value.userName}}</span>' +
        '</div>' +
        '<div class="good-detail-info">' +
        '<div class="starts">' +
        '{{#stuEvluatStars($value.starLevel)}}' +
        '</div>' +
        '<div class="reputationContent">{{$value.content}}</div>' +
        '<div class="repuationRelatInfo clearfix">' +
        '<div class="repuTime">时间：{{dataSub($value.createTime)}}</div>' +
        // '<div class="repuOrigin">来源：{{$value.videoName}}</div>' +
        '<div class="repuHitZan">' +
        '<i class="iconfont icon-zan"></i><span class="repuHitZanCount">{{$value.praiseSum}}</span>' +
        '</div>' +
        '</div>' +
        '{{if $value.response!=null && $value.response!=""}}' +
        '<div class="releaseOffice"><span class="office_a">熊猫中医回复：</span><p class="office_b">{{$value.response}}</p><span class="office_c">{{dataSub($value.response_time)}}</span></div>' +
        '{{/if}}' +
        '</div>' +
        '</div>' +
        '{{/each}}' +
        '</div>';
    var teacher =
        '<div style="width:110%;overflow:hidden">'+
        '{{each item as i}}' +
        '<div class="teacher">' +
        '<img src="{{i.headImg}}" />' +
        '<div class="teacher-text">' +
        '<p class="teacher-name">{{i.name}}</p>' +
        '<p class="teacher-company">熊猫中医</p>' +
        '<p class="teacher-introduction" style="height: 170px;" title="{{i.description}}">{{i.description}}</p>' +
        '</div>' +
        '</div>' +
        '{{/each}}'+
        "</div>";

    var emptyDefaul =
        "<div class='page-no-result'>" +
        "<img src='../images/personcenter/my_nodata.png'>" +
        "<div class='no-title'>暂无数据</div>" +
        "</div>";
    var free;
    var courseDetail;
    $(".baomingSucces").attr("href","/web/html/video.html?courseId="+courserId);
    RequestService("/course/getCourseById", "POST", {
        courserId:courserId
    }, function(data) {
        $("#NoShowIntroduct").css("display", "block");
        courseDetail = data.resultObject;
        collection = data.resultObject.collection;
        if(data.resultObject && data.resultObject.collection == true){
            $('.course-outline').removeClass('hide')
            $('.collection-course').removeClass('hide')
        }else{
            $('.collection-course').addClass('hide')
            $('.course-outline').addClass('hide')
        }
        free=data.resultObject.free;
        document.title=data.resultObject.courseName+" - 熊猫中医云课堂";
        var templateStr = template('course_detail_title_tpl',data.resultObject);
        $('.bigpic').html(templateStr);

        // $(".bigpic-body-btn .purchase").click(function(){
        //     window.location.reload;
        // })
        $(".sign-up,.sign-up1").click(function(){
            var apply = $(this).attr("data-apply");
            RequestService("/online/user/isAlive", "GET","", function(data) {
                if(!data.success) {
                    localStorage.username = null;
                    localStorage.password = null;
                    if($(".login").css("display") == "block") {
                        $(".login").css("display", "none");
                        $(".logout").css("display", "block");
                        $('#login').modal('show');
                    } else {
                        $('#login').modal('show');
                    }
                    return;
                }else{
                    RequestService("/course/getCourseApplyByCourseId", "GET", {
                        courseId:courserId
                    }, function(data) {
                        //获取其他数据
                        if(apply=='true'){
                            alert("已经购买");
                        }else if(!data.resultObject.free) {
                            RequestService("/shoppingCart/join", "post", {
                                courseId: courserId
                            }, function(n) {});
                            RequestService("/video/findVideosByCourseId", "GET", {
                                courseId: courserId
                            }, function(data) {
                                if(data.success == true) {
                                    window.location.href = "/web/html/order.html?courseId=" + courserId;
                                } else {
                                    rTips(data.errorMessage);
                                }
                            });
                        }else{
                            $("#sign-up-modal").html(template.compile(modal)(data.resultObject));
                            $("#sign-up-modal .sign-up-title img").click(function(){
                                $(".background-big").css("display","none");
                                $("#sign-up-modal").css("display","none");
                            });
                            $(".gotengxun").click(function(){
                                RequestService("/video/saveEntryVideo", "POST", {
                                    courseId:courserId,
                                    free:free
                                }, function(data) {
                                    if(data.success == true){
                                        if(data.resultObject=="报名成功"){
                                            if(!collection){
                                                $(".sign-up-body,.gotengxun").css("display","none");
                                                $(".sign-up-success,.baomingSucces").css("display","block");
                                                $(".bigpic-body-btn .sign-up").text("立即学习");
                                                $(".sign-up,.baomingSucces").attr("href","/web/html/video.html?courseId="+courserId);
                                                $(".sign-up1").css("display","none");
                                                $("#payCourseSlider .baomingSucces").css("display","block");
                                                $(".sign-up").unbind("click");
                                            }else{
                                                $(".background-big").css("display","none");
                                                $("#sign-up-modal").css("display","none");
                                                $(".sign-up").css("display","none");
                                                $(".bigpic-body-btn").html('<a class="purchase" style="background-color:#ccc;border-radius:4px">您已成功报名，可直接点击选集列表进行学习</a>');
                                                $(".yibaoming").css("display","block");
                                            }
                                        }
                                        $(".sign-up-title img").click(function(){
                                            $(".yibaoming").css("display","block");
                                        })
                                    }else{
                                        rTips(data.errorMessage);
                                    }
                                },false);
                            });
                            $(".background-big").css("display","block");
                            $("#sign-up-modal").css("display","block");
                            //省略号
                            $(".sign-up-body-top-right-body").dotdotdot();
                        }
                    },false);
                }
            });

        });
        $(".myClassName").text(data.resultObject.courseName);
        $(".myClassName").attr("href","/web/html/courseIntroductionPage.html?id="+courserId+'&courseType='+courseType+'&free='+fre);
        $(".enter-class").attr("href",data.resultObject.cloudClassroom);
        $(".table-title-inset .course-details").click();
        //省略号
        $('.bigpic-body-text').dotdotdot();
    });
    function rTips(errorMessage){
        $(".rTips").text(errorMessage);
        $(".rTips").css("display","block");
        setTimeout(function(){
            $(".rTips").css("display","none");
        },2000)
    }
    //常见问题
    $(".course-problem").click(function(){
        // RequestService("/course/getCourseById", "GET", {
        //     courserId:courserId
        // }, function(data) {
            if(courseDetail.commonProblem==null || courseDetail.commonProblem==""){
                $(".table-modal").html(template.compile(emptyDefaul));
            }else{
                $(".table-modal").html(courseDetail.commonProblem);
            }
        // })
    });

    //选集列表
    $(".collection-course").click(function(){
        RequestService("/course/getCoursesByCollectionId", "GET", {
            collectionId:courserId
        }, function(data) {
            if(data.resultObject ==null || data.resultObject.length == 0){
                $(".table-modal").html(template.compile(emptyDefaul));
            }else{
                console.log(data)
                $('.table-modal').html(template('collection_course_Tpl',{item:data.resultObject}))
            }
        })
    });


    //课程大纲
    $(".course-outline").click(function () {
        $(".pages").css("display","none");
        // RequestService('/course/getCourseById',"GET",{courserId:courserId},function(data){
            if(!courseDetail || !courseDetail.courseOutline){
                $(".table-modal").html(template.compile(emptyDefaul));
            }else{
                //获取其他数据
                $(".table-modal").html(courseDetail.courseOutline)
            }
        // });
    });
    //学员评价
    $(".course-evaluate").click(function() {
        Evalutation();
    });
    //课程详情
    $(".course-details").click(function(){
        // RequestService("/course/getCourseById", "GET", {
        //     courserId:courserId
        // }, function(data) {
            if(courseDetail.courseDetail==null || courseDetail.courseDetail==""){
                $(".table-modal").html(template.compile(emptyDefaul));
            }else {
                //获取其他数据
                $(".table-modal").html("<div class='pic'>" + courseDetail.courseDetail + "</div>");
            }
        // })
    });
    //授课老师
    $(".course-teacher").click(function(){
        // RequestService("/course/getCourseById", "GET", {
        //     courserId:courserId
        // }, function(data) {
            if(courseDetail.lecturerDescription==null || courseDetail.lecturerDescription==""){
                $(".table-modal").html(template.compile(emptyDefaul));
            }else {
                //获取其他数据
                $(".table-modal").html(courseDetail.lecturerDescription);
            }
        // })
    });
    RequestService("/course/getRecommendCourseByCourseId","GET",{
        courseId:courserId
    },function(data){
        $(".relative-course").html(template.compile(relativeCourse)({
            item:data.resultObject
        }));
        $.each(data.resultObject,function(i){
            if(courserId==123){
                var oriCost=data.resultObject[i].originalCost;
                $(".info").append('<del style="font-size:13px;color:#999;"><i style="font-style:normal;">￥</i></del>');
                $("del").text(oriCost);
            }
        });

        if(data.resultObject=="" || data.resultObject==null){
            $(".by-the-arrow").css("display","none");
            $(".by-the-arrow").css("display","none");
            $(".slide-box").css("display","none");
        }else{
            $(".relativeAnsNoData").css("display","none");
            $(".boxContent li").eq(0).addClass("diyiye");
            $(".allLunbo").html(data.resultObject.length);
            var $index=0;
            var $exdex=0;
            $("#next").click(function(){
                if($index+1>=$(".allLunbo").text()){
                    return false;
                }else{
                    $index++;
                }
                next();
                return $exdex=$index;
            })
            $("#prev").click(function(){
                if($index-1<0){
                    return false;
                }else{
                    $index--;
                }
                pre();
                return $exdex=$index;
            })
        }
        function next(){
            $(".currentLunbo").html($index+1);
            $(".boxContent li").eq($index).stop(true,true).
            css("left","100%").animate({"left":"0"});
            $(".boxContent li").eq($exdex).stop(true,true).
            css("left","0").animate({"left":"-100%"});
        }
        function pre(){
            $(".currentLunbo").html($index+1);
            $(".boxContent li").eq($index).stop(true,true).
            css("left","-100%").animate({"left":"0"});
            $(".boxContent li").eq($exdex).stop(true,true).
            css("left","0").animate({"left":"100%"});
        }
    });


    var haopingCount, totalCount, haopinglv;
    var list = {
        pageNumber: 1,
        pageSize: 10
    };
    function Evalutation() {
        RequestService("/course/findStudentCriticize", 'GET', {
            courseId: courserId,
            pageNumber: list.pageNumber,
            pageSize: list.pageSize
        }, function(data1) {
            if(data1.resultObject.items.length == 0) {
                $(".table-modal").html(template.compile(emptyDefaul));
            } else {
                $(".table-modal").html(template.compile(stuEvalutation)({
                    item: data1.resultObject.items
                }));
                RequestService("/course/getGoodCriticizSum", "GET", {
                    courseId: courserId
                }, function(data2) {
                    $(".good-repuBox:first-child").before(template.compile(haoping));
                    totalCount = data1.resultObject.totalCount;
                    haopingCount = data2.resultObject;
                    haopinglv = data2.resultObject / data1.resultObject.totalCount * 100;
                    haopinglv = haopinglv.toFixed(1) + "%";
                    $(".haopingCount").text(haopingCount);
                    $(".totalCount").text(totalCount);
                    $(".goodPing").text(haopinglv)
                });
                if(data1.resultObject.totalPageCount > 1) {
                    $(".pages").css("display", "block");
                    if(data1.resultObject.currentPage == 1) {
                        $("#Pagination").pagination(data1.resultObject.totalPageCount, {
                            callback: function(page) { //翻页功能
                                list.pageNumber = (page + 1);
                                list.pageSize = 10;
                                Evalutation();
                            }
                        });
                    }

                    /* $(".pagination a").eq(list.pageNumber-1).addClass("current").siblings().removeClass("current");*/
                } else {
                    $(".pages").css("display", "none");
                }
            }
        })
    };


};

$('.online').on('click',function(){
    $('.online').css("background","#23a523")
});
$('.enter-class').on('click',function(){
    $('.enter-class').css("background","#23a523")
});
var bitS;
var bitS2;
$(".cu-shou").click(function(){
    if($(this).hasClass("noClick")){

    } else{
        $(".cu-shou").removeClass("noClick").addClass("notpointer");
        $(this).addClass("noClick").removeClass("notpointer");
        bitS=$(this).text();
        $("#payCourseSlider li").each(function(){
            if($(this).text()==bitS){
                $(this).addClass("noClick").removeClass("notpointer");
            }
        })
    }
});
$("#payCourseSlider li").click(function(){
    $(".cu-shou").removeClass("noClick").addClass("notpointer");
    $(this).addClass("noClick").removeClass("notpointer");
    bitS2=$(this).text();
    $(".table-title span").each(function(){
        if($(this).text()==bitS2){
            $(this).addClass("noClick").removeClass("notpointer");
        }
    })
})
