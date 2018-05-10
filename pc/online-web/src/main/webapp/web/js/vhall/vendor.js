!
function(e, r) {
	function t(e) {
		return function(r) {
			return {}.toString.call(r) == "[object " + e + "]"
		}
	}
	function n() {
		return x++
	}
	function i(e) {
		return e.match(S)[0]
	}
	function a(e) {
		for (e = e.replace(q, "/"); e.match(C);) e = e.replace(C, "/");
		return e = e.replace(I, "$1/")
	}
	function s(e) {
		var r = e.length - 1,
			t = e.charAt(r);
		return "#" === t ? e.substring(0, r) : ".js" === e.substring(r - 2) || e.indexOf("?") > 0 || ".css" === e.substring(r - 3) || "/" === t ? e : e + ".js"
	}
	function o(e) {
		var r = A.alias;
		return r && T(r[e]) ? r[e] : e
	}
	function u(e) {
		var r, t = A.paths;
		return t && (r = e.match(j)) && T(t[r[1]]) && (e = t[r[1]] + r[2]), e
	}
	function c(e) {
		var r = A.vars;
		return r && e.indexOf("{") > -1 && (e = e.replace(G, function(e, t) {
			return T(r[t]) ? r[t] : e
		})), e
	}
	function f(e) {
		var r = A.map,
			t = e;
		if (r) for (var n = 0, i = r.length; i > n; n++) {
			var a = r[n];
			if (t = w(a) ? a(e) || e : e.replace(a[0], a[1]), t !== e) break
		}
		return t
	}
	function l(e, r) {
		var t, n = e.charAt(0);
		if (R.test(e)) t = e;
		else if ("." === n) t = a((r ? i(r) : A.cwd) + e);
		else if ("/" === n) {
			var s = A.cwd.match(L);
			t = s ? s[0] + e.substring(1) : e
		} else t = A.base + e;
		return 0 === t.indexOf("//") && (t = location.protocol + t), t
	}
	function d(e, r) {
		if (!e) return "";
		e = o(e), e = u(e), e = c(e), e = s(e);
		var t = l(e, r);
		return t = f(t)
	}
	function v(e) {
		return e.hasAttribute ? e.src : e.getAttribute("src", 4)
	}
	function h(e, r, t, n) {
		var i = P.test(e),
			a = $.createElement(i ? "link" : "script");
		t && (a.charset = t), U(n) || a.setAttribute("crossorigin", n), p(a, r, i, e), i ? (a.rel = "stylesheet", a.href = e) : (a.async = !0, a.src = e), V = a, M ? K.insertBefore(a, M) : K.appendChild(a), V = null
	}
	function p(e, t, n, i) {
		function a() {
			e.onload = e.onerror = e.onreadystatechange = null, n || A.debug || K.removeChild(e), e = null, t()
		}
		var s = "onload" in e;
		return !n || !W && s ? (s ? (e.onload = a, e.onerror = function() {
			O("error", {
				uri: i,
				node: e
			}), a()
		}) : e.onreadystatechange = function() {
			/loaded|complete/.test(e.readyState) && a()
		}, r) : (setTimeout(function() {
			g(e, t)
		}, 1), r)
	}
	function g(e, r) {
		var t, n = e.sheet;
		if (W) n && (t = !0);
		else if (n) try {
			n.cssRules && (t = !0)
		} catch (i) {
			"NS_ERROR_DOM_SECURITY_ERR" === i.name && (t = !0)
		}
		setTimeout(function() {
			t ? r() : g(e, r)
		}, 20)
	}
	function E() {
		if (V) return V;
		if (H && "interactive" === H.readyState) return H;
		for (var e = K.getElementsByTagName("script"), r = e.length - 1; r >= 0; r--) {
			var t = e[r];
			if ("interactive" === t.readyState) return H = t
		}
	}
	function m(e) {
		var r = [];
		return e.replace(J, "").replace(z, function(e, t, n) {
			n && r.push(n)
		}), r
	}
	function y(e, r) {
		this.uri = e, this.dependencies = r || [], this.exports = null, this.status = 0, this._waitings = {}, this._remain = 0
	}
	if (!e.seajs) {
		var b = e.seajs = {
			version: "2.2.3"
		},
			A = b.data = {},
			_ = t("Object"),
			T = t("String"),
			D = Array.isArray || t("Array"),
			w = t("Function"),
			U = t("Undefined"),
			x = 0,
			N = A.events = {};
		b.on = function(e, r) {
			var t = N[e] || (N[e] = []);
			return t.push(r), b
		}, b.off = function(e, r) {
			if (!e && !r) return N = A.events = {}, b;
			var t = N[e];
			if (t) if (r) for (var n = t.length - 1; n >= 0; n--) t[n] === r && t.splice(n, 1);
			else delete N[e];
			return b
		};
		var O = b.emit = function(e, r) {
				var t, n = N[e];
				if (n) for (n = n.slice(); t = n.shift();) t(r);
				return b
			},
			S = /[^?#]*\//,
			q = /\/\.\//g,
			C = /\/[^\/]+\/\.\.\//,
			I = /([^:\/])\/\//g,
			j = /^([^\/:]+)(\/.+)$/,
			G = /{([^{]+)}/g,
			R = /^\/\/.|:\//,
			L = /^.*?\/\/.*?\//,
			$ = document,
			k = i($.URL),
			X = $.scripts,
			B = $.getElementById("seajsnode") || X[X.length - 1],
			F = i(v(B) || k);
		b.resolve = d;
		var V, H, K = $.head || $.getElementsByTagName("head")[0] || $.documentElement,
			M = K.getElementsByTagName("base")[0],
			P = /\.css(?:\?|$)/i,
			W = +navigator.userAgent.replace(/.*(?:AppleWebKit|AndroidWebKit)\/(\d+).*/, "$1") < 536;
		b.request = h;
		var Y, z = /"(?:\\"|[^"])*"|'(?:\\'|[^'])*'|\/\*[\S\s]*?\*\/|\/(?:\\\/|[^\/\r\n])+\/(?=[^\/])|\/\/.*|\.\s*require|(?:^|[^$])\brequire\s*\(\s*(["'])(.+?)\1\s*\)/g,
			J = /\\\\/g,
			Q = b.cache = {},
			Z = {},
			ee = {},
			re = {},
			te = y.STATUS = {
				FETCHING: 1,
				SAVED: 2,
				LOADING: 3,
				LOADED: 4,
				EXECUTING: 5,
				EXECUTED: 6
			};
		y.prototype.resolve = function() {
			for (var e = this, r = e.dependencies, t = [], n = 0, i = r.length; i > n; n++) t[n] = y.resolve(r[n], e.uri);
			return t
		}, y.prototype.load = function() {
			var e = this;
			if (!(e.status >= te.LOADING)) {
				e.status = te.LOADING;
				var t = e.resolve();
				O("load", t);
				for (var n, i = e._remain = t.length, a = 0; i > a; a++) n = y.get(t[a]), n.status < te.LOADED ? n._waitings[e.uri] = (n._waitings[e.uri] || 0) + 1 : e._remain--;
				if (0 === e._remain) return e.onload(), r;
				var s = {};
				for (a = 0; i > a; a++) n = Q[t[a]], n.status < te.FETCHING ? n.fetch(s) : n.status === te.SAVED && n.load();
				for (var o in s) s.hasOwnProperty(o) && s[o]()
			}
		}, y.prototype.onload = function() {
			var e = this;
			e.status = te.LOADED, e.callback && e.callback();
			var r, t, n = e._waitings;
			for (r in n) n.hasOwnProperty(r) && (t = Q[r], t._remain -= n[r], 0 === t._remain && t.onload());
			delete e._waitings, delete e._remain
		}, y.prototype.fetch = function(e) {
			function t() {
				b.request(s.requestUri, s.onRequest, s.charset, s.crossorigin)
			}
			function n() {
				delete Z[o], ee[o] = !0, Y && (y.save(a, Y), Y = null);
				var e, r = re[o];
				for (delete re[o]; e = r.shift();) e.load()
			}
			var i = this,
				a = i.uri;
			i.status = te.FETCHING;
			var s = {
				uri: a
			};
			O("fetch", s);
			var o = s.requestUri || a;
			return !o || ee[o] ? (i.load(), r) : Z[o] ? (re[o].push(i), r) : (Z[o] = !0, re[o] = [i], O("request", s = {
				uri: a,
				requestUri: o,
				onRequest: n,
				charset: w(A.charset) ? A.charset(o) : A.charset,
				crossorigin: w(A.crossorigin) ? A.crossorigin(o) : A.crossorigin
			}), s.requested || (e ? e[s.requestUri] = t : t()), r)
		}, y.prototype.exec = function() {
			function e(r) {
				return y.get(e.resolve(r)).exec()
			}
			var t = this;
			if (t.status >= te.EXECUTING) return t.exports;
			t.status = te.EXECUTING;
			var i = t.uri;
			e.resolve = function(e) {
				return y.resolve(e, i)
			}, e.async = function(r, t) {
				return y.use(r, t, i + "_async_" + n()), e
			};
			var a = t.factory,
				s = w(a) ? a(e, t.exports = {}, t) : a;
			return s === r && (s = t.exports), delete t.factory, t.exports = s, t.status = te.EXECUTED, O("exec", t), s
		}, y.resolve = function(e, r) {
			var t = {
				id: e,
				refUri: r
			};
			return O("resolve", t), t.uri || b.resolve(t.id, r)
		}, y.define = function(e, t, n) {
			var i = arguments.length;
			1 === i ? (n = e, e = r) : 2 === i && (n = t, D(e) ? (t = e, e = r) : t = r), !D(t) && w(n) && (t = m("" + n));
			var a = {
				id: e,
				uri: y.resolve(e),
				deps: t,
				factory: n
			};
			if (!a.uri && $.attachEvent) {
				var s = E();
				s && (a.uri = s.src)
			}
			O("define", a), a.uri ? y.save(a.uri, a) : Y = a
		}, y.save = function(e, r) {
			var t = y.get(e);
			t.status < te.SAVED && (t.id = r.id || e, t.dependencies = r.deps || [], t.factory = r.factory, t.status = te.SAVED)
		}, y.get = function(e, r) {
			return Q[e] || (Q[e] = new y(e, r))
		}, y.use = function(r, t, n) {
			var i = y.get(n, D(r) ? r : [r]);
			i.callback = function() {
				for (var r = [], n = i.resolve(), a = 0, s = n.length; s > a; a++) r[a] = Q[n[a]].exec();
				t && t.apply(e, r), delete i.callback
			}, i.load()
		}, y.preload = function(e) {
			var r = A.preload,
				t = r.length;
			t ? y.use(r, function() {
				r.splice(0, t), y.preload(e)
			}, A.cwd + "_preload_" + n()) : e()
		}, b.use = function(e, r) {
			return y.preload(function() {
				y.use(e, r, A.cwd + "_use_" + n())
			}), b
		}, y.define.cmd = {}, e.define = y.define, b.Module = y, A.fetchedList = ee, A.cid = n, b.require = function(e) {
			var r = y.get(y.resolve(e));
			return r.status < te.EXECUTING && (r.onload(), r.exec()), r.exports
		};
		var ne = /^(.+?\/)(\?\?)?(seajs\/)+/;
		A.base = (F.match(ne) || ["", F])[1], A.dir = F, A.cwd = k, A.charset = "utf-8", A.preload = function() {
			var e = [],
				r = location.search.replace(/(seajs-\w+)(&|$)/g, "$1=1$2");
			return r += " " + $.cookie, r.replace(/(seajs-\w+)=1/g, function(r, t) {
				e.push(t)
			}), e
		}(), b.config = function(e) {
			for (var r in e) {
				var t = e[r],
					n = A[r];
				if (n && _(n)) for (var i in t) n[i] = t[i];
				else D(n) ? t = n.concat(t) : "base" === r && ("/" !== t.slice(-1) && (t += "/"), t = l(t)), A[r] = t
			}
			return O("config", e), b
		}
	}
}(this);
!
function() {
	var e = document.getElementById("seajsnode").getAttribute("src"),
		s = e.lastIndexOf("?") > 0 ? e.substring(e.lastIndexOf("?") + 1) : "20160705",
		t = $("[data-init]"),
		o = [];
	t.each(function() {
		o.push(this.getAttribute("data-init"))
	}), seajs.config({
		paths: {
			_3rdlibs: $("#seajsnode").attr("3rdlibs") ? $("#seajsnode").attr("3rdlibs") : "//cnstatic01.e.vhall.com/3rdlibs"
		},
		debug: !0,
		alias: {
			ejs: "_3rdlibs/ejs/2.4.2/ejs.min.js",
			"datetimepicker-js": "_3rdlibs/bootstrap-datetimepicker/2.0.0/js/bootstrap-datetimepicker.min.js",
			pushstream: "_3rdlibs/nginx-push-stream/0.5.1/pushstream.js",
			echarts: "_3rdlibs/echarts/3.0.2/echarts.min.js",
			"3.3.2/echarts": "_3rdlibs/echarts/3.3.2/echarts.min.js",
			zeroclipboard: "_3rdlibs/zeroclipboard/2.2.0/ZeroClipboard.min.js",
			swfobject: "_3rdlibs/swfobject/2.2.0/swfobject.js",
			jiathis: "http://v3.jiathis.com/code/jia.js",
			jweixin: "//res.wx.qq.com/open/js/jweixin-1.1.0.js",
			jqueryObject: "jqueryObject.js",
			"jquery-json": "_3rdlibs/jquery-json/2.4.0/jquery.json.min.js",
			base64: "_3rdlibs/base64/0.0.1/base64.js",
			"socket.io": "_3rdlibs/socket.io/1.3.5/socket.io.js",
			swfupload: "_3rdlibs/swfupload/2.0.0/swfupload.js",
			mCustomScrollbar: "_3rdlibs/mCustomScrollbar/3.0.8/jquery.mCustomScrollbar.concat.min.js",
			slotmachine: "_3rdlibs/jquery-slotMachine/2.0.0/jquery.slotmachine.js",
			"3rdlibs/particles": "_3rdlibs/particles/2.0.0/particles.min.js",
			iscroll: "_3rdlibs/iscroll/build/iscroll.js",
			_: "_3rdlibs/underscore/1.8.2/underscore.min.js",
			"jquery-danmu": "_3rdlibs/jquery-danmu/dist/jquery.danmu.min.js",
			hammer: "_3rdlibs/hammer/2.0.8/hammer.min.js",
			"base/vhall-pushstream": "project/base/vhall_pushstream.js",
			placeholder: "jquery.placeholder.min.js",
			init: "utils/init.js",
			qqFace: "utils/qqFace.js",
			downCount: "utils/downCount.js",
			popup: "utils/popup.js",
			"js-page": "utils/js-page.js",
			modal: "utils/modal.js",
			"utils/formEffect": "utils/formEffect.js",
			"utils/validator": "utils/validator.js",
			"utils/avatar": "utils/avatar.js",
			share: "utils/share.js",
			"utils/custom-share": "utils/custom-share.js",
			"utils/contextMenu": "utils/contextMenu.js",
			"utils/avalon": "utils/avalon.min.js",
			host: "project/initiator/host.js",
			sass22: "project/sass2.2/sass22.js",
			formEffect: "project/sass2.2/formEffect.js",
			"sass22-flow": "project/sass2.2/flow.js",
			verification: "project/sass2.2/verification.js",
			sass22flow: "project/sass2.2/sass22Flow.js",
			"sass22-orderlist": "project/sass2.2/orderlist.js",
			"sass22-loglist": "project/sass2.2/loglist.js",
			"sass2.2/usage": "project/sass2.2/usage.js",
			overviews: "project/sub-account/overviews.js",
			"sub-account": "project/sub-account/account.js",
			"sub-edit": "project/sub-account/edit.js",
			"sub-setup": "project/sub-account/setup.js",
			"sub-role": "project/sub-account/role.js",
			"sub-usage": "project/sub-account/usage.js",
			"sub-flowbeyond": "project/sub-account/flowbeyond.js",
			"sub-webinar": "project/sub-account/webinar.js",
			"sub-account-center": "project/sub-account/accountson.js",
			"sub-recordpart": "project/sub-account/sub-recordpart.js",
			"sub-income": "project/sub-account/income.js",
			"sub-usage-date": "project/sub-account/sub-usage.js",
			"sub-index": "project/sub-account/index.js",
			"sub-usage-son": "project/sub-account/usageson.js",
			"sub-orderinfo": "project/sub-account/orderinfo.js",
			hostRecordvideo: "project/basePage/hostRecordvideo.js",
			"webinar-join": "project/basePage/webinar_join.js",
			"embed/embedPage": "project/embed/embedPage.js",
			"embed/watcher": "project/embed/watcher.js",
			"embed/playbacker": "project/embed/playbacker.js",
			"embed/active": "project/embed/active.js",
			"webinar/header": "project/webinar/header.js",
			"webinar/active": "project/webinar/active.js",
			"webinar/survey": "project/webinar/jqlib.survey.js",
			"webinar/sign": "project/webinar/jqlib.sign.js",
			"webinar/userlist": "project/webinar/userlist.js",
			"webinar/groupDom": "project/webinar/groupDom.js",
			"webinar/record": "project/webinar/record.js",
			"webinar/login": "project/webinar/login.js",
			"webinar/chatContextMenu": "project/webinar/chatContextMenu.js",
			flashInit: "project/base/flashInit.js",
			hostSocketSupport: "project/base/hostSocketSupport.js",
			vhallApp: "project/base/vhallApp.js",
			liveHost: "project/base/liveHost.js",
			myBroadcast: "project/base/myBroadcast.js",
			liveHostSupport: "project/base/liveHostSupport.js",
			vhallSocket: "project/base/vhallSocket.js",
			common: "project/base/common.js",
			accountSettings: "project/sass2.2/accountSettings.js",
			question: "project/question/question.js",
			"question/init": "project/question/init.js",
			"chat/chat": "project/chat/chat.js",
			"chat/onoffline": "project/chat/onoffline.js",
			"chat/private": "project/chat/private.js",
			"chat/pay": "project/chat/pay.js",
			"chat/question": "project/chat/question.js",
			"chat/favs": "project/chat/favs.js",
			"chat/listQueue": "project/chat/listQueue",
			"chat/envelope": "project/chat/envelope.js",
			"socket/socket.io": "project/socket/socket.io.js",
			"socket/live_guest": "project/socket/live_guest.js",
			"flash/interface": "project/flash/interface.js",
			watcher: "project/watch/watcher.js",
			playbacker: "project/watch/playback.js",
			"account/login": "project/account/login.js",
			"account/register": "project/account/register.js",
			"app/batchrecharge": "project/app/batchrecharge.js",
			"app/batchpay": "project/app/batchpay.js",
			"app/batchcheck": "project/app/batchcheck.js",
			"app/recharge": "project/app/recharge.js",
			"statistics/zhuge": "project/statistics/zhuge.js",
			"statistics/mobile_webinar": "project/statistics/mobile_webinar.js",
			"mobile/embed": "project/mobile/embed.js",
			"mobile/bootstrap.modal": "project/mobile/bootstrap.modal.js",
			"mobile/join": "project/mobile/join.js",
			"mobile/watch": "project/mobile/watch.js",
			"mobile/playback": "project/mobile/playback.js",
			"mobile/watchcommon": "project/mobile/watchcommon.js",
			"mobile/marquee_m": "project/mobile/marquee_m.js",
			"mobile/eVideo": "project/mobile/eVideo.js",
			"mobile/login": "project/mobile/login.js",
			"mobile/chat": "project/mobile/chat.js",
			"mobile/private": "project/mobile/private.js",
			"mobile/question": "project/mobile/question.js",
			"mobile/template": "project/mobile/template.js",
			"mobile/live_mobile": "project/mobile/live_mobile.js",
			"mobile/doc": "project/mobile/doc.js",
			"mobile/vhallApp": "project/mobile/vhallApp.js",
			"mobile/envelope": "project/mobile/envelope.js",
			"mobile/pay": "project/mobile/pay.js",
			"mobile/wxpay": "project/mobile/wxpay.js",
			"mobile/wxshare": "project/mobile/wxshare.js",
			"mobile/inituser_join": "project/mobile/inituser_join.js",
			"mobile/auto_play_line": "project/mobile/auto_play_line.js",
			"mobile/dispatch": "project/mobile/dispatch.js",
			"mobile/dispatch_back": "project/mobile/dispatch_back",
			"gifts/gifts": "project/gifts/gifts.js",
			"chat/gifts": "project/chat/gifts.js",
			"mobile/gifts": "project/mobile/gifts.js",
			"material/gift": "project/material/gift.js",
			"mywebinar/gift": "project/mywebinar/gift.js",
			"vhall3/account/center": "project/vhall3/account/center.js",
			"vhall3/account/orderlist": "project/vhall3/account/orderlist.js",
			"vhall3/account/business": "project/vhall3/account/business.js",
			"account-file/account-file": "project/account-file/account-file.js",
			"mywebinar/stat": "project/mywebinar/stat.js",
			"vhall3/user/homepage": "project/vhall3/user/homepage.js",
			"vhall3/user/homepage_m": "project/vhall3/user/homepage_m.js",
			"mywebinar/infocollection": "project/mywebinar/infocollection.js",
			"mywebinar/watchlist": "project/mywebinar/watchlist.js",
			"mywebinar/onplaywatch": "project/mywebinar/onplaywatch.js",
			"mywebinar/questionlist": "project/mywebinar/questionlist.js",
			"mywebinar/envelope": "project/mywebinar/envelope.js",
			"mywebinar/chatlist": "project/mywebinar/chatlist.js",
			"mywebinar/stat": "project/mywebinar/stat.js",
			"vhall3/skin": "project/vhall3/skin/skin.js",
			"vhall3/wap-menu-setting": "project/vhall3/skin/wap-menu-setting.js",
			"mywebinar/stat": "project/mywebinar/stat.js",
			"vhall3/mywebinar/keywords": "project/vhall3/mywebinar/keywords.js",
			"vhall3/mywebinar/playerconfig": "project/vhall3/mywebinar/playerconfig.js",
			"vhall3/mywebinar/privilege": "project/vhall3/mywebinar/privilege.js",
			"vhall3/mywebinar/keywords": "project/vhall3/mywebinar/keywords.js",
			"mywebinar/watchlist-for-invi": "project/mywebinar/watchlist-for-invi.js",
			"vhall3/mywebinar/product": "project/vhall3/mywebinar/product.js",
			"vhall3/mywebinar/poster": "project/vhall3/mywebinar/poster.js",
			"vhall3/mywebinar/advertising": "project/vhall3/mywebinar/advertising.js",
			productCommon: "project/watch/productCommon.js",
			mobileProduct: "project/mobile/mobileProduct.js",
			"vhall3/invite/invite_card": "project/vhall3/invite/invite_card.js",
			"vhall3/marketing/marketing-index": "project/vhall3/marketing/marketing-index.js",
			"vhall3/mywebinar/bindphone": "project/vhall3/mywebinar/bindphone.js",
			"vhall3/subjects/index": "project/vhall3/subjects/index.js",
			"vhall3/subjects/store": "project/vhall3/subjects/store.js",
			"vhall3/subjects/view": "project/vhall3/subjects/view.js",
			"vhall3/subjects/view_mobile": "project/vhall3/subjects/view_mobile.js",
			"mobile/board": "project/mobile/board.js",
			"mobile/cuepoint": "project/mobile/cuepoint.js"
		},
		map: [
			[/^(.*\/.*\.(?:css|js))(?:.*)$/i, "$1?" + s]
		]
	}), seajs.use(o)
}();