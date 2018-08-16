$(function () {

    var roomId = $('#J_roomId').val();
    var channelId = $('#J_channelId').val();
    var appId = $('#J_appId').val();
    var accountId = $('#J_accountId').val();
    var token = $('#J_token').val();
    var nickname = $('#J_nickname').val();
    var headImg = $('#J_headImg').val();
    var curPage = 1;
    var page = 1;
    var docId;
    var emojiMap = new Map();
    var emojiReg = /\[.+?\]/g;
    var cameras = [];
    var mics = [];
    var loadAnchorIn = false;
    var loadAnchorOut = false;
    var transOverTimer;
    window.doc = null;

    window.Vhall.config({
        appId: appId,//应用 ID ,必填
        accountId: accountId,//第三方用户唯一标识,必填
        token: token,//token必填
        channelId: channelId
    });

    function destroyDoc() {
        $('#J_doc_main').empty();
        window.doc = null;
    }

    function initDoc() {
        destroyDoc();
        var width = $('#J_doc_main').width();
        var height = $(".video-main").height();
        window.doc = new VhallDoc({
            roomId: roomId,
            channelId: channelId, //频道Id
            docId: '',//jpg big
            docNode: 'J_doc_main',//文档显示节点div id
            width: width,
            height: height,
            success: function () {
                console.log("文档初始化成功");
            },
            complete: function () {
                console.log("文档初始化完成");
            },
            slideChange: function (slideIndex, stepIndex) {
            },
            stepChange: function (slideIndex, stepIndex) {
                curPage = slideIndex + 1;
                setPage(page, curPage);
            }
        });
    }

    function init() {
        setTimeout(function () {
            initDoc();
        }, 1000);
        window.Vhall.ready(function () {
            VHPublisher.init({
                roomId: roomId,
                videoNode: 'J_video_main',
                complete: function () {
                    console.log("初始化成功!")
                }
            });
            window.chat = new VhallChat({
                channelId: channelId //频道Id
            });
            window.chat.onCustomMsg(function (msg) {
                msg = JSON.parse(msg);
                renderMsg(msg)
            });
            window.chat.join(function (msg) {
                console.log("进入直播间");
                var userId = msg.third_party_user_id;
                if (!loadAnchorIn || userId != accountId) {
                    if (userId == accountId) {
                        loadAnchorIn = true;
                    }
                    viewJoinleaveRoomInfo(msg, "join");
                    joinStudent(userId, msg.avatar, msg.nick_name);
                }
            });
            window.chat.leave(function (msg) {
                console.log("离开直播间");
                var userId = msg.third_party_user_id;
                if (!loadAnchorOut || userId != accountId) {
                    if (userId == accountId) {
                        loadAnchorOut = true;
                    }
                    viewJoinleaveRoomInfo(msg, "leave");
                    removeStudent(userId);
                }
            });
        });
        setTimeout(function () {
            initDevices();
        }, 3000);
    }

    init();

    function initDevices() {
        try {
            cameras = window.Vhall.devices.cameras;
            mics = window.Vhall.devices.mics;
            var $JCameras = $('.J-cameras');
            var $JMics = $('.J-mics');
            $JCameras.html('');
            $JMics.html('');
            for (var i = 0; i < cameras.length; i++) {
                $JCameras.append('<option value="' + cameras[i] + '">' + cameras[i] + '</option>');
            }
            for (var i = 0; i < mics.length; i++) {
                $JMics.append('<option value="' + mics[i] + '">' + mics[i] + '</option>');
            }
        } catch(e) {
        }

    }

    $('#J_confirm_edit').on('click', function () {
        if ($('#J_play').data('status') == 1) {
            restartPlay();
        }
        $(".background-ask").addClass("hide");
        $(".setup-modal").addClass("hide");
    });

    function restartPlay() {
        // var width = $('.J-setup-width').val();
        // var height = $('.J-setup-height').val();
        // width = width ? width : 800;
        // height = height ? height : 450;
        VHPublisher.init({
            roomId: roomId,
            videoNode: 'J_video_main',
            success: function () {
                console.log('初始化成功==============');
            },
            complete: function (res) {
                console.log("初始化完成=============");
                console.log(res);
                if (res && res.code == 2000) {
                    VHPublisher.startPush({
                        // width: width,
                        // height: height,
                        camera: $('.J-cameras').val(),
                        mic: $('.J-mics').val(),
                        success: function (res) {
                            console.log('推流切换成功');
                            console.log(res);
                        }
                    })
                }
            }
        });
    }

    function micAndCamerasLack() {
        return !cameras || cameras.length === 0 || !mics || mics.length === 0;
    }

    $('#J_play').on('click', function () {
        var $this = $(this);
        $this.prop('disabled', 'disabled');
        if ($this.data('status') == 0) {
            if (micAndCamerasLack()) {
                initDevices();
                if (micAndCamerasLack()) {
                    $(".noll-equipment").removeClass("hide");
                    $(".background-ask").removeClass("hide");
                    $this.removeAttr('disabled');
                    initDevices();
                    return false;
                }
            }
            VHPublisher.startPush({
                width: 800,
                height: 450,
                camera: $('.J-cameras').val(),
                mic: $('.J-mics').val(),
                success: function () {
                    $('.play-time').text("00:00");
                    var n = 0;
                    timer = setInterval(function () {
                        n++;
                        var m = parseInt(n / 60);
                        var s = parseInt(n % 60);
                        $('.play-time').text(toDub(m) + ":" + toDub(s));
                    }, 1000);
                    updateLiveStatus("start");

                    $this.text('结束直播');
                    $this.css('background', "red");
                    $this.data('status', 1);
                    console.log("推流成功");
                },
                fail: function (e) {
                    console.log(e);
                    showTip("直播未能开启");
                }
            });
        } else {
            VHPublisher.stopPush({
                complete: function () {
                    $this.text('开始直播');
                    $this.css('background', "#00BC12");
                    $this.data('status', 0);
                    clearInterval(timer);
                    updateLiveStatus("stop");
                }
            });
        }
        $this.removeAttr('disabled');
    });

    function buttomMessageList() {
        var $chatPersonal = $('.chat-personal');
        $chatPersonal.scrollTop($chatPersonal[0].scrollHeight);
    }

    function renderMsg(msg) {
        if (msg.type == 11) { // 礼物
            if (msg.message) {
                var html = '<li>\n' +
                    '                            <span class="chat-name">' + msg.message.senderInfo.userName + ':</span>\n' +
                    '                            <span class="chat-content">送给主播</span>\n' +
                    '                            <span class="chat-gift">一个' + msg.message.giftInfo.name + '</span>\n' +
                    '                        </li>';

                $('#J_message_list').append(html);
            }

        } else if (msg.type == 10) {
            if (msg.message) {
                var html = '';
                var content = msg.message.content;
                var username = msg.message.username;
                if (content) {
                    content = content.replace(emojiReg, function (a, b) {
                        var imgUrl = emojiMap.get(a);
                        if (imgUrl) {
                            a = '<img src="' + imgUrl + '" style="width: 20px;height: 20px">';
                        }
                        return a;
                    })
                }
                html = '<li>\n' + ( msg.message.role == "host" ? '<span class="chat-status">主播</span>\n' : '') +
                    '                            <span class="chat-name">' + username + ':</span>\n' +
                    '                            <span class="chat-content">' + content + '</span>\n' +
                    '                        </li>';
                $('#J_message_list').append(html);
            }
        }
        buttomMessageList();
    }

    function initMessage() {
        $.ajax({
            method: 'GET',
            url: '/vhallyun/message',
            data: {'channel_id': channelId, 'start_time': '2018/01/01', 'limit': '1000'},
            success: function (resp) {
                if (resp.resultObject && resp.resultObject.data) {
                    var result = resp.resultObject.data;
                    for (var i = 0; i < result.length; i++) {
                        try {
                            renderMsg(JSON.parse(JSON.parse(result[i].data)));
                        } catch (e) {
                        }
                    }
                }
            }
        });
    }

    initMessage();

    function viewJoinleaveRoomInfo(msg, action) {
        var userId = msg.third_party_user_id;
        var $JMessageList = $('#J_message_list');
        if ($JMessageList.find('li').last().data('mid') !== (userId + '-' + action)) {
            var html = '<li data-mid="' + msg.third_party_user_id + '-' + action + '">\n' +
                (msg.third_party_user_id === accountId ? '<span class="chat-status">主播</span>' : '') +
                '                            <span class="chat-name">' + msg.nick_name + ':</span>\n' +
                '                            <span class="chat-content">' + (action === 'join' ? '进入直播间' : '退出') + '</span>\n' +
                '                        </li>';
            $JMessageList.append(html);
            buttomMessageList();
        }
    }

    function renderStudentList() {
        $.ajax({
            method: 'GET',
            url: '/vhallyun/roomJoinStudent',
            data: {'channelId': channelId, "pos": 0, "limit": 1000},
            success: function (resp) {
                var data = resp.resultObject;
                for (var i = data.length - 1; i >= 0; i--) {
                    if (data[i].id != accountId) {
                        appendStudentList(data[i].id, data[i].name, data[i].smallHeadPhoto, data[i].ban);
                    }
                }
                updatePersonNum();
            }
        });
    }

    function updatePersonNum() {
        $('.J-person-num').text('(' + $('.student-list li').length + '人)')
    }

    $('.J-refresh-list').on('click', function () {
        renderStudentList();
    });

    function joinStudent(userId, avatar, nickname) {
        if (userId != accountId) {
            if ($('.user-id-' + userId).length === 0) {
                $.ajax({
                    method: "GET",
                    url: '/vhallyun/banStatus',
                    data: {
                        channelId: channelId,
                        accountId: userId
                    },
                    success: function (resp) {
                        appendStudentList(userId, nickname, avatar, resp.resultObject)
                    }
                });
            }
        }
    }

    function appendStudentList(userId, nickname, avatar, ban) {
        removeStudent(userId);
        $('.student-list').append('<li>\n' +
            '                            <div class="head-portrait z ' + ' user-id-' + userId + '"' + ' data-id="' + userId + '">\n' +
            '                                <img src="' + avatar + '" alt="头像"/>\n' +
            '                            </div>\n' +
            '                            <span class="student-name z">' + nickname + '</span>\n' +
            ' <span class="select-ban y">' +
            (ban ? '<img src="/web/images/live-room/say-ban.png"/>' : '                                <img src="/web/images/live-room/say-icon.png" alt="选择禁言" title="禁言"/>\n' ) +
            '                            </span>' +
            '                        </li>');
        updatePersonNum();
    }

    function removeStudent(userId) {
        $('.user-id-' + userId).parent().remove();
    }

    renderStudentList();

    function setBanStatus(accountId, status) {
        $.ajax({
            method: 'POST',
            url: '/vhallyun/ban/' + channelId + '/' + accountId + '/' + status,
            success: function (resp) {
            }
        })
    }

    $('.J-eraser').on('click', function () {
        window.doc.setEraser(16);
    });

    $('.J-clear').on('click', function () {
        window.doc.clear();
    });
    $(".comment-bg").on("click", function () {
        var a = $(this).data("size");
        window.doc.setSize(a);
        window.doc.cancelPencil(false);
    });

    $(".comment-color").on("click", function () {
        var a = $(this).data("color");
        window.doc.setColor(a);
        window.doc.cancelPencil(false);
    });

    function toDub(n) {
        return n < 10 ? "0" + n : "" + n
    }

    var timer = null;

    function updateLiveStatus(event) {
        $.ajax({
            method: 'POST',
            url: '/course/updateLiveStatus',
            data: {"roomId": roomId, "event": event},
            async: false,
            success: function (resp) {
                console.log("调用后台更新状态成功");
            }
        });
    }

    function reloadDoc(changePage) {
        initDoc();
        window.doc.loadDoc(docId, channelId, function (docId) {
            if (changePage) {
                $('.video-main').css("background", "none");
                $('.J-close-doc').trigger("click");
                getImg();
            } else {
                window.doc.gotoSlide(curPage);
            }
        }, function (reason) {
            console.error(reason);
        });
    }

    function getImg() {
        setTimeout(function () {
            var imgs = window.doc.getThumImgList(function (list) {
            });
            $('.J-thumImg').html('');
            page = imgs.length;
            if (!page) {
                page = 1;
            }
            curPage = 1;
            setPage(page, curPage);
            for (var i = 0; i < imgs.length; i++) {
                $('.J-thumImg').append('<li class="' + (i + 1 === curPage ? "active" : "") + ' J-page-img J-page-num-' + (i + 1) + '">\n' +
                    '                                    <img src="' + imgs[i] + '"/>\n' +
                    '                                    <span>' + (i + 1) + '</span>\n' +
                    '                                </li>');
            }
        }, 1000);
    }

    $('.J-thumImg').on('click', '.J-page-img', function () {
        var p = $(this).find('span').text();
        curPage = parseInt(p);
        console.log(p);
        window.doc.gotoSlide(curPage);
        setPage(page, curPage);
    });

    $('.J-doc-prev').on('click', function () {
        if (window.doc && curPage > 1) {
            curPage = parseInt(curPage) - 1;
            window.doc.preSlide();
            setPage(page, curPage);
        }
    });
    $('.J-doc-next').on('click', function () {
        if (window.doc && curPage < page) {
            curPage = parseInt(curPage) + 1;
            window.doc.nextSlide();
            setPage(page, curPage);
        }
    });

    function setPage(page, curPage) {
        $('.pages').removeClass("hide");
        if (curPage <= 1) {
            curPage = 1;
        }
        if (curPage === 1) {
            $('.J-doc-prev').hide();
        } else {
            $('.J-doc-prev').show();
        }
        if (curPage === page) {
            $('.J-doc-next').hide();
        } else {
            $('.J-doc-next').show();
        }
        $('.now-page').text(curPage);
        $('.all-pages').text(page);
        $(".modal-list li").removeClass("active");
        $('.J-page-num-' + curPage).addClass('active');
    }

    $('.file-list').on('click', '.J-doc-operation', function () {
        if (!isSlideOpen()) {
            $(".icon-left").click();
        }
        docId = $(this).parent().data('did');
        reloadDoc(true);
        if (!page) {
            page = 1;
        }
        setPage(page, 1);
    });

    function isSlideOpen() {
        return !$(".icon-right").parent('.select-document-wrap').hasClass("select-left");
    }

    $('.file-list').on('click', '.J-doc-delete', function () {
        var $parent = $(this).parent();
        var docId = $parent.data('did');
        $.ajax({
            method: "DELETE",
            url: "/vhallyun/document/" + docId,
            success: function (resp) {
                $parent.remove();
                if ($('.hover-delect').length == 0) {
                    $('.null-document').removeClass('hide');
                }
            }
        })
    });

    transOverTimer = setInterval(changeTransOverStatus, 10 * 1000);
//转码定时调用
    function changeTransOverStatus() {
        $.ajax({
            method: "GET",
            url: "/vhallyun/document",
            success: function (resp) {
                var docs = resp.resultObject;
                var allFinishTransOver = true;
                for (var i = 0; i < docs.length; i++) {
                    var documentId = docs[i].documentId;
                    var status = docs[i].transStatus;
                    if (status != 0) {
                        var $docItem = $('.J-doc-item-text-' + documentId);
                        if (status === 1) {
                            if ($docItem.text() === "等待转换") {
                                $docItem.text("转换成功");
                                $docItem.after('<div class="doc-operation text-center J-doc-operation J-operation-' + documentId + '">\n' +
                                    '                        <p>演示</p>\n' +
                                    '                    </div>');
                            }
                        } else if (status === 2) {
                            $docItem.text("转换成功");
                        }
                    } else {
                        allFinishTransOver = false;
                    }
                }

                if (allFinishTransOver && transOverTimer) {
                    clearInterval(transOverTimer);
                    transOverTimer = null;
                }
            }
        })
    }

    function sendMessage() {
        var $JMessageText = $('#J_message_text');
        var message = $JMessageText.val();
        if (message) {
            var content = {
                type: 10,
                message: {
                    content: message,   //发送的内容
                    headImg: headImg,       //发送的头像
                    username: nickname,     //发送的用户名
                    role: "host"           //发送人的角色    主播： host   普通用户： normal
                }
            };
            $JMessageText.val('');
            $.ajax({
                method: "POST",
                url: "/vhallyun/message",
                data: {'body': JSON.stringify(content), 'channelId': channelId},
                success: function (resp) {
                    console.log("发送成功");
                }
            })
        } else {
            showTip("请输入聊天文字")
        }
    }

    $('#J_message_send').on('click', function () {
        sendMessage();
    });
    $('#J_message_text').keypress(function (event) {
        if (event.keyCode == 13) {
            sendMessage();
        }
    });
//------------------------------------------静态页面效果----------------------------------------------------------------

    function getWhiteHeight() {
        var videoHeight = $(document.body).height() - 219;
        var studentHeight = $(document.body).height() - 262;
        var chatHeight = $(document.body).height() - 152;
        var documentWidth = $(".video-width").width() - 170;

        //	文档高度
        $(".video-main").css({"height": videoHeight});
        //	文档左侧文件列表高度
        $(".select-document-wrap").css({"height": videoHeight});
        //	学员列表高度
        $(".student-list").css({"height": studentHeight});
        //	聊天区域
        $(".chat-personal").css({"height": chatHeight});
        //  文档宽度
        $(".document-content").css({"width": documentWidth});
    }

    getWhiteHeight();
    $(window).resize(function () {
        getWhiteHeight();
        documentAreaReload();
    });
//------------------------------------------工具栏----------------------------------------------------------------	


//	顶部工具hover效果
    $(".select-tool .show-img").hover(function () {
        $(this).siblings(".tip-tool").removeClass("hide");
        $(".select-huabi").addClass("hide");
        $(this).siblings(".select-huabi").removeClass("hide");
    }, function () {
        $(this).siblings(".tip-tool").addClass("hide");
    })
//	点击添加背景
    $(".select-tool li").click(function () {
//		判断是否点击的颜色
        if ($(this).hasClass("remove-color")) {
        } else {
            $(".select-tool li").removeClass("active");
            $(this).addClass("active");
        }

    })
//	点击下拉选项添加背景
    $(".select-tool .select-huabi .comment-bg").click(function () {
        $(".select-tool .select-huabi .comment-bg").removeClass("active");
        $(this).addClass("active");
//		判断是否点击颜色下拉选项
        if ($(this).hasClass("comment-color")) {
            var selectClass = $(this).find("img").attr("src");
            $(this).parent().siblings(".show-img").find(".sure-color").attr("src", selectClass)
        }
    })
//	收起下拉工具
    $(window).click(function () {
        $(".select-tool .select-huabi").addClass("hide");
    })


//------------------------------------------文档弹出框----------------------------------------------------------------	


//	点击文档弹出框
    $(".select-document .document-modal").click(function () {
        $(".background-ask").removeClass("hide");
        $(".modal-document").removeClass("hide");
    })
//	关闭文档弹出框
    $(".modal-top img").click(function () {
        $(".background-ask").addClass("hide");
        $(".modal-document").addClass("hide");
    })

    $('.file-list').on('mouseenter', '.hover-delect', function () {
        $(".hover-delect .delect-img").addClass("hide");
        $(this).find(".delect-img").removeClass("hide")
    });
    $('.file-list').on('mouseleave', '.hover-delect', function () {
        $(".hover-delect .delect-img").addClass("hide");
    });

    function documentAreaReload() {
        var documentWidth = 0;
        if (isSlideOpen()) {
            documentWidth = $(".video-width").width() - 170;
        } else {
            documentWidth = $(".video-width").width() - 70;
        }
        $(".document-content").css({"width": documentWidth});
        window.doc.resize(documentWidth, $(".video-main").height());
    }

//------------------------------------------文档左侧列表点击效果----------------------------------------------------------------	
    $(".icon-right").click(function () {
        $(this).parent(".select-document-wrap").addClass("select-left");
        $(".video-main .icon-left").removeClass("hide");
        documentAreaReload();
    })
    $(".icon-left").click(function () {
        $(this).siblings(".select-document-wrap").removeClass("select-left");
        $(".video-main .icon-left").addClass("hide");
        documentAreaReload();
    })
    $(".modal-list li").each(function () {
        var index = $(this).index();
        var num = index + 1;
        $(this).find("span").html(num)
    })
// $(".modal-list li").click(function () {
//     $(".modal-list li").removeClass("active");
//     $(this).addClass("active");
//     var p = $(this).data('page');
//     curPage = p;
//     setPage(page, p);
// });

//------------------------------------------设置----------------------------------------------------------------	

    $(".select-operation .set-up").click(function () {
        $(".background-ask").removeClass("hide");
        $(".setup-modal").removeClass("hide");
    })

    $(".comment-setup .cancel").click(function () {
        $(".background-ask").addClass("hide");
        $(".setup-modal").addClass("hide");
    })
//输入框正在输入时
// 	宽
    $('.comment-setup .width-my').on('blur', function () {
        var that = $(this);
        if ((that.val() < 120)) {
            $('.comment-setup .control-size').html("最小值为120X120");
            $('.comment-setup .control-size').removeClass("hide");

            that.val("120")
        } else if (that.val() > 1920) {
            $('.comment-setup .control-size').html("最大值为1920X1080");
            $('.comment-setup .control-size').removeClass("hide");

            that.val("1920")
        } else {
            $('.comment-setup .control-size').addClass("hide");
        }
    });
//  高
    $('.comment-setup .height-my').on('blur', function () {
        var that = $(this);
        if ((that.val() < 120)) {
            $('.comment-setup .control-size').html("最小值为120X120");
            $('.comment-setup .control-size').removeClass("hide");

            that.val("120")
        } else if (that.val() > 1080) {
            $('.comment-setup .control-size').html("最大值为1920X1080");
            $('.comment-setup .control-size').removeClass("hide");

            that.val("1080")
        } else {
            $('.comment-setup .control-size').addClass("hide");
        }
    });

//选择禁言
    $(".student-list").on('click', '.select-ban', function () {
        var that = $(this).find("img");
        var userId = $(this).parent().find('.head-portrait').data('id');
        if (that.attr("src") == "/web/images/live-room/say-icon.png") {
            setBanStatus(userId, 1);
            that.attr({"src": "/web/images/live-room/say-ban.png", "title": "取消禁言"});
        } else {
            setBanStatus(userId, 0);
            that.attr({"src": "/web/images/live-room/say-icon.png", "title": "禁言"});
        }
    });
    $(".comment-setup .cancel").click(function () {
        $(".background-ask").addClass("hide");
        $(".setup-modal").addClass("hide");
    });

//表情设置
    $.ajax({
        type: "get",
        url: "/web/js/live-room/emoticon.json",
        success: function (data) {
            var del = "";
            for (var i = 0; i < data.length; i++) {
                emojiMap.set(data[i].text, data[i].imgUrl);
                del += '<li><img src="' + data[i].imgUrl + '" data-text="' + data[i].text + '" class="J-emoji"></li>'
            }
            $(".expression-list").html(del)
        }
    });
    $(".expression-img").click(function () {
        var $expressionSelect = $(".expression-select");
        if ($expressionSelect.hasClass('hide')) {
            $expressionSelect.removeClass("hide");
        } else {
            $expressionSelect.addClass('hide');
        }
    });

    $('.expression-list').on('click', '.J-emoji', function () {
        var $messageText = $('#J_message_text');
        $(".expression-select").addClass('hide');
        $messageText.val($messageText.val() + $(this).data('text'));
        $messageText.focus();
    });


//------------------------------------------文档转换----------------------------------------------------------------

    $('.document-upload').on('click', function () {
        $('#file-input').trigger('click');
    });
    $('#file-input').change(function () {
        $('.document-upload').text('上传中');
        $('.document-upload').prop('disabled', 'disabled');
        $fileInput = $('#file-input');
        $('#submitFile').ajaxSubmit({
            type: 'post',
            dataType: 'json',
            success: function (result) {
                //请求成功后的操作
                var obj = result.resultObject;
                var liHtml = ' <li class="doc-title hover-delect" data-did ="' + obj.documentId + '">\n' +
                    '                <div class="doc-name doc-photo">' + obj.filename + '</div>\n' +
                    '                <div class="doc-time text-center">' + obj.createTime + '</div>\n' +
                    '                <div class="doc-progress text-center J-doc-item-text-' + obj.documentId + '">等待转换</div>\n' +
                    '                <div class="delect-img hide J-doc-delete"></div>\n' +
                    '            </li>';
                $('.J-doc-title').after(liHtml);
                $fileInput.val('');
                $('.document-upload').prop('disabled', '');
                $('.document-upload').text('上传');
                $('.null-document').hide();
                console.log(transOverTimer);
                if (!transOverTimer) {
                    transOverTimer = setInterval(changeTransOverStatus, 10 * 1000);
                }
            },
            error: function () {
                $fileInput.val('');
                $('.document-upload').prop('disabled', '');
                $('.document-upload').text('上传');
            }
        });
    })
 

//------------------------------------------点击设备时关闭弹窗----------------------------------------------------------------
	$(".equipment-close").click(function(){
		$(".noll-equipment").addClass("hide");
   $(".background-ask").addClass("hide");
	})
    
});
