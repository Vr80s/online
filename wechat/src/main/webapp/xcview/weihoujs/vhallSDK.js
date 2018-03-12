/*!
 *  @copyright (c) 2016
 *  @author: wenfeng.lei
 *  @update: Mon Aug 07 2017 17:37:59 GMT+0800 (中国标准时间)
 */
!
function(e) {
    function t(i) {
        if (n[i]) return n[i].exports;
        var r = n[i] = {
            exports: {},
            id: i,
            loaded: !1
        };
        return e[i].call(r.exports, r, r.exports, t),
        r.loaded = !0,
        r.exports
    }
    var n = {};
    return t.m = e,
    t.c = n,
    t.p = "/",
    t(0)
} ([function(e, t, n) {
    e.exports = n(25)
},
function(e, t, n) {
    var i = n(9),
    r = {};
    e.exports = {
        create: function(e) {
            i(e) && (r = e)
        },
        setter: function(e, t) {
            r[e] = t
        },
        getter: function(e) {
            return r[e]
        },
        get: function() {
            return r
        }
    }
},
function(e, t) {
    var n = {},
    i = null,
    r = null,
    o = null;
    i = function(e, t) {
        n[e] || (n[e] = []),
        n[e].push(t)
    },
    r = function() {
        var e = Array.prototype.slice.call(arguments),
        t = Array.prototype.shift.call(e),
        i = n[t];
        if (i && i.length >= 0) for (var r = 0,
        o = i.length; r < o; r++) i[r].apply(null, e)
    },
    o = function(e, t) {
        var i = n[e];
        if (!i) return ! 1;
        if (t) for (var r = i.length - 1; r >= 0; r--) {
            var o = i[r];
            o === t && i.splice(r, 1)
        } else i && (i.length = 0)
    },
    e.exports = {
        listen: i,
        trigger: r,
        remove: o
    }
},
function(e, t, n) {
    var i = n(9),
    r = {};
    e.exports = {
        create: function(e) {
            i(e) && (r = e)
        },
        setter: function(e, t) {
            r[e] = t
        },
        getter: function(e) {
            return r[e]
        },
        get: function() {
            return r
        }
    }
},
function(e, t, n) {
    function i(e) {
        if (e) {
            e = e.replace(/\</g, "&lt;").replace(/\>/g, "&gt;").replace(/\n/g, "<br/>");
            var t = "//cnstatic01.e.vhall.com/static/img/arclist/",
            n = e.match(/\[[^@]{1,3}\]/g);
            if (null !== n) for (var i = 0; i < n.length; i++) {
                var r = o(n[i]);
                r && (e = e.replace(n[i], '<img width="24" src="' + t + "Expression_" + r + '@2x.png" border="0"/>'))
            }
        }
        return e
    }
    n(27);
    var r = n(6),
    o = n(24);
    $.fn.qqFace = function(e) {
        var t = {
            id: "facebox",
            path: "face/",
            assign: "#content"
        },
        n = $.extend(t, e),
        i = n.assign,
        a = n.id,
        s = n.path;
        return i.length <= 0 ? (alert("缺少表情赋值对象。"), !1) : ($(this).click(function(e) {
            var t, i, c;
            if (!$(this).hasClass("disabled")) {
                var d = $(this).parent().find("." + a);
                if (d.length <= 0) if (r) {
                    t = '<div class="' + a + ' facebox-pc mCustomScrollbar"><table border="0" cellspacing="0" cellpadding="0"><tr>';
                    for (var l = 1; l <= 90; l++) i = o(l - 1),
                    t += '<td><img width="24" src="' + s + "Expression_" + l + '@2x.png" onclick="$(\'' + n.assign + "').setCaret();$('" + n.assign + "').insertAtCaret('" + i + "');\" /></td>",
                    l % 9 === 0 && (t += "</tr><tr>");
                    t += "</tr></table></div>",
                    $(this).parent().append(t),
                    c = $(this).position()
                } else {
                    var u = $(window).width(),
                    p = "";
                    t = '<div class="' + a + ' facebox-mobile" style="width:' + u + 'px;"><div class="qqFace-box" style="width:' + 5 * u + 'px">';
                    for (var l = 1; l <= 5; l++) {
                        t += '<div class="qqFace-mobile" style="width:' + u + 'px">';
                        for (var h = 1; h <= 20; h++) {
                            var g = 20 * (l - 1) + h;
                            i = o(g - 1),
                            i && (t += "<li onclick=\"$('" + n.assign + "').setCaret();$('" + n.assign + "').insertAtCaret('" + i + '\');"><img width="24" src="' + s + "Expression_" + g + '@2x.png" /></li>')
                        }
                        t += "<li onclick=\"$('" + n.assign + '\').deleteCaret();" ><img width="24" src="' + s + 'faceDelete@2x.png" /></li></div>',
                        p += 1 === l ? "<a class='active'></a>": "<a></a>"
                    }
                    t += "<div style='clear:both'></div></div><div class='text-center'>" + p + "</div></div>",
                    $(this).parent().append(t),
                    c = $(this).position(),
                    d = $(this).parent().find("." + a),
                    d.data("data", {
                        index: 0
                    });
                    var f, v, m = !1,
                    y = d.find(".qqFace-box").eq(0).get(0);
                    d[0].addEventListener("touchstart",
                    function(e) {
                        f = e.touches[0].pageX
                    },
                    !1),
                    d[0].addEventListener("touchmove",
                    function(e) {
                        e.preventDefault(),
                        v = e.touches[0].pageX - f;
                        var t = $(this).data("data").index * u,
                        n = "translate3d(" + (v - t) + "px, 0, 0)";
                        y.style.webkitTransform = n,
                        y.style.mozTransform = n,
                        y.style.transform = n,
                        m = !0
                    },
                    !1),
                    d[0].addEventListener("touchend",
                    function(e) {
                        if (m) {
                            var t = $(this).data("data").index,
                            n = t * u;
                            v < -50 ? t < 4 && (t += 1, $(this).data("data", {
                                index: t
                            }), n += u) : v > 50 && t >= 1 && (t -= 1, $(this).data("data", {
                                index: t
                            }), n -= u),
                            d.find(".text-center a").removeClass("active").eq(t).addClass("active");
                            var i = "translate3d(-" + n + "px, 0, 0)";
                            y.style.webkitTransform = i,
                            y.style.mozTransform = i,
                            y.style.transform = i
                        }
                        m = !1
                    },
                    !1),
                    d.on("click", "li",
                    function(e) {
                        e.stopPropagation()
                    })
                }
                $(this).parent().find("." + a).toggle(),
                e.stopPropagation()
            }
        }), void $(document).click(function() {
            $("." + a).hide()
        }))
    },
    $.fn.extend({
        selectContents: function() {
            $(this).each(function(e) {
                var t, n, i, r, o = this; (i = o.ownerDocument) && (r = i.defaultView) && "undefined" != typeof r.getSelection && "undefined" != typeof i.createRange && (t = window.getSelection()) && "undefined" != typeof t.removeAllRanges ? (n = i.createRange(), n.selectNode(o), 0 === e && t.removeAllRanges(), t.addRange(n)) : document.body && "undefined" != typeof document.body.createTextRange && (n = document.body.createTextRange()) && (n.moveToElementText(o), n.select())
            })
        },
        setCaret: function() {
            if (/msie/.test(navigator.userAgent.toLowerCase())) {
                var e = function() {
                    var e = $(this).get(0);
                    e.caretPos = document.selection.createRange().duplicate()
                };
                $(this).click(e).select(e).keyup(e)
            }
        },
        insertAtCaret: function(e) {
            var t = $(this).get(0);
            if (document.all && t.createTextRange && t.caretPos) {
                var n = t.caretPos;
                n.text = "" === n.text.charAt(n.text.length - 1) ? e + "": e
            } else if (t.setSelectionRange) {
                var i = t.selectionStart,
                o = t.selectionEnd,
                a = t.value.substring(0, i),
                s = t.value.substring(o);
                t.value = a + e + s;
                var c = e.length;
                t.setSelectionRange(i + c, i + c),
                r ? $(this).focus() : $(this).blur()
            } else t.value += e
        },
        deleteCaret: function() {
            var e = $(this),
            t = e.val(),
            n = /(\[[^@]{1,3}\])$/;
            t = n.test(t) ? t.replace(n, "") : t.substring(0, t.length - 1),
            e.val(t),
            e.blur()
        }
    }),
    e.exports = i
},
function(e, t, n) {
    var i = n(2),
    r = n(4),
    o = (n(3), {
        eventProcessors: {},
        init: function(e) {
            this.options = e,
            this.pushstream = new PushStream({
                host: e.domain,
                port: e.port,
                modes: "websocket|longpolling",
                messagesPublishedAfter: 0,
                useSSL: "https:" === window.location.protocol,
                messagesControlByArgument: !0
            }),
            this.pushstream.addChannel(e.roomid),
            this.pushstream.connect(),
            this.bind()
        },
        close: function() {
            this.pushstream.disconnect()
        },
        open: function() {
            this.pushstream.connect()
        },
        onevent: function(e, t) {
            return "string" != typeof e ? console.log("事件类型必须传入string") : void(this.eventProcessors[e] = t)
        },
        bind: function() {
            var e = this;
            this.pushstream.onmessage = function(t) {
                var n;
                try {
                    n = JSON.parse(t)
                } catch(e) {
                    return void console.log(e, t)
                }
                try {
                    e.eventProcessors[n.event](n)
                } catch(e) {
                    return void console.log(e, n)
                }
            },
            this.onevent("online",
            function(e) {
                i.trigger("userOnline", e)
            }),
            this.onevent("offline",
            function(e) {
                i.trigger("userOffline", e)
            }),
            this.onevent("msg",
            function(e) {
                var t = VHALL_SDK.getUserinfo();
                return ! (e.user_id == t.userid || t.forbidchat && "user" == e.role || e.data.notpublic && "host" != e.role) && (e.avatar = e.avatar ? e.avatar: "//cnstatic01.e.vhall.com/static/images/watch/head50.png", e.content = r(e.data.text), void i.trigger("chatMsg", e))
            }),
            this.onevent("question",
            function(e) {
                var t = VHALL_SDK.getUserinfo();
                return ("question" != e.data.type || "user" != t.role && t.userid != e.data.join_id) && ((!e.data.answer || "0" != e.data.answer.is_open || "user" != t.role || e.data.join_id == t.userid) && (e.data.content = r(e.data.content), e.data.answer && (e.data.answer.content = r(e.data.answer.content)), void i.trigger("questionMsg", e)))
            })
        },
        sendChat: function(e) {
            $.ajax({
                url: o.options.pubUrl,
                type: "get",
                dataType: "jsonp",
                jsonp: "callback",
                data: {
                    token: o.options.token,
                    event: "msg",
                    app_key: o.options.app_key,
                    data: JSON.stringify(e)
                },
                success: function(e) {
                    e = JSON.parse(e);
                    var t = {
                        code: 2e4,
                        msg: "请求成功"
                    };
                    "200" != e.code && (t = {
                        code: 20005,
                        msg: "接口请求失败"
                    }),
                    i.trigger("sendChat", t)
                },
                error: function(e) {
                    i.trigger("sendChat", {
                        code: 20005,
                        msg: "接口请求失败"
                    })
                }
            })
        },
        sendQuestion: function(e) {
            $.ajax({
                url: "//e.vhall.com/api/jssdk/v1/question/addquestion",
                type: "get",
                dataType: "jsonp",
                jsonp: "callback",
                data: {
                    token: o.options.token,
                    content: e,
                    app_key: o.options.app_key
                },
                success: function(e) {
                    var t = {
                        code: 2e4,
                        msg: "请求成功"
                    };
                    "200" != e.code && (t = {
                        code: 20005,
                        msg: "接口请求失败"
                    }),
                    i.trigger("sendQuestion", t)
                },
                error: function(e) {
                    i.trigger("sendQuestion", {
                        code: 20005,
                        msg: "接口请求失败"
                    })
                }
            })
        }
    });
    e.exports = o
},
function(e, t) {
    function n() {
        for (var e = navigator.userAgent,
        t = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"], n = !0, i = 0; i < t.length; i++) if (e.indexOf(t[i]) > 0) {
            n = !1;
            break
        }
        return n
    }
    e.exports = n()
},
function(e, t, n) {
    function i(e) {
        e !== c && (c = e, s.doc[0].src = e)
    }
    function r() {
        var e = "";
        e = "1" == a.get().isBoard ? s.board_url: s.curr_file && "0" != s.curr_file ? s.doc_url + "/" + s.curr_file + "/" + s.curr_page + ".jpg": "",
        i(e)
    }
    var o = n(9),
    a = (n(2), n(1)),
    s = {
        doc_url: "",
        curr_file: "0",
        curr_page: "",
        totalPage: "",
        board_url: "//cnstatic01.e.vhall.com/static/img/mobile/blankspace.jpg"
    },
    c = "";
    e.exports = {
        create: function(e) {
            s = $.extend({},
            s, e),
            $(s.docContent).
            html('<img style="width:100%"  id="doc_img" src="" onerror="this.src = " />'),
            		
            		
            		/*<img style="width:100%" 
            			src="//cnstatic01.e.vhall.com/static/img/mobile/doc_noloading.png"
            				onerror="this.src = '//cnstatic01.e.vhall.com/static/img/mobile/doc_error.png'">*/
            		
            		
            s.doc = $(s.docContent).find("img"),
            r()
        },
        setter: function(e, t) {
            1 == a.getter("type") ? setTimeout(function() {
                o(e) ? s = $.extend({},
                s, e) : s[e] = t,
                r()
            },
            15e3) : (o(e) ? s = $.extend({},
            s, e) : s[e] = t, r())
        },
        getter: function(e) {
            return e ? s[e] : s
        }
    }
},
function(e, t, n) {
    var i = n(6),
    r = n(15),
    o = n(1),
    a = n(2),
    s = n(12),
    c = n(16),
    d = {
        init: function(e) {
            if (i) s(["//cnstatic01.e.vhall.com/3rdlibs/swfobject/2.2.0/swfobject.js", "//cnstatic01.e.vhall.com/3rdlibs/jquery-object/jqueryObject.js"],
            function() {
                c.init(e)
            }),
            this.exchageWhite = c.exchageWhite;
            else {
                this.player = new r(e);
                var t = this;
                this.on = function(e, n) {
                    t.player.on(e, n)
                },
                this.setPlayerLine = function(e) {
                    t.player.setPlayerLine(e)
                },
                this.setPlayerDefinition = function(e) {
                    t.player.setPlayerDefinition(e)
                },
                this.setDefaultDefinitions = function(e) {
                    t.player.setDefaultDefinitions = e
                },
                1 == o.getter("type") && (this.seek = function(e) {
                    t.player.seek(e)
                }),
                a.trigger("playerReady"),
                this.player.onready()
            }
        }
    };
    e.exports = d
},
function(e, t) {
    function n(e) {
        return "[object Object]" === Object.prototype.toString.call(e)
    }
    e.exports = n
},
function(e, t) {
    e.exports = {
        10000 : "消息体格式不正确",
        10001 : "输入不能为空",
        10002 : "当前用户被禁言",
        10003 : "聊天输入不能超过140个字符",
        10004 : "当前已开启全员禁言",
        10005 : "当前活动不在直播",
        10006 : "当前活动未开启问答",
        20000 : "接口请求成功",
        20005 : "没有数据"
    }
},
function(e, t, n) {
    function i(e) {
        this.content = a(JSON.parse(e.content).text),
        this.avatar = e.avatar ? e.avatar: "//cnstatic01.e.vhall.com/static/images/watch/head50.png",
        this.role = e.user_role,
        this.user_name = e.user_name,
        this.time = e.created_at,
        this.user_id = e.user_id,
        this.account_id = e.account_id
    }
    var r = n(2),
    o = n(10),
    a = n(4),
    s = n(5);
    e.exports = {
        sign: 0,
        getLiveChatMsg: function() {
            return this.sign ? void $.ajax({
                url: "//e.vhall.com/api/jssdk/v1/webinar/historymsg",
                dataType: "jsonp",
                jsonp: "callback",
                data: {
                    webinar_id: VHALL_SDK.options.roomid
                },
                success: function(e) {
                    if ("200" == e.code) {
                        var t = [],
                        n = 0;
                        for (e.data.length; n < e.data.length; n++) t.push(new i(e.data[n]));
                        r.trigger("vhall_live_history_chat_msg", {
                            code: 200,
                            data: t,
                            msg: "拉取数据成功"
                        })
                    } else r.trigger("vhall_live_history_chat_msg", {
                        code: 20005,
                        msg: o[20005]
                    })
                },
                error: function(e) {
                    r.trigger("vhall_live_history_chat_msg", {
                        code: 20005,
                        msg: o[20005]
                    })
                }
            }) : r.trigger("error", {
                code: 1006,
                msg: "sign error"
            })
        },
        getRecordChatMsg: function(e) {
            return this.sign ? void $.ajax({
                url: "//e.vhall.com/api/jssdk/v1/webinar/getmsg",
                dataType: "jsonp",
                jsonp: "callback",
                data: {
                    webinar_id: VHALL_SDK.options.roomid,
                    curr_page: e
                },
                success: function(e) {
                    if ("200" == e.code) {
                        var t = [],
                        n = 0;
                        for (e.data.data.length; n < e.data.data.length; n++) t.push(new i(e.data.data[n]));
                        r.trigger("vhall_record_history_chat_msg", {
                            code: 200,
                            curr_page: e.data.curr_page,
                            total: e.data.total,
                            data: t,
                            total_page: e.data.total_page,
                            msg: "拉取数据成功"
                        })
                    } else r.trigger("vhall_record_history_chat_msg", {
                        code: 20005,
                        msg: o[20005]
                    })
                },
                error: function(e) {
                    r.trigger("vhall_record_history_chat_msg", {
                        code: 20005,
                        msg: o[20005]
                    })
                }
            }) : r.trigger("error", {
                code: 1006,
                msg: "sign error"
            })
        },
        sendRecordChat: function(e) {
            if (!this.sign) return r.trigger("error", {
                code: 1006,
                msg: "sign error"
            });
            var t = this;
            $.ajax({
                url: "//e.vhall.com/api/jssdk/v1/webinar/addmsg",
                dataType: "jsonp",
                jsonp: "callback",
                data: e,
                success: function(e) {
                    "200" == e.code ? (r.trigger("sendChat", {
                        code: 2e4,
                        msg: "请求成功"
                    }), t.getRecordChatMsg(1)) : r.trigger("sendChat", {
                        code: 20005,
                        msg: "接口请求失败"
                    })
                },
                error: function(e) {
                    r.trigger("sendChat", {
                        code: 20005,
                        msg: "接口请求失败"
                    })
                }
            })
        },
        getQuestionlist: function() {
            return this.sign ? 1 != VHALL_SDK.getRoominfo().openQuestion ? r.trigger("error", {
                code: 10006,
                msg: o[10006]
            }) : 1 != VHALL_SDK.getRoominfo().type ? r.trigger("error", {
                code: 10005,
                msg: o[10005]
            }) : void $.ajax({
                url: "//e.vhall.com/api/jssdk/v1/question/list",
                dataType: "jsonp",
                jsonp: "callback",
                data: {
                    token: s.options.token
                },
                success: function(e) {
                    if ("200" == e.code) {
                        var t = [],
                        n = 0;
                        for (e.data.length; n < e.data.length; n++) e.data[n].content = a(e.data[n].content),
                        e.data[n].answer && (e.data[n].answer.content = a(e.data[n].answer.content)),
                        t.push({
                            data: e.data[n]
                        });
                        r.trigger("getQuestionList", {
                            code: 200,
                            data: t,
                            msg: "请求成功"
                        })
                    } else r.trigger("getQuestionList", e)
                },
                error: function(e) {
                    r.trigger("getQuestionList", e)
                }
            }) : r.trigger("error", {
                code: 1006,
                msg: "sign error"
            })
        }
    }
},
function(e, t) {
    function n(e, t) {
        if ("string" == typeof e) {
            var n = e;
            e = [],
            e.push(n)
        }
        var r = function(e, t) {
            i(e.shift(),
            function() {
                e.length ? r(e, t) : t && t()
            })
        };
        r(e, t)
    }
    function i(e, t) {
        var n = !1,
        i = document.createElement("script");
        i.src = e,
        i.onload = i.onreadystatechange = function() {
            n || this.readyState && "loaded" != this.readyState && "complete" != this.readyState || (n = !0, t && t())
        },
        document.body.appendChild(i)
    }
    e.exports = n
},
function(e, t, n) {
    t = e.exports = n(14)(),
    t.push([e.id, ".facebox-pc{background:#f7f7f7;padding:2px;border:1px solid #afafaf;position:absolute;display:none;z-index:2;top:-266px;left:0}.facebox-pc table td{padding:0}.facebox-pc table td img{cursor:pointer;border:1px solid #f7f7f7}.facebox-pc table td img:hover{border:1px solid #06c}.facebox-mobile{background:#fff;border-top:1px solid #ddd;position:absolute;display:none;z-index:2;overflow:hidden;height:170px;padding:0 1%;top:40px;    padding-top: 0.4rem;}.facebox-mobile .qqFace-mobile{float:left;}.facebox-mobile .qqFace{float:left;text-align:left}.facebox-mobile li{display:inline-block;padding:6px 0;width:14%;text-align:center}.facebox-mobile .text-center a{width:10px;height:10px;border-radius:100%;background:#ddd;border:none;color:#fff;margin:5px 15px 0 0;display:inline-block;text-decoration:none}.facebox-mobile .text-center a.active{background:#ff3334}.facebox-mobile .text-center{text-align:center}", ""])
},
function(e, t) {
    e.exports = function() {
        var e = [];
        return e.toString = function() {
            for (var e = [], t = 0; t < this.length; t++) {
                var n = this[t];
                n[2] ? e.push("@media " + n[2] + "{" + n[1] + "}") : e.push(n[1])
            }
            return e.join("")
        },
        e.i = function(t, n) {
            "string" == typeof t && (t = [[null, t, ""]]);
            for (var i = {},
            r = 0; r < this.length; r++) {
                var o = this[r][0];
                "number" == typeof o && (i[o] = !0)
            }
            for (r = 0; r < t.length; r++) {
                var a = t[r];
                "number" == typeof a[0] && i[a[0]] || (n && !a[2] ? a[2] = n: n && (a[2] = "(" + a[2] + ") and (" + n + ")"), e.push(a))
            }
        },
        e
    }
},
function(e, t, n) {
    function i(e) {
        return this.currDefinitions = "",
        this.currCdnServerLine = e.lines[0],
        this.stream = e.stream,
        this.clientList = {},
        this.cdnServerLine = {},
        this.definitions = {},
        this.type = r.getter("type"),
        this.getLines(e.lines),
        this.getDefinitions(e.definitions),
        this.$videoCont = $(e.videoCont),
        this.video = this.createVideo(this.$videoCont),
        a.init(),
        this.bind(),
        this
    }
    var r = n(1),
    o = n(7),
    a = n(19),
    s = {
        A: "纯音频",
        SD: "标清",
        HD: "高清"
    };
    i.prototype.getLines = function(e) {
        for (var t = e.length,
        n = 0; n < t; n++) this.cdnServerLine[e[n].name] = e[n]
    },
    i.prototype.onready = function() {
        2 != this.type && (this.trigger("canPlayLines", this.cdnServerLine), this.getCanPlayDefinitions())
    },
    i.prototype.setDefaultDefinitions = function(e) {
        for (var t = 0,
        n = e.length; t < n; t++) this.definitions[s[e[t]]].valid = 1;
        this.getDefinitions(this.definitions),
        this.getCanPlayDefinitions()
    },
    i.prototype.getCanPlayDefinitions = function() {
        var e = {};
        for (var t in this.definitions) this.definitions[t].valid && (e[t] = this.definitions[t]);
        this.trigger("canPlayDefinitions", e)
    },
    i.prototype.getDefinitions = function(e) {
        if (1 == this.type) {
            this.definitions["原画质"] = {
                key: "D",
                name: "原画质",
                valid: 1,
                value: "",
                weight: -1
            };
            for (var t = e.length,
            n = 0; n < t; n++) this.definitions[e[n].name] = e[n]
        } else 3 == this.type && (this.definitions = {
            "原画质": {
                key: "D",
                name: "原画质",
                valid: 1,
                value: "",
                weight: 1
            },
            "纯音频": {
                key: "A",
                name: "纯音频",
                valid: 1,
                value: "",
                weight: 0
            }
        });
        2 != this.type && (this.currDefinitions = this.definitions["原画质"])
    },
    i.prototype.createVideo = function(e) {
        var t = $("<video id='vhall-h5-player'></video>");
        t.attr("webkit-playsinline", ""),
        t.attr("playsinline", ""),
        t.attr("controls", ""),
        t.css({
            width: "100%",
            height: "100%"
        });
        var n = "";
        return this.currCdnServerLine ? 1 == this.type ? n = this.currCdnServerLine.srv.replace("{stream}", this.stream + this.currDefinitions.value) : 3 == this.type && (n = this.currCdnServerLine.srv) : (console.log("当前无播放线路！"), this.trigger("playerError", {
            msg: "当前无播放线路！"
        })),
        t.attr("src", n),
        e.append(t),
        t.load(),
        t[0]
    },
    i.prototype.seek = function(e) {
        this.type && (this.video.currentTime = e, this.trigger("seek", e))
    },
    i.prototype.on = function(e, t) {
        this.clientList[e] || (this.clientList[e] = []),
        this.clientList[e].push(t)
    },
    i.prototype.trigger = function() {
        var e = Array.prototype.slice.call(arguments),
        t = Array.prototype.shift.call(e),
        n = this.clientList[t];
        if (n && n.length >= 0) for (var i = 0,
        r = n.length; i < r; i++) n[i].apply(null, e)
    },
    i.prototype.bind = function() {
        var e = this;
        this.video.addEventListener("waiting",
        function() {
            e.trigger("waiting")
        },
        !1),
        this.video.addEventListener("play",
        function() {
            e.trigger("play")
        },
        !1),
        this.video.addEventListener("pause",
        function() {
            e.trigger("pause")
        },
        !1),
        1 == this.type ? this.video.addEventListener("timeupdate",
        function() {
            e.trigger("timeupdate")
        },
        !1) : this.recordTimeupdate()
    },
    i.prototype.recordTimeupdate = function() {
        var e = 0,
        t = -1,
        n = "",
        i = "no",
        a = null;
        $.ajax({
            url: "//e.vhall.com/api/jssdk/v1/webinar/cuepoint",
            type: "GET",
            dataType: "jsonp",
            jsonp: "callback",
            data: {
                roomid: r.getter("id"),
                app_key: VHALL_SDK.options.appkey
            },
            success: function(e) {
                if ($.trim(e.data)) {
                    var t = $.parseJSON(e.data).cuepoint;
                    if ("object" == typeof t) {
                        for (var s = [], c = !1, d = !1, l = 0, u = t.length - 1; l <= u; l++) {
                            var p = $.parseJSON(t[l].content);
                            "flipOver" != p.type && "change_showtype" != p.type || s.push({
                                created_at: t[l].created_at,
                                type: p.type,
                                page: p.page ? p.page: "",
                                doc: p.doc ? p.doc: "",
                                showType: p.showType
                            }),
                            0 === t[l].created_at && ("flipOver" === p.type ? (p.doc && p.page ? o.create({
                                curr_file: p.doc,
                                curr_page: p.page
                            }) : o.create({
                                curr_file: "",
                                curr_page: ""
                            }), i = n, d = !0) : "change_showtype" === p.type && (r.setter("isBoard", p.showType), o.create({}), 1 === p.showType ? c = !0 : 0 === p.showType && (c = !1)))
                        }
                        a = s
                    }
                }
            }
        }),
        this.video.addEventListener("timeupdate",
        function() {
            var n = this.currentTime;
            if (e = parseInt(n), a && e != t) {
                for (var i = a.length - 1; i >= 0; i--) {
                    var s = a[i];
                    if (e > parseFloat(s.created_at)) {
                        if ("flipOver" === s.type) {
                            o.setter({
                                curr_file: s.doc,
                                curr_page: s.page
                            });
                            break
                        }
                        if ("change_showtype" === s.type) {
                            r.setter("isBoard", s.showType),
                            o.setter({});
                            break
                        }
                    }
                }
                t = e
            }
        },
        !1)
    },
    i.prototype.setPlayerLine = function(e) {
        if (this.cdnServerLine[e]) {
            var t = "",
            n = this.cdnServerLine[e];
            1 == this.type ? t = n.srv.replace("{stream}", r.getter("id") + this.currDefinitions.value) : 3 == this.type && (t = n.srv),
            this.currCdnServerLine = n,
            this.video.src = t,
            this.video.play()
        }
    },
    i.prototype.setPlayerDefinition = function(e) {
        if (1 == this.type) {
            if (this.definitions[e]) {
                var t = this.currCdnServerLine.srv.replace("{stream}", r.getter("id") + this.definitions[e].value);
                this.currDefinitions = this.definitions[e],
                this.video.src = t,
                this.video.play()
            }
        } else 3 == this.type && (this.currCdnServerLine.srv_audio && "纯音频" === e ? (this.video.src = this.currCdnServerLine.srv_audio, this.video.play()) : (this.video.src = this.currCdnServerLine.srv, this.video.play()))
    },
    e.exports = i
},
function(e, t, n) {
    function i(e) {
        e ? ($("#" + a).css({
            height: "1px",
            width: "1px",
            visibility: "hidden"
        }), $("#" + s).css({
            height: "100%",
            width: "100%",
            visibility: "visible"
        })) : ($("#" + s).css({
            height: "1px",
            width: "1px",
            visibility: "hidden"
        }), $("#" + a).css({
            height: "100%",
            width: "100%",
            visibility: "visible"
        }))
    }
    var r = n(1),
    o = "vhall-video-player",
    a = "vhall-doc-player",
    s = "vhall-white-board",
    c = function(e) {
        function t(t, n) {
            $("#" + t).flash({
                swf: n,
                width: "100%",
                height: "100%",
                allowfullscreen: !0,
                wmode: e.isFlashVR ? "direct": "transparent",
                allowscriptaccess: "always",
                allowFullScreenInteractive: !0,
                encodeParams: !1,
                bgcolor: "#363636",
                quality: "high",
                class: "Intranet_Runner",
                name: "Intranet_Runner",
                style: "visibility:visible",
                id: t,
                flashvars: e.flashvars
            })
        }
        function n(n) {
            var o = r.getter("isBoard");
            if (console.log(o), n.success) return i(parseInt(o)),
            !1;
            t(s, e.flash_whiteBoard_url);
            var a = setInterval(function() {
                100 == Math.floor($("#" + s)[0].PercentLoaded()) && (i(o), clearInterval(a))
            },
            60)
        }
        function c(n) {
            n.success || t(o, e.flash_video_url)
        }
        function d(n) {
            n.success || t(a, e.flash_doc_url)
        }
        $videoContent = $(e.videoCont),
        $docContent = $(e.docContent);
        var l = !1;
        try {
            var u = new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
            u && (l = !0)
        } catch(e) {
            try {
                l = void 0 != navigator.plugins["Shockwave Flash"]
            } catch(e) {
                l = !1
            }
        }
        if (!l) return void(e.videoCont ? $videoContent.html('<div style="position:absolute;color:#999;text-align:center;font-size:16px;height:30px;top:50%;margin-top:-15px;width:100%;">系统检测到您的浏览器未安装flash插件,点击<a href="//get.adobe.com/cn/flashplayer/" target="_blank">这里</a>安装</div>') : e.docContent && $docContent.html('<div style="position:absolute;color:#999;text-align:center;font-size:16px;height:30px;top:50%;margin-top:-15px;width:100%;">系统检测到您的浏览器未安装flash插件,点击<a href="//get.adobe.com/cn/flashplayer/" target="_blank">这里</a>安装</div>'));
        e.videoCont && $videoContent.append('<div style="width:100%;height:100%;" id="' + o + '"></div>'),
        e.docContent && $docContent.append("<div style='width:100%;height:100%;' id='" + a + "'></div><div style='width:1px;height:1px;' id='" + s + "'></div>");
        var p = "10.0.0",
        h = "//cnstatic01.e.vhall.com/3rdlibs/swfobject/2.2.0/expressInstall.swf",
        g = {};
        g.quality = "high",
        g.bgcolor = "#363636",
        g.allowscriptaccess = "always",
        g.allowfullscreen = "true",
        g.allowFullScreenInteractive = "true",
        g.wmode = e.isFlashVR ? "direct": "transparent";
        var f = {};
        f.styleclass = "Intranet_Runner",
        f.name = "Intranet_Runner",
        f.align = "middle",
        !e.flash_doc_url || swfobject.embedSWF(e.flash_doc_url, a, "100%", "100%", p, h, e.flashvar, g, f, d),
        !e.flash_whiteBoard_url || swfobject.embedSWF(e.flash_whiteBoard_url, s, "100%", "100%", p, h, e.flashvar, g, f, n),
        !e.flash_video_url || swfobject.embedSWF(e.flash_video_url, o, "100%", "100%", p, h, e.flashvar, g, f, c),
        swfobject.createCSS(".Intranet_Runner", "display:block;text-align:left;position:absolute;")
    }; !
    function(e) {
        e.sendMsgToFlash = function(e, t) {
            var n = $(".Intranet_Runner");
            $.each(n,
            function(n, i) {
                try {
                    i.sendMsgToAs(e, t)
                } catch(e) {
                    console.log("flsah 错误信息：" + e.message)
                }
            })
        },
        e.sendEveToFlash = function(e, t) {
            var n = $(".Intranet_Runner");
            $.each(n,
            function(n, i) {
                try {
                    i.sendEventToAs(e, t)
                } catch(e) {
                    console.log("flsah 错误信息：" + e.message)
                }
            })
        },
        e.sendCmdMsg = function(t) {
            try {
                var n = Base64.decode(t);
                return t = $.parseJSON(n),
                e.sendMsgToFlash(t.type, t),
                3 == r.getter("type") && "*changeShowType" == t.type && i(parseInt(t.showType)),
                !0
            } catch(e) {
                return console.log("parse JSON error:" + e.message),
                !1
            }
        },
        e.sendRecordMsg = function(e, t) {
            try {
                return sendEveToFlash(e, t),
                !0
            } catch(e) {
                return ldebug("parse JSON error:" + e.message),
                !1
            }
        }
    } (window),
    e.exports.init = c,
    e.exports.exchageWhite = i
},
function(e, t, n) {
    function i(e) {
        if ("undefined" == typeof e) return e = {},
        console.error("请先阅读文档传入正确参数");
        if (! (e.roomid && e.app_key && e.signedat && e.sign && e.username)) return console.error("有必填项未填");
        var t = {
            account: e.account,
            email: e.email,
            roomid: e.roomid,
            username: e.username,
            appkey: e.app_key,
            signedat: e.signedat,
            sign: e.sign,
            facedom: e.facedom,
            textdom: e.textdom,
            videoContent: e.videoContent,
            docContent: e.docContent
        };
        VHALL_SDK.options = t,
        o(["//cnstatic01.e.vhall.com/3rdlibs/nginx-push-stream/0.5.1/pushstream.js", "//cnstatic01.e.vhall.com/3rdlibs/socket.io/1.3.5/socket.io.min.js", "//cnstatic01.e.vhall.com/3rdlibs/jquery-json/2.4.0/jquery.json.min.js", "//cnstatic01.e.vhall.com/3rdlibs/base64/base64.js"],
        function() {
            r(t)
        })
    }
    var r = n(18),
    o = n(12);
    e.exports = i
},
function(e, t, n) {
    function i(e) {
        var t = {
            roomid: e.roomid,
            account: e.account,
            username: e.username,
            app_key: e.appkey,
            signedat: e.signedat,
            sign: e.sign
        };
        e.email && (t.email = e.email),
        $.ajax({
            url: "//e.vhall.com/api/jssdk/v1/webinar/init",
            type: "get",
            dataType: "jsonp",
            jsonp: "callback",
            data: t,
            success: function(t) {
            	if(200 == t.code){
            		t.data.roomid = e.roomid, o(t.data, e), c.trigger("ready");
            	}else{
            		$("#error_code").text(t.code);
            		$(".history_bg").show(); 
            	}
            	
                	//c.trigger("error", t)
            },
            error: function(e) {
                c.trigger("error", e)
            }
        })
    }
    function r(e, t) {
        var n = e.visitor;
        console.log(e),
        d.create({
            id: e.roomid,
            type: e.webinarStatus,
            openQuestion: e.openQuestion,
            isBoard: parseInt(e.doc.whiteBoard)
        }),
        l.create({
            username: n.nick_name,
            userid: n.id,
            sessionId: e.sessionId,
            forbidchat: e.forbidchat ? e.forbidchat: 0,
            role: n.role ? n.role: "user",
            avatar: n.avatar ? n.avatar: "//cnstatic01.e.vhall.com/static/images/watch/head50.png",
            is_gag: e.visitor.is_gag,
            is_kickout: e.visitor.is_kickout
        }),
        t.docContent && !g && u.create({
            doc_url: e.doc.srv,
            curr_file: e.doc.currFile,
            curr_page: e.doc.currPage,
            totalPage: e.doc.totalPage,
            docContent: t.docContent
        })
    }
    function o(e, t) {
        r(e, t),
        1 == e.webinarStatus && (s.init({
            pubUrl: e.pushstreamPubUrl,
            token: e.socketToken,
            domain: e.pushstreamDomain,
            port: e.pushstreamPort,
            roomid: t.roomid
        }), a.init({
            srv: e.socketSrv,
            token: e.socketToken,
            app: "vhall"
        })),
        p.sign = 1,
        t.facedom && t.textdom && $(t.facedom).qqFace({
            assign: t.textdom,
            path: "//cnstatic01.e.vhall.com/static/img/arclist/"
        }),
        t.videoContent && h.init($.extend({
            videoCont: t.videoContent,
            docContent: t.docContent
        },
        g ? e.flashPlayer: e.mobilePlayer))
    }
    var a = n(20),
    s = n(5),
    c = n(2),
    d = n(1),
    l = n(3),
    u = n(7);
    n(4);
    var p = n(11),
    h = n(8),
    g = n(6);
    e.exports = i
},
function(e, t, n) {
    function i() {
        var e = r();
        return e = e.match(/(https?:|rtmp:)?(\/\/)?([a-zA-Z0-9]+\.)?([a-zA-Z0-9]+\.)+[a-zA-Z0-9]+/),
        e && (e = e[0]),
        e
    }
    function r() {
        var e = "";
        return e = $("video#vhall-h5-player")[0].src
    }
    function o() {
        return (new Date).getTime() - b
    }
    function a() {
        return {
            p: j.p,
            pf: "3",
            aid: j.p,
            uid: C.join_uid,
            s: j.s
        }
    }
    function s() {
        var e = a();
        e.tt = o(),
        1 == C.webinar_type ? e.sd = i() : e.fd = r(),
        e.ua = j.ua,
        D({
            k: v.heart_beat,
            id: String(j.s) + (new Date).getTime(),
            s: j.s,
            token: e
        })
    }
    function c() {
        var e = a();
        1 == C.webinar_type ? e.sd = i() : e.fd = r(),
        D({
            k: v.player_pause,
            id: String(j.s) + (new Date).getTime(),
            s: j.s,
            token: e
        })
    }
    function d() {
        var e = a();
        1 == C.webinar_type ? e.sd = i() : e.fd = r(),
        D({
            k: v.player_resume,
            id: String(j.s) + (new Date).getTime(),
            s: j.s,
            token: e
        })
    }
    function l() {
        w = 0,
        L = 0;
        var e = a();
        1 == C.webinar_type ? e.sd = i() : e.fd = r(),
        D({
            k: v.open_stream,
            id: String(j.s) + (new Date).getTime(),
            s: j.s,
            token: e
        })
    }
    function u() {
        var e = a();
        1 == C.webinar_type ? e.sd = i() : e.fd = r(),
        e.tt = o(),
        e._bc = w,
        e._bt = "",
        D({
            k: v.close_stream,
            id: String(j.s) + (new Date).getTime(),
            s: j.s,
            token: e
        })
    }
    function p() {
        var e = a();
        1 == C.webinar_type ? e.sd = i() : e.fd = r(),
        e.tt = (new Date).getTime() - b,
        e._bc = k,
        e._bt = S,
        D({
            k: v.lag,
            id: String(j.s) + (new Date).getTime(),
            s: j.s,
            token: e
        })
    }
    function h() {
        var e = g.get(),
        t = f.get();
        C.webinar_id = e.id,
        C.webinar_type = e.type,
        C.sessionId = t.sessionId,
        C.join_uid = t.userid,
        j = {
            p: C.webinar_id,
            s: C.sessionId,
            ua: navigator.userAgent
        },
        setTimeout(function() {
            var e = 1;
            $("video#vhall-h5-player")[0] ? e = $("video#vhall-h5-player")[0].paused: $("audio")[0] && (e = $("audio")[0].paused),
            x || e || (l(this.src), clearInterval(x), x = setInterval(function() {
                s(),
                p()
            },
            6e4))
        },
        1e3),
        $("video#vhall-h5-player")[0] && ($("video#vhall-h5-player")[0].addEventListener("play",
        function() {
            b = (new Date).getTime(),
            clearInterval(x),
            x = setInterval(function() {
                s(),
                p()
            },
            6e4),
            _ && (l(this.src), _ = !1),
            L = 0,
            d()
        },
        !1), $("video#vhall-h5-player")[0].addEventListener("timeupdate",
        function() {
            L && (S = (new Date).getTime() - L)
        },
        !1), $("video#vhall-h5-player")[0].addEventListener("pause",
        function() {
            L = 0,
            clearInterval(x),
            c()
        },
        !1), $("video#vhall-h5-player")[0].addEventListener("ended",
        function() {
            L = 0,
            clearInterval(x)
        },
        !1), $("video#vhall-h5-player")[0].addEventListener("waiting",
        function() {
            k++,
            w++,
            L = (new Date).getTime()
        }))
    }
    var g = n(1),
    f = n(3),
    v = {
        open_stream: 92001,
        close_stream: 92002,
        heart_beat: 92003,
        player_pause: 92004,
        player_resume: 92005,
        lag: 94001
    },
    m = "//la.e.vhall.com/login",
    y = {},
    b = (new Date).getTime(),
    _ = !0,
    x = null,
    w = 0,
    k = 0,
    L = 0,
    S = 0,
    C = {},
    j = {
        p: C.webinar_id,
        s: C.sessionId,
        ua: navigator.userAgent
    },
    D = function(e) {
        e.token = Base64.encode(JSON.stringify(e.token)),
        $.getJSON(m, e,
        function() {})
    },
    T = {},
    A = null;
    A = function(e, t) {
        T[e] || (T[e] = []),
        T[e].push(t)
    },
    y.trigger = function() {
        var e = Array.prototype.slice.call(arguments),
        t = Array.prototype.shift.call(e),
        n = T[t];
        if (n && n.length >= 0) for (var i = 0,
        r = n.length; i < r; i++) n[i].apply(null, e)
    },
    A("changeline",
    function(e, t) {
        u(e),
        l(t)
    }),
    y.init = h,
    e.exports = y
},
function(e, t, n) {
    function i(e) {
        switch (e.type) {
        case "*disablechat":
            d.getter("userid") == e.user_id && d.setter("is_gag", 1),
            r.trigger("disableChat", e.user_id);
            break;
        case "*permitchat":
            d.getter("userid") == e.user_id && d.setter("is_gag", 0),
            r.trigger("permitChat", e.user_id);
            break;
        case "*kickout":
            d.getter("userid") == e.user_id && d.setter("is_kickout", 1),
            r.trigger("kickout", e.user_id);
            break;
        case "*kickoutrestore":
            d.getter("userid") == e.user_id && d.setter("is_kickout", 0),
            r.trigger("kickoutRestore", e.user_id);
            break;
        case "*forbidchat":
            "1" === e.status ? d.setter("forbidchat", 1) : d.setter("forbidchat", 0),
            r.trigger("forbidChat", e.status);
            break;
        case "*question":
            "1" == e.status ? c.setter("openQuestion", 1) : c.setter("openQuestion", 0),
            r.trigger("questionSwitch", {
                status: e.status
            });
            break;
        case "*publish_start":
            a ? window.sendMsgToFlash("*publish_start", $.toJSON(e)) : l.player.setPlayerDefinition(e.trans);
            break;
        case "*whiteBoard":
            a ? (c.setter("isBoard", e.status), window.sendMsgToFlash("*changeShowType", $.toJSON({
                showType: parseInt(e.status)
            })), "1" == e.status ? l.exchageWhite(1) : l.exchageWhite(0)) : (setTimeout(function() {
                c.setter("isBoard", e.status)
            },
            15e3), s.setter({}));
            break;
        case "*over":
            if (a) {
                var t = {};
                t.desc = "活动结束",
                c.setter("type", 3),
                window.sendMsgToFlash("*over", $.toJSON(t))
            } else setTimeout(function() {
                r.trigger("streamOver")
            },
            15e3);
            break;
        case "*publishStart":
            a ? (c.setter("type", 1), window.sendMsgToFlash("*publishStart", $.toJSON(e))) : setTimeout(function() {
                r.trigger("publishStart")
            },
            15e3);
            break;
        case "*announcement":
            r.trigger("announcement", e.content)
        }
    }
    var r = n(2),
    o = (n(4), n(21)),
    a = n(6),
    s = n(7),
    c = n(1),
    d = n(3),
    l = n(8),
    u = (n(5), {
        init: function(e) {
            this.options = e,
            this.socket = io.connect(e.srv, {
                query: "token=" + e.token + "&app=" + (e.app || "vhall"),
                transports: o ? ["polling"] : ["websocket", "polling"]
            }),
            this.bind()
        },
        bind: function() {
            this.socket.on("online",
            function(e) {}),
            this.socket.on("cmd",
            function(e) {
                try {
                    e = $.parseJSON(e)
                } catch(t) {
                    e = e
                }
                i(e)
            }),
            this.socket.on("flashMsg",
            function(e) {
                if (a) window.sendEveToFlash("flashMsg", e);
                else try {
                    var t = $.parseJSON(e);
                    "flipOver" == t.type && s.setter({
                        curr_file: t.doc,
                        curr_page: t.page,
                        totalPage: t.totalPage
                    })
                } catch(e) {}
            })
        },
        close: function() {
            this.socket.close()
        },
        open: function() {
            this.socket.open()
        }
    });
    e.exports = u
},
function(e, t) {
    function n() {
        var e = !1;
        return "Microsoft Internet Explorer" == navigator.appName && ("MSIE9.0" != navigator.appVersion.split(";")[1].replace(/[ ]/g, "") && "MSIE8.0" != navigator.appVersion.split(";")[1].replace(/[ ]/g, "") || (e = !0)),
        e
    }
    e.exports = n()
},
function(e, t) {
    function n(e) {
        return "[object Number]" === Object.prototype.toString.call(e)
    }
    e.exports = n
},
function(e, t) {
    function n(e) {
        return "[object String]" === Object.prototype.toString.call(e)
    }
    e.exports = n
},
function(e, t, n) {
    function i(e) {
        return c.indexOf(e)
    }
    function r(e) {
        return c[e]
    }
    function o(e) {
        return s(e) ? i(e) + 1 : a(e) ? r(e) : null
    }
    var a = n(22),
    s = n(23),
    c = ["[微笑]", "[撇嘴]", "[色]", "[发呆]", "[得意]", "[流泪]", "[害羞]", "[闭嘴]", "[睡]", "[哭]", "[尴尬]", "[发怒]", "[调皮]", "[呲牙]", "[惊讶]", "[难过]", "[酷]", "[汗]", "[抓狂]", "[吐]", "[偷笑]", "[愉快]", "[白眼]", "[傲慢]", "[饥饿]", "[困]", "[惊恐]", "[流汗]", "[憨笑]", "[悠闲]", "[奋斗]", "[咒骂]", "[疑问]", "[嘘]", "[晕]", "[疯了]", "[衰]", "[骷髅]", "[敲打]", "[再见]", "[擦汗]", "[抠鼻]", "[鼓掌]", "[糗大了]", "[坏笑]", "[左哼哼]", "[右哼哼]", "[哈欠]", "[鄙视]", "[委屈]", "[快哭了]", "[阴险]", "[亲亲]", "[吓]", "[可怜]", "[菜刀]", "[西瓜]", "[啤酒]", "[篮球]", "[乒乓]", "[咖啡]", "[饭]", "[猪头]", "[玫瑰]", "[凋谢]", "[嘴唇]", "[爱心]", "[心碎]", "[蛋糕]", "[闪电]", "[炸弹]", "[刀]", "[足球]", "[瓢虫]", "[便便]", "[月亮]", "[太阳]", "[礼物]", "[拥抱]", "[强]", "[弱]", "[握手]", "[胜利]", "[抱拳]", "[勾引]", "[拳头]", "[差劲]", "[爱你]", "[NO]", "[OK]"];
    Array.prototype.indexOf || (Array.prototype.indexOf = function(e) {
        var t = this.length >>> 0,
        n = Number(arguments[1]) || 0;
        for (n = n < 0 ? Math.ceil(n) : Math.floor(n), n < 0 && (n += t); n < t; n++) if (n in this && this[n] === e) return n;
        return - 1
    }),
    e.exports = o
},
function(e, t, n) {
    function i(e, t) {
        return c.sign ? t ? $.trim(t.text) ? t.text && $.trim(t.text).length > 140 ? o.trigger(e, {
            code: 10003,
            msg: u[10003]
        }) && !1 : ("1" == s.getter("is_gag") && o.trigger(e, {
            code: 10002,
            msg: u[10002]
        }) && !1, 1 != s.getter("forbidchat") || o.trigger(e, {
            code: 10004,
            msg: u[10004]
        }) && !1) : o.trigger(e, {
            code: 10001,
            msg: u[10001]
        }) && !1 : o.trigger(e, {
            code: 1e4,
            msg: u[1e4]
        }) && !1 : o.trigger(e, {
            code: 1006,
            msg: "sign error"
        }) && !1
    }
    var r = n(17),
    o = n(2),
    a = n(1),
    s = n(3),
    c = n(11),
    d = n(5),
    l = n(4),
    u = n(10),
    p = n(8);
    window.VHALL_SDK = window.VHALL_SDK || {},
    VHALL_SDK.Version = "2.0.0",
    VHALL_SDK.on = o.listen,
    VHALL_SDK.remove = o.remove,
    VHALL_SDK.init = r,
    VHALL_SDK.getRoominfo = a.get,
    VHALL_SDK.getUserinfo = s.get,
    VHALL_SDK.vhall_get_live_history_chat_msg = function(e) {
        c.getLiveChatMsg(e)
    },
    VHALL_SDK.vhall_get_live_history_question_msg = function(e) {
        c.getQuestionlist(e)
    },
    VHALL_SDK.vhall_get_record_history_chat_msg = function(e) {
        c.getRecordChatMsg(e)
    },
    VHALL_SDK.sendChat = function(e) {
        if (!i("sendChat", e)) return ! 1;
        var t = s.get();
        return "1" != t.is_gag && 1 != t.forbidchat && (1 == VHALL_SDK.getRoominfo().type ? d.sendChat(e) : c.sendRecordChat({
            content: e.text,
            webinar_id: VHALL_SDK.options.roomid,
            nick_name: t.username,
            user_id: t.userid,
            avatar: t.avatar
        })),
        {
            avatar: t.avatar,
            content: l(e.text),
            user_name: t.username,
            user_id: t.userid,
            role: t.role,
            third_user_id: t.third_user_id
        }
    },
    VHALL_SDK.sendQuestion = function(e) {
        if (!i("sendQuestion", e)) return ! 1;
        if (1 != VHALL_SDK.getRoominfo().type) return o.trigger("sendQuestion", {
            code: 10005,
            msg: u[10005]
        }) && !1;
        if (1 != VHALL_SDK.getRoominfo().openQuestion) return o.trigger("sendQuestion", {
            code: 10006,
            msg: u[10006]
        }) && !1;
        var t = s.get();
        "1" != t.is_gag && 1 != t.forbidchat && d.sendQuestion(e.text);
        var n = new Date;
        return n = (n.getHours() > 9 ? n.getHours() : "0" + n.getHours()) + ":" + (n.getMinutes() > 9 ? n.getMinutes() : "0" + n.getMinutes()),
        {
            avatar: t.avatar,
            data: {
                content: l(e.text),
                created_at: n,
                join_id: t.userid,
                nick_name: t.username
            }
        }
    },
    VHALL_SDK.player = p
},
function(e, t, n) {
    function i(e, t) {
        for (var n = 0; n < e.length; n++) {
            var i = e[n],
            r = h[i.id];
            if (r) {
                r.refs++;
                for (var o = 0; o < r.parts.length; o++) r.parts[o](i.parts[o]);
                for (; o < i.parts.length; o++) r.parts.push(d(i.parts[o], t))
            } else {
                for (var a = [], o = 0; o < i.parts.length; o++) a.push(d(i.parts[o], t));
                h[i.id] = {
                    id: i.id,
                    refs: 1,
                    parts: a
                }
            }
        }
    }
    function r(e) {
        for (var t = [], n = {},
        i = 0; i < e.length; i++) {
            var r = e[i],
            o = r[0],
            a = r[1],
            s = r[2],
            c = r[3],
            d = {
                css: a,
                media: s,
                sourceMap: c
            };
            n[o] ? n[o].parts.push(d) : t.push(n[o] = {
                id: o,
                parts: [d]
            })
        }
        return t
    }
    function o(e, t) {
        var n = v(),
        i = b[b.length - 1];
        if ("top" === e.insertAt) i ? i.nextSibling ? n.insertBefore(t, i.nextSibling) : n.appendChild(t) : n.insertBefore(t, n.firstChild),
        b.push(t);
        else {
            if ("bottom" !== e.insertAt) throw new Error("Invalid value for parameter 'insertAt'. Must be 'top' or 'bottom'.");
            n.appendChild(t)
        }
    }
    function a(e) {
        e.parentNode.removeChild(e);
        var t = b.indexOf(e);
        t >= 0 && b.splice(t, 1)
    }
    function s(e) {
        var t = document.createElement("style");
        return t.type = "text/css",
        o(e, t),
        t
    }
    function c(e) {
        var t = document.createElement("link");
        return t.rel = "stylesheet",
        o(e, t),
        t
    }
    function d(e, t) {
        var n, i, r;
        if (t.singleton) {
            var o = y++;
            n = m || (m = s(t)),
            i = l.bind(null, n, o, !1),
            r = l.bind(null, n, o, !0)
        } else e.sourceMap && "function" == typeof URL && "function" == typeof URL.createObjectURL && "function" == typeof URL.revokeObjectURL && "function" == typeof Blob && "function" == typeof btoa ? (n = c(t), i = p.bind(null, n), r = function() {
            a(n),
            n.href && URL.revokeObjectURL(n.href)
        }) : (n = s(t), i = u.bind(null, n), r = function() {
            a(n)
        });
        return i(e),
        function(t) {
            if (t) {
                if (t.css === e.css && t.media === e.media && t.sourceMap === e.sourceMap) return;
                i(e = t)
            } else r()
        }
    }
    function l(e, t, n, i) {
        var r = n ? "": i.css;
        if (e.styleSheet) e.styleSheet.cssText = _(t, r);
        else {
            var o = document.createTextNode(r),
            a = e.childNodes;
            a[t] && e.removeChild(a[t]),
            a.length ? e.insertBefore(o, a[t]) : e.appendChild(o)
        }
    }
    function u(e, t) {
        var n = t.css,
        i = t.media;
        if (i && e.setAttribute("media", i), e.styleSheet) e.styleSheet.cssText = n;
        else {
            for (; e.firstChild;) e.removeChild(e.firstChild);
            e.appendChild(document.createTextNode(n))
        }
    }
    function p(e, t) {
        var n = t.css,
        i = t.sourceMap;
        i && (n += "\n/*# sourceMappingURL=data:application/json;base64," + btoa(unescape(encodeURIComponent(JSON.stringify(i)))) + " */");
        var r = new Blob([n], {
            type: "text/css"
        }),
        o = e.href;
        e.href = URL.createObjectURL(r),
        o && URL.revokeObjectURL(o)
    }
    var h = {},
    g = function(e) {
        var t;
        return function() {
            return "undefined" == typeof t && (t = e.apply(this, arguments)),
            t
        }
    },
    f = g(function() {
        return /msie [6-9]\b/.test(window.navigator.userAgent.toLowerCase())
    }),
    v = g(function() {
        return document.head || document.getElementsByTagName("head")[0]
    }),
    m = null,
    y = 0,
    b = [];
    e.exports = function(e, t) {
        t = t || {},
        "undefined" == typeof t.singleton && (t.singleton = f()),
        "undefined" == typeof t.insertAt && (t.insertAt = "bottom");
        var n = r(e);
        return i(n, t),
        function(e) {
            for (var o = [], a = 0; a < n.length; a++) {
                var s = n[a],
                c = h[s.id];
                c.refs--,
                o.push(c)
            }
            if (e) {
                var d = r(e);
                i(d, t)
            }
            for (var a = 0; a < o.length; a++) {
                var c = o[a];
                if (0 === c.refs) {
                    for (var l = 0; l < c.parts.length; l++) c.parts[l]();
                    delete h[c.id]
                }
            }
        }
    };
    var _ = function() {
        var e = [];
        return function(t, n) {
            return e[t] = n,
            e.filter(Boolean).join("\n")
        }
    } ()
},
function(e, t, n) {
    var i = n(13);
    "string" == typeof i && (i = [[e.id, i, ""]]);
    n(26)(i, {});
    i.locals && (e.exports = i.locals)
}]);