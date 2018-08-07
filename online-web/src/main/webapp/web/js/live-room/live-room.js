
$(function () {
    var roomId = $('#J_roomId').val();
    var channelId = $('#J_channelId').val();
    var appId = $('#J_appId').val();
    var accountId = $('#J_accountId').val();
    var token = $('#J_token').val();
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
    //聊天初始化
    window.chat = new VhallChat({
        channelId: channelId
    });
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
            data: {"roomId" : roomId, "event" : event},
            async: false,
            success: function(resp) {
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
                headImg: "xxx",       //发送的头像
                username: "hahah",     //发送的用户名
                role: "host"           //发送人的角色    主播： host   普通用户： normal
            }
        };
        $('#J_message_text').val('');
        $.ajax({
            method: "POST",
            url: "/vhallyun/message",
            data: {'body': JSON.stringify(message), 'channelId': channelId},
            success: function (resp) {
                console.log("发送成功");
            }
        })
    });

    window.chat.on(function (msg) {
        console.log(msg);

        // $('#J_message_list').append(' <li>\n' +
        //     '                            <span class="chat-name">' + 明天会更好 + ':</span>\n' +
        //     '                            <span class="chat-content">这是评这是评论的内容这是评论的内容论的内容</span>\n' +
        //     '                        </li>');
    });


//------------------------------------------静态页面效果----------------------------------------------------------------

    function getWhiteHeight() {
        var videoHeight = $(document.body).height() - 219;
        var studentHeight = $(document.body).height() - 262;
        var chatHeight = $(document.body).height() - 152;
        
        
        //	文档高度
		$(".video-main").css({"height":videoHeight});
	//	文档左侧文件列表高度
		$(".select-document-wrap").css({"height":videoHeight});
	//	学员列表高度
		$(".student-list").css({"height":studentHeight});
	//	聊天区域
		$(".chat-personal").css({"height":chatHeight});	
    }

    getWhiteHeight();
    $(window).resize(function () {
        getWhiteHeight();
        if (window.doc) {
            window.doc.reSizeBorad($('#J_doc_main').width(), $('#J_doc_main').height());
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
$(".icon-right").click(function(){
	$(this).parent(".select-document-wrap").addClass("select-left");
	$(".video-main .icon-left").removeClass("hide");
})
$(".icon-left").click(function(){
	$(this).siblings(".select-document-wrap").removeClass("select-left");
	$(".video-main .icon-left").addClass("hide");
})
$(".modal-list li").each(function(){
	var index=$(this).index();
	var num=index+1;
	$(this).find("span").html(num)
})
$(".modal-list li").click(function(){
	$(".modal-list li").removeClass("active");
	$(this).addClass("active")
})

//------------------------------------------设置----------------------------------------------------------------	

$(".select-operation .set-up").click(function(){
	$(".background-ask").removeClass("hide");
	$(".setup-modal").removeClass("hide");
})

$(".comment-setup .cancel").click(function(){
	$(".background-ask").addClass("hide");
	$(".setup-modal").addClass("hide");
})







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
                    '                <div class="doc-progress text-center">等待转换</div>\n' +
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
