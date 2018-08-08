$(function () {
    var roomId = $('#J_roomId').val();
    var channelId = $('#J_channelId').val();
    var appId = $('#J_appId').val();
    var accountId = $('#J_accountId').val();
    var token = $('#J_token').val();
    var nickname = $('#J_nickname').val();
    var headImg = $('#J_headImg').val();
    var docId;
    window.doc = null;

    window.Vhall.config({
        appId: appId,//应用 ID ,必填
        accountId: accountId,//第三方用户唯一标识,必填
        token: token,//token必填
        channelId: channelId
    });
    VHPublisher.init({
        roomId: roomId,
        videoNode: 'J_video_main',
        complete: function () {
            console.log("初始化成功!")
        }
    });

    window.Vhall.ready(function () {
        /**
         * 初始化聊天对象
         */
        window.chat = new VhallChat({
            channelId: channelId //频道Id
        });
        /**
         * 监听聊天消息
         */
        window.chat.on(function (msg) {
            console.log(msg);
        });

        /**
         * 监听自定义消息
         */
        window.chat.onCustomMsg(function (msg) {
            msg = JSON.parse(msg);
            console.log(msg);
            renderMsg(msg)
        });

        window.chat.join(function (msg) {
            console.log(msg);
            console.log("进入直播间");
            viewJoinleaveRoomInfo(msg, "join");
        });
        window.chat.leave(function (msg) {
            console.log(msg);
            console.log("离开直播间");
            viewJoinleaveRoomInfo(msg, "leave");
        })
    });

    function renderMsg(msg) {
        console.log(msg.type);
        console.log(msg);
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
            console.log("==========")
            if (msg.message) {
                console.log("---------")
                var html = '';
                if (msg.message.role == 'host') {
                    html = '<li>\n' +
                        '                            <span class="chat-status">主播</span>\n' +
                        '                            <span class="chat-name">' + msg.message.username + ':</span>\n' +
                        '                            <span class="chat-content">' + msg.message.content + '</span>\n' +
                        '                        </li>';
                } else {
                    html = '<li>\n' +
                        '                            <span class="chat-name">' + msg.message.username + ':</span>\n' +
                        '                            <span class="chat-content">' + msg.message.content + '</span>\n' +
                        '                        </li>';
                }
                $('#J_message_list').append(html);
            }
        }
        $('.chat-personal').scrollTop($('.chat-personal')[0].scrollHeight);
    }

    $.ajax({
        method: 'GET',
        url: '/vhallyun/message',
        data: {'channel_id': channelId, 'start_time': '2018/01/01', 'limit': '1000'},
        success: function(resp) {
            if (resp.resultObject && resp.resultObject.data) {
                var result = resp.resultObject.data;
                for(var i = result.length - 1; i >=0 ; i--) {
                    renderMsg(JSON.parse(JSON.parse(result[i].data)));
                }
            }
        }
    });

    function viewJoinleaveRoomInfo(msg, action) {
        var html = '<li>\n' +
        '                            <span class="chat-name">' + msg.nick_name + ':</span>\n' +
        '                            <span class="chat-content">' + (action === 'join' ? '进入直播间' : '退出') + '</span>\n' +
            '                        </li>';
        $('#J_message_list').append(html);
        $('.chat-personal').scrollTop($('.chat-personal')[0].scrollHeight);
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
        console.log("event:" + event);
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

    $('#J_play').on('click', function () {
        var $this = $(this);
        $this.prop('disabled', 'disabled');
        if ($this.data('status') == 0) {
            VHPublisher.startPush({
                width: 200,
                height: 200,
                success: function () {
                    var n = 0;
                    timer = setInterval(function () {
                        n++;
                        var m = parseInt(n / 60);
                        var s = parseInt(n % 60);
                        $('.play-time').text(toDub(m) + ":" + toDub(s));
                    }, 1000);
                    updateLiveStatus("start");

                    $this.text('完成直播');
                    $this.data('status', 1);
                    console.log("推流成功");
                },
                fail: function () {
                    console.log("推流失败");
                }
            });
        } else {
            VHPublisher.stopPush({
                complete: function () {
                    $this.text('开始直播');
                    $this.data('status', 0);
                    clearInterval(timer);
                    $('.play-time').text("00:00");
                    updateLiveStatus("stop");
                }
            });
        }
        $this.prop('disabled', '');
    });

    function reloadDoc() {
        window.doc.loadDoc(docId, channelId, function (docId) {
            $('#J_doc_main').css("background", "none");
            $('.J-close-doc').trigger("click");
            console.info('文档加载成功！文档id为：', docId);
        }, function (reason) {
            console.error(reason);
        });
    }

    $('.file-list').on('click', '.J-doc-operation', function () {
        docId = $(this).parent().data('did');
        console.log(docId);
        if (!window.doc) {
            window.doc = new VhallDoc({
                roomId: roomId,
                channelId: channelId, //频道Id
                docId: docId,//jpg big
                docNode: 'J_doc_main',//文档显示节点div id
                width: $('#J_doc_main').width(),
                height: $('#J_doc_main').height()
            });
            window.doc.cancelPencil(false);
        }
        console.log("channelId:" + channelId);
        reloadDoc();
    });

    $('.file-list').on('click', '.J-doc-delete', function () {
        var $parent = $(this).parent();
        var docId = $parent.data('did');
        $.ajax({
            method: "DELETE",
            url: "/vhallyun/document/" + docId,
            success: function (resp) {
                $parent.remove();
            }
        })
    });

    //转码定时调用
    var transOverTimer = setInterval(function () {
        $.ajax({
            method: "GET",
            url: "/vhallyun/document",
            success: function (resp) {
                console.log(resp);
                var docs = resp.resultObject;
                for (var i = 0; i < docs.length; i++) {
                    var documentId = docs[i].documentId;
                    var status = docs[i].transStatus;
                    if (status != 0) {
                        var $docItem = $('.J-doc-item-text-' + documentId);
                        if (status === 1) {
                            if ($docItem.text() === "转换中") {
                                $docItem.text("转化成功");
                                console.log("xxxxxx");
                                $docItem.after('<div class="doc-operation text-center J-doc-operation J-operation-' + documentId + '">\n' +
                                    '                        <p>演示</p>\n' +
                                    '                    </div>');
                            }
                        } else if (status === 2) {
                            $docItem.text("转化成功");
                        }
                    }
                }
            }
        })
    }, 30 * 1000);

    $('#J_message_send').on('click', function () {
        var message = $('#J_message_text').val();
        var content = {
            type: 10,
            message: {
                content: message,   //发送的内容
                headImg: headImg,       //发送的头像
                username: nickname,     //发送的用户名
                role: "host"           //发送人的角色    主播： host   普通用户： normal
            }
        };
        $('#J_message_text').val('');
        $.ajax({
            method: "POST",
            url: "/vhallyun/message",
            data: {'body': JSON.stringify(content), 'channelId': channelId},
            success: function (resp) {
                console.log("发送成功");
            }
        })
    });
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
    window.doc = {};

    // cameras = window.Vhall.devices.cameras;
    // mics = window.Vhall.devices.mics;
    // console.log(cameras);
    // console.log(mics);
    // initDevices(cameras, mics);
    function init() {
        window.Vhall.ready(function () {
            VHPublisher.init({
                roomId: roomId,
                videoNode: 'J_video_main',
                complete: function () {
                    console.log("初始化成功!")
                }
            });
            var width = $('#J_doc_main').width();
            var height = $('#J_doc_main').height();
            console.log(width);
            console.log(height);
            window.doc = new VhallDoc({
                roomId: roomId,
                channelId: channelId, //频道Id
                docId: '',//jpg big
                docNode: 'J_doc_main',//文档显示节点div id
                width: width,
                height: height
            });
            window.doc.cancelPencil(false);
            window.chat = new VhallChat({
                channelId: channelId //频道Id
            });
            window.chat.onCustomMsg(function (msg) {
                msg = JSON.parse(msg);
                console.log(msg);
                renderMsg(msg)
            });

            window.chat.join(function (msg) {
                console.log(msg);
                console.log("进入直播间");
                viewJoinleaveRoomInfo(msg, "join");
                //TODO 判断是否被禁言
            });
            window.chat.leave(function (msg) {
                console.log(msg);
                console.log("离开直播间");
                viewJoinleaveRoomInfo(msg, "leave");
            });
        });
        window.Vhall.config({
            appId: appId,//应用 ID ,必填
            accountId: accountId,//第三方用户唯一标识,必填
            token: token,//token必填
            channelId: channelId
        });
    }

    init();

    function initDevices(cameras, mics) {
        for(var i = 0; i < cameras.length; i++) {
            $('.J-cameras').append('<option value="' + cameras[i] + '">' + cameras[i] + '</option>');
        }
        for(var i = 0; i < mics.length; i++) {
            $('.J-mics').append('<option value="' + mics[i] + '">' + mics[i] + '</option>');
        }
    }

    function stopPlay(simple) {

    }

    function startPlay() {

    }

    $('#J_play').on('click', function () {
        var $this = $(this);
        $this.prop('disabled', 'disabled');
        if ($this.data('status') == 0) {
            VHPublisher.startPush({
                width: 200,
                height: 200,
                success: function () {
                    var n = 0;
                    timer = setInterval(function () {
                        n++;
                        var m = parseInt(n / 60);
                        var s = parseInt(n % 60);
                        $('.play-time').text(toDub(m) + ":" + toDub(s));
                    }, 1000);
                    updateLiveStatus("start");

                    $this.text('结束直播');
                    $this.data('status', 1);
                    console.log("推流成功");
                },
                fail: function () {
                    console.log("推流失败");
                }
            });
        } else {
            VHPublisher.stopPush({
                complete: function () {
                    $this.text('开始直播');
                    $this.data('status', 0);
                    clearInterval(timer);
                    $('.play-time').text("00:00");
                    updateLiveStatus("stop");
                }
            });
        }
        $this.prop('disabled', '');
    });

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
                if (content) {
                    content.replace(emojiReg, function(a, b) {
                        var imgUrl = emojiMap[a];
                        if (imgUrl) {
                            a = '<img src="' + imgUrl + '">';
                        }
                        return a;
                    })
                }
                if (msg.message.role == 'host') {
                    html = '<li>\n' +
                        '                            <span class="chat-status">主播</span>\n' +
                        '                            <span class="chat-name">' + msg.message.username + ':</span>\n' +
                        '                            <span class="chat-content">' + content + '</span>\n' +
                        '                        </li>';
                } else
                    html = '<li>\n' +
                        '                            <span class="chat-name">' + msg.message.username + ':</span>\n' +
                        '                            <span class="chat-content">' + content + '</span>\n' +
                        '                        </li>';
                }
                $('#J_message_list').append(html);
            }
        $('.chat-personal').scrollTop($('.chat-personal')[0].scrollHeight);
    }

    $.ajax({
        method: 'GET',
        url: '/vhallyun/message',
        data: {'channel_id': channelId, 'start_time': '2018/01/01', 'limit': '1000'},
        success: function (resp) {
            if (resp.resultObject && resp.resultObject.data) {
                var result = resp.resultObject.data;
                for (var i = result.length - 1; i >= 0; i--) {
                    try {
                        renderMsg(JSON.parse(JSON.parse(result[i].data)));
                    } catch (e) {
                    }
                }
            }
        }
    });

    function viewJoinleaveRoomInfo(msg, action) {
        var html = '<li>\n' +
            '                            <span class="chat-name">' + msg.nick_name + ':</span>\n' +
            '                            <span class="chat-content">' + (action === 'join' ? '进入直播间' : '退出') + '</span>\n' +
            '                        </li>';
        $('#J_message_list').append(html);
        $('.chat-personal').scrollTop($('.chat-personal')[0].scrollHeight);
    }

    function renderStudentList() {
        $.ajax({
            method: 'GET',
            url: '/vhallyun/roomJoinStudent',
            data: {'roomId' : roomId, 'channelId' : channelId, "anchorId" : accountId},
            success: function(resp) {
                var data = resp.resultObject;
                for(var i = 0; i < data.length; i++) {
                    console.log(data[i]);
                }
                // $('.student-list').append('<li>\n' +
                //     '                            <div class="head-portrait z">\n' +
                //     '                                <img src="' + msg.avatar + '" alt="头像"/>\n' +
                //     '                            </div>\n' +
                //     '                            <span class="student-name z">我是超人</span>\n' +
                //     '                        </li>')
            }
        });
    }

    renderStudentList();

    function setBanStatus(accountId, status) {
        $.ajax({
            method: 'POST',
            url: 'ban/' + channelId + '/' + accountId + '/' + status,
            success: function (resp) {
                if (status === 1) {//禁言
                    //TODO
                } else {
                    //TODO
                }
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
        console.log("event:" + event);
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

    function reloadDoc() {
        window.doc.loadDoc(docId, channelId, function (docId) {
            $('.video-main').css("background", "none");
            $('.J-close-doc').trigger("click");
            getThumImgList();
        }, function (reason) {
            console.error(reason);
        });
    }

    function getThumImgList() {
        window.doc.getThumImgList(function(list) {
            console.log(list);
        });
    }

    $('.J-doc-prev').on('click', function () {
        if (window.doc && curPage > 1) {
            console.log(window.doc.preSlide());
            curPage = curPage - 1;
            setPage(page, curPage);
        }
    });
    $('.J-doc-next').on('click', function () {
        if (window.doc && curPage < page) {
            console.log(window.doc.nextSlide());
            curPage = curPage + 1;
            setPage(page, curPage);
        }
    });

    function setPage(page, curPage) {
        $('.pages').removeClass("hide");
        if (curPage <= 1) {
            curPage = 1;
        }
        $('.now-page').text(curPage);
        $('.all-pages').text(page);
    }

    $('.file-list').on('click', '.J-doc-operation', function () {
        docId = $(this).parent().data('did');
        page = $(this).parent().data('page');
        if (!page) {
            page = 1;
        }
        curPage = 1;
        console.log(docId);
        console.log("channelId:" + channelId);
        reloadDoc();
        setPage(page, curPage);
    });

    $('.file-list').on('click', '.J-doc-delete', function () {
        var $parent = $(this).parent();
        var docId = $parent.data('did');
        $.ajax({
            method: "DELETE",
            url: "/vhallyun/document/" + docId,
            success: function (resp) {
                $parent.remove();
            }
        })
    });

    //转码定时调用
    var transOverTimer = setInterval(function () {
        $.ajax({
            method: "GET",
            url: "/vhallyun/document",
            success: function (resp) {
                console.log(resp);
                var docs = resp.resultObject;
                for (var i = 0; i < docs.length; i++) {
                    var documentId = docs[i].documentId;
                    var status = docs[i].transStatus;
                    if (status != 0) {
                        var $docItem = $('.J-doc-item-text-' + documentId);
                        if (status === 1) {
                            if ($docItem.text() === "转换中") {
                                $docItem.text("转化成功");
                                console.log("xxxxxx");
                                $docItem.after('<div class="doc-operation text-center J-doc-operation J-operation-' + documentId + '">\n' +
                                    '                        <p>演示</p>\n' +
                                    '                    </div>');
                            }
                        } else if (status === 2) {
                            $docItem.text("转化成功");
                        }
                    }
                }
            }
        })
    }, 30 * 1000);

    $('#J_message_send').on('click', function () {
        var message = $('#J_message_text').val();
        var content = {
            type: 10,
            message: {
                content: message,   //发送的内容
                headImg: headImg,       //发送的头像
                username: nickname,     //发送的用户名
                role: "host"           //发送人的角色    主播： host   普通用户： normal
            }
        };
        $('#J_message_text').val('');
        $.ajax({
            method: "POST",
            url: "/vhallyun/message",
            data: {'body': JSON.stringify(content), 'channelId': channelId},
            success: function (resp) {
                console.log("发送成功");
            }
        })
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
        if (window.doc) {
            // window.doc.reSizeBorad($('#J_doc_main').width(), $('#J_doc_main').height());
        }
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
//  hover删除按钮显现
    $(".hover-delect").hover(function () {
        $(".hover-delect .delect-img").addClass("hide");
        $(this).find(".delect-img").removeClass("hide")
    }, function () {
        $(".hover-delect .delect-img").addClass("hide");
    })
//  点击删除
//     $(".hover-delect .delect-img").click(function () {
//         $(this).parent().remove();
//     })
//点击上传文件的URL

//------------------------------------------文档左侧列表点击效果----------------------------------------------------------------	
    $(".icon-right").click(function () {
        $(this).parent(".select-document-wrap").addClass("select-left");
        $(".video-main .icon-left").removeClass("hide");
    })
    $(".icon-left").click(function () {
        $(this).siblings(".select-document-wrap").removeClass("select-left");
        $(".video-main .icon-left").addClass("hide");
    })
    $(".modal-list li").each(function () {
        var index = $(this).index();
        var num = index + 1;
        $(this).find("span").html(num)
    })
    $(".modal-list li").click(function () {
        $(".modal-list li").removeClass("active");
        $(this).addClass("active")
    })

//------------------------------------------设置----------------------------------------------------------------	

    $(".select-operation .set-up").click(function () {
        $(".background-ask").removeClass("hide");
        $(".setup-modal").removeClass("hide");
    })

$(".comment-setup .cancel").click(function(){
	$(".background-ask").addClass("hide");
	$(".setup-modal").addClass("hide");
})
 //输入框正在输入时 
// 	宽
    $('.comment-setup .width-my').on('blur',function(){
    	var that=$(this);
      if((that.val()<120)){
      	$('.comment-setup .control-size').html("最小值为120X120");
      	$('.comment-setup .control-size').removeClass("hide");	
     
        that.val("120")
      }else if(that.val()>1920){
      	$('.comment-setup .control-size').html("最大值为1920X1080");
      	$('.comment-setup .control-size').removeClass("hide");	
      
        that.val("1920")
      }else{
      	$('.comment-setup .control-size').addClass("hide");	
      }
    });
//  高
  	$('.comment-setup .height-my').on('blur',function(){
    	var that=$(this);
      if((that.val()<120)){
      	$('.comment-setup .control-size').html("最小值为120X120");
      	$('.comment-setup .control-size').removeClass("hide");	
      
        that.val("120")
      }else if(that.val()>1080){
        $('.comment-setup .control-size').html("最大值为1920X1080");     
        $('.comment-setup .control-size').removeClass("hide");	
     
        that.val("1080")
      }else{
      	$('.comment-setup .control-size').addClass("hide");	
      }
    });
//表情设置
$(".expression-img").click(function(){
	$(".expression-select").removeClass("hide");
	$.ajax({
		type:"get",
		url:"/web/js/live-room/emoticon.json",
		success:function(data){
			var del="";
			for(var i=0;i<data.length;i++){
				del+='<li><img src='+data[i].imgUrl+'></li>'
			}
			$(".expression-list").html(del)	
		}
	});
})

//选择禁言
$(".student-list .select-ban").click(function(){
	var that=$(this).find("img")
	if(that.attr("src")=="/web/images/live-room/say-icon.png"){
		that.attr({"src":"/web/images/live-room/say-ban.png","title":"取消禁言"});
	}else{
		that.attr({"src":"/web/images/live-room/say-icon.png","title":"禁言"});
	}
})
    $(".comment-setup .cancel").click(function () {
        $(".background-ask").addClass("hide");
        $(".setup-modal").addClass("hide");
    })

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

    $('.expression-list').on('click', '.J-emoji', function(){
        var $messageText = $('#J_message_text');
        $(".expression-select").addClass('hide');
        $messageText.val($messageText.val() + $(this).data('text'));
        $messageText.focus();
    });


//------------------------------------------文档转换----------------------------------------------------------------
    var fileUrl;
    $('#file-input').change(function () {
        $('#submitFile').ajaxSubmit({
            type: 'post',
            dataType: 'json',
            success: function (result) {
                //请求成功后的操作
                var obj = result.resultObject;
                var liHtml = ' <li class="doc-title hover-delect" data-did ="' + obj.documentId + '">\n' +
                    '                <div class="doc-name doc-photo">' + obj.filename + '</div>\n' +
                    '                <div class="doc-time text-center">' + obj.createTime + '</div>\n' +
                    '                <div class="doc-progress text-center">转换中</div>\n' +
                    '                <div class="delect-img hide"></div>\n' +
                    '            </li>';
                $('.J-doc-title').after(liHtml);
                $('#file-input').val('');
            },
            error: function () {
                $('#file-input').val('');
            }
        });
    })
});
