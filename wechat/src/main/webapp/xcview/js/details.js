
if (!is_weixin()) {
    $(".weixin_li").remove();
}
var course_id = getQueryString("courseId");
/**
 * 判断是否需要跳转到pc网页
 */
h5PcConversions(true, course_id);



/**
 * 视频id
 */
var videoId = "";
var teacherId;
var teacherName;
var courseHead = "";
var roomNumber = "";
var lineState = 1;
var result = "";
var playBackType = 1;

var vhallObj = {
    accountId: localStorage.getItem("userId")
};
// 统一提交的方法
requestService("/xczh/course/liveDetails", {
        courseId: course_id
    },function (data) {
    	
    	if(data.code == 300){
    	    location.href=data.errorMessage;
    		return;
    	}if (data.success) {
            
            // 修改title
            document.setTitle = function (t) {
                document.title = t;
                var i = document.createElement('iframe');
                // i.src = '//m.baidu.com/favicon.ico';
                i.style.display = 'none';
                i.onload = function () {
                    setTimeout(function () {
                        i.remove();
                    }, 9)
                }
                document.body.appendChild(i);
            }
            setTimeout(function () {
                document.setTitle(result.gradeName);
                // alert(111);
            }, 1000);


            result = data.resultObject;
            // 视频id
            videoId = result.directId;

            vhallObj.roomId = result.directId;
            vhallObj.channelId = result.channelId;
            vhallObj.recordId = result.recordId;
            vhallObj.token =result.vhallYunToken;
            vhallObj.appId = result.appid;
            
            
            watchState = result.watchState;
            teacherId = result.userLecturerId; // 讲师Id
            teacherName = result.heir; // 上传人
            sessionStorage.setItem("hostName", teacherName);
            $("#userId").val(result.userId);
            $("#teacherId").val(result.userId);
            courseHead = result.smallImgPath;

            $(".details_size span:eq(1)").html(result.giftCount);

            $(".details_size span:eq(0)").html(result.learndCount);
            //学习人数
            sessionStorage.setItem("learndCount", result.learndCount);

            // 关注数
            $(".n_guanzhu").html(result.focusCount);
            // 粉丝数
            $(".n_fensi").html(result.fansCount);
            /**
             * 为详情页面添加数据
             */
            $(".headImg").attr("src", result.headImg);
            $(".guanz_headImg").attr("src", result.headImg);
            $(".smallImgPath").attr("src", result.smallImgPath);
            $(".main_title").find('.p0').html(result.heir);


            $(".details_chat1").attr("src", result.headImg);
            var children = $("#zhiboxiangqing [class='p1']").text(
                result.gradeName);
            var children = $("#zhiboxiangqing [class='p2'] span").text(
                teacherName);
            var children = $("#zhibopinglun [class='p1']").text(
                result.gradeName);
            var children = $("#zhibopinglun [class='p2']")
                .text(result.name);

            $(".anchor_center").html(result.lecturerDescription); //主播简介

            //  主播简介判断为空
            if (data.resultObject.lecturerDescription == null || data.resultObject.lecturerDescription == '') {
                $(".anchor_center01").hide();
                $(".null_anchor").show();
            } else {
                $(".anchor_center01").show();
                $(".null_anchor").hide();
            }

            /**
             * 关注 0 未关注 1 关注
             */
            if (result.isFocus == 1) {
                $(".add_follow").find('img').attr('src', '../images/yigz.png');
                $(".add_follow").find('p').html("已关注");
                $(".add_follow").removeClass("add_follows00");
                $(".add_follow").addClass("add_follows1");
                $(".add_follow").removeClass("add_follows0");

            } else if (result.isFocus == 0) {
                $(".add_follow").find('img').attr('src', '../images/weigz.png');
                $(".add_follow").find('p').html("加关注");
                $(".add_follow").removeClass("add_follows00");
                $(".add_follow").addClass("add_follows0");
                $(".add_follow").removeClass("add_follows1");
            }

            // 0表示生成中，1表示生成成功，2表示生成失败 3 直播状态不是已结束
            // 判断结束状态--提示
            playBackType = result.playBackType;

            if (playBackType == 0) {
                $(".history_span").text("即将直播");
            }

             lineState = result.lineState;
            
            /**
             * 直播状态1.直播中，2预告，3直播结束 4 即将直播
             */
            if (lineState == 3) {
                $(".history_span").text("直播回放");

                $(".coze_center .coze_cen_ri:last-child").css("margin-bottom", "0");

                $(".mCustomScrollbar").css("padding-bottom", "0");

                $(".cover").show(); /*回放时添加遮盖层*/
                $(".give_a1_img").attr("src", "/xcview/images/gafts.png");
                //alert(123);
            } else if (lineState == 4) {
                $(".history_span").text("即将直播");
            }
            if (lineState == 3 || lineState == 4) { // 隐藏送礼

                $("title").text("熊猫中医");

                $(".give_a1_img").hide();
                $(".give_a1_span02").hide();
                $(".poson").css('right', '0rem');
                $(".poson").css('margin-left', '0.4rem');
                $("#face").css('position', 'absolute'); //表情
                $("#face").css('top', '0'); //表情
                $("#face").css('left', '-0.05rem'); //表情
                $("#sendChat").addClass('important'); //发送按钮 添加class

                $(".give_a01").show();

                //                  点击输入框
                $("#mywords").click(function () {
                    $(".give_a1").hide();
                    $(".give_a1_img").hide();
                    $(".div_input").css("background", "none");

                });
                $(".details_size").hide();

                $(".div_input").css('width', '6rem');
                $(".coze_bottom_input").css('margin-left', '0rem');
                $(".give_a01").css('margin-left', '0.5rem');
                $(".give_a01").css('right', '0.6rem'); //表情

                $("#sendChat").addClass('importants'); //发送


                //                  点击表情
                $("#face").click(function () {
                    $(".coze_bottom").css('bottom', '7.1rem');
                });

                /* 点击发送 */
                $("#sendChat").click(function () {
                    $(".give_a1").hide(); /* 礼物隐藏 */
                    $(".give_a1").css("display", "none"); /* 礼物隐藏 */
                    $(".coze_bottom").css("bottom", "0"); /* 最底部区域到最下方 */
                    $(".face_img01").css('background', 'url(/xcview/images/face.png) no-repeat');
                    $(".face_img01").css("background-size", "100% 100%");

                });

                $("#mywords").css("width", "6rem");
                $("#face").hide();

            } else {

                // 正在直播
                $("title").text("熊猫中医");
                $(".history_span").text("直播中");

                $("#mywords").click(function () {
                    $(".coze_bottom input").css("width", "6rem");
                    $(".div_input").css("background", "none");
                });

                //      coze   点击其他区域，聊天区域
                $(".coze").click(function () {
                    $(".send_gifts").hide(); /*礼物区域隐藏*/
                    $("#sendChat").hide(); /*发送按钮隐藏*/
                    $(".give_a01").hide(); /*表情隐藏*/
                    $(".coze_bottom input").css("width", "6rem"); /*改变聊天input长度*/
                    $(".give_a1").show(); /*礼物显示*/
                });

                //      点击课件之间的  发送 礼物切换
                $(".details_footer_width li").click(function () {
                    $(".send_gifts").hide(); /*礼物区域隐藏*/
                    $("#sendChat").hide(); /*发送按钮隐藏*/
                    $(".give_a01").hide(); /*表情隐藏*/
                    $(".coze_bottom input").css("width", "6rem"); /*改变聊天input长度*/
                    $(".give_a1").show(); /*礼物显示*/
                    $(".div_input").css("background", "block"); /*input背景色隐藏*/
                });

                // 点击送礼开始 --显示送礼列表
                $(".give_a1").click(function () {
                      requestService("/xczh/manager/getWalletEnchashmentBalance", null,
                                function (data) {
                                    if (data.success) {
                                        $("#xmbShowSpan").html(data.resultObject);
                                    } else {
                                        $(".vanish2").show();
                                        setTimeout(function () {
                                            $(".vanish2").hide();
                                        }, 1500);
                                    }
                                });
                    $(".send_gifts").show();
                });

                // 点击发送
                $("#sendChat").click(function () {
                    $(".give_a01").hide(); /* 表情隐藏 */
                    $(this).hide(); /* 当前发送按钮隐藏 */
                    $(".coze_bottom input").css("width", "6rem");
                    $(".give_a1").show(); /* 礼物显示 */

                });
            }

            if (lineState == 4) { //即将直播的
                var startTime = result.startTime;
                $(".initiation_span").html(startTime.slice(0, 16));
                $("#initiation_gradename").html(result.gradeName);
                $(".video_end_top0").show();
                $(".cover").show(); //显示即将直播时候--聊天区域添加的遮盖层
                timer(new Date(startTime).getTime(),new Date().getTime());
            }
            
            var liveCase = result.liveCase;
            if(lineState ==1 && liveCase == 2){
                $(".video_end_top3").show();
            }
        }
    }, false)

/**
 * 倒计时的  分钟和秒数
 * @param 开始时间
 * @param 当前时间
 */
function timer(startTime, currentTime) {

    var strDiff = (startTime - currentTime) / 1000;
    var intDiff = parseInt(strDiff); //倒计时总秒数量

    window.setInterval(function () {
        var day = 0,
            hour = 0,
            minute = 0,
            second = 0; //时间默认值
        if (intDiff > 0) {
            minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
            second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
        }
        if (minute == 0 && second == 0) {
            $('#minute_show').html('请稍等,');
            $('#second_show').html('即将开始!');
        } else {
            if (minute <= 9) minute = '0' + minute;
            if (second <= 9) second = '0' + second;
            $('#minute_show').html('<s></s>' + minute + '分');
            $('#second_show').html('<s></s>' + second + '秒');
            intDiff--;
        }
    }, 1000);
}


// 聊天--关注开始
$(".add_follow").click(function () {
    var n_fensi = $(".n_fensi").html();
    var src = $(this).find('img').attr('src');
    var type = 1;
    var htmlstr = $(".add_follow").find('p').html();
    if (htmlstr == "已关注") { // 增加关注
        type = 2;
    } else {
        type = 1;
    }
    requestService("/xczh/myinfo/updateFocus", {
        lecturerId: teacherId,
        type: type
    }, function (data) {
        if (data.success) {
            if (htmlstr == "已关注") {
                $(".add_follow").find('img').attr('src', '/xcview/images/weigz.png');
                $(".add_follow").find('p').html("加关注");
                $(".add_follow").removeClass("add_follows00");
                $(".add_follow").addClass("add_follows0");
                $(".add_follow").removeClass("add_follows1");
                $(".n_fensi").html(parseInt(n_fensi) - 1);
                //判断是不是自己关注自己了
                var userId = localStorage.getItem("userId");
                if (teacherId == userId) {
                    var $span = $(".n_guanzhu");
                    var left_p = $span.html();
                    $span.html(parseInt(left_p) - 1);
                }
            } else {
                $(".add_follow").find('img').attr('src', '/xcview/images/yigz.png');
                $(".add_follow").find('p').html("已关注");
                $(".add_follow").removeClass("add_follows00");
                $(".add_follow").addClass("add_follows1");
                $(".add_follow").removeClass("add_follows0");
                // 粉丝数
                $(".n_fensi").html(parseInt(n_fensi) + 1);
                var userId = localStorage.getItem("userId");
                if (teacherId == userId) {
                    var $span = $(".n_guanzhu");
                    var left_p = $span.html();
                    $span.html(parseInt(left_p) + 1);
                }
                webToast("关注成功", "middle", 1500);
            }
        }
    })
});


/**
 * 点击主播头像跳转主播页面
 */
function userIndex() {
    location.href = "/xcview/html/live_personal.html?userLecturerId=" + teacherId;
}



