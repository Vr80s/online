define("embed/active", [ "mCustomScrollbar", "popup", "init", "utils/validator", "utils/formEffect", "webinar/groupDom", "webinar/login", "flashInit", "qqFace" ], function(require, exports, module) {
    require("mCustomScrollbar");
    var popup = require("popup");
    require("init");
    var validator = require("utils/validator");
    var formEffect = require("utils/formEffect");
    var groupDom = require("webinar/groupDom");
    var toLogin = require("webinar/login");
    var flashInit = require("flashInit");
    $(window).resize(function() {
        if (!$("body").attr("once")) {
            var _height = 0;
            var type = $(".type-list li a.active").attr("msg-type");
            if (type == "1" || type == "2") {
                _height += $(".send-box").outerHeight();
            }
            $(".chartlist").css({
                height: $(".play-box-right").height() - $(".flash-box").outerHeight() - $(".type-box").outerHeight() - _height
            });
        }
        $("body").removeAttr("once");
    });
    $("body").attr("once", 1);
    $(window).trigger("resize");
    $(document).on("click", function(event) {
        if (!$(".send-box-btn .set-box").hasClass("hide")) $(".send-box-btn .set-box").addClass("hide");
    });
    $(".chatmsg").on("click", "a.avatar,a.name", function() {
        var nick_name = $(this).attr("title");
        if (nick_name) {
            var mywords = $("#mywords").val();
            if (nick_name == pageinfo.join_uname) {
                return alert("不能@自己！");
            }
            if (mywords.indexOf("@" + nick_name + ":") >= 0) {
                return alert("您已@过该用户！");
            }
            $("#mywords").val(mywords + "@" + nick_name + ":");
        }
    });
    function stopEvent(event) {
        if (event.stopPropagation) event.stopPropagation(); else if (window.event) window.event.cancelBubble = true;
    }
    $(".type-list ul li a").click(function() {
        if (!$(this).hasClass("active")) {
            var _last_type = parseInt($(".type-list ul li a.active").attr("msg-type")), _this_type = parseInt($(this).attr("msg-type")), _height = 0;
            $(".type-list ul li a").removeClass("active");
            $(this).addClass("active");
            $(".jscontent").addClass("hide");
            $(this).siblings(".new").addClass("hide");
            if (pageinfo.role !== "user") {
                $(".qa-assistant").addClass("hide");
            } else {
                $(".qa-only-someone").addClass("hide");
            }
            if (_last_type < 4 && _this_type >= 4 || typeof is_playback != "undefined" && _this_type == 2 && _last_type != 2) {
                _height = 83;
            } else if (_last_type >= 4 && _this_type < 4 || !_last_type) {
                _height = -83;
            }
            $(".new-chat-msg").hide();
            $(".chartlist").height($(".chartlist").height() + _height);
            switch ($(this).attr("msg-type")) {
              case "1":
                $(".chatmsg").toggleClass("hide");
                $(".send-box").removeClass("hide");
                $("#mywords").val("");
                setTimeout(function() {
                    $(".chartlist").mCustomScrollbar("scrollTo", "99999");
                }, 50);
                break;

              case "2":
                $(".qa").toggleClass("hide");
                if (pageinfo.role !== "user") {
                    $(".qa-assistant").removeClass("hide");
                    $(".send-box").addClass("hide");
                } else {
                    $("#mywords").val("");
                    $(".qa-only-someone").removeClass("hide");
                    $(".send-box").removeClass("hide");
                }
                if (typeof is_playback != "undefined") {
                    $(".send-box").addClass("hide");
                }
                setTimeout(function() {
                    $(".chartlist").mCustomScrollbar("scrollTo", "99999");
                }, 50);
                break;

              case "3":
                $(".private").toggleClass("hide");
                $(".send-box").removeClass("hide");
                $("#mywords").val("");
                setTimeout(function() {
                    $(".chartlist").mCustomScrollbar("scrollTo", "99999");
                }, 50);
                break;

              case "4":
                $(".announcement").toggleClass("hide");
                $(".send-box").addClass("hide");
                break;

              case "5":
                $(".userlist").toggleClass("hide");
                $(".send-box").addClass("hide");
                break;

              default:
                console.log("default");
                break;
            }
        }
    });
    $(".chartlist").height($(".play-box-right").height() - $(".type-box").outerHeight() - $(".flash-box").outerHeight() - $(".send-box:not('.hide')").outerHeight());
    $(".send-box").removeClass("hide");
    $(".type-list li:not('.hide')").eq(0).find("a").click();
    $(".flash-box-btn .move").click(function() {
        var _self = $(this);
        if ($(".flash-box-btn").attr("watchDisplay") == "1") {
            if ($(".flash-box .close").hasClass("on")) {
                flashInit.exchange();
            } else {
                flashInit.exchange(1);
                if (_self.attr("clicked") == 1) {
                    $(".doc-box").css("height", "100%");
                    $(".flash-box").css("height", "auto");
                    _self.attr("clicked", 0);
                } else {
                    $(".doc-box").css("height", "168px");
                    $(".flash-box").css("height", "auto");
                    _self.attr("clicked", 1);
                }
            }
        } else {
            if ($(".flash-box .close").hasClass("on")) {
                flashInit.exchange();
            } else {
                flashInit.exchange(1);
            }
        }
    });
    $(".flash-box .close").click(function() {
        var _height = 24;
        if ($(this).hasClass("on")) {
            flashInit.exchangeHideFlash(1);
            _height = $(".flash-video.exchange")[0] ? $(".flash-video .doc-box").outerHeight() : $(".flash-video .video-box").outerHeight();
        } else {
            flashInit.exchangeHideFlash();
        }
        var type = $(".type-list li a.active").attr("msg-type");
        if (type == "1" || type == "2") {
            _height += $(".send-box").outerHeight();
        }
        $(".chartlist").animate({
            height: $(".play-box-right").height() - _height - $(".type-box").outerHeight()
        }, 300);
        $(".flash-box .flash").slideToggle();
        $(".flash-box .close").toggleClass("on");
    });
    var qqFace = require("qqFace");
    if ($("#mywords")[0]) {
        qqFace.qqFace({
            assign: "mywords",
            htmlDom: $(".face")
        });
    }
});