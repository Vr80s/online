/**
 * Created by admin on 2016/11/1.
 */
window.onload = function () {
    $(".studentCenterBox").addClass("select");
    var loginName = "";

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
    template.helper('zhibohref', function (id, collection) {

        if (collection) {
            return "/course/courses?courseId=" + id
        }
        return "/web/html/video.html?courseId=" + id;
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
//		if(num == 0) {
//			return "一级";
//		} else if(num == 1) {
//			return "二级";
//		} else if(num == 2) {
//			return "三级";
//		}
        return "";
    });
    //订单包括多少课程
    template.helper("tdNum", function (num) {
        if (num == 1) {
            return "<td>";
        } else {
            return '<td style="padding-bottom:10px">';
        }
    });
    //订单里面所有课程ID集合
    template.helper("order", function (num) {
        var ids = "";
        for (i = 0; i < num.length; i++) {
            ids += "," + num[i].id
        }
        ;
        ids = ids.substring(1);
        return ids;
    });
    //获取地址栏参数
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }

    var mycourse = '<div class="box clearfix">' +
        '{{each items as $value i }}' +
        '<div class="course" data-url="{{#zhibohref($value.id,$value.collection)}}">' +
        '<a href="javascript:;" data-videoId="{{$value.id}}">' +
        '{{#hasImg($value.smallImgPath)}}' +
        '<div class="name" title="{{$value.courseName}}">{{$value.courseName}}</div>' +
        // '<div class="range clearfix">' +
        // '<div class="rangelength">' +
        // '<img src="/web/images/130/mystudy_icon01.png"/ class="lt">' +
        // '{{#range($value.learndCount,$value.count)}}' +
        // '</div>' +
        // '<div class="rangenum">' +
        // '<span class="curr">{{$value.learndCount}}</span>/<span class="all">{{$value.count}}</span>' +
        // '</div>' +
        // '</div>' +
        '</a>' +
        '</div>' +
        '{{/each}}' +
        '</div>';
    var mycourse_live = '<div class="box clearfix">' +
        '{{each items as $value i }}' +
        '<div class="course" data-url="{{#zhibohref($value.id)}}">' +
        '<a href="/web/livepage/{{$value.id}}"   data-videoId="{{$value.id}}">' +
        '{{#hasImg($value.smallImgPath)}}' +
        '<div class="name" title="{{$value.courseName}}">{{$value.courseName}}</div>' +
        '<div class="name" title="{{$value.courseName}}">开始时间：{{$value.start_time}}</div>' +
        '<div class=" clearfix">' +
        //	'<div class="rangelength">' +
        //	'<img src="/web/images/130/mystudy_icon01.png"/ class="lt">' +
        //	'{{#range($value.learndCount,$value.count)}}' +
        //	'</div>' +
        // 	'<div class="rangenum">' +
        //	'<span class="curr">{{$value.learndCount}}</span>/<span class="all">{{$value.count}}</span>' +
        // 	'</div>' +
        '</div>' +
        '</a>' +
        '</div>' +
        '{{/each}}' +
        '</div>';
    var mycourse_xianxia = '<div class="box clearfix">' +
        '{{each items as $value i }}' +
        '<div class="course" data-url="/course/courses/{{$value.id}}">' +
        '<a href="/course/courses/{{$value.id}}"   data-videoId="{{$value.id}}">' +
        '{{#hasImg($value.smallImgPath)}}' +
        '<div class="name" title="{{$value.courseName}}">{{$value.courseName}}</div>' +
        '<div class="name" title="{{$value.courseName}}">{{$value.startTime}}-{{$value.endTime}}</div>' +
        '<div class=" clearfix">' +
        //	'<div class="rangelength">' +
        //	'<img src="/web/images/130/mystudy_icon01.png"/ class="lt">' +
        //	'{{#range($value.learndCount,$value.count)}}' +
        //	'</div>' +
        // 	'<div class="rangenum">' +
        //	'<span class="curr">{{$value.learndCount}}</span>/<span class="all">{{$value.count}}</span>' +
        '</div>' +
        //	'</div>' +
        '</a>' +
        '</div>' +
        '{{/each}}' +
        '</div>';
    var myorder =
        '{{each items as $value i}}' +
        '<div class="ord clearfix">' +
        '<p class="ord-01 clearfix">' +
        '<span class="time">{{$value.create_time}}</span>' +
        '<span class="numb">订单号：{{$value.order_no}}</span>' +
        '{{if $value.order_status=="2"}}' +
        '<span class="delete" style="float:right" data-orderNo="{{$value.order_no}}">删除订单</span>' +
        '{{/if}}' +
        '{{if $value.order_status=="0"}}' +
        '<span class="quXiao" style="float:right" data-orderNo="{{$value.order_no}}">取消订单</span>' +
        '{{/if}}' +
        '</p>' +

        '<div class="reBox clearfix">' +
        '<table>' +
        '<td style="width:370px">' +
        '{{each $value.orderDetail as $a a}}' +
        '<div class="ord-02 clearfix">' +
        '<div class="or1 clearfix">' +
        '<span class="img">{{#orderClick($a)}}<img src="{{$a.smallimg_path}}"/></a></span>' +
        '<table border="0" cellspacing="" cellpadding="" class="name">' +
        '<tr><td><span style="font:  16px 微软雅黑">{{$a.grade_name}}</span></td></tr>' +
        '<tr><td><span style="color:rgb(153,153,153)">主讲：{{$a.lecturer}}</span></td></tr>' +
        //			'<tr><td><span class="or7" style="font:  12px 微软雅黑">课程有效期至{{#expiry($value.create_time)}}</span></td></tr>' +
        '</table>' +
        '</div>' +

        '<div class="or7"></div>' +
        '</div>' +
        '{{/each}}' +
        '</td>' +

        '{{#tdNum(2)}}' +
        '{{each $value.orderDetail as $a a}}' +

        '<div class="or2" style="font-size:20px;height:100px;line-height:100px">￥{{$a.price}}</div>' +
        '<div class="or7" style="height:15px"></div>' +
        //		'<div class="or7"></div>' +
        '{{/each}}' +
        '{{#tdNum(2)}}' +
        '{{each $value.orderDetail as $a a}}' +
        '<div class="or4" style="color:rgb(55,194,91);font-size:20px;height:100px;line-height:100px">￥{{$a.price}}</div>' +
        '<div class="or7" style="height:15px"></div>' +
        '{{/each}}' +
        '</td>' +

        '{{#tdNum(2)}}<div class="or5" style="font-size:20px">{{#orderState($value.order_status)}}</div>' +
        '<div class="or7" style="height:15px"></div>' +
        '</td>' +
        //是否多条数据
        '{{#tdNum(2)}}' +
        '{{if $value.order_status=="2"}}' +
        //		'<div class="or6"><div class="box"><a href="/web/html/order.html?courseId={{#order($value.orderDetail)}}" target="_blank">重新购买</a>'+
        //提交订单部分修改
        //'<span class="delete" data-orderNo="{{$value.order_no}}">删除订单</span>'+
        '</div></div>' +
        '{{/if}}' +
        '{{if $value.order_status=="0"}}' +
        //		'<div class="or6"><div class="box"><a href="/web/{{$value.order_no}}/findOrderByOrderNo" target="_blank">去支付</a>'+
        //提交订单部分修改
        //'<span class="quXiao" data-orderNo="{{$value.order_no}}">取消订单</span>'+
        '</div></div>' +
        '{{/if}}' +
        '{{if $value.order_status=="1"}}' +
        //		'<div class="or6"><div class="box" >-- --</div></div>' +
        '{{/if}}' +
        '</td>' +
        '</table>' +
        '</div>' +
        //订单底部修改添加的 金额 支付部分
        '<div style="border-top: 1px solid #EBEBEB;height:48px;line-height: 48px;" id="totalMoney">' +
        //支付按钮的样式修改
        '{{if $value.order_status=="2"}}' +
        '<span  style="float: right;margin-right: 30px;"><a data-courseid="{{#order($value.orderDetail)}}" class="J-repeat-buy"' +
        ' target="_blank" style="width: 78px;height: 36px;text-align: center;line-height: 36px;border: 1px solid #2CB82C;color: #fff;background-color: #2CB82C;border-radius: 2px;">重新购买</a></span>' +
        '{{/if}}' +
        '{{if $value.order_status=="0"}}' +
        '<span  style="float: right;margin-right: 30px;"><a href="/order/pay?orderId={{$value.id}}" target="_blank" style="width: 78px;height: 36px;text-align: center;line-height: 36px;border: 1px solid #2CB82C;color: #fff;background-color: #2CB82C;border-radius: 2px;">去支付</a></span>' +
        '{{/if}}' +
        '<span style="float: right;margin-right: 30px;">实付款：<i style="font-style:normal;color:#2CB82C">{{$value.actual_pay}}</i>元</span><span style="float: right;margin-right: 30px;"></span>' +

        '</div>' +

        '</div>' +
        '{{/each}}';
    var emptyDefaul =
        "<div class='page-no-result'>" +
        "<img src='/web/images/personcenter/my_nodata.png'>" +
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
        '<div class="child_01">' +
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
    //	下级用户
    var tongji =
        '<p>我的团队：<span>{{item.oneCount+item.twoCount+item.threeCount}}</span> 人</p>' +
        '<p>合伙人：<span>{{item.oneCount}}</span> 人</p>';
//		'<p>一级合伙人：<span>{{item.oneCount}}</span> 人</p>' +
//		'<p>二级合伙人：<span>{{item.twoCount}}</span> 人</p>' +
//		'<p>三级合伙人：<span>{{item.threeCount}}</span> 人</p>';
    //三级补贴
    var tongji2 =
        '<p>我的学费补贴：<span>{{item.allFee}}</span> 元</p>';
//		'	<p>补贴：<span>{{item.levelFee1}}</span> 元</p>';
//		'	<p>一级补贴：<span>{{item.levelFee1}}</span> 元</p>' +
//		'	<p>二级补贴：<span>{{item.levelFee2}}</span> 元</p>' +
//		'	<p>三级补贴：<span>{{item.levelFee3}}</span> 元</p>';
    var list1 =
        '{{each item as $value i}}' +
        '<li class="clearfix">' +
        '<span title="{{$value.name}}" class="l1">' +
        '{{$value.name}}' +
        '</span>' +
        '<span class="l2" title="{{$value.login_name}}">' +
        '{{$value.login_name}}' +
        '</span>' +
        '<span class="l3" title="{{$value.create_time}}">' +
        '{{$value.create_time}}' +
        '</span>' +
        '<span class="l4" title="{{$value.buyCouseCount}}">' +
        '{{$value.buyCouseCount}}' +
        '</span>' +
        '</li>' +
        '{{/each}}';
    var list2 =
        '{{each item as $value i}}' +
        '<li class="clearfix">' +
        '	<span class="list1" title="{{$value.share_order_no}}">' +
        '{{$value.share_order_no}}' +
        '</span>' +
        '	<span class="list2" title="{{$value.course_name}}">' +
        '{{$value.course_name}}' +
        '	</span>' +
        '	<span class="list3" title="{{$value.login_name}}">' +
        '{{$value.login_name}}' +
        '</span>' +
        '<span class="list4" title="{{$value.actual_pay}}">' +
        '{{$value.actual_pay}}' +
        '</span>' +
        '<span class="list5" title="{{$value.pay_time}}">' +
        '{{$value.pay_time}}' +
        '</span>' +
        '<span class="list6" title="{{$value.subsidies}}">' +
        //		'<span class="list6" title="{{$value.subsidies}}（{{#fenji($value.level)}}）">' +
        '<i>{{$value.subsidies}}</i>' +
        '</span>' +
        '</li>' +
        '{{/each}}';
    var redeemList =
        '{{each items as r i}}' +
        '{{if i%3 == 2}}' +
        '<div class="redeem-List" style="margin-right:0;">' +
        '{{else}}' +
        '<div class="redeem-List">' +
        '{{/if}}' +
        '{{if i%3 == 0}}' +
        '<div class="fcode bc1">' + '<p class="duima">课程兑换码</p>' + '<p class="jieshijian">{{r.expiry_time}}</p>' + '</div>' +
        '{{/if}}' +
        '{{if i%3 == 1}}' +
        '<div class="fcode bc2">' + '<p class="duima">课程兑换码</p>' + '<p class="jieshijian">{{r.expiry_time}}</p>' + '</div>' +
        '{{/if}}' +
        '{{if i%3 == 2}}' +
        '<div class="fcode bc3">' + '<p class="duima">课程兑换码</p>' + '<p class="jieshijian">{{r.expiry_time}}</p>' + '</div>' +
        '{{/if}}' +
        '<p class="shiyong dot5" title="{{r.course_names}}">适用课程:{{#change(r.course_names)}}</p>' +
        '</div>' +
        '{{/each}}';
    var yiredeemList =
        '{{each items as r i}}' +
        '{{if i%3 == 2}}' +
        '<div class="redeem-List" style="margin-right:0;">' +
        '{{else}}' +
        '<div class="redeem-List">' +
        '{{/if}}' +
        '{{if i%3 == 0}}' +
        '<div class="fcode bc1">' + '<p class="duima">课程兑换码</p>' + '<p class="jieshijian">{{r.expiry_time}}</p>' + '<img src="/web/images/duihuan/yishiyong.png"/></div>' +
        '{{/if}}' +
        '{{if i%3 == 1}}' +
        '<div class="fcode bc2">' + '<p class="duima">课程兑换码</p>' + '<p class="jieshijian">{{r.expiry_time}}</p>' + '<img src="/web/images/duihuan/yishiyong.png"/></div>' +
        '{{/if}}' +
        '{{if i%3 == 2}}' +
        '<div class="fcode bc3">' + '<p class="duima">课程兑换码</p>' + '<p class="jieshijian">{{r.expiry_time}}</p>' + '<img src="/web/images/duihuan/yishiyong.png"/></div>' +
        '{{/if}}' +
        '<p class="yishiyong dot5" title="{{r.course_name}}">已兑换课程:{{#change(r.course_name)}}</p>' +
        '</div>' +
        '{{/each}}';
    var guoredeemList =
        '{{each items as r i}}' +
        '{{if i%3 == 2}}' +
        '<div class="redeem-List" style="margin-right:0;">' +
        '{{else}}' +
        '<div class="redeem-List">' +
        '{{/if}}' +
        '<div class="fcode guoqi">' + '<p class="duima">{{r.fcode}}</p>' + '<img src="/web/images/duihuan/yiguoqi.png"/></div>' +
        '<p class="guoshiyong dot5">过期时间:{{r.expiry_time}}</p>' +
        '</div>' +
        '{{/each}}';

    //帐号信息
    RequestService("/online/user/isAlive", "GET", null, function (data) { ///online/user/isAlive
        if (data.success === true) {
            //兑换码获取
            function redeem(status) {
                isAlive();
                var status = status;
                RequestService("/fcode/findMyFcode", "get", {
                    status: status,
                    pageNumber: 1,
                    pageSize: 9999
                }, function (data) {
                    if (data.success == true) {
                        console.log(data);
                        if (data.resultObject.items.length == 0) {
                            $(".redeemListBody").html(emptyDefaul);
                        } else {
                            if (status == 0) {
                                $(".redeemListBody").html(template.compile(redeemList)({
                                    items: data.resultObject.items
                                }));
                            } else if (status == 1) {
                                $(".redeemListBody").html(template.compile(yiredeemList)({
                                    items: data.resultObject.items
                                }));
                            } else if (status == 2) {
                                $(".redeemListBody").html(template.compile(guoredeemList)({
                                    items: data.resultObject.items
                                }));
                            }
                            var $dot5 = $('.dot5');
                            $dot5.each(function () {
                                if ($(this).height() > 40) {
                                    $(this).attr("data-txt", $(this).attr("data-text"));
                                    $(this).height(40);
                                }
                                var $dot4 = $(this);

                                function createDots() {
                                    $dot4.dotdotdot({
                                        after: 'span.qq'
                                    });
                                }

                                function destroyDots() {
                                    $dot4.trigger('destroy');
                                }

                                createDots();
                                $dot4.on(
                                    'click',
                                    'a.toggle',
                                    function () {
                                        $dot4.toggleClass('opened');

                                        if ($dot4.hasClass('opened')) {
                                            destroyDots();
                                        } else {
                                            createDots();
                                        }
                                        return false;
                                    }
                                );
                            });

                        }
                    }
                });
            }

            function iFrameHeight() {

                var ifm = document.getElementById("fra");

                var subWeb = document.frames ? document.frames["iframepage"].document :

                    ifm.contentDocument;

                if (ifm != null && subWeb != null) {

                    ifm.height = subWeb.body.scrollHeight;

                }
            }

            var atime;
            $(".account .mess .name").html(data.resultObject.name);
//          if (data.resultObject.info) {
//              $(".account .mess .msg").html(data.resultObject.info);
//              $(".account .mess .msg").attr("title", data.resultObject.info);
//          } else {
//              $(".account .mess .msg").html("说点什么来彰显你的个性吧……");
//              $(".account .mess .msg").attr("title", "说点什么来彰显你的个性吧……");
//          };
            
            loginName = data.resultObject.loginName;
            if (data.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
                path = data.resultObject.smallHeadPhoto;
                $(".account .headimg img").attr("src", path);
            } else {
                path = bath + data.resultObject.smallHeadPhoto
                $(".account .headimg img").attr("src", path);
            }
            ;

            //判断是否显示用户的
            if (data.resultObject.shareCode == null || data.resultObject.shareCode == "") {
                $(".myshare .share_link .cont .cont1").show().siblings().hide();
                $(".myshare .share_link .cont .cont1 .t2").off().on("click", function () {
//					window.open("/web/share/share.html");	
                    window.open("/web/html/payCourseDetailPage.html?id=" + data.resultObject.shareCourseId + "&courseType=1&free=0");
                });
            } else {
                $(".myshare .share_link .cont .cont2").show().siblings().hide();
                var acode = data.resultObject.shareCode;
                var aurl = "/web/html/login.html--ways=register";
                var url = "https://" + window.location.hostname + "/share?usercode=" + acode + "__" + aurl;
                RequestService("/short/url", "post", {
                    url: url
                }, function (data) {
                    if (data.success == true) {
                        $(".myshare .share_link .cont .cont2 .link span").html(data.resultObject);
                    } else {
                        alert(data.errorMessage);
                    }
                    ;
                });
            }
            ;
            //此处同步判断结束
            /*if($.getUrlParam("free") == 1 && $.getUrlParam("real") == 1) {
                //免费
                $(".content .main-right .right-title span").eq(1).addClass("act").siblings().removeClass("act");
                course_xianxia(1, 1, 12);
            } else if($.getUrlParam("free") == 0 && $.getUrlParam("real") == 1) {
                $(".content .main-right .right-title span").eq(0).addClass("act").siblings().removeClass("act");
                course_xianxia(0, 1, 12);
            } else */
            if ($.getUrlParam("free") == 1) {
                //免费
                $(".content .main-right .right-title span").eq(1).addClass("act").siblings().removeClass("act");
                course(1, 1, 12);
            } else if ($.getUrlParam("free") == 0) {
                $(".content .main-right .right-title span").eq(0).addClass("act").siblings().removeClass("act");
                course(0, 1, 12);
            } else if ($.getUrlParam("state") == 2) {
                $(".content .con-main .main-left .college").addClass("active").siblings().removeClass("active");
                $(".content .con-main .main-right .mycourse").hide();
                $(".content .con-main .main-right .mycollege").show();
                atime = window.setInterval(iFrameHeight, 300);
            } else if ($.getUrlParam("share") == 0) {
                $(".content .con-main .main-left .share").addClass("active").siblings().removeClass("active");
                $(".content .con-main .main-right .mycourse").hide();
                $(".content .con-main .main-right .myshare").show();
                share(0, "");
            } else if ($.getUrlParam("order") == 0) {
                $(".content .con-main .main-left .buy").addClass("active").siblings().removeClass("active");
                $(".content .con-main .main-right .mycourse").hide();
                $(".content .con-main .main-right .mybuy").show();
                order(-1, 0, 1, 5);
            } else if ($.getUrlParam("location") == "fcode") {
                $(".content .con-main .main-left .duihuan").addClass("active").siblings().removeClass("active");
                $(".content .con-main .main-right .mycourse").hide();
                $(".content .con-main .main-right .myredeem").show();
                redeemClick();
                redeem(0);
            } else {
                //付费
                $(".content .main-right .right-title span").eq(0).addClass("act").siblings().removeClass("act");
                course(0, 1, 12);
            }
            ;
            //左侧导航
            $(".content .main-left li").on("click", function () {
                var $this = $(this);
                $.ajax({
                    type: "get",
                    url: bath + "/online/user/isAlive",
                    async: false,
                    success: function (data) {
                        if (data.success === true) {
                            $(".pages").css("display", "none");
                            $this.addClass("active").siblings().removeClass("active");
                            $(".content .main-right .mybuy .right-title div").eq(0).addClass("act").siblings().removeClass("act");
                            $(".content .main-right .mycourse .right-title span").eq(0).addClass("act").siblings().removeClass("act");
                            if ($this.hasClass("buy")) {
                                clearInterval(atime);
                                $(".all-change a").each(function () {
                                    $(this).css("display", "block");
                                });
                                $(".all-change a").eq(0).css("display", "none");
                                $(".allorder span").html("全部订单").attr({
                                    "data-timeQuantum": "0",
                                    "title": "全部订单"
                                });
                                $(".mycourse").hide();
                                $(".mycourse-xianxia").hide();
                                $(".mycourse-live").hide();
                                $(".myanswerBox").hide();
                                $(".mybuy").show();
                                $(".mycollege").hide();
                                $(".myshare").hide();
                                $(".myredeem").hide();
                                order(-1, 0, 1, 5);
                            } else if ($this.hasClass("answer")) {
                                $(".mycourse").hide();
                                $(".mycourse-xianxia").hide();
                                $(".mycourse-live").hide();
                                $(".myanswerBox").show();
                                $(".mybuy").hide();
                                $(".mycollege").hide();
                                $(".myredeem").hide();
                                $(".myshare").hide();
                                createPage();
                            } else if ($this.hasClass("course")) {
                                clearInterval(atime);
                                $(".mycourse").show();
                                $(".mycourse-xianxia").hide();
                                $(".mycourse-live").hide();
                                $(".myanswerBox").hide();
                                $(".mybuy").hide();
                                $(".mycollege").hide();
                                $(".myshare").hide();
                                $(".myredeem").hide();
                                course(0, 1, 12);
                            } else if ($this.hasClass("course-xianxia")) {
                                clearInterval(atime);
                                $(".mycourse-xianxia").show();
                                $(".mycourse-live").hide();
                                $(".mycourse").hide();
                                $(".myanswerBox").hide();
                                $(".mybuy").hide();
                                $(".mycollege").hide();
                                $(".myshare").hide();
                                $(".myredeem").hide();
                                course_xianxia(0, 1, 12);
                            } else if ($this.hasClass("course-live")) {
                                clearInterval(atime);
                                $(".mycourse-xianxia").hide();
                                $(".mycourse-live").show();
                                $(".mycourse").hide();
                                $(".myanswerBox").hide();
                                $(".mybuy").hide();
                                $(".mycollege").hide();
                                $(".myshare").hide();
                                $(".myredeem").hide();
                                course_live(0, 1, 12);
                            } else if ($this.hasClass("share")) {
                                clearInterval(atime);
                                $(".mycourse").hide();
                                $(".mycourse-xianxia").hide();
                                $(".myanswerBox").hide();
                                $(".mybuy").hide();
                                $(".mycollege").hide();
                                $(".myredeem").hide();
                                $(".myshare").show();
                                $(".myshare .right-title span").eq(0).addClass("act").siblings().removeClass("act");
                                $(".myshare .share0").show().siblings().hide();
                                share(0, "");
                            } else if ($this.hasClass("duihuan")) {
                                clearInterval(atime);
                                $(".mycourse").hide();
                                $(".mycourse-xianxia").hide();
                                $(".myanswerBox").hide();
                                $(".mybuy").hide();
                                $(".mycollege").hide();
                                $(".myshare").hide();
                                $(".myredeem").show();
                                $(".myshare .right-title span").eq(0).addClass("act").siblings().removeClass("act");
                                redeemClick();
                                redeem(0);
                            }
                            ;
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

            function overRed() {
                $(".redeemOk").css("display", "block");

                function removeRedeemOk() {
                    $(".redeemOk").css("display", "none");
                }

                setTimeout(removeRedeemOk, 2000);
            };

            function redeemClick() {
                $("#redeemNumber").focus(function () {
                    $("#redeemErrey").text("");
                });
                $(".redeemListTitle ul li").click(function () {
                    console.log($(this).attr("data-redeem"));
                    redeem($(this).attr("data-redeem"));
                    $(".redeemListTitle ul li").removeClass("yt");
                    $(this).addClass("yt");
                });
                $(".redeemBtn").click(function () {
                    if ($("#redeemNumber").val() != "") {
                        RequestService("/fcode/addUserFcode", "POST", {
                            fcode: $("#redeemNumber").val()
                        }, function (data) {
                            if (data.success == true) {
                                overRed();
                                redeem($(".yt").attr("data-redeem"));
                            } else {
                                $("#redeemErrey").text("*" + data.errorMessage);
                            }
                        })
                    } else {

                    }
                });
            }

            //课程切换

            function course(courseStatus, pageNumber, pageSize) {
                isAlive();
                $(".main-right .mycourse .right-course .loadrr").show().siblings().hide();
                RequestService("/userCourse/courses/" + courseStatus + "/" + pageNumber + "/" + pageSize, "get", null, function (data) {
                    if (data.success == true) {
                        if (data.resultObject.items == "") {
                            if (data.resultObject.totalCount == 0) { //无数据
                                $(".main-right .mycourse .right-course .course" + courseStatus).html(emptyDefaul);
                                $(".main-right .mycourse .right-course .course" + courseStatus).show().siblings().hide();
                                $(".pages").css("display", "none");
                            }
                        } else if (data.resultObject.totalPageCount == 1 && data.resultObject.totalCount > 0) {
                            $(".pages").css("display", "none");
                            $(".main-right .mycourse .right-course .course" + courseStatus).show().siblings().hide();
                            if (courseStatus == 0) {
                                $(".main-right .mycourse .right-course .course0").html(template.compile(mycourse)({
                                    items: data.resultObject.items
                                }));
                            } else if (courseStatus == 1) {
                                $(".main-right .mycourse .right-course .course1").html(template.compile(mycourse)({
                                    items: data.resultObject.items
                                }));
                            }
                        } else if (data.resultObject.totalPageCount > 1) {
                            $(".pages").css("display", "block");
                            $(".searchPage .allPage").text(data.resultObject.totalPageCount);
                            $(".main-right .mycourse .right-course .course" + courseStatus).show().siblings().hide();
                            if (data.resultObject.currentPage == 1) {
                                $("#Pagination").pagination(data.resultObject.totalPageCount, {
                                    callback: function (page) { //翻页功能
                                        console.log(page);
                                        var param = {
                                            pageNumber: page + 1,
                                            pageSize: pageSize
                                        };
                                        course(courseStatus, param.pageNumber, pageSize);
                                    }
                                });
                            }
                            if (courseStatus == 0) {
                                $(".main-right .mycourse .right-course .course0").html(template.compile(mycourse)({
                                    items: data.resultObject.items
                                }));
                            } else if (courseStatus == 1) {
                                $(".main-right .mycourse .right-course .course1").html(template.compile(mycourse)({
                                    items: data.resultObject.items
                                }));
                            }
                        }
                        //课程切换
                        $(".content .main-right .mycourse .right-title span").off().on("click", function () {
                            $(this).addClass("act").siblings().removeClass("act");
                            course($(this).attr("data-id"), 1, pageSize);
                        });
                        //课程切换
                        $(".content .main-right .mycourse-xianxia .right-title span").off().on("click", function () {
                            $(this).addClass("act").siblings().removeClass("act");
                            course_xianxia($(this).attr("data-id"), 1, pageSize);
                        });
                        //课程切换
                        $(".content .main-right .mycourse-live .right-title span").off().on("click", function () {
                            $(this).addClass("act").siblings().removeClass("act");
                            course_live($(this).attr("data-id"), 1, pageSize);
                        });
                        //点击课程
                        $(".right-course .course").off().on("click", function () {
                            var $this = $(this);
                            $.ajax({
                                type: "get",
                                url: bath + "/online/user/isAlive",
                                async: false,
                                success: function (data) {
                                    if (data.success === true) {
                                        window.open($this.attr("data-url"), "_blank");
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
                    } else {

                        $(".main-right .mycourse .right-course").html(emptyDefaul);
                        $(".pages").css("display", "none");
                    }
                })
            };

            function course_xianxia(courseStatus, pageNumber, pageSize) {
                isAlive();
                $(".main-right .mycourse-xianxia .right-course .loadrr").show().siblings().hide();
                RequestService("/userCourse/realCourses/" + courseStatus + "/" + pageNumber + "/" + pageSize, "get", null, function (data) {
                    if (data.success == true) {
                        if (data.resultObject.items == "") {
                            if (data.resultObject.totalCount == 0) { //无数据
                                $(".main-right .mycourse-xianxia .right-course .course" + courseStatus + "-xianxia").html(emptyDefaul);
                                $(".main-right .mycourse-xianxia .right-course .course" + courseStatus + "-xianxia").show().siblings().hide();
                                $(".pages").css("display", "none");
                            }
                        } else if (data.resultObject.totalPageCount == 1 && data.resultObject.totalCount > 0) {
                            $(".pages").css("display", "none");
                            $(".main-right .mycourse-xianxia .right-course .course" + courseStatus + "-xianxia").show().siblings().hide();
                            if (courseStatus == 0) {
                                $(".main-right .mycourse-xianxia .right-course .course0-xianxia").show();
                                $(".main-right .mycourse-xianxia .right-course .course0-xianxia").html(template.compile(mycourse_xianxia)({
                                    items: data.resultObject.items
                                }));
                            } else if (courseStatus == 1) {
                                $(".main-right .mycourse-xianxia .right-course .course1-xianxia").html(template.compile(mycourse_xianxia)({
                                    items: data.resultObject.items
                                }));
                            }
                        } else if (data.resultObject.totalPageCount > 1) {
                            $(".pages").css("display", "block");
                            $(".searchPage .allPage").text(data.resultObject.totalPageCount);
                            $(".main-right .mycourse-xianxia .right-course .course-xianxia" + courseStatus).show().siblings().hide();
                            if (data.resultObject.currentPage == 1) {
                                $("#Pagination").pagination(data.resultObject.totalPageCount, {
                                    callback: function (page) { //翻页功能
                                        var param = {
                                            pageNumber: page + 1,
                                            pageSize: pageSize
                                        };
                                        console.log(page);
                                        course_xianxia(courseStatus, param.pageNumber, pageSize);
                                    }
                                });
                            }
                            if (courseStatus == 0) {
                                $(".main-right .mycourse-xianxia .right-course .course0-xianxia").html(template.compile(mycourse)({
                                    items: data.resultObject.items
                                }));
                            } else if (courseStatus == 1) {
                                $(".main-right .mycourse-xianxia .right-course .course1-xianxia").html(template.compile(mycourse)({
                                    items: data.resultObject.items
                                }));
                            }
                        }
                        //课程切换
                        $(".content .main-right .mycourse-xianxia .right-title span").off().on("click", function () {
                            $(this).addClass("act").siblings().removeClass("act");
                            course_xianxia($(this).attr("data-id"), 1, pageSize);
                        });
                        //点击课程
                        $(".right-course .course-xianxia").off().on("click", function () {
                            var $this = $(this);
                            $.ajax({
                                type: "get",
                                url: bath + "/online/user/isAlive",
                                async: false,
                                success: function (data) {
                                    if (data.success === true) {
                                        window.open($this.attr("data-url"), "_blank");
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
                    } else {

                        $(".main-right .mycourse-xianxia .right-course").html(emptyDefaul);
                        $(".pages").css("display", "none");
                    }
                })
            };

            function course_live(courseStatus, pageNumber, pageSize) {
                isAlive();
                $(".main-right .mycourse-live .right-course .loadrr").show().siblings().hide();
                RequestService("/userCourse/publicCourses/" + courseStatus + "/" + pageNumber + "/" + pageSize, "get", null, function (data) {
                    if (data.success == true) {
                        if (data.resultObject.items == "") {
                            if (data.resultObject.totalCount == 0) { //无数据
                                $(".main-right .mycourse-live .right-course .course" + courseStatus + "-live").html(emptyDefaul);
                                $(".main-right .mycourse-live .right-course .course" + courseStatus + "-live").show().siblings().hide();
                                $(".pages").css("display", "none");
                            }
                        } else if (data.resultObject.totalPageCount == 1 && data.resultObject.totalCount > 0) {
                            $(".pages").css("display", "none");
                            $(".main-right .mycourse-live .right-course .course" + courseStatus + "-live").show().siblings().hide();
                            if (courseStatus == 0) {
                                $(".main-right .mycourse-live .right-course .course0-live").show();
                                $(".main-right .mycourse-live .right-course .course0-live").html(template.compile(mycourse_live)({
                                    items: data.resultObject.items
                                }));
                            } else if (courseStatus == 1) {
                                $(".main-right .mycourse-live .right-course .course1-live").html(template.compile(mycourse_live)({
                                    items: data.resultObject.items
                                }));
                            }
                        } else if (data.resultObject.totalPageCount > 1) {
                            $(".pages").css("display", "block");
                            $(".searchPage .allPage").text(data.resultObject.totalPageCount);
                            $(".main-right .mycourse-live .right-course .course-live" + courseStatus).show().siblings().hide();
                            if (data.resultObject.currentPage == 1) {
                                $("#Pagination").pagination(data.resultObject.totalPageCount, {
                                    callback: function (page) { //翻页功能
                                        var param = {
                                            pageNumber: page + 1,
                                            pageSize: pageSize
                                        };
                                        console.log(param.pageNumber);
                                        console.log(courseStatus);
                                        course_live(courseStatus, param.pageNumber, pageSize);

                                    }
                                });
                            }
                            if (courseStatus == 0) {
                                $('.loadrr').css("display", "none");
                                $(".main-right .mycourse-live .right-course .course1-live").css("display", "none");
                                $(".main-right .mycourse-live .right-course .course0-live").css("display", "block");
                                $(".main-right .mycourse-live .right-course .course0-live").html(template.compile(mycourse_live)({
                                    items: data.resultObject.items
                                }));
                            } else if (courseStatus == 1) {
                                $('.loadrr').css("display", "none");
                                $(".main-right .mycourse-live .right-course .course1-live").css("display", "block");
                                $(".main-right .mycourse-live .right-course .course1-live").html(template.compile(mycourse_live)({
                                    items: data.resultObject.items
                                }));
                            }
                        }
                        //课程切换
                        $(".content .main-right .mycourse-live .right-title span").off().on("click", function () {
                            $(this).addClass("act").siblings().removeClass("act");
//							if($(this).attr("kg") == "true") {
                            course_live($(this).attr("data-id"), 1, pageSize);
//								$(this).attr("kg", "false");
//							} else {
//								if($(this).attr("data-id") == 0) {
//									$(".main-right .mycourse .right-course .course0").show();
//									$(".main-right .mycourse .right-course .course1").hide();
//								} else if($(this).attr("data-id") == 1) {
//									$(".main-right .mycourse .right-course .course1").show();
//									$(".main-right .mycourse .right-course .course0").hide();
//								};
//
//							};
                        });
                        //点击课程
                        $(".right-course .course-live").off().on("click", function () {
                            var $this = $(this);
                            $.ajax({
                                type: "get",
                                url: bath + "/online/user/isAlive",
                                async: false,
                                success: function (data) {
                                    if (data.success === true) {
                                        window.open($this.attr("data-url"), "_blank");
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
                    } else {

                        $(".main-right .mycourse-live .right-course").html(emptyDefaul);
                        $(".pages").css("display", "none");
                    }
                })
            };


            //订单切换
            function order(state, time, pageNumber, pageSize) {
                RequestService("/web/getMyAllOrder", "get", {
                    orderStatus: state ? state : -1,//-1quanbu
                    timeQuantum: time ? time : 0,//全部
                    pageNumber: pageNumber ? pageNumber : 1,
                    pageSize: pageSize ? pageSize : 5

                }, function (data) {
                    if (data.success == true) {
                        if (data.resultObject.items == "") {
                            if (data.resultObject.totalCount == 0) { //无数据
                                $(".mybuy .right-course .ordertable").html(emptyDefaul);
                                $(".pages").css("display", "none");
                            }
                        } else if (data.resultObject.totalPageCount == 1 && data.resultObject.totalCount > 0) {
                            $(".pages").css("display", "none");
                            $(".mybuy .right-course .ordertable").html(template.compile(myorder)({
                                items: data.resultObject.items
                            }))
                        } else if (data.resultObject.totalPageCount > 1) {
                            $(".pages").css("display", "block");
                            $(".searchPage .allPage").text(data.resultObject.totalPageCount);
                            if (data.resultObject.currentPage == 1) {
                                $("#Pagination").pagination(data.resultObject.totalPageCount, {
                                    callback: function (page) { //翻页功能
                                        var param = {
                                            pageNumber: page + 1,
                                            pageSize: pageSize
                                        };
//										console.log(page);
                                        order(state, time, param.pageNumber, pageSize);
                                    }
                                });
                            }
                            $(".mybuy .right-course .ordertable").html(template.compile(myorder)({
                                items: data.resultObject.items
                            }));
                        }
                        //订单状态切换
                        $(".content .main-right .mybuy .right-title div").off().on("click", function () {
                            if ($(this).hasClass("allorder")) {
                                $(this).addClass("act").siblings().removeClass("act");
                            } else {
                                $(this).addClass("act").siblings().removeClass("act");
                                order($(this).attr("data-orderStatus"), $(".allorder span").attr("data-timeQuantum"), 1, 5)
                            }
                        });
                        $(".content .main-right .mybuy .right-title .allorder span").off().on("click", function () {
                            order(-1, $(".allorder span").attr("data-timeQuantum"), 1, 5)
                        });
                        $(".all-change a").off().on("click", function () {
                            var $all = $('<a data-timeQuantum="0">全部订单</a>');
                            $(".all-change a").each(function () {
                                $(this).css("display", "block");
                            });
                            $(this).css("display", "none");
                            $(".allorder span").html($(this).text())
                                .attr({
                                    "data-timeQuantum": $(this).attr("data-timeQuantum"),
                                    "title": $(this).attr("title")
                                });
                            order(-1, $(".allorder span").attr("data-timeQuantum"), 1, 5)
                        });
                        //取消订单
                        $(".ordertable .ord .quXiao").off("click").on("click", function () {
                            var $this = $(this);
                            var orderNo = $(this).attr("data-orderNo");
                            $(".tips1").show();
                            $(".tips1 .close,.tips1 .qx").off().on("click", function () {
                                $(".tips1").hide();
                            });
                            $(".tips1 .ok").off().on("click", function () {
                                RequestService("/web/updateOrderStatu", "post", {
                                    type: 1,
                                    orderNo: orderNo
                                }, function (data) {
                                    if (data.success == true) {
                                        if (data.resultObject == "操作成功!") {
                                            $(".tips1").hide();
                                            window.location.href = "/my?order=0";
                                        }
                                    }
                                });
                            });
                        });
                        //删除订单
                        $(".ordertable .ord .delete").off("click").on("click", function () {
                            var $this = $(this);
                            var orderNo = $(this).attr("data-orderNo");
                            $(".tips2").show();
                            $(".tips2 .close,.tips2 .qx").off().on("click", function () {
                                $(".tips2").hide();
                            });
                            $(".tips2 .ok").off().on("click", function () {
                                RequestService("/web/updateOrderStatu", "post", {
                                    type: 0,
                                    orderNo: orderNo
                                }, function (data) {
                                    if (data.success == true) {
                                        if (data.resultObject == "操作成功!") {
                                            $(".tips2").hide();
                                            window.location.href = "/my?order=0";
                                        }
                                    }
                                });
                            });
                        });
                    } else {
                        $(".mybuy .right-course .ordertable").html(emptyDefaul);
                        $(".pages").css("display", "none");
                    }
                })
            };

            //提示框
            function rTips(num) {
                $(".rTips").html(num).css("display", "block");
                setTimeout(function () {
                    $(".rTips").css("display", "none");
                }, 1000)
            };
        } else {
            $('#login').css("display", "none");
            $(".loginGroup .logout").css("display", "block");
            $(".loginGroup .login").css("display", "none");
            location.href = "/index.html"
        }
    }, false);
    //选中变变色
    addSelectedMenu();
};

function addSelectedMenu() {
    $(".studentCenterBox").css("color", "#2cb82c");
}

$(function(){
    $('.ordertable').on('click', '.ord #totalMoney span .J-repeat-buy', function(e) {
        e.preventDefault();
        var courseId = $(this).data('courseid');
        console.log(courseId);
        if (courseId) {
            RequestService("/order/" + courseId, "POST", null, function(data) {
                if (data.success) {
                    window.location.href = "/order/pay?orderId=" + data.resultObject;
                } else {
                    showTip(data.errorMessage);
                }
            });
        }
    })
});


//购物车列表点击事件
template.helper('orderClick', function (order) {
    var a = '<a style="cursor:pointer;color=#333;display: block;width: 100%; height: 100%;"  href="/course/courses/' + order.id + '"  target="_blank">';
    // if(order.type == 3){
    // 	a = '<a style="cursor:pointer;color=#333;display: block;width: 100%; height: 100%;"  href="/web/html/payRealCourseDetailPage.html?id='+order.id+'"  target="_blank">';
    // }else if(order.type == 1){
    // 	a = '<a style="cursor:pointer;color=#333;display: block;width: 100%; height: 100%;" href="/web/html/payOpenCourseDetailPage.html?id='+order.id+'&direct_id='+order.direct_id+'"  target="_blank">';
    // }else{
    // 	a = '<a style="cursor:pointer;color=#333;display: block;width: 100%; height: 100%;" href="/web/html/payCourseDetailPage.html?id='+order.id+'&courseType='+order.course_type+'&free=0" target="_blank">';
    // }
// console.info(a);
    return a;
});