/**
 * Created by admin on 2016/11/2.
 */
/**
 * Created by Administrator on 2016/7/28.
 * IE只认可year/month/day格式 不认可year-month-day格式
 */

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
    var direct_id=arr[1];
//    var courseType=arr[1];
    var fre= arr[2];
};
template.helper('getId',function(courId){{
    if(courId==239){
        return true;
    }else{
        return false;
    }
}});
window.onload=function(){
    $(".header_left .path a").each(function() {
        if($(this).text() == "云课堂") {
            $(this).addClass("select");
        } else {
            $(this).removeClass("select");
        }
    });
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
       	'<span class="pricefree">加密</span>' +
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
        	'<p><span class="mianfei">输入密码</span><input type="password" id="pwd" onkeypress="if(event.keyCode==13) {gotengxun.click();return false;}"/></p>' +
        '</div>' +
        '</div>' +
        '<div class="sign-up-success">' +
        '<img src="/web/images/130/video_finish40.png"/></br>'+
        '<span class="signUp-courseName">{{courseName}}</span>'+
        '<span>课程报名成功</span>'+
        '</div>'+
        '<a class="gotengxun" href="javascript:;" id="gotengxun">' +
        '确认报名' +
        '</a>' +
//        '<a class="baomingSucces" href="/web/html/CourseDetailZhiBo.html?courseId='+courserId+'" >' +
        '<a class="baomingSucces" href="/web/livepage/'+courserId+'/'+direct_id+'/null" class="purchase" >' +
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
    var mytitlelist = '<div class="bigpic-img">' +
        '<img src="{{item.bigImgPath}}"/>' +
        '</div>' +
        '<div class="bigpic-body">' +
        '<span class="bigpic-body-title">' +
        '<span class="bigpic-body-title-nav">{{item.courseName}}</span>' +
        '{{if item.recommend == true}}' +
        '<i class="iconfont icon-jingpin jingpingCourse"><span>精</span></i>'+
        '{{/if}}'+
        '</span>' +
        '<p class="bigpic-body-text dot-ellipsis" title="{{item.description}}">{{item.description}}</p>' +
        '<p class="bigpic-body-list">' +
        '<span class="body-list-right">主讲：{{item.teacherName}}</span>' +
        '<span class="body-list-right myTimes" title="课程时长" style="cursor:default">课程时长：{{#timeChange(item.courseLength)}}</span>' +
        '<span title="学习人数" style="cursor:default">学习人数：{{item.learndCount}}人已学习</span>' +
        '{{if item.apply==true}}'+
        '<span title="有效期" style="cursor:default;color:#333;" class="youxiaoqi">有效期：1年' +
        '<span class="yibaoming"><img src="/web/images/130/baoming.png"/></span>'+
        '</span>' +
        '{{else}}' +
        '<span title="有效期" style="cursor:default;color:#333;" class="youxiaoqi">有效期：1年' +
        '<span class="yibaoming" style="display:none"><img src="/web/images/130/baoming.png"/></span>'+
        '</span>' +
        '{{/if}}'+
        '</p>' +
        '{{if item.free == true}}' +
        '<p class="bigpic-body-money">' +
        	'<span class="bigpic-body-overmoney">加密</span>' +
        '</p>' +
        '<div class="bigpic-body-btn">' +
        '{{if item.apply==true}}'+
//        '<a class="purchase" data-apply="{{item.apply}}" data-id="{{item.id}}" href="/web/html/CourseDetailZhiBo.html?courseId='+courserId+'" >立即学习</a>'+
        '<a class="purchase" data-apply="{{item.apply}}" data-id="{{item.id}}" href="/web/livepage/'+courserId+'/'+direct_id+'/null">立即学习</a>' +
//        '<a class="purchase" data-apply="{{item.apply}}" data-id="{{item.id}}" href="/web/livepage/'+courserId+'/{{direct_id}}/null" >立即学习</a>'+
        '{{else}}'+
        '<a class="sign-up purchase" data-apply="{{item.apply}}" data-id="{{item.id}}" target="_blank">立即报名</a>'+
        '{{/if}}'+
        '</div>'+
        '</div>' +
        '{{else}}' +
        '<p class="bigpic-body-money">' +
        '<span class="bigpic-body-redmoney">￥{{item.currentPrice}}</span>' +
        '<del class="bigpic-body-notmoney">￥{{item.originalCost}}</del>' +
        '</p>' +
        '<div class="bigpic-body-btn">' +
        '<a href="javascript:;" class="purchase" target="_blank">立即报名</a>'+
        '<a href="javascript:;" class="free-try-to-lean" ">免费试学</a>'+
        '</div>'+
        '</div>' +
        '{{/if}}';
    var emptyDefaul =
        "<div class='page-no-result'>" +
        "<img src='../images/personcenter/my_nodata.png'>" +
        "<div class='no-title'>暂无数据</div>" +
        "</div>";
    var courseOutline='<div class="table-school">'+
        '<div class="table-school-body">'+
        '{{each items}}'+
        '<p class="school-chapter">'+
        '<span class="bcg"></span>'+
        '<span class="school-chapter-text"><span>{{$value.name}}</span>'+
        '</p>'+
        '<div class="details-div">'+
        '{{each $value.chapterSons as $second d}}'+
        '<p class="details-div-title">{{$second.name}}</p>'+
        '<div class="details-div-body">'+
        '{{each $second.chapterSons as $third t}}'+
        '<p title="{{t+1}}-{{$third.name}}">{{t+1}}-{{$third.name}}</p>'+
        '{{/each}}'+
        '{{if $second.chapterSons.length%2!=0}}'+
        '<p></p>'+
        '{{/if}}'+
        '</div>'+
        '{{/each}}'+
        '</div>' +
        '{{/each}}'+
        '</div>' +
        '</div>';
    var free;
    $(".baomingSucces").attr("href","/web/html/CourseDetailZhiBo.html?courseId="+courserId);
    RequestService("/course/getCourseById", "POST", {
        courserId:courserId
    }, function(data) {
    	
        free=data.resultObject.free;
        $(".sidebar-body-QQ-name").append("<p class='greend-QQnumber'><span>QQ号 : </span>"+
        "<a href='tencent://AddContact/?fromId=50&fromSubId=1&subcmd=all&uin="+data.resultObject.qqno+"' >"+ data.resultObject.qqno + "</a></p>")
        document.title=data.resultObject.courseName+" - 熊猫中医云课堂";
        $(".bigpic-title").html(template.compile(mytitlelist)({
            item: data.resultObject
        }));
        if(courserId==123){
            var oriCost=data.resultObject.originalCost;
            $(".bigpic-body-money").append('<del style="font-size:13px;color:#999;"><i style="font-style:normal;">￥</i></del>');
            $("del").text(oriCost);
        }

        //面包宵导航
        if(data.resultObject.description_show==0){//不显示课程介绍页
            $("#NoShowIntroduct").css("display","block");
        }else{
            $("#showIntroduct").css("display","block");
        }
             //免费试学
            $(".free-try-to-lean").on("click", function () {
                RequestService("/online/user/isAlive", "GET", "", function (data) {
                    if (!data.success) {
                        localStorage.username = null;
                        localStorage.password = null;
                        if ($(".login").css("display") == "block") {
                            $(".login").css("display", "none");
                            $(".logout").css("display", "block");
                            $('#login').modal('show');
                        } else {
                            $('#login').modal('show');
                        }
                        return;
                    } else {
                        window.open("/web/html/freeAudition.html?courseId=" + courserId);
                    }
                }, false);
            })
        var bigHeight=$(".bigpic").outerHeight(true);
        var introHeight=$("#introductBox").outerHeight(true);
        var navHeight=$(".nav").outerHeight(true);
        var height=bigHeight+introHeight+navHeight;
        $(document).scroll(function () {
            if ($(document).scrollTop() > height+61) {
                $("#payCourseSlider").slideDown(100);
            } else {
                $("#payCourseSlider").slideUp(100);
            }
        });
        if(data.resultObject.apply==true){
            $("#payCourseSlider .sign-up1").css("display","none");
            $("#payCourseSlider .baomingSucces").css("display","block");
        }else{
            $("#payCourseSlider .sign-up1").css("display","block");
            $("#payCourseSlider .baomingSucces").css("display","none");
        }
        $(".bigpic-body-btn .purchase").click(function(){
            window.location.reload;
        })
        $(".sign-up,.sign-up1").click(function(){
            var id = $(this).attr("data-id");
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
                        if(apply == "true"){

                        }else{
                            $("#sign-up-modal").html(template.compile(modal)(data.resultObject));
                            $("#sign-up-modal .sign-up-title img").click(function(){
                                $(".background-big").css("display","none");
                                $("#sign-up-modal").css("display","none");
                            });
                            $(".gotengxun").click(function(){
                                var $this = $(this);
                                RequestService("/video/saveEntryVideo", "POST", {
                                    courseId:courserId,
                                    free:free,
                                    password:$("#pwd").val()
                                }, function(data) {
                                    if(data.success == true){
                                        if(data.resultObject=="报名成功"){
                                            $(".sign-up-body,.gotengxun").css("display","none");
                                            $(".sign-up-success,.baomingSucces").css("display","block");
                                            $(".bigpic-body-btn .sign-up").text("立即学习");
//                                            $(".sign-up,.baomingSucces").attr("href","/web/html/CourseDetailZhiBo.html?courseId="+courserId);
//                                            '<a href="/web/livepage/{{item.id}}/{{item.direct_id}}/null" class="purchase" >立即学习</a>' +	
//                                            $(".sign-up,.baomingSucces").attr("href","/web/livepage/"+courserId+"/"+{{item.direct_id}}+"/null");
                                            $(".sign-up1").css("display","none");
                                            $("#payCourseSlider .baomingSucces").css("display","block");
                                            $(".sign-up").unbind("click");
                                            $(".sign-up-title img").click(function(){
                                            	$(".yibaoming").css("display","block");
                                            })
                                        }
                                        rTips(data.resultObject);
                                    }else{
                                        rTips(data.errorMessage);
                                    }
                                });
                            });
                            $(".background-big").css("display","block");
                            $("#sign-up-modal").css("display","block");
                            //省略号
                            $(".sign-up-body-top-right-body").dotdotdot();
                        }
                    });
                }
            });

        });
        $(".myClassName").text(data.resultObject.courseName);
       // $(".myClassName").attr("href","/web/html/courseIntroductionPage.html?id="+courserId+'&courseType='+courseType+'&free='+free);
        $(".enter-class").attr("href",data.resultObject.cloudClassroom);
        $(".course-details").click();
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
    $(".course-problem").click(function(){
        RequestService("/course/getCourseById", "GET", {
            courserId:courserId
        }, function(data) {
            if(data.resultObject.commonProblem==null || data.resultObject.commonProblem==""){
                $(".table-modal").html(template.compile(emptyDefaul));
            }else{
                $(".table-modal").html(data.resultObject.commonProblem);
            }
        })
    });
    $(".course-outline").click(function () {
        $(".pages").css("display","none");
        RequestService('/course/getCourseCatalog',"GET",{courseId:courserId},function(data){
            if(data.resultObject.length==0){
                $(".table-modal").html(template.compile(emptyDefaul));
            }else{
                //获取其他数据
                $(".table-modal").html(template.compile(courseOutline)({
                    items:data.resultObject
                }));
                for (var i = 0; i < $(".details-div-body p").length; i++) {
                    var $this = $(".details-div-body p").eq(i);
                    var last = $this.text().substring($this.text().length - 3, $this.text().length);
                    if (last == ".. ") {
                        $this.attr("data-txt", $this.attr("data-text"))
                    }
                }
            }
        });
    });
    $(".course-details").click(function(){
        RequestService("/course/getCourseById", "GET", {
            courserId:courserId
        }, function(data) {
            if(data.resultObject.description==null || data.resultObject.description==""){
                $(".table-modal").html(template.compile(emptyDefaul));
            }else {
                //获取其他数据
                $(".table-modal").html("<div class='pic'>" + data.resultObject.description + "</div>");
            }
        })
    });
    $(".course-teacher").click(function(){
    	RequestService("/course/getCourseById", "GET", {
            courserId:courserId
        }, function(data) {
            if(data.resultObject.teacherDescription==null || data.resultObject.teacherDescription==""){
                $(".table-modal").html(template.compile(emptyDefaul));
            }else {
                //获取其他数据
                $(".table-modal").html(data.resultObject.teacherDescription);
            }
        })
//        RequestService("/lecturer/list/course/" +courserId, "GET", "", function (data) {
//            if(data.resultObject.length==0){
//                $(".table-modal").html(template.compile(emptyDefaul));
//            }else {
//                $(".table-modal").html(template.compile(teacher)({
//                    item: data.resultObject
//                }));
//                $(".teacher-introduction").dotdotdot();
//                for(i = 0; i < $(".teacher-introduction").length; i++) {
//                    var $this = $(".teacher-introduction").eq(i);
//                    var last = $this.text().substring($this.text().length - 3, $this.text().length);
//                    if(last == ".. ") {
//                        $this.attr("data-txt", $this.attr("data-text"))
//                    }
//                }
//            }
//        });
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

