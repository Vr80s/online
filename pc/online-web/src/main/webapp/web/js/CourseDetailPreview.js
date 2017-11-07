/**
 * Created by Administrator on 2016/7/28.
 * IE只认可year/month/day格式 不认可year-month-day格式
 */
window.onload=function(){
//	var courserId = "140";
    template.helper('href', function (num) {
        if (num != "") {
           return 'javascript:;';
        } else {
            return 'javascript:;';
        }
    });
    var slideNavIndex={
        /* 咨询中心*/
        consult_center:'<div class="consult_center">咨询中心</div>',
        online_consult:'<div class="online_consult">'+
        '<a href="http://crm2.qq.com/page/portalpage/wpa.php?uin=1078329360&aty=2&a=2&curl=&ty=1" target="_blank">'+
        '<img src="/web/images/zixun.gif" alt=""/><span>在线咨询</span>'+
        '</a></div>',
        phone_consult:'<div class="phone_consult">'+
        '<div class="phone_consult_box"><img src="/web/images/tel.gif" alt=""/><span class="dianhuazixun">电话咨询</span></div>' +
        '<span class="phone_number">0898-32881833</span>'+
        ' </div>',
        weixin:'<div class="sideWeixinBox">'+
        '<div class="sideWeixin">'+
        '<img src="/web/images/sideWeixinErma.png" />'+
        '<p>关注微信</p>'+
        '</div>'+
        '</div>',
        weixinErma:'<div class="sideWeixinErma">'+
        '<img src="/web/images/sideWeixin.png" />'+
        '<div class="sideSanjiao">'+
        '<img src="/web/images/float_sanjiao.png" />'+
        '</div>'+
        '</div>',
        weibo:'<a class="sideWeiboBox" href="http://weibo.com/u/6329861207?refer_flag=1001030102_&amp;is_hot=1" target="_blank">' +
        '<div class="sideWeibo">'+
        '<img src="/web/images/sideWeiboErma.png" />'+
        '<p>关注微博</p>'+
        '</div>'+
        '</a>',
        weiboErma:'<div class="sideWeiboErma">'+
        '<img src="/web/images/sideWeibopng.png" />'+
        '<div class="sideSanjiao1">'+
        '<img src="/web/images/float_sanjiao.png" />'+
        '</div>'+
        '</div>',
        h_top:'<div class="h_top" onclick="pageScroll();">' +
        '<span class="wrap">'+
        '<img src="/web/images/top.png" alt=""/><span class="h_top_s">top</span>'+
        '</span></div>'
    }
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
//      '<a href="{{#href($value.id)}}" >' +
        '{{#qshasImg($value.smallImgPath)}}' +
        '{{#online($value.scoreName)}}'+
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
//      '</a>' +
        "</li>" +
        '{{/each}}' +
        '</ul>' +
        '</div></div>';
    var strcourses =
        '{{each item}}' +
        '<div class="courses">' +
//      '<a href="{{#href($value.id)}}" >' +
        '{{#hasImg($value.smallImgPath)}}' +
        '{{#online($value.scoreName)}}' +
        '<div class="detail">' +
        '<p class="title" data-text="{{$value.courseName}}" title="{{$value.courseName}}">{{$value.courseName}}</p>' +
        '<p class="info">' +
        '<span>' +
        '{{if $value.free == true}}' +
       	'<span class="pricefree">免费</span>' +
        '{{else}}' +
        '<i>￥</i><span class="price">{{$value.currentPrice}}</span><del><i class="price1">￥</i>{{$value.originalCost}}</del>' +
        '{{/if}}' +
        '</span>' +
        '<span class="stuCount">{{$value.learndCount}}人已学习</span>' +
        '</p>' +
        '</div>' +
//      '</a>' +
        '</div>' +
        '{{/each}}';
    var modal =
    '<div class="rTips">报名失败，请刷新页面重试</div>' +
    '<div class="sign-up-title">' +
        '<span class="sign-up-title-left">课程报名</span>' +
        '<img src="../images/alter.png">' +
        '</div>' +
        '<div class="sign-up-body">' +
        '<div class="sign-up-body-top">' +
        '<img src="{{bigImgPath}}"/>' +
        '<div class="sign-up-body-top-right">' +
        '<p class="sign-up-body-top-right-name">{{courseName}}</p>' +
        '<p class="sign-up-body-top-right-body" title="{{description}}">{{description}}</p>' +
        '</div>' +
        '</div>' +
        '<div class="sign-up-body-center">' +
        '<p>系统升级中！即将进入<span>“腾讯课堂-博客云课堂”</span>；我们的努力，只为更好的你！</p>' +
    '</div>' +
    '<div class="sign-up-body-bottom">' +
    '{{if free == true}}' +
    '<p>价格：<span class="mianfei">免费</span></p>' +
    '</div>' +
    '<a class="gotengxun" target="_blank">' +
    '确认报名' +
    '</a>' +
    '</div>' +
    '{{else}}' +
        '<p>价格：<span>￥{{currentPrice}}</span><del>￥{{originalCost}}</del></p>' +
    '</div>' +
    '<a class="gotengxun" target="_blank">' +
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
    var mytitlelist ='<div class="bigpic-img">' +
        '<img src="{{item.bigImgPath}}"/>' +
        '</div>' +
        '<div class="bigpic-body">' +
        '<span class="bigpic-body-title">' +
        '<span class="bigpic-body-title-nav">{{item.courseName}}</span>' +
        '{{if item.recommend == true}}' +
        '<img src="/web/images/130/icon_10.png" class="jingpingCourse"/>'+
        '{{/if}}'+
        '</span>' +
        '<p class="bigpic-body-text dot-ellipsis" title="{{item.description}}">{{item.description}}</p>' +
        '<p class="bigpic-body-list">' +
        '<span class="body-list-right">主讲：{{item.teacherName}}</span>' +
        '<span class="body-list-right myTimes" title="课程时长" style="cursor:default">学习时长：{{#timeChange(item.courseLength)}}</span>' +
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
        '<span class="bigpic-body-overmoney">免费</span>' +
        '</p>' +
        '<div class="bigpic-body-btn">' +
        '</div>'+
        '</div>' +
        '{{else}}' +
        '<p class="bigpic-body-money">' +
        '<span class="bigpic-body-redmoney">￥{{item.currentPrice}}</span>' +
        '<del class="bigpic-body-notmoney">￥{{item.originalCost}}</del>' +
        '</p>' +
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
    $("body").append(template.compile(slideNavIndex.consult_center));
    $("body").append(template.compile(slideNavIndex.online_consult));
    $("body").append(template.compile(slideNavIndex.phone_consult));
    $("body").append(template.compile(slideNavIndex.weixin));
    $("body").append(template.compile(slideNavIndex.weibo));
    $("body").append(template.compile(slideNavIndex.weixinErma));
    $("body").append(template.compile(slideNavIndex.weiboErma));
    $("body").append(template.compile(slideNavIndex.h_top));
    //侧边栏微信，微博
    $(".sideWeixinBox").hover(function(){
        $(".sideWeixinErma").css("display","block");
        $(".sideWeixinBox").css("backgroundColor","#eef3f8");
    },function(){
        $(".sideWeixinErma").css("display","none");
        $(".sideWeixinBox").css("backgroundColor","#fff");
    });
    $(".sideWeiboBox").hover(function(){
        $(".sideWeiboErma").css("display","block");
        $(".sideWeiboBox").css("backgroundColor","#eef3f8");
    },function(){
        $(".sideWeiboErma").css("display","none");
        $(".sideWeiboBox").css("backgroundColor","#fff");
    });
    $(".phone_consult").hover(function(){
        $(this).animate({"width":"160px"},200);
        $(".phone_consult_box img").css("marginTop","20px");
        $(".phone_number").css("marginTop","3px");
        $(".dianhuazixun").css("display","none");
    },function(){
        $(this).animate({"width":"57px"},200);
        $(".phone_consult_box img").css("marginTop","8px");
        $(".phone_number").css("marginTop","0px");
        $(".dianhuazixun").css("display","block");
    })
    var systemTime;
    var url;
    RequestService("/grade/time/now", "GET", "", function(data) {//获取当前系统时间
        systemTime = data.data;
   		 RequestService("/grade/findGradeByCourseId", "GET", {
    	    courserId:courserId
   		 }, function(data) {
    	    $.each(data.resultObject,function(index,item){
            //获取班级信息
                var endTime=item.curriculumTime;
                var s=getRTime(endTime,systemTime,index);             
                var liveTime = item.curriculumTime.split(" ");
            if(new Date(Date.parse(endTime.replace(/-/g,"/"))).getTime() > new Date(Date.parse(systemTime.replace(/-/g,"/"))).getTime()){
                var str='<div class="classgrand">'+item.name+'</div>'+
                    '<div class="course-time">开课时间：'+ '<span>'+ liveTime[0]+'</span>'+'</div>' +
                    '<div class="baoming"><em></em>报名中<span>(仅剩'+item.seat+'席)</span></div>'+'<div class="daojishi daojishi' + index + '">距离开班<span>00</span>天<span>00</span>时<span>00</span>分<span>00</span>秒</div>'
                    +
                    '<div class="online"><a class="sign-up" data-apply="'+ item.apply +'" data-id="' + item.id + '" target="_blank">立即报名</a></div>'
                $('#detail-course').append(str);
            }else{
                var str='<div class="classgrand">'+item.name+'</div>'+
                    '<div class="course-time">开课时间：'+ '<span>'+ liveTime[0]+'</span>'+'</div>' +
                    '<div class="baoming storpHot"><em></em><span style="color:#333;">报名已结束</span></div>'+'<div class="daojishi daojishi' + index + '">距离开班<span>00</span>天<span>00</span>时<span>00</span>分<span>00</span>秒</div>'
                    +
                    '<div class="online"><a style="background-color: #ccc;" class="sign-upto" data-apply="'+ item.apply +'" data-id="' + item.id + '" target="_blank">立即报名</a></div>'
                $('#detail-course').append(str);
            }

            });
             RequestService("/course/getCourseById", "GET", {
                 courserId:courserId,
                 ispreview:1
             }, function(data) {
                 url = data.resultObject.cloudClassroom;
                 /*$(".sign-up").each(function () {
                     if ($(this).attr("data-apply") == "true") {
                         $(this).attr("href", url);
                         $(this).text("进入课堂");
                     }
                 });
                 $(".sign-upto").each(function () {
                     if ($(this).attr("data-apply") == "true") {
                         $(this).attr("href", url);
                         $(this).text("进入课堂");
                     }
                 });*/
                 //qq号码
                 $(".sidebar-body-QQ-name").append("<p class='greend-QQnumber'><span>QQ号 : </span>"+
        "<a href='javascript:;' >"+ data.resultObject.qqno + "</a></p>")
                 document.title=data.resultObject.courseName+" - 熊猫中医云课堂";
                 $(".bigpic-title").html(template.compile(mytitlelist)({
                     item: data.resultObject
                 }));
//               var times;
//               if(data.resultObject.courseLength<60){
//                   times=''+data.resultObject.courseLength+'分钟';
//               }else{
//                   times=parseInt(data.resultObject.courseLength/60);
//                   times=''+times+'小时';
//               }
//               $(".myTimes").html("<img src='../images/hour_16.png' style='margin-right: 5px;'/>" + times);
                 $(".myClassName").text(data.resultObject.courseName);
                 /*$(".enter-class").attr("href",data.resultObject.cloudClassroom);*/
                 $(".table-title-inset .course-details").click();
             });
/*             $(".sign-up").click(function(){
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
                             $("html").css({"overflow-x": "hidden", "overflow-y": "hidden"});
                         } else {
                             $('#login').modal('show');
                             $("html").css({"overflow-x": "hidden", "overflow-y": "hidden"});
                         }
                         return;
                     }else{
                         RequestService("/course/getCourseApplyByCourseId", "GET", {
                             courseId:courserId
                         }, function(data) {
                             //获取其他数据
                             console.log(data);
                             if(apply == "true"){
                             	
                             }else{
                                 $("#sign-up-modal").html(template.compile(modal)(data.resultObject));
                                 //省略号
                                 $(".sign-up-body-top-right-body").dotdotdot();
                                 for(i = 0; i < $(".sign-up-body-top-right-body").length; i++) {
                                     var $this = $(".sign-up-body-top-right-body").eq(i);
                                     var last = $this.text().substring($this.text().length - 3, $this.text().length);
                                     if(last == ".. ") {
                                         $this.attr("data-txt", $this.attr("data-text"))
                                     }
                                 }
                                 $("#sign-up-modal .sign-up-title img").click(function(){
                                     $(".background-big").css("display","none");
                                     $("#sign-up-modal").css("display","none");
                                 });
                                 $(".gotengxun").attr("data-url",data.resultObject.cloudClassroom);
                                 $(".gotengxun").attr("href",data.resultObject.cloudClassroom);
                                 url = $(".gotengxun").attr("data-url");
                                 $(".gotengxun").click(function(){
                                     var $this = $(this);
                                     $this.attr("href",url);
                                     RequestService("/online/apply/saveApply", "GET", {
                                         courseId:courserId,
                                         gradeId: id
                                     }, function(data) {
                                         if(data.success == true){
                                             location.reload();
                                             /!* console.log(url);
                                              var w=window.open('_blank');
                                              w.location=url;*!/
                                         }else{
                                             rTips();
                                         }
                                     });
                                 });
                                 $(".background-big").css("display","block");
                                 $("#sign-up-modal").css("display","block");
                             }

                         });
                     }
                 });

             });*/
    });
        //省略号
        var $dot5 = $('.bigpic-body-text');
        $($dot5).each(function () {
            if ($(this).height() > 80) {
                $(this).attr("data-txt", $(this).attr("data-text"));
                $(this).height(92);
            }
        });
        $('.bigpic-body-text').dotdotdot();
    });
    function rTips(){
        $(".rTips").css("display","block");
        setTimeout(function(){
            $(".rTips").css("display","none");
        },2000)
    }
    $(".course-problem").click(function(){
//    RequestService("/online/question/getQuestionList", "GET", {
//        courseId:courserId
//    }, function(data) {
//        //获取其他数据
//        $(".table-modal").html(template.compile(problem)({
//            item: data.resultObject
//        }));
//   })
		RequestService("/course/getCourseById", "GET", {
            courserId:courserId,
            ispreview:1
		  }, function(data) {
            if(data.resultObject.commonProblem=="" || data.resultObject.commonProblem==null ){
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
            courserId:courserId,
            ispreview:1
        }, function(data) {
            if(data.resultObject.courseDetail=="" || data.resultObject.courseDetail==null){
                $(".table-modal").html(template.compile(emptyDefaul));
            }else{
                //获取其他数据
                $(".table-modal").html("<div class='pic'>"+data.resultObject.courseDetail+"</div>");
            }
        })
    });
    $(".course-teacher").click(function(){
        RequestService("/lecturer/list/course/" +courserId, "GET", "", function (data) {
            if(data.resultObject.length==0){
                $(".table-modal").html(template.compile(emptyDefaul));
            }else{
                $(".table-modal").html(template.compile(teacher)({
                    item: data.resultObject
                }));
                $(".teacher-introduction").dotdotdot();
                for(i = 0; i < $(".teacher-introduction").length; i++) {
                    var $this = $(".teacher-introduction").eq(i);
                    var last = $this.text().substring($this.text().length - 3, $this.text().length);
                    if(last == ".. ") {
                        $this.attr("data-txt", $this.attr("data-text"))
                    }
                }
            }
        });
    });
    RequestService("/course/getRecommendCourseByCourseId","GET",{
        courseId:courserId
    },function(data){
        $(".relative-course").html(template.compile(relativeCourse)({
            item:data.resultObject
        }));
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
$(".cu-shou").click(function(){
   if($(this).hasClass("noClick")){

   } else{
       $(".cu-shou").removeClass("noClick").addClass("notpointer");
       $(this).addClass("noClick").removeClass("notpointer");
   }
});

function getRTime(date,systemTime,index){
    var EndTime= new Date(Date.parse(date.replace(/-/g,"/"))).getTime();  //从后台获取结束时间
    var NowTime = new Date(Date.parse(systemTime.replace(/-/g,"/"))).getTime();//从后台获取当前时间
        //倒计时
        var intDiff= (EndTime - NowTime)/1000;
        window.setInterval(function(){
            var day=0,
                hour=0,
                minute=0,
                second=0;//时间默认值
            if(intDiff > 0){
                day = Math.floor(intDiff / (60 * 60 * 24));
                hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
                minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
                second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
            }
            if (minute <= 9){
                minute = '0' + minute;
            }
            if (hour <= 9){
                hour = '0' + hour;
            }
            if (day <= 9){
                day = '0' + day;
            }
            if (second <= 9){
                second = '0' + second;
            }
            $(".daojishi" + index).html("距离开班<span>" + day + "</span>天<span>" + hour + "</span>时<span>" + minute + "</span>分<span>" + second + "</span>秒");
            intDiff--;
        }, 1000);

}
//RequestService("/otherlink/getOtherLink", "GET", "", function(data) {
//  //footer友情链接
//  $.each(data.resultObject, function (index, item) {
//      $('#friendLink1').append('<a href="' + item.url + '" target="_blank">' + item.name + '</a>');
//  });
//});




