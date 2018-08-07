


// 请求--》json文件
var emoji = [];
$.ajax({
	type : "POST",// 请求方式
	url : "https://file.ipandatcm.com/static/img/emoji.json",// 地址，就是json文件的请求路径
	dataType : "json",// 数据类型可以为 text xml json script jsonp
	async:false,
	success : function(result) {// 返回的参数就是 action里面所有的有get和set方法的参数
		emoji = result;
	}
});

var onOff = true, isFilter = true;

$("#filter-msg").on("click", function() {//只看主办方消息   
	    if (isFilter) {   //
	        $(this).addClass("filter-yes").attr("title", "查看全部消息");
	        for (var i = 0; i < $(".chatmsg li").length; i++) {
	            var isRole = $(".chatmsg li").eq(i).data("role");
	            if (isRole == "no") {
	                $(".chatmsg li").eq(i).addClass("hide");
	            }
	        }
	        $(".chartlist").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "99999");
	        
	        //变成红色
	        $("#filter-msg").removeClass("lecturer");
	        $("#filter-msg").addClass("lecturer_filter1");
	        isFilter = false;
	    } else {
	    	
	        $(this).removeClass("filter-yes").attr("title", "只看主办方消息");
	        $(".chatmsg li").removeClass("hide");
	        $(".chartlist").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "99999");
	        
	        $("#filter-msg").removeClass("lecturer_filter1");
	        $("#filter-msg").addClass("lecturer");
	        isFilter = true;
	    }
})


if(liveStatus == 1 || liveStatus == 3){
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
      
      
      var liveType = (liveStatus == 1 ? "live" : "vod");
      var recordId = (liveStatus == 1 ? "" : vhallObj.recordId);
        
      
      //判断是回放呢，还是直播呢
      
      VhallLive.init({
       roomId:vhallObj.roomId,
       recordId:recordId, //回放Id，点播必填，直播不写
       type:"live",
       videoNode:'myVideo',
       complete:function(){
          VhallLive.play();
       }
     });    
        
    }

    window.Vhall.ready(readyCallback);
    
    
    //配置文档和直播
    window.Vhall.config({
         appId :vhallObj.appId,//应用 ID ,必填
         accountId :vhallObj.accountId,//第三方用户唯一标识,必填
         token:vhallObj.token//token必填
    });

    /**
     * 加载消息
     */
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
                var str = chatLoad(msg);
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
             
             try{
             	var e="";
                if(msg.type ==10 ){//聊天
                    e+=liaotian(msg);
                }else if(msg.type == 11){ //礼物
                    e+=liveGiftList(msg);
                     //在礼物区域显示
                	createGiftList(msg.message);
                }else if(msg.type == 12){ // 开始直播啦
                
                }if(msg.type == 13){ //直播结束了  
                
                }
                
                if (e != "") {
					$("#chatmsg").append(e);
				}
               
             }catch(error){
               console.error(error);
             }
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
                headImg:smallHeadPhoto,       //发送的头像
                username:nickname,     //发送的用户名
                role:"normal"           //发送人的角色    主播： host   普通用户： normal
              } 
          } 
         /**
          * 发送消息
          */ 
         RequestService("/vhallyun/sendMessage","get",{channel_id:vhallObj.channel_id,body:JSON.stringify(content)},
                function(data) {
                if (data.success) {
                    var str = liaotian(content.message,false);
                    if(str!=""){
                     $("#chatmsg").append(str);  
                    }
                    $("#mywords").val('');
                }   
          }); 
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
  RequestService("/vhallyun/message","get",params,
        function(data) {
        if (data.success && data.resultObject.code == 200) {
        	 var res = data.resultObject;
        	 var e = "";
             for (var i = res.data.length - 1; i >= 0; i--) {
                var item = res.data[i].data;
                try{
                    item = JSON.parse(JSON.parse(item));
                	if(item.type ==10 ){//聊天
                        e+=liaotian(item);
                    }else if(item.type == 11){ //礼物
                        e+=liveGiftList(item);
                    }
                }catch(error){
                   console.error(error);
                }
             }
             if(e!=""){
             	 $("#chatmsg").html(e);
             }
        } 	
  });      	
}
msgList(0,10);



  //聊天消息
function liaotian(obj){
	obj = obj.message;
	
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
//      头像注释
    role_str+="<a class='name' href='javascript:;' title=' user_name'>"+obj.username+" </a>";
    
    var className = "";
    if(!isFilter && role =="user" ){
        className = "hide";
    }
    
    //替换表情为url
    var contentEmoji = (obj.content!=null && obj.content!="") ? replaceEmoji(obj.content) :obj.content;
    
    var aaa = "<li uid=' user_id' data-role="+str_hide+" class="+className+">"+
    /*聊天区域*/
        "<div class='msg'>"+
        "<p> " + role_str+"：<span style='color:#fff;'>"+contentEmoji+"</span></p >"+
        "</div>"+
      "</li>";
    return aaa;
}
    
//进入直播间
function viewJoinleaveRoomInfo(msg,joinOrLeave){

	var userName = msg.nick_name;
	var content = "进入直播间";
    if(joinOrLeave == "join"){
        content = "进入直播间";
    }else if(joinOrLeave == "leave"){
        content = "退出直播间";
    }
    var  role_str="<a class='name' href='javascript:;' title=' user_name'>"+userName+" </a>";
    var className = "";
    if(!isFilter){
        className = "hide";
    }
    var aaa = "<li uid=' user_id' data-role='no'  class="+className+" >"+
    /*聊天区域*/
        "<div class='msg'>"+
            "<p> " + role_str+"：<span style='color:#fff;'>"+content+"</span></p >"+
        "</div>"+
      "</li>";
   
    $("#chatmsg").append(aaa);
    
}
    
    
//送礼
function liveGiftList(obj){
	var message = obj.message;
    var  role_str="<a class='name' href='javascript:;' title=' user_name'>"+message.senderInfo.userName+" </a>";
    var className = "";
    if(!isFilter){
        className = "hide";
    }
    var aaa = "<li uid=' user_id' data-role='no'  class="+className+" >"+
    /*聊天区域*/
        "<div class='msg'>"+
            "<p> " + role_str+"：赠送给主播1个<span style='color:#fff;'>"+message.giftInfo.name+"</span></p >"+
        "</div>"+
      "</li>";
    return aaa;
  }


var pattern1 = /\[[\u4e00-\u9fa5]+\]/g;
var pattern2 = /\[[\u4e00-\u9fa5]+\]/;
/**
 * 表情
 */
function replaceEmoji(contents) {
	content = contents.match(pattern1);
	if(content == null){
	  return contents;
	}
	str = contents;
	for (i = 0; i < content.length; i++) {
		for (j = 0; j < emoji.length; j++) {
			if ("[" + emoji[j].text + "]" == content[i]) {
				src = emoji[j].imgUrl;
				break;
			}
		}
		var imgBag = "<img src="+src+" />";
		str = str.replace(pattern2, imgBag);
	}
	return str;
}
