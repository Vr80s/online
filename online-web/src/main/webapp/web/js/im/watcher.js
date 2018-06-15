define("embed/watcher", [ "init", "jquery-json", "embed/active", "chat/chat", "chat/question", "chat/private", "webinar/groupDom", "popup", "webinar/userlist", "socket/live_guest", "flashInit" ], function(require, exports, module) {
    require("init");
    require("jquery-json");
    require("embed/active");
    var chat = require("chat/chat");
    var question = require("chat/question");
    var private = require("chat/private");
    var groupDom = require("webinar/groupDom");
    var popup = require("popup");
    var userlist = require("webinar/userlist");
    require("socket/live_guest");
    var flashInit = require("flashInit");
    var hasBack = false;
    function preventScroll(id) {
        var _this = id;
        if (navigator.userAgent.indexOf("Firefox") > 0) {
            _this.addEventListener("DOMMouseScroll", function(e) {
                _this.scrollTop += e.detail > 0 ? 60 : -60;
                e.preventDefault();
            }, false);
        } else {
            _this.onmousewheel = function(e) {
                e = e || window.event;
                _this.scrollTop += e.wheelDelta > 0 ? -60 : 60;
                return false;
            };
        }
        return this;
    }
    $(".chartlist")[0] && preventScroll($(".chartlist")[0]);
    $(".chartlist").mCustomScrollbar({
        scrollInertia: 200,
        callbacks: {
            onTotalScrollBack: function() {
                if ($(".type-list a[msg-type='5']").hasClass("active")) {
                    var curr_page = $(".userlist.js-user").data("curr_page") ? $(".userlist.js-user").data("curr_page") : 1;
                    if (curr_page > 1) {
                        curr_page = curr_page - 1;
                        userlist.getUsers(curr_page, 1);
                    }
                }
            },
            onTotalScroll: function() {
                if ($(".type-list a[msg-type='5']").hasClass("active")) {
                    var curr_page = $(".userlist.js-user").data("curr_page") ? $(".userlist.js-user").data("curr_page") : 1;
                    curr_page = curr_page + 1;
                    getUsers(curr_page);
                }
            },
            onScroll: function(e) {
                if (!$(".chatmsg").hasClass("hide")) {
                    var container_height = $(this).height(), mCSB_dragger_bar_height = $(this).find(".mCSB_dragger_bar").height(), mcs_dragHeight = this.mcs.draggerTop;
                    if (container_height - mcs_dragHeight - mCSB_dragger_bar_height > 50) {
                        $(".chartlist").addClass("no-scroll");
                    } else {
                        $(".chartlist").removeClass("no-scroll");
                    }
                } else {
                    $(".chartlist").removeClass("no-scroll");
                }
            }
        }
    });
    if (pageinfo.is_chat) {
        $.ajax({
            url: "/api/webinar/v1/webinar/historymsg",
            type: "POST",
            data: {
                webinar_id: pageinfo.webinar_id
            },
            success: function(res) {
                hasBack = true;
                if (parseInt(res.code) == 200) {
                    var html = "";
                    for (var i = res.data.length - 1; i >= 0; i--) {
                        var data = res.data[i];
                        try {
                            data.data = JSON.parse(data.content);
                        } catch (e) {
                            data.data = data.content;
                            data.avater = "";
                        }
                        data.role = data.user_role;
                        data.app = "";
                        if (data.event == "msg") {
                            html += groupDom.chatLi(data);
                        } else {
                            html += groupDom.chatList(data);
                        }
                    }
                    $(".chatmsg").append(html);
                    setTimeout(function() {
                        $(".chartlist").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "99999");
                    }, 50);
                }
                if ($(".chatmsg").data("welcome")) {
                    $(".chatmsg").append($(".chatmsg").data("welcome"));
                }
            }
        });
    }
    if (pageinfo.is_question && pageinfo.role == "user") {
        $.dataRequest("/api/webinar/v1/webinar/qahistory", {
            webinar_id: pageinfo.webinar_id,
            join_id: pageinfo.join_uid
        }, function(res) {
            if (res.code == 200) {
                var html = "";
                for (var i = 0; i < res.data.length; i++) {
                    var msg = {
                        data: res.data[i]
                    };
                    html += groupDom.questionLi(msg);
                }
                $(".jscontent.qa").append(html);
            }
        });
    }
    $.dataRequest("/api/webinar/v1/webinar/getannouncement", {
        webinar_id: pageinfo.webinar_id,
        curr_page: 1
    }, function(res) {
        if (res.code == "200") {
            var data = res.data.data, html = "";
            if (data.length > 0) {
                for (var i = 0, len = data.length; i < len; i++) {
                    var content = {
                        content: ""
                    };
                    content = JSON.parse(data[i].content);
                    content.content = $.html_encode(content.content);
                    if (pageinfo.matchTheLink == "1") {
                        content.content = $.replace_link(content.content);
                    }
                    html += '<li>                                <p class="time">' + data[i].created_at + '</p>                                <p class="content">' + content.content + "</p>                            </li>";
                }
                $.messagetips(4);
                $(".announcement").append(html);
            }
        }
    });
    $(".userlist").on("click", ".gag", function() {
        var _type = $(this).hasClass("is-gag") ? 2 : 1, _uid = $(this).closest("li").attr("uid");
        $.dataRequest("/api/webinar/v1/webinar/bantochat", {
            user_id: _uid,
            type: _type
        }, function(res) {
            if (res.code == 200) {
                if (parseInt(res.data.is_gag)) {
                    pageinfo.is_gaglist.push(res.data.id);
                } else {
                    pageinfo.is_gaglist.splice($.inArray(res.data.id, pageinfo.is_gaglist), 1);
                }
            }
        });
    });
    $(".userlist").on("click", ".kickout", function() {
        var _uid = $(this).closest("li").attr("uid");
        $.dataRequest("/api/webinar/v1/webinar/kick", {
            user_id: _uid
        }, function(res) {});
    });
    $("#jssend").click(function() {
        if ($(this).hasClass("disabled")) {
            return false;
        }
        if ($(".type-list a[msg-type='1']").hasClass("active")) {
            chat.sendChat($("#mywords"));
        } else if ($(".type-list a[msg-type='2']").hasClass("active")) {
            question.sendQa($("#mywords"));
        } else if ($(".type-list a[msg-type='3']").hasClass("active")) {
            if ($(".private").attr("sendFlag") == "true") {
                if ($(".private").attr("sendto") == "") {
                    alert("请选择要私聊的对象");
                    return false;
                }
                if ($("#mywords").val().replace($(".private").attr("rubishstr"), "") == "") {
                    $("#mywords").val("不能发送空白信息");
                    $(".private").attr("sendFlag", "false");
                    setTimeout(function() {
                        $("#mywords").val("");
                        $(".private").attr("sendFlag", "true");
                        $("#mywords").val($(".private").attr("rubishstr"));
                    }, 3e3);
                    return false;
                }
//                private.sendPrivate({
//                    user_id: pageinfo.join_uid,
//                    user_name: $(".private").attr("sendtoname"),
//                    data: {
//                        text: $("#mywords").val().replace($(".private").attr("rubishstr"), "")
//                    },
//                    to: $(".private").attr("sendto")
//                });
            }
        }
    });
    function stopEvent(event) {
        if (event.stopPropagation) event.stopPropagation(); else if (window.event) window.event.cancelBubble = true;
    }
    $(".send-box-btn .set").click(function(event) {
        stopEvent(event);
        $(".send-box-btn .set-box").toggleClass("hide");
    });
    $(".send-box-btn .set-box a").click(function(event) {
        if (!$(this).hasClass("handlink")) {
            $(this).toggleClass("check");
            $.ajax({
                url: $(this).attr("api-url"),
                type: "post",
                data: {
                    webinar_id: pageinfo.webinar_id,
                    join_id: pageinfo.join_uid,
                    type: $(this).hasClass("check") ? 1 : 0
                },
                success: function(res) {},
                error: function() {}
            });
            $(".send-box-btn .set-box").toggleClass("hide");
        } else {
            if (!$(this).hasClass("open")) {
                popup.msg({
                    content: "主持人还未开启问答",
                    shade: 0
                });
                return false;
            }
        }
    });
    $(".qa-only-someone").click(function() {
        $(this).toggleClass("on");
        if ($(this).hasClass("on")) {
            $(".jscontent.qa li[question-id]:not([uid='" + pageinfo.join_uid + "'])").hide();
        } else {
            $(".jscontent.qa li[question-id]:not([uid='" + pageinfo.join_uid + "'])").show();
        }
    });
    $(".new-chat-msg").click(function() {
        $(".chartlist").mCustomScrollbar("scrollTo", "99999");
    });
    if (pageinfo.welcome_content != "") {
        var joinName = pageinfo.join_uname;
        if (joinName.length > 15) {
            joinName = joinName.substr(0, 15) + "...";
        }
        if (hasBack) {
            $(".jscontent.chatmsg").append('<li><div class="wel-msg"><span>' + joinName + "</span>" + pageinfo.welcome_content + "</div></li>");
        } else {
            $(".jscontent.chatmsg").data("welcome", '<li><div class="wel-msg"><span>' + joinName + "</span>" + pageinfo.welcome_content + "</div></li>");
        }
    }
    var onOff = true, isFilter = true;
    var timer;
    $(".file-btn").on("click", function() {
        var length = $(".img-box").find(".img-section").length;
        if (length < 4 && onOff) {
            $("#file-hide").click();
        } else if (length >= 4) {
            notice("一次只能上传4张图片");
        } else if (!onOff) {
            notice("图片正在上传，请稍等");
        }
    });
    $("#file-hide").fileupload({
        url: "/api/webinar/v1/webinar/chatpic-upload",
        dataType: "json",
        formData: {
            join_uid: pageinfo.join_uid,
            webinar_id: pageinfo.webinar_id
        },
        start: function(res) {
            onOff = false;
            $("#mywords").attr("isOn", onOff);
        },
        success: function(res) {
            if (res.code == 200) {
                var _img = $("<div class='img-section success-img'><img src='" + pageinfo.upload_url + "/" + res.file_src + "?x-oss-process=image/resize,m_lfit,h_34,w_34' data-url='" + res.file_src + "'><a class='img-close'></a></div>");
                $(".img-box").append(_img).show();
                $(".file-btn").addClass("file-yes");
            } else if (res.code == 505) {
                notice("图片涉及敏感内容，请尝试其他图片");
            } else if (res.code == 504) {
                notice("上传图片不能超过5MB");
            } else if (res.code == 503) {
                notice("图片限于bmp,png,jpeg,jpg格式");
            } else if (res.code == 506) {
                notice("上传失败，请检查网络");
            }
            onOff = true;
            $("#mywords").attr("isOn", onOff);
        },
        submit: function(e, data) {
            if (data.originalFiles[0]["size"].length != 0 && data.originalFiles[0]["size"] > 5 * 1024 * 1024) {
                notice("上传图片不能超过5MB");
                onOff = true;
                $("#mywords").attr("isOn", onOff);
                return false;
            }
        }
    });
    $(".img-box").on("click", ".img-close", function(e) {
        e.stopPropagation();
        $(this).closest(".img-section").remove();
        var length = $(".img-box").find(".img-section").length;
        if (length == 0) {
            $(".img-box").hide();
            $(".file-btn").removeClass("file-yes");
        }
    });
    $(".img-box").on("click", function(e) {
        e.stopPropagation();
    });
    var mySwiper;
    $(".chatmsg").on("click", ".news-img", function() {
        var index = $(this).data("index");
        if ($(".swiper-bigImg .swiper-slide").length > 0) {
            return;
        }
        for (var i = 0; i < $(".chatmsg .news-img").length; i++) {
            var url = $(".chatmsg .news-img").eq(i).data("url");
            var tpl = $("<div class='swiper-slide'><div class='swiper-zoom-container'><img data-src='" + url + "' class='swiper-lazy'/></div><div class='swiper-lazy-preloader'></div></div>");
            $(".swiper-wrapper").append(tpl);
        }
        $(".swiper-bigImg").show();
        mySwiper = new Swiper(".swiper-bigImg", {
            autoplay: false,
            initialSlide: index,
            loop: false,
            lazyLoading: true,
            prevButton: ".swiper-button-prev",
            nextButton: ".swiper-button-next",
            zoom: true
        });
    });
    $(".swiper-close").on("click", function() {
        $(".swiper-bigImg").hide();
        $(".swiper-wrapper").html("");
        mySwiper.destroy();
    });
    function notice(msg) {
        clearTimeout(timer);
        $(".notice-text").text(msg);
        $(".notice").show();
        timer = setTimeout(function() {
            $(".notice").hide();
        }, 1e3);
    }
    $(".filter-msg").on("click", function() {
        if (isFilter) {
            $(this).addClass("filter-yes").attr("title", "查看全部消息");
            for (var i = 0; i < $(".chatmsg li").length; i++) {
                var isRole = $(".chatmsg li").eq(i).data("role");
                if (isRole == "no") {
                    $(".chatmsg li").eq(i).addClass("hide");
                }
            }
            $(".chartlist").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "99999");
            isFilter = false;
        } else {
            $(this).removeClass("filter-yes").attr("title", "只看主办方消息");
            $(".chatmsg li").removeClass("hide");
            $(".chartlist").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "99999");
            isFilter = true;
        }
    });
});