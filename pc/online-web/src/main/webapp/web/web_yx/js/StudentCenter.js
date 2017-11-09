/**
 * Created by admin on 2016/11/1.
 */
window.onload = function () {
    var loginName = "";

      var base = new Config().base;

    template.helper('range', function (a, b) {
        var m = a / b;
        if (b == 0) {
            m = 0;
        } else {
            m = m * 120 + "px";
        }
        return "<span class='green' style='width:" + m + "'></span>"
    })
    template.helper('hasImg', function (obj) {
        if (obj != null) {
            return '<div class="aimg"><img  src="' + obj + '" class="rr"/></div>';
        } else {
            return '<div class="aimg" style="background-image:none;"><img   src="/web/images/load26.gif"/></div>';
        }
    });
    template.helper('zhibohref', function (id) {
        return "/web/html/CourseDetailZhiBo.html?courseId=" + id;
    });
    template.helper('orderState', function (num) {
        if (num == 0) {
            num = "待支付";
            return num;
        } else if (num == 1) {
            num = "已完成";
            return num;
        } else if (num == 2) {
            num = "已失效";
            return num;
        }
    });
    template.helper('image', function (num) {
        if (num == '' || num == null) {
            var m = parseInt(Math.random() * 20) + 1;
            num = "/web/images/defaultHead/" + m + ".png";
        } else {
            num = num;
        }
        return num;
    });
    template.helper("fenji", function (num) {
        if (num == 0) {
            return "一级";
        } else if (num == 1) {
            return "二级";
        } else if (num == 2) {
            return "三级";
        }
    });
    var emptyDefaul =
        "<div class='page-no-result'>" +
        "<img src='../images/personcenter/my_nodata.png'>" +
        "<div class='no-title'>暂无数据</div>" +
        "</div>";
    //班级信息
    var banjiMess =
        '<span class="banjiName">{{item.squad_name}}</span>' +
        '<span class="renshu">人数：</span>' +
        '<span class="renshu1">{{item.stuCount}}人</span>';
    //教师信息
    var teacherMess =
        '{{each item as $value i}}' +
        '<div class="teach_01">' +
        '<div class="teach_img">' +
        '	<img src="{{#image($value.personal_img)}}" />' +
        '</div>' +
        '<span class="dian">' +
        '	<span class="green"></span>' +
        '</span>' +
        '<div class="teach_mess">' +
        '<p class="name">{{$value.name}}</p>' +
        '<p class="xueke" title="{{$value.courseList[0].name}}">课程：{{$value.courseList[0].name}}</p>' +
        '</div>' +
        '{{if $value.courseList.length>1}}' +
        '<div class="more clearfix">' +
        '<span class="mm">课程：</span>' +
        '<div>' +
        '{{each $value.courseList as $m m}}' +
        '<span title="{{$m.name}}">{{$m.name}}</span>' +
        '{{/each}}' +
        '</div>' +
        '<span class="sanjiao"></span>' +
        '</div>' +

        '{{/if}}' +
        '</div>' +
        '{{/each}}';
    //学生信息
    var studentMess =
        '{{each item as $value i}}' +
        '<div class="child_01" data-iumb="{{i+1}}">' +
        '<div class="stud_img">' +
        '<img src="{{#image($value.img_url)}}" />' +
        '</div>' +
        '<span class="dian">' +
        '	<span class="blue"></span>' +
        '</span>' +
        '<div class="stud_mess">' +
        '<p class="name">{{$value.name}}</p>' +
        '<p class="xueke">{{$value.studentNo}}</p>' +
        '</div>' +
        '<div class="more">' +
        '<p>姓名：{{$value.name}}</p>' +
        '<p>学号：{{$value.studentNo}}</p>' +
        '<p>手机号：{{$value.mobile}}</p>' +
        '<p>QQ号：{{$value.qq}}</p>' +
        '<p>邮箱：{{$value.email}}</p>' +
        '<p>小组：{{$value.group}}组</p>' +
        '<span class="sanjiao"></span>' +
        '</div>' +
        '</div>' +
        '{{/each}}';
    //学生分组
    var groupMess =
        '<ul>\
            <li class="active" group="">全部</li>\
            {{each lists as item}}\
                <li data-groupid="{{item.group_name}}">{{item.group_name}}组</li>\
            {{/each}}\
        </ul>';
    //当前考试内容
    var exam1 =
        '{{each item as $value i}}' +
        '<li class="clearfix">' +
        '<span class="name" title="{{$value.paperTplName}}">{{$value.paperTplName}}</span>' +
        '<span class="course" title="{{$value.courseName}}">{{$value.courseName}}</span>' +
        '<span class="starttime">{{$value.startTime}}</span>' +
        '<span class="endtime">{{$value.endTime}}</span>' +
        '<span class="timelength">{{$value.duration}}</span>' +
        '{{if $value.status==0}}' +
        '<span class="tool" data-examId="{{$value.examId}}" data-studentId="{{$value.studentId}}"><em>开始考试</em></span>' +
        '{{/if}}' +
        '{{if $value.status==1}}' +
        '<span class="tool" data-examId="{{$value.examId}}" data-studentId="{{$value.studentId}}"><em>继续考试</em></span>' +
        '{{/if}}' +
        '</li>' +
        '{{/each}}';
    //历史考试内容
    var exam2 =
        '{{each item as $value i}}' +
        '<li class="clearfix">' +
        '<span class="name" title="{{$value.paperTplName}}">{{$value.paperTplName}}</span>' +
        '<span class="course" title="{{$value.courseName}}">{{$value.courseName}}</span>' +
        '<span class="starttime">{{$value.startTime}}</span>' +
        '<span class="endtime">{{$value.endTime}}</span>' +
        '{{if $value.status==2||$value.status==3}}' +
        '<span class="state">已交卷</span>' +
        '{{/if}}' +
        '{{if $value.status==4}}' +
        '<span class="state">已批阅</span>' +
        '{{/if}}' +
        '{{if $value.status==5}}' +
        '<span class="state">未交卷</span>' +
        '{{/if}}' +
        '{{if $value.status!=4}}' +
        '<span class="score">--</span>' +
        '{{else}}' +
        '<span class="score">{{$value.score}}</span>' +
        '{{/if}}' +
        '<span class="tool" data-state="{{$value.status}}" data-studentId="{{$value.studentId}}" data-examId="{{$value.examId}}"><em>查看试卷</em></span>' +
        '</li>' +
        '{{/each}}';
    //当前作业内容
    var work1 =
        '{{each item as $value i}}' +
        '<li>' +
        '<span class="name" title="{{$value.homework_name}}">{{$value.homework_name}}</span>' +
        '<span class="course" title="{{$value.name}}">{{$value.name}}</span>' +
        '<span class="starttime">{{$value.publish_time}}</span>' +
        '<span class="endtime">{{$value.end_time}}</span>' +
        '<span class="timelength">{{$value.isAnswer}}/{{$value.total_questions}}</span>' +
        '{{if $value.status==0}}' +
        '<span class="tool" data-homeId="{{$value.homework_id}}" data-studentId="{{$value.student_id}}"><em>开始作业</em></span>' +
        '{{/if}}' +
        '{{if $value.status==1}}' +
        '<span class="tool" data-homeId="{{$value.homework_id}}" data-studentId="{{$value.student_id}}"><em>继续作业</em></span>' +
        '{{/if}}' +
        '</li>' +
        '{{/each}}';
    //历史作业内容
    var work2 =
        '{{each item as $value i}}' +
        '<li>' +
        '<span class="name" title="{{$value.homework_name}}">{{$value.homework_name}}</span>' +
        '<span class="course" title="{{$value.name}}">{{$value.name}}</span>' +
        '<span class="starttime">{{$value.publish_time}}</span>' +
        '<span class="endtime">{{$value.end_time}}</span>' +
        '{{if $value.status==2}}' +
        '<span class="state">已交卷</span>' +
        '{{/if}}' +
        '{{if $value.status==3}}' +
        '<span class="state">未交卷</span>' +
        '{{/if}}' +
        '{{if $value.correct_percent==""||$value.correct_percent==null}}' +
        '<span class="score">--</span>' +
        '{{else}}' +
        '<span class="score">{{$value.correct_percent}}%</span>' +
        '{{/if}}' +
        '<span class="tool" data-homeId="{{$value.homework_id}}" data-studentId="{{$value.student_id}}"><em>查看作业</em></span>' +
        '</li>' +
        '{{/each}}';

    var work3 = '{{if success}}\
                {{if resultObject.items.length != 0}}\
				{{each resultObject.items}}\
				<li class="clearfix">\
					<span class="name">{{$value.reviewName}}</span>\
					<span class="course">{{$value.courseName}}</span>\
					<span class="starttime">{{$value.publishTime}}</span>\
					<span class="endtime">\
						<span class="endtime_span0" style="background-color: #f0f0f0">\
						<span class="endtime_span1">{{$value.progress}}%</span>\
						<span class="endtime_span2" style="width:{{$value.progress}}%;">&nbsp;</span>\
					</span>\
					</span>\
					{{if $value.progress == 100}}\
					    <span class="timelength"><a href="/web/web_yx/views/index.html#/review/details/{{$value.reviewId}}" target="_blank">查看复习</a></span>\
					    {{else}}\
					    <span class="timelength"><a href="/web/web_yx/views/index.html#/review/details/{{$value.reviewId}}" target="_blank">开始复习</a></span>\
					{{/if}}\
				</li>\
				{{/each}}\
				{{else}}\
				<div class="page-no-result">\
				<img src="../images/personcenter/my_nodata.png">\
				<div class="no-title">暂无数据</div>\
				</div>"\
				{{/if}}\
				{{else}}\
				<div class="page-no-result">\
				<img src="../images/personcenter/my_nodata.png">\
				<div class="no-title">暂无数据</div>\
				</div>"\
				{{/if}}';

    var work4 = '{{if resultObject.items.length != 0}}\
				{{each resultObject.items}}\
				<li class="clearfix">\
					<span class="name">{{$value.courseName}}</span>\
					<span class="endtime">\
					<span class="endtime_span0" style="background-color: #f0f0f0">\
					<span class="endtime_span1">{{$value.progress}}%</span>\
					<span class="endtime_span2" style="width:{{$value.progress}}%;">&nbsp;</span>\
					</span>\
					</span>\
					{{if $value.progress == 100}}\
					    <span class="timelength"><a href="/web/web_yx/views/index.html#/preview/details/{{$value.courseId}}" target="_blank">查看预习</a></span>\
					    {{else}}\
					    <span class="timelength"><a href="/web/web_yx/views/index.html#/preview/details/{{$value.courseId}}" target="_blank">开始预习</a></span>\
					{{/if}}\
				</li>\
				{{/each}}\
				{{else}}\
				<div class="page-no-result">\
				<img src="../images/personcenter/my_nodata.png">\
				<div class="no-title">暂无数据</div>\
				</div>"\
				{{/if}}';

    //帐号信息
    RequestService("/online/user/isAlive", "GET", null, function (data) { ///online/user/isAlive
        if (data.success === true) {
            loginName = data.resultObject.loginName;
            college("");
            var tj = {
                courseId: "",
                examName: "",
                status: "",
                startTime: "",
                endTime: "",
                pageNumber: 1,
                pageSize: 10
            };
            var wktj = {
                courseId: "",
                homeworkName: "",
                status: "",
                startTime: "",
                endTime: "",
                pageNumber: 1,
                pageSize: 10
            };
            var rwtj = {
                courseId: "",
                isFinished: "",
                loginName: "",
                publishTimeFrom: "",
                publishTimeTo: "",
                pageNumber: 1,
                pageSize: 10
            };
            //ifame
            function ifa() {
                $(window.parent.document).find("#fra").load(function () {
                    var main = $(window.parent.document).find("#iframeId");
                    var thisheight = $(document).height() + 30;
                    main.height(thisheight);
                });
            };
            //提示框
            function rTips(num) {
                $(".rTips").html(num).css("display", "block");
                setTimeout(function () {
                    $(".rTips").css("display", "none");
                }, 1000)
            };

            var rulset = '';

            function college(group) {
                //我的作业   我的班级   我的考试的tab切换
                $(".mycollege .right-title span").off("click").on("click", function () {
                    ifa();
                    $(this).addClass("act").siblings().removeClass("act");
                    $(".allcollege").find(".college" + $(this).attr("college")).show().siblings().hide();
                    if ($(this).attr("college") == "2") {
                        $(".allcollege .college2").find(".table").show().siblings(".history").hide();
                        work(1, wktj);
                    } else if ($(this).attr("college") == "3") {
                        $(".allcollege .college3").find(".table").show().siblings(".history").hide();
                        exam(1, tj);
                    } else if ($(this).attr("college") == "4") {
                        preview();
                    } else if ($(this).attr("college") == "5") {
                        review(1, rwtj);
                    }
                });
                //教师信息获取
                RequestService("/api/callThirdPost", "post", {
                    thirdUrl: base + "/bxg_anon/student/findMyTeacherToCourse",
                    loginName: loginName
                }, function (data) {
                    $(".college1 .banji").html(template.compile(banjiMess)({
                        item: data.resultObject.squadData
                    }));
                    $(".college1 .teach").html(template.compile(teacherMess)({
                        item: data.resultObject.teacherData
                    }));
                });

                /**
                 * 学生分组
                 */
                var groupId = '';
                //callThirdPost(groupId);
                (function callThirdPost(groupId) {
                    rulset = {};
                    RequestService("/api/callThirdPost", "post", {
                        thirdUrl: base + "/bxg_anon/student/findSquadGroup",
                        loginName: loginName,
                        group: groupId ? groupId : ""
                    }, function (callThirdData) {
                        //console.log('dd:',callThirdData);
                        //掉接口返回数据
                        $(".college1 .tab").html(template.compile(groupMess)({
                            lists: callThirdData.resultObject
                        }));
                        clickNen();

                        //$(".college1 .student .stud .stud-child ").off().on("click", function(){
                        //	$(this).addClass("active").siblings().removeClass("active");
                        //});

                    });
                    //return rulset;
                })(groupId);

                //添加背景颜色
                clickNen();
                function clickNen() {
                    $(".college1 .student .tab li").on("click", function () {
                        $(this).addClass("active").siblings().removeClass("active");
                        var groupId = $(this).data('groupid');
                        student(groupId);
                    });
                };


                //学生信息
                student(group);
                //班级同学的学生信息
                function student(group) {
                    RequestService("/api/callThirdPost", "post", {
                        thirdUrl: base + "/bxg_anon/student/findMyStudents",
                        loginName: loginName,
                        group: group ? group : ""
                    }, function (data) {
                        $(".college1 .stud-child").html(template.compile(studentMess)({
                            item: data.resultObject
                        }));
                        /*$(".college1 .student .tab li").off().on("click", function() {
                         $(this).addClass("active").siblings().removeClass("active");
                         student($(this).attr("group"));
                         });*/
                        $(".college1 .right-college .student .stud .stud-child .child_01").off().on("mouseover", function () {
                            var stud1 = $(this).data('iumb');
                            //console.log(stud1);
                            stud1 = stud1.toString();
                            var spstr = stud1.split('');
                            var isto = spstr[spstr.length - 1];
                            console.log('---------', isto);
                            if (isto == 0 || isto == 5) {
                                //console.log("不正常");
                                $('.more').css('left', '-140px');
                                $('.more .sanjiao').css({
                                    'border-left-color': '#fff',
                                    'left': '157px',
                                    'border-right-color': 'rgba(0,0,0,0)'
                                });

                            } else {
                                $(this).addClass("active1").siblings().removeClass("active1");
                                //console.log("正常");
                                $('.more').css('left', '110px');
                                $('.more .sanjiao').css({
                                    'border-right-color': '#fff',
                                    'left': '-18px',
                                    'border-left-color': 'rgba(0,0,0,0)'
                                });
                            }

                        });

                    });
                };
                allcourse();
            };
            //我的考试
            function exam(type, tj) {
                $(".pages").css("display", "none");
                //1代表当前考试，2代表历史考试
                if (type == 1) {
                    $(".college3 .tab3 span").eq(0).addClass("act").siblings().removeClass("act");
                    $(".college3 .table").show().siblings(".history").hide();
                    RequestService("/api/callThirdPost", "post", {
                        thirdUrl: base + "/bxg_anon/my/exam/currentExam",
                        loginName: loginName,
                    }, function (data) {
                        if (data.resultObject == "" || data.resultObject == null) {
                            $(".college3 .table ul").html(template.compile(emptyDefaul));
                        } else {
                            $(".college3 .table ul").html(template.compile(exam1)({
                                item: data.resultObject
                            }));
                        }
                        ;
                        $(".college3 .table ul .tool ").off().on("click", function () {
                            var $this = $(this);
                            RequestService("/api/callThirdPost", "post", {
                                thirdUrl: base + "/bxg_anon/my/exam/checkExam",
                                examId: $this.attr("data-examid"),
                                studentId: $this.attr("data-studentid")
                            }, function (data) {
                                if (data.success == true) {
                                    window.open("examJuan.html?examId=" + $this.attr("data-examid") + "&studentid=" + $this.attr("data-studentid"), "_blank");
                                } else {
                                    rTips(data.errorMessage);
                                }
                                ;
                            }, false);
                        });
                    });
                } else if (type == 2) {
                    $(".college3 .tab3 span").eq(1).addClass("act").siblings().removeClass("act");
                    $(".college3 .history").show().siblings(".table").hide();
                    //加载历史考试试卷
                    RequestService("/api/callThirdPost", "post", {
                        thirdUrl: base + "/bxg_anon/my/exam/hisExam",
                        loginName: loginName,
                        courseId: tj.courseId ? tj.courseId : "",
                        examName: tj.examName ? tj.examName : "",
                        status: tj.status ? tj.status : "",
                        startTime: tj.startTime ? tj.startTime : "",
                        endTime: tj.endTime ? tj.endTime : "",
                        pageNumber: tj.pageNumber,
                        pageSize: 10
                    }, function (data) {
                        if (data.resultObject.items == "" || data.resultObject.items == null) {
                            $(".college3 .history ul").html(template.compile(emptyDefaul));
                        } else {
                            if (data.resultObject.items == "") {
                                if (data.resultObject.totalCount == 0) { //无数据
                                    $(".college3 .history ul").html(template.compile(emptyDefaul));
                                    $(".pages").css("display", "none");
                                }
                            } else if (data.resultObject.totalPageCount == 1 && data.resultObject.totalCount > 0) {
                                $(".pages").css("display", "none");
                                $(".college3 .history ul").html(template.compile(exam2)({
                                    item: data.resultObject.items
                                }));
                            } else if (data.resultObject.totalPageCount > 1) {
                                $(".pages").css("display", "block");
                                $(".searchPage .allPage").text(data.resultObject.totalPageCount);
                                if (data.resultObject.currentPage == 1) {
                                    $("#Pagination").pagination(data.resultObject.totalPageCount, {
                                        callback: function (page) { //翻页功能
                                            console.log(page)
                                            var atj = {
                                                courseId: tj.courseId ? tj.courseId : "",
                                                examName: tj.examName ? tj.examName : "",
                                                status: tj.status ? tj.status : "",
                                                startTime: tj.startTime ? tj.startTime : "",
                                                endTime: tj.endTime ? tj.endTime : "",
                                                pageNumber: parseInt(page + 1),
                                                pageSize: tj.pageSize ? tj.pageSize : 10
                                            };
                                            exam(2, atj)
                                        }
                                    });
                                }
                                $(".college3 .history ul").html(template.compile(exam2)({
                                    item: data.resultObject.items
                                }));
                            }
                            ;
                            $(".college3 .history ul .tool").off().on("click", function () {
                                if ($(this).attr("data-state") != 4) {
                                    window.open("weiPiyue.html?examId=" + $(this).attr("data-examid") + "&studentid=" + $(this).attr("data-studentid") + "&status=" + $(this).attr("data-state"), "_blank");
                                } else {
                                    window.open("myExamAnswer.html?examId=" + $(this).attr("data-examid") + "&studentid=" + $(this).attr("data-studentid") + "&status=" + $(this).attr("data-state"), "_blank");
                                }
                                ;
                            });
                        }
                    });
                }
                ;

                //考试根据交卷状态筛选
                $(".college3 .filter_02 .name_01 .xiala em").off().on("click", function () {
                    $(this).parent().siblings().html($(this).html()).attr("data-state", $(this).attr("data-state"));
                    var tj = {
                        courseId: $(".college3 .history .filter_01 .name_01 i").attr("data-courseid"),
                        examName: $(".college3 .history .filter_03 input").val(),
                        status: $(this).attr("data-state"),
                        startTime: $(".college3 .history .filter_04 .time").val(),
                        endTime: $(".college3 .history .filter_04 .time1").val(),
                        pageNumber: 1,
                        pageSize: 10
                    };
                    exam(2, tj);
                });
                //考试作业的当前和历史点击切换
                $(".college3 .tab3 span").off().on("click", function () {
                    $(this).addClass("act").siblings().removeClass("act");
                    first();
                    exam($(this).attr("exam"), "");
                });
                //根据输入筛选内容
                $(".college3 .filter_03 .iconfont").off("click").on("click", function () {
                    var tj = {
                        courseId: $(".college3 .history .filter_01 .name_01 i").attr("data-courseid"),
                        examName: $(".college3 .history .filter_03 input").val(),
                        status: $(".college3 .filter_02 .name_01 i").attr("data-state"),
                        startTime: $(".college3 .history .filter_04 .time").val(),
                        endTime: $(".college3 .history .filter_04 .time1").val(),
                        pageNumber: 1,
                        pageSize: 10
                    };
                    exam(2, tj);
                });
                $(".college3 .filter_03 input").off().on("keypress", function (e) {
                    if (e.keyCode == 13) {
                        return $(".college3 .filter_03 .iconfont").click();
                    }
                });
                //根据时间
                $(".college3 .history .filter_04 input").off().on("blur", function () {
                    setTimeout(function () {
                        if ($(".college3 .history .filter_04 .time1").val() && $(".college3 .history .filter_04 .time").val()) {
                            var tj = {
                                courseId: $(".college3 .history .filter_01 .name_01 i").attr("data-courseid"),
                                examName: $(".college3 .history .filter_03 input").val(),
                                status: $(".college3 .filter_02 .name_01 i").attr("data-state"),
                                startTime: $(".college3 .history .filter_04 .time").val(),
                                endTime: $(".college3 .history .filter_04 .time1").val(),
                                pageNumber: 1,
                                pageSize: 10
                            };
                            exam(2, tj);
                        }
                    }, 300)
                });
                //点击开始考试和继续考试
            };
            //我的作业
            function work(type, wktj) {
                $(".pages").css("display", "none");
                //1代表当前考试，2代表历史考试
                if (type == 1) {
                    $(".college2 .tab2 span").eq(0).addClass("act").siblings().removeClass("act");
                    $(".college2 .table").show().siblings(".history").hide();
                    RequestService("/api/callThirdPost", "post", {
                        thirdUrl: base + "/bxg_anon/my/homework/currentHomework",
                        loginName: loginName,
                    }, function (data) {
                        if (data.resultObject == "" || data.resultObject == null) {
                            $(".college2 .table ul").html(template.compile(emptyDefaul));
                        } else {
                            $(".college2 .table ul").html(template.compile(work1)({
                                item: data.resultObject
                            }));
                        }
                        ;
                        $(".college2 .table ul .tool").off().on("click", function () {
                            window.open("zuoye.html?homeworkId=" + $(this).attr("data-homeid") + "&studentid=" + $(this).attr("data-studentid"), "_blank");
                        });
                    });
                } else if (type == 2) {
                    $(".college2 .tab2 span").eq(1).addClass("act").siblings().removeClass("act");
                    $(".college2 .history").show().siblings(".table").hide();
                    //加载历史作业试卷
                    RequestService("/api/callThirdPost", "post", {
                        thirdUrl: base + "/bxg_anon/my/homework/hisHomework",
                        loginName: loginName,
                        courseId: wktj.courseId ? wktj.courseId : "",
                        homeworkName: wktj.homeworkName ? wktj.homeworkName : "",
                        status: wktj.status ? wktj.status : "",
                        startTime: wktj.startTime ? wktj.startTime : "",
                        endTime: wktj.endTime ? wktj.endTime : "",
                        pageNumber: wktj.pageNumber,
                        pageSize: 10
                    }, function (data) {
                        if (data.resultObject.items == "" || data.resultObject.items == null) {
                            $(".college2 .history ul").html(template.compile(emptyDefaul));
                        } else {
                            if (data.resultObject.items == "") {
                                if (data.resultObject.totalCount == 0) { //无数据
                                    $(".college2 .history ul").html(template.compile(emptyDefaul));
                                    $(".pages").css("display", "none");
                                }
                            } else if (data.resultObject.totalPageCount == 1 && data.resultObject.totalCount > 0) {
                                $(".pages").css("display", "none");
                                $(".college2 .history ul").html(template.compile(work2)({
                                    item: data.resultObject.items
                                }));
                            } else if (data.resultObject.totalPageCount > 1) {
                                $(".pages").css("display", "block");
                                $(".searchPage .allPage").text(data.resultObject.totalPageCount);
                                if (data.resultObject.currentPage == 1) {
                                    $("#Pagination").pagination(data.resultObject.totalPageCount, {
                                        callback: function (page) { //翻页功能
                                            var awktj = {
                                                courseId: wktj.courseId ? wktj.courseId : "",
                                                homeworkName: wktj.homeworkName ? wktj.homeworkName : "",
                                                status: wktj.status ? wktj.status : "",
                                                startTime: wktj.startTime ? wktj.startTime : "",
                                                endTime: wktj.endTime ? wktj.endTime : "",
                                                pageNumber: parseInt(page + 1),
                                                pageSize: wktj.pageSize
                                            };
                                            work(2, awktj)
                                        }
                                    });
                                }
                                $(".college2 .history ul").html(template.compile(work2)({
                                    item: data.resultObject.items
                                }));
                            }
                        }
                        $(".college2 .history ul .tool").off().on("click", function () {
                            window.open("assignment.html?homeworkId=" + $(this).attr("data-homeid") + "&studentid=" + $(this).attr("data-studentid"), "_blank");
                        });
                    });
                }
                //考试根据交卷状态筛选
                $(".college2 .filter_02 .name_01 .xiala em").off().on("click", function () {
                    $(this).parent().siblings().html($(this).html()).attr("data-state", $(this).attr("data-state"));
                    var wktj = {
                        courseId: $(".college2 .history .filter_01 .name_01 i").attr("data-courseid"),
                        homeworkName: $(".college2 .history .filter_03 input").val(),
                        status: $(this).attr("data-state"),
                        startTime: $(".college2 .history .filter_04 .time").val(),
                        endTime: $(".college2 .history .filter_04 .time1").val(),
                        pageNumber: 1,
                        pageSize: 10
                    };
                    work(2, wktj);
                });
                //根据输入筛选内容
                $(".college2 .filter_03 .iconfont").off("click").on("click", function () {
                    var wktj = {
                        courseId: $(".college2 .history .filter_01 .name_01 i").attr("data-courseid"),
                        homeworkName: $(".college2 .history .filter_03 input").val(),
                        status: $(".college2 .filter_02 .name_01 i").attr("data-state"),
                        startTime: $(".college2 .history .filter_04 .time").val(),
                        endTime: $(".college2 .history .filter_04 .time1").val(),
                        pageNumber: 1,
                        pageSize: 10
                    };
                    work(2, wktj);
                });
                $(".college2 .filter_03 input").off().on("keypress", function (e) {
                    if (e.keyCode == 13) {
                        return $(".college2 .filter_03 .iconfont").click();
                    }
                });
                //根据时间
                $(".college2 .history .filter_04 input").off().on("blur", function () {
                    setTimeout(function () {
                        if ($(".college2 .history .filter_04 .time1").val() && $(".college2 .history .filter_04 .time").val()) {
                            var wktj = {
                                courseId: $(".college2 .history .filter_01 .name_01 i").attr("data-courseid"),
                                homeworkName: $(".college2 .history .filter_03 input").val(),
                                status: $(".college2 .filter_02 .name_01 i").attr("data-state"),
                                startTime: $(".college2 .history .filter_04 .time").val(),
                                endTime: $(".college2 .history .filter_04 .time1").val(),
                                pageNumber: 1,
                                pageSize: 10
                            };
                            work(2, wktj);
                        }
                    }, 300)
                });
                //考试作业的当前和历史点击切换
                $(".college2 .tab2 span").off().on("click", function () {
                    $(this).addClass("act").siblings().removeClass("act");
                    first();
                    work($(this).attr("work"), "");
                });
            };
            //我的复习
            function review(type, rwtj) {
                RequestService("/api/callThirdPost", "post", {
                    thirdUrl: base + "/bxg_stu/common/findCourse",
                    loginName: loginName
                }, function (data) {
                    if (data.success) {
                        if (data.resultObject.length != 0) {
                            $(".college5 .xiala").empty().html("<em data-courseid=''>请选择</em>");
                            for (var o = 0; o < data.resultObject.length; o++) {
                                $(".college5 .xiala").append("<em data-courseid=" + data.resultObject[o].id + ">" + data.resultObject[o].name + "</em>")
                            }
                            $(".college5 .filter_01 .name_01 .xiala em").unbind().click(function () {
                                var _this = $(this);
                                _this.parent().siblings().html(_this.html()).attr("data-courseId", _this.attr("data-courseid"));
                                if (type == 1) {
                                    var Finished = 0;
                                    var courseId1 = $(".college5_span_select1 i").attr("data-courseId");
                                    var publishTimeFrom1 = $(".college5_span_time1 .time").val();
                                    var publishTimeTo1 = $(".college5_span_time1 .time1").val();
                                } else if (type == 2) {
                                    var Finished = 1;
                                    var courseId1 = $(".college5_span_select2 i").attr("data-courseId");
                                    var publishTimeFrom1 = $(".college5_span_time2 .time").val();
                                    var publishTimeTo1 = $(".college5_span_time2 .time1").val();
                                }
                                var rwtj1 = {
                                    courseId: courseId1 ? courseId1 : "",
                                    isFinished: Finished,
                                    loginName: loginName,
                                    publishTimeFrom: publishTimeFrom1 ? publishTimeFrom1 : "",
                                    publishTimeTo: publishTimeTo1 ? publishTimeTo1 : "",
                                    pageNumber: 1,
                                    pageSize: 10
                                };
                                review(type, rwtj1);
                            });
                        } else {
                            $(".college5 .xiala").html("<em data-courseid=''>请选择</em>");
                        }
                    }
                });
                if (type == 1) {
                    $(".pages").css("display", "none");
                    $(".college5 .tab5 span").eq(0).addClass("act").siblings().removeClass("act");
                    $(".college5 .table").show().siblings(".history").hide();
                    RequestService("/api/callThirdPost", "post", {
                        thirdUrl: base + "/bxg_stu/review/list",
                        courseId: rwtj.courseId ? rwtj.courseId : "",
                        isFinished: 0,
                        loginName: loginName,
                        publishTimeFrom: rwtj.publishTimeFrom ? rwtj.publishTimeFrom : "",
                        publishTimeTo: rwtj.publishTimeTo ? rwtj.publishTimeTo : "",
                        pageNumber: rwtj.pageNumber || 1,
                        pageSize: 10
                    }, function (data) {
                        $(".college5_table_tbody1").html(template.compile(work3)(data));
                        if (data.resultObject.totalPageCount > 1) {
                            $(".pages").css("display", "block");
                            $(".searchPage .allPage").text(data.resultObject.totalPageCount);
                            if (data.resultObject.totalPageCount > 1) {
                                $("#Pagination").pagination(data.resultObject.totalPageCount, {
                                    callback: function (page) {
                                        //翻页功能
                                        console.log(page + "===");
                                        var rwtj1 = {
                                            thirdUrl: base + "/bxg_stu/review/list",
                                            courseId: rwtj.courseId ? rwtj.courseId : "",
                                            isFinished: 0,
                                            loginName: loginName,
                                            publishTimeFrom: rwtj.publishTimeFrom ? rwtj.publishTimeFrom : "",
                                            publishTimeTo: rwtj.publishTimeTo ? rwtj.publishTimeTo : "",
                                            pageNumber: parseInt(page + 1),
                                            pageSize: 10
                                        };
                                        RequestService("/api/callThirdPost", "post", rwtj1, function (data) {
                                            if (data.success) {
                                                $(".college5_table_tbody1").html(template.compile(work3)(data));
                                            }
                                        })
                                    }
                                });
                            }
                            $(".Start_review").unbind().click(function(){
                                var _this = $(this);

                            })
                        }

                    })
                } else if (type == 2) {
                    $(".pages").css("display", "none");
                    $(".college5 .tab5 span").eq(1).addClass("act").siblings().removeClass("act");
                    $(".college5 .history").show().siblings(".table").hide();
                    RequestService("/api/callThirdPost", "post", {
                        thirdUrl: base + "/bxg_stu/review/list",
                        courseId: rwtj.courseId ? rwtj.courseId : "",
                        isFinished: 1,
                        loginName: loginName,
                        publishTimeFrom: rwtj.publishTimeFrom ? rwtj.publishTimeFrom : "",
                        publishTimeTo: rwtj.publishTimeTo ? rwtj.publishTimeTo : "",
                        pageNumber: rwtj.pageNumber ? rwtj.pageNumber : 1,
                        pageSize: rwtj.pageSize ? rwtj.pageSize : 10
                    }, function (data) {
                        if (data.success) {
                            $(".college5_table_tbody2").html(template.compile(work3)(data));
                            if (data.resultObject.totalPageCount > 1) {
                                $(".pages").css("display", "block");
                                $(".searchPage .allPage").text(data.resultObject.totalPageCount);
                                if (data.resultObject.currentPage == 1) {
                                    $("#Pagination").pagination(data.resultObject.totalPageCount, {
                                        callback: function (page) { //翻页功能
                                            console.log(page);
                                            var rwtj1 = {
                                                courseId: rwtj.courseId ? rwtj.courseId : "",
                                                isFinished: 1,
                                                loginName: loginName,
                                                publishTimeFrom: rwtj.publishTimeFrom ? rwtj.publishTimeFrom : "",
                                                publishTimeTo: rwtj.publishTimeTo ? rwtj.publishTimeTo : "",
                                                pageNumber: parseInt(page + 1),
                                                pageSize: 10
                                            };
                                            RequestService("/api/callThirdPost", "post", rwtj1, function (data) {
                                                if (data.success) {
                                                    $(".college5_table_tbody2").html(template.compile(work3)(data));
                                                }
                                            })
                                        }
                                    });
                                }
                            }
                        }
                    })
                }
                //根据时间
                $(".college5  .filter_04 input").off().on("blur", function () {
                    setTimeout(function () {
                        if (type == 1) {
                            var Finished = 0;
                            var courseId1 = $(".college5_span_select1 i").attr("data-courseId");
                            var publishTimeFrom1 = $(".college5_span_time1 .time").val();
                            var publishTimeTo1 = $(".college5_span_time1 .time1").val();
                        } else if (type == 2) {
                            var Finished = 1;
                            var courseId1 = $(".college5_span_select2 i").attr("data-courseId");
                            var publishTimeFrom1 = $(".college5_span_time2 .time").val();
                            var publishTimeTo1 = $(".college5_span_time2 .time1").val();
                        }
                        var rwtj1 = {
                            courseId: courseId1 ? courseId1 : "",
                            isFinished: Finished,
                            loginName: loginName,
                            publishTimeFrom: publishTimeFrom1 ? publishTimeFrom1 : "",
                            publishTimeTo: publishTimeTo1 ? publishTimeTo1 : "",
                            pageNumber: 1,
                            pageSize: 10
                        };
                        if (publishTimeFrom1 != "" && publishTimeTo1 != "") {
                            review(type, rwtj1);
                        }

                    }, 300)
                });
                //考试作业的当前和历史点击切换
                $(".college5 .tab5 span").off().on("click", function () {
                    $(this).addClass("act").siblings().removeClass("act");
                    if ($(this).attr("work") == 1) {
                        var Finished = 0;
                        var courseId1 = $(".college5_span_select1 i").attr("data-courseId");
                        var publishTimeFrom1 = $(".college5_span_time1 .time").val();
                        var publishTimeTo1 = $(".college5_span_time1 .time1").val();
                    } else if ($(this).attr("work") == 2) {
                        var Finished = 1;
                        var courseId1 = $(".college5_span_select2 i").attr("data-courseId");
                        var publishTimeFrom1 = $(".college5_span_time2 .time").val();
                        var publishTimeTo1 = $(".college5_span_time2 .time1").val();
                    }
                    var rwtj1 = {
                        courseId: courseId1 ? courseId1 : "",
                        isFinished: Finished,
                        loginName: loginName,
                        publishTimeFrom: publishTimeFrom1 ? publishTimeFrom1 : "",
                        publishTimeTo: publishTimeTo1 ? publishTimeTo1 : "",
                        pageNumber: 1,
                        pageSize: 10
                    };
                    first();
                    review($(this).attr("work"), "");
                });
            }

            //我的预习
            function preview() {
                $(".pages").css("display", "none");
                RequestService("/api/callThirdPost", "post", {
                    thirdUrl: base + "/bxg_stu/preview/findPreviewStudentPage",
                    loginName: loginName,
                    pageNumber: 1,
                    pageSize: 10
                }, function (data) {
                    if (data.success) {
                        $(".college4_table_tbody1").html(template.compile(work4)(data));
                        if (data.resultObject.totalPageCount > 1) {
                            $(".pages").css("display", "block");
                            $(".searchPage .allPage").text(data.resultObject.totalPageCount);
                            if (data.resultObject.totalPageCount > 1) {
                                $("#Pagination").pagination(data.resultObject.totalPageCount, {
                                    callback: function (page) {
                                        //翻页功能
                                        RequestService("/api/callThirdPost", "post", {
                                            thirdUrl: base + "/bxg_stu/preview/findPreviewStudentPage",
                                            loginName: loginName,
                                            pageNumber: parseInt(page + 1),
                                            pageSize: 10
                                        }, function (data) {
                                            if (data.success) {
                                                $(".college4_table_tbody1").html(template.compile(work4)(data));
                                            }
                                        })
                                    }
                                });
                            }

                        }
                    }
                })
            }

            //加载所有课程名称
            function allcourse() {
                var xiala =
                    '<em data-courseId="" >请选择</em>' +
                    '{{each item as $value i}}' +
                    '<em data-courseId="{{$value.id}}" title="{{$value.name}}">{{$value.name}}</em>' +
                    '{{/each}}';
                RequestService("/api/callThirdPost", "post", {
                    thirdUrl: base + "/bxg_anon/my/exam/courseList",
                    loginName: loginName,
                }, function (data) {
                    if (data.resultObject != null && data.resultObject != "") {

                        $(".college3 .history .filter_01 .name_01 .xiala").html(template.compile(xiala)({
                            item: data.resultObject
                        }));
                        $(".college2 .history .filter_01 .name_01 .xiala").html(template.compile(xiala)({
                            item: data.resultObject
                        }));
                    }
                    $(".college3 .history .filter_01 .name_01 .xiala em").off().on("click", function () {
                        $(this).parent().siblings().html($(this).html()).attr("data-courseId", $(this).attr("data-courseId"));
                        var tj = {
                            courseId: $(".college3 .history .filter_01 .name_01 i").attr("data-courseid"),
                            examName: $(".college3 .history .filter_03 input").val(),
                            status: $(".college3 .history .filter_02 .name_01 i").attr("data-state"),
                            startTime: $(".college3 .history .filter_04 .time").val(),
                            endTime: $(".college3 .history .filter_04 .time1").val(),
                            pageNumber: 1,
                            pageSize: 10
                        };
                        exam(2, tj);
                    });
                    $(".college2 .history .filter_01 .name_01 .xiala em").off().on("click", function () {
                        $(this).parent().siblings().html($(this).html()).attr("data-courseId", $(this).attr("data-courseId"));
                        var wktj = {
                            courseId: $(".college2 .history .filter_01 .name_01 i").attr("data-courseid"),
                            homeworkName: $(".college2 .history .filter_03 input").val(),
                            status: $(".college2 .history .filter_02 .name_01 i").attr("data-state"),
                            startTime: $(".college2 .history .filter_04 .time").val(),
                            endTime: $(".college2 .history .filter_04 .time1").val(),
                            pageNumber: 1,
                            pageSize: 10
                        };
                        work(2, wktj);
                    });
                });
            };
            //学习中心添加红点
            student_center();
            function  student_center(){
                RequestService("/api/callThirdPost", "post", {
                    thirdUrl:base + "/bxg_anon/student/findMsgList",
                    loginName: loginName
                },function(findMsgListDB){
                    // 所有数据
                    var result = findMsgListDB.resultObject;
                    //循环获取下标
                    $(".icon-e").hide();
                    var ids3_1 = "";
                    var ids3_2 = "";
                    var ids4_3 = "";
                    var ids5_4 = "";
                    //$(".icon-e").hide();
                    for(var j=0;j<result.length;j++){
                        //测试
                        if(result[j].type == 3){
                            //console.log(id);
                            //当前测试
                            if(result[j].source_type==1){
                                ids3_1 = ids3_1+result[j].id+",";
                                $('.tab3_01').attr('data-msgid',ids3_1);
                                $("#J-exam-new i").show();
                                $("#J-exam i").show();
                            }
                            if(result[j].source_type==2){
                                ids3_2 = ids3_2+result[j].id+",";
                                $('.tab3_02').attr('data-msgid',ids3_2);
                                $("#J-exam-history i").show();
                                $("#J-exam i").show();
                            }
                        }
                        //作业
                        if(result[j].type == 4){
                            ids4_3 = ids4_3+result[j].id+",";
                            $('.tab2_01').attr('data-msgid',ids4_3);
                            //新作业
                            if(result[j].source_type==3){
                                $("#J-practice-new i").show();
                                $("#J-practice i").show();
                            }else{
                                $("#J-exam-new i").hide();
                            }
                        }
                        //复习
                        if(result[j].type == 5){
                            //g_msgId =result[j].id;
                            ids5_4 = ids5_4+result[j].id+",";
                            $('.tab5_01').attr('data-msgid',ids5_4);
                            if(result[j].source_type==4){
                                $("#J-preview-new i").show();
                                $("#J-preview i").show();
                            }else{
                                $("#J-exam-new i").hide();
                            }
                        }
                    }
                })
            }
            //我的练习的当前练习
            $('#J-practice').on('click', function(){
                $(".tab2_01").on('click',function(){
                    var msgId = $(this).data('msgid');
                    student_center_del(msgId);
                    $("#J-practice-new i").hide();
                    $("#J-practice i").hide();
                }).click()

            });
            //我的复习的未完成复习
            $('#J-preview').on('click', function(){
                $(".tab5_01").on('click',function(){
                    var msgId = $(this).data('msgid');
                    student_center_del(msgId);
                    $("#J-preview-new i").hide();
                    $("#J-preview i").hide();
                }).click()
            });
            //var zhou=0;
            //我的测试中的当前测试

            //$('#J-exam').on('click', function(){
            //    $(".tab3_01").on('click',function(){
            //        var g_msgId = $(this).data('msgid');
            //        student_center_del(g_msgId);
            //        $("#J-exam-new i").hide();
            //        //zhou+=1;
            //    })
            //})
            //
            ////我的测试中的历史测试
            //$('#J-exam').on('click', function(){
            //    $(".tab3_02").on('click',function(){
            //        var g_msgId = $(this).data('msgid');
            //        student_center_del(g_msgId);
            //        $("#J-exam-history i").hide();
            //        $("#J-exam i").hide();
            //        //a+=1;
            //    })
            //});

            //我的测试
            $('#J-exam').on('click', function(){
                $(".tab3_01").on('click',function(){
                            var g_msgId = $(this).data('msgid');
                    if ($(".college3 .tab3_01 i").is(":hidden") && $(".college3 .tab3_02 i").is(":hidden")){

                    }else {
                        student_center_del(g_msgId);
                        $("#J-exam-new i").hide();
                        $("#J-exam i").hide();
                    }

                        }).click()
                $(".tab3_02").on('click',function(){
                    var g_msgId = $(this).data('msgid');

                    if ($(".college3 .tab3_01 i").is(":hidden") && $(".college3 .tab3_02 i").is(":hidden")){

                    }else {
                        student_center_del(g_msgId);
                        $("#J-exam-history i").css("display","none");
                        $("#J-exam i").hide();
                    }
                })
            });

            //if(a==2){
            //    $("#J-exam i").hide();
            //}
            //学习中心移出红点
            function  student_center_del(msgId){
                msgId = msgId.substring(0,msgId.length-1);
                console.log(msgId+"++++++++++++++++++++");
               /* RequestService("/api/callThirdPost", "post", {
                    thirdUrl:base + "/bxg_anon/student/updateStudentMsg",
                    msgIds  : msgId
                })*/
                RequestService("/api/callThirdPost", "post", {
                    thirdUrl: base + "/bxg_anon/student/updateStudentMsg",
                    msgIds  : msgId
                }, function (data) {
                    if (data.success) {
                        console.log("gengxinchenggong!");
                    }
                })
            }

            //我的考试筛选条件初始化
            function first() {
                $(".college2 .history .filter_01 .name_01 i").attr("data-courseid", "").html("请选择");
                $(".college2 .history .filter_03 input").val("");
                $(".college2 .history .filter_02 .name_01 i").attr("data-state", "").html("请选择");
                $(".college2 .history .filter_04 .time").val("");
                $(".college2 .history .filter_04 .time1").val("");
                $(".college3 .history .filter_01 .name_01 i").attr("data-courseid", "").html("请选择");
                $(".college3 .history .filter_03 input").val("");
                $(".college3 .history .filter_02 .name_01 i").attr("data-state", "").html("请选择");
                $(".college3 .history .filter_04 .time").val("");
                $(".college3 .history .filter_04 .time1").val("");
                $(".college4 .history .filter_01 .name_01 i").attr("data-courseid", "").html("请选择");
                $(".college4 .history .filter_03 input").val("");
                $(".college4 .history .filter_02 .name_01 i").attr("data-state", "").html("请选择");
                $(".college4 .history .filter_04 .time").val("");
                $(".college4 .history .filter_04 .time1").val("");
                $(".college5 .history .filter_01 .name_01 i,.college5 .table .filter_01 .name_01 i").attr("data-courseid", "").html("请选择");
                $(".college5 .history .filter_03 input,.college5 .table .filter_03 input").val("");
                $(".college5 .history .filter_02 .name_01 i,.college5 .table .filter_02 .name_01 i").attr("data-state", "").html("请选择");
                $(".college5 .history .filter_04 .time,.college5 .table .filter_04 .time").val("");
                $(".college5 .history .filter_04 .time1,.college5 .table .filter_04 .time1").val("");
            };
        } else {
            $('#login').css("display", "none");
            $(".loginGroup .logout").css("display", "block");
            $(".loginGroup .login").css("display", "none");
            location.href = "/index.html"
        }
    }, false);

}