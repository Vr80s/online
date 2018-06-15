$(function(){

	// 开始直播
	DWLive.onLiveStart = function(j){
		// console.log(j);
	};

	// 停止直播
	DWLive.onLiveEnd = function(j){
		// console.log(j);
	};

	// 在线人数
	DWLive.onUserCountMessage = function(j){
		// console.log(j);
	};

	// 开始直播后显示公告
	DWLive.onAnnouncementShow = function (j) {
		console.log(j);
	};

	// 修改公告,发布公告
	DWLive.onAnnouncementRelease = function (j) {
		console.log(j);
	};

	// 删除公告
	DWLive.onAnnouncementRemove = function (j) {
		console.log(j);
	};

	// 设置音量
	$(".setsound").click(function(){
		DWLive.setSound(0.5);  // 设置音量(0-1)
	});

	// 接收公聊
	DWLive.onPublicChatMessage = function(j){
		console.log(j)
		var o = JSON.parse(j);
		var d = '<li name="' + o.username + '" uid="' + o.userid + '">'
					+'<div class="peo-infos">'
					+	'<p class="peo-names">'
					+		'<span class="p-n-names"><span class="name-tip">' + o.username + '</span></span>'
					+		'<i class="peo-icons "></i>'
					+	'</p>'
                    +   '<div class="hide" name="tipBtn">'
                    +       '<ul class="btnul">'
                    +           '<li name="whisper">私聊</li>'
                    +       '</ul>'
                    +       '<span class="btnul-arrow"></span>'
                    +   '</div>'
					+'</div>'
					+'<div class="peo-chat">'
					+	'<i class="icons"></i>'
					+	'<p class="chat-content">' + showEm(o.msg) + '</p>'
					+'</div>'
				+'</li>';
		$("#chat-list").append(d);

		$("#chat-list").parent().scrollTop($("#chat-list").height());

		if(o.username == DWLive.viewername){
			$("#chat-list li[name = " + o.username + "]").addClass("me");
		}

		DWLive.barrage(o.msg); // 发送弹幕
	};

	// 接收私聊
	DWLive.onPrivateChatMessage = function(j){
		var o = JSON.parse(j);
		var d = '<li name="' + o.fromusername + '">'
					+'<div class="peo-infos">'
					+	'<p class="peo-names">'
					+		'<span class="p-n-names">' + o.fromusername + '&nbsp;对&nbsp;' + o.tousername + '&nbsp;说</span>'
					+		'<i class="peo-icons "></i>'
					+	'</p>'
					+'</div>'
					+'<div class="peo-chat">'
					+	'<i class="icons"></i>'
					+	'<p class="chat-content pchat">' + showEm(o.msg) + '</p>'
					+'</div>'
				+'</li>';
		$("#chat-list").append(d);

		$("#chat-list").parent().scrollTop($("#chat-list").height());

		if(o.fromusername == DWLive.viewername){
			$("#chat-list li[name = " + o.fromusername + "]").addClass("me");
		}
	};

	// 接收私聊回复
	DWLive.onPrivateAnswer = function(j){
		var o = JSON.parse(j);
		var d = '<li name="' + o.fromusername + '">'
					+'<div class="peo-infos">'
					+	'<p class="peo-names">'
					+		'<span class="p-n-names">' + o.fromusername + '&nbsp;对&nbsp;' + o.tousername + '&nbsp;说</span>'
					+		'<i class="peo-icons "></i>'
					+	'</p>'
					+'</div>'
					+'<div class="peo-chat">'
					+	'<i class="icons"></i>'
					+	'<p class="chat-content pchat">' + showEm(o.msg) + '</p>'
					+'</div>'
				+'</li>';
		$("#chat-list").append(d);

		$("#chat-list").parent().scrollTop($("#chat-list").height());

	};


	// 提问
	DWLive.onQuestion = function(j){
		var o = JSON.parse(j);
		var qid = o.value.id; 

		var d = '<li id="' + qid + '">'
				+	'<div class="peo-infos">'
				+		'<p class="peo-names">'
				+			'<span class="p-n-names">' + o.value.userName + '</span>'
				+			'<i class="peo-icons"></i>'
				+		'</p>'
				+	'</div>'
				+	'<div class="peo-chat">'
				+		'<p class="chat-content">' + o.value.content + '</p>'
				+	'</div>'
				+'</li>';
		$("#question-main").append(d);
		$("#question-main").parent().scrollTop($("#question-main").height());

		if(o.value.userName !== DWLive.viewername){
			$("#" + qid).addClass("not-mines");
		}
	};

	// 接收回答
	DWLive.onAnswer = function(j){
		var o = JSON.parse(j);
		var answer = o.value;
		console.log(j);
		// 私密回答只能自己看
		if (answer.questionUserId !== DWLive.userid && answer.isPrivate) {
			return;
		}

		var qid = o.value.questionId;
		var d = 	'<div class="peo-repeat">'
				+		'<p class="teacher-name">'
				+			'<i></i>' + o.value.userName + ''
				+		'</p>'
				+		'<p class="repeat-content">' + o.value.content + '</p>'
				+	'</div>';
		$("#" + qid).append(d).show();
		$("#question-main").parent().scrollTop($("#question-main").height());
	};

	// 禁言
	DWLive.onInformation = function(j){
		var chat = $('#chat-content'),
            chatX = chat.offset().left,
            chatY = chat.offset().top;
        tips(chatX, chatY, '您已经被禁言！');
		return;
	};

	var chatTime = 0;
	function chatSend(){
		
		if(chatTime > 0){
			return;
		}else{
			chatTime = 10;
			var t = setInterval(function() { 
				$('#chat-submit').html(chatTime);
				chatTime--;
				if(chatTime <= 0){
					$('#chat-submit').html('发送');
					clearInterval(t);
				}
		     }, 1000);
		}
		
		var msg = Tools.formatContent($.trim($("#chat-content").val()));

        if(!msg){
        	var chat = $('#chat-content'),
	            chatX = chat.offset().left,
	            chatY = chat.offset().top;
            tips(chatX, chatY, '请输入您的聊天内容！');
			return;
		}

		if (msg.length > 300) {
			tips(chatX, chatY, '聊天不能超过300个字符');
			return;
		}

        var nmsg = '';
        $.each(msg.split(' '), function (i, n) {
            var ur = /^(https?|s?ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;
            if (ur.test(n)) {
                nmsg += '[uri_' + n + '] '
            } else {
                nmsg += n + ' ';
            }
        });

        var touserid = $('.select-current').attr('id');
        var tousername = $('.select-current').html();
        if (touserid == 'all') {
            DWLive.sendPublicChatMsg(nmsg); // 发送公聊
        } else {
            DWLive.sendPrivateChatMsg(touserid, tousername, nmsg); // 发送私聊
        }

		$("#chat-content").val('').focus();
	}

	$("#chat-submit").click(function(){
		chatSend();
	});

	$('#chat-content').bind('keypress', function(e) {
		if (e.keyCode == 13) {
			chatSend();
		}
	});

	function qaSend(){
		var qa = $('#question-content');
		var msg = Tools.formatContent($.trim(qa.html()));
		var chatX = qa.offset().left,
		    chatY = qa.offset().top;

		if(!msg){
	        tips(chatX, chatY, '请输入您的问题！');
			return;
		}

		if (msg.length > 300) {
			tips(chatX, chatY, '问题不能超过300个字符');
			return;
		}

		DWLive.sendQuestionMsg(msg); // 发送问题

		$("#question-content").html('').focus();
	}

	$("#question-submit").click(function(){
		qaSend();
	});

	$('#question-content').bind('keypress', function(e) {
		if (e.keyCode == 13) {
			qaSend();
		}
	});

	function tips(chatX, chatY, msg){
    	$('#input-tips').find('p').text(msg);
    	$('#input-tips').css({'left':chatX,'top':(chatY-42)}).stop(1,1).fadeIn(200).delay(1500).fadeOut(200);
	}

	//对老师说
    $('#to-teacher').bind('click',function(){
        if(!$(this).find('i').hasClass('active')){
        	$(this).find('i').addClass('active');
        	$(this).attr('for', 'teacher');
        }else{
        	$(this).find('i').removeClass('active');
        	$(this).attr('for', 'all');
        }
    });


    // 私聊
    $("#private-name").on('click', 'li', function () {
        var uid = $(this).attr('id'),
            uname = $(this).html();
        $('.select-current').attr('id', uid).html(uname);
    });

    $('.select-span').click(function (e) {
        $('#private-name').toggle();
        $(this).toggleClass('select-active');
        $(document).one("click", function () {
            $('#private-name').hide();
            $('.select-span').removeClass('select-active');
        });
        e.stopPropagation();
    });

    // 聊天页面浮出框
    $(document).on("click", 'ul[id="chat-list"] li span.name-tip', function (e) {
        var $thisTipBtn = $(this).parent().parent().next('div[name="tipBtn"]');
        if (!$thisTipBtn.is(':hidden')) {
            $thisTipBtn.hide();
            e.stopPropagation();
            return;
        }

        $('div[name="tipBtn"]').hide();
        var $li = $(this).parent().parent().parent().parent();
        if (!$li.find('div[name="tipBtn"]').length) {
            return;
        }

        if ($(this).html() == DWLive.viewername) {
            return;
        }

        $li.find('div[name="tipBtn"]').show();
        $(document).one("click", function () {
            $('div[name="tipBtn"]').hide();
        });

        e.stopPropagation();
    });

    // 点击浮出屏蔽按钮
    $(document).on("click", 'ul[id="chat-list"] li li', function () {
        var $t = $(this);
        var action = $t.attr('name');
        var $li = $(this).parent().parent().parent().parent();
        var uid = $li.attr('uid');
        var username = $li.find('.name-tip').html();

        tipBtnHanle(action, uid, username);

        $('div[name="tipBtn"]').hide();
    });

    function tipBtnHanle(action, uid, uname) {
        if (action == 'whisper') {
            $('#private-name').prepend('<li id="' + uid + '" title="' + uname + '">' + uname + '</li>');
            $('.select-current').attr('id', uid).html(uname);
            $('#chat-content').focus();

            var option = $("#private-name").find('li[id="' + uid + '"]');
            if (option.length > 1) {
                option.eq(0).remove();
            }

        }
    }

	// 签到
    DWLive.onStartRollCall = function(data){
		if (window.ROLLCALL_INTERVAL_TIMER > 0) {
			clearInterval(window.ROLLCALL_INTERVAL_TIMER);
			window.ROLLCALL_INTERVAL_TIMER = -1;
		}

		var rid = data.rollcallId,
			pid = data.publisherId,
			time = data.duration;
		$(".signtxt").html('签到倒计时: <span id="signtime">00:00</span>').css('margin-top', '75px');
		$(".signbtn button").show();
		$("#signtime").text(timeFormat(time));
		$(".sign").show();
		$(".signbtn button").click(function () {
            DWLive.answerRollcall(rid, pid);
			$(".sign").hide();

			if (window.ROLLCALL_INTERVAL_TIMER > 0) {
				clearInterval(window.ROLLCALL_INTERVAL_TIMER);
				window.ROLLCALL_INTERVAL_TIMER = -1;
			}
		});

		window.ROLLCALL_INTERVAL_TIMER = setInterval(function () {
			if (time > 1) {
				time--;
				$("#signtime").text(timeFormat(time));
			} else {
				$(".signtxt").html('签到结束').css('margin-top', '90px');
				$(".signbtn button").hide();
				setTimeout(function () {
					$(".sign").hide();
				}, 2000);
				if (window.ROLLCALL_INTERVAL_TIMER > 0) {
					clearInterval(window.ROLLCALL_INTERVAL_TIMER);
					window.ROLLCALL_INTERVAL_TIMER = -1;
				}
			}
		}, 1000)
	};

	function timeFormat(time) {
		var t = parseInt(time),
			h, i, s;
		h = Math.floor(t / 3600);
		h = h ? (h + ':') : '';
		i = h ? Math.floor(t % 3600 / 60) : Math.floor(t / 60);
		s = Math.floor(t % 60);
		i = i > 9 ? i : '0' + i;
		s = s > 9 ? s : '0' + s;
		return (h + i + ':' + s);
	}


    // 开始抽奖
    var win = false,
        stop = false;
    DWLive.onStartLottery = function(){
        stop = false;
        $(".lottery").show();
        $(".lotterybox").hide();
        if (win == true) {
            $(".lotteryh3").html('恭喜您中奖啦');
        } else {
            $(".lotteryh3").html('正在抽奖');
        }
    };

    // 中奖
    DWLive.onWinLottery = function(data){
        var code = data.lotteryCode,
            name = data.viewerName;
        if (data.viewerId == DWLive.viewerid) {
            $(".lotterynum").html(code);
            $(".lotterybox").hide();
            $(".lotterynum, .lotterytext, .lottery").show();
            $(".lotteryh3").html('恭喜您中奖啦');
            $(".lotterynum, .lotterytext").css('z-index', 9);
            win = true;
        } else {
            $(".lotteryname").html(name);
            $(".lotterybox, .lottery").show();
            if (win == true) {
                $(".lotteryh3").html('恭喜您中奖啦');
            } else {
                $(".lotteryh3").html('哎呀，就差一点');
            }
            setTimeout(function () {
                $(".lotterybox").hide();
                if (win == true) {
                    $(".lotteryh3").html('恭喜您中奖啦');
                } else {
                    $(".lotteryh3").html('正在抽奖');
                }
            }, 2000);
        }
    };

    $(".lotteryclose").click(function () {
        if (win == true) {
            if (stop == true) {
                $(".lottery").hide();
            }
            $(".lotterynum, .lotterytext").hide();
            $(".lotteryh3").html('正在抽奖');
            win = false;
        } else {
            $(".lottery").hide();
        }
    });

    // 结束抽奖
    DWLive.onStopLottery = function(){
        stop = true;
        if (win == false) {
            setTimeout(function () {
                $(".lottery").hide();
            }, 2000);
        }
    };


	// 答题
	var option = "";
    DWLive.onLiveStartVote = function(data){
		var voteid = data.voteId,
			pid = data.publisherId,
			count = data.voteCount;
		if (count == 5) {
			$(".vote-option").append("<li>A<span></span></li><li>B<span></span></li><li>C<span></span></li><li>D<span></span></li><li>E<span></span></li>");
			$(".vote-option").css('padding-left', 10);
			$(".vote-option li").css('margin-right', 7);
			$(".vote-option li:last-child").css('margin-right', 0);
			$("#vote1").append('<div class="votebtn"><button>提交</button></div>');
		}
		if (count == 4) {
			$(".vote-option").append("<li>A<span></span></li><li>B<span></span></li><li>C<span></span></li><li>D<span></span></li>");
			$(".vote-option").css('padding-left', 28);
			$(".vote-option li").css('margin-right', 15);
			$("#vote1").append('<div class="votebtn"><button>提交</button></div>');
		}
		if (count == 3) {
			$(".vote-option").append("<li>A<span></span></li><li>B<span></span></li><li>C<span></span></li>");
			$(".vote-option").css('padding-left', 50);
			$(".vote-option li").css('margin-right', 25);
			$("#vote1").append('<div class="votebtn"><button>提交</button></div>');
		}
		if (count == 2) {
			$(".vote-option").append("<li>A<span></span></li><li>B<span></span></li>");
			$(".vote-option li").eq(0).addClass("vote-r");
			$(".vote-option li").eq(1).addClass("vote-w");
			$(".vote-option").css('padding-left', 80);
			$(".vote-option li").css('margin-right', 40);
			$("#vote1").append('<div class="votebtn"><button>提交</button></div>');
		}
		$(".vote-option li:last-child").addClass("last-item");
		$(".vote").show();
		$("#vote1").show();
		$("#vote2").hide();
		$(".vote-list").empty();

		$(".vote-option li").click(function () {
			var index = $(".vote-option li").index(this);
			$(this).addClass("active").siblings().removeClass("active");
			$(".votebtn button").css('opacity', 1);
			option = index;
		});
		$(".votebtn button").click(function () {
			if ($(".vote-option li").hasClass('active')) {
                DWLive.replyVote(voteid, option, pid);
				$(".vote").hide();
				$(".vote-option").empty();
				$(".votebtn button").css('opacity', 0.6);
				$(".votebtn").remove();
			}
		});
		$(".vote-close").click(function () {
			$(".vote").hide();
			$(".vote-option, .vote-list").empty();
			$(".votebtn button").css('opacity', 0.6);
			option = "";
			$(".votebtn").remove();
		});
	};

    // 结束答题
    DWLive.onLiveStopVote = function(){
		$(".vote").hide();
		$(".vote-option, .vote-list").empty();
		$(".votebtn button").css('opacity', 0.6);
		$(".votebtn").remove();
	};

    // 答题统计
    DWLive.onLiveVoteResult = function(data){
        var count = data.voteCount,
            coption = data.correctOption,
            statisics = data.statisics,
            zcount = "";
        switch (coption) {
            case -1:
                coption = "";
                break;
            case 0:
                if (count == 2) {
                    coption = '<i class="vote-ricon"></i>';
                } else {
                    coption = "A";
                }
                break;
            case 1:
                if (count == 2) {
                    coption = '<i class="vote-wicon"></i>';
                } else {
                    coption = "B";
                }
                break;
            case 2:
                coption = "C";
                break;
            case 3:
                coption = "D";
                break;
            case 4:
                coption = "E";
                break;
        }
        switch (option) {
            case 0:
                if (count == 2) {
                    option = '<i class="vote-ricon"></i>';
                } else {
                    option = "A";
                }
                break;
            case 1:
                if (count == 2) {
                    option = '<i class="vote-wicon"></i>';
                } else {
                    option = "B";
                }
                break;
            case 2:
                option = "C";
                break;
            case 3:
                option = "D";
                break;
            case 4:
                option = "E";
                break;
        }
        switch (count) {
            case 2:
                var li = '<li>'
                    + '<span class="spanl">√:</span>'
                    + '<div class="vote-bar">'
                    + '<div class="vote-in"></div>'
                    + '</div>'
                    + '<span class="color1">' + statisics[0].count + '人</span>'
                    + '<span>(' + statisics[0].percent + '%)</span>'
                    + '</li>'
                    + '<li class="last-item">'
                    + '<span class="spanl">X:</span>'
                    + '<div class="vote-bar">'
                    + '<div class="vote-in"></div>'
                    + '</div>'
                    + '<span class="color1">' + statisics[1].count + '人</span>'
                    + '<span>(' + statisics[1].percent + '%)</span>'
                    + '</li>';
                $(".vote-list").append(li);
                zcount = statisics[0].count + statisics[1].count;
                break;
            case 3:
                var li = '<li>'
                    + '<span class="spanl">A:</span>'
                    + '<div class="vote-bar">'
                    + '<div class="vote-in"></div>'
                    + '</div>'
                    + '<span class="color1">' + statisics[0].count + '人</span>'
                    + '<span>(' + statisics[0].percent + '%)</span>'
                    + '</li>'
                    + '<li>'
                    + '<span class="spanl">B:</span>'
                    + '<div class="vote-bar">'
                    + '<div class="vote-in"></div>'
                    + '</div>'
                    + '<span class="color1">' + statisics[1].count + '人</span>'
                    + '<span>(' + statisics[1].percent + '%)</span>'
                    + '</li>'
                    + '<li class="last-item">'
                    + '<span class="spanl">C:</span>'
                    + '<div class="vote-bar">'
                    + '<div class="vote-in"></div>'
                    + '</div>'
                    + '<span class="color1">' + statisics[2].count + '人</span>'
                    + '<span>(' + statisics[2].percent + '%)</span>'
                    + '</li>';
                $(".vote-list").append(li);
                zcount = statisics[0].count + statisics[1].count + statisics[2].count;
                break;
            case 4:
                var li = '<li>'
                    + '<span class="spanl">A:</span>'
                    + '<div class="vote-bar">'
                    + '<div class="vote-in"></div>'
                    + '</div>'
                    + '<span class="color1">' + statisics[0].count + '人</span>'
                    + '<span>(' + statisics[0].percent + '%)</span>'
                    + '</li>'
                    + '<li>'
                    + '<span class="spanl">B:</span>'
                    + '<div class="vote-bar">'
                    + '<div class="vote-in"></div>'
                    + '</div>'
                    + '<span class="color1">' + statisics[1].count + '人</span>'
                    + '<span>(' + statisics[1].percent + '%)</span>'
                    + '</li>'
                    + '<li>'
                    + '<span class="spanl">C:</span>'
                    + '<div class="vote-bar">'
                    + '<div class="vote-in"></div>'
                    + '</div>'
                    + '<span class="color1">' + statisics[2].count + '人</span>'
                    + '<span>(' + statisics[2].percent + '%)</span>'
                    + '</li>'
                    + '<li class="last-item">'
                    + '<span class="spanl">D:</span>'
                    + '<div class="vote-bar">'
                    + '<div class="vote-in"></div>'
                    + '</div>'
                    + '<span class="color1">' + statisics[3].count + '人</span>'
                    + '<span>(' + statisics[3].percent + '%)</span>'
                    + '</li>';
                $(".vote-list").append(li);
                zcount = statisics[0].count + statisics[1].count + statisics[2].count + statisics[3].count;
                break;
            case 5:
                var li = '<li>'
                    + '<span class="spanl">A:</span>'
                    + '<div class="vote-bar">'
                    + '<div class="vote-in"></div>'
                    + '</div>'
                    + '<span class="color1">' + statisics[0].count + '人</span>'
                    + '<span>(' + statisics[0].percent + '%)</span>'
                    + '</li>'
                    + '<li>'
                    + '<span class="spanl">B:</span>'
                    + '<div class="vote-bar">'
                    + '<div class="vote-in"></div>'
                    + '</div>'
                    + '<span class="color1">' + statisics[1].count + '人</span>'
                    + '<span>(' + statisics[1].percent + '%)</span>'
                    + '</li>'
                    + '<li>'
                    + '<span class="spanl">C:</span>'
                    + '<div class="vote-bar">'
                    + '<div class="vote-in"></div>'
                    + '</div>'
                    + '<span class="color1">' + statisics[2].count + '人</span>'
                    + '<span>(' + statisics[2].percent + '%)</span>'
                    + '</li>'
                    + '<li>'
                    + '<span class="spanl">D:</span>'
                    + '<div class="vote-bar">'
                    + '<div class="vote-in"></div>'
                    + '</div>'
                    + '<span class="color1">' + statisics[3].count + '人</span>'
                    + '<span>(' + statisics[3].percent + '%)</span>'
                    + '</li>'
                    + '<li class="last-item">'
                    + '<span class="spanl">E:</span>'
                    + '<div class="vote-bar">'
                    + '<div class="vote-in"></div>'
                    + '</div>'
                    + '<span class="color1">' + statisics[4].count + '人</span>'
                    + '<span>(' + statisics[4].percent + '%)</span>'
                    + '</li>';
                $(".vote-list").append(li);
                zcount = statisics[0].count + statisics[1].count + statisics[2].count + statisics[3].count + statisics[4].count;
                break;
        }
        for (var i = 0; i < statisics.length; i++) {
            $(".vote-in").eq(i).css('width', statisics[i].percent * 1.2);
        }
        $("#vote-count").html(zcount);
        $("#yansw em").html(option);
        $("#cansw em").html(coption);
        if ($("#yansw em").html() == $("#cansw em").html()) {
            $("#yansw").addClass("vote-righta");
        } else {
            $("#yansw").removeClass("vote-righta");
        }
        $(".vote").show();
        $("#vote2").show();
        $("#vote1").hide();
        $(".votebtn").remove();
        option = "";
        $(".vote-close").click(function () {
            $(".vote").hide();
            $(".vote-list").empty();
            option = "";
        });
    }

});


