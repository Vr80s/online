


function chZJ(videoId){
   return;
}

var course_id = getQueryString("courseId");

//新的token啦：频道id：ch_fc6f51f4    ，   token access:27376e92:798807b652f03baf
//access:27376e92:798807b652f03baf
/**
 * 
 */
var obj = {
    roomId:"lss_ad04fdfe",
    appId:"27376e92",
    accountId:"test_jssdk",
    token:"access:27376e92:798807b652f03baf",
    channelId:'ch_fc6f51f4'
}

  
function elsBind(){
    window.doc = {};
    
    var readyCallback = function(){
    	
     VhallLive.init({
       roomId:obj.roomId,
       type:'live',
       videoNode:'myVideo',
       complete:function(){
          VhallLive.play();
       }
     });	
    	
      window.doc = new VhallDocPassive({
        channelId:obj.channelId, //频道Id
        docNode:'my-doc-area'//文档显示节点div id
      });
      
        /**
         * 初始化聊天对象
         */
        window.chat = new VhallChat({
           channelId:'ch_fc6f51f4'//频道Id，必填
        });

        /**
         * 监听聊天消息
         */
        window.chat.on(function(msg){
            //在此收到聊天消息，消息内容为msg
            if (msg){
                var str = "<div class='coze_cen_ri'> "+
                            "<div class='coze_cen_bg_ri'>"+
                                "<span class='span_name'>"+msg.nick_name+"：</span>"+msg.data+""+
                            " </div> "+
                        "<div class='both'></div></div>";
                $("#chatmsg").append(str);  
            }
            $("#mywords").val('');
        });
        
        /**
         * 监听自定义消息
         */
        window.chat.onCustomMsg(function(msg){
        	msg = JSON.parse(msg);
        	/**
        	 * 接受到的消息
        	 */ 
        	createGiftList(msg);
        })
        
        /**
         * 某某进入直播间
         */
        window.chat.join(function(msg){
        	
        	
        	var userName = msg.nick_name;
            var content = "进入直播间";
    
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
        	
        	
        })
        
        /**
         * 某某离开直播间
         */
        window.chat.leave(function(msg){
        	
            var userName = msg.nick_name;
            var content = "退出直播间";
            
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
            console.log(msg);
            
            //赋值学习人数
            var learndCount =  sessionStorage.getItem("learndCount");
            if(isNotBlank(learndCount) && isNotBlank(msg.data.connection_online_num)){
                learndCount = parseInt(learndCount) + parseInt(msg.data.connection_online_num);
            }
            $(".details_size span:eq(0)").html(learndCount);
        })
    }

    window.Vhall.ready(readyCallback);
    
    
    window.Vhall.config({
         appId :obj.appId,//应用 ID ,必填
         accountId :obj.accountId,//第三方用户唯一标识,必填
         token:obj.token//token必填
    });

    
    
//    setTimeout(function(){
//      
//     },1000);
      
    /**
     * 发送聊天消息
     */  
    $("#sendChat").click(function() {
        $(".coze_bottom").css("bottom", "0rem");  //这是输入框在最底部,添加到其他文件不起作用
        var text = $("#mywords").val();
        if(text!=null){
        	
          window.chat.emit(text);
        
        }
    });
}
//初始化    
elsBind();

/**
 * 获取消息列表
 * @param {} pos   第几页
 * @param {} limit 每页多少条
 */
function msgList(pos,limit){
	
//	channel_id string  是   频道ID
//  type    int 否   查询类型 ，1 聊天列表（ 默认），2 自定义聊天列表
//  pos    int 否   获取条目节点，默认为 0。eg : 10 从第10条开始查询
//  limit   int 否   获取条目数量，默认为 10 条，最大为1000条
//  start_time  date    是   查询开始时间，格式为：2017/01/01
//  end_time    date    否   查询结束时间，默认为当前时间，格式为：2017/01/01
	
	
  var num = (pos - 1) * limit;
  num = num < 0 ? 0 : num;	
 	
  var params = {
    channel_id:obj.channelId,
    type:1,
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
                    var item = res.data[i];
                    var userName = item.data;
                    if(isNotBlank(item.role) &&  item.role == "host"){ //说明是主播
                        var hostName = sessionStorage.getItem("hostName");
                        userName = "<span class='span_zhubo'>主播</span>"+ (isNotBlank(hostName) ?  hostName : "");
                    }
                     e += "<div class='coze_cen_ri'> "+
                    "  <div class='coze_cen_bg_ri'> "+
                    "<span class='span_name'>"+userName+"：</span>"+   //用户名
                    "   "+item.content+"  "+
                    " </div> "+
                    " <div class='both'></div></div>";
             }	
             $("#chatmsg").html(e);
        } 	
  });      	
}
msgList();


/**
 * 
 */


  