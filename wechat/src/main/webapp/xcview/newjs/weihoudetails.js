

var vhallId = "";

var falg = 1;

/**
 * 初始化视频啦
 * @param videoId
 */
function chZJ(videoId,vhallId){
	
	//主播的用户id
	vhallId = vhallId;
	
	//获取开播时间和
	
    var map;
    requestService("/bxg/common/getWeihouSign", {video:videoId}, function(data) {
    	map  = data.resultObject;
    },false)
    $("#video").html("");
    
    var weihouSignInfo ={
    		facedom: "#face",
            textdom: "#mywords",
            app_key: map.app_key,// 第三方app_key
            signedat: map.signedat,// 签名时间戳
            sign: map.sign,// 签名
            email: map.email,
            roomid: map.roomid,// 活动id
            account: map.account,// 第三方用户id
            username: map.username,// 用户昵称
            docContent: "#doc"
    }
    weihouSignInfo.videoContent="#video";
    VHALL_SDK.init(weihouSignInfo);
}

/**
 * 在聊天区域增加自定义的信息
 * @param content  内容
 * @param type 1 进入房间提示
 */
function liaoTianArea(content,type){
	 	var msg = null;
	    msg = VHALL_SDK.sendChat({
		      text: content
		});
	    var content ="";
	    if(type == 1){
	    	content = "进入直播间";
	    }
	    var room = VHALL_SDK.getRoominfo();
		if (msg && room.type == 1){
			var str = "<div class='coze_cen_ri'> "+
					"<div class='coze_cen_bg_ri'>"+
						"<span class='span_name'>"+msg.user_name+"：</span>"+content+""+
					" </div> "+
				" <div class='both'></div></div>";
        $("#chatmsg").append(str);  
		}
		$("#mywords").val('');
	    setTimeout(function(){
	    	  $(".chatmsg-box").mCustomScrollbar("scrollTo","bottom","0");
     },50);
}


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
   
    
    
	window.setInterval(function(){
	
		console.log("直播状态："+VHALL_SDK.getRoominfo().type)
		
	},1000);
    
    
    
    
    
    /**
	 * [onChatMessage 直播收到聊天消息]
	 * 
	 * @param {[type]}
	 *            msg [object]
	 * @return {[type]} [description]
	 */
    VHALL_SDK.on('chatMsg', function(msg) {
    	
    	var userName = msg.user_name;
    	if(msg.role =="host"){ //说明是主播
    		userName = "主播    "+userName;
    	}
    	var str = "<div class='coze_cen_ri'> "+
		"  <div class='coze_cen_bg_ri'> "+
		"<span class='span_name'>"+userName+"：</span>"+   //用户名
		"	"+item.content+"  "+
		" </div> "+
		" <div class='both'></div></div>";
    	
        $("#chatmsg").append(str);
        $(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTo","bottom");
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
        
        
        var room  = VHALL_SDK.getRoominfo();
        if(room.type  == 3 ){
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
                    }
                }
            });
            $(".chatmsg-box").mCustomScrollbar("scrollTo","bottom","0");
        }else if(room.type == 1){
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
                            VHALL_SDK.vhall_get_live_history_chat_msg(curr_page + 1);
                        //}
                    }
                }
            });
        	$(".chatmsg-box").mCustomScrollbar("scrollTo","bottom","0");
        } 
        
    });
    /**
	 * [用户上线]
	 * 
	 * @param {[type]}
	 *            msg [description]
	 * @return {[type]} [description]
	 */
    VHALL_SDK.on('userOnline', function(msg) {
    	
    	//alert("userOnline:"+msg);
    	
/*        msg = VHALL_SDK.sendChat({
		      text: content
		});*/
    	
    	var userName = msg.user_name;
    	var content = "进入直播间";

    	var str = "<div class='coze_cen_ri'> "+
		"  <div class='coze_cen_bg_ri'> "+
		"<span class='span_name'>"+userName+"：</span>"+   //用户名
		"	"+content+"  "+
		" </div> "+
		" <div class='both'></div></div>";
    	
        $("#chatmsg").append(str);
        $(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTo","bottom");
    	
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
    	
    	//alert("userOffline:"+msg);
    	
/*        msg = VHALL_SDK.sendChat({
		      text: content
		});*/
  	
	  	var userName = msg.user_name;
	  	var content = "退出直播间";
	
	  	var str = "<div class='coze_cen_ri'> "+
			"  <div class='coze_cen_bg_ri'> "+
			"<span class='span_name'>"+userName+"：</span>"+   //用户名
			"	"+content+"  "+
			" </div> "+
			" <div class='both'></div></div>";
  	
        $("#chatmsg").append(str);
        $(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTo","bottom");
    	
    	
        console.log(msg);
    });
    
    /**
	 * [onSendChatSuccess 消息发送回调事件]
	 * class="span_name"
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
            //alert('问答已开启');
        } else {
            //alert('问答已关闭');
        }
        
    });
    VHALL_SDK.on('streamOver', function(msg) {
        
    	alert('活动已结束'+msg);   

    	$("#video").html("");
    	
		$(".video_end_top").show(); 
    	
    });
    VHALL_SDK.on('publishStart', function(msg) {
        alert('活动开始推流'+msg);
        alert("==========================")
        //如果活动开始了
        $(".video_end_top").hide();
        
    });
    
    var userInfo = "";
    VHALL_SDK.on('vhall_live_history_chat_msg', function(res) {
    	if(userInfo ==""){
    		userInfo = VHALL_SDK.getUserinfo();
    	}
        if (res.code == 200 ) {
        	var Name = localStorage.name;
            var str = '';
            
            for (var i = res.data.length - 1; i >= 0; i--) {
            	var item = res.data[i];
            	
            	var userName = item.user_name;
            	if(vhallId == item.account_id){ //说明是主播
            		userName = "主播    "+userName;
            	}
        		 str += "<div class='coze_cen_ri'> "+
    			"  <div class='coze_cen_bg_ri'> "+
    			"<span class='span_name'>"+userName+"：</span>"+   //用户名
    			"	"+item.content+"  "+
    			" </div> "+
    			" <div class='both'></div></div>";
            }
            $("#chatmsg").append(str);
            setTimeout(function(){
            	$(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTo","bottom","0");
            },50);
        }
    });
   
   
   
    VHALL_SDK.on('vhall_record_history_chat_msg', function(res) {
    	
    	if(userInfo ==""){
    		userInfo = VHALL_SDK.getUserinfo();
    	}
    	if (res.code == 200 ) {
    		
    		var Name = localStorage.name;
            var str = '';
            
            $("#chatmsg").data('curr_page', res.curr_page);
            for (var i = res.data.length - 1; i >= 0; i--) {
            	
            	var item = res.data[i];
            	var userName = item.user_name;
            	if(vhallId == item.account_id){ //说明是主播
            		userName = "主播    "+userName;
            	}
        		 str += "<div class='coze_cen_ri'> "+
    			"  <div class='coze_cen_bg_ri'> "+
    			"<span class='span_name'>"+userName+"：</span>"+   //用户名
    			"	"+item.content+"  "+
    			" </div> "+
    			" <div class='both'></div></div>";
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
   
    
    $("#sendChat").click(function() {
	    var text = $("#mywords").val();
	    giftDsPuTong(text);
    });
    
    /**
     * 普通聊天、发送礼物、打赏要显示的
     * 在评论区应该显示的内容
     */
    function giftDsPuTong(content){
        var userInfo  = VHALL_SDK.getUserinfo();
        var msg = null;
 	    msg = VHALL_SDK.sendChat({
 		      text: content
 		  });
 	    var room = VHALL_SDK.getRoominfo();
 		if (msg && room.type == 1){
 			var str = "<div class='coze_cen_ri'> "+
						"<div class='coze_cen_bg_ri'>"+
							"<span class='span_name'>"+msg.user_name+"：</span>"+msg.content+""+
						" </div> "+
					" <div class='both'></div></div>";
	        $("#chatmsg").append(str);  
 		}
 		$("#mywords").val('');
 	    setTimeout(function(){
 	    	  $(".chatmsg-box").mCustomScrollbar("scrollTo","bottom","0");
         },50);
    }
    
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