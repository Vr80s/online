


var onOff = true, isFilter = true;

    //聊天消息
    function liaotian(obj){
    	console.log("nnnn:"+obj);
        var role_str = "";
	    var role= obj.role;
	    var str_hide = "no";
        if(role=='assistant'){
	    	role_str +="<a class='tips assistant' data-role='yes' href='javascript:;'>助理</a>";
	    	str_hide = "yes";
	    } else if(role == 'host'){
	    	role_str +="<a class='tips host' data-role='yes' href='javascript:;'>主播</a>";
	    	str_hide = "yes";
	    }
	    else if(role == 'guest'){
	    	role_str +="<a class='tips guest' data-role='yes' href='javascript:;'>嘉宾</a>";
	    	str_hide = "yes";
	    }
//	    头像注释
	    role_str+="<a class='name' href='javascript:;' title=' user_name'>"+obj.user_name+" </a>";
	    
	    var className = "";
	    if(!isFilter && role =="user" ){
	    	className = "hide";
	    }
	    var aaa = "<li uid=' user_id' class="+className+"  data-role="+str_hide+">"+
	    /*聊天区域*/
	        "<div class='msg'>"+
	        "<p> " + role_str+"：<span style='color:#fff;'>"+obj.content+"</span></p >"+
	        "</div>"+
	      "</li>";
	    return aaa;
    }
    
    //进入直播间
    function liveToGoOut(obj,falg){
	  /*  console.log("参与人数："+obj.data.attend_count);
        console.log("当前在线人数："+obj.data.concurrent_user);*/
        var content = "进入直播间";
        if(falg == 1){
            content = "进入直播间";
        }else if(falg == 2){
        	content = "退出直播间";
        }
	    var  role_str="<a class='name' href='javascript:;' title=' user_name'>"+obj.user_name+" </a>";
	    var className = "";
	    if(!isFilter){
	    	className = "hide";
	    }
	    var aaa = "<li uid=' user_id' data-role='no'  class="+className+" >"+
	    /*聊天区域*/
	        "<div class='msg'>"+
	        	"<p> " + role_str+"：<span style='color:#fff;'>"+obj.content+"</span></p >"+
	        "</div>"+
	      "</li>";
	    return aaa;
    }
    //送礼
    function liveGiftList(obj){
  	    var  role_str="<a class='name' href='javascript:;' title=' user_name'>"+obj.user_name+" </a>";
	    var className = "";
	    if(!isFilter){
	    	className = "hide";
	    }
	    var aaa = "<li uid=' user_id' data-role='no'  class="+className+" >"+
  	    /*聊天区域*/
  	        "<div class='msg'>"+
  	        	"<p> " + role_str+"：<span style='color:#fff;'>"+obj.content+"</span></p >"+
  	        "</div>"+
  	      "</li>";
  	    return aaa;
      }


$(document).ready(function() {
	
	var map;
    $.ajax({
        url: '/weihou/getWeihouSign',
        method: 'get',
        data: {videoId:room_id},
        dataType: 'json',
        contentType: 'application/json',
        async:false,
        success: function (data) {
            if (data.success) {
            	map  = data.resultObject;
            } else {
               alert("获取微吼签名信息有误");
            }
        }
    });
	
	function t(t) {
		var o = new RegExp("(^|&)" + t + "=([^&]*)(&|$)", "i"),
			e = window.location.search.substr(1).match(o);
		return null != e ? decodeURIComponent(e[2]) : ""
	}
	(function() {
		for (var t = navigator.userAgent, o = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"], e = !0, n = 0; n < o.length; n++) if (t.indexOf(o[n]) > 0) {
			e = !1;
			break
		}
		return e
	})() && $("body").addClass("page-pc"), $("#exchange").click(function() {
		$("#modal").show()
	}),
	$("#lines").on("click", "li", function() {
		var t = $(this).text();
		$(this).siblings("li").removeClass("active"), $(this).addClass("active"), VHALL_SDK.player.setPlayerLine(t), $("#modal").hide(), $(".help-tool").click()
	}), $("#definitions").on("click", "li", function() {
		var t = $(this).text();
		$(this).siblings("li").removeClass("active"), $(this).addClass("active"), VHALL_SDK.player.setPlayerDefinition(t), $("#modal").hide(), $(".help-tool").click()
	}), $(".header").on("click", "a", function() {
		var t = $(this).attr("data-target");
		$(".header a").removeClass("active"), $(this).addClass("active"), $(".tab-pane").removeClass("active"), $("." + t).addClass("active"), "notice-msg-box" == t ? $(".send-box").hide() : $(".send-box").show()
	}), $("#userEvent").click(function() {
		$("#userCusEvent").show(), $("#userCusEvent .layer-textarea").val(JSON.stringify({
			type: "xxx_event",
			data: {
				id: 1,
				name: "test"
			}
		}))
	}), $("#userCusEvent img").click(function() {
		$("#userCusEvent").hide()
	}), $("#userCusEvent .layer-btn").click(function() {
		try {
			var t = JSON.parse($("#userCusEvent .layer-textarea").val());
			VHALL_SDK.sendCustomEvent(t), $("#userCusEvent img").click()
		} catch (t) {
			alert("JSON不合法")
		}
	}), $("#userData").click(function() {
		$("#userinfo").show()
	}), $("#userinfo img").click(function() {
		$("#userinfo").hide()
	}), $("#userinfo .layer-btn").click(function() {
		var t = $("#userinfo .layer-textarea").val();
		VHALL_SDK.updateUserInfo(t), $("#userinfo img").click()
	}), $("#hideVideo").click(function() {
		$("#video").toggleClass("on"), $("#doc").toggleClass("on")
	}),
	
	
	VHALL_SDK.init({
		facedom: "#face",
		textdom: "#mywords",
		app_key: map.app_key,
		signedat: map.signedat,
		sign: map.sign,
		email: map.email,
		roomid: map.roomid,
		account: map.account,
		username: map.username,
		videoContent: "#video",
		docContent: "#doc"
	});
	
	VHALL_SDK.on("customEvent", function(t) {
		//alert(JSON.stringify(t))
	}), VHALL_SDK.on("chatMsg", function(t) {
	
		$("#chatmsg").append(liaotian(t)), $(".chatmsg-box").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "99999")
	
	
	}), VHALL_SDK.on("questionMsg", function(t) {
	
		//console.log("问答", t), $("#question-msg").append(e(t)), $(".question-msg-box").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "99999")
	
	
	}), VHALL_SDK.on("ready", function() {
		VHALL_SDK.vhall_get_history_notice(), 1 == VHALL_SDK.getRoominfo().type ? (VHALL_SDK.vhall_get_live_history_chat_msg(), 1 == VHALL_SDK.getRoominfo().openQuestion && VHALL_SDK.vhall_get_live_history_question_msg()) : VHALL_SDK.vhall_get_record_history_chat_msg()
	}), VHALL_SDK.on("error", function(t) {
		alert("发生错误: " + JSON.stringify(t))
	}), VHALL_SDK.on("userOnline", function(t) {
		//用户上线
		
		console.log(t);
		$("#chatmsg").append(liveToGoOut(t,1));
		$(".chatmsg-box").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "999999");
		
	}), VHALL_SDK.on("userOffline", function(t) {
		//用户下线
		
		console.log(t)
		
		$("#chatmsg").append(liveToGoOut(t,2));
		$(".chatmsg-box").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "999999");
		
	}), VHALL_SDK.on("sendSign", function(t) {
		console.log(t)
	}), VHALL_SDK.on("UpdateUser", function(t) {
		console.log(t)
	}), VHALL_SDK.on("sendCustomEvent", function(t) {
		console.log(t)
	}), VHALL_SDK.on("sendChat", function(t) {
		console.log(t)
	}), VHALL_SDK.on("sendChat", function(t) {
		console.log(t)
	}), VHALL_SDK.on("sendQuestion", function(t) {
		console.log(t)
	}), VHALL_SDK.on("disableChat", function(t) {
		console.log("禁言", t)
	}), VHALL_SDK.on("permitChat", function(t) {
		console.log("恢复禁言", t)
	}), VHALL_SDK.on("forbidChat", function(t) {
		console.log("全体禁言", t)
	}), VHALL_SDK.on("kickout", function(t) {
		console.log("踢出", t)
	}), VHALL_SDK.on("kickoutRestore", function(t) {
		console.log("恢复踢出", t)
	}), VHALL_SDK.on("questionSwitch", function(t) {
		1 == t.status ? alert("问答已开启") : alert("问答已关闭")
	}), Date.prototype.Format = function(t) {
		var o = {
			"M+": this.getMonth() + 1,
			"d+": this.getDate(),
			"h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12,
			"H+": this.getHours(),
			"m+": this.getMinutes(),
			"s+": this.getSeconds(),
			"q+": Math.floor((this.getMonth() + 3) / 3),
			S: this.getMilliseconds()
		};
		/(y+)/.test(t) && (t = t.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length)));
		for (var e in o) new RegExp("(" + e + ")").test(t) && (t = t.replace(RegExp.$1, 1 == RegExp.$1.length ? o[e] : ("00" + o[e]).substr(("" + o[e]).length)));
		return t
	}, VHALL_SDK.on("announcement", function(t) {
		var o = "<li><div>" + t + "</div><span>" + (new Date).Format("yyyy-MM-dd HH:mm:ss") + "</span></li>";
		$("#notice-msg>ul").append(o)
	}), VHALL_SDK.on("vhall_history_notice", function(t) {
		if ("200" == t.code) {
			var o = "";
			$.each(t.data.data, function(t, e) {
				o = "<li><div>" + JSON.parse(e.content).content + "</div><span>" + e.created_at + "</span></li>" + o
			}), $("#notice-msg>ul").html(o)
		} else console.log(t.msg)
	}), VHALL_SDK.on("startSign", function(t) {
		function o() {
			setTimeout(function() {
				e.text(--n + "秒"), 0 != n && $(".sign").hasClass("active") ? o() : ($(".sign").removeClass("active"), e.text("0秒"), e = null)
			}, 1e3)
		}
		var e = $(".sign .sign-second"),
			n = t.sign_show_time;
		e.text(n + "秒"), $(".sign").data("signID", t.sign_id).addClass("active"), $(".sign-btn").click(function() {
			$(".sign").removeClass("active"), VHALL_SDK.sendSign($(".sign").data("signID"))
		}), o()
	}), VHALL_SDK.on("streamOver", function(t) {
		alert("活动已结束")
	}), VHALL_SDK.on("publishStart", function(t) {
		alert("活动开始推流")
	}), VHALL_SDK.on("vhall_live_history_chat_msg", function(t) {
		if (200 == t.code) {
			for (var e = "", n = t.data.length - 1; n >= 0; n--) e += liaotian(t.data[n]);
			$("#chatmsg").append(e), setTimeout(function() {
				$(".chatmsg-box").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "999999")
			}, 50)
		}
	}), VHALL_SDK.on("vhall_record_history_chat_msg", function(t) {
		if (200 == t.code) {
			var e = "";
			$("#chatmsg").data("curr_page", t.curr_page);
			for (var n = t.data.length - 1; n >= 0; n--) e += liaotian(t.data[n]);
			1 == t.curr_page ? ($("#chatmsg").html(e), setTimeout(function() {
				$(".chatmsg-box").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "999999")
			}, 50)) : ($("#chatmsg").prepend(e), $(".chartlist").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "20px"))
		}
	}), VHALL_SDK.on("getQuestionList", function(t) {
//		if (200 == t.code) {
//			for (var o = "", n = t.data.length - 1; n >= 0; n--) o += e(t.data[n]);
//			$("#question-msg").append(o), setTimeout(function() {
//				$(".chatmsg-box").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "999999")
//			}, 50)
//		}
	}), $(".chatmsg-box").mCustomScrollbar({
		scrollInertia: 200,
		callbacks: {
			onTotalScrollBack: function() {
				if ("1" != VHALL_SDK.room.type) {
					var t = parseInt($("#chatmsg").data("curr_page"));
					VHALL_SDK.vhall_get_record_history_chat_msg(t + 1)
				}
			}
		}
	}), $("#sendChat").click(function() { //发送聊天消息
		
		 var room = VHALL_SDK.getRoominfo();
		 if (room.type != 1){
			 return;
		 }
		var t = $("#mywords").val(),
			n = null;
		
		
		$(".tab-pane.active").hasClass("chatmsg-box") ? ((n = VHALL_SDK.sendChat({
			text: t
		})) && $("#chatmsg").append(liaotian(n)), $("#mywords").val(""), $(".chatmsg-box").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "999999")) : ((n = VHALL_SDK.sendQuestion({
			text: t
		})) && $("#question-msg").append(e(n)), $("#mywords").val(""), $(".question-box").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "999999"))
	
	}), 
	$("#filter-msg").on("click", function() {//只看主办方消息   
		
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
	}),
	
	
	
	VHALL_SDK.on("playerError", function(t) {
		console.log(t)
	}), VHALL_SDK.on("playerReady", function() {
		VHALL_SDK.player.on("canPlayLines", function(t) {
			var o = "";
			t.forEach(function(t) {
				o += "<li>" + t + "</li>"
			}), $("#lines").html(o).find("li").eq(0).addClass("active")
		}), VHALL_SDK.player.on("canPlayDefinitions", function(t) {
			var o = "";
			t.forEach(function(t) {
				o += "<li>" + t + "</li>"
			}), $("#definitions").html(o).find("li").eq(0).addClass("active")
		})
	}), $(".sign-header>img").click(function(t) {
		t.stopPropagation(), $(".sign").removeClass("active"), $(".sign .sign-second").text("0秒")
	}), $(".help-tool").click(function(t) {
		$("#userEvent").toggleClass("active"), setTimeout(function() {
			$("#userData").toggleClass("active")
		}, 100), setTimeout(function() {
			$("#exchange").toggleClass("active")
		}, 200), setTimeout(function() {
			$("#hideVideo").toggleClass("active")
		}, 300)
	})
})