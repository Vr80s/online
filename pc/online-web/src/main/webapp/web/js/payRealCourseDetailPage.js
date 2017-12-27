/**
 * Created by Administrator on 2016/7/28.
 * IE只认可year/month/day格式 不认可year-month-day格式
 */

//解析url地址
var ourl = document.location.search;
var apams = ourl.substring(1).split("&");
var arr = [];
var isFirst=true;
var sex = 1;
for(i = 0; i < apams.length; i++) {
    var apam = apams[i].split("=");
    arr[i] = apam[1];
    var courserId = arr[0];
    var courseType = arr[1];
    var fre = arr[2];
};
if(courserId == "undefined") {
    var courserId = 142;
}

window.onload = function() {


    RequestService("/online/user/isAlive", "GET", "", function(data) {
        if(data.success&&data.resultObject.perfectInformation==false) {
            /*关闭信息认证*/
            $('.personBack').css({'display':'block'});
            //点击关闭
            $('.closeInfo').click(function(){
                $('.personBack').css({'display':'none'});
            })
            //先下课程个人信息获取

            $('.first').click()
            $('.man').click()

            RequestService("/online/apply/getUserApplyInfo", "get", {
            }, function(data) {
                console.log(data);
                if(data.success&&data.resultObject!=null){
                    var personInfo = data.resultObject;
                    console.log(personInfo)
                    //渲染页面
                    //姓名
                    $('.truename').val(personInfo.realName);
                    //电话
                    $('.phonenumber').val(personInfo.mobile);
                    //身份证
                    $('.cardNumber').val(personInfo.idCardNo);
                    //邮箱
                    $('.emailname').val(personInfo.email);
                    //微信
                    $('.weiXinNumber').val(personInfo.wechatNo);
                    //QQ
                    $('.QQnumber').val(personInfo.qq);
                    //介绍人
                    $('.introduce').val(personInfo.referee);
                    //判断性别
                    if(personInfo.sex == 1){
                        //男
                        $('.woman em').removeClass('active')
                        $('.man em').addClass('active')
                        $('.man input').css({'select':'selected'})

                    }else{
                        //女
                        $('.man em').removeClass('active')
                        $('.woman em').addClass('active')
                        $('.woman input').css({'select':'selected'})
                    }
                    //判断是否首次参见
                    if(personInfo.isFirst){
                        //首次
                        $('.notFirst em').removeClass('active')
                        $('.first em').addClass('active')
                        $('.first input').css({'select':'selected'})
                    }else{
                        //老学员
                        $('.first em').removeClass('active')
                        $('.notFirst em').addClass('active')
                        $('.notFirst input').css({'select':'selected'})
                    }
                }else{
                }


            })
        }
    }, false);




    //编译线下培训班个人信息
    $('.man').click(function(){
        $('.woman em').removeClass('active')
        $('.man em').addClass('active')
    })

    $('.woman').click(function(){
        $('.man em').removeClass('active')
        $('.woman em').addClass('active')
        sex = 0;
    })
    //编译是否是第一次参加
    $('.first').click(function(){
        $('.notFirst em').removeClass('active')
        $('.first em').addClass('active')
    })

    $('.notFirst').click(function(){
        $('.first em').removeClass('active')
        $('.notFirst em').addClass('active')
        isFirst = false;
    })


    $(".header_left .path a").each(function() {
        if($(this).text() == "云课堂") {
            $(this).addClass("select");
        } else {
            $(this).removeClass("select");
        }
    });
    template.helper('href', function(num) {
        if(num != "") {
            return '' + bath + '/web/courseDetail/' + num;
        } else {
            return 'javascript:;';
        }
    });
    template.helper("stuEvluatStars", function(num) {
        var start = "";
        for(var i = 0; i < num; i++) {
            start += '<i class="iconfont icon-shoucang"></i>';
        }
        return start;
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
        "</div>" +
        '<div class="relative-course-bottom slide-box clearfix">' +
        '<div id="box" class="slideBox clearfix">' +
        '<ul class="course boxContent clearfix">' +
        '{{each item as $value i}}' +
        "<li>" +
        '{{#indexHref($value.description_show,$value.free,$value.id,$value.scoreName)}}' +
        '{{#qshasImg($value.smallImgPath)}}' +
        '{{#online($value.multimediaType)}}' +
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
        '<div style="width:110%;overflow:hidden">' +
        '{{each item as i}}' +
        '<div class="teacher">' +
        '<img src="{{i.headImg}}" />' +
        '<div class="teacher-text">' +
        '<p class="teacher-name">{{i.name}}</p>' +
        '<p class="teacher-company">熊猫中医</p>' +
        '<p class="teacher-introduction" style="height: 170px;" title="{{i.description}}">{{i.description}}</p>' +
        '</div>' +
        '</div>' +
        '{{/each}}' +
        "</div>";
    var haoping = '<div class="good-reputation">' +
        '<div class="good-reputation-count">' +
        '<span>好评</span><span class="goodPing"></span>' +
        '<span class="totalPeople">(<span class="totalCount"></span>条评论，<span class="haopingCount"></span>条好评)</span>' +
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
        '<div class="repuOrigin">来源：{{$value.videoName}}</div>' +
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

    var mytitlelist = '<div class="bigpic-img">' +
        '<img src="{{item.bigImgPath}}"/>' +
        '</div>' +
        '<div class="bigpic-body">' +
        '<span class="bigpic-body-title">' +
        '<span class="bigpic-body-title-nav">{{item.courseName}}</span>' +
        '{{if item.recommend==true}}' +
        '<i class="iconfont icon-jingpin jingpingCourse"><span>精</span></i>' +
        '{{/if}}' +
        '</span>' +
        '{{if item.currentPrice!="0.00"}}' +
        //		'<span id="d_clip_button" class="shareCourse" data-clipboard-target="fe_text">分享课程，赚取学费<em>>></em></span>' +
        '{{/if}}' +
        '<p class="bigpic-body-text dot-ellipsis" title="{{item.description}}">{{item.description}}</p>' +
        '<p class="bigpic-body-list">' +
        '<span class="body-list-right">主讲：{{item.teacherName}}</span>' +
        '</p>' +

        '{{if item.apply==true}}' +
        '<span title="有效期" style="cursor:default;color:#777;" class="youxiaoqi">有效期：{{item.startTime}}-{{item.endTime}}'  +
        '<span class="yibaoming"><img src="/web/images/130/baoming.png"/></span>' +
        '</span>' +
        '{{else}}' +
        '<span title="有效期" style="cursor:default;color:#777;" class="youxiaoqi">有效期：{{item.startTime}}-{{item.endTime}}'  +
        '<span class="yibaoming" style="display:none"><img src="/web/images/130/baoming.png"/></span>' +
        '</span>' +
        '{{/if}}' +

        '<br/><span title="课程开始时间" style="cursor:default;color:#777;" class="youxiaoqi">课程开始时间：{{item.start_time}}&nbsp;{{item.week}}' +
        '</span>' +
        
        '{{if item.selfCourse == true}}' +
        	'<a href="/web/html/CourseDetailZhiBo.html?courseId=' + courserId + '" class="purchase common_ljck" >立即查看</a>' +
		'{{else}}'+ 
	        '{{if item.free == true}}' +
	        '<p class="bigpic-body-money">' +
	        '<span class="bigpic-body-overmoney">免费</span>' +
	        '</p>' +
	        '</div>' +
	        '{{else}}' +
	        '<p class="bigpic-body-money">' +
	        '<span class="bigpic-body-redmoney">￥{{item.currentPrice}}</span>' +
	        '<del class="bigpic-body-notmoney">￥{{item.originalCost}}</del>' +
	        '</p>' +
	        '<div class="bigpic-body-btn">' +
	        '{{if item.apply==false && item.available}}' +
	        '<a  href="javascript:;" class="gotengxun purchase">立即报名</a>' +
	        '{{if item.currentPrice!="0.00"}}' +
	        //		'<a class="free-try-to-lean" >免费试学</a>' +
	        //		'<span class="addCar">加入购物车</span>' +
	        //		'<span class="userInfo">报名信息</span>' +
	        //		'<span class="payHistory">跳到充值记录</span>' +
	        //		'<span class="cashHistory">跳到提现记录</span>' +
	        '{{/if}}' +
	        '{{else}}' +
	        //'<a style="cursor:pointer"  data-url="/web/livepage/{{$value.id}}/{{$value.direct_id}}/null">'+
	        //		'<a href="/web/livepage/{{item.id}}/{{item.direct_id}}/null" class="purchase" >立即学习</a>' +
	        //		'<a href="/web/html/CourseDetailZhiBo.html?courseId=' + courserId + '" class="purchase" >立即学习</a>' +
	        '{{/if}}' +
	        '</div>' +
	        '</div>' +
	        '{{/if}}'+
		'{{/if}}';
    var courseList = '{{each items}}' +
        '<div class="classgrand">{{$value.name}}</div>' +
        '<div class="course-time">报名截止时间：' + '<span>{{dataSub($value.curriculumTime)}}</span>' + '</div>' +
        '{{if $value.openClass==true}}' +
        '<div class="baoming"><em></em>' +
        '<span>已报名人数：<span style="color:#ff4012">{{$value.studentCount}}人</span></span>' +
        '</div>' +
        '<div class="online"><a class="sign-up" href="javascript:;"><img src="/web/images/baoming.gif" class="bm"></a>' +
        '</div>' +
        '</div>' +
        '{{else}}' +
        '<div class="baoming"><i></i>' +
        '<span>已报名人数：<span style="color:#ff4012">{{$value.studentCount}}人</span></span>' +
        '</div>' +
        '<div class="online"><a class="sign-up a1" href="javascript:;">报名已结束</a>' +
        '<img class="onlineImg" src="/web/images/130/finish.png"/>' +
        '</div>' +
        '</div>' +
        '{{/if}}' +
        '{{/each}}';
    var emptyDefaul =
        "<div class='page-no-result'>" +
        "<img src='../images/personcenter/my_nodata.png'>" +
        "<div class='no-title'>暂无数据</div>" +
        "</div>";
    var courseOutline = '<div class="table-school">' +
        '<div class="table-school-body">' +
        '{{each items}}' +
        '<p class="school-chapter">' +
        '<span class="bcg"></span>' +
        '<span class="school-chapter-text"><span>{{$value.name}}</span>' +
        '</p>' +
        '<div class="details-div">' +
        '{{each $value.chapterSons as $second d}}' +
        '<p class="details-div-title">{{$second.name}}</p>' +
        '<div class="details-div-body">' +
        '{{each $second.chapterSons as $third t}}' +
        '<p title="{{t+1}}-{{$third.name}}">{{t+1}}-{{$third.name}}</p>' +
        '{{/each}}' +
        '{{if $second.chapterSons.length%2!=0}}' +
        '<p></p>' +
        '{{/if}}' +
        '</div>' +
        '{{/each}}' +
        '</div>' +
        '{{/each}}' +
        '</div>' +
        '</div>';

    function rTips(errorMessage) {
        $(".rTips").text(errorMessage);
        $(".rTips").css("display", "block");
        setTimeout(function() {
            $(".rTips").css("display", "none");
        }, 2000)
    }
    $("#payCourseSlider .purchase").attr("href", '/web/html/order.html?courseId=' + courserId);
    $("#payCourseSlider .studyImmed").attr("href", '/web/html/myStudyCenter.html?free=' + fre);
    var free;
    RequestService("/grade/findGradeInfoByCourseId", "GET", {
        courseId: courserId
    }, function(data) {
        /*$('#detail-course').html(template.compile(courseList)({
            items:data.resultObject
        }))*/
        RequestService("/course/getCourseById", "POST", {
            courserId: courserId
        }, function(data) {
            free = data.resultObject.free;
            $(".sidebar-body-QQ-name").append("<p class='greend-QQnumber'><span>QQ号 : </span>" +
                "<a href='tencent://AddContact/?fromId=50&fromSubId=1&subcmd=all&uin=" + data.resultObject.qqno + "' >" + data.resultObject.qqno + "</a></p>")
            document.title = data.resultObject.courseName + " - 熊猫中医云课堂";
            $(".bigpic-title").html(template.compile(mytitlelist)({
                item: data.resultObject
            }));
            //加入购物车
            $(".addCar").off("click").on("click", function() {
                $.ajax({
                    type: "get",
                    url: bath + "/online/user/isAlive",
                    async: false,
                    success: function(data) {
                        if(data.success === true) {
                            RequestService("/shoppingCart/join", "post", {
                                courseId: courserId
                            }, function(data) {
                                if(data.success) {
                                    if(data.resultObject == "该课程已在购物车中了，无需重复加入") {
                                        $(".hasAddcar").css("display", "block");
                                    } else {
                                        RequestService("/shoppingCart/findCourseNum", "GET", null, function(m) {
                                            if(m.success == true && m.resultObject != 0) {
                                                $(".shopping").css("display", "block");
                                                if(m.resultObject <= 99) {
                                                    $(".shopping em").text(m.resultObject);
                                                } else {
                                                    $(".shopping em").text(99);
                                                }
                                            }
                                        });
                                        rTips("已成功添加至购物车");
                                    }
                                }else{
                                    rTips(data.errorMessage);
                                }
                            });
                        } else {
                            $('#login').modal('show');
                            $('#login').css("display", "block");
                            $(".loginGroup .logout").css("display", "block");
                            $(".loginGroup .login").css("display", "none");
                            return false;
                        }
                    }
                });

            });
            //面包宵导航
            if(data.resultObject.description_show == 0) { //不显示课程介绍页
                $("#NoShowIntroduct").css("display", "block");
            } else {
                $("#showIntroduct").css("display", "block");
            };
            $(".myClassName").text(data.resultObject.courseName);
            $(".myClassName").attr("href", "/web/html/courseIntroductionPage.html?id=" + courserId + '&courseType=' + courseType + '&free=' + fre);
            $(".enter-class").attr("href", data.resultObject.cloudClassroom);
            $(".table-title-inset .course-details").click();
            $(".gotengxun").click(function() {
                $.ajax({
                    type: "get",
                    url: bath + "/online/user/isAlive",
                    async: false,
                    success: function(m) {
                        if(m.success === true) {
                            RequestService("/shoppingCart/join", "post", {
                                courseId: courserId
                            }, function(n) {});
//							RequestService("/video/findVideosByCourseId", "GET", {
//								courseId: courserId
//							}, function(data) {
//								if(data.success == true) {
                            window.location.href = "/web/html/order.html?courseId=" + courserId;
//								} else {
//									rTips(data.errorMessage);
//								}
//							});
                        } else {
                            $('#login').modal('show');
                            $('#login').css("display", "block");
                            $(".loginGroup .logout").css("display", "block");
                            $(".loginGroup .login").css("display", "none");
                        }
                    }
                });

            });


            //点击报名信息
            $('.userInfo').click(function(){
                console.log(111)
                $('.personBack').css({'display':'block'});
                //点击关闭
                $('.closeInfo').click(function(){
                    $('.personBack').css({'display':'none'});
                })
                //点击保存
                $('#save').click(function(){
                    $('.personBack').css({'display':'none'});
                })

            })
            //点击跳转充值记录
            $('.payHistory').click(function(){
                console.log(123)
                window.open('http://dev.ixincheng.com/web/html/personcenter.html')
                location.href="http://dev.ixincheng.com/web/html/personcenter.html"
                localStorage.setItem("personcenter","mymoney ");
                localStorage.setItem("findStyle","profile ");


            })
            //点击跳到提现记录
            $('.cashHistory').click(function(){
                console.log(456)
                location.href="http://dev.ixincheng.com/web/html/personcenter.html"
                localStorage.setItem("personcenter","mymoney");
                localStorage.setItem("findStyle","messages ");
            })

            var bigHeight = $(".bigpic").outerHeight(true);
            var introHeight = $("#introductBox").outerHeight(true);
            var navHeight = $(".nav").outerHeight(true);
            var height = bigHeight + introHeight + navHeight;
            $(document).scroll(function() {
                if($(document).scrollTop() > height + 61) {
                    $("#payCourseSlider").slideDown(100);
                } else {
                    $("#payCourseSlider").slideUp(100);
                }
            });
            if(data.resultObject.apply == true) {
                $("#payCourseSlider .purchase").css("display", "none");
                $("#payCourseSlider .studyImmed").css("display", "block");
            } else {
                if(data.resultObject.apply){
                    $("#payCourseSlider .purchase").css("display", "block");
                }else{
                    $("#payCourseSlider .purchase").css("display", "none");
                }

                $("#payCourseSlider .studyImmed").css("display", "none");
            }
            var pathName = location.pathname;
            var serach = location.search;
            var url = pathName + serach;
            url = url.replace(/\?/g, '--').replace(/&/g, '@@');
            RequestService("/online/user/isAlive", "GET", "", function(data) {
                if(!data.success) {
                    $("#d_clip_button").click(function() {
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
                    });
                } else {
                    if(data.resultObject.shareCode == null) {
                        $("#d_clip_button").click(function() {
                            $(".noShareDaShi").css("display", "block");
                        });
                    } else {
                        var sUrl = "http://" + window.location.hostname + "/share?usercode=" + data.resultObject.shareCode + "__" + url;
                        RequestService("/short/url", "POST", {
                            url: sUrl
                        }, function(data) {
                            if(data.success == true) {
                                $(".sharUrl").val(data.resultObject);
                                // 定义一个新的复制对象
                                var clip = new ZeroClipboard(document.getElementById("d_clip_button"), {
                                    moviePath: "/web/js/ZeroClipboard/ZeroClipboard.swf"
                                });
                                // 复制内容到剪贴板成功后的操作
                                clip.on('complete', function(client, args) {
                                    $(".shareDaShi").css("display", "block");
                                });
                            }
                        });
                    }
                }
            });

            share();

            function share() {
                $(".shareDaShi i").click(function() {
                    $(".shareDaShi").css("display", "none");
                });
                $(".noShareDaShi i").click(function() {
                    $(".noShareDaShi").css("display", "none");
                });
                $(".shareBtn").click(function() {
                    $(".shareDaShi").css("display", "none");
                });
                $(".getZige").click(function() {
                    $(".noShareDaShi").css("display", "none");
                    window.open("/web/html/payCourseDetailPage.html?id=" + data.resultObject.shareCourseId + "&courseType=1&free=0");
                });
                $(".hasAddcar i").click(function() {
                    $(".hasAddcar").css("display", "none");
                });
                $(".hasAddcar .goCar").click(function() {
                    $(".hasAddcar").css("display", "none");
                    window.open("/web/html/shoppingCart.html");
                });
            };

            //免费试学
            $(".free-try-to-lean").on("click", function() {
                RequestService("/online/user/isAlive", "GET", "", function(data) {
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
                    } else {
                        window.open("/web/html/freeAudition.html?courseId=" + courserId);
                    }
                }, false);
            })
        });
    });
    $(".course-problem").click(function() {
        $(".pages").css("display", "none");
        RequestService("/course/getCourseById", "GET", {
            courserId: courserId
        }, function(data) {
            if(data.resultObject.commonProblem == null || data.resultObject.commonProblem == "") {
                $(".table-modal").html(template.compile(emptyDefaul));
            } else {
                $(".table-modal").html(data.resultObject.commonProblem);
            }
        })
    });
    $(".course-outline").click(function() {
        $(".pages").css("display", "none");
        RequestService('/course/getCourseCatalog', "GET", {
            courseId: courserId
        }, function(data) {
            if(data.resultObject.length == 0) {
                $(".table-modal").html(template.compile(emptyDefaul));
            } else {
                //获取其他数据
                $(".table-modal").html(template.compile(courseOutline)({
                    items: data.resultObject
                }));
                for(var i = 0; i < $(".details-div-body p").length; i++) {
                    var $this = $(".details-div-body p").eq(i);
                    var last = $this.text().substring($this.text().length - 3, $this.text().length);
                    if(last == ".. ") {
                        $this.attr("data-txt", $this.attr("data-text"))
                    }
                }
            }
        });
    });
    $(".course-evaluate").click(function() {
        Evalutation();
    });
    $(".course-details").click(function() {
        RequestService("/course/getCourseById", "GET", {
            courserId: courserId
        }, function(data) {
            if(data.resultObject.description == null || data.resultObject.description == "") {
                $(".table-modal").html(template.compile(emptyDefaul));
            } else {
                //获取其他数据
                $(".table-modal").html("<div class='pic'>" + data.resultObject.description + "</div>");
            }
        })
    });
    $(".course-teacher").click(function() {
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
//			RequestService("/lecturer/list/course/" + courserId, "GET", "", function(data) {
//				if(data.resultObject.length == 0) {
//					$(".table-modal").html(template.compile(emptyDefaul));
//				} else {
//					$(".table-modal").html(template.compile(teacher)({
//						item: data.resultObject
//					}));
//					$(".teacher-introduction").dotdotdot();
//					for(i = 0; i < $(".teacher-introduction").length; i++) {
//						var $this = $(".teacher-introduction").eq(i);
//						var last = $this.text().substring($this.text().length - 3, $this.text().length);
//						if(last == ".. ") {
//							$this.attr("data-txt", $this.attr("data-text"))
//						}
//					}
//				}
//			});
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

    RequestService("/course/getRecommendCourseByCourseId", "GET", {
        courseId: courserId
    }, function(data) {
        $(".relative-course").html(template.compile(relativeCourse)({
            item: data.resultObject
        }));
        if(data.resultObject == "" || data.resultObject == null) {
            $(".by-the-arrow").css("display", "none");
            $(".by-the-arrow").css("display", "none");
            $(".slide-box").css("display", "none");
        } else {
            $(".relativeAnsNoData").css("display", "none");
            $(".boxContent li").eq(0).addClass("diyiye");
            $(".allLunbo").html(data.resultObject.length);
            var $index = 0;
            var $exdex = 0;
            $("#next").click(function() {
                if($index + 1 >= $(".allLunbo").text()) {
                    return false;
                } else {
                    $index++;
                }
                next();
                return $exdex = $index;
            })
            $("#prev").click(function() {
                if($index - 1 < 0) {
                    return false;
                } else {
                    $index--;
                }
                pre();
                return $exdex = $index;
            })
        }

        function next() {
            $(".currentLunbo").html($index + 1);
            $(".boxContent li").eq($index).stop(true, true).
            css("left", "100%").animate({
                "left": "0"
            });
            $(".boxContent li").eq($exdex).stop(true, true).
            css("left", "0").animate({
                "left": "-100%"
            });
        }

        function pre() {
            $(".currentLunbo").html($index + 1);
            $(".boxContent li").eq($index).stop(true, true).
            css("left", "-100%").animate({
                "left": "0"
            });
            $(".boxContent li").eq($exdex).stop(true, true).
            css("left", "0").animate({
                "left": "100%"
            });
        }
    });
    //省略号
    var $dot5 = $('.bigpic-body-text');
    $($dot5).each(function() {
        if($(this).height() > 80) {
            $(this).attr("data-txt", $(this).attr("data-text"));
            $(this).height(92);
        }
    });
    $('.bigpic-body-text').dotdotdot();
};

$('.online').on('click', function() {
    $('.online').css("background", "#23a523")
});
$('.enter-class').on('click', function() {
    $('.enter-class').css("background", "#23a523")
});
var bitS;
var bitS2;
$(".cu-shou").click(function() {
    if($(this).hasClass("noClick")) {

    } else {
        $(".cu-shou").removeClass("noClick").addClass("notpointer");
        $(this).addClass("noClick").removeClass("notpointer");
        bitS = $(this).text();
        $("#payCourseSlider li").each(function() {
            if($(this).text() == bitS) {
                $(this).addClass("noClick").removeClass("notpointer");
            }
        })
    }
});
$("#payCourseSlider li").click(function() {
    $(".cu-shou").removeClass("noClick").addClass("notpointer");
    $(this).addClass("noClick").removeClass("notpointer");
    bitS2 = $(this).text();
    $(".table-title span").each(function() {
        if($(this).text() == bitS2) {
            $(this).addClass("noClick").removeClass("notpointer");
        }
    })
})
$("#save").click(function (){
    kecheng();
});

function kecheng() {
    var name = true;
    var sex = true;
    //	var birthday=true;
    var phone = true;
    var email = true;
    var QQ = true;
    var weChat = true;
    var passName = /^[\u4E00-\u9FA5]{1,6}$/;
    var value = $.trim($(".kecheng .truename").val()); // 获取值
    var iden = true;
    //昵称不能能有空格
    $(".warning").css("display", "none");
    //	昵称不能为空

    //姓名正则验证
//	var name_test = /^[\u4E00-\u9FA5]{1,6}$/;

    if($.trim(value) == "") {
        $(".kecheng .true-warn").text("真实姓名不能为空").css("display", "inline-block");
        name = false;
        return false;
    } //昵称大于4位
    else if(!passName.test($(".truename ").val())) {
        $(".kecheng .true-warn").text("真实姓名请输入2-7个汉字").css("display", "inline-block");
        name = false;
        return false;
    };
    //性别验证
    if(!$('.cy-myprofile-myfom-dv-radio-zu input[name="gender"]:checked').val()) {
        $(".sex-warn").css("display", "inline-block");
        sex = false;
    };

    //手机号验证
    var phonePatt = /^1[3,4,5,7,8]\d{9}$/gi;
    if($.trim($(".phonenumber").val()) == "") {
        $(".phone-warn").text("手机号不能为空").css("display", "inline-block");
        phone = false;
        return false;
    } else if(!phonePatt.test($(".phonenumber").val())) {
        $(".phone-warn").text("手机号格式不正确").css("display", "inline-block");
        phone = false;
        return false;
    };

    //微信验证
    var WeChatPatt = /^[a-zA-Z\d_]{5,}$/;
    if($.trim($(".weiXinNumber").val()) == "") {
        $(".weChat-warn").text("微信号不能为空").css("display", "inline-block");
        weChat = false;
        return false;
    } else if(!WeChatPatt.test($(".weiXinNumber").val())) {
        $(".weChat-warn").text("微信号格式不正确").css("display", "inline-block");
        weChat = false;
        return false;
    }

    //身份证号验证
    var cardPatt = /^\d{17}(\d|x)$/;
    if($.trim($(".cardNumber").val()) == "") {
        $(".card-warn").text("身份证号不能为空").css("display", "inline-block");
        iden = false;
        return false;
    } else if(isCardID($(".cardNumber").val())!=true) {
        $(".card-warn").text(isCardID($(".cardNumber").val())).css("display", "inline-block");
        iden = false;
        return false;
    } else {
        $(".card-warn").css("display", "none");
    };

    //	QQ号验证
    var QQPatt = /^[1-9]\d{4,14}$/gi;
    if($.trim($(".QQnumber").val()) == "") {
//		$(".QQ-warn").text("QQ号不能为空").css("display", "inline-block");
        QQ = false;

    } else if(!QQPatt.test($(".QQnumber").val())) {
        $(".QQ-warn").text("QQ号格式不正确").css("display", "inline-block");
        QQ = false;
        return false;
    }



    //email号验证
    var emailPatt = /^(\w-*\_*)+@(\w-?)+(\.[a-zA-Z]{2,})+$/;
    if($.trim($(".emailname").val()) == "") {
//		$(".email-warn").text("邮箱不能为空").css("display", "inline-block");
        email = false;
    } else if(!emailPatt.test($(".emailname").val())) {
        $(".email-warn").text("邮箱格式不正确").css("display", "inline-block");
        email = false;
        return false;
    }

    var year = $("#year").html();
    var month = $("#month").html();
    var day = $("#day").html();

    if($('.man em').hasClass('active')){
        sex = 1;
    }else{
        sex = 0;
    }




    if(($(".truename").val()!='')&&($(".weiXinNumber").val()!='')&&($(".cardNumber").val()!='')){
        //点击保存的时候间数据传递到后台
        RequestService("/online/apply/saveOrUpdateApply", "post", {
            realName:$('.truename').val(),
            mobile:$('.phonenumber').val(),
            idCardNo:$('.cardNumber').val(),
            email:$('.emailname').val(),
            wechatNo:$('.weiXinNumber').val(),
            qq:$('.QQnumber').val(),
            referee:$('.introduce').val(),
            isFirst:isFirst,
            sex:sex,
            occupation:$('.profession').val()
        }, function(data) {
            if(data.success == true){
                //保存成功提示
//			alert('信息保存成功！')
                $('.rTips').html('保存成功').fadeIn(500,function(){
                    $('.rTips').fadeOut(500,function(){
                        $('.personBack').fadeOut()
                    })
                });
            }else{
                $('.rTips').html('信息不完整').fadeIn(500,function(){
                    $('.rTips').fadeOut(500)
                });
            }

        })
    }



}