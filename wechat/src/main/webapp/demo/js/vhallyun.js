



function chZJ(videoId){
   return;
}

var course_id = getQueryString("courseId");

//新的token啦：频道id：ch_d260ab70    ，   token access:27376e92:8520e066f987ba58
//access:27376e92:8520e066f987ba58
/**
 * 
 */
var vhallObj = {
    roomId:"lss_e96b3c35",
    appId:"27376e92",
    accountId:"test_jssdk",
    token:"access:27376e92:9d9d4041ccf028ca",
    channelId:'ch_c7fb060c',
    recordId:''
}

//直播状态1.直播中，2预告，3直播结束 4 即将直播
if(lineState == 1 || lineState == 3){
    //初始化 微吼云播放器 
    elsBind();
    
    //初始化消息
    msgList(0,10);
}  


function elsBind(){
    window.doc = {};
    
    var readyCallback = function(){
    	
        /**
         * 开始推流事件监听
         */
        VhallLive.onPublishStart(function(){
             console.log('直播开始！');
             VhallLive.play();
        })
         
        /**
         * 停止推流事件监听
         */
        VhallLive.onPublishStop(function(){
            console.log('直播结束！');
        })
 
    	
        window.doc = new VhallDocPassive({
            channelId:vhallObj.channelId, //频道Id
            docNode:'my-doc-area'//文档显示节点div id
        });
      
        var liveType = (lineState == 1 ? "live" : "vod");
        var recordId = (lineState == 1 ? "" : vhallObj.recordId);
        
        VhallLive.init({
           roomId:vhallObj.roomId,
           type:'liveType',
           recordId:vhallObj.recordId, //回放Id，点播必填，直播不写
           videoNode:'myVideo',
           complete:function(){
              VhallLive.play();
           }
        }); 
    }

    window.Vhall.ready(readyCallback);
    
    window.Vhall.config({
         appId :vhallObj.appId,//应用 ID ,必填
         accountId :vhallObj.accountId,//第三方用户唯一标识,必填
         token:vhallObj.token//token必填
    });
    
    
     
    setTimeout(function(){
    	
    	 window.Vhall.ready(function(){
    	    /**
             * 初始化聊天对象
             */
            window.chat = new VhallChat({
               channelId:vhallObj.channelId //频道Id
            });
            /**
             * 监听聊天消息
             */
            window.chat.on(function(msg){
                //在此收到聊天消息，消息内容为msg
                if (msg){
                    var str = chatLoad(msg,true);
                    if(str!=""){
                     $("#chatmsg").append(str);  
                    }
                }
                $("#mywords").val('');
            });
            
            /**
             * 监听自定义消息
             */
            window.chat.onCustomMsg(function(msg){
                msg = JSON.parse(msg);
                //在聊天消息中显示
                var str = chatLoad(msg,true);
                if(str!=""){
                   $("#chatmsg").append(str);  
                }
                //浮动效果
                createGiftList(msg);
            })
            
            /**
             * 某某进入直播间
             */
            window.chat.join(function(msg){
                viewJoinleaveRoomInfo(msg,"join");
            })
            
            /**
             * 某某离开直播间
             */
            window.chat.leave(function(msg){
                viewJoinleaveRoomInfo(msg,"leave");
            })
    	 });
         //在初始化一个消息的
    	 window.Vhall.config({
              appId :vhallObj.appId,//应用 ID ,必填
              accountId :vhallObj.accountId,//第三方用户唯一标识,必填
              token:vhallObj.token//token必填
         });
    },1000);
      
    /**
     * 发送聊天消息
     */  
    $("#sendChat").click(function() {
        $(".coze_bottom").css("bottom", "0rem");  //这是输入框在最底部,添加到其他文件不起作用
        var text = $("#mywords").val();
        if(text!=null){
          var content = {
            type:1,                 //消息类型     1 聊天消息
            message:{
                content:text,   //发送的内容
                headImg:localStorage.getItem("smallHeadPhoto"),       //发送的头像
                username:localStorage.getItem("name"),     //发送的用户名
                role:"normal"           //发送人的角色    主播： host   普通用户： normal
              } 
          }	
         /**
          * 发送消息
          */ 
         requestService("/xczh/vhall/vhallYunSendMessage",{channel_id:vhallObj.channel_id,body:JSON.stringify(content)},
                function(data) {
                if (data.success) {
                    var str = chatLoad(content,false);
                    if(str!=""){
                     $("#chatmsg").append(str);  
                    }
                    $("#mywords").val('');
                	
                }   
          }); 
        }
    });
}


/**
 * 获取消息列表
 * @param {} pos   第几页
 * @param {} limit 每页多少条
 */
function msgList(pos,limit){
	
  var num = (pos - 1) * limit;
  num = num < 0 ? 0 : num;	
 	
  var params = {
    channel_id:vhallObj.channelId,
    pos:num,
    limit:limit,
    start_time:"2017/01/01"
  }
  /**
   * 获取列表啦
   */ 
  requestService("/xczh/vhall/vhallYunMessageList",params,
        function(data) {
        if (data.success && data.resultObject.code == 200) {
        	 var res = data.resultObject;
        	 var e = "";
             for (var i = res.data.length - 1; i >= 0; i--) {
                    var item = res.data[i].data;
                    e+=chatLoad(item,isParse);
             }
             if(e!=""){
             	$("#chatmsg").html(e);
             }
        } 	
  });      	
}

/**
 * 进入或退出直播间
 * @param {} msg
 * @param {} joinOrLeave
 */
function viewJoinleaveRoomInfo(msg,joinOrLeave){
            
    var userName = msg.nick_name;
    
    var content = "进入直播间";
    if(joinOrLeave == "join"){
        content = "进入直播间";
    }else if(joinOrLeave == "leave"){
        content = "退出直播间";
    }
    console.log("参与人数："+msg.data.connection_online_num);
    console.log("当前在线人数："+msg.data.user_online_num);
    var str = "<div class='coze_cen_ri'> "+
        "  <div class='coze_cen_bg_ri'> "+
        "<span class='span_name'>"+userName+"：</span>"+   //用户名
        "   "+content+"  "+
        " </div> "+
        " <div class='both'></div></div>";
        
    $("#chatmsg").append(str);
    $(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTop","bottom");
    
    
    var learndCount =  sessionStorage.getItem("learndCount");
    if(isNotBlank(learndCount) && isNotBlank(msg.data.attend_count)){
        learndCount = parseInt(learndCount) + parseInt(msg.data.attend_count);
    }
    $(".details_size span:eq(0)").html(learndCount);
}

/**
 * 聊天消息渲染
 * @param {} content 自定义内容体
 * @return {}  是否转义
 */
function chatLoad(content,isParse){
	
	try{
        var content  = isParse ? JSON.parse(content) : content;
        var message  = content.message;
        
        var htmlStr = "";
        if(content.type == 10){ //聊天消息
             var userName = message.username;
             if(isNotBlank(message.role) &&  message.role == "host"){ //说明是主播
                  userName = "<span class='span_zhubo'>主播</span>"+ message.username ;
             }
             htmlStr =  "<div class='coze_cen_ri'> "+
                "  <div class='coze_cen_bg_ri'> "+
                "<span class='span_name'>"+userName+"：</span>"+   //用户名
                "   "+message.content+"  "+
                " </div> "+
                " <div class='both'></div></div>";
            
        }else if(content.type == 11){ //礼物消息
             htmlStr = "<div class='coze_cen_ri'> "+
                "<div class='coze_cen_bg_ri'>"+
                    "<span class='span_name'>"+message.senderInfo.userName+"：</span>" +
                "赠送给主播1个<span style='color: #F97B49;'>"+message.giftInfo.name+"</span>"+
                " </div> "+
                "<div class='both'></div></div>";
        }
        return htmlStr;
	}catch(err){
	   console.error(err);
	    return "";
	}
}

  