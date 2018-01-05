

/**
 * 加载请求视频
 */
$(document).ready(function() {
	
	$("#exchange").click(function(){
        $("#modal").show();
    });
    /**
	 * 播放线路
	 */
    $("#lines").on("click", 'li', function(){
        var _line = $(this).text();
        $(this).siblings('li').removeClass('active');
        $(this).addClass('active');
        VHALL_SDK.player.setPlayerLine(_line);
        $("#modal").hide();
    });
    /**
	 * 播放线路
	 */
    $("#definitions").on("click", 'li', function(){
        var _line = $(this).text();
        $(this).siblings('li').removeClass('active');
        $(this).addClass('active');
        VHALL_SDK.player.setPlayerDefinition(_line);
        $("#modal").hide();
    });
    $(".header").on('click', 'a', function(){
        var target = $(this).attr("data-target");
        $(".header a").removeClass('active');
        $(this).addClass('active');
        $(".tab-pane").removeClass("active");
        $("." + target).addClass('active');
    });
    $("#hideVideo").click(function(){
        $("#video").toggleClass("on");
        $("#doc").toggleClass("on");
        });
   
        
//        // 使用underscore.js的template模板引擎
//        var chat_template = _.template($("#chat-template").html());
//        var question_template = _.template($("#question-template").html());
    /**
	 * [onChatMessage 直播收到聊天消息]
	 * 
	 * @param {[type]}
	 *            msg [object]
	 * @return {[type]} [description]
	 */
    VHALL_SDK.on('chatMsg', function(msg) {
    	
    	/*console.log(msg);*/
    	
		var str = "<div class='coze_cen'> "+
		 "<img src="+msg.avatar+" alt='' "+
		"	class='coze_cen_left_img' /> "+
		"  <div class='coze_cen_bg'> "+
		"	<img src='/xcviews/images/sanjiao2.png' alt='' />"+msg.content+"  "+
		" </div> "+
		" <div class='both'></div></div>";
		
        $("#chatmsg").append(str);
        $(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar('scrollTo', '99999');
    });
    VHALL_SDK.on('questionMsg', function(msg) {
        console.log('问答', msg);
        //$("#question-msg").append(question_template(msg));
        $(".question-msg-box").mCustomScrollbar('update').mCustomScrollbar('scrollTo', '99999');
    });
    VHALL_SDK.on('ready', function() {
        if (VHALL_SDK.getRoominfo().type == 1) {
            // 当前直播中
            VHALL_SDK.vhall_get_live_history_chat_msg();
            VHALL_SDK.vhall_get_live_history_question_msg();
        } else {
            // 当前不在直播
            VHALL_SDK.vhall_get_record_history_chat_msg();
        }
        
        /*
		 * 房间信息
		 */
        console.log(VHALL_SDK.getRoominfo());
        /*
		 * 用户信息
		 */
        console.log(VHALL_SDK.getRoominfo());
    });
    /**
	 * [用户上线]
	 * 
	 * @param {[type]}
	 *            msg [description]
	 * @return {[type]} [description]
	 */
    VHALL_SDK.on('userOnline', function(msg) {
        console.log(msg);
    });
    /**
	 * [onUserOffline 用户下线]
	 * 
	 * @param {[type]}
	 *            msg [description]
	 * @return {[type]} [description]
	 */
    VHALL_SDK.on('userOffline', function(msg) {
        console.log(msg);
    });
    
    /**
	 * [onSendChatSuccess 消息发送回调事件]
	 * 
	 * @return {[type]} [description]
	 */
    VHALL_SDK.on('sendChat', function(msg) {
    	
        console.log(msg);
    });
    /**
	 * [onSendChatSuccess 问答消息发送回调事件]
	 * 
	 * @return {[type]} [description]
	 */
    VHALL_SDK.on('sendQuestion', function(msg) {
        console.log(msg);
    });
    /**
	 * [onSendChatSuccess 直播禁言消息]
	 * 
	 * @return {[type]} [description]
	 */
    VHALL_SDK.on('disableChat', function(msg) {
        console.log('禁言', msg);
    });
    /**
	 * [onPermitChat 直播恢复禁言消息]
	 * 
	 * @param {[type]}
	 *            msg [description]
	 * @return {[type]} [description]
	 */
    VHALL_SDK.on('permitChat', function(msg) {
        console.log('恢复禁言', msg);
    });
    /**
	 * [onForbidChat 直播全体禁言消息]
	 * 
	 * @param {[type]}
	 *            msg [description]
	 * @return {[type]} [description]
	 */
    VHALL_SDK.on('forbidChat', function(msg) {
        console.log('全体禁言', msg);
    });
    /**
	 * [onKickout 直播踢出消息]
	 * 
	 * @param {[type]}
	 *            msg [description]
	 * @return {[type]} [description]
	 */
    VHALL_SDK.on('kickout', function(msg) {
        console.log('踢出', msg);
    });
    /**
	 * [onKickoutRestore 直播恢复踢出消息]
	 * 
	 * @param {[type]}
	 *            msg [description]
	 * @return {[type]} [description]
	 */
    VHALL_SDK.on('kickoutRestore', function(msg) {
        console.log('恢复踢出', msg);
    });
    VHALL_SDK.on('questionSwitch', function(msg) {
        if (msg.status == 1) {
            alert('问答已开启');
        } else {
            alert('问答已关闭');
        }
        
    });
    VHALL_SDK.on('streamOver', function(msg) {
        alert('活动已结束');            
    });
    VHALL_SDK.on('publishStart', function(msg) {
        alert('活动开始推流');            
    });
    
    var userInfo = "";
    VHALL_SDK.on('vhall_live_history_chat_msg', function(res) {
    	
    	if(userInfo ==""){
    		userInfo = VHALL_SDK.getUserinfo();
    	}
        if (res.code == 200 ) {
            var str = '';
            for (var i = res.data.length - 1; i >= 0; i--) {
            	if(userInfo.userid == res.data[i].user_id){
            		 str += "<div class='coze_cen_ri'> "+
        			 "<img src="+res.data[i].avatar+" alt='' "+
        			"	class='coze_cen_ri_img' /> "+
        			"  <div class='coze_cen_bg_ri'> "+
        			"	<img src='/xcviews/images/sanjiao2.png' alt='' />"+res.data[i].content+"  "+
        			" </div> "+
        			" <div class='both'></div></div>";
            	}else{
            		str += "<div class='coze_cen'>";
                    str+="<img src='"+res.data[i].avatar+"' alt='' class='coze_cen_left_img' />";
                    str+="<div class='coze_cen_bg'>";
                    str+="<img src='/xcviews/images/sanjiao.png' alt='' />"+res.data[i].content+"</div>" +
                    		"<div class='both'></div></div>";
            	}
            }
            $("#chatmsg").append(str);
            setTimeout(function(){
                $(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar('scrollTo', '999999');
            },50);
        }
    });
   
    VHALL_SDK.on('vhall_record_history_chat_msg', function(res) {
    	if(userInfo ==""){
    		userInfo = VHALL_SDK.getUserinfo();
    	}
    	if (res.code == 200 ) {
            var str = '';
            $("#chatmsg").data('curr_page', res.curr_page);
            for (var i = res.data.length - 1; i >= 0; i--) {
            	 if(userInfo.userid == res.data[i].user_id){
            	    str += "<div class='coze_cen_ri'> "+
        			 "<img src="+res.data[i].avatar+" alt='' "+
        			"	class='coze_cen_ri_img' /> "+
        			"  <div class='coze_cen_bg_ri'> "+
        			"	<img src='/xcviews/images/sanjiao2.png' alt='' />"+res.data[i].content+"  "+
        			" </div> "+
        			" <div class='both'></div></div>";
                 }else{
                	 str += "<div class='coze_cen'>";
                     str+="<img src='"+res.data[i].avatar+"' alt='' class='coze_cen_left_img' />";
                     str+="<div class='coze_cen_bg'>";
                     str+="<img src='/xcviews/images/sanjiao.png' alt='' />"+res.data[i].content+"</div>" +
                     		"<div class='both'></div></div>";
                 }
            }
            if (res.curr_page == 0){
            	$("#chatmsg").html(str);
            	setTimeout(function(){
        			$(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTo","bottom");
                },50);
            }else{
            	if(res.curr_page == 1){
            		$("#chatmsg").html(str);
            		setTimeout(function(){
            			$(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTo","bottom");
                    },50);
            		
            	}else{
            		$("#chatmsg").prepend(str);
            		setTimeout(function() {
            		   $(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTo","-=500");
            		 },100)   
            	}
            }
        }
    });
    /**
	 * 问答历史
	 */
    VHALL_SDK.on('getQuestionList', function(res) {
        if (res.code == 200 ) {
            var str = '';
            for (var i = res.data.length - 1; i >= 0; i--) {
                //str += question_template(res.data[i]);
            }
            $("#question-msg").append(str);
            setTimeout(function(){
                $(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar('scrollTo', '999999');
            },50);
        }
    });
    var falg = 1;
    $(".chatmsg-box").mCustomScrollbar({
    	scrollInertia: 200,
     	theme:"dark",
         axis:"y",
         onTotalScroll:"50px",
         alwaysTriggerOffsets:false,
         onTotalScrollBackOffse:"100px",
         onTotalScrollOffset:"100px",
         callbacks: {
             onTotalScrollBack: function() {
                 //if (VHALL_SDK.room.type != '1') {
                     var curr_page = parseInt($('#chatmsg').data('curr_page'));
                     if(falg==1){
                     	curr_page++;
                     	falg++;
                     }
                     VHALL_SDK.vhall_get_record_history_chat_msg(curr_page + 1);
                 //}
             	console.log("111111111111");
             }
         }
    });
    
    $("#sendChat").click(function() {
    	
    	var userInfo  = VHALL_SDK.getUserinfo();
    	
        var text = $("#mywords").val();
        var msg = null;
      msg = VHALL_SDK.sendChat({
	      text: text
	  });
	  if (msg)
	     var str = "<div class='coze_cen_ri'> "+
			 "<img src="+msg.avatar+" alt='' "+
			"	class='coze_cen_ri_img' /> "+
			"  <div class='coze_cen_bg_ri'> "+
			"	<img src='/xcviews/images/sanjiao2.png' alt='' />"+msg.content+"  "+
			" </div> "+
			" <div class='both'></div></div>";
	    $("#chatmsg").append(str);  
	  $("#mywords").val('');
	  $(".chatmsg-box").mCustomScrollbar("scrollTo","bottom","0");
    });
    VHALL_SDK.on("playerReady", function(){
    	/**
    	 * 设备准备就绪后
    	 */
    	$("video").attr("x5-playsinline", "");
    	$("video").attr("poster", courseHead);
        /**
		 * 可播线路消息
		 */
        VHALL_SDK.player.on('canPlayLines', function(msg) {
            var _src = '';
            for (var i in msg) {
                _src += '<li>' + i + '</li>';
            }
            $("#lines").html(_src).find("li").eq(0).addClass('active');
        });
        /**
		 * 可播清晰度消息
		 */
        VHALL_SDK.player.on('canPlayDefinitions', function(msg) {
            var _src = '';
            for (var i in msg) {
                _src += '<li>' + i + '</li>';
            }
            $("#definitions").html(_src).find("li").eq(0).addClass('active');
        });
    });



})        